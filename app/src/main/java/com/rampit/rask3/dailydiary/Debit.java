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
import androidx.core.content.ContextCompat;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdapterdebit;
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
//Show all Debits
//InternetConnection1() - Check whether internet is on or not
//updateLabel1() - Update textview when date is selected
//updateLabel2() - Update textview when date is selected
//updateLabel() - Update textview when date is selected
//populateRecyclerView() - get all debits
//list() - show all debits
//populateRecyclerView2() - get all debits between two dates
//list1() - show all debits  between two dates
//alert() - debit delete popup
//closed() - get closed amount
//beforebal() - get before balance
//populateRecyclerView23() - get current final balance
//onBackPressed() - function called when back button is pressed
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed
//progressbar_load() - Load progressbar
//progre() - Stop progressbar
//onNavigationItemSelected() - when navigation item pressed


public class Debit extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView name,pass,city,pin,email,phone,dataaa,sessss,totdeb,totdoc,totint;
    EditText datt;
    LinearLayout linearr;
    TextView dcc,intr,dday,ins,ddd,toccc,noo;
    Button submit,add,edit;
    String ss,Id,ddat,color;
    Integer ses,doumm,intrae,dab,in,disco;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    public static String TABLENAME = "REGISTER";
    RecyclerView recyclerView;
    Cursor cursor;
    public static String TABLE_NAME ="NAMES";
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> Names1 = new ArrayList<>();
    ArrayList<String> ID = new ArrayList<>();
    ArrayList<String> collect1 = new ArrayList<>();
    RecyclerViewAdapterdebit mAdapter;
    String from,to;
    Calendar myCalendar,myCalendar1,myCalendar2;
    String dateString;
    SearchView searchView,datesearch;
    SharedPreferences.Editor editor;
    NavigationView navigationView;
    Menu nav_Menu;
    LinearLayout lll,debli;
    Button logou,profil,search,clear;
    ImageButton clos;
    Integer balanceamount,debitam1,instamt,pamount,pdays,toda,toda1,misda,slllno,misam,cloosed,befo,newc,curc,nipc,nipnipc,totdab;;
    Double paiddays,balanceday,totda,dbdate,todate,missiday,missda1,missiamo,todaaal,toda2;
    String idd,iii,nme,paidamount,installment,totaldays,ddbt,debitdaaa,pp,dbdda,Clo,cloo,formattedDate,xcol,xbal,missed;
    String zero,one,two,three,four,five,six,seven,eight,nine,ten,adpr,edpr,depr,vipr,todaaaa,intrst,todatee,fromdatee,idi21;
    ImageView seesim;
    EditText fromda,todat;
    DatePickerDialog datePickerDialog,datePickerDialog1;
    FloatingActionButton fab;
    Dialog dialog;
    Integer showinng = 0 ;
    @Override

//    String parsedStr = Name.replaceAll("(.{4})", "$1\n");
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
        cloosed = 0;
        befo = 0;
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dialog = new Dialog(Debit.this,R.style.AlertDialogTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_debit);
        setTitle(R.string.title_activity_debit);
        ((Callback)getApplication()).datee();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }

//        dcc = findViewById(R.id.doc);
        dday = findViewById(R.id.debid);
//        intr = findViewById(R.id.in);
        ins = findViewById(R.id.inst);
//        totdeb = findViewById(R.id.debitt1);
        debli = findViewById(R.id.debiline);
//        totdoc = findViewById(R.id.docu1);
//        totint = findViewById(R.id.intree1);
        ddd = findViewById(R.id.dbdate);
        add = findViewById(R.id.add);
//        clos = findViewById(R.id.closedd);
        toccc =findViewById(R.id.totcu);
        fromda = findViewById(R.id.from_date);
        todat = findViewById(R.id.to_date);
        search = findViewById(R.id.search3);
        clear = findViewById(R.id.clear);
        dataaa = findViewById(R.id.da);
        sessss = findViewById(R.id.sess);
        seesim = findViewById(R.id.sesimg);
        linearr = findViewById(R.id.liniy);
        noo = findViewById(R.id.no);
        lll = (findViewById(R.id.linee));
        TextView pro = lll.findViewById(R.id.pro);
        logou = lll.findViewById(R.id.logout);
        profil = lll.findViewById(R.id.profile);
        fab = findViewById(R.id.fab_arrow);
        doumm = 0;
        intrae =0;
        dab = 0;
        toda1 = 0;
        myCalendar = Calendar.getInstance();
        myCalendar1 = Calendar.getInstance();
        myCalendar2 = Calendar.getInstance();
        LinearLayout linear = findViewById(R.id.li90);
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),Debit.this)) {
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
        myCalendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        todaaaa = df.format(myCalendar.getTime());
        fromdatee = df.format(myCalendar.getTime());
        todatee = df.format(myCalendar.getTime());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent name = new Intent(Debit.this,Newdebit.class);
//                name.putExtra("ID","0");
                startActivity(name);
                finish();
            }
        });
        datt = (EditText) findViewById(R.id.search1);
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
//                new DatePickerDialog(Debit.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                String myFormat = "yyyy/MM/dd"; //In which you need put here
//                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
////                dateString = sdf.format(myCalendar.getTime());
//                String myFormat1 = "dd/MM/yyyy"; //In which you need put here
////                mAdapter.filter(dateString);
//                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat, Locale.US);
////                datt.setText(sdf1.format(myCalendar.getTime()));
//            }
//        });
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
        todat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (in == 0){
                    datePickerDialog1 =  new DatePickerDialog(Debit.this,R.style.DatePicker, date1, myCalendar1
                            .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                            myCalendar1.get(Calendar.DAY_OF_MONTH ));
                }else{
                    datePickerDialog1 =  new DatePickerDialog(Debit.this,R.style.DatePicker1, date1, myCalendar1
                            .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                            myCalendar1.get(Calendar.DAY_OF_MONTH ));
                }
                datePickerDialog1.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog1.show();
                String myFormat = "yyyy/MM/dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                todatee = sdf.format(myCalendar1.getTime());
                String myFormat1 = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                todat.setText(sdf1.format(myCalendar1.getTime()));
            }
        });
        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();
            }

        };
        fromda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (in == 0){
                    datePickerDialog =  new DatePickerDialog(Debit.this,R.style.DatePicker, date2, myCalendar2
                            .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                            myCalendar2.get(Calendar.DAY_OF_MONTH ));
                }else{
                    datePickerDialog =  new DatePickerDialog(Debit.this,R.style.DatePicker1, date2, myCalendar2
                            .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                            myCalendar2.get(Calendar.DAY_OF_MONTH ));
                }
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
                String myFormat = "yyyy/MM/dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                fromdatee = sdf.format(myCalendar2.getTime());
                String myFormat1 = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                fromda.setText(sdf1.format(myCalendar2.getTime()));
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(todatee.equalsIgnoreCase("")||fromdatee.equalsIgnoreCase("")){}
                else{
                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                Date newda = null;
                try {
                    newda = df.parse(fromdatee);
                    Date debit = df.parse(todatee);
                    long diffInMillisec =  newda.getTime() - debit.getTime();
                    long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                    Log.d("day5",String.valueOf(diffInDays));
                    if(diffInDays > 0){
                        AlertDialog.Builder alertbox = new AlertDialog.Builder(Debit.this,R.style.AlertDialogTheme);
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
                    }else{
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        list1();
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

//                        list1();
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }



                }
            }
        });

//        datt.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//                if (s.length() > 0)
//                { //do your work here }
//                    dateString = datt.getText().toString();
//                    Log.d("datata",dateString);
//                    mAdapter.filter(dateString);
//                }
//
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//                dateString = datt.getText().toString();
//                Log.d("datata",dateString);
//                mAdapter.filter(dateString);
//
//
//            }
//        });

//        lll = (findViewById(R.id.linee));
//        logou = lll.findViewById(R.id.logout);
//        profil = lll.findViewById(R.id.profile);

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
                Intent i = new Intent(Debit.this,Settings_activity.class);
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
                    Intent i = new Intent(Debit.this,NavigationActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    editor.putString("theme","0");
                    editor.apply();
                    Intent i = new Intent(Debit.this,NavigationActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        });

        logou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Debit.this,R.style.AlertDialogTheme);
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
                                Intent i = new Intent(Debit.this,Splash.class);
                                startActivity(i);
                                finish();
                            }
                        });
                alertbox.show();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

         editor = pref.edit();
        String sesid = pref.getString("id","");
        String module_i = "3";
        ((Callback)getApplication()).privilege(sesid,module_i);

        adpr = pref.getString("add_privilege","");
        edpr = pref.getString("edit_privilege","");
        depr = pref.getString("delete_privilege","");
        vipr = pref.getString("view_privilege","");
        if(adpr.equalsIgnoreCase("0")){
            add.setVisibility(View.GONE);
        }
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
            pro.setVisibility(View.GONE);
            profil.setVisibility(View.GONE);
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

//        sqlite = new SQLiteHelper(this);
        final SharedPreferences.Editor editor = pref.edit();
//        editor.putInt("isloginn", 1);
//        editor.commit();
        String dd = pref.getString("Date","");
        dataaa.setText(dd);
        ses = pref.getInt("session", 1);
        ((Callback)getApplication()).NIP(ses);
        ((Callback)getApplication()).CURR(ses);
        if(in == 0){
            add.setBackgroundResource(R.drawable.add);
//            clos.setBackgroundResource(R.drawable.close_button);
            if (ses == 1) {
                ss = getString(R.string.morning);
                seesim.setBackgroundResource(R.drawable.sun);
                sessss.setText(ss);
            } else if (ses == 2) {
                ss = getString(R.string.evening);
                seesim.setBackgroundResource(R.drawable.moon);
                sessss.setText(ss);
            }
        }
        else{
            add.setBackgroundResource(R.drawable.add_blue);
//            clos.setBackgroundResource(R.drawable.close_blue);
            if (ses == 1) {
                ss = getString(R.string.morning);
                seesim.setBackgroundResource(R.drawable.sun_blue);
                sessss.setText(ss);
            } else if (ses == 2) {
                ss = getString(R.string.evening);
                seesim.setBackgroundResource(R.drawable.moon_blue);
                sessss.setText(ss);
            }
        }
        Calendar myCalendar321 = Calendar.getInstance();
        String datestr = df.format(myCalendar321.getTime());
        String todateee = df.format(myCalendar321.getTime());
        closed(datestr,getApplicationContext());
        beforebal(datestr,getApplicationContext());
        populateRecyclerView23(datestr,todateee,getApplicationContext());
        String dsdsd = pref.getString("NAME","");
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
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Debit.this,R.style.AlertDialogTheme);
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
                                Intent i = new Intent(Debit.this,Splash.class);
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
                Intent qq = new Intent(Debit.this,NavigationActivity.class);
                startActivity(qq);
                finish();
            }
        });

        nn.setText(dsdsd);
        nnn.setText("Date :"+" "+dd);
        nnnn.setText("Session :"+" "+ss);
//        searchView = (SearchView) findViewById(R.id.search);
//        searchView.setInputType(InputType.TYPE_CLASS_TEXT);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String text) {
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String text) {
//                if(Names.size() == 0){
//
//                }else {
//                    mAdapter.filter(text);
//                }
//                return false;
//            }
//        });
        final EditText sear = findViewById(R.id.search);
        sear.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        datt.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        if(in == 0){
            Log.d("thee","thh");
            sear.setBackgroundResource(R.drawable.search_widget);
            datt.setBackgroundResource(R.drawable.search_widget);
        }else{
            Log.d("thee","th1h");
            sear.setBackgroundResource(R.drawable.search_widget1);
            datt.setBackgroundResource(R.drawable.search_widget1);
//            recreate();
        }
        sear.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0)
                {
                    if(Names.size() == 0){
                    }else{
                        mAdapter.filter1(String.valueOf(s));
                    }
                }

            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
            public void afterTextChanged(Editable s) {
//                dateString = datt.getText().toString();
//                Log.d("datata",dateString);
                if(Names.size() == 0){
                }else{
                    mAdapter.filter1(String.valueOf(s));
                }
            }
        });
        datt.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0)
                {
                    if(Names.size() == 0){
                    }else{
                        mAdapter.filter(String.valueOf(s));
                    }
                }

            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
            public void afterTextChanged(Editable s) {
//                dateString = datt.getText().toString();
//                Log.d("datata",dateString);
                if(Names.size() == 0){
                }else{
                    mAdapter.filter(String.valueOf(s));
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromda.setText("");
                todat.setText("");
                sear.setText("");
                datt.setText("");
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                list();
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
//                list();
            }
        });
//        clos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                datt.setText("");
//                doumm = 0;
//                intrae =0;
//                dab = 0;
//                list();
//            }
//        });
//        lastcollection1();
        recyclerView = findViewById(R.id.re);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
//        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);
//        recyclerView.setLayoutManager(layoutManager);
            Intent nan =getIntent();

            Id = nan.getStringExtra("ididi");
            color = nan.getStringExtra("co");
//            Log.d("color",)
            if(Id == null){
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                list1();
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
//                list1();
//                LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
//                        new IntentFilter("custom-message"));

            }else{
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                list1();
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
//                list1();
//populateRecyclerView1();
//                LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
//                        new IntentFilter("custom-message"));

            }
            fab.hide();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                Log.d("dxdy",String.valueOf(dx)+" "+String.valueOf(dy));
                if (dy > 0 || dy < 0  )
                {
                    Log.d("scrolled", String.valueOf(fab.getVisibility()));
                    fab.show();
                }
                if(fab.getVisibility() == View.VISIBLE){
//                    fab.show();
                }

            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                Log.d("scrolled1","scrolled1");
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    Log.d("scrolled1",String.valueOf(fab.getVisibility()));

                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            // this code will be executed after 2 seconds
//                                        Log.d("Status ",status);
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    // Stuff that updates the UI
                                    fab.hide();
                                }
                            });
                        }
                    }, 2000);
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerView.smoothScrollToPosition(0);
                }
            });
        }
    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Debit account) {
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

    LinearLayoutManager layoutManager = new LinearLayoutManager(this) {

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(Debit.this) {

                private static final float SPEED = 5f;// Change this value (default=25f)

                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return SPEED / displayMetrics.densityDpi;
                }

            };
            smoothScroller.setTargetPosition(position);
            startSmoothScroll(smoothScroller);
        }

    };
    //updateLabel1() - Update textview when date is selected
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void updateLabel1() {

        String myFormat = "yyyy/MM/dd";
        String mmm = "dd/MM/yyyy";//In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(mmm, Locale.US);
        todatee = sdf.format(myCalendar1.getTime());
        todat.setText(sdf1.format(myCalendar1.getTime()));
    }
    //updateLabel2() - Update textview when date is selected
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void updateLabel2() {

        String myFormat = "yyyy/MM/dd";
        String mmm = "dd/MM/yyyy";//In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(mmm, Locale.US);
        fromdatee = sdf.format(myCalendar2.getTime());
        fromda.setText(sdf1.format(myCalendar2.getTime()));
    }

    public void updatee(String see1, String cle1, String ordered1){
        Integer see2 = Integer.valueOf(see1);
        Integer cle2 = Integer.valueOf(cle1);
        String io;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY1 = "SELECT * FROM dd_customers WHERE order_id = ? AND tracking_id = ?";
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{String.valueOf(see1),String.valueOf(ses)});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("id");
                    idi21 = cursor.getString(index);

                    Log.d("namesii1", idi21);
                }
                while (cursor.moveToNext());
            }
        }
        String MY_QUERY11 = "SELECT * FROM dd_account_debit WHERE customer_id = ? ";
        Cursor cursor1 = database.rawQuery(MY_QUERY11, new String[]{idi21});
        if (cursor1 != null){
            if(cursor1.getCount() != 0){
                cursor1.moveToFirst();
                do{
                    int index1;
                    index1 = cursor1.getColumnIndexOrThrow("id");
                    io = cursor1.getString(index1);
                    if(io == null | io.equalsIgnoreCase("")){
                        io = "0";
                    }


                }
                while (cursor1.moveToNext());
            }else{
                io = "0" ;
            }
        }else{
            io = "0";
        }
//        see2 = see1 + 1;
        for (int i = see2; i <= cle2;i++){
            database = sqlite.getWritableDatabase();
            Log.d("forrloop",String.valueOf(i));
            ContentValues cv = new ContentValues();
            cv.put("order_id",i-1);
            String[] args =  new String[]{String.valueOf(i),String.valueOf(ses)};
            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
            Log.d("forrloop1",String.valueOf(i));
            Log.d("forrloop2",String.valueOf(i-1));
        }
        Log.d("names21", io);
        if(io.equalsIgnoreCase("0")){
            database = sqlite.getWritableDatabase();
            ContentValues cv1 = new ContentValues();
            cv1.put("order_id","-1");
            cv1.put("previous_order_id",see1);
            cv1.put("debit_type","-1");
            database.update("dd_customers", cv1,  "id=? AND tracking_id = ?", new String[]{idi21,String.valueOf(ses)});
            Toast.makeText(Debit.this,idi21 +" "+String.valueOf(see1)+" "+String.valueOf(ses) ,Toast.LENGTH_SHORT).show();
        }else{
            database = sqlite.getWritableDatabase();
            ContentValues cv1 = new ContentValues();
            cv1.put("order_id","-1");
            cv1.put("previous_order_id",see1);
            cv1.put("debit_type","3");
            database.update("dd_customers", cv1,  "id=? AND tracking_id = ?", new String[]{idi21,String.valueOf(ses)});
            Toast.makeText(Debit.this,idi21 +" "+String.valueOf(see1)+" "+String.valueOf(ses) ,Toast.LENGTH_SHORT).show();
        }
        database.close();
        sqlite.close();
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
        dateString = sdf.format(myCalendar.getTime());
        datt.setText(sdf1.format(myCalendar.getTime()));
if(Names.size() == 0){

}else{
    mAdapter.filter(dateString);
    Log.d("hjyj",dateString);
}
     }
    private void populateRecyclerView1(){
        String[] columns1 = {
                "document_charge","interest","debit_days","installment_amount","debit_date"};
        database = sqlite.getWritableDatabase();
        String whereClause = "id=?";
        String[] whereArgs = new String[] {Id};
        Cursor cursor = database.query("dd_account_debit",
                columns1,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
//                while(cursor.moveToNext()) {
//                    int index;
//
//                    for (int i = 0; i < cursor.getColumnCount(); i++) {
//                        index = cursor.getColumnIndexOrThrow("NAME");
//                        String firstName = cursor.getString(index);
//
//
//                        index = cursor.getColumnIndexOrThrow("ID");
//                        long id = cursor.getLong(index);
//                        Names.add(firstName);
//                        //... do something with data
//                    }
//                    Log.d("names", String.valueOf(Names));
//                }
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("document_charge");
                    String doccc = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("interest");
                    String in = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_days");
                    String dda = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("installment_amount");
                    String insam = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_date");
                    ddat = cursor.getString(index);

//                    dcc.setText("\u20B9"+" "+doccc);
//                    intr.setText("\u20B9"+" "+in);
                    if(color.equalsIgnoreCase("white")){
                        dday.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.White)));
                        ddd.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.White)));
                        ins.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.White)));
                        toccc.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.White)));
                    }
                    if(color.equalsIgnoreCase("blue")){
                        dday.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.RoyalBlue)));
                        ddd.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.RoyalBlue)));
                        ins.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.RoyalBlue)));
                        toccc.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.RoyalBlue)));
                    }
                    if(color.equalsIgnoreCase("red")){
                        dday.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.Red)));
                        ddd.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.Red)));
                        ins.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.Red)));
                        toccc.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.Red)));
                    }
//                    dday.setText(dda);
//                    ins.setText("\u20B9"+" "+insam);
                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//                    String formattedDate = df.format(c.getTime());
                    String myFormat1 = "dd/MM/yyyy";//In which you need put here
                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                    try {
                        Date debit = df.parse(ddat);
                        ddat = sdf1.format(debit);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
//                    ddd.setText(ddat);
                    linearr.setVisibility(View.VISIBLE);

//
//                    Log.d("names", String.valueOf(Names));
//                    Log.d("names", String.valueOf(Names));
//                    Log.d("LOG_TAG_HERE", row_values);
                }
                while (cursor.moveToNext());
            }
        }
    }
    public void popup1(String iii){
//        Names1.clear();
//        sll = 0;
//        collectoo = 0;



    }

    //populateRecyclerView() - get all debits
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView() {
        Names.clear();
        Names1.clear();
        ID.clear();
        intrae = 0;
        doumm = 0;
        dab = 0;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String[] columns = {"debit_amount",
                "id",""};
         String MY_QUERY = "SELECT a.*,b.customer_name,b.debit_type,b.CID,b.order_id_new,b.id as ID FROM dd_account_debit a INNER JOIN dd_customers b on b.id = a.customer_id " +
                 "WHERE b.tracking_id = ? AND b.debit_type IN (0,1,2) AND a.active_status = 1 GROUP BY b.id ORDER BY b.order_id_new ASC";

        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{String.valueOf(ses)});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("ID");
                    String idid = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("created_date");
                    String idid1 = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    long id = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String dbamount = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("document_charge");
                    String dcamount = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("interest");
                    String intamount = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_date");
                    String deeeda = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String deeety = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("CID");
                    String CID = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String order_id = cursor.getString(index);

                    Integer ds = Integer.parseInt(dcamount);
                    Integer deba = Integer.parseInt(dbamount);
                    Integer ina = Integer.parseInt(intamount);
                    doumm = doumm + ds ;
                    dab = dab + deba ;
                    intrae = intrae + ina ;
                    Names1.add(Name);
                    if(deeety.equalsIgnoreCase("-1")) {
                        Names.add(id + "," + Name + "," + dbamount + "," + intamount + "," + intamount + "," + deeeda + "," + deeety + "," + CID + "," + idid + "," + order_id+ "," + CID);

                        ID.add(String.valueOf(CID));
                    }
                    else if(deeety.equalsIgnoreCase("0")) {
    Names.add(id + "," + Name + "," + dbamount + "," + intamount + "," + intamount + "," + deeeda + "," + deeety + "," + CID + "," + idid  + "," + order_id+ "," + CID);
                        ID.add(String.valueOf(CID));
                    }else if(deeety.equalsIgnoreCase("1")) {
    Names.add(id + "," + Name + "," + dbamount + "," + intamount + "," + intamount + "," + deeeda + "," + deeety + "," + CID + "," + idid  + "," + order_id+ "," + CID);
                        ID.add(String.valueOf(CID));
}else if(deeety.equalsIgnoreCase("2")) {
    Names.add(id + "," + Name + "," + dbamount + "," + intamount + "," + intamount + "," + deeeda + "," + deeety + "," + order_id + "," + idid  + "," + order_id+ "," + CID);
                        ID.add(String.valueOf(order_id));
                    }
//                    Names.add(id+"   "+Name+"             "+dbamount+"           "+dcamount+"        "+intamount);


                    Log.d("names", String.valueOf(order_id)+" "+String.valueOf(CID)+" "+String.valueOf(id)+" "+idid);

                    toccc.setText(String.valueOf(Names.size()));
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

    //list() - show all debits
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void list(){
        populateRecyclerView();
        collect1.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        String MY_QUERY = "SELECT b.*,a.customer_name,a.id as ID,a.CID as cid,a.debit_type as dtype, a.order_id_new as oid,a.location,a.phone_1,sum(c.collection_amount) as collect,sum(c.discount) as disc FROM dd_customers a LEFT JOIN dd_account_debit b on  b.customer_id = a.id" +
                " Left JOIN dd_collection c on  c.debit_id = b.id " +
                "WHERE  a.tracking_id = ?  AND b.active_status = 1 AND a.debit_type IN (0,1,2)   GROUP BY a.id ORDER BY a.order_id_new ASC";
//   String mm = "SELECT b.*,sum(c.co)";
        Cursor cursor = database.rawQuery(MY_QUERY, new String[]{String.valueOf(ses)});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("id");
                    String orde = cursor.getString(index);
                    iii = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("disc");
                    disco = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("cid");
                    String cide = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("oid");
                    String oide = cursor.getString(index);
                    Integer oio = Integer.parseInt(oide);
                    index = cursor.getColumnIndexOrThrow("ID");
                    String ID = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("dtype");
                    String dtype = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);
                    nme = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_date");
                    debitdaaa = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("document_charge");
                    intrst = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    ddbt = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("location");
                    String Loc = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("phone_1");
                    String Pho = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("closeing_date");
                    Clo = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("collect");
                    xcol = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("installment_amount");
                    installment = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_days");
                    totaldays = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("interest");
                    String intamount = cursor.getString(index);
                    String myFormat1 = "dd/MM/yyyy";//In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                    Log.d("paiddd122", orde);
                    if(debitdaaa == null){
                    }else{
                        try {
                            dbdda = debitdaaa;
                            Date debit = sdf.parse(dbdda);
                            dbdda = sdf1.format(debit);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }}
                    if(Clo == null){}else{
                        try {
                            cloo = Clo;
                            Date debit = sdf.parse(cloo);
                            cloo = sdf1.format(debit);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }}
                    if(xcol == null){
                        xcol ="0";
                    }
                    paidamount = String.valueOf(xcol);
                    if(installment == null){
                        installment ="0";
                    }
                    if(installment.equalsIgnoreCase("")){
                        installment = "0";
                    }
                    if(ddbt == null){
                        ddbt = "0";
                    }
                    if(totaldays == null){
                        totaldays = "0";
                    }
                    if(debitdaaa == null){
                        debitdaaa = "0";
                    }
                    if(totaldays.equalsIgnoreCase("0") || installment.equalsIgnoreCase("0")){
                        collect1.add("0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0"
                                + "," + "0"+"," + "0" +","+ "0"+","+ "0"+","+"0"+","+"0"+","+"0"+","+"0"+","+"0"+","+"0"+","+"0"+","+"0");
                    }else {
                        instamt = Integer.parseInt(installment);
                        pamount = Integer.parseInt(paidamount);
                        Double pa = new Double(pamount);
                        Double ins = new Double(instamt);
                        Integer paaiddd = pamount / instamt;
                        paiddays = (double) pamount / instamt;
                        if(String.valueOf(paiddays).equalsIgnoreCase("NaN")){
                            paiddays = 0.0;
                        }
//                  Log.d("paiddd", String.valueOf(instamt));
                        DecimalFormat df = new DecimalFormat("####.##");
                        paiddays = Double.valueOf(df.format(paiddays));
                        pp = String.valueOf(paiddays);
                        pdays = paaiddd;

                        debitam1 = Integer.parseInt(ddbt);
                        balanceamount = debitam1 - pamount;
                        balanceamount = balanceamount - disco ;
                        totda = Double.parseDouble(totaldays);
                        balanceday = totda - paiddays;
                        balanceday = Double.valueOf(df.format(balanceday));
                        xbal = df.format(balanceday);

                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat dff = new SimpleDateFormat("yyyy/MM/dd");
                        String formattedDate = dff.format(c.getTime());
                        try {
                            Date newda = dff.parse(formattedDate);
                            Date debit = dff.parse(debitdaaa);
                            long diffInMillisec = newda.getTime() - debit.getTime();
                            long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                            Log.d("dates", String.valueOf(diffInDays));
                            if(diffInDays == 0){
                                misda = 0;
                                misam = 0;
                                toda = 0 ;
                                toda1 = 1;
                                missda1 = 0.0 ;
                            }else {
                                String tod = String.valueOf(diffInDays);
                                toda = Integer.parseInt(tod) ;
                                toda1 = Integer.parseInt(tod) + 1;
//                                todaaal = new doub
//        Integer po = Integer.parseInt(pp);
                                missiday = paiddays;
                                Integer tota = Integer.parseInt(totaldays);
                                Double mii1 = new Double(toda);
                                Double mii2 = new Double(pdays);
                                misda =  toda - pdays;
                                Double mii;
                                mii = mii1 - mii2;
                                toda2 = Double.parseDouble(tod);
                                missda1 = toda2 - paiddays;
                                missda1 = Double.valueOf(df.format(missda1));
                                if(missda1 < 0.0 || missda1 == null){
                                    missda1 = 0.0 ;
                                }
                                Log.d("mmm", String.valueOf(toda));
                                if (misda >= tota) {
                                    misam = balanceamount;
//                                    misda = balanceday.intValue();
//                                    missed = String.valueOf(misda);
//                                    misda = Integer.parseInt(missed);
                                } else {
                                    misam = misda * instamt;
                                    if(misam < 0){
                                        misam = 0;
                                    }
                                }
                            }
//                mid.setText("Missingdays :"+" "+String.valueOf(misda));
//                mama.setText("Missingamount :"+" "+String.valueOf(misam));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Log.d("mmm", String.valueOf(toda));
                        collect1.add(totaldays + "," +  String.valueOf(paiddays) + "," +  String.valueOf(balanceday) + "," +
                                String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," +
                                String.valueOf(balanceamount) + "," + dbdda + "," + cloo+"," +intrst+","+installment+","+toda1+","+orde+","+Name+","+ddbt+","+cide+","+oide+","+ID+","+intamount+","+dtype);

                    }
//                        slno.setText("ID :" + " " + orde);
//                        namee.setText("NAME :" + " " + Name);
//                        opd.setText("Opening Date :" + " " + dbdda);
//                        deb.setText("Debit Amount :" + " " + ddbt);
//                        loc.setText("Location :" + " " + Loc);
//                        pho.setText("Phone :" + " " + Pho);
//                        cd.setText("Closing Date :" + " " + cloo);
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
        Log.d("collleee",String.valueOf(collect1));
        if(Names.size() == 0){
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    progre();
                }
            });
            debli.setVisibility(View.GONE);
            noo.setVisibility(View.VISIBLE);
        }else{
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    progre();
                }
            });
            ins.setText("\u20B9"+" "+String.valueOf(intrae));
            dday.setText("\u20B9"+" "+String.valueOf(doumm));
            ddd.setText("\u20B9"+" "+String.valueOf(dab));
            debli.setVisibility(View.VISIBLE);
            noo.setVisibility(View.GONE);
            mAdapter = new RecyclerViewAdapterdebit(Names,Names1,collect1,in,getApplicationContext(),edpr,depr,ID, ses, Debit.this);
//                ItemTouchHelper.Callback callback = new ItemMoveCallback(mAdapter);
//                ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//                touchHelper.attachToRecyclerView(recyclerView);
            mAdapter.setHasStableIds(true);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setItemViewCacheSize(1000);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            mAdapter.onAttachedToRecyclerView(recyclerView);
            recyclerView.setLayoutManager(layoutManager);


        }
    }

    //populateRecyclerView2() - get all debits between two dates
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView2() {
        Names.clear();
        Names1.clear();
        ID.clear();
        intrae = 0;
        doumm = 0;
        dab = 0;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String[] columns = {"debit_amount",
                "id",""};
        String MY_QUERY = "SELECT a.*,b.customer_name,b.debit_type,b.CID,b.order_id_new,b.id as ID FROM dd_account_debit a INNER JOIN dd_customers b on b.id = a.customer_id " +
                "WHERE b.tracking_id = ? AND b.debit_type IN (0,1,2) AND a.debit_date BETWEEN ? AND ? AND a.active_status = 1 GROUP BY b.id ORDER BY b.order_id_new ASC";

        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{String.valueOf(ses),fromdatee,todatee});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("ID");
                    String idid = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    long id = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String dbamount = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("document_charge");
                    String dcamount = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("interest");
                    String intamount = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_date");
                    String deeeda = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String deeety = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("CID");
                    String CID = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String order_id = cursor.getString(index);

                    Integer ds = Integer.parseInt(dcamount);
                    Integer deba = Integer.parseInt(dbamount);
                    Integer ina = Integer.parseInt(intamount);
                    doumm = doumm + ds ;
                    dab = dab + deba ;
                    intrae = intrae + ina ;
                    Names1.add(Name);
                    if(deeety.equalsIgnoreCase("-1")) {
                        Names.add(id + "," + Name + "," + dbamount + "," + intamount + "," + intamount + "," + deeeda + "," + deeety + "," + CID + "," + idid  + "," + order_id+ "," + CID);
                        ID.add(String.valueOf(CID));
                    }
                    else if(deeety.equalsIgnoreCase("0")) {
                        Names.add(id + "," + Name + "," + dbamount + "," + intamount + "," + intamount + "," + deeeda + "," + deeety + "," + CID + "," + idid  + "," + order_id+ "," + CID);
                        ID.add(String.valueOf(CID));
                    }else if(deeety.equalsIgnoreCase("1")) {
                        Names.add(id + "," + Name + "," + dbamount + "," + intamount + "," + intamount + "," + deeeda + "," + deeety + "," + CID + "," + idid  + "," + order_id+ "," + CID);
                        ID.add(String.valueOf(CID));
                    }else if(deeety.equalsIgnoreCase("2")) {
                        Names.add(id + "," + Name + "," + dbamount + "," + intamount + "," + intamount + "," + deeeda + "," + deeety + "," + order_id + "," + idid  + "," + order_id+ "," + CID);
                        ID.add(String.valueOf(order_id));
                    }
//                    Names.add(id+"   "+Name+"             "+dbamount+"           "+dcamount+"        "+intamount);
                    Log.d("names", String.valueOf(order_id));
                    toccc.setText(String.valueOf(Names.size()));
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

    //list1() - show all debits  between two dates
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void list1(){
        populateRecyclerView2();
        collect1.clear();
        Log.d("dadad",fromdatee+" "+todatee);
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        String MY_QUERY = "SELECT b.*,a.customer_name,a.id as ID,a.CID as cid,a.debit_type as dtype, a.order_id_new as oid,a.location,a.phone_1,sum(c.collection_amount) as collect,sum(c.discount) as disc FROM dd_customers a LEFT JOIN dd_account_debit b on  b.customer_id = a.id" +
                " Left JOIN dd_collection c on  c.debit_id = b.id WHERE  a.tracking_id = ? AND b.debit_date BETWEEN ? AND ? AND b.active_status = 1 AND a.debit_type IN (0,1,2)  GROUP BY a.id ORDER BY a.order_id_new ASC";
//   String mm = "SELECT b.*,sum(c.co)";
        Cursor cursor = database.rawQuery(MY_QUERY, new String[]{String.valueOf(ses),fromdatee,todatee});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("id");
                    String orde = cursor.getString(index);
                    iii = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("disc");
                    disco = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("cid");
                    String cide = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("oid");
                    String oide = cursor.getString(index);
                    Integer oio = Integer.parseInt(oide);
                    index = cursor.getColumnIndexOrThrow("ID");
                    String ID = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("dtype");
                    String dtype = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);
                    nme = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_date");
                    debitdaaa = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("document_charge");
                    intrst = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    ddbt = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("location");
                    String Loc = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("phone_1");
                    String Pho = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("interest");
                    String intamount = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("closeing_date");
                    Clo = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("collect");
                    xcol = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("installment_amount");
                    installment = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_days");
                    totaldays = cursor.getString(index);
                    String myFormat1 = "dd/MM/yyyy";//In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                    Log.d("paiddd122", orde);
                    if(debitdaaa == null){
                    }else{
                        try {
                            dbdda = debitdaaa;
                            Date debit = sdf.parse(dbdda);
                            dbdda = sdf1.format(debit);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }}
                    if(Clo == null){}else{
                        try {
                            cloo = Clo;
                            Date debit = sdf.parse(cloo);
                            cloo = sdf1.format(debit);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }}
                    if(xcol == null){
                        xcol ="0";
                    }
                    paidamount = String.valueOf(xcol);
                    if(installment == null){
                        installment ="0";
                    }
                    if(installment.equalsIgnoreCase("")){
                        installment = "0";
                    }
                    if(ddbt == null){
                        ddbt = "0";
                    }
                    if(totaldays == null){
                        totaldays = "0";
                    }
                    if(debitdaaa == null){
                        debitdaaa = "0";
                    }
                    if(totaldays.equalsIgnoreCase("0") || installment.equalsIgnoreCase("0")){
                        collect1.add("0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0"
                                + "," + "0"+"," + "0" +","+ "0"+","+ "0"+","+"0"+","+"0"+","+"0"+","+"0"+","+"0"+","+"0"+","+"0"+","+"0");
                    }else {
                        instamt = Integer.parseInt(installment);
                        pamount = Integer.parseInt(paidamount);
                        Double pa = new Double(pamount);
                        Double ins = new Double(instamt);
                        Integer paaiddd = pamount / instamt;
                        paiddays = (double) pamount / instamt;
                        if(String.valueOf(paiddays).equalsIgnoreCase("NaN")){
                            paiddays = 0.0;
                        }
//                  Log.d("paiddd", String.valueOf(instamt));
                        DecimalFormat df = new DecimalFormat("####.##");
                        paiddays = Double.valueOf(df.format(paiddays));
                        pp = String.valueOf(paiddays);
                        pdays = paaiddd;

                        debitam1 = Integer.parseInt(ddbt);
                        balanceamount = debitam1 - pamount;
                        balanceamount = balanceamount - disco ;
                        totda = Double.parseDouble(totaldays);
                        balanceday = totda - paiddays;
                        balanceday = Double.valueOf(df.format(balanceday));
                        xbal = df.format(balanceday);

                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat dff = new SimpleDateFormat("yyyy/MM/dd");
                        String formattedDate = dff.format(c.getTime());
                        try {
                            Date newda = dff.parse(formattedDate);
                            Date debit = dff.parse(debitdaaa);
                            long diffInMillisec = newda.getTime() - debit.getTime();
                            long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                            Log.d("dates", String.valueOf(diffInDays));
                            if(diffInDays == 0){
                                misda = 0;
                                misam = 0;
                                toda = 0 ;
                                toda1 = 1;
                                missda1 = 0.0;
                            }else {
                                String tod = String.valueOf(diffInDays);
                                toda = Integer.parseInt(tod) ;
                                toda1 = Integer.parseInt(tod) + 1;
//                                todaaal = new doub
//        Integer po = Integer.parseInt(pp);
                                missiday = paiddays;
                                Integer tota = Integer.parseInt(totaldays);
                                Double mii1 = new Double(toda);
                                Double mii2 = new Double(pdays);
                                misda =  toda - pdays;
                                Double mii;
                                mii = mii1 - mii2;
                                toda2 = Double.parseDouble(tod);
                                missda1 = toda2 - paiddays;
                                missda1 = Double.valueOf(df.format(missda1));
                                if(missda1 < 0.0 || missda1 == null){
                                    missda1 = 0.0 ;
                                }
                                Log.d("mmm", String.valueOf(toda));
                                if (misda >= tota) {
                                    misam = balanceamount;
//                                    misda = balanceday.intValue();
//                                    missed = String.valueOf(misda);
//                                    misda = Integer.parseInt(missed);
                                } else {
                                    misam = misda * instamt;
                                    if(misam < 0){
                                        misam = 0;
                                    }
                                }
                            }
//                mid.setText("Missingdays :"+" "+String.valueOf(misda));
//                mama.setText("Missingamount :"+" "+String.valueOf(misam));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Log.d("mmm", String.valueOf(toda));
                        collect1.add(totaldays + "," +  String.valueOf(paiddays) + "," +  String.valueOf(balanceday) +
                                "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," +
                                String.valueOf(balanceamount) + "," + dbdda + "," + cloo+"," +intrst+","+installment+","+toda1+","+orde+","+Name+","+ddbt+","+cide+","+oide+","+ID+","+intamount+","+dtype);

                    }
//                        slno.setText("ID :" + " " + orde);
//                        namee.setText("NAME :" + " " + Name);
//                        opd.setText("Opening Date :" + " " + dbdda);
//                        deb.setText("Debit Amount :" + " " + ddbt);
//                        loc.setText("Location :" + " " + Loc);
//                        pho.setText("Phone :" + " " + Pho);
//                        cd.setText("Closing Date :" + " " + cloo);
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
            Log.d("collleee",String.valueOf(collect1));
            if(Names.size() == 0){
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        progre();
                    }
                });
                debli.setVisibility(View.GONE);
                noo.setVisibility(View.VISIBLE);
                ins.setText("0");
                dday.setText("0");
                ddd.setText("0");
                toccc.setText("0");
            }else{
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        progre();
                    }
                });
                ins.setText("\u20B9"+" "+String.valueOf(intrae));
                dday.setText("\u20B9"+" "+String.valueOf(doumm));
                ddd.setText("\u20B9"+" "+String.valueOf(dab));
                debli.setVisibility(View.VISIBLE);
                noo.setVisibility(View.GONE);
                mAdapter = new RecyclerViewAdapterdebit(Names,Names1,collect1,in,getApplicationContext(),edpr,depr,ID,ses,Debit.this);
//                ItemTouchHelper.Callback callback = new ItemMoveCallback(mAdapter);
//                ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//                touchHelper.attachToRecyclerView(recyclerView);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(layoutManager);

            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }

    }
//    public void lastcollection1 (){
//        sqlite = new SQLiteHelper(this);
//        database = sqlite.getReadableDatabase();
//        Cursor cursor = database.rawQuery("SELECT b.id FROM dd_customers c LEFT JOIN dd_account_debit b on b.customer_id = c.id " +
//                        "WHERE c.CID = ? ORDER BY b.id ",
//                new String[]{ "55"});
//        if (cursor != null) {
//            if (cursor.getCount() != 0) {
//                cursor.moveToFirst();
//                do {
//                    int index;
//
//                    index = cursor.getColumnIndexOrThrow("id");
//                    String collect1 = cursor.getString(index);
//
//
//                    Log.d("qw12",collect1);
//                }
//                while (cursor.moveToNext());
//            }
//        }
//    }

    //alert() - debit delete popup
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void alert(){
    AlertDialog.Builder alertbox = new AlertDialog.Builder(Debit.this,R.style.AlertDialogTheme);
    String erree = getString(R.string.debitdel);
    String ok = getString(R.string.ok);
    String war = getString(R.string.warning);
    alertbox.setMessage(erree);
    alertbox.setTitle(war);
    alertbox.setIcon(R.drawable.logo);

    alertbox.setPositiveButton(ok,
            new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface arg0,
                                    int arg1) {


                }
            });
    alertbox.show();
}

    //closed() - get closed amount
    //Params - date and application context
    //Created on 25/01/2022
    //Return - NULL
    public void closed(String dateString, Context context){
        SQLiteHelper sqlite;
        SQLiteDatabase database;
        cloosed =0 ;
        sqlite = new SQLiteHelper(context);
        database = sqlite.getReadableDatabase();
        Log.d("popo",dateString);
        String NIP = "SELECT SUM(b.debit_amount) as debitted,SUM(b.document_charge) as docuu,SUM(b.interest)as interr " +
                " FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE a.debit_type_updated <= ? AND  b.active_status = 0 AND a.tracking_id = ? ";
        String sess = String.valueOf(ses);
        Cursor cursor = database.rawQuery(NIP, new String[]{dateString,sess});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("docuu");
                    String docc = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debitted");
                    String intre = cursor.getString(index);

                    if(docc != null){
                    }else{
                        docc ="0";
                    }if(intre != null){
                    }else{
                        intre ="0";
                    }
                    Integer doo = Integer.parseInt(docc);
                    Integer in = Integer.parseInt(intre);
                    cloosed = doo + in ;
                    Log.d("cloossed",String.valueOf(cloosed));
//                    beforebal();
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

    //beforebal() - get before balance
    //Params - date and application context
    //Created on 25/01/2022
    //Return - NULL
    public void beforebal(String dateString, Context context){
        SQLiteHelper sqlite;
        SQLiteDatabase database;
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        sqlite = new SQLiteHelper(context);
        database = sqlite.getReadableDatabase();
        String NIP = "SELECT SUM(b.debit_amount) as debitted,SUM(b.document_charge) as docuu,SUM(b.interest)as interr, " +
                "(SELECT sum(a.amount) FROM dd_accounting a INNER JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE a.accounting_date < ? AND a.tracking_id = ? AND b.acc_type = '1') as acc_credited ," +
                "(SELECT sum(a.amount) FROM dd_accounting a INNER JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE a.accounting_date < ? AND a.tracking_id = ? AND b.acc_type = '2') as acc_debited ," +
                "(SELECT sum(a.collection_amount) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 1 AND b.debit_type IN (0,1,2) AND a.collection_date < ?) as collected," +
                "(SELECT sum(a.discount) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 1 AND b.debit_type IN (0,1,2) AND a.collection_date < ?) as discounted" +
                " FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE b.debit_date < ? AND  a.debit_type IN (0,1,2) AND a.tracking_id = ? AND b.active_status = 1 ";
        String sess = String.valueOf(ses);
        Cursor cursor = database.rawQuery(NIP, new String[]{dateString,sess,dateString,sess,sess,dateString,sess,dateString,dateString,sess});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("debitted");
                    String debit = cursor.getString(index);
//
                    index = cursor.getColumnIndexOrThrow("collected");
                    String collect = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("acc_debited");
                    String acc_debit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("docuu");
                    String docc = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("interr");
                    String intre = cursor.getString(index);
//
                    index = cursor.getColumnIndexOrThrow("acc_credited");
                    String acc_credit = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("discounted");
                    String discount = cursor.getString(index);
                    if(debit != null){
                    }else{
                        debit ="0";
                    }
                    if(discount != null){
                    }else{
                        discount ="0";
                    }
                    if(collect != null){
                    }else{
                        collect ="0";
                    }if(acc_debit != null){
                    }else{
                        acc_debit ="0";
                    }if(acc_credit != null){
                    }else{
                        acc_credit ="0";
                    }
                    if(docc != null){
                    }else{
                        docc ="0";
                    }if(intre != null){
                    }else{
                        intre ="0";
                    }
                    Integer dbb = Integer.parseInt(debit);
                    Integer dio = Integer.parseInt(discount);
                    Integer ac_d = Integer.parseInt(acc_debit);
                    Integer col = Integer.parseInt(collect);
                    col = col - dio ;
                    Integer ac_c = Integer.parseInt(acc_credit);
                    Integer doo = Integer.parseInt(docc);
                    Integer in = Integer.parseInt(intre);
                    Integer dbed = dbb - in;
                    befo = (ac_c+col+doo+cloosed) - (dbed+ac_d);
                    Log.d("Callidccna", collect+" "+debit+" "+acc_debit+" "+acc_credit+","+dio);
                    Log.d("Callidna",debit+" "+docc+" "+intre);
                    Log.d("Callidna",String.valueOf(befo));
//                    yesterdaybal = String.valueOf(befo);
//                    bef.setText("\u20B9"+" "+yesterdaybal);
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

    //populateRecyclerView23() - get current final balance
    //Params - from date , to date and application context
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerView23(String dateString, String todateee, Context context) {
        SQLiteHelper sqlite;
        SQLiteDatabase database;
        sqlite = new SQLiteHelper(context);
        database = sqlite.getReadableDatabase();

//        String MY_QUERY = "SELECT a.*,b.customer_name,b.id as ID,c.debit_amount FROM dd_collection a INNER JOIN dd_customers b on b.id = a.customer_id INNER JOIN dd_account_debit c on c.customer_id = a.customer_id ";
        String MY_QUERY1 = "SELECT SUM(collection_amount) as paid," +
                "(SELECT SUM(b.amount) FROM dd_accounting_type a INNER JOIN dd_accounting b on b.acc_type_id = a.id WHERE b.accounting_date BETWEEN ? AND ? AND b.tracking_id = ? AND a.acc_type = '1' ) as todaycredit," +
                "(SELECT SUM(a.collection_amount) from dd_account_debit c LEFT JOIN dd_collection a ON a.debit_id = c.id INNER JOIN dd_customers b on b.id = a.customer_id where a.collection_date BETWEEN ? AND ? AND b.debit_type = '0' AND b.tracking_id = ?  AND c.active_status = 1) AS todaycollect," +
                "(SELECT SUM(a.debit_amount) from dd_account_debit a INNER JOIN dd_customers b on b.id = a.customer_id Where a.debit_date BETWEEN ? AND ? AND b.tracking_id = ? AND a.active_status = 1 ) as totaldebit," +
                "(SELECT SUM(a.document_charge) from dd_account_debit a INNER JOIN dd_customers b on b.id = a.customer_id Where a.debit_date BETWEEN ? AND ? AND b.tracking_id =? AND a.active_status = 1 ) as totaldocument ," +
                "(SELECT SUM(a.interest) from dd_account_debit a INNER JOIN dd_customers b ON b.id = a.customer_id Where a.debit_date BETWEEN ? AND ? AND b.tracking_id = ? AND a.active_status = 1 ) as totalinterest," +
                "(SELECT SUM(a.collection_amount) from dd_account_debit c LEFT JOIN dd_collection a ON a.debit_id = c.id INNER JOIN dd_customers b on b.id = a.customer_id where a.collection_date BETWEEN ? AND ? AND b.debit_type ='2' AND b.tracking_id = ? AND c.active_status = 1 ) as totalNIPNIP ," +
                "(SELECT SUM(a.collection_amount) from dd_account_debit c LEFT JOIN dd_collection a ON a.debit_id = c.id INNER JOIN dd_customers b on b.id = a.customer_id where a.collection_date BETWEEN ? AND ? AND b.debit_type ='1' AND b.tracking_id = ? AND c.active_status = 1 ) as totalNIP ," +
                "(SELECT SUM(b.amount) FROM dd_accounting_type a INNER JOIN dd_accounting b on b.acc_type_id = a.id WHERE b.accounting_date BETWEEN ? AND ? AND b.tracking_id = ? AND a.acc_type = '2' ) as totaldebittacc " +
                "FROM   dd_collection    ";
        String sess = String.valueOf(ses);
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{dateString,todateee,sess,dateString,todateee,sess,dateString,todateee,sess,dateString,todateee,sess,dateString,todateee,sess
                ,dateString,todateee,sess,dateString,todateee,sess,dateString,todateee,sess});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("todaycollect");
                    String today = cursor.getString(index);

//                    index = cursor.getColumnIndexOrThrow("beforecollect");
//                    String yesterday = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("todaycollect");
                    String paid = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totaldebit");
                    String debit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totaldocument");
                    String document = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totalinterest");
                    String interest = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totalNIPNIP");
                    String nipnip = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totalNIP");
                    String nip = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totaldebit");
                    String debbbt = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totaldebittacc");
                    String debbbtacc = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("todaycredit");
                    String credd = cursor.getString(index);

                    if(paid == null){
                        paid ="0";
                    }
//                    if(yesterday == null){
//                        yesterday ="0";
//                    }
                    if(today == null ){
                        today ="0";
//                        balance_cust.add(Long.valueOf(paid));
                    }else if(today =="0"){
//                        balance_cust.add(Long.valueOf(paid));
                    }else{
//                        paid_cust.add(Long.valueOf(paid));
                    }
                    if(debit == null){
                        debit = "0";
                    }
                    if(debbbt == null){
                        debbbt = "0";
                    }
                    if(document == null){
                        document = "0";
                    }
                    if(interest == null){
                        interest = "0";
                    }
                    if(credd == null){
                        credd = "0";
                    }
                    if(nip == null){
                        nip = "0";
                    }
                    if(nipnip == null){
                        nipnip = "0";
                    }
                    if(debbbtacc == null){
                        debbbtacc = "0";
                    }
                    Integer innn = Integer.parseInt(interest);
                    Integer documm = Integer.parseInt(document);
                    Integer dabit = Integer.parseInt(debit);
//                    Integer daabit = dabit -(innn+documm);
//                    befo = Integer.parseInt(yesterday);

                    newc = Integer.parseInt(credd);
                    curc = Integer.parseInt(today);
                    nipc = Integer.parseInt(nip);
                    nipnipc = Integer.parseInt(nipnip);
                    dab = dabit - innn;
                    totdab = Integer.parseInt(debbbtacc);
//                    bef.setText("Before balance :"+" "+yesterday);
//                    tc.setText("\u20B9"+" "+credd);
//                    totc.setText("\u20B9"+" "+today);
//                    nic.setText("\u20B9"+" "+nip);
//                    ninic.setText("\u20B9"+" "+nipnip);
//                    dcc.setText("\u20B9"+" "+document);
//                    intt.setText("\u20B9"+" "+interest);
//                    hg.setText("\u20B9"+" "+String.valueOf(dabit));
//                    lw.setText("\u20B9"+" "+debbbtacc);
                    Integer debitam1 = Integer.parseInt(debit);
                    Integer pamount = Integer.parseInt(paid);
//                    Names.add(today+"   "+paid+" "+debit+" "+document+" "+interest+" "+nipnip+" "+nip+" "+debbbt+" "+credd);
//                    collection.add(today);
                    Integer currentbal = (befo+newc+curc+nipc+nipnipc+documm)-(dab+totdab);
                    Log.d("ppp", String.valueOf(currentbal));
                    SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
                    final SharedPreferences.Editor editor = pref.edit();
                    editor.putString("totalbal",String.valueOf(currentbal));
                    editor.apply();
//                    if(currentbal <= 0){
//                        curbal.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.Red)));
//                    }else if(currentbal > 0){
//                        curbal.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.Green)));
//                    }
//                    curbal.setText("\u20B9"+" "+String.valueOf(currentbal));
//                    collection.addAll(Names);
//                    Names.add(id+" "+Name+" "+paid);
//                    Log.d("colammm", String.valueOf(collection));
                    Log.d("names", String.valueOf(currentbal));
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
//        String size = String.valueOf(Names.size());
//        String psize = String.valueOf(paid_cust.size());
//        String bsize = String.valueOf(balance_cust.size());

    }
    //onBackPressed() - function called when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onBackPressed() {
        Intent nn = new Intent(Debit.this,NavigationActivity.class);
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
//        getMenuInflater().inflate(R.menu.settings_activity, menu);

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
//        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_back) {
//           Intent back = new Intent()
//            return true;
//        }

        return super.onOptionsItemSelected(item);
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
            Intent new1 = new Intent(Debit.this,MainActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.user) {
            Intent new1 = new Intent(Debit.this,HomeActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.debit) {
            Intent new1 = new Intent(Debit.this,Debit.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.collection) {
            Intent new1 = new Intent(Debit.this,AllCollection.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.account) {
            Intent new1 = new Intent(Debit.this,Account.class);
            startActivity(new1);
            finish();
        }  else if (id == R.id.report) {
            Intent new1 = new Intent(Debit.this,ReportActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.customer) {
            Intent new1 = new Intent(Debit.this,Customer_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.settings) {
            Intent new1 = new Intent(Debit.this,Settings_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.tally) {
            Intent new1 = new Intent(Debit.this,Tally_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.employee) {
            Intent new1 = new Intent(Debit.this,Employee_details.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.today) {
            Intent new1 = new Intent(Debit.this,Today_report.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.logout) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Debit.this,R.style.AlertDialogTheme);
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
                            Intent i = new Intent(Debit.this,Splash.class);
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
