package com.company.messagerie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import com.company.messagerie.config.ApplicationConfig;
import com.company.messagerie.config.CassandraConnector;
import com.datastax.driver.core.Session;

@SpringBootApplication
@ImportResource( { "classpath:applicationContext.xml" })
public class Application {
	
    final private Boolean cassandraEnabled = true;
	@Value("${spring.data.cassandra.contact-points}")
	private String cassandraHosts;
	@Value("${spring.data.cassandra.keyspace-name}")
	private String cassandraKeyspace;
    
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
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Start Producer/Consumer
		ApplicationConfig.getInstance();
    }
    
    @Bean
    public Session getCassandra() {
        if (!cassandraEnabled) {
            return null;
        }
        
		CassandraConnector client = new CassandraConnector();
        client.connect(cassandraHosts, null, cassandraKeyspace);
        return client.getSession();
    }
}
