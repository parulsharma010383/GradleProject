package com.log.ren;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingExample {
	
	
	public static Logger logger = Logger.getLogger(LoggingExample.class.getName());
	private static final SimpleDateFormat logDate = new SimpleDateFormat("dd-MMM-yyyy");

	public static void main(String[] args) {
		 try {
			  getLogFilePatternFromSubsystem("logdetails");
	          //LogManager.getLogManager().readConfiguration(new FileInputStream("mylogging.properties"));
	          logger.info("INSIDE MAIN");
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }
		 logger.setLevel(Level.FINE);
	     logger.info("INSIDE LOGGING In the folder");
	            
	            for(int i=0; i<10; i++){
	                //logging messages
	                logger.log(Level.INFO, "Msg"+i);
	            }
	            
	            logger.log(Level.CONFIG, "Config data");
	            logger.warning("warning message");
	            logger.log(Level.SEVERE, "severe");
	       
	}
	public static final void getLogFilePatternFromSubsystem(final String subsystem) throws SecurityException, IOException{
		final String FS = File.separator;
		String renbase = "C:\\VitekTemp";
		String logType = System.getProperty("REN.logType", "server");
		String logDir = renbase + FS + "log" + FS + logType + FS + logDate.format(new Date());
		// Create the log directory
		
		new File(logDir).mkdirs();

		FileHandler fileHandler = new FileHandler(logDir + FS + subsystem+ ".log", 2000, 1);
		  
		try {
			
			fileHandler.setFormatter(new MyFormatter());
			logger.addHandler(fileHandler);
			 logger.info("INSIDE LOGGING METHOD In the folder");
			 
		} catch (SecurityException e) {
			e.printStackTrace();
		}
  
		// Set log file.  %u - file name conflict resolution number, %g - rotating log number
		
				//logDir + FS + subsystem + "%u.%g.log";
	}

}
