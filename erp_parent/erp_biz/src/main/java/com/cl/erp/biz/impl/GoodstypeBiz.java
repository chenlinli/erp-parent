package com.cl.erp.biz.impl;
import com.cl.erp.biz.IGoodstypeBiz;
import com.cl.erp.dao.IGoodstypeDao;
import com.cl.erp.entity.Goodstype;
/**
 * 商品分类业务逻辑类
 * @author Administrator
 *
 */
public class GoodstypeBiz extends BaseBiz<Goodstype> implements IGoodstypeBiz {

	private IGoodstypeDao goodstypeDao;
	
	public void setGoodstypeDao(IGoodstypeDao goodstypeDao) {
		this.goodstypeDao = goodstypeDao;
		setBaseDao(goodstypeDao);
	}

	
}
