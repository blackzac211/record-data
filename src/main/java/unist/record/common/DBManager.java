package unist.record.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class DBManager {
	private Connection conn;
	
	public DBManager() {
		try {
			// Class.forName("oracle.jdbc.OracleDriver");
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://20.0.4.26:3306/ipxvr", "fullrec", "Un!s7i!fullrec");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public PreparedStatement getPreparedStatement(String sql) throws Exception {
		return conn.prepareStatement(sql);
	}
}
