package com.projeto.ecommercee.service;


import com.projeto.ecommercee.dto.ViaCepDTO;
import com.projeto.ecommercee.dto.user.AddressCreateDTO;
import com.projeto.ecommercee.dto.user.UserCreateDTO;
import com.projeto.ecommercee.entity.Address;
import com.projeto.ecommercee.entity.Role;
import com.projeto.ecommercee.entity.User;
import com.projeto.ecommercee.exception.InsufficientStockException;
import com.projeto.ecommercee.exception.UserAlreadyExistsException;
import com.projeto.ecommercee.exception.UsernameNotExistsException;
import com.projeto.ecommercee.repository.RoleRepository;
import com.projeto.ecommercee.repository.UserRepository;
import com.projeto.ecommercee.repository.ViaCepService;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static com.projeto.ecommercee.entity.Role.Values.BASIC;
import static java.util.Objects.isNull;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ViaCepService viaCepService;


    public User save(User user){
        return userRepository.save(user);
    }

    public User findById(String uuid) {
        return userRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário com id %s não encontrado.".formatted(uuid.toString())));
    }

    public User findByUsername(String username) {
       return userRepository.findByUsername(username).orElseThrow(
               UsernameNotExistsException::new
       );
    }

    @Transactional
    public User createUser(UserCreateDTO userDTO){
        boolean userExist = userRepository.existsByUsername(userDTO.username()) || userRepository.existsByEmail(userDTO.email());
        if (userExist) {
            throw new UserAlreadyExistsException();
        }

        var role = roleRepository.findByName(BASIC.name()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        User user = new User(userDTO, encoder.encode(userDTO.password().trim()));
        user.setRoles(Set.of(role));
        return userRepository.save(user);
    }

    public void addAddress(String username, AddressCreateDTO addressDTO){
        User user = findByUsername(username);

        try {
            ViaCepDTO viaCepDTO = viaCepService.getAddress(addressDTO.zipCode().replace("-", ""));
            Address address = new Address(viaCepDTO, addressDTO.street());
            user.setAddress(address);
            userRepository.save(user);
        } catch (FeignException.BadRequest e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zip Code not found");
        }

    }


    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
