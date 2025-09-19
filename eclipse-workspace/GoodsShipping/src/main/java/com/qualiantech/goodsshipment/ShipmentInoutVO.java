package com.qualiantech.goodsshipment;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class ShipmentInoutVO {
	private String inoutId;
	private String isactive;
	private String created;
	private String updated;
	private String createdby;
	private String updatedby;
	private String documentNo;
	private Date documentDate;
	private String customerId;
	private Vector<ShipmentLineVO> lineItems;

//    public ShipmentInoutVO(String createdby, String updatedby, String customerId, String documentNo, Date documentDate) {
//        this.createdby = createdby;
//        this.updatedby = updatedby;
//        this.documentNo = documentNo;
//        this.documentDate = documentDate;
//        this.customerId = customerId;
//    }

	public ShipmentInoutVO() {
	}

	public String getInoutId() {
		return inoutId;
	}

	public void setInoutId(String inoutId) {
		this.inoutId = inoutId;
	}

	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	public Timestamp getCreated() {
		ZonedDateTime utcNow = ZonedDateTime.now(ZoneId.of("UTC"));
		return Timestamp.from(utcNow.toInstant());
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public Timestamp getUpdated() {
		return Timestamp.from(ZonedDateTime.now(ZoneId.of("UTC")).toInstant());
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Vector<ShipmentLineVO> getLineItems() {
		return lineItems;
	}

	public void setLineItems(Vector<ShipmentLineVO> lineItems) {
		this.lineItems = lineItems;
	}
}
