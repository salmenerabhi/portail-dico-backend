package com.actia.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.actia.projects.entities.Tool;
import com.actia.projects.entities.UserEntity;

@Repository
public interface ToolRepository extends  JpaRepository<Tool,String>{

}
