package com.actia.projects.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.actia.projects.dto.ResetPassword;
import com.actia.projects.dto.UserDto;
import com.actia.projects.entities.FileDB;
import com.actia.projects.repository.FileDBRepository;
import com.actia.projects.services.FileStorageService;
import com.actia.projects.services.JwtUtil;
import com.actia.projects.services.PasswordServices;
import com.actia.projects.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    FileDBRepository fileDBRepository;
    @Autowired
    PasswordServices passwordServices;
    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    JwtUtil jwtUtil;
    
    
    @PostMapping("/resetPassword")
    public void resetPassword(@RequestBody ResetPassword resetPassword) {
        String token = resetPassword.getToken();
        if (Boolean.TRUE.equals(jwtUtil.isTokenExpired(token))) {
            String email = jwtUtil.extractUsername(token);
            passwordServices.resetPassword(resetPassword, email);
        } else throw new RuntimeException("Token expired");

    }

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendHtmlEmail(@RequestBody String email) throws MessagingException {
        passwordServices.sendEmail(email);
        return ResponseEntity.ok("email send");
    }

    @PostMapping("/register")
    public UserDto addUser(@RequestPart(value = "userDto") String userDto,
                           @RequestPart(value = "image", required = false) MultipartFile file) throws IOException {
        UserDto user = new ObjectMapper().readValue(userDto, UserDto.class);
        FileDB image = fileStorageService.store(file);
        user.setImage(image);
        return userService.addUser(user);
    }

    @PutMapping
    public UserDto updateUser(@RequestPart(value = "userDto") String userDto,
                              @RequestPart(value = "image", required = false) MultipartFile file) throws IOException {
        UserDto user = new ObjectMapper().readValue(userDto, UserDto.class);
        FileDB image = fileStorageService.store(file);
        user.setImage(image);
        return userService.updateUser(user);
    }

    @PostMapping("/add")
    public UserDto addUserWithoutImage(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @PutMapping("/update")
    public UserDto updateUserWithoutImage(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable(name = "id") String id) {
        return userService.getUserById(id);
    }
 
    
  
 



    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(name = "id") String id) {
        userService.deleteUser(id);
    }


}
