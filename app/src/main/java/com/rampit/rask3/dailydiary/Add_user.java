package com.rampit.rask3.dailydiary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
//used to add new user by admin
//InternetConnection1() - Check whether internet is on or not
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed
//populateRecyclerView() - Add or update an user
//populateRecyclerView1() - Add privileges to new user
//populateRecyclerView2() - Get user datas
//populateRecyclerView3() - Check whether username and password is already exists

public class Add_user extends AppCompatActivity {

    EditText user,pass,otp;
    TextView dateee,session;
    Integer ses;
    String timing,id,idi;
    Button addd;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    ImageView seesim;
    Integer in,yesno;
    CheckBox yes,nope;
    Integer showinng = 0;
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
        yesno = 0;
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
        setContentView(R.layout.add_user);
        setTitle(R.string.title_add_user_activity);
        ((Callback)getApplication()).datee();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        user = findViewById(R.id.usr);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        yes = findViewById(R.id.yes);
        nope = findViewById(R.id.no);
        pass = findViewById(R.id.pss);
        addd = findViewById(R.id.ad);
        seesim = findViewById(R.id.sesimg);
        otp = findViewById(R.id.otp);
//        sqlite = new SQLiteHelper(this);
        ses = pref.getInt("session", 1);
        String dd = pref.getString("Date","");
        dateee = findViewById(R.id.da);
        session = findViewById(R.id.sess);
        Intent name = getIntent();
        idi = name.getStringExtra("ID");
        if(idi == null){

        }else{
            populateRecyclerView2();
        }
        dateee.setText(dd);
        if(in == 0){
            yes.setButtonDrawable(R.drawable.custom_checkbox);
            nope.setButtonDrawable(R.drawable.custom_checkbox);
            if(ses == 1){
                timing = getString(R.string.morning);
                seesim.setBackgroundResource(R.drawable.sun);
            }else if(ses == 2){
                timing = getString(R.string.evening);
                seesim.setBackgroundResource(R.drawable.moon);
            }
        }else{
            yes.setButtonDrawable(R.drawable.custom_checkbox1);
            nope.setButtonDrawable(R.drawable.custom_checkbox1);
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),Add_user.this)) {
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
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(yes.isChecked()){
                    yesno = 1;
                    nope.setChecked(false);
//                    Toast.makeText(Add_user.this,yesno, Toast.LENGTH_SHORT).show();
                }else{
                    yesno =0;
                }
            }
        });
        nope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nope.isChecked()){
                    yesno = 0;
                    yes.setChecked(false);
//                    Toast.makeText(Add_user.this,yesno, Toast.LENGTH_SHORT).show();
                }else{
                    yesno =0;
                }
            }
        });
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
    addd.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            populateRecyclerView3();
        }
    });


    }
    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Add_user account) {
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
            Intent back = new Intent(Add_user.this,user_activity.class);
            startActivity(back);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //populateRecyclerView() - Add or update an user
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView() {
        String nm = user.getText().toString();
        String lo = pass.getText().toString();
        String oottp = otp.getText().toString();
        Log.d("ooott",oottp);
        if(nm.equalsIgnoreCase("") || lo.equalsIgnoreCase("")|| oottp.equalsIgnoreCase("")){
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Add_user.this,R.style.AlertDialogTheme);
            String enn = getString(R.string.enter_userandpass);
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
        }else if(idi == null){
            sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = df.format(c.getTime());
        ContentValues values = new ContentValues();
        values.put("username",nm);
        values.put("password",lo);
        values.put("otp",oottp);
        values.put("backup_privilege",yesno);
        values.put("created_date",formattedDate);
        database.insert("dd_user",null,values);
            sqlite.close();
            database.close();
        populateRecyclerView1();
    }else{
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            String formattedDate = df.format(c.getTime());
//            Log.d("iid",id);
            ContentValues values = new ContentValues();
            values.put("username", nm);
            values.put("password", lo);
            values.put("otp",oottp);
            values.put("backup_privilege",yesno);
            values.put("updated_date", formattedDate);
            database.update("dd_user", values,"id = ?", new String[]{idi});
            sqlite.close();
            database.close();
            populateRecyclerView1();
        }
    }

    //populateRecyclerView1() - Add privileges to new user
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView1() {
        String nm = user.getText().toString();
        String lo = pass.getText().toString();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = df.format(c.getTime());
        String MY_QUERY = "SELECT * FROM dd_user WHERE username = ? AND password = ? AND created_date = ? AND ID > 1 ";
        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{nm,lo,formattedDate});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("ID");
                    id = cursor.getString(index);
                    Log.d("idid",id);
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
        if(id != null){
            Integer count = 19;
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
            for(int i=0;i<= count;i++){
                Integer modid = i+1;
                ContentValues values = new ContentValues();
                values.put("module_id",String.valueOf(modid));
                values.put("user_id",id);
                values.put("created_date",formattedDate);
                database.insert("dd_privilege",null,values);
                Log.d("forloop",String.valueOf(modid));
            }
            sqlite.close();
            database.close();
            Intent ne = new Intent(Add_user.this,user_activity.class);
            startActivity(ne);
            finish();
        }

    }

    //populateRecyclerView2() - Get user datas
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView2() {

        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = df.format(c.getTime());
        String MY_QUERY = "SELECT * FROM dd_user WHERE ID = ? ";
        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{idi});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("username");
                    String name = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("password");
                    String passs = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("otp");
                    String ooy = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("backup_privilege");
                    Integer backpri = cursor.getInt(index);

                    Log.d("idid",name);
                    user.setText(name);
                    pass.setText(passs);
                    otp.setText(ooy);
                    yesno = backpri;
                    if(yesno.equals(1)){
                        yes.setChecked(true);
                        nope.setChecked(false);
                    }else{
                        yes.setChecked(false);
                        nope.setChecked(true);
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
        database.close();}
        if(id != null){
            Integer count = 18;
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
            for(int i=0;i<= count;i++){
                Integer modid = i+1;
                ContentValues values = new ContentValues();
                values.put("module_id",String.valueOf(modid));
                values.put("user_id",id);
                values.put("created_date",formattedDate);
                database.insert("dd_privilege",null,values);
                Log.d("forloop",String.valueOf(modid));
            }
            sqlite.close();
            database.close();
            Intent ne = new Intent(Add_user.this,user_activity.class);
            startActivity(ne);
            finish();
        }

    }

    //populateRecyclerView3() - Check whether username and password is already exists
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView3() {
        String nm = user.getText().toString();
        String lo = pass.getText().toString();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = df.format(c.getTime());
        String MY_QUERY = "SELECT * FROM dd_user WHERE username = ?   ";
        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{nm});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("ID");
                    id = cursor.getString(index);
                    Log.d("idid",id);
                }
                while (cursor.moveToNext());
            }else{
                id = "0";
            }
        }else{
            id = "0";
        }
        if(id == null || id.equalsIgnoreCase("0") ||id.equalsIgnoreCase("") || id.equalsIgnoreCase(idi) ){
            populateRecyclerView();
        }else{
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Add_user.this,R.style.AlertDialogTheme);
            String enn = getString(R.string.user_exists);
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
        }
    }
}
