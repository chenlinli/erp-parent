package com.cl.erp.action;
import com.cl.erp.biz.IGoodstypeBiz;
import com.cl.erp.entity.Goodstype;

/**
 * 商品分类Action 
 * @author Administrator
 *
 */
public class GoodstypeAction extends BaseAction<Goodstype> {

	private IGoodstypeBiz goodstypeBiz;
	
	public void setGoodstypeBiz(IGoodstypeBiz goodstypeBiz) {
		this.goodstypeBiz = goodstypeBiz;
		setBaseBiz(goodstypeBiz);
	}
	
	
}
