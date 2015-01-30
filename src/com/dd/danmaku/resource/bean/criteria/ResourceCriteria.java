package com.dd.danmaku.resource.bean.criteria;

import java.util.Date;

public class ResourceCriteria implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	//按关键字查询
	private String uploaderId;//资源上传的up主id
	private String videoId;//对应的视频文件id
	private String title;//资源标题
	private String status;//资源状态：待审核、上线、下线
	private boolean isOriginal;//资源是否原创
	private Date uploadDTMFrom;//资源上传日期
	private Date uploadDTMTo;//资源上传日期
	
	private String[] tags;		//标签名称数组
	private String[] categories;//分类标识数组
	
	//排序条件
	private String clickCountOrder;//资源点击数排序
	private String danmuCountOrder;//资源弹幕数排序
	private String favorCountOrder;//资源收藏数排序
	private String scoreOrder;//资源评分排序
	private String uploadDTMOrder;//资源上传日期排序
	
	//分页用
	private Integer startSize; //起始条数
	private Integer pageSize;  //每页显示数
	
	
	public String getUploaderId() {
		return uploaderId;
	}
	public void setUploaderId(String uploaderId) {
		this.uploaderId = uploaderId;
	}
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public String[] getCategories() {
		return categories;
	}
	public void setCategories(String[] categories) {
		this.categories = categories;
	}
	public String getClickCountOrder() {
		return clickCountOrder;
	}
	public void setClickCountOrder(String clickCountOrder) {
		this.clickCountOrder = clickCountOrder;
	}
	public String getDanmuCountOrder() {
		return danmuCountOrder;
	}
	public void setDanmuCountOrder(String danmuCountOrder) {
		this.danmuCountOrder = danmuCountOrder;
	}
	public String getFavorCountOrder() {
		return favorCountOrder;
	}
	public void setFavorCountOrder(String favorCountOrder) {
		this.favorCountOrder = favorCountOrder;
	}
	public String getScoreOrder() {
		return scoreOrder;
	}
	public void setScoreOrder(String scoreOrder) {
		this.scoreOrder = scoreOrder;
	}
	public String getUploadDTMOrder() {
		return uploadDTMOrder;
	}
	public void setUploadDTMOrder(String uploadDTMOrder) {
		this.uploadDTMOrder = uploadDTMOrder;
	}
	public Integer getStartSize() {
		return startSize;
	}
	public void setStartSize(Integer startSize) {
		this.startSize = startSize;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Date getUploadDTMFrom() {
		return uploadDTMFrom;
	}
	public void setUploadDTMFrom(Date uploadDTMFrom) {
		this.uploadDTMFrom = uploadDTMFrom;
	}
	public Date getUploadDTMTo() {
		return uploadDTMTo;
	}
	public void setUploadDTMTo(Date uploadDTMTo) {
		this.uploadDTMTo = uploadDTMTo;
	}
	public boolean isOriginal() {
		return isOriginal;
	}
	public void setOriginal(boolean isOriginal) {
		this.isOriginal = isOriginal;
	}
}
