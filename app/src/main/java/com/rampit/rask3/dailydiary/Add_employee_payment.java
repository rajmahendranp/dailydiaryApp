package com.rampit.rask3.dailydiary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdapteremployee;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdapteremployeepay;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


//Updated on 25/01/2022 by RAMPIT
//used to give debit to new employee by admin
//InternetConnection1() - Check whether internet is on or not
//add() - Add new payment for an employee
//populateRecyclerView() - Show all employees
//onCreateOptionsMenu() - when navigation menu created
//onBackPressed() - function called when back button is pressed
//onOptionsItemSelected() - when navigation item pressed


public class Add_employee_payment extends AppCompatActivity {

    RadioButton cred,debb;
    Button addddd,cancc;
    String actype,idd,timing,IdD,typ;
    EditText amou,nam,ress;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    Integer ses;
    TextView dateee,session,payin;
    ImageView seesim;
    Integer in;
    RadioButton vara,sel;
    DatePickerDialog datePickerDialog;
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
        setContentView(R.layout.add_employee_payment);
        setTitle(R.string.add_employee_payment);
        ((Callback)getApplication()).datee();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addddd = findViewById(R.id.addemploo);
        cancc = findViewById(R.id.cancel);
        cred = findViewById(R.id.crd);
        debb = findViewById(R.id.deb);
        payin = findViewById(R.id.info);
        amou = findViewById(R.id.amount);
        seesim = findViewById(R.id.sesimg);
        ress = findViewById(R.id.emp_reas);
        nam = findViewById(R.id.emp_name);
        vara = findViewById(R.id.crd);
        sel = findViewById(R.id.deb);
        if(in == 0){
            vara.setButtonDrawable(R.drawable.custom_radio);
            sel.setButtonDrawable(R.drawable.custom_radio);
        }else{
            vara.setButtonDrawable(R.drawable.custom_radio1);
            sel.setButtonDrawable(R.drawable.custom_radio1);
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),Add_employee_payment.this)) {
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
        ses = pref.getInt("session", 1);
        String dd = pref.getString("Date","");
        actype = "0";
        dateee = findViewById(R.id.da);
        session = findViewById(R.id.sess);
        dateee.setText(dd);
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
        Intent na = getIntent();
        idd = na.getStringExtra("ID");
//        populateRecyclerView();
        cred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actype = "7";
                debb.setChecked(false);
            }
        });
        debb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actype = "18";
                cred.setChecked(false);
            }
        });
        addddd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adddd = amou.getText().toString();
                if(adddd.equalsIgnoreCase("") || actype.equalsIgnoreCase("0")){
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(Add_employee_payment.this,R.style.AlertDialogTheme);
                    String enn = getString(R.string.enter_amount_alert);
                    String war = getString(R.string.warning);
                    String ook = getString(R.string.ok);
                    alertbox.setMessage(enn);
                    alertbox.setTitle(war);
                    alertbox.setIcon(R.drawable.dailylogo);
                    alertbox.setPositiveButton(ook,
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0,
                                                    int arg1) {


                                }
                            });
                    alertbox.show();
                }else{
                    add();
                }
            }
        });
        populateRecyclerView();
cancc.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent na = new Intent(Add_employee_payment.this,Employee_payment.class);
        na.putExtra("ID",idd);
        startActivity(na);
        finish();
    }
});
    }
    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Add_employee_payment account) {
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

    //add() - Add new payment for an employee
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void add(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = df.format(c.getTime());
        String lo = ress.getText().toString();
        String ph = amou.getText().toString();
        String pp = payin.getText().toString();
        String cr = formattedDate;

        if(ph.equalsIgnoreCase("") || ph.equalsIgnoreCase("0")){
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Add_employee_payment.this,R.style.AlertDialogTheme);
            String enn = getString(R.string.enter_amount);
            String war = getString(R.string.warning);
            String ook = getString(R.string.ok);
            alertbox.setMessage(enn);
            alertbox.setTitle(war);
            alertbox.setIcon(R.drawable.dailylogo);
            alertbox.setNeutralButton(ook,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {
                        }
                    });
            alertbox.show();
        }else {
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("employee_id", idd);
            values.put("amount", ph);
            values.put("reason", lo);
            values.put("acc_type_id", actype);
            values.put("payment_info", pp);
            values.put("tracking_id", String.valueOf(ses));
            values.put("created_date", cr);
            database.insert("dd_employee_payment", null, values);
            database.close();
            sqlite.close();
            if (ph.equalsIgnoreCase("") || ph.equalsIgnoreCase("0")) {
            } else {
                sqlite = new SQLiteHelper(this);
                database = sqlite.getWritableDatabase();
                ContentValues values1 = new ContentValues();
                values1.put("acc_type_id", actype);
                values1.put("amount", ph);
                values1.put("accounting_date", formattedDate);
                values1.put("tracking_id", ses);
                values.put("payment_info", pp);
                values1.put("created_date", formattedDate);
                database.insert("dd_accounting", null, values1);
                database.close();
                sqlite.close();
            }
            Intent na = new Intent(Add_employee_payment.this, Employee_payment.class);
            na.putExtra("ID", idd);
            startActivity(na);
            finish();
        }
    }

    //populateRecyclerView() - Show all employees
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView() {

        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY = "SELECT * FROM dd_employee where id = ?";

        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{idd});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("name");
                    String Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    long id = cursor.getLong(index);

//                    index = cursor.getColumnIndexOrThrow("amount");
//                    String ammm = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("reason");
                    String reason = cursor.getString(index);
//if(ammm == null){
//    ammm ="0";
//}
                    nam.setText(Name);
ress.setText(reason);
//amou.setText(ammm);
//                    amou.setText(amount);

                }
                while (cursor.moveToNext());
                cursor.close();
                database.close();
                sqlite.close();
            }else{
                cursor.close();
                database.close();
                sqlite.close();
            }
        }else{
            cursor.close();
            database.close();
            sqlite.close();
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
    //onBackPressed() - function called when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onBackPressed() {
        Intent na = new Intent(Add_employee_payment.this,Employee_payment.class);
        na.putExtra("ID",idd);
        startActivity(na);
        finish();
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
            Intent na = new Intent(Add_employee_payment.this,Employee_payment.class);
            na.putExtra("ID",idd);
            startActivity(na);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
