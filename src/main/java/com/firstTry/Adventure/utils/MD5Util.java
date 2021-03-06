package com.firstTry.Adventure.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5加密工具
 * 
 * @author Roger
 * @email luojie2luojuan@qq.com
 * @date 2018-3-19
 *
 */
public class MD5Util {

	public static String encode(String encryptionStr) {
		return DigestUtils.md5Hex(encryptionStr);
	}

	public static void main(String[] args) {
		System.out.println(encode("admin123456"));
	}
}
