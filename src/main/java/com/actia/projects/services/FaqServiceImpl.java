package com.actia.projects.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.actia.projects.entities.Faq;
import com.actia.projects.entities.RequestFile;
import com.actia.projects.repository.FaqRepository;


@Service
public class FaqServiceImpl implements FaqService{

	@Autowired
	FaqRepository faqRepository;	
	
	 //Create a FAQ
	@Override
	public Faq addFaq(Faq faq) {
		Faq faqs= faqRepository.save(faq);
		return faqs;	
	}

	//Get a list of all an FAQs 
	@Override
	public List<Faq> getAllFaq() {
		return (List<Faq>) faqRepository.findAll();
	}

	//Delete a FAQ 
	@Override
	public void deletefaq(String id) {
		faqRepository.deleteById(id);		
	}
	
	//Update a FAQ
	@Override
	public Faq updateFaq(Faq faq){
		return faqRepository.save(faq);
	}

}
