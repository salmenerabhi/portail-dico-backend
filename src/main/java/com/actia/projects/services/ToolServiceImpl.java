package com.actia.projects.services;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.actia.projects.entities.Tool;
import com.actia.projects.repository.ToolRepository;

@Service
public class ToolServiceImpl implements ToolService{
	
	@Autowired
	ToolRepository toolRepository;

	 //Create a tool 
	@Override
	public Tool createTool(Tool tool){
		return toolRepository.save(tool);
	}
	
	 //Get a tool 
	@Override
	public Tool getTool (String id){
		return toolRepository.getById(id);
	}
	
	//Get a list of all tools 
	@Override
	public List<Tool> getAllTools() {
		return (List<Tool>) toolRepository.findAll();
	}


}

