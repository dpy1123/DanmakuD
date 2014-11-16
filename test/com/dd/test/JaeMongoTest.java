package com.dd.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:config/spring/applicationContext.xml"})  
public class JaeMongoTest {
	
	 @Test 
	public void main() {
		System.out.println("找不到或无法加载主类");
	}
}
