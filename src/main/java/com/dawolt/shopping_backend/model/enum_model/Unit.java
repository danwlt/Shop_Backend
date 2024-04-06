package com.dawolt.shopping_backend.model.enum_model;

import lombok.Getter;

@Getter
public enum Unit {
    PIECE("Piece"),
    GRAM("Gram"),
    KILOGRAM("Kilogram"),
    LITER("Liter"),
    MILLILITER("Milliliter");

    private final String label;

    Unit(String label) {
        this.label = label;
    }

}
