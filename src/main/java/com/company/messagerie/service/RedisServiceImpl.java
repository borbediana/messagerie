package com.company.messagerie.service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.company.messagerie.rest.MessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;

public class RedisServiceImpl implements RedisService {

   private RedisClient redisClient;
   private RedisConnection<String, String> connection;

    @Autowired
    public void setRedisClient(RedisClient redisClient) {
        this.redisClient = redisClient;
   }
    
    @PostConstruct
    private void openConnectionToRedis() {
		connection = redisClient.connect();
    }
	
	@Override
	public void addMessage(MessageRequest messageRequest) {
		connection.lpush("msg", fromObjectToJson(messageRequest));
	}
	
	@Override
	public MessageRequest getMessage() {
		return fromJsonToObject(connection.lpop("msg"));
	}

	@Override
	public List<MessageRequest> getAllMessages() {
		return connection.lrange("msg", 0, -1).stream().filter(Objects::nonNull)
				.map(this::fromJsonToObject).collect(Collectors.toList());
	}

    private String fromObjectToJson(MessageRequest messageRequest) {
    	if(messageRequest == null) {
    		return null;
    	}
    	
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			return mapper.writeValueAsString(messageRequest);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    private MessageRequest fromJsonToObject(String jsonObject) {
    	if(jsonObject == null) {
    		return null;
    	}
    	
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			return mapper.readValue(jsonObject, MessageRequest.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
}
