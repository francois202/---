package com.medsystem.controller;

import com.medsystem.model.Role;
import com.medsystem.model.UserEntity;
import com.medsystem.repository.RoleRepository;
import com.medsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           Model model) {

        if (userRepository.existsByUsername(username)) {
            model.addAttribute("error", "Такой пользователь уже существует.");
            return "register";
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(passwordEncoder.encode(password));

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));

        userEntity.setRoles(Collections.singletonList(userRole));
        userRepository.save(userEntity);

        return "redirect:/auth/login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
