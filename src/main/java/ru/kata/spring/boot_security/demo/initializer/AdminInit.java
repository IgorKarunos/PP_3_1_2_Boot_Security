package ru.kata.spring.boot_security.demo.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Set;

@Component
public class AdminInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminInit(UserRepository userRepository,
                     RoleRepository roleRepository,
                     PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Role adminRole = roleRepository.findByName("ADMIN");
        if (adminRole == null) {
            adminRole = new Role("ADMIN");
            roleRepository.save(adminRole);
        }
        Role userRole = roleRepository.findByName("USER");
        if (userRole == null) {
            userRole = new Role("USER");
            roleRepository.save(userRole);
        }
        if (userRepository.findByEmail("admin@mail.ru") == null) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@mail.ru");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);
        }
    }
}
