package selenium;


import javax.swing.*;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.ProxyHTTP;
import com.jcraft.jsch.Session;

import java.io.*;
import java.util.Collections;
import java.util.Properties;
import java.util.Vector;

public class ChooseMethods {

	public  Session session = null;
	public  Channel channel = null;
	public  ChannelSftp channelSftp = null;
	public  int time = 60;
	
	public static  String dataSet=null;
	public static  String mifName=null;
	public static  String privateKeyPath=null;
	public static  String userName=null;
	public static  String urlServer=null;
	public static  String portNumber=null;
	public static  String pathtoLocalFile=null;
	public static  String destLogfile=null;
	public static  String destExportfile=null;
	public static  String script=null;
	
	public static  String proxy = null;
	public static  String portProxy ;
	public static  String localPort = null;
	public static  String remoteHost = null;
	public static  String remotePort = null;
	
	
	Factory_ELT_Soma etlandsoma = new Factory_ELT_Soma();
	
	
	
	public void readPropFile(){
		try {

			Properties properties = new Properties();
			properties.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("table.properties"));
			 dataSet = properties.getProperty("dataSet");
			 mifName = properties.getProperty("mifName");
			 privateKeyPath = properties.getProperty("privateKeyPath");
			 userName = properties.getProperty("userName");
			 urlServer = properties.getProperty("urlServer");
			 portNumber = properties.getProperty("port");
			 pathtoLocalFile= properties.getProperty("pathtoLocalFile");
			 destLogfile= properties.getProperty("destLogfile");
			 destExportfile= properties.getProperty("destExportfile");
			 script= properties.getProperty("script");
			 
			 proxy = properties.getProperty("proxy");
			 portProxy = properties.getProperty("portProxy");
		     localPort = properties.getProperty("localPort");
			 remoteHost = properties.getProperty("remoteHost");
			 remotePort = properties.getProperty("remotePort");
			
			 
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
			
		}
		
	}
	
	
	public String scriptType(){
		return script;
	}
	
	
	public void serverConnection(){
		try {
			readPropFile();
			java.util.Properties  config  = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			String privateKey = privateKeyPath;
			jsch.addIdentity(privateKey);
			session = jsch.getSession(userName, urlServer, Integer.parseInt(portNumber));
			session.setConfig(config);
			session.connect();
			System.out.println("Session connected to the Server");
			
			
		}catch (Exception sessionException) {
			System.out.println(sessionException);
		}
	}
	
	
	public void serverConnectionProxy(){
		try {
			readPropFile();
			java.util.Properties  config  = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			String privateKey = privateKeyPath;
			jsch.addIdentity(privateKey);
			session = jsch.getSession(userName, urlServer, Integer.parseInt(portNumber));
			session.setConfig(config);
			session.setProxy(new ProxyHTTP(proxy,Integer.parseInt(portProxy)));
			session.setPortForwardingL(Integer.parseInt(localPort), remoteHost,Integer.parseInt(remotePort) );
	        session.connect();
	        System.out.println("Session connected to the Server");
			
			
		}catch (Exception sessionException) {
			System.out.println(sessionException);
		}
	}
	public void performeSOMA() {

		try {
			
			
			channel = session.openChannel("shell");
			OutputStream ops = channel.getOutputStream();
			PrintStream ps = new PrintStream(ops, true);
			
			

			channel.connect();
			String sudo = "sudo su - migration";
			String cdsoma = "cd soma";
			String runSoma = "./soma "+dataSet+" -mode Production";
			String exit = "exit";
			
			ps.println(sudo);
			ps.println(cdsoma);
			
			if (etlandsoma.checkingEtl().equals("SUCCESS")) {
				ps.println(runSoma);
			} else {
				JOptionPane.showMessageDialog(null, "All the records processed by ETL are fail!");
				ps.println(exit); //exit from migration user
				ps.println(exit); // exit from root. Close terminal
			}
			
			ps.println(exit); //exit from migration user
			ps.println(exit); // exit from root. Close terminal
			
			InputStream in = channel.getInputStream();
			
		    byte[] tmp = new byte[1024];
			while (true) {
				
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
					if(time==0){
						channel.disconnect();
					}
				}
				
				if (channel.isClosed()) {
					//System.out.println("exit-status: " + channel.getExitStatus());
					break;
				}
				try {Thread.sleep(1000);
				
				
				} catch (Exception ee) {}
				 
			}
			channel.disconnect();
			System.out.println("Soma executed ! ");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void performeETL() {

		try {
			
			channel = session.openChannel("shell");
			OutputStream ops = channel.getOutputStream();
			PrintStream ps = new PrintStream(ops, true);
			
			

			channel.connect();
			String sudo = "sudo su - migration";
			String copy = "cp /home/"+userName+"/"+mifName+".csv"+" /data/nokia/migration/etl/mif/";
			String cdetl = "cd etl";
			String runEtl = "./etl "+dataSet+" -mifFilename "+mifName+".csv";
			String exit = "exit";
			
			ps.println(sudo);
			ps.println(copy);
			ps.println(cdetl);
			ps.println(runEtl);
			ps.println(exit); //exit from migration user
			ps.println(exit); // exit from your user. Close terminal

			InputStream in = channel.getInputStream();
			
		    byte[] tmp = new byte[1024];
			while (true) {
				
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
					if(time==0){
						channel.disconnect();
					}
				}
				
				if (channel.isClosed()) {
					//System.out.println("exit-status: " + channel.getExitStatus());
					break;
				}
				try {Thread.sleep(1000);
				
				
				} catch (Exception ee) {}
				 
			}
			channel.disconnect();
			System.out.println("ETL executed");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void executeExport() {

		try {
		
			channel = session.openChannel("shell");
			OutputStream ops = channel.getOutputStream();
			PrintStream ps = new PrintStream(ops, true);
			
			

			channel.connect();
			String sudo = "sudo su - migration";
			String cdetl = "cd export";
			String runexport = "./export " + dataSet ;
			String exit = "exit";
			
			ps.println(sudo);
			ps.println(cdetl);
			ps.println(runexport);
			ps.println(exit); //exit from migration user
			ps.println(exit); // exit from your user. Close terminal

			InputStream in = channel.getInputStream();
			
		    byte[] tmp = new byte[1024];
			while (true) {
				
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
					if(time==0){
						channel.disconnect();
					}
				}
				
				if (channel.isClosed()) {
					//System.out.println("exit-status: " + channel.getExitStatus());
					break;
				}
				try {Thread.sleep(1000);
				
				
				} catch (Exception ee) {}
				 
			}
			channel.disconnect();
			System.out.println("Export file executed");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	public void copyFiletoServer() {
		try {
			readPropFile();
            channel = session.openChannel("sftp");
			channel.connect();
			System.out.println("Sftp channel connected to upload file");

			channelSftp = (ChannelSftp) channel;
			String fileName = pathtoLocalFile+mifName+".csv";
			System.out.println(fileName);
			channelSftp.put(fileName, "/home/"+userName);
			System.out.println("File  " + fileName + " copied to " + urlServer+"/home/"+userName);
		} catch (Exception e) {
			System.err.println(e);
		}
		channel.disconnect();
	}

	public void downloadSomalogfile() {
		try {
			channel = session.openChannel("sftp");
			channel.setInputStream(System.in);
			channel.setOutputStream(System.out);
			channel.connect();
			System.out.println("Channel sftp connected to download Soma Log file");

			channelSftp = (ChannelSftp) channel;
			try {
				// Setting the folder location of the external system as
				// configured
				// to download the file from
				channelSftp.cd("/data/nokia/migration/soma/log");
				System.out.println("Directory changed to ->/data/nokia/migration/soma/log");
			} catch (Exception ftpException) {
				System.out.println("Failed to change the directory in sftp.");
			}
			
			
			Vector<ChannelSftp.LsEntry> lsEntries = channelSftp.ls(mifName+"*");
			 Collections.sort(lsEntries);
            // Validating if files exist to process the request further
            if (lsEntries.isEmpty()) {
                System.out.println("No file exist in the specified sftp folder location.");
            }
            // Iterating the list of entries to download the file(s) from the sftp 
           
                try {
                	
                    // Downloading the specified file from the sftp to the specified folder path
                    channelSftp.get(lsEntries.lastElement().getFilename(), destLogfile);
                    System.out.println(lsEntries.lastElement().getFilename()); 
                    System.out.println("Soma log file downloaded.");  
                } catch (Exception sftpException) {
                    System.out.println("Failed to download the file the sftp folder location.");                    
                }  
			
		} catch (Exception e) {
			System.err.println(e);
		}
		
		channel.disconnect();	
	}
	
	
	public void downloadExportFile() {
		try {
			
			channel = session.openChannel("sftp");
			channel.connect();
			System.out.println("SFTP channel connected to download export file");

			channelSftp = (ChannelSftp) channel;
			try {
				// Setting the folder location of the external system as
				// configured
				// to download the file from
				channelSftp.cd("/data/nokia/migration/export/output");
				System.out.println("Directory changed to -> /data/nokia/migration/export/output");
			} catch (Exception  sftpException) {
				System.out.println("Failed to change the directory in sftp.");
			}
			
			
			Vector<ChannelSftp.LsEntry> lsEntries = channelSftp.ls(mifName+"*");
			 Collections.sort(lsEntries);
            // Validating if files exist to process the request further
            if (lsEntries.isEmpty()) {
                System.out.println("No file exist in the specified sftp folder location.");
            }
            // Iterating the list of entries to download the file(s) from the sftp 
           
                try {
                	
                    // Downloading the specified file from the sftp to the specified folder path
                    channelSftp.get(lsEntries.lastElement().getFilename(), destExportfile);
                    System.out.println(lsEntries.lastElement().getFilename()); 
                    System.out.println("Export File downloaded.");  
                } catch (Exception sftpException) {
                    System.out.println("Failed to download the file the sftp folder location.");                    
                }  
			
		} catch (Exception e) {
			System.err.println(e);
		}
		
		channel.disconnect();
		session.disconnect();
		
 
		
	}
	
	
	
	public void executeHarness() {

		try {
			readPropFile();
			
			channel = session.openChannel("shell");
			OutputStream ops = channel.getOutputStream();
			PrintStream ps = new PrintStream(ops, true);
			
			

			channel.connect();
			String sudo = "sudo su - migration";
			String cdsoma = "cd harness";
			String runHarness = "./harness "+dataSet;
			String exit = "exit";
			
			ps.println(sudo);
			ps.println(cdsoma);
			
			if (etlandsoma.checkingEtl().equals("SUCCESS")) {
				ps.println(runHarness);
				
				
				
			} else {
				JOptionPane.showMessageDialog(null, "All the records processed by ETL are fail!");
				ps.println(exit); //exit from migration user
				ps.println(exit); // exit from root. Close terminal
			}
			
			ps.println(exit); //exit from migration user
			ps.println(exit); // exit from root. Close terminal
			
			InputStream in = channel.getInputStream();
			
		    byte[] tmp = new byte[1024];
			while (true) {
				
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
					if(time==0){
						channel.disconnect();
					}
				}
				
				if (channel.isClosed()) {
					//System.out.println("exit-status: " + channel.getExitStatus());
					break;
				}
				try {Thread.sleep(1000);
				
				
				} catch (Exception ee) {}
				 
			}
			channel.disconnect();
			System.out.println(" Harness executed");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}





	public void downloadHarnesslogfile() {
		try {
			//ConnectToServer();
			channel = session.openChannel("sftp");
			channel.connect();
			System.out.println("Sftp channel connected to download Harness log file");

			channelSftp = (ChannelSftp) channel;
			try {
				// Setting the folder location of the external system as
				// configured
				// to download the file from
				channelSftp.cd("/data/nokia/migration/harness/log");
				System.out.println("Directory changed in -> /data/nokia/migration/harness/log");
			} catch (Exception sftpException) {
				System.out.println("Failed to change the directory in sftp.");
			}
			
			
			Vector<ChannelSftp.LsEntry> lsEntries = channelSftp.ls(mifName+"*");
			 Collections.sort(lsEntries);
            // Validating if files exist to process the request further
            if (lsEntries.isEmpty()) {
                System.out.println("No file exist in the specified sftp folder location.");
            }
            // Iterating the list of entries to download the file(s) from the sftp 
           
                try {
                	
                    // Downloading the specified file from the sftp to the specified folder path
                    channelSftp.get(lsEntries.lastElement().getFilename(), destLogfile);
                    System.out.println(lsEntries.lastElement().getFilename()); 
                    System.out.println("Harness log file downloaded.");  
                } catch (Exception sftpException) {
                    System.out.println("Failed to download the file the sftp folder location.");                    
                }  
			
		} catch (Exception e) {
			System.err.println(e);
		}
		
		channel.disconnect();
		//session.disconnect();
		
 
		
	}
	

}
