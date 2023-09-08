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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

//Updated on 25/01/2022 by RAMPIT
//used to Add new employee by admin
//InternetConnection1() - Check whether internet is on or not
//edit() - show employee details
//updateLabel() - Update textview when date is selected
//updateLabel1() - Update textview when date is selected
//add_details() - Insert or update employee details
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed


public class Add_employee extends AppCompatActivity {

    EditText nme,loc,phn,jdate,ldays,rdate,res,amou;
    Button add,cancel;
    SQLiteHelper sqlite;
    Integer iid,ses;
    TextView dateee,session;
    SQLiteDatabase database;
    Calendar myCalendar,myCalendar1;
    String IdD,joining,resign,actype,acc,timing;
    public static String TABLE_NAME ="dd_employeeEEEEE";
    ImageView seesim;
    Integer in ;
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
        Resources res1 = getResources();
        DisplayMetrics dm = res1.getDisplayMetrics();
        Configuration conf = res1.getConfiguration();
        conf.locale = myLocale;
        res1.updateConfiguration(conf, dm);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.add_employee);
        setTitle(R.string.add_employee);
        ((Callback)getApplication()).datee();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nme = findViewById(R.id.name);
        loc = findViewById(R.id.location);
        phn = findViewById(R.id.phone);
        jdate = findViewById(R.id.joindate);
        ldays = findViewById(R.id.leave);
        seesim = findViewById(R.id.sesimg);
        rdate = findViewById(R.id.resigndate);
        res = findViewById(R.id.reason);
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),Add_employee.this)) {
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
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
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
        add = findViewById(R.id.addemploo);
        cancel = findViewById(R.id.cancel);
        myCalendar = Calendar.getInstance();
        myCalendar1 = Calendar.getInstance();
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
        jdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (in == 0){
                    datePickerDialog =  new DatePickerDialog(Add_employee.this,R.style.DialogTheme, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH ));
                }else{
                    datePickerDialog =  new DatePickerDialog(Add_employee.this,R.style.DialogTheme1, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH ));
                }
                datePickerDialog.show();
                String myFormat = "yyyy/MM/dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                joining = sdf.format(myCalendar.getTime());
                String myFormat1 = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                jdate.setText(sdf1.format(myCalendar.getTime()));
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
        rdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Add_employee.this, date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
                String myFormat = "yyyy/MM/dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                joining = sdf.format(myCalendar1.getTime());
                String myFormat1 = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                rdate.setText(sdf1.format(myCalendar1.getTime()));
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nm = nme.getText().toString();
if(nm.equalsIgnoreCase("")){
    AlertDialog.Builder alertbox = new AlertDialog.Builder(Add_employee.this,R.style.AlertDialogTheme);
    String logmsg = getString(R.string.fill_all);
    String cann = getString(R.string.cancel);
    String warr = getString(R.string.warning);
    String logo = getString(R.string.Logout);
    alertbox.setMessage(logmsg);
    alertbox.setTitle(warr);
    alertbox.setIcon(R.drawable.dailylogo);
    alertbox.setNeutralButton(cann,
            new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface arg0,
                                    int arg1) {

                }
            });
    alertbox.show();
}else{
    add_details();
}
            }
        });
        Intent name = getIntent();
        IdD = name.getStringExtra("ID");
        if(IdD != null){
            edit();
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(Add_employee.this,Employee_details.class);
                startActivity(back);
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
        public static boolean checkConnection(Context context, final Add_employee account) {
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

    //edit() - show employee details
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void edit() {
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        String MY_QUERY1 = "SELECT * FROM dd_employee WHERE id = ?";
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{String.valueOf(IdD)});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("name");
                    String Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    String id = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("location");
                    String loct = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("leave_days");
                    String ldaa = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("phone");
                    String pne = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("reason");
                    String rom = cursor.getString(index);

//                    index = cursor.getColumnIndexOrThrow("amount");
//                    String dbamount = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("joining_date");
                    String joi = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("resigning_date");
                    String resd = cursor.getString(index);

//                    index = cursor.getColumnIndexOrThrow("acc_type_id");
//                    String accctype = cursor.getString(index);

//                    if(accctype.equalsIgnoreCase("1")){
//                        debb.setChecked(false);
//                        cred.setChecked(true);
//                        acc = "Credit";
//                    }else if(accctype.equalsIgnoreCase("2")){
//                        cred.setChecked(false);
//                        debb.setChecked(true);
//                        acc = "Debit";
//                    }

                nme.setText(Name);
                loc.setText(loct);
                phn.setText(pne);
                jdate.setText(joi);
                ldays.setText(ldaa);
                rdate.setText(resd);
                res.setText(rom);
//                amou.setText(dbamount);

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
    }

    //updateLabel() - Update textview when date is selected
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void updateLabel() {

        String myFormat = "yyyy/MM/dd";
        String mmm = "dd/MM/yyyy";//In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(mmm, Locale.US);
        joining = sdf.format(myCalendar.getTime());
        jdate.setText(sdf1.format(myCalendar.getTime()));
       }

    //updateLabel1() - Update textview when date is selected
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void updateLabel1() {

        String myFormat = "yyyy/MM/dd";
        String mmm = "dd/MM/yyyy";//In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(mmm, Locale.US);
        resign = sdf.format(myCalendar1.getTime());
        rdate.setText(sdf1.format(myCalendar1.getTime()));
    }

    //add_details() - Insert or update employee details
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void add_details() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = df.format(c.getTime());
        String nm = nme.getText().toString();
        String lo = loc.getText().toString();
        String ph = phn.getText().toString();
        String jd = jdate.getText().toString();
        String ld = ldays.getText().toString();
        String rd = rdate.getText().toString();
        String re = res.getText().toString();
        String cr = formattedDate;

        if(IdD != null){
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name",nm);
            values.put("location",lo);
            values.put("phone",ph);
            values.put("joining_date",jd);
            values.put("leave_days",ld);
            values.put("resigning_date",rd);
            values.put("reason",re);
            values.put("created_date",cr);
            database.update("dd_employee", values, "id = ?", new String[]{IdD});
            sqlite.close();
            database.close();
            Intent ne = new Intent(Add_employee.this,Employee_details.class);
            startActivity(ne);
            finish();

        }else {
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",nm);
        values.put("location",lo);
        values.put("phone",ph);
        values.put("joining_date",jd);
        values.put("leave_days",ld);
        values.put("resigning_date",rd);
        values.put("reason",re);
        values.put("created_date",cr);
        database.insert("dd_employee",null,values);
            sqlite.close();
            database.close();
        Intent ne = new Intent(Add_employee.this,Employee_details.class);
        startActivity(ne);
            finish();
    }}

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
            Intent back = new Intent(Add_employee.this,Employee_details.class);
            startActivity(back);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
