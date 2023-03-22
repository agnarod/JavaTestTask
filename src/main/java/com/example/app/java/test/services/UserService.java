package com.example.app.java.test.services;

import com.example.app.java.test.shared.dtos.UserDto;

public interface UserService {

	UserDto createUser(UserDto userInput);
	UserDto selectUser(String userId);
	UserDto updateUser(String userId, UserDto newData);
}
