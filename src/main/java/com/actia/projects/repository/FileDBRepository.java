package com.actia.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.actia.projects.entities.FileDB;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

}