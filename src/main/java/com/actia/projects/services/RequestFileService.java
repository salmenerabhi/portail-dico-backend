package com.actia.projects.services;

import java.util.List;

import com.actia.projects.dto.CalendarEvents;
import com.actia.projects.dto.NbrMarqueFamille;
import com.actia.projects.dto.NbrMarqueFamilleRole;
import com.actia.projects.dto.NbrMarqueSite;
import com.actia.projects.dto.NbrRejectedRCDateDto;
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
    List<NbrMarqueFamille> findnbrphraseFamilleMarque ();
    List<RequestFileStatDto> findTreatmentperRequest ();
    List<NbrMarqueSite> findnbrphraseMarqueSite ();
    List<NbrMarqueSite> findnbrphraseMarqueSiteweek ();
    List<NbrMarqueSite> findnbrphraseMarqueSitemonth ();
    List<NbrMarqueSite> findnbrphraseMarqueSiteyear ();
    List<NbrMarqueSite> findnbrphraseMarqueSitecible ();
    List<NbrRejectedRCDateDto> NbrRejectedRCday ();
    List<NbrRejectedRCDateDto> NbrRejectedRCweek ();
    List<NbrRejectedRCDateDto> NbrRejectedRCmonth ();
    List<NbrRejectedRCDateDto> NbrRejectedRCyear ();
    List<NbrRejectedRCDateDto> NbrRejectedRCcible ();
    List<RequestFileStatDto> findnbrbymarque ();
    List<RequestFileStatDto> findnbrRejectedbymarque ();
    List<NbrMarqueFamilleRole> findnbrbymarqueFamilleRole ();


    List <CalendarEvents> getEvents();



}
