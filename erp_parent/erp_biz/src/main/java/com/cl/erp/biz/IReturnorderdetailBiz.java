package com.cl.erp.biz;
import com.cl.erp.entity.Returnorderdetail;
/**
 * 退货订单明细业务逻辑层接口
 * @author Administrator
 *
 */
public interface IReturnorderdetailBiz extends IBaseBiz<Returnorderdetail>{

	void doOutStore(long id,Long storeuuid, Long empuuid);

	void doInStore(long id, Long storeuuid, Long uuid);
	
	
}

