package com.qualiantech.database;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class GoodsShipment {
	public static void main(String[] args) throws Exception {
		Scanner sc=new Scanner(System.in);
		GoodsShipment goodsshipment = new GoodsShipment();
		InoutDAO inoutdao = new InoutDAO();
		InoutLineDAO inoutlinedao = new InoutLineDAO();
		System.out.println();
		boolean repeat = true;
		while(repeat) {
        System.out.print("Press : \n1 to insert \n2 to update \n3 to view \n4 to exit");
   
       // \n2 to insert \n3 to update \n4 to delete \n5 to exit \n\nEnter here : "); // \n4 to delete
        int run = sc.nextInt();
		switch(run) {
			
			case 1:	
				boolean insert = true;
				while(insert) {
					goodsshipment.insertion(inoutdao,inoutlinedao);
					System.out.println("Do you want to insert Inout? Y/N :");
					String entry = sc.next();
					if("Y".equalsIgnoreCase(entry)) 
						insert = true;
					else
						break;		
				}
				break;
			case 2:
				boolean update=true;
				while(update) {
					
					System.out.print("Press : \n1 to update inout \n2 to update inoutline \n3 to exit");
					
					 int execute = sc.nextInt();
					 switch(execute) {
						 case 1:
							 goodsshipment.updationInout(inoutdao);
							break;
						 case 2:
							 goodsshipment.updationInoutLine(inoutlinedao);
							 break;
						 case 3:
							 update=false;
							 break;
						default:
							System.out.println("invalid");
							break;
					 }
							
				}
			    break;
			case 3:
				boolean view=true;
				while(view) {
					System.out.print("Press : \n1 to view inout \n2 to view inoutline \n3 to exit");
					
					 int execute = sc.nextInt();
					 switch(execute) {
						 case 1:
							 goodsshipment.viewInout(inoutdao);
							break;
						 case 2:
						 goodsshipment.viewInoutLine(inoutlinedao);
							 break;
						 case 3:
							 view=false;
							 break;
						default:
							System.out.println("invalid");
							break;
				}
				}
				break;
			 
			case 4:
				
				repeat = false;
				break;
			
			default:
				System.out.println("invalid");
				break;
	}
		}
		sc.close();
	}
	
	public  void insertion(InoutDAO inoutdao,InoutLineDAO inoutlinedao) throws Exception {
		try (Connection connection = DBConnection.getConnection()){
			connection.setAutoCommit(false);

		
		inoutdao = getInoutDAO();
		
		insertionInoutDAO(inoutdao,connection);

		String inoutId=getInoutId(inoutdao.getDocumentNo());
		List<InoutLineDAO> line = getInoutLineDAO(inoutId);
		
		insertionInoutLineDAO(line,inoutlinedao);
		connection.setAutoCommit(true);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public  void insertionInoutDAO(InoutDAO inoutdao, Connection connection) throws SQLException {
		
		try {
			String query = "insert into m_inout(createdby,updatedby,customer_id,document_no,document_date) values(?,?,?,?,?)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, inoutdao.getCreatedby());
			pst.setString(2, inoutdao.getUpdatedby());
			pst.setString(3, inoutdao.getCustomerId());
			pst.setString(4, inoutdao.getDocumentNo());
			//System.out.println(inoutdao.getDocumentDate());
			Timestamp timestamp = new Timestamp(inoutdao.getDocumentDate().getTime());
			//Timestamp fromTS1 = new Timestamp();
			//System.out.println(fromTS1);
			pst.setTimestamp(5,timestamp);
			pst.executeUpdate();
			connection.setAutoCommit(true);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		finally {
			connection.setAutoCommit(false);
			//connection.close();
		}
	}
	public  void insertionInoutLineDAO(List<InoutLineDAO> lineList,InoutLineDAO inoutlinedao) throws SQLException {
		try(Connection connection = DBConnection.getConnection()) {
			String query = "insert into m_inoutline(createdby,updatedby,inout_id,product_id,quantity) values(?,?,?,?,?)";
			try(PreparedStatement pst = connection.prepareStatement(query)){
				for(InoutLineDAO line : lineList) {
					pst.setString(1, line.getCreatedby());
					pst.setString(2, line.getUpdatedby());
					pst.setString(3, line.getInoutId());
					pst.setString(4, line.getProductId());
					pst.setInt(5, line.getQuantity());
					pst.executeUpdate();
				}
			lineList.clear();
			System.out.println("Inout and Inoutline inserted");
			}
		}
	}
	public  InoutDAO getInoutDAO() throws Exception {
		getCustomerDetail();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Select the Customer ID : ");
		String customerId = sc.next();
		
		System.out.println("Enter the documentNo : ");
		String documentNo = sc.next();
		
		System.out.print("Enter a date (yyyy/MM/dd): "); 
		String dateStr = sc.next();
		
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//       // Date sqlDate = null;
//
//       
//            java.util.Date utilDate = sdf.parse(dateStr);
//            Date sqlDate = new Date(utilDate.getTime());
            //inoutdao.setDocumentDate(sqlDate);
        
            
            DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate localDate= LocalDate.parse(dateStr, formatter);
            Date sqlDate= Date.valueOf(localDate);
            String createdby="22";
            // Output the result
//            System.out.println("SQL Date: " + sqlDate);
        
        return new InoutDAO(createdby,createdby,customerId, documentNo, sqlDate);
		
	}
	
	
	public  ArrayList<InoutLineDAO> getInoutLineDAO(String inoutId) throws SQLException {
		boolean repeat= true;
		ArrayList<InoutLineDAO>  inoutLineDAO= new ArrayList<InoutLineDAO>();

		//getInoutIdDetail();
		while(repeat) {
		
		getProductDetail();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Select the Product ID : ");
		String productId = sc.next();
		System.out.println("Enter the quantity : ");
		int quantity = sc.nextInt();
		String createdby="22";
		inoutLineDAO.add(new InoutLineDAO(createdby,createdby,inoutId,productId,quantity));
		System.out.println("Do you want to insert Inoutline again? Y/N :");
		String entry = sc.next();
		if("Y".equalsIgnoreCase(entry)) 
			repeat = true;
		else
			break;	
		}
		return inoutLineDAO;
	}
	
	
	
	
	
	
	
	public  void updationInout(InoutDAO inoutdao) throws Exception {
		try(Connection connection = DBConnection.getConnection()) {
			connection.setAutoCommit(false);

			Scanner sc = new Scanner(System.in);
			String sql = "select document_no from m_inout;";
			PreparedStatement pst2 = connection.prepareStatement(sql);
			ResultSet rst2 = pst2.executeQuery();
			System.out.println("inout document numbers");
			while(rst2.next()) {
				System.out.println(rst2.getString("document_no"));
			}
			System.out.println("select the documentNo : ");
			String documentNo = sc.next();
			getCustomerDetail();
			System.out.println("select the Customer Id  : ");
			String customerId = sc.next();
			System.out.println("Enter the document Date [yyyy/MM/dd]: ");
			String documentDate = sc.next();
			 DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy/MM/dd");
	         LocalDate localDate= LocalDate.parse(documentDate, formatter);
	         Date sqlDate= Date.valueOf(localDate);
			String query = "update m_inout set customer_id = ?, document_date = ? where document_no = ? ";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, customerId);
			Timestamp timestamp = new Timestamp(sqlDate.getTime());
			pst.setTimestamp(2,timestamp);
			pst.setString(3, documentNo);
			
			pst.executeUpdate();
			
			System.out.println("updated");
			connection.setAutoCommit(true);
		}
	
	}
	

	public  void updationInoutLine(InoutLineDAO inoutlinedao) throws Exception {
		try(Connection connection = DBConnection.getConnection()) {
			connection.setAutoCommit(false);

			Scanner sc = new Scanner(System.in);
			String sql = "select document_no from m_inout;";
			PreparedStatement pst2 = connection.prepareStatement(sql);
			ResultSet rst2 = pst2.executeQuery();
			while(rst2.next()) {
				System.out.println(rst2.getString("document_no"));
			}
			System.out.println("select the documentNo : ");
			String documentNo = sc.next();
			
			String Id = getInoutId(documentNo);	
			
			String query = "select inoutline_id from m_inoutline where inout_id = ?;";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1,Id);
			ResultSet rst = pst.executeQuery();
			while(rst.next()) {
				System.out.println(rst.getString("inoutline_id"));
			}
			System.out.println("select the inoutlineid : ");
			String inoutlineid = sc.next();
			getProductDetail();
			System.out.println("select the productid : ");
			String productId = sc.next();
			System.out.println("Enter the quantity: ");
			int quantity = sc.nextInt();
			String query1 = "update m_inoutline set product_id = ?, quantity = ? where inoutline_id = ? ";
			PreparedStatement pst1 = connection.prepareStatement(query1);
			
			pst1.setString(1,productId);
			pst1.setInt(2,quantity );
			pst1.setString(3, inoutlineid );
			pst1.executeUpdate();
			
			System.out.println("updated");
			connection.setAutoCommit(true);
		}
	
	}


	public  void viewInout(InoutDAO inoutdao) throws Exception {
		try(Connection connection = DBConnection.getConnection()) {
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Enter the  limit: ");
			int limit = sc.nextInt();
			System.out.println("Enter the  offset: ");
			int offset = sc.nextInt();
			
			String sql = "select * from m_inout limit ? offset ?";
			try(PreparedStatement pst = connection.prepareStatement(sql)) {
				pst.setInt(1, limit);
				pst.setInt(2, offset);
				ResultSet rst = pst.executeQuery();
				while(rst.next()) {
					 ZonedDateTime createdTimestamp = rst.getTimestamp("created").toInstant().atZone(ZoneId.of("Asia/Kolkata"));
					 ZonedDateTime updatedTimestamp = rst.getTimestamp("updated").toInstant().atZone(ZoneId.of("Asia/Kolkata"));
					 Date createdDate = Date.valueOf(createdTimestamp.toLocalDateTime().toLocalDate());
					 Date updatedDate = Date.valueOf(updatedTimestamp.toLocalDateTime().toLocalDate());
					System.out.println(rst.getString("inout_id")+"   "+rst.getString("isactive") +"   "+createdDate+"   "+rst.getString("createdby")+"   "+ updatedDate +"   "+rst.getString("updatedby")+"   "+rst.getString("document_no") +"   "+ rst.getTimestamp("document_date")+"   "+rst.getString("customer_id"));		
				}
			}	
		}
	}
	
	public  void viewInoutLine(InoutLineDAO inoutlinedao) throws SQLException {
		try(Connection connection = DBConnection.getConnection()){
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter the limit: ");
			int limit =sc.nextInt();
			System.out.println("Enter the offset");
			int offfset =sc.nextInt();
			String query="select * from m_inoutline limit ? offset ?;";
			try(PreparedStatement pst = connection.prepareStatement(query)){
				pst.setInt(1, limit);
				pst.setInt(2, offfset);
				ResultSet rst = pst.executeQuery();

				while(rst.next()) {
					 ZonedDateTime createdTimestamp = rst.getTimestamp("created").toInstant().atZone(ZoneId.of("Asia/Kolkata"));
					 ZonedDateTime updatedTimestamp = rst.getTimestamp("updated").toInstant().atZone(ZoneId.of("Asia/Kolkata"));
					                     				
					 Date createdDate = Date.valueOf(createdTimestamp.toLocalDateTime().toLocalDate());
					 Date updatedDate = Date.valueOf(updatedTimestamp.toLocalDateTime().toLocalDate());
					System.out.println(rst.getString("inoutline_id") + "   " + rst.getString("isactive") + "   " + createdDate+ "   "+rst.getString("createdby") + "   " + updatedDate + "   " + rst.getString("updatedby") + "   " + rst.getString("inout_id") + "   " + rst.getInt("quantity") + "   " + rst.getString("product_id"));
				}
			}
			
		}
	}
	public  void viewById() throws SQLException{
		try(Connection connection = DBConnection.getConnection()){
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter the document no:");
			String document_no = sc.next();
			String viewinoutid = null;
			String query="select * from m_inout where document_no = ?;";
			try(PreparedStatement pst = connection.prepareStatement(query)){
				pst.setString(1, document_no);
				ResultSet rst = pst.executeQuery();
//				if(!rst.next()) {
//					System.out.println("Value not found");
//					return;
//				}
				while(rst.next()) {
					viewinoutid = rst.getString("inout_id");
					ZonedDateTime createdTimestamp = rst.getTimestamp("created").toInstant().atZone(ZoneId.of("Asia/Kolkata"));
					 ZonedDateTime updatedTimestamp = rst.getTimestamp("updated").toInstant().atZone(ZoneId.of("Asia/Kolkata"));
					 Date createdDate = Date.valueOf(createdTimestamp.toLocalDateTime().toLocalDate());
					 Date updatedDate = Date.valueOf(updatedTimestamp.toLocalDateTime().toLocalDate());
					System.out.println(rst.getString("inout_id")+"   "+rst.getString("isactive") +"   "+ createdDate +"   "+rst.getString("createdby")+"   "+ updatedDate +"   "+rst.getString("updatedby")+"   "+rst.getString("document_no") +"   "+ rst.getTimestamp("document_date")+"   "+rst.getString("customer_id"));

				}
			
			}
			String querysql = "Select * from m_inoutline where inout_id=?;";
			try (PreparedStatement pst = connection.prepareStatement(querysql)){
				pst.setString(1,viewinoutid);
				ResultSet rst = pst.executeQuery();
				if(!rst.next()) {
					System.out.println("Value not found");
					return;
				}
				while(rst.next()) {
					ZonedDateTime createdTimestamp = rst.getTimestamp("created").toInstant().atZone(ZoneId.of("Asia/Kolkata"));
					 ZonedDateTime updatedTimestamp = rst.getTimestamp("updated").toInstant().atZone(ZoneId.of("Asia/Kolkata"));
					 Date createdDate = Date.valueOf(createdTimestamp.toLocalDateTime().toLocalDate());
					 Date updatedDate = Date.valueOf(updatedTimestamp.toLocalDateTime().toLocalDate());
					System.out.println(rst.getString("inout_lineid") + "   " + rst.getString("isactive") + "   " + createdDate + "   "+rst.getString("createdby") + "   " + updatedDate + "   " + rst.getString("updatedby") + "   " + rst.getString("inout_id") + "   " + rst.getInt("quantity") + "   " + rst.getString("product_id"));
				}
			
			}
			
		}
	}
	public  void getCustomerDetail() throws SQLException {
		HashMap<String,String> customerList=new HashMap();
		Connection connection = DBConnection.getConnection();
		String query="Select customer_id,name from customer ;";
		PreparedStatement pst = connection.prepareStatement(query);
		ResultSet rst= pst.executeQuery();
		while(rst.next()) {
			customerList.put(rst.getString("customer_id"),rst.getString("name"));	
		}
		System.out.println(customerList);
	}
	public  void getProductDetail() throws SQLException {
		HashMap<String,String> productList=new HashMap();
		Connection connection = DBConnection.getConnection();
		String query="Select product_id, name from product ;";
		PreparedStatement pst = connection.prepareStatement(query);
		ResultSet rst= pst.executeQuery();
		while(rst.next()) {
			productList.put(rst.getString("product_id"),rst.getString("name"));	
		}
		System.out.println(productList);
	}
	
	public  String getInoutId(String document_no) throws SQLException {
		Connection connection = DBConnection.getConnection();
		String query="Select inout_id from m_inout where document_no=?;";
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setString(1, document_no);
		ResultSet rst = pst.executeQuery();
		String inoutId = null; 
		while(rst.next()) {
			 inoutId = rst.getString("inout_id");	
		}
		//System.out.println(inoutList);
		return inoutId;
	}
	
	
	

}



/*public void insertValues(String args[]) throws SQLException{
	String query ="insert into inout (inout_id, isactive, created, createdby, updated, updatedby, document_id, document_date, customer_id) values(?,?,?,?,?,?,?,?,?)";
	Connection con= DriverManager.getConnection(url, username, password);
	PreparedStatement = con.prepareStatement(query)) {
		
	}
}*/
