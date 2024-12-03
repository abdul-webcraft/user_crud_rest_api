package com.crud.service.impl;

import com.crud.config.AppConstants;
import com.crud.exceptions.UserAlreadyExistsException;
import com.crud.exceptions.UserNotFoundException;
import com.crud.exceptions.UserServiceLogicException;
import com.crud.model.Role;
import com.crud.model.User;
import com.crud.payloads.request.UserRequest;
import com.crud.payloads.response.ApiResponse;
import com.crud.repository.RoleRepository;
import com.crud.repository.UserRepository;
import com.crud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ResponseEntity<ApiResponse<?>> createUser(UserRequest userRequest) throws UserAlreadyExistsException, UserServiceLogicException {
        try {

            if (userRepository.existsByEmail(userRequest.getEmail())) {
                throw new UserAlreadyExistsException("Email already Exists  !!");
            }
//            if(userRepository.existsByUsername(userRequest.getUsername())){
//                throw new UserAlreadyExistsException("Username already exists !!");
//            }

            User user=new User();
            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            user.setMobileNo(userRequest.getMobileNo());
            user.setAddress(userRequest.getAddress());

            Role role = roleRepository.findById(AppConstants.NORMAL_USER).get();
            user.getRoles().add(role);

            // Save user and map to response DTO
            User savedUser = userRepository.save(user);
            ApiResponse<User> response=new ApiResponse<>("SUCCESS",savedUser);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (UserAlreadyExistsException ex) {
            throw new UserAlreadyExistsException(ex.getMessage());
        }catch (Exception ex) {
            throw new UserServiceLogicException();
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> registerUser(UserRequest userRequest) throws UserAlreadyExistsException, UserServiceLogicException {
        try {

            if (userRepository.existsByEmail(userRequest.getEmail())) {
                throw new UserAlreadyExistsException("Email already Exists  !!");
            }
//            if(userRepository.existsByUsername(userRequest.getUsername())){
//                throw new UserAlreadyExistsException("Username already exists !!");
//            }

            User user=new User();
            user.setName(userRequest.getName());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            user.setEmail(userRequest.getEmail());
            user.setMobileNo(userRequest.getMobileNo());
            user.setAddress(userRequest.getAddress());

            Role role = roleRepository.findById(AppConstants.ADMIN_USER).get();
            user.getRoles().add(role);

            // Save user and map to response DTO
            User savedUser = userRepository.save(user);
            ApiResponse<User> response=new ApiResponse<>("SUCCESS",savedUser);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (UserAlreadyExistsException ex) {
            throw new UserAlreadyExistsException(ex.getMessage());
        }catch (Exception ex) {
            throw new UserServiceLogicException();
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> updateUser(Long id, UserRequest userRequest) throws UserNotFoundException, UserAlreadyExistsException {
        try {
            User getUser=userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not existed with given id :" + id));
            User user=new User();
            user.setId(getUser.getId());
            user.setName(getUser.getName());
            user.setEmail(getUser.getEmail());
            user.setPassword(getUser.getPassword());
            user.setMobileNo(userRequest.getMobileNo());
            user.setAddress(userRequest.getAddress());
            User updatedUser = userRepository.save(user);
            var response = new ApiResponse<>("SUCCESS",updatedUser);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (UserNotFoundException ex){
            throw new UserNotFoundException(ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> getUserById(Long id) throws UserServiceLogicException, UserNotFoundException {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not existed with given id :" + id));

            ApiResponse<?> response = new ApiResponse<>("SUCCESS", user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (UserNotFoundException ex){
            throw new UserNotFoundException(ex.getMessage());
        }catch(Exception e) {
            throw new UserServiceLogicException();
        }
    }

    @Override
    public User getUserByUsername(String username) throws UserServiceLogicException, UserNotFoundException {
        try {
            return userRepository.findByEmail(username).orElseThrow(() -> new UserNotFoundException("Email are not exists !!"));
        }catch (UserNotFoundException ex){
            throw new UserNotFoundException(ex.getMessage());
        }catch(Exception e) {
            throw new UserServiceLogicException();
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> getAllUser() throws UserServiceLogicException,UserNotFoundException {
        try {
            List<User> users = userRepository.findAll();
            if(users.isEmpty()){
                throw new UserNotFoundException("User are not Exists..");
            }
            ApiResponse<List<?>> response = new ApiResponse<>("SUCCESS",users);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new UserServiceLogicException();
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> deleteUser(Long id) throws UserServiceLogicException, UserNotFoundException {
        try {
            var user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
            userRepository.delete(user);
            ApiResponse<?> response = new ApiResponse<>("SUCCESS",null);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new UserServiceLogicException();
        }
    }
}
