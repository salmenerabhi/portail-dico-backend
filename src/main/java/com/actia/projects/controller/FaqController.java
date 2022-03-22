package com.actia.projects.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.actia.projects.entities.Faq;
import com.actia.projects.entities.UserEntity;
import com.actia.projects.repository.UserRepository;
import com.actia.projects.services.FaqService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/faq")
public class FaqController {
	
	
	@Autowired
	FaqService faqService;
	
	@Autowired
	UserRepository userRepository ;
	
	@PostMapping()
	@ResponseBody
	public Faq ajouterFaq(@RequestBody Faq faq){
		UserEntity user=userRepository.findById(faq.getUser().getId()).get()	;
		faq.setUser(user);
	Faq e3  = faqService.addFaq(faq);
		return e3;
	}
	
	@GetMapping()
	@ResponseBody
	public List<Faq> getListFaqs(){
		return faqService.getAllFaq();
	}
	
	 @DeleteMapping("/{id}")
	    public void deleteFaq(@PathVariable(name = "id") String id) {
	        faqService.deletefaq(id);
	    }


}
