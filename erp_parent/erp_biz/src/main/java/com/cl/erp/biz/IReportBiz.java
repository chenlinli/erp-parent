package com.cl.erp.biz;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 报表业务接口
 * @author CL
 *
 */
public interface IReportBiz {
	/*
	 * 销售统计报表
	 */
	List ordersReport(Date startDate,Date endDate);
	
	public List<Map<String,Object>> tendReport(int year);
}
