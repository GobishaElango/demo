package com.qualiantech.goodsshipment;
import java.sql.*;

public class DBConnection {
	private final static String url="jdbc:postgresql://localhost:5432/supermarketdb?autoCommit=false";
	private final static String username="postgres";
	private final static String password="qualian";
//	private static Connection con;
	
public static Connection getConnection() throws SQLException {
	return  DriverManager.getConnection(url,username,password);
	
}
}
