package com.dd.danmaku.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
	public ModelAndView show(String videoId){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("view");//设置跳转页面
		
		ResourceCriteria criteria = new ResourceCriteria();
		criteria.setVideoId(videoId);
		Resource resource = this.getResourceFacade().listByCriteria(criteria).get(0);
		
		String subCategory = null;//获取该Resource所属的二级分类
		String subCategoryDisplayNme = null;
		for (Iterator<Category> iterator = resource.getCategories().iterator(); iterator.hasNext();) {
			Category category = iterator.next();
			subCategory = category.getName();
			subCategoryDisplayNme = category.getDisplayName();
		}
		//获取该Resource所属的一级分类
		String mainCategory = null;
		String mainCategoryDisplayNme = null;
		HashMap<Category, List<Category>> allCategries = this.getCategoryFacade().getAllCategories();
		for (Category key : allCategries.keySet()) {
			for(Category value : allCategries.get(key)){
				if (value.getName().equalsIgnoreCase(subCategory)) {
					mainCategory = key.getName();
					mainCategoryDisplayNme = key.getDisplayName();
					break;
				}
			}
		}
		
		Video video = videoService.getById(videoId);
		String videoUrl = "getVideo.do?filename=" + video.getFsFileName();
		
		mv.addObject("mainCategory", mainCategory);
		mv.addObject("mainCategoryDisplayName", mainCategoryDisplayNme);
		mv.addObject("subCategory", subCategory);
		mv.addObject("subCategoryDisplayName", subCategoryDisplayNme);
		mv.addObject("resource", resource);
		mv.addObject("videoUrl", videoUrl);
//		mv.addObject("danmuWsUrl", Constants.DANMU_WS_URL);
		
		//更新点击数
		resourceService.updateCount(resource.getId(), "clickCount", 1);
				
		return mv;
	}
	

}
