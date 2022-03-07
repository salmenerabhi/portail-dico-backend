package com.actia.projects.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.actia.projects.dto.UserDto;
import com.actia.projects.entities.FileDB;
import com.actia.projects.entities.Tool;
import com.actia.projects.services.FileStorageService;
import com.actia.projects.services.ToolServiceImpl;
import com.actia.projects.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/tool")
public class ToolController {

	 @Autowired
	    ToolServiceImpl toolService;
	 @Autowired
	    FileStorageService fileStorageService;
	
	   @PostMapping("/addtool")
	    public Tool addtool(@RequestPart(value = "tool") String Tool,
	                           @RequestPart(value = "image", required = false) MultipartFile file) throws IOException {
	        Tool tool = new ObjectMapper().readValue(Tool, Tool.class);
	        FileDB image = fileStorageService.store(file);
	        tool.setImage(image);
	        return toolService.addTool(file);
	    }
}
