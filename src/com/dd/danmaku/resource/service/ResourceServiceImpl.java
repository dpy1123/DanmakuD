package com.dd.danmaku.resource.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dd.danmaku.resource.dao.ResourceDao;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
	
	@Resource
	protected ResourceDao resourceDao;
}
