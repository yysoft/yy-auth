package com.zz91.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpUtils {

	// private final static Logger LOG = Logger.getLogger(HttpUtils.class);

	private static HttpUtils _instance = null;

	private static MultiThreadedHttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();

	private static HttpClient client = new HttpClient(httpConnectionManager);

	static {

		// 每主机最大连接数和总共最大连接数，通过hosfConfiguration设置host来区分每个主机

		client.getHttpConnectionManager().getParams()
				.setDefaultMaxConnectionsPerHost(8);

		client.getHttpConnectionManager().getParams()
				.setMaxTotalConnections(48);

		client.getHttpConnectionManager().getParams()
				.setConnectionTimeout(5000);

		client.getHttpConnectionManager().getParams().setSoTimeout(5000);

		client.getHttpConnectionManager().getParams().setTcpNoDelay(true);

		client.getHttpConnectionManager().getParams().setLinger(1000);

		// 失败的情况下会进行3次尝试,成功之后不会再尝试

		client.getHttpConnectionManager().getParams().setParameter(
				HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());

	}

	public static synchronized HttpUtils getInstance() {
		if (_instance == null) {
			_instance = new HttpUtils();
		}
		return _instance;
	}

	/**
	 * 获取用户的IP地址
	 * 
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		
		if(ip!=null && ip.contains(",")){
			ip=ip.split(",")[0].trim();
		}

		return ip;
	}

	/**
	 * 设置cookie
	 * 
	 * @param response
	 *            : 从外部传进来的response对象,不可以为null
	 * @param key
	 *            : cookie的健
	 * @param value
	 *            : cookie的值
	 * @param domain
	 *            : cookie所在的域,可以为null,为null时按时默认的域存储
	 * @param maxAge
	 *            : cookie最大时效,以秒为单位,为null时表示不设置最大时效,按照浏览器进程结束
	 */
	public void setCookie(HttpServletResponse response, String key,
			String value, String domain, Integer maxAge) {
		Cookie c = new Cookie(key, value);
		if (domain != null && domain.length() > 0) {
			c.setDomain(domain);
		}
		if (maxAge != null) {
			c.setMaxAge(maxAge);
		}
		c.setPath("/");
		response.addCookie(c);
	}

	/**
	 * 从cookie中得到值
	 * 
	 * @param request
	 * @param key
	 *            :cookie名称
	 * @param domain
	 *            :域名
	 * @return
	 */
	public String getCookie(HttpServletRequest request, String key,
			String domain) {
		
//		domain = StringUtils.isEmpty(domain)?request.getServerName():domain;
		
		if (key == null || "".equals(key)) {
			return null;
		}
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];
				if (key.equals(c.getName())) {
//					if(StringUtils.isNotEmpty(domain) ){
//						if(domain.equals(c.getDomain())){
							return c.getValue();
//						}
//					}else{
//						return c.getValue();
//					}
				}
			}
		}
		return null;
	}

	public final static String CHARSET_DEFAULT = "ISO-8859-1";
	public final static String CHARSET_UTF8 = "utf-8";
	public final static int HTTP_CONNECT_TIMEOUT = 10000;
	public final static int HTTP_SO_TIMEOUT = 120000;

	/**
	 * 默认连接10秒超时，数据读取120秒超时
	 * 
	 * @param url
	 * @param charset
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String httpGet(String url, String charset) throws HttpException,
			IOException {
		return httpGet(url, charset, HTTP_CONNECT_TIMEOUT, HTTP_SO_TIMEOUT);
	}

	/**
	 * @param url
	 *            ：请求的URL
	 * @param charset
	 *            ：编码
	 * @param connectionTimeout
	 *            ：连接超时时间，单位毫秒
	 * @param soTimeout
	 *            ：数据读取超时时间，单位毫秒
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String httpGet(String url, String charset, int connectionTimeout,
			int soTimeout) throws HttpException, IOException {
		if (charset == null) {
			charset = HttpUtils.CHARSET_DEFAULT;
		}
		String responseString = "";
		// 构造HttpClient的实例
		// HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		GetMethod getMethod = new GetMethod(url);

		// 使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(0, false));
		getMethod.getParams().setContentCharset(charset);

		// httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
		// httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);

		try {
			// 执行getMethod
			int statusCode = client.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK) {
				// // 读取内容
				// byte[] responseBody = getMethod.getResponseBody();
				// // 处理内容
				// responseString = new String(responseBody);
				responseString = httpResponseAsString(getMethod
						.getResponseBodyAsStream(), charset);
			}
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
		return responseString;
	}

	public String httpPost(String url, NameValuePair[] data, String charset)
			throws HttpException, IOException {
		return httpPost(url, data, charset, HTTP_CONNECT_TIMEOUT,
				HTTP_SO_TIMEOUT);
	}

	/**
	 * @param url
	 * @param data
	 *            : NameValuePair[] data = { new NameValuePair("id",
	 *            "youUserName"), new NameValuePair("passwd", "yourPwd") };
	 * @param charset
	 *            : 针对post后返回的页面设置字符编码
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String httpPost(String url, NameValuePair[] data, String charset,
			int connectionTimeout, int soTimeout) throws HttpException,
			IOException {
		if (charset == null) {
			charset = CHARSET_DEFAULT;
		}
		// HttpClient httpClient = new HttpClient();
		client.getParams().setContentCharset(charset);

		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				charset);

		// httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
		// httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);

		PostMethod postMethod = new PostMethod(url);
		// 将表单的值放入postMethod中
		postMethod.setRequestBody(data);
		// 执行postMethod
		String responseString = "";
		try {
			int statusCode = client.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				// responseString = new String(postMethod.getResponseBody());
				responseString = httpResponseAsString(postMethod
						.getResponseBodyAsStream(), charset);
			}
		} finally {
			postMethod.releaseConnection();
		}
		return responseString;
		// 暂不处理301/302转向
		// HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
		// 301或者302
		// if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
		// || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
		// // 从头中取出转向的地址
		// Header locationHeader = postMethod.getResponseHeader("location");
		// String location = null;
		// if (locationHeader != null) {
		// location = locationHeader.getValue();
		// System.out.println("The page was redirected to:" + location);
		// } else {
		// System.err.println("Location field value is null.");
		// }
		// }
	}

	public String httpResponseAsString(InputStream is, String encode)
			throws IOException {
		if (is == null) {
			return "";
		}

		StringBuffer out = new StringBuffer();
		String line;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, encode));
			while ((line = reader.readLine()) != null) {
				out.append(line).append("\n");
			}
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return out.toString();
	}

	public static void main(String[] args) {
		try {
			System.out.println(HttpUtils.getInstance().httpGet("http://www.zz91.com/",
					CHARSET_UTF8));
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
