package selenium;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;

import javax.swing.JOptionPane;

public class ConnectToDatabase {
	public static Connection conn = null;
	public static ResultSet rs;	

	public Connection dbConnection() {

		try {

			Properties properties = new Properties();
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("table.properties"));
			String url = properties.getProperty("url");
			String driver = properties.getProperty("driver");
			String username = properties.getProperty("username");
			String password = properties.getProperty("password");

			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("You are connected to data base !");
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}
