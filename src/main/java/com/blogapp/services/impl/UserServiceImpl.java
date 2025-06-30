package com.blogapp.services.impl;

import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.UserDTO;
import com.blogapp.repositories.UserRepository;
import com.blogapp.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user =  this.mapToUser(userDTO);
        User savedUser  = userRepository.save(user);
        return this.mapToUserDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Long id) {
        User existingUser = userRepository.findById(id)
                                          .orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setAbout(userDTO.getAbout());
        User updatedUser = userRepository.save(existingUser);
        return mapToUserDTO(updatedUser);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                                  .orElseThrow(()-> new ResourceNotFoundException("User", "id", id));

        return this.mapToUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToUserDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(user);
    }

//    Mapping dto to user entity
    private User mapToUser(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }
//    Mapping user to UserDTO
    private UserDTO mapToUserDTO(User user){
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(user.getId());
//        userDTO.setAbout(user.getAbout());
//        userDTO.setName(user.getName());
//        userDTO.setEmail(user.getEmail());
//        userDTO.setPassword(user.getPassword());
//        return  userDTO;
        return modelMapper.map(user, UserDTO.class);
    }
}
