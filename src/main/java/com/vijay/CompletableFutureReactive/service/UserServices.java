package com.vijay.CompletableFutureReactive.service;

import com.vijay.CompletableFutureReactive.entity.User;
import com.vijay.CompletableFutureReactive.exception.ResourceNotFoundException;
import com.vijay.CompletableFutureReactive.mapper.Mapper;
import com.vijay.CompletableFutureReactive.repository.UserRepository;
import com.vijay.CompletableFutureReactive.request.UserRequest;
import com.vijay.CompletableFutureReactive.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@AllArgsConstructor
public class UserServices {
    private final UserRepository userRepository;

    @Async
    public CompletableFuture<List<UserResponse>> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userListResponse = users.stream()
                .map(user -> {
                    UserResponse userDto = Mapper.toDto(user, UserResponse.class);
                    return userDto;
                })
                .toList();
        return CompletableFuture.completedFuture(userListResponse);
    }

    @Async
    public CompletableFuture<UserResponse> getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return CompletableFuture.completedFuture(Mapper.toDto(user, UserResponse.class));
    }

    @Async
    public CompletableFuture<UserResponse> createUser(UserRequest userRequest) {
        User user = Mapper.toEntity(userRequest, User.class);
        User savedUser = userRepository.save(user);
        return CompletableFuture.completedFuture(Mapper.toDto(savedUser,UserResponse.class));
    }

    @Async
    public CompletableFuture<UserResponse> updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        User updatedUser = Mapper.toEntity(userRequest, User.class, "id");
        User savedUser = userRepository.save(updatedUser);
        return CompletableFuture.completedFuture(Mapper.toDto(savedUser, UserResponse.class));
    }

    @Async
    public CompletableFuture<Void> deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
        return CompletableFuture.completedFuture(null);
    }

}

