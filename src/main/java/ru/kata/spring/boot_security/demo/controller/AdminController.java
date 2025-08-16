package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserDetailsService;

import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserDetailsService userService;
    private final RoleService roleService;

    public AdminController(UserDetailsService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @PostMapping("/new")
    public String newUserForm(Model model) {
        List<Role> allRoles = roleService.getAllRoles();
        System.out.println("Роли в системе: " + allRoles);
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "new";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roles", required = false) List<Long> roleIds) {
        if (roleIds != null) {
            List<Role> roles = roleService.getRolesByIds(roleIds);
            user.setRoles(new HashSet<>(roles));
        } else {
            user.setRoles(new HashSet<>());
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "edit";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}