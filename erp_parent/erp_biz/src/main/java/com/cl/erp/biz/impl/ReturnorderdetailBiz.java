package com.cl.erp.biz.impl;
import java.util.Date;
import java.util.List;

import com.cl.erp.biz.IReturnorderdetailBiz;
import com.cl.erp.dao.IReturnorderdetailDao;
import com.cl.erp.dao.IReturnordersDao;
import com.cl.erp.dao.IStoredetailDao;
import com.cl.erp.dao.IStoreoperDao;
import com.cl.erp.entity.Returnorderdetail;
import com.cl.erp.entity.Returnorders;
import com.cl.erp.entity.Storedetail;
import com.cl.erp.entity.Storeoper;
import com.cl.erp.exception.ErpException;
/**
 * 退货订单明细业务逻辑类
 * @author Administrator
 *
 */
public class ReturnorderdetailBiz extends BaseBiz<Returnorderdetail> implements IReturnorderdetailBiz {

	private IReturnorderdetailDao returnorderdetailDao;
	private IStoredetailDao storedetailDao;
	private IReturnordersDao returnordersDao;
	private IStoreoperDao storeoperDao;
	
	public IStoredetailDao getStoredetailDao() {
		return storedetailDao;
	}

	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
	}

	public IReturnordersDao getReturnordersDao() {
		return returnordersDao;
	}

	public void setReturnordersDao(IReturnordersDao returnordersDao) {
		this.returnordersDao = returnordersDao;
	}

	public IStoreoperDao getStoreoperDao() {
		return storeoperDao;
	}

	public void setStoreoperDao(IStoreoperDao storeoperDao) {
		this.storeoperDao = storeoperDao;
	}

	public IReturnorderdetailDao getReturnorderdetailDao() {
		return returnorderdetailDao;
	}

	public void setReturnorderdetailDao(IReturnorderdetailDao returnorderdetailDao) {
		this.returnorderdetailDao = returnorderdetailDao;
		setBaseDao(returnorderdetailDao);
	}

	@Override
	public void doOutStore(long id, Long storeuuid,Long empuuid) {
		Returnorderdetail detail = returnorderdetailDao.get(id);
		if(!detail.getState().equals(Returnorderdetail.STATE_NOT_OUT)){
			throw new ErpException("采购明细已经出库");
		}
		//设置明细已经出库
		detail.setState(Returnorderdetail.STATE_OUT);
		
		//设置出库的时间
		detail.setEndtime(new Date());
		//设置出库人
		detail.setEnder(empuuid);
		//设置仓库
		detail.setStoreuuid(storeuuid);
		
		
		//更新库存信息
		Storedetail storedetail = new Storedetail();
		storedetail.setGoodsuuid(detail.getGoodsuuid());
		storedetail.setStoreuuid(detail.getStoreuuid());
		List<Storedetail> storedetailList = storedetailDao.getList(storedetail, null, null);
		if(storedetailList.size()>0){
			Storedetail detail2 = storedetailList.get(0);
			detail2.setNum(detail2.getNum()-detail.getNum());
			if(detail2.getNum()<0){
				throw new ErpException("库存不足");
			}
		}else{
			throw new ErpException("库存不足");
		}
		
		Storeoper storeoper = new Storeoper();
		storeoper.setEmpuuid(empuuid);
		storeoper.setStoreuuid(detail.getStoreuuid());
		storedetail.setNum(detail.getNum());
		storeoper.setOpertime(new Date());
		storeoper.setType(Storeoper.TYPE_OUT);
		storeoper.setGoodsuuid(detail.getGoodsuuid());
		storeoperDao.add(storeoper);
		
		Returnorderdetail rd= new Returnorderdetail();
		Returnorders ro = detail.getReturnorders();
		rd.setReturnorders(ro);
		rd.setState(Returnorderdetail.STATE_NOT_OUT);
		long count = returnorderdetailDao.getCount(rd, null, null);
		if(count == 0){
			ro.setState(Returnorders.STATE_END);
			ro.setEnder(empuuid);
			ro.setEndtime(detail.getEndtime());
		}
	}

	@Override
	public void doInStore(long id, Long storeuuid, Long empuuid) {
		Returnorderdetail detail = returnorderdetailDao.get(id);
		if(!detail.getState().equals(Returnorderdetail.STATE_NOT_IN)){
			throw new ErpException("销售明细已经入库");
		}
		//设置明细已经入库
		detail.setState(Returnorderdetail.STATE_IN);
		
		//设置出库的时间
		detail.setEndtime(new Date());
		//设置出库人
		detail.setEnder(empuuid);
		//设置仓库
		detail.setStoreuuid(storeuuid);
		
		
		//更新库存信息
		Storedetail storedetail = new Storedetail();
		storedetail.setGoodsuuid(detail.getGoodsuuid());
		storedetail.setStoreuuid(detail.getStoreuuid());
		List<Storedetail> storedetailList = storedetailDao.getList(storedetail, null, null);
		if(storedetailList.size()>0){
			//有该商品库存里,会更新数据库商品数目
			long num = 0;
			if(null!=storedetailList.get(0).getNum()){
				num = storedetailList.get(0).getNum();
			}
			storedetailList.get(0).setNum(num+detail.getNum());
		}else{
			//插入库存记录
			storedetail.setNum(detail.getNum());
			storedetailDao.add(storedetail);
		}
	
		
		Storeoper storeoper = new Storeoper();
		storeoper.setEmpuuid(empuuid);
		storeoper.setStoreuuid(detail.getStoreuuid());
		storedetail.setNum(detail.getNum());
		storeoper.setOpertime(new Date());
		storeoper.setType(Storeoper.TYPE_IN);
		storeoper.setGoodsuuid(detail.getGoodsuuid());
		storeoperDao.add(storeoper);
		
		Returnorderdetail rd= new Returnorderdetail();
		Returnorders ro = detail.getReturnorders();
		rd.setReturnorders(ro);
		rd.setState(Returnorderdetail.STATE_NOT_IN);
		long count = returnorderdetailDao.getCount(rd, null, null);
		if(count == 0){
			ro.setState(Returnorders.STATE_END);
			ro.setEnder(empuuid);
			ro.setEndtime(detail.getEndtime());
		}		
	}

	
}
