package selenium;

import java.sql.SQLException;


public  class CrateTemplateFds extends DataSetTemplate_Factory  {
	public CrateTemplateFds() throws SQLException{
		EtlSuccess("fds_stage");
		SomaSuccess("fds_stage");
	}
}
