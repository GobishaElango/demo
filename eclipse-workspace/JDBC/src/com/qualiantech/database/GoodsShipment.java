package com.qualiantech.database;

import java.sql.*;
import 
public class GoodsShipment {

	public static void main(String args[]) {
		Connection con=null;
		try {
			con=DBConnection.getConnection();
		} catch (SQLException e)
		{
			
			e.printStackTrace();
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			System.out.println("Connected");
		}
	}



/*public void insertValues(String args[]) throws SQLException{
	String query ="insert into inout (inout_id, isactive, created, createdby, updated, updatedby, document_id, document_date, customer_id) values(?,?,?,?,?,?,?,?,?)";
	Connection con= DriverManager.getConnection(url, username, password);
	PreparedStatement = con.prepareStatement(query)) {
		
	}
}*/
}
