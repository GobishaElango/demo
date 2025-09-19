package com.qualiantech.purchaseorder;

import java.sql.Date;
import java.util.ArrayList;


public class PurchaseorderVO{
	private char isActive;
	private Date createdDate;
	private Date updatedDate;
	private String createdBy;
	private String updatedBy;
	private String documentNo;
	private Date orderDate;
	private Date deliveryDate;
	private String vendorId;
	private String vendorName;
	private String vendorAddress;
	private String deliveryAddress;
	private double totalAmount;
    private ArrayList<PurchaseorderlineVO> lineItems;
	
    public PurchaseorderVO(String documentNo,Date orderDate, Date deliveryDate, String vendorName ,String vendorAddress, String deliveryAddress, double totalAmount,ArrayList<PurchaseorderlineVO> lineItems) {
    	this.documentNo = documentNo;
		this.orderDate = orderDate;
		this.deliveryDate = deliveryDate;
		this.vendorName = vendorName;
		this.vendorAddress = vendorAddress;
		this.deliveryAddress = deliveryAddress;
		this.totalAmount = totalAmount;
		this.lineItems = lineItems;
	}
    public PurchaseorderVO() {
    }
    
    public PurchaseorderVO(char isActive, Date created, String createdBy, Date updated,
			String updatedBy,String documentNo, Date orderDate, Date deliveryDate, String vendorId, String vendorAddress, String deliveryAddress, double totalAmount,
			ArrayList<PurchaseorderlineVO> lineItems) {
	
		this.isActive = isActive;
		this.createdDate = created;
		this.createdBy = createdBy;
		this.updatedDate = updated;
		this.updatedBy = updatedBy;
		this.documentNo = documentNo;
		this.orderDate = orderDate;
		this.deliveryDate = deliveryDate;
		this.vendorId = vendorId;
		this.vendorAddress = vendorAddress;
		this.deliveryAddress = deliveryAddress;
		this.totalAmount = totalAmount;
		this.lineItems = lineItems;
	}
    
	public char getIsActive() {
		return isActive;
	}
	public void setIsactive(char isActive) {
		this.isActive = isActive;
	}
	public Date getCreated() {
		return createdDate;
	}
	public void setCreated(Date created) {
		this.createdDate = created;
	}

	public Date getUpdated() {
		return updatedDate;
	}
	public void setUpdated(Date updated) {
		this.updatedDate = updated;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedby(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorAddress() {
		return vendorAddress;
	}
	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	public ArrayList<PurchaseorderlineVO> getLineItems(){
		return lineItems;
	}
	
	public void setLineItems(ArrayList<PurchaseorderlineVO> lineItems) {
		this.lineItems = lineItems;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
}

