package com.company.messagerie.service;

import java.util.List;
import com.company.messagerie.rest.MessageRequest;

public interface RedisService {

	public void addMessage(MessageRequest messageRequest);
	public List<MessageRequest> getAllMessages();
	public MessageRequest getMessage();
}
