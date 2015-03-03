package com.dd.danmaku.resource.service;

import com.dd.danmaku.resource.bean.Video;

public interface VideoService {
	/**
	 * 添加新的视频信息
	 * @param video
	 * @return 新加入的对象的id
	 */
	public String add(Video video);
	
	/**
	 * 更新视频信息
	 * @param video
	 */
	public void update(Video video);
	
	/**
	 * 删除一条视频信息
	 * @param id
	 */
	public void del(String id);

	/**
	 * 找到指定的视频信息
	 * @param id
	 * @return
	 */
	public Video getById(String id);
	
	/**
	 * 找到指定的视频信息
	 * @param filename
	 * @return
	 */
	public Video getByFileName(String filename);
	
	/**
	 * 更新视频的时长
	 * @param duration 时长，秒
	 */
	public void updateVideoDuration(String id, long duration);
	
	/**
	 * 更新视频的转换状态
	 * @param status 转换状态
	 */
	public void updateVideoStatus(String id, String status);
	
	/**
	 * 更新视频新生成的预览图
	 * @param previewImg 预览图
	 */
	public void updatePreviewImg(String id, String previewImg);
}
