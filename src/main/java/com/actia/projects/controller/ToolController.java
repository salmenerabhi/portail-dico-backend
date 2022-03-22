package com.actia.projects.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.actia.projects.entities.Tool;
import com.actia.projects.services.ToolService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/tool")
public class ToolController {
	@Autowired
	ToolService  toolService;
	  @Autowired
	    ServletContext context;
	@PostMapping
	public Tool createTool(@RequestPart("file") MultipartFile file,@RequestPart("image") MultipartFile image, @RequestPart(value = "tool") String toolString) throws JsonMappingException, JsonProcessingException{
		Tool tool = new ObjectMapper().readValue(toolString, Tool.class);
		boolean isExit = new File(context.getRealPath("src/doc/")).exists();
        if (!isExit)
        {
            new File (context.getRealPath("src/doc/")).mkdir();
            
        }
        if (file.getName() != null) {
            String filename = file.getOriginalFilename();
            String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
            File serverFile = new File (context.getRealPath("src/doc/"+File.separator+newFileName));
            String distfile = "src/doc/"+ file.getOriginalFilename();
            try
            {
               
                FileUtils.writeByteArrayToFile(serverFile,file.getBytes());
                Files.copy(file.getInputStream(),
                        Paths.get(distfile),
                        StandardCopyOption.REPLACE_EXISTING);

            }catch(Exception e) {
                e.printStackTrace();
            }
		
	}
       
        if (image.getName() != null) {
            String imagename = image.getOriginalFilename();
            String newImage = FilenameUtils.getBaseName(imagename)+"."+FilenameUtils.getExtension(imagename);
            File serverFile = new File (context.getRealPath("src/doc/"+File.separator+newImage));
            String distfile = "src/doc/"+ image.getOriginalFilename();
            try
            {
               
                FileUtils.writeByteArrayToFile(serverFile,image.getBytes());
                Files.copy(image.getInputStream(),
                        Paths.get(distfile),
                        StandardCopyOption.REPLACE_EXISTING);

            }catch(Exception e) {
                e.printStackTrace();
            }
		
	}
        tool.setImage(image.getOriginalFilename());
        tool.setName(file.getOriginalFilename());
        return toolService.createTool(tool);
} 
	  @GetMapping(path="/doc/{id}")
	    public byte[] getPhoto(@PathVariable String id) throws Exception{
	        Tool tool = toolService.getTool(id);
	        return Files.readAllBytes(Paths.get("C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/"+tool.getName()));

	    }
	  
	  }
