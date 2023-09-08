package com.rampit.rask3.dailydiary;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdapter_remaining;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdaptercollection;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdaptercurrent;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdapternip;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdaptertoday;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.DecimalFormat;
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
//Show Extra Collection amount
//progressbar_load() - Load progressbar
//progre() - Stop progressbar
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed
//populateRecyclerView() - get and show balance amounts

public class Remaining extends AppCompatActivity {
    TextView bala,tota,noo,dateee,session;
    ArrayList<String> Names = new ArrayList<>();
    Button search;
    Integer balll,ses,ball11,ball2;
    String dateString,timing;
    Calendar myCalendar;
    SQLiteHelper sqlite;
    RecyclerView recyclerView;
    SQLiteDatabase database;
    RecyclerViewAdapter_remaining mAdapter;
    ImageView seesim;
    Integer sno;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ii = pref.getString("theme","");
        if(ii.equalsIgnoreCase("")){
            ii = "1";
        }
        Log.d("thee",ii);
        Integer in = Integer.valueOf(ii) ;
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dialog = new Dialog(Remaining.this,R.style.AlertDialogTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_remaining);
        setTitle(R.string.title_activity_NIP);
        ((Callback)getApplication()).datee();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.re);
        tota = findViewById(R.id.total);
        seesim = findViewById(R.id.sesimg);
        noo = findViewById(R.id.no);
        bala = findViewById(R.id.balance);
        myCalendar = Calendar.getInstance();
//        sqlite = new SQLiteHelper(this);
        Calendar c = Calendar.getInstance();
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

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat dff = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c.getTime());
        String fordate = dff.format(c.getTime());
//        from.setText(fordate);
        dateString = formattedDate;
        balll =0;
        ball11 = 0;
        ball2 = 0;
        ses = pref.getInt("session", 1);
        String dd = pref.getString("Date","");
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
        ((Callback)getApplication()).NIP(ses);
//        populateRecyclerView();
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
            Intent back = new Intent(Remaining.this,ReportActivity.class);
            startActivity(back);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //populateRecyclerView() - get and show balance amounts
    //Params - selected item
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerView() {
        Names.clear();
        balll = 0 ;
        ball11 = 0 ;
        ball2 = 0 ;
        sno = 0;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT *,SUM(amount) as amm FROM dd_remaining WHERE tracking_id = ? GROUP BY customer_id",new String[]{String.valueOf(ses)});
        Cursor cursor1 =database.rawQuery("SELECT cus.*,deb.customer_id,col.collection_amount as collect,sum(col.collection_amount)as paid ,deb.debit_amount,deb.debit_date,deb.id as did FROM dd_customers cus " +
                " LEFT JOIN dd_account_debit deb on deb.customer_id = cus.id " +
                "  LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id from dd_collection WHERE collection_date <= ?  ) col on  deb.id = col.debit_id  " +
                "  WHERE cus.tracking_id = ? AND deb.debit_date <= ? AND deb.active_status = 1 AND  cus.debit_type IN(1) GROUP BY cus.id ORDER BY cus.order_id_new ASC;",new String[]{dateString, String.valueOf(ses),dateString});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("id");
                    String id = cursor.getString(index);
                    Log.d("iiidddd",id);
                    index = cursor.getColumnIndexOrThrow("CID");
                    String CID = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("customer_id");
                    String oid = cursor.getString(index);


                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("amm");
                    String debit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("created_date");
                    String total = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_id");
                    String total1 = cursor.getString(index);


                    if(total == null){
                        total ="0";
                    }
                    if(total1 == null){
                        total1 ="0";
                    }
                    Log.d("or21",total1);
                    if(debit == null){
                        debit ="0";
                    }
                    sno = sno + 1 ;
                    Integer vaa = Integer.parseInt(debit);
                    balll = balll + vaa;
                    ball11 = ball11 + Integer.parseInt(debit) ;
                    ball2 = ball2;
//                    if(debtyp == 2){
                    Names.add(CID +","+ name +","+debit +","+ total+","+vaa);
//                    }else{
//                        Names.add(CID +","+ name +","+debit +","+ total+","+vaa);
//                    }
                    Log.d("LOG_TAG_HERE", String.valueOf(balll));
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


            String size = String.valueOf(Names.size());
            Log.d("nipp",ball11+" "+ball2);
            String bsize = String.valueOf(balll);
            tota.setText(size);
            bala.setText("\u20B9"+bsize);
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
                mAdapter = new RecyclerViewAdapter_remaining(getApplicationContext(), Names);
                recyclerView.setAdapter(mAdapter);
            }
        }else{
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    progre();
                }
            });
            cursor.close();
            sqlite.close();
            database.close();
        }
    }

}

