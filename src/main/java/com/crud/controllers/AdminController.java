package com.crud.controllers;

import com.crud.exceptions.UserAlreadyExistsException;
import com.crud.exceptions.UserNotFoundException;
import com.crud.exceptions.UserServiceLogicException;
import com.crud.payloads.request.UserRequest;
import com.crud.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {


    @Autowired
    private UserService userService;

    @PostMapping("user")
//    @PreAuthorize("hasRole('ADMIN_USER')")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest) throws UserAlreadyExistsException, UserServiceLogicException {
        return userService.createUser(userRequest);
    }

    @PutMapping("user/{id}")
//    @PreAuthorize("hasRole('ADMIN_USER')")
    public ResponseEntity<?> updateUser(@PathVariable Long id,@Valid @RequestBody UserRequest userRequest) throws UserNotFoundException, UserAlreadyExistsException {
        return userService.updateUser(id,userRequest);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws UserNotFoundException, UserServiceLogicException {
        return userService.deleteUser(id);
    }

}
