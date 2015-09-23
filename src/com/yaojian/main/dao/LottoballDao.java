package com.yaojian.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yaojian.main.dao.bean.LaoHuangLi;
import com.yaojian.main.dao.bean.Lottoball;
import com.yaojian.main.dao.utils.DBUtil;
import com.yaojian.main.log.LogUtils;

public class LottoballDao {

	public List<Lottoball> getLottoballListByYi(String yiString) {
		List<Lottoball> lottoballList = null;
		DBUtil util = new DBUtil();
		Connection connetion = util.openConnection();
		String sql = "SELECT ll.id,ll.redball_1,ll.redball_2,ll.redball_3,ll.redball_4,"
				+ "ll.redball_5,ll.redball_6,ll.redball_7,ll.redball_8,ll.redball_9,ll.blueball_1,"
				+ "ll.blueball_2,ll.blueball_3,ll.periods,ll.lotterydate,"
				+ "lhl.id,lhl.yangli,lhl.yinli,lhl.wuxing,lhl.chongsha,"
				+ "lhl.baiji,lhl.jishen,lhl.yi,lhl.xiongshen,lhl.ji "
				+ "FROM laohuangli lhl,lottolottery ll "
				+ "WHERE ll.lotterydate=lhl.yangli AND lhl.yi LIKE ? ;";
		System.out.println("sql:" + sql);
		PreparedStatement preparedStatement = null;
		ResultSet resulte = null;
		try {
			preparedStatement = connetion.prepareStatement(sql);
			preparedStatement.setString(1, "%" + yiString + "%");
			resulte = preparedStatement.executeQuery();
			if (resulte == null || !resulte.next()) {
				return lottoballList;
			} else {
				lottoballList = new ArrayList<Lottoball>();
				do {
					LaoHuangLi laoHuangLi = new LaoHuangLi();
					Lottoball lottoball = new Lottoball();
					lottoball.setId(resulte.getInt(1));
					lottoball.setRedball_1(resulte.getInt(2));
					lottoball.setRedball_2(resulte.getInt(3));
					lottoball.setRedball_3(resulte.getInt(4));
					lottoball.setRedball_4(resulte.getInt(5));
					lottoball.setRedball_5(resulte.getInt(6));
					lottoball.setRedball_6(resulte.getInt(7));
					lottoball.setRedball_7(resulte.getInt(8));
					lottoball.setRedball_8(resulte.getInt(9));
					lottoball.setRedball_9(resulte.getInt(10));
					lottoball.setBlueball_1(resulte.getInt(11));
					lottoball.setBlueball_2(resulte.getInt(12));
					lottoball.setBlueball_3(resulte.getInt(13));
					lottoball.setPeriods(resulte.getString(14));
					lottoball.setLotterydate(resulte.getDate(15));
					laoHuangLi.setId(resulte.getInt(16));
					laoHuangLi.setYangli(resulte.getDate(17));
					laoHuangLi.setYinli(resulte.getString(18));
					laoHuangLi.setWuxing(resulte.getString(19));
					laoHuangLi.setChongsha(resulte.getString(20));
					laoHuangLi.setBaiji(resulte.getString(21));
					laoHuangLi.setJishen(resulte.getString(22));
					laoHuangLi.setYi(resulte.getString(23));
					laoHuangLi.setXiongshen(resulte.getString(24));
					laoHuangLi.setJi(resulte.getString(25));
					lottoball.setLaohuangli(laoHuangLi);
					lottoballList.add(lottoball);
				} while (resulte.next());
			}
		} catch (SQLException e) {
			LogUtils.e("SQLException:", e);
			e.printStackTrace();
		} finally {
			try {
				if (resulte != null) {
					resulte.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				util.closeConn(connetion);
			} catch (Exception e) {
				LogUtils.e("Exception:", e);
				e.printStackTrace();
			}
		}
		return lottoballList;
	}

	public boolean insert(Lottoball lottoball) {
		DBUtil util = new DBUtil();
		Connection connetion = util.openConnection();
		String sql = "SELECT count(id) FROM lottolottery WHERE periods=?";
		PreparedStatement preparedStatement = null;
		ResultSet resulte = null;
		try {
			preparedStatement = connetion.prepareStatement(sql);
			preparedStatement.setString(1, lottoball.getPeriods());
			resulte = preparedStatement.executeQuery();
			if (resulte == null || !resulte.next()) {
				preparedStatement.close();
				// TODO 插入
				sql = "INSERT INTO lottolottery(redball_1,redball_2,redball_3,redball_4,redball_5,blueball_1,blueball_2,periods,lotterydate) VALUES (?,?,?,?,?,?,?,?,?)";
				preparedStatement = connetion.prepareStatement(sql);
				return insertData(preparedStatement, lottoball);
			} else {
				if (resulte.first()) {
					int countInt = resulte.getInt(1);
					if (countInt <= 0) {
						preparedStatement.close();
						sql = "INSERT INTO lottolottery(redball_1,redball_2,redball_3,redball_4,redball_5,blueball_1,blueball_2,periods,lotterydate) VALUES (?,?,?,?,?,?,?,?,?)";
						preparedStatement = connetion.prepareStatement(sql);
						return insertData(preparedStatement, lottoball);
					}
				}
			}
		} catch (SQLException e) {
			LogUtils.e("SQLException:", e);
			e.printStackTrace();
		} finally {
			try {
				if (resulte != null) {
					resulte.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				util.closeConn(connetion);
			} catch (Exception e) {
				LogUtils.e("Exception:", e);
				e.printStackTrace();
			}
		}

		return false;
	}

	private boolean insertData(PreparedStatement preparedStatement,
			Lottoball lottoball) throws SQLException {
		preparedStatement.setInt(1, lottoball.getRedball_1());
		preparedStatement.setInt(2, lottoball.getRedball_2());
		preparedStatement.setInt(3, lottoball.getRedball_3());
		preparedStatement.setInt(4, lottoball.getRedball_4());
		preparedStatement.setInt(5, lottoball.getRedball_5());
		preparedStatement.setInt(6, lottoball.getBlueball_1());
		preparedStatement.setInt(7, lottoball.getBlueball_2());
		preparedStatement.setString(8, lottoball.getPeriods());
		preparedStatement.setDate(9, lottoball.getLotterydate());
		return preparedStatement.execute();
	}
}
