package com.vijay.CompletableFutureReactive.request;


import lombok.Data;

@Data
public class ProductRequest {
    private Long id;
    private String name;
    private String description;
    private double price;
    private Long categoryId;
}

