package com.dd.danmaku.resource;


import java.util.Date;
import java.util.Set;



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
	
	/** full constructor */


	
}
