package com.vijay.CompletableFutureReactive.repository;

import com.vijay.CompletableFutureReactive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
