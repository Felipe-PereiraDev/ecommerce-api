package com.projeto.ecommercee.controller;

import com.projeto.ecommercee.dto.user.AddressCreateDTO;
import com.projeto.ecommercee.dto.user.UserCreateDTO;
import com.projeto.ecommercee.dto.user.UserResponseDTO;
import com.projeto.ecommercee.entity.User;
import com.projeto.ecommercee.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable("id") String id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(new UserResponseDTO(user));
    }


    @GetMapping()
    public ResponseEntity<List<UserResponseDTO>> findAllUsers() {
        List<UserResponseDTO> users  = userService.findAll().stream()
                .map(UserResponseDTO::new).sorted(Comparator.comparing(UserResponseDTO::username)).toList();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Validated UserCreateDTO data) {
        var createdUser = userService.createUser(data);
        String url = "/user/" + createdUser.getId().toString();
        return ResponseEntity.created(URI.create(url)).body(new UserResponseDTO(createdUser));
    }

    @PostMapping("/{username}/address")
    public ResponseEntity<?> addAddress(@PathVariable("username") String username,
                                        @RequestBody AddressCreateDTO data) {
        userService.addAddress(username, data);
        return ResponseEntity.ok().build();
    }
}
