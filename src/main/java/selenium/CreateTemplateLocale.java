package selenium;


import java.sql.SQLException;



public  class CreateTemplateLocale extends DataSetTemplate_Factory  {
	
	public CreateTemplateLocale() throws SQLException{
		EtlSuccess("locale_STAGE");
		SomaSuccess("locale_STAGE");
	}
   }
