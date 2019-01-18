package util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerManager {
	private static Logger LOGGER = null;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
		LOGGER = Logger.getLogger("GDC-Global");
		if (LogFileManager.ENABLE) {
			try {
				Handler handler = new FileHandler(LogFileManager.getDefaultPath(),true);
				handler.setFormatter(new SimpleFormatter());
				LOGGER.addHandler(handler);
			} catch (SecurityException | IOException e) {
				LOGGER.log(Level.SEVERE, "Handler "+LogFileManager.getDefaultPath(), e);
			}
		}
	}
	
	public static Logger getPathLogger(String path) {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
		Logger log = Logger.getLogger("GDC-Settings");
		Handler handler;
		try {
			handler = new FileHandler(path+LogFileManager.SEPARATOR+"settings"+LogFileManager.EXTENTION,true);
			handler.setFormatter(new SimpleFormatter());
			log.addHandler(handler);
		} catch (Exception e) {
		}
		return log;
	}

	public static Logger getLogger() {
		if (LOGGER == null) {
			new LoggerManager();
		}
		return LOGGER;
	}

	public static void info(String msg) {
		if (LOGGER == null) {
			new LoggerManager();
		}
		LOGGER.info(msg);
	}
	
	/*TODO named logger new file ?
	public static Logger getLogger(String name) {
		Logger log = Logger.getLogger(name);
		try { //if (LogFileManager.ENABLE)
			Handler handler = new FileHandler(LogManager.PATH,true);
			handler.setFormatter(new SimpleFormatter());
			log.addHandler(handler);
		} catch (SecurityException | IOException e) {
			log = getLogger();
			log.log(Level.SEVERE, "Handler "+LogManager.PATH, e);
		}
		return log;
	}*/
}
