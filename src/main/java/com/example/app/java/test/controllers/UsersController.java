package com.example.app.java.test.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.java.test.services.UserService;
import com.example.app.java.test.shared.dtos.UserDto;
import com.example.app.java.test.ui.models.requests.UsersRequest;
import com.example.app.java.test.ui.models.responses.UserDetailsResponse;

@RequestMapping("/users")
@RestController
public class UsersController {
	@Autowired UserService userService;

	@PostMapping
	public UserDetailsResponse createUser(@RequestBody UsersRequest user) {
		//creating user to manage between operations
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);
		//calling function to store user in DB
		UserDto storedUserDto =  userService.createUser(userDto);
		
		//sending stored user in response
		UserDetailsResponse returnedValue= new UserDetailsResponse();
		BeanUtils.copyProperties(storedUserDto, returnedValue);
		return returnedValue;
	}
	
	@GetMapping(path="/{userId}")
	public UserDetailsResponse selectUser(@PathVariable String userId) {
		
		UserDto selectedUserDto = userService.selectUser(userId);
		
		UserDetailsResponse returnedValue = new UserDetailsResponse();
		BeanUtils.copyProperties(selectedUserDto, returnedValue);
		
		return returnedValue;
	}
	
	@PutMapping(path = "/{userId}")
	public UserDetailsResponse updateUser(@PathVariable String userId,@RequestBody UsersRequest user) {
		UserDto newData = new UserDto();
		BeanUtils.copyProperties(user, newData);
		
		UserDto updatedUserDto = userService.updateUser(userId, newData);
		
		UserDetailsResponse storedDetailsResponse = new UserDetailsResponse();
		BeanUtils.copyProperties(updatedUserDto, storedDetailsResponse);
		
		return storedDetailsResponse;
	}
}
