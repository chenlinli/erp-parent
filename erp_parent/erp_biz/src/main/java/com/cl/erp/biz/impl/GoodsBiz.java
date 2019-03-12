package com.cl.erp.biz.impl;
import com.cl.erp.biz.IGoodsBiz;
import com.cl.erp.dao.IGoodsDao;
import com.cl.erp.entity.Goods;
/**
 * 商品业务逻辑类
 * @author Administrator
 *
 */
public class GoodsBiz extends BaseBiz<Goods> implements IGoodsBiz {

	private IGoodsDao goodsDao;
	
	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
		setBaseDao(goodsDao);
	}

	
}
