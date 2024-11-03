package com.vijay.CompletableFutureReactive.response;


import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private double price;
    private Long categoryId;
}

