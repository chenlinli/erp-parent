package com.cl.erp.biz;
import java.util.List;

import com.cl.erp.entity.Role;
import com.cl.erp.entity.Tree;
/**
 * 角色业务逻辑层接口
 * @author Administrator
 *
 */
public interface IRoleBiz extends IBaseBiz<Role>{
	/**
	 * 获取角色菜单权限
	 * 
	 * @return
	 */
	List<Tree> readRoleMenus(Long uuid);
	
	/**
	 * 更新角色权限
	 * @param uuid：角色uuid
	 * @param checkedStr：勾选的权限，逗号分隔
	 */
	void updateRoleMenus(Long uuid,String checkedStr);
}

