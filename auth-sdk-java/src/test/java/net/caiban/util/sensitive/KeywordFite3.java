package net.caiban.util.sensitive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//list实现
public class KeywordFite3 {
	private static Map<Character, List<String>> keywords = new HashMap<Character, List<String>>();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		List<String> wordList=new ArrayList<String>();
		wordList.add("操你妈");
		wordList.add("操你姐");
		addKeywords(wordList);
		System.out.println(fiter("我们吵架，我说:操你妈，操你姐"));
	}
	
	//添加敏感词
	public static void addKeywords(List<String> list){
		for(String s:list){
			char key = s.charAt(0);
			List<String> strs=keywords.get(key);
			if(strs==null){
				strs = new ArrayList<String>();
				keywords.put(key, strs);
			}
			strs.add(s);
		}
	}
	
	public static String fiter(String filetxt){
		StringBuffer sb = new StringBuffer();
		for(int i =0 ;i<filetxt.length();){
			char c = filetxt.charAt(i);
			String findValue = null;
			if(keywords.containsKey(c)){
				List<String> keyword = keywords.get(c);
				for(String s:keyword){
					String temp = filetxt.substring(i,(s.length()<=filetxt.length()-i)?i+s.length():i);
					if(s.equals(temp)){
						findValue = temp;
						break;
					}
				}
			}
			if(findValue!=null){
				sb.append("**");
				i+=(findValue.length()-1);
			}else{
				sb.append(c);
				i++;
			}
		}
		return sb.toString();
	}
}
