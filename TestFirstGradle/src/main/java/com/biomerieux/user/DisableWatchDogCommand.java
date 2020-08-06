package com.biomerieux.user;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class DisableWatchDogCommand extends Command{
	
	public static Logger logger = Logger.getLogger(DisableWatchDogCommand.class.getName());
	

	public DisableWatchDogCommand() throws FileNotFoundException{
		super("sc mkdir tempdir ");
	   
		
		
		//super("sc config MylaServiceMonitor start= disabled");
		
	}

	@Override
	public String processResults(int errCode) {
		
		//logger.setLevel(Level.FINE);
		System.out.println("Inside processresults disable watchdog");
		if(errCode!=1) {
			logger.info("Inside disable watchdog");
			return "success";
		}
		else {
			logger.info("Inside process Results disable watchdog");
			return "failure";
		}
				
		}
}