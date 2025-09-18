package com.qualiantech.goodsshipment;

import java.util.UUID;

public class ShipmentLineVO {
	private String inoutId;
	private String productId;
	private int quantity;
	private String inoutlineId;

	public ShipmentLineVO() {
	}

	public String getInoutId() {
		return inoutId;
	}

	public void setInoutId(String inoutId) {
		this.inoutId = inoutId;
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

	public String getInoutlineId() {
		return inoutlineId;
	}

	public void setInoutlineId(String string) {
		this.inoutlineId = inoutlineId;

	}
}
