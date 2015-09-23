package com.yaojian.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yaojian.main.dao.DoubleballDao;
import com.yaojian.main.dao.LaoHuangLiDao;
import com.yaojian.main.dao.LottoballDao;
import com.yaojian.main.dao.bean.Doubleball;
import com.yaojian.main.dao.bean.LaoHuangLi;
import com.yaojian.main.dao.bean.LotteryBall;
import com.yaojian.main.dao.bean.Lottoball;

public class AnalysisMain {

	private static List<Integer> ballNumberList = new ArrayList<Integer>();

	public static void main(String args[]) {
		Calendar calendar = Calendar.getInstance();
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.MONDAY:
		case Calendar.WEDNESDAY:
		case Calendar.SATURDAY:
			System.out.println("=================今天是大乐透开奖推荐==================");
			getLottoBallZouShi();
			break;
		case Calendar.TUESDAY:
		case Calendar.THURSDAY:
		case Calendar.SUNDAY:
			System.out.println("=================今天是双色球开奖推荐==================");
			getDoubleBallZoushi();
			break;
		}
	}

	private static void getDoubleBallZoushi() {
		LaoHuangLiDao laoHuangLiDao = new LaoHuangLiDao();
		LaoHuangLi laoHuangLi = laoHuangLiDao.getTodayLaohuangli();
		Map<String, LotteryBall> lotteryBallMap = new HashMap<String, LotteryBall>();
		if (laoHuangLi != null) {
			if (laoHuangLi.getYi() != null) {
				DoubleballDao doubleballDao = new DoubleballDao();
				String yiString = laoHuangLi.getYi();
				String[] yiStringArray = yiString.split(",");
				for (String tempString : yiStringArray) {
					List<Doubleball> doubleballList = doubleballDao
							.getDoubleballListByYi(tempString);
					for (Doubleball doubleball : doubleballList) {
						lotteryBallMap.put(doubleball.getPeriods(), doubleball);
					}
				}
				initDoubleBallNumber();
				for (String key : lotteryBallMap.keySet()) {
					Doubleball doubleball = (Doubleball) lotteryBallMap
							.get(key);
					getDoubleBallNumber(doubleball);
				}
				for (int i = 0; i < ballNumberList.size(); i++) {
					if (i < 33) {
						System.out.println((i + 1) + ":"
								+ ballNumberList.get(i));
					} else {
						System.out.println((i - 32) + ":"
								+ ballNumberList.get(i));
					}
				}
			} else {
				System.out.println("今天没有相关[宜]事项！");
			}
		} else {
			System.out.println("未查询到今天的老黄历");
		}

	}

	private static void getLottoBallZouShi() {
		LaoHuangLiDao laoHuangLiDao = new LaoHuangLiDao();
		LaoHuangLi laoHuangLi = laoHuangLiDao.getTodayLaohuangli();
		Map<String, LotteryBall> lotteryBallMap = new HashMap<String, LotteryBall>();
		if (laoHuangLi != null) {
			if (laoHuangLi.getYi() != null) {
				LottoballDao lottoballDao = new LottoballDao();
				String yiString = laoHuangLi.getYi();
				String[] yiStringArray = yiString.split(",");
				for (String tempString : yiStringArray) {
					List<Lottoball> lottoballList = lottoballDao
							.getLottoballListByYi(tempString);
					for (Lottoball lottoball : lottoballList) {
						lotteryBallMap.put(lottoball.getPeriods(), lottoball);
					}
				}
				initLottoBallNumber();
				for (String key : lotteryBallMap.keySet()) {
					Lottoball lottoball = (Lottoball) lotteryBallMap.get(key);
					getLottoBallNumber(lottoball);
				}
				for (int i = 0; i < ballNumberList.size(); i++) {
					if (i < 35) {
						System.out.println((i + 1) + ":"
								+ ballNumberList.get(i));
					} else {
						System.out.println((i - 34) + ":"
								+ ballNumberList.get(i));
					}
				}
			} else {
				System.out.println("今天没有相关[宜]事项！");
			}
		} else {
			System.out.println("未查询到今天的老黄历");
		}
	}

	private static void getDoubleBallNumber(Doubleball doubleball) {
		getRedBallNumber(doubleball.getRedball_1());
		getRedBallNumber(doubleball.getRedball_2());
		getRedBallNumber(doubleball.getRedball_3());
		getRedBallNumber(doubleball.getRedball_4());
		getRedBallNumber(doubleball.getRedball_5());
		getRedBallNumber(doubleball.getRedball_6());
		getRedBallNumber(doubleball.getRedball_7());
		getRedBallNumber(doubleball.getRedball_8());
		getRedBallNumber(doubleball.getRedball_9());
		getBlueBallNumber(doubleball.getBlueball_1(), true);
		getBlueBallNumber(doubleball.getBlueball_2(), true);
		getBlueBallNumber(doubleball.getBlueball_3(), true);

	}

	private static void getLottoBallNumber(Lottoball lottoball) {
		getRedBallNumber(lottoball.getRedball_1());
		getRedBallNumber(lottoball.getRedball_2());
		getRedBallNumber(lottoball.getRedball_3());
		getRedBallNumber(lottoball.getRedball_4());
		getRedBallNumber(lottoball.getRedball_5());
		getRedBallNumber(lottoball.getRedball_6());
		getRedBallNumber(lottoball.getRedball_7());
		getRedBallNumber(lottoball.getRedball_8());
		getRedBallNumber(lottoball.getRedball_9());
		getBlueBallNumber(lottoball.getBlueball_1(), false);
		getBlueBallNumber(lottoball.getBlueball_2(), false);
		getBlueBallNumber(lottoball.getBlueball_3(), false);

	}

	private static void getRedBallNumber(Integer ballNumber) {
		if (ballNumber == null) {
			return;
		}
		switch (ballNumber) {
		case 1:
			ballNumberList.set(0, (ballNumberList.get(0) + 1));
			break;
		case 2:
			ballNumberList.set(1, (ballNumberList.get(1) + 1));
			break;
		case 3:
			ballNumberList.set(2, (ballNumberList.get(2) + 1));
			break;
		case 4:
			ballNumberList.set(3, (ballNumberList.get(3) + 1));
			break;
		case 5:
			ballNumberList.set(4, (ballNumberList.get(4) + 1));
			break;
		case 6:
			ballNumberList.set(5, (ballNumberList.get(5) + 1));
			break;
		case 7:
			ballNumberList.set(6, (ballNumberList.get(6) + 1));
			break;
		case 8:
			ballNumberList.set(7, (ballNumberList.get(7) + 1));
			break;
		case 9:
			ballNumberList.set(8, (ballNumberList.get(8) + 1));
			break;
		case 10:
			ballNumberList.set(9, (ballNumberList.get(9) + 1));
			break;
		case 11:
			ballNumberList.set(10, (ballNumberList.get(10) + 1));
			break;
		case 12:
			ballNumberList.set(11, (ballNumberList.get(11) + 1));
			break;
		case 13:
			ballNumberList.set(12, (ballNumberList.get(12) + 1));
			break;
		case 14:
			ballNumberList.set(13, (ballNumberList.get(13) + 1));
			break;
		case 15:
			ballNumberList.set(14, (ballNumberList.get(14) + 1));
			break;
		case 16:
			ballNumberList.set(15, (ballNumberList.get(15) + 1));
			break;
		case 17:
			ballNumberList.set(16, (ballNumberList.get(16) + 1));
			break;
		case 18:
			ballNumberList.set(17, (ballNumberList.get(17) + 1));
			break;
		case 19:
			ballNumberList.set(18, (ballNumberList.get(18) + 1));
			break;
		case 20:
			ballNumberList.set(19, (ballNumberList.get(19) + 1));
			break;
		case 21:
			ballNumberList.set(20, (ballNumberList.get(20) + 1));
			break;
		case 22:
			ballNumberList.set(21, (ballNumberList.get(21) + 1));
			break;
		case 23:
			ballNumberList.set(22, (ballNumberList.get(22) + 1));
			break;
		case 24:
			ballNumberList.set(23, (ballNumberList.get(23) + 1));
			break;
		case 25:
			ballNumberList.set(24, (ballNumberList.get(24) + 1));
			break;
		case 26:
			ballNumberList.set(25, (ballNumberList.get(25) + 1));
			break;
		case 27:
			ballNumberList.set(26, (ballNumberList.get(26) + 1));
			break;
		case 28:
			ballNumberList.set(27, (ballNumberList.get(27) + 1));
			break;
		case 29:
			ballNumberList.set(28, (ballNumberList.get(28) + 1));
			break;
		case 30:
			ballNumberList.set(29, (ballNumberList.get(29) + 1));
			break;
		case 31:
			ballNumberList.set(30, (ballNumberList.get(30) + 1));
			break;
		case 32:
			ballNumberList.set(31, (ballNumberList.get(31) + 1));
			break;
		case 33:
			ballNumberList.set(32, (ballNumberList.get(32) + 1));
			break;
		case 34:
			ballNumberList.set(33, (ballNumberList.get(33) + 1));
			break;
		case 35:
			ballNumberList.set(34, (ballNumberList.get(34) + 1));
			break;
		}
	}

	private static void getBlueBallNumber(Integer ballNumber, boolean isDouble) {
		if (ballNumber == null) {
			return;
		}
		switch (ballNumber) {
		case 1:
			if (isDouble) {
				ballNumberList.set(33, (ballNumberList.get(33) + 1));
			} else {
				ballNumberList.set(35, (ballNumberList.get(35) + 1));
			}
			break;
		case 2:
			if (isDouble) {
				ballNumberList.set(34, (ballNumberList.get(34) + 1));
			} else {
				ballNumberList.set(36, (ballNumberList.get(36) + 1));
			}
			break;
		case 3:
			if (isDouble) {
				ballNumberList.set(35, (ballNumberList.get(35) + 1));
			} else {
				ballNumberList.set(37, (ballNumberList.get(37) + 1));
			}
			break;
		case 4:
			if (isDouble) {
				ballNumberList.set(36, (ballNumberList.get(36) + 1));
			} else {
				ballNumberList.set(38, (ballNumberList.get(38) + 1));
			}
			break;
		case 5:
			if (isDouble) {
				ballNumberList.set(37, (ballNumberList.get(37) + 1));
			} else {
				ballNumberList.set(39, (ballNumberList.get(39) + 1));
			}
			break;
		case 6:
			if (isDouble) {
				ballNumberList.set(38, (ballNumberList.get(38) + 1));
			} else {
				ballNumberList.set(40, (ballNumberList.get(40) + 1));
			}
			break;
		case 7:
			if (isDouble) {
				ballNumberList.set(39, (ballNumberList.get(39) + 1));
			} else {
				ballNumberList.set(41, (ballNumberList.get(41) + 1));
			}
			break;
		case 8:
			if (isDouble) {
				ballNumberList.set(40, (ballNumberList.get(40) + 1));
			} else {
				ballNumberList.set(42, (ballNumberList.get(42) + 1));
			}
			break;
		case 9:
			if (isDouble) {
				ballNumberList.set(41, (ballNumberList.get(41) + 1));
			} else {
				ballNumberList.set(43, (ballNumberList.get(43) + 1));
			}
			break;
		case 10:
			if (isDouble) {
				ballNumberList.set(42, (ballNumberList.get(42) + 1));
			} else {
				ballNumberList.set(44, (ballNumberList.get(44) + 1));
			}
			break;
		case 11:
			if (isDouble) {
				ballNumberList.set(43, (ballNumberList.get(43) + 1));
			} else {
				ballNumberList.set(45, (ballNumberList.get(45) + 1));
			}
			break;
		case 12:
			if (isDouble) {
				ballNumberList.set(44, (ballNumberList.get(44) + 1));
			} else {
				ballNumberList.set(46, (ballNumberList.get(46) + 1));
			}
			break;
		case 13:
			if (isDouble) {
				ballNumberList.set(45, (ballNumberList.get(45) + 1));
			}
			break;
		case 14:
			if (isDouble) {
				ballNumberList.set(46, (ballNumberList.get(46) + 1));
			}
			break;
		case 15:
			if (isDouble) {
				ballNumberList.set(47, (ballNumberList.get(47) + 1));
			}
			break;
		case 16:
			if (isDouble) {
				ballNumberList.set(48, (ballNumberList.get(48) + 1));
			}
			break;
		}
	}

	private static void initDoubleBallNumber() {
		ballNumberList = new ArrayList<Integer>();
		for (int i = 0; i < 49; i++) {
			ballNumberList.add(0);
		}
	}

	private static void initLottoBallNumber() {
		ballNumberList = new ArrayList<Integer>();
		for (int i = 0; i < 47; i++) {
			ballNumberList.add(0);
		}
	}
}
