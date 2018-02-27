package com.company.messagerie.service;

import java.util.List;

import com.company.messagerie.model.MessageRequest;

public interface BusinessService {

	public boolean persistMessage(MessageRequest message);
	public List<MessageRequest> getAllMessages();
}
