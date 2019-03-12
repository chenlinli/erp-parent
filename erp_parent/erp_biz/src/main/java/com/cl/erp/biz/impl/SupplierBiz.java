package com.cl.erp.biz.impl;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.cl.erp.biz.ISupplierBiz;
import com.cl.erp.dao.ISupplierDao;
import com.cl.erp.entity.Supplier;
import com.cl.erp.exception.ErpException;

import oracle.net.aso.l;
/**
 * 供应商业务逻辑类
 * @author Administrator
 *
 */
public class SupplierBiz extends BaseBiz<Supplier> implements ISupplierBiz {

	private ISupplierDao supplierDao;
	
	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
		setBaseDao(supplierDao);
	}
		/**
		 * 导出数据
		 */
	@Override
	public void export(OutputStream os,Supplier t1) {
		//获取要导出的数据列表
		List<Supplier> list = supplierDao.getList(t1, null, null);
		//工作簿创建
		HSSFWorkbook book = new HSSFWorkbook();
		//创建工作表
		String sheetName = "";
		if(t1.getType().equals(Supplier.CUSTOMER))
			sheetName="客户";
		//创建行：行索引0开始
		else if(t1.getType().equals(Supplier.SUPPLIER)){
			sheetName="供应商";
		}
		HSSFSheet sheet = book.createSheet(sheetName);
		HSSFRow row = sheet.createRow(0);
		HSSFCell ceil =null;
		String[] header = {"名称","地址","联系人","电话","Email"};
		int [] width={5000,8000,4000,8000,10000};
		for(int i=0;i<header.length;i++){
			//创建单元格:列索引0开始
			 ceil = row.createCell(i);
			 ceil.setCellValue(header[i]);
			 //设置列宽
			 sheet.setColumnWidth(i, width[i]);
		}
		int rowcount=1;
		//导出的内容
		for(Supplier supplier:list){
			 row = sheet.createRow(rowcount++);
			row.createCell(0).setCellValue(supplier.getName());
			row.createCell(1).setCellValue(supplier.getAddress());
			row.createCell(2).setCellValue(supplier.getContact());
			row.createCell(3).setCellValue(supplier.getTele());
			row.createCell(4).setCellValue(supplier.getEmail());			
		}
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
	/**
	 * 导入供应商
	 * @throws IOException 
	 */
		@Override
		public void doImport(InputStream is) throws IOException {
			HSSFWorkbook bk=null;
			try {
				bk = new HSSFWorkbook(is);
				Sheet sheet = bk.getSheetAt(0);
				String type="";
				if(sheet.getSheetName().equals("供应商")){
					type=Supplier.SUPPLIER;
				}else if(sheet.getSheetName().equals("客户")){
					type=Supplier.CUSTOMER;
				}else{
					throw new ErpException("工作表名称不正确");
				}
				//读数据
				int lastRow = sheet.getLastRowNum();
				Supplier supplier = null;
				for(int i=1;i<=lastRow;i++){
					supplier  = new Supplier();
					supplier.setName(sheet.getRow(i).getCell(0).getStringCellValue());
					List<Supplier> list = supplierDao.getList(null, supplier, null);
					if(list.size()>0){
						supplier=list.get(0);
					}
					
					supplier.setAddress(sheet.getRow(i).getCell(1).getStringCellValue());
					supplier.setContact(sheet.getRow(i).getCell(2).getStringCellValue());
					supplier.setTele(sheet.getRow(i).getCell(3).getStringCellValue());
					supplier.setEmail(sheet.getRow(i).getCell(0).getStringCellValue());
					//判断是否存在
					if(list.size()==0){
						//新增
						supplier.setType(type);
						supplierDao.add(supplier);
					}
				}
			}finally {
				if(null!=bk){
					try {
						bk.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	
}
