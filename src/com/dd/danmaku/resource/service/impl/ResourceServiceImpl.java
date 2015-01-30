package com.dd.danmaku.resource.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dd.danmaku.resource.bean.Resource;
import com.dd.danmaku.resource.dao.ResourceDao;
import com.dd.danmaku.resource.service.ResourceService;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
	
	@javax.annotation.Resource
	protected ResourceDao resourceDao;

	public String add(Resource resource) {
		resourceDao.save(resource);
		return resource.getId();
	}

	public void addTags(String resourceId, List<String> tags) {
		Resource r = getById(resourceId);
		r.setTags(tags);
		resourceDao.update(r);
	}

	public void changeStatus(String resourceId, String status) {
		Resource r = getById(resourceId);
		r.setStatus(status);
		resourceDao.update(r);
	}

	public void updateDuration(String resourceId, String duration) {
		Resource r = getById(resourceId);
		r.setDuration(duration);
		resourceDao.update(r);
	}

	public void updateCount(String resourceId, String propName, long deta) {
		resourceDao.updateCount(resourceId, propName, deta);
	}

	public Resource getById(String id) {
		return resourceDao.get(Resource.class, id);
	}

	public Resource getByVideoId(String videoId) {
		// TODO Auto-generated method stub
		return null;
	}

}
