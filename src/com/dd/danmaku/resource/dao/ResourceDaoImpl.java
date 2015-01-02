package com.dd.danmaku.resource.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.dd.danmaku.common.dao.BaseDaoMongoImpl;
import com.dd.danmaku.resource.bean.Resource;

@Repository
public class ResourceDaoImpl extends BaseDaoMongoImpl implements ResourceDao {

	public void updateCount(String resourceId, String propName, long deta) {
		Criteria criteria = Criteria.where("id").is(resourceId);
		Query query = new Query(criteria);
		Update update = new Update();
		update.inc(propName, deta);
		mongoTemplate.updateFirst(query, update, Resource.class);
	}

}
