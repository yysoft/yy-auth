package net.caiban;


public class HttpClientTest {

	public static void main(String[] args) {
		HttpRunThread thread=new HttpRunThread(1000, "run_1");
		thread.start();
		
		HttpRunThread thread2=new HttpRunThread(1000, "run_2");
		thread2.start();
		
//		try {
//			HttpUtils.getInstance().httpGet("http://192.168.2.178:8080/zz91/fragment/huzhu/newestPost.htm?categoryId=1&size=8", HttpUtils.CHARSET_UTF8);
//			HttpUtils.getInstance().httpGet("http://192.168.2.178:8080/zz91/fragment/huzhu/newestPost.htm?categoryId=1&size=8", HttpUtils.CHARSET_UTF8);
//		} catch (HttpException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
