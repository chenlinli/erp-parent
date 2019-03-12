package com.cl.erp.action;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.cl.erp.biz.IReturnordersBiz;
import com.cl.erp.entity.Emp;
import com.cl.erp.entity.Orders;
import com.cl.erp.entity.Returnorderdetail;
import com.cl.erp.entity.Returnorders;
import com.cl.erp.exception.ErpException;

/**
 * 退货订单Action 
 * @author Administrator
 *
 */
public class ReturnordersAction extends BaseAction<Returnorders> {

	private IReturnordersBiz returnordersBiz;
	
	public void setReturnordersBiz(IReturnordersBiz returnordersBiz) {
		this.returnordersBiz = returnordersBiz;
		setBaseBiz(returnordersBiz);
		
	}
	
	private String json;

	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	/**
	 * 添加退货但
	 */
	public void add(){
		Emp emp = getLoginUser();
		if(null==emp){
			ajaxReturn(false, "亲，没有登录哦");
			return ;
		}
		try {
			Returnorders returnorders = getT();
			//设置采购退货单的创建者
			returnorders.setCreater(emp.getUuid());
			//获取采购退货明细
			List<Returnorderdetail> details = JSON.parseArray(json, Returnorderdetail.class);
			//设置关联
			returnorders.setReturnorderDetails(details);
			//调用服务层
			returnordersBiz.add(returnorders);
			ajaxReturn(true, "添加采购退货申请成功");
		} catch (Exception e) {
			ajaxReturn(false, "添加采购退货失败");
			e.printStackTrace();
		}
	}
	
	public void doCheck(){
		//获取当前登录用户
		Emp loginUser = getLoginUser();
		if(null==loginUser || loginUser.getUuid()==null){
			//用户没登陆/session失效
			ajaxReturn(false, "亲，没有登录");
			return ;
		}
		try {
			returnordersBiz.doCheck(getId(),loginUser.getUuid());
			ajaxReturn(true, "审核成功");
		} catch (ErpException e) {
			ajaxReturn(false,e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			ajaxReturn(false, "审核失败");
		}
	}
	/**
	 * 我的退货查询
	 */
	public void myListByPage(){
		if(null == getT1()){
			//构建查询条件
			setT1(new Returnorders());
			
		}
		Emp loginUser = getLoginUser();
		
		//登录用户的uuid查询
		getT1().setCreater(loginUser.getUuid());
		super.listByPage();;
	}
	
}
