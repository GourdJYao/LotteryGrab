package com.yaojian.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yaojian.main.dao.bean.Doubleball;
import com.yaojian.main.dao.bean.LaoHuangLi;
import com.yaojian.main.dao.utils.DBUtil;
import com.yaojian.main.log.LogUtils;

public class DoubleballDao {

	public List<Doubleball> getDoubleballListByYi(String yiString) {
		List<Doubleball> doubleballList = null;
		DBUtil util = new DBUtil();
		Connection connetion = util.openConnection();
		String sql = "SELECT dl.id,dl.redball_1,dl.redball_2,dl.redball_3,dl.redball_4,"
				+ "dl.redball_5,dl.redball_6,dl.redball_7,dl.redball_8,dl.redball_9,dl.blueball_1,"
				+ "dl.blueball_2,dl.blueball_3,dl.periods,dl.lotterydate,"
				+ "lhl.id,lhl.yangli,lhl.yinli,lhl.wuxing,lhl.chongsha,"
				+ "lhl.baiji,lhl.jishen,lhl.yi,lhl.xiongshen,lhl.ji "
				+ "FROM laohuangli lhl,doublelottery dl "
				+ "WHERE dl.lotterydate=lhl.yangli AND lhl.yi LIKE ?;";
		PreparedStatement preparedStatement = null;
		ResultSet resulte = null;
		try {
			preparedStatement = connetion.prepareStatement(sql);
			preparedStatement.setString(1, "%" + yiString + "%");
			resulte = preparedStatement.executeQuery();
			if (resulte == null || !resulte.next()) {
				return doubleballList;
			} else {
				doubleballList = new ArrayList<Doubleball>();
				do {
					LaoHuangLi laoHuangLi = new LaoHuangLi();
					Doubleball doubleball = new Doubleball();
					doubleball.setId(resulte.getInt(1));
					doubleball.setRedball_1(resulte.getInt(2));
					doubleball.setRedball_2(resulte.getInt(3));
					doubleball.setRedball_3(resulte.getInt(4));
					doubleball.setRedball_4(resulte.getInt(5));
					doubleball.setRedball_5(resulte.getInt(6));
					doubleball.setRedball_6(resulte.getInt(7));
					doubleball.setRedball_7(resulte.getInt(8));
					doubleball.setRedball_8(resulte.getInt(9));
					doubleball.setRedball_9(resulte.getInt(10));
					doubleball.setBlueball_1(resulte.getInt(11));
					doubleball.setBlueball_2(resulte.getInt(12));
					doubleball.setBlueball_3(resulte.getInt(13));
					doubleball.setPeriods(resulte.getString(14));
					doubleball.setLotterydate(resulte.getDate(15));
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
					doubleball.setLaohuangli(laoHuangLi);
					doubleballList.add(doubleball);
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
		return doubleballList;
	}

	public boolean insert(Doubleball redball) {
		DBUtil util = new DBUtil();
		Connection connetion = util.openConnection();
		String sql = "SELECT count(id) FROM doublelottery WHERE periods=?";
		PreparedStatement preparedStatement = null;
		ResultSet resulte = null;
		try {
			preparedStatement = connetion.prepareStatement(sql);
			preparedStatement.setString(1, redball.getPeriods());
			resulte = preparedStatement.executeQuery();
			if (resulte == null || !resulte.next()) {
				preparedStatement.close();
				// TODO 插入
				sql = "INSERT INTO doublelottery(redball_1,redball_2,redball_3,redball_4,redball_5,redball_6,blueball_1,periods,lotterydate) VALUES (?,?,?,?,?,?,?,?,?)";
				preparedStatement = connetion.prepareStatement(sql);
				return insertData(preparedStatement, redball);
			} else {
				if (resulte.first()) {
					int countInt = resulte.getInt(1);
					if (countInt <= 0) {
						preparedStatement.close();
						sql = "INSERT INTO doublelottery(redball_1,redball_2,redball_3,redball_4,redball_5,redball_6,blueball_1,periods,lotterydate) VALUES (?,?,?,?,?,?,?,?,?)";
						preparedStatement = connetion.prepareStatement(sql);
						return insertData(preparedStatement, redball);
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
			Doubleball redball) throws SQLException {
		preparedStatement.setInt(1, redball.getRedball_1());
		preparedStatement.setInt(2, redball.getRedball_2());
		preparedStatement.setInt(3, redball.getRedball_3());
		preparedStatement.setInt(4, redball.getRedball_4());
		preparedStatement.setInt(5, redball.getRedball_5());
		preparedStatement.setInt(6, redball.getRedball_6());
		preparedStatement.setInt(7, redball.getBlueball_1());
		preparedStatement.setString(8, redball.getPeriods());
		preparedStatement.setDate(9, redball.getLotterydate());
		return preparedStatement.execute();
	}
}
