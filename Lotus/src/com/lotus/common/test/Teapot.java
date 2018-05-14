package com.lotus.common.test;

import java.math.BigDecimal;
import java.util.Date;

import com.lotus.database.annotation.Column;
import com.lotus.database.annotation.ColumnIgnore;
import com.lotus.database.annotation.Entity;

@Entity(defaultColumn=true)
public class Teapot {
	@Column(PK=true)
	private long id;
	private String manufacturer;
	private double volume;
	private BigDecimal price;
	@Column(name = "count")
	private int teacupCount;
	private boolean isCeramics;
	@ColumnIgnore
	private Date createTime;
	@ColumnIgnore
	private String isJavaBean;

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getTeacupCount() {
		return teacupCount;
	}

	public void setTeacupCount(int teacupCount) {
		this.teacupCount = teacupCount;
	}

	public boolean isCeramics() {
		return isCeramics;
	}

	public void setCeramics(boolean isCeramics) {
		this.isCeramics = isCeramics;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIsJavaBean() {
		return isJavaBean;
	}

	public void setIsJavaBean(String isJavaBean) {
		this.isJavaBean = isJavaBean;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
