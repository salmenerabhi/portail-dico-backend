package com.actia.projects.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Repository;

import com.actia.projects.dto.CalendarEvents;
import com.actia.projects.dto.NbrMarqueFamille;
import com.actia.projects.dto.NbrMarqueFamilleRole;
import com.actia.projects.dto.NbrMarqueSite;
import com.actia.projects.dto.NbrRejectedRCDateDto;
import com.actia.projects.dto.RequestFileStatDto;
import com.actia.projects.dto.TargetStatDto;
import com.actia.projects.entities.RequestFile;
import com.actia.projects.entities.UserEntity;


@Repository
public interface RequestFileRepository extends JpaRepository<RequestFile, String> {
	
	@Query(value="Select id as Id , name as Subject,echeanceRC as StartTime,echeanceRD as EndTime from request_file r",nativeQuery = true)
	List<CalendarEvents> findEvents ();
	
	@Query("Select r from RequestFile r  where r.state='rejected' or r.state='finished' ")
	List<RequestFile> findAllFR ();
	@Query("Select r from RequestFile r  where r.state='in_progress' or r.state='unstarted' or r.state='verified' ")
	List<RequestFile> findAllUS ();
	@Query("Select r from RequestFile r  where r.user.id=:id ")
	List<RequestFile> findAllFilesByUser (@Param("id") String id);
	@Query("from RequestFile e where not(e.echeanceRD < :from and e.echeanceRC > :to)")
	public List<RequestFile> findBetween(@Param("from") Date from, @Param("to") @DateTimeFormat(iso=ISO.DATE_TIME) Date to);

	@Query("Select r from RequestFile r"
			+ " join r.user u"
			+ " where u.responsable=:id ")
	List<RequestFile> findAllFilesByTL (@Param("id") String id);
	
	@Query("SELECT COUNT(r) FROM RequestFile r WHERE r.state='rejected' ")
    public int countRejected();
	@Query("SELECT COUNT(r) FROM RequestFile r WHERE r.state='unstarted' ")
    public int countunstarted();
	@Query("SELECT COUNT(r) FROM RequestFile r WHERE r.state='finished' ")
    public int countfinished();
	@Query("SELECT COUNT(r) FROM RequestFile r WHERE r.state='in_progress' ")
    public int countinprogress();
	@Query("SELECT COUNT(r) FROM RequestFile r WHERE r.state='verified' ")
    public int countverified();
	@Query("SELECT COUNT(r) FROM RequestFile r WHERE r.state='to_verify' ")
    public int counttoverify();
	
	@Query("SELECT COUNT(r) FROM RequestFile r WHERE r.fileType='Demande'")
    public int countdemandes();
	@Query("SELECT COUNT(r) FROM RequestFile r WHERE r.fileType='Demande' AND r.state='finished' ")
    public int countdemandestraites();
	@Query("SELECT COUNT(r) FROM RequestFile r WHERE r.fileType='Demande' AND r.state='rejected' ")
    public int countdemandesrejetes();
	
	@Query("SELECT COUNT(r) FROM RequestFile r WHERE r.fileType='Approximation'")
    public int countapproximation();
	@Query("SELECT COUNT(r) FROM RequestFile r WHERE r.fileType='Approximation' AND r.state='finished' ")
    public int countapproximationtraites();
	@Query("SELECT COUNT(r) FROM RequestFile r WHERE r.fileType='Approximation' AND r.state='rejected' ")
    public int countapproximationrejetes();
	
	
	@Query(value="SELECT COUNT(*) AS value, date_part('week', echeanceRD) as week FROM request_file r WHERE EXTRACT(year from echeanceRD)= :year GROUP BY week" ,nativeQuery = true)
    public List<TargetStatDto> countTarget(@Param("year") int year);
	@Query("SELECT COUNT(r) FROM RequestFile r WHERE r.user.id=:id ")
    public int countdemandesParUser(@Param("id") String id);
	@Query("SELECT COUNT(r) FROM RequestFile r WHERE r.user.id=:id and r.state='rejected'")
    public int countrejectedParUser(@Param("id") String id);
	
	
	@Query(value="SELECT COUNT(*) AS value, first_name as firstname FROM request_file r , users u WHERE r.user_id=u.id AND r.state='rejected' GROUP BY firstname" ,nativeQuery = true)
	List<RequestFileStatDto> findRejectedFilesByUsers ();
	
	@Query(value="SELECT COUNT(*) AS value, first_name as firstname FROM request_file r , users u WHERE r.user_id=u.id AND r.state='finished' GROUP BY firstname" ,nativeQuery = true)
	List<RequestFileStatDto> findFinishedFilesByUsers ();
	
	@Query(value="SELECT famille as famille,marque as marque, SUM(nombrephrase) as nbr FROM request_file r, brand b WHERE r.marque_id=b.id GROUP BY marque, famille ORDER BY nbr asc" ,nativeQuery = true)
	List<NbrMarqueFamille> findnbrphraseFamilleMarque ();
	
	//Calculate the treatment time of a request
	@Query(value="SELECT name as firstname ,COALESCE(EXTRACT(DAY FROM  echeanceRD- echeanceRC ),0) AS value FROM request_file r;" ,nativeQuery = true)
	List<RequestFileStatDto> findTreatementbyRequest ();
	
	// find number of phrases per brand and per site
	@Query(value="SELECT site as famille,marque as marque, SUM(nombrephrase) as nbr, date_part('day', echeanceRD) as date FROM request_file r,users u , brand b WHERE r.marque_id=b.id and r.user_id=u.id GROUP BY marque, site,date ORDER BY marque asc" ,nativeQuery = true)
	List<NbrMarqueSite> findnbrphraseMarqueSite ();
	@Query(value="SELECT site as famille,marque as marque, SUM(nombrephrase) as nbr, date_part('week', echeanceRD) as date FROM request_file r,users u , brand b WHERE r.marque_id=b.id and r.user_id=u.id GROUP BY marque, site,date ORDER BY marque asc" ,nativeQuery = true)
	List<NbrMarqueSite> findnbrphraseMarqueSiteweek ();
	@Query(value="SELECT site as famille,marque as marque, SUM(nombrephrase) as nbr, date_part('month', echeanceRD) as date FROM request_file r,users u , brand b WHERE r.marque_id=b.id and r.user_id=u.id GROUP BY marque, site,date ORDER BY marque asc" ,nativeQuery = true)
	List<NbrMarqueSite> findnbrphraseMarqueSitemonth ();
	@Query(value="SELECT site as famille,marque as marque, SUM(nombrephrase) as nbr, date_part('year', echeanceRD) as date FROM request_file r,users u , brand b WHERE r.marque_id=b.id and r.user_id=u.id GROUP BY marque, site,date ORDER BY marque asc" ,nativeQuery = true)
	List<NbrMarqueSite> findnbrphraseMarqueSiteyear ();
	@Query(value="SELECT site as famille,marque as marque, SUM(nombrephrase) as nbr, CAST(cible AS INTEGER) as date FROM request_file r,users u , brand b , target c WHERE r.marque_id=b.id and r.user_id=u.id and r.cible_id=c.id GROUP BY marque, site,date ORDER BY nbr asc" ,nativeQuery = true)
	List<NbrMarqueSite> findnbrphraseMarqueSitecible ();
	
	// find number of phrases per brand and per site
	@Query(value="SELECT COUNT(*) AS value, first_name as firstname ,date_part('day', echeanceRD) as date FROM request_file r , users u WHERE r.user_id=u.id AND r.state='rejected' GROUP BY  date, firstname ORDER BY firstname" ,nativeQuery = true)
	List<NbrRejectedRCDateDto> NbrRejectedRCday ();
	@Query(value="SELECT COUNT(*) AS value, first_name as firstname ,date_part('week', echeanceRD) as date FROM request_file r , users u WHERE r.user_id=u.id AND r.state='rejected' GROUP BY  date, firstname ORDER BY firstname" ,nativeQuery = true)
	List<NbrRejectedRCDateDto> NbrRejectedRCweek ();
	@Query(value="SELECT COUNT(*) AS value, first_name as firstname ,date_part('month', echeanceRD) as date FROM request_file r , users u WHERE r.user_id=u.id AND r.state='rejected' GROUP BY  date, firstname ORDER BY firstname" ,nativeQuery = true)
	List<NbrRejectedRCDateDto> NbrRejectedRCmonth ();
	@Query(value="SELECT COUNT(*) AS value, first_name as firstname ,date_part('year', echeanceRD) as date FROM request_file r , users u WHERE r.user_id=u.id AND r.state='rejected' GROUP BY  date, firstname ORDER BY firstname" ,nativeQuery = true)
	List<NbrRejectedRCDateDto> NbrRejectedRCyear ();
	@Query(value="SELECT COUNT(*) AS value, first_name as firstname ,CAST(cible AS INTEGER) as date FROM request_file r , users u, target c WHERE r.user_id=u.id AND r.state='rejected' AND r.cible_id=c.id GROUP BY  date, firstname ORDER BY firstname" ,nativeQuery = true)
	List<NbrRejectedRCDateDto> NbrRejectedRCcible ();
	
	//
	@Query(value="SELECT marque as firstname, SUM(nombrephrase) as value  FROM request_file r, brand b WHERE r.marque_id=b.id GROUP BY marque",nativeQuery = true)
	List<RequestFileStatDto> findnbrbymarque();
	@Query(value="SELECT COUNT(r) as  value, marque as firstname FROM Request_file r,brand b WHERE r.state='rejected' AND r.marque_id=b.id GROUP BY marque",nativeQuery = true)
	List<RequestFileStatDto> findnbrRejectedbymarque();
	@Query(value="SELECT marque as marque, SUM(nombrephrase) as nbr, role as role ,famille as famille FROM request_file r, brand b, users u  WHERE r.user_id=u.id and r.marque_id=b.id GROUP BY marque,famille, role order by marque",nativeQuery = true)
	List<NbrMarqueFamilleRole> findnbrbymarquerole();
	
	public RequestFile findByName(String name);

}
