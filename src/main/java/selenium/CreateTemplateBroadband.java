package selenium;

import java.sql.SQLException;

public class CreateTemplateBroadband extends DataSetTemplate_Factory {

	public CreateTemplateBroadband() throws SQLException {
		EtlSuccess("Broadband_STAGE");
		SomaSuccess("Broadband_STAGE");
	}

}
