package com.crud.controllers;

import com.crud.exceptions.UserAlreadyExistsException;
import com.crud.exceptions.UserNotFoundException;
import com.crud.exceptions.UserServiceLogicException;
import com.crud.model.User;
import com.crud.payloads.request.LoginRequest;
import com.crud.payloads.request.UserRequest;
import com.crud.payloads.response.JwtAuthResponse;
import com.crud.security.JwtTokenHelper;
import com.crud.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("login")
    public ResponseEntity<JwtAuthResponse<?>> createToken(@RequestBody LoginRequest loginRequest) throws Exception {
        authenticate(loginRequest.getUsername(),loginRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse<User> response=new JwtAuthResponse<>(token,userService.getUserByUsername(userDetails.getUsername()));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void authenticate(String username,String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            System.out.println("Invalid Username and password");
            throw new UserNotFoundException("Invalid Username and Password");
        }
    }

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest userRequest) throws UserAlreadyExistsException, UserServiceLogicException {
        return userService.registerUser(userRequest);
    }

}
