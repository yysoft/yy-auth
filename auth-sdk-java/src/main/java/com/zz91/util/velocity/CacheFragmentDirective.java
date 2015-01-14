/**
 * 
 */
package com.zz91.util.velocity;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

/**
 * @author mays (mays@zz91.net)
 * 
 * <br />指令：#cacheFragment(fragmentUrl, cacheKey, cacheEnable, cacheExpired)
 * <br />用途：用于缓存页面片段，如果没有缓存，则从指定URL获取数据
 * <br />参数：
 * <br />    fragmentUrl:片段数据URL，可以包含完整参数，不能为空
 * <br />    cacheKey:缓存key，如果为空则自动生成
 * <br />    cacheEnable:是否启用缓存，为空则默认使用缓存，1：禁用缓存
 * <br />    cacheExpired:缓存超时时间，单位秒，为空或非正整数则使用默认超时时间，正整数：缓存秒数
 * <br />
 * <br />示例：
 * <br />#cacheFragment("/path/to/fragment.htm?code=1000", "", "", "")
 * <br />    #for($list in $!{fragmentList})
 * <br />        id:$!{list.id}
 * <br />        name:$!{list.name}
 * <br />    #end
 * <br />#end
 * <br />
 *
 * created by 2011-10-9
 */
public class CacheFragmentDirective extends Directive {
	
	final static Logger LOG = Logger.getLogger(CacheFragmentDirective.class);
	
	public static String CACHE_VERSION="0";
	public static Boolean DEBUG=false;

	@Override
	public String getName() {
		return "cacheFragment";
	}

	@Override
	public int getType() {
		return BLOCK;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer write, Node node)
			throws IOException, ResourceNotFoundException, ParseErrorException,
			MethodInvocationException {
		
		Node keyNode=node.jjtGetChild(0);
		String cacheEnable=(String) node.jjtGetChild(2).value(context);
		String cacheExpired=(String) node.jjtGetChild(3).value(context);
		String url=(String) keyNode.value(context);
		if(StringUtils.isEmpty(url)){
			return false;
		}
		
		String cacheKey=(String) node.jjtGetChild(1).value(context);
		if(StringUtils.isEmpty(cacheKey)){
			try {
				cacheKey=MD5.encode(url);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				return false;
			}
		}
		cacheKey=cacheKey+"."+CACHE_VERSION;
		String html="";
		
		if(DEBUG){
			cacheEnable="1";
		}
		
		if(!"1".equals(cacheEnable)){
			html=(String) MemcachedUtils.getInstance().getClient().get(cacheKey);
		}
		
		if(StringUtils.isEmpty(html)){
			
			html=buildHtml(context, node, url);
			
			if(!"1".equals(cacheEnable)){
				Integer exp=300; //300
				if(StringUtils.isNumber(cacheExpired)){
					exp=Integer.valueOf(cacheExpired);
				}
				MemcachedUtils.getInstance().getClient().set(cacheKey, exp, html);
			}
		}
		write.write(html);
		return true;
	}

	@SuppressWarnings("unchecked")
	private String buildHtml(InternalContextAdapter context, Node node, String url){
		
		String responseText=null;
		try {
			responseText = HttpUtils.getInstance().httpGet(url, HttpUtils.CHARSET_UTF8);
		} catch (HttpException e1) {
			LOG.error("Failed connect to api. error msg:"+e1.getMessage()+" url:"+url);
		} catch (IOException e1) {
			LOG.error("Failed connect to api. error msg:"+e1.getMessage()+" url:"+url);
		}
		try {
			if(StringUtils.isEmpty(responseText)){
				return "";
			}
			if(responseText.startsWith("{")){
				JSONObject jbody=JSONObject.fromObject(responseText);
				Set<String> jbodykey=jbody.keySet();
				for(String outkey: jbodykey){
					context.put(outkey, jbody.get(outkey));
				}
			}else{
				context.put("fragmentContext", responseText);
			}
			Node bodyNode=node.jjtGetChild(4);
			Writer tempWriter=new StringWriter();
			bodyNode.render(context, tempWriter);
			String result = tempWriter.toString();
			tempWriter.close();
			return result;
		} catch (MalformedURLException e) {
			LOG.error("Failed build html. error msg:"+e.getMessage()+" url:"+url);
		} catch (IOException e) {
			LOG.error("Failed build html. error msg:"+e.getMessage()+" url:"+url);
		}
		return "";
		
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String url="http://test.zz9l.com:8080/fragment/common/companyDetails.htm?cid=627461";
		try {
			String responseText=HttpUtils.getInstance().httpGet(url, HttpUtils.CHARSET_UTF8);
			
			if(StringUtils.isEmpty(responseText)){
			}
			if(responseText.startsWith("{")){
				JSONObject jbody=JSONObject.fromObject(responseText);
				Set<String> jbodykey=jbody.keySet();
				for(String outkey: jbodykey){
					System.out.println(outkey+":"+jbody.get(outkey));
				}
			}else{
				System.out.println(">>>>>>>>>>"+responseText);
			}
		} catch (MalformedURLException e) {
			LOG.error("Failed build html. error msg:"+e.getMessage()+" url:"+url);
		} catch (IOException e) {
			LOG.error("Failed build html. error msg:"+e.getMessage()+" url:"+url);
		}
		
//		Document doc= Jsoup.parse("<h2>test</h2>");
//		System.out.println(doc.select("body").toString());
//		System.out.println("-------------");
//		System.out.println(doc.select("body").html());
//		URL targetUrl;
//		try {
//			targetUrl = new URL("http://test.zz9l.com:8080/fragment/common/companyDetails.htm?cid=627461");
//			Document doc = Jsoup.parse(targetUrl, 10000);
//			JSONObject jbody=JSONObject.fromObject(doc.select("body").text());
//			Set<String> jbodykey=jbody.keySet();
//			for(String outkey: jbodykey){
//				System.out.println(outkey+"="+jbody.get(outkey));
//			}
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
}
