package com.actia.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.actia.projects.entities.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

}
