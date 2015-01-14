package net.caiban.util.sensitive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


//hashmap实现
@SuppressWarnings({ "rawtypes", "unchecked" })
public class KeywordFilter {

	private HashMap keysMap = new HashMap();
	private int matchType = 1; // 1:最小长度匹配 2：最大长度匹配

	//添加敏感词
	public void addKeywords(List<String> keywords) {
		for (int i = 0; i < keywords.size(); i++) {
			String key = keywords.get(i).trim();
			HashMap nowhash = null;
			nowhash = keysMap;
			for (int j = 0; j < key.length(); j++) {
				char word = key.charAt(j);
				Object wordMap = nowhash.get(word);
				if (wordMap != null) {
					nowhash = (HashMap) wordMap;
				} else {
					HashMap<String, String> newWordHash = new HashMap<String, String>();
					newWordHash.put("isEnd", "0");
					nowhash.put(word, newWordHash);
					nowhash = newWordHash;
				}
				if (j == key.length() - 1) {
					nowhash.put("isEnd", "1");
				}
			}
		}
	}
	//重置关键词
	public void restKeyWord(){
		keysMap.clear();
	}
	
	/**
	 * 仅判断是否有关键字
	 */
	public boolean isContentKeyWords(String txt) {
		for (int i = 0; i < txt.length(); i++) {
			int len = checkKeyWords(txt, i, 1);
			if (len > 0) {
				return true;
			}
		}
		txt = null;
		return false;
	}
	
	private int checkKeyWords(String txt, int begin, int flag) {
		HashMap nowhash = null;
		nowhash = keysMap;
		int maxMatchRes = 0;
		int res = 0;
		int l = txt.length();
		char word = 0;
		for (int i = begin; i < l; i++) {
			word = txt.charAt(i);
			Object wordMap = nowhash.get(word);
			if (wordMap != null) {
				res++;
				nowhash = (HashMap) wordMap;
				if (((String) nowhash.get("isEnd")).equals("1")) {
					if (flag == 1) {
						wordMap = null;
						nowhash = null;
						txt = null;
						return res;
					} else {
						maxMatchRes = res;
					}
				}
			} else {
				txt = null;
				nowhash = null;
				return maxMatchRes;
			}
		}
		txt = null;
		nowhash = null;
		return maxMatchRes;
	}
	
	/**
	 * 返回关键字
	 */
	public Set<String> getTxtKeyWords(String txt) {
		Set set = new HashSet();
		int l = txt.length();
		for (int i = 0; i < l;i++) {
			int len = checkKeyWords(txt, i, matchType);
			if (len > 0) {
				set.add(txt.substring(i, i + len));
				//i += len;
			}else{
			//	i++;
			}
		}
		txt = null;
		return set;
	}
	

	public int getMatchType() {
		return matchType;
	}

	public void setMatchType(int matchType) {
		this.matchType = matchType;
	}
	
	public static void main(String[] args) {
		KeywordFilter filter = new KeywordFilter();
		List<String> keywords = new ArrayList<String>();
		keywords.add("中国人");
		keywords.add("人民");
		keywords.add("起来");
		keywords.add("了");
		filter.addKeywords(keywords);
		String txt = "中国人民站起来了";
		boolean boo = filter.isContentKeyWords(txt);
		System.out.println(boo);
		Set set = filter.getTxtKeyWords(txt);
		System.out.println(set);
	}
}
