package com.projeto.ecommercee.service;


import com.projeto.ecommercee.dto.user.AddressCreateDTO;
import com.projeto.ecommercee.dto.user.UserCreateDTO;
import com.projeto.ecommercee.entity.Address;
import com.projeto.ecommercee.entity.User;
import com.projeto.ecommercee.exception.UserAlreadyExistsException;
import com.projeto.ecommercee.exception.UsernameNotExistsException;
import com.projeto.ecommercee.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public User findById(String uuid) {
        return userRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public User findByUsername(String username) {
       return userRepository.findByUsername(username).orElseThrow(
               () -> new UsernameNotExistsException()
       );
    }

    public User createUser(UserCreateDTO userDTO){
        boolean userExist = userRepository.existsByUsername(userDTO.username()) || userRepository.existsByEmail(userDTO.email());
        if (userExist) {
            throw new UserAlreadyExistsException();
        }
        User user = new User(userDTO);
        return userRepository.save(user);
    }

    public void addAddress(String username, AddressCreateDTO addressDTO){
        Address address = new Address(addressDTO);

        User user = findByUsername(username);

        user.setAddress(address);

        userRepository.save(user);
    }



}
