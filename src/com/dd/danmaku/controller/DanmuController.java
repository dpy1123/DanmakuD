package com.dd.danmaku.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dd.danmaku.resource.bean.Danmu;
import com.dd.danmaku.resource.service.DanmuService;
import com.dd.danmaku.resource.service.ResourceService;



/**
 * 处理弹幕相关请求
 * @author dd
 * @version 1.0 [2015.02.02]
 */
@Controller
public class DanmuController {
	
	@Resource
	DanmuService danmuService;
	@Resource
	ResourceService resourceService;
	
	@RequestMapping("getDanmaku.do")
	public @ResponseBody List<Danmu> getDanmaku(String vid){
		List<Danmu> result = null;

		try{
			result = danmuService.list(vid, 0, 2000);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "sendDanmaku.do", method = { RequestMethod.POST })
	public @ResponseBody String saveDanmaku(@RequestBody Danmu danmu){//danmu前台是json格式post过来的，要加@RequestBody以解析
		try {
			if ("Custom".equals(danmu.getStyle())) {
				danmu.setClazz(URLDecoder.decode(danmu.getClazz(), "UTF-8"));
				danmu.setParam(URLDecoder.decode(danmu.getParam(), "UTF-8"));
			} else {
				danmu.setText(URLDecoder.decode(danmu.getText(), "UTF-8"));// danmu的Text在前台做过encodeURI
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		danmuService.add(danmu);
		
		
		//更新弹幕数
		try {
			resourceService.updateCount(resourceService.getByVideoId(danmu.getVideoId()).getId(), "danmuCount", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ok";
	}
}
