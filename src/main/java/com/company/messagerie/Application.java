package com.company.messagerie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource( { "classpath:applicationContext.xml" })
public class Application {
	
    public static void main(String[] args) {
		
		try
		{
		    SpringApplication.run(Application.class, args);
		}
		catch(Throwable th)
		{
			th.printStackTrace();
		}
    }
/*
    @Bean
    public CommandLineRunner run(ApplicationContext appContext) {
        return args -> {

            String[] beans = appContext.getBeanDefinitionNames();
            Arrays.stream(beans).sorted().forEach(System.out::println);

        };
    }*/
}
