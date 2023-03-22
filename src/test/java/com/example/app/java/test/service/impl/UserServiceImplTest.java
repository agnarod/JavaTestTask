package com.example.app.java.test.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.app.java.test.io.entities.UserEntity;
import com.example.app.java.test.io.repositories.UserRepository;
import com.example.app.java.test.services.impl.UserServiceImpl;
import com.example.app.java.test.shared.Utils;
import com.example.app.java.test.shared.dtos.UserDto;

public class UserServiceImplTest {
	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserRepository userRepository;

	@Mock
	Utils utils;

	UserEntity userEntity;

	String generatedIdString;

	@BeforeEach
	void setup() throws Exception {

		MockitoAnnotations.openMocks(this);

		userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setUserId(generatedIdString);
		userEntity.setLogin("myUser");
		userEntity.setFullname("Jhon Smith");
		userEntity.setDob(LocalDate.parse("1992-08-04"));
		userEntity.setGender("male");

	}

	@Test
	void testSelectUser() {
		when(userRepository.findByuserId(anyString())).thenReturn(userEntity);
		
		UserDto userDto = userService.selectUser("fdasfsadfs");
		
		assertNotNull(userDto);
		assertEquals("Jhon Smith", userDto.getFullname());
	}

	@Test
	void testSelectUser_UserNotFound() {
		when(userRepository.findByuserId(anyString())).thenReturn(null);
		
		assertThrows(RuntimeException.class, ()->{
			userService.selectUser("afdskfjsi");
		});
	}

	@Test
	void testCreateUser() {
		when(userRepository.findByLogin(anyString())).thenReturn(null);
		when(utils.generateId(anyInt())).thenReturn(generatedIdString);
		
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

		UserDto userDto = new UserDto();

		userDto.setLogin("myUser");
		userDto.setFullname("Jhon Smith");
		userDto.setDob(LocalDate.parse("1992-08-04"));
		userDto.setGender("male");
		
		UserDto storeDto = userService.createUser(userDto);
		
		assertNotNull(storeDto);
		assertEquals(userEntity.getUserId(), userDto.getUserId());
		assertEquals(userEntity.getFullname(), userDto.getFullname());
		assertEquals(userEntity.getLogin(), userDto.getLogin());
		assertEquals(userEntity.getDob(), userDto.getDob());	
	}

	@Test
	void testCreateUser_userAlreadyExist() {
		when(userRepository.findByLogin(anyString())).thenReturn(null);
		when(utils.generateId(anyInt())).thenReturn(generatedIdString);
		
		when(userRepository.findByuserId(anyString())).thenReturn(userEntity);

		UserDto userDto = new UserDto();

		userDto.setLogin("myUser");
		userDto.setFullname("Jhon Smith");
		userDto.setDob(LocalDate.parse("1992-08-04"));
		userDto.setGender("male");
		

		assertThrows(RuntimeException.class, ()->{
			UserDto storeDto = userService.createUser(userDto);
		});
	}

	@Test
	void testUpdateUser() {
		
		when(userRepository.findByuserId(anyString())).thenReturn(userEntity);
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

		UserDto userDto = new UserDto();

		userDto.setLogin("myUser");
		userDto.setFullname("Jhon Smith");
		userDto.setDob(LocalDate.parse("1992-08-04"));
		userDto.setGender("male");
		
		UserDto storeDto = userService.updateUser("fdhjhaljhfdksdhflajf", userDto);
		
		assertNotNull(storeDto);
		assertEquals(userEntity.getUserId(), userDto.getUserId());
		assertEquals(userEntity.getFullname(), userDto.getFullname());
		assertEquals(userEntity.getLogin(), userDto.getLogin());
		assertEquals(userEntity.getDob(), userDto.getDob());
	}

	@Test
	void testUpdateUser_UserNotFound() {
		when(userRepository.findByuserId(anyString())).thenReturn(null);

		UserDto userDto = new UserDto();

		userDto.setLogin("myUser");
		userDto.setFullname("Jhon Smith");
		userDto.setDob(LocalDate.parse("1992-08-04"));
		userDto.setGender("male");
		
		assertThrows(RuntimeException.class, ()->{
			UserDto storeDto = userService.updateUser("fdhjhaljhfdksdhflajf", userDto);
		});
		
	}
}
