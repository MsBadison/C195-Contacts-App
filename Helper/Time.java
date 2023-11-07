package App.Helper;


import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoField;
import java.util.TimeZone;

public class Time {

    /**
     * Converts local date and time to zoneddatetime
     * @param date the local date
     * @param time the local time
     * @return zoneddatetime
     */
    public static ZonedDateTime convertLocaltoZoned(LocalDate date, LocalTime time){
        ZonedDateTime startUTC = ZonedDateTime.of(date, time, ZoneId.of("UTC"));
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        return startUTC.withZoneSameInstant(localZoneId);
    }

    /**
     * Converts local date and time to UTC timestamp
     * @param time the local time
     * @param date teh local date
     * @return UTC timestamp
     */
    public static Timestamp convertLocalToUPCTimestamp(LocalTime time, LocalDate date){
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime localZonedDateTime = ZonedDateTime.of(date, time, localZoneId);
        ZonedDateTime utcDateTime = localZonedDateTime.withZoneSameInstant(utcZone);
        return Timestamp.valueOf(utcDateTime.toLocalDateTime());
    }

    /**
     * Checks if start time is after end time
     * @param start the start time
     * @param end the end time
     * @return boolean
     */
    public static boolean checkStartEndTime(Timestamp start, Timestamp end) {
        if (start.after(end)) {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Check if time is during business house
     * @param start the start time
     * @param end the end time
     * @param date the date
     * @return boolean
     */
    public static boolean checkBusHours(LocalTime start, LocalTime end, LocalDate date){
        ZoneId estZone = ZoneId.of("America/New_York");
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime startTime = ZonedDateTime.of(date, start, localZoneId);
        ZonedDateTime endTime = ZonedDateTime.of(date, end, localZoneId);
        LocalTime open = LocalTime.of(07,  59);
        LocalTime close = LocalTime.of(22,  01);
        ZonedDateTime openTime = ZonedDateTime.of(date, open, estZone);
        ZonedDateTime closeTime = ZonedDateTime.of(date, close, estZone);
        if(startTime.isAfter(openTime) && (endTime.isBefore(closeTime))){
            return true;
        }
        return false;
    }

    /**
     * Gets the week number
     * @param date the date
     * @return the week number
     */
    public static Integer getWeekNumber(ZonedDateTime date){
        return date.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
    }

    /**
     * Gets the month number
     * @param date the date
     * @return the month number
     */
    public static Integer getMonthNumber(ZonedDateTime date){
        return date.getMonthValue();
    }

    /**
     * Gets the month name
     * @param date the date
     * @return the month name
     */
    public static String getMonthName(ZonedDateTime date){
        return date.getMonth().name();
    }
}
