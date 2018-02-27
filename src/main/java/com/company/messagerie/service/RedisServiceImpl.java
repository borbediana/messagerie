package com.company.messagerie.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.company.messagerie.model.MessageRequest;
import com.company.messagerie.util.JsonUtil;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;

public class RedisServiceImpl implements RedisService {

	@Autowired
	private RedisClient redisClient;
	
	private RedisConnection<String, String> connection;
    
    @PostConstruct
    private void openConnectionToRedis() {
		connection = redisClient.connect();
    }
	
	@Override
	public void addMessage(MessageRequest messageRequest) {
		connection.lpush("msg", JsonUtil.fromObjectToJson(messageRequest));
	}
	
	@Override
	public MessageRequest getMessage() {
		return JsonUtil.fromJsonToObject(connection.lpop("msg"));
	}

	@Override
	public List<MessageRequest> getAllMessages() {
		return connection.lrange("msg", 0, -1).stream().filter(Objects::nonNull)
				.map(JsonUtil::fromJsonToObject).collect(Collectors.toList());
	}
}
