package com.dd.danmaku.web.controller;

import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dd.danmaku.common.utils.StringUtils;
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
	public ModelAndView show(HttpServletRequest request, String resourceId){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("view");//设置跳转页面
		
		Resource resource = resourceService.getById(resourceId);
		
		//获取该Resource所属的二级分类
		String subCategoryId = resource.getCategories().get(0);
		Category subCategory = categoryService.getCategoryById(subCategoryId);
		
		//获取该Resource所属的一级分类
		Category mainCategory = categoryService.getParentCategory(subCategoryId);
		
		//处理p参数
		String p = request.getParameter("p");
		int curP = 0;//默认取第一个video
		if(!StringUtils.isEmpty(p) && resource.getVideos().size() > 1){
			//有p参数，且视频数确实>1
			curP = Integer.parseInt(p) - 1;
		}
		//获取当前页视频
		Video video = videoService.getById(resource.getVideos().get(curP));
		String videoUrl = request.getContextPath()+"/getFsFile.do?filename=" + video.getConvertedFsFileName();
		
		//准备分p列表(如果是多p的情况)
		if(resource.getVideos().size() > 1){
			LinkedList<HashMap<String, String>> videoList = new LinkedList<HashMap<String, String>>();
			for (int i = 0; i < resource.getVideos().size(); i++) {
				HashMap<String, String> item = new HashMap<String, String>();
				//设置分p地址 vUrl
				String vId = resource.getVideos().get(i);
				item.put("vUrl", request.getContextPath()+"/view.do?resourceId=" + resourceId + "&p=" + (i+1));
				//设置分p名 vName
				String subTitle = resource.getSubTitles().get(vId);
				if(!StringUtils.isEmpty(subTitle))
					item.put("vName", (i+1)+"、" + subTitle);
				else
					item.put("vName", (i+1)+"、" + (i+1)+"P");
				//设置显示类型
				if(i == curP)
					item.put("display", "current");
				else if(i == curP-1 || i == curP+1)
					item.put("display", "show");
				else
					item.put("display", "hidden");
				videoList.add(item);
				
			}
			mv.addObject("videoList", videoList);
		}
				
		mv.addObject("mainCategory", mainCategory);
		mv.addObject("subCategory", subCategory);
		mv.addObject("resource", resource);
		mv.addObject("videoUrl", videoUrl);
		mv.addObject("videoId", video.getId());
//		mv.addObject("danmuWsUrl", Constants.DANMU_WS_URL);
		
		//更新点击数
		resourceService.updateCount(resource.getId(), "clickCount", 1);
				
		return mv;
	}
	

}
