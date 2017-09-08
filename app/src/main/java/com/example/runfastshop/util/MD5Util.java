package com.example.runfastshop.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MD5Util {
	public static String MD5(String inStr)
	{
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		byte[] byteArray = null;
		try {
			byteArray = inStr.getBytes("gbk");
		} catch (UnsupportedEncodingException e) {
			return null;
		}

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = md5Bytes[i] & 0xFF;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}


//	public static String getMD5ForWX(String s) {
//
//		StringBuffer sbuf = new StringBuffer();
//		try {
//			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
//			mdTemp.update(s.getBytes("utf-8"));
//			byte[] buf = mdTemp.digest();
//			for (byte b : buf) {
//				sbuf.append(Integer.toHexString(b >> 4 & 0xF).toUpperCase());
//				sbuf.append(Integer.toHexString(b & 0xF).toUpperCase());
//			}
//		} catch (NoSuchAlgorithmException ex) {
//			Logger.getLogger(ComUtil.class.getName()).log(Level.SEVERE, null,
//					ex);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		return sbuf.toString();
//	}
}
