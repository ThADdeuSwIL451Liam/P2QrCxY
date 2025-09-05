// 代码生成时间: 2025-09-05 12:04:19
package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@RequestMapping("/api/inventory")
public class InventoryManagementSystem implements CommandLineRunner {

    private final Map<String, Integer> inventory = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(InventoryManagementSystem.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Initialize inventory with some items
        inventory.put("item1", 10);
        inventory.put("item2", 20);
        inventory.put("item3", 15);
    }

    @GetMapping("/items")
    public ResponseEntity<Map<String, Integer>> getItems() {
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @PostMapping("/items")
    public ResponseEntity<Map<String, Integer>> addItem(@RequestBody Map<String, Integer> newItem) {
        newItem.forEach((key, value) -> {
            if (inventory.containsKey(key)) {
                inventory.put(key, inventory.get(key) + value);
            } else {
                inventory.put(key, value);
            }
        });
        return new ResponseEntity<>(inventory, HttpStatus.CREATED);
    }

    @PostMapping("/items/update")
    public ResponseEntity<Map<String, Integer>> updateItem(@RequestBody Map<String, Integer> updatedItem) {
        updatedItem.forEach((key, value) -> {
            if (inventory.containsKey(key)) {
                inventory.put(key, value);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
            }
        });
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @PostMapping("/items/delete")
    public ResponseEntity<Void> deleteItem(@RequestBody String itemKey) {
        if (inventory.containsKey(itemKey)) {
            inventory.remove(itemKey);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }
    }
}
