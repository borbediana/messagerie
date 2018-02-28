package com.company.messagerie.consumer;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.messagerie.model.MessageRequest;

public class MessageConsumer<T> {
    private BlockingQueue<MessageRequest> queue;
    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
    
    public MessageConsumer(BlockingQueue<MessageRequest> queue) {
        this.queue = queue;
    }

    public void consume(Consumer<MessageRequest> consumer) {
        try {
            consumer.accept(queue.take());
            logger.info("Remove from queue");
        } catch (Exception e) {
			logger.error("Supplier can not supply any value to consume!");
        }
    }
}
