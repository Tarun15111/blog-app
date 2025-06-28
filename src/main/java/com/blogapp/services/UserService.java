package com.blogapp.services;

import com.blogapp.entities.User;
import com.blogapp.payloads.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

//    Creating user
    UserDTO createUser(UserDTO userDTO);

//    Updating user
    UserDTO updateUser(UserDTO userDTO, Long id);

//    Fetching the user
    UserDTO getUserById(Long id);

//    Listing all users
    List<UserDTO> getAllUsers();

//    Delete user
    void deleteUser(Long id);
}
