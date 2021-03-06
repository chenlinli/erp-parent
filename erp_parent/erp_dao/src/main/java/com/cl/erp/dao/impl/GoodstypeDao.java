package com.cl.erp.dao.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import com.cl.erp.dao.IGoodstypeDao;
import com.cl.erp.entity.Goodstype;
/**
 * 商品分类数据访问类
 * @author Administrator
 *
 */
public class GoodstypeDao extends BaseDao<Goodstype> implements IGoodstypeDao {

	
	/**
	 * 构建查询条件
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(Goodstype goodstype1,Goodstype goodstype2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Goodstype.class);
		if(goodstype1!=null){
			if(goodstype1.getName()!=null &&  goodstype1.getName().trim().length()>0)
			{
				dc.add(Restrictions.like("name", goodstype1.getName(), MatchMode.ANYWHERE));			
			}
		
		}		
		return dc;
	}
	
	
}

