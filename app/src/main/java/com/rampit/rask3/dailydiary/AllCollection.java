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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;


//Updated on 25/01/2022 by RAMPIT
//used go to all other collection pages
//InternetConnection1() - Check whether internet is on or not
//onBackPressed() - function called when back button is pressed
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed
//onNavigationItemSelected() - when navigation item pressed
//mainvalues() - Check whether any user is there or not
//progressbar_load() - Load progressbar
//progre() - Stop progressbar
//mainvalues1() - Check whether any debit is there or not



public class AllCollection extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> ID = new ArrayList<String>();
    ArrayList<String> Names1 = new ArrayList<>();
    ArrayList<Long> ID1 = new ArrayList<>();
    ArrayList<Long> paid_cust = new ArrayList<>();
    ArrayList<Long> balance_cust = new ArrayList<>();
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    EditText searchView, searchid;
    Button ad, can, save ,save1;
    TextView tot, ppp, bbb, totco,alert, collect_history,clobal;
    ScrollView sc1, sc2;
    TextView namee, slno1, namee1, deb, pam, bam, opd, loc, pho, cd, tod, pad, mid, bad, noo, dateee, session, mama, total1, instal, tott, time, totbal, bul;
    EditText col, ofe, dit, datt, slno;
    Integer balanceamount, debitam1, instamt, pamount, pdays, toda,toda1, misda, slllno, ses, misam, paiidddd, sll, collectoo,disco;
    Double paiddays, balanceday, totda, dbdate, todate,missiday,missda1,toda2;
    String idd, iii,didi, nme, paidamount, installment, totaldays, ddbt, debitdaaa, pp, dbdda, Clo, timing, cloo, formattedDate, collect, dateString, balat,missed,didid,quickbulk,quickbulk1;
    public static String TABLENAME5 = "dd_collection";
    public static String TABLENAME = "dd_accounting";
    NavigationView navigationView;
    Menu nav_Menu;
    LinearLayout lll,lll1;
    Calendar myCalendar;
    Button logou, profil,single,bulk,drag,quick;
    ImageButton clos,exp;
    ImageView seesim;
    Integer in ;
    Integer dabamo,docamo,intamo,expo1,paiamo,userid,debitid;
    SharedPreferences.Editor editor;
    private PopupWindow mPopupWindow;
    String ss, zero, one, two, three, four, five, six, seven, eight, nine, ten, iddd, adpr, edpr, depr, vipr,MY_QUERY,datead,datedd,didd,iiddd,ccid,Name,iidcid,xcol,sele12;
    DatePickerDialog datePickerDialog;
    Dialog dialog ;
    Integer showinng = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ii = pref.getString("theme","");
        String bul = pref.getString("bulkupdate","0");
        String sinc = pref.getString("singlecollection","0");
        quickbulk = pref.getString("quickbulkupdate","0");
        quickbulk1 = pref.getString("quickbulk","0");
        String bul_add = pref.getString("bulkupdate_add","0");
        String sinc_add = pref.getString("singlecollection_add","0");
        String quick_add = pref.getString("quickbulkupdate_add","0");
//        Log.d("bul_qbul",bul+" "+sinc+" "+quickbulk+" "+bul_add+" "+sinc_add+" "+quick_add+" "+quickbulk1);
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
//        quickbulk = pref.getString("quickbulk","0");
        paiamo = 0;
        expo1 = 0 ;
        userid = 0;
        debitid = 0;
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dialog = new Dialog(AllCollection.this,R.style.AlertDialogTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_allcollection);
        setTitle(R.string.title_activity_collection);
        ((Callback)getApplication()).datee();
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        sll = 0;
        collectoo = 0;
        paiidddd = 0;
        toda1 = 0;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 1);

            }
        }

        @SuppressLint("WrongViewCast") ConstraintLayout linear = findViewById(R.id.linearr);
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),AllCollection.this)) {
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
        editor = pref.edit();
        quick = findViewById(R.id.quick);
//        quick.setVisibility(View.GONE);
//        quick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent bulku = new Intent(AllCollection.this,Quick_bulkupdate.class);
//                startActivity(bulku);
//                finish();
//            }
//        });
        tot = findViewById(R.id.total);
        ppp = findViewById(R.id.pid);
        bbb = findViewById(R.id.bal);
        tott = findViewById(R.id.tott);
        time = findViewById(R.id.time);
        save1 = findViewById(R.id.save2);
        lll1 = findViewById(R.id.liniy);
        exp = findViewById(R.id.exp);
        alert = findViewById(R.id.alert);
        totbal = findViewById(R.id.tot_bal);

        single = findViewById(R.id.single);
        drag = findViewById(R.id.drag);
        bulk = findViewById(R.id.bulk);

        seesim = findViewById(R.id.sesimg);
        collect_history = findViewById(R.id.collection_history);
        total1 = findViewById(R.id.dayss);
        instal = findViewById(R.id.inamount);
        String sesid = pref.getString("id", "0");
        sc1 = findViewById(R.id.scroll1);
        sc2 = findViewById(R.id.scroll2);
        clobal = findViewById(R.id.clo_bal);
        datt = (EditText) findViewById(R.id.search1);
        clos = findViewById(R.id.closedd);
        lll = (findViewById(R.id.linee));
        TextView pro = lll.findViewById(R.id.pro);
        logou = lll.findViewById(R.id.logout);
        profil = lll.findViewById(R.id.profile);
//        if(sesid.equalsIgnoreCase("1")){
//        }else{
//            String module_i = "4";
//            ((Callback) getApplication()).privilege(sesid, module_i);
//            adpr = pref.getString("add_privilege", "");
//            edpr = pref.getString("edit_privilege", "");
//            depr = pref.getString("delete_privilege", "");
//            vipr = pref.getString("view_privilege", "");
//            Log.d("dtr",adpr);
//            Log.d("dtr",vipr);
//            if (adpr.equalsIgnoreCase("0") && vipr.equalsIgnoreCase("0")) {
//                single.setVisibility(View.INVISIBLE);
//                drag.setVisibility(View.GONE);
//                bulk.setVisibility(View.INVISIBLE);
//                alert.setVisibility(View.VISIBLE);
//                //                clos.setVisibility(View.INVISIBLE);
//            }else if (adpr.equalsIgnoreCase("0") || vipr.equalsIgnoreCase("0")) {
//                single.setVisibility(View.INVISIBLE);
//                drag.setVisibility(View.GONE);
//                bulk.setVisibility(View.INVISIBLE);
//                alert.setVisibility(View.VISIBLE);
////                clos.setVisibility(View.INVISIBLE);
//            }else{
//                single.setVisibility(View.VISIBLE);
//                drag.setVisibility(View.VISIBLE);
//                bulk.setVisibility(View.VISIBLE);
//                alert.setVisibility(View.GONE);
////                sc2.setVisibility(View.VISIBLE);
////                sc1.setVisibility(View.GONE);
////                datt.setVisibility(View.INVISIBLE);
////                clos.setVisibility(View.INVISIBLE);
//            }}
        Log.d("bul_qbul",bul+" "+sinc+" "+quickbulk+" "+bul_add+" "+sinc_add+" "+quick_add+" "+quickbulk1+" "+sesid);
          if(sesid.equalsIgnoreCase("1")){
            sinc_add = "1";
            bul_add = "1";
            quick_add = "1";
            if(quickbulk1.equalsIgnoreCase("0")){
                quick.setVisibility(View.GONE);
            }else{
                quick.setVisibility(View.VISIBLE);
            }
        }
        else{
            String module_i = "4";
            ((Callback) getApplication()).privilege(sesid, module_i);
            adpr = pref.getString("add_privilege", "");
            edpr = pref.getString("edit_privilege", "");
            depr = pref.getString("delete_privilege", "");
            vipr = pref.getString("view_privilege", "");
            Log.d("dtr",adpr);
            Log.d("dtr",vipr);
            if (adpr.equalsIgnoreCase("0") && vipr.equalsIgnoreCase("0")) {
                single.setVisibility(View.INVISIBLE);
                drag.setVisibility(View.GONE);
                bulk.setVisibility(View.INVISIBLE);
                alert.setVisibility(View.VISIBLE);
                if(quickbulk.equalsIgnoreCase("4")){
                    quick.setVisibility(View.VISIBLE);
                }else{
                    quick.setVisibility(View.GONE);
                }
                if(sinc.equalsIgnoreCase("4")){
                    single.setVisibility(View.VISIBLE);
                }else{
                    single.setVisibility(View.GONE);
                }
                if(bul.equalsIgnoreCase("4")){
                    bulk.setVisibility(View.VISIBLE);
                }else{
                    bulk.setVisibility(View.GONE);
                }
                //                clos.setVisibility(View.INVISIBLE);
            }
            else if (adpr.equalsIgnoreCase("0") || vipr.equalsIgnoreCase("0")) {
                single.setVisibility(View.INVISIBLE);
                drag.setVisibility(View.GONE);
                bulk.setVisibility(View.INVISIBLE);
                alert.setVisibility(View.VISIBLE);
                if(quickbulk.equalsIgnoreCase("4")){
                    quick.setVisibility(View.VISIBLE);
                }else{
                    quick.setVisibility(View.GONE);
                }
                if(sinc.equalsIgnoreCase("4")){
                    single.setVisibility(View.VISIBLE);
                }else{
                    single.setVisibility(View.GONE);
                }
                if(bul.equalsIgnoreCase("4")){
                    bulk.setVisibility(View.VISIBLE);
                }else{
                    bulk.setVisibility(View.GONE);
                }
//                clos.setVisibility(View.INVISIBLE);
            }
            else{
                single.setVisibility(View.VISIBLE);
                drag.setVisibility(View.VISIBLE);
                bulk.setVisibility(View.VISIBLE);
                alert.setVisibility(View.GONE);
                if(quickbulk.equalsIgnoreCase("4")){
                    quick.setVisibility(View.VISIBLE);
                }else{
                    quick.setVisibility(View.GONE);
                }
                if(sinc.equalsIgnoreCase("4")){
                    single.setVisibility(View.VISIBLE);
                }else{
                    single.setVisibility(View.GONE);
                }
                if(bul.equalsIgnoreCase("4")){
                    bulk.setVisibility(View.VISIBLE);
                }else{
                    bulk.setVisibility(View.GONE);
                }
//                sc2.setVisibility(View.VISIBLE);
//                sc1.setVisibility(View.GONE);
//                datt.setVisibility(View.INVISIBLE);
//                clos.setVisibility(View.INVISIBLE);
            }
            if(quickbulk1.equalsIgnoreCase("0")){
                quick.setVisibility(View.GONE);
            }else{
//                quick.setVisibility(View.VISIBLE);
            }}
        zero = pref.getString("company", "");
        one = pref.getString("user1", "");
        two = pref.getString("debit", "");
        three = pref.getString("collection", "");
        four = pref.getString("account", "");
        five = pref.getString("reports", "");
        six = pref.getString("customer", "");
        seven = pref.getString("settings", "");
        eight = pref.getString("tally", "");
        nine = pref.getString("employee_details", "");
        ten = pref.getString("today_report", "");
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        nav_Menu = navigationView.getMenu();
        if (zero.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.company).setVisible(false);
        }
        if (one.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.user).setVisible(false);
        }
        if (two.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.debit).setVisible(false);
        }
        if (three.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.collection).setVisible(false);
        }
        if (four.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.account).setVisible(false);
        }
        if (five.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.report).setVisible(false);
        }
        if (six.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.customer).setVisible(false);
        }
        if (seven.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.settings).setVisible(false);
            profil.setVisibility(View.GONE);
            pro.setVisibility(View.GONE);
        }
        if (eight.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.tally).setVisible(false);
        }
        if (nine.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.employee).setVisible(false);
        }
        if (ten.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.today).setVisible(false);
        }

        LinearLayout relativeLayout = (LinearLayout) findViewById(R.id.lolo);


        Button theme = lll.findViewById(R.id.theme);
        namee1 = findViewById(R.id.name1);
        namee = findViewById(R.id.name);
        if(in == 0){
            logou.setBackgroundResource(R.drawable.logout);
            profil.setBackgroundResource(R.drawable.user1);
            theme.setBackgroundResource(R.drawable.theme);
            single.setBackgroundResource(R.drawable.report_button);
            bulk.setBackgroundResource(R.drawable.report_button);
            drag.setBackgroundResource(R.drawable.report_button);
            quick.setBackgroundResource(R.drawable.report_button);
        }else{
            logou.setBackgroundResource(R.drawable.logoutblue);
            profil.setBackgroundResource(R.drawable.user_blue);
            theme.setBackgroundResource(R.drawable.theme1);
            single.setBackgroundResource(R.drawable.report_button1);
            bulk.setBackgroundResource(R.drawable.report_button1);
            drag.setBackgroundResource(R.drawable.report_button1);
            quick.setBackgroundResource(R.drawable.report_button1);
        }

//        single.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent single = new Intent(AllCollection.this,Collection.class);
//                startActivity(single);
//                finish();
//            }
//        });
//        bulk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent bulku = new Intent(AllCollection.this,Bulkupdate.class);
//                startActivity(bulku);
//                finish();
//            }
//        });
          if(sinc_add.equalsIgnoreCase("0")){
            single.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(AllCollection.this,R.style.AlertDialogTheme);
                    String enn = getString(R.string.you_dont_have_permission);
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
            });
        }
        else{
        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent single = new Intent(AllCollection.this,Collection.class);
                startActivity(single);
                finish();
            }
        });
        }
         if(bul_add.equalsIgnoreCase("0")){
            bulk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(AllCollection.this,R.style.AlertDialogTheme);
                    String enn = getString(R.string.you_dont_have_permission);
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
            });
        }
        else{
        bulk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bulku = new Intent(AllCollection.this,Bulkupdate.class);
                startActivity(bulku);
                finish();
            }
        });
         }

        if(quick_add.equalsIgnoreCase("0")){
            quick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(AllCollection.this,R.style.AlertDialogTheme);
                    String enn = getString(R.string.you_dont_have_permission);
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
            });
        }
        else{
            quick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent bulku = new Intent(AllCollection.this,Quick_bulkupdate.class);
                    bulku.putExtra("scroll",0);
                    startActivity(bulku);
                    finish();
                }
            });        }
        drag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dragu = new Intent(AllCollection.this,Draganddrop.class);
                startActivity(dragu);
                finish();
            }
        });
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AllCollection.this,Settings_activity.class);
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
                    Intent i = new Intent(AllCollection.this,NavigationActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    editor.putString("theme","0");
                    editor.apply();
                    Intent i = new Intent(AllCollection.this,NavigationActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        });

        logou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(AllCollection.this,R.style.AlertDialogTheme);
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
                                editor.putInt("isloginn", 0);
                                editor.commit();
                                Intent i = new Intent(AllCollection.this, Splash.class);
                                startActivity(i);
                                finish();
                            }
                        });
                alertbox.show();
            }
        });

        Calendar b = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
//        b.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));

        SimpleDateFormat df2 = new SimpleDateFormat("hh:mm aaa");

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        String timeZone = Calendar.getInstance().getTimeZone().getID();
        df2.setTimeZone(TimeZone.getTimeZone(timeZone));
        String timee = df2.format(b.getTime());
        Log.d("timm",timee);
        formattedDate = df.format(c.getTime());
//        Log.d("allcollection_date",formattedDate);
        final String formattedDate1 = formattedDate ;
        final String forrrddd = df1.format(c.getTime());
        slllno = 0;
        ses = pref.getInt("session", 1);


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("allcollection_date",formattedDate1);
                        ((Callback)getApplication()).closed(ses);
                        ((Callback)getApplication()).closed1(formattedDate1, String.valueOf(ses));
                        ((Callback)getApplication()).beforebal(formattedDate1, String.valueOf(ses));
                        ((Callback)getApplication()).populateRecyclerView23(formattedDate1, formattedDate1 ,String.valueOf(ses));
                        mainvalues();
                        mainvalues1();
//        balat = pref.getString("totalbal", "");
//        totbal.setText("\u20B9"+""+balat);
                        if(userid <= 0){
                            AlertDialog.Builder alertbox = new AlertDialog.Builder(AllCollection.this,R.style.AlertDialogTheme);
                            String logmsg = getString(R.string.no_user);
                            String cann = getString(R.string.ok);
                            String warr = getString(R.string.warning);
                            alertbox.setMessage(logmsg);
                            alertbox.setTitle(warr);
                            alertbox.setIcon(R.drawable.dailylogo);
                            alertbox.setNeutralButton(cann,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface arg0,
                                                            int arg1) {
                                            Intent mk = new Intent(AllCollection.this,Addnames.class);
                                            startActivity(mk);
                                            finish();

                                        }
                                    });
                            alertbox.show();
                        }
                        else if(debitid <= 0){
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    progre();
                                }
                            });
                            AlertDialog.Builder alertbox = new AlertDialog.Builder(AllCollection.this,R.style.AlertDialogTheme);
                            String logmsg = getString(R.string.no_debit);
                            String cann = getString(R.string.ok);
                            String warr = getString(R.string.warning);
                            String logo = getString(R.string.Logout);
                            alertbox.setMessage(logmsg);
                            alertbox.setTitle(warr);
                            alertbox.setIcon(R.drawable.dailylogo);
                            alertbox.setNeutralButton(cann,
                                    new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface arg0,
                                                            int arg1) {
                                            Intent mk = new Intent(AllCollection.this,Newdebit.class);
                                            startActivity(mk);
                                            finish();
                                        }
                                    });
                            alertbox.show();
                        }
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

        String dd = pref.getString("Date", "");
        try {
            Date debit = df1.parse(dd);
            formattedDate = df.format(debit);
            Log.d("deae", formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateee = findViewById(R.id.da);
        session = findViewById(R.id.sess);
//        dateee.setText(dd);

        String dsdsd = pref.getString("NAME", "");
        if (ses == 1) {
            ss = getString(R.string.morning);
        } else if (ses == 2) {
            ss = getString(R.string.evening);
        }
        TextView dayy = (TextView) findViewById(R.id.day);
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

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        TextView nn = headerView.findViewById(R.id.name);
        TextView nnn = headerView.findViewById(R.id.date);
        TextView nnnn = headerView.findViewById(R.id.session);
        nn.setText(dsdsd);
        nnn.setText("Date :" + " " + dd);
        nnnn.setText("Session :" + " " + ss);
        LinearLayout wq = headerView.findViewById(R.id.navdash);
        ImageView logy = headerView.findViewById(R.id.logy);
        if(in == 0){
            logy.setBackgroundResource(R.drawable.logout);
        }
        else{
            logy.setBackgroundResource(R.drawable.logoutblue);
        }
        logy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(AllCollection.this,R.style.AlertDialogTheme);
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
                                Intent i = new Intent(AllCollection.this,Splash.class);
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
                Intent qq = new Intent(AllCollection.this,NavigationActivity.class);
                startActivity(qq);
                finish();
            }
        });

        sqlite = new SQLiteHelper(this);
        idd = "0";


        myCalendar = Calendar.getInstance();
        datead = pref.getString("coldate","");
        if(datead.equalsIgnoreCase("")){

        }else{
//            dated1 = datead;
            try {
                SimpleDateFormat df3 = new SimpleDateFormat("yyyy/MM/dd");
                SimpleDateFormat df4 = new SimpleDateFormat("dd/MM/yyyy");
                Date debit = df3.parse(datead);
                datedd = df4.format(debit);
//                dated1 = df.format(debit);
                formattedDate = datead;
                Log.d("deae2",datedd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            datt.setText(datedd);
        }
        Intent name = getIntent();
        idd = name.getStringExtra("ID");
        didi = name.getStringExtra("DID");
        sele12 = name.getStringExtra("selecte");
        if(sele12 == null || sele12.equalsIgnoreCase("")){
            sele12 = "0";
            Log.d("selectedva",sele12);
        }else{
            Log.d("selectedva1",sele12);
            editor.putString("selectedvalue",sele12);
            editor.apply();
        }

    }
    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final AllCollection account) {
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


    //onBackPressed() - function called when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onBackPressed() {
        Intent nn = new Intent(AllCollection.this,NavigationActivity.class);
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
            Intent new1 = new Intent(AllCollection.this,MainActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.user) {
            Intent new1 = new Intent(AllCollection.this,HomeActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.debit) {
            Intent new1 = new Intent(AllCollection.this,Debit.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.collection) {
            Intent new1 = new Intent(AllCollection.this,AllCollection.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.account) {
            Intent new1 = new Intent(AllCollection.this,Account.class);
            startActivity(new1);
            finish();
        }  else if (id == R.id.report) {
            Intent new1 = new Intent(AllCollection.this,ReportActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.customer) {
            Intent new1 = new Intent(AllCollection.this,Customer_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.settings) {
            Intent new1 = new Intent(AllCollection.this,Settings_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.tally) {
            Intent new1 = new Intent(AllCollection.this,Tally_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.employee) {
            Intent new1 = new Intent(AllCollection.this,Employee_details.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.today) {
            Intent new1 = new Intent(AllCollection.this,Today_report.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.logout) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(AllCollection.this,R.style.AlertDialogTheme);
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
                            Intent i = new Intent(AllCollection.this,Splash.class);
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

    //mainvalues() - Check whether any user is there or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void mainvalues(){
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
//        String mobile = pref.getString("mobile","0");

        sqlite = new SQLiteHelper(AllCollection.this);
        database = sqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM dd_customers WHERE tracking_id = ?",new String[]{String.valueOf(ses)});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("id");
                    userid = cursor.getInt(index);

                }
                while (cursor.moveToNext());

                cursor.close();
                sqlite.close();
                database.close();
            }else{
                userid = 0;
                cursor.close();
                sqlite.close();
                database.close();
            }
        }else{
            userid = 0;
            cursor.close();
            sqlite.close();
            database.close();
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

    //mainvalues1() - Check whether any debit is there or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void mainvalues1(){

        sqlite = new SQLiteHelper(AllCollection.this);
        database = sqlite.getWritableDatabase();
//        Cursor cursor = database.rawQuery("SELECT a.*,b.id as ID FROM dd_customers a " +
//                "LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE a.tracking_id = ?",
//                new String[]{String.valueOf(ses)});
        Cursor cursor = database.rawQuery("SELECT a.*,b.id as ID FROM dd_customers a " +
                        "LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE a.tracking_id = ? AND b.active_status = 1 ORDER BY b.id DESC LIMIT 0,1",
                new String[]{String.valueOf(ses)});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("ID");
                    debitid = cursor.getInt(index);
                    Log.d("debitid", String.valueOf(debitid));
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            progre();
                        }
                    });
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                debitid = 0;
                cursor.close();
                sqlite.close();
                database.close();
            }
        }else{
            debitid = 0;
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    progre();
                }
            });
            cursor.close();
            sqlite.close();
            database.close();
        }
    }


}
