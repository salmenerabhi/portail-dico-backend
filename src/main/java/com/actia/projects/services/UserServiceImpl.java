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
import com.actia.projects.entities.RequestFile;
import com.actia.projects.entities.UserEntity;
import com.actia.projects.repository.UserRepository;
import org.springframework.security.core.userdetails.User;


@Service
public class UserServiceImpl implements UserService {

	
	
	
	@Autowired
    UserRepository userRepository;
	 @Autowired
	    BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(email);

        if (user == null) throw new UsernameNotFoundException(email);

        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
	}

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


	@Override
	public UserDto updateUser(UserDto userDto) {
        ModelMapper modelMapper = new ModelMapper();
        UserEntity user = modelMapper.map(userDto, UserEntity.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userDto;
    }


	@Override
	public UserDto getUserById(String id) {
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto;
        Optional<UserEntity> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new RuntimeException("there is no user with this id");
        } else {

            userDto = modelMapper.map(user.get(), UserDto.class);
        }

        return userDto;
    }

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
	@Override
	public 	 void deleteUser(String id) {
		 userRepository.deleteById(id);
		
	}

	@Override
	 public UserDto getUserByEmail(String email) {
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto;
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("there is no user with this email");
        } else {

            userDto = modelMapper.map(user, UserDto.class);
        }

        return userDto;
    }

	@Override
	public void updatePassword(UserEntity user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
	
	@Override
	public List<UserEntity> getTL() {
		return  userRepository.findTL();
		
	}
}
