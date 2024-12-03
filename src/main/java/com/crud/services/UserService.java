package com.crud.services;

import com.crud.exceptions.UserAlreadyExistsException;
import com.crud.exceptions.UserNotFoundException;
import com.crud.exceptions.UserServiceLogicException;
import com.crud.model.User;
import com.crud.payloads.request.UserRequest;
import com.crud.payloads.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<ApiResponse<?>> createUser(UserRequest userRequest)throws UserAlreadyExistsException, UserServiceLogicException;

    ResponseEntity<ApiResponse<?>> registerUser(UserRequest userRequest)throws UserAlreadyExistsException, UserServiceLogicException;

    ResponseEntity<ApiResponse<?>> updateUser(Long id ,UserRequest userRequest)throws UserNotFoundException,UserAlreadyExistsException;

    ResponseEntity<ApiResponse<?>> getUserById(Long id)throws UserServiceLogicException,UserNotFoundException;

    User getUserByUsername(String username)throws UserServiceLogicException,UserNotFoundException;


    ResponseEntity<ApiResponse<?>> getAllUser()throws UserServiceLogicException ,UserNotFoundException;

    ResponseEntity<ApiResponse<?>> deleteUser(Long id)throws UserServiceLogicException,UserNotFoundException;

}
