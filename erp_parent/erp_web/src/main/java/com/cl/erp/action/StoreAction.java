package com.cl.erp.action;
import com.cl.erp.biz.IStoreBiz;
import com.cl.erp.entity.Emp;
import com.cl.erp.entity.Store;

/**
 * 仓库Action 
 * @author Administrator
 *
 */
public class StoreAction extends BaseAction<Store> {

	private IStoreBiz storeBiz;
	
	public void setStoreBiz(IStoreBiz storeBiz) {
		this.storeBiz = storeBiz;
		setBaseBiz(storeBiz);
	}
	
	public void myList(){
		if(null == getT1()){
			setT1(new Store());
		}
		Emp emp = getLoginUser();
		getT1().setEmpuuid(emp.getUuid());
		super.list();
		
	}
	
	
}
