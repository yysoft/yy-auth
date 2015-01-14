package net.caiban.auth.dashboard.service.auth;

import java.util.List;

import net.caiban.auth.dashboard.domain.auth.AuthRight;
import net.caiban.auth.dashboard.dto.ExtTreeDto;

public interface AuthRightService {
 
	public List<ExtTreeDto> queryTreeNode(String parentCode);
	/**
	 * 创建权限信息，code根据类别生成
	 * code生成规则：同级类别最大code＋1
	 */
	public Integer createRight(AuthRight right, String parentCode);
	/**
	 * 更新权限基本信息，不更新code
	 */
	public Integer updateRight(AuthRight right);
	/**
	 * 删除自己及其所有子类别
	 */
	public Integer deleteRightByCode(String code);
	
	public AuthRight queryOneRight(String code);
	
}
 
