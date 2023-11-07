package com.recruitment.task.api;

import com.recruitment.task.dto.UserDTO;
import com.recruitment.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{login}")
    private ResponseEntity<UserDTO> getUserByLogin(@PathVariable String login) {
        return ResponseEntity.ok(userService.getUserByLogin(login));
    }
}
