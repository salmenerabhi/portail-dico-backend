package com.actia.projects.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.actia.projects.entities.Log;
import com.actia.projects.entities.Log.LogType;
import com.actia.projects.repository.LogsRepository;

@Service
public class LogServiceImpl  implements LogService {

	@Autowired
	LogsRepository logsRepository;
	
	
	//Create a log
	@Override
	public Log createLog(Log log){
        log.setCreationDate(new java.util.Date());
		return logsRepository.save(log);
	}
	
	//Get a log
	@Override
	public Log getLog (String id){
		return logsRepository.getById(id);
	}
	
	//Get the error log
	@Override
	public Log getLogError (String id){
		return logsRepository.findLogError();
	}
	
	//Get the TTBT log
	@Override
		public Log getLogTTBT (String id){
			return logsRepository.findLogTTBT();
		}
		
	//Get a list of all logs
	@Override
	public List<Log> getAllLogs() {
		return (List<Log>) logsRepository.findAll();
	}
	
	//Delete a log
	@Override
	public 	 void deleteLog(String id) {
		logsRepository.deleteById(id);
		
	}
	
	//Get a log by type
	@Override
	public String getLogByType() {
		return logsRepository.findByType(LogType.Ref).get(0).getId();
	}
}
