package com.qualiantech.supermarket.quotation;

import java.util.ArrayList;
public class QuotationVO {
	private String quotationId;
	private char isActive;
	private String createdDate;
	private String createdBy;
	private String updatedDate;
	private String updatedBy;
	private String vendorId;
	private String vendorName;
	private String documentNo;
	private String date ;
	private String validTill ;
	private ArrayList<QuotationLineVO> lineItems;
	public QuotationVO() {
		
	}
	public QuotationVO(String documentNo,String vendorName ,String date, String validTill,ArrayList<QuotationLineVO> lineItems) {
		//super();
		this.documentNo = documentNo;
		this.vendorName = vendorName;
		this.date = date;
		this.validTill = validTill;
		this.lineItems = lineItems;
	}

	public QuotationVO(String quotationId, char isActive, String created, String createdBy, String updated,
			String updatedBy, String vendorId,String documentNo, String date, String validTill,
			ArrayList<QuotationLineVO> lineItems) {
	
		this.quotationId = quotationId;
		this.isActive = isActive;
		this.createdDate = created;
		this.createdBy = createdBy;
		this.updatedDate = updated;
		this.updatedBy = updatedBy;
		this.vendorId = vendorId;
		this.documentNo = documentNo;
		this.date = date;
		this.validTill = validTill;
		this.lineItems = lineItems;
	}
	
	//Getters and Setters
	public String getQuotationId() {
		return quotationId;
	}
	public void setQuotationId(String quotationId) {
		this.quotationId = quotationId;
	}
	public char getIsActive() {
		return isActive;
	}
	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getValidTill() {
		return validTill;
	}
	public void setValidTill(String validTill) {
		this.validTill = validTill;
	}
	public ArrayList<QuotationLineVO> getLineItems() {
		return lineItems;
	}
	public void setLineItems(ArrayList<QuotationLineVO> lineItems) {
		this.lineItems = lineItems;
	}
	
}


