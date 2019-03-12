package com.cl.erp.action;
import com.cl.erp.biz.IStoreoperBiz;
import com.cl.erp.entity.Storeoper;

/**
 * 仓库操作记录Action 
 * @author Administrator
 *
 */
public class StoreoperAction extends BaseAction<Storeoper> {

	private IStoreoperBiz storeoperBiz;
	
	public void setStoreoperBiz(IStoreoperBiz storeoperBiz) {
		this.storeoperBiz = storeoperBiz;
		setBaseBiz(storeoperBiz);
	}
	
	
}
