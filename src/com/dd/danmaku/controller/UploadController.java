package com.dd.danmaku.controller;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

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

	Logger logger= Logger.getLogger(UploadController.class);
	
	@javax.annotation.Resource
	ResourceService resourceService;
	@javax.annotation.Resource
	CategoryService categoryService;
	
	/**
	 * 处理上传前的准备工作
	 * @return 上传页面
	 */
	@RequestMapping("preUpload.do")
	public ModelAndView upload(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("upload");//设置跳转页面
		
		//得到页面要上传到的服务器的ws地址
//		FileManagerFacadeRemoteInter fileManagerFacade = (FileManagerFacadeRemoteInter) EjbInvoke.ejbLookupRemote("127.0.0.1:1099", "FileManagerFacadeImpl");
//		FileManager fileManager = fileManagerFacade.getAvalible();
//		String wsUrl = fileManager.getWsUrl();
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
		mv.setViewName("home");//设置跳转页面
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
		resource.setVideos(Arrays.asList(videoIds));
		resourceService.add(resource);
		
		return mv;
	}
}
