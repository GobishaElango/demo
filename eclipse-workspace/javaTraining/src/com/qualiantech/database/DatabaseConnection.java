package com.qualiantech.database;
import java.sql.*;
public class DatabaseConnection {

	public DatabaseConnection() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con=null;
		try {jdbc:postgresql://localhost:5432/mydb?autoCommit=false

			con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/productdb", "postgres", "gobi");
			Statement sl = con.createStatement();
						ResultSet rs = sl.executeQuery("select * from bpartner");
						while(rs.next()) {
							System.out.println(rs.getString(1));
						}
						
						
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						try {
							con.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
			 
				}
			 
			}
		
			 
			/*PreparedStatement st=con.prepareStatement("select*from product where productCategory_Id=?");
			st=con.prepareStatement("select*from product where productCategory_Id=?");
			st.setString(1, "pctg1");
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString("name"));
				
			}
		}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			System.out.println("connected");
	
		}*/
		//catch(Exception e) {
			//System.out.println(e);
		//}
		
	


