package com.cl.erp.action;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cl.erp.biz.ISupplierBiz;
import com.cl.erp.entity.Supplier;
import com.cl.erp.exception.ErpException;

/**
 * 供应商Action 
 * @author Administrator
 *
 */
public class SupplierAction extends BaseAction<Supplier> {

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	private ISupplierBiz supplierBiz;
	
	public void setSupplierBiz(ISupplierBiz supplierBiz) {
		this.supplierBiz = supplierBiz;
		setBaseBiz(supplierBiz);
	}
	
	//自动补全，easyui-combobox自动发送的
	private String q;

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public ISupplierBiz getSupplierBiz() {
		return supplierBiz;
	}
	
	public void list(){
		//查询条件是否为null
		if(null==getT1()){
			//构建查询条件
			setT1(new Supplier());
		}
		getT1().setName(q);
		super.list();
	}
	
	public void export(){
		// 
		String filename="";
		if(Supplier.SUPPLIER.equals(getT1().getType())){
			filename="供应商";
		}
		if(Supplier.CUSTOMER.equals(getT1().getType())){
			filename="客户";
		}
		filename+=".xls";
		HttpServletResponse response = ServletActionContext.getResponse();
		
		try {
			//实现下载文件
			response.setHeader("Content-Disposition", "attachment;filename="+new String(filename.getBytes(),"ISO-8859-1"));
			supplierBiz.export(response.getOutputStream(), getT1());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private String fileFileName;//文件名
	private String fileContentType;//文件类型
	private File file;//文件
	
	public void doImport(){
		//文件类型的判断:application/vnd.ms-excel
		if(!"application/octet-stream".equals(fileContentType)){
			ajaxReturn(false, "上传文件必须为excel文件");
			return ;
		}
		try {
			supplierBiz.doImport(new FileInputStream(file));
			ajaxReturn(true, "上次文件成功");
		} catch (ErpException e) {
			ajaxReturn(false, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			ajaxReturn(false, "上传文件失败");
			e.printStackTrace();
		}
	}
}
