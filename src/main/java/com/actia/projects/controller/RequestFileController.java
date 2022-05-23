package com.actia.projects.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.actia.projects.dto.RequestFileStatDto;
import com.actia.projects.dto.StatRequestFiles;
import com.actia.projects.dto.TargetStatDto;
import com.actia.projects.entities.Brand;
import com.actia.projects.entities.RequestFile;
import com.actia.projects.entities.RequestFile.Langue;
import com.actia.projects.entities.RequestFile.State;
import com.actia.projects.entities.Target;
import com.actia.projects.entities.UserEntity;
import com.actia.projects.repository.RequestFileRepository;
import com.actia.projects.services.RequestFileService;
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


	//Check if the directory of "src/doc/files" and if the directory exists or not and create a request file and launch the count method
	//POST: http://localhost:8085/requestfile
	@PostMapping
	public RequestFile createRequestfile1(@RequestPart("file") MultipartFile file,
			@RequestPart(value = "requestfile") String requestfileString)
			throws IOException, Exception {
		RequestFile requestfile = new ObjectMapper().readValue(requestfileString, RequestFile.class);
		boolean isExit = new File(context.getRealPath("src/doc/files/")).exists();
		String filename = file.getOriginalFilename();
		String date = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss_SSS").format(new Date());
		String distfile = "src/doc/files/" + FilenameUtils.getBaseName(filename) +"_"+date  + "." + FilenameUtils.getExtension(filename);;
		String distfile1 = FilenameUtils.getBaseName(filename) +"_"+date  + "." + FilenameUtils.getExtension(filename);;

		if (!isExit) {
			new File(context.getRealPath("src/doc/files/")).mkdir();
		}
		if (file.getName() != null) {
			
			String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
			File serverFile = new File(context.getRealPath("src/doc/files/" + File.separator + newFileName));
			try {
				FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
				Files.copy(file.getInputStream(), Paths.get(distfile), StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		requestfile.setName(distfile1);
	 requestFileService.createRequestFile(requestfile);
		return count(requestfile.getName());
	}

	public RequestFile count(@PathVariable String name) throws IOException, Exception {
		RequestFile requestfile = requestFileService.getRequestFileByName(name);
		File file = new File(
				"C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/files/"
						+ requestfile.getName());
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try (InputStream is = new FileInputStream(file)) {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(is);
			NodeList list = doc.getElementsByTagName("Request");
			requestfile.setNombrephrase(list.getLength());
			requestFileService.createRequestFile(requestfile);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		if(requestfile.getNombrephrase()>100){
			requestfile.setState(State.to_verify);
			requestFileService.updateRequestFile(requestfile);
		}
		return requestfile;
	}
	
	
	//Get and return the request file content by its ID 
	//GET: http://localhost:8085/requestfile/doc/{id}
	@GetMapping(path = "/doc/{id}")
	public byte[] getFile(@PathVariable String id) throws Exception {
		RequestFile requestFile = requestFileService.getRequestFile(id);
		return Files.readAllBytes(Paths
				.get("C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/files/"
						+ requestFile.getName()));
	}

	
	//Set the response type for every file extension and download the request file by ID
	//GET: http://localhost:8085/requestfile/download/{id}
	@GetMapping(path = "/download/{id}")
	public void download(@PathVariable String id, HttpServletResponse response) throws Exception {
		String folderPath = "C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/files/";
		Path path = Paths
				.get("C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/files/"
						+ id);
		if (id.indexOf(".doc") > -1)
			response.setContentType("application/msword");
		if (id.indexOf(".docx") > -1)
			response.setContentType("application/msword");
		if (id.indexOf(".xls") > -1)
			response.setContentType("application/vnd.ms-excel");
		if (id.indexOf(".csv") > -1)
			response.setContentType("application/vnd.ms-excel");
		if (id.indexOf(".ppt") > -1)
			response.setContentType("application/ppt");
		if (id.indexOf(".pdf") > -1)
			response.setContentType("application/pdf");
		if (id.indexOf(".xml") > -1)
			response.setContentType("application/xml");
		if (id.indexOf(".exe") > -1)
			response.setContentType("application/octet-stream");
		if (id.indexOf(".zip") > -1)
			response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=" + id);
		response.setHeader("Content-Transfer-Encoding", "binary");
		try {
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			FileInputStream fis = new FileInputStream(folderPath + id);
			int len;
			byte[] buf = new byte[1024];
			while ((len = fis.read(buf)) > 0) {
				bos.write(buf, 0, len);
			}
			bos.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Get the request file by its ID
	//GET: http://localhost:8085/requestfile/{id}
	@GetMapping(path = "/{id}")
	public RequestFile getFilebyId(@PathVariable String id) {
		return requestFileService.getRequestFile(id);
	}

	//Get a request file's username
	//GET: http://localhost:8085/requestfile/user/{id}
	@GetMapping(path = "/user/{id}")
	public String getuserName(@PathVariable String id) {
		return requestFileService.getuserName(id);

	}

	//Get a list of all an Finished and Rejected request files 
	//GET: http://localhost:8085/requestfile/state/fr
	@GetMapping(path = "state/fr")
	public List<RequestFile> getFileRF() {
		return requestFileService.getAllRequestFileFR();
	}

	//Get a list of all an Unstarted and InProgress request files 
	//GET: http://localhost:8085/requestfile/state/us
	@GetMapping(path = "state/us")
	public List<RequestFile> getFileUS() {
		return requestFileService.getAllRequestFileUS();
	}

	//Get a list of all request files 
	//GET: http://localhost:8085/requestfile
	@GetMapping()
	public List<RequestFile> getListRequestFile() {
		return requestFileService.getAllRequestFile();
	}

	//Update a request file
	//PUT: http://localhost:8085/requestfile
	@PutMapping
	public RequestFile updateRequestFile(@RequestBody RequestFile requestFile) {
		return requestFileService.updateRequestFile(requestFile);
	}

	//Update a request file
	//PUT: http://localhost:8085/requestfile/update
	@PutMapping(path = "update")
	public RequestFile updateUser(@RequestPart(value = "requestfile") String requestfileString,
			@RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
		RequestFile requestfile = new ObjectMapper().readValue(requestfileString, RequestFile.class);
		String filename = file.getOriginalFilename();
		String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
		File serverFile = new File(context.getRealPath("src/doc/files/" + File.separator + newFileName));
		String distfile = "src/doc/files/" + file.getOriginalFilename();
		requestfile.setName(file.getOriginalFilename());
		return requestFileService.updateRequestFile(requestfile);
	}

	//Get a list of all brands
	//GET: http://localhost:8085/requestfile/brand
	@GetMapping("/brand")
	public List<Brand> getBrands() {
		List<Brand> list = requestFileService.retrieveAllBrands();
		return list;
	}

	//Get a list of all targets
	//GET: http://localhost:8085/requestfile/target
	@GetMapping("/target")
	public List<Target> getTargets() {
		List<Target> list = requestFileService.retrieveAllTargets();
		return list;
	}

	// Create a brand
	//POST: http://localhost:8085/requestfile/addbrand
	@PostMapping("/addbrand")
	public Brand addBrand(@RequestBody Brand brand) {
		Brand app = requestFileService.createBrand(brand);
		return app;
	}

	// Create a target
	//POST: http://localhost:8085/requestfile/addtarget
	@PostMapping("/addtarget")
	public Target addAppointment(@RequestBody Target target) {
		Target app = requestFileService.createTarget(target);
		return app;
	}

	//Delete a brand by its ID
	//DELETE: http://localhost:8085/requestfile/brand/{id}
	@DeleteMapping("/brand/{id}")
	public void deleteBrand(@PathVariable(name = "id") String id) {
		requestFileService.deleteBrand(id);
	}

	//Delete a target by its ID
	//DELETE: http://localhost:8085/requestfile/target/{id}
	@DeleteMapping("/target/{id}")
	public void deleteTarget(@PathVariable(name = "id") String id) {
		requestFileService.deleteTarget(id);
	}

	//Get a list of all request files by user 
	//GET: http://localhost:8085/requestfile/filebyuser/{id}
	@GetMapping("/filebyuser/{id}")
	public List<RequestFile> getAllFilesByUser(@PathVariable(name = "id") String id) {
		List<RequestFile> list = requestFileService.getAllRequestsByUser(id);
		return list;
	}

	//Get a list of all request files by Team Leader 
	//GET: http://localhost:8085/requestfile/filebytl/{id}
	@GetMapping("/filebytl/{id}")
	public List<RequestFile> getAllFilesByTl(@PathVariable(name = "id") String id) {
		List<RequestFile> list = requestFileService.getAllUsersByTL(id);
		return list;
	}

	//Get request files state statistics
	//GET: http://localhost:8085/requestfile/stat
	@GetMapping("/stat")
	public StatRequestFiles getStatistic() {
		return requestFileService.getStat();
	}
	
	//Get target statistics
	//GET: http://localhost:8085/requestfile/statTarget
	@GetMapping("/statTarget/{year}")
	public List<TargetStatDto> getTargetPerWeek(@PathVariable(name = "year") int year) {
		List<TargetStatDto> list =requestFileService.getStatTargetPerWeek(year);
		return list;
	}
	
	//Get request files state per user statistics
	//GET: http://localhost:8085/requestfile/statnbr/{id}
	@GetMapping("/statnbr/{id}")
	public int getRfperUser(@PathVariable String id) {
		return requestFileService.getRfParUser(id);
	}
	
	//Get request files rejected state per user statistics
	//GET: http://localhost:8085/requestfile/statnbr/{id}
	@GetMapping("/statnbr1/{id}")
	public int getRejectedperUser(@PathVariable String id) {
		return requestFileService.getRejectedParUser(id);
	}

	
	//Create a script that executes the necessary files to extract the phrases from XML files and
	//launch it to add those phrases to the dictionary
	//POST: http://localhost:8085/requestfile//write
	@PostMapping("/write")
	public void writeScript(@RequestBody RequestFile requestfile) throws Exception {
		BufferedWriter bw3 = null;
		String langue= "" ;
		bw3 = new BufferedWriter(new FileWriter(new File(
				"C:/Users/rabhi/OneDrive/Documents/projects/MajSuperDico/scriptdico.bat")));
		if (requestfile.getLangue()==Langue.EN) {
			 langue = "en_GB";
		}
		else if (requestfile.getLangue()==Langue.FR) {
			langue = "fr_FR";
		}
		else {
			langue = "de_DE";
		}
		File file = new File(
				"C:/Users/rabhi/OneDrive/Documents/projects/MajSuperDico/scriptdico.bat");
		bw3.write("@echo off \n@echo 		__***__ Mise à jour dico __***__\nrem TortoiseProc.exe /commanF:update /path: \"F:/mdiagdico/trunk/Donnees/Dicos/dicmd.xml\" /closeonenF:1 fr_FR en_GB \nC:\\Users\\rabhi\\OneDrive\\Documents\\projects\\MajSuperDico\\perl\\bin\\perl.exe \"C:\\Users\\rabhi\\OneDrive\\Documents\\projects\\MajSuperDico\\maj_superdico.pl\" \"C:\\Users\\rabhi\\OneDrive\\Documents\\projects\\MajSuperDico\\mapping\" \"C:\\Users\\rabhi\\OneDrive\\Documents\\projects\\MajSuperDico\\dicmd.xml\" \"C:\\Users\\rabhi\\OneDrive\\Documents\\projects\\projet pfe\\portail-dico\\back\\portail-dico\\src\\doc\\files\\"+requestfile.getName() + "\" \"" + langue+ "\" \nrem ../perl/bin/perl \"maj_superdico.pl\" \"F:/mdiagdico/trunk/Donnees/Mapping\" \"F:/mdiagdico/trunk/Donnees/Dicos/dicmd.xml\"  \"F:/mdiagdico/trunk/Donnees/_Request_sentences/Not_Implemented/FR/newUserThesauRequest.xml\" \"fr_FR\" \npause");
		bw3.flush();
		bw3.close();
		launch();
		}

		//Launch and execute the script of the dictionnary filling and print the output in the console 
		//GET: http://localhost:8085/requestfile/launch
		@GetMapping("/launch")
		public static void launch() throws IOException, InterruptedException {
			ProcessBuilder builder = new ProcessBuilder(Arrays.asList(new String[] {"cmd.exe", "/C", "start", "C:\\Users\\rabhi\\OneDrive\\Documents\\projects\\MajSuperDico" + File.separator + "scriptdico.bat"}));
			builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
			builder.redirectError(ProcessBuilder.Redirect.INHERIT);
			Process process = builder.start();
			process.waitFor();
			}
				
		//Launch and execute the script of the dictionnary filling and print the output in the console 
				//GET: http://localhost:8085/requestfile/launch
				@GetMapping("/launchDecoup")
				public static void launchScriptDecoupage() throws IOException, InterruptedException {
					ProcessBuilder builder = new ProcessBuilder(Arrays.asList(new String[] {"cmd.exe", "/C", "start", "C:\\Users\\rabhi\\OneDrive\\Documents\\projects\\Generation_Dico" + File.separator + "decoupMake.bat"}));
					builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
					builder.redirectError(ProcessBuilder.Redirect.INHERIT);
					Process process = builder.start();
					process.waitFor();
					}
				
				
				@GetMapping("/users/rejected")
				public List<RequestFileStatDto> getrejectedusers() {
					List<RequestFileStatDto> list = requestFileService.findRejectedFilesByUser();
					return list;
				}
				
				@GetMapping("/users/finished")
				public List<RequestFileStatDto> getfinishedusers() {
					List<RequestFileStatDto> list = requestFileService.findFinishedFilesByUser();
					return list;
				}

	/////////////////////// Schedule /////////////////////////
	
	@RequestMapping("/api")
	String home() {
		return "Welcome!";
	}

	@GetMapping("/api/events")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	List<RequestFile> events(@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
			@RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to) {
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
		requestFileService.createRequestFile(e);
		return e;
	}
	
	
}
