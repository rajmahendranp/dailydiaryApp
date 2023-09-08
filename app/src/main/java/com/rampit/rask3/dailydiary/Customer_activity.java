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

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdaptercustomer;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

//Updated on 25/01/2022 by RAMPIT
//Show all users
//InternetConnection1() - Check whether internet is on or not
//updateLabel() - Update textview when date is selected
//populateRecyclerView() - get all current and NIP users with debit
//populateRecyclerView1() -  get all NIPNIP users with debit
//list() -  show all datas
//calculate() -  Calculate missing , paid and balacne days and amount
//popup() -  Show debit details in popup
//onBackPressed() - function called when back button is pressed
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed
//onNavigationItemSelected() - when navigation item pressed
//progressbar_load() - Load progressbar
//progre() - Stop progressbar

public class Customer_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView name,pass,city,pin,email,phone,noo,dateee,session;
    EditText datt;
    TextView dcc,intr,dday,ins,ddd;
    Button submit,add,edit;
    String ss,Id,timing,names12,slno,slno1,datead;
    Integer ses,disco;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    public static String TABLENAME = "REGISTER";
    RecyclerView recyclerView;
    Cursor cursor;
    public static String TABLE_NAME ="NAMES";
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<Long> ID = new ArrayList<>();
    RecyclerViewAdaptercustomer mAdapter;
    String from,to;
    Calendar myCalendar;
    String dateString,idd;
    SearchView searchView,datesearch;
    Integer balanceamount,debitam1,instamt,pamount,pdays,toda,misda,sll,misam,toda1;
    Double paiddays,balanceday,totda,dbdate,todate,juj,missda1,toda2;
    String iii,nme,paidamount,installment,totaldays,ddbt,debitdaaa,pp,ppd,balanc,missed,closing,paidays,infoo,mieesd,indidd;
    NavigationView navigationView;
    String zero,one,two,three,four,five,six,seven,eight,nine,ten;
    Menu nav_Menu;
    SharedPreferences.Editor editor;
    ImageView seesim;
    Integer in ;
    LinearLayout lll;
    ImageButton clos;
    Button logou,profil,search,clear;
    DatePickerDialog datePickerDialog;
    public static String TABLENAME5 = "dd_collection";
    Dialog dialog;
    Integer showinng = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
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
        dialog = new Dialog(Customer_activity.this,R.style.AlertDialogTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_customers);
        setTitle(R.string.title_activity_customers);
        ((Callback)getApplication()).datee();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        editor = pref.edit();
        datt = findViewById(R.id.search1);
        clos = findViewById(R.id.closedd);
        zero = pref.getString("company","");
        one = pref.getString("user1","");
        two = pref.getString("debit","");
        three = pref.getString("collection","");
        four = pref.getString("account","");
        five = pref.getString("reports","");
        six = pref.getString("customer","");
        seven = pref.getString("settings","");
        eight = pref.getString("tally","");
        nine = pref.getString("employee_details","");
        ten = pref.getString("today_report","");
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        nav_Menu = navigationView.getMenu();
        lll = (findViewById(R.id.linee));
        TextView pro = lll.findViewById(R.id.pro);
        logou = lll.findViewById(R.id.logout);
        profil = lll.findViewById(R.id.profile);
        if(zero.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.company).setVisible(false);
        }
        if(one.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.user).setVisible(false);
        }
        if(two.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.debit).setVisible(false);
        }
        if(three.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.collection).setVisible(false);
        }
        if(four.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.account).setVisible(false);
        }
        if(five.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.report).setVisible(false);
        }
        if(six.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.customer).setVisible(false);
        }
        if(seven.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.settings).setVisible(false);
            profil.setVisibility(View.GONE);
            pro.setVisibility(View.GONE);
        }
        if(eight.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.tally).setVisible(false);
        }
        if(nine.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.employee).setVisible(false);
        }
        if(ten.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.today).setVisible(false);
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),Customer_activity.this)) {
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

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
//        datead = df1.format(c.getTime());
//            datt.setText(datead);
        myCalendar = Calendar.getInstance();
toda1 = 0;
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
//        datt.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                if (in == 0){
//                    datePickerDialog =  new DatePickerDialog(Customer_activity.this,R.style.DialogTheme, date, myCalendar
//                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                            myCalendar.get(Calendar.DAY_OF_MONTH ));
//                }else{
//                    datePickerDialog =  new DatePickerDialog(Customer_activity.this,R.style.DialogTheme1, date, myCalendar
//                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                            myCalendar.get(Calendar.DAY_OF_MONTH ));
//                }
//                datePickerDialog.show();
//                String myFormat = "yyyy/MM/dd"; //In which you need put here
//                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
////                dateString = sdf.format(myCalendar.getTime());
//                String myFormat1 = "dd/MM/yyyy"; //In which you need put here
////                mAdapter.filter(dateString);
//                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
//                datt.setText(sdf1.format(myCalendar.getTime()));
////                sumcollection1();
//            }
//        });
//        clos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar c = Calendar.getInstance();
//                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//                SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
//                String forrrddd = df1.format(c.getTime());
//                datt.setText(forrrddd);
//
//            }
//        });
       datt.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        datt.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) { //do your work here }

                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) { //do your work here }
                    String yu = String.valueOf(s);
                    if(Names.size() > 0){
                        mAdapter.filter(yu);
                    }
                }else{
//populateRecyclerView();
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

                }
            }
        });
//        lll = (findViewById(R.id.linee));
//        logou = lll.findViewById(R.id.logout);
//        profil = lll.findViewById(R.id.profile);
        seesim = findViewById(R.id.sesimg);
        Button theme = lll.findViewById(R.id.theme);
        if(in == 0){
            logou.setBackgroundResource(R.drawable.logout);
            profil.setBackgroundResource(R.drawable.user1);
            theme.setBackgroundResource(R.drawable.theme);
        }else{
            logou.setBackgroundResource(R.drawable.logoutblue);
            profil.setBackgroundResource(R.drawable.user_blue);
            theme.setBackgroundResource(R.drawable.theme1);
        }
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Customer_activity.this,Settings_activity.class);
                startActivity(i);
                finish();
            }
        });
        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String err = pref.getString("theme","");
                if(err.equalsIgnoreCase("")){
                    err = "1";
                }
                Integer trt = Integer.parseInt(err);
                if(trt.equals(0)){
                    editor.putString("theme","1");
                    editor.apply();
                    Intent i = new Intent(Customer_activity.this,NavigationActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    editor.putString("theme","0");
                    editor.apply();
                    Intent i = new Intent(Customer_activity.this,NavigationActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        });

        logou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Customer_activity.this,R.style.AlertDialogTheme);
                String logmsg = getString(R.string.logout_alert);
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
                alertbox.setPositiveButton(logo,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                editor.clear();
                                editor.putInt("isloginn",0);
                                editor.commit();
                                Intent i = new Intent(Customer_activity.this,Splash.class);
                                startActivity(i);
                                finish();
                            }
                        });
                alertbox.show();
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
        seesim = findViewById(R.id.sesimg);
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
        session.setText(timing);
        String dsdsd = pref.getString("NAME","");
        if(ses == 1){
            ss = getString(R.string.morning);
        }else if(ses == 2){
            ss = getString(R.string.evening);
        }
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        TextView nn = headerView.findViewById(R.id.name);
        TextView nnn = headerView.findViewById(R.id.date);
        TextView nnnn = headerView.findViewById(R.id.session);
        LinearLayout wq = headerView.findViewById(R.id.navdash);
        ImageView logy = headerView.findViewById(R.id.logy);
        if(in == 0){
            logy.setBackgroundResource(R.drawable.logout);
        }else{
            logy.setBackgroundResource(R.drawable.logoutblue);
        }
        logy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Customer_activity.this,R.style.AlertDialogTheme);
                String logmsg = getString(R.string.logout_alert);
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
                alertbox.setPositiveButton(logo,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                editor.clear();
                                editor.putInt("isloginn",0);
                                editor.commit();
                                Intent i = new Intent(Customer_activity.this,Splash.class);
                                startActivity(i);
                                finish();
                            }
                        });

                alertbox.show();
            }
        });
        wq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent qq = new Intent(Customer_activity.this,NavigationActivity.class);
                startActivity(qq);
                finish();
            }
        });

        nn.setText(dsdsd);
        nnn.setText("Date :" + " " + dd);
        nnnn.setText("Session :" + " " + ss);
//        sqlite = new SQLiteHelper(this);
        recyclerView = findViewById(R.id.re);
        noo = findViewById(R.id.no);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        sll = 0;
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
        Intent name = getIntent();
        idd = name.getStringExtra("ID");
        indidd = name.getStringExtra("DID");
        if(idd == null) {
            idd ="0";
            Toast.makeText(getApplicationContext(), idd, Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(getApplicationContext(), idd, Toast.LENGTH_SHORT).show();
            list();
        }
    }
    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Customer_activity account) {
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

    //updateLabel() - Update textview when date is selected
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";
        String mmm = "dd/MM/yyyy";//In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(mmm, Locale.US);
        datead = sdf1.format(myCalendar.getTime());
        String ddd = sdf.format(myCalendar.getTime());
//        datead = myCalendar.get
        datt.setText(datead);
          mAdapter.filter(ddd);
        Log.d("hjyj","uyuy");
    }
    //populateRecyclerView() - get all current and NIP users with debit
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView() {
        Names.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String[] columns = {"debit_amount",
                "id",""};
        String MY_QUERY = "SELECT a.*,b.customer_name,b.CID,b.order_id_new,b.id as ID FROM  dd_customers b LEFT JOIN dd_account_debit a  on a.customer_id = b.id WHERE b.tracking_id = ? AND b.debit_type IN (0,1) AND a.active_status = 1 GROUP BY b.id ORDER BY b.CID ASC";

        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{String.valueOf(ses)});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    String ddid = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("CID");
                    long id = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("ID");
                    long id1 = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String dbamount = cursor.getString(index);
                    sll = sll + 1;

                    Names.add(id+","+Name+","+dbamount+","+id1+","+ddid+","+id);
                    ID.add(id);

                    Log.d("names", String.valueOf(sll));
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
        populateRecyclerView1();

//        if(Names.size() == 0){
//            recyclerView.setVisibility(View.GONE);
//            noo.setVisibility(View.VISIBLE);
//        }else {
//            recyclerView.setVisibility(View.VISIBLE);
//            noo.setVisibility(View.GONE);
//            mAdapter = new RecyclerViewAdaptercustomer(Names,in, getApplicationContext(), Customer_activity.this);
//            recyclerView.setAdapter(mAdapter);
//        }
    }
    //populateRecyclerView1() -  get all NIPNIP users with debit
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView1() {
//        Names.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String[] columns = {"debit_amount",
                "id",""};
        String MY_QUERY = "SELECT a.*,b.customer_name,b.CID,b.id as ID,b.order_id_new FROM dd_customers b LEFT JOIN dd_account_debit a  on a.customer_id = b.id  WHERE b.tracking_id = ? AND b.debit_type = '2' AND a.active_status = 1 GROUP BY b.id ORDER BY b.order_id_new ASC";

        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{String.valueOf(ses)});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    String ddid = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String ddid1 = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("CID");
                    long id = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("ID");
                    long id1 = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String dbamount = cursor.getString(index);
                    sll = sll + 1;

                    Names.add(id+","+Name+","+dbamount+","+id1+","+ddid+","+ddid1);
                    ID.add(id);

                    Log.d("names", String.valueOf(sll));
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
            mAdapter = new RecyclerViewAdaptercustomer(Names,in, getApplicationContext(), Customer_activity.this);
            recyclerView.setAdapter(mAdapter);
        }
    }
    //list() -  show all datas
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void list(){
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        Intent name = getIntent();
        idd = name.getStringExtra("ID");
        indidd = name.getStringExtra("DID");
//        if(idd == null) {
//            idd ="0";
//            Toast.makeText(getApplicationContext(), idd, Toast.LENGTH_SHORT).show();
//        }
//        else {
            Toast.makeText(getApplicationContext(), idd, Toast.LENGTH_SHORT).show();
            String MY_QUERY = "SELECT a.*,b.customer_name,b.id as ID,b.location,b.phone_1 FROM dd_customers b LEFT JOIN dd_account_debit a  on a.customer_id = b.id  WHERE b.id =? AND a.active_status = 1 AND b.tracking_id = ? ";

            Cursor cursor = database.rawQuery(MY_QUERY, new String[]{idd, String.valueOf(ses)});
            if (cursor != null) {
                if (cursor.getCount() != 0) {
                    cursor.moveToFirst();
                    do {
                        int index;
                        index = cursor.getColumnIndexOrThrow("id");
                        String orde = cursor.getString(index);
                        iii = cursor.getString(index);
                        index = cursor.getColumnIndexOrThrow("customer_name");
                        String Name = cursor.getString(index);
                        nme = cursor.getString(index);
                        index = cursor.getColumnIndexOrThrow("debit_date");
                        debitdaaa = cursor.getString(index);
                        index = cursor.getColumnIndexOrThrow("debit_amount");
                        ddbt = cursor.getString(index);
                        index = cursor.getColumnIndexOrThrow("location");
                        String Loc = cursor.getString(index);
                        index = cursor.getColumnIndexOrThrow("phone_1");
                        String Pho = cursor.getString(index);
                        index = cursor.getColumnIndexOrThrow("closeing_date");
                         closing = cursor.getString(index);
                        index = cursor.getColumnIndexOrThrow("installment_amount");
                        installment = cursor.getString(index);
                        index = cursor.getColumnIndexOrThrow("debit_days");
                        totaldays = cursor.getString(index);

//                        slno.setText("ID :" + " " + orde);
//                        namee.setText("NAME :" + " " + Name);
//                        opd.setText("Opening Date :" + " " + debitdaaa);
//                        deb.setText("Debit Amount :" + " " + ddbt);
//                        loc.setText("Location :" + " " + Loc);
//                        pho.setText("Phone :" + " " + Pho);
//                        cd.setText("Closing Date :" + " " + Clo);
//                        tod.setText("Total days :" + " " + totaldays);

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
            calculate();
//        }
    }
    //calculate() -  Calculate missing , paid and balacne days and amount
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void calculate(){
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        String[] columns = {"debit_amount",
                "id", ""};
        String ii = "1";
        String SUM_QUERY = "SELECT sum(a.collection_amount) as paid,sum(a.discount) as disc,b.tracking_id,b.info,b.CID,b.customer_name,b.order_id_new FROM  dd_customers b LEFT JOIN dd_account_debit c  on c.customer_id = b.id  LEFT JOIN dd_collection a ON a.debit_id = c.id  WHERE c.customer_id = ? AND b.tracking_id =? AND c.active_status = 1  ";
        Cursor cur = database.rawQuery(SUM_QUERY,new String[]{idd, String.valueOf(ses) });

        if (cur != null) {
            if (cur.getCount() != 0) {
                cur.moveToFirst();
                do {
                    int index;

                    index = cur.getColumnIndexOrThrow("paid");
                    Integer orde = cur.getInt(index);
                    index = cur.getColumnIndexOrThrow("disc");
                    disco = cur.getInt(index);
                    index = cur.getColumnIndexOrThrow("info");
                     infoo = cur.getString(index);
                    index = cur.getColumnIndexOrThrow("customer_name");
                    names12 = cur.getString(index);
                    index = cur.getColumnIndexOrThrow("CID");
                    slno = cur.getString(index);
                    index = cur.getColumnIndexOrThrow("order_id_new");
                    slno1 = cur.getString(index);
                    Log.d("paiddd", names12+" "+slno);
                    paidamount = String.valueOf(orde);
                }
                while (cur.moveToNext());
                cur.close();
                sqlite.close();
                database.close();
            }else{
                cur.close();
                sqlite.close();
                database.close();
            }
        }else{
            cur.close();
            sqlite.close();
            database.close();
        }
        if(paidamount != "0" && installment != null){
            instamt = Integer.parseInt(installment);
            pamount = Integer.parseInt(paidamount);
            paiddays = (double) pamount / instamt;
            if(String.valueOf(paiddays).equalsIgnoreCase("NaN")){
                paiddays = 0.0;
            }
//                  Log.d("paiddd", String.valueOf(instamt));
            DecimalFormat df = new DecimalFormat("####.##");
            paiddays = Double.valueOf(df.format(paiddays));
            ppd = df.format(paiddays);
            pdays = paiddays.intValue();
            paidays = String.valueOf(pdays);
        }
        if(ddbt != null && paidamount != "0"){
            debitam1 = Integer.parseInt(ddbt);
            balanceamount = debitam1 - pamount;
            balanceamount = balanceamount - disco;
            balanc = String.valueOf(balanceamount);
        }
        if(totaldays != null ){
            totda = Double.parseDouble(totaldays);
            balanceday = totda - paiddays;
            DecimalFormat df = new DecimalFormat("####.##");
            balanceday = Double.valueOf(df.format(balanceday));
            pp = df.format(balanceday);
        }
        if(debitdaaa != null){
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            String formattedDate = df.format(c.getTime());
            try {
                Date newda = df.parse(formattedDate);
                Date debit = df.parse(debitdaaa);
                long diffInMillisec =  newda.getTime() - debit.getTime();
                long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                if(diffInDays == 0){
                    misda = 0;
                    misam = 0;
                    missed = String.valueOf(misda);
                    toda = 0;
                    toda1 = 1;
                    missda1 = 0.0;
                }else {
                    String tod = String.valueOf(diffInDays);
                    toda = Integer.parseInt(tod);
                    toda1 = Integer.parseInt(tod)+1;
//                                todaaal = new doub
//        Integer po = Integer.parseInt(pp);
                    Integer tota = Integer.parseInt(totaldays);
                    Double mii1 = new Double(toda);
                    Double mii2 = new Double(pdays);
                    misda =  toda - pdays;
                    missed = String.valueOf(misda);
                    Double mii;
                    mii = mii1 - mii2;
                    toda2 = Double.parseDouble(tod);
                    missda1 = toda2 - paiddays;
                    DecimalFormat df11 = new DecimalFormat("####.##");
                    missda1 = Double.valueOf(df11.format(missda1));
                    if(missda1 < 0.0 || missda1 == null){
                        missda1 = 0.0 ;
                    }
                    if (misda >= balanceday) {
                        misam = balanceamount;
//                        misda = balanceday.intValue();
//                        missed = String.valueOf(misda);
//                        misda = Integer.parseInt(mieesd);
                    } else {
                        misam = misda * instamt;
                        if(misam < 0){
                            misam = 0;
                        }
                    }
                    Log.d("mmm", String.valueOf(misda));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
Integer uh = Integer.parseInt(slno1);
        if(uh<=5000) {
           popup(paidamount, balanc, String.valueOf(missda1), String.valueOf(paiddays), closing, debitdaaa, totaldays, infoo, names12, slno, ddbt, in, toda1);
        }else if(uh>5000){
           popup(paidamount, balanc, String.valueOf(missda1), String.valueOf(paiddays), closing, debitdaaa, totaldays, infoo, names12, slno1, ddbt, in, toda1);
        }
    }

    //popup() -  Show debit details in popup
    //Params - paid amount , balance amount , missed amount , paid dayas , closing date , debit date , total days , info , name , slno , debit amount ,theme , today collection
    //Created on 25/01/2022
    //Return - NULL
    public void popup(String paidamount, String balanc, String missed, String paidays, String closing, String debitdaaa, String totaldays, String infoo, String names12, String slno, String debit, Integer in, Integer toda){

        final Dialog dialog = new Dialog(Customer_activity.this);
        //set layout custom
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popcustomer);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);
//        final RecyclerView rvcaddy = (RecyclerView) dialog.findViewById(R.id.rvAnimals);
        final TextView ddate = (TextView) dialog.findViewById(R.id.debitdate);
        final TextView camount = (TextView) dialog.findViewById(R.id.creditedamount);
        final TextView slnno = (TextView) dialog.findViewById(R.id.slno);
        final TextView naaam = (TextView) dialog.findViewById(R.id.name);
        final TextView dbam = (TextView) dialog.findViewById(R.id.debitamount);
        final TextView bamount = (TextView) dialog.findViewById(R.id.balanceamount);
        final TextView cdate = (TextView) dialog.findViewById(R.id.closingdate);
        final TextView inf = (TextView) dialog.findViewById(R.id.info);
        final TextView tdays = (TextView) dialog.findViewById(R.id.totaldays);
        final TextView cdays = (TextView) dialog.findViewById(R.id.completeddays);
        final TextView mdays = (TextView) dialog.findViewById(R.id.missingdays);
        final TextView days1 = (TextView) dialog.findViewById(R.id.days);
        final Button close = (Button)dialog.findViewById(R.id.close);
        if(in == 0){
            close.setBackgroundResource(R.drawable.close);
        }else{
            close.setBackgroundResource(R.drawable.close_blue1);
        }
        String formattedDate = null;
        String formattedDate2 = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date debit2 = df.parse(debitdaaa);
            formattedDate = df1.format(debit2);
            Date debit22 = df.parse(closing);
            formattedDate2 = df1.format(debit22);
            Log.d("deae",formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        slnno.setText(slno);
        naaam.setText(names12);
        ddate.setText(formattedDate);
        dbam.setText("\u20B9"+" "+debit);
        camount.setText("\u20B9"+" "+paidamount);
        bamount.setText("\u20B9"+" "+balanc);
        cdate.setText(formattedDate2);
        mdays.setText(missed);
        tdays.setText(totaldays);
        cdays.setText(paidays);
        inf.setText(infoo);
        days1.setText(String.valueOf(toda));

        dialog.show();
    }

    //onBackPressed() - function called when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onBackPressed() {
      Intent nn = new Intent(Customer_activity.this,NavigationActivity.class);
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
//        getMenuInflater().inflate(R.menu.main2, menu);
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
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
    //onNavigationItemSelected() - when navigation item pressed
    //Params - selected item
    //Created on 25/01/2022
    //Return - NULL
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.company) {
            // Handle the camera action
            Intent new1 = new Intent(Customer_activity.this,NavigationActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.user) {
            Intent new1 = new Intent(Customer_activity.this,HomeActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.debit) {
            Intent new1 = new Intent(Customer_activity.this,Debit.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.collection) {
            Intent new1 = new Intent(Customer_activity.this,AllCollection.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.account) {
            Intent new1 = new Intent(Customer_activity.this,Account.class);
            startActivity(new1);
            finish();
        }  else if (id == R.id.report) {
            Intent new1 = new Intent(Customer_activity.this,ReportActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.customer) {
            Intent new1 = new Intent(Customer_activity.this,Customer_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.settings) {
            Intent new1 = new Intent(Customer_activity.this,Settings_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.tally) {
            Intent new1 = new Intent(Customer_activity.this,Tally_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.employee) {
            Intent new1 = new Intent(Customer_activity.this,Employee_details.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.today) {
            Intent new1 = new Intent(Customer_activity.this,Today_report.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.logout) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Customer_activity.this,R.style.AlertDialogTheme);
            alertbox.setMessage("Are You Sure want to logout");
            alertbox.setTitle("Warning");
            alertbox.setIcon(R.drawable.logo);
            alertbox.setNeutralButton("CANCEL",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {


                        }
                    });
            alertbox.setPositiveButton("LOGOUT",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {

                            editor.clear();
                            editor.putInt("isloginn",0);
                            editor.commit();
                            Intent i = new Intent(Customer_activity.this,Splash.class);
                            startActivity(i);
                            finish();
                        }
                    });
            alertbox.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
