package com.dd.danmaku.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.dd.danmaku.resource.bean.Resource;
import com.dd.danmaku.resource.service.ResourceService;


/**
 * 处理资源上传
 * @author dd
 * @version 1.0 [2015.01.27]
 */
@Controller
public class UploadResource {

	Logger logger= Logger.getLogger(UploadResource.class);
	
	@javax.annotation.Resource
	ResourceService resourceService;
	
	/**
	 * 处理上传表单的提交
	 * @return 上传结果
	 */
	@RequestMapping(value = "uploadResource.do", method = { RequestMethod.POST })
	public ModelAndView onUpload(MultipartHttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home.html");//设置跳转页面
		String title = request.getParameter("title");
		String tag = request.getParameter("tag");
		String category = request.getParameter("category");
		String type = request.getParameter("type");
		String description = request.getParameter("description");
		String source = request.getParameter("source");
		
		
		
		String[] videoIds = request.getParameterValues("videoId");
		if(videoIds == null){//如果videoIds为空 表示用户没有上传文件
			
		}
		
		Resource resource = new Resource("system", title, description, Resource.IN_USING, "copy".equals(type)?false:true);
		resource.setCategories(Arrays.asList(category));
		List<String> videos = new ArrayList<String>();
		for (String videoId : videoIds) {
			videos.add(videoId);
		}
		resource.setVideos(Arrays.asList(videoIds));
		resourceService.add(resource);
		
		return mv;
	}
}
