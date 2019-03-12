package com.cl.erp.biz.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cl.erp.biz.IStoreoperBiz;
import com.cl.erp.dao.IEmpDao;
import com.cl.erp.dao.IGoodsDao;
import com.cl.erp.dao.IStoreDao;
import com.cl.erp.dao.IStoreoperDao;
import com.cl.erp.entity.Storeoper;
/**
 * 仓库操作记录业务逻辑类
 * @author Administrator
 *
 */
public class StoreoperBiz extends BaseBiz<Storeoper> implements IStoreoperBiz {

	private IStoreoperDao storeoperDao;
	private IEmpDao empDao;
	private IGoodsDao goodsDao;
	private IStoreDao storeDao;
	
	
	public IEmpDao getEmpDao() {
		return empDao;
	}

	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
	}

	public IGoodsDao getGoodsDao() {
		return goodsDao;
	}

	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	public IStoreDao getStoreDao() {
		return storeDao;
	}

	public void setStoreDao(IStoreDao storeDao) {
		this.storeDao = storeDao;
	}

	public IStoreoperDao getStoreoperDao() {
		return storeoperDao;
	}

	public void setStoreoperDao(IStoreoperDao storeoperDao) {
		this.storeoperDao = storeoperDao;
		setBaseDao(storeoperDao);
	}

	public List<Storeoper> getListByPage(Storeoper t1,Storeoper t2,Object param,int firstResult, int maxResults){
		 List<Storeoper> logList = super.getListByPage(t1, t2, param, firstResult, maxResults);
		 Map<Long,String> empNameMap = new HashMap<Long,String>();
		 Map<Long,String> goodsNameMap= new HashMap<Long,String>();
		 Map<Long,String> storeNameMap= new HashMap<Long,String>();
		 for (Storeoper log : logList) {
			log.setEmpname(getEmpName(log.getEmpuuid(), empNameMap, empDao));
			log.setGoodsname(getGoodsName(log.getGoodsuuid(), goodsNameMap, goodsDao));
			log.setStorename(getStoreName(log.getStoreuuid(), storeNameMap, storeDao));
		}
		 return logList;
	}
}
