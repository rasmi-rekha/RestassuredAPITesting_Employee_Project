package DataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	public Connection createConnection() throws IOException {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@//10.67.200.35:1521/adeledb",
					"AUTOTM_REL4_10MAY17", "AUTOTM_REL4_10MAY17");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;

	}

	public void closeConnection(Connection con) throws SQLException{
		con.close();
	}
}
