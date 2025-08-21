// 代码生成时间: 2025-08-21 11:43:35
package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;

@RestController
@RequestMapping("/api")
public class ResponsiveLayoutController {

    private final Model model; // Assuming a model for demonstration purposes

    public ResponsiveLayoutController(Model model) {
        this.model = model;
    }

    @GetMapping("/responsive")
    public String responsiveLayout() {
        // Add model attributes for reactive view rendering
        model.addAttribute("title", "Responsive Layout Example");
        return "responsiveLayout"; // Return the name of the reactive template
    }

    // Exception handling
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return new ResponseEntity<>("Error: " + ex.getReason(), ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // Log the exception
        System.err.println("An error occurred: " + ex.getMessage());
        return new ResponseEntity<>("Internal Server Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
