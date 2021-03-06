package com.dd.danmaku.resource.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dd.danmaku.common.utils.DateUtils;

/**
 * 资源实体类.
 * @author DD
 * @version v2.0,2014-12-27
 */
@Document(collection = "resource")
public class Resource implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String WAITING = "待审核";
	@Transient
	public static final String IN_USING = "上线";
	@Transient
	public static final String DELETEED = "下线";
	/**
	 * 默认无图时显示的资源图片
	 */
	@Transient
	public static final String NO_PIC = "NO_PREVIEW_PIC";
	
	
	@Id
	private String id;//标识
	private String uploaderId;//资源上传的up主id
	private List<String> videos;//资源包含的视频文件id
	private HashMap<String, String> subTitles;//视频文件id对应的视频名称
	private List<String> categories;//对应的分类s
	private List<String> tags;//对应的标签s
	private String title;//资源标题
	private String description;//资源简介
	private String status;//资源状态：待审核、上线、下线
	private Long duration;//资源所包含视频的总时长，单位是s
//	private String source;//资源来源【这个将来移到video中，用来区分是本系统存储还是外部存储的】
	private Boolean isOriginal;//资源是否原创
//	private String type;//资源类型：1.视频 2.专辑 3.话题（sp的集合）【专辑和话题应该是独立的实体】
	private String previewImg;//资源预览图【通过该字段从存储获取内容】
	private Long clickCount;//资源点击数
	private Long danmuCount;//资源弹幕数
	private Long favorCount;//资源收藏数
	private Integer score;//资源评分
	private Date createDTM;//资源上传日期
	
	public Resource() {
	}
	
	/**
	 * 
	 * @param uploaderId
	 * @param title
	 * @param description
	 * @param status WAITING IN_USING DELETEED
	 * @param isOriginal
	 */
	public Resource(String uploaderId, String title, String description, String status,
			Boolean isOriginal) {
		this.uploaderId = uploaderId;
		this.title = title;
		this.description = description;
		this.status = status;
		this.duration = 0L;
		this.isOriginal = isOriginal;
		this.previewImg = NO_PIC;
		this.clickCount = 0L;
		this.danmuCount = 0L;
		this.favorCount = 0L;
		this.score = 0;
		this.createDTM = DateUtils.getCurrentDate();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getUploaderId() {
		return uploaderId;
	}


	public void setUploaderId(String uploaderId) {
		this.uploaderId = uploaderId;
	}


	public List<String> getVideos() {
		return videos;
	}


	public void setVideos(List<String> videos) {
		this.videos = videos;
	}


	public HashMap<String, String> getSubTitles() {
		return subTitles;
	}

	public void setSubTitles(HashMap<String, String> subTitles) {
		this.subTitles = subTitles;
	}

	public List<String> getCategories() {
		return categories;
	}


	public void setCategories(List<String> categories) {
		if(this.categories == null)
			this.categories = categories;
		else
			try {
				this.categories.addAll(categories);
			} catch (UnsupportedOperationException e) {
				//如果原有的this.categories是Arrays.asList来的，是没有实现addAll方法的，重新包装一下
				this.categories = new ArrayList<String>(this.categories);
				this.categories.addAll(categories);
			}
	}


	public List<String> getTags() {
		return tags;
	}


	public void setTags(List<String> tags) {
		if(this.tags == null)
			this.tags = tags;
		else
			try {
				this.tags.addAll(tags);
			} catch (UnsupportedOperationException e) {
				//如果原有的this.tags是Arrays.asList来的，是没有实现addAll方法的，重新包装一下
				this.tags = new ArrayList<String>(this.tags);
				this.tags.addAll(tags);
			}
			
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Long getDuration() {
		return duration;
	}


	public void setDuration(Long duration) {
		this.duration = duration;
	}


	public Boolean getIsOriginal() {
		return isOriginal;
	}


	public void setIsOriginal(Boolean isOriginal) {
		this.isOriginal = isOriginal;
	}


	public String getPreviewImg() {
		return previewImg;
	}

	/**
	 * 设置预览图名称，通过次此名称在存储系统中获取图片内容
	 * @param previewImg
	 */
	public void setPreviewImg(String previewImg) {
		this.previewImg = previewImg;
	}


	public Long getClickCount() {
		return clickCount;
	}


	public void setClickCount(Long clickCount) {
		this.clickCount = clickCount;
	}


	public Long getDanmuCount() {
		return danmuCount;
	}


	public void setDanmuCount(Long danmuCount) {
		this.danmuCount = danmuCount;
	}


	public Long getFavorCount() {
		return favorCount;
	}


	public void setFavorCount(Long favorCount) {
		this.favorCount = favorCount;
	}


	public Integer getScore() {
		return score;
	}


	public void setScore(Integer score) {
		this.score = score;
	}


	public Date getCreateDTM() {
		return createDTM;
	}


	public void setCreateDTM(Date createDTM) {
		this.createDTM = createDTM;
	}

}
