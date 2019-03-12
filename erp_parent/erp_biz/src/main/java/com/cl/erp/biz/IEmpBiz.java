package com.cl.erp.biz;
import java.util.List;

import com.cl.erp.entity.Emp;
import com.cl.erp.entity.Menu;
import com.cl.erp.entity.Tree;
/**
 * 员工业务逻辑层接口
 * @author Administrator
 *
 */
public interface IEmpBiz extends IBaseBiz<Emp>{
	
	//校验用户登录
	Emp findByUserNameAndPwd(String username,String pwd);
	//修改密码
	public void updatePwd(Long uuid,String oldPwd, String newPwd);
	
	//重置密码
	void updatePwd_reset(Long uuid,String newPwd);
	
	//获取用户角色权限
	
	List<Tree> readEmpRoles(Long uuid);
	//更新用户角色
	void updateEmpRoles(Long uuid,String checkStr);
	/**
	 * 用户菜单权限
	 * @param uuid
	 * @return
	 */
	List<Menu> getMenusByEmpuuid(Long uuid);
	/**
	 * 获取用户的菜单
	 * @param uuid
	 * @return
	 */
	Menu readMenusByEmpuuid(Long uuid);
}

