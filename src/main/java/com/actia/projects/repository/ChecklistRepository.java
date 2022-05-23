package com.actia.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.actia.projects.entities.Checklist;



public interface ChecklistRepository extends JpaRepository<Checklist, String> {

	
	
}
