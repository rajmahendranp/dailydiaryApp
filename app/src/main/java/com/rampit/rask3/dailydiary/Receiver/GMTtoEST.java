package com.rampit.rask3.dailydiary.Receiver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class GMTtoEST {
    public static void main(String args[]) {

        Date date = new Date();
        DateFormat estFormat = new SimpleDateFormat();
        DateFormat gmtFormat = new SimpleDateFormat();
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        TimeZone estTime = TimeZone.getTimeZone("EST");
        TimeZone istTime = TimeZone.getTimeZone("IST");
        estFormat.setTimeZone(gmtTime);
        gmtFormat.setTimeZone(estTime);
        gmtFormat.setTimeZone(istTime);
        System.out.println("GMT Time: " + estFormat.format(date));
        System.out.println("EST Time: " + gmtFormat.format(date));

    }

    public String ReturnMeEst(Date GmtTime) {
         Date date = new Date();
        Date date1 = new Date();

        DateFormat estFormat = new SimpleDateFormat();
        DateFormat gmtFormat = new SimpleDateFormat();
        DateFormat gmtFormat1 = new SimpleDateFormat();

        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        TimeZone estTime = TimeZone.getTimeZone("EST");
        TimeZone istTime = TimeZone.getTimeZone("Asia/Kolkata");

        estFormat.setTimeZone(gmtTime);

        String timeInGmt = "";
        gmtFormat.setTimeZone(estTime);
        gmtFormat1.setTimeZone(istTime);

         System.out.println("GMT Time: " + estFormat.format(date));
        System.out.println("EST Time: " + gmtFormat.format(GmtTime));
        System.out.println("IST Time: " + gmtFormat1.format(GmtTime));

        timeInGmt = gmtFormat1.format(GmtTime);

        return timeInGmt;
    }

}
