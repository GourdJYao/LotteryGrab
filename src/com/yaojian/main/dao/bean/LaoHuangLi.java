package com.yaojian.main.dao.bean;

import java.io.Serializable;
import java.sql.Date;

public class LaoHuangLi implements Serializable {
	private static final long serialVersionUID = 6303492294642786154L;
	private Integer id;
	private Date yangli;
	private String yinli;
	private String wuxing;
	private String chongsha;
	private String baiji;
	private String jishen;
	private String yi;
	private String xiongshen;
	private String ji;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getYangli() {
		return yangli;
	}

	public void setYangli(Date yangli) {
		this.yangli = yangli;
	}

	public String getYinli() {
		return yinli;
	}

	public void setYinli(String yinli) {
		this.yinli = yinli;
	}

	public String getWuxing() {
		return wuxing;
	}

	public void setWuxing(String wuxing) {
		this.wuxing = wuxing;
	}

	public String getChongsha() {
		return chongsha;
	}

	public void setChongsha(String chongsha) {
		this.chongsha = chongsha;
	}

	public String getBaiji() {
		return baiji;
	}

	public void setBaiji(String baiji) {
		this.baiji = baiji;
	}

	public String getJishen() {
		return jishen;
	}

	public void setJishen(String jishen) {
		this.jishen = jishen;
	}

	public String getYi() {
		return yi;
	}

	public void setYi(String yi) {
		this.yi = yi;
	}

	public String getXiongshen() {
		return xiongshen;
	}

	public void setXiongshen(String xiongshen) {
		this.xiongshen = xiongshen;
	}

	public String getJi() {
		return ji;
	}

	public void setJi(String ji) {
		this.ji = ji;
	}

}
