package com.example.zaas.pocketbanker.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shseth on 3/26/2016.
 */
public class DateUtils {

    public static String getDateStringFromMillis(long millis) {
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date(millis));
    }

    public static String getDateTimeStringFromMillis(long millis) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(millis));
    }

}
