package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTime {

	private static final DateTimeFormatter formatIdAutoSave = DateTimeFormatter.ofPattern("yyMM 'Auto Save' dd MMM yyyy");
	private static final DateTimeFormatter formatIdSave = DateTimeFormatter.ofPattern("yyMM-dd MMM yyyy HH'H'mm");
	private static final DateTimeFormatter formatHeure = DateTimeFormatter.ofPattern("HH'H'mm");
	private static final DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd MMMM yyyy");
	private static final DateTimeFormatter formatDateSave = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	public static String toString(LocalTime heure) {
        return heure.format(formatHeure);
	}
	
	public static String toString(LocalDate date) {
        return date.format(formatDate);
	}
	
	public static String saveToString(LocalDate date) {
        return date.format(formatDateSave);
	}
	
	public static LocalTime toLocalTime(String heure) {
        return LocalTime.parse(heure, formatHeure);
	}
	
	public static LocalDate toLocalDate(String date) {
        return LocalDate.parse(date,formatDate);
	}
	
	public static LocalDate saveToLocalDate(String date) {
        return LocalDate.parse(date,formatDateSave);
	}
	
	public static LocalTime getLocalTime(int hours, int minutes) {
		int totalSeconds = (hours * 60 + minutes) * 60;
        return LocalTime.of((totalSeconds / 3600) % 24, (totalSeconds / 60) % 60);
	}

	public static String getNameSave(LocalDate heure) {
        return heure.format(formatIdAutoSave);
	}
	
	public static String getNameSave(LocalDateTime heure) {
        return heure.format(formatIdSave);
	}
}
