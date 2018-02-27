package com.company.messagerie.producer;

import java.util.concurrent.BlockingQueue;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;

import com.company.messagerie.model.MessageRequest;
import com.company.messagerie.service.RedisService;

public class MessageProducer<T> {
	
	@Autowired
	public RedisService redisService;
	
	private BlockingQueue<MessageRequest> queue;

	public MessageProducer(BlockingQueue<MessageRequest> queue) {
		this.queue = queue;
	}

	public void produce(Supplier<MessageRequest> supplier) {
		try {
			final MessageRequest msg = supplier.get();
			queue.put(msg);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
