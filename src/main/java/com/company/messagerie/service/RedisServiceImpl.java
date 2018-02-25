package com.company.messagerie.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;

public class RedisServiceImpl implements RedisService {

   private RedisClient redisClient;

    @Autowired
    public void setRedisClient(RedisClient redisClient) {
        this.redisClient = redisClient;
   }
	
	@Override
	public void addMessage(String message, String author) {
		ping();
	}
	
    private String ping() {
        RedisConnection<String, String> connection = redisClient.connect();
        String result = connection.ping();
        connection.close();
        return result;
    }
}
