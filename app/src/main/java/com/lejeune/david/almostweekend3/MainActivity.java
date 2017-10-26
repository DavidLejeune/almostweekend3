package com.lejeune.david.almostweekend3;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    AnalogClock clkSec, clkMinutes, clkHours,clkDays;

    private ProgressBar firstBar = null;
    private int i = 0;

    private TextView txtDaysWork, txtHoursWork, txtMinutesWork, txtSecondsWork , txtDate, txtPercentage;
    private TextView txtDaysWeekend, txtHoursWeekend, txtMinutesWeekend, txtSecondsWeekend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // update TextView here!
                                countDown();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();

    }

    private void init(){


        firstBar = (ProgressBar)findViewById(R.id.firstBar);
        firstBar.setVisibility(View.VISIBLE);
        firstBar.setMax(100);

        txtDate = (TextView) findViewById(R.id.txtDate);
        txtPercentage = (TextView) findViewById(R.id.txtPercentage);
//        txtDaysWork  = (TextView) findViewById(R.id.txtDaysWork);
//        txtHoursWork = (TextView) findViewById(R.id.txtHoursWork);
//        txtMinutesWork = (TextView) findViewById(R.id.txtMinutesWork);
//        txtSecondsWork = (TextView) findViewById(R.id.txtSecondsWork);
        txtDaysWeekend  = (TextView) findViewById(R.id.txtDaysWeekend);
        txtHoursWeekend = (TextView) findViewById(R.id.txtHoursWeekend);
        txtMinutesWeekend = (TextView) findViewById(R.id.txtMinutesWeekend);
        txtSecondsWeekend = (TextView) findViewById(R.id.txtSecondsWeekend);
        countDown();
    }


    private void countDown(){

        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.setTime(now);

        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        java.text.SimpleDateFormat s = new java.text.SimpleDateFormat("ss");
        java.text.SimpleDateFormat fullDayNameDF = new java.text.SimpleDateFormat("EEEE");
        java.text.SimpleDateFormat abbrDayNameDF = new java.text.SimpleDateFormat("E");
        //SimpleDateFormat intDayNameDF = calendar.get(Calendar.DAY_OF_WEEK);

        String formattedDate = df.format(now);
        String fullDayNameStr = fullDayNameDF.format(now);
        String abbrDayNameStr = abbrDayNameDF.format(now);
        String abbrMonthNameStr = (new java.text.SimpleDateFormat("MMM")).format(now);
        String fullMonthNameStr = (new java.text.SimpleDateFormat("MMMM")).format(now);

        int secondLeftInt = 60 - (Integer.parseInt(s.format(now)));
        int intDayNameStr = calendar.get(Calendar.DAY_OF_WEEK);

        txtDate.setText(formattedDate);
//        txtDate.setText(txtDate.getText() + "\n" + fullDayNameStr);
//        txtDate.setText(txtDate.getText() + "\n" + abbrDayNameStr);
//        txtDate.setText(txtDate.getText() + "\n" + intDayNameStr);
//        txtDate.setText(txtDate.getText() + "\n" + abbrMonthNameStr);
//        txtDate.setText(txtDate.getText() + "\n" + fullMonthNameStr);

        Calendar nowC = Calendar.getInstance();
        int currentDay = nowC.get(Calendar.DAY_OF_WEEK);
        //System.out.println(nowC.toString());

        // Settings for weekend and workweekstart
        int workStartDay = Calendar.MONDAY;
        int workStartHour = 8;
        int workStartMinute = 30;
        Calendar work = Calendar.getInstance();
        work.set(Calendar.DAY_OF_WEEK, workStartDay);
        work.set(Calendar.HOUR_OF_DAY, workStartHour);
        work.set(Calendar.MINUTE, workStartMinute);

        int weekendStartDay = Calendar.FRIDAY;
        int weekendStartHour = 15;
        int weekendStartMinute = 30;
        Calendar weekend = Calendar.getInstance();
        weekend.set(Calendar.HOUR_OF_DAY, weekendStartHour);
        weekend.set(Calendar.MINUTE, weekendStartMinute);
        weekend.add(Calendar.DAY_OF_YEAR, weekendStartDay - currentDay);



        // calulcating time to weekend from workweek start
        long millisLeftWork = weekend.getTimeInMillis() - work.getTimeInMillis() - 60000;
        // days + hours
        long hoursLeft = millisLeftWork  / (60 * 60 * 1000);
        long rawQuotient = hoursLeft;
        int remainderHours = (int) rawQuotient % 24;
        int nrDays = (int) (rawQuotient - remainderHours) / 24 ;
        // minutes
        long minutesLeft =  (millisLeftWork % (60 * 60 * 1000)) / (60 * 1000);

        // outputting timeleft
//        String timeleft = Integer.toString(nrDays) + " Days " +  Integer.toString(remainderHours) + " Hours " + Long.toString(minutesLeft) + " Minutes" ;
//        txtDate.setText(txtDate.getText() + "\n" + "time left since start of workweek  : " + timeleft);
//        txtDate.setText(txtDate.getText() + "\n" + "seconds  : " + Integer.toString(secondLeftInt));

        // output
//        txtDaysWork.setText(Integer.toString(nrDays));
//        txtHoursWork.setText(Integer.toString(remainderHours));
//        txtMinutesWork.setText(Long.toString(minutesLeft));
//        txtSecondsWork.setText(Integer.toString(secondLeftInt));






//
//        System.out.println("time left since start of workweek  : " + timeleft);
//        System.out.println("Seconds : " + Integer.toString(secondLeftInt));


        // calulcating time to weekend from now
        long millisLeft = weekend.getTimeInMillis() - nowC.getTimeInMillis() - 60000;
        // days + hours
        hoursLeft = millisLeft  / (60 * 60 * 1000);
        rawQuotient = hoursLeft;
        remainderHours = (int) rawQuotient % 24;
        nrDays = (int) (rawQuotient - remainderHours) / 24 ;
        // minutes
        minutesLeft =  (millisLeft % (60 * 60 * 1000)) / (60 * 1000);

        // output
        Random rnd = new Random();
        txtDaysWeekend.setText(Integer.toString(nrDays));
        txtDaysWeekend.setTextColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));

        rnd = new Random();
        txtHoursWeekend.setText(Integer.toString(remainderHours));
        txtHoursWeekend.setTextColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));

        rnd = new Random();
        txtMinutesWeekend.setText(Long.toString(minutesLeft));
        txtMinutesWeekend.setTextColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));

        rnd = new Random();
        txtSecondsWeekend.setText(Integer.toString(secondLeftInt));
        txtSecondsWeekend.setTextColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));



        // outputting timeleft
//        timeleft = Integer.toString(nrDays) + " Days " +  Integer.toString(remainderHours) + " Hours " + Long.toString(minutesLeft) + " Minutes" ;
//        txtDate.setText(txtDate.getText() + "\n" + "time left since now  : " + timeleft);
//        txtDate.setText(txtDate.getText() + "\n" + "seconds  : " + Integer.toString(secondLeftInt));
//
//        System.out.println("time left since now  : " + timeleft);

        long div = millisLeftWork / 100 ;
        double div2 = (double) millisLeft / div;
        String perc = String.format("%.2f", 100 - div2)  ;
        txtPercentage.setText(perc + " %");
        i = firstBar.getMax()  - ((int) div2) ;
        firstBar.setProgress(i);


    }



}
