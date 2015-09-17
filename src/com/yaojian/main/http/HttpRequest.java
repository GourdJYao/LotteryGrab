package com.yaojian.main.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {
	private static final String BASE_URL = "http://baidu.lecai.com/api/hao123/new_lottery_all.php";

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

	static public String streamToString(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}
}
