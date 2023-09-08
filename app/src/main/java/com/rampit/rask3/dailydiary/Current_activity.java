package com.rampit.rask3.dailydiary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdaptercollection;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdaptercurrent;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;



//Updated on 25/01/2022 by RAMPIT
//Show all debit users
//InternetConnection1() - Check whether internet is on or not
//updateLabel() - Update textview when date is selected
//updateLabel1() - Update textview when date is selected
//populateRecyclerView() - get all current and NIP user
//populateRecyclerView2() - get all NIPNIP user
//populateRecyclerView1() - get all current and NIP user
//populateRecyclerView3() - get all NIPNIP user
//onCreateOptionsMenu() - when navigation menu created
//progressbar_load() - Load progressbar
//progre() - Stop progressbar
//onOptionsItemSelected() - when navigation item pressed


public class Current_activity extends AppCompatActivity {
    TextView from,to,dateee,session,noo,tdeb,tdoc,tint,tcus;
    ArrayList<String> Names = new ArrayList<>();
    Button search,clear;
    String dateString,dateString1;
    Calendar myCalendar,myCalendar1;
    SQLiteHelper sqlite;
    RecyclerView recyclerView;
    Integer ses,totdeb,totdoc,totins,sno;
    String timing,formattedDate;
    SQLiteDatabase database;
    RecyclerViewAdaptercurrent mAdapter;
    ImageView seesim;
    String tablename ="dd_account_debit";
    String column = "debit_date";
    DatePickerDialog datePickerDialog,datePickerDialog1;
    Dialog dialog;
    Integer showinng = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ii = pref.getString("theme","");

        if(ii.equalsIgnoreCase("")){
            ii = "1";
        }
        Log.d("thee",ii);
        final Integer in = Integer.valueOf(ii) ;
        if(in == 0){
            Log.d("thee","thh");
            setTheme(R.style.AppTheme1);
        }else{
            Log.d("thee","th1h");
            setTheme(R.style.AppTheme11);
//            recreate();
        }
        String localeName = pref.getString("language","");
        if(localeName == null){
            localeName = "ta";
        }
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        dialog = new Dialog(Current_activity.this,R.style.AlertDialogTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_current);
        setTitle(R.string.title_current_activity);
        ((Callback)getApplication()).datee();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        from = findViewById(R.id.from_date);
        to = findViewById(R.id.to_date);
        clear = findViewById(R.id.clear);
        search = findViewById(R.id.search);
        tdeb = findViewById(R.id.deb12);
        tdoc = findViewById(R.id.doc);
        tint = findViewById(R.id.inst);
        tcus =findViewById(R.id.totcu);
        seesim = findViewById(R.id.sesimg);
        recyclerView = findViewById(R.id.re);
        dateee = findViewById(R.id.da);
        session = findViewById(R.id.sess);
        LinearLayout linear = findViewById(R.id.linearr);
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            //your method here
                            final SharedPreferences pref11 = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                            String isLoading = pref11.getString("isLoading","no");
                            String isLoaded_time = pref11.getString("isLoaded_time","no");
                            Log.d("isLoading",isLoading+" "+isLoaded_time);
                            if (InternetConnection1.checkConnection(getApplicationContext(),Current_activity.this)) {
                                // Internet Available...
                                Log.d("internet","on");
                                showinng = 0;
                            }
                            else {
//                                progre();
                                // Internet Not Available...
                                Log.d("internet","off");
                                runOnUiThread(new Runnable() {
                                    @SuppressLint("RestrictedApi")
                                    @Override
                                    public void run() {
                                        // Stuff that updates the UI
//                                    fab.hide();
//                                        progressBar_cyclic.setVisibility(View.GONE);
//                                        textView3.setVisibility(View.GONE);
//                                        textView2.setText(getString(R.string.backup_status)+getString(R.string.check_internet));
//                                        textView3.setText(R.string.updated_time+isLoaded_time);
                                        if(isLoading.equalsIgnoreCase("no") && showinng.equals(0)){
                                            Snackbar snackbar = Snackbar
                                                    .make(linear, getString(R.string.on_internet_for_backup), Snackbar.LENGTH_INDEFINITE)
                                                    .setAction(getString(R.string.ok), new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            showinng = 0;
                                                        }
                                                    });
                                            snackbar.setActionTextColor((ContextCompat.getColor(getApplicationContext(), R.color.Red)));
                                            View sbView = snackbar.getView();
                                            TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
                                            textView.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
                                            snackbar.show();
                                            showinng = 1 ;
                                        }

                                    }
                                });


                            }

                        } catch (Exception e) {
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 10000); //execute in every 10 minutes
        noo = findViewById(R.id.no);
        ses = pref.getInt("session", 1);
        TextView dayy =(TextView)findViewById(R.id.day);
        String weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        if(weekday_name.equalsIgnoreCase("Monday")){
            weekday_name =  getString(R.string.monday);
        }else if(weekday_name.equalsIgnoreCase("Tuesday")){
            weekday_name =  getString(R.string.tuesday);
        }else if(weekday_name.equalsIgnoreCase("Wednesday")){
            weekday_name =  getString(R.string.wednesday);
        }else if(weekday_name.equalsIgnoreCase("Thursday")){
            weekday_name =  getString(R.string.thursday);
        }else if(weekday_name.equalsIgnoreCase("Friday")){
            weekday_name =  getString(R.string.friday);
        }else if(weekday_name.equalsIgnoreCase("Saturday")){
            weekday_name =  getString(R.string.saturday);
        }else if(weekday_name.equalsIgnoreCase("Sunday")){
            weekday_name =  getString(R.string.sunday);
        }
        dayy.setText(weekday_name);
        String dd = pref.getString("Date","");
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date debit = df1.parse(dd);
            formattedDate = df.format(debit);
            Log.d("deae",formattedDate);
            String ffff = df1.format(debit);
            from.setText(ffff);
            dateString = formattedDate;
            to.setText(ffff);
            dateString1 = formattedDate;
//            populateRecyclerView();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateee = findViewById(R.id.da);
        session = findViewById(R.id.sess);
        dateee.setText(dd);
        if(in == 0){
            if(ses == 1){
                timing = getString(R.string.morning);
                seesim.setBackgroundResource(R.drawable.sun);
            }else if(ses == 2){
                timing = getString(R.string.evening);
                seesim.setBackgroundResource(R.drawable.moon);
            }
        }else{
            if(ses == 1){
                timing = getString(R.string.morning);
                seesim.setBackgroundResource(R.drawable.sun_blue);
            }else if(ses == 2){
                timing = getString(R.string.evening);
                seesim.setBackgroundResource(R.drawable.moon_blue);
            }
        }

        session.setText(timing);
        myCalendar = Calendar.getInstance();
        myCalendar1 = Calendar.getInstance();
//        sqlite = new SQLiteHelper(this);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        from.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (in == 0){
                    datePickerDialog =  new DatePickerDialog(Current_activity.this,R.style.DialogTheme, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH ));
                }else{
                    datePickerDialog =  new DatePickerDialog(Current_activity.this,R.style.DialogTheme1, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH ));
                }
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
                String myFormat = "yyyy/MM/dd";
                String myFormat1 = "dd/MM/yyyy";//In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                dateString = sdf.format(myCalendar.getTime());
                from.setText(sdf1.format(myCalendar.getTime()));
            }
        });
        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };
        to.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (in == 0){
                    datePickerDialog1 =  new DatePickerDialog(Current_activity.this,R.style.DialogTheme, date1, myCalendar1
                            .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                            myCalendar1.get(Calendar.DAY_OF_MONTH ));
                }else{
                    datePickerDialog1 =  new DatePickerDialog(Current_activity.this,R.style.DialogTheme1, date1, myCalendar1
                            .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                            myCalendar1.get(Calendar.DAY_OF_MONTH ));
                }
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog1.show();
                String myFormat = "yyyy/MM/dd";
                String myFormat1 = "dd/MM/yyyy";//In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                dateString1 = sdf.format(myCalendar1.getTime());
                to.setText(sdf1.format(myCalendar1.getTime()));
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                from.setText("");
                to.setText("");
//                populateRecyclerView1();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                populateRecyclerView1();
                            }
                        });
                    }
                }, 500);

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        progressbar_load();
                    }
                });
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("bothdates",dateString+" "+dateString1);
//                populateRecyclerView();
                if(dateString.equalsIgnoreCase("")||dateString1.equalsIgnoreCase("")){}else{

                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    Date newda = null;
                    try {
                        newda = df.parse(dateString);
                        Date debit = df.parse(dateString1);
                        long diffInMillisec =  newda.getTime() - debit.getTime();
                        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                        Log.d("day5",String.valueOf(diffInDays));
                        if(diffInDays > 0){
                            AlertDialog.Builder alertbox = new AlertDialog.Builder(Current_activity.this,R.style.AlertDialogTheme);
                            String enn = getString(R.string.fromtodate);
                            String war = getString(R.string.warning);
                            String ook = getString(R.string.ok);
                            alertbox.setMessage(enn);
                            alertbox.setTitle(war);
                            alertbox.setIcon(R.drawable.logo);
                            alertbox.setPositiveButton(ook,
                                    new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface arg0,
                                                            int arg1) {
                                        }
                                    });
                            alertbox.show();
                        }else{ Log.d("bothdates",dateString+" "+dateString1);
//                            populateRecyclerView();
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            populateRecyclerView();
                                        }
                                    });
                                }
                            }, 500);

                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    progressbar_load();
                                }
                            });
                           }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }



                }
            }

        });
//        populateRecyclerView1();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        populateRecyclerView1();
                    }
                });
            }
        }, 500);

        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                progressbar_load();
            }
        });
    }
    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Current_activity account) {
            final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected to the internet
//                Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();
                String sss = String.valueOf(activeNetworkInfo.getTypeName());
                Log.d("y7",sss);

                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
//                    yy = 1;
                    return true;
                } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's data plan
//                    yy = 2;
                    return true;
                }else{

                }
            }else{
                Log.d("y7","null");
                AlertDialog.Builder alertbox = new AlertDialog.Builder(account,R.style.AlertDialogTheme);
                String enn = account.getString(R.string.on_internet);
                String war = account.getString(R.string.warning);
                String ook = account.getString(R.string.ok);
                alertbox.setMessage(enn);
                alertbox.setTitle(war);
                alertbox.setCancelable(false);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setPositiveButton(ook,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                try {
//                                    login.setMobileDataEnabled(login,true);
//                                    settings_activity.ssss();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
//                alertbox.show();
            }
            return false;
        }
    }
    //updateLabel() - Update textview when date is selected
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void updateLabel() {

        String myFormat = "yyyy/MM/dd";
        String myFormat1 = "dd/MM/yyyy";//In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        dateString = sdf.format(myCalendar.getTime());
        from.setText(sdf1.format(myCalendar.getTime()));;
    }
    //updateLabel1() - Update textview when date is selected
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void updateLabel1() {

        String myFormat = "yyyy/MM/dd";
        String myFormat1 = "dd/MM/yyyy";//In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        dateString1 = sdf.format(myCalendar1.getTime());
        to.setText(sdf1.format(myCalendar1.getTime()));

    }
    //populateRecyclerView() - get all current and NIP user
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerView() {
        totdeb = 0;
        totdoc = 0;
        totins = 0;
        sno = 0;
        Names.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT a.*,b.CID as ID,b.order_id_new,b.debit_type,b.customer_name " +
                "FROM dd_account_debit a INNER JOIN dd_customers b ON b.id = a.customer_id WHERE( a.debit_date BETWEEN ?  AND ?) AND b.tracking_id = ? AND b.debit_type IN (0,1) AND a.active_status = 1  GROUP BY b.id ORDER BY b.CID ASC",
                new String[]{dateString, dateString1,String.valueOf(ses)});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("ID");
                    String id = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String oid = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_type");
                    Integer debtyp = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String name = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debit = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("document_charge");
                    String docu = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("interest");
                    String inter = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String total = cursor.getString(index);

                    if(docu == null){
                        docu ="0";
                    }
                    if(debit == null){
                        debit ="0";
                    }
                    if(inter == null){
                        inter ="0";
                    }

                    Integer dd = Integer.parseInt(docu);
                    Integer dd1 = Integer.parseInt(debit);
                    Integer dd2 = Integer.parseInt(inter);
                    totdoc = totdoc + dd;
                    totdeb = totdeb + dd1;
                    totins = totins + dd2;
                    sno = sno + 1;
//                    String parsedStr = name.replaceAll("(.{14})", "$1\n");
                    if(debtyp == 2){
                        Names.add(id+","+name+","+debit+","+docu+","+inter+","+total);
                    }else{
                        Names.add(id+","+name+","+debit+","+docu+","+inter+","+total);
                    }
                    Log.d("nnn", String.valueOf(Names));
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                cursor.close();
                sqlite.close();
                database.close();
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }
        populateRecyclerView2();
//        if(Names.size() == 0){
//            recyclerView.setVisibility(View.GONE);
//            noo.setVisibility(View.VISIBLE);
//            tcus.setText("0");
//            tdoc.setText("0");
//            tint.setText("0");
//            tdeb.setText("0");
//        }else {
//            recyclerView.setVisibility(View.VISIBLE);
//            noo.setVisibility(View.GONE);
//            mAdapter = new RecyclerViewAdaptercurrent(getApplicationContext(), Names);
//            recyclerView.setAdapter(mAdapter);
//            tdeb.setText("\u20B9"+String.valueOf(totdeb));
//            tdoc.setText("\u20B9"+String.valueOf(totdoc));
//            tint.setText("\u20B9"+String.valueOf(totins));
//            tcus.setText(String.valueOf(Names.size()));
//        }
    }
    //populateRecyclerView2() - get all NIPNIP user
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerView2() {
//        totdeb = 0;
//        totdoc = 0;
//        totins = 0;
//        sno = 0;
//        Names.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT a.*,b.CID as ID,b.order_id_new,b.debit_type,b.customer_name " +
                "FROM dd_account_debit a INNER JOIN dd_customers b ON b.id = a.customer_id WHERE( a.debit_date BETWEEN ?  AND ?) AND b.tracking_id = ? AND a.active_status = 1 AND b.debit_type = '2' GROUP BY b.id ORDER BY b.order_id_new ASC", new String[]{dateString, dateString1,String.valueOf(ses)});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("ID");
                    String id = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String oid = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_type");
                    Integer debtyp = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String name = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debit = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("document_charge");
                    String docu = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("interest");
                    String inter = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String total = cursor.getString(index);

                    if(docu == null){
                        docu ="0";
                    }
                    if(debit == null){
                        debit ="0";
                    }
                    if(inter == null){
                        inter ="0";
                    }

                    Integer dd = Integer.parseInt(docu);
                    Integer dd1 = Integer.parseInt(debit);
                    Integer dd2 = Integer.parseInt(inter);
                    totdoc = totdoc + dd;
                    totdeb = totdeb + dd1;
                    totins = totins + dd2;
                    sno = sno + 1;
//                    String parsedStr = name.replaceAll("(.{14})", "$1\n");
                    if(debtyp == 2){
                        Names.add(sno+","+name+","+debit+","+docu+","+inter+","+total);
                    }else{
                        Names.add(oid+","+name+","+debit+","+docu+","+inter+","+total);
                    }
                    Log.d("nnn", String.valueOf(Names));
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                cursor.close();
                sqlite.close();
                database.close();
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }
        if(Names.size() == 0){
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    progre();
                }
            });
            recyclerView.setVisibility(View.GONE);
            noo.setVisibility(View.VISIBLE);
            tcus.setText("0");
            tdoc.setText("0");
            tint.setText("0");
            tdeb.setText("0");
        }else {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    progre();
                }
            });
            recyclerView.setVisibility(View.VISIBLE);
            noo.setVisibility(View.GONE);
            mAdapter = new RecyclerViewAdaptercurrent(getApplicationContext(), Names);
            recyclerView.setAdapter(mAdapter);
            tdeb.setText("\u20B9"+String.valueOf(totdeb));
            tdoc.setText("\u20B9"+String.valueOf(totdoc));
            tint.setText("\u20B9"+String.valueOf(totins));
            tcus.setText(String.valueOf(Names.size()));
        }
    }
    //populateRecyclerView1() - get all current and NIP user
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerView1()
    {
        totdeb = 0;
        totdoc = 0;
        totins = 0;
        sno = 0 ;
        Names.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT a.*,b.CID as ID,b.order_id_new,b.debit_type,b.customer_name FROM dd_account_debit a INNER JOIN dd_customers b ON b.id = a.customer_id " +
                "WHERE b.tracking_id = ? AND b.debit_type IN (0,1) AND a.active_status = 1 GROUP BY b.id ORDER BY b.CID ASC", new String[]{String.valueOf(ses)});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("ID");
                    String id = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String oid = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_type");
                    Integer debtyp = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String name = cursor.getString(index);


                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("document_charge");
                    String docu = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("interest");
                    String inter = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String total = cursor.getString(index);
                    if(docu == null){
                        docu ="0";
                    }
                    if(debit == null){
                        debit ="0";
                    }
                    if(inter == null){
                        inter ="0";
                    }

                    Integer dd = Integer.parseInt(docu);
                    Integer dd1 = Integer.parseInt(debit);
                    Integer dd2 = Integer.parseInt(inter);

                    totdoc = totdoc + dd;
                    totdeb = totdeb + dd1;
                    totins = totins + dd2;
                    sno = sno + 1;
//                    String parsedStr = name.replaceAll("(.{14})", "$1\n");
                    if(debtyp == 2){
                        Names.add(id+","+name+","+debit+","+docu+","+inter+","+total);
                    }else{
                        Names.add(id+","+name+","+debit+","+docu+","+inter+","+total);
                    }
//                    Log.d("LOG_TAG_HERE", USER);
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                cursor.close();
                sqlite.close();
                database.close();
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }
        populateRecyclerView3();
//        if(Names.size() == 0){
//            recyclerView.setVisibility(View.GONE);
//            noo.setVisibility(View.VISIBLE);
//            tcus.setText("0");
//            tdoc.setText("0");
//            tint.setText("0");
//            tdeb.setText("0");
//
//        }else {
//            recyclerView.setVisibility(View.VISIBLE);
//            noo.setVisibility(View.GONE);
//            mAdapter = new RecyclerViewAdaptercurrent(getApplicationContext(), Names);
//            recyclerView.setAdapter(mAdapter);
//            tdeb.setText("\u20B9"+String.valueOf(totdeb));
//            tdoc.setText("\u20B9"+String.valueOf(totdoc));
//            tint.setText("\u20B9"+String.valueOf(totins));
//            tcus.setText(String.valueOf(Names.size()));
//        }

    }
    //populateRecyclerView3() - get all NIPNIP user
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerView3()
    {
//        totdeb = 0;
//        totdoc = 0;
//        totins = 0;
//        sno = 0 ;
//        Names.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT a.*,b.id as ID,b.order_id_new,b.debit_type,b.customer_name FROM dd_account_debit a INNER JOIN dd_customers b ON b.id = a.customer_id" +
                " WHERE b.tracking_id = ? AND b.debit_type = '2' AND a.active_status = 1 GROUP BY b.id ORDER BY b.order_id_new ASC", new String[]{String.valueOf(ses)});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("ID");
                    String id = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String oid = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_type");
                    Integer debtyp = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String name = cursor.getString(index);


                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("document_charge");
                    String docu = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("interest");
                    String inter = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String total = cursor.getString(index);
                    if(docu == null){
                        docu ="0";
                    }
                    if(debit == null){
                        debit ="0";
                    }
                    if(inter == null){
                        inter ="0";
                    }

                    Integer dd = Integer.parseInt(docu);
                    Integer dd1 = Integer.parseInt(debit);
                    Integer dd2 = Integer.parseInt(inter);

                    totdoc = totdoc + dd;
                    totdeb = totdeb + dd1;
                    totins = totins + dd2;
                    sno = sno + 1;
//                    String parsedStr = name.replaceAll("(.{14})", "$1\n");
                    if(debtyp == 2){
                        Names.add(oid+","+name+","+debit+","+docu+","+inter+","+total);
                    }else{
                        Names.add(oid+","+name+","+debit+","+docu+","+inter+","+total);
                    }
//                    Log.d("LOG_TAG_HERE", USER);
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                cursor.close();
                sqlite.close();
                database.close();
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }
        if(Names.size() == 0){
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    progre();
                }
            });
            recyclerView.setVisibility(View.GONE);
            noo.setVisibility(View.VISIBLE);
            tcus.setText("0");
            tdoc.setText("0");
            tint.setText("0");
            tdeb.setText("0");

        }else {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    progre();
                }
            });
         Log.d("pop",String.valueOf(Names));
            recyclerView.setVisibility(View.VISIBLE);
            noo.setVisibility(View.GONE);
            mAdapter = new RecyclerViewAdaptercurrent(getApplicationContext(), Names);
            recyclerView.setAdapter(mAdapter);
            tdeb.setText("\u20B9"+String.valueOf(totdeb));
            tdoc.setText("\u20B9"+String.valueOf(totdoc));
            tint.setText("\u20B9"+String.valueOf(totins));
            tcus.setText(String.valueOf(Names.size()));
        }

    }
    //onCreateOptionsMenu() - when navigation menu created
    //Params - Menu
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.back_button, menu);

        return true;
    }
    //progressbar_load() - Load progressbar
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void progressbar_load(){
        //set layout custom
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Log.d("lll","kkl");
        dialog.setContentView(R.layout.progressbar);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.setCancelable(false);
        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }
    //progre() - Stop progressbar
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void progre(){
        dialog.cancel();
    }
    //onOptionsItemSelected() - when navigation item pressed
    //Params - selected item
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_back) {
            Intent back = new Intent(Current_activity.this,ReportActivity.class);
            startActivity(back);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
