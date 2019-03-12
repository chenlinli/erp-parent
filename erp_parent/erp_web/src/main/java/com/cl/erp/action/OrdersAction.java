package com.cl.erp.action;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.cl.erp.biz.IOrdersBiz;
import com.cl.erp.entity.Emp;
import com.cl.erp.entity.Orderdetail;
import com.cl.erp.entity.Orders;
import com.cl.erp.exception.ErpException;
import com.redsum.bos.ws.Waybilldetail;

/**
 * 订单Action 
 * @author Administrator
 *
 */
public class OrdersAction extends BaseAction<Orders> {

	private IOrdersBiz ordersBiz;
	
	public void setOrdersBiz(IOrdersBiz ordersBiz) {
		this.ordersBiz = ordersBiz;
		setBaseBiz(ordersBiz);
	}
	//接收订单明细的json格式的字符串，数组形式的json字符串，里面的元素，每一个订单明细
	private String json;

	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public IOrdersBiz getOrdersBiz() {
		return ordersBiz;
	}
	/**
	 * 添加订单
	 */
	public void add(){
		Emp emp = getLoginUser();
		if(null==emp || emp.getUuid()==null){
			//用户没登陆/session失效
			ajaxReturn(false, "亲，没有登录");
			return ;
		}
		try {
			Orders order = getT();
			//System.out.println(order);
			//设置订单创建者
			order.setCreater(emp.getUuid());
			
			//System.out.println("json:"+json);
			List<Orderdetail> list = JSON.parseArray(json,Orderdetail.class);
			//System.out.println(list.size());
			//设置订单明细
			order.setOrderDetails(list);
			
			ordersBiz.add(order);
			ajaxReturn(true, "添加订单成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ajaxReturn(false, "添加订单失败");
		}
	}
	/**
	 * 订单审核
	 */
	public void doCheck(){
		//获取当前登录用户
		Emp loginUser = getLoginUser();
		if(null==loginUser || loginUser.getUuid()==null){
			//用户没登陆/session失效
			ajaxReturn(false, "亲，没有登录");
			return ;
		}
		try {
			ordersBiz.doCheck(getId(), loginUser.getUuid());
			ajaxReturn(true, "审核成功");
		} catch (UnauthorizedException e) {
			ajaxReturn(false, "权限不足");
			e.printStackTrace();
		}catch (ErpException e) {
			ajaxReturn(false, e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			ajaxReturn(false, "审核失败");
			e.printStackTrace();
		}
	}
	
	public void doStart(){
		//获取当前登录用户
		Emp loginUser = getLoginUser();
		if(null==loginUser || loginUser.getUuid()==null){
			//用户没登陆/session失效
			ajaxReturn(false, "亲，没有登录");
			return ;
		}
		try {
			ordersBiz.doStart(getId(), loginUser.getUuid());
			ajaxReturn(true, "确认成功");
		} catch (ErpException e) {
			ajaxReturn(false, e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			ajaxReturn(false, "确认失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 我的订单
	 */
	public void myListByPage(){
		if(null == getT1()){
			//构建查询条件
			setT1(new Orders());
			
		}
		Emp loginUser = getLoginUser();
		
		//登录用户的uuid查询
		getT1().setCreater(loginUser.getUuid());
		super.listByPage();
	}
	/**导出订单
	 * 
	 */
	public void export(){
		String filename="Orders_"+getId()+".xls";
		HttpServletResponse response = ServletActionContext.getResponse();
		
		try {
			//实现下载文件
			response.setHeader("Content-Disposition", "attachment;filename="+new String(filename.getBytes(),"ISO-8859-1"));
			ordersBiz.export(response.getOutputStream(), getId());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Long waybillsn;

	public Long getWaybillsn() {
		return waybillsn;
	}
	public void setWaybillsn(Long waybillsn) {
		this.waybillsn = waybillsn;
	}
	/*
	 * 根据运单号返回运单详情
	 */
	public void waybilldetailList(){
		List<Waybilldetail> waybilldetailList = ordersBiz.waybilldetailList(waybillsn);
		write(JSON.toJSONString(waybilldetailList));
	}
}
