package Test.code;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJDBC {

	public static void main(String[] args) {
		String jdbcUrl = "jdbc:mysql://localhost/ArkivIT?"
				+ "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; 
		String user = "root";
		String password = ""; 
		try 
		{
			System.out.println("Connetcing to database.."  + jdbcUrl);
			Connection myCon = DriverManager.getConnection(jdbcUrl, user, password);
			System.out.println("Connection is sucessfull!");
		}

		catch(Exception e) 
		{
			e.printStackTrace();
		}

	}

}
