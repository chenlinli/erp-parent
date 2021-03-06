package com.cl.erp.entity;

import java.util.List;

/**
 * 退货订单实体类
 * @author Administrator *
 */
public class Returnorders {	
	public static final String TYPE_IN = "0";//销售退货
	public static final String TYPE__OUT = "1";//采购退货
	public static final String STATE_NOT_CONFIRMED = "0";//未审核状态
	public static final String STATE__CONFIRMED = "1";//已审核状态
	public static final String STATE_END="2";//采购退货订单下的所有明细处理完毕
	private Long uuid;//编号
	private java.util.Date createtime;//生成日期
	private java.util.Date checktime;//审核日期
	private java.util.Date endtime;//出库日期
	private String type;//订单类型：1.入库单退货申请，2 出库单退货
	private Long creater;//下单员
	private String creatername;
	private Long checker;//审核员工编号
	private String checkername;
	private Long ender;//库管员
	private String endername;
	private Long supplieruuid;//供应商及客户编号
	private String suppliername;
	private List<Returnorderdetail> returnorderDetails;//采购退货详情
	

	public List<Returnorderdetail> getReturnorderDetails() {
		return returnorderDetails;
	}
	public void setReturnorderDetails(List<Returnorderdetail> returnorderDetails) {
		this.returnorderDetails = returnorderDetails;
	}
	public String getCreatername() {
		return creatername;
	}
	@Override
	public String toString() {
		return "Returnorders [uuid=" + uuid + ", createtime=" + createtime + ", checktime=" + checktime + ", endtime="
				+ endtime + ", type=" + type + ", creater=" + creater + ", creatername=" + creatername + ", checker="
				+ checker + ", checkername=" + checkername + ", ender=" + ender + ", endername=" + endername
				+ ", supplieruuid=" + supplieruuid + ", suppliername=" + suppliername + ", totalmoney=" + totalmoney
				+ ", state=" + state + ", waybillsn=" + waybillsn + ", orders=" + orders + "]";
	}
	public void setCreatername(String creatername) {
		this.creatername = creatername;
	}
	public String getCheckername() {
		return checkername;
	}
	public void setCheckername(String checkername) {
		this.checkername = checkername;
	}
	public String getEndername() {
		return endername;
	}
	public void setEndername(String endername) {
		this.endername = endername;
	}
	public String getSuppliername() {
		return suppliername;
	}
	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	private Double totalmoney;//合计金额
	private String state;//订单状态
	private Long waybillsn;//运单号
	private Orders orders;//原订单

	public Long getUuid() {		
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public java.util.Date getCreatetime() {		
		return createtime;
	}
	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}
	public java.util.Date getChecktime() {		
		return checktime;
	}
	public void setChecktime(java.util.Date checktime) {
		this.checktime = checktime;
	}
	public java.util.Date getEndtime() {		
		return endtime;
	}
	public void setEndtime(java.util.Date endtime) {
		this.endtime = endtime;
	}
	public String getType() {		
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getCreater() {		
		return creater;
	}
	public void setCreater(Long creater) {
		this.creater = creater;
	}
	public Long getChecker() {		
		return checker;
	}
	public void setChecker(Long checker) {
		this.checker = checker;
	}
	public Long getEnder() {		
		return ender;
	}
	public void setEnder(Long ender) {
		this.ender = ender;
	}
	public Long getSupplieruuid() {		
		return supplieruuid;
	}
	public void setSupplieruuid(Long supplieruuid) {
		this.supplieruuid = supplieruuid;
	}
	public Double getTotalmoney() {		
		return totalmoney;
	}
	public void setTotalmoney(Double totalmoney) {
		this.totalmoney = totalmoney;
	}
	public String getState() {		
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getWaybillsn() {		
		return waybillsn;
	}
	public void setWaybillsn(Long waybillsn) {
		this.waybillsn = waybillsn;
	}
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}

}
