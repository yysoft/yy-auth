package com.zz91.util.db;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * ibaits sql语句创建工具
 * @author liuwb
 *
 */
public class IBaitsSqlStmtCreator {
	/**
	 * 示例：
	 * <pre>
	 *		<isNotEmpty property="typeCode">
	 *			type_code = #typeCode#,
	 *		</isNotEmpty>
	 * </pre>
	 * @param columnName
	 * @param propertyName
	 * @return
	 */
	public static String buildIsNotEmpty(String columnName ,String propertyName){
		StringBuilder buf=new StringBuilder();
		buf.append("<isNotEmpty property=\"").append(propertyName).append("\">\n    ");
		buf.append(columnName).append("=#").append(propertyName).append("#,\n");
		buf.append("</isNotEmpty>");
		return buf.toString();
	}
	
	public static String buildIsNotEmptyForMap(Map<String,String> columnPropertyMap){
		if(columnPropertyMap==null||columnPropertyMap.size()==0)
			return "";
		StringBuilder buf=new StringBuilder();
		for(Entry<String,String> entry:columnPropertyMap.entrySet()){
			buf.append(buildIsNotEmpty(entry.getKey(),entry.getValue())).append(" \n");
		}
		return buf.toString();
	}
	
	/**
	 * 示例：
	 * <pre>
	 * 	INSERT INTO table_name (
	 *     column1,
	 *     column2,
	 *     column3
	 *  ) VALUES (
	 *     #property1#,
	 *     #property2#,
	 *     #property3#)
	 *  <selectKey keyProperty="id" resultClass="java.lang.Integer">
	 *     select last_insert_id() as value 
	 *  </selectKey>
	 *  </pre>
	 * @param tableName 
	 * @param columnPropertyMap
	 * @return
	 */
	public static String buildInsertStmt(String tableName,Map<String,String> columnPropertyMap){
		if(columnPropertyMap==null||columnPropertyMap.size()==0)
			return "";
		StringBuilder insertBuf=new StringBuilder();
		StringBuilder colBuf=new StringBuilder();
		StringBuilder propBuf=new StringBuilder();
		insertBuf.append("INSERT INTO ").append(tableName).append(" (\n");
		for(Entry<String,String> entry:columnPropertyMap.entrySet()){
			colBuf.append("    ").append(entry.getKey()).append(",\n");
			propBuf.append("    ").append("#").append(entry.getValue()).append("#,\n");
		}
		colBuf.deleteCharAt(colBuf.length()-2);
		propBuf.deleteCharAt(propBuf.length()-2);
		insertBuf.append(colBuf).append(") VALUES (\n").append(propBuf).append(")");
		insertBuf.append("\n<selectKey keyProperty=\"id\" resultClass=\"java.lang.Integer\">\n");
		insertBuf.append("    select last_insert_id() as value \n</selectKey>");
		return insertBuf.toString();
	}

	public static String buildUpdateStmt(String tableName,Map<String,String> columnPropertyMap){
		if(columnPropertyMap==null||columnPropertyMap.size()==0)
			return "";
		StringBuilder updateBuf=new StringBuilder();
		updateBuf.append("UPDATE ").append(tableName).append(" \nSET\n");
		updateBuf.append(buildIsNotEmptyForMap(columnPropertyMap));
		updateBuf.append("gmt_modified = now() \nWHERE id=#id#");
		return updateBuf.toString();
	}
	
	public static String buildResultMapProperty(String columnName ,String propertyName){
		StringBuilder buf=new StringBuilder();
		buf.append("<result column=\"").append(columnName).append("\" property=\"").append(propertyName).append("\"/>");
		return buf.toString();
	}
	
	public static String buildResultMap(String resultClassName,Map<String,String> columnPropertyMap){
		if(columnPropertyMap==null||columnPropertyMap.size()==0)
			return "";
		StringBuilder buf=new StringBuilder();
		buf.append("<resultMap class=\"").append(resultClassName).append("\" id=\"").append(resultClassName).append("Result\">\n");
		for(Entry<String,String> entry:columnPropertyMap.entrySet()){
			buf.append("    ").append(buildResultMapProperty(entry.getKey() ,entry.getValue())).append("\n");
		}
		buf.append("</resultMap>");
		return buf.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String,String> columnPropertyMap=new LinkedHashMap<String,String>();
//		for(int i=1;i<4;i++){
//			columnPropertyMap.put("column"+i,"property"+i);
//		}
		System.out.println(buildInsertStmt("inquiry",columnPropertyMap));
		System.out.println(buildUpdateStmt("inquiry",columnPropertyMap));
		System.out.println(buildResultMap("inquiryDO",columnPropertyMap));
		
	}			
}
