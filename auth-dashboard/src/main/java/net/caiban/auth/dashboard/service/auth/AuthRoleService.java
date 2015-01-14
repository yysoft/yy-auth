package net.caiban.auth.dashboard.service.auth;

import java.util.List;

import net.caiban.auth.dashboard.domain.auth.AuthRole;
import net.caiban.auth.dashboard.dto.ExtTreeDto;

public interface AuthRoleService {

	public List<AuthRole> queryRole();

	public Integer createRole(AuthRole role);

	/**
	 * 删除角色信息 先删除角色权限关联，再删除角色
	 */
	public Integer deleteRole(Integer roleId);

	public List<ExtTreeDto> queryRightTreeNode(String parentCode, Integer roleId);

	public Integer updateRole(AuthRole role);

	public Integer updateRoleRight(Integer roleId, Integer rightId,
			Boolean checked);
}
