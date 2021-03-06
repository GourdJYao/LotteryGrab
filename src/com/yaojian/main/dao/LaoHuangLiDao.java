package com.yaojian.main.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.yaojian.main.dao.bean.LaoHuangLi;
import com.yaojian.main.dao.utils.DBUtil;
import com.yaojian.main.log.LogUtils;

public class LaoHuangLiDao {

	public LaoHuangLi getTodayLaohuangli() {
		DBUtil util = new DBUtil();
		Connection connetion = util.openConnection();
		String sql = "SELECT id,yangli,yinli,wuxing,chongsha,baiji,jishen,yi,xiongshen,ji FROM laohuangli WHERE yangli=?;";
		PreparedStatement preparedStatement = null;
		ResultSet resulte = null;
		try {
			preparedStatement = connetion.prepareStatement(sql);
			preparedStatement.setDate(1, new Date(System.currentTimeMillis()));
			resulte = preparedStatement.executeQuery();
			if (resulte == null || !resulte.next()) {
				return null;
			} else {
				if (resulte.first()) {
					LaoHuangLi laoHuangLi = new LaoHuangLi();
					laoHuangLi.setId(resulte.getInt(1));
					laoHuangLi.setYangli(resulte.getDate(2));
					laoHuangLi.setYinli(resulte.getString(3));
					laoHuangLi.setWuxing(resulte.getString(4));
					laoHuangLi.setChongsha(resulte.getString(5));
					laoHuangLi.setBaiji(resulte.getString(6));
					laoHuangLi.setJishen(resulte.getString(7));
					laoHuangLi.setYi(resulte.getString(8));
					laoHuangLi.setXiongshen(resulte.getString(9));
					laoHuangLi.setJi(resulte.getString(10));
					return laoHuangLi;
				}
				return null;
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
		return null;
	}

	public String getLastLaoHuangLi() {
		DBUtil util = new DBUtil();
		Connection connetion = util.openConnection();
		String sql = "SELECT yangli FROM laohuangli ORDER BY yangli DESC";
		PreparedStatement preparedStatement = null;
		ResultSet resulte = null;
		try {
			preparedStatement = connetion.prepareStatement(sql);
			resulte = preparedStatement.executeQuery();
			if (resulte == null || !resulte.next()) {
				return "1915-01-01";
			} else {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				return format.format(resulte.getDate(1));
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
		return "1915-01-01";
	}

	public boolean insertLaoHuangLi(LaoHuangLi laoHuangLi) {
		DBUtil util = new DBUtil();
		Connection connetion = util.openConnection();
		String sql = "SELECT count(id) FROM laohuangli WHERE yangli=?;";
		PreparedStatement preparedStatement = null;
		ResultSet resulte = null;
		try {
			preparedStatement = connetion.prepareStatement(sql);
			preparedStatement.setDate(1, laoHuangLi.getYangli());
			resulte = preparedStatement.executeQuery();
			if (resulte == null || !resulte.next()) {
				preparedStatement.close();
				sql = "INSERT INTO laohuangli(yangli,yinli,wuxing,chongsha,baiji,jishen,yi,xiongshen,ji) VALUES (?,?,?,?,?,?,?,?,?)";
				preparedStatement = connetion.prepareStatement(sql);
				return insertData(preparedStatement, laoHuangLi);
			} else {
				if (resulte.first()) {
					int countInt = resulte.getInt(1);
					if (countInt <= 0) {
						preparedStatement.close();
						sql = "INSERT INTO laohuangli(yangli,yinli,wuxing,chongsha,baiji,jishen,yi,xiongshen,ji) VALUES (?,?,?,?,?,?,?,?,?)";
						preparedStatement = connetion.prepareStatement(sql);
						return insertData(preparedStatement, laoHuangLi);
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
			LaoHuangLi laoHuangLi) throws SQLException {
		preparedStatement.setDate(1, laoHuangLi.getYangli());
		preparedStatement.setString(2, laoHuangLi.getYinli());
		preparedStatement.setString(3, laoHuangLi.getWuxing());
		preparedStatement.setString(4, laoHuangLi.getChongsha());
		preparedStatement.setString(5, laoHuangLi.getBaiji());
		preparedStatement.setString(6, laoHuangLi.getJishen());
		preparedStatement.setString(7, laoHuangLi.getYi());
		preparedStatement.setString(8, laoHuangLi.getXiongshen());
		preparedStatement.setString(9, laoHuangLi.getJi());
		return preparedStatement.execute();

	}
}
