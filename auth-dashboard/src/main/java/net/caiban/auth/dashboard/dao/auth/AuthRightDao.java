package net.caiban.auth.dashboard.dao.auth;

import java.util.List;

import net.caiban.auth.dashboard.domain.auth.AuthRight;

public interface AuthRightDao {
 
	/**
	 * 根据父权限Code查找子权限，
	 */
	public List<AuthRight> queryChild(String parentCode);
	public Integer countChild(String parentCode);
	/**
	 * 创建权限信息，code根据类别生成
	 * code生成规则：同级类别最大code＋1
	 */
	public Integer createRight(AuthRight right);
	/**
	 *  
	 */
	public String queryMaxCodeOfChild(String parentCode);
	/**
	 * 更新权限基本信息，不更新code
	 */
	public Integer updateRight(AuthRight right);
	/**
	 * 删除自己及其所有子类别
	 */
	public Integer deleteRightByCode(String code);
	
	public AuthRight queryOneRight(String code);
	
	public Integer insertRight(AuthRight right);
	
	public Integer queryIdByCode(String code);
	
	public Integer deleteDeptRight(String code);
	
	public Integer deleteRoleRight(String code);
	
	public String queryNameByCode(String code);
}
 
