package com.company.messagerie.consumer;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;

import com.company.messagerie.model.MessageRequest;
import com.company.messagerie.service.BusinessService;

public class MessageConsumer<T> {
	
	@Autowired
	public BusinessService businessService;
	
    private BlockingQueue<MessageRequest> queue;
    
    public MessageConsumer(BlockingQueue<MessageRequest> queue) {
        this.queue = queue;
    }

    public void consume(Consumer<MessageRequest> consumer) {
        try {
            consumer.accept(queue.take());
        } catch (InterruptedException e) {
        	e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
