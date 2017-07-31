package kbm.co.za.mobile.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by monageng on 2017/05/27.
 */

public class DateUtils {
    public static String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";

    public static String getMonthFromDate(Date date) {
        String month = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int calMonth = calendar.get(Calendar.MONTH);

        switch (calMonth) {
            case Calendar.JANUARY : month = "JAN"; break;
            case Calendar.FEBRUARY : month = "FEB";break;
            case Calendar.MARCH : month = "MAR";break;
            case Calendar.APRIL : month = "APR";break;
            case Calendar.MAY : month = "MAY";break;
            case Calendar.JUNE : month = "JUN";break;
            case Calendar.JULY : month = "JUL";break;
            case Calendar.AUGUST : month = "AUG";break;
            case Calendar.SEPTEMBER : month = "SEPT"; break;
            case Calendar.OCTOBER : month = "OCT";break;
            case Calendar.NOVEMBER : month = "NOV";break;
            case Calendar.DECEMBER : month = "DEC";break;

        }

        return  month;
    }

    public static Date parseToDate(String dateInput, String pattern) throws Exception {
        if (dateInput == null) return  null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            Date date = dateFormat.parse(dateInput);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new Exception("Unable to part given date to " + pattern + " for input " + dateInput);
        }
    }

    public static String formatToDateString(Date date, String pattern) throws Exception {
        if (date == null) return  null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            String formattedDate = dateFormat.format(date);
            return formattedDate;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unable to part given date to " + pattern + " for input ");
        }
    }

}
