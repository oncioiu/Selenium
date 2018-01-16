package selenium;


import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;

public class Factory_ELT_Soma {

	public  String etlresult = null;
    public  String somaResult = null;
    public  String dataSet =null;
   
	

	public void readPropFile() {
		
		
		try {

			Properties properties = new Properties();
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("table.properties"));
			
			dataSet = properties.getProperty("dataSet");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
			//return null;
		}
		
		
		
	}
	
	
	

	public String checkingEtl() throws SQLException {
		readPropFile();
		switch (dataSet) {


		case "hsns":
			DataSetTemplate_Factory hsns = new CrateTemplateHsns();
			etlresult = hsns.EtlSuccess("HSNS_STAGE");
			break;
			
		case "fds":
			DataSetTemplate_Factory fds = new CrateTemplateFds();
			etlresult = fds.EtlSuccess("fds_stage");
			break;
			
		case "baseband":
			DataSetTemplate_Factory baseband = new CreateTemplateBaseband();
			etlresult = baseband.EtlSuccess("baseband_stage");
			break;
			
		case "bbipConnect":
			DataSetTemplate_Factory bbipConnect = new CrateTemplateBbipConnect();
			etlresult = bbipConnect.EtlSuccess("bbip_Connect_stage");
			break;
			
		case "bbipDisconnect":
			DataSetTemplate_Factory bbipDisconnect = new CreateTemplateBbipDisconnect();
			etlresult = bbipDisconnect.EtlSuccess("bbip_Disconnect_stage");
			break;
			
		case "eubaConnect":
			DataSetTemplate_Factory eubaConnect = new CreateTemplateEubaConnect();
			etlresult = eubaConnect.EtlSuccess("euba_Connect_Stage");
			break;
			
		case "eubaDisconnect":
			DataSetTemplate_Factory eubaDisconnect = new CreateTemplateEubaDisconnect();
			etlresult = eubaDisconnect.EtlSuccess("euba_disconnect_stage");
			break;
			
		case "broadband":
			DataSetTemplate_Factory broadband = new CreateTemplateBroadband();
			etlresult = broadband.EtlSuccess("Broadband_STAGE");
			break;
			
		case "buba":
			DataSetTemplate_Factory buba = new CreateTemplateBuba();
			etlresult = buba.EtlSuccess("Buba_STAGE");
			break;
			
		case "locale":
			DataSetTemplate_Factory locale = new CreateTemplateLocale();
			etlresult = locale.EtlSuccess("locale_STAGE");
			break;
		
		case "ldbs":
			DataSetTemplate_Factory ldbs = new CreateTemplateLdbs();
			etlresult = ldbs.EtlSuccess("ldbs_STAGE");
			break;
		
		}
		System.out.println(etlresult);
		return etlresult;

	}
	
	public String checkingSoma() throws SQLException {
		readPropFile();
		switch(dataSet){
		
		case "hsns":
			DataSetTemplate_Factory hsns = new CrateTemplateHsns();
			somaResult = hsns.SomaSuccess("HSNS_STAGE");
			break;
			
		case "fds":
			DataSetTemplate_Factory fds = new CrateTemplateFds();
			somaResult = fds.SomaSuccess("fds_stage");
			break;
			
		case "baseband":
			DataSetTemplate_Factory baseband = new CreateTemplateBaseband();
			somaResult = baseband.SomaSuccess("baseband_stage");
			break;
			
		case "bbipConnect":
			DataSetTemplate_Factory bbipConnect = new CrateTemplateBbipConnect();
			somaResult = bbipConnect.SomaSuccess("bbip_Connect_stage");
			break;
			
		case "bbipDisconnect":
			DataSetTemplate_Factory bbipDisconnect = new CreateTemplateBbipDisconnect();
			somaResult = bbipDisconnect.SomaSuccess("bbip_Disconnect_stage");
			break;
			
		case "eubaConnect":
			DataSetTemplate_Factory eubaConnect = new CreateTemplateEubaConnect();
			somaResult = eubaConnect.SomaSuccess("euba_Connect_Stage");
			break;
			
		case "eubaDisconnect":
			DataSetTemplate_Factory eubaDisconnect = new CreateTemplateEubaDisconnect();
			somaResult = eubaDisconnect.SomaSuccess("euba_disconnect_stage");
			break;
			
		case "buba":
			DataSetTemplate_Factory buba = new CreateTemplateBuba();
			somaResult = buba.SomaSuccess("Buba_STAGE");
			break;
			
		case "ldbs":
			DataSetTemplate_Factory ldbs = new CreateTemplateLdbs();
			somaResult = ldbs.SomaSuccess("ldbs_STAGE");
			break;
			
		}
		System.out.println(somaResult);

		return somaResult ;

	}

	
	
	

}
