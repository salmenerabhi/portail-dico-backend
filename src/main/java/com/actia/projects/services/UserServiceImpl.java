package com.actia.projects.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.actia.projects.dto.UserDto;
import com.actia.projects.entities.UserEntity;
import com.actia.projects.repository.FaqRepository;
import com.actia.projects.repository.RequestFileRepository;
import com.actia.projects.repository.UserRepository;
import org.springframework.security.core.userdetails.User;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
    FaqRepository faqRepository;
	@Autowired
    RequestFileRepository requestFileRepository;
	@Autowired
    UserRepository userRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	 
	 //Get a user by Username
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException(email);
        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
	}

	 //Create a user 
	@Override
	public UserDto addUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new RuntimeException("this email is used");
        } else {
            ModelMapper modelMapper = new ModelMapper();
            UserEntity user = modelMapper.map(userDto, UserEntity.class);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setCreationDate(new java.util.Date());
            userRepository.save(user);
        }
        return userDto;
    }

	//Update a user
	@Override
	public UserDto updateUser(UserDto userDto) {
        ModelMapper modelMapper = new ModelMapper();
        UserEntity user = modelMapper.map(userDto, UserEntity.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userDto;
    }

	//Get a user by ID
	@Override
	public UserDto getUserById(String id) {
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto;
        Optional<UserEntity> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new RuntimeException("there is no user with this id");
        } 
        else {
            userDto = modelMapper.map(user.get(), UserDto.class);
        }
        return userDto;
    }

	//Get a list of all users 
	@Override
	public List<UserDto> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        List<UserDto> usersDto = new ArrayList<>();
        for (UserEntity userEntity : users) {
            ModelMapper modelMapper = new ModelMapper();
            UserDto user = modelMapper.map(userEntity, UserDto.class);
            usersDto.add(user);
        }
        return usersDto;
    }
	
	//Delete a user
	@Override
	public 	 void deleteUser(String id) {
		requestFileRepository.deleteAll(userRepository.findRequestByuser(id));
		faqRepository.deleteAll(userRepository.findFaqByuser(id));
		userRepository.deleteById(id);
	}

	//Get a user by email
	@Override
	 public UserDto getUserByEmail(String email) {
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto;
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("there is no user with this email");
        }
        else {
            userDto = modelMapper.map(user, UserDto.class);
        }
        return userDto;
    }

	//Update user's password
	@Override
	public void updatePassword(UserEntity user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
	
	//Get a list of all Team Leaders 
	@Override
	public List<UserEntity> getTL() {
		return  userRepository.findTL();
		
	}
}
