package ru.kata.spring.boot_security.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", allRoles);
        logger.debug("Открыта форма создания пользователя");
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
        logger.info("Создан новый пользователь: {}", user.getEmail());
        return "redirect:/admin";
    }

    // Теперь GET, а не POST — просто открывает форму
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        logger.debug("Открыта форма редактирования пользователя с ID {}", id);
        return "edit";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roles", required = false) List<Long> roleIds) {
        if (roleIds != null) {
            List<Role> roles = roleService.getRolesByIds(roleIds);
            user.setRoles(new HashSet<>(roles));
        } else {
            user.setRoles(new HashSet<>());
        }
        userService.updateUser(user);
        logger.info("Пользователь обновлён: {}", user.getEmail());
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        logger.info("Пользователь удалён: id={}", id);
        return "redirect:/admin";
    }
}