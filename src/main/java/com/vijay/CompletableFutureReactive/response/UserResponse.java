package com.vijay.CompletableFutureReactive.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String role;
    private String gender;
    private String mobileNumber;
}
