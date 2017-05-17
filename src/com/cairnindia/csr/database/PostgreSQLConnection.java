package com.cairnindia.csr.database;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Joey
 *
 */
public class PostgreSQLConnection {
	static Connection conn ;
	final static String username="cairn";
	final static String password="cairn_2013";
	public static Connection getConnection() throws SQLException{

		if(conn==null||conn.isClosed()){
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn = DriverManager
					.getConnection("jdbc:postgresql://139.59.1.193:3389/cairn_csr",
							username, password);
		}
		return conn;
	}
}
