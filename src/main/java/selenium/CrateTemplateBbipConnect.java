package selenium;

import java.sql.SQLException;



public  class CrateTemplateBbipConnect extends DataSetTemplate_Factory  {
	
	public CrateTemplateBbipConnect() throws SQLException{
		EtlSuccess("bbip_Connect_stage");
		harnessSuccess("bbip_Connect_stage");
	}
    	
}
