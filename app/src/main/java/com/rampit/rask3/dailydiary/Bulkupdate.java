package com.rampit.rask3.dailydiary;

import android.Manifest;
import android.animation.ValueAnimator;
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
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.rampit.rask3.dailydiary.Adapter.MyRecyclerViewAdapter1;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdaptercollection;
import com.rampit.rask3.dailydiary.Adapter.mobilenumber_adapter;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


//Updated on 25/01/2022 by RAMPIT
//used go make bulk collection
//call() - Call an number
//InternetConnection1() - Check whether internet is on or not
//updateLabel() - Update textview when date is selected
//expand() - Expand an view
//collapse() - collapse an view
//listo() - call list3 function
//list1() - get closed debit amount
//popup1() - get collection history of an customer
//pppp() - show collection history of an customer
//populateRecyclerView() - used to total paid and un paid and all customers debits of current , Nip
//populateRecyclerView1() - used to total paid and un paid and all customers debits of NipNip
//onOptionsItemSelected() - when navigation item pressed
//list() - used to all customers debits and caluclation of current , Nip
//list_nip() - used to all customers debits and caluclation of NipNip
//list3() - used to all customers debits and caluclation of current , Nip and notifyDataSetChanged will be called
//list3_nip() - used to all customers debits and caluclation of NipNip and notifyDataSetChanged will be called
//list2() - used to all customers debits and caluclation of current , Nip and notifyDataSetChanged will be called
//list2_nip() - used to all customers debits and caluclation of NipNip and notifyDataSetChanged will be called
//upddate() - used to insert and update multiple collection values
//dattee() - used to insert and update single collection values
//onBackPressed() - function called when back button is pressed
//progressbar_load() - Load progressbar
//progressbar_load2() - Load progressbar
//progressbar_load1() - Load progressbar
//progressbar_stop() - Stop progressbar
//progressbar_stop1() - Stop progressbar
//progressbar_stop2() - Stop progressbar



public class Bulkupdate extends AppCompatActivity {
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    RecyclerView recyclerView;
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> Names1 = new ArrayList<>();
    ArrayList<String> ooqw = new ArrayList<>();
    ArrayList<String> CURR = new ArrayList<>();
    ArrayList<String> NNP = new ArrayList<>();
    ArrayList<String> collection = new ArrayList<>();
    ArrayList<String> onlyid = new ArrayList<>();
    ArrayList<String> ID = new ArrayList<>();
    ArrayList<Long> ID1 = new ArrayList<>();
    ArrayList<String> collect1 = new ArrayList<>();
    ArrayList<Long> paid_cust = new ArrayList<>();
    ArrayList<Long> balance_cust = new ArrayList<>();
    public static String TABLE_NAME ="dd_collection";
    Integer ses,cle1,see1,see2,see,cle;
    TextView tot,ppp,bbb,noo,dateee,session,totco,tott;
    EditText dat;
    private MenuItem securedConnection;
    Button updata,adddebit,collectionhis;
    Calendar myCalendar;
    Menu menu;
    Integer xcoll,paiidddd,debitam,backk;
    String s,idi,dateString,datedd,timing,dated1,selected,cleared,datead,idicid;
    RecyclerViewAdaptercollection mAdapter;
    SimpleDateFormat sdf1;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    LinearLayout bull,lll1;
    Integer balanceamount,debitam1,instamt,pamount,pdays,toda,misda,slllno,misam;
    Double paiddays,balanceday,totda,dbdate,todate,missiday,missda1,toda2,missiamo,todaaal;
    Date newda,debittt;
    TextView totbal,time,uy,clobal;
    ImageView seesim,saveall,exitt;
    ImageButton exp;
    Integer in,collectoo,sll,disss,toda1,dabamo,expo1;
    DatePickerDialog datePickerDialog;
    String idd,iii,nme,paidamount,installment,totaldays,ddbt,debitdaaa,pp,dbdda,Clo,cloo,formattedDate,xcol,xbal,missed,debiitdate,todaydate,colllle1,balat,Name,adpr,edpr,depr,vipr,xcol1,date211,selectedvaaa;
    EditText search;
    FloatingActionButton fab;
    Dialog dialog;
    Integer searchcid;
    Cursor cursor;
    ImageButton clos,sear;
    Handler handler;
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
        expo1 = 0;
        String localeName = pref.getString("language","");
        if(localeName == null){
            localeName = "ta";
        }
        searchcid = 0;
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dialog = new Dialog(Bulkupdate.this,R.style.AlertDialogTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bulk_update);
        String sesid = pref.getString("id", "");
        dat = findViewById(R.id.date);
        if(sesid.equalsIgnoreCase("1")){

        }else{
            String module_i = "4";
            ((Callback) getApplication()).privilege(sesid, module_i);
            adpr = pref.getString("add_privilege", "");
            edpr = pref.getString("edit_privilege", "");
            depr = pref.getString("delete_privilege", "");
            vipr = pref.getString("view_privilege", "");
            Log.d("dtr",adpr);
            Log.d("dtr",vipr);

            if (adpr.equalsIgnoreCase("0") && vipr.equalsIgnoreCase("0")) {
                dat.setVisibility(View.INVISIBLE);
            }
            else if (adpr.equalsIgnoreCase("0") || vipr.equalsIgnoreCase("0")) {
                dat.setVisibility(View.INVISIBLE);
            }
            else{
                dat.setVisibility(View.INVISIBLE);
            }
        }
        handler = new Handler();
        dialog = new Dialog(Bulkupdate.this);
        uy = findViewById(R.id.tee);
        setTitle("");
        String ii1 = pref.getString("NAME","");
        uy.setText(ii1);
        ((Callback)getApplication()).datee();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        LinearLayout linear = findViewById(R.id.linearlayout1);
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),Bulkupdate.this)) {
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
//        toolbar.setTitle("Bulkupdate");
        time = findViewById(R.id.time);
        exp = findViewById(R.id.exp);
        if(in == 0){
            exp.setBackgroundResource(R.drawable.add);
        }else{
            exp.setBackgroundResource(R.drawable.add_blue);
        }
        sear = findViewById(R.id.sea);
        seesim = findViewById(R.id.sesimg);
        tot = findViewById(R.id.total);
        ppp = findViewById(R.id.pid);
        bbb = findViewById(R.id.bal);
        tott = findViewById(R.id.totot);
        lll1 = findViewById(R.id.liniy);
        collectionhis = findViewById(R.id.collection_history);
        totbal = findViewById(R.id.tot_bal);
        adddebit = findViewById(R.id.add_debit);
        noo = findViewById(R.id.no);
        bull = findViewById(R.id.bulkline);
        totco = findViewById(R.id.collection);
        updata = findViewById(R.id.upall);
        clobal = findViewById(R.id.clo_bal);
        fab = findViewById(R.id.fab_arrow);
        clos = findViewById(R.id.closedd);
        saveall = findViewById(R.id.saveall);
        exitt = findViewById(R.id.exit);
        xcoll = 0;
        backk = 0;
        collectoo = 0;

        search = findViewById(R.id.search);
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
        recyclerView = findViewById(R.id.re);
//        recyclerView.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        editor = pref.edit();

        ses = pref.getInt("session", 1);
        String dd = pref.getString("Date","");
        datead = pref.getString("buldate","");
        Calendar mk = Calendar.getInstance();
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date211 = sdf.format(mk.getTime());
//        ((Callback)getApplication()).closed1(date211, String.valueOf(ses));
//        ((Callback)getApplication()).beforebal(date211, String.valueOf(ses));
//        ((Callback)getApplication()).populateRecyclerView23(date211, date211 ,String.valueOf(ses));
        balat = pref.getString("totalbal","");
        String nbnb = getString(R.string.total_balance1);
        Log.d("balaa",balat);
        totbal.setText("\u20B9"+" "+balat);
        dateee = findViewById(R.id.da);
        session = findViewById(R.id.sess);
        dateee.setText(dd);
        if(in == 0){
            clos.setBackgroundResource(R.drawable.close);
            search.setBackgroundResource(R.drawable.search_widget);
            sear.setBackgroundResource(R.drawable.search_orange);
            search.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.White)));
            if(ses == 1){
                timing = getString(R.string.morning);
                seesim.setBackgroundResource(R.drawable.sun);
            }else if(ses == 2){
                timing = getString(R.string.evening);
                seesim.setBackgroundResource(R.drawable.moon);
            }
        }else{
            clos.setBackgroundResource(R.drawable.close_blue1);
            search.setBackgroundResource(R.drawable.search_widget1);
            sear.setBackgroundResource(R.drawable.search_blue);
            search.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.Black)));
            if(ses == 1){
                timing = getString(R.string.morning);
                seesim.setBackgroundResource(R.drawable.sun_blue);
            }else if(ses == 2){
                timing = getString(R.string.evening);
                seesim.setBackgroundResource(R.drawable.moon_blue);
            }
        }
        final LinearLayout lllooo =findViewById(R.id.linearlayout1);
//        lllooo.getViewTreeObserver().addOnGlobalLayoutListener(new  ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Rect r = new Rect();
//                lllooo.getWindowVisibleDisplayFrame(r);
//                int screenHeight = lllooo.getRootView().getHeight();
//                int keypadHeight = screenHeight - r.bottom;
////                if (keypadHeight > screenHeight * 0.15) {
////                    fab.setVisibility(View.VISIBLE);
////                    Toast.makeText(Bulkupdate.this,"Keyboard is showing",Toast.LENGTH_LONG).show();
////                } else {
//
////                    Toast.makeText(Bulkupdate.this,"keyboard closed",Toast.LENGTH_LONG).show();
//                   }
//            }
////        });
        final Handler handler1 = new Handler();
        final Runnable runnable = new Runnable() {
            public void run() {
                Log.d("Runnable","Handler is working");
                runOnUiThread(new Runnable() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void run() {
                        // Stuff that updates the UI
//                                    fab.hide();
                        fab.setVisibility(View.GONE);
                    }
                });
            }
        };

        fab.setVisibility(View.GONE);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                Log.d("scrolled", String.valueOf(dy));
                if (dy > 0 ||dy<0 )
                {
                    Log.d("scrolled", String.valueOf(fab.getVisibility()));
//                    fab.show();
                    fab.setVisibility(View.VISIBLE);
                    handler.removeCallbacks(runnable);
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
                    Log.d("scrolled100",String.valueOf(fab.getVisibility()));

//                    handler.postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            //Do something after 100ms
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    // Stuff that updates the UI
////                                    fab.hide();
//                                    fab.setVisibility(View.GONE);
//                                }
//                            });
//                        }
//                    }, 10000);
                    handler.postDelayed(runnable, 10000);

//                    new Timer().schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            // this code will be executed after 2 seconds
////                                        Log.d("Status ",status);
////                            fab.setVisibility(View.GONE);
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    // Stuff that updates the UI
////                                    fab.hide();
//                                    fab.setVisibility(View.GONE);
//                                }
//                            });
//                        }
//                    }, 10000);
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
        paiidddd = 0;
        debitam = 0;
        toda1 = 0;
        session.setText(timing);
        myCalendar = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat df2 = new SimpleDateFormat("hh:mm aaa");
        String timeZone = Calendar.getInstance().getTimeZone().getID();
        df2.setTimeZone(TimeZone.getTimeZone(timeZone));
        String timee = df2.format(c.getTime());
        time.setText(timee);
        String myFormat1 = "dd/MM/yyyy";//In which you need put here
        sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
//        String formattedDate = df.format(c.getTime());
        try {
            Date debit = df1.parse(dd);
            datedd = df1.format(debit);
            dated1 = df.format(debit);
            Log.d("deae",dated1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dat.setText(datedd);
        dateString = formattedDate;
        Intent name = getIntent();
        String dstr = name.getStringExtra("newda");
        if(dstr != null){
            dateString = dstr;
            try {
                Date debit = df.parse(dstr);
                datedd = sdf1.format(debit);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dat.setText(datedd);
        }
        if(datead.equalsIgnoreCase("")){
        }else{
            dated1 = datead;
            try {
                Date debit = df.parse(dated1);
                datedd = df1.format(debit);
//                dated1 = df.format(debit);
                Log.d("deae",dated1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dat.setText(datedd);
        }
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setFocusable(true);
                search.setFocusableInTouchMode(true);
            }
        });
        search.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    search.setFocusable(false);
//                    Toast.makeText(getApplicationContext(), String.valueOf(interr), Toast.LENGTH_SHORT).show();
//                    calclate();
                }
            }
        });
        search.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0)
                {
                    if(Names.size() == 0){
                    }else{
//                        mAdapter.filter(String.valueOf(s));
                        String hh = String.valueOf(s);
                        if(hh == null || hh.equalsIgnoreCase("")){
                            hh = "0";
                        }
                        searchcid= Integer.parseInt(hh);
                        Log.d("searchcid",String.valueOf(searchcid));
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
//                    mAdapter.filter(String.valueOf(s));
                    String hh = String.valueOf(s);
                    if(hh == null || hh.equalsIgnoreCase("")){
                        hh = "0";
                    }
                    searchcid= Integer.parseInt(hh);
                    Log.d("searchcid1",String.valueOf(searchcid));
                }
            }
        });
        sear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("searchid",searchcid);
                editor.commit();
//        list3();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                list3();
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
        });
        clos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setText("");
                searchcid = 0;
//        list3();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                list3();
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
        });
        Intent nn = getIntent();
        selected = nn.getStringExtra("selected");
        cleared = nn.getStringExtra("cleared");
        idi = nn.getStringExtra("idi");
        selectedvaaa = nn.getStringExtra("selectedvaaa");
//        Log.d("naid",idi);
        if(selected != null && cleared != null && idi != null){
            upddate();
//            list1();
//            list();
        }
        adddebit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newdeb = new Intent(Bulkupdate.this,Newdebit.class);
                newdeb.setFlags(FLAG_ACTIVITY_NEW_TASK);
                startActivity(newdeb);
                finish();
            }
        });
        exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expo1.equals(0)) {
                    expand(lll1,250,250);
                    expo1 = 1;
                    if(in == 0){
                        exp.setBackgroundResource(R.drawable.minus_orange);
                    }else{
                        exp.setBackgroundResource(R.drawable.minus_blue);
                    }
                }else if(expo1.equals(1)){
                    collapse(lll1,250,0);
                    expo1 = 0 ;
                    if(in == 0){
                        exp.setBackgroundResource(R.drawable.add);
                    }else{
                        exp.setBackgroundResource(R.drawable.add_blue);
                    }
                }
            }
        });
        updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.updatt(dated1);
//                securedConnection.setEnabled(true);
                backk =1;
//                list();
                Intent nre = new Intent(Bulkupdate.this,Bulkupdate.class);
                nre.setFlags(FLAG_ACTIVITY_NEW_TASK);
                startActivity(nre);

                Toast.makeText(Bulkupdate.this,"Updated Successfully" ,Toast.LENGTH_SHORT).show();
            }
        });
//        list1();
//        list();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list1();
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
//        populateRecyclerView();
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
        dat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (in == 0){
                    datePickerDialog =  new DatePickerDialog(Bulkupdate.this,R.style.DialogTheme, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH ));
                }else{
                    datePickerDialog =  new DatePickerDialog(Bulkupdate.this,R.style.DialogTheme1, date, myCalendar
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
        saveall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Bulkupdate.this,R.style.AlertDialogTheme);
                String logmsg = getString(R.string.bulupdate_alert);
                String warr = getString(R.string.warning);
                String logo = getString(R.string.confirm);
                alertbox.setMessage(logmsg);
                alertbox.setTitle(warr);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setPositiveButton(logo,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {

//                            list();
//                            final Dialog dialog = new Dialog(Bulkupdate.this);
//                            //set layout custom
//                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                            dialog.setContentView(R.layout.progressbar);
//                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                            lp.copyFrom(dialog.getWindow().getAttributes());
//                            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                            lp.gravity = Gravity.CENTER;
//                            dialog.setCancelable(false);
//                            dialog.getWindow().setAttributes(lp);
//                            dialog.show();
                                mAdapter.updatt1(dated1);
//                            securedConnection.setEnabled(true);
                                backk =1;
//                            new Timer().schedule(new TimerTask() {
//                                @Override
//                                public void run() {
//                                    // this code will be executed after 2 seconds
//                                    dialog.dismiss();
//
//                                    Intent nn = new Intent(Bulkupdate.this,AllCollection.class);
//                                    startActivity(nn);
//                                    finish();
//                                }
//                            }, 2000);


                            }
                        });
                alertbox.show();
            }
        });

        exitt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Bulkupdate.this,R.style.AlertDialogTheme);
                String logmsg = getString(R.string.bulupdate_alert1);
                String warr = getString(R.string.warning);
                String logo = getString(R.string.confirm);
                alertbox.setMessage(logmsg);
                alertbox.setTitle(warr);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setPositiveButton(logo,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {

//                            list();
//                            final Dialog dialog = new Dialog(Bulkupdate.this);
//                            //set layout custom
//                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                            dialog.setContentView(R.layout.progressbar);
//                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                            lp.copyFrom(dialog.getWindow().getAttributes());
//                            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                            lp.gravity = Gravity.CENTER;
//                            dialog.setCancelable(false);
//                            dialog.getWindow().setAttributes(lp);
//                            dialog.show();
                                mAdapter.updatt(dated1);
//                            securedConnection.setEnabled(true);
                                backk =1;
//                            new Timer().schedule(new TimerTask() {
//                                @Override
//                                public void run() {
//                                    // this code will be executed after 2 seconds
//                                    dialog.dismiss();
//
//                                    Intent nn = new Intent(Bulkupdate.this,AllCollection.class);
//                                    startActivity(nn);
//                                    finish();
//                                }
//                            }, 2000);


                            }
                        });
                alertbox.show();
            }
        });

//        list1();
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
//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            int ydy = 0;
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int offset = dy - ydy;
//                ydy = dy;
//                Log.d("scrolling_scroll","yes");
//                InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(recyclerView.getWindowToken(), 0);
//
////                boolean shouldRefresh = (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0)
////                        && (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING) && offset > 30;
////                if (shouldRefresh) {
////                    //swipeRefreshLayout.setRefreshing(true);
////                    //Refresh to load data here.
////                    return;
////                }
////                boolean shouldPullUpRefresh = linearLayoutManager.findLastCompletelyVisibleItemPosition() == linearLayoutManager.getChildCount() - 1
////                        && recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING && offset < -30;
////                if (shouldPullUpRefresh) {
////                    //swipeRefreshLayout.setRefreshing(true);
////                    //refresh to load data here.
////                    return;
////                }
////                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
        lllooo.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = lllooo.getRootView().getHeight() - lllooo.getHeight();

                if (heightDiff > 100) { // Value should be less than keyboard's height
                    Log.e("MyActivity", "keyboard opened");
                } else {
                    Log.e("MyActivity", "keyboard closed");
                }
            }
        });
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                return false;
            }
        });

    }

    //call() - Call an number
    //Params - mobilenmuber
    //Created on 25/01/2022
    //Return - NULL
    public void call(String mobilenmuber) {
        String p = "+91"+mobilenmuber;
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + p));
        if (ContextCompat.checkSelfPermission(Bulkupdate.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Bulkupdate.this,new String[]{Manifest.permission.CALL_PHONE} ,1);
        }
        else
        {
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Bulkupdate account) {
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
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dated1 = sdf.format(myCalendar.getTime());
        dat.setText(sdf1.format(myCalendar.getTime()));
//        populateRecyclerView();
        editor.putString("buldate",dated1);
        editor.apply();
        Intent u78 = new Intent(Bulkupdate.this,Bulkupdate.class);
        startActivity(u78);

//        list1();
//        list();

    }

    //expand() - Expand an view
    //Params - view , time taken and height to expand
    //Created on 25/01/2022
    //Return - NULL
    public static void expand(final View v, int duration, int targetHeight) {

        int prevHeight  = v.getHeight();

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();

    }

    //collapse() - collapse an view
    //Params - view , time taken and height to collapse
    //Created on 25/01/2022
    //Return - NULL
    public static void collapse(final View v, int duration, int targetHeight) {
        int prevHeight  = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    //listo() - call list3 function
    //Params - search id
    //Created on 25/01/2022
    //Return - NULL
    public void listo(Integer cc){
        searchcid = cc;
        list3();
    }

    //list1() - get closed debit amount
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void list1(){
        dabamo = 0;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        String mm = "SELECT a.customer_name,a.id as ID,a.location,a.phone_1,a.phone_2,a.debit_type_updated,b.*,c.vv as collect,c.debit_id " +
                " ,a.debit_type FROM dd_customers  a " +
                "LEFT JOIN dd_account_debit b on b.customer_id = a.id "  +
                "LEFT JOIN (SELECT SUM(collection_amount) as vv,debit_id,collection_date from dd_collection WHERE collection_date = ? GROUP BY debit_id ORDER BY debit_id  ) c ON  b.id = c.debit_id  WHERE a.tracking_id = ? AND  " +
                "b.active_status = 0 AND c.collection_date = ?  GROUP BY b.id ORDER BY a.order_id_new ASC";
        String MY_QUERY = "SELECT b.*,a.customer_name,a.id as ID,a.location,a.phone_1,sum(c.collection_amount) as collect,a.debit_type_updated FROM dd_account_debit b LEFT JOIN dd_customers a on  a.id = b.customer_id  " +
                " Left JOIN (SELECT collection_amount,collection_date,customer_id,debit_id,discount from dd_collection GROUP BY debit_id ) c on c.debit_id = b.id  WHERE  a.tracking_id = ?  AND b.active_status = 0 GROUP BY b.id ";
//   String mm = "SELECT b.*,sum(c.co)";
        Cursor cursor = database.rawQuery(mm, new String[]{dated1,String.valueOf(ses),dated1});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("collect");
                    xcol1 = cursor.getString(index);
                    String vc = cursor.getString(index);
                    Log.d("ins3",vc);
                    if(vc == null ){
                        vc = "0";
                    }
                    Integer d1 = Integer.parseInt(vc);
                    dabamo = dabamo + d1;
                    clobal.setText("\u20B9"+""+String.valueOf(dabamo));
//                      deb12.setText(String.valueOf(dabamo));
//                    doc.setText(String.valueOf(docamo));
//                    inst.setText(String.valueOf(intamo));
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                cursor.close();
                sqlite.close();
                database.close();
                clobal.setText("\u20B9"+""+"0");
//                doc.setText("0");
//                inst.setText("0");
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
            clobal.setText("\u20B9"+""+"0");
//            doc.setText("0");
//            inst.setText("0");
        }

    }

    //popup1() - get collection history of an customer
    //Params - customer id
    //Created on 25/01/2022
    //Return - NULL
    public void popup1(String iii){
        Names1.clear();
        sll = 0;
        collectoo = 0;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();

        String MY_QUERY = "SELECT a.*,c.CID FROM dd_customers c  LEFT JOIN  dd_account_debit b ON b.customer_id = c.id  " +
                "LEFT JOIN dd_collection a ON a.debit_id = b.id  where a.customer_id = ? AND b.active_status = ?" +
                "  ORDER BY a.collection_date DESC";
        String whereClause = "customer_id=? ";
        String[] whereArgs = new String[] {iii};
//        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{idd});
        String[] columns = {"customer_name",
                "id","collection_amount","collection_date","customer_id"};
        String order = "collection_date";
        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{iii,"1"});
//        Cursor cursor = database.query("dd_collection",
//                columns,
//                whereClause,
//                whereArgs,
//                null,
//                null,
//                order+" DESC");
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("CID");
                    idicid = cursor.getString(index);

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
                    collectoo = collectoo + tt;
                    Log.d("nam12", String.valueOf(collectoo));
//                    if (collectoo == 0) {

//                    } else {
                        if(tt<=0){
                        }else{

                            sll = sll + 1;
                            Names1.add(String.valueOf(sll) + "," + dateString + "," + dbamount);
                            ID1.add(id);
                            Log.d("nam", String.valueOf(Names1));
                        }
//                    pppp();
//                    }
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
                if(String.valueOf(Names1.size()) == null || String.valueOf(Names1.size()).equalsIgnoreCase("")){

                }else{
                    if(collectoo == 0){
                        AlertDialog.Builder alertbox = new AlertDialog.Builder(Bulkupdate.this,R.style.AlertDialogTheme);
                        String logmsg = getString(R.string.nocollection);
                        String cann = getString(R.string.ok);
                        String warr = getString(R.string.warning);
                        alertbox.setMessage(logmsg);
                        alertbox.setTitle(warr);
                        alertbox.setIcon(R.drawable.dailylogo);
                        alertbox.setPositiveButton(cann,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0,
                                                        int arg1) {
                                    }
                                });
                        alertbox.show();
                    }else{
                        pppp();
                    }
                }
            }else{
                cursor.close();
                sqlite.close();
                database.close();
                Log.d("nam12", "po1");
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Bulkupdate.this,R.style.AlertDialogTheme);
                String logmsg = getString(R.string.nocollection);
                String cann = getString(R.string.ok);
                String warr = getString(R.string.warning);
                alertbox.setMessage(logmsg);
                alertbox.setTitle(warr);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setPositiveButton(cann,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                            }
                        });
                alertbox.show();
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }


    }

    //pppp() - show collection history of an customer
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void pppp(){
        final Dialog dialog = new Dialog(Bulkupdate.this);
        //set layout custom
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popcollection);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);
        final RecyclerView rvcaddy = (RecyclerView) dialog.findViewById(R.id.rvAnimals);
        final TextView total = (TextView) dialog.findViewById(R.id.amount1);
        final TextView naam = (TextView) dialog.findViewById(R.id.nnam);
        final TextView nidd = (TextView) dialog.findViewById(R.id.id);
        total.setText(String.valueOf(collectoo));
        String ty = getString(R.string.name1);
        String ty1 = getString(R.string.SN1);
        naam.setText(ty+" "+Name);
        nidd.setText(ty1+" "+idicid);
        final MyRecyclerViewAdapter1 adapter = new MyRecyclerViewAdapter1(getApplicationContext(),Names1, ID1);
        noo =(TextView) dialog.findViewById(R.id.no);
//        searchView.setQueryHint("Google Search");
        Button close = (Button) dialog.findViewById(R.id.close);
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
//            rvcaddy.setAdapter(adapter);
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//            rvcaddy.setLayoutManager(mLayoutManager);
            adapter.setHasStableIds(true);

//            recyclerView.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//        rvcaddy.setAdapter(adapter);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvcaddy.setLayoutManager(mLayoutManager);
            rvcaddy.setAdapter(adapter);
            rvcaddy.setLayoutManager(mLayoutManager);
            rvcaddy.setItemViewCacheSize(1000);
            rvcaddy.setHasFixedSize(true);
            rvcaddy.setNestedScrollingEnabled(false);
            adapter.onAttachedToRecyclerView(rvcaddy);
        }
        dialog.show();
    }

    //populateRecyclerView() - used to total paid and un paid and all customers debits of current , Nip
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerView(){
        Names.clear();
        ooqw.clear();
        paid_cust.clear();
        balance_cust.clear();
        paiidddd = 0;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        Log.d("deaea",dated1);
        editor.putString("buldate",dated1);
        editor.apply();
//        String MY_QUERY = "SELECT a.*,b.customer_name,b.id as ID,c.debit_amount FROM dd_collection a INNER JOIN dd_customers b on b.id = a.customer_id INNER JOIN dd_account_debit c on c.customer_id = a.customer_id ";
//        String MY_QUERY1 = "SELECT SUM(a.collection_amount) as paid,b.customer_name,b.id as ID,b.debit_type,b.CID,b.order_id_new,c.debit_amount,a.customer_id," +
//                "(SELECT collection_amount from dd_collection where collection_date = ? AND customer_id = b.id) AS collect " +
//                "FROM  dd_customers b LEFT JOIN dd_collection a on b.id =  a.customer_id " +
//                "LEFT JOIN dd_account_debit c on b.id = c.customer_id WHERE b.tracking_id = ? AND c.debit_date <= ?  GROUP BY b.id ORDER BY b.order_id_new ASC";
        String MY_QUERY1 = "SELECT cus.*,deb.customer_id,col.collection_amount ,sum(col.collection_amount)as paid,sum(col.discount)as disc  ,deb.debit_amount,deb.debit_date,deb.id as did FROM dd_customers cus " +
                " LEFT JOIN dd_account_debit deb on deb.customer_id = cus.id " +
                "  LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id,discount from dd_collection WHERE collection_date = ? GROUP BY customer_id ) col on  deb.id = col.debit_id  AND col.collection_date = ?" +
                "  WHERE cus.tracking_id = ? AND deb.debit_date <= ? AND deb.active_status = 1 AND  cus.debit_type IN(0,1,2) GROUP BY cus.id ORDER BY cus.order_id_new ASC";
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{dated1,dated1,String.valueOf(ses),dated1});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    String id = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("did");
                    String did = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debit = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_date");
                    String debitdate = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("collection_amount");
                    String collect = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("paid");
                    String paid = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String typ = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String orderrr = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    long ii1d = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("CID");
                    String iid = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("disc");
                    String disco = cursor.getString(index);
                    if(disco == null){
                        disco = "0";
                    }
                    if(collect == null){
                        collect = "0";
                        colllle1 = "";
                    }
                    if(paid == null){
                        paid ="0";
                    }else{
                    }
                    if(debitdate == null){
                        debitdate ="0";
                    }else{
                    }
                    Integer paa = Integer.parseInt(collect);
                    Log.d("colllecttt",String.valueOf(paa));
                    paiidddd = paiidddd + paa;
                    if(debit == null){
                        debit = "0";
                        if(collect == null){
                            collect = "0";
                            colllle1 = "";
                        }
                    }else{
                        if(paid == null ){
                            paid ="0";
                            balance_cust.add(ii1d);
                        }else if(paid =="0" || paid.equalsIgnoreCase("0")){
                            balance_cust.add(ii1d);
                        }else{
                            paid_cust.add(ii1d);
                        }

                        if(collect == null){
                            collect = "0";
                            colllle1 = "";

                        }}
//                    Log.d("disco",orderrr);
                    String parsedStr = Name.replaceAll("(.{14})", "$1\n");
                    if (typ.equalsIgnoreCase("0")) {
                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                        Names1.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
//                        CURR.add(typ);
                        ooqw.add(orderrr);
                    }
                    if (typ.equalsIgnoreCase("1")) {
                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                        Names1.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
//                        CURR.add(typ);
                    }
                    ooqw.add(orderrr);
                    if (typ.equalsIgnoreCase("2")) {
                        Names.add(orderrr+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                        Names1.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
//                        NNP.add(typ);
                        ooqw.add(orderrr);
                    }
                    if (typ.equalsIgnoreCase("3")) {
                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                        Names1.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                        ooqw.add(orderrr);
                    }

                    totco.setText("\u20B9"+" "+String.valueOf(paiidddd));
                    debiitdate = debitdate;
                    if(debitdate.equalsIgnoreCase("0")){
                    }else {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                        Calendar c = Calendar.getInstance();
                        todaydate = df.format(c.getTime());
                        try {
                            newda = df.parse(debiitdate);
                            Log.d("dates", String.valueOf(newda));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        try {
                            debittt = df.parse(todaydate);
                            Log.d("dates", String.valueOf(newda));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        long diffInMillisec = newda.getTime() - debittt.getTime();
                        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);


                    }
                    //                    else{
                    Log.d("colammm", String.valueOf(ooqw));
//                        Log.d("names", String.valueOf(Names));
//
//                    }
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
            String psize = String.valueOf(paid_cust.size());
            String bsize = String.valueOf(balance_cust.size());
            Integer pp = Integer.parseInt(psize);
            Integer bb = Integer.parseInt(bsize);
            Integer tt = pp + bb ;
            tot.setText(String.valueOf(tt));
            ppp.setText(psize);
            bbb.setText(bsize);
            Integer yty = dabamo + paiidddd;
            Integer tt1 = paiidddd + dabamo ;
            tott.setText(String.valueOf(tt1));

        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }
    }

    //populateRecyclerView1() - used to total paid and un paid and all customers debits of NipNip
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerView1(){
        Names1.clear();
        CURR.clear();
        NNP.clear();
        ooqw.clear();
//        paid_cust.clear();
//        balance_cust.clear();
//        paiidddd = 0;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        Log.d("deaea32",date211);
//        editor.putString("buldate",dated1);
//        editor.apply();
//        String MY_QUERY = "SELECT a.*,b.customer_name,b.id as ID,c.debit_amount FROM dd_collection a INNER JOIN dd_customers b on b.id = a.customer_id INNER JOIN dd_account_debit c on c.customer_id = a.customer_id ";
//        String MY_QUERY1 = "SELECT SUM(a.collection_amount) as paid,b.customer_name,b.id as ID,b.debit_type,b.CID,b.order_id_new,c.debit_amount,a.customer_id," +
//                "(SELECT collection_amount from dd_collection where collection_date = ? AND customer_id = b.id) AS collect " +
//                "FROM  dd_customers b LEFT JOIN dd_collection a on b.id =  a.customer_id " +
//                "LEFT JOIN dd_account_debit c on b.id = c.customer_id WHERE b.tracking_id = ? AND c.debit_date <= ?  GROUP BY b.id ORDER BY b.order_id_new ASC";
        String MY_QUERY1 = "SELECT cus.*,deb.customer_id,col.collection_amount ,sum(col.collection_amount)as paid,sum(col.discount)as disc  ,deb.debit_amount,deb.debit_date,deb.id as did FROM dd_customers cus " +
                " LEFT JOIN dd_account_debit deb on deb.customer_id = cus.id " +
                "  LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id,discount from dd_collection WHERE collection_date = ? GROUP BY customer_id ) col on  deb.id = col.debit_id  AND col.collection_date = ?" +
                "  WHERE cus.tracking_id = ? AND deb.debit_date <= ? AND deb.active_status = 1 AND  cus.debit_type IN(0,1,2) GROUP BY cus.id ORDER BY cus.order_id_new ASC";
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{dated1,dated1,String.valueOf(ses),dated1});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    String id = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("did");
                    String did = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debit = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_date");
                    String debitdate = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("collection_amount");
                    String collect = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("paid");
                    String paid = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String typ = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String orderrr = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    long ii1d = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("CID");
                    String iid = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("disc");
                    String disco = cursor.getString(index);
                    if(disco == null){
                        disco = "0";
                    }
                    if(collect == null){
                        collect = "0";
//                        colllle1 = "";
                    }
                    if(paid == null){
                        paid ="0";
                    }else{
                    }
                    if(debitdate == null){
                        debitdate ="0";
                    }else{
                    }
                    Integer paa = Integer.parseInt(collect);
                    Log.d("colllecttt",String.valueOf(paa));
//                    paiidddd = paiidddd + paa;
                    if(debit == null){
                        debit = "0";
                        if(collect == null){
                            collect = "0";
//                            colllle1 = "";
                        }
                    }else{
                        if(paid == null ){
                            paid ="0";
//                            balance_cust.add(ii1d);
                        }else if(paid =="0" || paid.equalsIgnoreCase("0")){
//                            balance_cust.add(ii1d);
                        }else{
//                            paid_cust.add(ii1d);
                        }

                        if(collect == null){
                            collect = "0";
//                            colllle1 = "";

                        }}
//                    Log.d("disco09",orderrr);
                    String parsedStr = Name.replaceAll("(.{14})", "$1\n");
                    if (typ.equalsIgnoreCase("0")) {
//                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                        Names1.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                        CURR.add(typ);
                        ooqw.add(typ);
                    }
                    if (typ.equalsIgnoreCase("1")) {
//                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                        Names1.add(iid + "," + parsedStr + "," + debit + "," + collect + "," + typ + "," + orderrr + "," + debitdate + "," + id + "," + did + "," + disco);
                        CURR.add(typ);
                        ooqw.add(typ);
                    }
                    if (typ.equalsIgnoreCase("2")) {
//                        Names.add(orderrr+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                        Names1.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                        NNP.add(typ);
                        ooqw.add(typ);
                    }
                    if (typ.equalsIgnoreCase("3")) {
//                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                        Names1.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                        ooqw.add(orderrr);
                    }

                    totco.setText("\u20B9"+" "+String.valueOf(paiidddd));
                    debiitdate = debitdate;
                    if(debitdate.equalsIgnoreCase("0")){
                    }else {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                        Calendar c = Calendar.getInstance();
                        todaydate = df.format(c.getTime());
                        try {
                            newda = df.parse(debiitdate);
                            Log.d("dates", String.valueOf(newda));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        try {
                            debittt = df.parse(todaydate);
                            Log.d("dates", String.valueOf(newda));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        long diffInMillisec = newda.getTime() - debittt.getTime();
                        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);


                    }
                    //                    else{
                    Log.d("colamm09m", String.valueOf(ooqw));
//                        Log.d("names", String.valueOf(Names));
//
//                    }
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
//            String size = String.valueOf(Names.size());
//            String psize = String.valueOf(paid_cust.size());
//            String bsize = String.valueOf(balance_cust.size());
//            Integer pp = Integer.parseInt(psize);
//            Integer bb = Integer.parseInt(bsize);
//            Integer tt = pp + bb ;
//            tot.setText(String.valueOf(tt));
//            ppp.setText(psize);
//            bbb.setText(bsize);
//            Integer yty = dabamo + paiidddd;
//            Integer tt1 = paiidddd + dabamo ;
//            tott.setText(String.valueOf(tt1));

        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }
    }
    //    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.settings_activity, menu);
//
//        MenuItem getItem = menu.findItem(R.id.action_back);
//        if (getItem != null) {
//            AppCompatButton button = (AppCompatButton) getItem.getActionView();
//            if(in == 0){
//                button.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bordered_button123) );
//            }else{
//                button.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bordered_button12) );
//            }
//            button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.White));
//            button.setText(getString(R.string.saveall1));
//            Typeface ty = Typeface.createFromAsset(getApplicationContext().getAssets(),"opensans.ttf");
//            button.setTypeface(ty);
//            button.setTextSize(13);
////            button.setLeft(10);
//            button.setTop(10);
//            button.setHeight(10);
//            button.setWidth(25);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    AlertDialog.Builder alertbox = new AlertDialog.Builder(Bulkupdate.this,R.style.AlertDialogTheme);
//                    String logmsg = getString(R.string.bulupdate_alert);
//                    String warr = getString(R.string.warning);
//                    String logo = getString(R.string.confirm);
//                    alertbox.setMessage(logmsg);
//                    alertbox.setTitle(warr);
//                    alertbox.setIcon(R.drawable.dailylogo);
//                    alertbox.setPositiveButton(logo,
//                            new DialogInterface.OnClickListener() {
//
//                                public void onClick(DialogInterface arg0,
//                                                    int arg1) {
//
////                                    list();
////                                    final Dialog dialog = new Dialog(Bulkupdate.this);
////                                    //set layout custom
////                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////                                    dialog.setContentView(R.layout.progressbar);
////                                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
////                                    lp.copyFrom(dialog.getWindow().getAttributes());
////                                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
////                                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
////                                    lp.gravity = Gravity.CENTER;
////                                    dialog.setCancelable(false);
////                                    dialog.getWindow().setAttributes(lp);
////                                    dialog.show();
//                                    mAdapter.updatt(dated1);
////                            securedConnection.setEnabled(true);
//                                    backk =1;
////                                    new Timer().schedule(new TimerTask() {
////                                        @Override
////                                        public void run() {
////                                            // this code will be executed after 2 seconds
////                                            dialog.dismiss();
////
////                                            Intent nn = new Intent(Bulkupdate.this,AllCollection.class);
////                                            startActivity(nn);
////                                            finish();
////                                        }
////                                    }, 2000);
//
//
//                                }
//                            });
//                    alertbox.show();
//                }
//            });
//            //Set a ClickListener, the text,
//            //the background color or something like that
//        }
//
//        return super.onCreateOptionsMenu(menu);
//    }
//@Override
//public boolean onCreateOptionsMenu(Menu menu) {
//    // Inflate the menu; this adds items to the action bar if it is present.
//    getMenuInflater().inflate(R.menu.back_button1, menu);
//
//    return true;
//}
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
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Bulkupdate.this,R.style.AlertDialogTheme);
            String logmsg = getString(R.string.bulupdate_alert);
            String warr = getString(R.string.warning);
            String logo = getString(R.string.confirm);
            alertbox.setMessage(logmsg);
            alertbox.setTitle(warr);
            alertbox.setIcon(R.drawable.dailylogo);
            alertbox.setPositiveButton(logo,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {

//                            list();
//                            final Dialog dialog = new Dialog(Bulkupdate.this);
//                            //set layout custom
//                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                            dialog.setContentView(R.layout.progressbar);
//                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                            lp.copyFrom(dialog.getWindow().getAttributes());
//                            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                            lp.gravity = Gravity.CENTER;
//                            dialog.setCancelable(false);
//                            dialog.getWindow().setAttributes(lp);
//                            dialog.show();
                            mAdapter.updatt(dated1);
//                            securedConnection.setEnabled(true);
                            backk =1;
//                            new Timer().schedule(new TimerTask() {
//                                @Override
//                                public void run() {
//                                    // this code will be executed after 2 seconds
//                                    dialog.dismiss();
//
//                                    Intent nn = new Intent(Bulkupdate.this,AllCollection.class);
//                                    startActivity(nn);
//                                    finish();
//                                }
//                            }, 2000);


                        }
                    });
            alertbox.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //list() - used to all customers debits and caluclation of current , Nip
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void list(){
//        populateRecyclerView1();
        populateRecyclerView();
//        populateRecyclerView1();
        onlyid.clear();
        debitam = 0;
        collect1.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        Log.d("sssear11",String.valueOf(searchcid));
        if(searchcid > 0){
            String MY_QUERY = "SELECT b.*,a.customer_name,a.id as ID,a.order_id_new,a.location,a.phone_1,a.phone_2,col.collection_amount,sum(c.collection_amount) as collect,sum(col.discount)as disc,c.other_fee,c.discount,a.debit_type,a.CID FROM dd_customers a LEFT JOIN dd_account_debit b on  b.customer_id = a.id" +
                    " Left JOIN dd_collection c on c.debit_id = b.id  " +
                    " LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id,discount from dd_collection WHERE collection_date = ? GROUP BY customer_id ) col on  b.id = col.debit_id  AND col.collection_date = ?" +
                    " WHERE  a.tracking_id = ? AND a.debit_type IN(0,1) AND b.debit_date <= ? AND b.active_status = 1 AND a.CID = ?  GROUP BY a.id  ORDER BY a.order_id_new ASC ";
//   String mm = "SELECT b.*,sum(c.co)";
            cursor = database.rawQuery(MY_QUERY, new String[]{dated1,dated1,String.valueOf(ses),dated1, String.valueOf(searchcid)});

        }else{
            String MY_QUERY = "SELECT b.*,a.customer_name,a.id as ID,a.order_id_new,a.location,a.phone_1,a.phone_2,col.collection_amount,sum(c.collection_amount) as collect,sum(col.discount)as disc,c.other_fee,c.discount,a.debit_type,a.CID FROM dd_customers a LEFT JOIN dd_account_debit b on  b.customer_id = a.id" +
                    " Left JOIN dd_collection c on c.debit_id = b.id  " +
                    " LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id,discount from dd_collection WHERE collection_date = ? GROUP BY customer_id ) col on  b.id = col.debit_id  AND col.collection_date = ?" +
                    " WHERE  a.tracking_id = ? AND a.debit_type IN(0,1) AND b.debit_date <= ? AND b.active_status = 1  GROUP BY a.id  ORDER BY a.order_id_new ASC ";
//   String mm = "SELECT b.*,sum(c.co)";
            cursor = database.rawQuery(MY_QUERY, new String[]{dated1,dated1,String.valueOf(ses),dated1});
        }

        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("collection_amount");
                    String collect10 = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String orderrr = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String typ = cursor.getString(index);
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
                    index = cursor.getColumnIndexOrThrow("phone_2");
                    String Pho1 = cursor.getString(index);
                    if(Pho1 == null || Pho1.equalsIgnoreCase("") || Pho1.equalsIgnoreCase("null")){
                        Pho1 = "0";
                    }
                    index = cursor.getColumnIndexOrThrow("other_fee");
                    String Oth = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("discount");
                    String Dis = cursor.getString(index);
                    disss = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("closeing_date");
                    Clo = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("collect");
                    xcol = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("installment_amount");
                    installment = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_days");
                    totaldays = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("CID");
                    String iid = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("disc");
                    String disco = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("ID");
                    String id = cursor.getString(index);
                    String myFormat1 = "dd/MM/yyyy";//In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                    if(collect10 == null){
                        collect10 = "0";
//                            colllle1 = "";
                    }
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
                    if(ddbt == null){
                        ddbt = "0";
                    }
                    if(totaldays == null){
                        totaldays = "0";
                    }
                    if(debitdaaa == null){
                        debitdaaa = "0";
                    }
                    if(Loc == null){
                        Loc = " ";
                    }
                    if(Pho == null){
                        Pho = " ";
                    }
                    if(Oth == null){
                        Oth = " ";
                    }
                    if(Dis == null){
                        Dis = "0";
                    }
                    if(disco == null){
                        disco = "0";
                    }
                    Integer da = Integer.parseInt(ddbt);
                    debitam = debitam + da ;
                    Log.d("tototo",totaldays);
                    if(totaldays.equalsIgnoreCase("0")){
                        collect1.add("0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0"+","+"0"+ "," + "0" + "," + "0" + "," + "0" + "," + "0"+","+"0"+","+"0" +","+iid+","+nme+","+"0"+","+"0"+","+typ+","+orderrr+","+"0"+","+id+","+iii+","+"0"+","+"0");
                        Log.d("collleee",String.valueOf(collect1));
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
                        balanceamount = balanceamount - disss;

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
                                missda1 =0.0;
                            }else {
                                String tod = String.valueOf(diffInDays);
                                toda = Integer.parseInt(tod) ;
                                toda1 = Integer.parseInt(tod) + 1;
                                toda2 = Double.parseDouble(tod);
//                                todaaal = new doub
//        Integer po = Integer.parseInt(pp);
                                missiday = paiddays;
                                Integer tota = Integer.parseInt(totaldays);
                                Double mii1 = new Double(toda);
                                Double mii2 = new Double(pdays);
                                misda =  toda - pdays;
                                Double mii;
                                mii = mii1 - mii2;
                                missda1 = toda2 - paiddays;
                                DecimalFormat df1 = new DecimalFormat("####.##");
                                missda1 = Double.valueOf(df1.format(missda1));
                                if(missda1 < 0.0 || missda1 == null){
                                    missda1 = 0.0 ;
                                }
                                Log.d("mmm", String.valueOf(mii));
                                if (misda >= balanceday) {
                                    misam = balanceamount;
//                                        misda = balanceday.intValue();
//                                        missed = String.valueOf(misda);
//                        misda = Integer.parseInt(missed);
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
                        if (typ.equalsIgnoreCase("0")) {
//                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            Names.add(iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            CURR.add(typ);
                            ooqw.add(typ);
                            onlyid.add(iid);
                        }
                        if (typ.equalsIgnoreCase("1")) {
//                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid + "," + nme + "," + ddbt + "," + collect10 + "," + typ + "," + orderrr + "," + debitdaaa + "," + id + "," + iii + "," + disco+","+Pho1);
                            Names.add(iid + "," + nme + "," + ddbt + "," + collect10 + "," + typ + "," + orderrr + "," + debitdaaa + "," + id + "," + iii + "," + disco + "," + Pho1);
                            CURR.add(typ);
                            ooqw.add(typ);
                            onlyid.add(iid);
                        }
                        if (typ.equalsIgnoreCase("2")) {
//                        Names.add(orderrr+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            Names.add(iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+ "," + Pho1);
                            NNP.add(typ);
                            ooqw.add(typ);
                            onlyid.add(iid);
                        }
                        if (typ.equalsIgnoreCase("3")) {
//                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            Names.add(iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+ "," + Pho1);
                            ooqw.add(orderrr);
                            onlyid.add(iid);
                        }
                        collect1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+ "," + Pho1);

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

        }else{
            cursor.close();
            sqlite.close();
            database.close();

        }
        list_nip();
      /*  if( debitam == 0)
        {
            bull.setVisibility(View.GONE);
            noo.setVisibility(View.VISIBLE);
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Bulkupdate.this,R.style.AlertDialogTheme);
            String enn = getString(R.string.no_debit);
            String war = getString(R.string.warning);
            String ook = getString(R.string.create_debit);
            alertbox.setMessage(enn);
            alertbox.setTitle(war);
            alertbox.setIcon(R.drawable.dailylogo);
            alertbox.setCancelable(false);
            alertbox.setPositiveButton(ook,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {
                            Intent i = new Intent(Bulkupdate.this,Newdebit.class);
                            startActivity(i);
                            finish();
                        }
                    });
            alertbox.show();
//                    adddebit.setVisibility(View.VISIBLE);
        }
        else {
            Log.d("opopopoiu","0987521234");
//            final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
//            String ii = pref.getString("selectedvalue1","");
//            if(ii == null || ii.equalsIgnoreCase("")){
//                ii = "0" ;
//            }else{
//            }

            recyclerView.setVisibility(View.VISIBLE);
            noo.setVisibility(View.GONE);

//                    adddebit.setVisibility(View.GONE);
            mAdapter = new RecyclerViewAdaptercollection(onlyid,Names,Names1,CURR,NNP,ses,collect1, datedd, in,getApplicationContext(),Bulkupdate.this);


//                    ItemTouchHelper.Callback callback =
//                            new ItemMoveCallback2(mAdapter);
//                    ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//                    touchHelper.attachToRecyclerView(recyclerView);
            mAdapter.setHasStableIds(true);

//            recyclerView.postDelayed(new Runnable() {
//                @Override
//                public void run() {
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemViewCacheSize(1000);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            mAdapter.onAttachedToRecyclerView(recyclerView);

//            recyclerView.setOverScrollMode(1);
//                }
//            },500);
//            recyclerView.smoothScrollToPosition(Integer.parseInt(ii));
//            editor.putString("selectedvalue1", "0");
//            editor.apply();
        }*/
    }

    //list_nip() - used to all customers debits and caluclation of NipNip
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void list_nip(){
//        populateRecyclerView1();
//        populateRecyclerView();
//        populateRecyclerView1();
//        onlyid.clear();
//        debitam = 0;
//        collect1.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        Log.d("sssear11",String.valueOf(searchcid));
        if(searchcid > 0){
            String MY_QUERY = "SELECT b.*,a.customer_name,a.id as ID,a.order_id_new,a.location,a.phone_1,a.phone_2,col.collection_amount,sum(c.collection_amount) as collect,sum(col.discount)as disc,c.other_fee,c.discount,a.debit_type,a.CID FROM dd_customers a LEFT JOIN dd_account_debit b on  b.customer_id = a.id" +
                    " Left JOIN dd_collection c on c.debit_id = b.id  " +
                    " LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id,discount from dd_collection WHERE collection_date = ? GROUP BY customer_id ) col on  b.id = col.debit_id  AND col.collection_date = ?" +
                    " WHERE  a.tracking_id = ? AND a.debit_type IN(2) AND b.debit_date <= ? AND b.active_status = 1 AND a.CID = ?  GROUP BY a.id  ORDER BY a.order_id_new ASC ";
//   String mm = "SELECT b.*,sum(c.co)";
            cursor = database.rawQuery(MY_QUERY, new String[]{dated1,dated1,String.valueOf(ses),dated1, String.valueOf(searchcid)});

        }else{
            String MY_QUERY = "SELECT b.*,a.customer_name,a.id as ID,a.order_id_new,a.location,a.phone_1,a.phone_2,col.collection_amount,sum(c.collection_amount) as collect,sum(col.discount)as disc,c.other_fee,c.discount,a.debit_type,a.CID FROM dd_customers a LEFT JOIN dd_account_debit b on  b.customer_id = a.id" +
                    " Left JOIN dd_collection c on c.debit_id = b.id  " +
                    " LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id,discount from dd_collection WHERE collection_date = ? GROUP BY customer_id ) col on  b.id = col.debit_id  AND col.collection_date = ?" +
                    " WHERE  a.tracking_id = ? AND a.debit_type IN(2) AND b.debit_date <= ? AND b.active_status = 1  GROUP BY a.id  ORDER BY a.order_id_new ASC ";
//   String mm = "SELECT b.*,sum(c.co)";
            cursor = database.rawQuery(MY_QUERY, new String[]{dated1,dated1,String.valueOf(ses),dated1});
        }

        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("collection_amount");
                    String collect10 = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String orderrr = cursor.getString(index);
                    Log.d("order_id111",orderrr);
                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String typ = cursor.getString(index);
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
                    index = cursor.getColumnIndexOrThrow("phone_2");
                    String Pho1 = cursor.getString(index);
                    if(Pho1 == null || Pho1.equalsIgnoreCase("") || Pho1.equalsIgnoreCase("null")){
                        Pho1 = "0";
                    }
                    index = cursor.getColumnIndexOrThrow("other_fee");
                    String Oth = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("discount");
                    String Dis = cursor.getString(index);
                    disss = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("closeing_date");
                    Clo = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("collect");
                    xcol = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("installment_amount");
                    installment = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_days");
                    totaldays = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("CID");
                    String iid = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("disc");
                    String disco = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("ID");
                    String id = cursor.getString(index);
                    String myFormat1 = "dd/MM/yyyy";//In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                    if(collect10 == null){
                        collect10 = "0";
//                            colllle1 = "";
                    }
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
                    if(ddbt == null){
                        ddbt = "0";
                    }
                    if(totaldays == null){
                        totaldays = "0";
                    }
                    if(debitdaaa == null){
                        debitdaaa = "0";
                    }
                    if(Loc == null){
                        Loc = " ";
                    }
                    if(Pho == null){
                        Pho = " ";
                    }
                    if(Oth == null){
                        Oth = " ";
                    }
                    if(Dis == null){
                        Dis = "0";
                    }
                    if(disco == null){
                        disco = "0";
                    }
                    Integer da = Integer.parseInt(ddbt);
                    debitam = debitam + da ;
                    Log.d("tototo",totaldays);
                    if(totaldays.equalsIgnoreCase("0")){
                        collect1.add("0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0"+","+"0"+ "," + "0" + "," + "0" + "," + "0" + "," + "0"+","+"0"+","+"0" +","+iid+","+nme+","+"0"+","+"0"+","+typ+","+orderrr+","+"0"+","+id+","+iii+","+"0"+","+"0");
                        Log.d("collleee",String.valueOf(collect1));
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
                        balanceamount = balanceamount - disss;

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
                                missda1 =0.0;
                            }else {
                                String tod = String.valueOf(diffInDays);
                                toda = Integer.parseInt(tod) ;
                                toda1 = Integer.parseInt(tod) + 1;
                                toda2 = Double.parseDouble(tod);
//                                todaaal = new doub
//        Integer po = Integer.parseInt(pp);
                                missiday = paiddays;
                                Integer tota = Integer.parseInt(totaldays);
                                Double mii1 = new Double(toda);
                                Double mii2 = new Double(pdays);
                                misda =  toda - pdays;
                                Double mii;
                                mii = mii1 - mii2;
                                missda1 = toda2 - paiddays;
                                DecimalFormat df1 = new DecimalFormat("####.##");
                                missda1 = Double.valueOf(df1.format(missda1));
                                if(missda1 < 0.0 || missda1 == null){
                                    missda1 = 0.0 ;
                                }
                                Log.d("mmm", String.valueOf(mii));
                                if (misda >= balanceday) {
                                    misam = balanceamount;
//                                        misda = balanceday.intValue();
//                                        missed = String.valueOf(misda);
//                        misda = Integer.parseInt(missed);
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
                        if (typ.equalsIgnoreCase("0")) {
//                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            Names.add(iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+ "," + Pho1);
                            CURR.add(typ);
                            ooqw.add(typ);
                            onlyid.add(iid);
                        }
                        if (typ.equalsIgnoreCase("1")) {
//                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid + "," + nme + "," + ddbt + "," + collect10 + "," + typ + "," + orderrr + "," + debitdaaa + "," + id + "," + iii + "," + disco+","+Pho1);
                            Names.add(iid + "," + nme + "," + ddbt + "," + collect10 + "," + typ + "," + orderrr + "," + debitdaaa + "," + id + "," + iii + "," + disco+ "," + Pho1);
                            CURR.add(typ);
                            ooqw.add(typ);
                            onlyid.add(iid);
                        }
                        if (typ.equalsIgnoreCase("2")) {
//                        Names.add(orderrr+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            Names.add(iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+ "," + Pho1);
                            NNP.add(typ);
                            ooqw.add(typ);
                            onlyid.add(iid);
                        }
                        if (typ.equalsIgnoreCase("3")) {
//                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            Names.add(iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+ "," + Pho1);
                            ooqw.add(orderrr);
                            onlyid.add(iid);
                        }
                        collect1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+ "," + Pho1);

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

        }else{
            cursor.close();
            sqlite.close();
            database.close();

        }
        if( debitam == 0){
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    progressbar_stop2();
                }
            });
            bull.setVisibility(View.GONE);
            noo.setVisibility(View.VISIBLE);
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Bulkupdate.this,R.style.AlertDialogTheme);
            String enn = getString(R.string.no_debit);
            String war = getString(R.string.warning);
            String ook = getString(R.string.create_debit);
            alertbox.setMessage(enn);
            alertbox.setTitle(war);
            alertbox.setIcon(R.drawable.dailylogo);
            alertbox.setCancelable(false);
            alertbox.setPositiveButton(ook,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {
                            Intent i = new Intent(Bulkupdate.this,Newdebit.class);
                            i.setFlags(FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
//                            finish();
                        }
                    });
            alertbox.show();
//                    adddebit.setVisibility(View.VISIBLE);
        }else {
            Log.d("opopopoiu","0987521234");
//            final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
//            String ii = pref.getString("selectedvalue1","");
//            if(ii == null || ii.equalsIgnoreCase("")){
//                ii = "0" ;
//            }else{
//            }
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    progressbar_stop2();
                }
            });
            recyclerView.setVisibility(View.VISIBLE);
            noo.setVisibility(View.GONE);

//                    adddebit.setVisibility(View.GONE);
            mAdapter = new RecyclerViewAdaptercollection(onlyid,Names,Names1,CURR,NNP,ses,collect1, datedd, in,getApplicationContext(),Bulkupdate.this);


//                    ItemTouchHelper.Callback callback =
//                            new ItemMoveCallback2(mAdapter);
//                    ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//                    touchHelper.attachToRecyclerView(recyclerView);
            mAdapter.setHasStableIds(true);

//            recyclerView.postDelayed(new Runnable() {
//                @Override
//                public void run() {
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemViewCacheSize(1000);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            mAdapter.onAttachedToRecyclerView(recyclerView);

//            recyclerView.setOverScrollMode(1);
//                }
//            },500);
//            recyclerView.smoothScrollToPosition(Integer.parseInt(ii));
//            editor.putString("selectedvalue1", "0");
//            editor.apply();
        }
    }

    //list3() - used to all customers debits and caluclation of current , Nip and notifyDataSetChanged will be called
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void list3(){
//        populateRecyclerView1();
        populateRecyclerView();
//        populateRecyclerView1();
        onlyid.clear();
        Log.d("sssear",String.valueOf(searchcid));
        debitam = 0;
        collect1.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        if(searchcid > 0){
            String MY_QUERY = "SELECT b.*,a.customer_name,a.id as ID,a.order_id_new,a.location,a.phone_1,a.phone_2,col.collection_amount,sum(c.collection_amount) as collect,sum(col.discount)as disc,c.other_fee,c.discount,a.debit_type,a.CID FROM dd_customers a LEFT JOIN dd_account_debit b on  b.customer_id = a.id" +
                    " Left JOIN dd_collection c on c.debit_id = b.id  " +
                    " LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id,discount from dd_collection WHERE collection_date = ? GROUP BY customer_id ) col on  b.id = col.debit_id  AND col.collection_date = ?" +
                    " WHERE  a.tracking_id = ? AND a.debit_type IN(0,1) AND b.debit_date <= ? AND b.active_status = 1 AND a.CID = ?  GROUP BY a.id  ORDER BY a.order_id_new ASC ";
//   String mm = "SELECT b.*,sum(c.co)";
            cursor = database.rawQuery(MY_QUERY, new String[]{dated1,dated1,String.valueOf(ses),dated1, String.valueOf(searchcid)});

        }else{
            String MY_QUERY = "SELECT b.*,a.customer_name,a.id as ID,a.order_id_new,a.location,a.phone_1,a.phone_2,col.collection_amount,sum(c.collection_amount) as collect,sum(col.discount)as disc,c.other_fee,c.discount,a.debit_type,a.CID FROM dd_customers a LEFT JOIN dd_account_debit b on  b.customer_id = a.id" +
                    " Left JOIN dd_collection c on c.debit_id = b.id  " +
                    " LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id,discount from dd_collection WHERE collection_date = ? GROUP BY customer_id ) col on  b.id = col.debit_id  AND col.collection_date = ?" +
                    " WHERE  a.tracking_id = ? AND a.debit_type IN(0,1) AND b.debit_date <= ? AND b.active_status = 1  GROUP BY a.id  ORDER BY a.order_id_new ASC ";
//   String mm = "SELECT b.*,sum(c.co)";
            cursor = database.rawQuery(MY_QUERY, new String[]{dated1,dated1,String.valueOf(ses),dated1});
        }

        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("collection_amount");
                    String collect10 = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String orderrr = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String typ = cursor.getString(index);
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
                    index = cursor.getColumnIndexOrThrow("phone_2");
                    String Pho1 = cursor.getString(index);
                    if(Pho1 == null || Pho1.equalsIgnoreCase("") || Pho1.equalsIgnoreCase("null")){
                        Pho1 = "0";
                    }
                    index = cursor.getColumnIndexOrThrow("other_fee");
                    String Oth = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("discount");
                    String Dis = cursor.getString(index);
                    disss = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("closeing_date");
                    Clo = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("collect");
                    xcol = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("installment_amount");
                    installment = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_days");
                    totaldays = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("CID");
                    String iid = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("disc");
                    String disco = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("ID");
                    String id = cursor.getString(index);
                    String myFormat1 = "dd/MM/yyyy";//In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                    if(collect10 == null){
                        collect10 = "0";
//                            colllle1 = "";
                    }
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
                    if(ddbt == null){
                        ddbt = "0";
                    }
                    if(totaldays == null){
                        totaldays = "0";
                    }
                    if(debitdaaa == null){
                        debitdaaa = "0";
                    }
                    if(Loc == null){
                        Loc = " ";
                    }
                    if(Pho == null){
                        Pho = " ";
                    }
                    if(Oth == null){
                        Oth = " ";
                    }
                    if(Dis == null){
                        Dis = "0";
                    }
                    if(disco == null){
                        disco = "0";
                    }
                    Integer da = Integer.parseInt(ddbt);
                    debitam = debitam + da ;
                    Log.d("tototo",totaldays);
                    if(totaldays.equalsIgnoreCase("0")){
                        collect1.add("0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0"+","+"0"+ "," + "0" + "," + "0" + "," + "0" + "," + "0"+","+"0"+","+"0" +","+iid+","+nme+","+"0"+","+"0"+","+typ+","+orderrr+","+"0"+","+id+","+iii+","+"0"+","+"0");
                        Log.d("collleee",String.valueOf(collect1));
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
                        balanceamount = balanceamount - disss;

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
                                missda1 =0.0;
                            }else {
                                String tod = String.valueOf(diffInDays);
                                toda = Integer.parseInt(tod) ;
                                toda1 = Integer.parseInt(tod) + 1;
                                toda2 = Double.parseDouble(tod);
//                                todaaal = new doub
//        Integer po = Integer.parseInt(pp);
                                missiday = paiddays;
                                Integer tota = Integer.parseInt(totaldays);
                                Double mii1 = new Double(toda);
                                Double mii2 = new Double(pdays);
                                misda =  toda - pdays;
                                Double mii;
                                mii = mii1 - mii2;
                                missda1 = toda2 - paiddays;
                                DecimalFormat df1 = new DecimalFormat("####.##");
                                missda1 = Double.valueOf(df1.format(missda1));
                                if(missda1 < 0.0 || missda1 == null){
                                    missda1 = 0.0 ;
                                }
                                Log.d("mmm", String.valueOf(mii));
                                if (misda >= balanceday) {
                                    misam = balanceamount;
//                                        misda = balanceday.intValue();
//                                        missed = String.valueOf(misda);
//                        misda = Integer.parseInt(missed);
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
                        if (typ.equalsIgnoreCase("0")) {
//                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            Names.add(iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            CURR.add(typ);
                            ooqw.add(typ);
                            onlyid.add(iid);
                        }
                        if (typ.equalsIgnoreCase("1")) {
//                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid + "," + nme + "," + ddbt + "," + collect10 + "," + typ + "," + orderrr + "," + debitdaaa + "," + id + "," + iii + "," + disco+","+Pho1);
                            Names.add(iid + "," + nme + "," + ddbt + "," + collect10 + "," + typ + "," + orderrr + "," + debitdaaa + "," + id + "," + iii + "," + disco+","+Pho1);
                            CURR.add(typ);
                            ooqw.add(typ);
                            onlyid.add(iid);
                        }
                        if (typ.equalsIgnoreCase("2")) {
//                        Names.add(orderrr+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            Names.add(iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            NNP.add(typ);
                            ooqw.add(typ);
                            onlyid.add(iid);
                        }
                        if (typ.equalsIgnoreCase("3")) {
//                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            Names.add(iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            ooqw.add(orderrr);
                            onlyid.add(iid);
                        }
                        collect1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);

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
//                list3_nip();

            }
            else{
                cursor.close();
                sqlite.close();
                database.close();
//                list3_nip();
                Log.d("list3_nip","1");

            }
            Log.d("collleee",String.valueOf(collect1));

        }else{
            cursor.close();
            sqlite.close();
            database.close();
//            list3_nip();
            Log.d("list3_nip","2");
        }
        list3_nip();
        Log.d("list3_nip","3");
       /* if( debitam == 0){
            bull.setVisibility(View.GONE);
            noo.setVisibility(View.VISIBLE);
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Bulkupdate.this,R.style.AlertDialogTheme);
            String enn = getString(R.string.no_debit);
            String war = getString(R.string.warning);
            String ook = getString(R.string.create_debit);
            String cancel = getString(R.string.cancel);
            alertbox.setMessage(enn);
            alertbox.setTitle(war);
            alertbox.setIcon(R.drawable.dailylogo);
            alertbox.setCancelable(false);
            alertbox.setPositiveButton(ook,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {
                            Intent i = new Intent(Bulkupdate.this,Newdebit.class);
                            startActivity(i);
                            finish();
                        }
                    });
            alertbox.setNegativeButton(cancel,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0,
                                            int arg1) {
                            search.setText("");
                            searchcid = 0;
                            list3();
                        }
                    });
            alertbox.show();
//                    adddebit.setVisibility(View.VISIBLE);
        }
        else {
            Log.d("opopopoiu","0987521234");
//            Log.d("opopopoiu",String.valueOf(debitam));
            bull.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            noo.setVisibility(View.GONE);
//            final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
//            String ii = pref.getString("selectedvalue1","");
//            if(ii == null || ii.equalsIgnoreCase("")){
//                ii = "0" ;
//            }else{
//            }

//            recyclerView.setVisibility(View.VISIBLE);
//            noo.setVisibility(View.GONE);
//
////                    adddebit.setVisibility(View.GONE);
//            mAdapter = new RecyclerViewAdaptercollection(onlyid,Names,Names1,CURR,NNP,ses,collect1, datedd, in,getApplicationContext(),Bulkupdate.this);
//
//
////                    ItemTouchHelper.Callback callback =
////                            new ItemMoveCallback2(mAdapter);
////                    ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
////                    touchHelper.attachToRecyclerView(recyclerView);
//            mAdapter.setHasStableIds(true);
//
////            recyclerView.postDelayed(new Runnable() {
////                @Override
////                public void run() {
//            recyclerView.setAdapter(mAdapter);
//            recyclerView.setLayoutManager(layoutManager);
//            recyclerView.setItemViewCacheSize(1000);
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setNestedScrollingEnabled(false);
//            mAdapter.onAttachedToRecyclerView(recyclerView);
            mAdapter.notifyDataSetChanged();

//            recyclerView.setOverScrollMode(1);
//                }
//            },500);
//            recyclerView.smoothScrollToPosition(Integer.parseInt(ii));
//            editor.putString("selectedvalue1", "0");
//            editor.apply();
        }*/
    }

    //list3_nip() - used to all customers debits and caluclation of NipNip and notifyDataSetChanged will be called
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void list3_nip(){
//        populateRecyclerView1();
//        populateRecyclerView();
//        populateRecyclerView1();
//        onlyid.clear();
//        Log.d("sssear",String.valueOf(searchcid));
//        debitam = 0;
//        collect1.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        if(searchcid > 0){
            String MY_QUERY = "SELECT b.*,a.customer_name,a.id as ID,a.order_id_new,a.location,a.phone_1,a.phone_2,col.collection_amount,sum(c.collection_amount) as collect,sum(col.discount)as disc,c.other_fee,c.discount,a.debit_type,a.CID FROM dd_customers a LEFT JOIN dd_account_debit b on  b.customer_id = a.id" +
                    " Left JOIN dd_collection c on c.debit_id = b.id  " +
                    " LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id,discount from dd_collection WHERE collection_date = ? GROUP BY customer_id ) col on  b.id = col.debit_id  AND col.collection_date = ?" +
                    " WHERE  a.tracking_id = ? AND a.debit_type IN(2) AND b.debit_date <= ? AND b.active_status = 1 AND a.CID = ?  GROUP BY a.id  ORDER BY a.order_id_new ASC ";
//   String mm = "SELECT b.*,sum(c.co)";
            cursor = database.rawQuery(MY_QUERY, new String[]{dated1,dated1,String.valueOf(ses),dated1, String.valueOf(searchcid)});

        }else{
            String MY_QUERY = "SELECT b.*,a.customer_name,a.id as ID,a.order_id_new,a.location,a.phone_1,a.phone_2,col.collection_amount,sum(c.collection_amount) as collect,sum(col.discount)as disc,c.other_fee,c.discount,a.debit_type,a.CID FROM dd_customers a LEFT JOIN dd_account_debit b on  b.customer_id = a.id" +
                    " Left JOIN dd_collection c on c.debit_id = b.id  " +
                    " LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id,discount from dd_collection WHERE collection_date = ? GROUP BY customer_id ) col on  b.id = col.debit_id  AND col.collection_date = ?" +
                    " WHERE  a.tracking_id = ? AND a.debit_type IN(2) AND b.debit_date <= ? AND b.active_status = 1  GROUP BY a.id  ORDER BY a.order_id_new ASC ";
//   String mm = "SELECT b.*,sum(c.co)";
            cursor = database.rawQuery(MY_QUERY, new String[]{dated1,dated1,String.valueOf(ses),dated1});
        }

        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("collection_amount");
                    String collect10 = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String orderrr = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String typ = cursor.getString(index);
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
                    index = cursor.getColumnIndexOrThrow("phone_2");
                    String Pho1 = cursor.getString(index);
                    if(Pho1 == null || Pho1.equalsIgnoreCase("") || Pho1.equalsIgnoreCase("null")){
                        Pho1 = "0";
                    }
                    index = cursor.getColumnIndexOrThrow("other_fee");
                    String Oth = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("discount");
                    String Dis = cursor.getString(index);
                    disss = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("closeing_date");
                    Clo = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("collect");
                    xcol = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("installment_amount");
                    installment = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_days");
                    totaldays = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("CID");
                    String iid = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("disc");
                    String disco = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("ID");
                    String id = cursor.getString(index);
                    String myFormat1 = "dd/MM/yyyy";//In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                    if(collect10 == null){
                        collect10 = "0";
//                            colllle1 = "";
                    }
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
                    if(ddbt == null){
                        ddbt = "0";
                    }
                    if(totaldays == null){
                        totaldays = "0";
                    }
                    if(debitdaaa == null){
                        debitdaaa = "0";
                    }
                    if(Loc == null){
                        Loc = " ";
                    }
                    if(Pho == null){
                        Pho = " ";
                    }
                    if(Oth == null){
                        Oth = " ";
                    }
                    if(Dis == null){
                        Dis = "0";
                    }
                    if(disco == null){
                        disco = "0";
                    }
                    Integer da = Integer.parseInt(ddbt);
                    debitam = debitam + da ;
                    Log.d("tototo",totaldays);
                    if(totaldays.equalsIgnoreCase("0")){
                        collect1.add("0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0"+","+"0"+ "," + "0" + "," + "0" + "," + "0" + "," + "0"+","+"0"+","+"0" +","+iid+","+nme+","+"0"+","+"0"+","+typ+","+orderrr+","+"0"+","+id+","+iii+","+"0"+","+"0");
                        Log.d("collleee",String.valueOf(collect1));
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
                        balanceamount = balanceamount - disss;

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
                                missda1 =0.0;
                            }else {
                                String tod = String.valueOf(diffInDays);
                                toda = Integer.parseInt(tod) ;
                                toda1 = Integer.parseInt(tod) + 1;
                                toda2 = Double.parseDouble(tod);
//                                todaaal = new doub
//        Integer po = Integer.parseInt(pp);
                                missiday = paiddays;
                                Integer tota = Integer.parseInt(totaldays);
                                Double mii1 = new Double(toda);
                                Double mii2 = new Double(pdays);
                                misda =  toda - pdays;
                                Double mii;
                                mii = mii1 - mii2;
                                missda1 = toda2 - paiddays;
                                DecimalFormat df1 = new DecimalFormat("####.##");
                                missda1 = Double.valueOf(df1.format(missda1));
                                if(missda1 < 0.0 || missda1 == null){
                                    missda1 = 0.0 ;
                                }
                                Log.d("mmm", String.valueOf(mii));
                                if (misda >= balanceday) {
                                    misam = balanceamount;
//                                        misda = balanceday.intValue();
//                                        missed = String.valueOf(misda);
//                        misda = Integer.parseInt(missed);
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
                        if (typ.equalsIgnoreCase("0")) {
//                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            Names.add(iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            CURR.add(typ);
                            ooqw.add(typ);
                            onlyid.add(iid);
                        }
                        if (typ.equalsIgnoreCase("1")) {
//                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid + "," + nme + "," + ddbt + "," + collect10 + "," + typ + "," + orderrr + "," + debitdaaa + "," + id + "," + iii + "," + disco+","+Pho1);
                            Names.add(iid + "," + nme + "," + ddbt + "," + collect10 + "," + typ + "," + orderrr + "," + debitdaaa + "," + id + "," + iii + "," + disco+","+Pho1);
                            CURR.add(typ);
                            ooqw.add(typ);
                            onlyid.add(iid);
                        }
                        if (typ.equalsIgnoreCase("2")) {
//                        Names.add(orderrr+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            Names.add(iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            NNP.add(typ);
                            ooqw.add(typ);
                            onlyid.add(iid);
                        }
                        if (typ.equalsIgnoreCase("3")) {
//                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            Names.add(iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            ooqw.add(orderrr);
                            onlyid.add(iid);
                        }
                        collect1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);

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

        }else{
            cursor.close();
            sqlite.close();
            database.close();

        }
        if( debitam == 0){
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    progressbar_stop2();
                }
            });
            bull.setVisibility(View.GONE);
            noo.setVisibility(View.VISIBLE);
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Bulkupdate.this,R.style.AlertDialogTheme);
            String enn = getString(R.string.no_debit);
            String war = getString(R.string.warning);
            String ook = getString(R.string.create_debit);
            String cancel = getString(R.string.cancel);
            alertbox.setMessage(enn);
            alertbox.setTitle(war);
            alertbox.setIcon(R.drawable.dailylogo);
            alertbox.setCancelable(false);
            alertbox.setPositiveButton(ook,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {
                            Intent i = new Intent(Bulkupdate.this,Newdebit.class);
                            i.setFlags(FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
//                            finish();
                        }
                    });
            alertbox.setNegativeButton(cancel,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0,
                                            int arg1) {
                            search.setText("");
                            searchcid = 0;
                            list3();
                        }
                    });
            alertbox.show();
//                    adddebit.setVisibility(View.VISIBLE);
        }else {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    progressbar_stop2();
                }
            });
            Log.d("opopopoiu","0987521234");
//            Log.d("opopopoiu",String.valueOf(debitam));
            bull.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            noo.setVisibility(View.GONE);
//            final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
//            String ii = pref.getString("selectedvalue1","");
//            if(ii == null || ii.equalsIgnoreCase("")){
//                ii = "0" ;
//            }else{
//            }

//            recyclerView.setVisibility(View.VISIBLE);
//            noo.setVisibility(View.GONE);
//
////                    adddebit.setVisibility(View.GONE);
//            mAdapter = new RecyclerViewAdaptercollection(onlyid,Names,Names1,CURR,NNP,ses,collect1, datedd, in,getApplicationContext(),Bulkupdate.this);
//
//
////                    ItemTouchHelper.Callback callback =
////                            new ItemMoveCallback2(mAdapter);
////                    ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
////                    touchHelper.attachToRecyclerView(recyclerView);
//            mAdapter.setHasStableIds(true);
//
////            recyclerView.postDelayed(new Runnable() {
////                @Override
////                public void run() {
//            recyclerView.setAdapter(mAdapter);
//            recyclerView.setLayoutManager(layoutManager);
//            recyclerView.setItemViewCacheSize(1000);
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setNestedScrollingEnabled(false);
//            mAdapter.onAttachedToRecyclerView(recyclerView);
            mAdapter.notifyDataSetChanged();

//            recyclerView.setOverScrollMode(1);
//                }
//            },500);
//            recyclerView.smoothScrollToPosition(Integer.parseInt(ii));
//            editor.putString("selectedvalue1", "0");
//            editor.apply();
        }
    }

    LinearLayoutManager layoutManager = new LinearLayoutManager(this) {

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(Bulkupdate.this) {

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

    //list2() - used to all customers debits and caluclation of current , Nip and notifyDataSetChanged will be called
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void list2(){
//        populateRecyclerView1();
//        populateRecyclerView();
//        populateRecyclerView1();
        onlyid.clear();
        debitam = 0;
        collect1.clear();
        SQLiteHelper sqlite = new SQLiteHelper(this);
        SQLiteDatabase database = sqlite.getWritableDatabase();
        String MY_QUERY = "SELECT b.*,a.customer_name,a.id as ID,a.order_id_new,a.location,a.phone_1,a.phone_2,col.collection_amount,sum(c.collection_amount) as collect,sum(col.discount)as disc,c.other_fee,c.discount,a.debit_type,a.CID FROM dd_customers a LEFT JOIN dd_account_debit b on  b.customer_id = a.id" +
                " Left JOIN dd_collection c on c.debit_id = b.id  " +
                " LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id,discount from dd_collection WHERE collection_date = ? GROUP BY customer_id ) col on  b.id = col.debit_id  AND col.collection_date = ?" +
                " WHERE  a.tracking_id = ? AND a.debit_type IN(0,1) AND b.debit_date <= ? AND b.active_status = 1  GROUP BY a.id  ORDER BY a.order_id_new ASC ";
//   String mm = "SELECT b.*,sum(c.co)";
        Cursor cursor = database.rawQuery(MY_QUERY, new String[]{dated1,dated1,String.valueOf(ses),dated1});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("collection_amount");
                    String collect10 = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String orderrr = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String typ = cursor.getString(index);
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
                    index = cursor.getColumnIndexOrThrow("phone_2");
                    String Pho1 = cursor.getString(index);
                    if(Pho1 == null || Pho1.equalsIgnoreCase("") || Pho1.equalsIgnoreCase("null")){
                        Pho1 = "0";
                    }
                    index = cursor.getColumnIndexOrThrow("other_fee");
                    String Oth = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("discount");
                    String Dis = cursor.getString(index);
                    disss = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("closeing_date");
                    Clo = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("collect");
                    xcol = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("installment_amount");
                    installment = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_days");
                    totaldays = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("CID");
                    String iid = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("disc");
                    String disco = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("ID");
                    String id = cursor.getString(index);
                    String myFormat1 = "dd/MM/yyyy";//In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                    if(collect10 == null){
                        collect10 = "0";
//                            colllle1 = "";
                    }
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
                    if(ddbt == null){
                        ddbt = "0";
                    }
                    if(totaldays == null){
                        totaldays = "0";
                    }
                    if(debitdaaa == null){
                        debitdaaa = "0";
                    }
                    if(Loc == null){
                        Loc = " ";
                    }
                    if(Pho == null){
                        Pho = " ";
                    }
                    if(Oth == null){
                        Oth = " ";
                    }
                    if(Dis == null){
                        Dis = "0";
                    }
                    if(disco == null){
                        disco = "0";
                    }
                    Integer da = Integer.parseInt(ddbt);
                    debitam = debitam + da ;
                    Log.d("tototo",totaldays);
                    if(totaldays.equalsIgnoreCase("0")){
                        collect1.add("0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0"+","+"0"+ "," + "0" + "," + "0" + "," + "0" + "," + "0"+","+"0"+","+"0" +","+iid+","+nme+","+"0"+","+"0"+","+typ+","+orderrr+","+"0"+","+id+","+iii+","+"0"+","+"0");
                        Log.d("collleee",String.valueOf(collect1));
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
                        balanceamount = balanceamount - disss;

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
                                missda1 =0.0;
                            }else {
                                String tod = String.valueOf(diffInDays);
                                toda = Integer.parseInt(tod) ;
                                toda1 = Integer.parseInt(tod) + 1;
                                toda2 = Double.parseDouble(tod);
//                                todaaal = new doub
//        Integer po = Integer.parseInt(pp);
                                missiday = paiddays;
                                Integer tota = Integer.parseInt(totaldays);
                                Double mii1 = new Double(toda);
                                Double mii2 = new Double(pdays);
                                misda =  toda - pdays;
                                Double mii;
                                mii = mii1 - mii2;
                                missda1 = toda2 - paiddays;
                                DecimalFormat df1 = new DecimalFormat("####.##");
                                missda1 = Double.valueOf(df1.format(missda1));
                                if(missda1 < 0.0 || missda1 == null){
                                    missda1 = 0.0 ;
                                }
                                Log.d("mmm", String.valueOf(mii));
                                if (misda >= balanceday) {
                                    misam = balanceamount;
//                                        misda = balanceday.intValue();
//                                        missed = String.valueOf(misda);
//                        misda = Integer.parseInt(missed);
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
                        if (typ.equalsIgnoreCase("0")) {
//                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            Names.add(iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            CURR.add(typ);
                            ooqw.add(typ);
                            onlyid.add(iid);
                        }
                        if (typ.equalsIgnoreCase("1")) {
//                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid + "," + nme + "," + ddbt + "," + collect10 + "," + typ + "," + orderrr + "," + debitdaaa + "," + id + "," + iii + "," + disco+","+Pho1);
                            Names.add(iid + "," + nme + "," + ddbt + "," + collect10 + "," + typ + "," + orderrr + "," + debitdaaa + "," + id + "," + iii + "," + disco+","+Pho1);
                            CURR.add(typ);
                            ooqw.add(typ);
                            onlyid.add(iid);
                        }
                        if (typ.equalsIgnoreCase("2")) {
//                        Names.add(orderrr+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            Names.add(iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            NNP.add(typ);
                            ooqw.add(typ);
                            onlyid.add(iid);
                        }
                        if (typ.equalsIgnoreCase("3")) {
//                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            Names.add(iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            ooqw.add(orderrr);
                            onlyid.add(iid);
                        }
                        collect1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);

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
//                list2_nip();
            }else{
                cursor.close();
                sqlite.close();
                database.close();
//                list2_nip();
            }
            Log.d("collleee",String.valueOf(collect1));
        /*    if( debitam == 0){
                bull.setVisibility(View.GONE);
                noo.setVisibility(View.VISIBLE);
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Bulkupdate.this,R.style.AlertDialogTheme);
                String enn = getString(R.string.no_debit);
                String war = getString(R.string.warning);
                String ook = getString(R.string.create_debit);
                String cancel = getString(R.string.cancel);
                alertbox.setMessage(enn);
                alertbox.setTitle(war);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setCancelable(false);
                alertbox.setPositiveButton(ook,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                Intent i = new Intent(Bulkupdate.this,Newdebit.class);
                                startActivity(i);
                                finish();
                            }
                        });

                alertbox.show();
//                    adddebit.setVisibility(View.VISIBLE);
            }
            else {

                mAdapter.notifyDataSetChanged();
                search.setText("");
                searchcid = 0;
                String s = search.getText().toString();
//                mAdapter.filter(String.valueOf(s));
//                recyclerView.setVisibility(View.VISIBLE);
//                noo.setVisibility(View.GONE);
////                    adddebit.setVisibility(View.GONE);
//                mAdapter = new RecyclerViewAdaptercollection(Names,Names1,CURR,NNP,ses,collect1, datedd, in,getApplicationContext(),Bulkupdate.this);
////                    ItemTouchHelper.Callback callback =
////                            new ItemMoveCallback2(mAdapter);
////                    ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
////                    touchHelper.attachToRecyclerView(recyclerView);
//                mAdapter.setHasStableIds(true);
////mAdapter.setIsRecyclable(false);
//                recyclerView.setAdapter(mAdapter);
//                recyclerView.smoothScrollToPosition(Integer.parseInt(ii));
//                editor.putString("selectedvalue1", "0");
//                editor.apply();
            }*/

        }else{
            cursor.close();
            sqlite.close();
            database.close();
//            list2_nip();
        }
        list2_nip();
    }

    //list2_nip() - used to all customers debits and caluclation of NipNip and notifyDataSetChanged will be called
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void list2_nip(){
//        populateRecyclerView1();
//        populateRecyclerView();
//        populateRecyclerView1();
//        onlyid.clear();
//        debitam = 0;
//        collect1.clear();
        SQLiteHelper sqlite = new SQLiteHelper(this);
        SQLiteDatabase database = sqlite.getWritableDatabase();
        String MY_QUERY = "SELECT b.*,a.customer_name,a.id as ID,a.order_id_new,a.location,a.phone_1,a.phone_2,col.collection_amount,sum(c.collection_amount) as collect,sum(col.discount)as disc,c.other_fee,c.discount,a.debit_type,a.CID FROM dd_customers a LEFT JOIN dd_account_debit b on  b.customer_id = a.id" +
                " Left JOIN dd_collection c on c.debit_id = b.id  " +
                " LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id,discount from dd_collection WHERE collection_date = ? GROUP BY customer_id ) col on  b.id = col.debit_id  AND col.collection_date = ?" +
                " WHERE  a.tracking_id = ? AND a.debit_type IN(2) AND b.debit_date <= ? AND b.active_status = 1  GROUP BY a.id  ORDER BY a.order_id_new ASC ";
//   String mm = "SELECT b.*,sum(c.co)";
        Cursor cursor = database.rawQuery(MY_QUERY, new String[]{dated1,dated1,String.valueOf(ses),dated1});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("collection_amount");
                    String collect10 = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String orderrr = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String typ = cursor.getString(index);
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
                    index = cursor.getColumnIndexOrThrow("phone_2");
                    String Pho1 = cursor.getString(index);
                    if(Pho1 == null || Pho1.equalsIgnoreCase("") || Pho1.equalsIgnoreCase("null")){
                        Pho1 = "0";
                    }
                    index = cursor.getColumnIndexOrThrow("other_fee");
                    String Oth = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("discount");
                    String Dis = cursor.getString(index);
                    disss = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("closeing_date");
                    Clo = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("collect");
                    xcol = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("installment_amount");
                    installment = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_days");
                    totaldays = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("CID");
                    String iid = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("disc");
                    String disco = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("ID");
                    String id = cursor.getString(index);
                    String myFormat1 = "dd/MM/yyyy";//In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                    if(collect10 == null){
                        collect10 = "0";
//                            colllle1 = "";
                    }
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
                    if(ddbt == null){
                        ddbt = "0";
                    }
                    if(totaldays == null){
                        totaldays = "0";
                    }
                    if(debitdaaa == null){
                        debitdaaa = "0";
                    }
                    if(Loc == null){
                        Loc = " ";
                    }
                    if(Pho == null){
                        Pho = " ";
                    }
                    if(Oth == null){
                        Oth = " ";
                    }
                    if(Dis == null){
                        Dis = "0";
                    }
                    if(disco == null){
                        disco = "0";
                    }
                    Integer da = Integer.parseInt(ddbt);
                    debitam = debitam + da ;
                    Log.d("tototo",totaldays);
                    if(totaldays.equalsIgnoreCase("0")){
                        collect1.add("0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0"+","+"0"+ "," + "0" + "," + "0" + "," + "0" + "," + "0"+","+"0"+","+"0" +","+iid+","+nme+","+"0"+","+"0"+","+typ+","+orderrr+","+"0"+","+id+","+iii+","+"0"+","+"0");
                        Log.d("collleee",String.valueOf(collect1));
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
                        balanceamount = balanceamount - disss;

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
                                missda1 =0.0;
                            }else {
                                String tod = String.valueOf(diffInDays);
                                toda = Integer.parseInt(tod) ;
                                toda1 = Integer.parseInt(tod) + 1;
                                toda2 = Double.parseDouble(tod);
//                                todaaal = new doub
//        Integer po = Integer.parseInt(pp);
                                missiday = paiddays;
                                Integer tota = Integer.parseInt(totaldays);
                                Double mii1 = new Double(toda);
                                Double mii2 = new Double(pdays);
                                misda =  toda - pdays;
                                Double mii;
                                mii = mii1 - mii2;
                                missda1 = toda2 - paiddays;
                                DecimalFormat df1 = new DecimalFormat("####.##");
                                missda1 = Double.valueOf(df1.format(missda1));
                                if(missda1 < 0.0 || missda1 == null){
                                    missda1 = 0.0 ;
                                }
                                Log.d("mmm", String.valueOf(mii));
                                if (misda >= balanceday) {
                                    misam = balanceamount;
//                                        misda = balanceday.intValue();
//                                        missed = String.valueOf(misda);
//                        misda = Integer.parseInt(missed);
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
                        if (typ.equalsIgnoreCase("0")) {
//                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            Names.add(iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            CURR.add(typ);
                            ooqw.add(typ);
                            onlyid.add(iid);
                        }
                        if (typ.equalsIgnoreCase("1")) {
//                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid + "," + nme + "," + ddbt + "," + collect10 + "," + typ + "," + orderrr + "," + debitdaaa + "," + id + "," + iii + "," + disco+","+Pho1);
                            Names.add(iid + "," + nme + "," + ddbt + "," + collect10 + "," + typ + "," + orderrr + "," + debitdaaa + "," + id + "," + iii + "," + disco+","+Pho1);
                            CURR.add(typ);
                            ooqw.add(typ);
                            onlyid.add(iid);
                        }
                        if (typ.equalsIgnoreCase("2")) {
//                        Names.add(orderrr+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            Names.add(iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            NNP.add(typ);
                            ooqw.add(typ);
                            onlyid.add(iid);
                        }
                        if (typ.equalsIgnoreCase("3")) {
//                        Names.add(iid+","+parsedStr+","+debit+","+collect+","+typ+","+orderrr+","+debitdate+","+id+","+did+","+disco);
                            Names1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                    +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            Names.add(iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);
                            ooqw.add(orderrr);
                            onlyid.add(iid);
                        }
                        collect1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
                                +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1+","+iid+","+nme+","+ddbt+","+collect10+","+typ+","+orderrr+","+debitdaaa+","+id+","+iii+","+disco+","+Pho1);

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
            if( debitam == 0){
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        progressbar_stop2();
                    }
                });
                bull.setVisibility(View.GONE);
                noo.setVisibility(View.VISIBLE);
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Bulkupdate.this,R.style.AlertDialogTheme);
                String enn = getString(R.string.no_debit);
                String war = getString(R.string.warning);
                String ook = getString(R.string.create_debit);
                String cancel = getString(R.string.cancel);
                alertbox.setMessage(enn);
                alertbox.setTitle(war);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setCancelable(false);
                alertbox.setPositiveButton(ook,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                Intent i = new Intent(Bulkupdate.this,Newdebit.class);
                                i.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
//                                finish();
                            }
                        });

                alertbox.show();
//                    adddebit.setVisibility(View.VISIBLE);
            }
            else {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        progressbar_stop2();
                        mAdapter.notifyDataSetChanged();
                        search.setText("");
                        searchcid = 0;
                        String s = search.getText().toString();
                    }
                });


//                mAdapter.filter(String.valueOf(s));
//                recyclerView.setVisibility(View.VISIBLE);
//                noo.setVisibility(View.GONE);
////                    adddebit.setVisibility(View.GONE);
//                mAdapter = new RecyclerViewAdaptercollection(Names,Names1,CURR,NNP,ses,collect1, datedd, in,getApplicationContext(),Bulkupdate.this);
////                    ItemTouchHelper.Callback callback =
////                            new ItemMoveCallback2(mAdapter);
////                    ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
////                    touchHelper.attachToRecyclerView(recyclerView);
//                mAdapter.setHasStableIds(true);
////mAdapter.setIsRecyclable(false);
//                recyclerView.setAdapter(mAdapter);
//                recyclerView.smoothScrollToPosition(Integer.parseInt(ii));
//                editor.putString("selectedvalue1", "0");
//                editor.apply();
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();

        }

    }
    //upddate() - used to insert and update multiple collection values
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void upddate() {
        see = Integer.valueOf(selected);
        Integer cle = Integer.valueOf(cleared);
        editor.putString("selectedvalue1", selectedvaaa);
        editor.apply();
        see1 = see ;
        s = String.valueOf(see1);
        cle1 = cle ;
        Log.d("namessssss54", String.valueOf(see));
        Log.d("namessssss54", String.valueOf(cle));
        if (see1 < cle1) {
            sqlite = new SQLiteHelper(this);
            database = sqlite.getReadableDatabase();
            String MY_QUERY1 = "SELECT * FROM dd_customers WHERE order_id_new = ? AND tracking_id = ?";
            Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{String.valueOf(see1),String.valueOf(ses)});
            if (cursor != null){
                if(cursor.getCount() != 0){
                    cursor.moveToFirst();
                    do{
                        int index;
                        index = cursor.getColumnIndexOrThrow("id");
//                        idi = cursor.getString(index);
                        Log.d("namessssss", idi);
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
            Log.d("namessssss", idi);
            see2 = see1 + 1;
            for (int i = see2; i <= cle1;i++){
                sqlite = new SQLiteHelper(this);
                database = sqlite.getWritableDatabase();
                Log.d("forrloop",String.valueOf(i));
                ContentValues cv = new ContentValues();
                cv.put("order_id_new",i-1);
                String[] args =  new String[]{String.valueOf(i), String.valueOf(ses)};
                database.update("dd_customers", cv,  "order_id_new=? AND tracking_id = ?",args);
                Log.d("forrloop1",String.valueOf(i));
                Log.d("forrloop2",String.valueOf(i+1));
                sqlite.close();
                database.close();
            }
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
            ContentValues cv1 = new ContentValues();
            cv1.put("order_id_new",cle1);
            database.update("dd_customers", cv1,  "id=? AND order_id_new=? AND tracking_id = ?", new String[]{idi,String.valueOf(see1), String.valueOf(ses)});
            sqlite.close();
            database.close();
            Toast.makeText(Bulkupdate.this,see1 +" "+cle1+" "+"smaller" ,Toast.LENGTH_SHORT).show();
        }else if (see1 > cle1){
            sqlite = new SQLiteHelper(this);
            database = sqlite.getReadableDatabase();
            String MY_QUERY1 = "SELECT * FROM dd_customers WHERE order_id_new = ? AND tracking_id = ?";
            Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{String.valueOf(see),String.valueOf(ses)});
            if (cursor != null){
                if(cursor.getCount() != 0){
                    cursor.moveToFirst();
                    do{
                        int index;
                        index = cursor.getColumnIndexOrThrow("id");
//                        idi = cursor.getString(index);
                        Log.d("namessssss", idi);
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
            see2 = see1 - 1;
            for (int i = see2; i >= cle1;i--){
                sqlite = new SQLiteHelper(this);
                database = sqlite.getWritableDatabase();
                Log.d("forrloop",String.valueOf(i));
                ContentValues cv = new ContentValues();
                cv.put("order_id_new",i+1);
                String[] args =  new String[]{String.valueOf(i), String.valueOf(ses)};
                database.update("dd_customers", cv,  "order_id_new=? AND tracking_id = ?",args);
                Log.d("forrloop3",String.valueOf(i));
                Log.d("forrloop4",String.valueOf(i+1));
                sqlite.close();
                database.close();
            }
            Log.d("forrloop5",String.valueOf(cle1)+" "+String.valueOf(see1)+" "+String.valueOf(idi));
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
            ContentValues cv1 = new ContentValues();
            cv1.put("order_id_new",cle1);
            database.update("dd_customers", cv1,  "id=? AND order_id_new=? AND tracking_id = ?", new String[]{idi,String.valueOf(see1), String.valueOf(ses)});
            Toast.makeText(Bulkupdate.this,see1 +" "+cle1+" "+idi ,Toast.LENGTH_SHORT).show();
            sqlite.close();
            database.close();
        }
    }

    //dattee() - used to insert and update single collection values
    //Params - collection date , amount , customer id, customer name , entered amount , debit id
    //Created on 25/01/2022
    //Return - NULL
    public void dattee(final String dateee, Integer collection_amount, final String savn, final String nam, final String camount, final String deid) {
        if (collection_amount == null) {
            collection_amount =0;
        }
        final Integer finalCollection_amount = collection_amount;
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                String todate = df.format(c.getTime());

                formattedDate = dateee;
                Log.d("drted",formattedDate);

                Log.d("cololol",String.valueOf(finalCollection_amount)+" "+camount+" "+savn+" "+nam);
                // Stuff that updates the UI
                if( finalCollection_amount == -1){
                    Log.d("cololol1",String.valueOf(finalCollection_amount)+" "+camount+" "+savn+" "+nam);
                    SQLiteHelper sqlite = new SQLiteHelper(getApplicationContext());
                    SQLiteDatabase database = sqlite.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("customer_id", savn);
                    values.put("customer_name", nam);
                    values.put("collection_amount", camount);
                    values.put("other_fee", "0");
                    values.put("discount", "0");
                    values.put("collection_date", dated1);
                    values.put("debit_id",deid);
                    values.put("created_date", todate);
                    database.insert("dd_collection", null, values);
                    Calendar cc = Calendar.getInstance();
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
                    String tod = sdf1.format(cc.getTime());
                    ((Callback)getApplication()).closed1(tod, String.valueOf(ses));
                    ((Callback)getApplication()).beforebal(tod, String.valueOf(ses));
                    ((Callback)getApplication()).populateRecyclerView23(tod, tod ,String.valueOf(ses));
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    balat = pref.getString("totalbal","");
                    totbal.setText("\u20B9"+" "+balat);
//            mAdapter.notifyDataSetChanged();
                    sqlite.close();
                    database.close();
                }
                else {
                    Log.d("cololol2",String.valueOf(finalCollection_amount)+" "+camount+" "+savn+" "+nam);
                    SQLiteHelper sqlite = new SQLiteHelper(getApplicationContext());
                    SQLiteDatabase database = sqlite.getWritableDatabase();
                    ContentValues cv1 = new ContentValues();
                    cv1.put("collection_amount",camount);
                    database.update("dd_collection", cv1, "customer_id=? AND collection_date=? ", new String[]{savn,dated1});
                    Calendar cc = Calendar.getInstance();
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
                    String tod = sdf1.format(cc.getTime());
                    ((Callback)getApplication()).closed1(tod, String.valueOf(ses));
                    ((Callback)getApplication()).beforebal(tod, String.valueOf(ses));
                    ((Callback)getApplication()).populateRecyclerView23(tod, tod ,String.valueOf(ses));
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    balat = pref.getString("totalbal","");
                    totbal.setText("\u20B9"+" "+balat);
                    sqlite.close();
                    database.close();
//list();
//mAdapter.notifyDataSetChanged();
                }
            }
        });


    }
    //    public void onBackPressed() {
//        if (!shouldAllowBack()) {
//
//          } else {
//            super.onBackPressed();
//        }
//        Intent nn = new Intent(Bulkupdate.this,NavigationActivity.class);
//        startActivity(nn);
//    }
    //onBackPressed() - function called when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alertbox = new AlertDialog.Builder(Bulkupdate.this,R.style.AlertDialogTheme);
        String logmsg = getString(R.string.bulupdate_alert);
        String warr = getString(R.string.warning);
        String logo = getString(R.string.confirm);
        alertbox.setMessage(logmsg);
        alertbox.setTitle(warr);
        alertbox.setIcon(R.drawable.dailylogo);
        alertbox.setPositiveButton(logo,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0,
                                        int arg1) {

//                       list();
//                       final Dialog dialog = new Dialog(Bulkupdate.this);
//                       //set layout custom
//                       dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                       dialog.setContentView(R.layout.progressbar);
//                       WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                       lp.copyFrom(dialog.getWindow().getAttributes());
//                       lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                       lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                       lp.gravity = Gravity.CENTER;
//                       dialog.setCancelable(false);
//                       dialog.getWindow().setAttributes(lp);
//                       dialog.show();

                        mAdapter.updatt(dated1);
//                       securedConnection.setEnabled(true);
                        backk =1;
//                       new Timer().schedule(new TimerTask() {
//                           @Override
//                           public void run() {
//                               // this code will be executed after 2 seconds
//                               dialog.dismiss();
//
//                               Intent nn = new Intent(Bulkupdate.this,AllCollection.class);
//                               startActivity(nn);
//                               finish();
//                           }
//                       }, 3000);


                    }
                });
        alertbox.show();
    }



    //progressbar_load() - Load progressbar
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void progressbar_load(){
        //set layout custom
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.progressbar);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.setCancelable(false);
        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }

    //progressbar_load2() - Load progressbar
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void progressbar_load3(){
        //set layout custom
        dialog = new Dialog(Bulkupdate.this,R.style.AlertDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progressbar);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.setCancelable(false);
        dialog.getWindow().setAttributes(lp);
        dialog.show();


    }

    //progressbar_load1() - Load progressbar
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void progressbar_load1(){
        //set layout custom
        dialog.setContentView(R.layout.progressbar);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.setCancelable(false);
        dialog.getWindow().setAttributes(lp);
//        dialog.show();

    }

    //progressbar_stop() - Stop progressbar
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void progressbar_stop(){
        dialog.dismiss();
        Intent nn = new Intent(Bulkupdate.this,AllCollection.class);
        nn.setFlags(FLAG_ACTIVITY_NEW_TASK);
        startActivity(nn);
        finish();
    }

    //progressbar_stop1() - Stop progressbar
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void progressbar_stop1(){
        dialog.dismiss();
        list2();
//        Intent nn = new Intent(Bulkupdate.this,AllCollection.class);
//        nn.setFlags(FLAG_ACTIVITY_NEW_TASK);
//        startActivity(nn);
//        finish();
    }

    //progressbar_stop2() - Stop progressbar
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void progressbar_stop2(){
        Log.d("p_load","no");
        dialog.dismiss();
//        Intent nn = new Intent(Bulkupdate.this,AllCollection.class);
//        startActivity(nn);
//        finish();
    }
}
