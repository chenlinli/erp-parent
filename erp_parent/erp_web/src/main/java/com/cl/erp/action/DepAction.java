package com.cl.erp.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import com.alibaba.fastjson.JSON;
import com.cl.erp.biz.IDepBiz;
import com.cl.erp.entity.Dep;

/**
 * 部门Action
 * @author CL
 *
 */
public class DepAction extends BaseAction<Dep> {

	private IDepBiz depBiz;

	public void setDepBiz(IDepBiz depBiz) {
		this.depBiz = depBiz;
		super.setBaseBiz(this.depBiz);
	}

}

