package com.company.messagerie.rest;

import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping(value = "/message", produces = "application/json")
public class HelloController {
    
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String index() {
        return "{\"message\": \"Greetings from Spring Boot!\"}";
    }
    
}
