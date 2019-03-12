package com.cl.erp.biz.impl;
import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.cl.erp.biz.IOrderdetailBiz;
import com.cl.erp.dao.IOrderdetailDao;
import com.cl.erp.dao.IStoredetailDao;
import com.cl.erp.dao.IStoreoperDao;
import com.cl.erp.dao.ISupplierDao;
import com.cl.erp.entity.Orderdetail;
import com.cl.erp.entity.Orders;
import com.cl.erp.entity.Storedetail;
import com.cl.erp.entity.Storeoper;
import com.cl.erp.entity.Supplier;
import com.cl.erp.exception.ErpException;
import com.redsum.bos.ws.impl.IWaybillService;
/**
 * 订单明细业务逻辑类
 * @author Administrator
 *
 */
public class OrderdetailBiz extends BaseBiz<Orderdetail> implements IOrderdetailBiz {
	private IOrderdetailDao orderdetailDao;
	private IStoredetailDao storedetailDao;
	private IStoreoperDao storeoperDao;
	private IWaybillService waybillService;
	private ISupplierDao supplierDao;
	
	public ISupplierDao getSupplierDao() {
		return supplierDao;
	}

	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	public IWaybillService getWaybillService() {
		return waybillService;
	}

	public void setWaybillService(IWaybillService waybillService) {
		this.waybillService = waybillService;
	}

	public IStoredetailDao getStoredetailDao() {
		return storedetailDao;
	}

	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
	}

	public IStoreoperDao getStoreoperDao() {
		return storeoperDao;
	}

	public void setStoreoperDao(IStoreoperDao storeoperDao) {
		this.storeoperDao = storeoperDao;
	}

	public IOrderdetailDao getOrderdetailDao() {
		return orderdetailDao;
	}

	
	
	public void setOrderdetailDao(IOrderdetailDao orderdetailDao) {
		this.orderdetailDao = orderdetailDao;
		setBaseDao(orderdetailDao);
	}

	@Override
	@RequiresPermissions("采购订单入库")
	public void doInStore(Long uuid, Long storeuuid, Long empuuid) {
		//获取明细信息
		Orderdetail detail = orderdetailDao.get(uuid);
		//状态判断
		if(!detail.getState().equals(Orderdetail.STATE_NOT_IN)){
			throw new ErpException("不能重复入库");
		}
		//修改状态为已入库
		detail.setState(Orderdetail.STATE_IN);
		//设置入库时间
		detail.setEndtime(new Date());
		//设置库管员
		detail.setEnder(empuuid);
		//设置入库仓库
		detail.setStoreuuid(storeuuid);
		
		//更新库存信息
		//查询条件：仓库编号和商品编号
		Storedetail storedetail = new Storedetail();
		storedetail.setGoodsuuid(detail.getGoodsuuid());
		storedetail.setStoreuuid(detail.getStoreuuid());
		//是否存在库存信息
		List<Storedetail> storeList = storedetailDao.getList(storedetail, null, null);
		if(storeList.size()>0){
			//有该商品库存里,会更新数据库商品数目
			long num = 0;
			if(null!=storeList.get(0).getNum()){
				num = storeList.get(0).getNum();
			}
			storeList.get(0).setNum(num+detail.getNum());
		}else{
			//插入库存记录
			storedetail.setNum(detail.getNum());
			storedetailDao.add(storedetail);
		}
		
		//增加操作记录
		Storeoper log = new Storeoper();
		log.setEmpuuid(empuuid);
		log.setGoodsuuid(detail.getGoodsuuid());
		log.setNum(detail.getNum());
		log.setOpertime(detail.getEndtime());
		log.setType(Storeoper.TYPE_IN); 
		log.setStoreuuid(storeuuid);
		//保存到数据库
		storeoperDao.add(log);
		
		
		//判断订单下所有明细是否都入库
		//查询订单下是否还存在状态为0的明细
		//count(1) where state = 0 and ordersuuid  =
		Orderdetail od = new Orderdetail();
		Orders orders = detail.getOrders();
		//构建查询条件
		od.setState(Orderdetail.STATE_NOT_IN);
		od.setOrders(orders);
		long count = orderdetailDao.getCount(od, null, null);
		if(count==0){
			//所有明细都已经入库
			orders.setState(Orders.STATE_END);
			orders.setEndtime(detail.getEndtime());
			orders.setEnder(empuuid);
		}
	}
	/**
	 * 订单出库
	 */
	@Override
	@RequiresPermissions("销售订单出库")
	public void doOutStore(Long uuid, Long storeuuid, Long empuuid) {
		//获取明细信息
		Orderdetail detail = orderdetailDao.get(uuid);
		//状态判断
		if(!detail.getState().equals(Orderdetail.STATE_NOT_OUT)){
			throw new ErpException("已经出库，不能重复出库");
		}
		//修改状态为已出库
		detail.setState(Orderdetail.STATE_OUT);
		//设置入库时间
		detail.setEndtime(new Date());
		//设置库管员
		detail.setEnder(empuuid);
		//设置入库仓库
		detail.setStoreuuid(storeuuid);
		
		//更新库存信息
		//查询条件：仓库编号和商品编号
		Storedetail storedetail = new Storedetail();
		storedetail.setGoodsuuid(detail.getGoodsuuid());
		storedetail.setStoreuuid(detail.getStoreuuid());
		//是否存在库存信息
		List<Storedetail> storeList = storedetailDao.getList(storedetail, null, null);
		if(storeList.size()>0){
			//有该商品库存里,会更新数据库商品数目
			Storedetail ad = storeList.get(0);
			ad.setNum(ad.getNum()-detail.getNum());
			if(ad.getNum()<0){
				throw new ErpException("库存不足");
			}
		}else{
			throw new ErpException("库存不足");
		}
		
		//增加操作记录
		Storeoper log = new Storeoper();
		log.setEmpuuid(empuuid);
		log.setGoodsuuid(detail.getGoodsuuid());
		log.setNum(detail.getNum());
		log.setOpertime(detail.getEndtime());
		log.setType(Storeoper.TYPE_OUT); 
		log.setStoreuuid(storeuuid);
		//保存到数据库
		storeoperDao.add(log);
		
		
		//判断订单下所有明细是否都出库
		//查询订单下是否还存在状态为0的明细
		//count(1) where state = 0 and ordersuuid  =
		Orderdetail od = new Orderdetail();
		Orders orders = detail.getOrders();
		//构建查询条件
		od.setState(Orderdetail.STATE_NOT_OUT);
		od.setOrders(orders);
		long count = orderdetailDao.getCount(od, null, null);
		if(count==0){
			//所有明细都已经出库
			orders.setState(Orders.STATE_OUT);
			orders.setEndtime(detail.getEndtime());
			orders.setEnder(empuuid);
			//订单出库，产生运单号
			//客户
			Supplier supplier = supplierDao.get(orders.getSupplieruuid());
			Long waybillSn=waybillService.addWaybill(1l, supplier.getAddress(), supplier.getContact(), supplier.getTele(), "--");
			//更新运单号
			orders.setWaybillsn(waybillSn);
			
		}
	}

	
}
