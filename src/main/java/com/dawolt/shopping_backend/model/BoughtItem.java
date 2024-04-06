package com.dawolt.shopping_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoughtItem {
    @Id
    private String boughItemID;
    private String name;
    private int purchases;

    public  BoughtItem(String name){
        this.name = name;
        this.purchases = 1;
    }
}


