package com.cl.erp.biz;
import com.cl.erp.entity.Inventory;
/**
 * 盘盈盘亏业务逻辑层接口
 * @author Administrator
 *
 */
public interface IInventoryBiz extends IBaseBiz<Inventory>{

	void doCheck(long id, Long uuid);
	
	
}

