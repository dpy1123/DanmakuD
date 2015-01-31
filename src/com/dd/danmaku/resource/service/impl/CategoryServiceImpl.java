package com.dd.danmaku.resource.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.dd.danmaku.resource.bean.Category;
import com.dd.danmaku.resource.dao.CategoryDao;
import com.dd.danmaku.resource.service.CategoryService;
import com.dd.danmaku.utils.StringUtils;



@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

	@Resource
	private CategoryDao categoryDao;

	public boolean add(Category category) {
		if (null == category || StringUtils.isEmpty(category.getName())) {
			throw new RuntimeException("category对象不合法");
		}
		
		if( StringUtils.isEmpty(category.getPid())){
			category.setPid(Category.ROOT);
		}
		try {
			categoryDao.save(category);
		} catch (Exception e) {
			throw new RuntimeException("添加category失败");
		}
		return true;
	}
	
	public boolean increaseCount(String categoryId) {
		if (StringUtils.isEmpty(categoryId)) {
			throw new RuntimeException("参数不合法");
		}
		
		try {
			Category category = categoryDao.get(Category.class, categoryId);
			category.setCount(category.getCount() + 1);
			categoryDao.update(category);
		} catch (Exception e) {
			throw new RuntimeException("更新category下的资源数失败");
		}
		return true;
	}

	public Category getCategoryByName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new RuntimeException("参数不合法");
		}
		
		try {
			return categoryDao.getCategoryByName(name);
		} catch (Exception e) {
			throw new RuntimeException("获取分类失败");
		}
	}

	public LinkedHashMap<Category, List<Category>> getAllCategories() {
		return categoryDao.getAllCategories();
	}

	public List<Category> getSubCategoriesByName(String name) {
		List<Category> result = null;
		if (StringUtils.isEmpty(name)) {
			throw new RuntimeException("参数不合法");
		}
		
		try {
			Category parent = categoryDao.getCategoryByName(name);
			result = categoryDao.getSubCategories(parent.getId());
		} catch (Exception e) {
			throw new RuntimeException("获取分类失败");
		}
		return result;
	}

	public Category getCategoryById(String id) {
		return categoryDao.get(Category.class, id);
	}

	public Category getParentCategory(String id) {
		Category child = categoryDao.get(Category.class, id);
		return categoryDao.get(Category.class, child.getPid());
	}
}
