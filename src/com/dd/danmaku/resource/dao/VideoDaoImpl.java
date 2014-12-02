package com.dd.danmaku.resource.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository  
public class VideoDaoImpl extends VideoDao {
	 @Autowired  
	    private MongoTemplate mongoTemplate; 
}
