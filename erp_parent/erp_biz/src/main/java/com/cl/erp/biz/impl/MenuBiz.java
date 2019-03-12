package com.cl.erp.biz.impl;
import com.cl.erp.biz.IMenuBiz;
import com.cl.erp.dao.IMenuDao;
import com.cl.erp.entity.Menu;
/**
 * 菜单业务逻辑类
 * @author Administrator
 *
 */
public class MenuBiz extends BaseBiz<Menu> implements IMenuBiz {

	private IMenuDao menuDao;
	
	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
		setBaseDao(menuDao);
	}

	
}
