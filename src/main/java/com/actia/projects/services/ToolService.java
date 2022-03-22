package com.actia.projects.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.actia.projects.entities.Tool;
import com.actia.projects.exceptions.StorageException;
import com.actia.projects.repository.ToolRepository;

@Service
public class ToolService {
	

@Autowired
ToolRepository toolRepository;


public Tool createTool(Tool tool){
	return toolRepository.save(tool);
}
public Tool getTool (String id){
	return toolRepository.getById(id);
}


}

