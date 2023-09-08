package com.rampit.rask3.dailydiary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdapterclosing;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

//Updated on 25/01/2022 by RAMPIT
//Close an debit manually
//InternetConnection1() - Check whether internet is on or not
//onBackPressed() - function called when back button is pressed
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed
//populateRecyclerView() - get all debit from userid
//close_deb() - manually close an debit
//progressbar_load() - Load progressbar
//progre() - Stop progressbar



public class Closing_account_activity extends AppCompatActivity {
    TextView bala,tota,dateee,session,noo;
    ArrayList<String> Names = new ArrayList<>();
    Button search;
    Integer balll,ses,sno;
    String dateString,timing;
    Calendar myCalendar;
    SQLiteHelper sqlite;
    RecyclerView recyclerView;
    SQLiteDatabase database;
    RecyclerViewAdapterclosing mAdapter;
    ImageView seesim;
    Dialog dialog;
    Integer showinng = 0;
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
        dialog = new Dialog(com.rampit.rask3.dailydiary.Closing_account_activity.this,R.style.AlertDialogTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_nip);
        setTitle(R.string.close);
        ((Callback)getApplication()).datee();
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.re);
        seesim = findViewById(R.id.sesimg);
        tota = findViewById(R.id.total);
        noo = findViewById(R.id.no);
        bala = findViewById(R.id.balance);
        myCalendar = Calendar.getInstance();
        ses = pref.getInt("session", 1);
        String dd = pref.getString("Date","");
        dateee = findViewById(R.id.da);
        session = findViewById(R.id.sess);
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),Closing_account_activity.this)) {
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
//        sqlite = new SQLiteHelper(this);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat dff = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c.getTime());
        String fordate = dff.format(c.getTime());
//        from.setText(fordate);
        dateString = formattedDate;
        balll =0;
        Intent totalaccounts = getIntent();
        final String iidd = totalaccounts.getStringExtra("cid");
//        populateRecyclerView();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        populateRecyclerView(iidd);
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
        public static boolean checkConnection(Context context, final Closing_account_activity account) {
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


    //onBackPressed() - function called when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onBackPressed() {
        Intent back = new Intent(com.rampit.rask3.dailydiary.Closing_account_activity.this,Totalaccounts.class);
        startActivity(back);
        finish();
        super.onBackPressed();
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
            Intent back = new Intent(com.rampit.rask3.dailydiary.Closing_account_activity.this,ReportActivity.class);
            startActivity(back);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //populateRecyclerView() - get all debit from userid
    //Params - selected item
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerView(String iidd) {
        Log.d("iiddiidd",iidd);
        Names.clear();
        balll = 0;
        sno = 0;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
//        Cursor cursor =database.rawQuery("SELECT SUM(a.collection_amount) as paid,b.customer_name,b.CID as ID,b.debit_type,b.order_id,c.*,(c.debit_amount-SUM(a.collection_amount)) as balance" +
//                " FROM dd_customers b LEFT JOIN dd_collection a on b.id =  a.customer_id LEFT JOIN dd_account_debit c on b.id = c.customer_id " +
//                "WHERE b.debit_type ='2' AND b.tracking_id = ? GROUP BY b.id ORDER BY b.order_id;",new String[]{String.valueOf(ses)});
        Cursor cursor =database.rawQuery("SELECT cus.*,deb.customer_id,col.collection_amount as collect,sum(col.collection_amount)as paid ,deb.debit_amount,deb.debit_date,deb.id as did FROM dd_customers cus " +
                " LEFT JOIN dd_account_debit deb on deb.customer_id = cus.id " +
                "  LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id from dd_collection  ) col on  deb.id = col.debit_id  " +
                "  WHERE  deb.active_status = 1 AND cus.id = ? GROUP BY deb.id ORDER BY cus.order_id_new ASC;",new String[]{iidd});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("did");
                    String id = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String oid = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_type");
                    Integer debtyp = cursor.getInt(index);

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("paid");
                    String total = cursor.getString(index);
                    if(total == null){
                        total ="0";
                    }
                    if(debit == null){
                        debit ="0";
                    }
                    Integer vaa = Integer.parseInt(debit)-Integer.parseInt(total);
                    balll = balll + vaa;
                    sno = sno + 1 ;
                    if(debtyp == 2){
                        Names.add(iidd +","+ name +","+debit +","+ total+","+vaa +"," + id);
                    }else{
                        Names.add(iidd +","+ name +","+debit +","+ total+","+vaa +"," + id);
                    }
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
                mAdapter = new RecyclerViewAdapterclosing(getApplicationContext(), Names , com.rampit.rask3.dailydiary.Closing_account_activity.this);
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

    //close_deb() - manually close an debit
    //Params - debit id
    //Created on 25/01/2022
    //Return - NULL
    public void close_deb(String did){
        SQLiteHelper sqlite = new SQLiteHelper(this);
        SQLiteDatabase database = sqlite.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("active_status","0");
        database.update("dd_account_debit", cv, "id = ? ", new String[]{did});
        sqlite.close();
        database.close();
        onBackPressed();
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
}

