/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-20 下午04:32:40
 */
package com.zz91.util.lang;

/**
 *
 * @author Ryan(rxm1025@gmail.com)
 *
 */
public class EscapeUnescape {
	public static String escape(String src) {
		if (src != null) {
			int i;
			char j;
			StringBuffer tmp = new StringBuffer();
			tmp.ensureCapacity(src.length() * 6);
			for (i = 0; i < src.length(); i++) {
				j = src.charAt(i);
				if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
					tmp.append(j);
				else if (j < 256) {
					tmp.append("%");
					if (j < 16)
						tmp.append("0");
					tmp.append(Integer.toString(j, 16));
				} else {
					tmp.append("%u");
					tmp.append(Integer.toString(j, 16));
				}
			}
			return replaceToFalseChars(tmp.toString());
		} else {
			return null;
		}
	}

	public static String unescape(String src) {
		if (src != null) {
			src=replaceToTrueChars(src);
			StringBuffer tmp = new StringBuffer();
			tmp.ensureCapacity(src.length());
			int lastPos = 0, pos = 0;
			char ch;
			while (lastPos < src.length()) {
				pos = src.indexOf("%", lastPos);
				if (pos == lastPos) {
					if (src.charAt(pos + 1) == 'u') {
						ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
						tmp.append(ch);
						lastPos = pos + 6;
					} else {
						ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
						tmp.append(ch);
						lastPos = pos + 3;
					}
				} else {
					if (pos == -1) {
						tmp.append(src.substring(lastPos));
						lastPos = src.length();
					} else {
						tmp.append(src.substring(lastPos, pos));
						lastPos = pos;
					}
				}
			}
			return tmp.toString();
		} else {
			return null;
		}
	}

	/**
	 * @disc 对字符串重新编码
	 * @param src
	 * @return
	 */
	public static String isoToGB(String src) {
		String strRet = null;
		try {
			strRet = new String(src.getBytes("ISO_8859_1"), "GB2312");
		} catch (Exception e) {

		}
		return strRet;
	}

	/**
	 * @disc 对字符串重新编码
	 * @param src
	 * @return
	 */
	public static String isoToUTF(String src) {
		String strRet = null;
		try {
			strRet = new String(src.getBytes("ISO_8859_1"), "UTF-8");
		} catch (Exception e) {

		}
		return strRet;
	}

	private static String replaceToTrueChars(String s) {
		s = s.replace("asto", "%");
		s = s.replace("xie", "\\");
		s = s.replace("fang", "/");
		s = s.replace("xiao", "+");
		s = s.replace("ming", "-");
		return s;
	}

	private static String replaceToFalseChars(String s) {
		s = s.replace("%", "asto");
		s = s.replace("\\", "xie");
		s = s.replace("/", "fang");
		s = s.replace("+", "xiao");
		s = s.replace("-", "ming");
		return s;
	}

	public static void main(String[] args) {
		String tmp = "中文";
		System.out.println("testing escape : " + tmp);
		tmp = escape(tmp);
		System.out.println(tmp);
		System.out.println("testing unescape :" + tmp);
		System.out.println(unescape("%u6211%u4eec"));
	}
}
