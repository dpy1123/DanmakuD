package com.dd.danmaku.resource.dao.impl;


import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.dd.danmaku.common.dao.BaseDaoMongoImpl;
import com.dd.danmaku.resource.bean.Danmu;
import com.dd.danmaku.resource.dao.DanmuDao;


@Repository
public class DanmuDaoImpl extends BaseDaoMongoImpl implements DanmuDao {

	public void delete(String videoId) {
		Criteria criteria = Criteria.where("videoId").is(videoId);
		Query query = new Query(criteria);
		mongoTemplate.remove(query, Danmu.class);
	}

	public void delete(String videoId, String userId) {
		Criteria criteria = Criteria.where("videoId").is(videoId)
				.andOperator(Criteria.where("userId").is(userId));
		Query query = new Query(criteria);
		mongoTemplate.remove(query, Danmu.class);
	}

}
