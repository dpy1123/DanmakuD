package com.dd.danmaku.resource.bean;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dd.danmaku.utils.DateUtils;

/**
 * 视频对象实体类.
 * @author DD
 * @version v2.0,2014-12-27
 */
@Document(collection = "video")
public class Video {
	@Transient
	public static final String UPLOADED = "待转换";
	@Transient
	public static final String CONVERTING = "转换中";
	@Transient
	public static final String CONVERTED = "转换成功";
	@Transient
	public static final String FAILED = "转换失败";
	
	@Id
	private String id;
	private String originalName;//原始文件名
	private String fsFileName;//在文件系统中的文件名【通过该字段从存储地获取视频内容，目前是本系统，将来可能是腾讯视频等的id等】
	private long size;//视频大小
	private Date createDTM;
	
	private long duration;//视频时长
	private String status;//视频转换状态，待转换/转换中/转换成功/转换失败
	private byte[] previewImg;//视频预览图
	
	public Video(String originalName, String fsFileName, long size) {
		this.originalName = originalName;
		this.fsFileName = fsFileName;
		this.size = size;
		this.status = Video.UPLOADED;
		this.createDTM = DateUtils.getCurrentDate();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getFsFileName() {
		return fsFileName;
	}

	public void setFsFileName(String fsFileName) {
		this.fsFileName = fsFileName;
	}
	
	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Date getCreateDTM() {
		return createDTM;
	}

	public void setCreateDTM(Date createDTM) {
		this.createDTM = createDTM;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public byte[] getPreviewImg() {
		return previewImg;
	}

	public void setPreviewImg(byte[] previewImg) {
		this.previewImg = previewImg;
	}
	
	
}
