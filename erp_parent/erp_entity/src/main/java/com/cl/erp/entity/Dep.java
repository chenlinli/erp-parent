package com.cl.erp.entity;
/**
 * 
 * @author CL
 *
 */
public class Dep {
	//部门编号
	private Long uuid;
	//名称
	private String name;
	//电话
	private String tele
	;
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTele() {
		return tele;
	}
	@Override
	public String toString() {
		return "Dep [uuid=" + uuid + ", name=" + name + ", tele=" + tele + "]";
	}
	public void setTele(String tele) {
		this.tele = tele;
	}
	

}
