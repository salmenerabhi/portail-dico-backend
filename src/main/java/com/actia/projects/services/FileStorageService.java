package com.actia.projects.services;

import java.io.IOException;
import java.util.Objects;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.actia.projects.entities.FileDB;
import com.actia.projects.repository.FileDBRepository;

import org.springframework.util.StringUtils;


@Service
public class FileStorageService {
  @Autowired
 FileDBRepository fileDBRepository;

    public FileDB store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        FileDB fileDb = new FileDB(fileName, file.getContentType(), file.getBytes());
        return fileDBRepository.save(fileDb);
    }


}