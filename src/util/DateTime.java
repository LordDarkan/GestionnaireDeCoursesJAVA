package util;

import java.time.LocalTime;

public class Time {
	public static LocalTime getLocalTime(int hours, int minutes) {
		int totalSeconds = (hours * 60 + minutes) * 60;
        return LocalTime.of((totalSeconds / 3600) % 24, (totalSeconds / 60) % 60);
	}
}
