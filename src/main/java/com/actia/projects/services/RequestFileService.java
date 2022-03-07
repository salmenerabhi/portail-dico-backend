package com.actia.projects.services;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.actia.projects.entities.RequestFile;
import com.actia.projects.repository.FileDBRepository;
import com.actia.projects.repository.RequestFileRepository;

@Service
public class RequestFileService {
@Autowired
	RequestFileRepository requestFileRepository	;

	
	
}
