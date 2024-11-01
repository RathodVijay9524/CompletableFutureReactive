package com.vijay.CompletableFutureReactive.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String role;
    private String gender;
    private String mobileNumber;
}
