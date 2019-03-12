package com.cl.erp.biz.impl;
import com.cl.erp.biz.IStoreBiz;
import com.cl.erp.dao.IStoreDao;
import com.cl.erp.entity.Store;
/**
 * 仓库业务逻辑类
 * @author Administrator
 *
 */
public class StoreBiz extends BaseBiz<Store> implements IStoreBiz {

	private IStoreDao storeDao;
	
	public void setStoreDao(IStoreDao storeDao) {
		this.storeDao = storeDao;
		setBaseDao(storeDao);
	}

	
}
