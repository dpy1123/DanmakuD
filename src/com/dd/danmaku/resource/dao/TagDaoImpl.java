package com.dd.danmaku.resource.dao;

import org.springframework.stereotype.Repository;

import com.dd.danmaku.common.dao.BaseDaoMongoImpl;
import com.dd.danmaku.resource.bean.Tag;


@Repository
public class TagDaoImpl extends BaseDaoMongoImpl implements TagDao {

	
	public boolean isExist(String tagName) {
		return (getResultCount(Tag.class, "{ name : '%1$s' }", tagName) > 0);
	}

}
