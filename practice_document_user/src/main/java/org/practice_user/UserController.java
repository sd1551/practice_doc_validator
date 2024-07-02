package org.practice_user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/{userId}/doc")
    public void uploadDocument(@PathVariable("userId") UUID userId, @RequestParam("file") MultipartFile file) throws IOException {
        userService.uploadDocument(userId, file);
    }

    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody UserDto userDto) {
        userService.addUser(userDto.getLogin());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
