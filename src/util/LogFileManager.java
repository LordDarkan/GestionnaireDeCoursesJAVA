package util;

import java.io.File;
import java.time.LocalDate;

public class LogFileManager {
	public static final boolean ENABLE = true;
	
	private static final String SEPARATOR = "/";
	public static final String EXTENTION = ".log";
	
	private static final String NAME_DIRECTORY = "logHistory";
	private static final String NAME_DEFAULT = "gdc";
	
	public static final String PATH_DIRECTORY = NAME_DIRECTORY+SEPARATOR;
	public static final String PATH_DEFAULT = NAME_DEFAULT+EXTENTION;
	

	public static void checkFiles() {
		if (ENABLE) {
			checkDirectory();
			checkMonthFile();
		}
	}
	
	private static void checkDirectory() {
		File file = new File(NAME_DIRECTORY);
		if(!file.exists()) { 
			file.mkdir();
		}
	}
	
	private static void checkMonthFile() {
		LocalDate lastMonth = LocalDate.now().minusMonths(1);
		String nameLogFile = DateTime.getNameLog(lastMonth);
		File log = new File(PATH_DEFAULT);
		File logLastMonth = new File(PATH_DIRECTORY+nameLogFile+EXTENTION);
		if(!log.exists() && !logLastMonth.exists()) { 
			try {
				logLastMonth.createNewFile();
			} catch (Exception e) {
				System.err.println("EMPTY LOG "+logLastMonth+" not  created");
			}
		} else if(log.exists() && !logLastMonth.exists()) { 
			boolean success = log.renameTo(logLastMonth);
			if (!success) {
				System.err.println("LOG "+logLastMonth+" not  created");
			}
		}
	}

}
