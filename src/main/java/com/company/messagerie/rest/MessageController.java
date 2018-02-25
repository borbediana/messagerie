package com.company.messagerie.rest;

import org.springframework.web.bind.annotation.RestController;

import com.company.messagerie.service.RedisService;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping(value = "/message", produces = "application/json")
public class MessageController {
	
	@Autowired
	private RedisService redisService;

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseEntity<MessageRequest> getMessageById(String messageId) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.PUT)
    public ResponseEntity<String> createMessage(MessageRequest message) {
    	
    	redisService.addMessage(message.getMessage(), message.getAuthor());
        return new ResponseEntity<>("", HttpStatus.OK);
    }

}
