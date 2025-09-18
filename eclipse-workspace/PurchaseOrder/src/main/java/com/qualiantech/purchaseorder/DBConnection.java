
package com.qualiantech.purchaseorder;

import java.sql.*;

public class DBConnection {
	private final static String url = "jdbc:postgresql://localhost:5432/Supermarket";
	private final static String username = "postgres";
	private final static String password = "qualian"; // private static Connection con;

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		return DriverManager.getConnection(url, username, password);

	}

	public static void main(String[] args) {
		try (Connection conn = getConnection()) {
			if (conn != null) {
				System.out.println("Db connected");
			} else {
				System.out.println(" Db not connected");
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(" Error: " + e.getMessage());
		}
	}
}
