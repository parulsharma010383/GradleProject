package com.log.ren;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class CommonLogger extends Logger {
	
	public CommonLogger(final Class c) {
		this(c.getName());
	}

	public CommonLogger(final String loggerName) {
		super(loggerName, null);

		/*
		 * Do what Logger.getLogger() would normally do, add the logger to the log manager. This will set the
		 * logger's parent, among other things (the Logger class was meant to be used as a factory). The
		 * second time we instantiate a logger with the same name the log manager does notthing. The logger
		 * hierarchy is not meant to handle this case, the constructor is supposed to be protected/private.
		 * But because we are already using the constructor in thousands of classes we do the following as a
		 * work around. Manually set the parent of this second, or third, etc, instance of a logger, to the
		 * root logger.
		 */
		boolean isFirstInstanceWithGivenName = LogManager.getLogManager().addLogger(this);
		if (isFirstInstanceWithGivenName == false) {
			Logger root = getLogger("");
			this.setParent(root);
		}
	}
	
	public static Logger getLogger(final Class c) {
		return getLogger(c.getName());
	}
	
	
		
		

}
