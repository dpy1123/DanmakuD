package com.dd.danmaku.resource.service;

import java.util.List;

import com.dd.danmaku.resource.bean.Danmu;


/**
 * 弹幕系统逻辑Service层
 * @author DD
 * @version v1.0,2013-03-12
 */
public interface DanmuService {
	
	/**
	 * 添加弹幕对象
	 * @param danmu 弹幕对象
	 * @return
	 */
	public String add(Danmu danmu);
	
	/**
	 * 删除弹幕
	 * @param videoId
	 * @param userId
	 */
	public void del(String videoId, String userId);
	

	/**
	 * 查询弹幕
	 * @param videoId
	 * @param offset   指定查询返回的第一条记录的位置
	 * @param maxSize  设置查询最多返回多少几条记录
	 * @return
	 */
	public List<Danmu> list(String videoId, int offset, int maxSize);
	
}
