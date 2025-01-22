package com.pgs.ecommerce.Ecommerce.infrastructure.rest;

import com.pgs.ecommerce.Ecommerce.application.UserService;
import com.pgs.ecommerce.Ecommerce.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
// http://localhost:8085/api/v1/users
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Slf4j

public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        return ResponseEntity.ok(this.userService.save(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(this.userService.findById(id));
    }
}
