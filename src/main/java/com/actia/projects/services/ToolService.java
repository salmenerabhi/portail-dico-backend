package com.actia.projects.services;

import java.util.List;

import com.actia.projects.entities.Tool;

public interface ToolService {

	Tool createTool(Tool tool);
	Tool getTool (String id);
	List<Tool> getAllTools();
}
