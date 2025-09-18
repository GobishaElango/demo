package com.qualiantech.supermarket.quotation;

public class QuotationLineVO {
	private String quotationlineId;
	private char isActive;
	private String created;
	private String createdBy;
	private String updated;
	private String updatedBy;
	private String quotationId;
    private String productId;
    private String productName;
    private String uom;
    private int quantity;
    private double price;
    private double lineNetAmt;
    
    public QuotationLineVO() {
    	
    }
    public QuotationLineVO(String productName, double price, int quantity, String uom,double lineNetAmt) {
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
		this.uom=uom;
		this.lineNetAmt = lineNetAmt;
	}
	public QuotationLineVO(String quotationlineId, char isActive, String created, String createdBy, String updated,
			String updatedBy, String quotationId, String productId, double price,int quantity,String uom,
			double lineNetAmt) {
		
		this.quotationlineId = quotationlineId;
		this.isActive = isActive;
		this.created = created;
		this.createdBy = createdBy;
		this.updated = updated;
		this.updatedBy = updatedBy;
		this.quotationId = quotationId;
		this.productId = productId;
		this.uom = uom;
		this.quantity = quantity;
		this.price = price;
		this.lineNetAmt = lineNetAmt;
	}
	public String getQuotationlineId() {
		return quotationlineId;
	}
	public void setQuotationlineId(String quotationlineId) {
		this.quotationlineId = quotationlineId;
	}
	public char getIsActive() {
		return isActive;
	}
	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	public String getQuotationId() {
		return quotationId;
	}
	public void setQuotationId(String quotationId) {
		this.quotationId = quotationId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
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
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getLineNetAmt() {
		return lineNetAmt;
	}
	public void setLineNetAmt(double lineNetAmt) {
		this.lineNetAmt = lineNetAmt;
	}
	@Override
	public String toString() {
		return "QuotationlineVO [productName=" + productName + ", uom=" + uom + ", quantity=" + quantity + ", price="
				+ price + ", lineNetAmt=" + lineNetAmt + "]";
	}


    
    
}


