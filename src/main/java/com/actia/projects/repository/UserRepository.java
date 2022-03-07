package com.actia.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.actia.projects.entities.UserEntity;

@Repository
public interface UserRepository extends  JpaRepository<UserEntity,String>{
@Transactional
public UserEntity findByEmail(String email);
}
