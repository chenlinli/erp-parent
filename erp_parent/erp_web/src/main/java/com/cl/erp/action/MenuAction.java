package com.cl.erp.action;
import com.alibaba.fastjson.JSON;
import com.cl.erp.biz.IEmpBiz;
import com.cl.erp.biz.IMenuBiz;
import com.cl.erp.entity.Menu;

/**
 * 菜单Action 
 * @author Administrator
 *
 */
public class MenuAction extends BaseAction<Menu> {

	private IMenuBiz menuBiz;
	private IEmpBiz empBiz;
	
	public IEmpBiz getEmpBiz() {
		return empBiz;
	}
	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
	}
	public IMenuBiz getMenuBiz() {
		return menuBiz;
	}
	public void setMenuBiz(IMenuBiz menuBiz) {
		this.menuBiz = menuBiz;
		setBaseBiz(menuBiz);
	}
	//获取菜单,通过获取主菜单，自关联就会带出旗下所有的菜单
	public void getMenuTree(){
		//Menu menu = menuBiz.get("0");
		Menu menu = empBiz.readMenusByEmpuuid(getLoginUser().getUuid());
		write(JSON.toJSONString(menu));
	}
	
	
}
