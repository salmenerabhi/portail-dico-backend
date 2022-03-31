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
	@Query("Select r from RequestFile r  where r.state='in_progress' or r.state='unstarted' ")
	List<RequestFile> findAllUS ();
	@Query("from RequestFile e where not(e.echeanceRD < :from and e.echeanceRC > :to)")
	public List<RequestFile> findBetween(@Param("from") Date from, @Param("to") @DateTimeFormat(iso=ISO.DATE_TIME) Date to);
}
