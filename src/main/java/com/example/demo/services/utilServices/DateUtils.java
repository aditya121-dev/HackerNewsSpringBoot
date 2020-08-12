package com.example.demo.services.utilServices;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
/***
 * @author Aditya Soni( adityasoni182@gmail.com )
 * @version v1
 * @since 12 August 2020
 */
public class DateUtils {


    public static String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:s";

    /**
     * Parse date time string to LocalDateTime by using @link DateFormats.DD_MM_YYYY_HH_MM_SS format
     *
     * @param dateTimeStr
     * @return
     */
    public static LocalDateTime parseDateTimeString(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormat.forPattern(DATE_TIME_FORMAT));

    }

    /***
     * This function is used to convert the unix time to local date time.
     * @param unixTimeStamp
     * @return
     */
    public static String convertUnixTimeToLocalDateTime(String unixTimeStamp) {
        if (StringUtils.isNotBlank(unixTimeStamp)) {
            long timeStamp = Long.parseLong(unixTimeStamp);
            LocalDateTime dateTime = new DateTime(timeStamp * 1000L).toLocalDateTime();
            String time = dateTime.toString(DateUtils.DATE_TIME_FORMAT);
            return time;

        } else {
            return Strings.EMPTY;
        }
    }

    /***
     * This function is used to get user year duration.
     * @param localDateTimeStr
     * @return
     */
    public static String getUserSinceYears(String localDateTimeStr) {

        LocalDateTime localDateTime = parseDateTimeString(localDateTimeStr);
        LocalDateTime todayLocalDateTime = LocalDateTime.now();
        int years = todayLocalDateTime.getYear() - localDateTime.getYear();
        return years + " years";
    }
}
