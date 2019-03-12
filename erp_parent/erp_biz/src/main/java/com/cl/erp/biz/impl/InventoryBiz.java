package com.cl.erp.biz.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder.In;

import com.cl.erp.biz.IInventoryBiz;
import com.cl.erp.dao.IEmpDao;
import com.cl.erp.dao.IGoodsDao;
import com.cl.erp.dao.IInventoryDao;
import com.cl.erp.dao.IStoreDao;
import com.cl.erp.dao.IStoredetailDao;
import com.cl.erp.dao.IStoreoperDao;
import com.cl.erp.entity.Emp;
import com.cl.erp.entity.Inventory;
import com.cl.erp.entity.Storedetail;
import com.cl.erp.entity.Storeoper;
import com.cl.erp.exception.ErpException;
/**
 * 盘盈盘亏业务逻辑类
 * @author Administrator
 *
 */
public class InventoryBiz extends BaseBiz<Inventory> implements IInventoryBiz {

	private IInventoryDao inventoryDao;
	private IEmpDao empDao;
	private IStoreDao storeDao;
	private IGoodsDao goodsDao;
	private IStoredetailDao storedetailDao;
	private IStoreoperDao storeoperDao;
	
	public IStoreoperDao getStoreoperDao() {
		return storeoperDao;
	}

	public void setStoreoperDao(IStoreoperDao storeoperDao) {
		this.storeoperDao = storeoperDao;
	}

	public IStoredetailDao getStoredetailDao() {
		return storedetailDao;
	}

	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
	}

	public IGoodsDao getGoodsDao() {
		return goodsDao;
	}

	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	public IEmpDao getEmpDao() {
		return empDao;
	}

	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
	}

	public IStoreDao getStoreDao() {
		return storeDao;
	}

	public void setStoreDao(IStoreDao storeDao) {
		this.storeDao = storeDao;
	}

	public IInventoryDao getInventoryDao() {
		return inventoryDao;
	}

	public void setInventoryDao(IInventoryDao inventoryDao) {
		this.inventoryDao = inventoryDao;
		setBaseDao(inventoryDao);
	}
	
	public List<Inventory> getListByPage(Inventory t1,Inventory t2,Object param,int firstResult, int maxResults){
		List<Inventory> list = super.getListByPage(t1, t2, param, firstResult, maxResults);
		Map<Long,String> empNameMap = new HashMap<Long,String>();
		Map<Long,String> storeNameMap = new HashMap<Long,String>();
		Map<Long,String> goodsMap = new HashMap<Long,String>();
		for (Inventory inventory : list) {
			inventory.setCheckername(getEmpName(inventory.getChecker(),empNameMap , empDao));
			inventory.setCreatername(getEmpName(inventory.getCreater(), empNameMap, empDao));
			inventory.setStorename(getStoreName(inventory.getStoreuuid(), storeNameMap, storeDao));
			inventory.setGoodsname(getGoodsName(inventory.getGoodsuuid(), goodsMap, goodsDao));
		}
		return list;
	}

	public void add(Inventory inventory){
		inventory.setCreatetime(new Date());
		inventory.setState(Inventory.STATE_CREATE);
		super.add(inventory);
	}

	@Override
	public void doCheck(long uuid, Long empuuid) {
		// TODO Auto-generated method stub
		Inventory inventory = inventoryDao.get(uuid);
		if(inventory.getState().equals(Inventory.STATE_CHECK)){
			throw new ErpException("亲,已经审核过！");
		}
		//设置审核员
		inventory.setChecker(empuuid);
		//设置审核时间
		inventory.setChecktime(new Date());
		//设置已经审核过
		inventory.setState(Inventory.STATE_CHECK);
		
		//修改库存
		Storedetail storedetail = new Storedetail();
		storedetail.setStoreuuid(inventory.getStoreuuid());
		storedetail.setGoodsuuid(inventory.getGoodsuuid());
		List<Storedetail> list = storedetailDao.getList(storedetail, null, null);
		Storedetail sd = list.get(0);
		if(sd!=null){		
			if(inventory.getType().equals(Inventory.TYPE_OUT)){
				//盘亏
				sd.setNum(sd.getNum()-inventory.getNum());
				if(sd.getNum()<0){
					throw new ErpException("库存不足，盘亏失败");
				}
			}else if(inventory.getType().equals(Inventory.TYPE_IN)){
				sd.setNum(sd.getNum()+inventory.getNum());
			}
		}else{
			if(inventory.getType().equals(Inventory.TYPE_OUT)){
				throw new ErpException("库存不足，盘亏失败");
			}else if(inventory.getType().equals(Inventory.TYPE_IN)){
				sd.setNum(sd.getNum()+inventory.getNum());
			}
		}
		//增加库存修改记录
		Storeoper storeoper = new Storeoper();
		storeoper.setEmpuuid(empuuid);
		storeoper.setGoodsuuid(inventory.getGoodsuuid());
		storeoper.setOpertime(inventory.getChecktime());
		storeoper.setNum(inventory.getNum());
		storeoper.setStoreuuid(inventory.getStoreuuid());
		if(inventory.getType().equals(Inventory.TYPE_OUT)){
			storeoper.setType(Storeoper.TYPE_OUT);	
		}else{
			storeoper.setType(Storeoper.TYPE_IN);
		}
		storeoperDao.add(storeoper);
	}
	
}
