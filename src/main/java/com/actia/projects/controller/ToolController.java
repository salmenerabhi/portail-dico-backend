package com.actia.projects.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;
import com.actia.projects.entities.Tool;
import com.actia.projects.services.ToolService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletContext;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/tool")
public class ToolController {
	@Autowired
	ToolService toolService;
	@Autowired
	ServletContext context;

	@Autowired
	private Environment env; 
	
	
	//Check if the directory of "src/doc" and if the tool exists or not and create a tool and its associated image
	//POST: http://localhost:8085/tool
	@PostMapping
	public Tool createTool(@RequestPart("file") MultipartFile file,
			@RequestPart(value = "image", required = false) MultipartFile image,
			@RequestPart(value = "tool") String toolString) throws JsonMappingException, JsonProcessingException {
		Tool tool = new ObjectMapper().readValue(toolString, Tool.class);
		boolean isExit = new File(context.getRealPath("src/doc/")).exists();
		if (!isExit) {
			new File(context.getRealPath("src/doc/")).mkdir();
		}
		if (file.getName() != null) {
			String filename = file.getOriginalFilename();
			String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
			File serverFile = new File(context.getRealPath("src/doc/" + File.separator + newFileName));
			String distfile = "src/doc/" + file.getOriginalFilename();
			try {
				FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
				Files.copy(file.getInputStream(), Paths.get(distfile), StandardCopyOption.REPLACE_EXISTING);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (image.getName() != null) {
			String imagename = image.getOriginalFilename();
			String newImage = FilenameUtils.getBaseName(imagename) + "." + FilenameUtils.getExtension(imagename);
			File serverFile = new File(context.getRealPath("src/doc/" + File.separator + newImage));
			String distfile = "src/doc/" + image.getOriginalFilename();
			try {
				FileUtils.writeByteArrayToFile(serverFile, image.getBytes());
				Files.copy(image.getInputStream(), Paths.get(distfile), StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		tool.setImage(image.getOriginalFilename());
		tool.setName(file.getOriginalFilename());
		return toolService.createTool(tool);
	}

	//Get a tool content using its ID
	//GET: http://localhost:8085/tool/doc/{id}
	@GetMapping(path = "/doc/{id}")
	public byte[] gettool(@PathVariable String id) throws Exception {
		Tool tool = toolService.getTool(id);
		System.out.println( env.getProperty("doc"));
		return Files.readAllBytes
				(Paths.get("C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/"+ tool.getName()));
	}
	@GetMapping(path = "/image/{id}")
	public byte[] gettoolimage(@PathVariable String id) throws Exception {
		Tool tool = toolService.getTool(id);
		return Files.readAllBytes
				(Paths.get("C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/"+ tool.getImage()));
	}
	//Get a list of all tools
	//GET: http://localhost:8085/tool
	@GetMapping()
	public List<Tool> getListTools() {
		return toolService.getAllTools();
	}

}
