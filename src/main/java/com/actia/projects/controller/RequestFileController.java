package com.actia.projects.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import java.lang.reflect.Type;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.actia.projects.dto.RequestFileDto;
import com.actia.projects.dto.UserDto;
import com.actia.projects.entities.Checklist;
import com.actia.projects.entities.FileDB;
import com.actia.projects.entities.RequestFile;
import com.actia.projects.entities.RequestFile.State;
import com.actia.projects.entities.Tool;
import com.actia.projects.repository.RequestFileRepository;
import com.actia.projects.services.RequestFileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/requestfile")
public class RequestFileController {
	
	@Autowired
	RequestFileService requestFileService;
	@Autowired
	RequestFileRepository requestFileRepository;
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
	  
	  
	  @GetMapping(path="/download/{id}")
	    public void download(@PathVariable String id, HttpServletResponse response) throws Exception {
	       String folderPath = "C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/files/";
	        Path path = Paths.get("C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/files/" + id);
	        if (id.indexOf(".doc") > -1) response.setContentType("application/msword");
	        if (id.indexOf(".docx") > -1) response.setContentType("application/msword");
	        if (id.indexOf(".xls") > -1) response.setContentType("application/vnd.ms-excel");
	        if (id.indexOf(".csv") > -1) response.setContentType("application/vnd.ms-excel");
	        if (id.indexOf(".ppt") > -1) response.setContentType("application/ppt");
	        if (id.indexOf(".pdf") > -1) response.setContentType("application/pdf");
	        if (id.indexOf(".exe") > -1) response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition", "attachment; filename=" +id);
	        response.setHeader("Content-Transfer-Encoding", "binary");
	        try {
	            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
	            FileInputStream fis = new FileInputStream(folderPath+id);
	            int len;
	            byte[] buf = new byte[1024];
	            while((len = fis.read(buf)) > 0) {
	                bos.write(buf,0,len);
	            }
	            bos.close();
	            response.flushBuffer();
	        }
	        catch(IOException e) {
	            e.printStackTrace();

	        }
	    }
	  
	  @GetMapping(path="/launch")
	  public static void launch(String command) {
	        try {
	            Runtime runtime = Runtime.getRuntime();
	            Process process = runtime.exec("C://Users//rabhi//OneDrive//Documents//projects//projet pfe//portail-dico//front//potail-dico//src//assets//scripts//tosalmen//MajSuperDico//launch_maj_superdico.bat");
	            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
	            String line = null;
	            while ((line = input.readLine()) != null) {
	                System.out.println(line);
	            }
	            //Attendre la fin de l'execution
	            if (process.waitFor() != 0) {
	                System.out.println("Une erreur est survenue ");
	            }
	        } catch (InterruptedException ex) {
                System.out.println("Une erreur est survenue1 ");
	        } catch (IOException ex) {
                System.out.println("Une erreur est survenue2 ");
	        }
	    }

	  
	  
	  @GetMapping(path="/{id}")
	    public RequestFile getFilebyId(@PathVariable String id){
		  return requestFileService.getRequestFile(id);

	    }
	  
	  @GetMapping(path="/user/{id}")
	    public String getuserName(@PathVariable String id){
		  return requestFileService.getuserName(id);

	    }
	  
	  
	  @GetMapping(path="state/fr")
	    public List<RequestFile> getFileRF(){
		  return requestFileService.getAllRequestFileFR();

	    }
	  @GetMapping(path="state/us")
	    public List<RequestFile> getFileUS(){
		  return requestFileService.getAllRequestFileUS();

	    }
	  @GetMapping()
		@ResponseBody
		public List<RequestFile> getListRequestFile(){
			return requestFileService.getAllRequestFile();
		}
	  
	  @PutMapping
	    public RequestFile updateRequestFile(@RequestBody RequestFile requestFile) {
	        return requestFileService.updateRequestFile(requestFile);
	    }
	  
	  @PutMapping(path="update")
	    public RequestFile updateUser(@RequestPart(value = "requestfile") String requestfileString,
	                              @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
			RequestFile requestfile = new ObjectMapper().readValue(requestfileString, RequestFile.class);
			String filename = file.getOriginalFilename();
            String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
            File serverFile = new File (context.getRealPath("src/doc/files/"+File.separator+newFileName));
            String distfile = "src/doc/files/"+ file.getOriginalFilename();
            requestfile.setName(file.getOriginalFilename());
	        return requestFileService.updateRequestFile(requestfile);
	    }

	  @RequestMapping("/api")
	    @ResponseBody
	    String home() {
	        return "Welcome!";
	    }
	  
	  @GetMapping("/api/events")
	    @JsonSerialize(using = LocalDateTimeSerializer.class)
	    List<RequestFile> events(@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date  from, @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date  to) {
	        return requestFileRepository.findBetween(from, to);
	    }

	  @PostMapping("/api/events/create")
	    @JsonSerialize(using = LocalDateTimeSerializer.class)
	    @Transactional
	    RequestFile createEvent(@RequestBody RequestFile params) {

	        RequestFile r = requestFileRepository.findById(params.getName()).get();

	        RequestFile e = new RequestFile();
	        e.setEcheanceRC(params.echeanceRC);
	        e.setEcheanceRD(params.echeanceRD);
	        e.setName(params.getName());

	        requestFileRepository.save(e);

	        return e;
	    }

	  
	  }
