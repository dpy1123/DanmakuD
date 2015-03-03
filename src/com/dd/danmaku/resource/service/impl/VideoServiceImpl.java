package com.dd.danmaku.resource.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dd.danmaku.resource.bean.Video;
import com.dd.danmaku.resource.dao.VideoDao;
import com.dd.danmaku.resource.service.VideoService;

@Service("videoService")
public class VideoServiceImpl implements VideoService {

	@Resource
	protected VideoDao videoDao;
	
	public String add(Video video) {
		videoDao.save(video);
		return video.getId();
	}

	public void update(Video video) {
		videoDao.update(video);
	}
	
	public void del(String id) {
		videoDao.delete(Video.class, id);
	}

	public Video getById(String id) {
		return videoDao.get(Video.class, id);
	}

	/**
	 * 根据video的fsFileName信息查找
	 * @param fsFileName 存入文件系统的文件名
	 */
	public Video getByFileName(String fsFileName) {
		return videoDao.find(Video.class, String.format("{'fsFileName' : '%1$s'}", fsFileName));
	}
	
	public void updateVideoDuration(String id, long duration)
			{
		Video v = getById(id);
		v.setDuration(duration);
		videoDao.update(v);
	}

	public void updateVideoStatus(String id, String status) {
		Video v = getById(id);
		v.setStatus(status);
		videoDao.update(v);
	}

	public void updatePreviewImg(String id, String previewImg)
			{
		Video v = getById(id);
		v.setPreviewImg(previewImg);
		videoDao.update(v);
	}

}
