package com.zz91.util.net;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class NetUtils {

	private static NetUtils _instance = null;
	
	/**
	 * 获取NetUtils实例
	 * @return
	 */
	public static synchronized NetUtils getInstance() {
		if (_instance == null) {
			_instance = new NetUtils();
		}
		return _instance;
	}
	
	/**
	 * 获取URLConnection
	 * @param strUrl
	 * @return
	 */
	public static URLConnection getURLConnection(String strUrl) {
		URL url;
		URLConnection con = null;
		try {
			url = new URL(strUrl);
			con = url.openConnection();
			con.setUseCaches(false);
			con.setDoOutput(true);
			con.setDoInput(true);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return con;
	}
}
