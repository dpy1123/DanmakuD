package com.dd.danmaku.server;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.gridfs.GridFsCriteria.whereFilename;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;


/**
 * 用于上传文件到GridFS
 * @author dd
 * @version 1.0 [2014.12.6]
 */
@Controller
public class FileUploadServ {
	
	Logger logger= Logger.getLogger(FileUploadServ.class);
	
	@Resource
	GridFsTemplate gridFsTemplate;
	
	@RequestMapping(value = "upload.do", method = { RequestMethod.POST })
	public @ResponseBody Map<String, Object> uploadFile(MultipartHttpServletRequest request) {
		logger.info("=================uploadFile=======================");  
		Iterator<String> itr = request.getFileNames();
        MultipartFile myfile;
        
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
//		for(MultipartFile myfile : files){  
		 while (itr.hasNext()) {
			myfile = request.getFile(itr.next());
            if(!myfile.isEmpty()){  
                System.out.println("文件长度: " + myfile.getSize());  
                System.out.println("文件类型: " + myfile.getContentType());  
//                System.out.println("文件名称: " + myfile.getName());  
                System.out.println("文件原名: " + myfile.getOriginalFilename());  
                
                logger.info("文件原名: " + myfile.getOriginalFilename());
                
				try {
					GridFSFile gfile = gridFsTemplate.store(myfile.getInputStream(), myfile.getOriginalFilename());
					System.out.println(gfile.getLength());
				} catch (IOException e) {
					logger.error(e.getStackTrace());
					e.printStackTrace();
				}
				
				Map<String, Object> fileInfo = new HashMap<String, Object>();
				fileInfo.put("name", myfile.getOriginalFilename());
				fileInfo.put("size", myfile.getSize());
				fileInfo.put("url", "get?");
				fileInfo.put("deleteUrl", "../delete.do?filename="+myfile.getOriginalFilename());
				fileInfo.put("deleteType", "DELETE");
				list.add(fileInfo);
            }  
        }
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("files", list);
		return result;  
	}
	
	@RequestMapping(value = "delete.do", method = RequestMethod.DELETE)
    public @ResponseBody Map<String, Object> delete(String filename) {
		Map<String, Object> success = new HashMap<String, Object>();
		System.out.println(filename);
		
		gridFsTemplate.delete(query(whereFilename().is(filename)));
		
        Map<String, Object> results = new HashMap<String, Object>();
        success.put(filename, true);
        results.put("files", success);
        return results;
    }
	
	@RequestMapping(value = "test.do")
	public @ResponseBody String test(){
		String s = null;
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command("echo hello cmd");
			builder.redirectErrorStream(true);
			Process process = builder.start();
			InputStream in = process.getInputStream();
			byte[] bs = new byte[1024];
			while ((in.read(bs)) != -1) {//正在转换,输出cmd状态
				s += new String(bs);
			}
		} catch (Exception e) {
			throw new RuntimeException("格式转换中发生异常");
		}
		return s;
	}
}
