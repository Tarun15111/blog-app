package com.blogapp.controllers;

import com.blogapp.payloads.APIResponse;
import com.blogapp.payloads.UserDTO;
import com.blogapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

//    POST create user
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        UserDTO userDTO1 =  userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO1);
    }

//    PUT update user
    @PutMapping("/update/{id}")
    public  ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id){
            UserDTO userDTO1 = userService.updateUser(userDTO, id);
            return  ResponseEntity.ok(userDTO1);
    }

//    DELETE: delete user
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<APIResponse>(new APIResponse("User deleted Successfully", true), HttpStatus.OK);
    }
//    GET fetch user data
    @GetMapping("/getUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/getId/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

}
