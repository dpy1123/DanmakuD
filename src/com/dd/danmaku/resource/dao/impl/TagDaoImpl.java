package com.dd.danmaku.resource.dao.impl;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.dd.danmaku.common.dao.BaseDaoMongoImpl;
import com.dd.danmaku.resource.bean.Tag;
import com.dd.danmaku.resource.dao.TagDao;


@Repository
public class TagDaoImpl extends BaseDaoMongoImpl implements TagDao {

	
	public boolean isExist(String tagName) {
		return (getResultCount(Tag.class, "{ name : '%1$s' }", tagName) > 0);
	}

	public Tag getTagByName(String name) {
		Criteria criteria = Criteria.where("name").is(name);
		Query query = new Query(criteria);
		return mongoTemplate.findOne(query, Tag.class);
	}

}
