package com.cl.erp.dao.impl;
import java.util.List;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.cl.erp.dao.IEmpDao;
import com.cl.erp.entity.Emp;
import com.cl.erp.entity.Menu;

import oracle.net.aso.l;
import oracle.net.aso.p;
/**
 * 员工数据访问类
 * @author Administrator
 *
 */
public class EmpDao extends BaseDao<Emp> implements IEmpDao {

	
	/**
	 * 构建查询条件
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(Emp emp1,Emp emp2,Object param){
		System.out.println(emp1);
		DetachedCriteria dc=DetachedCriteria.forClass(Emp.class);
		if(emp1!=null){
			if(emp1.getUsername()!=null &&  emp1.getUsername().trim().length()>0)
			{
				dc.add(Restrictions.like("username", emp1.getUsername(), MatchMode.ANYWHERE));			
			}
			if(emp1.getPwd()!=null &&  emp1.getPwd().trim().length()>0)
			{
				dc.add(Restrictions.like("pwd", emp1.getPwd(), MatchMode.ANYWHERE));			
			}
			if(emp1.getName()!=null &&  emp1.getName().trim().length()>0)
			{
				dc.add(Restrictions.like("name", emp1.getName(), MatchMode.ANYWHERE));			
			}
			if(emp1.getEmail()!=null &&  emp1.getEmail().trim().length()>0)
			{
				dc.add(Restrictions.like("email", emp1.getEmail(), MatchMode.ANYWHERE));			
			}
			if(emp1.getTele()!=null &&  emp1.getTele().trim().length()>0)
			{
				dc.add(Restrictions.like("tele", emp1.getTele(), MatchMode.ANYWHERE));			
			}
			if(emp1.getAddress()!=null &&  emp1.getAddress().trim().length()>0)
			{
				dc.add(Restrictions.like("address", emp1.getAddress(), MatchMode.ANYWHERE));			
			}
			if(null!=emp1.getDep()&&null!=emp1.getDep().getUuid()){
				dc.add(Restrictions.eq("dep", emp1.getDep()));
			}
			
			if(null!=emp1.getGender()){
				dc.add(Restrictions.eq("gender", emp1.getGender()));
			}
			
			if(null!=emp1.getBirthday()){
				dc.add(Restrictions.ge("birthday", emp1.getBirthday()));
			}
		
		}		
		//结束日期
		if(null != emp2 && null!=emp2.getBirthday()){
			dc.add(Restrictions.le("birthday", emp2.getBirthday()));
		}
		return dc;
	}

	/**
	 * 用户登录
	 */
	@Override
	public Emp findByUserNameAndPwd(String username, String pwd) {
		//查询前加密
		String hql = "from Emp where username = ? and pwd = ?";
		List<Emp> list = (List<Emp>) getHibernateTemplate().find(hql, username,pwd);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	

	/**
	 * 修改密码
	 */
	@Override
	public void updatePwd(Long uuid, String newPwd) {
		String hql = "update Emp set pwd = ? where uuid = ?";
		getHibernateTemplate().bulkUpdate(hql, newPwd,uuid);
	}

	@Override
	public List<Menu> getMenusByEmpuuid(Long uuid) {
		String hql="select m from Emp e join e.roles r join r.menus m where e.uuid=?";
		return (List<Menu>) getHibernateTemplate().find(hql, uuid);
	}
	
}

