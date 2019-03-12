package com.cl.erp.biz.impl;

import java.util.List;

import com.cl.erp.biz.IDepBiz;
import com.cl.erp.dao.IDepDao;
import com.cl.erp.dao.impl.DepDao;
import com.cl.erp.entity.Dep;
/**
 * 部门业务实现
 * @author CL
 *
 */
public class DepBiz extends BaseBiz<Dep> implements IDepBiz {

	private IDepDao depDao;
	
	public void setDepDao(IDepDao depDao) {
		this.depDao = depDao;
		super.setBaseDao(this.depDao);
	}
	
}
