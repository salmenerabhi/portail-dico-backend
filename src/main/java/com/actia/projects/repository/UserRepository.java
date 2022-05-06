package com.actia.projects.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.actia.projects.entities.Faq;
import com.actia.projects.entities.RequestFile;
import com.actia.projects.entities.UserEntity;

@Repository
public interface UserRepository extends  JpaRepository<UserEntity,String>{
@Transactional
public UserEntity findByEmail(String email);

@Query("Select u from UserEntity u  where u.role='TL'")
List<UserEntity> findTL ();

@Query("select f  from Faq f join f.user u  where u.id= :id ")
public List<Faq> findFaqByuser (@Param("id") String id);


@Query("select f  from RequestFile f join f.user u  where u.id= :id ")
public List<RequestFile> findRequestByuser (@Param("id") String id);
}
