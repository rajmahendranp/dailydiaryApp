package com.rampit.rask3.dailydiary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.rampit.rask3.dailydiary.Adapter.MyRecyclerAdapter3;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdapteraccounting;
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
import java.util.concurrent.TimeUnit;


//Updated on 25/01/2022 by RAMPIT
//used view and filter all account datas
//InternetConnection1() - Check whether internet is on or not
//popup() - Add new account type
//populateRecyclerView4() - Get all account datas
//populateRecyclerView() - Get all account datas
//populateRecyclerView1() - Get all credit account datas
//populateRecyclerView2() - Get all debit account datas
//updateLabel() - Update textview when date is selected
//updateLabel1() - Update textview when date is selected
//onBackPressed() - function called when back button is pressed
//progressbar_load() - Load progressbar
//progre() - Stop progressbar
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed
//onNavigationItemSelected() - when navigation item pressed


public class Account extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
Button add;
EditText datt;
String dateString,typeid,acda;
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> Names1 = new ArrayList<>();
    ArrayList<String> Amountt = new ArrayList<>();
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    RecyclerView recyclerView;
    Calendar myCalendar,myCalendar1;
    String ss,zero,one,two,three,four,five,six,seven,eight,nine,ten,idd,adpr,edpr,depr,vipr,dateString1;
    Spinner type;
    Integer ses,totcr,totde;
    String timing;
    TextView noo,dateee,session,from,to,total,varavu,selavu;
    RecyclerViewAdapteraccounting mAdapter;
    NavigationView navigationView;
    Menu nav_Menu;
    SimpleDateFormat sdf1;
    LinearLayout lll;
    Button logou,profil,search,clear;
    ImageButton clos;
    SharedPreferences.Editor editor;
    ImageView seesim;
    Integer sl,amoun1,in;
    String MY_QUERY,spinn,weekday,USER,balat;
    Cursor cursor;
    DatePickerDialog datePickerDialog,datePickerDialog1;
    Dialog dialog ;
    CoordinatorLayout coordinatorLayout;
    Integer showinng = 0;

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
        dialog = new Dialog(Account.this,R.style.AlertDialogTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_account);
        setTitle(R.string.title_activity_account);
        ((Callback)getApplication()).datee();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        coordinatorLayout = findViewById(R.id.coordinator);
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),Account.this)) {
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
                                                    .make(coordinatorLayout, getString(R.string.on_internet_for_backup), Snackbar.LENGTH_INDEFINITE)
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

        from = findViewById(R.id.from_date);
        to = findViewById(R.id.to_date);
        clear = findViewById(R.id.clear);
        search = findViewById(R.id.search);
        varavu = findViewById(R.id.varavu);
        selavu = findViewById(R.id.selavu);
        editor = pref.edit();
        lll = (findViewById(R.id.linee));
        TextView pro = lll.findViewById(R.id.pro);
        logou = lll.findViewById(R.id.logout);
        profil = lll.findViewById(R.id.profile);
        String sesid = pref.getString("id","");
        String module_i = "5";
        ((Callback)getApplication()).privilege(sesid,module_i);
        adpr = pref.getString("add_privilege","");
        edpr = pref.getString("edit_privilege","");
        depr = pref.getString("delete_privilege","");
        vipr = pref.getString("view_privilege","");
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
                Intent i = new Intent(Account.this,Settings_activity.class);
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
                    Intent i = new Intent(Account.this,NavigationActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    editor.putString("theme","0");
                    editor.apply();
                    Intent i = new Intent(Account.this,NavigationActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        });
        logou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Account.this,R.style.AlertDialogTheme);
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
                                Intent i = new Intent(Account.this,Splash.class);
                                startActivity(i);
                                finish();
                            }
                        });
                alertbox.show();
            }
        });
        ses = pref.getInt("session", 1);
        String dd = pref.getString("Date","");
        total = findViewById(R.id.total);
        dateee = findViewById(R.id.da);
        add = findViewById(R.id.acc_add);
        session = findViewById(R.id.sess);
        dateee.setText(dd);
        if(in == 0){
            add.setBackgroundResource(R.drawable.add);
            if(ses == 1){
                timing = getString(R.string.morning);
                seesim.setBackgroundResource(R.drawable.sun);
            }else if(ses == 2){
                timing = getString(R.string.evening);
                seesim.setBackgroundResource(R.drawable.moon);
            }
        }else{
            add.setBackgroundResource(R.drawable.add_blue);
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
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Account.this,R.style.AlertDialogTheme);
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
                                Intent i = new Intent(Account.this,Splash.class);
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
                Intent qq = new Intent(Account.this,NavigationActivity.class);
                startActivity(qq);
                finish();
            }
        });
        nn.setText(dsdsd);
        nnn.setText("Date :" + " " + dd);
        nnnn.setText("Session :" + " " + ss);
        dateString1 = "";
        dateString = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        final String formattedDate = df.format(c.getTime());
        String myFormat1 = "dd/MM/yyyy";//In which you need put here
        sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
//        datt.setText( sdf1.format(c.getTime()));

//        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        balat = pref.getString("totalbal", "");
        recyclerView = findViewById(R.id.re);
        datt = findViewById(R.id.date);
        noo = findViewById(R.id.no);
        type = findViewById(R.id.spinner);
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
//            recreate();
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
//                    populateRecyclerView1();
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
                                    ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
                                    ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
                                    populateRecyclerView1();
                                }
                            });
                        }
                    }, 2500);

                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            progressbar_load();
                        }
                    });
                }else if(typeid.equalsIgnoreCase("2")){
//                    populateRecyclerView2();
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
                                    ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
                                    ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
                                    populateRecyclerView2();
                                }
                            });
                        }
                    }, 2500);

                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            progressbar_load();
                        }
                    });
                }else if(typeid.equalsIgnoreCase("0")){
                    if(dateString.equalsIgnoreCase("") || dateString1.equalsIgnoreCase("")) {
//                        populateRecyclerView4();
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
                                        ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
                                        ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
                                        populateRecyclerView4();
                                    }
                                });
                            }
                        }, 2500);

                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                progressbar_load();
                            }
                        });
                    }else{
//                        populateRecyclerView();
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
                                        ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
                                        ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
                                        populateRecyclerView();
                                    }
                                });
                            }
                        }, 2500);

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
                Log.d("typedd",String.valueOf(nu));
            }
            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }

        });
        myCalendar = Calendar.getInstance();
        myCalendar1 = Calendar.getInstance();

//        dateString = formattedDate;
//        dateString1 = formattedDate;
        datt.setText(formattedDate);
//        populateRecyclerView4();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
                        ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
                        ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
                        populateRecyclerView4();
                    }
                });
            }
        }, 2500);

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
        clos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datt.setText("");
                dateString = "";
                dateString1 = "";
//                populateRecyclerView4();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
                                ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
                                ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
                                populateRecyclerView4();
                            }
                        });
                    }
                }, 2500);

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
        from.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (in == 0){
                    datePickerDialog =  new DatePickerDialog(Account.this,R.style.DialogTheme, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH ));
                }else{
                    datePickerDialog =  new DatePickerDialog(Account.this,R.style.DialogTheme1, date, myCalendar
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
        to.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (in == 0){
                    datePickerDialog1 =  new DatePickerDialog(Account.this,R.style.DialogTheme, date1, myCalendar1
                            .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                            myCalendar1.get(Calendar.DAY_OF_MONTH ));
                }else{
                    datePickerDialog1 =  new DatePickerDialog(Account.this,R.style.DialogTheme1, date1, myCalendar1
                            .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                            myCalendar1.get(Calendar.DAY_OF_MONTH ));
                }
                datePickerDialog1.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog1.show();
                String myFormat = "yyyy/MM/dd";
                String myFormat1 = "dd/MM/yyyy";//In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                dateString1 = sdf.format(myCalendar.getTime());
                to.setText(sdf1.format(myCalendar.getTime()));
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                from.setText("");
                to.setText("");
                dateString = "";
                dateString1 = "";
                type.setSelection(0);
//                populateRecyclerView4();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
                                ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
                                ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
                                populateRecyclerView4();
                            }
                        });
                    }
                }, 2500);

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
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dateString.equalsIgnoreCase("")||dateString1.equalsIgnoreCase("")){}else{

                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    Date newda = null;
                    try {
                        newda = df.parse(dateString);
                        Date debit = df.parse(dateString1);
                        long diffInMillisec =  newda.getTime() - debit.getTime();
                        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                        Log.d("day5",String.valueOf(diffInDays));
                        if(diffInDays > 0){
                            AlertDialog.Builder alertbox = new AlertDialog.Builder(Account.this,R.style.AlertDialogTheme);
                            String enn = getString(R.string.fromtodate);
                            String war = getString(R.string.warning);
                            String ook = getString(R.string.ok);
                            alertbox.setMessage(enn);
                            alertbox.setTitle(war);
                            alertbox.setIcon(R.drawable.logo);
                            alertbox.setPositiveButton(ook,
                                    new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface arg0,
                                                            int arg1) {
                                        }
                                    });
                            alertbox.show();
                        }else{ Log.d("bothdates",dateString+" "+dateString1);

                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
                                            ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
                                            ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
                                            populateRecyclerView();
                                        }
                                    });
                                }
                            }, 2500);

                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    progressbar_load();
                                }
                            });
                            type.setSelection(0);}

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }



                }
            }

        });
//        datt.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                new DatePickerDialog(Account.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                String myFormat = "yyyy/MM/dd"; //In which you need put here
//                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//                dateString = sdf.format(myCalendar.getTime());
//                datt.setText(sdf.format(myCalendar.getTime()));
//
//            }
//        });
//
//        datt.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//                if (s.length() > 0)
//                { //do your work here }
//                    dateString = datt.getText().toString();
//                    Log.d("datata",dateString);
//                   if(Names.size() == 0){
//                   }else{
//                       mAdapter.filter(dateString);
//                   }
//                }
//            }
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//            }
//            public void afterTextChanged(Editable s) {
//                dateString = datt.getText().toString();
//                Log.d("datata",dateString);
//                if(Names.size() == 0){
//                }else{
//                    mAdapter.filter(dateString);
//                }
//            }
//        });
        if(adpr.equalsIgnoreCase("0")){
            add.setVisibility(View.GONE);
        }
    add.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
Intent news = new Intent(Account.this,Addaccount.class);
startActivity(news);
            finish();
        }
    });
        varavu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup("1");
            }
        });
        selavu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup("2");

            }
        });
    }

    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Account account) {
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

    //popup() - Add new account type
    //Params - accont type
    //Created on 25/01/2022
    //Return - NULL
    public void popup(final String ii){
        Names1.clear();
        sqlite = new SQLiteHelper(Account.this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY = "SELECT * FROM dd_accounting_type WHERE acc_type = ?  ";

        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{ii});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("acc_type_name");
                    String Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    long id = cursor.getLong(index);


                    Names1.add( id+ "," + Name);
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
        final Dialog dialog = new Dialog(Account.this);
        //set layout custom
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popaccount);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        final RecyclerView rvcaddy = (RecyclerView) dialog.findViewById(R.id.rvAnimals);
        final EditText eddd = (EditText) dialog.findViewById(R.id.edit);
        final Button savv = (Button) dialog.findViewById(R.id.save);
        final MyRecyclerAdapter3 adapter = new MyRecyclerAdapter3(getApplicationContext(),Names1,Account.this,ses);
        final Button close = (Button) dialog.findViewById(R.id.close);
        savv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = eddd.getText().toString();
                if(value.equalsIgnoreCase("")){

                }else{
//                    Log.d("daa", data + dataid + amo + nnnt + dateString);
                    sqlite = new SQLiteHelper(Account.this);
                    database = sqlite.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("acc_type_name", value);
                    values.put("acc_type", ii);
                    values.put("created_date", dateString);
                    database.insert("dd_accounting_type", null, values);
                    sqlite.close();
                    database.close();
                    Intent ne = new Intent(Account.this, Account.class);
//                ne.putExtra("id",dataid);
                    startActivity(ne);
                    finish();
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        if(Names1.size() == 0){
            rvcaddy.setVisibility(View.GONE);
            noo.setVisibility(View.VISIBLE);
        }else {
            rvcaddy.setVisibility(View.VISIBLE);
            noo.setVisibility(View.GONE);
            rvcaddy.setAdapter(adapter);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvcaddy.setLayoutManager(mLayoutManager);
        }
        dialog.show();
    }

    //populateRecyclerView4() - Get all account datas
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView4() {
        Names.clear();
        sl = 0;
        amoun1 = 0;
        totcr = 0;
        totde = 0;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY = "SELECT a.*,b.acc_type_name,b.acc_type,SUM(a.amount) as total FROM dd_accounting a LEFT JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE a.tracking_id = ?  GROUP BY a.id ORDER BY a.accounting_date DESC";
        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{String.valueOf(ses)});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("accounting_date");
                    String ate = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    long id = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("acc_type_name");
                    String acc1 = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("acc_type");
                    Integer accty = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("note");
                    String acc11 = cursor.getString(index);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    String myFormat1 = "dd/MM/yyyy";//In which you need put here
                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                    try {
                        Date debit = sdf.parse(ate);
                        acda = sdf1.format(debit);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
//                    if(acc.equalsIgnoreCase("1")){
//                        acc ="Credit";
//                    }else if(acc.equalsIgnoreCase("2")){
//                        acc = "Debit";
//                    }
                    index = cursor.getColumnIndexOrThrow("amount");
                    String amm = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("total");
                    String am_to = cursor.getString(index);
                    Integer ana = Integer.parseInt(am_to);

                    if(accty == 1){
                        totcr = totcr + Integer.valueOf(amm);
                    }else{
                        totde = totde + Integer.valueOf(amm);
                    }
                    amoun1 = totcr - totde;
                    //                    String parsedStr = acc1.replaceAll("(.{5})", "$1\n");

//                    index = cursor.getColumnIndexOrThrow("amount");
//                    String tot = cursor.getString(index);
                    if(acda == null){
                        acda = "0";
                    }
                    if(acc1 == null){
                        acc1 = "0";
                    }
                    if(acc11 == null || acc11.equalsIgnoreCase("")){
                        acc11 = "NO";
                    }
                    Log.d("o0",acc11);
                    if(amm == null){
                        amm = "0";
                    }
                    if(sl == null){
                        sl = 0;
                    }
                    if(accty == null){
                        accty = 0;
                    }
                    if(acc1.equalsIgnoreCase("Investment")){
                        String nnaa = getApplicationContext().getString(R.string.investment);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Finance")){
                        String nnaa = getString(R.string.finance);
                        USER=nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Chittu")){
                        String nnaa = getString(R.string.chittu2);
                        USER = nnaa ;
                    }
                    else if(acc1.equalsIgnoreCase("Transfer_Hand")){
                        String nnaa = getString(R.string.transferhand);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Anamattu")){
                        String nnaa = getString(R.string.anamattu);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Other_Income")){
                        String nnaa = getString(R.string.otherincome);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Employee_credit")){
                        String nnaa = getString(R.string.employeecredit);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("High")){
                        String nnaa = getString(R.string.high);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Profit")){
                        String nnaa = getString(R.string.profit);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Expense")){
                        String nnaa = getString(R.string.expense);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Petrol")){
                        String nnaa = getString(R.string.petrol);
                        USER = nnaa;
                    }
                    else if(acc1.equals("Rent")){
                        String nnaa = getApplicationContext().getString(R.string.rent1);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Debit_Hand")){
                        String nnaa = getString(R.string.debithand);
                        USER=nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Vehicle_Expense")){
                        String nnaa = getString(R.string.vehicleexpense);
                        USER = nnaa ;
                    }
                    else if(acc1.equalsIgnoreCase("Other_Expense")){
                        String nnaa = getString(R.string.otherexpense);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Donation_Expense")){
                        String nnaa = getString(R.string.donationexpense);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Other_Income")){
                        String nnaa = getString(R.string.otherincome);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Employee_Expense")){
                        String nnaa = getString(R.string.employeeexpense);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Low")){
                        String nnaa = getString(R.string.low_amount);
                        USER = nnaa;
                    }
                    else  if(acc1.equalsIgnoreCase("Lose")){
                        String nnaa = getString(R.string.lose);
                        USER = nnaa;
                    }
                    else{
                        USER = acc1;
                    }

                    if(amm.equalsIgnoreCase("0")){

}else {
    sl = sl + 1;
    Names.add(id + "," + acda + "," + USER + "," + amm + "," + sl+","+accty+","+acc11);
    Amountt.add(amm);
}
                    String fg = getString(R.string.total_balance1);
                    final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    balat = pref.getString("totalbal", "");
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    balat = pref.getString("totalbal", "");
                                    total.setText(fg+" : "+"\u20B9"+balat);
                                }
                            });
                        }
                    }, 500);

                    Log.d("names", String.valueOf(acc11));
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
            mAdapter = new RecyclerViewAdapteraccounting(Names,in,getApplicationContext(),edpr,depr);
            recyclerView.setAdapter(mAdapter);
//            mAdapter.filter(dateString);
        }
    }

    //populateRecyclerView() - Get all account datas
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView() {
        Names.clear();
        sl = 0 ;
        amoun1 = 0 ;
        totcr = 0;
        totde = 0;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY = "SELECT a.*,b.acc_type_name,b.acc_type,SUM(a.amount) as total FROM dd_accounting a LEFT JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE a.accounting_date between ? AND ? AND a.tracking_id = ? GROUP BY a.id ORDER BY a.accounting_date DESC";
        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{dateString,dateString1,String.valueOf(ses)});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("accounting_date");
                    String ate = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    long id = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("acc_type_name");
                    String acc1 = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("acc_type");
                    Integer accty = cursor.getInt(index);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    String myFormat1 = "dd/MM/yyyy";//In which you need put here
                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                    try {
                        Date debit = sdf.parse(ate);
                        acda = sdf1.format(debit);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
//                    if(acc.equalsIgnoreCase("1")){
//                        acc ="Credit";
//                    }else if(acc.equalsIgnoreCase("2")){
//                        acc = "Debit";
//                    }
                    index = cursor.getColumnIndexOrThrow("amount");
                    String amm = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("total");
                    String am_to = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("note");
                    String acc11 = cursor.getString(index);
                    Integer an = Integer.parseInt(am_to);
//                    String parsedStr = acc1.replaceAll("(.{5})", "$1\n");

//                    index = cursor.getColumnIndexOrThrow("amount");
//                    String tot = cursor.getString(index);
                    if (accty == 1) {
                        totcr = totcr + Integer.valueOf(amm);
                    } else {
                        totde = totde + Integer.valueOf(amm);
                    }
                    amoun1 = totcr - totde;
                    sl = sl + 1;
                    if (acda == null) {
                        acda = "0";
                    }
                    if (acc1 == null) {
                        acc1 = "0";
                    }
                    if (amm == null) {
                        amm = "0";
                    }
                    if (sl == null) {
                        sl = 0;
                    }
                    if (accty == null) {
                        accty = 0;
                    }
                    if(acc11 == null || acc11.equalsIgnoreCase("")){
                        acc11 = "NO";
                    }
                    if (acc1.equalsIgnoreCase("Investment")) {
                        String nnaa = getApplicationContext().getString(R.string.investment);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Finance")) {
                        String nnaa = getString(R.string.finance);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Chittu")) {
                        String nnaa = getString(R.string.chittu2);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Transfer_Hand")) {
                        String nnaa = getString(R.string.transferhand);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Anamattu")) {
                        String nnaa = getString(R.string.anamattu);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Other_Income")) {
                        String nnaa = getString(R.string.otherincome);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Employee_credit")) {
                        String nnaa = getString(R.string.employeecredit);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("High")) {
                        String nnaa = getString(R.string.high);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Profit")) {
                        String nnaa = getString(R.string.profit);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Expense")) {
                        String nnaa = getString(R.string.expense);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Petrol")) {
                        String nnaa = getString(R.string.petrol);
                        USER = nnaa;
                    }
                    else if (acc1.equals("Rent")) {
                        String nnaa = getApplicationContext().getString(R.string.rent1);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Debit_Hand")) {
                        String nnaa = getString(R.string.debithand);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Vehicle_Expense")) {
                        String nnaa = getString(R.string.vehicleexpense);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Other_Expense")) {
                        String nnaa = getString(R.string.otherexpense);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Donation_Expense")) {
                        String nnaa = getString(R.string.donationexpense);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Other_Income")) {
                        String nnaa = getString(R.string.otherincome);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Employee_Expense")) {
                        String nnaa = getString(R.string.employeeexpense);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Low")) {
                        String nnaa = getString(R.string.low_amount);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Lose")) {
                        String nnaa = getString(R.string.lose);
                        USER = nnaa;
                    }
                    else{
                        USER = acc1;
                    }
                    if (amm.equalsIgnoreCase("0")) {

                    } else {
                        Names.add(id + "," + acda + "," + USER + "," + amm + "," + sl + "," + accty + "," +acc11);
                        Amountt.add(amm);
                        String fg = getString(R.string.total);
                        total.setText(fg + " : " + "\u20B9" + amoun1);
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
            String fg = getString(R.string.total_balance1);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                            balat = pref.getString("totalbal", "");
                            total.setText(fg+" : "+"\u20B9"+balat);
                        }
                    });
                }
            }, 500);

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
            mAdapter = new RecyclerViewAdapteraccounting(Names, in, getApplicationContext(),edpr,depr);
            recyclerView.setAdapter(mAdapter);
//            mAdapter.filter(dateString);
        }
    }

    //populateRecyclerView1() - Get all credit account datas
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView1() {
        Names.clear();
        sl = 0;
        amoun1 = 0;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        if(dateString.equalsIgnoreCase("") || dateString1.equalsIgnoreCase("")){
             MY_QUERY = "SELECT a.*,b.acc_type_name,SUM(a.amount)as total,b.acc_type FROM dd_accounting a LEFT JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE  a.tracking_id = ? AND b.acc_type = '1' GROUP BY a.id ORDER BY a.accounting_date DESC";
             cursor = database.rawQuery(MY_QUERY,new String[]{String.valueOf(ses)});
        }else {
             MY_QUERY = "SELECT a.*,b.acc_type_name,b.acc_type,SUM(a.amount)as total FROM dd_accounting a LEFT JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE a.accounting_date BETWEEN ? AND ? AND a.tracking_id = ? AND b.acc_type = '1' GROUP BY a.id ORDER BY a.accounting_date DESC";
             cursor = database.rawQuery(MY_QUERY,new String[]{dateString,dateString1,String.valueOf(ses)});
        }

        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("accounting_date");
                    String ate = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    long id = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("acc_type_name");
                    String acc1 = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("acc_type");
                    Integer accty = cursor.getInt(index);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    String myFormat1 = "dd/MM/yyyy";//In which you need put here
                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                    try {
                        Date debit = sdf.parse(ate);
                        acda = sdf1.format(debit);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
//                    if(acc.equalsIgnoreCase("1")){
//                        acc ="Credit";
//                    }else if(acc.equalsIgnoreCase("2")){
//                        acc = "Debit";
//                    }
                    index = cursor.getColumnIndexOrThrow("amount");
                    String amm = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("total");
                    String am_to = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("note");
                    String acc11 = cursor.getString(index);
                    Integer am = Integer.parseInt(am_to);
//                    index = cursor.getColumnIndexOrThrow("amount");
//                    String tot = cursor.getString(index);
                    if (acda == null) {
                        acda = "0";
                    }
                    if (acc1 == null) {
                        acc1 = "0";
                    }
                    if(acc11 == null || acc11.equalsIgnoreCase("")){
                        acc11 = "NO";
                    }
                    if (amm == null) {
                        amm = "0";
                    }
                    if (sl == null) {
                        sl = 0;
                    }
                    if (acc1.equalsIgnoreCase("Investment")) {
                        String nnaa = getApplicationContext().getString(R.string.investment);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Finance")) {
                        String nnaa = getString(R.string.finance);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Chittu")) {
                        String nnaa = getString(R.string.chittu2);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Transfer_Hand")) {
                        String nnaa = getString(R.string.transferhand);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Anamattu")) {
                        String nnaa = getString(R.string.anamattu);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Other_Income")) {
                        String nnaa = getString(R.string.otherincome);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Employee_credit")) {
                        String nnaa = getString(R.string.employeecredit);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("High")) {
                        String nnaa = getString(R.string.high);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Profit")) {
                        String nnaa = getString(R.string.profit);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Expense")) {
                        String nnaa = getString(R.string.expense);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Petrol")) {
                        String nnaa = getString(R.string.petrol);
                        USER = nnaa;
                    }
                    else if (acc1.equals("Rent")) {
                        String nnaa = getApplicationContext().getString(R.string.rent1);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Debit_Hand")) {
                        String nnaa = getString(R.string.debithand);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Vehicle_Expense")) {
                        String nnaa = getString(R.string.vehicleexpense);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Other_Expense")) {
                        String nnaa = getString(R.string.otherexpense);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Donation_Expense")) {
                        String nnaa = getString(R.string.donationexpense);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Other_Income")) {
                        String nnaa = getString(R.string.otherincome);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Employee_Expense")) {
                        String nnaa = getString(R.string.employeeexpense);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Low")) {
                        String nnaa = getString(R.string.low_amount);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Lose")) {
                        String nnaa = getString(R.string.lose);
                        USER = nnaa;
                    }
                    else{
                        USER = acc1;
                    }
                    if (amm.equalsIgnoreCase("0")) {

                    } else {
                        sl = sl + 1;
                        amoun1 = amoun1 + am;
                        Names.add(id + "," + acda + "," + USER + "," + amm + "," + sl + "," + accty + "," + acc11);
                        String fg = getString(R.string.credit21);
                        total.setText(fg + " : " + "\u20B9" + amoun1);
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
            mAdapter = new RecyclerViewAdapteraccounting(Names, in, getApplicationContext(),edpr,depr);
            recyclerView.setAdapter(mAdapter);
//            mAdapter.filter(dateString);
        }
    }

    //populateRecyclerView2() - Get all debit account datas
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView2() {
        Names.clear();
        amoun1 = 0;
        sl = 0;
        sqlite = new SQLiteHelper(this);
        Log.d("date",dateString+dateString1);
        database = sqlite.getReadableDatabase();
        if(dateString.equalsIgnoreCase("") || dateString1.equalsIgnoreCase("")){
             MY_QUERY = "SELECT a.*,b.acc_type_name,b.acc_type,SUM(a.amount)as total FROM dd_accounting a LEFT JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE  a.tracking_id = ? AND b.acc_type = '2' GROUP BY a.id ORDER BY a.accounting_date DESC";
            cursor = database.rawQuery(MY_QUERY,new String[]{String.valueOf(ses)});
        }else {
             MY_QUERY = "SELECT a.*,b.acc_type_name,b.acc_type,SUM(a.amount)as total FROM dd_accounting a LEFT JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE a.accounting_date BETWEEN ? AND ? AND a.tracking_id = ? AND b.acc_type = '2' GROUP BY a.id ORDER BY a.accounting_date DESC";
            cursor = database.rawQuery(MY_QUERY,new String[]{dateString,dateString1,String.valueOf(ses)});
        }
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("accounting_date");
                    String ate = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    long id = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("acc_type_name");
                    String acc1 = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("acc_type");
                    Integer accty = cursor.getInt(index);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    String myFormat1 = "dd/MM/yyyy";//In which you need put here
                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                    try {
                        Date debit = sdf.parse(ate);
                        acda = sdf1.format(debit);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
//                    if(acc.equalsIgnoreCase("1")){
//                        acc ="Credit";
//                    }else if(acc.equalsIgnoreCase("2")){
//                        acc = "Debit";
//                    }
                    index = cursor.getColumnIndexOrThrow("amount");
                    String amm = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("total");
                    String am_to = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("note");
                    String acc11 = cursor.getString(index);
//                    index = cursor.getColumnIndexOrThrow("amount");
//                    String tot = cursor.getString(index);
                   Integer am = Integer.parseInt(am_to);
                    if(acda == null){
                        acda = "0";
                    }
                    if(acc1 == null){
                        acc1 = "0";
                    }
                    if(acc11 == null || acc11.equalsIgnoreCase("")){
                        acc11 = "NO";
                    }
                    if(amm == null){
                        amm = "0";
                    }
                    if(sl == null){
                        sl = 0;
                    }
                    if (acc1.equalsIgnoreCase("Investment")) {
                        String nnaa = getApplicationContext().getString(R.string.investment);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Finance")) {
                        String nnaa = getString(R.string.finance);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Chittu")) {
                        String nnaa = getString(R.string.chittu2);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Transfer_Hand")) {
                        String nnaa = getString(R.string.transferhand);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Anamattu")) {
                        String nnaa = getString(R.string.anamattu);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Other_Income")) {
                        String nnaa = getString(R.string.otherincome);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Employee_credit")) {
                        String nnaa = getString(R.string.employeecredit);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("High")) {
                        String nnaa = getString(R.string.high);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Profit")) {
                        String nnaa = getString(R.string.profit);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Expense")) {
                        String nnaa = getString(R.string.expense);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Petrol")) {
                        String nnaa = getString(R.string.petrol);
                        USER = nnaa;
                    }
                    else if (acc1.equals("Rent")) {
                        String nnaa = getApplicationContext().getString(R.string.rent1);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Debit_Hand")) {
                        String nnaa = getString(R.string.debithand);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Vehicle_Expense")) {
                        String nnaa = getString(R.string.vehicleexpense);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Other_Expense")) {
                        String nnaa = getString(R.string.otherexpense);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Donation_Expense")) {
                        String nnaa = getString(R.string.donationexpense);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Other_Income")) {
                        String nnaa = getString(R.string.otherincome);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Employee_Expense")) {
                        String nnaa = getString(R.string.employeeexpense);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Low")) {
                        String nnaa = getString(R.string.low_amount);
                        USER = nnaa;
                    }
                    else if (acc1.equalsIgnoreCase("Lose")) {
                        String nnaa = getString(R.string.lose);
                        USER = nnaa;
                    }
                    else{
                        USER = acc1;
                    }
                    if(amm.equalsIgnoreCase("0")){

                    }else{
                        sl = sl + 1;
                        amoun1 = amoun1 + am ;
                        Names.add(id+","+acda+","+USER+","+amm+","+sl+","+accty + "," + acc11);

                        String fg = getString(R.string.debit21);
                        total.setText(fg+" : "+"\u20B9"+String.valueOf(amoun1));
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
            mAdapter = new RecyclerViewAdapteraccounting(Names, in, getApplicationContext(),edpr,depr);
            recyclerView.setAdapter(mAdapter);
//            mAdapter.filter(dateString);
        }
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

    //updateLabel1() - Update textview when date is selected
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void updateLabel1() {
        String myFormat = "yyyy/MM/dd";
        String myFormat1 = "dd/MM/yyyy";//In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        dateString1 = sdf.format(myCalendar1.getTime());
        to.setText(sdf1.format(myCalendar1.getTime()));

    }
//    private void updateLabel() {
//
//        String myFormat = "yyyy/MM/dd"; //In which you need put here
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//        dateString = sdf.format(myCalendar.getTime());
//        datt.setText(sdf.format(myCalendar.getTime()));
//        if(Names.size() == 0){
//        }else{
//            mAdapter.filter(dateString);
//        }
//    populateRecyclerView();
//    }


    //onBackPressed() - function called when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onBackPressed() {
       Intent nn = new Intent(Account.this,NavigationActivity.class);
   startActivity(nn);
        finish();
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

    //onCreateOptionsMenu() - when navigation menu created
    //Params - Menu
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    //onNavigationItemSelected() - when navigation item pressed
    //Params - selected item
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        if (id == R.id.company) {
            // Handle the camera action
            Intent new1 = new Intent(Account.this,MainActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.user) {
            Intent new1 = new Intent(Account.this,HomeActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.debit) {
            Intent new1 = new Intent(Account.this,Debit.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.collection) {
            Intent new1 = new Intent(Account.this,AllCollection.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.account) {
            Intent new1 = new Intent(Account.this,Account.class);
            startActivity(new1);
            finish();
        }  else if (id == R.id.report) {
            Intent new1 = new Intent(Account.this,ReportActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.customer) {
            Intent new1 = new Intent(Account.this,Customer_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.settings) {
            Intent new1 = new Intent(Account.this,Settings_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.tally) {
            Intent new1 = new Intent(Account.this,Tally_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.employee) {
            Intent new1 = new Intent(Account.this,Employee_details.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.today) {
            Intent new1 = new Intent(Account.this,Today_report.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.logout) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Account.this,R.style.AlertDialogTheme);
            alertbox.setMessage("Are You Sure want to logout");
            alertbox.setTitle("Warning");
            alertbox.setIcon(R.drawable.dailylogo);
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
                            Intent i = new Intent(Account.this,Splash.class);
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
}
