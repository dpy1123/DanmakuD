package com.dd.danmaku.resource;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 资源实体类.
 * @author DD
 * @version v1.0,2013-03-23
 * @serial dd.tv/resource
 */

public class Resource implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	
	// Fields
	private String id;//标识
	private String uploaderId;//资源上传的up主id
	private Set<String> videos;//对应的视频文件ids
	private Set<String> categories;//对应的分类s
	private Set<String> tags;//对应的标签s
	private String title;//资源标题
	private String description;//资源简介
	private String status;//资源状态：待审核、上线、下线
	private String duration;//【可选】资源所包含视频的总时长，如43:21
	private String source;//资源来源
	private Boolean isOriginal;//资源是否原创
	private String type;//资源类型：1.视频 2.专辑 3.话题（sp的集合）
	private String previewImgPath;//【可选】资源预览图path
	private Long clickCount;//资源点击数
	private Long danmuCount;//【可选】资源弹幕数
	private Long favorCount;//资源收藏数
	private Integer score;//资源评分
	private Date createDTM;//资源上传日期
	
	
	/** default constructor  */
	public Resource() {
	}
	
	/** full constructor */
	public Resource(String id, String uploaderId, String videoId,
			Set<Category> categories, Set<Tag> tags, String title,
			String description, String status, String duration, String source,
			Boolean isSp, Boolean isOriginal, String previewImgPath, Long clickCount,
			Long danmuCount, Long favorCount, Integer score, Date uploadDTM) {
		super();
		this.id = id;
		this.uploaderId = uploaderId;
		this.videoId = videoId;
		this.categories = categories;
		this.tags = tags;
		this.title = title;
		this.description = description;
		this.status = status;
		this.duration = duration;
		this.source = source;
		this.isSp = isSp;
		this.isOriginal = isOriginal;
		this.previewImgPath = previewImgPath;
		this.clickCount = clickCount;
		this.danmuCount = danmuCount;
		this.favorCount = favorCount;
		this.score = score;
		this.createDTM = uploadDTM;
	}


	@Id
	@Column(name = "id", nullable = false, length = 50)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "uid", nullable = false, length = 50)
	public String getUploaderId() {
		return uploaderId;
	}
	public void setUploaderId(String uploaderId) {
		this.uploaderId = uploaderId;
	}
	
	@Column(name = "vid", nullable = false, length = 50)
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "category_resource_relation", 
		joinColumns = { @JoinColumn(name = "resource_id", nullable = false, updatable = false) }, 
		inverseJoinColumns = { @JoinColumn(name = "category_id", nullable = false, updatable = false) }
	)
	public Set<Category> getCategories() {
		return categories;
	}
	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "tag_resource_relation", 
		joinColumns = { @JoinColumn(name = "resource_id", nullable = false, updatable = false) }, 
		inverseJoinColumns = { @JoinColumn(name = "tag_id", nullable = false, updatable = false) }
	)
	public Set<Tag> getTags() {
		return tags;
	}
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	
	@Column(name = "title", nullable = false, length = 80)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "description", length = 450)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "duration")
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	@Column(name = "source")
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	@Column(name = "is_sp")
	public Boolean getIsSp() {
		return isSp;
	}
	public void setIsSp(Boolean isSp) {
		this.isSp = isSp;
	}
	@Column(name = "is_original")
	public Boolean getIsOriginal() {
		return isOriginal;
	}
	public void setIsOriginal(Boolean isOriginal) {
		this.isOriginal = isOriginal;
	}
	
	@Column(name = "preview_img_path", length = 450)
	public String getPreviewImgPath() {
		return previewImgPath;
	}
	public void setPreviewImgPath(String previewImgPath) {
		this.previewImgPath = previewImgPath;
	}
	
	@Column(name = "click_count")
	public Long getClickCount() {
		return clickCount;
	}
	public void setClickCount(Long clickCount) {
		this.clickCount = clickCount;
	}
	
	@Column(name = "danmu_count")
	public Long getDanmuCount() {
		return danmuCount;
	}
	public void setDanmuCount(Long danmuCount) {
		this.danmuCount = danmuCount;
	}
	
	@Column(name = "favor_count")
	public Long getFavorCount() {
		return favorCount;
	}
	public void setFavorCount(Long favorCount) {
		this.favorCount = favorCount;
	}
	
	@Column(name = "score")
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "upload_dtm", nullable = false, length = 19)
	public Date getUploadDTM() {
		return createDTM;
	}
	public void setUploadDTM(Date uploadDTM) {
		this.createDTM = uploadDTM;
	}
	
}
