package com.projeto.ecommercee;

import com.projeto.ecommercee.entity.Role;
import com.projeto.ecommercee.entity.User;
import com.projeto.ecommercee.repository.RoleRepository;
import com.projeto.ecommercee.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static com.projeto.ecommercee.entity.Role.Values.ADMIN;
import static com.projeto.ecommercee.entity.Role.Values.BASIC;

@Component
public class AppRun implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public AppRun(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder encoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (!roleRepository.existsByName(Role.Values.ADMIN.name())) {
            roleRepository.saveAndFlush(new Role(null, Role.Values.ADMIN.name()));
        }
        if (!roleRepository.existsByName(Role.Values.BASIC.name())) {
            roleRepository.saveAndFlush(new Role(null, Role.Values.BASIC.name()));
        }
        createUserAdmin();
    }

    public void createUserAdmin() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            var roleAdmin = roleRepository.findByName(ADMIN.name())
                    .orElse(null);
            var roleBasic = roleRepository.findByName(BASIC.name())
                    .orElse(null);

            admin.setUsername("admin");
            admin.setPassword(encoder.encode("admin"));
            admin.setEmail("admin@gmail.com");
            Set<Role> roles = new HashSet<>();
            roles.add(roleAdmin);
            roles.add(roleBasic);
            admin.setRoles(roles);
            userRepository.save(admin);
        }
    }
}
