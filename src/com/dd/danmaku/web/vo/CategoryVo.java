package com.dd.danmaku.web.vo;

import com.dd.danmaku.resource.bean.Category;

public class CategoryVo extends Category {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 前台url中使用的名称
	 */
	private String showName;

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}
	

}
