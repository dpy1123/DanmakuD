package com.dd.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dd.danmaku.fileconvert.FileConvertService;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:config/spring/applicationContext.xml"})  
public class ConvertTest {
	@Resource
	protected FileConvertService fileConvertService;
	@Test
	public void convert() {
		try {
			fileConvertService.generatePreviewPicture("C:\\Users\\dd\\Downloads\\0.flv", "C:\\Users\\dd\\Downloads\\0.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
