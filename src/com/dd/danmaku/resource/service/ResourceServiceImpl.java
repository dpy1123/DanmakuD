package com.dd.danmaku.resource.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dd.danmaku.resource.dao.ResourceDao;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
	
	@Resource
	protected ResourceDao resourceDao;

	public String add(com.dd.danmaku.resource.bean.Resource resource) {
		resourceDao.save(resource);
		return resource.getId();
	}

	public void addTags(String resourceId, List<String> tags) {
		com.dd.danmaku.resource.bean.Resource r = getById(resourceId);
		r.setTags(tags);
		resourceDao.update(r);
	}

	public void changeStatus(String resourceId, String status) {
		com.dd.danmaku.resource.bean.Resource r = getById(resourceId);
		r.setStatus(status);
		resourceDao.update(r);
	}

	public void updateDuration(String resourceId, String duration) {
		com.dd.danmaku.resource.bean.Resource r = getById(resourceId);
		r.setDuration(duration);
		resourceDao.update(r);
	}

	public void updateCount(String resourceId, String propName, long deta) {
		resourceDao.updateCount(resourceId, propName, deta);
	}

	public com.dd.danmaku.resource.bean.Resource getById(String id) {
		return resourceDao.get(com.dd.danmaku.resource.bean.Resource.class, id);
	}
}
