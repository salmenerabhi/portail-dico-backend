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
	
	
	
	@Override
	public Faq addFaq(Faq faq) {
		Faq faqs= faqRepository.save(faq);
		return faqs;
		
	}

	@Override
	public List<Faq> getAllFaq() {
		return (List<Faq>) faqRepository.findAll();
	}

	@Override
	public void deletefaq(String id) {
		faqRepository.deleteById(id);		
	}
	
	@Override
	public Faq updateFaq(Faq faq){

		return faqRepository.save(faq);
	}

}
