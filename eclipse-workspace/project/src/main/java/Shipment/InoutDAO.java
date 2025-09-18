package Shipment;
import java.util.*;
import java.sql.*;

public class InoutDAO {
	private static final long serialVersionUID = 1L;
	private final static String url="jdbc:postgresql://localhost:5432/supermarketdb";
	private final static String username="postgres";
	private final static String password="gobi";
	
	
	public List<InoutVO> main(int page, int RecordsPerPage) throws ClassNotFoundException, SQLException {
		List<InoutVO> inoutvo = new ArrayList<>();
		Class.forName("org.postgresql.Driver");
		try(Connection connection = DriverManager.getConnection(url,username, password)){
			String query = "select inout_id,document_no, document_date, customer_id from m_inout limit ? offset ?";
			PreparedStatement pst = connection.prepareStatement(query);
			
			pst.setInt(1, RecordsPerPage);
			pst.setInt(2, page);
			ResultSet rst = pst.executeQuery();
			while(rst.next()) {
				InoutVO inout = new InoutVO();
				inout.setInoutId(rst.getString("inout_id"));
				inout.setDocumentNo(rst.getString("document_no"));
				inout.setDocumentDate(rst.getDate("document_date"));
				inout.setCustomerId(rst.getString("customer_id"));
				inoutvo.add(inout);
			}
			
		}
		return inoutvo;
	}



public int getInoutCount() throws ClassNotFoundException, SQLException {
    Class.forName("org.postgresql.Driver");
    try (Connection con = DriverManager.getConnection(url, username, password)) {
        String query = "SELECT COUNT(*) FROM m_inout";
        try (PreparedStatement statement = con.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
    }
    return 0;
}


public List<InoutLineVO> getInoutLineById(String inoutId) throws ClassNotFoundException, SQLException {
    List<InoutLineVO> inoutlinevo = new ArrayList<>();
    Class.forName("org.postgresql.Driver");
    try (Connection con = DriverManager.getConnection(url, username, password)) {
		String query = "select  inout_id, product_id, quantity from m_inoutline where inout_id=?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
//            int offset = (page - 1) * RecordsPerPage;
            statement.setString(1, inoutId);
//            statement.setInt(2, RecordsPerPage);
//            statement.setInt(3, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    InoutLineVO inoutLine = new InoutLineVO();
                    inoutLine.setInoutId(resultSet.getString("inout_id"));
                    inoutLine.setProductId(resultSet.getString("product_id"));
                    inoutLine.setQuantity(resultSet.getInt("quantity"));
                    inoutlinevo.add(inoutLine);
                }
            }
        }
    }
    return inoutlinevo;
}
}