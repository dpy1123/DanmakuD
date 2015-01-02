package com.dd.danmaku.resource.dao;

import com.dd.danmaku.common.dao.BaseDaoInter;

public interface ResourceDao extends BaseDaoInter {

	/**
	 * 更新资源的评分或点击数或弹幕数或收藏数
	 * @param resourceId
	 * @param propName 属性名，可以是score、clickCount、danmuCount、favorCount
	 * @param deta 差值，+1表示在原来基础上+1，-1表示在原来基础上-1
	 * @return
	 */
	void updateCount(String resourceId, String propName, long deta);

}
