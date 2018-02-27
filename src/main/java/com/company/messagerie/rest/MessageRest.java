package com.company.messagerie.rest;

import org.springframework.web.bind.annotation.RestController;

import com.company.messagerie.model.MessageRequest;
import com.company.messagerie.model.MessageRequest.MessageType;
import com.company.messagerie.service.BusinessService;
import com.company.messagerie.service.RedisService;
import org.springframework.http.ResponseEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping(value = "/message", produces = "application/json")
public class MessageRest {
	
	@Autowired
	private RedisService redisService;
	@Autowired
	private BusinessService businessService;
    
    @RequestMapping(value = "/create", method = RequestMethod.PUT)
    public ResponseEntity<String> createMessage(MessageRequest message) {
    	if(message.getType() == null) {
    		message.setType(MessageType.CHAT);
    	}
    	
    	redisService.addMessage(message);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
    
    @RequestMapping(value = "/persist", method = RequestMethod.GET)
    public ResponseEntity<MessageRequest> persistLastMessage() {
    	
    	MessageRequest lastMessage = redisService.getMessage();
    	boolean success = businessService.persistMessage(lastMessage);
    	HttpStatus status = success ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    	
        return new ResponseEntity<>(lastMessage, status);
    }
    
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<MessageRequest>> getAllMessages() {
    	
        return new ResponseEntity<>(businessService.getAllMessages(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/allt", method = RequestMethod.GET)
    public ResponseEntity<List<MessageRequest>> getAllMessagest() {
    	
        return new ResponseEntity<>(redisService.getAllMessages(), HttpStatus.OK);
    }
}
