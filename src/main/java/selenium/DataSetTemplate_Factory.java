package selenium;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public  class DataSetTemplate_Factory {
	
	public static Connection conn = null;
	public static ResultSet rs;
	public static String etlresult = null;
    public static String somaResult = null;
    public static String harnessResult = null;
    public static String sql=null;
    
	
    
    ConnectToDatabase databaseConnection = new ConnectToDatabase();
    

	
	public String EtlSuccess(String Name_OF_staging_table) throws SQLException {
		conn=  databaseConnection.dbConnection();
	    sql = "select * from "+Name_OF_staging_table;
	    System.out.println(sql);
		Statement s = conn.createStatement();
		rs = s.executeQuery(sql);
		while(rs.next()) {

			String Validation_result = rs.getString("validation_result");
			if (Validation_result.contains("SUCCESS")) {
				etlresult = "SUCCESS";

			}else if (rs.getString("validation_result") == null) {
				etlresult = "NULL";
				
			}else {
				etlresult = "FAILURE";
			}
		}
		return etlresult;
	}
	
	public String SomaSuccess(String Name_OF_staging_table) throws SQLException {
		conn=databaseConnection.dbConnection();
		sql  = "select * from "+Name_OF_staging_table;
		Statement s = conn.createStatement();
	    rs = s.executeQuery(sql);
		while (rs.next()) {

			String Validation_result = rs.getString("migration_desc");
			if (Validation_result.contains("SUCCESS")) {
				somaResult = "Success";

			}else if (rs.getString("migration_desc") == null) {
				somaResult = "NULL";
				
			}else {
				somaResult = "FAILURE";
			}
			
		}
		System.out.println(etlresult);
		return somaResult;
	}

	

	
	public String harnessSuccess(String Name_OF_staging_table) throws SQLException {
		conn=databaseConnection.dbConnection();
		sql  = "select * from "+Name_OF_staging_table;
		System.out.println(sql);
		Statement s = conn.createStatement();
	    rs = s.executeQuery(sql);
		while (rs.next()) {

			String Validation_result = rs.getString("validation_result");
			if (Validation_result.contains("SUCCESS")) {
				harnessResult = "Success";

			}else if (rs.getString("validation_result") == null) {
				harnessResult = "NULL";
				
			}else {
				harnessResult = "FAILURE";
			}
			
		}
		System.out.println(etlresult);
		return somaResult;
	}

}
