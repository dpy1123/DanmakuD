package com.dd.danmaku.resource.bean.criteria;

import java.io.Serializable;

/**
 * 弹幕系统查询条件类
 * @author DD
 * @version v1.0,2013-03-12
 */
public class DanmuCriteria implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String videoId;//根据视频id查询
	private String userId;//根据发送用户id查询
	private int maxSize;//最大结果数
	
	
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
}
