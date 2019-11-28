package DataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBActions {
	 Statement stmt = null;
	 ResultSet rs = null;

	public ResultSet  executeQuery(Connection con,String query) throws IOException, SQLException {

			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			return rs;

	}

	/*public static void main(String[] args) throws IOException {
		DBConnection.createConnection();
		executeQuery("Select * from subscribers where rownum < 4");
	}*/
}
