package com.zz91.util.lang;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zz91.util.cache.MemcachedUtils;
/**
 * 敏感词过滤工具 
 * 需要服务器本地有敏感词词库文件 "/usr/data/keylimit/limit"
 * 词库不得包含“+”符号
 * 
 * @author kongsj
 * @date 2013-01-28
 *
 */
public class SensitiveUtils {
	
	final static String ZZ91_SENSITIVE= "zz91_sensitive";
	final static String SENSITIVE_URL = "/usr/data/keylimit/limit";
	/**
	 * 过滤敏感信息，替换为 * 
	 * 提示 敏感词有 哪些
	 * @param filterText 
	 * @return map 包含两个重要参数 sensitiveSet 发现的敏感词 type:set;
	 * 								filterValue 敏感词过滤后的信息内容 type:string
	 * @throws Exception
	 */
	public static Map<String, Object> getSensitiveFilter(String filterText) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取敏感词 list
		List<String> listWord = getlistWord();
		
		Matcher m = null;
		Set<String> sensitiveSet = new HashSet<String>();
		for (int i = 0; i < listWord.size(); i++) {
			Pattern p = Pattern.compile(listWord.get(i),Pattern.CASE_INSENSITIVE);
			StringBuffer sb = new StringBuffer();
			m = p.matcher(filterText);
			while (m.find()) {
				sensitiveSet.add(m.group());
				m.appendReplacement(sb, "*");
			}
			m.appendTail(sb);
			filterText = sb.toString();
		}
		map.put("sensitiveSet", sensitiveSet);
		map.put("filterValue", filterText);
		return map;
	}
	
	/**
	 * 验证是否有敏感词 
	 * @param filterText
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public static Boolean validateSensitiveFilter(String filterText) throws IOException {
		// 获取敏感词 list
		List<String> listWord = getlistWord();
		
		Matcher m = null;
		for (int i = 0; i < listWord.size(); i++) {
			Pattern p = Pattern.compile(listWord.get(i),Pattern.CASE_INSENSITIVE);
			m = p.matcher(filterText);
			while (m.find()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 过滤敏感信息
	 * @param filterText 需要过滤的信息
	 * @param filter 过滤的 符号例如："*" 则所有敏感词过滤以"*"代替
	 * @return 
	 * @throws Exception
	 */
	public static String getSensitiveValue(String filterText,String filter) throws Exception {
		// 获取敏感词 list
		List<String> listWord = getlistWord();
		// 判断过滤符号
		if(filter==null){
			filter = "*";
		}
		Matcher m = null;
		for (int i = 0; i < listWord.size(); i++) {
			Pattern p = Pattern.compile(listWord.get(i),Pattern.CASE_INSENSITIVE);
			StringBuffer sb = new StringBuffer();
			m = p.matcher(filterText);
			while (m.find()) {
				m.appendReplacement(sb, filter);
			}
			m.appendTail(sb);
			filterText = sb.toString();
		}
		return filterText;
	}

	/**
	 * 更新敏感词缓存 接口 缓存有效时间 12小时
	 * @throws IOException 
	 * @throws Exception
	 */
	public static void updateCache() throws IOException {
		List<String> listWord = new ArrayList<String>();
		FileReader reader = new FileReader(SENSITIVE_URL);
		BufferedReader br = new BufferedReader(reader);
		String s = null;
		while ((s = br.readLine()) != null) {
			listWord.add(s.trim());
		}
		br.close();
		reader.close();
		MemcachedUtils.getInstance().getClient().set(ZZ91_SENSITIVE,3600 * 12, listWord);
	}
	
	@SuppressWarnings("unchecked")
	private static List<String> getlistWord() throws IOException{
		List<String> listWord = (List<String>) MemcachedUtils.getInstance().getClient().get(ZZ91_SENSITIVE);
		if (listWord == null || listWord.size() <= 0) {
			updateCache();
			listWord = (List<String>) MemcachedUtils.getInstance().getClient().get(ZZ91_SENSITIVE);
		}
		return listWord;
	}

	public static void main(String[] args) throws Exception {
		String filterText = " 中国人民站起来了124ssssaa中av国人民 老虎机波霸";
		MemcachedUtils.getInstance().init("web.properties");
		
		System.out.println(SensitiveUtils.getSensitiveFilter(filterText));
		System.out.println(SensitiveUtils.validateSensitiveFilter(filterText));
		System.out.println(SensitiveUtils.getSensitiveValue(filterText, ""));
	}
}
