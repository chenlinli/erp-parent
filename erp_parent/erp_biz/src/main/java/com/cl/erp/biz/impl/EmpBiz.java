package com.cl.erp.biz.impl;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;

import com.alibaba.fastjson.JSON;
import com.cl.erp.biz.IEmpBiz;
import com.cl.erp.dao.IEmpDao;
import com.cl.erp.dao.IMenuDao;
import com.cl.erp.dao.IRoleDao;
import com.cl.erp.entity.Emp;
import com.cl.erp.entity.Menu;
import com.cl.erp.entity.Role;
import com.cl.erp.entity.Tree;
import com.cl.erp.exception.ErpException;

import oracle.net.aso.e;
import oracle.net.aso.l;
import redis.clients.jedis.Jedis;
/**
 * 员工业务逻辑类
 * @author Administrator
 *
 */
public class EmpBiz extends BaseBiz<Emp> implements IEmpBiz {

	private int  hashIterations = 2;
	private IEmpDao empDao;
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
	}

	public IEmpDao getEmpDao() {
		return empDao;
	}

	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
		setBaseDao(empDao);
	}

	@Override
	public Emp findByUserNameAndPwd(String username, String pwd) {
		//加密密码
		pwd = encrypt(pwd, username);
		System.out.println(pwd);
		return empDao.findByUserNameAndPwd(username, pwd);
	}

	@Override
	public void updatePwd(Long uuid, String oldPwd, String newPwd) {
		//取出员工信息
		Emp emp = empDao.get(uuid);
		//加密
		String encrypt = encrypt(oldPwd, emp.getUsername()) ;
		//校验原始密码是否正确
		if(!encrypt.equals(emp.getPwd())){
			//自定义异常
			throw new ErpException("原始密码不正确");
		}
		//修改密码
		
		empDao.updatePwd(uuid, encrypt(newPwd, emp.getUsername()));
	}
	
	/**
	 * 新增员工:设置初始密码：密码加密
	 */
	public void add(Emp emp){
		//String pwd = emp.getPwd();
		//source:原密码
		//salt:搅乱码
		//加密后的密码
		String newPwd = encrypt(emp.getUsername(), emp.getUsername());
		//System.out.println("new :"+newPwd);
		//设置加密后的密码
		emp.setPwd(newPwd);
		//保存到数据库
		super.add(emp);
	}
	//加密
	public String encrypt(String pwd,String salt){
		//source:原密码
		//salt:搅乱码
		//hashIterations:散列次数，加密次数
		return new Md5Hash(pwd,salt,hashIterations).toString();
	}

	@Override
	@RequiresPermissions("重置密码")
	public void updatePwd_reset(Long uuid, String newPwd) {
		Emp emp = empDao.get(uuid);
		System.out.println(emp);
		empDao.updatePwd(uuid, encrypt(newPwd,emp.getUsername()));
	}
	//获取用户角色权限
	
	public List<Tree> readEmpRoles(Long uuid){
		List<Tree> list = new ArrayList<Tree>();
		Emp emp = empDao.get(uuid);
		//获取用户下的角色
		List<Role> empRoles = emp.getRoles();
		//所有角色
		List<Role> roles = roleDao.getList(null, null, null);
		Tree t1=null;
		for (Role role : roles) {
			t1=new Tree();
			t1.setId(role.getUuid()+"");
			t1.setText(role.getName());
			//是否勾选
			if(empRoles.contains(role)){
				t1.setChecked(true);
			}
			list.add(t1);
		}
		return list;
	}
		//更新用户角色
	@RequiresPermissions("用户角色设置")
	public void updateEmpRoles(Long uuid,String checkStr){
		//获取用户，清空用户角色
		Emp emp = empDao.get(uuid);
		emp.setRoles(new ArrayList<Role>());
		String[] ids=checkStr.split(",");
		Role role = null;
		for (String id : ids) {
			//设置用户角色
			role=roleDao.get(Long.valueOf(id));
			emp.getRoles().add(role);
		}
		try {
			//清楚缓存中的菜单权限
			jedis.del("menus_"+uuid);
		} catch (Exception e) {
			//清除缓存失败
			System.out.println("清除缓存失败");
			e.printStackTrace();
		}
	}
	
	public List<Menu> getMenusByEmpuuid(Long uuid){
		//从缓存取,不同的人有不同的权限，分开存储，否则覆盖
		String menusJson = jedis.get("menus_"+uuid);
		List<Menu> menus=null;
		if(null!=menusJson){
			//存在,取出转成List
			System.out.println("缓存里存在menus");
			menus = JSON.parseArray(menusJson,Menu.class);
		}else{
			//第一次查询
			System.out.println("从数据库里查询");
			menus = empDao.getMenusByEmpuuid(uuid);
			jedis.set("menus_"+uuid, JSON.toJSONString(menus));
		}
		return menus;
	}

	@Override
	public Menu readMenusByEmpuuid(Long uuid) {
		Menu root = menuDao.get("0");
		List<Menu> list = empDao.getMenusByEmpuuid(uuid);
		//根菜单
		Menu menu = cloneMenu(root);
		//一级菜单
		Menu m1=null,_m2=null;
		for (Menu mm : root.getMenus()) {
			m1=cloneMenu(mm);
			for(Menu m2:mm.getMenus()){
				//用户有这个二级菜单
				if(list.contains(m2)){
					_m2=cloneMenu(m2);
					//二级菜单加入一级菜单
					m1.getMenus().add(_m2);
				}
			}
			//m1有二级菜单才加入
			if(m1.getMenus().size()>0){
				//一级菜单有二级菜单就加入根菜单
				menu.getMenus().add(m1);
			}
		}
		return menu;
	}
	
	private Menu cloneMenu(Menu src){
		Menu menu = new Menu();
		menu.setIcon(src.getIcon());
		menu.setMenuid(src.getMenuid());
		menu.setMenuname(src.getMenuname());
		menu.setUrl(src.getUrl());
		menu.setMenus(new ArrayList<Menu>());
		return menu;
	}

	
}
