package com.actia.projects.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.actia.projects.dto.StatRequestFiles;
import com.actia.projects.dto.UserDto;
import com.actia.projects.entities.Brand;
import com.actia.projects.entities.Checklist;
import com.actia.projects.entities.RequestFile;
import com.actia.projects.entities.RequestFile.State;
import com.actia.projects.entities.Target;
import com.actia.projects.entities.Tool;
import com.actia.projects.entities.UserEntity;
import com.actia.projects.repository.BrandRepository;
import com.actia.projects.repository.ChecklistRepository;
import com.actia.projects.repository.FileDBRepository;
import com.actia.projects.repository.RequestFileRepository;
import com.actia.projects.repository.TargetRepository;
import com.actia.projects.repository.UserRepository;
import java.lang.reflect.Type;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

@Service
public class RequestFileServiceImpl implements RequestFileService{

	@Autowired
	RequestFileRepository requestFileRepository	;
	@Autowired
	ChecklistRepository checklistRepository	;
	@Autowired
	UserRepository userRepository;
	@Autowired
	BrandRepository brandRepository;
	@Autowired
	TargetRepository targetRepository;

	//Create a request file
	@Override
	public RequestFile createRequestFile(RequestFile requestFile){
		return requestFileRepository.save(requestFile);
	}
	
	//Get a request file
	@Override
	public RequestFile getRequestFile (String id){
		return requestFileRepository.getById(id);
	}
	
	//Update a request file
	@Override
	public RequestFile updateRequestFile(RequestFile requestFile){
		return requestFileRepository.save(requestFile);
	}
	
	//Create a request file
	@Override
	public List<RequestFile> getAllRequestFile() {
		 List<RequestFile> rf= requestFileRepository.findAll();
		 List<RequestFile> requestDto = new ArrayList<>();
	     for (RequestFile files : rf) {
	         ModelMapper modelMapper = new ModelMapper();
	         RequestFile requestFile = modelMapper.map(files, RequestFile.class);
	         requestDto.add(requestFile);
	     }
	     return requestDto;
	}
	
	//Get a list of all an Finished and Rejected request files 
	@Override	
	public List<RequestFile> getAllRequestFileFR() {
		return  requestFileRepository.findAllFR();
		
	}
	
	//Get a list of all an Unstarted and InProgress request files 
	@Override
	public List<RequestFile> getAllRequestFileUS() {
		return  requestFileRepository.findAllUS();
		
	}
	
	//Get a request file's username
	@Override	
	public String getuserName (String id){
		return userRepository.getById(id).getFirstName();
	}
	
	// Create a brand
	@Override
	public Brand createBrand(Brand brand){
	
		return brandRepository.save(brand);
	}
	
	// Create a target
	@Override
	public Target createTarget(Target target){
	
		return targetRepository.save(target);
	}
	
	//Get a list of all brands 
	@Override
	public List<Brand> retrieveAllBrands() {
		brandRepository.findAll();
		return (List<Brand>) brandRepository.findAll();
	}
	
	//Get a list of all targets
	@Override
	public List<Target> retrieveAllTargets() {
		targetRepository.findAll();
		return (List<Target>) targetRepository.findAll();
	}
	
	//Delete a brand
	@Override
	public 	 void deleteBrand(String id) {
		brandRepository.deleteById(id);	
	}
	
	//Delete a target
	@Override
	public 	 void deleteTarget(String id) {
		targetRepository.deleteById(id);
	}
	
	//Get a list of all request files by user 
	@Override
	public List<RequestFile> getAllRequestsByUser(String id) {
		List<RequestFile> requestfiles = (List<RequestFile>) requestFileRepository.findAllFilesByUser(id);
		return requestfiles;
	}
	
	//Get a list of all request files by Team Leader 
	@Override
	public List<RequestFile> getAllUsersByTL(String id) {
		List<RequestFile> requestfiles = (List<RequestFile>) requestFileRepository.findAllFilesByTL(id);
		return requestfiles;
	}
	
	//Get request files state statistics
	@Override
	public StatRequestFiles getStat(){
		StatRequestFiles stat=new StatRequestFiles();
	    stat.setRejected(requestFileRepository.countRejected());
	    stat.setUnstarted(requestFileRepository.countunstarted());
	    stat.setFinished(requestFileRepository.countfinished());
	    stat.setIn_progress(requestFileRepository.countinprogress());
	    stat.setVerified(requestFileRepository.countverified());
	    stat.setTo_verify(requestFileRepository.counttoverify());
	    return stat;
	}
	}
