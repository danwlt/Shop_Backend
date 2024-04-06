package com.dawolt.shopping_backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    @Id
    private String tokenID;
    public String token;
    public String tokenType = "BEARER";

    // Add for advanced jwt features
    public boolean revoked;
    public boolean expired;

    public User user;
}
