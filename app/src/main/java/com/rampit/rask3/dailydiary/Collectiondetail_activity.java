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

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdaptercollectiondetail;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

//Updated on 25/01/2022 by RAMPIT
//Display an users all collections
//InternetConnection1() - Check whether internet is on or not
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed
//populateRecyclerView() - get users all collections


public class Collectiondetail_activity extends AppCompatActivity {
    TextView total;
    EditText datt;
    TextView dcc,intr,dday,ins,ddd,nam1e,dateee,session;
    Button submit,add,edit;
    String ss,Id,tota,timing;
    Integer ses,sll,totalcollect;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    public static String TABLENAME = "REGISTER";
    RecyclerView recyclerView;
    Cursor cursor;
    public static String TABLE_NAME ="NAMES";
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> Col = new ArrayList<>();
    ArrayList<Long> ID = new ArrayList<>();
    ArrayList<Long> IID = new ArrayList<>();
    RecyclerViewAdaptercollectiondetail mAdapter;
    String idd,adpr,edpr,depr,vipr,didd;
    Calendar myCalendar;
    String dateString,balat;
    ImageView seesim;
    Integer in;
    SearchView searchView,datesearch;
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
        setContentView(R.layout.activity_collectiondetail);
        setTitle(R.string.title_activity_collectiondetail);
        ((Callback)getApplication()).datee();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        total = findViewById(R.id.totllll);
        nam1e = findViewById(R.id.nam);
        seesim = findViewById(R.id.sesimg);
        myCalendar = Calendar.getInstance();
//        sqlite = new SQLiteHelper(this);
        Intent name = getIntent();
        idd = name.getStringExtra("ID");
        didd = name.getStringExtra("DID");
       sll = 0;
       totalcollect = 0;
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
        final SharedPreferences.Editor editor = pref.edit();
        ses = pref.getInt("session", 1);
        String dd = pref.getString("Date","");
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),Collectiondetail_activity.this)) {
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
        String sesid = pref.getString("id","");
        String module_i = "7";
        ((Callback)getApplication()).privilege(sesid,module_i);
        adpr = pref.getString("add_privilege","");
        edpr = pref.getString("edit_privilege","");
        depr = pref.getString("delete_privilege","");
        vipr = pref.getString("view_privilege","");
        recyclerView = findViewById(R.id.re);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
populateRecyclerView();
    }
    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Collectiondetail_activity account) {
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
            Intent back = new Intent(Collectiondetail_activity.this,Customer_activity.class);
            startActivity(back);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //populateRecyclerView() - get users all collections
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView() {
        Names.clear();
        Log.d("oopoo",String.valueOf(idd)+" "+String.valueOf(didd));
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();

        String MY_QUERY = "SELECT a.*,b.collection_amount,b.collection_date,b.customer_id FROM dd_customers a" +
                " LEFT JOIN dd_account_debit c on c.customer_id = a.id LEFT JOIN dd_collection b on b.debit_id = c.id  where a.id = ? AND a.tracking_id = ? AND c.active_status = 1  " +
                "ORDER BY b.collection_date DESC ";

        String whereClause = "customer_id=? AND debit_id = ?";
        String[] whereArgs = new String[] {idd,didd};
//        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{idd});
        String[] columns = {"customer_name",
                "id","collection_amount","collection_date","customer_id"};
        String order = "collection_date";
        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{idd,String.valueOf(ses)});
//        Cursor cursor = database.query("dd_collection",
//                columns,
//                whereClause,
//                whereArgs,
//                order,
//                null,
//                order+" DESC");
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("customer_id");
                    long id = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    long iid = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("collection_amount");
                    String dbamount = cursor.getString(index);
                    Integer tt = cursor.getInt(index);

                    index = cursor.getColumnIndexOrThrow("collection_date");
                    String cdate = cursor.getString(index);

                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//                    String formattedDate = df.format(c.getTime());
                    String myFormat1 = "dd/MM/yyyy";//In which you need put here
                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                    if(cdate == null){

                    }else{
                        try {
                            Date debit = df.parse(cdate);
                            dateString = sdf1.format(debit);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

//                    cdate = dateString;
                    if (tt == 0) {
                        nam1e.setText(Name);
                    } else {
                        sll = sll + 1;
                        totalcollect = totalcollect + tt;
                        Names.add(String.valueOf(sll) + "," + dateString + "," + dbamount);
                        ID.add(id);
                        IID.add(iid);
                        Col.add(dbamount);
                        nam1e.setText(Name);
                        Log.d("names", String.valueOf(Names));
                    }
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


        mAdapter = new RecyclerViewAdaptercollectiondetail(edpr,depr,Names,ID,IID,Col,in,getApplicationContext(), Collectiondetail_activity.this);

        recyclerView.setAdapter(mAdapter);
        total.setText("\u20B9"+" "+String.valueOf(totalcollect));
        Log.d("collected",String.valueOf(totalcollect));
    }
    private void populateRecyclerView1() {
        Names.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();

        String MY_QUERY = "SELECT a.* FROM dd_collection where customer_id = ? AND debit_id = ?";
        String whereClause = "customer_id=? AND debit_id = ?";
        String[] whereArgs = new String[] {idd,didd};
//        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{idd});
        String[] columns = {"customer_name",
                "id","collection_amount","collection_date","customer_id"};
        String order = "collection_date";
        Cursor cursor = database.query("dd_collection",
                columns,
                whereClause,
                whereArgs,
                order,
                null,
                order+" DESC");
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("customer_id");
                    long id = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    long iid = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("collection_amount");
                    String dbamount = cursor.getString(index);
                    Integer tt = cursor.getInt(index);

                    index = cursor.getColumnIndexOrThrow("collection_date");
                    String cdate = cursor.getString(index);

                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//                    String formattedDate = df.format(c.getTime());
                    String myFormat1 = "dd/MM/yyyy";//In which you need put here
                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                    try {
                        Date debit = df.parse(cdate);
                        dateString = sdf1.format(debit);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

//                    cdate = dateString;
                    sll = sll + 1;
                    totalcollect = totalcollect + tt;
                    Names.add(String.valueOf(sll)+","+dateString+","+dbamount);
                    ID.add(id);
                    IID.add(iid);
                    Col.add(dbamount);
                    nam1e.setText(Name);

                    Log.d("names", String.valueOf(Names));
                }
                while (cursor.moveToNext());
            }
        }


        mAdapter = new RecyclerViewAdaptercollectiondetail(edpr,depr,Names,ID,IID,Col,in,getApplicationContext(), Collectiondetail_activity.this);

        recyclerView.setAdapter(mAdapter);
        total.setText("\u20B9"+" "+String.valueOf(totalcollect));
        Log.d("collected",String.valueOf(totalcollect));
    }
}
