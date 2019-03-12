package com.cl.erp.action;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.cl.erp.biz.IRoleBiz;
import com.cl.erp.entity.Role;
import com.cl.erp.entity.Tree;

/**
 * 角色Action 
 * @author Administrator
 *
 */
public class RoleAction extends BaseAction<Role> {

	private IRoleBiz roleBiz;
	
	public void setRoleBiz(IRoleBiz roleBiz) {
		this.roleBiz = roleBiz;
		setBaseBiz(roleBiz);
	}
	
	public void readRoleMenus(){
		List<Tree> menus = roleBiz.readRoleMenus(getId());
		write(JSON.toJSONString(menus));
	}
	
	private String checkedStr;//选中的菜单id

	public String getCheckedStr() {
		return checkedStr;
	}

	public void setCheckedStr(String checkedStr) {
		this.checkedStr = checkedStr;
	}

	public IRoleBiz getRoleBiz() {
		return roleBiz;
	}
	
	public void updateRoleMenu(){
		try {
			roleBiz.updateRoleMenus(getId(), checkedStr);
			ajaxReturn(true, "更新角色权限成功");
		} catch (Exception e) {
			ajaxReturn(false, "更新角色权限失败");
			e.printStackTrace();
		}
	}
	
	
}
