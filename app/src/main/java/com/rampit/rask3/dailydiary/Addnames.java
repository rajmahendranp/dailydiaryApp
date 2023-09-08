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
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Handler;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static com.rampit.rask3.dailydiary.R.drawable.login_selector;


//Updated on 25/01/2022 by RAMPIT
//used to add or update new customer
//InternetConnection1() - Check whether internet is on or not
//dataa() - get customer details
//onCreateOptionsMenu() - when navigation menu created
//InputFilter() - eliminate whitespaces and special characters of an string
//onOptionsItemSelected() - when navigation item pressed
//cid() - get customer details


public class Addnames extends AppCompatActivity {
    TextView na1;
    EditText namee,father,loc,land1,land2,phone1,phone2,rec,rel,relpho,inf,ord;
    Button add,cancel,save;
    SQLiteHelper sqlite;
    Integer ses,orddd,CIDorder;
    TextView dateee,session,accc;
    String id,timing,prevorder,prevCID;
    SQLiteDatabase database;
    public static String TABLENAME2 = "dd_customers";
    ImageView seesim;
    private PopupWindow mPopupWindow;
    Integer in;
    Integer showinng = 0;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        setContentView(R.layout.add_name);
        setTitle(R.string.title_activity_addnames);
        ((Callback)getApplication()).datee();

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        na1 = findViewById(R.id.na1);
        namee = findViewById(R.id.name);
        namee.setFilters(new InputFilter[] { filter });
        father = findViewById(R.id.father);
        loc = findViewById(R.id.location);
        land1 = findViewById(R.id.landmark1);
        land2 = findViewById(R.id.landmark2);
        phone1 = findViewById(R.id.phone1);
        phone2 = findViewById(R.id.phone2);
        rec = findViewById(R.id.recommend);
        rel = findViewById(R.id.relation);
        seesim = findViewById(R.id.sesimg);
        accc = findViewById(R.id.acc_no);
        relpho = findViewById(R.id.relation_phone);
        inf = findViewById(R.id.info);
        add = findViewById(R.id.add);
        cancel = findViewById(R.id.cancel);
        dateee = findViewById(R.id.da);
        session = findViewById(R.id.sess);
//        sqlite = new SQLiteHelper(this);
        save = findViewById(R.id.save);
//        save.setEnabled(false);
        LinearLayout linear = findViewById(R.id.addnamesline);
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),Addnames.this)) {
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
        final LinearLayout lllooo =findViewById(R.id.addnamesline);
        lllooo.getViewTreeObserver().addOnGlobalLayoutListener(new  ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                lllooo.getWindowVisibleDisplayFrame(r);
                int screenHeight = lllooo.getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;
                if (keypadHeight > screenHeight * 0.15) {
//                    add.setVisibility(View.VISIBLE);
                    save.setVisibility(View.VISIBLE);
//                    cancel.setVisibility(View.VISIBLE);
//                    Toast.makeText(Addnames.this,"Keyboard is showing",Toast.LENGTH_LONG).show();
                } else {
                    add.setVisibility(View.GONE);
                    save.setVisibility(View.GONE);
                    cancel.setVisibility(View.GONE);
//                    Toast.makeText(Addnames.this,"keyboard closed",Toast.LENGTH_LONG).show();
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            save.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.spin3));
        }else{
//            save.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.spin3));
        }
//        save.setBackgroundColor((ContextCompat.getColor(getApplicationContext(), R.color.spin2)));
        ses = pref.getInt("session", 1);

        String dd = pref.getString("Date", "");
        dateee.setText(dd);
        if(in == 0){
            if (ses == 1) {
                timing = getString(R.string.morning);
                seesim.setBackgroundResource(R.drawable.sun);
            } else if (ses == 2) {
                timing = getString(R.string.evening);
                seesim.setBackgroundResource(R.drawable.moon);
            }
            na1.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.eye_orange, 0);
        }else{
            if (ses == 1) {
                timing = getString(R.string.morning);
                seesim.setBackgroundResource(R.drawable.sun_blue);
            } else if (ses == 2) {
                timing = getString(R.string.evening);
                seesim.setBackgroundResource(R.drawable.moon_blue);
            }
            na1.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.eye_blue, 0);
        }
        session.setText(timing);
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        String MY_QUERY1 = "SELECT order_id FROM dd_customers WHERE (debit_type= '0' OR debit_type = '1') AND tracking_id = ? ORDER BY order_id DESC LIMIT 0,1   ";
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{String.valueOf(ses)});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("order_id");
                    prevorder = cursor.getString(index);
                    if(prevorder == null || prevorder.equalsIgnoreCase("") || prevorder.equalsIgnoreCase("null")){
                        prevorder = "0" ;
                    }
                    Log.d("orsderdf",prevorder);
                    if (prevorder.trim().equals(null)) {
                        prevorder = "0";
                        orddd = Integer.parseInt(prevorder);
                        orddd = orddd + 1;
                        Log.d("orderrre", String.valueOf(orddd));
                    } else {
                        orddd = Integer.parseInt(prevorder);
                        orddd = orddd + 1;
                        Log.d("orderrre", String.valueOf(orddd));
                    }

                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }
            else {
                cursor.close();
                sqlite.close();
                database.close();
                orddd = 0 + 1;
                Log.d("orderrre", String.valueOf(orddd));
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }
        na1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Integer ddd = dbamount + interr + dccharge;
//                    MessageBox("Hello World");
//                Dialog dialog = new Dialog(getApplicationContext());
//                Log.d("rtrt",String.valueOf(ddd));
                String naam = namee.getText().toString();
                String logmsg = getString(R.string.allcal);
                String cann = getString(R.string.cancel);
                String warr = getString(R.string.warning);
                String logo = getString(R.string.Logout);
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                // Inflate the custom layout/view
                View customView = inflater.inflate(R.layout.debit_pop,null);
                mPopupWindow = new PopupWindow(
                        customView,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                mPopupWindow.setOutsideTouchable(true);
                mPopupWindow.setFocusable(true);
                // Removes default background.
                mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                if(Build.VERSION.SDK_INT>=21){
                    mPopupWindow.setElevation(5.0f);
                }
                TextView tt = (TextView) customView.findViewById(R.id.tv);
                // Get a reference for the custom view close button
                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
                // Set a click listener for the popup window close button
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        mPopupWindow.dismiss();
                    }
                });
                tt.setText(naam);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    mPopupWindow.showAsDropDown(view, 0, 0, Gravity.END);
                } else {
                    mPopupWindow.showAsDropDown(view, view.getWidth() - mPopupWindow.getWidth(), 0);
                }
//                mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
//                mPopupWindow.setOutsideTouchable(true);
//                mPopupWindow.setFocusable(true);
//                mPopupWindow.showAtLocation(deb_text, Gravity.CENTER,0,0);
            }
        });


        Intent name = getIntent();
        id = name.getStringExtra("ID");
        if(id == null || id.equalsIgnoreCase("")|| id.equalsIgnoreCase("0")){
            id = "0";
            cid(String.valueOf(ses));
        }else{
            dataa();
        }
//        Log.d("inteid", id);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(Addnames.this, HomeActivity.class);
                startActivity(back);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                String formattedDate = df.format(c.getTime());
                String nam = namee.getText().toString();
                nam= nam.replace(",", "");
                String fath = father.getText().toString();
                String lo = loc.getText().toString();
                String lan1 = land1.getText().toString();
                String lan2 = land2.getText().toString();
                String ph1 = phone1.getText().toString();
                String ph2 = phone2.getText().toString();
                String recc = rec.getText().toString();
                String rell = rel.getText().toString();
                String rellph = relpho.getText().toString();
                String info = inf.getText().toString();
                String sess = String.valueOf(ses);
                String status = "-1";
                Integer ii = Integer.parseInt(id);
                if (nam.equalsIgnoreCase("")) {
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(Addnames.this,R.style.AlertDialogTheme);
                    String enn = getString(R.string.fill_all);
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
                } else {
                    if (ii == 0) {
                        save.setEnabled(true);
                        save.setBackgroundResource(R.drawable.login_selector);

                        sqlite = new SQLiteHelper(Addnames.this);
                        database = sqlite.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("order_id", "-1");
                        values.put("order_id_new", CIDorder);
                        values.put("CID", CIDorder);
                        values.put("customer_name", nam);
                        values.put("father_name", fath);
                        values.put("location", lo);
                        values.put("landmark_1", lan1);
                        values.put("landmark_2", lan2);
                        values.put("phone_1", ph1);
                        values.put("phone_2", ph2);
                        values.put("recommended_by", recc);
                        values.put("relationship", rell);
                        values.put("relation_phone", rellph);
                        values.put("info", info);
                        values.put("tracking_id", sess);
                        values.put("created_date", formattedDate);
                        values.put("debit_type", status);
//                        database.insert(TABLENAME2, null, values);
//                        Intent intent = new Intent(Addnames.this, HomeActivity.class);
//                        startActivity(intent);
                    } else {
                        save.setEnabled(true);
                        save.setBackgroundResource(R.drawable.login_selector);
                        sqlite = new SQLiteHelper(Addnames.this);
                        database = sqlite.getWritableDatabase();
                        ContentValues values = new ContentValues();
//                    values.put("order_id", orddd);
                        values.put("customer_name", nam);
                        values.put("father_name", fath);
                        values.put("location", lo);
                        values.put("landmark_1", lan1);
                        values.put("landmark_2", lan2);
                        values.put("phone_1", ph1);
                        values.put("phone_2", ph2);
                        values.put("recommended_by", recc);
                        values.put("relationship", rell);
                        values.put("relation_phone", rellph);
                        values.put("info", info);
                        values.put("tracking_id", sess);
                        values.put("updated_date", formattedDate);
//                        database.update(TABLENAME2, values, "id = ?", new String[]{id});
//                        Intent intent = new Intent(Addnames.this, HomeActivity.class);
//                        startActivity(intent);

                    }
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save.setEnabled(false);
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                String formattedDate = df.format(c.getTime());
                String nam = namee.getText().toString();
                nam= nam.replace(",", "");
                String fath = father.getText().toString();
                String lo = loc.getText().toString();
                String lan1 = land1.getText().toString();
                String lan2 = land2.getText().toString();
                String ph1 = phone1.getText().toString();
                String ph2 = phone2.getText().toString();
                String recc = rec.getText().toString();
                String rell = rel.getText().toString();
                String rellph = relpho.getText().toString();
                String in = inf.getText().toString();
                String sess = String.valueOf(ses);
                String status = "-1";
                Integer ii = Integer.parseInt(id);
                if (nam.equalsIgnoreCase("")) {
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(Addnames.this,R.style.AlertDialogTheme);
                    String enn = getString(R.string.fill_all);
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
                } else {
                    if (ii == 0) {
//                        save.setClickable(false);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            save.setBackground(ContextCompat.getDrawable(getApplicationContext(), login_selector));
                        }else{
                            save.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), login_selector) );
                        }
                        sqlite = new SQLiteHelper(Addnames.this);
                        database = sqlite.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("order_id", "-1");
                        values.put("order_id_new", CIDorder);
                        values.put("CID", CIDorder);
                        values.put("customer_name", nam);
                        values.put("father_name", fath);
                        values.put("location", lo);
                        values.put("landmark_1", lan1);
                        values.put("landmark_2", lan2);
                        values.put("phone_1", ph1);
                        values.put("phone_2", ph2);
                        values.put("recommended_by", recc);
                        values.put("relationship", rell);
                        values.put("relation_phone", rellph);
                        values.put("info", in);
                        values.put("tracking_id", sess);
                        values.put("created_date", formattedDate);
                        values.put("debit_type", status);
                        database.insert(TABLENAME2, null, values);
                        sqlite.close();
                        database.close();
                        Intent intent = new Intent(Addnames.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
//                        save.setEnabled(true);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            save.setBackground(ContextCompat.getDrawable(getApplicationContext(), login_selector));
                        }else{
                            save.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), login_selector) );
                        }
                        sqlite = new SQLiteHelper(Addnames.this);
                        database = sqlite.getWritableDatabase();
                        ContentValues values = new ContentValues();
//                    values.put("order_id", orddd);
                        values.put("customer_name", nam);
                        values.put("father_name", fath);
                        values.put("location", lo);
                        values.put("landmark_1", lan1);
                        values.put("landmark_2", lan2);
                        values.put("phone_1", ph1);
                        values.put("phone_2", ph2);
                        values.put("recommended_by", recc);
                        values.put("relationship", rell);
                        values.put("relation_phone", rellph);
                        values.put("info", in);
                        values.put("tracking_id", sess);
                        values.put("updated_date", formattedDate);
                        database.update(TABLENAME2, values, "id = ?", new String[]{id});
                        sqlite.close();
                        database.close();
                        Intent intent = new Intent(Addnames.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

    }
    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Addnames account) {
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

    //dataa() - get customer details
    //    //Params - NULL
    //    //Created on 25/01/2022
    //Return - NULL
    public void dataa(){
        sqlite = new SQLiteHelper(Addnames.this);
        database = sqlite.getWritableDatabase();
        String[] columns = {"customer_name", "order_id",
                "father_name", "location", "landmark_1", "landmark_2", "phone_1", "phone_2", "recommended_by", "relationship", "relation_phone", "info","CID"};
        String order = "order_id";
        String whereClause = "id=?";
        String[] whereArgs = new String[]{id};
        Cursor cursor1 = database.query("dd_customers",
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                order + " ASC");
        if (cursor1 != null) {
            if (cursor1.getCount() != 0) {
                cursor1.moveToFirst();
                do {
                    int index;
                    index = cursor1.getColumnIndexOrThrow("CID");
                    String orde = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("order_id");
                    String orde1 = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("customer_name");
                    String Name = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("father_name");
                    String father1 = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("location");
                    String location = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("landmark_1");
                    String landd1 = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("landmark_2");
                    String landd2 = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("phone_1");
                    String phonee1 = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("phone_2");
                    String phonee2 = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("recommended_by");
                    String recom = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("relationship");
                    String re = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("relation_phone");
                    String reph = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("info");
                    String inn = cursor1.getString(index);
                    if(orde1 == null || orde.equalsIgnoreCase("")){
                        orde1= "-1";
                    }
                    Integer ty1 = Integer.parseInt(orde1);
                    if(ty1 >5000){
                        accc.setText("Acc Number :"+" "+orde1);
                    }else{
                        accc.setText("Acc Number :"+" "+orde);
                    }
                    namee.setText(Name);
                    father.setText(father1);
                    loc.setText(location);
                    land1.setText(landd1);
                    land2.setText(landd2);
                    phone1.setText(phonee1);
                    phone2.setText(phonee2);
                    rec.setText(recom);
                    rel.setText(re);
                    relpho.setText(reph);
                    inf.setText(inn);

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

    private String blockCharacters = "(~*#^|$%&!,";
    //InputFilter() - eliminate whitespaces and special characters of an string
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (source != null && blockCharacters.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };
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
            Intent back = new Intent(Addnames.this,HomeActivity.class);
            startActivity(back);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //cid() - get customer details
    //Params - order id ( NOT USED )
    //Created on 25/01/2022
    //Return - NULL
    public void cid(String ord){
        sqlite = new SQLiteHelper(Addnames.this);
        database = sqlite.getWritableDatabase();
        String MY_QUERY1 = "SELECT CID FROM dd_customers WHERE  tracking_id = ? ORDER BY CID DESC LIMIT 0,1 ";
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{String.valueOf(ses)});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("CID");
                    prevCID = cursor.getString(index);
//                    Log.d("CIDorderrr1",prevorder);
                    if (prevCID.trim().equals(null)) {
                        prevCID = "0";
                        CIDorder = Integer.parseInt(prevCID);
                        CIDorder = CIDorder + 1;
                        Log.d("CIDorderrre", String.valueOf(CIDorder));
                    } else {
                        CIDorder = Integer.parseInt(prevCID);
                        CIDorder = CIDorder + 1;
                        Log.d("CIDorderrre", String.valueOf(CIDorder));
                    }
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
                accc.setText("Acc Number :"+" "+String.valueOf(CIDorder));
            }
            else {
                cursor.close();
                sqlite.close();
                database.close();
                CIDorder =  1;
                accc.setText("Acc Number :"+" "+String.valueOf(CIDorder));
                Log.d("CIDorderrre", String.valueOf(CIDorder));
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }
    }

}
