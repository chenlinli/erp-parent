package com.cl.erp.test.dao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cl.erp.dao.IDepDao;

public class DepDaoTest {

	@Test
	public void testDep(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext_*.xml");
		IDepDao dao = (IDepDao) ac.getBean("depDao");
		//System.out.println(dao.getList().size());
	}
}
