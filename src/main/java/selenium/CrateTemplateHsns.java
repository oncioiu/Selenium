package selenium;

import java.sql.SQLException;






public  class CrateTemplateHsns extends DataSetTemplate_Factory  {
	
	public CrateTemplateHsns() throws SQLException{
		EtlSuccess("HSNS_STAGE");
		SomaSuccess("HSNS_STAGE");
	}
 }
