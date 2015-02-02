package com.dd.danmaku.resource.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dd.danmaku.resource.bean.Danmu;
import com.dd.danmaku.resource.dao.DanmuDao;
import com.dd.danmaku.resource.service.DanmuService;
import com.dd.danmaku.utils.StringUtils;


@Service("danmuService")
public class DanmuServiceImpl implements DanmuService {

	@Resource
	private DanmuDao danmuDao;
	
	public String add(Danmu danmu) {
		// 如果danmu为空，或者不能为空的属性为空，抛出空值异常;
		if (null == danmu || StringUtils.isEmpty(danmu.getVideoId())
				|| StringUtils.isEmpty(danmu.getUserId())
				|| StringUtils.isEmpty(danmu.getStart())
				|| StringUtils.isEmpty(danmu.getEnd())
				|| StringUtils.isEmpty(danmu.getStyle())
				|| StringUtils.isEmpty(danmu.getLevel())
				|| StringUtils.isEmpty(danmu.getText())
				|| StringUtils.isEmpty(danmu.getEffect())
				|| StringUtils.isEmpty(danmu.getSendTime())) {
			throw new RuntimeException("弹幕对象不合法");
		}

		// 如果返回的错误信息是未知的数据库异常，抛出 “添加弹幕失败” 异常
		try {
			danmuDao.save(danmu);
		} catch (Exception e) {
			throw new RuntimeException("未知的数据库异常，添加弹幕失败");
		}
		return danmu.getId();
	}

	public void del(String videoId, String userId) {
		// 检查videoId为空，则抛出查询条件空值异常
		if (StringUtils.isEmpty(videoId)) {
			throw new RuntimeException("videoId不能为空");
		}
		
		try {
			if(!StringUtils.isEmpty(userId)){//如果包含userId就用videoId和userId删除弹幕
				danmuDao.delete(videoId, userId);
			}else{
				danmuDao.delete(videoId);
			}
		} catch (Exception e) {
			throw new RuntimeException("未知的数据库异常，删除弹幕失败");
		}
	}

	public List<Danmu> list(String videoId, int offset, int maxSize) {
		// 检查criteria是否为空，为空，则抛出异常E0000 空值异常
		if (StringUtils.isEmpty(videoId)) {
			throw new RuntimeException("videoId不能为空");
		}
		List<Danmu> result = null;
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("sendTime", "DESC");
		try {
			result = danmuDao.getResultList(Danmu.class, "{ videoId : '%1$s' }", offset, maxSize, orderBy, videoId);
		} catch (Exception e) {
			throw new RuntimeException("未知的数据库异常，查询弹幕失败");
		}
		return result;
	}


}
