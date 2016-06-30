package com.mapbar.carlimit.model;

import java.io.Serializable;

public class CarLimitModel implements Serializable {
	private static final long serialVersionUID = -7185879938285220150L;

	public CarLimitModel() {
	}

	private String sdate;
	private String date;
	private String ct;
	private String ch;
	private String tp;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCt() {
		return ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
	}

	public String getCh() {
		return ch;
	}

	public void setCh(String ch) {
		this.ch = ch;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}
}
