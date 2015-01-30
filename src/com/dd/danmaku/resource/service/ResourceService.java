package com.dd.danmaku.resource.service;

import java.util.List;

import com.dd.danmaku.resource.bean.Resource;
import com.dd.danmaku.resource.bean.criteria.ResourceCriteria;


public interface ResourceService {
	/**
	 * 添加新资源
	 * @param resource
	 * @return resourceId
	 */
	public String add(Resource resource);
	
	/**
	 * 为资源添加标签
	 * @param resourceId
	 * @param tags 标签id的list
	 */
	public void addTags(String resourceId, List<String> tags);

	/**
	 * 更改资源状态，待审核、上线、下线
	 * @param resourceId
	 * @param status
	 * @return
	 */
	public void changeStatus(String resourceId, String status);
	
	/**
	 * 更新资源视频的时长，格式42:12
	 * @param resourceId
	 * @param duration
	 * @return
	 */
	public void updateDuration(String resourceId, String duration);

	/**
	 * 更新资源的评分或点击数或弹幕数或收藏数
	 * @param resourceId
	 * @param propName 属性名，可以是score、clickCount、danmuCount、favorCount
	 * @param deta 差值，+1表示在原来基础上+1，-1表示在原来基础上-1
	 * @return
	 */
	public void updateCount(String resourceId, String propName, long deta);

	/**
	 * 根据id返回相应对象
	 * @param id
	 * @return
	 */
	public Resource getById(String id);
	
	/**
	 * 根据查询条件类返回符号条件的对象集合
	 * @param criteria
	 * @return
	 */
//	public List<Resource> listByCriteria(ResourceCriteria criteria);

	/**
	 * 根据查询条件类返回符号条件的对象的数量
	 * @param criteria
	 * @return
	 */
//	public int countByCriteria(ResourceCriteria criteria);
}
