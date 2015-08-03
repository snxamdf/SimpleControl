package com.yhy.test.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.cglib.beans.BeanMap;

public class MainTest {
	// 初始化
	static BeanMap beanMap = null;

	public static void main(String[] args) throws Exception {
		String s = "${cc.cc.dd}";
		String regex = "\\$\\{(.*)\\}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(s);
		if (matcher.matches()) {
			String group = matcher.group(1);
			System.out.println(group);
		} else {
			System.out.println("no matches!!");
		}

	}

	public static void pring() {

	}
}
