package com.yhy.test.main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import net.sf.cglib.beans.BeanMap;

public class MainTest {
	// 初始化
	static BeanMap beanMap = null;

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 500; i++) {
			int round = (int)(Math.random()*2);
			System.out.println(round);
		}
	}

}

class Test implements Runnable {

	@Override
	public void run() {
		for (int i = 0; i < 500; i++) {
			try {
				pring();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void pring() throws Exception {
		URL url = new URL("http://localhost:8080/SC/tran/test.action");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("GET");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.connect();
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		String content = "key=j0r53nmbbd78x7m1pqml06u2&type=1&toemail=jiucool@gmail.com" + "&activatecode=" + URLEncoder.encode("久酷博客", "utf-8");
		out.writeBytes(content);
		out.flush();
		out.close();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 设置编码,否则中文乱码
		String line = "";

		while ((line = reader.readLine()) != null) {
			// line = new String(line.getBytes(), "utf-8");
			// System.out.println(line);
		}

		reader.close();
		connection.disconnect();
	}
}
