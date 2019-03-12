package com.cl.erp.biz;
import java.util.List;

import javax.mail.MessagingException;

import com.cl.erp.entity.Storealert;
import com.cl.erp.entity.Storedetail;
/**
 * 仓库库存业务逻辑层接口
 * @author Administrator
 *
 */
public interface IStoredetailBiz extends IBaseBiz<Storedetail>{
	public List<Storealert> getStorealertList();
	
	void sendStorealertMail() throws MessagingException;
	
}

