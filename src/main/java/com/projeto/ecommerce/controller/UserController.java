package com.projeto.ecommerce.controller;

import com.projeto.ecommerce.controller.docs.UserApi;
import com.projeto.ecommerce.dto.user.AddressCreateDTO;
import com.projeto.ecommerce.dto.user.UserCreateDTO;
import com.projeto.ecommerce.dto.user.UserResponseDTO;
import com.projeto.ecommerce.entity.User;
import com.projeto.ecommerce.service.AuthenticationService;
import com.projeto.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController implements UserApi {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Validated UserCreateDTO data) {
        var createdUser = userService.createUser(data);
        String url = "/user/" + createdUser.getId().toString();
        return ResponseEntity.created(URI.create(url)).body(new UserResponseDTO(createdUser));
    }

    @GetMapping("{id}")
    @PreAuthorize("@authenticationService.hasAccessPermission(#id)")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable("id") String id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(new UserResponseDTO(user));

    }

    @GetMapping()
    public ResponseEntity<List<UserResponseDTO>> listAllUsers() {
        List<UserResponseDTO> users  = userService.findAll().stream()
                .map(UserResponseDTO::new).sorted(Comparator.comparing(UserResponseDTO::username)).toList();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/{userId}/address")
    @PreAuthorize("@authenticationService.hasAccessPermission(#id)")
    public ResponseEntity<?> addAddress(@PathVariable("userId") String userId,
                                        @RequestBody AddressCreateDTO data) {
        userService.addAddress(userId, data);
        return ResponseEntity.ok().build();
    }
}
