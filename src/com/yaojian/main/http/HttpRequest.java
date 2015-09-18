package com.yaojian.main.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;

public class HttpRequest {
	private static final String BASE_URL = "http://baidu.lecai.com/api/hao123/new_lottery_all.php";
	private static String regEx = "[\u4e00-\u9fa5]";
	private static Pattern pat = Pattern.compile(regEx);
	private static List<String> huangli = new ArrayList<String>();

	public static String executeRequest() {
		return executeRequest(BASE_URL);
	}

	public static String executeRequest(String urlString) {
		URL url = null;
		HttpURLConnection connection = null;
		InputStream in = null;
		try {
			url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			in = connection.getInputStream();
			return streamToString(in);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
		return "";
	}

	public static List<String> executeHtmlPPagerRequest(String urlString) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			huangli.clear();
			url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			Parser parser = new Parser(connection);
			NodeList list = parser.parse(null);
			for (int z = 0; z < list.size(); z++) {
				paserHtml(list.elementAt(z));
			}
			return huangli;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return huangli;
	}

	private static void paserHtml(Node node) {
		NodeList childNode = node.getChildren();
		if (childNode == null) {
			if (node.getText().trim().equals("\n")
					|| node.getText().trim().equals("")
					|| node.getText().equals("br /")
					|| !isContainsChinese(node.getText().trim())) {
				return;
			}
			System.out.println("childNode.getName:" + node.getText());
			huangli.add(node.getText().trim());
			return;
		} else {
			for (int i = 0; i < childNode.size(); i++) {
				paserHtml(childNode.elementAt(i));
			}
		}
	}

	public static boolean isContainsChinese(String str) {
		Matcher matcher = pat.matcher(str);
		boolean flg = false;
		if (matcher.find()) {
			flg = true;
		}
		return flg;
	}

	static public String streamToString(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}
}
