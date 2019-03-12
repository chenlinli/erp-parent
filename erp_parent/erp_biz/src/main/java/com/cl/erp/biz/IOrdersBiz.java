package com.cl.erp.biz;
import java.io.OutputStream;
import java.util.List;

import com.cl.erp.entity.Orders;
import com.redsum.bos.ws.Waybilldetail;
/**
 * 订单业务逻辑层接口
 * @author Administrator
 *
 */
public interface IOrdersBiz extends IBaseBiz<Orders>{
	void doCheck(Long uuid,Long empuuid);
	public void doStart(Long uuid, Long empuuid);
	/**
	 * 导出订单
	 */
	void export(OutputStream os ,Long uuid);
	/**
	 * 根据运单号查询运单详情
	 * @param sn
	 * @return
	 */
	List<Waybilldetail> waybilldetailList(Long sn);
}

