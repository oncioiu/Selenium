package selenium;

import java.sql.SQLException;

public class CreateTemplateBbipDisconnect extends DataSetTemplate_Factory {

	public CreateTemplateBbipDisconnect() throws SQLException {
		EtlSuccess("bbip_Disconnect_stage");
		harnessSuccess("bbip_Disconnect_stage");
	}

}
