package com.dd.danmaku.resource.bean;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dd.danmaku.utils.DateUtils;

/**
 * 标签实体类.
 * @author DD
 * @version v2.0,2015-01-02
 */
@Document(collection = "tag")
public class Tag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;//标签id
	private String name;
	private Long count;//标签的引用数，热度
	private Boolean isSpecialEdition;//标签是否是专辑
	private Date createDTM;//标签的创建日期
	private Date updateDTM;//标签的更新日期
	
	public Tag() {
	}

	public Tag(String name, Long count, Boolean isSpecialEdition) {
		this.name = name;
		this.count = count;
		this.isSpecialEdition = isSpecialEdition;
		this.createDTM = DateUtils.getCurrentDate();
		this.updateDTM = this.createDTM;
	}
	
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}

	public Boolean getIsSpecialEdition() {
		return isSpecialEdition;
	}
	public void setIsSpecialEdition(Boolean isSpecialEdition) {
		this.isSpecialEdition = isSpecialEdition;
	}

	public Date getCreateDTM() {
		return createDTM;
	}
	public void setCreateDTM(Date createDTM) {
		this.createDTM = createDTM;
	}

	public Date getUpdateDTM() {
		return updateDTM;
	}
	public void setUpdateDTM(Date updateDTM) {
		this.updateDTM = updateDTM;
	}

}
