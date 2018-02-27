package com.company.messagerie;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

@SpringBootApplication
@ImportResource( { "classpath:applicationContext.xml" })
public class Application {
	
    final private Boolean cassandraEnabled = true;
    final private String cassandraHosts = "192.168.0.151";
    final private String cassandraKeyspace = "mykeyspace";
    
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
    public static void main(String[] args) {
    	logger.info("Starting application");
    	
		try
		{
		    SpringApplication.run(Application.class, args);
		}
		catch(Throwable th)
		{
			logger.error("Error occured at application startup!");
			th.printStackTrace();
		}
		
		// Start Producer/Consumer
		ApplicationConfig.getInstance();
    }
    
    @Bean
    public Session getCassandra() {
        if (!cassandraEnabled) {
            return null;
        }
        Cluster cluster = Cluster.builder().addContactPoints(cassandraHosts).build();
        Session session = cluster.connect(cassandraKeyspace);
        return session;
    }
}
