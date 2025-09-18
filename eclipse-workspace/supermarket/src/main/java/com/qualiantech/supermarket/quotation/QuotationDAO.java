package com.qualiantech.supermarket.quotation;

import java.sql.*;
import java.sql.Date;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;



public class QuotationDAO {
	public Connection openConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		return DriverManager.getConnection("jdbc:postgresql://localhost:5432/supermarket", 
				"postgres", "qualian");	
	}
	//view
	public static String formatDate(String dateString) {
		LocalDate date = LocalDate.parse(dateString);
		return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));	
	}
	
	//for pagination
	public int getQuotationsCount() {
		int rowCount=0;
		try(Connection connection=openConnection();
			Statement stmt=connection.createStatement();
		){
			ResultSet rs=stmt.executeQuery("select count(*) from quotation");
            if(rs.next()) {
            	rowCount=rs.getInt(1);
            }
		
	    }
		catch(Exception e) {
			e.printStackTrace();
		}
		return rowCount;	
	}
	
	public String generateDocumentNo() {
		String documentNo="QTN00";
		try(Connection connection= openConnection();
			Statement st=connection.createStatement();
		){
			ResultSet rs=st.executeQuery("select Max(documentno) from quotation");
			if(rs.next()) {
				String quoteId=rs.getString(1);
				if(quoteId==null) {
					documentNo+=1;
				}
				else {
					int num=Integer.parseInt(quoteId.substring(4));
					++num;
					if(num>9) {
						documentNo="QTN0"+num;
					}
					else {
						documentNo+=num;
					}
				}
				
			}	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return documentNo;
	}

	public String getVendorIdByName(String vendorName) {
    	String vendorId=null;
    	try(Connection connection= openConnection();
    		PreparedStatement pstmt1 =connection.prepareStatement("select vendor_id from vendor where name=?");
    	){
			pstmt1.setString(1, vendorName);
            ResultSet rs=pstmt1.executeQuery();
            if(rs.next()) {
              	vendorId=rs.getString(1); 
            }
    		
    	}
    	catch(Exception e) {
			e.printStackTrace();
		}
    	return vendorId;
    }
	
	public String[] getProductIdUOMByName(String productName) {
    	String productId=null;
    	String uom=null;
    	try(Connection connection=openConnection();
		    PreparedStatement pstmt =connection.prepareStatement("select product_id,uom from product where name=?");){
		    pstmt.setString(1, productName);
		    ResultSet rs=pstmt.executeQuery();
		    if(rs.next()) {
		    	productId=rs.getString(1);
			    uom=rs.getString(2);
		    }   
	    }
	    catch(Exception e) {
			e.printStackTrace();
		}
    	return new String[]{productId,uom};
    }
	
	public ArrayList<QuotationVO> getAllQuotations(int limit,int offset){
		ArrayList<QuotationVO> quotations=new ArrayList<>();
		try(Connection connection=openConnection();
			PreparedStatement pstmt=connection.prepareStatement("select q.documentno,v.name,q.documentdate,q.validTill from quotation as q "
					+ "inner join vendor as v on q.vendor_id=v.vendor_id order by q.documentno desc limit ? offset ?");
		){
			pstmt.setInt(1, limit);
			pstmt.setInt(2, offset);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				ArrayList<QuotationLineVO> lineItems=new ArrayList<>();
				String documentNo = rs.getString(1);
				PreparedStatement pstmt2=connection.prepareStatement("select p.name, ql.price, ql.quantity, p.uom, ql.linenetamt from quotationline as ql "
						+ "inner join quotation as q on  ql.quotation_id=q.quotation_id "
						+ "inner join product as p on  ql.product_id=p.product_id where q.documentno=?");
				pstmt2.setString(1,documentNo);
				ResultSet rs2=pstmt2.executeQuery();
		        while (rs2.next()) {
		        	lineItems.add(new QuotationLineVO(rs2.getString(1),rs2.getDouble(2) ,rs2.getInt(3),rs2.getString(4),rs2.getDouble(5)));	    
		        }
				quotations.add(new QuotationVO(documentNo,rs.getString(2),""+rs.getDate(3),""+rs.getDate(4), lineItems));			

			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return quotations;
    }
	
	public QuotationVO getQuotationById(String quotationId) {
		QuotationVO quotation=null;
		try(Connection connection=openConnection();
			PreparedStatement pstmt=connection.prepareStatement("select q.documentno,v.name,q.documentdate,q.validTill from quotation as q join vendor as v on q.vendor_id=v.vendor_id where q.documentno=?");
		){
			pstmt.setString(1, quotationId);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				ArrayList<QuotationLineVO> lineItems=new ArrayList<>();
				String documentNo = rs.getString(1);
				PreparedStatement pstmt2=connection.prepareStatement("select p.name, ql.price, ql.quantity, p.uom, ql.linenetamt from quotationline as ql "
						+ "inner join quotation as q on  ql.quotation_id=q.quotation_id "
						+ "inner join product as p on  ql.product_id=p.product_id where q.documentno=?");
		        pstmt2.setString(1,documentNo);
				ResultSet rs2=pstmt2.executeQuery();
		        while (rs2.next()) {
		            lineItems.add(new QuotationLineVO(rs2.getString(1),rs2.getDouble(2) ,rs2.getInt(3),rs2.getString(4),rs2.getDouble(5)));    
		        }
				quotation=new QuotationVO(rs.getString(1),rs.getString(2),""+rs.getDate(3),""+rs.getDate(4),lineItems);		

			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return quotation;
		
	}
	
	public int insertQuotationLines(ArrayList<QuotationLineVO>lineItems, String quotationId) {
		int insertedRowCount=0;
		String query = "INSERT INTO quotationline (createdby,updatedby,quotation_id,product_id,uom,quantity,price,linenetamt)"
				+ "VALUES (?,?,?,?,?,?,?,?)";
		try(Connection connection=openConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);){	
			for(QuotationLineVO lineItem:lineItems) {
			
			String[]productUOM=getProductIdUOMByName(lineItem.getProductName());
			
			pstmt.setString(1, "1");
			pstmt.setString(2, "1");
			pstmt.setString(3, quotationId);
			pstmt.setString(4, productUOM[0]);
			pstmt.setString(5, productUOM[1]);
			pstmt.setInt(6, lineItem.getQuantity());
			pstmt.setDouble(7,lineItem.getPrice());
			pstmt.setDouble(8,lineItem.getLineNetAmt());
			pstmt.executeUpdate(); 
			insertedRowCount++;
           }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return insertedRowCount;
		
	}
	//Add Quotation & QuotationLine
	public int insertQuotation(QuotationVO quotation) {
		String quotationId=null;
		int insertedRowCount=0;
		try(Connection connection= openConnection();
            PreparedStatement pstmt1 = connection.prepareStatement("insert into quotation(createdby,updatedby, vendor_id, documentno,documentdate,validtill) values(?,?,?,?,?,?)");
		){
            pstmt1.setString(1,"1");
            pstmt1.setString(2,"1");
            pstmt1.setString(3,getVendorIdByName(quotation.getVendorName()));
            pstmt1.setString(4,quotation.getDocumentNo());
            pstmt1.setDate(5, Date.valueOf(quotation.getDate()));
            pstmt1.setDate(6, Date.valueOf(quotation.getValidTill()));
            pstmt1.executeUpdate();
            PreparedStatement pstmt2 =connection.prepareStatement("select quotation_id from quotation where documentno=?");
			pstmt2.setString(1,quotation.getDocumentNo() );
            ResultSet rs1=pstmt2.executeQuery();
            if(rs1.next()) {
            	quotationId=rs1.getString(1); 
            }
            insertedRowCount=insertQuotationLines(quotation.getLineItems(),quotationId);
            
		}  
        catch (Exception e) {
            e.printStackTrace();
        }
		return insertedRowCount;
		
		
		
	}
	//update Quotation, delete and insert lineItem
	public int updateQuotation(QuotationVO quotation){
		String quotationId=null;
		int insertedRowCount=0;
		try(Connection connection= openConnection();
            PreparedStatement pstmt=connection.prepareStatement("update quotation set vendor_id=?, documentdate=?, validTill=? where documentno=?");
		){
            pstmt.setString(1,getVendorIdByName(quotation.getVendorName()));
            pstmt.setDate(2, Date.valueOf(quotation.getDate()));
            pstmt.setDate(3, Date.valueOf(quotation.getValidTill()));
            pstmt.setString(4, quotation.getDocumentNo());
            pstmt.executeUpdate(); 
            PreparedStatement pstmt2 =connection.prepareStatement("select quotation_id from quotation where documentno=?");
			pstmt2.setString(1,quotation.getDocumentNo() );
            ResultSet rs1=pstmt2.executeQuery();
            if(rs1.next()) {
            	quotationId=rs1.getString(1); 
            }
            PreparedStatement pstmt3 = connection.prepareStatement("delete from quotationline where quotation_id=?");
            pstmt3.setString(1,quotationId);
    		pstmt3.executeUpdate();
    		insertedRowCount=insertQuotationLines(quotation.getLineItems(),quotationId);
    	
		}
		catch(Exception e) {
			 e.printStackTrace();
		}
		return insertedRowCount; 	
	}
	
	public int deleteQuotation(String[] quotationIds) {
		int deletedRowCount=0;
		try(Connection connection= openConnection();
			PreparedStatement pstmt1=connection.prepareStatement("delete from quotationline USING quotation where quotationline.quotation_id = quotation.quotation_id AND quotation.documentno = ?");
			PreparedStatement pstmt2=connection.prepareStatement("delete from quotation where documentno=?");
		){
			for(String documentno:quotationIds) {
				 pstmt1.setString(1, documentno);
				 pstmt1.executeUpdate();
				 pstmt2.setString(1, documentno);
				 pstmt2.executeUpdate();
				 deletedRowCount++;
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return deletedRowCount;
		
	}

}
