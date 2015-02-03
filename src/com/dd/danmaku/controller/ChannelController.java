package com.dd.danmaku.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dd.danmaku.resource.bean.Category;
import com.dd.danmaku.resource.bean.Resource;
import com.dd.danmaku.resource.service.CategoryService;
import com.dd.danmaku.resource.service.ResourceService;



@Controller
public class ChannelController {
	
	@javax.annotation.Resource
	private ResourceService resourceService = null;
	@javax.annotation.Resource
	private CategoryService categoryService = null;
	
	/**
	 * 得到主页各分类频道的展示数据
	 * @return 主页
	 */
	@RequestMapping("home.do")
	public ModelAndView list(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home");//设置跳转页面


		LinkedHashMap<Category, List<Category>> categories = categoryService.getAllCategories();
		mv.addObject("categories", categories);

		//准备Categories中的每个子分类的内容
		LinkedHashMap<Category, List<Resource>> resources = new LinkedHashMap<Category, List<Resource>>();
		for (Category mainCategory : categories.keySet()) {
			List<Category> subCategories = categories.get(mainCategory);
			
			String cates = "" ;
			for (int i = 0; i < subCategories.size(); i++) {
				cates += "'"+subCategories.get(i).getId()+"'";
				if(i!=subCategories.size()-1)
					cates += ",";
			}
			String query = "{ status: '%1$s', categories : {$in:[ %2$s ]} }";
			LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>(); 
			orderBy.put("score", "DESC");
			List<Resource> subResources = resourceService.getResourceDao().getResultList(Resource.class, query, 0, 10, orderBy, Resource.IN_USING, cates);
			resources.put(mainCategory, subResources);
		}

		mv.addObject("resources", resources);
		
		//准备右侧channel热门视频的内容
//		ResourceCriteria hotCriteria = new ResourceCriteria();
//		hotCriteria.setStatus("上线");
//		hotCriteria.setIsSp("0");
//		hotCriteria.setClickCountOrder("DESC");
//		hotCriteria.setUploadDTMFrom(DateUtils.nDaysAfterNowDate(-7));
//		hotCriteria.setUploadDTMTo(new Date());
//		hotCriteria.setStartSize(0);
//		hotCriteria.setPageSize(5);
//		List<Resource> hotResources = resourceFacadeRemoteInter.listByCriteria(hotCriteria);
//		for (int i = 0; i < hotResources.size(); i++) {
//			hotResources.get(i).setPreviewImgPath(Constants.FILE_SYSTEM_URL + hotResources.get(i).getPreviewImgPath());
//		}
//		mv.addObject("hotResources", hotResources);
		
		//准备右侧channel新增视频的内容
//		ResourceCriteria newCriteria = new ResourceCriteria();
//		newCriteria.setStatus("上线");
//		newCriteria.setIsSp("0");
//		newCriteria.setUploadDTMOrder("DESC");
//		newCriteria.setStartSize(0);
//		newCriteria.setPageSize(5);
//		List<Resource> newResources = resourceFacadeRemoteInter.listByCriteria(newCriteria);
//		for (int i = 0; i < newResources.size(); i++) {
//			newResources.get(i).setPreviewImgPath(Constants.FILE_SYSTEM_URL + newResources.get(i).getPreviewImgPath());
//		}
//		mv.addObject("newResources", newResources);
		
		return mv;
	}
	
}
