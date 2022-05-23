package com.actia.projects.services;

import java.util.List;

import com.actia.projects.dto.RequestFileStatDto;
import com.actia.projects.dto.StatRequestFiles;
import com.actia.projects.dto.TargetStatDto;
import com.actia.projects.entities.Brand;
import com.actia.projects.entities.RequestFile;
import com.actia.projects.entities.Target;
import com.actia.projects.entities.UserEntity;

public interface RequestFileService {

	RequestFile createRequestFile(RequestFile requestFile);
	RequestFile getRequestFile(String id);
	RequestFile updateRequestFile(RequestFile requestFile);
	List<RequestFile> getAllRequestFile();
	List<RequestFile> getAllRequestFileFR();
	List<RequestFile> getAllRequestFileUS();
	String getuserName (String id);
	Brand createBrand(Brand brand);
	Target createTarget(Target target);
	List<Brand> retrieveAllBrands();
	List<Target> retrieveAllTargets();
	void deleteBrand(String id);
	void deleteTarget(String id);
	List<RequestFile> getAllRequestsByUser(String id);
	List<RequestFile> getAllUsersByTL(String id);
	StatRequestFiles getStat();
    int getRfParUser(String id);
    RequestFile getRequestFileByName (String name);
    int getRejectedParUser(String id);
    List<TargetStatDto> getStatTargetPerWeek(int year);
    List<RequestFileStatDto> findRejectedFilesByUser ();
    List<RequestFileStatDto> findFinishedFilesByUser ();

}
