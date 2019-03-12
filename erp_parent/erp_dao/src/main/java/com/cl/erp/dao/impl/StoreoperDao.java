package com.cl.erp.dao.impl;
import java.util.Calendar;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import com.cl.erp.dao.IStoreoperDao;
import com.cl.erp.entity.Storeoper;

import oracle.net.aso.d;
/**
 * 仓库操作记录数据访问类
 * @author Administrator
 *
 */
public class StoreoperDao extends BaseDao<Storeoper> implements IStoreoperDao {

	
	/**
	 * 构建查询条件
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(Storeoper storeoper1,Storeoper storeoper2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Storeoper.class);
		if(storeoper1!=null){
			//根据类型查询
			if(storeoper1.getType()!=null &&  storeoper1.getType().trim().length()>0)
			{
				dc.add(Restrictions.like("type", storeoper1.getType(), MatchMode.ANYWHERE));			
			}
			//商品
			if(storeoper1.getGoodsuuid()!=null)
			{
				dc.add(Restrictions.eq("goodsuuid", storeoper1.getGoodsuuid()));			
			}
			//仓库
			if(storeoper1.getStoreuuid()!=null)
			{
				dc.add(Restrictions.eq("storeuuid", storeoper1.getStoreuuid()));			
			}
			//员工
			if(storeoper1.getEmpuuid()!=null)
			{
				dc.add(Restrictions.eq("empuuid", storeoper1.getEmpuuid()));			
			}
			//起始操作时间
			if(storeoper1.getOpertime()!=null)
			{
				Calendar dar = Calendar.getInstance();
				dar.setTime(storeoper1.getOpertime());
				dar.set(Calendar.HOUR, 0);
				dar.set(Calendar.MINUTE,0);
				dar.set(Calendar.SECOND, 0);
				dar.set(Calendar.MILLISECOND, 0);
				dc.add(Restrictions.ge("opertime",dar.getTime()));			
			}
			if(storeoper1!=null){
				
				if(storeoper2.getOpertime()!=null)
				{
					//终止日期
					Calendar dar = Calendar.getInstance();
					dar.setTime(storeoper2.getOpertime());
					dar.set(Calendar.HOUR, 23);
					dar.set(Calendar.MINUTE,59);
					dar.set(Calendar.SECOND, 59);
					dar.set(Calendar.MILLISECOND, 999);
					//"2017-09-04 12:23:34"--->2017-09-04 23:59:59:999
					dc.add(Restrictions.le("opertime", dar.getTime()));			
				}
			}
		
		}		
		return dc;
	}
	
	
}

