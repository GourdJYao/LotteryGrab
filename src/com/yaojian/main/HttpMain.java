package com.yaojian.main;

import java.util.Set;

import net.sf.json.JSONObject;

import com.yaojian.main.http.HttpRequest;
import com.yaojian.main.http.HttpUtils;

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
			}
		}

	}
}
