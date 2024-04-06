package com.dawolt.shopping_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import com.dawolt.shopping_backend.model.enum_model.Unit;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingItem {
    private String name;
    private int amount;
    private Unit unit;
}
