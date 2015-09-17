package com.yaojian.main.dao.bean;

import java.io.Serializable;
import java.sql.Date;

public class LotteryBall implements Serializable {
	private static final long serialVersionUID = 178357931638064791L;
	protected Integer id;
	protected Integer redball_1;
	protected Integer redball_2;
	protected Integer redball_3;
	protected Integer redball_4;
	protected Integer redball_5;
	protected Integer redball_6;
	protected Integer redball_7;
	protected Integer redball_8;
	protected Integer redball_9;
	protected Integer blueball_1;
	protected Integer blueball_2;
	protected Integer blueball_3;
	protected String periods;
	protected Date lotterydate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRedball_1() {
		return redball_1;
	}

	public void setRedball_1(Integer redball_1) {
		this.redball_1 = redball_1;
	}

	public Integer getRedball_2() {
		return redball_2;
	}

	public void setRedball_2(Integer redball_2) {
		this.redball_2 = redball_2;
	}

	public Integer getRedball_3() {
		return redball_3;
	}

	public void setRedball_3(Integer redball_3) {
		this.redball_3 = redball_3;
	}

	public Integer getRedball_4() {
		return redball_4;
	}

	public void setRedball_4(Integer redball_4) {
		this.redball_4 = redball_4;
	}

	public Integer getRedball_5() {
		return redball_5;
	}

	public void setRedball_5(Integer redball_5) {
		this.redball_5 = redball_5;
	}

	public Integer getRedball_6() {
		return redball_6;
	}

	public void setRedball_6(Integer redball_6) {
		this.redball_6 = redball_6;
	}

	public Integer getRedball_7() {
		return redball_7;
	}

	public void setRedball_7(Integer redball_7) {
		this.redball_7 = redball_7;
	}

	public Integer getRedball_8() {
		return redball_8;
	}

	public void setRedball_8(Integer redball_8) {
		this.redball_8 = redball_8;
	}

	public Integer getRedball_9() {
		return redball_9;
	}

	public void setRedball_9(Integer redball_9) {
		this.redball_9 = redball_9;
	}

	public Integer getBlueball_1() {
		return blueball_1;
	}

	public void setBlueball_1(Integer blueball_1) {
		this.blueball_1 = blueball_1;
	}

	public Integer getBlueball_2() {
		return blueball_2;
	}

	public void setBlueball_2(Integer blueball_2) {
		this.blueball_2 = blueball_2;
	}

	public Integer getBlueball_3() {
		return blueball_3;
	}

	public void setBlueball_3(Integer blueball_3) {
		this.blueball_3 = blueball_3;
	}

	public String getPeriods() {
		return periods;
	}

	public void setPeriods(String periods) {
		this.periods = periods;
	}

	public Date getLotterydate() {
		return lotterydate;
	}

	public void setLotterydate(Date lotterydate) {
		this.lotterydate = lotterydate;
	}

}
