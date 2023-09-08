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
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdaptertoday;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;



//Updated on 25/01/2022 by RAMPIT
//Show today collection
//InternetConnection1() - Check whether internet is on or not
//progressbar_load() - Load progressbar
//progre() - Stop progressbar
//updateLabel() - Update textview when date is selected
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed
//populateRecyclerView() - get current and NIP users today collection
//populateRecyclerView1() - get NIPNIP users today collection
//adapter1() - set recyclerview to an adapter
//adapter2() - set recyclerview to an adapter
//adapter3() - set recyclerview to an adapter
//list1() - get today collection amount


public class Todaycollection_activity extends AppCompatActivity {
    TextView from,to,dateee,session,noo,totalcus,totalcoll,paidcu,notpaidcu;
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> Paid = new ArrayList<>();
    ArrayList<String> Nopaid = new ArrayList<>();
    Button search,clear;
    String dateString,dateString1,timing,typeid;
    Calendar myCalendar;
    Integer ses,collection,sno;
    SQLiteHelper sqlite;
    RecyclerView recyclerView;
    SQLiteDatabase database;
    RecyclerViewAdaptertoday mAdapter;
    ImageView seesim;
    Spinner type;
    Integer dabamo;
    String xcol;
    DatePickerDialog datePickerDialog;
    Dialog dialog ;
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
        dabamo = 0;
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        dialog = new Dialog(Todaycollection_activity.this,R.style.AlertDialogTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        res.updateConfiguration(conf, dm);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_todaycollection);
        setTitle(R.string.title_activity_todaycollection);
        ((Callback)getApplication()).datee();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        from = findViewById(R.id.date);
        recyclerView = findViewById(R.id.re);
        seesim = findViewById(R.id.sesimg);
        search = findViewById(R.id.search);
        clear = findViewById(R.id.clear);
        totalcoll = findViewById(R.id.totcol);
        totalcus = findViewById(R.id.totcus);
        paidcu = findViewById(R.id.paid);
        notpaidcu = findViewById(R.id.notpaid);

        noo = findViewById(R.id.no);
        myCalendar = Calendar.getInstance();
//        sqlite = new SQLiteHelper(this);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat dff = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c.getTime());
        String fordate = dff.format(c.getTime());
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),Todaycollection_activity.this)) {
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
        from.setText(fordate);
        dateString = formattedDate;
        ses = pref.getInt("session", 1);
        String dd = pref.getString("Date","");
        dateee = findViewById(R.id.da);
        session = findViewById(R.id.sess);
        dateee.setText(dd);
        String myFormat = "yyyy/MM/dd";
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

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
//        list1();
//        populateRecyclerView();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list1();
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
                    datePickerDialog =  new DatePickerDialog(Todaycollection_activity.this,R.style.DialogTheme, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH ));
                }else{
                    datePickerDialog =  new DatePickerDialog(Todaycollection_activity.this,R.style.DialogTheme1, date, myCalendar
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

        List<String> spin = new ArrayList<>();
        String sell = getString(R.string.select);
        String ccred = getString(R.string.paid_customers);
        String ddebb = getString(R.string.nopaid_customers);
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

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("bothdates",dateString+" "+dateString1);
                type.setSelection(0);
//                list1();
//                populateRecyclerView();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                list1();
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
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar oi = Calendar.getInstance();
                dateString = sdf.format(oi.getTime());
                from.setText("");
                type.setSelection(0);
//                list1();
//                populateRecyclerView();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                list1();
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
        });
    }
    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Todaycollection_activity account) {
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
            Intent back = new Intent(Todaycollection_activity.this,ReportActivity.class);
            startActivity(back);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //populateRecyclerView() - get current and NIP users today collection
    //Params - selected item
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerView() {
        Names.clear();
        Paid.clear();
        Nopaid.clear();
        collection = 0;
        sno = 0;
        Log.d("ddd33",dateString);
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
//        Cursor cursor =database.rawQuery("SELECT c.debit_amount,b.customer_name,b.CID as ID,b.order_id,b.debit_type,(SELECT collection_amount from dd_collection where collection_date = ? AND customer_id = b.id) AS collect FROM  dd_customers b  " +
//                "LEFT JOIN dd_account_debit c on b.id = c.customer_id LEFT JOIN dd_collection a on c.id = a.debit_id WHERE b.tracking_id = ? AND c.active_status = 1 AND b.debit_type IN (0,1,2) GROUP BY b.id ORDER BY b.order_id ASC;",new String[]{dateString, String.valueOf(ses)});
        Cursor cursor =database.rawQuery("SELECT cus.*,deb.customer_id,col.collection_amount as collect,sum(col.collection_amount)as paid ,deb.debit_amount,deb.debit_date,deb.id as did FROM dd_customers cus " +
                " LEFT JOIN dd_account_debit deb on deb.customer_id = cus.id " +
                "  LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id from dd_collection WHERE collection_date = ? " +
                "GROUP BY customer_id ) col on  deb.id = col.debit_id  AND col.collection_date = ?" +
                "  WHERE cus.tracking_id = ?  AND deb.active_status = 1 AND  cus.debit_type IN(0,1) GROUP BY cus.id ORDER BY cus.CID ASC;",
                new String[]{dateString,dateString, String.valueOf(ses)});
//      if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("CID");
                    String id = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String oid = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_type");
                    Integer debtyp = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("collect");
                    String total = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debbbb = cursor.getString(index);
                    sno = sno + 1;
if(total == null || total.equalsIgnoreCase("0")){
    total = "0";
    Log.d("todayc",String.valueOf(total));
    if(debtyp == 2){
        Nopaid.add(id+","+name+","+total);
    }else{
        Nopaid.add(id+","+name+","+total);
    }

}else{
    Log.d("todayc1",String.valueOf(total));
    if(debtyp == 2){
        Paid.add(id+","+name+","+total);
    }else{
        Paid.add(id+","+name+","+total);
    }
}

collection = collection +Integer.valueOf(total);
                    Log.d("todayc2",String.valueOf(collection));

                    if(debbbb == null){

                    }else{
                        if(debtyp == 2){
                            Names.add(id+","+name+","+total);
                        }else{
                            Names.add(id+","+name+","+total);
                        }
                }
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }else{
//                runOnUiThread(new Runnable()
//                {
//                    @Override
//                    public void run()
//                    {
//                        progre();
//                    }
//                });
                cursor.close();
                sqlite.close();
                database.close();
            }

       populateRecyclerView1();

    }

    //populateRecyclerView1() - get NIPNIP users today collection
    //Params - selected item
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerView1() {
//        Names.clear();
//        Paid.clear();
//        Nopaid.clear();
//        collection = 0;
//        sno = 0;
        Log.d("todayc211",String.valueOf(collection));
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
//        Cursor cursor =database.rawQuery("SELECT c.debit_amount,b.customer_name,b.CID as ID,b.order_id,b.debit_type,(SELECT collection_amount from dd_collection where collection_date = ? AND customer_id = b.id) AS collect FROM  dd_customers b  " +
//                "LEFT JOIN dd_account_debit c on b.id = c.customer_id LEFT JOIN dd_collection a on c.id = a.debit_id WHERE b.tracking_id = ? AND c.active_status = 1 AND b.debit_type IN (0,1,2) GROUP BY b.id ORDER BY b.order_id ASC;",new String[]{dateString, String.valueOf(ses)});
        Cursor cursor =database.rawQuery("SELECT cus.*,deb.customer_id,col.collection_amount as collect,sum(col.collection_amount)as paid ,deb.debit_amount,deb.debit_date,deb.id as did FROM dd_customers cus " +
                " LEFT JOIN dd_account_debit deb on deb.customer_id = cus.id " +
                "  LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id from dd_collection WHERE collection_date = ? GROUP BY customer_id ) col on  deb.id = col.debit_id  AND col.collection_date = ?" +
                "  WHERE cus.tracking_id = ? AND deb.debit_date <= ? AND deb.active_status = 1 AND  cus.debit_type IN(2) GROUP BY cus.id ORDER BY cus.order_id_new ASC;",new String[]{dateString,dateString, String.valueOf(ses),dateString});
//      if (cursor != null) {
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                int index;

                index = cursor.getColumnIndexOrThrow("CID");
                String id = cursor.getString(index);
                index = cursor.getColumnIndexOrThrow("order_id_new");
                String oid = cursor.getString(index);
                index = cursor.getColumnIndexOrThrow("debit_type");
                Integer debtyp = cursor.getInt(index);
                index = cursor.getColumnIndexOrThrow("customer_name");
                String name = cursor.getString(index);

                index = cursor.getColumnIndexOrThrow("collect");
                String total = cursor.getString(index);
                index = cursor.getColumnIndexOrThrow("debit_amount");
                String debbbb = cursor.getString(index);
                sno = sno + 1;
                if(total == null || total.equalsIgnoreCase("0")){
                    total = "0";
                    if(debtyp == 2){
                        Nopaid.add(oid+","+name+","+total);
                    }else{
                        Nopaid.add(oid+","+name+","+total);
                    }

                }else{
                    if(debtyp == 2){
                        Paid.add(oid+","+name+","+total);
                    }else{
                        Paid.add(oid+","+name+","+total);
                    }
                }

                collection = collection +Integer.valueOf(total);
                if(debbbb == null){

                }else{
                    if(debtyp == 2){
                        Names.add(oid+","+name+","+total);
                    }else{
                        Names.add(oid+","+name+","+total);
                    }
                }
            }
            while (cursor.moveToNext());
            cursor.close();
            sqlite.close();
            database.close();
        }else{
//            runOnUiThread(new Runnable()
//            {
//                @Override
//                public void run()
//                {
//                    progre();
//                }
//            });
            cursor.close();
            sqlite.close();
            database.close();
        }

        adapter1();

    }
    //adapter1() - set recyclerview to an adapter
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
            totalcus.setText("0");
            totalcoll.setText("0");
            paidcu.setText("0");
            notpaidcu.setText("0");
        }else {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    progre();
                }
            });
            Log.d("coll22",String.valueOf(collection));
//            collection = 0;
Integer coll = collection  + dabamo ;
            Log.d("coll21",String.valueOf(coll));
            totalcus.setText(String.valueOf(Names.size()));
            totalcoll.setText(String.valueOf(coll));
            paidcu.setText(String.valueOf(Paid.size()));
            notpaidcu.setText(String.valueOf(Nopaid.size()));
            recyclerView.setVisibility(View.VISIBLE);
            noo.setVisibility(View.GONE);
            mAdapter = new RecyclerViewAdaptertoday(getApplicationContext(), Names);
            recyclerView.setAdapter(mAdapter);
        }
    }
    //adapter2() - set recyclerview to an adapter
    //Params - selected item
    //Created on 25/01/2022
    //Return - NULL
    private void adapter2() {
        if(Paid.size() == 0){
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
            totalcus.setText("0");
            totalcoll.setText("0");
            paidcu.setText("0");
            notpaidcu.setText("0");
        }else {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    progre();
                }
            });
//            collection = 0;
//            collection = collection + dabamo;
            Integer coll = collection  + dabamo ;
            Log.d("coll22",String.valueOf(coll));
            totalcus.setText(String.valueOf(Names.size()));
            totalcoll.setText(String.valueOf(coll));
            paidcu.setText(String.valueOf(Paid.size()));
            notpaidcu.setText(String.valueOf(Nopaid.size()));
            recyclerView.setVisibility(View.VISIBLE);
            noo.setVisibility(View.GONE);
            mAdapter = new RecyclerViewAdaptertoday(getApplicationContext(), Paid);
            recyclerView.setAdapter(mAdapter);
        }
    }
    //adapter3() - set recyclerview to an adapter
    //Params - selected item
    //Created on 25/01/2022
    //Return - NULL
    private void adapter3() {
        if(Nopaid.size() == 0){
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
            totalcus.setText("0");
            totalcoll.setText("0");
            paidcu.setText("0");
            notpaidcu.setText("0");
        }else {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    progre();
                }
            });
//            collection = 0;
//            collection = collection + dabamo;
            Integer coll = collection  + dabamo ;
            Log.d("coll23",String.valueOf(coll));
            totalcus.setText(String.valueOf(Names.size()));
            totalcoll.setText(String.valueOf(coll));
            paidcu.setText(String.valueOf(Paid.size()));
            notpaidcu.setText(String.valueOf(Nopaid.size()));
            recyclerView.setVisibility(View.VISIBLE);
            noo.setVisibility(View.GONE);
            mAdapter = new RecyclerViewAdaptertoday(getApplicationContext(), Nopaid);
            recyclerView.setAdapter(mAdapter);
        }
    }
    //list1() - get today collection amount
    //Params - selected item
    //Created on 25/01/2022
    //Return - NULL
    public void list1(){
        dabamo = 0;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        String mm = "SELECT a.customer_name,a.id as ID,a.location,a.phone_1,a.debit_type_updated,b.*,c.vv as collect,c.debit_id " +
                " ,a.debit_type FROM dd_customers  a " +
                "LEFT JOIN dd_account_debit b on b.customer_id = a.id "  +
                "LEFT JOIN (SELECT SUM(collection_amount) as vv,debit_id,collection_date from dd_collection WHERE collection_date = ? GROUP BY debit_id ORDER BY debit_id  ) c ON  b.id = c.debit_id  WHERE a.tracking_id = ? AND  b.active_status = 0 AND c.collection_date = ?  GROUP BY b.id ORDER BY b.id";
        String MY_QUERY = "SELECT b.*,a.customer_name,a.id as ID,a.location,a.phone_1,sum(c.collection_amount) as collect,a.debit_type_updated FROM dd_account_debit b LEFT JOIN dd_customers a on  a.id = b.customer_id  " +
                " Left JOIN (SELECT collection_amount,collection_date,customer_id,debit_id,discount from dd_collection GROUP BY debit_id ) c on c.debit_id = b.id  WHERE  a.tracking_id = ?  AND b.active_status = 0 GROUP BY b.id ";
//   String mm = "SELECT b.*,sum(c.co)";
        Cursor cursor = database.rawQuery(mm, new String[]{dateString,String.valueOf(ses),dateString});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("collect");
                    xcol = cursor.getString(index);
                    String vc = cursor.getString(index);
                    Log.d("ins3",vc);
                    if(vc == null ){
                        vc = "0";
                    }
                    Integer d1 = Integer.parseInt(vc);
                    dabamo = dabamo + d1;

//                    clobal.setText("\u20B9"+""+String.valueOf(dabamo));
//                      deb12.setText(String.valueOf(dabamo));
//                    doc.setText(String.valueOf(docamo));
//                    inst.setText(String.valueOf(intamo));
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                cursor.close();
                sqlite.close();
                database.close();
//                clobal.setText("\u20B9"+""+"0");
//                doc.setText("0");
//                inst.setText("0");
            }
        }else{
//            runOnUiThread(new Runnable()
//            {
//                @Override
//                public void run()
//                {
//                    progre();
//                }
//            });
            cursor.close();
            sqlite.close();
            database.close();
//            clobal.setText("\u20B9"+""+"0");
//            doc.setText("0");
//            inst.setText("0");
        }

    }
}
