package com.qualiantech.purchaseorder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

public class PurchaseorderDAO {

	public Connection openConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		return DriverManager.getConnection("jdbc:postgresql://db:5432/Supermarket", "postgres", "qualian");
	}

	public static String formatDate(String dateString) {
		LocalDate date = LocalDate.parse(dateString);
		return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	public int getPurchaseorderCount() {
		int rowCount = 0;
		try (Connection connection = openConnection(); Statement stmt = connection.createStatement();) {
			ResultSet rs = stmt.executeQuery("select count(*) from purchaseorder");
			if (rs.next()) {
				rowCount = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowCount;
	}

	public String generateDocumentNo() {
		String prefix = "PO";
		String documentNo = prefix + "001";
		try (Connection connection = openConnection(); Statement st = connection.createStatement()) {
			ResultSet rs = st.executeQuery("SELECT MAX(document_no) FROM purchaseorder");
			if (rs.next()) {
				String maxDocNo = rs.getString(1);
				if (maxDocNo != null && maxDocNo.startsWith(prefix)) {
					String numericPart = maxDocNo.substring(prefix.length());
					int num = Integer.parseInt(numericPart);
					num++;

					documentNo = prefix + String.format("%03d", num);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return documentNo;
	}

	private static String statusMessage = null;

	public static void setStatusMessage(String msg) {
		statusMessage = msg;
	}

	public static String getStatusMessage() {
		return statusMessage;
	}

	public String getVendorIdByName(String vendorName) {
		String vendorId = null;
		try (Connection connection = openConnection();
				PreparedStatement pstmt1 = connection.prepareStatement("select vendor_id from vendor where name=?");) {
			pstmt1.setString(1, vendorName);
			ResultSet rs = pstmt1.executeQuery();
			if (rs.next()) {
				vendorId = rs.getString(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return vendorId;
	}

	public String[] getProductIdUOMByName(String productName) {
		String productId = null;
		String uom = null;
		try (Connection connection = openConnection();
				PreparedStatement pstmt = connection
						.prepareStatement("select product_id,uom from product where name=?");) {
			pstmt.setString(1, productName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				productId = rs.getString(1);
				uom = rs.getString(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String[] { productId, uom };
	}

	public ArrayList<PurchaseorderVO> getAllPurchaseorders(int limit, int offset) {
		ArrayList<PurchaseorderVO> purchaseorders = new ArrayList<>();
		try (Connection connection = openConnection();
				// connection.setAutoCommit(true);
				PreparedStatement pstmt = connection.prepareStatement(
						"select po.document_no, po.order_date, po.delivery_date, v.name, po.vendor_address, po.delivery_address, po.total_amount from purchaseorder as po "
								+ " inner join vendor as v on po.vendor_id=v.vendor_id order by po.document_no desc limit ? offset ?");) {
			pstmt.setInt(1, limit);
			pstmt.setInt(2, offset);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ArrayList<PurchaseorderlineVO> lineItems = new ArrayList<>();
				String documentNo = rs.getString(1);
				PreparedStatement pstmt2 = connection.prepareStatement(
						" select p.name,pol.price, pol.quantity,pol.uom,pol.amount from purchaseorderline as pol "
								+ " inner join purchaseorder as po on pol.order_id =po.order_id "
								+ " inner join product as p on pol.product_id=p.product_id where po.document_no = ? ");
				pstmt2.setString(1, documentNo);
				ResultSet rs2 = pstmt2.executeQuery();
				while (rs2.next()) {
					lineItems.add(new PurchaseorderlineVO(rs2.getString(1), rs2.getDouble(2), rs2.getInt(3),
							rs2.getString(4), rs2.getDouble(5)));
				}
				purchaseorders.add(new PurchaseorderVO(rs.getString(1), rs.getDate(2), rs.getDate(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getDouble(7), lineItems));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return purchaseorders;
	}

	public int insertPurchaseorderLines(ArrayList<PurchaseorderlineVO> lineItems, String orderId) {
		int insertedRowCount = 0;
		String query = " insert into purchaseorderline ( createdby, updatedby,order_id,product_id,uom,quantity,price,amount)"
				+ "values(?,?,?,?,?,?,?,?) ";
		try (Connection connection = openConnection(); PreparedStatement pstmt = connection.prepareStatement(query);) {
			for (PurchaseorderlineVO lineItem : lineItems) {
				String[] productUOM = getProductIdUOMByName(lineItem.getProductName());
				pstmt.setString(1, "1");
				pstmt.setString(2, "1");
				pstmt.setObject(3, UUID.fromString(orderId));
				pstmt.setObject(4, UUID.fromString(productUOM[0]));
				pstmt.setString(5, productUOM[1]);
				pstmt.setInt(6, lineItem.getQuantity());
				pstmt.setDouble(7, lineItem.getPrice());
				pstmt.setDouble(8, lineItem.getAmount());
				pstmt.executeUpdate();
				insertedRowCount++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return insertedRowCount;

	}

	// Add Purchaseorder
	public int insertPurchaseorder(PurchaseorderVO purchaseorder) {
		String orderId = null;
		int insertedRowCount = 0;
		try (Connection connection = openConnection();
				PreparedStatement pstmt1 = connection.prepareStatement(
						"insert into purchaseorder(createdby,updatedby,vendor_id,document_no,order_date,delivery_date,vendor_address,delivery_address,total_amount) values(?,?,?,?,?,?,?,?,?)");) {
			pstmt1.setString(1, "1");
			pstmt1.setString(2, "1");
			pstmt1.setObject(3, UUID.fromString(getVendorIdByName(purchaseorder.getVendorName())));
			pstmt1.setString(4, purchaseorder.getDocumentNo());
			pstmt1.setDate(5, Date.valueOf(purchaseorder.getOrderDate().toString()));
			pstmt1.setDate(6, Date.valueOf(purchaseorder.getDeliveryDate().toString()));
			pstmt1.setString(7, purchaseorder.getVendorAddress());
			pstmt1.setString(8, purchaseorder.getDeliveryAddress());
			pstmt1.setDouble(9, purchaseorder.getTotalAmount());
			pstmt1.executeUpdate();
			PreparedStatement pstmt2 = connection
					.prepareStatement("select order_id from purchaseorder where document_no = ?");
			pstmt2.setString(1, purchaseorder.getDocumentNo());
			;
			ResultSet rs1 = pstmt2.executeQuery();
			if (rs1.next()) {
				orderId = rs1.getString(1);
			}
			insertedRowCount = insertPurchaseorderLines(purchaseorder.getLineItems(), orderId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return insertedRowCount;
	}

//update purchaseorder
	public int updatePurchaseorder(PurchaseorderVO purchaseorder) {
		String orderId = null;
		int insertedRowCount = 0;
		try (Connection connection = openConnection();
				PreparedStatement pstmt = connection.prepareStatement(
						"update purchaseorder set vendor_id=?, order_date=?, delivery_date =?, vendor_address =?, delivery_address =?, total_amount=?  where document_no=?;");) {
			pstmt.setObject(1, UUID.fromString(getVendorIdByName(purchaseorder.getVendorName())));
			pstmt.setDate(2, Date.valueOf(purchaseorder.getOrderDate().toString()));
			pstmt.setDate(3, Date.valueOf(purchaseorder.getDeliveryDate().toString()));
			pstmt.setString(4, purchaseorder.getVendorAddress());
			pstmt.setString(5, purchaseorder.getDeliveryAddress());
			pstmt.setDouble(6, purchaseorder.getTotalAmount());
			pstmt.setString(7, purchaseorder.getDocumentNo());
			pstmt.executeUpdate();
			PreparedStatement pstmt2 = connection
					.prepareStatement("select order_id from purchaseorder where document_no=?");
			pstmt2.setString(1, purchaseorder.getDocumentNo());
			ResultSet rs1 = pstmt2.executeQuery();
			if (rs1.next()) {
				orderId = rs1.getString(1);
			}
			PreparedStatement pstmt3 = connection.prepareStatement("delete from purchaseorderline where order_id=?");
			pstmt3.setObject(1, UUID.fromString(orderId));
			pstmt3.executeUpdate();
			insertedRowCount = insertPurchaseorderLines(purchaseorder.getLineItems(), orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return insertedRowCount;
	}

//delete Purchase order
	public int deletePurchaseorder(String[] orderIds) {
		int deletedRowCount = 0;
		try (Connection connection = openConnection();
				PreparedStatement pstmt1 = connection.prepareStatement(
						"delete from purchaseorderline USING purchaseorder where purchaseorderline.order_id = purchaseorder.order_id AND purchaseorder.document_no = ?");
				PreparedStatement pstmt2 = connection
						.prepareStatement("delete from purchaseorder where document_no=?");) {
			for (String documentno : orderIds) {
				pstmt1.setString(1, documentno);
				pstmt1.executeUpdate();
				pstmt2.setString(1, documentno);
				pstmt2.executeUpdate();
				deletedRowCount++;
			}
			// connection.commit();
			if (orderIds.length == 1) {
				setStatusMessage("Purchase Order " + orderIds[0] + " Deleted Successfully");
			} else if (orderIds.length > 1) {
				setStatusMessage("Purchase Orders Deleted Successfully!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deletedRowCount;
	}

}
