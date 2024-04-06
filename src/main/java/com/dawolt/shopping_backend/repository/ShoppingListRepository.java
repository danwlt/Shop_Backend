package com.dawolt.shopping_backend.repository;


import com.dawolt.shopping_backend.model.ShoppingList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShoppingListRepository extends MongoRepository<ShoppingList, String> {

}
