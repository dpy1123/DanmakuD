package com.dd.danmaku.resource.dao;

import com.dd.danmaku.common.dao.BaseDaoInter;

public interface DanmuDao extends BaseDaoInter {
	/**
	 * 根据视频id删除弹幕
	 * @param videoId
	 */
	public void delete(String videoId);
	
	/**
	 * 根据视频id和用户id删除弹幕
	 * @param videoId
	 * @param userId
	 */
	public void delete(String videoId, String userId);
}
