package com.dawolt.shopping_backend.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingList {
    @Id
    private String shoppingListID;
    private String name;
    private String owner_username;
    private List<String> usernames;
    private LocalDateTime creationDate;
    private List<ShoppingItem> shoppingItems;
    private List<String> tags;

    public ShoppingList(String name, String owner_username){
        this.name = name;
        this.owner_username = owner_username;
        this.usernames = new ArrayList<String>(Collections.singletonList(owner_username));
        this.creationDate = LocalDateTime.now().plusHours(1);
        this.shoppingItems = new ArrayList<ShoppingItem>();
        this.tags = new ArrayList<String>();
    }

    public ShoppingList(String name, String owner_username, ArrayList<String> tags){
        this.name = name;
        this.owner_username = owner_username;
        this.usernames = new ArrayList<String>(Collections.singletonList(owner_username));
        this.creationDate = LocalDateTime.now().plusHours(1);
        this.shoppingItems = new ArrayList<ShoppingItem>();
        this.tags = tags;
    }
}
