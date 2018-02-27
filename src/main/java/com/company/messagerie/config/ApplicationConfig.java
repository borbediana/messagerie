package com.company.messagerie.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.company.messagerie.consumer.MessageConsumer;
import com.company.messagerie.model.MessageRequest;
import com.company.messagerie.producer.MessageProducer;
import com.company.messagerie.service.BusinessService;
import com.company.messagerie.service.RedisService;

public class ApplicationConfig {

	@Autowired
	public static RedisService redisService;
	@Autowired
	public static BusinessService businessService;
	
	private static final int MESSAGES_NUMBER = 100;
    private static ApplicationConfig instance;
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
    
	private static final BlockingQueue<MessageRequest> queue = new ArrayBlockingQueue<>(MESSAGES_NUMBER);
    
    private ApplicationConfig(){
    }
    
    @PostConstruct
    private static void init() {
    	startProducer();
    	startConsumer();
    	logger.info("Producer and cosumer threads were started!");
    }
    
    public static synchronized ApplicationConfig getInstance(){
        if(instance == null){
            instance = new ApplicationConfig();
//            init();
        }
        return instance;
    }

	private static void startProducer() {
		final MessageProducer<MessageRequest> messageProducer = new MessageProducer<>(queue);
		final Supplier<MessageRequest> supplier = () -> redisService.getMessage();
		new Thread(() -> {
			for (int i = 0; i < MESSAGES_NUMBER; i++) {
				messageProducer.produce(supplier);
			}
		}).start();
	}
    
    private static void startConsumer() {
        final MessageConsumer<MessageRequest> messageConsumer = new MessageConsumer<>(queue);
        final Consumer<MessageRequest> consumer = (s) -> businessService.persistMessage(s);
        
        new Thread(() -> {
            for (int i = 0; i < MESSAGES_NUMBER; i++) {
            	messageConsumer.consume(consumer);
            }
        }).start();
    }
}
