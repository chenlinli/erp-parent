package com.cl.erp.biz.impl;
import java.util.ArrayList;
import java.util.List;

import com.cl.erp.biz.IRoleBiz;
import com.cl.erp.dao.IMenuDao;
import com.cl.erp.dao.IRoleDao;
import com.cl.erp.entity.Emp;
import com.cl.erp.entity.Menu;
import com.cl.erp.entity.Role;
import com.cl.erp.entity.Tree;

import oracle.net.aso.e;
import redis.clients.jedis.Jedis;
/**
 * 角色业务逻辑类
 * @author Administrator
 *
 */
public class RoleBiz extends BaseBiz<Role> implements IRoleBiz {

	private IRoleDao roleDao;
	private IMenuDao menuDao;
	private Jedis jedis;
	
	public Jedis getJedis() {
		return jedis;
	}

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	public IMenuDao getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}

	public IRoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
		setBaseDao(roleDao);
	}

	/**
	 * uuid:角色uuid
	 */
	@Override
	public List<Tree> readRoleMenus(Long uuid) {
		List<Tree> list = new ArrayList<Tree>();
		//获取角色菜单
		Role role = roleDao.get(uuid);
		List<Menu> menus = role.getMenus();
		//根菜单
		Menu root= menuDao.get("0");
		Tree t1=null,t2=null;
		//一级菜单
		for (Menu menu : root.getMenus()) {
			t1=new Tree();
			t1.setText(menu.getMenuname());
			t1.setId(menu.getMenuid());
			//二级菜单
			for(Menu m2:menu.getMenus()){
				t2=new Tree();
				t2.setId(m2.getMenuid());
				t2.setText(m2.getMenuname());
				if(menus.contains(m2)){
					t2.setChecked(true);
				}
				t1.getChildren().add(t2);
			}
			list.add(t1);
		}
		return list;
	}
/**
 * 更新角色权限
 * @param uuid：角色uuid
 * @param checkedStr：勾选的权限，逗号分隔
 */
	public void updateRoleMenus(Long uuid,String checkedStr){
		Role role = roleDao.get(uuid);
		//角色的权限置空
		role.setMenus(new ArrayList<Menu>());
		String[] ids = checkedStr.split(",");
		Menu menu = null;
		for (String id : ids) {
			menu = menuDao.get(id);
			role.getMenus().add(menu);
		}
		//通过角色找角色对应的所有用户
		List<Emp> emps = role.getEmps();
		for (Emp emp : emps) {
			//清楚每个用的菜单权限
			try {
				jedis.del("menus_"+emp.getUuid());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
}
