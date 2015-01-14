package com.zz91.util.exception;

import java.util.HashMap;
import java.util.Map;

import com.zz91.util.lang.StringUtils;

public class AuthorizeException extends Exception {
	
	private static final long serialVersionUID = -4184884450072605567L;

	private final static Map<String, String> errorMap=new HashMap<String, String>();
	
	static {
		errorMap.put("000", "登录账户或密码不正确");
		errorMap.put("100", "登录账号或Email不能为空");
		errorMap.put("101", "登录账号或Email不存在");
		errorMap.put("200", "登录密码不能为空");
		errorMap.put("201", "登录密码错误");
		errorMap.put("300", "账户已被冻结");
		errorMap.put("301", "账户未激活");
		errorMap.put("500", "连接服务器发生错误");
	}
	
	public final static String ERROR_LOGIN="000";
	public final static String NEED_ACCOUNT="100";
	public final static String NOREG_ACCOUNT="101";
	public final static String NEED_PASS="200";
	public final static String NOREG_PASS="201";
	public final static String BLOCKED="300";
	public final static String UNACTIVE="301";
	public final static String ERROR_SERVER="500";
	

	public AuthorizeException(String message, Throwable cause){
		super(message, cause);
	}

	public AuthorizeException(String message){
		super(message);
	}
	
	/**
	 * 根据错误编码得到登录账户的错误信息
	 * @param errorCode
	 * @return
	 */
	public static String getMessage(String errorCode){
		String msg=errorMap.get(errorCode);
		if(StringUtils.isEmpty(msg)){
			return errorMap.get("000");
		}
		return msg;
	}
	
}
