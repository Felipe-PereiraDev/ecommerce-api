package com.projeto.ecommercee.service;


import com.projeto.ecommercee.dto.user.AddressCreateDTO;
import com.projeto.ecommercee.dto.user.UserCreateDTO;
import com.projeto.ecommercee.entity.Address;
import com.projeto.ecommercee.entity.Role;
import com.projeto.ecommercee.entity.User;
import com.projeto.ecommercee.exception.UserAlreadyExistsException;
import com.projeto.ecommercee.exception.UsernameNotExistsException;
import com.projeto.ecommercee.repository.RoleRepository;
import com.projeto.ecommercee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.projeto.ecommercee.entity.Role.Values.BASIC;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RoleRepository roleRepository;


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

        var role = roleRepository.findByName(BASIC.name()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        User user = new User(userDTO, encoder.encode(userDTO.password()));
        user.setRoles(Set.of(role));
        System.out.println(user.getRoles());
        return userRepository.save(user);
    }

    public void addAddress(String username, AddressCreateDTO addressDTO){
        Address address = new Address(addressDTO);

        User user = findByUsername(username);

        user.setAddress(address);

        userRepository.save(user);
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }
}
