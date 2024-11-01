package com.vijay.CompletableFutureReactive.controller;



import com.vijay.CompletableFutureReactive.request.UserRequest;
import com.vijay.CompletableFutureReactive.response.UserResponse;
import com.vijay.CompletableFutureReactive.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @RequestMapping
    public CompletableFuture<ResponseEntity<List<UserResponse>>> getAllUsers(){
        return userService.getUsers()
                .thenApply(ResponseEntity::ok);
    }
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<UserResponse>> getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .thenApply(ResponseEntity::ok);
    }
    @PostMapping
    public CompletableFuture<ResponseEntity<UserResponse>> createUser(@RequestBody UserRequest userRequest) {
        return userService.create(userRequest)
                .thenApply(ResponseEntity::ok);
    }
    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<UserResponse>> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return userService.updateUser(id,userRequest)
                .thenApply(ResponseEntity::ok);
    }
    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id)
                .thenApply(v -> ResponseEntity.noContent().build());
    }
}
