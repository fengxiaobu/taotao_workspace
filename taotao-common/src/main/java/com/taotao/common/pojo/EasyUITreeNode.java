package com.taotao.common.pojo;

import java.io.Serializable;
/**
 * 商品分类结果集
 * @author luopa
 *
 */
public class EasyUITreeNode implements Serializable {
	private long id;
	private String text;
	private String state;

	
	public String getText() {
		return text;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
