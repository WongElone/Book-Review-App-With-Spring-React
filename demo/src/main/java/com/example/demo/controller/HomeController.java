package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HomeController {

    @GetMapping
    public String helloWorld() {
        return "Hello World! /api/v1 is the base url of the API endpoints available for testing";
    }

    @GetMapping("/api/v1")
    public String apiGuide() {
        return "If you want to test the API endpoints, please check out the documentation linked in the github page.";
    }
}
