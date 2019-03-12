package com.cl.erp.action;
import com.cl.erp.biz.IOrderdetailBiz;
import com.cl.erp.entity.Emp;
import com.cl.erp.entity.Orderdetail;
import com.cl.erp.exception.ErpException;

/**
 * 订单明细Action 
 * @author Administrator
 *
 */
public class OrderdetailAction extends BaseAction<Orderdetail> {

	private IOrderdetailBiz orderdetailBiz;
	
	public void setOrderdetailBiz(IOrderdetailBiz orderdetailBiz) {
		this.orderdetailBiz = orderdetailBiz;
		setBaseBiz(orderdetailBiz);
	}
	private Long storeuuid;//入库

	public Long getStoreuuid() {
		return storeuuid;
	}

	public void setStoreuuid(Long storeuuid) {
		this.storeuuid = storeuuid;
	}

	public IOrderdetailBiz getOrderdetailBiz() {
		return orderdetailBiz;
	}
	
	public void doOutStore(){
		Emp loginUser = getLoginUser();
		if(null==loginUser || loginUser.getUuid()==null){
			//用户没登陆/session失效
			ajaxReturn(false, "亲，没有登录");
			return ;
		}
		
		try {
			//明细入库
			orderdetailBiz.doOutStore(getId(), storeuuid, loginUser.getUuid());
			ajaxReturn(true, "出库成功");
		} catch (ErpException e) {
			ajaxReturn(false, e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			ajaxReturn(false, "出库失败");
			e.printStackTrace();
		}
	}
	
	public void doInStore(){
		Emp loginUser = getLoginUser();
		if(null==loginUser || loginUser.getUuid()==null){
			//用户没登陆/session失效
			ajaxReturn(false, "亲，没有登录");
			return ;
		}
		
		try {
			//明细入库
			orderdetailBiz.doInStore(getId(), storeuuid, loginUser.getUuid());
			ajaxReturn(true, "入库成功");
		} catch (ErpException e) {
			ajaxReturn(false, e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			ajaxReturn(false, "入库失败");
			e.printStackTrace();
		}
	}
	
}
