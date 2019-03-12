package com.cl.erp.action;
import java.util.Date;

import com.cl.erp.biz.IInventoryBiz;
import com.cl.erp.entity.Emp;
import com.cl.erp.entity.Inventory;
import com.cl.erp.exception.ErpException;

import oracle.net.aso.a;

/**
 * 盘盈盘亏Action 
 * @author Administrator
 *
 */
public class InventoryAction extends BaseAction<Inventory> {

	private IInventoryBiz inventoryBiz;
	
	public void setInventoryBiz(IInventoryBiz inventoryBiz) {
		this.inventoryBiz = inventoryBiz;
		setBaseBiz(inventoryBiz);
	}
	


	public IInventoryBiz getInventoryBiz() {
		return inventoryBiz;
	}

	public void add(){
		Emp emp = getLoginUser();
		if(null==emp){
			ajaxReturn(false, "亲，还没有登录");
			return ;
		}
		getT().setCreater(emp.getUuid());
		super.add();

	}
	
	public void doCheck(){
		Emp emp = getLoginUser();
		if(null==emp){
			ajaxReturn(false, "亲，还没有登录");
			return ;
		}
		try {
			inventoryBiz.doCheck(getId(),emp.getUuid());
			ajaxReturn(true, "审核成功");
		} catch (ErpException e) {
			ajaxReturn(false, e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "审核失败");
		}
	}
}
