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

import com.dd.danmaku.resource.bean.Video;
import com.dd.danmaku.resource.service.VideoService;
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
	
	@Resource
	VideoService videoService;
	
	@RequestMapping(value = "uploadVideo.do", method = { RequestMethod.POST })
	public @ResponseBody Map<String, Object> uploadFile(MultipartHttpServletRequest request) {
		logger.info("=================uploadFile=======================");  
		MultipartFile myfile;
        Iterator<String> itr = request.getFileNames();

        List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
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
            	Map<String, Object> fileInfo = new HashMap<String, Object>();
				fileInfo.put("name", myfile.getOriginalFilename());
				fileInfo.put("size", myfile.getSize());
				
				//保存文件
				try {
					GridFSFile gfile = gridFsTemplate.store(myfile.getInputStream(), filename, myfile.getContentType());
					// note: to set aliases, call put( "aliases" , List<String> )
					gfile.put("aliases", myfile.getOriginalFilename());//在别名中存储文件原名
					gfile.save();
					logger.info("文件已保存至fs，文件大小："+gfile.getLength());
					
					fileInfo.put("url", "get?");
					fileInfo.put("deleteUrl", "../deleteVideo.do?filename=" + filename);
					fileInfo.put("deleteType", "DELETE");
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("文件保存至fs失败："+e.getStackTrace());
					fileInfo.put("error", "文件保存至fs失败");
				}
				
				
				//文件信息入库
				Video video = new Video(myfile.getOriginalFilename(), filename, myfile.getSize());
				try {
					String vId = videoService.add(video);
					logger.info("文件信息入库，id：" + vId);
					fileInfo.put("id", vId);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("文件信息入库失败："+e.getStackTrace());
					fileInfo.put("error", "文件信息入库失败");
				}
				
				list.add(fileInfo);
            }  
        }
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("files", list);
		
		return result;  
	}
	
	@RequestMapping(value = "deleteVideo.do", method = RequestMethod.DELETE)
    public @ResponseBody Map<String, Object> delete(String filename) {
		Map<String, Object> success = new HashMap<String, Object>();
		logger.info("从fs删除文件：" + filename);
		
		gridFsTemplate.delete(query(whereFilename().is(filename)));
		
        Map<String, Object> results = new HashMap<String, Object>();
        success.put(filename, true);
        results.put("files", success);
        
        try {
        	Video video = videoService.getByFileName(filename);
        	videoService.del(video.getId());
        	logger.info("删除文件信息，id：" + video.getId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("文件信息删除失败："+e.getStackTrace());
		}
        return results;
    }
	
	@RequestMapping(value = "test.do")
	public @ResponseBody String test(){
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
			throw new RuntimeException("格式转换中发生异常: "+e.getMessage());
		}
		return s;
	}
}
