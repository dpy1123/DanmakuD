package com.dd.danmaku.common.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;


/**
 * @Description DAO基类,实现了实体的基本增删改查列表分页方法,
 * 	<br/>所有DAO的实现必须继承该类
 * @author  dd
 * @version  1.0
 */
public abstract class BaseDaoMongoImpl implements BaseDaoInter {

	@Autowired
	protected MongoTemplate mongoTemplate;
	
	/**
	 * 查找实体
	 * @param <T> 动态传入实体类
	 * @param entityClass 实体类
	 * @param pk 主键
	 * @return 根据指定主键返回的实体
	 */
	public <T> T get(Class <T> entityClass, Object pk) {
		T obj = mongoTemplate.findById(pk, entityClass);
		return obj;
	}
	
	/**
	 * 保存实体<br> 
	 * <p>This will perform an insert if the object is not already
	 * present, that is an 'upsert'.</p>
	 * 
	 * <p>The object is converted to the MongoDB native representation using an
	 * instance of {@see MongoConverter}. Unless configured otherwise, an
	 * instance of SimpleMongoConverter will be used.</p>
	 * 
	 * <p>If you object has an "Id' property, it will be set with the generated Id from MongoDB.
	 * If your Id property is a String then MongoDB ObjectId will be used to populate that string.
	 * Otherwise, the conversion from ObjectId to your property type will be handled by Spring's BeanWrapper class that leverages Spring 3.0's new Type Conversion API.</p>
	 * 
	 * @param entity 需要保存的实体
	 */
	public void save(Object entity) {
		mongoTemplate.save(entity);
	}
	
	/**
	 * 更新实体
	 * <p>等同于save的功能，只是在语意上区分。</p>
	 * @param entity 需要保存的实体
	 */
	public void update(Object entity) {
		mongoTemplate.save(entity);
	}
	
	/**
	 * 删除实体
	 * @param entityClass 需要删除实体类
	 * @param pk 需要删除的实体主键
	 */
	public void delete(Class<?> entityClass, Object pk) {
		mongoTemplate.remove(get(entityClass, pk));
	}
	
	/**
	 * @param whereJpql mongo风格的查询语句
	 * @param args [不使用]
	 */
	public <T> List<T> getResultList(Class<T> entityClass 
		, String whereJpql 
		, LinkedHashMap<String, String> orderBy
		, Object... args) {
		//创建查询
		BasicQuery query = new BasicQuery(whereJpql);
		
		//设置排序
		if(orderBy != null && orderBy.size() > 0) {
			List<Order> orders = new ArrayList<Order>();
			for (String property : orderBy.keySet()) {
				orders.add(new Order(
						"DESC".equalsIgnoreCase(orderBy.get(property)) ? Direction.DESC
								: Direction.ASC, property));
			}
			Sort sort = new Sort(orders);
			query.with(sort);
		}
		
		//返回结果集
		return mongoTemplate.find(query, entityClass);
	}
	
	/**
	 * @param whereJpql mongo风格的查询语句
	 * @param args [不使用]
	 */
	public <T> List<T> getResultList(Class<T> entityClass 
		, String whereJpql 
		, int firstResult 
		, int maxResult 
		, LinkedHashMap<String, String> orderBy
		, Object... args) {
		//创建查询
		BasicQuery query = new BasicQuery(whereJpql);
		//设置排序
		if(orderBy != null && orderBy.size() > 0) {
			List<Order> orders = new ArrayList<Order>();
			for (String property : orderBy.keySet()) {
				orders.add(new Order(
						"DESC".equalsIgnoreCase(orderBy.get(property)) ? Direction.DESC
								: Direction.ASC, property));
			}
			Sort sort = new Sort(orders);
			query.with(sort);
		}
		//对查询结果集进行分页
		query.skip(firstResult).limit(maxResult);
		//返回结果集
		return mongoTemplate.find(query, entityClass);
	}
	
	/**
	 * @param whereJpql mongo风格的查询语句
	 * @param args [不使用]
	 */
	public <T> int getResultCount(Class<T> entityClass
			, String whereJpql
			, Object... args) {
		BasicQuery query = new BasicQuery(whereJpql);
		return (int) mongoTemplate.count(query, entityClass);
	}
	
}