package com.dd.danmaku.resource.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * 分类实体类.
 * @author DD
 * @version v2.0,2015-01-02
 */
@Document(collection = "category")
public class Category {

	private String id;//分类id
	private String pid;//分类父id，一级分类的pid是ROOT
	private String name;
	private Integer level;//分类的层级数
	private Long count;//分类下的资源数
	private Integer order;//分类顺序
	
	
	/** default constructor  */
	public Category() {
	}
	
	/** full constructor */
	public Category(String pid, String name, Integer level, Long count,
			Integer order) {
		super();
		this.pid = pid;
		this.name = name;
		this.level = level;
		this.count = count;
		this.order = order;
	}
	
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
		
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}

}
