package com.actia.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.actia.projects.entities.Brand;

public interface BrandRepository  extends JpaRepository<Brand, String>{

}
