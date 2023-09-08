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
import android.os.Build;
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
import android.view.Gravity;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;



//Updated on 25/01/2022 by RAMPIT
//Close an days Tally
//InternetConnection1() - Check whether internet is on or not
//progressbar_load() - Load progressbar
//progre() - Stop progressbar
//updateLabel() - Update textview when date is selected
//chckdate() - Check whether date does not exist two days
//getDateDiff() - get days difference between two dates
//calculate() - Calculate tally amount
//list1() - get total collection
//sumcollection() - get total high and low
//sumcollection1() - get total account amount
//auto() - update amount automatically
//zero() - insert tally
//zero1() - get and set tally
//check() - get tally
//onBackPressed() - function called when back button is pressed
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed
//beforebal() - get before balance
//populateRecyclerView23() - get total debits all datas
//onNavigationItemSelected() - when navigation item pressed


public class Tally_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText twot,fivehu,hond,fif,twen,twoh,tn,fiv,cus1,cus2,datt,onet;
    TextView twt,tw,fv,hu,fi,twe,te,fii,cu,tott,dateee,session,msssg,colt,hit,lot,tobal,save,oneto;
    Integer twwww,onttt,fvvvv,twhu,onhu,fuf,twee,tonn,fiyy,cust1,cust2,totol,pooop,ses,tot21,hig21,low21,hig211,low211,id1,id2,bal21,cloosed,befo;
    String twotho,onetho,twohu,fivehun,hunder,fufty,twer,ton,fiy,c1,c2,todate,paidamount,paidamount1,paidamount2,alertss,alertss1,did,timing,formattedDate;
    Button cll,reset;
    String ss,zero,one,two,three,four,five,six,seven,eight,nine,ten,adpr,edpr,depr,vipr,id;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    public static String TABLENAME = "dd_accounting";
    NavigationView navigationView;
    ScrollView sc1,sc2;
    SharedPreferences.Editor editor;
    Menu nav_Menu;
    LinearLayout lll;
    Button logou,profil;
    Calendar myCalendar;
    ImageButton clos;
    ImageView seesim;
    String datead,dated1,datedd,xcol;
    DatePickerDialog datePickerDialog;
    Integer dabamo;
    Dialog dialog ;
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
        befo = 0;
        dabamo = 0;
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dialog = new Dialog(Tally_activity.this,R.style.AlertDialogTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_tally_activity);
        setTitle(R.string.title_activity_tally_activity);
        ((Callback)getApplication()).datee();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
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
        tot21 = 0;
        bal21 = 0;
        hig21 = 0;
        low21 = 0;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        reset = findViewById(R.id.reset);
        tobal = findViewById(R.id.totbal);
        editor = pref.edit();
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
        datt = (EditText) findViewById(R.id.search1);
        save = findViewById(R.id.save);
        cll = findViewById(R.id.close);
        lll = (findViewById(R.id.linee));
        TextView pro = lll.findViewById(R.id.pro);
        logou = lll.findViewById(R.id.logout);
        profil = lll.findViewById(R.id.profile);
        cll.setEnabled(false);
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),Tally_activity.this)) {
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            cll.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.spin3));
        }
        else{
            cll.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.spin3));
        }
        Calendar c = Calendar.getInstance();
        seesim = findViewById(R.id.sesimg);
        clos = findViewById(R.id.closedd);
        hit = findViewById(R.id.high);
        lot = findViewById(R.id.low);
        totol = 0;
        colt = findViewById(R.id.totcol);
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        datead = pref.getString("tallydate","");

        if(datead.equalsIgnoreCase("")){
            formattedDate = df.format(c.getTime());
            datedd =df1.format(c.getTime());

            Log.d("po90",formattedDate);
//            closed12(formattedDate,getApplicationContext());
//            beforebal(formattedDate,getApplicationContext());
//            populateRecyclerView23(formattedDate,formattedDate,getApplicationContext());

        }else{
//            formattedDate = datead;
            dated1 = datead;
            try {
                Date debit = df.parse(dated1);
                formattedDate = df.format(debit);
                datedd = df1.format(debit);
                Log.d("po901",formattedDate);
//                closed12(formattedDate,getApplicationContext());
//                beforebal(formattedDate,getApplicationContext());
//                populateRecyclerView23(formattedDate,formattedDate,getApplicationContext());
                Log.d("deae33",dated1);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            datt.setText(datedd);
        }
        todate = datedd;
//        todate = df.format((c.getTime()));
//        String forrrddd = df1.format(c.getTime());

//        balat = pref.getString("totalbal", "");
//        totbal.setText("\u20B9"+""+balat);
        datt.setText(datedd);
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
                Intent i = new Intent(Tally_activity.this,Settings_activity.class);
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
                    Intent i = new Intent(Tally_activity.this,NavigationActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    editor.putString("theme","0");
                    editor.apply();
                    Intent i = new Intent(Tally_activity.this,NavigationActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        });
        logou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Tally_activity.this,R.style.AlertDialogTheme);
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
                                Intent i = new Intent(Tally_activity.this,Splash.class);
                                startActivity(i);
                                finish();
                            }
                        });
                alertbox.show();
            }
        });
        ses = pref.getInt("session", 1);
        String dd = pref.getString("Date","");
//        ((Callback)getApplication()).closed(ses);
//        ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
//        ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
//        ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
                        ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
                        ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));

                        ((Callback)getApplication()).closed(ses);
                        list1();
                        sumcollection1();
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
        dateee = findViewById(R.id.da);
        session = findViewById(R.id.sess);
        dateee.setText(dd);
        try {
            Date debit = df1.parse(dd);
//            todate = df.format(debit);
            Log.d("deae",todate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
//        sqlite = new SQLiteHelper(this);
        myCalendar = Calendar.getInstance();

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
        datt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (in == 0){
                    datePickerDialog =  new DatePickerDialog(Tally_activity.this,R.style.DialogTheme, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH ));
                }else{
                    datePickerDialog =  new DatePickerDialog(Tally_activity.this,R.style.DialogTheme1, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH ));
                }
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
                String myFormat = "yyyy/MM/dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//                dateString = sdf.format(myCalendar.getTime());
                String myFormat1 = "dd/MM/yyyy"; //In which you need put here
//                mAdapter.filter(dateString);
                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                datt.setText(sdf1.format(myCalendar.getTime()));
//                sumcollection1();
            }
        });
        clos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
                formattedDate = df.format(c.getTime());
                todate = df1.format(c.getTime());
                String forrrddd = df1.format(c.getTime());
                datt.setText(forrrddd);

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                list1();
                                sumcollection();
                                sumcollection1();
                                calculate();
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
        if(in == 0){
            if(ses == 1){
                timing = getString(R.string.morning);
                seesim.setBackgroundResource(R.drawable.sun);
            }else if(ses == 2){
                timing = getString(R.string.evening);
                seesim.setBackgroundResource(R.drawable.moon);
            }
        }
        else{
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
//        list1();
//        sumcollection1();
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
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Tally_activity.this,R.style.AlertDialogTheme);
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
                                Intent i = new Intent(Tally_activity.this,Splash.class);
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
                Intent qq = new Intent(Tally_activity.this,NavigationActivity.class);
                startActivity(qq);
                finish();
            }
        });
        nn.setText(dsdsd);
        nnn.setText("Date :" + " " + dd);
        nnnn.setText("Session :" + " " + ss);
//        todate = df.format(c.getTime());
        twot = findViewById(R.id.two);
        onet = findViewById(R.id.one);
        fivehu = findViewById(R.id.five);
        hond = findViewById(R.id.hundred);
        twoh = findViewById(R.id.twohundred);
        fif = findViewById(R.id.fifty);
        twen = findViewById(R.id.twenty);
        tn = findViewById(R.id.ten);
        msssg = findViewById(R.id.message);
        fiv = findViewById(R.id.fiver);
        cus1 = findViewById(R.id.cust);
        cus2 = findViewById(R.id.cuuussstom);
        tott = findViewById(R.id.total);

        twt = findViewById(R.id.twotto);
        oneto = findViewById(R.id.onetto);
        tw = findViewById(R.id.twohto);
        fv = findViewById(R.id.fivehto);
        hu = findViewById(R.id.onehto);
        fi = findViewById(R.id.fifto);
        twe = findViewById(R.id.twento);
        te = findViewById(R.id.tento);
        fii = findViewById(R.id.fivto);
        cu = findViewById(R.id.custoo);
        sc1 = findViewById(R.id.scroll1);
        sc2 = findViewById(R.id.scroll2);
        check();
//        zero1();
        String sesid = pref.getString("id","");
        String module_i = "9";
        ((Callback)getApplication()).privilege(sesid,module_i);
        adpr = pref.getString("add_privilege","");
        edpr = pref.getString("edit_privilege","");
        depr = pref.getString("delete_privilege","");
        vipr = pref.getString("view_privilege","");
        String balat = pref.getString("totalbal","0");
        Calendar mk = Calendar.getInstance();
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        final String date211 = sdf.format(mk.getTime());

//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ((Callback)getApplication()).closed1(date211, String.valueOf(ses));
//        ((Callback)getApplication()).beforebal(date211, String.valueOf(ses));
//        ((Callback)getApplication()).populateRecyclerView23(date211, date211 ,String.valueOf(ses));
//                    }
//                });
//            }
//        }, 500);

//        bal21 = Integer.parseInt(balat);
//        tobal.setText("\u20B9"+" "+balat);
        if(edpr.equalsIgnoreCase("0")){
            sc1.setVisibility(View.VISIBLE);
            sc2.setVisibility(View.GONE);
        }
        totol = 0;
        twot.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
//                    InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                    Toast.makeText(getApplicationContext(), String.valueOf(interr), Toast.LENGTH_SHORT).show();
//                    calclate();
                    String nn = twot.getText().toString();
                    if(nn.equalsIgnoreCase("0")){
                        twot.setSelection(1);
                    }
                }
            }
        });
        twot.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                twot.setSelection(0);
                String nn = twot.getText().toString();
                if(nn.equalsIgnoreCase("0")){
                    twot.setSelection(1);
                }
                if (s.length() > 0) { //do your work here }
                    twotho = twot.getText().toString();
                    twwww = 2000 * Integer.parseInt(twotho);
                    Log.d("twohund", String.valueOf(twwww));
                    twt.setText(String.valueOf(twwww));
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            public void afterTextChanged(Editable s) {
                twotho = twot.getText().toString();
                if (twotho.trim().equals("")) {
                    twt.setText("0");
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("two_thousand", "0");
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    sqlite.close();
                    database.close();
                    calculate();
                } else {
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("two_thousand", twotho);
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    sqlite.close();
                    database.close();
                    calculate();
                }
            }
        });
        onet.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                twot.setSelection(0);
                String nn = onet.getText().toString();
                if(nn.equalsIgnoreCase("0")){
                    onet.setSelection(1);
                }
                if (s.length() > 0) { //do your work here }
                    onetho = onet.getText().toString();
                    onttt = 1000 * Integer.parseInt(onetho);
                    Log.d("twohund", String.valueOf(twwww));
                    oneto.setText(String.valueOf(onttt));
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            public void afterTextChanged(Editable s) {
                onetho = onet.getText().toString();
                if (onetho.trim().equals("")) {
                    onet.setText("0");
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("one_thousand", "0");
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    sqlite.close();
                    database.close();
                    calculate();
                } else {
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("one_thousand", onetho);
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    sqlite.close();
                    database.close();
                    calculate();
                }
            }
        });
        onet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String nn = onet.getText().toString();
                    if(nn.equalsIgnoreCase("0")){
                        onet.setSelection(1);
                    } } }});
        fivehu.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                onet.setSelection(0);
                String nn = onet.getText().toString();
                if(nn.equalsIgnoreCase("0")){
                    onet.setSelection(1);
                }
                if (s.length() > 0) { //do your work here }
                    onetho = onet.getText().toString();
                    onttt = 1000 * Integer.parseInt(onetho);
                    Log.d("onehund", String.valueOf(onttt));
                    oneto.setText(String.valueOf(onttt));
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            public void afterTextChanged(Editable s) {
                onetho = onet.getText().toString();
                if (onetho.trim().equals("")) {
                    oneto.setText("0");
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("one_thousand", "0");
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    calculate();
                    sqlite.close();
                    database.close();
                } else {
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("one_thousand", onetho);
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    calculate();
                    sqlite.close();
                    database.close();
                }
            }
        });
        fivehu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String nn = fivehu.getText().toString();
                    if(nn.equalsIgnoreCase("0")){
                        fivehu.setSelection(1);
                    } } }});
        fivehu.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0) { //do your work here }
                    fivehun = fivehu.getText().toString();
                    fvvvv = 500 * Integer.parseInt(fivehun);
//                    Log.d("twohund",String.valueOf(twhu));
                    fv.setText(String.valueOf(fvvvv));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
                fivehun = fivehu.getText().toString();
                if (fivehun.trim().equals("")) {
                    fv.setText("0");
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("five_hundred", "0");
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    calculate();
                    sqlite.close();
                    database.close();
                } else {
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("five_hundred", fivehun);
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    calculate();
                    sqlite.close();
                    database.close();
                }
            }
        });
        twoh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String nn = twoh.getText().toString();
                    if(nn.equalsIgnoreCase("0")){
                        twoh.setSelection(1);
                    } } }});
        twoh.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0) { //do your work here }
                    twohu = twoh.getText().toString();
                    twhu = 200 * Integer.parseInt(twohu);
                    tw.setText(String.valueOf(twhu));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
                twohu = twoh.getText().toString();
                if (twohu.trim().equals("")) {
                    tw.setText("0");
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("two_hundred", "0");
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    calculate();
                    sqlite.close();
                    database.close();
                } else {
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("two_hundred", twohu);
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    calculate();
                    sqlite.close();
                    database.close();
                }

            }
        });
        hond.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String nn = hond.getText().toString();
                    if(nn.equalsIgnoreCase("0")){
                        hond.setSelection(1);
                    } } }});
        hond.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0) { //do your work here }
                    hunder = hond.getText().toString();
                    onhu = 100 * Integer.parseInt(hunder);
                    hu.setText(String.valueOf(onhu));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
                hunder = hond.getText().toString();
                if (hunder.trim().equals("")) {
                    hu.setText("0");
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("one_hundred", "0");
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    calculate();
                    sqlite.close();
                    database.close();
                } else {
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("one_hundred", hunder);
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    calculate();
                    sqlite.close();
                    database.close();
                }
            }
        });
        fif.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String nn = fif.getText().toString();
                    if(nn.equalsIgnoreCase("0")){
                        fif.setSelection(1);
                    } } }});
        fif.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0) { //do your work here }
                    fufty = fif.getText().toString();
                    fuf = 50 * Integer.parseInt(fufty);
                    fi.setText(String.valueOf(fuf));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
                fufty = fif.getText().toString();
                if (fufty.trim().equals("")) {
                    fi.setText("0");
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("fifty", "0");
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    calculate();
                    sqlite.close();
                    database.close();
                } else {
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("fifty", fufty);
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    calculate();
                    sqlite.close();
                    database.close();
                }
            }
        });
        twen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String nn = twen.getText().toString();
                    if(nn.equalsIgnoreCase("0")){
                        twen.setSelection(1);
                    } } }});
        twen.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0) { //do your work here }
                    twer = twen.getText().toString();
                    twee = 20 * Integer.parseInt(twer);
                    twe.setText(String.valueOf(twee));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
                twer = twen.getText().toString();
                if (twer.trim().equals("")) {
                    twe.setText("0");
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("twenty", "0");
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    calculate();
                    sqlite.close();
                    database.close();
                } else {
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("twenty", twer);
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    calculate();
                    sqlite.close();
                    database.close();
                }

            }
        });
        tn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String nn = tn.getText().toString();
                    if(nn.equalsIgnoreCase("0")){
                        tn.setSelection(1);
                    } } }});
        tn.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0) { //do your work here }
                    ton = tn.getText().toString();
                    tonn = 10 * Integer.parseInt(ton);
                    te.setText(String.valueOf(tonn));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
                ton = tn.getText().toString();
                if (ton.trim().equals("")) {
                    te.setText("0");
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("ten", "0");
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    calculate();
                    sqlite.close();
                    database.close();
                } else {
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("ten", ton);
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    calculate();
                    sqlite.close();
                    database.close();
                }
            }
        });
        fiv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String nn = fiv.getText().toString();
                    if(nn.equalsIgnoreCase("0")){
                        fiv.setSelection(1);
                    } } }});
        fiv.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0) { //do your work here }
                    fiy = fiv.getText().toString();
                    fiyy = 5 * Integer.parseInt(fiy);
                    fii.setText(String.valueOf(fiyy));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
                fiy = fiv.getText().toString();
                if (fiy.trim().equals("")) {
                    fii.setText("0");
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("five", "0");
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    calculate();
                    sqlite.close();
                    database.close();
                } else {
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid1",id);
                    Log.d("iid12",fiy);
                    ContentValues values = new ContentValues();
                    values.put("five", fiy);
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    calculate();
                    sqlite.close();
                    database.close();
                }
            }
        });
        cus2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String nn = cus2.getText().toString();
                    if(nn.equalsIgnoreCase("0")){
                        cus2.setSelection(1);
                    } } }});
        cus2.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0) { //do your work here }
                    c2 = cus2.getText().toString();
                    cust2 = Integer.parseInt(c2);
                    if (c1 != null && c2 != null) {
                        cust2 = cust1 * Integer.parseInt(c2);
                        cu.setText(String.valueOf(cust2));
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
                c2 = cus2.getText().toString();
                if (c2.trim().equals("")) {
                    cu.setText("0");
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("custom1", "0");
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    calculate();
                    sqlite.close();
                    database.close();
                } else {
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("custom1", c2);
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    calculate();
                    sqlite.close();
                    database.close();
                }
            }
        });
        cus1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String nn = cus1.getText().toString();
                    if(nn.equalsIgnoreCase("0")){
                        cus1.setSelection(1);
                    } } }});
        cus1.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0) { //do your work here }
                    c1 = cus1.getText().toString();
                    cust1 = Integer.parseInt(c1);
                    if (c1 != null && c2 != null) {
                        cust1 = cust2 * Integer.parseInt(c1);
                        cu.setText(String.valueOf(cust1));
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
                c1 = cus1.getText().toString();
                if (c1.trim().equals("")) {
                    cu.setText("0");
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("custom2", "0");
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    calculate();
                    sqlite.close();
                    database.close();
                } else {
                    sqlite = new SQLiteHelper(Tally_activity.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("iid",id);
                    ContentValues values = new ContentValues();
                    values.put("custom2", c1);
                    values.put("tracking_id", ses);
                    values.put("updated_date", formattedDate);
                    database.update("dd_tally", values,"id = ? AND created_date = ?", new String[]{id,formattedDate});
                    calculate();
                    sqlite.close();
                    database.close();
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cll.setEnabled(true);
                cll.setBackgroundResource(R.drawable.login_selector);
            }
        });
        cll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                list1();
                                sumcollection();
                                sumcollection1();
                                calculate();
                                String logmsg = getString(R.string.auto);
                                String cann = getString(R.string.ok);
                                String warr = getString(R.string.warning);
                                String logo = getString(R.string.manual);
                                AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext(),R.style.AlertDialogTheme);
                                alertbox.setMessage(alertss);
                                msssg.setText(alertss1);
                                alertbox.setTitle(warr);
                                Log.d("tott", String.valueOf(totol));

                                alertbox.setIcon(R.drawable.dailylogo);
                                Integer pd = Integer.parseInt(paidamount);
                                Log.d("tott", String.valueOf(pd));
                                if (bal21.equals(totol)) {
                                    alertbox.setNeutralButton(cann,
                                            new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface arg0,
                                                                    int arg1) {

                                                }
                                            });
                                }
                                else {
                                    alertbox.setNeutralButton(logmsg,
                                            new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface arg0,
                                                                    int arg1) {
                                                    msssg.setVisibility(View.VISIBLE);
                                                    cll.setClickable(false);
                                                    auto();
                                                }
                                            });
                                    alertbox.setPositiveButton(logo,
                                            new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface arg0,
                                                                    int arg1) {
                                                    cll.setClickable(false);
                                                    msssg.setVisibility(View.VISIBLE);
                                                    Log.d("actya", todate);
                                                    Intent acc = new Intent(Tally_activity.this, Bulkupdate.class);
//                                    acc.putExtra("amount", String.valueOf(pooop));
//                                    acc.putExtra("id", did);
                                                    editor.putString("buldate",formattedDate);
                                                    editor.apply();
                                                    startActivity(acc);
                                                    finish();
                                                }
                                            });
                                }
                                alertbox.show();
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
//        zero();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String logmsg = getString(R.string.resetalert);
                String cann = getString(R.string.ok);
                String warr = getString(R.string.warning);
                String logo = getString(R.string.cancel);
                AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext(),R.style.AlertDialogTheme);
                alertbox.setMessage(logmsg);
//                msssg.setText(alertss1);
                alertbox.setTitle(warr);
                Log.d("tott", String.valueOf(totol));
                alertbox.setIcon(R.drawable.dailylogo);
                    alertbox.setNeutralButton(cann,
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                    twot.setText("0");
                                    oneto.setText("0");
                                    fivehu.setText("0");
                                    hond.setText("0");
                                    fif.setText("0");
                                    twen.setText("0");
                                    twoh.setText("0");
                                    tn.setText("0");
                                    fiv.setText("0");
                                    cus1.setText("0");
                                    cus2.setText("0");
//                datt.setText("0");
                                    calculate();
                                }
                            });
                    alertbox.setPositiveButton(logo,
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0,
                                                    int arg1) {

                                }
                            });

                alertbox.show();

            }
        });
        calculate();
    }
    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Tally_activity account) {
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

    //updateLabel() - Update textview when date is selected
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";
        String mmm = "dd/MM/yyyy";//In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(mmm, Locale.US);
//        dateString = sdf.format(myCalendar.getTime());
        formattedDate = sdf.format(myCalendar.getTime());
        todate = sdf1.format(myCalendar.getTime());
        datt.setText(sdf1.format(myCalendar.getTime()));
        chckdate();
        twot.setText("0");
        oneto.setText("0");
        fivehu.setText("0");
        hond.setText("0");
        fif.setText("0");
        twen.setText("0");
        twoh.setText("0");
        tn.setText("0");
        fiv.setText("0");
        cus1.setText("0");
        cus2.setText("0");
//        datt.setText("");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list1();
                        sumcollection();
                        sumcollection1();
                        calculate();
                        check();
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


//        mAdapter.filter(dateString);
        Log.d("hjyj","uyuy");
    }

    //chckdate() - Check whether date does not exist two days
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void chckdate() {
        Calendar tod = Calendar.getInstance();
        String myFormat = "yyyy/MM/dd";
        String mmm = "dd/MM/yyyy";//In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(mmm, Locale.US);
        String todaydate = sdf.format(tod.getTime());
        int dateDifference = (int) getDateDiff(sdf,formattedDate,todaydate);
        Log.d("tallydatediffer",String.valueOf(dateDifference));
        if(dateDifference >= 2){
            save.setVisibility(View.INVISIBLE);
            cll.setVisibility(View.INVISIBLE);
            reset.setVisibility(View.INVISIBLE);
        }

    }

    //getDateDiff() - get days difference between two dates
    //Params - date format , start and end date
    //Created on 25/01/2022
    //Return - Days
    public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //calculate() - Calculate tally amount
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void calculate() {
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String balat = pref.getString("totalbal","0");
        bal21 = Integer.parseInt(balat);
        Integer twothous = Integer.parseInt(twt.getText().toString());
        Integer onethous = Integer.parseInt(oneto.getText().toString());
        Integer fivehunde = Integer.parseInt(fv.getText().toString());
        Integer onehund = Integer.parseInt(hu.getText().toString());
        Integer twohund = Integer.parseInt(tw.getText().toString());
        Integer fifffty = Integer.parseInt(fi.getText().toString());
        Integer tuwenty = Integer.parseInt(twe.getText().toString());
        Integer then = Integer.parseInt(te.getText().toString());
        Integer fyve = Integer.parseInt(fii.getText().toString());
        Integer kast = Integer.parseInt(cu.getText().toString());
        totol = twothous+onethous+fivehunde+onehund+twohund+fifffty+tuwenty+then+fyve+kast;
//        sumcollection();
//        sumcollection1();
        tott.setText("\u20B9"+" "+String.valueOf(totol));
        Log.d("hh21",String.valueOf(totol));
        Log.d("hh22",String.valueOf(bal21));
        if(bal21 > totol){
            Integer hh = 0;
            hh = bal21 - totol ;
//       hh = hh + low21;
            Log.d("hh",String.valueOf(low21));
            Log.d("hh",String.valueOf(bal21));
            lot.setText("\u20B9" + " " + String.valueOf(hh));
            hit.setText("\u20B9" + " " + "0");
        }else if (bal21 < totol){
            Integer hh = 0;
//            bal21 = Math.abs(bal21);
            hh =   totol - bal21 ;
//       hh = hh + hig21;
            Log.d("hh",String.valueOf(hig21));
            Log.d("hh",String.valueOf(bal21));

            hit.setText("\u20B9" + " " + String.valueOf(hh));
            lot.setText("\u20B9" + " " + "0");
        }else if(bal21.equals(totol)){
            hit.setText("\u20B9" + " " + "0");
            lot.setText("\u20B9" + " " + "0");
        }
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                progre();
            }
        });
    }

    //list1() - get total collection
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void list1(){
        dabamo = 0;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        String mm = "SELECT a.customer_name,a.id as ID,a.location,a.phone_1,a.debit_type_updated,b.*,c.vv as collect,c.debit_id " +
                " ,a.debit_type FROM dd_customers  a " +
                "LEFT JOIN dd_account_debit b on b.customer_id = a.id "  +
                "LEFT JOIN (SELECT SUM(collection_amount) as vv,debit_id,collection_date from dd_collection WHERE collection_date = ? GROUP BY debit_id ORDER BY debit_id  ) c ON  b.id = c.debit_id  WHERE a.tracking_id = ? AND  b.active_status = 0 AND c.collection_date = ?  GROUP BY b.id ORDER BY b.id";
        String MY_QUERY = "SELECT b.*,a.customer_name,a.id as ID,a.location,a.phone_1,sum(c.collection_amount) as collect,a.debit_type_updated FROM dd_account_debit b LEFT JOIN dd_customers a on  a.id = b.customer_id  " +
                " Left JOIN (SELECT collection_amount,collection_date,customer_id,debit_id,discount from dd_collection GROUP BY debit_id ) c on c.debit_id = b.id  WHERE  a.tracking_id = ?  AND b.active_status = 0 GROUP BY b.id ";
//   String mm = "SELECT b.*,sum(c.co)";
        Cursor cursor = database.rawQuery(mm, new String[]{formattedDate,String.valueOf(ses),formattedDate});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("collect");
                    xcol = cursor.getString(index);
                    String vc = cursor.getString(index);
                    Log.d("ins3",vc);
                    if(vc == null ){
                        vc = "0";
                    }
                    Integer d1 = Integer.parseInt(vc);
                    dabamo = dabamo + d1;
                    Log.d("uuii",String.valueOf(dabamo));
//                    clobal.setText("\u20B9"+""+String.valueOf(dabamo));
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
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }

    }

    //sumcollection() - get total high and low
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void sumcollection(){
        editor.putString("tallydate",formattedDate);
        editor.apply();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        String SUM_QUERY = "SELECT sum(a.collection_amount) as collect, +" +
                "(SELECT  sum(amount) FROM dd_accounting  WHERE acc_type_id =? AND accounting_date =? AND tracking_id = ?) as ddebits, " +
                "(SELECT   sum(amount) FROM dd_accounting WHERE acc_type_id =? AND accounting_date =? AND tracking_id = ? ) as dcredits, " +
                "(SELECT  id FROM dd_accounting  WHERE acc_type_id =? AND accounting_date =? AND tracking_id = ?) as ddebits1, " +
                "(SELECT   id FROM dd_accounting WHERE acc_type_id =? AND accounting_date =? AND tracking_id = ? ) as dcredits1 " +
                "FROM dd_customers c LEFT JOIN  dd_account_debit b on b.customer_id = c.id LEFT JOIN dd_collection a on a.debit_id = b.id   WHERE a.collection_date = ? AND c.tracking_id = ? ";
        Cursor cur = database.rawQuery(SUM_QUERY,new String[]{"19",formattedDate,String.valueOf(ses),"8",
                formattedDate,String.valueOf(ses),"19",formattedDate,String.valueOf(ses),"8",formattedDate,
                String.valueOf(ses),formattedDate, String.valueOf(ses)});
        if (cur != null) {
            if (cur.getCount() != 0) {
                cur.moveToFirst();
                do {
                    int index;
//                    CASE amount WHEN SUM(amount)  IS NULL THEN amount = '1' ELSE amount  END
                    index = cur.getColumnIndexOrThrow("dcredits");
                    Integer od = cur.getInt(index);
                    index = cur.getColumnIndexOrThrow("ddebits");
                    Integer odd = cur.getInt(index);
                    index = cur.getColumnIndexOrThrow("dcredits1");
                    id1 = cur.getInt(index);
                    index = cur.getColumnIndexOrThrow("ddebits1");
                    id2 = cur.getInt(index);
                    index = cur.getColumnIndexOrThrow("collect");
                    Integer oddd = cur.getInt(index);
                    if(od != null && odd != null && oddd != null){
                        Integer odo = (oddd + od) - (odd);
                        Integer bh = od - odd ;
                        paidamount = String.valueOf(odo);
                        paidamount1 = String.valueOf(bh);
                        paidamount2 = String.valueOf(oddd);
                    }
                    hig21 = od;
                    low21 = odd;
                    hig211 = od;
                    low211 = odd;
                    Integer bh = od - odd ;
                    paidamount1 = String.valueOf(bh);
                    paidamount2 = String.valueOf(oddd);
                    colt.setText("\u20B9" + " " + String.valueOf(oddd));
                    Log.d("debittt112123",String.valueOf(oddd));
                    tot21 = oddd;
//                    Log.d("debittt",String.valueOf(odd)+" "+String.valueOf(oddd));
                    Log.d("debittt11",paidamount);
//                    paidamount = String.valueOf(od);
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
        Integer pd = Integer.parseInt(paidamount);
        Integer pd1 = Integer.parseInt(paidamount1);
        Integer pp = pd +totol ;
        Integer ppd = Integer.parseInt(paidamount2);
        Log.d("iopoikop",String.valueOf(pd)+" "+String.valueOf(pd1)+" "+String.valueOf(totol));
        if(bal21.equals(totol)){
            alertss = getString(R.string.equals_tally);
            alertss1 = getString(R.string.equals_tally);
//            if(ses == 1){
//            editor.putInt("session",2);
//            editor.commit();}
//            else if (ses == 2){
//                editor.putInt("session",1);
//                editor.commit();
//        }
            msssg.setVisibility(View.VISIBLE);
            msssg.setText(alertss1);
            Log.d("totol","equals");
        } else if(ppd.equals(totol) && bal21 > 0){
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
            Log.d("io2121","opop");
            String table = "dd_accounting";
            String whereClause = "acc_type_id =? AND accounting_date =? AND tracking_id = ?";
            String[] whereArgs = new String[] {"8",formattedDate,String.valueOf(ses)};
            ContentValues vv = new ContentValues();
            vv.put("amount","0");
            database.update(table,vv,whereClause,whereArgs);
            sqlite.close();
            database.close();
//            database.delete(table, whereClause, whereArgs);
            alertss = getString(R.string.equals_tally);
            alertss1 = getString(R.string.equals_tally);
            msssg.setVisibility(View.VISIBLE);
            msssg.setText(alertss1);
            Log.d("totol","equals");
        }else if(ppd.equals(totol) && bal21 < 0){
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
            Log.d("io3232","opop");
            String table = "dd_accounting";
            String whereClause = "acc_type_id =? AND accounting_date =? AND tracking_id = ?";
            String[] whereArgs = new String[] {"19",formattedDate,String.valueOf(ses)};
            ContentValues vv = new ContentValues();
            vv.put("amount","0");
            database.update(table,vv,whereClause,whereArgs);
            sqlite.close();
            database.close();
//            database.delete(table, whereClause, whereArgs);
            alertss = getString(R.string.equals_tally);
            alertss1 = getString(R.string.equals_tally);
////
            msssg.setVisibility(View.VISIBLE);
            msssg.setText(alertss1);
            Log.d("totol","equals");
        } else if(bal21 > totol){
//             pooop = pd - totol;
            pooop =  bal21 - totol;
            String low =  getString(R.string.low_amount);
            String added = getString(R.string.is_added);
            alertss1 = low+" "+"\u20B9"+pooop+" "+added;
            alertss = low+" "+"\u20B9"+pooop+" "+added;
            did = "19";
            Log.d("totol",String.valueOf(pooop));
        }else if(bal21 < totol){
            String high =  getString(R.string.high);
            String added = getString(R.string.is_added);
//             pooop = totol - pd;
            
            pooop =   totol - bal21;

            alertss1 = high+" "+"\u20B9"+pooop+" "+added;
            alertss = high+" "+"\u20B9"+pooop+" "+added;
            did = "8";
            Log.d("totol",String.valueOf(pooop));
        }
    }

    //sumcollection1() - get total account amount
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void sumcollection1(){
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        Log.d("daa",formattedDate);
        String SUM_QUERY = "SELECT sum(a.collection_amount) as collect, +" +
                "(SELECT  sum(amount) FROM dd_accounting  WHERE acc_type_id =? AND accounting_date =? AND tracking_id = ?) as ddebits, " +
                "(SELECT   sum(amount) FROM dd_accounting WHERE acc_type_id =? AND accounting_date =? AND tracking_id = ? ) as dcredits " +
                "FROM dd_customers c LEFT JOIN  dd_account_debit b on b.customer_id = c.id LEFT JOIN dd_collection a on a.debit_id = b.id   WHERE a.collection_date = ? AND c.debit_type IN (0,1,2) AND c.tracking_id = ? AND b.active_status = 1 ";
        Cursor cur = database.rawQuery(SUM_QUERY,new String[]{"19",formattedDate,String.valueOf(ses),"8",formattedDate,String.valueOf(ses),formattedDate, String.valueOf(ses)});
        if (cur != null) {
            if (cur.getCount() != 0) {
                cur.moveToFirst();
                do {
                    int index;
//                    CASE amount WHEN SUM(amount)  IS NULL THEN amount = '1' ELSE amount  END
                    index = cur.getColumnIndexOrThrow("dcredits");
                    Integer od = cur.getInt(index);
                    index = cur.getColumnIndexOrThrow("ddebits");
                    Integer odd = cur.getInt(index);

                    index = cur.getColumnIndexOrThrow("collect");
                    Integer oddd = cur.getInt(index);
                    Log.d("debittt112123aa12",String.valueOf(oddd));
                    oddd = oddd + dabamo;
//                    if(od != null && odd != null && oddd != null){
//                        Integer odo = (oddd + od) - odd;
//
//
//                        paidamount = String.valueOf(odo);
//                    }
                    Integer odo = (oddd + od) - odd;
//                    Integer ot = totol - odo;
//                    if(odd == 0 ) {
//                        lot.setText("\u20B9" + " " + "0");
//                    }else{
//                        lot.setText("\u20B9" + " " + String.valueOf(odd));
//                    }
//                    if(od == 0) {
//                        hit.setText("\u20B9" + " " + "0");
//                    }else{
//                        hit.setText("\u20B9" + " " + String.valueOf(od));
//                    }
                    if(oddd == 0){
                        colt.setText("\u20B9" + " " + String.valueOf(oddd));
                        Log.d("debittt112123aa",String.valueOf(oddd));
                        tot21 = oddd;
                    }else{
                        colt.setText("\u20B9" + " " + String.valueOf(oddd));
                    }
                    final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    String balat = pref.getString("totalbal","0");
                    bal21 = Integer.parseInt(balat);
                    tobal.setText("\u20B9"+" "+balat);
                    hig21 = od;
                    low21 = odd;
                    hig211 = od;
                    low211 = odd;
                    if(odd == 0 && od == 0){
                        lot.setText("\u20B9" + " " + "0");
                        hit.setText("\u20B9" + " " + "0");
                    }else {
//                        if (totol < odo) {
//                            Integer ot1 = odo - totol;
//                            lot.setText("\u20B9" + " " + String.valueOf(ot1));
//                            hit.setText("\u20B9" + " " + String.valueOf(od));
//                        } else if (totol > odo) {
//                            Integer ot1 = totol - odo;
                        lot.setText("\u20B9" + " " + "0");
                        hit.setText("\u20B9" + " " + "0");
//                        }
                        tot21 = oddd;
                        colt.setText("\u20B9" + " " + String.valueOf(oddd));
                        Log.d("debittt112123bb",String.valueOf(oddd));
                        hig21 = od;
                        low21 = odd;
                        hig211 = od;
                        low211 = odd;
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                progre();
                            }
                        });
                    }
                    Log.d("debittt",String.valueOf(odd)+" "+String.valueOf(oddd));
//                    Log.d("debittt11",paidamount);
//                    paidamount = String.valueOf(od);
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
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    progre();
                }
            });
            cur.close();
            sqlite.close();
            database.close();
        }
    }

    //auto() - update amount automatically
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void auto(){
        Integer pd = Integer.parseInt(paidamount);
        if(bal21 == totol){
            Log.d("totol","equals");
        }
        else if(bal21 > totol){
            if(id2.equals(0) &&  id1 > 0 ){
                sqlite = new SQLiteHelper(this);
                database = sqlite.getWritableDatabase();
                Integer pooop = pd - totol;
                pooop =  bal21 - totol ;
                pooop = pooop + low21 - hig21;
                ContentValues cv = new ContentValues();
                cv.put("acc_type_id", "19");
                cv.put("amount", String.valueOf(pooop));
                cv.put("accounting_date", formattedDate);
                cv.put("tracking_id",String.valueOf(ses));
                cv.put("created_date", formattedDate);
                database.insert(TABLENAME, null, cv);
                Log.d("totol",String.valueOf(pooop));
                ContentValues cv1 = new ContentValues();
                cv1.put("amount", "0");
                cv1.put("updated_date", formattedDate);
                database.update(TABLENAME,cv1,"acc_type_id =? AND accounting_date =? AND tracking_id = ?",new String[]{"8",formattedDate,String.valueOf(ses)});
                sqlite.close();
                database.close();
                ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
                ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
                ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
//                closed12(formattedDate,getApplicationContext());
//            beforebal(formattedDate,getApplicationContext());
//            populateRecyclerView23(formattedDate,formattedDate,getApplicationContext());
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent q1 = new Intent(Tally_activity.this,Tally_activity.class);
                                startActivity(q1);
                            }
                        });
                    }
                }, 1000);

            } else if(id2 > 0 && id1.equals(0) ){
                sqlite = new SQLiteHelper(this);
                database = sqlite.getWritableDatabase();
                Integer pooop = pd - totol;
                pooop =  bal21 - totol;
                pooop = pooop + low21 + hig21;
                ContentValues cv1 = new ContentValues();
                cv1.put("amount", String.valueOf(pooop));
                cv1.put("updated_date", formattedDate);
                database.update(TABLENAME,cv1,"acc_type_id =? AND accounting_date =? AND tracking_id = ?",new String[]{"19",formattedDate,String.valueOf(ses)});
                ContentValues cv = new ContentValues();
                cv.put("acc_type_id", "8");
                cv.put("amount", "0");
                cv.put("accounting_date", formattedDate);
                cv.put("tracking_id",String.valueOf(ses));
                cv.put("created_date", formattedDate);
                database.insert(TABLENAME, null, cv);
                sqlite.close();
                database.close();
                Log.d("totol",String.valueOf(pooop));
                ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
                ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
                ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
//                closed12(formattedDate,getApplicationContext());
//                beforebal(formattedDate,getApplicationContext());
//                populateRecyclerView23(formattedDate,formattedDate,getApplicationContext());
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent q1 = new Intent(Tally_activity.this,Tally_activity.class);
                                startActivity(q1);
                            }
                        });
                    }
                }, 1000);

            } else if(id2.equals(0) && id1.equals(0)){
                sqlite = new SQLiteHelper(this);
                database = sqlite.getWritableDatabase();
                Integer pooop = pd - totol;
                pooop =  bal21 - totol ;
                pooop = pooop + low21 + hig21;
                ContentValues cv = new ContentValues();
                cv.put("acc_type_id", "8");
                cv.put("amount", "0");
                cv.put("accounting_date", formattedDate);
                cv.put("tracking_id",String.valueOf(ses));
                cv.put("created_date", formattedDate);
                database.insert(TABLENAME, null, cv);
                Log.d("totol",String.valueOf(pooop));
                ContentValues cv1 = new ContentValues();
                cv1.put("acc_type_id", "19");
                cv1.put("amount", String.valueOf(pooop));
                cv1.put("accounting_date", formattedDate);
                cv1.put("tracking_id",String.valueOf(ses));
                cv1.put("created_date", formattedDate);
                database.insert(TABLENAME, null, cv1);
                sqlite.close();
                database.close();
                ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
                ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
                ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
//                closed12(formattedDate,getApplicationContext());
//                beforebal(formattedDate,getApplicationContext());
//                populateRecyclerView23(formattedDate,formattedDate,getApplicationContext());
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent q1 = new Intent(Tally_activity.this,Tally_activity.class);
                                startActivity(q1);
                            }
                        });
                    }
                }, 1000);

            }else if(id2 > 0 && id1 > 0){
                sqlite = new SQLiteHelper(this);
                database = sqlite.getWritableDatabase();
                Integer pooop = pd - totol;
                pooop =  bal21 - totol ;
                pooop = pooop + low21 - hig21;
                ContentValues cv1 = new ContentValues();
                cv1.put("amount", String.valueOf(pooop));
                cv1.put("updated_date", formattedDate);
                database.update(TABLENAME,cv1,"acc_type_id =? AND accounting_date =? AND tracking_id = ?",new String[]{"19",formattedDate,String.valueOf(ses)});
                Log.d("totol21a2",String.valueOf(pooop));
                ContentValues cv = new ContentValues();
                cv.put("amount", "0");
                cv.put("updated_date", formattedDate);
                database.update(TABLENAME,cv,"acc_type_id =? AND accounting_date =? AND tracking_id = ?",new String[]{"8",formattedDate,String.valueOf(ses)});
                sqlite.close();
                database.close();
                ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
                ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
                ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
//                closed12(formattedDate,getApplicationContext());
//                beforebal(formattedDate,getApplicationContext());
//                populateRecyclerView23(formattedDate,formattedDate,getApplicationContext());
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent q1 = new Intent(Tally_activity.this,Tally_activity.class);
                                startActivity(q1);
                            }
                        });
                    }
                }, 1000);

            }

        }
        else if(bal21 < totol){
            if(id1.equals(0) && id2 > 0){
                sqlite = new SQLiteHelper(this);
                database = sqlite.getWritableDatabase();
                Integer pooop = pd - totol;
                pooop =  totol - bal21  ;
                pooop = pooop  + hig21 - low21;
                ContentValues cv = new ContentValues();
                cv.put("acc_type_id", "8");
                cv.put("amount", String.valueOf(pooop));
                cv.put("accounting_date", formattedDate);
                cv.put("tracking_id",String.valueOf(ses));
                cv.put("created_date", formattedDate);
                database.insert(TABLENAME, null, cv);
                Log.d("totola",String.valueOf(pooop));
                ContentValues cv1 = new ContentValues();
                cv1.put("amount", "0");
                cv1.put("updated_date", formattedDate);
                database.update(TABLENAME,cv1,"acc_type_id =? AND accounting_date =? AND tracking_id = ?",new String[]{"19",formattedDate,String.valueOf(ses)});
                sqlite.close();
                database.close();
                ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
                ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
                ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
//                closed12(formattedDate,getApplicationContext());
//                beforebal(formattedDate,getApplicationContext());
//                populateRecyclerView23(formattedDate,formattedDate,getApplicationContext());
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent q1 = new Intent(Tally_activity.this,Tally_activity.class);
                                startActivity(q1);
                            }
                        });
                    }
                }, 1000);

            }else if(id1 > 0 && id2.equals(0)){
                sqlite = new SQLiteHelper(this);
                database = sqlite.getWritableDatabase();
                Integer pooop = pd - totol;
                pooop =  totol - bal21 ;
                pooop = pooop + low21 + hig21;
                ContentValues cv1 = new ContentValues();
                cv1.put("amount", String.valueOf(pooop));
                cv1.put("updated_date", formattedDate);
                database.update(TABLENAME,cv1,"acc_type_id =? AND accounting_date =? AND tracking_id = ?",new String[]{"8",formattedDate,String.valueOf(ses)});
                ContentValues cv = new ContentValues();
                cv.put("acc_type_id", "19");
                cv.put("amount", "0");
                cv.put("accounting_date", formattedDate);
                cv.put("tracking_id",String.valueOf(ses));
                cv.put("created_date", formattedDate);
                database.insert(TABLENAME, null, cv);
                Log.d("totola",String.valueOf(pooop));
                sqlite.close();
                database.close();
                ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
                ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
                ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
//                closed12(formattedDate,getApplicationContext());
//                beforebal(formattedDate,getApplicationContext());
//                populateRecyclerView23(formattedDate,formattedDate,getApplicationContext());
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent q1 = new Intent(Tally_activity.this,Tally_activity.class);
                                startActivity(q1);
                            }
                        });
                    }
                }, 1000);

            }else if(id1.equals(0) && id2.equals(0)){
                sqlite = new SQLiteHelper(this);
                database = sqlite.getWritableDatabase();
                Integer pooop = pd - totol;
                pooop =  totol - bal21 ;
                pooop = pooop + low21 + hig21;
                ContentValues cv = new ContentValues();
                cv.put("acc_type_id", "8");
                cv.put("amount", String.valueOf(pooop));
                cv.put("accounting_date", formattedDate);
                cv.put("tracking_id",String.valueOf(ses));
                cv.put("created_date", formattedDate);
                database.insert(TABLENAME, null, cv);
                Log.d("totol21a",String.valueOf(pooop));
                ContentValues cv1 = new ContentValues();
                cv1.put("acc_type_id", "19");
                cv1.put("amount", "0");
                cv1.put("accounting_date", formattedDate);
                cv1.put("tracking_id",String.valueOf(ses));
                cv1.put("created_date", formattedDate);
                database.insert(TABLENAME, null, cv1);
                sqlite.close();
                database.close();
                ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
                ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
                ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
//                closed12(formattedDate,getApplicationContext());
//                beforebal(formattedDate,getApplicationContext());
//                populateRecyclerView23(formattedDate,formattedDate,getApplicationContext());
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent q1 = new Intent(Tally_activity.this,Tally_activity.class);
                                startActivity(q1);
                            }
                        });
                    }
                }, 1000);

            }else if(id1 > 0 && id2 > 0){
                sqlite = new SQLiteHelper(this);
                database = sqlite.getWritableDatabase();
                Integer pooop = pd - totol;
                pooop =  totol - bal21  ;
                pooop = pooop  + hig21 - low21;
                ContentValues cv1 = new ContentValues();
                cv1.put("amount", String.valueOf(pooop));
                cv1.put("updated_date", formattedDate);
                database.update(TABLENAME,cv1,"acc_type_id =? AND accounting_date =? AND tracking_id = ?",new String[]{"8",formattedDate,String.valueOf(ses)});
                Log.d("totol21a2",String.valueOf(pooop));
                ContentValues cv = new ContentValues();
                cv.put("amount", "0");
                cv.put("updated_date", formattedDate);
                database.update(TABLENAME,cv,"acc_type_id =? AND accounting_date =? AND tracking_id = ?",new String[]{"19",formattedDate,String.valueOf(ses)});
                sqlite.close();
                database.close();
                ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
                ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
                ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
//                closed12(formattedDate,getApplicationContext());
//                beforebal(formattedDate,getApplicationContext());
//                populateRecyclerView23(formattedDate,formattedDate,getApplicationContext());
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent q1 = new Intent(Tally_activity.this,Tally_activity.class);
                                startActivity(q1);
                            }
                        });
                    }
                }, 1000);


            }
        }
//        Intent nn = new Intent(Tally_activity.this,Tally_activity.class);
//        startActivity(nn);
    }

    //zero() - insert tally
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void zero(){
        Log.d("hj",formattedDate);
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("two_thousand", "0");
        values.put("one_thousand", "0");
        values.put("five_hundred", "0");
        values.put("two_hundred", "0");
        values.put("one_hundred", "0");
        values.put("fifty", "0");
        values.put("twenty", "0");
        values.put("ten", "0");
        values.put("five", "0");
        values.put("custom1", "0");
        values.put("custom2", "0");
        values.put("tracking_id", ses);
        values.put("created_date", formattedDate);
        database.insert("dd_tally", null, values);
        zero1();
        sqlite.close();
        database.close();
    }

    //zero1() - get and set tally
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void zero1(){
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        String SUM_QUERY = "SELECT * FROM dd_tally WHERE created_date = ? AND tracking_id = ?";
        Cursor cur = database.rawQuery(SUM_QUERY,new String[]{formattedDate,String.valueOf(ses)});
        if (cur != null) {
            if (cur.getCount() != 0) {
                cur.moveToFirst();
                do {
                    int index;
//                    CASE amount WHEN SUM(amount)  IS NULL THEN amount = '1' ELSE amount  END

                    index = cur.getColumnIndexOrThrow("id");
                    id = cur.getString(index);

                    index = cur.getColumnIndexOrThrow("two_thousand");
                    String tt = cur.getString(index);
                    Integer tt1 = cur.getInt(index);

                    index = cur.getColumnIndexOrThrow("one_thousand");
                    String ot = cur.getString(index);
                    Integer ot1 = cur.getInt(index);

                    index = cur.getColumnIndexOrThrow("five_hundred");
                    String fh = cur.getString(index);
                    Integer fh1 = cur.getInt(index);

                    index = cur.getColumnIndexOrThrow("two_hundred");
                    String th = cur.getString(index);
                    Integer th1 = cur.getInt(index);

                    index = cur.getColumnIndexOrThrow("one_hundred");
                    String oh = cur.getString(index);
                    Integer oh1 = cur.getInt(index);

                    index = cur.getColumnIndexOrThrow("fifty");
                    String ft = cur.getString(index);
                    Integer ft1 = cur.getInt(index);

                    index = cur.getColumnIndexOrThrow("twenty");
                    Integer twh1 = cur.getInt(index);
                    String twh = cur.getString(index);

                    index = cur.getColumnIndexOrThrow("ten");
                    String t = cur.getString(index);
                    Integer t1 = cur.getInt(index);

                    index = cur.getColumnIndexOrThrow("five");
                    String f = cur.getString(index);
                    Integer f1 = cur.getInt(index);

                    index = cur.getColumnIndexOrThrow("custom1");
                    String c1 = cur.getString(index);
                    Integer c11 = cur.getInt(index);

                    index = cur.getColumnIndexOrThrow("custom2");
                    String c2 = cur.getString(index);
                    Integer c21 = cur.getInt(index);


//                    Log.d("debittt",String.valueOf(odd)+" "+String.valueOf(oddd));
                    Log.d("debittt1123",f);
//                    paidamount = String.valueOf(od);
                    twot.setText(tt);
                    onet.setText(ot);
                    fivehu.setText(fh);
                    twoh.setText(th);
                    hond.setText(oh);
                    fif.setText(ft);
                    twen.setText(twh);
                    tn.setText(t);
                    fiv.setText(f);
                    cus1.setText(c2);
                    cus2.setText(c1);
                    twt.setText(String.valueOf(2000 * tt1));
                    oneto.setText(String.valueOf(1000 * ot1));
                    fv.setText(String.valueOf(500 * fh1));
                    hu.setText(String.valueOf(100 * oh1));
                    tw.setText(String.valueOf(200 * th1));
                    fi.setText(String.valueOf(50 * ft1));
                    twe.setText(String.valueOf(20 * twh1));
                    te.setText(String.valueOf(10 * t1));
                    fii.setText(String.valueOf(5 * f1));
                    cu.setText(String.valueOf(c21 * c11));
                    calculate();
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
    }

    //check() - get tally
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void check (){
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        Log.d("datree",formattedDate);
        String SUM_QUERY = "SELECT * FROM dd_tally WHERE created_date = ? AND tracking_id = ?";
        Cursor cur = database.rawQuery(SUM_QUERY,new String[]{formattedDate,String.valueOf(ses)});
        if (cur != null) {
            if (cur.getCount() != 0) {
                cur.moveToFirst();
                do {
                    int index;
//                    CASE amount WHEN SUM(amount)  IS NULL THEN amount = '1' ELSE amount  END

                    index = cur.getColumnIndexOrThrow("id");
                    id = cur.getString(index);
                    if(id == null){
                        save.setText(R.string.save);
                        zero();
                  Log.d("op12","op");
                    }else{
                        save.setText(R.string.edit);
                        zero1();
                        Log.d("op12","op1");
                    }
                }
                while (cur.moveToNext());
                cur.close();
                sqlite.close();
                database.close();
            }else{
                cur.close();
                sqlite.close();
                database.close();
                Log.d("op12","op22");
                zero();
                save.setText(R.string.save);
            }
        }else{
            cur.close();
            sqlite.close();
            database.close();
        }
    }

    //onBackPressed() - function called when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onBackPressed() {
        Intent na = new Intent(Tally_activity.this,NavigationActivity.class);
        startActivity(na);
        finish();
    }

    //onCreateOptionsMenu() - when navigation menu created
    //Params - Menu
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.tally_activity, menu);
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

    //beforebal() - get before balance
    //Params - date and application context
    //Created on 25/01/2022
    //Return - NULL
    public void beforebal(String dateString, Context context){
        SQLiteHelper sqlite;
        SQLiteDatabase database;
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        befo = 0;
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
    //populateRecyclerView23() - get total debits all datas
    // Params -from date , to date and session
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
                    Integer newc = Integer.parseInt(credd);
                    Integer curc = Integer.parseInt(today);
                    Integer nipc = Integer.parseInt(nip);
                    Integer nipnipc = Integer.parseInt(nipnip);
                    Integer dab = dabit - innn;
                    Integer totdab = Integer.parseInt(debbbtacc);
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
                    Log.d("totalbal", String.valueOf(currentbal));
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
            Intent new1 = new Intent(Tally_activity.this,MainActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.user) {
            Intent new1 = new Intent(Tally_activity.this,HomeActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.debit) {
            Intent new1 = new Intent(Tally_activity.this,Debit.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.collection) {
            Intent new1 = new Intent(Tally_activity.this,AllCollection.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.account) {
            Intent new1 = new Intent(Tally_activity.this,Account.class);
            startActivity(new1);
            finish();
        }  else if (id == R.id.report) {
            Intent new1 = new Intent(Tally_activity.this,ReportActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.customer) {
            Intent new1 = new Intent(Tally_activity.this,Customer_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.settings) {
            Intent new1 = new Intent(Tally_activity.this,Settings_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.tally) {
            Intent new1 = new Intent(Tally_activity.this,Tally_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.employee) {
            Intent new1 = new Intent(Tally_activity.this,Employee_details.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.today) {
            Intent new1 = new Intent(Tally_activity.this,Today_report.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.logout) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Tally_activity.this,R.style.AlertDialogTheme);
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
                            Intent i = new Intent(Tally_activity.this,Splash.class);
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
