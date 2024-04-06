package com.dawolt.shopping_backend.controller;

import com.dawolt.shopping_backend.model.BoughtItem;
import com.dawolt.shopping_backend.model.ShoppingList;
import com.dawolt.shopping_backend.service.BoughtItemService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/recent")
public class RecentItemController {

    @Autowired
    BoughtItemService boughtItemService;
    @GetMapping("/getTopRecentItems")
    public List<BoughtItem> getTopRecentItems(HttpServletRequest request){
        return boughtItemService.getMostBoughtItems((String) request.getAttribute("username"), 6);
    }
    @GetMapping("/getRecentItems")
    public List<BoughtItem> getAllRecentItems(HttpServletRequest request){
        return boughtItemService.getBoughtItems((String) request.getAttribute("username"));
    }
}
