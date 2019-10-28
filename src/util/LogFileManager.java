package util;

import java.io.File;
import java.time.LocalDate;

import data.Mapper;
import models.Settings;

public class LogFileManager {
	public static boolean ENABLE = true;
	
	public static final String SEPARATOR = System.getProperty("file.separator");
	public static final String EXTENTION = ".log";
	
	private static String PATH_DIRECTORY = "";
	
	private static final String NAME_DIRECTORY = "logHistory";
	private static final String NAME_DEFAULT = "gdc";
	
	public static String getDefaultPath() {
		return PATH_DIRECTORY+SEPARATOR+NAME_DEFAULT+EXTENTION;
	}

	public static void checkFiles() {
		if (ENABLE)
			checkMapper();
			
		if (ENABLE) {
			checkDirectory();
			checkMonthFile();
		}
	}
	
	private static void checkMapper() {
		Mapper mapper = Mapper.getInstance();
		if (mapper != null) {
			Settings settings = mapper.getSettings();
			String saveD = settings.getPathSaveDirectory();
			PATH_DIRECTORY = saveD+SEPARATOR+NAME_DIRECTORY;
			File file = new File(saveD);
			if(!file.exists()) {
				ENABLE = false;
			}
		} else {
			ENABLE = false;
		}
		
	}

	private static void checkDirectory() {
		File file = new File(PATH_DIRECTORY);
		if(!file.exists()) { 
			System.out.println("don't exist");
			file.mkdir();
		}
	}
	
	private static void checkMonthFile() {
		LocalDate lastMonth = LocalDate.now().minusMonths(1);
		String nameLogFile = DateTime.getNameLog(lastMonth);
		File log = new File(getDefaultPath());
		File logLastMonth = new File(PATH_DIRECTORY+SEPARATOR+nameLogFile+EXTENTION);
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
