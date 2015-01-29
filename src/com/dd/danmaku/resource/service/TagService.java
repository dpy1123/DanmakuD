package com.dd.danmaku.resource.service;

import java.util.List;

import com.dd.danmaku.resource.bean.Tag;


public interface TagService {
	
	/**
	 * 判断标签是否已存在
	 * @param tagName
	 * @return
	 */
	public boolean isExist(String tagName);
	
	/**
	 * 新增标签
	 * @param tag
	 * @return
	 */
	public boolean add(Tag tag);
	
	/**
	 * 当某个标签被引用后，更新该标签的引用数和更新时间
	 * @param tagId
	 * @return
	 */
	public boolean addNewRef(String tagId);
	
	/**
	 * 将某个标签设置为专辑标签【管理员】
	 * @param tagId
	 * @return
	 */
	public boolean setAsSpecialEdition(String tagId);

	/**
	 * 根据标签名得到标签
	 * @param name
	 * @return
	 */
	public Tag getTagByName(String name);
	
	/**
	 * 列出所有热门标签
	 * @param n 指定显示top多少
	 * @return
	 */
	public List<Tag> listTopTags(int n);
	
	/**
	 * 列出所有热门专辑
	 * @param n 指定显示top多少
	 * @return
	 */
	public List<Tag> listTopSpecialEditions(int n);
}
