package com.actia.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.actia.projects.entities.RequestFile;

@Repository
public interface RequestFileRepository extends JpaRepository<RequestFile, String> {

}
