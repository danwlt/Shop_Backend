package com.dawolt.shopping_backend.service;

import com.dawolt.shopping_backend.model.BoughtItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoughtItemService {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public BoughtItemService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void addBoughtItem(String username, String name){
        String colName = _getCollectionName(username);
        BoughtItem boughtItem = new BoughtItem(name);
        mongoTemplate.save(boughtItem, colName);
    }

    public void incrementPurchases(String username, String documentId) {
        String colName = _getCollectionName(username);
        Query query = new Query(Criteria.where("_id").is(documentId));
        Update update = new Update().inc("purchases", 1);
        mongoTemplate.updateFirst(query, update, BoughtItem.class, colName);
    }

    public List<BoughtItem> getMostBoughtItems(String username, int amount){
        String colName = _getCollectionName(username);
        Query query = new Query().with(Sort.by(Sort.Order.desc("purchases"))).limit(amount);
        return mongoTemplate.find(query, BoughtItem.class, colName);
    }

    public List<BoughtItem> getBoughtItems(String username){
        String colName = _getCollectionName(username);
        Query query = new Query().with(Sort.by(Sort.Order.desc("purchases")));
        return mongoTemplate.find(query, BoughtItem.class, colName);
    }

    public void deleteItem(String username, String documentId){
        String colName = _getCollectionName(username);
        Query query = new Query(Criteria.where("_id").is(documentId));
        mongoTemplate.remove(query, colName);
    }

    public void deleteCollection(String username){
        String colName = _getCollectionName(username);
        mongoTemplate.dropCollection(colName);
    }

    public static String _getCollectionName(String username){
        return "shopping_items_" + username;
    }

}
