package com.actia.projects.services;

import java.util.List;

import com.actia.projects.entities.Faq;

public interface FaqService {

	
	    Faq addFaq(Faq faq);
		List<Faq> getAllFaq();
		void deletefaq(String id); 
		Faq updateFaq(Faq faq);
}
