/**
 * IK 中文分词  版本 5.0.1
 * IK Analyzer release 5.0.1
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * 源代码由林良益(linliangyi2005@gmail.com)提供
 * 版权声明 2012，乌龙茶工作室
 * provided by Linliangyi and copyright 2012 by Oolong studio
 * 
 * 
 */
package com.zz91.util.analzyer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import com.zz91.util.analzyer.base.IKAnalyzer;

/**
 * 使用IKAnalyzer进行分词的演示 
 * 
 */
public class IKAnalzyerUtils {
	
	public static List<String> getAnalzyerList(String keywords){
		List<String> list =new ArrayList<String>();
		// 构建IK分词器，使用smart分词模式
		Analyzer analyzer = new IKAnalyzer(true);

		// 获取Lucene的TokenStream对象
		TokenStream ts = null;
		try {
			ts = analyzer.tokenStream("myAnalzyer",new StringReader(keywords));
			// 获取词元位置属性 位置 : 起始 | 结束
//			OffsetAttribute offset = ts.addAttribute(OffsetAttribute.class);
			// 获取词元文本属性
			CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
			// 获取词元文本属性 中文 / 英文
//			TypeAttribute type = ts.addAttribute(TypeAttribute.class);
			// 重置TokenStream（重置StringReader）
			ts.reset();
			// 迭代获取分词结果
			while (ts.incrementToken()) {
				list.add(term.toString());
//				System.out.println(offset.startOffset() + " - " + offset.endOffset() + " : " + term.toString() + " | " + type.type());
			}
			// 关闭TokenStream（关闭StringReader）
			ts.end(); 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 释放TokenStream的所有资源
			if (ts != null) {
				try {
					ts.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {
		
		List<String> list = IKAnalzyerUtils.getAnalzyerList("美丽的老玫瑰虎机");
		for(String str :list ){
			System.out.println(str);
		}

	}

}
