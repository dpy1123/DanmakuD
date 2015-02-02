package com.dd.danmaku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dd.danmaku.resource.bean.Category;
import com.dd.danmaku.resource.bean.Resource;
import com.dd.danmaku.resource.bean.Video;
import com.dd.danmaku.resource.service.CategoryService;
import com.dd.danmaku.resource.service.ResourceService;
import com.dd.danmaku.resource.service.VideoService;


@Controller
public class ViewController {

	@javax.annotation.Resource
	ResourceService resourceService;
	@javax.annotation.Resource
	CategoryService categoryService;
	@javax.annotation.Resource
	VideoService videoService;
	
	/**
	 * 得到各分类频道的展示数据
	 * @return 分类频道页面
	 */
	@RequestMapping("view.do")
	public ModelAndView show(String resourceId){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("view");//设置跳转页面
		
		Resource resource = resourceService.getById(resourceId);
		
		//获取该Resource所属的二级分类
		String subCategoryId = resource.getCategories().get(0);
		Category subCategory = categoryService.getCategoryById(subCategoryId);
		
		//获取该Resource所属的一级分类
		Category mainCategory = categoryService.getParentCategory(subCategoryId);
		
		
		Video video = videoService.getById(resource.getVideos().get(0));
		String videoUrl = "getVideo.do?filename=" + video.getFsFileName();
		
		mv.addObject("mainCategory", mainCategory);
		mv.addObject("subCategory", subCategory);
		mv.addObject("resource", resource);
		mv.addObject("videoUrl", videoUrl);
//		mv.addObject("danmuWsUrl", Constants.DANMU_WS_URL);
		
		//更新点击数
		resourceService.updateCount(resource.getId(), "clickCount", 1);
				
		return mv;
	}
	

}
