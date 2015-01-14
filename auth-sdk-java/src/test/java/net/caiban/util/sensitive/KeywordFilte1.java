package net.caiban.util.sensitive;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class KeywordFilte1 {
	public static void main(String[] args) throws Exception {
		String filterText = " 中国人民站起来了124ssssaa中国人";
		KeywordFilte1.getFilterText(filterText);
		}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getFilterText(String filterText) throws Exception {
		List listWord = new ArrayList();
		FileReader reader = new FileReader("/root/dev/keywords");
		BufferedReader br = new BufferedReader(reader);
		String s = null;
		while ((s = br.readLine()) != null) {
			listWord.add(s.trim());
		}
		br.close();
		reader.close();
		Matcher m = null;
		
		for (int i = 0; i < listWord.size(); i++) {
			Pattern p = Pattern.compile(listWord.get(i).toString(),Pattern.CASE_INSENSITIVE);
			StringBuffer sb = new StringBuffer();
			m = p.matcher(filterText);
			while (m.find()) {
			m.appendReplacement(sb, "**");
			System.out.println(sb);
		}
			System.out.println("________");
			m.appendTail(sb);
			System.out.println(sb);
			System.out.println("---------");
			filterText = sb.toString();
			}
			return filterText;
		}
}
