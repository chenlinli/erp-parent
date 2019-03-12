package com.cl.erp.biz;
import com.cl.erp.entity.Returnorders;
/**
 * 退货订单业务逻辑层接口
 * @author Administrator
 *
 */
public interface IReturnordersBiz extends IBaseBiz<Returnorders>{

	void doCheck(long id, Long uuid);
	
	
}

