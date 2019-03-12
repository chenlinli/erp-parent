package com.cl.erp.biz.impl;

import java.util.List;
import java.util.Map;

import com.cl.erp.biz.IBaseBiz;
import com.cl.erp.dao.IBaseDao;
import com.cl.erp.dao.IEmpDao;
import com.cl.erp.dao.IGoodsDao;
import com.cl.erp.dao.IStoreDao;
import com.cl.erp.dao.ISupplierDao;

public class BaseBiz<T> implements IBaseBiz<T> {

	/** 数据访问注入*/
	private IBaseDao<T> baseDao;

	public void setBaseDao(IBaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}
	
	/**
	 * 条件查询
	 * @param t1
	 * @return
	 */
	public List<T> getList(T t1,T t2,Object param){
		return baseDao.getList(t1,t2,param);
	}
	
	/**
	 * 条件查询
	 * @param t1
	 * @return
	 */
	public List<T> getListByPage(T t1,T t2,Object param,int firstResult, int maxResults){
		//System.out.println("biz");
		return baseDao.getListByPage(t1,t2,param,firstResult, maxResults);
	}

	@Override
	public long getCount(T t1,T t2,Object param) {
		return baseDao.getCount(t1,t2,param);
	}

	@Override
	public void add(T t) {
		baseDao.add(t);
	}

	/**
	 * 删除
	 */
	public void delete(Long uuid){
		baseDao.delete(uuid);
	}
	
	/**
	 * 通过编号查询对象
	 * @param uuid
	 * @return
	 */
	public T get(Long uuid){
		return baseDao.get(uuid);
	}
	
	/**
	 * 通过字符串编号查询对象
	 * @param uuid
	 * @return
	 */
	public T get(String uuid){
		return baseDao.get(uuid);
	}
	
	/**
	 * 更新
	 */
	public void update(T t){
		baseDao.update(t);
	}
	
	public String getGoodsName(Long uuid,Map<Long,String> goodsMap,IGoodsDao goodsDao){
		 if(uuid==null){
			 return null;
		 }
		 String goodsName = goodsMap.get(uuid);
		 if(null==goodsName){
			 goodsName = goodsDao.get(uuid).getName();
			 goodsMap.put(uuid, goodsName);
		 }
		 return goodsName;
	}
	
	public String getStoreName(Long uuid,Map<Long,String> storeMap,IStoreDao storeDao){
		 if(uuid==null){
			 return null;
		 }
		 String storeName = storeMap.get(uuid);
		 if(null==storeName){
			 storeName=storeDao.get(uuid).getName();
			 storeMap.put(uuid, storeName);
		 }
		 return storeName;
	}
	
	/**
	 * 员工编号和姓名缓存
	 * @param uuid
	 * @param empNameMap
	 * @return 员工名称
	 */
	public String getEmpName(Long uuid,Map<Long,String> empNameMap,IEmpDao empDao){
		//缓存取出员工名称
		if(null==uuid)	
			return null;
		String   empName=empNameMap.get(uuid);
		if(null==empName){
			//没有找到员工名称
			empName = empDao.get(uuid).getName();
			//存入缓存
			empNameMap.put(uuid, empName);
		}
		
		return empName;
		
	}

	/**
	 * 供应商编号和姓名缓存
	 * @param uuid
	 * @param empNameMap
	 * @return 供应商名称
	 */
	public String getSupplierName(Long uuid,Map<Long,String> supplierNameMap,ISupplierDao supplierDao){
		if(null==uuid)	
			return null;
		//缓存取出员工名称
		String   supplierName=supplierNameMap.get(uuid);
		if(null==supplierName){
			//没有找到员工名称
			supplierName = supplierDao.get(uuid).getName();
			//存入缓存
			supplierNameMap.put(uuid, supplierName);
		}
		
		return supplierName;
		
	}

		
}