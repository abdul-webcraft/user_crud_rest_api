package com.crud.controllers;

import com.crud.exceptions.UserAlreadyExistsException;
import com.crud.exceptions.UserNotFoundException;
import com.crud.exceptions.UserServiceLogicException;
import com.crud.payloads.request.UserRequest;
import com.crud.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("user/{id}")
//    @PreAuthorize("hasRole('NORMAL_USER') or hasRole('ADMIN_USER')")
    public ResponseEntity<?> getUserById(@PathVariable Long id) throws UserNotFoundException, UserServiceLogicException {
        return userService.getUserById(id);
    }

    @GetMapping("users")
//    @PreAuthorize("hasRole('NORMAL_USER') or hasRole('ADMIN_USER')")
    public ResponseEntity<?> getAllUsers() throws UserNotFoundException, UserServiceLogicException {
        return userService.getAllUser();
    }


}
