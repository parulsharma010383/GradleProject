package com.biomerieux.user;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public abstract class Command {
	
	public ProcessBuilder pb;
	public static Logger logger = Logger.getLogger(Command.class.getName());
	
	
	
	public Command(String commandString) {
	//	logger.info("Executing command :"+ pb.toString());
		//this.logger = logger;
		pb = new ProcessBuilder("cmd", "/c", commandString);
		System.out.println("Inside command");
		  try {
		//	LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
			logger.info("Inside log command");
			logger.info("Executing command :"+ pb.toString());
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}

	public void execute() throws IOException, InterruptedException {
		try {
			
			Process process = pb.start();
			System.out.println("Inside execute method");
			logger.info("INSIDE EXECUTE");
			int errCode = process.waitFor();
			
			logger.info("error code = "+ errCode);
			String status=processResults(errCode);
			logger.info("Status :"+status);
		} catch(IOException ioe) {
			throw ioe;
		} catch(InterruptedException ie) {
			throw ie;
		}
			
	}

	abstract String processResults(int errCode);

}
