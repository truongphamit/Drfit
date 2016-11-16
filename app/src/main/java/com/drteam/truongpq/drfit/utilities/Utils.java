package com.drteam.truongpq.drfit.utilities;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by phamtruong on 10/4/16.
 */

public class Utils {
    public static Date convertStringToDate(String d) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("DD-MM-YYYY");
        try {
            date = format.parse(d);
            System.out.println(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    public static String convertMsToDate(long ms) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(new Date(ms));
    }

    public static long getDayFromMs(long ms) {
        return ms / (1000 * 60 * 60 * 24);
    }

    // convert milliseconds into the day of the week string
    public static String dayStringFormat(long msecs) {
        GregorianCalendar cal = new GregorianCalendar();

        cal.setTime(new Date(msecs));

        int dow = cal.get(Calendar.DAY_OF_WEEK);

        switch (dow) {
            case Calendar.MONDAY:
                return "Mon";
            case Calendar.TUESDAY:
                return "Tue";
            case Calendar.WEDNESDAY:
                return "Wed";
            case Calendar.THURSDAY:
                return "Thu";
            case Calendar.FRIDAY:
                return "Fri";
            case Calendar.SATURDAY:
                return "Sat";
            case Calendar.SUNDAY:
                return "Sun";
        }
        return "Unknown";
    }

    public static String dateStringFormat(long msecs) {
        String date = "";
        GregorianCalendar cal = new GregorianCalendar();

        cal.setTime(new Date(msecs));

        int dow = cal.get(Calendar.DAY_OF_WEEK);
        switch (dow) {
            case Calendar.MONDAY:
                date += "Mon, ";
                break;
            case Calendar.TUESDAY:
                date += "Tue, ";
                break;
            case Calendar.WEDNESDAY:
                date += "Wed, ";
                break;
            case Calendar.THURSDAY:
                date += "Thu, ";
                break;
            case Calendar.FRIDAY:
                date += "Fri, ";
                break;
            case Calendar.SATURDAY:
                date += "Sat, ";
                break;
            case Calendar.SUNDAY:
                date += "Sun, ";
                break;
            default:
                date += "Unknown, ";
        }

        switch (cal.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
                date += "Jan ";
                break;
            case Calendar.FEBRUARY:
                date += "Feb ";
                break;
            case Calendar.MARCH:
                date += "Mar ";
                break;
            case Calendar.APRIL:
                date += "Apr ";
                break;
            case Calendar.MAY:
                date += "May ";
                break;
            case Calendar.JUNE:
                date += "Jun ";
                break;
            case Calendar.JULY:
                date += "Jul ";
                break;
            case Calendar.AUGUST:
                date += "Aug ";
                break;
            case Calendar.SEPTEMBER:
                date += "Sep ";
                break;
            case Calendar.OCTOBER:
                date += "Oct ";
                break;
            case Calendar.NOVEMBER:
                date += "Nov ";
                break;
            case Calendar.DECEMBER:
                date += "Dec ";
                break;
            default:
                date += "Unknown ";
                break;
        }
        date += cal.get(Calendar.DAY_OF_MONTH);
        date += ", ";
        date += cal.get(Calendar.YEAR);
        return date;
    }

    public static long getFirstDayOfWeek(long ms) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date(ms));
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        cal.set(Calendar.DAY_OF_MONTH,
                (cal.get(Calendar.DAY_OF_MONTH) - dayOfWeek) + 1);
        return cal.getTimeInMillis();
    }

    public static long getLastDayOfWeek(long ms) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date(ms));
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)
                + (7 - dayOfWeek));
        return cal.getTimeInMillis();
    }

    public static String getMonthFromMs(long ms) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date(ms));
        switch (cal.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
                return "January";
            case Calendar.FEBRUARY:
                return "February";
            case Calendar.MARCH:
                return "March";
            case Calendar.APRIL:
                return "April";
            case Calendar.MAY:
                return "May";
            case Calendar.JUNE:
                return "June";
            case Calendar.JULY:
                return "July";
            case Calendar.AUGUST:
                return "August";
            case Calendar.SEPTEMBER:
                return "September";
            case Calendar.OCTOBER:
                return "October";
            case Calendar.NOVEMBER:
                return "November";
            case Calendar.DECEMBER:
                return "December";
            default:
                return "Unknown ";
        }
    }

    public static int getYearFromMs(long ms) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date(ms));
        return cal.get(Calendar.YEAR);
    }
}