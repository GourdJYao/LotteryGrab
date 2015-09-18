package com.yaojian.main;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import net.sf.json.JSONObject;

import org.json.JSONArray;

import com.yaojian.main.dao.DoubleballDao;
import com.yaojian.main.dao.LaoHuangLiDao;
import com.yaojian.main.dao.LottoballDao;
import com.yaojian.main.dao.bean.Doubleball;
import com.yaojian.main.dao.bean.LaoHuangLi;
import com.yaojian.main.dao.bean.Lottoball;
import com.yaojian.main.http.HttpRequest;
import com.yaojian.main.http.HttpUtils;
import com.yaojian.main.log.LogUtils;
import com.yaojian.main.utils.DateUtils;

public class HttpMain {

	private static Timer caipiaoTimer;

	private static CaipiaoTask caipiaoTask;

	private static final String LAOHUANGLI_URL = "http://apis.haoservice.com/lifeservice/laohuangli/d?date=%s&key=db14ddd8deb94c239cc796afa4011006";
	private static final String LAOHUANGLI_URL_YEARS = "http://nongli.911cha.com/%s.html";

	// private static final String LAOHUANGLI_URL_YEARS =
	// "http://v.juhe.cn/laohuangli/d?date=%s&key=7eaa94bcecc9db9b98b80f5a801cb366";

	public static void main(String[] args) {
		 startCaipiaoTask();
		// startLaohuangli();
		startLaohuangli1();
	}

	private static void startLaohuangli1() {
		try {
			LaoHuangLiDao laoHuangLiDao = new LaoHuangLiDao();
			String dateString = laoHuangLiDao.getLastLaoHuangLi();
			while (DateUtils.compareNowDate(dateString)) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");
				java.util.Date date = format.parse(dateString);
				String dateURl = String.format(LAOHUANGLI_URL_YEARS, format.format(date));
				System.out.println(dateURl);
				List<String> huangliList = HttpRequest
						.executeHtmlPPagerRequest(dateURl);
				if (huangliList != null && huangliList.size() > 0) {
					LaoHuangLi laoHuangLi = new LaoHuangLi();
					int i = 0;
					while (i < huangliList.size()) {
						String value = huangliList.get(i);
						if (laoHuangLi.getWuxing() == null
								|| laoHuangLi.getWuxing().split(",").length <= 3) {
							if (value.equals("年五行")) {
								laoHuangLi.setWuxing(huangliList.get(++i));
							}
							if (value.equals("月五行")) {
								laoHuangLi.setWuxing(laoHuangLi.getWuxing() + ","
										+ huangliList.get(++i));
							}
							if (value.equals("日五行")) {
								laoHuangLi.setWuxing(laoHuangLi.getWuxing() + ","
										+ huangliList.get(++i));
							}
							if (value.equals("时五行")) {
								laoHuangLi.setWuxing(laoHuangLi.getWuxing() + ","
										+ huangliList.get(++i));
								System.out.println("" + laoHuangLi.getWuxing());
							}
						}

						if (value.equals("公历")) {
							String gongliDateString = huangliList.get((i = i + 1));
							while (!gongliDateString.startsWith("公元")) {
								gongliDateString = huangliList.get((i = i + 1));
							}
							String[] gongliDateArray = gongliDateString.split(" ");
							gongliDateArray[0] = gongliDateArray[0]
									.replaceAll("公元", "");
							gongliDateArray[0] = gongliDateArray[0]
									.replaceAll("年", "-").replaceAll("月", "-")
									.replaceAll("日", "");
							try {
								laoHuangLi.setYangli(new java.sql.Date(format.parse(
										gongliDateArray[0]).getTime()));
								laoHuangLi.setYinli(gongliDateArray[1]);
								System.out.println("" + laoHuangLi.getYangli());
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						
						if(value.equals("农历")){
							String nongli= huangliList.get((i = i + 1));
							while(!nongli.startsWith("一")){
								nongli= huangliList.get((i = i + 1));
							}
							nongli+=huangliList.get((i = i + 1));
							nongli+=huangliList.get((i = i + 1));
							if(huangliList.get((i = i + 1)).equals("干支")){
								nongli+=","+huangliList.get((i = i + 1));
							}
							laoHuangLi.setYinli(nongli+","+laoHuangLi.getYinli());
						}
						if(value.equals("生肖")&&!laoHuangLi.getYinli().contains("属")){
							String shuxiang= huangliList.get((i = i + 1));
							while(!shuxiang.startsWith("属")){
								shuxiang= huangliList.get((i = i + 1));
							}
							laoHuangLi.setYinli(laoHuangLi.getYinli()+","+shuxiang);
						}
						
						if(value.equals("星座")){
							String xingzuo= huangliList.get((i = i + 1));
							while(!xingzuo.contains("座")){
								xingzuo= huangliList.get((i = i + 1));
							}
							laoHuangLi.setYinli(laoHuangLi.getYinli()+","+xingzuo);
							System.out.println("" + laoHuangLi.getYinli());
						}
						if(value.equals("冲煞")&&laoHuangLi.getChongsha()==null){
							String chongsha= huangliList.get((i = i + 1));
							while(!chongsha.contains("冲")){
								chongsha= huangliList.get((i = i + 1));
							}
							laoHuangLi.setChongsha(chongsha);
							System.out.println("" + laoHuangLi.getChongsha());
						}
						if(value.equals("【宜】")){
							String yi = "";
							String tempyi=huangliList.get((i = i + 1));
							while(!tempyi.contains("【忌】")){
								if(tempyi.contains("黄历")){
									tempyi=huangliList.get((i = i + 1));
									continue;
								}
								if(tempyi.length()>2){
									tempyi=huangliList.get((i = i + 1));
									continue;
								}
								if(yi.length()==0){
									yi +=tempyi;
								}else{
									yi +=","+tempyi;	
								}
								tempyi=huangliList.get((i = i + 1));
							}
							--i;
							laoHuangLi.setYi(yi);
							System.out.println("" + laoHuangLi.getYi());
						}
						if(value.equals("【忌】")){
							String ji = "";
							String tempji=huangliList.get((i = i + 1));
							while(!tempji.contains("吉神宜趋")){
								if(tempji.contains("黄历")){
									tempji=huangliList.get((i = i + 1));
									continue;
								}
								if(tempji.length()>2){
									tempji=huangliList.get((i = i + 1));
									continue;
								}
								if(ji.length()==0){
									ji +=tempji;
								}else{
									ji +=","+tempji;	
								}
								tempji=huangliList.get((i = i + 1));
							}
							--i;
							laoHuangLi.setJi(ji);
							System.out.println("" + laoHuangLi.getJi());
						}
						
						if(value.equals("吉神宜趋")){
							String jishen=huangliList.get((i = i + 1));
							while(jishen.contains("黄历")||jishen.contains("吉日")){
								jishen=huangliList.get((i = i + 1));
							}
							laoHuangLi.setJishen(jishen);
							System.out.println("" + laoHuangLi.getJishen());
						}
						if(value.equals("凶神宜忌")){
							String xiongshen=huangliList.get((i = i + 1));
							while(xiongshen.contains("黄历")||xiongshen.contains("吉日")){
								xiongshen=huangliList.get((i = i + 1));
							}
							laoHuangLi.setXiongshen(xiongshen);;
							System.out.println("" + laoHuangLi.getXiongshen());
						}
						
						if(value.equals("彭祖百忌")){
							String baiji=huangliList.get((i = i + 1));
							while(baiji.contains("黄历")||baiji.contains("吉日")||baiji.contains("acronym")){
								baiji=huangliList.get((i = i + 1));
							}
							laoHuangLi.setBaiji(baiji);
							System.out.println("" + laoHuangLi.getBaiji());
						}
						++i;
					}
				}
				dateString = format.format(new java.util.Date(
						(date.getTime() + 24 * 60 * 60 * 1000)));
			}
		} catch (ParseException e) {
			LogUtils.e("Error Date ParseException", e);
			e.printStackTrace();
		}
	}

	private static void startLaohuangli() {
		try {
			LaoHuangLiDao laoHuangLiDao = new LaoHuangLiDao();
			String dateString = laoHuangLiDao.getLastLaoHuangLi();
			while (DateUtils.compareNowDate(dateString)) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = format.parse(dateString);
				String dateURl = String.format(LAOHUANGLI_URL, dateString);
				String s = HttpRequest.executeRequest(dateURl);
				System.out.println("s:" + s);
				JSONObject jsonObject = JSONObject.fromObject(s);
				if (jsonObject != null
						&& jsonObject.getString("error_code") != null
						&& jsonObject.getString("error_code").equals("0")) {
					JSONObject resultJSONObject = jsonObject
							.getJSONObject("result");
					if (resultJSONObject != null) {
						LaoHuangLi laoHuangLi = new LaoHuangLi();
						if (resultJSONObject.getString("yangli") != null) {
							laoHuangLi.setYangli(new Date(format.parse(
									resultJSONObject.getString("yangli"))
									.getTime()));
						}
						if (resultJSONObject.getString("yinli") != null) {
							laoHuangLi.setYinli(resultJSONObject
									.getString("yinli"));
						}
						if (resultJSONObject.getString("wuxing") != null) {
							laoHuangLi.setWuxing(resultJSONObject
									.getString("wuxing"));
						}
						if (resultJSONObject.getString("chongsha") != null) {
							laoHuangLi.setChongsha(resultJSONObject
									.getString("chongsha"));
						}
						if (resultJSONObject.getString("baiji") != null) {
							laoHuangLi.setBaiji(resultJSONObject
									.getString("baiji"));
						}
						if (resultJSONObject.getString("jishen") != null) {
							laoHuangLi.setJishen(resultJSONObject
									.getString("jishen"));
						}
						if (resultJSONObject.getString("yi") != null) {
							laoHuangLi.setYi(resultJSONObject.getString("yi"));
						}
						if (resultJSONObject.getString("xiongshen") != null) {
							laoHuangLi.setXiongshen(resultJSONObject
									.getString("xiongshen"));
						}
						if (resultJSONObject.getString("ji") != null) {
							laoHuangLi.setJi(resultJSONObject.getString("ji"));
						}
						boolean isSuccess = laoHuangLiDao
								.insertLaoHuangLi(laoHuangLi);
						if (isSuccess) {
							LogUtils.i("insert Laohuangli success,date:"
									+ dateString);
							System.out
									.println("insert Laohuangli success,date:"
											+ dateString);
						} else {
							LogUtils.i("insert Laohuangli fail,date:"
									+ dateString);
							System.out.println("insert Laohuangli fail,date:"
									+ dateString);
						}
					}
				} else {
					LogUtils.i("error http JSON String,s:" + s);
					System.out.println("error http JSON String,s:" + s);
				}
				dateString = format.format(new java.util.Date(
						(date.getTime() + 24 * 60 * 60 * 1000)));
			}
		} catch (ParseException e) {
			LogUtils.e("Error Date ParseException", e);
			e.printStackTrace();
		}
	}

	private static void startCaipiaoTask() {

		if (caipiaoTimer == null) {
			caipiaoTimer = new Timer();
		}

		if (caipiaoTask == null) {
			caipiaoTask = new CaipiaoTask();
		}
		caipiaoTimer.schedule(caipiaoTask, 0, 24 * 60 * 60 * 1000);

	}

	@Override
	protected void finalize() throws Throwable {
		cannelCaipiaoTask();
		super.finalize();
	}

	private static void cannelCaipiaoTask() {
		if (caipiaoTask != null) {
			caipiaoTask.cancel();
		}
		if (caipiaoTimer != null) {
			caipiaoTimer.cancel();
		}
	}

	static class CaipiaoTask extends TimerTask {
		@Override
		public void run() {
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
