package com.cl.erp.action;
import com.cl.erp.biz.IReturnorderdetailBiz;
import com.cl.erp.entity.Emp;
import com.cl.erp.entity.Returnorderdetail;
import com.cl.erp.exception.ErpException;

/**
 * 退货订单明细Action 
 * @author Administrator
 *
 */
public class ReturnorderdetailAction extends BaseAction<Returnorderdetail> {

	private IReturnorderdetailBiz returnorderdetailBiz;
	private Long storeuuid;//入库

	public Long getStoreuuid() {
		return storeuuid;
	}

	public void setStoreuuid(Long storeuuid) {
		this.storeuuid = storeuuid;
	}

	public void setReturnorderdetailBiz(IReturnorderdetailBiz returnorderdetailBiz) {
		this.returnorderdetailBiz = returnorderdetailBiz;
		setBaseBiz(returnorderdetailBiz);
	}
	
	public void doOutStore(){
		Emp loginUser = getLoginUser();
		if(null==loginUser || loginUser.getUuid()==null){
			//用户没登陆/session失效
			ajaxReturn(false, "亲，没有登录");
			return ;
		}
		try{
			returnorderdetailBiz.doOutStore(getId(),storeuuid,loginUser.getUuid());
			ajaxReturn(true, "采购退货出库成功");
		}catch(ErpException e){
			e.printStackTrace();
			ajaxReturn(false, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "采购退货出库失败");
		}
	}
	
	public void doInStore(){
		Emp loginUser = getLoginUser();
		if(null==loginUser || loginUser.getUuid()==null){
			//用户没登陆/session失效
			ajaxReturn(false, "亲，没有登录");
			return ;
		}
		try{
			returnorderdetailBiz.doInStore(getId(),storeuuid,loginUser.getUuid());
			ajaxReturn(true, "销售退货入库成功");
		}catch(ErpException e){
			e.printStackTrace();
			ajaxReturn(false, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "销售退货入库失败");
		}
	}
	
}
