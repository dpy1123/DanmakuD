package com.dd.danmaku.resource.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.dd.danmaku.resource.bean.Video;

@Repository
public class VideoDaoImpl implements VideoDao {
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public String add(Video video) throws Exception {
		mongoTemplate.insert(video);
		return null;
	}

	@Override
	public boolean del(String id) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Video getById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateVideoDuration(String id, long duration)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateVideoStatus(String id, String status) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updatePreviewImg(String id, byte[] previewImg)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
}
