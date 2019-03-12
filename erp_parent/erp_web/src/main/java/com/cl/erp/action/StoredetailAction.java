package com.cl.erp.action;
import java.util.List;

import javax.mail.MessagingException;

import com.alibaba.fastjson.JSON;
import com.cl.erp.biz.IStoredetailBiz;
import com.cl.erp.entity.Storealert;
import com.cl.erp.entity.Storedetail;
import com.cl.erp.exception.ErpException;

/**
 * 仓库库存Action 
 * @author Administrator
 *
 */
public class StoredetailAction extends BaseAction<Storedetail> {

	private IStoredetailBiz storedetailBiz;
	
	public void setStoredetailBiz(IStoredetailBiz storedetailBiz) {
		this.storedetailBiz = storedetailBiz;
		setBaseBiz(storedetailBiz);
	}
	
	public void getStorealertList(){
		List<Storealert> storealertList = storedetailBiz.getStorealertList();
		write(JSON.toJSONString(storealertList));
	}
	
	public void sendStorealertMail(){
		try {
			storedetailBiz.sendStorealertMail();
			ajaxReturn(true, "发送预警邮件成功！");
		} catch (MessagingException e) {
			ajaxReturn(false, "构建预警邮件失败");
			e.printStackTrace();
		}catch (ErpException e) {
			ajaxReturn(false, e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			ajaxReturn(false, "发送预警邮件失败");
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
}
