package com.dd.danmaku.common.dao;

import com.duapp.ddplayer.bean.Danmu;
import com.duapp.ddplayer.service.DanmuService;
import com.duapp.ddplayer.utils.JSONUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DanmuController
{
    @Resource
  private DanmuService danmuService;
  public static Logger logger = Logger.getLogger(DanmuController.class);
    
  public DanmuService getDanmuService()
  {
    return this.danmuService;
  }

  public void setDanmuService(DanmuService danmuService) {
    this.danmuService = danmuService;
  }
  @RequestMapping({"getDanmus.do"})
  public void listByVideoId(HttpServletResponse response, String videoId) {
    logger.info("dd----call: getDanmus.do ");
    List<Danmu> resources = this.danmuService.listByVideoId(videoId);

    String result = JSONUtils.toJSONString(resources);
    response.setCharacterEncoding("utf-8");
    PrintWriter out = null;
    try {
      out = response.getWriter();
      out.write(result);
      out.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      out.close();
    }
  }
  @RequestMapping({"sendDanmu.do"})
  public void save(HttpServletResponse response, @RequestBody Danmu danmu) {//danmu前台是json格式post过来的，要加@RequestBody以解析

      try {
        if("Custom".equals(danmu.getStyle())){
          danmu.setClazz(URLDecoder.decode(danmu.getClazz(), "UTF-8"));
          danmu.setParam(URLDecoder.decode(danmu.getParam(), "UTF-8"));
        }else{
          danmu.setText(URLDecoder.decode(danmu.getText(), "UTF-8"));//danmu的Text在前台做过encodeURI
        }
      } catch(UnsupportedEncodingException e1) {
        e1.printStackTrace();
      }
	  this.danmuService.save(danmu);
	  
	  String result = JSONUtils.toJSONString(danmu);
	  
	  logger.info("dd----call: sendDanmu.do \n result: "+result);
	    response.setCharacterEncoding("utf-8");
	    PrintWriter out = null;
	    try {
	      out = response.getWriter();
	      out.write(result);
	      out.flush();
	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	      out.close();
	    }
  }
}