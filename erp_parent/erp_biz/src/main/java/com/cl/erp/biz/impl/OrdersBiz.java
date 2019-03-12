package com.cl.erp.biz.impl;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;

import com.cl.erp.biz.IOrdersBiz;
import com.cl.erp.dao.IEmpDao;
import com.cl.erp.dao.IOrdersDao;
import com.cl.erp.dao.ISupplierDao;
import com.cl.erp.entity.Orderdetail;
import com.cl.erp.entity.Orders;
import com.cl.erp.exception.ErpException;
import com.redsum.bos.ws.Waybilldetail;
import com.redsum.bos.ws.impl.IWaybillService;
/**
 * 订单业务逻辑类
 * @author Administrator
 *
 */
public class OrdersBiz extends BaseBiz<Orders> implements IOrdersBiz {

	private IOrdersDao ordersDao;
	private IEmpDao empDao;	
	private IWaybillService waybillService;
	public IWaybillService getWaybillService() {
		return waybillService;
	}

	public void setWaybillService(IWaybillService waybillService) {
		this.waybillService = waybillService;
	}

	public IEmpDao getEmpDao() {
		return empDao;
	}

	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
	}

	public ISupplierDao getSupplierDao() {
		return supplierDao;
	}

	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	public IOrdersDao getOrdersDao() {
		return ordersDao;
	}

	private ISupplierDao supplierDao;
	
	public void setOrdersDao(IOrdersDao ordersDao) {
		this.ordersDao = ordersDao;
		setBaseDao(ordersDao);
	}

	public void add(Orders orders){
		Subject subject = SecurityUtils.getSubject();
		//代码级别的权限控制
		if(Orders.TYPE_IN.equals(orders.getType())){
			//当前登录的用户是否有采购的权限
			if(!subject.isPermitted("采购订单申请")){
				throw new ErpException("权限不足");
			}
		}else if(Orders.TYPE_OUT.equals(orders.getType())){
			if(!subject.isPermitted("销售订单录入")){
				throw new ErpException("权限不足");
			}
		}else{
			throw new ErpException("非法参数");
		}
		//设置订单状态\类型\时间
		orders.setState(Orders.STATE_CREATE);
		//orders.setType(Orders.TYPE_IN);
		orders.setCreatetime(new Date());
		Double total  = 0.0;//累计金额
		for (Orderdetail detail : orders.getOrderDetails()) {
			total+=detail.getMoney();
			detail.setState(Orderdetail.STATE_NOT_IN);
			//设置跟订单的关系
			detail.setOrders(orders);
		}
		//设置总金额
		orders.setTotalmoney(total);
		ordersDao.add(orders);
	}
	
	public List<Orders> getListByPage(Orders t1,Orders t2,Object param,int firstResult, int maxResults) {
		//获取分页后的订单列表
		List<Orders> list = super.getListByPage(t1, t2, param,firstResult,maxResults);
		//缓存员工编号和名称
		Map<Long,String> empNameMap = new HashMap<Long,String>();
		Map<Long,String> supplierNameMap= new HashMap<Long,String>();
		for(Orders order:list){
			order.setCreaterName(getEmpName(order.getCreater(), empNameMap,empDao));
			order.setCheckerName(getEmpName(order.getChecker(), empNameMap,empDao));
			order.setStarterName(getEmpName(order.getStarter(), empNameMap,empDao));
			order.setEnderName(getEmpName(order.getEnder(), empNameMap,empDao));
			order.setSupplierName(getSupplierName(order.getSupplieruuid(), supplierNameMap,supplierDao));
		}
		return list;
	}

	/**
	 * 审核订单
	 */
	@Override
	@RequiresPermissions("采购订单审核")
	public void doCheck(Long uuid, Long empuuid) {
		//修改订单状态
		Orders o = ordersDao.get(uuid);
		//设置审核时间
		if(!o.getState().equals(Orders.STATE_CREATE)){
			//已经审核过
			throw new ErpException("亲，该订单已经审核过");
		}
		o.setState(Orders.STATE_CHECK);
		//设置审核时间
		o.setChecktime(new Date());
		//设置审核员
		o.setChecker(empuuid);
		
	}
	/**
	 * empuuid确认订单uuiid
	 */
	@RequiresPermissions("采购订单确认")
	public void doStart(Long uuid, Long empuuid){
		Orders o = ordersDao.get(uuid);
		//设置审核时间
		if(!o.getState().equals(Orders.STATE_CHECK)){
			//已经审核过
			throw new ErpException("亲，该订单已经确认过");
		}
		o.setState(Orders.STATE_START);
		//设置确认时间
		o.setStarttime(new Date());
		//设置确认员
		o.setStarter(empuuid);
	}

	@Override
	public void export(OutputStream os, Long uuid) {
		Orders order = ordersDao.get(uuid);
		
		List<Orderdetail> orderDetails = order.getOrderDetails();
		//工作簿创建
		HSSFWorkbook book = new HSSFWorkbook();
		//创建工作表
		String sheetName="";
		if(Orders.TYPE_IN.equals(order.getType())){
			sheetName="采购订单";
		}
		if(Orders.TYPE_OUT.equals(order.getType())){
			sheetName="销售订单";
		}
		HSSFSheet sheet = book.createSheet(sheetName);
		//创建行：行索引0开始
		HSSFRow row = sheet.createRow(0);
		//创建单元格样式
		HSSFCellStyle style_content = book.createCellStyle();
		//上下左右边框
		style_content.setBorderBottom(BorderStyle.THIN);
		style_content.setBorderTop(BorderStyle.THIN);
		style_content.setBorderLeft(BorderStyle.THIN);
		style_content.setBorderRight(BorderStyle.THIN);
		//设置对齐方式和字体
		//水平居中
		style_content.setAlignment(HorizontalAlignment.CENTER);
		//垂直居中
		style_content.setVerticalAlignment(VerticalAlignment.CENTER);
		//设置日期格
		HSSFCellStyle style_date =  book.createCellStyle();
		//把style——content的样式复制到date_style
		//style_content.cloneStyleFrom(style_date);
		style_date.cloneStyleFrom(style_content);
		
		HSSFDataFormat dataFormat = book.createDataFormat();
		style_date.setDataFormat(dataFormat.getFormat("yyyy-MM-dd HH:mm:ss"));
		//设置内容字体
		HSSFFont contentFont = book.createFont();
		//
		contentFont.setFontName("宋体");
		contentFont.setFontHeightInPoints((short)11);
		style_content.setFont(contentFont);
		//标题样式
		HSSFCellStyle style_title = book.createCellStyle();
		style_title.setAlignment(HorizontalAlignment.CENTER);
		style_title.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont titleFont = book.createFont();
		//设置标题字体
		titleFont.setFontName("黑体");
		titleFont.setFontHeightInPoints((short)18);
		titleFont.setBold(true);
		style_title.setFont(titleFont);
		//合并单元格
		//采购单,供应商，订单明细
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
		sheet.addMergedRegion(new CellRangeAddress(2,2,1,3));
		sheet.addMergedRegion(new CellRangeAddress(7,7,0,3));
		//创建11行，4列
		int count = orderDetails.size()+9;
		for(int i=2;i<=count;i++){
			row=sheet.createRow(i);
			for(int j=0;j<4;j++){
				row.createCell(j).setCellStyle(style_content);
			}
			//设置内容行高
			row.setHeight((short)500);
		}
		//设置列宽
		for(int j=0;j<4;j++){
			sheet.setColumnWidth(j, 5000);
		}
		//设置第一行标题
		HSSFCell titleCell = sheet.createRow(0).createCell(0);
		titleCell.setCellValue(sheetName);
		titleCell.setCellStyle(style_title);
		
		//设置值
		sheet.getRow(2).getCell(0).setCellValue("供应商");
		sheet.getRow(3).getCell(0).setCellValue("下单日期");
		sheet.getRow(4).getCell(0).setCellValue("审核日期");
		sheet.getRow(5).getCell(0).setCellValue("采购日期");
		sheet.getRow(6).getCell(0).setCellValue("入库日期");
		
		sheet.getRow(3).getCell(2).setCellValue("经办人");
		sheet.getRow(4).getCell(2).setCellValue("经办人");
		sheet.getRow(5).getCell(2).setCellValue("经办人");
		sheet.getRow(6).getCell(2).setCellValue("经办人");	
		
		sheet.getRow(7).getCell(0).setCellValue("订单明细");
		sheet.getRow(8).getCell(0).setCellValue("商品名称");
		sheet.getRow(8).getCell(1).setCellValue("数量");
		sheet.getRow(8).getCell(2).setCellValue("价格");
		sheet.getRow(8).getCell(3).setCellValue("金额");
		//设置行高，列宽
		sheet.getRow(0).setHeight((short)1000);
		//设置订单详情
		//设置供应商
		Map<Long,String> supplierNameMap= new HashMap<Long,String>();
		sheet.getRow(2).getCell(1).setCellValue(getSupplierName(order.getSupplieruuid(), supplierNameMap, supplierDao));
		//设置日期，经办人
		sheet.getRow(3).getCell(1).setCellStyle(style_date);
		sheet.getRow(4).getCell(1).setCellStyle(style_date);
		sheet.getRow(5).getCell(1).setCellStyle(style_date);
		sheet.getRow(6).getCell(1).setCellStyle(style_date);
		
		if(null!=order.getCreatetime()){
			sheet.getRow(3).getCell(1).setCellValue(order.getCreatetime());
		}
		if(null!=order.getChecktime())
			sheet.getRow(4).getCell(1).setCellValue(order.getChecktime());
		if(null!=order.getStarttime())
			sheet.getRow(5).getCell(1).setCellValue(order.getStarttime());
		if(null!=order.getEndtime())
			sheet.getRow(6).getCell(1).setCellValue(order.getEndtime());
		//设置经办人
		Map<Long,String> empNameMap = new HashMap<Long,String>();
		
		sheet.getRow(3).getCell(3).setCellValue(getEmpName(order.getCreater(), empNameMap, empDao));
		sheet.getRow(4).getCell(3).setCellValue(getEmpName(order.getChecker(), empNameMap, empDao));
		sheet.getRow(5).getCell(3).setCellValue(getEmpName(order.getStarter(), empNameMap, empDao));
		sheet.getRow(6).getCell(3).setCellValue(getEmpName(order.getEnder(), empNameMap, empDao));
		
		
		//设置订单明细内容
		int index = 0;
		if(null !=orderDetails && orderDetails.size()>0){
			for(int i=9;i<count;i++){
				HSSFRow r = sheet.getRow(i);
				Orderdetail orderdetail = orderDetails.get(index);
				r.getCell(0).setCellValue(orderdetail.getGoodsname());
				r.getCell(1).setCellValue(orderdetail.getNum());
				r.getCell(2).setCellValue(orderdetail.getPrice());
				r.getCell(3).setCellValue(orderdetail.getMoney());
				index++;
			}
		}
		//设置合计
		HSSFRow sumRow = sheet.getRow(count);
		sumRow.getCell(0).setCellValue("合计");
		sumRow.getCell(3).setCellValue(order.getTotalmoney());
		
		//写到输出流
		try {
			book.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				book.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Waybilldetail> waybilldetailList(Long sn) {
		return waybillService.walbilldetailList(sn);
	}

}
