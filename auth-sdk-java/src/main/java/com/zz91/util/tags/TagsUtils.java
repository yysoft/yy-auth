/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-4
 */
package com.zz91.util.tags;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;

import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;


/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-4
 */
public class TagsUtils {

	private static Logger LOG = Logger.getLogger(TagsUtils.class);
	
	private static TagsUtils _instance=null;
	
	private static String API_HOST="http://china.zz91.com/tags/api";
	private String DEFAULT_PROP = "tags.properties";
	
	public static final String DEFAULT_ENCODE="utf-8";
	
	public static final String ORDER_SEARCH="search_count";
	public static final String ORDER_CITED="cited_count";
	public static final String ORDER_CLICK="click_count";
	
	private TagsUtils(){
		
	}
	
	synchronized public static TagsUtils getInstance(){
		if(_instance==null){
			_instance = new TagsUtils();
		}
		return _instance;
	}
	
//	private final static int TIMEOUT=100000;  //TODO 发布后改为10秒＝10000
	
	public void init(){
		init(DEFAULT_PROP);
	}
	
	public void init(String properties) {
		// 从配置文件读取缓存服务器信息
		LOG.debug("Loading tag properties:" + properties);
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(
				properties);
		Properties p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			LOG.error("An error occurred when load search properties:"
					+ properties, e);
		}
		
		API_HOST = p.getProperty("tags.host");
		
	}
	
	public String arrangeTags(String tags){
		if(tags==null){
			return "";
		}
		tags=tags.replace("，", ",").replace("＃", "#").replace("＄", "$").replace("\"", "").replace("“", "");
		Set<String> tagSet = new HashSet<String>();
		String[] tagsArray=tags.split(",");
		for(String tag:tagsArray){
			tagSet.add(tag);
		}
		if(tagSet.size()<=0){
			return "";
		}
		tags=tagSet.toString().replace(" ", "").replace("[", "").replace("]", "");
		return tags;
	}
	
	public Map<String, String> encodeTags(String tags, String encode){
		Map<String, String> map=new HashMap<String, String>();
		if(tags==null){
			return map;
		}
		tags=tags.replace("，", ",").replace("＃", "#").replace("＄", "$").replace("\"", "").replace("“", "");
		Set<String> tagSet = new HashSet<String>();
		String[] tagsArray=tags.split(",");
		for(String tag:tagsArray){
			tagSet.add(tag);
		}
		for(String t:tagSet){
			try {
				map.put(t, URLEncoder.encode(t, encode));
			} catch (UnsupportedEncodingException e) {
			}
		}
		return map;
	}
	
	public void createTags(String tags){
		String url=API_HOST+"/createTags.htm";
//		String url=API_HOST+"/create.htm";
//		String url="http://localhost:8080/api/create.htm";
		try {
			HttpUtils.getInstance().httpGet(url+"?t="+URLEncoder.encode(tags, "utf-8"), null);
//			NameValuePair[] data={new NameValuePair("t", tags)};
//			HttpUtils.getInstance().httpPost(url, data, HttpUtils.CHARSET_UTF8);
		} catch (HttpException e) {
			LOG.error("error connect tags server. url:"+url);
		} catch (IOException e) {
			LOG.error("error connect tags server. url:"+url);
		}
	}
	
	public void clickTags(String tags){
		String url=API_HOST+"/click.htm";
		try {
//			NameValuePair[] data={new NameValuePair("t", tags)};
//			HttpUtils.getInstance().httpPost(url, data, HttpUtils.CHARSET_UTF8);
			HttpUtils.getInstance().httpGet(url+"?t="+URLEncoder.encode(tags, "utf-8"), null);
		} catch (HttpException e) {
			LOG.error("error connect tags server. url:"+url);
		} catch (IOException e) {
			LOG.error("error connect tags server. url:"+url);
		}
	}
	
	public void searchTags(String tags){
		String url=API_HOST+"/search.htm";
		try {
//			NameValuePair[] data={new NameValuePair("t", tags)};
//			HttpUtils.getInstance().httpPost(url, data, HttpUtils.CHARSET_UTF8);
			HttpUtils.getInstance().httpGet(url+"?t="+URLEncoder.encode(tags, "utf-8"), null);
		} catch (HttpException e) {
			LOG.error("error connect tags server. url:"+url);
		} catch (IOException e) {
			LOG.error("error connect tags server. url:"+url);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> queryTagsByIndex(String indexKey, Integer size){
		Map<String, String> map=new HashMap<String, String>();
		String url=API_HOST+"/queryByIndex.htm?ik="+indexKey+"&l="+size;
		String responseText="";
		try {
			responseText = HttpUtils.getInstance().httpGet(url, null);
			if(responseText.startsWith("{")){
				JSONObject job=JSONObject.fromObject(responseText);
				Set<String> jobset=job.keySet();
				for(String k:jobset){
					map.put(k, job.getString(k));
				}
			}
		} catch (HttpException e) {
			LOG.error("error connect tags server. url:"+url+" Exception:"+e.getMessage());
		} catch (IOException e) {
			LOG.error("error connect tags server. url:"+url+" Exception:"+e.getMessage());
		}
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> queryTagsByCode(String code, Integer depth, Integer size){
		
		if(depth==null){
			depth=0;
		}
		if(code==null){
			code="";
		}
		Map<String, String> map=new HashMap<String, String>();
		String url=API_HOST+"/queryByCode.htm?c="+code+"&d="+depth+"&l="+size;
		String responseText="";
		try {
			responseText = HttpUtils.getInstance().httpGet(url, HttpUtils.CHARSET_UTF8);
			if(responseText.startsWith("{")){
				JSONObject job=JSONObject.fromObject(responseText);
				Set<String> jobset=job.keySet();
				for(String k:jobset){
					map.put(k, job.getString(k));
				}
			}
		} catch (HttpException e) {
			LOG.error("error connect tags server. url:"+url+" Exception:"+e.getMessage());
		} catch (IOException e) {
			LOG.error("error connect tags server. url:"+url+" Exception:"+e.getMessage());
		}
		
		return map;
	}
	
	/**
	 * 根据关键字查找相关标签
	 * @param keywords
	 * 		关键字
	 * @param sort
	 * 		排序规则： 
	 * 			search 按照搜索热度从高到底排序；
	 * 			click 按照被点击次数从高到底排序；
	 * 			cited 按照被创建次数从高到低排序
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> queryTagsByKey(String keywords, String sort, Integer size){
		if(keywords==null){
			keywords="";
		}
		if(StringUtils.isEmpty(sort)){
			sort=ORDER_SEARCH;
		}
		
		Map<String, String> map=new HashMap<String, String>();
		String responseText="";
		String url="";
		try {
			keywords=URLEncoder.encode(keywords, "utf-8");
			url=API_HOST+"/queryTagsByKw.htm?k="+keywords+"&s="+sort+"&l="+size;
			responseText = HttpUtils.getInstance().httpGet(url, null);
			if(responseText.startsWith("{")){
				JSONObject job=JSONObject.fromObject(responseText);
				Set<String> jobset=job.keySet();
				for(String k:jobset){
					map.put(k, job.getString(k));
				}
			}
		} catch (HttpException e) {
			LOG.error("error connect tags server. url:"+url+" Exception:"+e.getMessage());
		} catch (IOException e) {
			LOG.error("error connect tags server. url:"+url+" Exception:"+e.getMessage());
		}
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> queryTagsByTag(String tag, String sort, Integer size){
		if(tag==null){
			tag="";
		}
		if(StringUtils.isEmpty(sort)){
			sort=ORDER_SEARCH;
		}
		
		Map<String, String> map=new HashMap<String, String>();
		String responseText="";
		String url="";
		try {
			tag=URLEncoder.encode(tag, "utf-8");
			url=API_HOST+"/queryTagsByTag.htm?t="+tag+"&s="+sort+"&l="+size;
			responseText = HttpUtils.getInstance().httpGet(url, HttpUtils.CHARSET_UTF8);
			if(responseText.startsWith("{")){
				JSONObject job=JSONObject.fromObject(responseText);
				Set<String> jobset=job.keySet();
				for(String k:jobset){
					map.put(k, job.getString(k));
				}
			}
		} catch (HttpException e) {
			LOG.error("error connect tags server. url:"+url+" Exception:"+e.getMessage());
		} catch (IOException e) {
			LOG.error("error connect tags server. url:"+url+" Exception:"+e.getMessage());
		}
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> queryTagsByHot(Integer size,Integer day){
		Map<String, String> map=new HashMap<String, String>();
		String responseText="";
		String url="";
		try {
			url=API_HOST+"/queryTagsByHot.htm?l="+size+"&d="+day;
			responseText = HttpUtils.getInstance().httpGet(url, HttpUtils.CHARSET_UTF8);
			if(responseText.startsWith("{")){
				JSONObject job=JSONObject.fromObject(responseText);
				Set<String> jobset=job.keySet();
				for(String k:jobset){
					map.put(k, job.getString(k));
				}
			}
		} catch (HttpException e) {
			LOG.error("error connect tags server. url:"+url+" Exception:"+e.getMessage());
		} catch (IOException e) {
			LOG.error("error connect tags server. url:"+url+" Exception:"+e.getMessage());
		}
		
		return map;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
//		TagsUtils.API_HOST="http://china.zz91.com/tags/api";
		TagsUtils.getInstance().createTags("北京28");
		
//		System.out.println(URLDecoder.decode("%E5%8C%97%E4%BA%AC28", HttpUtils.CHARSET_UTF8));
		
//		TagsUtils.getInstance().createTags("杭州28 < 30%");
//		TagsUtils.getInstance().createTags("杭州28 > 5%");
//		
		Map<String, String> tagsMap=TagsUtils.getInstance().queryTagsByTag("北京", null, 100);
		for(String  key:tagsMap.keySet()){
			System.out.println(key+":"+tagsMap.get(key));
		}
		
//		URL url;
//		try {
//			url = new URL(API_HOST+"/queryTagsByTag.htm?t=黄杂铜&s=search_count&l=20");
//			Document doc = Jsoup.parse(url, TIMEOUT);
//			System.out.println(doc.select("body").text());
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		String str="{\"黄杂铜2\":\"%E9%BB%84%E6%9D%82%E9%93%9C2\",\"黄杂铜1\":\"%E9%BB%84%E6%9D%82%E9%93%9C1\",\"黄杂铜Fe<3%\":\"%E9%BB%84%E6%9D%82%E9%93%9CFe%3C3%25\",\"黄杂铜\":\"%E9%BB%84%E6%9D%82%E9%93%9C\",\"黄杂铜锭\":\"%E9%BB%84%E6%9D%82%E9%93%9C%E9%94%AD\"}";
//		JSONObject job=JSONObject.fromObject(str);
//		System.out.println(job.size());
	}
	
	
//	public static void main(String[] args) {
//		Map<String, String> map=new HashMap<String, String>();
//		map.put("杭州", "%2SDF%@%$ASSDF");
//		map.put("杭州2", "%2SDF%@%$ASSDF");
//		map.put("杭州3", "%2SDF%@%$ASSDF");
//		map.put("杭州4", "%2SDF%@%$ASSDF");
//		map.put("杭州5", "%2SDF%@%$ASSDF");
//		
//		System.out.println(JSONObject.fromObject(map).toString());
//		
//		String nm=JSONObject.fromObject(map).toString();
//		
//		Map<String, String> result=new HashMap<String, String>();
//		JSONObject job=JSONObject.fromObject(nm);
//		Set<String> joben=job.keySet();
//		for(String k:joben){
//			System.out.println(k+":"+job.get(k));
//		}
//		
//	}

}
