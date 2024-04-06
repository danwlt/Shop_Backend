package com.dawolt.shopping_backend.controller;

import com.dawolt.shopping_backend.model.ShoppingList;
import com.dawolt.shopping_backend.model.User;
import com.dawolt.shopping_backend.service.ShoppingListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingList")
public class ShoppingListController {

    @Autowired
    ShoppingListService shoppingListService;

    @GetMapping("/getShoppingLists")
    public List<ShoppingList> getShoppingLists(HttpServletRequest request){
        return shoppingListService.getShoppingListsByUsername((String) request.getAttribute("username"));
    }

    @GetMapping("/getSingleShoppingList")
    public List<ShoppingList> searchShoppingLists(HttpServletRequest request, @RequestParam String shoppingListID){
        return shoppingListService.getShoppingListByID((String) request.getAttribute("username"), shoppingListID);
    }

    // ToDo: Add search

    //ToDo: Add filter params

    //ToDo: Search by tag

    @PostMapping("/create")
    public void addShoppingList(HttpServletRequest request, @RequestBody ShoppingList shoppingList){
        shoppingListService.createShoppingList(shoppingList.getName(), (String) request.getAttribute("username"));
    }

    @PostMapping("/addUser")
    public void addUserToShoppingList(HttpServletRequest request, @RequestBody ShoppingList shoppingList){
        shoppingListService.addUserToShoppingList((String) request.getAttribute("username"), shoppingList.getShoppingListID(), shoppingList.getUsernames());
    }


    @PostMapping("/addTag")
    public void addTagToShoppingList(HttpServletRequest request, @RequestBody ShoppingList shoppingList){
        shoppingListService.addTagToShoppingList((String) request.getAttribute("username"), shoppingList.getShoppingListID(), shoppingList.getTags());
    }

    @DeleteMapping("/removeUser/{ListID}")
    public void removeUserFromShoppingList(HttpServletRequest request, @PathVariable String ListID, @RequestParam String user){
        shoppingListService.removeUserFromShoppingList((String) request.getAttribute("username"), ListID, user);
    }

    @DeleteMapping("/delete/{ListID}")
    public void deleteShoppingList(HttpServletRequest request, @PathVariable String ListID){
        shoppingListService.deleteShoppingList(ListID, (String) request.getAttribute("username"));
    }

    @DeleteMapping("/removeTag/{ListID}")
    public void removeTagFromShoppingList(HttpServletRequest request, @PathVariable String ListID, @RequestParam String tag){
        shoppingListService.removeTagFromShoppingList((String) request.getAttribute("username"), ListID, tag);
    }

    @PostMapping("/addItem")
    public void addItemToShoppingList(HttpServletRequest request, @RequestBody ShoppingList shoppingList){
        shoppingListService.addItem((String) request.getAttribute("username"), shoppingList.getShoppingListID(), shoppingList.getShoppingItems());
    }

    @DeleteMapping("/removeItem")
    public void removeItemFromShoppingList(HttpServletRequest request, @RequestBody ShoppingList shoppingList){
        shoppingListService.removeItem((String) request.getAttribute("username"), shoppingList.getShoppingListID(), shoppingList.getShoppingItems());
    }
}
