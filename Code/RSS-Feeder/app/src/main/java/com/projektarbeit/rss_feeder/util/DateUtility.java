package com.projektarbeit.rss_feeder.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alexander on 27.06.2017.
 */

// 27.06.2017 | AE | Klasse erstellt

public class DateUtility {


    public static String convertTimeToString(Date date) {

        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = formatter.format(date);
        return  s;
    }

    public static Date convertStringToTime(String dtString) {

        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;

        try {
            date = (Date) formatter.parseObject(dtString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return date;
    }
}
