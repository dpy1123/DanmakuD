package com.dd.danmaku.server;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.dd.danmaku.utils.ImageUtils;
import com.mongodb.gridfs.GridFSFile;

/**
 * 配合前台croppic控件，上传图片文件到GridFS
 * @author dd
 * @version 1.0 [2015.02.04]
 */
@Controller
public class ImgUploader {

	Logger logger = Logger.getLogger(ImgUploader.class);
	
	@Resource
	GridFsTemplate gridFsTemplate;
	
	String imgTempPath;//保存上传图片的临时路径
	
	public ImgUploader() {
		try {
			String classFileRealPath = URLDecoder.decode(this.getClass().getResource("").getPath(), "UTF-8");
			imgTempPath = classFileRealPath.substring(0, classFileRealPath.indexOf("WEB-INF")) + "temp_files/img/";//根路径
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "uploadImg.do", method = { RequestMethod.POST })
	public @ResponseBody Map<String, Object> uploadImg(MultipartHttpServletRequest request) {
		logger.info("=================uploadFile=======================");  
		MultipartFile myfile;
        Iterator<String> itr = request.getFileNames();

        Map<String, Object> fileInfo = new HashMap<String, Object>();
		while (itr.hasNext()) {
			myfile = request.getFile(itr.next());
            if(!myfile.isEmpty()){  
                
                logger.info("文件原名: " + myfile.getOriginalFilename());
                //TODO 防止多个用户上传同名文件相互影响，filename = userId + originalName;
                String filename = myfile.getOriginalFilename();
                
                //返回前台的信息
				fileInfo.put("name", myfile.getOriginalFilename());
				fileInfo.put("size", myfile.getSize());
				
				//保存文件
				try {
					//第一部上传的未裁剪的图片，先保存在临时目录下
					
//					GridFSFile gfile = gridFsTemplate.store(myfile.getInputStream(), filename, myfile.getContentType());
					// note: to set aliases, call put( "aliases" , List<String> )
//					gfile.put("aliases", myfile.getOriginalFilename());//在别名中存储文件原名
//					gfile.save();
//					logger.info("文件已保存至fs，文件大小："+gfile.getLength());
//					GridFsResource resource = gridFsTemplate.getResource(filename);
					
					//数据保存在本地临时文件	
					File file = null;
					if(!new File(imgTempPath).exists()){
						new File(imgTempPath).mkdirs();
					}
					file = new File(imgTempPath , filename);
					FileOutputStream out = new FileOutputStream(file);
					out.write(myfile.getBytes());
					out.close();
					FileInputStream ins = new FileInputStream(file);
					HashMap<String, Object> imgInfo = ImageUtils.getInfo(ins);
					ins.close();
					logger.info("文件已保存至"+imgTempPath+"，文件大小："+file.length());
					
					fileInfo.put("status", "success");
					fileInfo.put("url", request.getContextPath()+"/temp_files/img/"+filename);
					fileInfo.put("width", imgInfo.get("width"));
					fileInfo.put("height", imgInfo.get("height"));
					
				} catch (IOException e) {
					logger.error("图片保存失败", e);
					fileInfo.put("status", "error");
					fileInfo.put("message", "文件保存失败");
				}
				
            }  
        }
		
		return fileInfo;  
	}
	
	
	
	@RequestMapping(value = "cropImg.do", method = { RequestMethod.POST })
	public @ResponseBody Map<String, Object> cropImg(MultipartHttpServletRequest request) {
		logger.info("=================uploadFile=======================");  
//		imgUrl 		// your image path (the one we recieved after successfull upload)
//		/DanmakuD/getFsFile.do?filename=eb0a15c5-7764-4706-b232-c1b861a18772

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
		
//		GridFSDBFile file = gridFsTemplate.findOne(query(whereFilename().is(filename)));
//		GridFsResource resource = new GridFsResource(file);
		
		
		Map<String, Object> fileInfo = new HashMap<String, Object>();
		try {
			File file = new File(imgTempPath , filename);
			FileInputStream ins = new FileInputStream(file);
			
			byte[] temp = ImageUtils.scale(ins, (int)Float.parseFloat(imgH), (int)Float.parseFloat(imgW), false);
			ByteArrayInputStream in = new ByteArrayInputStream(temp); 
			
			temp = ImageUtils.cut(in, Integer.parseInt(imgX1), Integer.parseInt(imgY1), Integer.parseInt(cropW), Integer.parseInt(cropH));
			in = new ByteArrayInputStream(temp); 
					
			//保存文件
			String newfile = UUID.randomUUID().toString();
			GridFSFile gfile = gridFsTemplate.store(in, newfile, "image/jpeg");
			// note: to set aliases, call put( "aliases" , List<String> )
			gfile.put("aliases", "CROP_"+filename);//在别名中存储文件原名
			gfile.save();
			
			//删除原图
//			gridFsTemplate.delete(query(whereFilename().is(filename)));
			
			ins.close();
			//删除临时文件
			file.delete();
			
			fileInfo.put("status", "success");
			fileInfo.put("url", request.getContextPath()+"/getFsFile.do?filename="+newfile);
			fileInfo.put("img_name", newfile);
			
		} catch (IOException e) {
			logger.error("图片crop失败", e);
			fileInfo.put("status", "error");
			fileInfo.put("message", "文件保存至fs失败");
		}
        
		return fileInfo;  
	}

}
