package com.company.messagerie.rest;

import org.springframework.web.bind.annotation.RestController;

import com.company.messagerie.service.RedisService;

import org.springframework.http.ResponseEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping(value = "/message", produces = "application/json")
public class MessageController {
	
	@Autowired
	private RedisService redisService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<MessageRequest>> getAllMessages(String messageId) {
    	
        return new ResponseEntity<>(redisService.getAllMessages(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.PUT)
    public ResponseEntity<String> createMessage(MessageRequest message) {
    	
    	redisService.addMessage(message);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
    
    @RequestMapping(value = "/persist", method = RequestMethod.GET)
    public ResponseEntity<MessageRequest> persistLastMessage(String messageId) {
    	
    	MessageRequest lastMessage = redisService.getMessage();
    	// persist message
    	
        return new ResponseEntity<>(lastMessage, HttpStatus.OK);
    }
}
