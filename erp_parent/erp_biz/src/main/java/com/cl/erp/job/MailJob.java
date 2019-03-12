package com.cl.erp.job;

import java.util.List;

import javax.mail.MessagingException;

import com.cl.erp.biz.IStoredetailBiz;
import com.cl.erp.entity.Storealert;
/**
 * 后台定时检测库存预警
 * 如果存在预警则发送预警邮件给工作人员
 * @author CL
 *
 */
public class MailJob {

	private IStoredetailBiz storedetailBiz;

	public IStoredetailBiz getStoredetailBiz() {
		return storedetailBiz;
	}

	public void setStoredetailBiz(IStoredetailBiz storedetailBiz) {
		this.storedetailBiz = storedetailBiz;
	}
	
	
	public void sendStorealertMail(){
		List<Storealert> storealertList = storedetailBiz.getStorealertList();
		if(storealertList.size()>0){
			try{
				storedetailBiz.sendStorealertMail();
			}catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}
}
