package com.actia.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.actia.projects.entities.Target;


public interface TargetRepository  extends JpaRepository<Target, String> {

}
