package com.stock.model;

public class DeliveryStockInfo {
	private Long stockInfoSrl;
	private String prdTitle;
	private String optTitle;
	private Long amount;
	
	public Long getStockInfoSrl() {
		return stockInfoSrl;
	}
	public void setStockInfoSrl(Long stockInfoSrl) {
		this.stockInfoSrl = stockInfoSrl;
	}
	public String getPrdTitle() {
		return prdTitle;
	}
	public void setPrdTitle(String prdTitle) {
		this.prdTitle = prdTitle;
	}
	public String getOptTitle() {
		return optTitle;
	}
	public void setOptTitle(String optTitle) {
		this.optTitle = optTitle;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
}