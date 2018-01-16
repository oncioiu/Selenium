package selenium;


import java.sql.SQLException;


public class CreateTemplateLdbs extends DataSetTemplate_Factory {

	public CreateTemplateLdbs() throws SQLException {
		EtlSuccess("ldbs_STAGE");
		SomaSuccess("ldbs_STAGE");
	}
}
