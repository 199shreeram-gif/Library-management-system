package com.company.librarysystem.component;

import com.company.librarysystem.entity.Role;
import com.company.librarysystem.entity.User;
import com.company.librarysystem.repository.RoleRepository;
import com.company.librarysystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            // Create both roles
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(adminRole);
            roleRepository.save(userRole);

            // Create the admin user
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("password123"));
            admin.setRoles(Set.of(adminRole));

            userRepository.save(admin);
            System.out.println("Admin created! Username: admin | Password: password123");
        }
    }
}