package com.actia.projects.services;

import java.util.List;

import com.actia.projects.entities.Log;

public interface LogService {

	Log createLog(Log log);
	Log getLog (String id);
	Log getLogError (String id);
	Log getLogTTBT (String id);
	List<Log> getAllLogs();
	void deleteLog(String id);
}
