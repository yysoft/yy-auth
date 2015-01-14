package net.caiban.auth.dashboard.dao.auth;

import java.util.List;

import net.caiban.auth.dashboard.domain.auth.AuthRight;
import net.caiban.auth.dashboard.domain.auth.AuthRole;

public interface AuthUserDao {
 
	public Integer insertUser(String account, String email, String password, Integer steping);
//	public Integer insertUserRole(Integer userId, Integer roleId);
	public List<AuthRight> queryRightOfUser(String parentCode, String account);
	public Integer countUser(String account, String password);
	public List<AuthRight> queryChildRightOfUser(String parentCode, String account);
	
	public Integer updatePassword(String password, String account);
	
	public Integer deleteUserRole(Integer userId);
	
	public Integer insertUserRole(Integer userId, Integer[] roleArr);
	
	public Integer queryUserIdByAccount(String account);
	
	public Integer updateSteping(Integer userId, Integer steping);
	
	public List<AuthRole> queryRoleOfUser(String account);
}
 
