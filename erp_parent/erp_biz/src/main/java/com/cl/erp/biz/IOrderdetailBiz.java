package com.cl.erp.biz;
import com.cl.erp.entity.Orderdetail;
/**
 * 订单明细业务逻辑层接口
 * @author Administrator
 *
 */
public interface IOrderdetailBiz extends IBaseBiz<Orderdetail>{
	
	void doInStore(Long uuid,Long storeuuid,Long empuuid);
	void doOutStore(Long uuid,Long storeuuid,Long empuuid);
	
}

