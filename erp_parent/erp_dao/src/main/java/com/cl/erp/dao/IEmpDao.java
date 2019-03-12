package com.cl.erp.dao;

import java.util.List;

import com.cl.erp.entity.Emp;
import com.cl.erp.entity.Menu;
/**
 * 员工数据访问接口
 * @author Administrator
 *
 */
public interface IEmpDao extends IBaseDao<Emp>{
	//校验用户登录
	Emp findByUserNameAndPwd(String username,String pwd);
	void updatePwd(Long uuid,String newPwd);
	List<Menu> getMenusByEmpuuid(Long uuid);
}
