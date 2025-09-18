package com.qualiantech.purchaseorder;

import java.util.UUID;

public class PurchaseorderlineVO{
	private String orderlineId;
	private char isActive;
	private String created;
	private String createdBy;
	private String updated;
	private String updatedBy;
	private String orderId;
	private String productId;
	private String productName;
	private String uom;
	private int quantity;
	private double price;
	private double amount;
	
	public PurchaseorderlineVO(){
		
	}
	public PurchaseorderlineVO(String productName, double price, int quantity, String uom, double amount){
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
		this.uom=uom;
		this.amount = amount;
	}
	
	public PurchaseorderlineVO(String orderlineId, char isActive, String created, String createdBy, String updated, String updatedBy, String orderId, String productId, double price, int quantity, String uom, double amount) {
		this.orderlineId = orderlineId;
		this.isActive = isActive;
		this.created = created;
		this.createdBy = createdBy;
		this.updated = updated;
		this.updatedBy = updatedBy;
		this.orderId = orderId;
		this.productId = productId;
		this.uom = uom;
		this.price = price;
		this.quantity = quantity;
		this.amount = amount;
	}
	
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getOrderlineId() {
		return orderlineId;
	}
	public void setOrderlineId(String orderlineId) {
		this.orderlineId = orderlineId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public char getIsActive() {
		return isActive;
	}
	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String toString() {
		return "Purchaseorderline [productName=" + productName + ", uom=" + uom + ", quantity=" + quantity + ", price="
				+ price + ", amount=" + amount + "]";
	}
}