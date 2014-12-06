package com.dd.danmaku.server;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFSFile;

/**
 * 用于上传文件到GridFS
 * @author dd
 * @version 1.0 [2014.12.6]
 */
@Controller
public class FileUploadServ {
	@Resource
	GridFsTemplate gridFsTemplate;
	
	@RequestMapping("upload.do")
	public void uploadFile(@RequestParam MultipartFile[] files) {
		System.out.println("=================uploadFile=======================");  
		for(MultipartFile myfile : files){  
            if(!myfile.isEmpty()){  
                System.out.println("文件长度: " + myfile.getSize());  
                System.out.println("文件类型: " + myfile.getContentType());  
//                System.out.println("文件名称: " + myfile.getName());  
                System.out.println("文件原名: " + myfile.getOriginalFilename());  
                
				try {
					GridFSFile gfile = gridFsTemplate.store(myfile.getInputStream(), myfile.getOriginalFilename());
					System.out.println(gfile.getLength());
				} catch (IOException e) {
					e.printStackTrace();
				}
            }  
        }  
	}
}
