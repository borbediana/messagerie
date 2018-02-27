package com.company.test.messagerie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.messagerie.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class ApplicationTest {
	
	@Test
	public void contextLoads() {
	}
}
