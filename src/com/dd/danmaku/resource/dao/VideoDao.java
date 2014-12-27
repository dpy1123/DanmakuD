package com.dd.danmaku.resource.dao;

import com.dd.danmaku.resource.bean.Video;

public interface VideoDao {
	/**
	 * 添加新的视频信息
	 * @param video
	 * @return 新加入的对象的id
	 */
	public String add(Video video) throws Exception;
	
	/**
	 * 删除一条视频信息
	 * @param id
	 * @return
	 */
	public boolean del(String id) throws Exception;

	/**
	 * 找到指定的视频信息
	 * @param id
	 * @return
	 */
	public Video getById(String id) throws Exception;
	
	/**
	 * 更新视频的时长
	 * @param duration 时长，秒
	 */
	public boolean updateVideoDuration(String id, long duration) throws Exception;
	
	/**
	 * 更新视频的转换状态
	 * @param status 转换状态
	 */
	public boolean updateVideoStatus(String id, String status) throws Exception;
	
	/**
	 * 更新视频新生成的预览图
	 * @param previewImg 预览图
	 */
	public boolean updatePreviewImg(String id, byte[] previewImg) throws Exception;

}
