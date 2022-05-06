package com.actia.projects.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.actia.projects.entities.Log;
import com.actia.projects.entities.Log.LogType;


public interface LogsRepository extends  JpaRepository<Log,String> {

	@Query("Select l from Log l  where l.type='Error'")
	public Log findLogError ();
	
	@Query("Select l from Log l  where l.type='TTBT'")
	public Log findLogTTBT ();
	
	List<Log> findByType(LogType type);
}

