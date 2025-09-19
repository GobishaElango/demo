package Shipment;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class InoutLineVO {
	private String inoutline_id;
	private String isactive;
	private String created;
	private String updated;
	private String createdby;
	private String updatedby;
	private String inoutId;
	private String productId;
	private int quantity;
	
	
	public InoutLineVO() {
		
		
	}

//	public InoutLineDAO(String inoutline_id, String isactive, String created, String updated, String createdby,
//			String updatedby, String productId, int quantity) {
//		super();
//		this.inoutline_id = inoutline_id;
//		this.isactive = isactive;
//		this.created = created;
//		this.updated = updated;
//		this.createdby = createdby;
//		this.updatedby = updatedby;
//		this.inoutId = inoutId;
//		this.productId = productId;
//		this.quantity = quantity;
//	}

	public InoutLineVO(String createdby,String updatedby,String inoutId, String productId, int quantity) {
		this.createdby = createdby;
		this.updatedby = updatedby;
		this.inoutId = inoutId;
		this.productId = productId;
		this.quantity = quantity;
	}
	
	

	public String getIsactive() {
		return isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	public Timestamp getCreated() {
		return Timestamp.from(ZonedDateTime.now(ZoneId.of("UTC")).toInstant());
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
}