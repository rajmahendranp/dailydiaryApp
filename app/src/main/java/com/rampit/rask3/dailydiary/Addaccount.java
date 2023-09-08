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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

//Updated on 25/01/2022 by RAMPIT
//used to add or update account by admin
//onBackPressed() - function called when back button is pressed
//InternetConnection1() - Check whether internet is on or not
//spin() - get account type datas
//edit() - get account type datas
//onCreateOptionsMenu() - when navigation menu created
//spinnervalues() - get all account type datas
//updateLabel() - Update textview when date is selected



public class Addaccount extends AppCompatActivity {
    EditText dat,amou,noo;
    TextView ball,dateee,session,varavu,selavu,amoch;
    Calendar myCalendar;
    Integer ses;
    String dateString,data,dataid,dataid1,did,ammo,editid,editedcldt,accty,edd,timing;
    ImageView seesim;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> Credits = new ArrayList<>();
    ArrayList<String> Debits = new ArrayList<>();
    ArrayList<String> Credits1 = new ArrayList<>();
    ArrayList<String> Debits1 = new ArrayList<>();
    List<String> arrayList = new ArrayList<>();
    List<String> arrayList1 = new ArrayList<>();
    List<String> arrayList2 = new ArrayList<>();
    List<String> spin = new ArrayList<>();
    List<String> spin1 = new ArrayList<>();
    Spinner cd,de;
    Button relo,sav,canc;
    ArrayAdapter spinnerArrayAdapter1,spinnerArrayAdapter;
    public static String TABLENAME = "dd_accounting";
    SimpleDateFormat sdf1;
    Integer in;
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
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.add_account);
        setTitle(R.string.title_activity_add_account);
        ((Callback)getApplication()).datee();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        amoch = findViewById(R.id.amoch);
        dat = findViewById(R.id.date);
        cd =findViewById(R.id.credit);
        de = findViewById(R.id.debit);
        relo = findViewById(R.id.reload);
        sav = findViewById(R.id.save);
        canc = findViewById(R.id.cancel);
        noo = findViewById(R.id.note);
        amou = findViewById(R.id.amount);
        ball = findViewById(R.id.balance);
        varavu = findViewById(R.id.varavu);
        selavu = findViewById(R.id.selavu);
        myCalendar = Calendar.getInstance();
        sqlite = new SQLiteHelper(this);
        Calendar c = Calendar.getInstance();
        Intent acc = getIntent();
        did = acc.getStringExtra("id");
        ammo = acc.getStringExtra("amount");
        if(ammo == null){
            ammo = "0";
        }
        edd = "0";
        ses = pref.getInt("session", 1);
        String dd = pref.getString("Date","");
        dateee = findViewById(R.id.da);
        session = findViewById(R.id.sess);
        seesim = findViewById(R.id.sesimg);
        dateee.setText(dd);
        if(in == 0){
            relo.setBackgroundResource(R.drawable.refresh);
            if(ses == 1){
                timing = getString(R.string.morning);
                seesim.setBackgroundResource(R.drawable.sun);
            }else if(ses == 2){
                timing = getString(R.string.evening);
                seesim.setBackgroundResource(R.drawable.moon);
            }
        }
        else{
            relo.setBackgroundResource(R.drawable.refresh_blue);
            if(ses == 1){
                timing = getString(R.string.morning);
                seesim.setBackgroundResource(R.drawable.sun_blue);
            }else if(ses == 2){
                timing = getString(R.string.evening);
                seesim.setBackgroundResource(R.drawable.moon_blue);
            }
        }
        session.setText(timing);
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
        amou.setText(ammo);
        ses = pref.getInt("session", 1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = df.format(c.getTime());
        String myFormat1 = "dd/MM/yyyy";//In which you need put here
        sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        dat.setText(sdf1.format(c.getTime()));
        dateString = formattedDate;
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }};
        String balat = pref.getString("totalbal","");
        ball.setText("\u20B9"+" "+balat);
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),Addaccount.this)) {
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
        dat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (in == 0){
                    datePickerDialog =  new DatePickerDialog(Addaccount.this,R.style.DialogTheme, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH ));
                }
                else{
                    datePickerDialog =  new DatePickerDialog(Addaccount.this,R.style.DialogTheme1, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH ));
                }
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
                String myFormat = "yyyy/MM/dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateString = sdf.format(myCalendar.getTime());
                dat.setText(sdf1.format(myCalendar.getTime()));
            }
        });
        spinnervalues();
        Intent name = getIntent();
        editid = name.getStringExtra("ID");
//         spinnerArrayAdapter = new ArrayAdapter(this,
//                android.R.layout.simple_spinner_dropdown_item, Credits);
//        cd.setAdapter(spinnerArrayAdapter);
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
            cd.setAdapter(spinnerArrayAdapter);
        }
        else{
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
            cd.setAdapter(spinnerArrayAdapter);
//            recreate();
        }
        if(in == 0){
            final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                    this,R.layout.spinner_design,spin1){
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
            canc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent back = new Intent(Addaccount.this,Account.class);
                    startActivity(back);
                    finish();
                }
            });
            spinnerArrayAdapter1.setDropDownViewResource(R.layout.spinner_dropdown);
            de.setAdapter(spinnerArrayAdapter1);


        }
        else{
            final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                    this,R.layout.spinner_design1,spin1){
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
            canc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent back = new Intent(Addaccount.this,Account.class);
                    startActivity(back);
                    finish();
                }
            });
            spinnerArrayAdapter1.setDropDownViewResource(R.layout.spinner_dropdown);
            de.setAdapter(spinnerArrayAdapter1);

        }
        cd.setEnabled(true);
        de.setEnabled(true);
//        Log.d("ddd",did);


//        cd.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                   de.setEnabled(false);
//                }
//                return false;
//            }
//        });
//
//        de.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//cd.setEnabled(false);
//             }
//                return false;
//            }
//        });
        if(editid == null){
        }else{
            Log.d("eddi",editid);
            edit();
        }
        if(did != null) {
            if (did.equalsIgnoreCase("8")) {
                Log.d("ddd",did);
                de.setEnabled(false);
                cd.setSelection(8);
            } else if (did.equalsIgnoreCase("19")) {
                Log.d("ddd",did);
                cd.setEnabled(false);
                de.setSelection(10);
            }
        }
relo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        cd.setEnabled(true);
        de.setEnabled(true);
        cd.setSelection(0);
        de.setSelection(0);
        amou.setText("");
        noo.setText("");
        edd ="1";
    }
});
        cd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                Integer nu = cd.getSelectedItemPosition();
//              String kk = getString(R.string.credited_amount);
//                amoch.setText(kk);
                 data = Credits.get(nu);
                 dataid = Credits1.get(nu);
                 if(dataid.equalsIgnoreCase("8")){
                     Log.d("dda",dataid);
                     spin(dataid);
                 }
//                 de.setEnabled(false);
            }
            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
//                String kk = getString(R.string.amount);
//                amoch.setText(kk);
            }

        });
        de.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                Integer nu = de.getSelectedItemPosition();
//                String kk = getString(R.string.debit_amount);
//                amoch.setText(kk);

                data = Debits.get(nu);
                 dataid = Debits1.get(nu);
                if(dataid.equalsIgnoreCase("19")){
                    Log.d("dda",dataid);
                    spin(dataid);
                }
                 //                cd.setEnabled(false);
            }
            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
//                String kk = getString(R.string.amount);
//                amoch.setText(kk);
            }

        });
//        String kk = getString(R.string.amount);
//        amoch.setText(kk);
//varavu.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        popup("1");
//    }
//});
//selavu.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        popup("2");
//
//    }
//});
sav.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Log.d("daaa",data);
        if(dataid.equalsIgnoreCase("0") || dataid == null){
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Addaccount.this,R.style.AlertDialogTheme);
            String er = getString(R.string.typealert);
            String er1 = getString(R.string.warning);
            String er2 = getString(R.string.ok);
            alertbox.setMessage(er);
            alertbox.setTitle(er1);
            alertbox.setIcon(R.drawable.dailylogo);
            alertbox.setPositiveButton(er2,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {

                        }
                    });
            alertbox.show();
        }
        else{
            sav.setEnabled(false);
            String amo = amou.getText().toString();
            String nnnt = noo.getText().toString();
            Log.d("daa22",dataid);
            if(amo.equalsIgnoreCase("")){
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Addaccount.this,R.style.AlertDialogTheme);
                String er = getString(R.string.typealert);
                String er1 = getString(R.string.warning);
                String er2 = getString(R.string.ok);
                alertbox.setMessage(er);
                alertbox.setTitle(er1);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setPositiveButton(er2,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {

                            }
                        });
                alertbox.show();
                Log.d("daa",data+dataid+dateString);

            }else{if(editid == null)
            {
                Log.d("daa2", data + dataid + amo + nnnt + dateString + editid);
                sqlite = new SQLiteHelper(Addaccount.this);
                database = sqlite.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("acc_type_id", dataid);
                values.put("amount", amo);
                values.put("note", nnnt);
                values.put("accounting_date", dateString);
                values.put("tracking_id", ses);
                values.put("created_date", dateString);
                database.insert(TABLENAME, null, values);
                sqlite.close();
                database.close();
                Intent ne = new Intent(Addaccount.this, Account.class);
//                ne.putExtra("id",dataid);
                startActivity(ne);
                finish();
            }
            else {
                Log.d("daa1", data + " "+dataid1 +" "+amo + nnnt + dateString+" "+editid);
                sqlite = new SQLiteHelper(Addaccount.this);
                database = sqlite.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("acc_type_id", dataid1);
                values.put("amount", amo);
                values.put("note", nnnt);
                values.put("accounting_date", dateString);
                values.put("tracking_id", ses);
                values.put("updated_date", dateString);
                database.update(TABLENAME,  values,"id = ?",new String[]{editid});
                sqlite.close();
                database.close();
                Intent ne = new Intent(Addaccount.this, Account.class);
//                ne.putExtra("id",dataid);
                startActivity(ne);
                finish();
            }
            }
        }
    }
});
    }

    //onBackPressed() - function called when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onBackPressed() {
        Intent back = new Intent(Addaccount.this,Account.class);
        startActivity(back);
        finish();
        super.onBackPressed();
    }
    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Addaccount account) {
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

    //spin() - get account type datas
    //Params - account type id
    //Created on 25/01/2022
    //Return - NULL
    private void spin(String idd1) {
        Log.d("datad",idd1);
        sqlite = new SQLiteHelper(Addaccount.this);
        database = sqlite.getWritableDatabase();
        Calendar myCalendar1 = Calendar.getInstance();
        SimpleDateFormat d3f = new SimpleDateFormat("yyyy/MM/dd");
        String dateString1 = d3f.format(myCalendar1.getTime());

        String MY_QUERY = "SELECT a.*,b.acc_type_name,b.acc_type FROM dd_accounting a LEFT JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE a.acc_type_id = ? AND a.tracking_id = ? AND a.accounting_date = ? ";

        String MY_QUERY1 = "SELECT * FROM dd_accounting WHERE id = ? AND tracking_id = ?";
        String[] whereArgs1 = new String[] {idd1, String.valueOf(ses),dateString};
        Cursor cursor1 = database.rawQuery(MY_QUERY,whereArgs1);
        if (cursor1 != null) {
            if (cursor1.getCount() != 0) {
                cursor1.moveToFirst();
                do {
                    int index;
                    index = cursor1.getColumnIndexOrThrow("id");
                    editid = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("amount");
                    String db_am = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("acc_type_id");
                    accty = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("acc_type_name");
                    String accty2 = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("acc_type");
                    String accty1 = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("note");
                    String nott = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("accounting_date");
                    String accdate = cursor1.getString(index);

                    Date bb = null;
                    Date cc = null;
                    if(db_am == null){
                        db_am = "0";
                    }
//if(accty1 == null){
//    accty1 = "1";
//}
                    SimpleDateFormat ss = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat sss = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar calendar = Calendar.getInstance();
                    try {
                        bb = ss.parse(accdate);
                        editedcldt = sss.format(bb);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Log.d("accc", db_am + " " + accty + " " + nott + " " + editedcldt+" "+editid);
if(ammo.equalsIgnoreCase("0")) {
    amou.setText(db_am);
}else{
    amou.setText(ammo);
}
                    noo.setText(nott);
                    Integer aa = Integer.parseInt(accty1);
                    if(aa == 1){
                        int i = arrayList.indexOf(accty);
                        Log.d("inddd11",String.valueOf(i+1));
                        cd.setSelection(i+1);
                        de.setEnabled(false);
                        dataid = String.valueOf(i+1);
                        dataid1 = String.valueOf(i+1);
                    }else if(aa == 2){
                        Integer o = Integer.parseInt(idd1);
                        int i1 = arrayList2.indexOf(o);
                        int i = arrayList1.indexOf(accty);
                        Log.d("inddd21",String.valueOf(i+10));
                        de.setSelection(i+1);
                        cd.setEnabled(false);
                        dataid = String.valueOf(i+10);
                        dataid1 = String.valueOf(i+10);
                    }
                    Log.d("daaa21",dataid);
                }
                while (cursor1.moveToNext());
                cursor1.close();
                sqlite.close();
                database.close();
            }else{
                cursor1.close();
                sqlite.close();
                database.close();
            }
        }else{
            cursor1.close();
            sqlite.close();
            database.close();
        }
    }

    //edit() - get account type datas
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void edit() {
        Log.d("edii",editid);
        sqlite = new SQLiteHelper(Addaccount.this);
        database = sqlite.getWritableDatabase();
        String MY_QUERY = "SELECT a.*,b.acc_type_name,b.acc_type FROM dd_accounting a LEFT JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE a.id = ? AND a.tracking_id = ? ";

        String MY_QUERY1 = "SELECT * FROM dd_accounting WHERE id = ? AND tracking_id = ?";
        String[] whereArgs1 = new String[] {editid, String.valueOf(ses)};
        Cursor cursor1 = database.rawQuery(MY_QUERY,whereArgs1);
        if (cursor1 != null) {
            if (cursor1.getCount() != 0) {
                cursor1.moveToFirst();
                do {
                    int index;

                    index = cursor1.getColumnIndexOrThrow("amount");
                    String db_am = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("acc_type_id");
                     accty = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("acc_type_name");
                    String accty2 = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("acc_type");
                    String accty1 = cursor1.getString(index);

                    index = cursor1.getColumnIndexOrThrow("note");
                    String nott = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("accounting_date");
                    String accdate = cursor1.getString(index);

                    Date bb = null;
                    Date cc = null;
//if(accty1 == null){
//    accty1 = "1";
//}
                    SimpleDateFormat ss = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat sss = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar calendar = Calendar.getInstance();
                    try {
                        bb = ss.parse(accdate);
                        editedcldt = sss.format(bb);
                        myCalendar.setTime(bb);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Log.d("accc", db_am + " " + accty + " " + nott + " " + editedcldt);

                    amou.setText(db_am);
                    noo.setText(nott);
                    dat.setText(editedcldt);
//                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//                    SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
//                    try {
//                        Date debit = df.parse(dstr);
//                        datedd = sdf1.format(debit);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
                    Integer aa = Integer.parseInt(accty1);
                    if(aa == 1){
                        int i = arrayList.indexOf(accty);
                        Log.d("inddd",String.valueOf(i));
                        cd.setSelection(i+1);
                    de.setEnabled(false);
                    dataid = String.valueOf(i+1);
                    dataid1 = String.valueOf(i+1);
                }else if(aa == 2){
                        int i = arrayList1.indexOf(accty);
                        Log.d("inddd",String.valueOf(i));
                        de.setSelection(i+1);
                        cd.setEnabled(false);
                        dataid = String.valueOf(i+10);
                        dataid1 = String.valueOf(i+10);
                        Log.d("daaa21",dataid);
                    }
                    Log.d("daaa21",dataid);
                }
                while (cursor1.moveToNext());
                cursor1.close();
                sqlite.close();
                database.close();
            }else{
                cursor1.close();
                sqlite.close();
                database.close();
            }
        }else{
            cursor1.close();
            sqlite.close();
            database.close();
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
            Intent back = new Intent(Addaccount.this,Account.class);
            startActivity(back);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //spinnervalues() - get all account type datas
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void spinnervalues() {
        Credits.clear();
        Debits.clear();
        arrayList.clear();
        arrayList1.clear();
        arrayList2.clear();
        Credits1.clear();
        Debits1.clear();
        spin.clear();
        spin1.clear();
        String sell = getString(R.string.select);
        spin.add(sell);
        spin1.add(sell);
        Credits.add(sell);
        Debits.add(sell);
        Credits1.add("0");
        Debits1.add("0");

        String[] columns1 = {
                "acc_type_name","acc_type","id"};
        sqlite = new SQLiteHelper(Addaccount.this);
        database = sqlite.getWritableDatabase();
        Cursor cursor = database.query("dd_accounting_type",
                columns1,
                null,
                null,
                null,
                null,
                null);
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("acc_type_name");
                    String USER = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("acc_type");
                    Integer PASS = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("id");
                    String ID = cursor.getString(index);

                    if(PASS == 1 ){
                        arrayList.add(ID);
                    }else if(PASS == 2 ){
                        arrayList1.add(ID);
                    }
                    arrayList2.add(ID);
                    if(USER.equalsIgnoreCase("investment")){
                        String nnaa = getApplicationContext().getString(R.string.investment);
                        USER = nnaa;
                    }
                    else if(USER.equalsIgnoreCase("Finance")){
                        String nnaa = getString(R.string.finance);
                        USER=nnaa;
                    }
                    else if(USER.equalsIgnoreCase("Chittu")){
                        String nnaa = getString(R.string.chittu2);
                        USER = nnaa ;
                    }
                    else if(USER.equalsIgnoreCase("Transfer_Hand")){
                        String nnaa = getString(R.string.transferhand);
                        USER = nnaa;
                    }
                    else if(USER.equalsIgnoreCase("Anamattu")){
                        String nnaa = getString(R.string.anamattu);
                        USER = nnaa;
                    }
                    else if(USER.equalsIgnoreCase("Other_Income")){
                        String nnaa = getString(R.string.otherincome);
                        USER = nnaa;
                    }
                    else if(USER.equalsIgnoreCase("Employee_credit")){
                        String nnaa = getString(R.string.employeecredit);
                        USER = nnaa;
                    }
                    else if(USER.equalsIgnoreCase("High")){
                        String nnaa = getString(R.string.high);
                        USER = nnaa;
                    }
                    else if(USER.equalsIgnoreCase("Profit")){
                        String nnaa = getString(R.string.profit);
                        USER = nnaa;
                    }
                    else if(USER.equalsIgnoreCase("Expense")){
                        String nnaa = getString(R.string.expense);
                        USER = nnaa;
                    }
                    else if(USER.equalsIgnoreCase("Petrol")){
                        String nnaa = getString(R.string.petrol);
                        USER = nnaa;
                    }
                    else if(USER.equalsIgnoreCase("rent")){
                        String nnaa = getApplicationContext().getString(R.string.rent1);
                        USER = nnaa;
                    }
                    else if(USER.equalsIgnoreCase("Debit_Hand")){
                        String nnaa = getString(R.string.debithand);
                        USER=nnaa;
                    }
                    else if(USER.equalsIgnoreCase("Vehicle_Expense")){
                        String nnaa = getString(R.string.vehicleexpense);
                        USER = nnaa ;
                    }
                    else if(USER.equalsIgnoreCase("Other_Expense")){
                        String nnaa = getString(R.string.otherexpense);
                        USER = nnaa;
                    }
                    else if(USER.equalsIgnoreCase("Donation_Expense")){
                        String nnaa = getString(R.string.donationexpense);
                        USER = nnaa;
                    }
                    else if(USER.equalsIgnoreCase("Other_Income")){
                        String nnaa = getString(R.string.otherincome);
                        USER = nnaa;
                    }
                    else if(USER.equalsIgnoreCase("Employee_Expense")){
                        String nnaa = getString(R.string.employeeexpense);
                        USER = nnaa;
                    }
                    else if(USER.equalsIgnoreCase("Low")){
                        String nnaa = getString(R.string.low_amount);
                        USER = nnaa;
                    }
                    else if(USER.equalsIgnoreCase("Lose")){
                        String nnaa = getString(R.string.lose);
                        USER = nnaa;
                    }
//                    else{
//
//                    }
if(PASS == 1 ){
    Credits.add(USER);
    Credits1.add(ID);
//    arrayList.add(USER);
    spin.add(USER);
}else if(PASS == 2 ){
    Debits.add(USER);
    Debits1.add(ID);
//    arrayList1.add(USER);
    spin1.add(USER);
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

    }
//    public void popup(final String ii){
//        Names.clear();
//        database = sqlite.getReadableDatabase();
//        String MY_QUERY = "SELECT * FROM dd_accounting_type WHERE acc_type = ?  ";
//
//        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{ii});
//        if (cursor != null){
//            if(cursor.getCount() != 0){
//                cursor.moveToFirst();
//                do{
//                    int index;
//                    index = cursor.getColumnIndexOrThrow("acc_type_name");
//                    String Name = cursor.getString(index);
//
//                    index = cursor.getColumnIndexOrThrow("id");
//                    long id = cursor.getLong(index);
//
//
//                                Names.add( id+ "," + Name);
//                }
//                while (cursor.moveToNext());
//            }
//        }
//        final Dialog dialog = new Dialog(Addaccount.this);
//        //set layout custom
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.popaccount);
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(dialog.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.gravity = Gravity.CENTER;
//        dialog.getWindow().setAttributes(lp);
//        final RecyclerView rvcaddy = (RecyclerView) dialog.findViewById(R.id.rvAnimals);
//        final EditText eddd = (EditText) dialog.findViewById(R.id.edit);
//        final Button savv = (Button) dialog.findViewById(R.id.save);
//        final MyRecyclerAdapter3 adapter = new MyRecyclerAdapter3(getApplicationContext(),Names,Addaccount.this,ses);
//        final Button close = (Button) dialog.findViewById(R.id.close);
//        savv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String value = eddd.getText().toString();
//                if(value.equalsIgnoreCase("")){
//
//                }else{
////                    Log.d("daa", data + dataid + amo + nnnt + dateString);
//                    sqlite = new SQLiteHelper(Addaccount.this);
//                    database = sqlite.getWritableDatabase();
//                    ContentValues values = new ContentValues();
//                    values.put("acc_type_name", value);
//                    values.put("acc_type", ii);
//                    values.put("created_date", dateString);
//                    database.insert("dd_accounting_type", null, values);
//                    Intent ne = new Intent(Addaccount.this, Account.class);
////                ne.putExtra("id",dataid);
//                    startActivity(ne);
//                }
//            }
//        });
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        if(Names.size() == 0){
//            rvcaddy.setVisibility(View.GONE);
//            noo.setVisibility(View.VISIBLE);
//        }else {
//            rvcaddy.setVisibility(View.VISIBLE);
//            noo.setVisibility(View.GONE);
//            rvcaddy.setAdapter(adapter);
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//            rvcaddy.setLayoutManager(mLayoutManager);
//        }
//        dialog.show();
//    }
//    SELECT sum(a.amount) FROM dd_accounting a INNER JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE a.accounting_date < ? AND a.tracking_id = ? AND b.acc_type = '1'

    //updateLabel() - Update textview when date is selected
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void updateLabel() {

        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateString = sdf.format(myCalendar.getTime());
        dat.setText(sdf1.format(myCalendar.getTime()));
    }
    }
