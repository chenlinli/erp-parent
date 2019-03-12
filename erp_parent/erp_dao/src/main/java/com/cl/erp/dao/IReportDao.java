package com.cl.erp.dao;

import java.util.Date;
import java.util.List;

/**
 * 报表数据访问结构
 * @author CL
 *
 */
public interface IReportDao {

	/*
	 * 销售统计报表
	 */
	List ordersReport(Date startDate,Date endDate);
	/**
	 * 销售趋势图
	 * @param year
	 * @return
	 */
	List tendReport(int year);
}
