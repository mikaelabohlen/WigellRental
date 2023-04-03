package org.example.utils;

public class TimeUtil {

    public static String formatTimeMinutesToHourMinutes(int minutes) {
        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;
        return String.format("%02d:%02d", hours, remainingMinutes);
    }
}
