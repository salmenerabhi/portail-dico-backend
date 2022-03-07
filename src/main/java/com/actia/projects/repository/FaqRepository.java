package com.actia.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.actia.projects.entities.Faq;


@Repository
public interface FaqRepository extends JpaRepository<Faq, String> {

}
