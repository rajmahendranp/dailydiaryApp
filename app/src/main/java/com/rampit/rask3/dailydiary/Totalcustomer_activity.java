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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdaptertotalcust;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


//Updated on 25/01/2022 by RAMPIT
//Show all customers
//InternetConnection1() - Check whether internet is on or not
//progressbar_load() - Load progressbar
//progre() - Stop progressbar
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed
//populateRecyclerView() - get all non debit user
//populateRecyclerView1() - get all debit user
//adapter1() -Show recyclerview in adapter
//adapter2() -Show recyclerview in adapter
//adapter3() -Show recyclerview in adapter


public class Totalcustomer_activity extends AppCompatActivity
       {
    Button add;
    EditText datt;
    String dateString,typeid,timing;
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> Debit = new ArrayList<>();
    ArrayList<String> Non_debit = new ArrayList<>();
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    RecyclerView recyclerView;
    Calendar myCalendar;
    TextView to,ddb,ndb,dateee,session,noo;
    Integer ses,sno;
    Spinner type;
    RecyclerViewAdaptertotalcust mAdapter;
    ImageView seesim;
    DatePickerDialog datePickerDialog;
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
        dialog = new Dialog(Totalcustomer_activity.this,R.style.AlertDialogTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_totalcustomers);
        setTitle(R.string.title_activity_totalcustomer);
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
        seesim = findViewById(R.id.sesimg);
        to = findViewById(R.id.total);
        noo = findViewById(R.id.no);
        ddb = findViewById(R.id.debit);
        ndb = findViewById(R.id.nondebit);
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),Totalcustomer_activity.this)) {
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


        session.setText(timing);
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
        List<String> spin = new ArrayList<>();
        String sell = getString(R.string.select);
        String ccred = getString(R.string.debit_customers);
        String ddebb = getString(R.string.non_debit_customers);
        spin.add(sell);
        spin.add(ccred);
        spin.add(ddebb);
        type = findViewById(R.id.spinner);
        if(in == 0){
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    this,R.layout.spinner_design,spin){
                @Override
                public View getDropDownView(int position, View convertView,
                                            ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if(position%2 == 1) {
                        // Set the item text color
                        tv.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.spin3)));
                        // Set the item background color
                        tv.setBackgroundColor((ContextCompat.getColor(getApplicationContext(), R.color.spin2)));
                    }
                    else {
                        // Set the alternate item text color
                        tv.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.spin3)));
                        // Set the alternate item background color
                        tv.setBackgroundColor((ContextCompat.getColor(getApplicationContext(), R.color.spin1)));
                    }
                    return view;
                }
            };
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
            type.setAdapter(spinnerArrayAdapter);


        }else{
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    this,R.layout.spinner_design1,spin){
                @Override
                public View getDropDownView(int position, View convertView,
                                            ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if(position%2 == 1) {
                        // Set the item text color
                        tv.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.spin3)));
                        // Set the item background color
                        tv.setBackgroundColor((ContextCompat.getColor(getApplicationContext(), R.color.spin2)));
                    }
                    else {
                        // Set the alternate item text color
                        tv.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.spin3)));
                        // Set the alternate item background color
                        tv.setBackgroundColor((ContextCompat.getColor(getApplicationContext(), R.color.spin1)));
                    }
                    return view;
                }
            };
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
            type.setAdapter(spinnerArrayAdapter);
        }

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                Integer nu = type
                        .getSelectedItemPosition();
                typeid = String.valueOf(nu);

                if(typeid.equalsIgnoreCase("1"))
                {
                adapter2();
                }else if(typeid.equalsIgnoreCase("2")){
                 adapter3();
                }else if(typeid.equalsIgnoreCase("0")){
                   adapter1();

                }
                Log.d("typedd",String.valueOf(nu));
//

            }

            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }

        });
    }
           //InternetConnection1() - Check whether internet is on or not
           //Params - NULL
           //Created on 25/01/2022
           //Return - NULL
           public static class InternetConnection1 {

               /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
               public static boolean checkConnection(Context context, final Totalcustomer_activity account) {
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
                   Intent back = new Intent(Totalcustomer_activity.this,ReportActivity.class);
                   startActivity(back);
                   finish();
                   return true;
               }

               return super.onOptionsItemSelected(item);
           }

           //populateRecyclerView() - get all non debit user
           //Params - selected item
           //Created on 25/01/2022
           //Return - NULL
    private void populateRecyclerView() {
        Names.clear();
        sno = 0 ;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String[] columns = {"debit_amount",
                "id",""};
        String MY_QUERY1 = "SELECT SUM(a.collection_amount) as paid,b.customer_name,b.id as ID,c.debit_amount,(SELECT collection_amount from dd_collection where collection_date = ? AND customer_id = b.id) AS collect FROM  dd_customers b LEFT JOIN dd_collection a on b.id =  a.customer_id LEFT JOIN dd_account_debit c on b.id = c.customer_id  GROUP BY b.id ";

        String MY_QUERY = "SELECT a.debit_amount,b.customer_name,b.CID as ID,b.location,b.phone_1,b.debit_type,b.order_id_new " +
                "FROM dd_customers b LEFT JOIN dd_account_debit a on b.id =  a.customer_id WHERE b.tracking_id = ? " +
                "AND b.debit_type IN (0,1,3,-1) GROUP BY b.id Order BY b.CID ASC";

        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{String.valueOf(ses)});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String ate = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("location");
                    String locc = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_type");
                    Integer dbb_typ= cursor.getInt(index);

                    index = cursor.getColumnIndexOrThrow("ID");
                    long id = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String order = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    Integer db = cursor.getInt(index);
                    String debittt = cursor.getString(index);

                    if(db == 0 || dbb_typ == 3 || dbb_typ == -1){
                        if (locc == null || locc.equalsIgnoreCase("")) {
                            locc = "no location";
                        }
//                        if(dbb_typ == 2){
//                            Non_debit.add(order+","+ate+","+locc);
//                        }else {
                            Non_debit.add(id+","+ate+","+locc);
//                        }
                    }else{
                        Log.d("ooiioi",order+" "+id);
                        if (locc == null || locc.equalsIgnoreCase("")) {
                            locc = "no location";
                        }
//                        if(dbb_typ == 2){
//                            Debit.add(order+","+ate+","+locc);
//                        }else {
                            Debit.add(id+","+ate+","+locc);
//                        }
                    }
                    if (locc == null || locc.equalsIgnoreCase("")) {
                        locc = "no location";
                    }
                    sno = sno + 1 ;
                    if(dbb_typ == 2){
                        Names.add(id+","+ate+","+locc);
                    }else {
                        Names.add(id + "," + ate + "," + locc);
                    }
                    Log.d("names", String.valueOf(Names));
                    Log.d("names1", String.valueOf(Debit));
                    Log.d("names2", String.valueOf(Non_debit));
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
                String size = String.valueOf(Names.size());
                String dsize = String.valueOf(Debit.size());
                String ndsize = String.valueOf(Non_debit.size());
                Log.d("names", String.valueOf(Names.size()));
                Log.d("names1", String.valueOf(Debit.size()));
                Log.d("names2", String.valueOf(Non_debit.size()));
                to.setText(size);
                ndb.setText(dsize);
                ddb.setText(ndsize);
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
//adapter1();
populateRecyclerView1();
    }
           //populateRecyclerView1() - get all debit user
           //Params - selected item
           //Created on 25/01/2022
           //Return - NULL
           private void populateRecyclerView1() {
//               Names.clear();
//               sno = 0 ;
               sqlite = new SQLiteHelper(this);
               database = sqlite.getReadableDatabase();
               String[] columns = {"debit_amount",
                       "id",""};
               String MY_QUERY1 = "SELECT SUM(a.collection_amount) as paid,b.customer_name,b.id as ID,c.debit_amount,(SELECT collection_amount from dd_collection where collection_date = ? AND customer_id = b.id) AS collect FROM  dd_customers b LEFT JOIN dd_collection a on b.id =  a.customer_id LEFT JOIN dd_account_debit c on b.id = c.customer_id  GROUP BY b.id ";

               String MY_QUERY = "SELECT a.debit_amount,b.customer_name,b.CID as ID,b.location,b.phone_1,b.debit_type,b.order_id_new FROM dd_customers b LEFT JOIN dd_account_debit a on b.id =  a.customer_id WHERE b.tracking_id = ? ANd b.debit_type = '2'  GROUP BY b.id Order BY b.order_id_new ASC";

               Cursor cursor = database.rawQuery(MY_QUERY,new String[]{String.valueOf(ses)});
               if (cursor != null){
                   if(cursor.getCount() != 0){
                       cursor.moveToFirst();
                       do{
                           int index;

                           index = cursor.getColumnIndexOrThrow("customer_name");
                           String ate = cursor.getString(index);

                           index = cursor.getColumnIndexOrThrow("location");
                           String locc = cursor.getString(index);

                           index = cursor.getColumnIndexOrThrow("debit_type");
                           Integer dbb_typ= cursor.getInt(index);

                           index = cursor.getColumnIndexOrThrow("ID");
                           long id = cursor.getLong(index);

                           index = cursor.getColumnIndexOrThrow("order_id_new");
                           String order = cursor.getString(index);

                           index = cursor.getColumnIndexOrThrow("debit_amount");
                           Integer db = cursor.getInt(index);
                           String debittt = cursor.getString(index);
                           Log.d("ooiioi",order);
                           if(db == 0){
                               if (locc == null || locc.equalsIgnoreCase("")) {
                                   locc = "no location";
                               }
                               if(db == 0 || dbb_typ == 3){
                                   Non_debit.add(order+","+ate+","+locc);
                               }else {
                                   Non_debit.add(id+","+ate+","+locc);
                               }

                           }else{
                               if (locc == null || locc.equalsIgnoreCase("")) {
                                   locc = "no location";
                               }
                               if(dbb_typ == 2){
                                   Debit.add(order+","+ate+","+locc);
                               }else {
                                   Debit.add(id+","+ate+","+locc);
                               }
                           }
                           if (locc == null || locc.equalsIgnoreCase("")) {
                               locc = "no location";
                           }
                           sno = sno + 1 ;
                           if(dbb_typ == 2){
                               Names.add(order+","+ate+","+locc);
                           }else {
                               Names.add(order + "," + ate + "," + locc);
                           }
                           Log.d("names", String.valueOf(Names));
                           Log.d("names1", String.valueOf(Debit));
                           Log.d("names2", String.valueOf(Non_debit));
                       }
                       while (cursor.moveToNext());
                       cursor.close();
                       sqlite.close();
                       database.close();
                       String size = String.valueOf(Names.size());
                       String dsize = String.valueOf(Debit.size());
                       String ndsize = String.valueOf(Non_debit.size());
                       to.setText(size);
                       ndb.setText(dsize);
                       ddb.setText(ndsize);

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
               adapter1();

           }
           //adapter1() -Show recyclerview in adapter
           //Params - selected item
           //Created on 25/01/2022
           //Return - NULL
           private void adapter1() {
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
                   mAdapter = new RecyclerViewAdaptertotalcust(getApplicationContext(), Names);
                   recyclerView.setAdapter(mAdapter);
               }
           }
           //adapter2() - Show recyclerview in adapter
           //Params - selected item
           //Created on 25/01/2022
           //Return - NULL
           private void adapter2() {
               if(Debit.size() == 0){
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
                   mAdapter = new RecyclerViewAdaptertotalcust(getApplicationContext(), Debit);
                   recyclerView.setAdapter(mAdapter);
               }
           }

           //adapter3() - Show recyclerview in adapter
           //Params - selected item
           //Created on 25/01/2022
           //Return - NULL
           private void adapter3() {
               if(Non_debit.size() == 0){
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
                   mAdapter = new RecyclerViewAdaptertotalcust(getApplicationContext(), Non_debit);
                   recyclerView.setAdapter(mAdapter);
               }
           }

       }
