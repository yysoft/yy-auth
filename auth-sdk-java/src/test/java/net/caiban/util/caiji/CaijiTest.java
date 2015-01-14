package net.caiban.util.caiji;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

import com.zz91.util.http.HttpUtils;

/**
 *	author:kongsj
 *	date:2013-5-29
 */
public class CaijiTest {
	final static String URL = "http://price.zz91.com/priceDetails_461396_metal.htm";
	public static void main(String[] args) {
		String html = "";
		try {
			html = HttpUtils.getInstance().httpGet(URL, HttpUtils.CHARSET_UTF8);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Integer start = html.indexOf("<div class=\"content\">");
		Integer end = html.indexOf("<div class=\"thisLabel\">");
		String content = html.substring(start, end);
		System.out.println(content);
	}
}
