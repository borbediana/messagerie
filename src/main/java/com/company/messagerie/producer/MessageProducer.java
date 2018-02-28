package com.company.messagerie.producer;

import java.util.concurrent.BlockingQueue;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.messagerie.model.MessageRequest;

public class MessageProducer<T> {
	private BlockingQueue<MessageRequest> queue;
    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

	public MessageProducer(BlockingQueue<MessageRequest> queue) {
		this.queue = queue;
	}

	public void produce(Supplier<MessageRequest> supplier) {
		if(supplier == null) {
			logger.info("Supplier is null!");
			return;
		}
		
		try {
			final MessageRequest msg = supplier.get();
			if (msg == null) {
				return;
			}
			
			queue.put(msg);
			logger.info("Put in queue" + msg.getContent());
		} catch (Exception e) {
			logger.error("Supplier can not supply any value to produce!");
		}
	}
}
