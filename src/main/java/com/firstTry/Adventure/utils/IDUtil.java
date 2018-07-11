package com.firstTry.Adventure.utils;

import java.util.UUID;

public class IDUtil {
	/**
	 * 生成UUID，并去除横杆
	 * @author Roger
	 * @email luojie2luojuan@qq.com
	 * @date 2018-3-19
	 * @return UUID String
	 */
	public static String makeUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

	public static void main(String[] args) {
			System.out.println(makeUUID());
	}
}
