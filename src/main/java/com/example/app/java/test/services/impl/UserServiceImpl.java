package com.example.app.java.test.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.java.test.io.entities.UserEntity;
import com.example.app.java.test.io.repositories.UserRepository;
import com.example.app.java.test.services.UserService;
import com.example.app.java.test.shared.Utils;
import com.example.app.java.test.shared.dtos.UserDto;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;

	@Override
	public UserDto createUser(UserDto userInput) {
		if(userRepository.findByLogin(userInput.getLogin())  != null)
			throw new RuntimeException("Record already exist");
		
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userInput, userEntity);
		userEntity.setUserId(utils.generateId(30));
		
		UserEntity storedUserEntity = userRepository.save(userEntity);
		
		UserDto storeUserDto = new UserDto();
		BeanUtils.copyProperties(storedUserEntity, storeUserDto);
		return storeUserDto;
	}
	
	@Override
	public UserDto selectUser(String userId) {
		
		UserEntity userEntity = userRepository.findByuserId(userId);
		if(userEntity == null )
			throw new RuntimeException("User Not Found");
		
		UserDto selectedUserDto = new UserDto();
		BeanUtils.copyProperties(userEntity, selectedUserDto);
		
		return selectedUserDto;
	}
	
	@Override
	public UserDto updateUser(String userId, UserDto newData) {
		UserEntity currentEntity = userRepository.findByuserId(userId);
		
		if(currentEntity == null)
			throw new RuntimeException("User Not Found");
			
		
		currentEntity.setGender(newData.getGender());
		currentEntity.setDob(newData.getDob());
		currentEntity.setFullname(newData.getFullname());
		
		UserEntity storedEntity = userRepository.save(currentEntity);
		
		UserDto storeDto = new UserDto();
		BeanUtils.copyProperties(storedEntity, storeDto);
		return storeDto;
	}
}
