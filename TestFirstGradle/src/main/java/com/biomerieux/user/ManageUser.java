package com.biomerieux.user;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManageUser {
	
	public static Logger logger = Logger.getLogger(ManageUser.class.getName());
	private static final SimpleDateFormat logDate = new SimpleDateFormat("dd-MMM-yyyy");
//	private Command disableWatchDog = new DisableWatchDogCommand();
	private Command exportDatabase = new ManageDatabaseResetCommand();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("ManageUser Class");
		
		 try {
			String handler= getLogFilePatternFromSubsystem("logdetails");
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         //LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
         logger.setLevel(Level.FINE);
         logger.info("Starting the ResetUserManagementDatabaseTool");
	
		System.out.println("date :"+ logDate.format(new Date()));
		ManageUser tool = new ManageUser();
		try {
			tool.resetUserManagementDatabase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void resetUserManagementDatabase() throws IOException, InterruptedException{
		logger.entering(this.getClass().getName(), "doSomething");
		try {
			//disableWatchDog.execute();			
			logger.info("watchDog disabled");	
			//stopWatchDog.execute();			
			logger.info("watchDog stopped");
			logger.info("Calling manage opends");
			System.out.println("Calling manage opends");
			exportDatabase.execute();
			

			Scanner scanner = new Scanner(System.in);
			String str = scanner.nextLine();
			while (!str.equalsIgnoreCase("STOP")) {
				str = scanner.nextLine();
			}
			System.exit(0);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static final String getLogFilePatternFromSubsystem(final String subsystem) throws SecurityException, IOException{
		final String FS = File.separator;
		String renbase = "C:\\Users\\16001854\\VitekTemp\\24033RepairCorruptUserManagementDatabase";
		String logType = System.getProperty("REN.logType", "server");
		String logDir = renbase + FS + "log" + FS + logType + FS + logDate.format(new Date());
		// Create the log directory
		
		new File(logDir).mkdirs();

		FileHandler fileHandler = new FileHandler(logDir+FS+"logdetails.log", 2000, 1);
		  
		try {
			
			fileHandler.setFormatter(new MyFormatter());
			logger.addHandler(fileHandler);
			 logger.info("INSIDE LOGGING METHOD In the folder");
			 
		} catch (SecurityException e) {
			e.printStackTrace();
		}
  
		// Set log file.  %u - file name conflict resolution number, %g - rotating log number
		return "fileHandler";
				//logDir + FS + subsystem + "%u.%g.log";
	}


}
