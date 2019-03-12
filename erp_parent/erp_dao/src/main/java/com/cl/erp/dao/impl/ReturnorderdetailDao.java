package com.cl.erp.dao.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import com.cl.erp.dao.IReturnorderdetailDao;
import com.cl.erp.entity.Returnorderdetail;
/**
 * 退货订单明细数据访问类
 * @author Administrator
 *
 */
public class ReturnorderdetailDao extends BaseDao<Returnorderdetail> implements IReturnorderdetailDao {

	
	/**
	 * 构建查询条件
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(Returnorderdetail returnorderdetail1,Returnorderdetail returnorderdetail2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Returnorderdetail.class);
		if(returnorderdetail1!=null){
			if(returnorderdetail1.getGoodsname()!=null &&  returnorderdetail1.getGoodsname().trim().length()>0)
			{
				dc.add(Restrictions.like("goodsname", returnorderdetail1.getGoodsname(), MatchMode.ANYWHERE));			
			}
			if(returnorderdetail1.getState()!=null &&  returnorderdetail1.getState().trim().length()>0)
			{
				dc.add(Restrictions.eq("state", returnorderdetail1.getState()));			
			}
			//根据订单号查询
			if(null!=returnorderdetail1.getReturnorders()&&returnorderdetail1.getReturnorders().getUuid()!=null){
				dc.add(Restrictions.eq("returnorders", returnorderdetail1.getReturnorders()));
			}
		}		
		return dc;
	}
	
	
}

