package com.cl.erp.dao.impl;
import java.util.Calendar;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import com.cl.erp.dao.IInventoryDao;
import com.cl.erp.entity.Inventory;
/**
 * 盘盈盘亏数据访问类
 * @author Administrator
 *
 */
public class InventoryDao extends BaseDao<Inventory> implements IInventoryDao {

	
	/**
	 * 构建查询条件
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(Inventory inventory1,Inventory inventory2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Inventory.class);
		if(inventory1!=null){
			//类型查询
			if(inventory1.getType()!=null &&  inventory1.getType().trim().length()>0)
			{
				dc.add(Restrictions.eq("type", inventory1.getType()));			
			}
			//状态查询：审核/未审核/完成
			if(inventory1.getState()!=null &&  inventory1.getState().trim().length()>0)
			{
				dc.add(Restrictions.eq("state", inventory1.getState()));			
			}
			if(inventory1.getRemark()!=null &&  inventory1.getRemark().trim().length()>0)
			{
				dc.add(Restrictions.like("remark", inventory1.getRemark(), MatchMode.ANYWHERE));			
			}
			
			if(null!=inventory1.getCreatetime()){
				Calendar car = Calendar.getInstance();
				car.setTime(inventory1.getCreatetime());
				car.set(Calendar.HOUR, 0);
				car.set(Calendar.MINUTE,0);
				car.set(Calendar.SECOND,0);
				car.set(Calendar.MILLISECOND,0);
				dc.add(Restrictions.ge("createtime", car.getTime()));
			}
			
			if(null!=inventory1.getChecktime()){
				Calendar car = Calendar.getInstance();
				car.setTime(inventory1.getChecktime());
				car.set(Calendar.HOUR, 0);
				car.set(Calendar.MINUTE,0);
				car.set(Calendar.SECOND,0);
				car.set(Calendar.MILLISECOND,0);
				dc.add(Restrictions.ge("checktime", car.getTime()));
			}	
		}	
		if(inventory2!=null){
			
			if(null!=inventory2.getCreatetime()){
				Calendar car = Calendar.getInstance();
				car.setTime(inventory2.getCreatetime());
				car.set(Calendar.HOUR, 23);
				car.set(Calendar.MINUTE,59);
				car.set(Calendar.SECOND,59);
				car.set(Calendar.MILLISECOND,999);
				dc.add(Restrictions.le("createtime", car.getTime()));
			}
			if(null!=inventory2.getChecktime()){
				Calendar car = Calendar.getInstance();
				car.setTime(inventory2.getChecktime());
				car.set(Calendar.HOUR, 23);
				car.set(Calendar.MINUTE,59);
				car.set(Calendar.SECOND,59);
				car.set(Calendar.MILLISECOND,999);
				dc.add(Restrictions.le("checktime", car.getTime()));
			}
					}
		return dc;
	}
	
	
}

