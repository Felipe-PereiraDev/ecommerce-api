package com.projeto.ecommercee;

import com.projeto.ecommercee.entity.Role;
import com.projeto.ecommercee.entity.User;
import com.projeto.ecommercee.repository.RoleRepository;
import com.projeto.ecommercee.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static com.projeto.ecommercee.entity.Role.Values.*;

@Component
public class AppRun implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

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
