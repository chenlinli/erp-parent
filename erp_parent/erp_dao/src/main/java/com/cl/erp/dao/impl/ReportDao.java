package com.cl.erp.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.cl.erp.dao.IReportDao;

public class ReportDao extends HibernateDaoSupport implements IReportDao{

	@Override
	public List ordersReport(Date startDate,Date endDate) {
		//原生sql与HQL的区别
		/*
		 * 不能直接使用表名，用类名，首字母大写
		 * 关联关系： 多的一方的“属性”（字段）等于一的一方的“表”（类）别名：多：goods gs,orders o一：,goodstype gt,orderdetails:ol
		 */
		String hql="select new Map(gt.name as name,sum(ol.money) as y) "+
						"from Goodstype gt,Goods gs,Orderdetail ol,Orders o "+
						"where ol.goodsuuid = gs.uuid and gs.goodstype = gt "+
						"and ol.orders = o and o.type='2' ";
		
		List<Date> list = new ArrayList<Date>();
				
		if(startDate!=null){
			hql+="and o.createtime >= ? ";
			list.add(startDate);
		}
		if(null!=endDate){ 
			hql+="and o.createtime <= ? ";
			list.add(endDate);
		}
		hql+="group by gt.name ";
		return getHibernateTemplate().find(hql,list.toArray(new Date[]{}));
	}

	@Override
	public List<Map<String,Object>> tendReport(int year) {
		/*String hql="select month(o.createtime),sum(ol.money) "
				+ "from Orderdetail ol,Orders o "
				+ "where ol.orders=o  "
				+ "and o.type='2' "
				+ "and year(o.createtime) = ? "
				+ "group by month(o.createtime) ";*/
		String hql = "select new Map(month(o.createtime) as name,sum(ol.money) as y) "
				+ "from Orderdetail ol, Orders o "
				+ "where ol.orders=o "
				+ "and o.type='2' and year(o.createtime)=? "
				+ "group by month(o.createtime)";
		
		//return (List<Map<String, Object>>) getHibernateTemplate().find(hql, year);
		
		return (List<Map<String, Object>>) getHibernateTemplate().find(hql,year);
	}

}
