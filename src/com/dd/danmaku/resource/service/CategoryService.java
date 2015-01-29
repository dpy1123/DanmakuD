package com.dd.danmaku.resource.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.dd.danmaku.resource.bean.Category;


public interface CategoryService {
	
	/**
	 * 增加新分类
	 * @param category
	 * @return
	 */
	public boolean add(Category category);
	
	/**
	 * 该分类拥有的资源数+1
	 * @param categoryId
	 * @return
	 */
	public boolean increaseCount(String categoryId);
	
	/**
	 * 根据分类名得到分类
	 * @param name
	 * @return
	 */
	public Category getCategoryByName(String name);
	
	/**
	 * 根据分类名得到所有子分类
	 * @param name
	 * @return
	 */
	public List<Category> getSubCategoriesByName(String name);
	
	
	/**
	 * 得到所有的分类
	 * @return <一级分类，对应的二级分类集合>
	 */
	public LinkedHashMap<Category, List<Category>> getAllCategories();
	
}
