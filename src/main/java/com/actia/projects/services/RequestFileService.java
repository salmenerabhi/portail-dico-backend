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
import com.actia.projects.entities.RequestFile;
import com.actia.projects.entities.Tool;
import com.actia.projects.entities.UserEntity;
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
UserRepository userRepository;

public RequestFile createRequestFile(RequestFile requestFile){

	return requestFileRepository.save(requestFile);
}
public RequestFile getRequestFile (String id){
	return requestFileRepository.getById(id);
}

//public List<RequestFile> getAllFiles(String email) {
//
//    UserEntity currentUser = userRepository.findByEmail(email);
//
//    List<RequestFile> files = currentUser.getRequestFile();
//
//    Type listType = new TypeToken<List<RequestFile>>() {}.getType();
//    List<RequestFile> requestFile = new ModelMapper().map(files, listType);
//
//    return requestFile;
//}

public List<RequestFile> getAllRequestFile() {
	 List<RequestFile> rf= requestFileRepository.findAll();
	
	 List<RequestFile> requestDto = new ArrayList<>();
     for (RequestFile files : rf) {


         ModelMapper modelMapper = new ModelMapper();
         RequestFile user = modelMapper.map(files, RequestFile.class);

         requestDto.add(user);
     }
     return requestDto;
}
	
	
}
