package com.rampit.rask3.dailydiary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdapteremployeepay;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


//Updated on 25/01/2022 by RAMPIT
//used show employee payment details
//InternetConnection1() - Check whether internet is on or not
//populateRecyclerView1() -get en employee data
//populateRecyclerView() - get employee payment details
//adapter1() - set data to an adapter
//adapter2() - set data to an adapter
//adapter3() - set data to an adapter
//onBackPressed() - function called when back button is pressed
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed

public class Employee_payment extends AppCompatActivity {

    Button addd;
    RecyclerView recyclerView;
    String idid,timing,tyyyy,adpr,edpr,depr,vipr,datedd,typeid;
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> Credit = new ArrayList<>();
    ArrayList<String> Debit = new ArrayList<>();
    RecyclerViewAdapteremployeepay mAdapter;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    Integer ses,iiddii;
    TextView dateee,session,namee,noo;
    ImageView seesim;
    Spinner type;
    Integer in,showinng = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ii = pref.getString("theme","");
        if(ii.equalsIgnoreCase("")){
            ii = "1";
        }
        Log.d("thee",ii);
         in = Integer.valueOf(ii) ;
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
        setContentView(R.layout.employee_payment);
        setTitle(R.string.employee_payment);
        ((Callback)getApplication()).datee();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addd = findViewById(R.id.add);
        type = findViewById(R.id.spinner);
        namee = findViewById(R.id.nam);
        iiddii = 0;
        TextView dayy =(TextView)findViewById(R.id.day);
        noo = findViewById(R.id.no);
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

        seesim = findViewById(R.id.sesimg);
        Intent name = getIntent();
        idid = name.getStringExtra("ID");
        if(idid == null){

        }else {
        Log.d("ididd",idid);
        }recyclerView = findViewById(R.id.re);
        String sesid = pref.getString("id","");
        String module_i = "11";
        ((Callback)getApplication()).privilege(sesid,module_i);
        adpr = pref.getString("add_privilege","");
        edpr = pref.getString("edit_privilege","");
        depr = pref.getString("delete_privilege","");
        vipr = pref.getString("view_privilege","");
        if(adpr.equalsIgnoreCase("0")){
            addd.setVisibility(View.GONE);
        }

        ses = pref.getInt("session", 1);
        String dd = pref.getString("Date","");
        dateee = findViewById(R.id.da);
        session = findViewById(R.id.sess);
        dateee.setText(dd);
        if(in == 0){
            addd.setBackgroundResource(R.drawable.add);
            if(ses == 1){
                seesim.setBackgroundResource(R.drawable.sun);
                timing = getString(R.string.morning);
            }else if(ses == 2){
                seesim.setBackgroundResource(R.drawable.moon);
                timing = getString(R.string.evening);
            }
        }else{
            addd.setBackgroundResource(R.drawable.add_blue);
            if(ses == 1){
                seesim.setBackgroundResource(R.drawable.sun_blue);
                timing = getString(R.string.morning);
            }else if(ses == 2){
                seesim.setBackgroundResource(R.drawable.moon_blue);
                timing = getString(R.string.evening);
            }
        }
        session.setText(timing);
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),Employee_payment.this)) {
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
//        sqlite = new SQLiteHelper(this);
        addd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent na = new Intent(Employee_payment.this,Add_employee_payment.class);
                na.putExtra("ID",idid);
                startActivity(na);
                finish();
            }
        });
        List<String> spin = new ArrayList<>();
        String sell = getString(R.string.select);
        String ccred = getString(R.string.credit21);
        String ddebb = getString(R.string.debit21);

        spin.add(sell);
        spin.add(ccred);
        spin.add(ddebb);
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
//        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
//                android.R.layout.simple_spinner_dropdown_item, Names);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                // Object item = parentView.getItemAtPosition(position);

                Integer nu = type
                        .getSelectedItemPosition();
                typeid = String.valueOf(nu);

                if(typeid.equalsIgnoreCase("1"))
                {
                    adapter2();
//                    populateRecyclerView1();
                }else if(typeid.equalsIgnoreCase("2")){
//                    populateRecyclerView2();
                    adapter3();
                }else if(typeid.equalsIgnoreCase("0")){
                    adapter1();
                }
                Log.d("typedd",String.valueOf(nu));
            }
            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }

        });
        populateRecyclerView1();
        populateRecyclerView();
    }
    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Employee_payment account) {
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

    //populateRecyclerView1() -get en employee data
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView1() {
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY = "SELECT * FROM  dd_employee WHERE id = ?";

        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{idid});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("name");
                    String Name = cursor.getString(index);
                    namee.setText(Name);
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
        adapter1();
//        mAdapter = new RecyclerViewAdapteremployeepay(Names,getApplicationContext(),edpr,depr);
//        recyclerView.setAdapter(mAdapter);
    }

    //populateRecyclerView() - get employee payment details
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView() {
        iiddii = 0;
        Names.clear();
        Credit.clear();
        Debit.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY = "SELECT e.*,b.id as ID,b.name FROM dd_employee_payment e INNER JOIN dd_employee b on b.id = e.employee_id WHERE e.tracking_id = ? AND e.employee_id = ?";

        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{String.valueOf(ses),idid});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("name");
                    String Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("amount");
                    String amou = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("acc_type_id");
                    String typ = cursor.getString(index);
//                    created_date
                    index = cursor.getColumnIndexOrThrow("created_date");
                    String date = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("employee_id");
                    String emploo = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("id");
                    long id = cursor.getLong(index);


                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date debit = df.parse(date);
                         datedd = df1.format(debit);
//                        dated1 = df.format(debit);
                        Log.d("deae",datedd);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    iiddii = iiddii + 1;
                    namee.setText(Name);
                    if(typ.equalsIgnoreCase("7")){
                        String ii = getString(R.string.credit21);
                        tyyyy = ii;
                        Credit.add(id+","+datedd+","+tyyyy+","+amou+","+iiddii+","+emploo);
                    }else if(typ.equalsIgnoreCase("18")){
                        String ii = getString(R.string.debit21);
                        tyyyy = ii;
                        Debit.add(id+","+datedd+","+tyyyy+","+amou+","+iiddii+","+emploo);
                    }
                    Names.add(id+","+datedd+","+tyyyy+","+amou+","+iiddii+","+emploo);

                    Log.d("names", String.valueOf(Names));
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
adapter1();
//        mAdapter = new RecyclerViewAdapteremployeepay(Names,getApplicationContext(),edpr,depr);
//        recyclerView.setAdapter(mAdapter);
    }

    //adapter1() - set data to an adapter
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void adapter1(){
        if(Names.size() == 0){
            recyclerView.setVisibility(View.GONE);
            noo.setVisibility(View.VISIBLE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            noo.setVisibility(View.GONE);
            mAdapter = new RecyclerViewAdapteremployeepay(Names,in,getApplicationContext(),edpr,depr);
            recyclerView.setAdapter(mAdapter);
//            mAdapter.filter(dateString);
        }
    }
    //adapter2() - set data to an adapter
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void adapter2(){
        if(Credit.size() == 0){
            recyclerView.setVisibility(View.GONE);
            noo.setVisibility(View.VISIBLE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            noo.setVisibility(View.GONE);
            mAdapter = new RecyclerViewAdapteremployeepay(Credit, in, getApplicationContext(),edpr,depr);
            recyclerView.setAdapter(mAdapter);
//            mAdapter.filter(dateString);
        }
    }
    //adapter3() - set data to an adapter
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void adapter3(){
        if(Debit.size() == 0){
            recyclerView.setVisibility(View.GONE);
            noo.setVisibility(View.VISIBLE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            noo.setVisibility(View.GONE);
            mAdapter = new RecyclerViewAdapteremployeepay(Debit, in, getApplicationContext(),edpr,depr);
            recyclerView.setAdapter(mAdapter);
//            mAdapter.filter(dateString);
        }
    }
    //onBackPressed() - function called when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onBackPressed() {
        Intent nn = new Intent(Employee_payment.this,Employee_details.class);
        startActivity(nn);
        finish();
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
            Intent nn = new Intent(Employee_payment.this,Employee_details.class);
            startActivity(nn);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
