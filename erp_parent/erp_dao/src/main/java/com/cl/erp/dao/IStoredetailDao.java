package com.cl.erp.dao;

import java.util.List;

import com.cl.erp.entity.Storealert;
import com.cl.erp.entity.Storedetail;
/**
 * 仓库库存数据访问接口
 * @author Administrator
 *
 */
public interface IStoredetailDao extends IBaseDao<Storedetail>{
	
	public List<Storealert> getStorealertList();
}
