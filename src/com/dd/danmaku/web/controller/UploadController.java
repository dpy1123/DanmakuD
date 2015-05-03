package com.dd.danmaku.web.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.dd.danmaku.common.utils.StringUtils;
import com.dd.danmaku.jms.JMSMessage;
import com.dd.danmaku.jms.JMSSender;
import com.dd.danmaku.resource.bean.Category;
import com.dd.danmaku.resource.bean.Resource;
import com.dd.danmaku.resource.service.CategoryService;
import com.dd.danmaku.resource.service.ResourceService;


/**
 * 处理资源上传
 * @author dd
 * @version 1.0 [2015.01.27]
 */
@Controller
public class UploadController {

	Logger logger = Logger.getLogger(UploadController.class);
	
	@javax.annotation.Resource
	ResourceService resourceService;
	@javax.annotation.Resource
	CategoryService categoryService;
	@javax.annotation.Resource
	JMSSender jmsSender;
	
	/**
	 * 处理上传前的准备工作
	 * @return 上传页面
	 */
	@RequestMapping("preUpload.do")
	public ModelAndView upload(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("upload");//设置跳转页面
		
		//得到页面上“隶属栏目”下拉列表的内容
		LinkedHashMap<Category, List<Category>> categories = categoryService.getAllCategories();
		
		//添加传到前台的参数
		mv.addObject("categories", categories);//指定页面上“隶属栏目”下拉列表的内容
		return mv;
		
	}
	
	/**
	 * 处理上传表单的提交
	 * @return 上传结果
	 */
	@RequestMapping(value = "uploadResource.do", method = { RequestMethod.POST })
	public ModelAndView onUpload(MultipartHttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:home.do");//设置跳转页面
		String title = request.getParameter("title");
		String tag = request.getParameter("tag");
		String category = request.getParameter("category");
		String type = request.getParameter("type");
		String description = request.getParameter("description");
		String source = request.getParameter("source");
		String img_name = request.getParameter("img_name");
		String[] videoIds = request.getParameterValues("videoId");
		
		//中文逗号换成英文逗号
		title = title.replaceAll("，", ",");
		tag = tag.replaceAll("，", ",");
		
		//处理资源标题和分p标题
		String mainTitle = null;
		HashMap<String, String> subTitles = null;
		if(title.contains(",")){
			String[] titles = title.split(",");
			mainTitle = titles[0];
			subTitles = new HashMap<String, String>();
			for (int i = 0; i < videoIds.length; i++) {
				if(i+1 < titles.length)
					subTitles.put(videoIds[i], titles[i+1]);
			}
		}else{
			mainTitle = title;
		}
		
		
		String categoryId = categoryService.getCategoryByName(category).getId();
		Resource resource = new Resource("system", mainTitle, description, Resource.IN_USING, "copy".equals(type)?false:true);
		resource.setCategories(Arrays.asList(categoryId));
		resource.setSubTitles(subTitles);
		if(!StringUtils.isEmpty(img_name))
			resource.setPreviewImg(img_name);
		resource.setVideos(Arrays.asList(videoIds));
		resourceService.add(resource);
		
		//添加到消息队列进行视频格式转换
		for (String videoId : videoIds) {
			HashMap<String, Object> content = new HashMap<String, Object>();
			content.put("convertTimes", 1);
			content.put("videoId", videoId);
			content.put("resourceId", resource.getId());
			JMSMessage msg = new JMSMessage(JMSMessage.ACTION_VIDEO_CONVERT, content);
			jmsSender.sendMessage(msg);
		}
		
		return mv;
	}
}
