package net.caiban.auth.dashboard.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author mays@caiban.net
 * 用于Ext表格分页时返回的数据信息
 * 格式:
 */
public class PageDto<E> implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	final static int DEFAULT_SIZE		= 20;
	final static String DEFAULT_DIR		= "desc";

//	private Integer recordsReturned;  	//返回到页面的数据量
	private Integer totalRecords;  		//总数据数
	private Integer start;  		//页首
	private String sort;  			//排序字段
	private String dir; 			//desc or asc
	private Integer limit;  			//分页大小
//	private Integer totalPages;        //总页数，通过计算获得，不能直接赋值
//	private Integer currentPage = 0;// 当前页
 
	private List<E> records;// 记录集

	public PageDto() {
		this(DEFAULT_SIZE,null,DEFAULT_DIR);
	}

	public PageDto(int pageSize){
		this(pageSize,null,DEFAULT_DIR);
	}

	public PageDto(int limit, String sort, String dir){
		if(limit<=0){
			limit=DEFAULT_SIZE;
		}
		this.limit = limit;
		this.sort = sort;
		this.dir = dir;
	}


	/**
	 * @return the totalRecords
	 */
	public Integer getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords the totalRecords to set
	 */
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * @return the records
	 */
	public List<E> getRecords() {
		return records;
	}

	/**
	 * @param records the records to set
	 */
	public void setRecords(List<E> records) {
		this.records = records;
	}

	/**
	 * @return the start
	 */
	public Integer getStart() {
		if(start==null){
			start=0;
		}
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Integer start) {
		this.start = start;
	}

	/**
	 * @return the limit
	 */
	public Integer getLimit() {
		if(limit==null){
			limit=20;
		}
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

//	/**
//	 * 通过计算获取总页数
//	 * @return
//	 */
//	public Integer totalPages() {
//		totalPages = totalRecords / pageSize + 1;
//		if (totalRecords % pageSize == 0) {
//			totalPages--;
//		}
//		return totalPages;
//	}

}
