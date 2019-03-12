package com.cl.erp.action;
import java.util.List;

import org.apache.struts2.views.JspSupportServlet;

import com.alibaba.fastjson.JSON;
import com.cl.erp.biz.IEmpBiz;
import com.cl.erp.entity.Emp;
import com.cl.erp.entity.Tree;

/**
 * 员工Action 
 * @author Administrator
 *
 */
public class EmpAction extends BaseAction<Emp> {

	private IEmpBiz empBiz;
	
	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
		setBaseBiz(empBiz);
	}
	
	
	private String oldPwd;
	private String newPwd;

	public String getOldPwd() {
		return oldPwd;
	}
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public IEmpBiz getEmpBiz() {
		return empBiz;
	}
	/**
	 * 修改密码调用的方法
	 */
	public void updatePwd(){
		Emp emp = getLoginUser();
		if(null==emp){
			ajaxReturn(false, "还未登录");
			return ;
		}
		try {
			empBiz.updatePwd(emp.getUuid(), oldPwd, newPwd);
			ajaxReturn(true, "修改密码成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "修改密码失败");
		}
	}
	/**
	 * 重置密码
	 */
	public void updatePwd_reset(){
		try {
			empBiz.updatePwd_reset(getId(),newPwd);
			ajaxReturn(true, "修改密码成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "修改密码失败");
		}
	}
	
	private String checkedStr;
	
	public String getCheckedStr() {
		return checkedStr;
	}
	public void setCheckedStr(String checkedStr) {
		this.checkedStr = checkedStr;
	}
	//用户角色信息
	public void readEmpRoles(){
		List<Tree> list = empBiz.readEmpRoles(getId());
		write(JSON.toJSONString(list));
	}
	
	public void updateEmpRoles(){
		try {
			empBiz.updateEmpRoles(getId(), checkedStr);
			ajaxReturn(true, "更新用户角色成功");
		} catch (Exception e) {
			ajaxReturn(false, "更新用户角色失败");
			e.printStackTrace();
		}
	}
	/**
	 * 获取用户菜单权限
	 */
	public void getMenusByEmpuuid(){
		if(null!=getLoginUser()){
			List<com.cl.erp.entity.Menu> list = empBiz.getMenusByEmpuuid(getLoginUser().getUuid());
			write(JSON.toJSONString(list));
		}
	}
	
}
