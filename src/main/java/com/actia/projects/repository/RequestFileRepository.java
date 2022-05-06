package com.actia.projects.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Repository;

import com.actia.projects.entities.RequestFile;
import com.actia.projects.entities.RequestFile.State;


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
}
