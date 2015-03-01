package com.dd.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

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
import com.jcloud.jss.JingdongStorageService;
import com.jcloud.jss.domain.Bucket;
import com.jcloud.jss.http.Method;
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
//		FileInputStream ins = new FileInputStream(new File("C:\\Users\\DD\\Downloads\\test.mp4"));
//		GridFSFile gfile = gridFsTemplate.store(ins, "test.mp4");
//		System.out.println(gridFsTemplate.getResource("test.mp4").contentLength());
		
//		FileOutputStream out = new FileOutputStream(new File("C:\\Users\\dd\\Downloads\\test000.mp4"));
//		downloadData(gridFsTemplate.getResource("test.mp4").getInputStream(), out);
		
		System.out.println("jss test");
//		JingdongStorageService jss = new JingdongStorageService("6c9d23300af64f468899becb3da4a234","a783af9daa984e86938de67bdcb82ebf21PZvDVZ");
//		List<Bucket> buckets = jss.listBucket();
//		for (Bucket bucket : buckets) {
//			System.out.println(bucket.getName()+" "+bucket.getLocation());
//		}
		
//		File file = new File("C:\\Users\\DD\\Downloads\\test.mp4");
//		FileInputStream ins = new FileInputStream(file);
//		String md5 = jss.bucket("danmakufs").object("test.mp4").entity(file.length(), ins).put();
//		System.out.println(md5);
		
//		String url = jss.bucket("danmakufs").object("test.mp4").generatePresignedUrl(3600, Method.GET).toString();
//		System.out.println(url);
//		jss.destroy();
		
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

	 @Test
	 public void cmdtest() {
		 String s = null;
			try {
				ProcessBuilder builder = new ProcessBuilder();
				builder.command("java", "-version");
				builder.redirectErrorStream(true);
				Process process = builder.start();
				InputStream in = process.getInputStream();
				byte[] bs = new byte[1024];
				while ((in.read(bs)) != -1) {//正在转换,输出cmd状态
					s += new String(bs);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(s);
	}
}
