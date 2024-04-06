package com.dawolt.shopping_backend.service;

import java.util.List;

import com.dawolt.shopping_backend.model.BoughtItem;
import com.dawolt.shopping_backend.model.ShoppingItem;
import com.dawolt.shopping_backend.model.ShoppingList;
import com.dawolt.shopping_backend.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.domain.Sort;


@Service
public class ShoppingListService {
    private final ShoppingListRepository shoppingListRepository;
    private final BoughtItemService boughtItemService;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public  ShoppingListService(ShoppingListRepository shoppingListRepository, BoughtItemService boughtItemService, MongoTemplate mongoTemplate){
        this.shoppingListRepository = shoppingListRepository;
        this.boughtItemService = boughtItemService;
        this.mongoTemplate = mongoTemplate;
    }

    public void createShoppingList(String name, String username){
        ShoppingList shoppingList = new ShoppingList(name, username);
        shoppingListRepository.save(shoppingList);
    }

    public void addUserToShoppingList(String username, String shoppingListId, List<String> usernames){
        Query query = new Query(Criteria.where("_id").is(shoppingListId).and("usernames").in(username));
        for (String user : usernames) {
            Update update = new Update().addToSet("usernames", user);
            mongoTemplate.updateFirst(query, update, ShoppingList.class);
        }
    }

    public void removeUserFromShoppingList(String username, String shoppingListId, String userToDelete) {
        Query query = new Query(Criteria.where("_id").is(shoppingListId).and("usernames").in(username));
        Update update = new Update().pull("usernames", userToDelete);
        mongoTemplate.updateFirst(query, update, ShoppingList.class);
    }

    public void deleteShoppingList(String shoppingListId, String owner_username){
        Query query = new Query(Criteria.where("_id").is(shoppingListId).and("owner_username").is(owner_username));
        mongoTemplate.remove(query, ShoppingList.class);
    }

    //Maybe add filter
    public List<ShoppingList> getShoppingListsByUsername(String username) {
        Query query = new Query(Criteria.where("usernames").in(username));
        query.with(Sort.by(Sort.Order.asc("name")));
        return mongoTemplate.find(query, ShoppingList.class);
    }

    public List<ShoppingList> getShoppingListByID(String username, String shoppingListID) {
        Query query = new Query(Criteria.where("_id").is(shoppingListID).and("usernames").in(username));
        query.with(Sort.by(Sort.Order.asc("name")));
        return mongoTemplate.find(query, ShoppingList.class);
    }

    public void addTagToShoppingList(String username, String shoppingListId, List<String> tags){
        Query query = new Query(Criteria.where("_id").is(shoppingListId).and("usernames").in(username));
        for (String tag : tags) {
            Update update = new Update().addToSet("tags", tag);
            mongoTemplate.updateFirst(query, update, ShoppingList.class);
        }
    }

    public void removeTagFromShoppingList(String username, String shoppingListId, String tag){
        Query query = new Query(Criteria.where("_id").is(shoppingListId).and("usernames").in(username));
        Update update = new Update().pull("tags", tag);
        mongoTemplate.updateFirst(query, update, ShoppingList.class);
    }

    public void addItem(String username, String shoppingListId, List<ShoppingItem> shoppingItems){
        Query query = new Query(Criteria.where("_id").is(shoppingListId).and("usernames").in(username));
        for (ShoppingItem shoppingItem : shoppingItems){
            Update update = new Update().addToSet("shoppingItems", shoppingItem);
            mongoTemplate.updateFirst(query, update, ShoppingList.class);
        }
    }

    public void removeItem(String username, String shoppingListId, List<ShoppingItem> shoppingItems){
        Query query = new Query(Criteria.where("_id").is(shoppingListId).and("usernames").in(username));
        for (ShoppingItem shoppingItem : shoppingItems){
            Update update = new Update().pull("shoppingItems", shoppingItem);
            _updateBoughtItem(username, shoppingItem);
            mongoTemplate.updateFirst(query, update, ShoppingList.class);
        }
    }

    private void _updateBoughtItem(String username, ShoppingItem shoppingItem){
        Query query = new Query(Criteria.where("name").is(shoppingItem.getName().toLowerCase()));
        BoughtItem existingItem = mongoTemplate.findOne(query, BoughtItem.class, BoughtItemService._getCollectionName(username));

        if (existingItem != null) {
            boughtItemService.incrementPurchases(username, existingItem.getBoughItemID());
        } else {
            boughtItemService.addBoughtItem(username, shoppingItem.getName().toLowerCase());
        }
    }
}
