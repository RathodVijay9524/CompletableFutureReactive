package com.vijay.CompletableFutureReactive.service;



import com.vijay.CompletableFutureReactive.entity.User;
import com.vijay.CompletableFutureReactive.exception.ResourceNotFoundException;
import com.vijay.CompletableFutureReactive.mapper.Mapper;
import com.vijay.CompletableFutureReactive.repository.UserRepository;
import com.vijay.CompletableFutureReactive.request.UserRequest;
import com.vijay.CompletableFutureReactive.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@AllArgsConstructor
@Log4j2
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;

    @Async("asyncTaskExecutor")
    public CompletableFuture<List<UserResponse>> getUsers() {
        log.info("Fetching all User");
        return CompletableFuture.supplyAsync(() -> {
            List<User> users = userRepository.findAll();
            return users.stream()
                    .map(user -> mapper.toDto(user, UserResponse.class))
                    .toList();
        });
    }

    @Async("asyncTaskExecutor")
    public CompletableFuture<UserResponse> getUser(Long id) {
        log.info("Fetching patient with ID: {}", id);
        return CompletableFuture.supplyAsync(() -> {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
            return mapper.toDto(user, UserResponse.class);
        });
    }

    @Async("asyncTaskExecutor")
    public CompletableFuture<UserResponse> create(UserRequest userRequest) {
        log.info("Saving User: {}", userRequest.getName());
        return CompletableFuture.supplyAsync(() -> {
            User user = mapper.toDto(userRequest, User.class);
            userRepository.save(user);
            return mapper.toDto(user, UserResponse.class);
        });
    }

    @Async("asyncTaskExecutor")
    public CompletableFuture<Void> deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);
        return CompletableFuture.runAsync(() -> {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
            userRepository.delete(user);
        });
    }

    @Async("asyncTaskExecutor")
    public CompletableFuture<UserResponse> updateUser(Long id, UserRequest userRequest) {
        log.info("Updating User with ID: {}", id);
        return CompletableFuture.supplyAsync(() -> {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
            User updatedUser = Mapper.toEntity(userRequest, User.class, "id");
            updatedUser.setId(user.getId());
            User savedUser = userRepository.save(updatedUser);
            return mapper.toDto(savedUser, UserResponse.class);
        });
    }
}