package com.gmail.sirotempest.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Patterns;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.gmail.sirotempest.utils.Constants.NOTIFICATION_PERIODICITY_KEY;

public class Util {

    public static boolean isValidName(String name) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        return patron.matcher(name).matches();
    }

    /*public static boolean isValidPhone(String phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }

    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }*/

    public static String format(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        return sdf.format(date);
    }

    public static String formatMin(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM", Locale.US);
        return sdf.format(date);
    }

    /*public static boolean productAboutToExpire(String expireDateString, Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int periodicity = Integer.parseInt(sharedPreferences.getString(NOTIFICATION_PERIODICITY_KEY, "30"));

        Date currentDate = new java.util.Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Date expireDate = null;

        try {
            expireDate = format.parse(expireDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff;

        try {
            diff = expireDate.getTime() - currentDate.getTime();
        } catch (Exception e) {
            return false;//if the date has not been placed, returned false (without date case)
        }

        int daysLeft =  (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        //compare if there are more than x days with food expiration date, where x is periodicity defined at settings (default is 3 days)
        if(daysLeft < periodicity && daysLeft >= 0) {
            return true;
        } else {
            return false;
        }

    }*/
}
