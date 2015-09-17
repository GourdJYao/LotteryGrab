package com.yaojian.main.dao.bean;

import java.sql.Date;

import com.yaojian.main.dao.utils.LotteryBall;

public class Lottoball extends LotteryBall {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6508038004181916823L;

	public Lottoball() {

	}

	public Lottoball(Integer redball_1, Integer redball_2, Integer redball_3,
			Integer redball_4, Integer redball_5, Integer redball_6,
			Integer redball_7, Integer redball_8, Integer redball_9,
			Integer blueball_1, Integer blueball_2, Integer blueball_3,
			String periods, Date lotterydate) {
		this.redball_1 = redball_1;
		this.redball_2 = redball_2;
		this.redball_3 = redball_3;
		this.redball_4 = redball_4;
		this.redball_5 = redball_5;
		this.redball_6 = redball_6;
		this.redball_7 = redball_7;
		this.redball_8 = redball_8;
		this.redball_9 = redball_9;
		this.blueball_1 = blueball_1;
		this.blueball_2 = blueball_2;
		this.blueball_3 = blueball_3;
		this.periods = periods;
		this.lotterydate = lotterydate;
	}

	public Lottoball getNormalRedball(Integer redball_1, Integer redball_2,
			Integer redball_3, Integer redball_4, Integer redball_5,
			Integer blueball_1, Integer blueball_2, String periods,
			Date lotterydate) {
		return new Lottoball(redball_1, redball_2, redball_3, redball_4,
				redball_5, null, null, null, null, blueball_1, blueball_2,
				null, periods, lotterydate);
	}
}
