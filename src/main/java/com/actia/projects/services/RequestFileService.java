package com.actia.projects.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.actia.projects.dto.UserDto;
import com.actia.projects.entities.Checklist;
import com.actia.projects.entities.RequestFile;
import com.actia.projects.entities.RequestFile.State;
import com.actia.projects.entities.Tool;
import com.actia.projects.entities.UserEntity;
import com.actia.projects.repository.ChecklistRepository;
import com.actia.projects.repository.FileDBRepository;
import com.actia.projects.repository.RequestFileRepository;
import com.actia.projects.repository.UserRepository;

import java.lang.reflect.Type;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

@Service
public class RequestFileService {
@Autowired
	RequestFileRepository requestFileRepository	;
@Autowired
ChecklistRepository checklistRepository	;
@Autowired
UserRepository userRepository;

public RequestFile createRequestFile(RequestFile requestFile){

	return requestFileRepository.save(requestFile);
}
public RequestFile getRequestFile (String id){
	return requestFileRepository.getById(id);
}

public RequestFile updateRequestFile(RequestFile requestFile){

	return requestFileRepository.save(requestFile);
}





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
	
public List<RequestFile> getAllRequestFileFR() {
	return  requestFileRepository.findAllFR();
	
}
public List<RequestFile> getAllRequestFileUS() {
	return  requestFileRepository.findAllUS();
	
}
	
public String getuserName (String id){
	return userRepository.getById(id).getFirstName();
}
}
