package com.dd.danmaku.resource.service;

import java.util.List;

import com.dd.danmaku.resource.bean.Danmu;
import com.dd.danmaku.resource.bean.criteria.DanmuCriteria;


/**
 * 弹幕系统逻辑Service层
 * @author DD
 * @version v1.0,2013-03-12
 */
public interface DanmuService {
	
	/**
	 * 添加弹幕对象
	 * @param danmu [弹幕对象]
	 * @return [boolean]
	 * @throws Exception
	 */
	public boolean add(Danmu danmu) throws Exception;
	
	/**
	 * 删除弹幕
	 * @param criteria [条件查询类]包含videoId、userId
	 * @return [boolean]
	 * @throws Exception
	 */
	public boolean del(DanmuCriteria criteria) throws Exception;
	

	/**
	 * 查询弹幕
	 * @param criteria [条件查询类]包含videoId、maxSize。
	 * @return [List]
	 * @throws Exception
	 */
	public List<Danmu> list(DanmuCriteria criteria) throws Exception;
	
}
