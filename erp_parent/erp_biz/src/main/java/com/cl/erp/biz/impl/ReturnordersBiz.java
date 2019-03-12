package com.cl.erp.biz.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cl.erp.biz.IReturnordersBiz;
import com.cl.erp.dao.IEmpDao;
import com.cl.erp.dao.IReturnorderdetailDao;
import com.cl.erp.dao.IReturnordersDao;
import com.cl.erp.dao.ISupplierDao;
import com.cl.erp.entity.Returnorderdetail;
import com.cl.erp.entity.Returnorders;
import com.cl.erp.exception.ErpException;
/**
 * 退货订单业务逻辑类
 * @author Administrator
 *
 */
public class ReturnordersBiz extends BaseBiz<Returnorders> implements IReturnordersBiz {

	private IReturnordersDao returnordersDao;
	private IEmpDao empDao;
	private ISupplierDao supplierDao;
	private IReturnorderdetailDao returnorderdetailDao;
	
	public IReturnorderdetailDao getReturnorderdetailDao() {
		return returnorderdetailDao;
	}

	public void setReturnorderdetailDao(IReturnorderdetailDao returnorderdetailDao) {
		this.returnorderdetailDao = returnorderdetailDao;
	}

		public IEmpDao getEmpDao() {
		return empDao;
	}

	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
	}

	public ISupplierDao getSupplierDao() {
		return supplierDao;
	}

	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	public IReturnordersDao getReturnordersDao() {
		return returnordersDao;
	}

	public void setReturnordersDao(IReturnordersDao returnordersDao) {
		this.returnordersDao = returnordersDao;
		setBaseDao(returnordersDao);
	}
	
	public List<Returnorders> getListByPage(Returnorders t1,Returnorders t2,Object param,int firstResult, int maxResults) {
		//获取分页后的订单列表
		List<Returnorders> list = super.getListByPage(t1, t2, param, firstResult, maxResults);
		//缓存员工编号和名称
		Map<Long,String> empNameMap = new HashMap<Long,String>();
		Map<Long,String> supplierNameMap= new HashMap<Long,String>();
		for (Returnorders returnorders : list) {
			//下单员
			
			returnorders.setCreatername(getEmpName(returnorders.getCreater(), empNameMap, empDao));
			
			//审核员
			returnorders.setCheckername(getEmpName(returnorders.getChecker(), empNameMap, empDao));
			//库管员
			returnorders.setEndername(getEmpName(returnorders.getEnder(),empNameMap,empDao));
			//供应商
			returnorders.setSuppliername(getSupplierName(returnorders.getSupplieruuid(), supplierNameMap, supplierDao));
		}
		return list;
	}
	
	public void add(Returnorders returnorders){
		//设置采购退货状态为未审核
		returnorders.setState(Returnorders.STATE_NOT_CONFIRMED);
		//设置采购退货单创建时间
		returnorders.setCreatetime(new Date());
		Double total = 0.0;
		for(Returnorderdetail detail : returnorders.getReturnorderDetails()){
			total+=detail.getMoney();
			detail.setState(Returnorderdetail.STATE_NOT_CONFIRMED);
			detail.setReturnorders(returnorders);
		}
		returnorders.setTotalmoney(total);
		returnordersDao.add(returnorders);
	}

	/**
	 * 审核采购退货单,
	 */
	@Override
	public void doCheck(long id, Long uuid) {
		Returnorders returnorders = returnordersDao.get(id);
		if(!returnorders.getState().equals(Returnorders.STATE_NOT_CONFIRMED)){
			throw new ErpException("亲，不能重复审核");
		}
		//设置订单退货状态
		returnorders.setState(Returnorders.STATE__CONFIRMED);
		//设置审核时间
		returnorders.setChecktime(new Date());
		//设置审核人员
		returnorders.setChecker(uuid);
	}

	
}
