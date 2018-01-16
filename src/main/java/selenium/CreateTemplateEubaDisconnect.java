package selenium;


import java.sql.SQLException;



public  class CreateTemplateEubaDisconnect extends DataSetTemplate_Factory  {
	
	public CreateTemplateEubaDisconnect() throws SQLException{
		EtlSuccess("euba_disconnect_stage");
		harnessSuccess("euba_disconnect_stage");
	}
   }
