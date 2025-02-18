package com.projeto.ecommercee.service;

import com.projeto.ecommercee.dto.user.UserCreateDTO;
import com.projeto.ecommercee.entity.Role;
import com.projeto.ecommercee.entity.User;
import com.projeto.ecommercee.exception.UsernameNotExistsException;
import com.projeto.ecommercee.repository.RoleRepository;
import com.projeto.ecommercee.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static com.projeto.ecommercee.entity.Role.Values.BASIC;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@DataJpaTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private RoleRepository roleRepository;
    
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Should save a user with success")
    void shouldSaveUserWithSuccess() {
        UserCreateDTO userDTO = new UserCreateDTO("admin", "admin@gmail.com", "senha123", "4002-8922");

        User user = new User(userDTO, "$2a$12$C0uGCLdk0vwC4FIYV/cYse8f9giXJ.ont5oLBi/iGKeOkROlirjHa");

        Role role = new Role(2L, BASIC.name());

        when(roleRepository.findByName(BASIC.name())).thenReturn(Optional.of(role));

        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        var userCreated =  userService.createUser(userDTO);


        // Assert
        assertEquals(userDTO.username(), userCreated.getUsername());
        assertEquals(userDTO.email(), userCreated.getEmail());
        assertEquals(userDTO.phone(), userCreated.getPhone());
    }

    @Test
    @DisplayName("Should return a user when the username exists in database")
    void testFindByUsernameWhenExistingShouldReturnUser() {
        // Arrange
        String USERNAME = "felipe";
        User user = new User();
        user.setUsername("felipe");
        user.setEmail("felipe@gmail.com");

        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));

        // Act
        var userFound = userService.findByUsername(USERNAME);


        // Assert (Validação)
        assertNotNull(userFound);
        assertEquals(USERNAME, userFound.getUsername());
    }

    @Test
    @DisplayName("Should return a UsernameNotExistsException when the username does not exists in database")
    void testFindByUsernameWhenNotExistingShouldReturnUsernameNotExistsException() {
        // Arrange
        String USERNAME = "felipe";

        // Assert (Validação)
        assertThrowsExactly(UsernameNotExistsException.class, () -> userService.findByUsername(USERNAME));
    }


}