package com.actia.projects.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.actia.projects.entities.Log;
import com.actia.projects.entities.Log.LogType;
import com.actia.projects.services.LogService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/logs")
public class LogController {

	@Autowired
	LogService logService;
	@Autowired
	ServletContext context;

	//Check if the directory of "src/doc/logs" exists or not and if the log exists and if so delete it so the output
	//of the log comparison will always be up to date and create a log and call the compare method to create the log diff
	//POST: http://localhost:8085/logs
	@PostMapping
	public Log createLog(@RequestPart("file") MultipartFile file, @RequestPart(value = "log") String logString)
			throws Exception {
		Log log = new ObjectMapper().readValue(logString, Log.class);
		boolean isExist = new File(context.getRealPath("src/doc/logs/")).exists();
		if (!isExist) {
			new File(context.getRealPath("src/doc/logs/")).mkdir();
		}
		boolean fileExist = new File(
				"C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/logs/"
						+ file.getOriginalFilename()).exists();
		if (file.getName() != null) {
			if (fileExist) {
				File fileToDelete = new File(
						"C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/logs/"
								+ file.getOriginalFilename());
				fileToDelete.delete();
			}
			String filename = file.getOriginalFilename();
			String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
			File serverFile = new File(context.getRealPath("src/doc/logs/" + File.separator + newFileName));
			String distfile = "src/doc/logs/" + file.getOriginalFilename();
			try {
				FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
				Files.copy(file.getInputStream(), Paths.get(distfile), StandardCopyOption.REPLACE_EXISTING);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
//		if (log.getType().equals(LogType.Error) || log.getType().equals(LogType.TBBT_FNC) || log.getType().equals(LogType.TBBT_GPC)|| log.getType().equals(LogType.TBBT_MENU)) {

		if (log.getType().equals(LogType.Error) || log.getType().equals(LogType.TBBT)) {
			File fileerror = new File(
					"C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/logs/"
							+ log.getFilename());
			compare(log.getType(), fileerror, logService.getLogByType());
		}
		log.setFilename(file.getOriginalFilename());
		return logService.createLog(log);
	}

	//Get the content of the output log error :LogError.txt (the output of the comparison between the reference log and the error log)
	//GET: http://localhost:8085/logs/doc/error
	@GetMapping(path = "/doc/error")
	public List<Log> getLogError() throws Exception {
		byte[] bytes = Files.readAllBytes(Paths.get(
				"C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/logs/logError.txt"));
		String s = new String(bytes, StandardCharsets.UTF_8);
		return getContentes(s);
	}

	//Get the content of the output log TTBT :LogTTBT.txt (the output of the comparison between the reference log and the TTBT log)
	//GET: http://localhost:8085/logs/doc/error
	@GetMapping(path = "/doc/ttbt")
	public List<Log> getLogTTBT() throws Exception {
		byte[] bytes = Files.readAllBytes(Paths.get(
				"C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/logs/logTTBT.txt"));
		String s = new String(bytes, StandardCharsets.UTF_8);
		return getContentesttbt(s);
	}
	
	//Get and return the log file content by its ID 
	public File getFile(String id) throws Exception {
		Log log = logService.getLog(id);
		return new File(
				"C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/logs/"
						+ log.getFilename());
	}


	//Split the output log lines according to different criteria and get a list of the log content (Number, Description, FileName) 
	public List<Log> getContentes(String s) {
		String Tableau[] = s.split("error type ");
		List<Log> logs = new ArrayList<>();
		for (String st : Tableau) {
			Log log = new Log();
			log.setNumero(st.substring(0, 2));

			int i = st.indexOf("<");
			int j = st.lastIndexOf("File");
			int k = st.indexOf("D:\\ServeurIC\\IC\\WORKAREA\\MDIAGDICOS\\DICMD\\");
			int l = st.indexOf("#define");
			int m = st.indexOf("§") + 1;

			String string = "D:\\ServeurIC\\IC\\WORKAREA\\MDIAGDICOS\\DICMD\\";
			if (log.getNumero().equals("2§")) {
				if (i > 0 && j > 0) {
					log.setDescription(st.substring(i, j));
					log.setFilename(st.substring(k + string.length()));
				}
				if (l > 0 && j > 0) {
					log.setDescription(st.substring(l, j));
					log.setFilename(st.substring(k + string.length()));
				}
			} else if (log.getNumero().equals("6§")) {
				log.setDescription(st.substring(2));
			}
			if (log.getNumero().equals("5§")) {
				if (i > 0 && j > 0) {
					log.setDescription(st.substring(i, j));
					log.setFilename(st.substring(k + string.length()));
				}
				if (m > 0 && j > 0) {
					log.setDescription(st.substring(m, j));
					log.setFilename(st.substring(k + string.length()));
				}
			}
			if (log.getNumero().equals("8§")) {
				if (i > 0 && j > 0) {
					log.setDescription(st.substring(i, j));
					log.setFilename(st.substring(k + string.length()));
				}
				if (m > 0 && j > 0) {
					log.setDescription(st.substring(m, j));
					log.setFilename(st.substring(k + string.length()));
				}
			} else if (log.getNumero().equals("9§")) {
				if (j > 0)
					log.setDescription(st.substring(2, j));
				if (k > 0)
					log.setFilename(st.substring(k + string.length()));
			} else if (log.getNumero().equals("11")) {
				if (i < 0) {
					if (j > 0)
						log.setDescription(st.substring(2, j));
					if (k > 0)
						log.setFilename(st.substring(k + string.length()));
				} else {
					if (i > 0 && j > 0) {
						log.setDescription(st.substring(i, j));
					}
					if (i > 0 && j > 0) {
						log.setFilename(st.substring(k + string.length()));
					}
				}
			} else {
				if (i > 0 && j > 0) {
					log.setDescription(st.substring(i, j));
				}
				if (i > 0 && j > 0) {
					log.setFilename(st.substring(k + string.length()));
				}
			}
			logs.add(log);
		}
		return logs;
	}

	public List<Log> getContentesttbt(String s) {
		String Tableau[] = s.split("\n");
		List<Log> logs = new ArrayList<>();
		for (String st : Tableau) {
			Log log = new Log();
			
			log.setFilename(st.substring(0,st.length()-1));
			logs.add(log);
		}
		return logs;
	}

	//Get the log by its ID
	//GET: http://localhost:8085/logs/{id}
	@GetMapping(path = "/{id}")
	public Log getFilebyId(@PathVariable String id) {
		return logService.getLog(id);
	}

	//Get a list of all logs
	//GET: http://localhost:8085/logs
	@GetMapping()
	public List<Log> getListLogs() {
		return logService.getAllLogs();
	}

	//Delete a log by its ID
	//DELETE: http://localhost:8085/logs/{id}
	@DeleteMapping("/{id}")
	public void deleteLog(@PathVariable(name = "id") String id) {
		logService.deleteLog(id);
	}

	//Read the reference log and the log(error or TTBT) line by line and compare both of them and extract the difference (the lines 
	//existent in the log (error or TTBT) and inexistant in the reference log
	public void compare(LogType logType, File id, String idRef) throws Exception {
		BufferedReader br1 = null;
		BufferedReader br2 = null;
		BufferedWriter bw3 = null;
		String sCurrentLine;
		int linelength;
		HashMap<String, Integer> expectedrecords = new HashMap<String, Integer>();
		HashMap<String, Integer> actualrecords = new HashMap<String, Integer>();

		br2 = new BufferedReader(new FileReader(id));
		br1 = new BufferedReader(new FileReader(getFile(idRef)));

		while ((sCurrentLine = br1.readLine()) != null) {
			if (expectedrecords.containsKey(sCurrentLine)) {
				expectedrecords.put(sCurrentLine, expectedrecords.get(sCurrentLine) + 1);
			} else {
				expectedrecords.put(sCurrentLine, 1);
			}
		}
		while ((sCurrentLine = br2.readLine()) != null) {
			if (expectedrecords.containsKey(sCurrentLine)) {
				int expectedCount = expectedrecords.get(sCurrentLine) - 1;
				if (expectedCount == 0) {
					expectedrecords.remove(sCurrentLine);
				} else {
					expectedrecords.put(sCurrentLine, expectedCount);
				}
			} else {
				if (actualrecords.containsKey(sCurrentLine)) {
					actualrecords.put(sCurrentLine, actualrecords.get(sCurrentLine) + 1);
				} else {
					actualrecords.put(sCurrentLine, 1);
				}
			}
		}

		// expected is left with all records not present in the reference log
		// actual is left with all records not present in the log(error or TTBT)
		//Create the output log according to the type of the input log and delete if existant to make it up to date
		if (logType.equals(LogType.Error)) {
			bw3 = new BufferedWriter(new FileWriter(new File(
					"C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/logs/logError.txt")));
			File file = new File(
					"C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/logs/logError.txt");
			file.delete();
		}
		
//		else if (logType.equals(LogType.TTBT_FNC)) {
//			bw3 = new BufferedWriter(new FileWriter(new File(
//					"C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/logs/logTTBT_FNC.txt")));
//			File file = new File(
//					"C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/logs/logTTBT_FNC.txt");
//			file.delete();
//		}
//		
//		else if (logType.equals(LogType.TTBT_GPC)) {
//			bw3 = new BufferedWriter(new FileWriter(new File(
//					"C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/logs/logTTBT_GPC.txt")));
//			File file = new File(
//					"C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/logs/logTTBT_GPC.txt");
//			file.delete();
//		}
//		
//		else {
//			bw3 = new BufferedWriter(new FileWriter(new File(
//					"C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/logs/logTTBT_MENU.txt")));
//			File file = new File(
//					"C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/logs/logTTBT_MENU.txt");
//			file.delete();
//		}
		
		else {
			bw3 = new BufferedWriter(new FileWriter(new File(
					"C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/logs/logTTBT.txt")));
			File file = new File(
					"C:/Users/rabhi/OneDrive/Documents/projects/projet pfe/portail-dico/back/portail-dico/src/doc/logs/logTTBT.txt");
			file.delete();
		}

		
		bw3.write("Records which are not present in log\n");
		for (String key : actualrecords.keySet()) {
			for (int i = 0; i < actualrecords.get(key); i++) {
				bw3.write(key);
				bw3.newLine();
			}
		}
		bw3.flush();
		bw3.close();
	}
}
