package selenium;


public class Main {

	public static String script = null;

	public static void main(String[] args) {

		ChooseMethods methods = new ChooseMethods();

		methods.serverConnectionProxy();// Connect to server
		/*methods.copyFiletoServer();
		methods.performeETL();

		if (methods.scriptType().equals("soma")) {
			methods.performeSOMA();
			methods.downloadSomalogfile();
			methods.executeExport();
			methods.downloadExportFile();
			System.out.println("Soma");
		}

		if (methods.scriptType().equals("harness")) {
			methods.executeHarness();
			methods.downloadHarnesslogfile();
			methods.executeExport();
			methods.downloadExportFile();
			System.out.println("Harness");
		}*/
		
		

	}

}
