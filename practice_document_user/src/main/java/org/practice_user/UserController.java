package org.practice_user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping("/add")
    public String showAddUserForm() {
        return "addUser";
    }

    @PostMapping("/add")
    public String addUser(@RequestParam("login") String login, Model model) {
        userService.addUser(login);
        return "redirect:/users";
    }

    @GetMapping("/{userId}/upload")
    public String showUploadDocumentForm(@PathVariable("userId") UUID userId, Model model) {
        model.addAttribute("userId", userId);
        return "uploadDocument";
    }

    @PostMapping("/{userId}/upload")
    public String uploadDocument(@PathVariable("userId") UUID userId, @RequestParam("file") MultipartFile file, Model model) throws IOException {
        userService.uploadDocument(userId, file);
        return "redirect:/users";
    }
}