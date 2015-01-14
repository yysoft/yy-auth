package net.caiban.util.log;

import java.net.UnknownHostException;
import java.text.ParseException;

import com.zz91.util.log.LogUtil;


public class LogTest {

	/**
	 * @param args
	 * @throws ParseException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws ParseException, UnknownHostException {
		LogUtil.getInstance().init("logclient.properties");
		for (int i = 0; i < 100; i++) {
//			LogUtil.getInstance().log(3, "Leon"+i, InetAddress.getLocalHost().getHostAddress(), 1, new Date(), "xxxxx");
		}
	}
	

}
