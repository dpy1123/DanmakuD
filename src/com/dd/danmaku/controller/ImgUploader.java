package com.dd.danmaku.controller;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.gridfs.GridFsCriteria.whereFilename;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.dd.danmaku.resource.bean.Category;
import com.dd.danmaku.resource.bean.Video;
import com.dd.danmaku.resource.service.CategoryService;
import com.dd.danmaku.resource.service.ResourceService;
import com.dd.danmaku.resource.service.VideoService;
import com.dd.danmaku.server.FileUploadServ;
import com.dd.danmaku.utils.ImageUtils;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;


@Controller
public class ImgUploader {

	Logger logger= Logger.getLogger(ImgUploader.class);
	
	@Resource
	GridFsTemplate gridFsTemplate;
	
	@RequestMapping(value = "uploadImg.do", method = { RequestMethod.POST })
	public @ResponseBody Map<String, Object> uploadImg(MultipartHttpServletRequest request) {
		logger.info("=================uploadFile=======================");  
		MultipartFile myfile;
        Iterator<String> itr = request.getFileNames();

        Map<String, Object> fileInfo = new HashMap<String, Object>();
		while (itr.hasNext()) {
			myfile = request.getFile(itr.next());
            if(!myfile.isEmpty()){  
//                System.out.println("文件长度: " + myfile.getSize());  
//                System.out.println("文件类型: " + myfile.getContentType());  
//                System.out.println("文件名称: " + myfile.getName());  
//                System.out.println("文件原名: " + myfile.getOriginalFilename());  
                
                logger.info("文件原名: " + myfile.getOriginalFilename());
                String filename = UUID.randomUUID().toString();
                logger.info("存入fs文件名: " + filename);
                
                //返回前台的信息
				fileInfo.put("name", myfile.getOriginalFilename());
				fileInfo.put("size", myfile.getSize());
				
				//保存文件
				try {
					GridFSFile gfile = gridFsTemplate.store(myfile.getInputStream(), filename, myfile.getContentType());
					// note: to set aliases, call put( "aliases" , List<String> )
					gfile.put("aliases", myfile.getOriginalFilename());//在别名中存储文件原名
					gfile.save();
					logger.info("文件已保存至fs，文件大小："+gfile.getLength());

					GridFsResource resource = gridFsTemplate.getResource(filename);
					HashMap<String, Object> imgInfo = ImageUtils.getInfo(resource.getInputStream());
					
					fileInfo.put("status", "success");
					fileInfo.put("url", request.getContextPath()+"/getVideo.do?filename="+filename);
					fileInfo.put("width", imgInfo.get("width"));
					fileInfo.put("height", imgInfo.get("height"));
					
				} catch (IOException e) {
					e.printStackTrace();
					fileInfo.put("status", "error");
					fileInfo.put("message", "文件保存至fs失败");
				}
				
            }  
        }
		
		return fileInfo;  
	}
	
	
	
	@RequestMapping(value = "cropImg.do", method = { RequestMethod.POST })
	public @ResponseBody Map<String, Object> cropImg(MultipartHttpServletRequest request) {
		logger.info("=================uploadFile=======================");  
//		imgUrl 		// your image path (the one we recieved after successfull upload)
//		/DanmakuD/getVideo.do?filename=eb0a15c5-7764-4706-b232-c1b861a18772

//		imgInitW  	// your image original width (the one we recieved after upload)
//		imgInitH 	// your image original height (the one we recieved after upload)
//
//		imgW 		// your new scaled image width
//		imgH 		// your new scaled image height
//
//		imgX1 		// top left corner of the cropped image in relation to scaled image
//		imgY1 		// top left corner of the cropped image in relation to scaled image
//		cropW 		// cropped image width
//		cropH 		// cropped image height
		
//		rotation
		String imgUrl = request.getParameter("imgUrl");
		String filename = imgUrl.substring(imgUrl.indexOf("=")+1);
		
		String imgW = request.getParameter("imgW");
		String imgH = request.getParameter("imgH");
		
		String imgX1 = request.getParameter("imgX1");
		String imgY1 = request.getParameter("imgY1");
		String cropW = request.getParameter("cropW");
		String cropH = request.getParameter("cropH");
		
		GridFSDBFile file = gridFsTemplate.findOne(query(whereFilename().is(filename)));
		GridFsResource resource = new GridFsResource(file);
		
		Map<String, Object> fileInfo = new HashMap<String, Object>();
		try {
			byte[] temp = ImageUtils.scale(resource.getInputStream(), (int)Float.parseFloat(imgH), (int)Float.parseFloat(imgW), false);
			ByteArrayInputStream in = new ByteArrayInputStream(temp); 
			
			temp = ImageUtils.cut(in, Integer.parseInt(imgX1), Integer.parseInt(imgY1), Integer.parseInt(cropW), Integer.parseInt(cropH));
			in = new ByteArrayInputStream(temp); 
					
			//保存文件
			String newfile = UUID.randomUUID().toString();
			GridFSFile gfile = gridFsTemplate.store(in, newfile, resource.getContentType());
			// note: to set aliases, call put( "aliases" , List<String> )
			gfile.put("aliases", "CROP_"+file.get("aliases"));//在别名中存储文件原名
			gfile.save();
			
			fileInfo.put("status", "success");
			fileInfo.put("url", request.getContextPath()+"/getVideo.do?filename="+newfile);
			
		} catch (IOException e) {
			e.printStackTrace();
			fileInfo.put("status", "error");
			fileInfo.put("message", "文件保存至fs失败");
		}
				
		
		return fileInfo;  
	}
	

}
