package selenium;


import java.sql.SQLException;


public class CreateTemplateEubaConnect extends DataSetTemplate_Factory {

	public CreateTemplateEubaConnect() throws SQLException {
		EtlSuccess("euba_Connect_Stage");
		harnessSuccess("euba_Connect_Stage");
	}
}
