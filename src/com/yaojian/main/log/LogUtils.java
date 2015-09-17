package com.yaojian.main.log;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogUtils {
	private static Logger logger = null;
	static {
		PropertyConfigurator.configure(".\\src\\log4j.properties");
		logger = Logger.getLogger(LogUtils.class.getName());
	}

	public static void i(String info) {
		logger.info(info);
	}

	public static void i(String info, Throwable e) {
		logger.info(info, e);
	}

	public static void w(String info) {
		logger.warn(info);
	}

	public static void w(String info, Throwable e) {
		logger.warn(info, e);
	}

	public static void e(String info) {
		logger.error(info);
	}

	public static void e(String info, Throwable e) {
		logger.error(info, e);
	}

	public static void f(String info) {
		logger.fatal(info);
	}

	public static void f(String info, Throwable e) {
		logger.fatal(info, e);
	}

	public static void d(String info) {
		logger.debug(info);
	}

	public static void d(String info, Throwable e) {
		logger.debug(info, e);
	}

}
