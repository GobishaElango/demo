import java.sql.*;

public class demo {

	public static void main(String[] args) throws SQLException {
		batchDemo();
	}
	public  static void readRecords() throws SQLException{

		String url="jdbc:postgresql://localhost:5432/demo";
		String username="postgres";
		String password="gobi";
		String query = "select * from employee";
		
		
		Connection con = DriverManager.getConnection(url,username,password);
		Statement st =con.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		
		while(rs.next()) {
		System.out.println("ID is"+rs.getInt(1));
		System.out.println("Name is" + rs.getString(2));
		System.out.println("Salary is: "+rs.getInt(3));
		}
		con.close();
	}
	//Read records
	public  static void insertRecord() throws SQLException{

		String url="jdbc:postgresql://localhost:5432/demo";
		String username="postgres";
		String password="gobi";
		String query = "insert into employee values (5,'Raj',56000)";
		
		
		Connection con = DriverManager.getConnection(url,username,password);
		Statement st =con.createStatement();
		int rows = st.executeUpdate(query);
		System.out.println("No. of rows affected:" +rows);
		con.close();
	}
//insert record
public  static void insertVar() throws SQLException{

	String url="jdbc:postgresql://localhost:5432/demo";
	String username="postgres";
	String password="gobi";
	
	int id=6;
	String name="Veni";
	int salary=340000;
	
	String query = "insert into employee values ("+ id +",'"+name +"',"+salary+");";
	
	
	Connection con = DriverManager.getConnection(url,username,password);
	Statement st =con.createStatement();
	int rows = st.executeUpdate(query);
	System.out.println("No. of rows affected:" +rows);
	con.close();
}
//insert record with variable
public  static void insertUsingPst() throws SQLException{

	String url="jdbc:postgresql://localhost:5432/demo";
	String username="postgres";
	String password="gobi";
	
	int id=7;
	String name="Rini";
	int salary=39000;
	
	String query = "insert into employee values (?,?,?)";
	
	
	Connection con = DriverManager.getConnection(url,username,password);
	PreparedStatement pst=con.prepareStatement(query);
	pst.setInt(1,id);
	pst.setString(2,name);
	pst.setInt(3,salary);
	int rows = pst.executeUpdate();
	System.out.println("No. of rows affected:" +rows);
	con.close();

System.out.println("No. of rows affected:" +rows);
}

//insert record using Prepared statement
public  static void deleteRecord() throws SQLException{

	String url="jdbc:postgresql://localhost:5432/demo";
	String username="postgres";
	String password="gobi";
	
	int id=5;
	
	String query = "delete from employee where emp_id=5";
	
	
	Connection con = DriverManager.getConnection(url,username,password);
	Statement st=con.createStatement();
	int rows =st.executeUpdate(query);
	/*while(rs.next()) {
		System.out.println("Id is"+rs.getInt(1));
		System.out.println("Name is"+rs.getString(2));
		System.out.println("Salary is"+rs.getInt(3));
	}*/
	
	
	System.out.println("No. of rows affected:"+rows);
	con.close();

}
//delete record
public  static void deleteUsingPst() throws SQLException{

	String url="jdbc:postgresql://localhost:5432/demo";
	String username="postgres";
	String password="gobi";
	
	int id=1;
//	String name="Ram";
	//int salary=50000;
	
	String query = "delete from employee where emp_id=?";
	
	
	Connection con = DriverManager.getConnection(url,username,password);
	PreparedStatement pst=con.prepareStatement(query);
	pst.setInt(1,id);
	//pst.setString(2,name);
	//pst.setInt(3,salary);
	int rows = pst.executeUpdate();
	System.out.println("No. of rows affected:" +rows);
	con.close();

System.out.println("No. of rows affected:" +rows);
}
//delete using prepared statement
public  static void updateRecord() throws SQLException{

	String url="jdbc:postgresql://localhost:5432/demo";
	String username="postgres";
	String password="gobi";
	
	int id=1;
	
	String query = "update employee set salary=50000 where emp_id=1";
	
	
	Connection con = DriverManager.getConnection(url,username,password);
	Statement st=con.createStatement();
	int rows =st.executeUpdate(query);
	/*while(rs.next()) {
		System.out.println("Id is"+rs.getInt(1));
		System.out.println("Name is"+rs.getString(2));
		System.out.println("Salary is"+rs.getInt(3));
	}*/
	
	
	System.out.println("No. of rows affected:"+rows);
	con.close();

}
//updaterecord 

public  static void updateUsingPst() throws SQLException{

	String url="jdbc:postgresql://localhost:5432/demo";
	String username="postgres";
	String password="gobi";
	
	//int id=1;
    String name="Veni";
	//int salary=50000;
	
	String query = "update employee set emp_id=5 where ename=?";
	
	
	Connection con = DriverManager.getConnection(url,username,password);
	PreparedStatement pst=con.prepareStatement(query);
	pst.setString(1,name);
	//pst.setString(2,name);
	//pst.setInt(3,salary);
	int rows = pst.executeUpdate();
	System.out.println("No. of rows affected:" +rows);
	con.close();

System.out.println("No. of rows affected:" +rows);
}
//update using prepare statement


public  static void sp() throws SQLException{

	String url="jdbc:postgresql://localhost:5432/demo";
	String username="postgres";
	String password="gobi";
	//String query = "select * from employee";
	
	
	Connection con = DriverManager.getConnection(url,username,password);
	CallableStatement cst=con.prepareCall("{call GetEmp()}");
	ResultSet rs = cst.executeQuery();
	while(rs.next()) {
		System.out.println("Id is "+ rs.getInt(1));
		System.out.println("Name is "+ rs.getString(2));
		System.out.println("Salary is"+rs.getInt(3));
	}
	
	con.close();
}

//calling stored procedure with input parameter


public  static void sP2() throws SQLException{

	String url="jdbc:postgresql://localhost:5432/demo";
	String username="postgres";
	String password="gobi";
	//String query = "select * from employee";
	
	int id=3;
	Connection con = DriverManager.getConnection(url,username,password);
	CallableStatement cst=con.prepareCall("{call GetEmpById(?)}");
	cst.setInt(1,id);
	ResultSet rs = cst.executeQuery();
	while(rs.next()) {
		System.out.println("Id is "+ rs.getInt(1));
		System.out.println("Name is "+ rs.getString(2));
		System.out.println("Salary is"+rs.getInt(3));
	}
	
	con.close();
}


//calling stored procedure with out parameter
public  static void sP3() throws SQLException{

	String url="jdbc:postgresql://localhost:5432/demo";
	String username="postgres";
	String password="gobi";
	//String query = "select * from employee";
	
	int id=3;
	
	Connection con = DriverManager.getConnection(url,username,password);
	CallableStatement cst=con.prepareCall("{call GetNameById(?,?)}");
	cst.setInt(1,id);
	cst.registerOutParameter(2, Types.VARCHAR);
	cst.executeUpdate();
	System.out.println(cst.getString(2));

	con.close();
}

//commit vs autocommit

public  static void commitDemo() throws SQLException{

	String url="jdbc:postgresql://localhost:5432/demo";
	String username="postgres";
	String password="gobi";
	String query1 = "update employee set salary=55000 where emp_id=2";
	String query2 = "update employee set salary=55000 where emp_id=3";
	
	
	Connection con = DriverManager.getConnection(url,username,password);
	con.setAutoCommit(false);
	Statement st = con.createStatement();
	int rows1=st.executeUpdate(query1);
	
	System.out.println("No.of rows affected:"+rows1);
	
	int rows2=st.executeUpdate(query2);
	System.out.println("No.of rows affected:"+rows2);
	if(rows1>0 && rows2>0)
		con.commit();
	
	con.close();
}

//batch processing 

public  static void batchDemo() throws SQLException{

	String url="jdbc:postgresql://localhost:5432/demo";
	String username="postgres";
	String password="gobi";
	String query1 = "update employee set salary=50000 where emp_id=2";
	String query2 = "update employee set salary=50000 where emp_id=3";
	String query3 = "update employee set salary=300000 where emp_id=4";
	String query4 = "update employee set salary=550000 where emp_id=5";
	
	
	Connection con = DriverManager.getConnection(url,username,password);
	con.setAutoCommit(false);
	Statement st =con.createStatement();
	st.addBatch(query1);
	st.addBatch(query2);
	st.addBatch(query3);
	st.addBatch(query4);
	
	int[] res = st.executeBatch();
	
	for (int i: res) {
		if(i>0)
			continue;
		else
			con.rollback();
	}
	con.commit();
	
	con.close();
}

}

