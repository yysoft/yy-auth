package com.zz91.util.sms;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;

import com.zz91.util.datetime.DateUtil;
import com.zz91.util.file.FileUtils;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

/**
 * <br />
 * SmsUtil 短信发送工具包 使用方法: <br />
 * 
 * <br />第一步：在配置文件中配置邮件api host，默认：smsclient.properties 
 * <br />例：sms.host=http://apps.zz91.com/zz91-sms
 * 
 * <br />第二步：初始化工具
 * <br />SmsUtil.getInstance().init("web.properties");
 * <br />或 SmsUtil.getInstance().init(); 表示初始化smsclient.properties
 * <br />注:系统启动时初始化
 * 
 * <br />第三步: 准备数据
 * <br />templateCode: 模板code, 如果不填，则不使用模板
 * <br />receiver: 接收电话, 必填
 * <br />gmtSend: 定时发送短信, 不填或null表示立即发送
 * <br />gatewayCode: 网关code, 不填或null表示使用默认网关发送
 * <br />priority: 优先级
 * <br />content: 短信内容
 * 
 * <br />第四步: 选择短信调用方法, 发送短信 SmsUtil.getInstance().sendSms(String templateCode, String receiver, Date gmtSend,
			String gatewayCode, Integer priority, String content,
			String[] smsParameter)
 * 
 * @author root
 *
 */
public class SmsUtil {
	private static String SMS_CONFIG = "smsclient.properties";
	private static String API_HOST = "http://apps.zz91.com/zz91-sms/";
	private static SmsUtil _instance;
	private static Logger LOG = Logger.getLogger(SmsUtil.class);
	public final static int PRIORITY_HEIGHT = 0;
	public final static int PRIORITY_DEFAULT = 1;
	public final static int PRIORITY_TASK = 10;

	public synchronized static SmsUtil getInstance() {
		if (_instance == null) {
			_instance = new SmsUtil();
		}
		return _instance;
	}

	public void init() {
		init(SMS_CONFIG);
	}

	public void init(String properties) {
		if (StringUtils.isEmpty(properties)) {
			properties = SMS_CONFIG;
		}
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = FileUtils.readPropertyFile(properties,
					HttpUtils.CHARSET_UTF8);
			API_HOST = (String) map.get("sms.host");
		} catch (IOException e) {
			LOG.error("An error occurred when load sms properties:"
					+ properties, e);
		}
	}

	/**
	 * 短信发送 参数
	 * 
	 * @param templateCode
	 *            模板code
	 * @param receiver
	 *            接收电话
	 * @param gmtSend
	 *            发送时间
	 * @param gatewayCode
	 *            网关code
	 * @param priority
	 *            优先级
	 * @param content
	 *            内容
	 * @param smsParameter
	 *            转换参数
	 */
	public void sendSms(String templateCode, String receiver, Date gmtSend,
			String gatewayCode, Integer priority, String content,
			String[] smsParameter) {
		String url = "sms/send.htm";
		if(!API_HOST.endsWith("/")){
			url = "/" + url;
		}
		try {
			JSONArray js = JSONArray.fromObject(smsParameter);
			NameValuePair[] data = {
					new NameValuePair("templateCode", templateCode),
					new NameValuePair("receiver", receiver),
					new NameValuePair("gmtSendStr", DateUtil.toString(gmtSend,
							"yyyy-MM-dd HH:mm:ss")),
					new NameValuePair("gatewayCode", gatewayCode),
					new NameValuePair("priority", priority.toString()),
					new NameValuePair("content", content),
					new NameValuePair("smsParameter", js.toString()) };
			HttpUtils.getInstance().httpPost(API_HOST + url, data,
					HttpUtils.CHARSET_UTF8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 直接简单发送(加发送时间)
	 * 
	 * @param receiver
	 *            接收电话
	 * @param content
	 *            内容
	 * @param gmtSend
	 *            发送时间
	 */
	public void sendSms(String receiver, String content, Date gmtSend) {
		sendSms(null, receiver, gmtSend, null, PRIORITY_DEFAULT, content, null);
	}
	
	/**
	 * 带优先级简单发送
	 * 
	 * @param receiver
	 *            接收电话
	 * @param content
	 *            内容
	 * @param gmtSend
	 *            发送时间
	 * @param priority
	 *            优先级
	 */
	public void sendSms(String receiver, String content,Integer priority) {
		sendSms(null, receiver, null, null, priority, content, null);
	}

	/**
	 * 简单发送
	 * 
	 * @param receiver
	 *            接收电话
	 * @param content
	 *            内容
	 * @param gmtSend
	 *            发送时间
	 */
	public void sendSms(String receiver, String content) {
		sendSms(null, receiver, null, null, PRIORITY_DEFAULT, content, null);
	}

	/**
	 * 根据模板编号发送短信
	 * @param templateCode
	 *            模板编号
	 * @param receiver
	 *            接收电话
	 * @param content
	 *            内容
	 * @param gmtSend
	 *            发送时间
	 * @param smsParameter
	 *            模板参数
	 */
	public void sendSms(String templateCode, String receiver, String content,
			Date gmtSend,String[] smsParameter) {
		sendSms(templateCode, receiver, gmtSend, null, PRIORITY_DEFAULT, content, smsParameter);
	}

	public static void main(String[] args) throws ParseException {
		API_HOST = "http://web.zz91.com:8080/sms/";
//		for(int i=1;i<200;i++){
//			SmsUtil.getInstance().sendSms("13738194812", "我是一个并【zz91】");
			SmsUtil.getInstance().sendSms("sms_product", "13738194812", null, null, new String[]{"1","2","3","4"});
//		}
		
//		String[] s=new String[]{"2","1","3"};
//		JSONArray ja=JSONArray.fromObject(s);
//		System.out.println(ja.toString());
		
//		String sr="[\"2\",\"3\",\"1\"]";
//		JSONArray jar=JSONArray.fromObject(sr);
		
//		String[] sa=new String[]{};
//		sa=(String[]) ja.toArray(sa);
		
//		String str = ja.toString();
//		JSONArray obj = JSONArray.fromObject(str);
//		sa = (String[]) obj.toArray(sa);
//		System.out.println(sa.toString());
	}
}