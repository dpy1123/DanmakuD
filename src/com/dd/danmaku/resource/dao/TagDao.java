package com.dd.danmaku.resource.dao;

import com.dd.danmaku.common.dao.BaseDaoInter;


public interface TagDao extends BaseDaoInter {
	/**
	 * 判断标签是否已存在
	 * @param tagName
	 * @return
	 */
	public boolean isExist(String tagName);
}
