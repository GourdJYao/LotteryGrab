package com.yaojian.main;

import java.sql.Date;
import java.util.Set;

import net.sf.json.JSONObject;

import org.json.JSONArray;

import com.yaojian.main.dao.DoubleballDao;
import com.yaojian.main.dao.LottoballDao;
import com.yaojian.main.dao.bean.Doubleball;
import com.yaojian.main.dao.bean.Lottoball;
import com.yaojian.main.http.HttpRequest;
import com.yaojian.main.http.HttpUtils;
import com.yaojian.main.log.LogUtils;

public class HttpMain {

	public static void main(String[] args) {
		String s = HttpRequest.executeRequest();
		String temp = HttpUtils.decodeUnicode(s);
		temp = temp.replaceAll("Hao.caiPiaoInit\\(", "");
		temp = temp.substring(0, temp.length() - 5);
		System.out.println(temp);
		JSONObject jsonObject = JSONObject.fromObject(temp);
		JSONObject caipiaoObject = jsonObject.getJSONObject("caipiao");
		if (caipiaoObject != null) {
			Set<String> keySet = caipiaoObject.keySet();
			for (String key : keySet) {
				JSONObject tempObject = caipiaoObject.getJSONObject(key);
				String lotteryName = tempObject.getString("name");
				if (lotteryName.equals("双色球")) {
					insertDoubleBall(tempObject);
				} else if (lotteryName.equals("大乐透")) {
					insertLottoBall(tempObject);
				}
			}
		}
	}

	// 解析双色球数据并插入
	public static void insertDoubleBall(JSONObject tempObject) {
		DoubleballDao redballDao = new DoubleballDao();
		JSONArray ballArray = new JSONArray(tempObject.getJSONArray("detail"));
		Doubleball redball = new Doubleball();
		for (int i = 0; i < ballArray.length(); i++) {
			switch (i) {
			case 0:
				redball.setRedball_1(ballArray.getInt(i));
				break;
			case 1:
				redball.setRedball_2(ballArray.getInt(i));
				break;
			case 2:
				redball.setRedball_3(ballArray.getInt(i));
				break;
			case 3:
				redball.setRedball_4(ballArray.getInt(i));
				break;
			case 4:
				redball.setRedball_5(ballArray.getInt(i));
				break;
			case 5:
				redball.setRedball_6(ballArray.getInt(i));
				break;
			case 6:
				redball.setBlueball_1(ballArray.getInt(i));
				break;
			}
		}
		redball.setPeriods(tempObject.getString("phase"));
		redball.setLotterydate(new Date(tempObject.getLong("open") * 1000));
		boolean isSuccess = redballDao.insert(redball);
		if (isSuccess) {
			LogUtils.i("lottoballDao insert success lottoball Date:"
					+ redball.getLotterydate() + " Periods:"
					+ redball.getPeriods() + ",redball_1:"
					+ redball.getRedball_1() + ",redball_2:"
					+ redball.getRedball_2() + ",redball_3:"
					+ redball.getRedball_3() + ",redball_4:"
					+ redball.getRedball_4() + ",redball_5:"
					+ redball.getRedball_5() + ",redball_6"
					+ redball.getRedball_6() + ",blueball_1"
					+ redball.getBlueball_1());
		} else {
			LogUtils.i("lottoballDao insert fail lottoball Date:"
					+ redball.getLotterydate() + " Periods:"
					+ redball.getPeriods() + ",redball_1:"
					+ redball.getRedball_1() + ",redball_2:"
					+ redball.getRedball_2() + ",redball_3:"
					+ redball.getRedball_3() + ",redball_4:"
					+ redball.getRedball_4() + ",redball_5:"
					+ redball.getRedball_5() + ",redball_6"
					+ redball.getRedball_6() + ",blueball_1"
					+ redball.getBlueball_1());
		}
	}

	// 解析大乐透并插入
	public static void insertLottoBall(JSONObject tempObject) {
		LottoballDao lottoballDao = new LottoballDao();
		JSONArray ballArray = new JSONArray(tempObject.getJSONArray("detail"));
		Lottoball lottoball = new Lottoball();
		for (int i = 0; i < ballArray.length(); i++) {
			switch (i) {
			case 0:
				lottoball.setRedball_1(ballArray.getInt(i));
				break;
			case 1:
				lottoball.setRedball_2(ballArray.getInt(i));
				break;
			case 2:
				lottoball.setRedball_3(ballArray.getInt(i));
				break;
			case 3:
				lottoball.setRedball_4(ballArray.getInt(i));
				break;
			case 4:
				lottoball.setRedball_5(ballArray.getInt(i));
				break;
			case 5:
				lottoball.setBlueball_1(ballArray.getInt(i));
				break;
			case 6:
				lottoball.setBlueball_2(ballArray.getInt(i));
				break;
			}
		}
		lottoball.setPeriods(tempObject.getString("phase"));
		lottoball.setLotterydate(new Date(tempObject.getLong("open") * 1000));
		boolean isSuccess = lottoballDao.insert(lottoball);
		if (isSuccess) {
			LogUtils.i("lottoballDao insert success lottoball Date:"
					+ lottoball.getLotterydate() + " Periods:"
					+ lottoball.getPeriods() + ",redball_1:"
					+ lottoball.getRedball_1() + ",redball_2:"
					+ lottoball.getRedball_2() + ",redball_3:"
					+ lottoball.getRedball_3() + ",redball_4:"
					+ lottoball.getRedball_4() + ",redball_5:"
					+ lottoball.getRedball_5() + ",blueball_1"
					+ lottoball.getBlueball_1() + ",blueball_2"
					+ lottoball.getBlueball_2());
		} else {
			LogUtils.i("lottoballDao insert fail lottoball Date:"
					+ lottoball.getLotterydate() + " Periods:"
					+ lottoball.getPeriods() + ",redball_1:"
					+ lottoball.getRedball_1() + ",redball_2:"
					+ lottoball.getRedball_2() + ",redball_3:"
					+ lottoball.getRedball_3() + ",redball_4:"
					+ lottoball.getRedball_4() + ",redball_5:"
					+ lottoball.getRedball_5() + ",blueball_1"
					+ lottoball.getBlueball_1() + ",blueball_2"
					+ lottoball.getBlueball_2());
		}
	}
}
