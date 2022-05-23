package com.actia.projects.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Repository;

import com.actia.projects.dto.RequestFileStatDto;
import com.actia.projects.dto.TargetStatDto;
import com.actia.projects.entities.RequestFile;
import com.actia.projects.entities.UserEntity;


@Repository
public interface RequestFileRepository extends JpaRepository<RequestFile, String> {
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
	
	public RequestFile findByName(String name);

}
