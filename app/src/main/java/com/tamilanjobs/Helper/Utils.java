package com.tamilanjobs.Helper;


import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    static final String serverDateFormat = "yyyy-MM-dd HH:mm:ss";
    static final String serverDateFormat_multiple = "yyyy-MM-dd";

    public static final String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static final String months_number[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    static int progressBarStatus = 0;
    static long fileSize = 0;

    private Context _context;

    public Context get_context() {
        return _context;
    }

   public static boolean isValidMobile(String phone) {
        return phone.length() == 10 && android.util.Patterns.PHONE.matcher(phone).matches();
    }

  public   static boolean isValidMail(String email) {
        return (email.length() > 3 || email.length() < 250) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

  public   static void showMessage(String text) {
        Toast.makeText(AppController.getInstance(), text, Toast.LENGTH_SHORT).show();
    }
    public static String getTimeString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        String stringTime = sdf.format(date);
        stringTime = stringTime.replace(".m.", "m");
        return stringTime;
    }


    public static String getDateString(String strDate) {
        Date date = stringToDate(strDate);
        return getDateString(date);

    }

    public static String getDateString_(String strDate) {
        Date date = stringToDate(strDate);
        return getDateString_(date);
    }

    public static String getDateString(Date date) {
        String stringDate;
        if (Utils.isToday(date)) {
            stringDate = "Today";
        } else if (Utils.isYesterday(date)) {
            stringDate = "Yesterday";
        } else {
            stringDate = String.valueOf(date.getDate() + " " + months[date.getMonth()] + " " + (date.getYear() + 1900));
        }
        return stringDate;
    }

    public static String getDateString_(Date date) {
        return String.valueOf(date.getDate() + "-" + months[date.getMonth()] + "-" + (date.getYear() + 1900));
    }

    public static Date stringToDate(String time) {

        // this is for d
        if (time.length() == 10) time = time + " 00:00:00";

        SimpleDateFormat formatter = new SimpleDateFormat(serverDateFormat);
        Date createDate = null;
        try {
            createDate = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return createDate;
    }


    public static boolean isToday(Date date) {

        Calendar c1 = Calendar.getInstance(); // today
        c1.add(Calendar.DAY_OF_YEAR, 0); // today

        Calendar c2 = Calendar.getInstance();
        c2.setTime(date); // your date

        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }

    public static boolean isYesterday(Date date) {

        Calendar c1 = Calendar.getInstance(); // today
        c1.add(Calendar.DAY_OF_YEAR, -1); // yesterday

        Calendar c2 = Calendar.getInstance();
        c2.setTime(date); // your date

        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }

    public static String getTimeString(String strDate) {
        Date date = stringToDate(strDate);
        return getTimeString(date);
    }

    public static String getDateTimeString(String news_datetime) {
        return getDateString(news_datetime) + " " + getTimeString(news_datetime);
    }

    //yyyy/mm/dd
    public static String getYearMonthDateString(String strDate) {
        Date date = stringToYearMonthDate(strDate);
        return getYearMonthDateString_(date);

    }

    public static String getYearMonthDateString_(Date date) {
        return String.valueOf((date.getYear() + 1900)+ "-" + months_number[date.getMonth()]  +date.getDate());
    }


    public static Date stringToYearMonthDate(String time) {

        // this is for d
        if (time.length() == 10) time = time + " 00:00:00";

        SimpleDateFormat formatter = new SimpleDateFormat(serverDateFormat_multiple);
        Date createDate = null;
        try {
            createDate = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return createDate;
    }

    public static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }


}
