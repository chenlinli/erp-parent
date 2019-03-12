package com.cl.erp.biz;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.cl.erp.entity.Supplier;
/**
 * 供应商业务逻辑层接口
 * @author Administrator
 *
 */
public interface ISupplierBiz extends IBaseBiz<Supplier>{
	
	void export(OutputStream os,Supplier t1);
	void doImport(InputStream is)  throws IOException;
}

