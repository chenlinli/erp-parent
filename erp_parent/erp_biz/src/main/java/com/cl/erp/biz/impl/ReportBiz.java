package com.cl.erp.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cl.erp.biz.IReportBiz;
import com.cl.erp.dao.IReportDao;

public class ReportBiz implements IReportBiz {

	private IReportDao reportDao;
	public IReportDao getReportDao() {
		return reportDao;
	}
	public void setReportDao(IReportDao reportDao) {
		this.reportDao = reportDao;
	}
	/*
	 * 销售统计报表
	 */
	@Override
	public List ordersReport(Date startDate,Date endDate) {
		return reportDao.ordersReport(startDate,endDate);
	}
	@Override
	public List<Map<String,Object>> tendReport(int year) {
		//对月份进行查缺补漏
		//map:{"name":2,"y":466.32}
		List<Map<String,Object>> yearData =  reportDao.tendReport(year);
		//最终返回的数据
		List<Map<String,Object>> rtnMap = new ArrayList<Map<String,Object>>();
		//key:2；值:{"name":2,"y":466.32}
		Map<String,Map<String,Object>> yearDataMap=new HashMap<String,Map<String,Object>>();
		//把数据库中存在的月份的数据，存入
		for(Map<String,Object> month: yearData){
			yearDataMap.put(month.get("name")+"", month);
		}
		Map<String,Object> monthData=null;
		for(int i=1;i<=12;i++){
			monthData = yearDataMap.get(i+"");
			if(monthData==null){
				monthData=new HashMap<String,Object>();
				monthData.put("name", i+"月");
				monthData.put("y", 0);
			}else{
				monthData.put("name", i+"月");
			}
			rtnMap.add(monthData);
		}
		return rtnMap;
	}

	
}
