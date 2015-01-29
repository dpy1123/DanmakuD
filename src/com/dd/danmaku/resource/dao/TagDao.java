package com.dd.danmaku.resource.dao;

import com.dd.danmaku.common.dao.BaseDaoInter;
import com.dd.danmaku.resource.bean.Tag;


public interface TagDao extends BaseDaoInter {
	/**
	 * 判断标签是否已存在
	 * @param tagName
	 * @return
	 */
	public boolean isExist(String tagName);
	
	/**
	 * 根据标签名得到标签
	 * @param name
	 * @return
	 */
	public Tag getTagByName(String name);
}
