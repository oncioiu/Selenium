package selenium;

import java.sql.SQLException;

public class CreateTemplateBaseband extends DataSetTemplate_Factory {

	public CreateTemplateBaseband() throws SQLException {
		EtlSuccess("baseband_stage");
		SomaSuccess("baseband_stage");
	}
}
