package com.cl.erp.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.cl.erp.biz.IReportBiz;

public class ReportAction {
	private IReportBiz reportBiz;
	//月/日/年：
	private Date startDate;
	private Date endDate;

	private int year;
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public IReportBiz getReportBiz() {
		return reportBiz;
	}

	public void setReportBiz(IReportBiz reportBiz) {
		this.reportBiz = reportBiz;
	}

	/**
	 * 销售统计报表
	 * @return
	 */
	public void ordersReport() {
		//System.out.println(startDate+"----"+endDate);
		List list = reportBiz.ordersReport(startDate,endDate);
		write(JSON.toJSONString(list));
	}
	
	/**
	 * 输出字符串到前端
	 * @param jsonString
	 */
	public void write(String jsonString){
		try {
			//响应对象
			HttpServletResponse response = ServletActionContext.getResponse();
			//设置编码
			response.setContentType("text/html;charset=utf-8"); 
			//输出给页面
			response.getWriter().write(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tendReport(){
		//System.out.println(year);
		List list = reportBiz.tendReport(year);
		write(JSON.toJSONString(list));
	}
}
