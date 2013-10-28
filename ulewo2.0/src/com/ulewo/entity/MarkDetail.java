package com.ulewo.entity;

/**
 * TODO: 
 * 
 * @author luo.hl
 * @date 2013-10-28 下午5:20:22
 * @version 0.1.0 
 * @copyright ulewo.com 
 */
public class MarkDetail {
	private int id;
	private String userid;
	private Integer leftmark;
	private Integer opmark;
	private String optype;
	private String opcontent;
	private String optime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Integer getLeftmark() {
		return leftmark;
	}

	public void setLeftmark(Integer leftmark) {
		this.leftmark = leftmark;
	}

	public Integer getOpmark() {
		return opmark;
	}

	public void setOpmark(Integer opmark) {
		this.opmark = opmark;
	}

	public String getOptype() {
		return optype;
	}

	public void setOptype(String optype) {
		this.optype = optype;
	}

	public String getOpcontent() {
		return opcontent;
	}

	public void setOpcontent(String opcontent) {
		this.opcontent = opcontent;
	}

	public String getOptime() {
		return optime;
	}

	public void setOptime(String optime) {
		this.optime = optime;
	}

}
