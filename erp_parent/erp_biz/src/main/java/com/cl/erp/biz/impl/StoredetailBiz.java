package com.cl.erp.biz.impl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import com.cl.erp.biz.IStoredetailBiz;
import com.cl.erp.dao.IGoodsDao;
import com.cl.erp.dao.IStoreDao;
import com.cl.erp.dao.IStoredetailDao;
import com.cl.erp.entity.Storealert;
import com.cl.erp.entity.Storedetail;
import com.cl.erp.exception.ErpException;
import com.cl.erp.util.MailUtil;
/**
 * 仓库库存业务逻辑类
 * @author Administrator
 *
 */
public class StoredetailBiz extends BaseBiz<Storedetail> implements IStoredetailBiz {

	private IStoredetailDao storedetailDao;
	private IGoodsDao goodsDao;
	private IStoreDao storeDao;
	
	public IGoodsDao getGoodsDao() {
		return goodsDao;
	}

	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	public IStoreDao getStoreDao() {
		return storeDao;
	}

	public void setStoreDao(IStoreDao storeDao) {
		this.storeDao = storeDao;
	}

	public IStoredetailDao getStoredetailDao() {
		return storedetailDao;
	}

	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
		setBaseDao(storedetailDao);
	}

	public List<Storedetail> getListByPage(Storedetail storedetail1,Storedetail storedetail2,Object param,int firstResult, int maxResults){
		List<Storedetail> list = super.getListByPage(storedetail1, storedetail2, param, firstResult, maxResults);
		//查商品和库存
		Map<Long,String> goodsMap = new HashMap<Long, String>();
		Map<Long,String> storeMap = new HashMap<Long, String>();
		for (Storedetail sd : list) {
			sd.setGoodsname(getGoodsName(sd.getGoodsuuid(), goodsMap,goodsDao));
			sd.setStorename(getStoreName(sd.getStoreuuid(),storeMap,storeDao));
		}
		return list;
	}

	@Override
	public List<Storealert> getStorealertList() {
		// TODO Auto-generated method stub
		return storedetailDao.getStorealertList();
	}

	private MailUtil mailUtil;
	public MailUtil getMailUtil() {
		return mailUtil;
	}

	public void setMailUtil(MailUtil mailUtil) {
		this.mailUtil = mailUtil;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	private String to;
	private String text;
	private String subject;
	/**
	 * 发送告警邮件
	 */
	@Override
	public void sendStorealertMail() throws MessagingException {
		List<Storealert> storealertList = storedetailDao.getStorealertList();
		int cnt = storealertList==null?0:storealertList.size();
		if(cnt>0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			//发送邮件
			mailUtil.sendMail(to, subject.replace("[time]", sdf.format(new Date())), text.replace("[count]", String.valueOf(cnt)));
			
		}else{
			throw new ErpException("没有需要预警的商品");
		}
	}

}
