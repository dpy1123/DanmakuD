package com.dd.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dd.danmaku.resource.bean.Video;
import com.mongodb.DBCollection;
import com.mongodb.gridfs.GridFSFile;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:config/spring/applicationContext.xml"})  
public class JaeMongoTest {
	@Resource
	MongoTemplate mongoTemplate;
	@Resource
	GridFsTemplate gridFsTemplate;
	 @Test 
	public void main() throws Exception {
		System.out.println("mongo test");
//		Video v = new Video("1st");
//		mongoTemplate.insert(v);
//		
//		mongoTemplate.updateFirst(new Query(Criteria.where("name").is("1st")),
//				new Update().set("status", "change"), Video.class);
		
		System.out.println("gridfs test");
//		FileInputStream ins = new FileInputStream(new File("C:\\Users\\dd\\Downloads\\test.mp4"));
//		GridFSFile gfile = gridFsTemplate.store(ins, "test.mp4");
		System.out.println(gridFsTemplate.getResource("test.mp4").contentLength())
		;
		FileOutputStream out = new FileOutputStream(new File("C:\\Users\\dd\\Downloads\\test000.mp4"));
		downloadData(gridFsTemplate.getResource("test.mp4").getInputStream(), out);
		
	}
	 public void downloadData(InputStream in, OutputStream out) throws IOException {
			byte buffer[] = new byte[2048];
			int len = buffer.length;
			while (true) {
				try {
					len = in.read(buffer);
					if (len == -1)
						break;
					out.write(buffer, 0, len);
				} catch (IOException e) {
//					e.printStackTrace();
					len = -1;
					break;
				}
				out.flush();
			}
		}
}
