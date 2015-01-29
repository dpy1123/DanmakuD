package com.dd.danmaku.resource.dao;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.dd.danmaku.common.dao.BaseDaoMongoImpl;
import com.dd.danmaku.resource.bean.Category;


@Repository
public class CategoryDaoImpl extends BaseDaoMongoImpl implements CategoryDao {

	public LinkedHashMap<Category, List<Category>> getAllCategories() {
		LinkedHashMap<Category, List<Category>> result = new LinkedHashMap<Category, List<Category>>();
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("order", "ASC");
		List<Category> categories = getResultList(Category.class, "{ pid : '%1$s' }", orderBy, Category.ROOT);
		for (int i = 0; i < categories.size(); i++) {
			Category root = categories.get(i);
			List<Category> subCategories = getResultList(Category.class, "{ pid : '%1$s' }", orderBy, root.getId());
			result.put(root, subCategories);
		}
		return result;
	}

}
