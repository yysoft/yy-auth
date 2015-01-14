package com.zz91.util.log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLConnection;

import com.zz91.util.net.NetUtils;

@Deprecated
public class LogClient {
	
	private String apiHost = "http://127.0.0.1:680/log/logServer";
	
	private String appCode = "";
	
	
	public void setHost(String host){
		this.apiHost = host;
	}
	
	public void setAppCode(String appCode){
		this.appCode = appCode;
	}
	
	public boolean recordLog(LogInfo logInfo){
		boolean flag = false;
		logInfo.setAppCode(appCode);
		URLConnection con = NetUtils.getURLConnection(apiHost);
		//输出流
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream objOut;
		try {
			objOut = new ObjectOutputStream(byteOut);
			//输出到缓冲区
			objOut.writeObject(logInfo);
			objOut.flush();
			byte[] buf= byteOut.toByteArray();
			//输出消息到服务器
			con.setRequestProperty("Content-type", "application/octet-stream");
			con.setRequestProperty("Content-length","" +  buf.length);
			DataOutputStream dataOut = new DataOutputStream(con.getOutputStream());
			dataOut.write(buf);
			dataOut.flush();
			dataOut.close();
			//响应消息流
			ObjectInputStream in = new ObjectInputStream(con.getInputStream());
			//接受响应消息
			LogInfo inLogInfo = (LogInfo) in.readObject();
			in.close();
			String resMessage = inLogInfo.getMessage();
			if("success".equals(resMessage.trim()))
				flag = true;
				
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	
/*	public static void main(String[] args) {
		String strUrl = "http://127.0.0.1:680/log/logServer";
		System.out.println("Connect Url:" + strUrl);
		try {
			//准备URL连接
			URL url = new URL(strUrl);
			URLConnection con = url.openConnection();
			con.setUseCaches(false);
			con.setDoOutput(true);
			con.setDoInput(true);
			
			//输出流
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
			
			//对象初始化
			LogInfo logInfo = new LogInfo(1, "zzwork10000", "admin", 2, "192.168.1.216"
					, 1, new Date(), "", "admin调用了远程日志方法，请存储！", new Date(), new Date());
			
			//输出到缓冲区
			objOut.writeObject(logInfo);
			objOut.flush();
			byte[] buf= byteOut.toByteArray();
			//输出消息到服务器
			con.setRequestProperty("Content-type", "application/octet-stream");
			con.setRequestProperty("Content-length","" +  buf.length);
			DataOutputStream dataOut = new DataOutputStream(con.getOutputStream());
			dataOut.write(buf);
			dataOut.flush();
			dataOut.close();
			
			//响应消息流
			ObjectInputStream in = new ObjectInputStream(con.getInputStream());
			//接受响应消息
			LogInfo inLogInfo = (LogInfo) in.readObject();
			in.close();
			System.out.println("From Server:");
			System.out.println(inLogInfo.toString());
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
