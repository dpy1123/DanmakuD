package com.dd.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:config/spring/applicationContext.xml"})  
public class JaeMongoTest {
	@Resource
	MongoTemplate mongoTemplate;
	 @Test 
	public void main() {
		System.out.println("找不到或无法加载主类");
	}
}
