package com.actia.projects.services;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.actia.projects.entities.FileDB;
import com.actia.projects.entities.Tool;
import com.actia.projects.repository.ToolRepository;

@Service
public class ToolServiceImpl  {

	
	
	@Autowired
	ToolRepository toolRepository;	
	@Autowired
	FileStorageService fileStorageService;

	public Tool addTool(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String description = StringUtils.cleanPath(Objects.requireNonNull(file.getResource().getDescription()));
        FileDB image= fileStorageService.store(file);
        Tool tool = new Tool(fileName,description, file.getContentType(), file.getBytes(), image);
        return toolRepository.save(tool);
    }

	
	public List<Tool> getAllTools() {
		// TODO Auto-generated method stub
		return null;
	}

}
