package com.actia.projects.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import javax.servlet.ServletContext;
import java.lang.reflect.Type;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.actia.projects.entities.RequestFile;
import com.actia.projects.entities.Tool;
import com.actia.projects.services.RequestFileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/requestfile")
public class RequestFileController {
	
	@Autowired
	RequestFileService requestFileService;
	@Autowired
    ServletContext context;
	
	
	@PostMapping
	public RequestFile createRequestfile(@RequestPart("file") MultipartFile file, @RequestPart(value = "requestfile") String requestfileString) throws JsonMappingException, JsonProcessingException{
		RequestFile requestfile = new ObjectMapper().readValue(requestfileString, RequestFile.class);
		boolean isExit = new File(context.getRealPath("src/doc/files/")).exists();
        if (!isExit)
        {
            new File (context.getRealPath("src/doc/files/")).mkdir();
            
        }
        if (file.getName() != null) {
            String filename = file.getOriginalFilename();
            String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
            File serverFile = new File (context.getRealPath("src/doc/files/"+File.separator+newFileName));
            String distfile = "src/doc/files/"+ file.getOriginalFilename();
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
       
        requestfile.setName(file.getOriginalFilename());
     
        return requestFileService.createRequestFile(requestfile);
} 
	  @GetMapping(path="/doc/{id}")
	    public byte[] getFile(@PathVariable String id) throws Exception{
	        RequestFile requestFile = requestFileService.getRequestFile(id);
	        return Files.readAllBytes(Paths.get("C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/files/"+requestFile.getName()));

	    }
	  
//	  @GetMapping
//	    public ResponseEntity<List<RequestFile>> getRequestFiles(Principal principal) {
//
//	        List<RequestFile> requestFile = requestFileService.getAllFiles(principal.getName());
//
//	        Type listType = new TypeToken<List<RequestFile>>() {}.getType();
//	        List<RequestFile> file = new ModelMapper().map(requestFile, listType);
//
//	        return new ResponseEntity<List<RequestFile>>(requestFile, HttpStatus.OK);
//
//	    }
	  
	  @GetMapping()
		@ResponseBody
		public List<RequestFile> getListRequestFile(){
			return requestFileService.getAllRequestFile();
		}
	  
	  }
