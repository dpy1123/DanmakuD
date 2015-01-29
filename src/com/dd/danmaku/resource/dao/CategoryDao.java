package com.dd.danmaku.resource.dao;

import java.util.LinkedHashMap;
import java.util.List;

import com.dd.danmaku.common.dao.BaseDaoInter;
import com.dd.danmaku.resource.bean.Category;


public interface CategoryDao extends BaseDaoInter {

	/**
	 * 得到所有的分类
	 * @return <一级分类，对应的二级分类集合>
	 */
	public LinkedHashMap<Category, List<Category>> getAllCategories();

}
