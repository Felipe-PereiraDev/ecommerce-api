package com.projeto.ecommerce.service;

import com.projeto.ecommerce.dto.ViaCepDTO;
import com.projeto.ecommerce.dto.user.AddressCreateDTO;
import com.projeto.ecommerce.dto.user.UserCreateDTO;
import com.projeto.ecommerce.entity.Role;
import com.projeto.ecommerce.entity.User;
import com.projeto.ecommerce.exception.UsernameNotExistsException;
import com.projeto.ecommerce.repository.RoleRepository;
import com.projeto.ecommerce.repository.UserRepository;
import com.projeto.ecommerce.repository.ViaCepService;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.projeto.ecommerce.entity.Role.Values.BASIC;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@DataJpaTest
//@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private RoleRepository roleRepository;
    
    @InjectMocks
    private UserService userService;

    @Mock
    private ViaCepService viaCepService;

    private static final String USERNAME = "felipe";
    private static final String EMAIL = "felipe";
    private static final String UUID_USER = "4441ffc4-254c-43b0-9862-a0bb701d9a4c";

    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        // Arrange
        user = new User();
        user.setUsername(USERNAME);
        user.setEmail(EMAIL);
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

        // Assert (Validação)
        assertThrowsExactly(UsernameNotExistsException.class, () -> userService.findByUsername(USERNAME));
    }


    @Test
    @DisplayName("Should save address with success")
    void testAddAddressWithSuccess() {
        // Arrange

        ViaCepDTO viaCepDTO = new ViaCepDTO("128828", "Jorge Teixeira", "Amazonas", "Manaus");
        AddressCreateDTO addressRequest = new AddressCreateDTO("128828", "Amazonas", "Manaus", "nandum");

        when(userRepository.findById(UUID.fromString(UUID_USER))).thenReturn(Optional.of(user));
        when(viaCepService.getAddress("128828")).thenReturn(viaCepDTO);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        userService.addAddress(UUID_USER, addressRequest);

        // Assert (Validação)

        verify(userRepository).save(user);
        assertNotNull(user.getAddress());
        assertEquals(viaCepDTO.cep(), user.getAddress().getZipCode());
        assertEquals(viaCepDTO.estado(), user.getAddress().getState());

    }

    @Test
    @DisplayName("Should throw exception when address cannot be added")
    void testAddAddressShouldThrowExceptionWhenAddressCannotBeAdded(){

        AddressCreateDTO addressRequest = new AddressCreateDTO("128828", "Amazonas", "Manaus", "nandum");

        when(userRepository.findById(UUID.fromString(UUID_USER))).thenReturn(Optional.of(user));
        when(viaCepService.getAddress(addressRequest.zipCode())).thenThrow(FeignException.BadRequest.class);
        // Assert (Validação)

        assertThrows(ResponseStatusException.class,() -> userService.addAddress(UUID_USER, addressRequest));

    }



    @Test
    @DisplayName("Should return all users successfully")
    void testFindAllReturnsAllUsersSuccessfully() {
        //Arrange
        User use1 = new User();
        User use2 = new User();
        final int TOTAL_USERS = 2;

        var listUsers = List.of(use1, use2);
        when(userRepository.findAll()).thenReturn(listUsers);

        // Act
        var users = userService.findAll();

        // Assert
        assertNotNull(users);
        assertEquals(TOTAL_USERS, users.size());
    }



}