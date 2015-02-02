package com.dd.danmaku.controller;

import java.net.URLDecoder;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dd.danmaku.resource.bean.Danmu;
import com.dd.danmaku.resource.service.DanmuService;
import common.tools.EjbInvoke;

import dd.tv.danmu.ejb.remote.DanmuFacadeRemoteInter;
import dd.tv.web.servlet.PrintWriter;
import dd.tv.web.servlet.String;



/**
 * 处理弹幕相关请求
 * @author dd
 * @version 1.0 [2015.02.02]
 */
@Controller
public class DanmuController {
	
	@Resource
	DanmuService danmuService;
	
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
	public @ResponseBody String sendDanmaku(HttpServletRequest request){
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String style = request.getParameter("style");
		String effect = request.getParameter("effect");
		String text = request.getParameter("text");
		String sendTime = request.getParameter("sendTime");
		//注意看下原来的项目 danmu实体应该有color属性
		System.out.println("start="+start);
		System.out.println("end="+end);
		System.out.println("style="+style);
		System.out.println("effect="+effect);
		System.out.println("text="+URLDecoder.decode(text, "utf-8"));
		System.out.println("sendTime="+sendTime);
		
		Danmu danmu = new Danmu();
		danmu.setStart(sec2tc(Float.parseFloat(start)));
		danmu.setEnd(sec2tc(Float.parseFloat(end)));
		danmu.setStyle(style);
		danmu.setLevel("0");
		danmu.setText(text);
		danmu.setEffect(effect);
		danmu.setVideoId("video001");
		danmu.setUserId("user002");
		danmu.setSendTime(sendTime);
		DanmuFacadeRemoteInter danmuFacadeRemoteInter = (DanmuFacadeRemoteInter) EjbInvoke.ejbLookupRemote("127.0.0.1:1099", "DanmuFacadeImpl");
		danmuFacadeRemoteInter.add(danmu);
		
		PrintWriter out = response.getWriter();
		out.write("ok");
	}
}
