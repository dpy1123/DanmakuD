package com.dd.danmaku.resource.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dd.danmaku.resource.bean.Tag;
import com.dd.danmaku.resource.dao.TagDao;
import com.dd.danmaku.resource.service.TagService;
import com.dd.danmaku.utils.StringUtils;




@Service("tagService")
public class TagServiceImpl implements TagService {

	@Resource
	private TagDao tagDao;

	public boolean isExist(String tagName) {
		if (StringUtils.isEmpty(tagName)) {
			throw new RuntimeException("参数不合法");
		}

		try {
			return tagDao.isExist(tagName);
		} catch (Exception e) {
			throw new RuntimeException("判断标签是否已存在失败");
		}
	}
	
	/**
	 * 保存成功返回true；若标签名已存在，返回false
	 */
	public boolean add(Tag tag) {
		if (null == tag || StringUtils.isEmpty(tag.getName())) {
			throw new RuntimeException("tag对象不合法");
		}
		
		try {
			if(tagDao.isExist(tag.getName())){//标签已存在
				return false;
			}else{//不存在，创建新标签
				tagDao.save(tag);
			}
		} catch (Exception e) {
			throw new RuntimeException("添加标签失败");
		}
		return true;
	}

	public boolean addNewRef(String tagId) {
		if (StringUtils.isEmpty(tagId)){
			throw new RuntimeException("id不能为空");
		}
		
		try {
			Tag tag = tagDao.get(Tag.class, tagId);
			tag.setCount(tag.getCount() + 1);
			tag.setUpdateDTM(new Date());
			tagDao.update(tag);
		} catch (Exception e) {
			throw new RuntimeException("更新已有标签的引用数和更新时间失败");
		}
		return true;
	}

	public boolean setAsSpecialEdition(String tagId) {
		if (StringUtils.isEmpty(tagId)){
			throw new RuntimeException("id不能为空");
		}
		
		try {
			Tag tag = tagDao.get(Tag.class, tagId);
			tag.setIsSpecialEdition(true);
			tagDao.update(tag);
		} catch (Exception e) {
			throw new RuntimeException("设置为专辑标签失败");
		}
		return true;
	}

	
	public Tag getTagByName(String name) {
		if (StringUtils.isEmpty(name)){
			throw new RuntimeException("name不能为空");
		}
		
		try {
			return tagDao.getTagByName(name);
		} catch (Exception e) {
			throw new RuntimeException("获取标签失败");
		}
	}
	
	public List<Tag> listTopTags(int n) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Tag> listTopSpecialEditions(int n) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
