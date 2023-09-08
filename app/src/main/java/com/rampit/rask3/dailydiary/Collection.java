package com.rampit.rask3.dailydiary;

import android.Manifest;
import android.animation.ValueAnimator;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;

import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.rampit.rask3.dailydiary.Adapter.MyRecyclerViewAdapter1;
import com.rampit.rask3.dailydiary.Adapter.MyRecyclerViewAdapter12;
import com.rampit.rask3.dailydiary.Adapter.MyRecyclerViewAdapter_col;
import com.rampit.rask3.dailydiary.Adapter.mobilenumber_adapter;
import com.rampit.rask3.dailydiary.Adapter.mobilenumber_adapter1;
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
//single collection page
//pphhoonnee() - show user numbers
//call() - Call numbers
//onRestart() - Called when activity is restarted
//onPause() - Called when activity is paused
//onDestroy() - Called when activity is destroyed
//createFloatView() - Creates an button float
//onDoubleClick() - Called when button is double clicked
//popup1122() - get all current and NIP user
//popup11222() - get all NIPNIP user
//list1() - get closed debits
//onBackPressed() - function called when back button is pressed
//inte() - Intent to refresh the page
//updateLabel() - Update textview when date is selected
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed
//onNavigationItemSelected() - when navigation item pressed
//today() - get total collection
//expand() - Expand an view
//collapse() - collapse an view
//popup() - get users of current and NIP
//popup12() - get users of NIPNIP
//add() - Add colletcion
//add1() - update collection
//othercharges_id() - add or update other charges
//last() - gets last inserted id
//populateRecyclerView() - gets collection datas to show
//list() - list collection datas
//calculate1() - get other charges
//calculate() - days caluclation
//popup1() - gets collection history
//ppp() - shows collection history
//progressbar_load() - Load progressbar
//progre() - Stop progressbar

public class Collection extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,DoubleTapCallback {
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
    TextView tot, ppp, bbb, totco, collect_history,clobal;
    ScrollView sc1, sc2;
    TextView namee, slno1, namee1, deb, pam, bam, opd, loc, pho, cd, tod, pad, mid, bad, noo, dateee, session, mama, total1, instal, tott, time, totbal, bul;
    EditText col, ofe, dit, datt, slno;
    Integer balanceamount, debitam1, instamt, pamount, pdays, toda,toda1, misda, slllno, ses, misam, paiidddd, sll, collectoo,disco;
    Double paiddays, balanceday, totda, dbdate, todate,missiday,missda1,toda2;
    String idd, iii,didi, nme, paidamount, installment, totaldays, ddbt, debitdaaa, pp, dbdda, Clo, timing, cloo, formattedDate, collect, dateString, balat,missed,didid;
    public static String TABLENAME5 = "dd_collection";
    public static String TABLENAME = "dd_accounting";
    NavigationView navigationView;
    Menu nav_Menu;
    LinearLayout lll,lll1;
    Calendar myCalendar;
    Button logou, profil;
    ImageButton clos,exp;
    ImageView seesim;
    Integer in ;
    Integer dabamo,docamo,intamo,expo1,paiamo,otherid;
    SharedPreferences.Editor editor;
    private PopupWindow mPopupWindow;
    String ss, zero, one, two, three, four, five, six, seven, eight, nine, ten, iddd, adpr, edpr, depr, vipr,MY_QUERY,datead,datedd,didd,iiddd,ccid,Name,iidcid,xcol,sele12;
    DatePickerDialog datePickerDialog;
    Dialog dialog;
    WindowManager wm;
    ImageView btn_floatView;
    ArrayList <String> ppooo = new ArrayList<String>();

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
        paiamo = 0;
        expo1 = 0 ;
        otherid = 0;
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dialog = new Dialog(Collection.this,R.style.AlertDialogTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_collection);
        setTitle(R.string.title_activity_collection);
        ((Callback)getApplication()).datee();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
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
        wm = (WindowManager) Collection.this.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        createFloatView();
        editor = pref.edit();
        tot = findViewById(R.id.total);
        ppp = findViewById(R.id.pid);
        bbb = findViewById(R.id.bal);
        tott = findViewById(R.id.tott);
        time = findViewById(R.id.time);
        save1 = findViewById(R.id.save2);
        lll1 = findViewById(R.id.liniy);
        exp = findViewById(R.id.exp);
        totbal = findViewById(R.id.tot_bal);

        seesim = findViewById(R.id.sesimg);
        collect_history = findViewById(R.id.collection_history);
        total1 = findViewById(R.id.dayss);
        instal = findViewById(R.id.inamount);
        String sesid = pref.getString("id", "");
        sc1 = findViewById(R.id.scroll1);
        sc2 = findViewById(R.id.scroll2);
        clobal = findViewById(R.id.clo_bal);
        datt = (EditText) findViewById(R.id.search1);
        clos = findViewById(R.id.closedd);
        lll = (findViewById(R.id.linee));
        TextView pro = lll.findViewById(R.id.pro);
        logou = lll.findViewById(R.id.logout);
        profil = lll.findViewById(R.id.profile);
        final LinearLayout lllooo =findViewById(R.id.lolo);
        lllooo.getViewTreeObserver().addOnGlobalLayoutListener(new  ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                lllooo.getWindowVisibleDisplayFrame(r);
                int screenHeight = lllooo.getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;
                if (keypadHeight > screenHeight * 0.15) {

                    save.setVisibility(View.VISIBLE);

//                    Toast.makeText(Collection.this,"Keyboard is showing",Toast.LENGTH_LONG).show();
                } else {

                    save.setVisibility(View.GONE);

//                    Toast.makeText(Collection.this,"keyboard closed",Toast.LENGTH_LONG).show();
                }
            }
        });
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
                sc1.setVisibility(View.VISIBLE);
                sc2.setVisibility(View.GONE);
                datt.setVisibility(View.INVISIBLE);
                clos.setVisibility(View.INVISIBLE);
            }else if (adpr.equalsIgnoreCase("0") || vipr.equalsIgnoreCase("0")) {
                sc1.setVisibility(View.VISIBLE);
                sc2.setVisibility(View.GONE);
                datt.setVisibility(View.INVISIBLE);
                clos.setVisibility(View.INVISIBLE);
            } else{
                sc2.setVisibility(View.VISIBLE);
                sc1.setVisibility(View.GONE);
                datt.setVisibility(View.INVISIBLE);
                clos.setVisibility(View.INVISIBLE);
            }
        }
        exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expo1.equals(0)){
                    expand(lll1,250,LinearLayout.LayoutParams.WRAP_CONTENT);
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
        save = findViewById(R.id.save1);
        save.setEnabled(true);
//        save1.setEnabled(false);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            save.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.spin3));
//            save1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.spin3));
//        } else {
//            save.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.spin3));
//            save1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.spin3));
//
//        }
        LinearLayout relativeLayout = (LinearLayout) findViewById(R.id.lolo);
        relativeLayout.setOnTouchListener(new Collection.OnSwipeTouchListener(Collection.this) {
            public void onSwipeTop() {
                Toast.makeText(Collection.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(Collection.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Intent nee = new Intent(Collection.this, Bulkupdate.class);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                        R.anim.left_to_right, R.anim.left_to_right1).toBundle();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(nee, bundle);
                    finish();
                }
                Toast.makeText(Collection.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(Collection.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });

        LinearLayout relativeLayout1 = (LinearLayout) findViewById(R.id.lino);
        relativeLayout1.setOnTouchListener(new Collection.OnSwipeTouchListener(Collection.this) {
            public void onSwipeTop() {
                Toast.makeText(Collection.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(Collection.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Intent nee = new Intent(Collection.this, Bulkupdate.class);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                        R.anim.left_to_right, R.anim.left_to_right1).toBundle();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(nee, bundle);
                    finish();
                }
                Toast.makeText(Collection.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(Collection.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
        pho = findViewById(R.id.phone);
//        lll = (findViewById(R.id.linee));
//        logou = lll.findViewById(R.id.logout);
//        profil = lll.findViewById(R.id.profile);
        Button theme = lll.findViewById(R.id.theme);
        namee1 = findViewById(R.id.name1);
        namee = findViewById(R.id.name);
        if(in == 0){
            logou.setBackgroundResource(R.drawable.logout);
            profil.setBackgroundResource(R.drawable.user1);
            theme.setBackgroundResource(R.drawable.theme);
            exp.setBackgroundResource(R.drawable.add);
            pho.setCompoundDrawablesWithIntrinsicBounds(R.drawable.phone, 0, 0, 0);
            namee1.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.search_orange, 0);
            namee.setCompoundDrawablesWithIntrinsicBounds( R.drawable.info_orange, 0,0, 0);
        }else{
            logou.setBackgroundResource(R.drawable.logoutblue);
            profil.setBackgroundResource(R.drawable.user_blue);
            exp.setBackgroundResource(R.drawable.add_blue);
            theme.setBackgroundResource(R.drawable.theme1);
            pho.setCompoundDrawablesWithIntrinsicBounds(R.drawable.phone_blue, 0, 0, 0);
            namee1.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.search_blue, 0);
            namee.setCompoundDrawablesWithIntrinsicBounds( R.drawable.info_blue, 0,0, 0);
        }
        namee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    mPopupWindow.showAsDropDown(view, 0, 0, Gravity.START);
                } else {
                    mPopupWindow.showAsDropDown(view, 0,view.getWidth() - mPopupWindow.getWidth());
                }
            }
        });
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Collection.this,Settings_activity.class);
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
                    Intent i = new Intent(Collection.this,NavigationActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    editor.putString("theme","0");
                    editor.apply();
                    Intent i = new Intent(Collection.this,NavigationActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        });

        logou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Collection.this,R.style.AlertDialogTheme);
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
                                Intent i = new Intent(Collection.this, Splash.class);
                                startActivity(i);
                                finish();
                            }
                        });
                alertbox.show();
            }
        });
        slno = findViewById(R.id.slno);

        slno1 = findViewById(R.id.slno1);

        opd = findViewById(R.id.odate);
        mama = findViewById(R.id.bmamount);
        deb = findViewById(R.id.debitamount);
        pam = findViewById(R.id.paid);
        bam = findViewById(R.id.bamount);
        loc = findViewById(R.id.location);

        cd = findViewById(R.id.cdate);

        tod = findViewById(R.id.tdays);
        pad = findViewById(R.id.pdays);
        mid = findViewById(R.id.mdays);
        bad = findViewById(R.id.bdays);
        col = findViewById(R.id.collection);
        dit = findViewById(R.id.discount);
        ofe = findViewById(R.id.ofee);
        ad = findViewById(R.id.add1);
        can = findViewById(R.id.cancel1);
        bul = findViewById(R.id.bulk);

        Calendar b = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
//        b.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));

        SimpleDateFormat df2 = new SimpleDateFormat("hh:mm aaa");

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        String timeZone = Calendar.getInstance().getTimeZone().getID();
        df2.setTimeZone(TimeZone.getTimeZone(timeZone));
        String timee = df2.format(b.getTime());
        time.setText(timee);
        Log.d("timm",timee);
        formattedDate = df.format(c.getTime());
        final String forrrddd = df1.format(c.getTime());
        datt.setText(forrrddd);
        slllno = 0;
        ses = pref.getInt("session", 1);
//        ((Callback)getApplication()).closed(ses);
//        ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
//        ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
//        ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
        balat = pref.getString("totalbal", "");
        Log.d("tot_bal_tot",String.valueOf(balat));
        totbal.setText("\u20B9"+""+balat);
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
        dateee.setText(dd);
        if(in == 0){
            clos.setBackgroundResource(R.drawable.close_button);
            if (ses == 1) {
                timing = getString(R.string.morning);
                seesim.setBackgroundResource(R.drawable.sun);
            } else if (ses == 2) {
                timing = getString(R.string.evening);
                seesim.setBackgroundResource(R.drawable.moon);
            }
        }else{
            clos.setBackgroundResource(R.drawable.close_blue);
            if (ses == 1) {
                timing = getString(R.string.morning);
                seesim.setBackgroundResource(R.drawable.sun_blue);
            } else if (ses == 2) {
                timing = getString(R.string.evening);
                seesim.setBackgroundResource(R.drawable.moon_blue);
            }
        }
        session.setText(timing);
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
        dayy.setText(weekday_name);
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
        }else{
            logy.setBackgroundResource(R.drawable.logoutblue);
        }
        logy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Collection.this,R.style.AlertDialogTheme);
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
                                Intent i = new Intent(Collection.this,Splash.class);
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
                Intent qq = new Intent(Collection.this,NavigationActivity.class);
                startActivity(qq);
                finish();
            }
        });

//        sqlite = new SQLiteHelper(this);
        idd = "0";

        bul.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Intent nee = new Intent(Collection.this, Bulkupdate.class);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                        R.anim.left_to_right, R.anim.left_to_right1).toBundle();
                startActivity(nee, bundle);
                finish();
            }
        });
        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nee = new Intent(Collection.this, NavigationActivity.class);
                startActivity(nee);
                finish();
            }
        });

        slno1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup1122();
            }
        });
        namee1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup1122();
            }
        });
//        save.setEnabled(true);
//        save.setBackgroundResource(R.drawable.login_selector);
//        ad.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Integer cole = 0;
//                String adddd = col.getText().toString();
//                adddd = adddd.replace(" ", "");
//                String slll = slno.getText().toString();
//                Log.d("ui",adddd);
//                if (adddd == null || adddd.equalsIgnoreCase("")  || slll.equalsIgnoreCase("")) {
//                    AlertDialog.Builder alertbox = new AlertDialog.Builder(Collection.this,R.style.AlertDialogTheme);
//                    String enn = getString(R.string.entyer_amount);
//                    String war = getString(R.string.warning);
//                    String ook = getString(R.string.ok);
//                    alertbox.setMessage(enn);
//                    alertbox.setTitle(war);
//                    alertbox.setIcon(R.drawable.logo);
//                    alertbox.setPositiveButton(ook,
//                            new DialogInterface.OnClickListener() {
//
//                                public void onClick(DialogInterface arg0,
//                                                    int arg1) {
//
//                                }
//                            });
//                    alertbox.show();
//                } else {
//                    save.setEnabled(true);
//                    save.setBackgroundResource(R.drawable.login_selector);
//                    save1.setEnabled(true);
//                    save1.setBackgroundResource(R.drawable.login_selector);
////                    add();
//                }
////                cole = Integer.parseInt(adddd);
//
//            }
//        });
        disco = 0;
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Integer cole = 0;
//                String adddd = col.getText().toString();
//                adddd = adddd.replace(" ", "");
//                String slll = slno.getText().toString();
//                if (adddd == null || adddd.equalsIgnoreCase("") || slll.equalsIgnoreCase("")) {
//                    AlertDialog.Builder alertbox = new AlertDialog.Builder(Collection.this,R.style.AlertDialogTheme);
//                    String enn = getString(R.string.entyer_amount);
//                    String war = getString(R.string.warning);
//                    String ook = getString(R.string.ok);
//                    alertbox.setMessage(enn);
//                    alertbox.setTitle(war);
//                    alertbox.setIcon(R.drawable.logo);
//                    alertbox.setPositiveButton(ook,
//                            new DialogInterface.OnClickListener() {
//
//                                public void onClick(DialogInterface arg0,
//                                                    int arg1) {
//
//
//                                }
//                            });
//                    alertbox.show();
//                } else {
//                    String cco = collect;
//                    if(cco == null || cco.equalsIgnoreCase("")){
//                        cco = "0";
//                    }
//                    Integer cc12 = Integer.parseInt(cco);
//                    Integer as = Integer.parseInt(adddd);
//                    Integer bn = (balanceamount - disco) + cc12 ;
//                    Log.d("op",String.valueOf(bn));
//                    if(as > bn){
//
//                        String er2 = String.valueOf(bn);
//                        AlertDialog.Builder alertbox = new AlertDialog.Builder(Collection.this,R.style.AlertDialogTheme);
//                        String enn = getString(R.string.amountgreat);
//                        String enn1 = getString(R.string.amountgreat1);
//                        String war = getString(R.string.warning);
//                        String ook = getString(R.string.ok);
//                        alertbox.setMessage(enn+" "+"\u20B9"+" "+er2+" "+enn1);
//                        alertbox.setTitle(war);
//                        alertbox.setIcon(R.drawable.logo);
//                        alertbox.setPositiveButton(ook,
//                                new DialogInterface.OnClickListener() {
//
//                                    public void onClick(DialogInterface arg0,
//                                                        int arg1) {
//                                    }
//                                });
//                        alertbox.show();
//                    }else{
//                        add();
//                        Log.d("erer",formattedDate);
//                        ((Callback)getApplication()).closed(ses);
//                        ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
//                        ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
//                        ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
//                        if(as.equals(bn)){
//                            AlertDialog.Builder alertbox = new AlertDialog.Builder(Collection.this,R.style.AlertDialogTheme);
//                            String enn = getString(R.string.newdebitalert);
//                            String war = getString(R.string.warning);
//                            String ook = getString(R.string.ok);
//                            String ook1 = getString(R.string.cancel);
//
//                            alertbox.setMessage(enn);
//                            alertbox.setTitle(war);
//                            alertbox.setIcon(R.drawable.logo);
//                            alertbox.setNegativeButton(ook1,
//                                    new DialogInterface.OnClickListener() {
//
//                                        public void onClick(DialogInterface arg0,
//                                                            int arg1) {
//                                            Intent ui = new Intent(Collection.this,Collection.class);
//                                            startActivity(ui);
//                                        }
//                                    });
//                            alertbox.setPositiveButton(ook,
//                                    new DialogInterface.OnClickListener() {
//
//                                        public void onClick(DialogInterface arg0,
//                                                            int arg1) {
//                                            String qww = slno.getText().toString();
//                                            Intent deb= new Intent(Collection.this, Newdebit.class);
//                                            deb.putExtra("idi",qww);
//                                            deb.putExtra("type","2");
//                                            startActivity(deb);
//                                        }
//                                    });
//                            alertbox.show();
//                        }else{
//                            Intent intent = new Intent(Collection.this, Collection.class);
//                            startActivity(intent);
//                        }
//                    }
//                }
////                cole = Integer.parseInt(adddd);
//
//            }
//        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("bbaa",String.valueOf(balanceamount));
//                Integer cole = 0;

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String disc = dit.getText().toString();
                                String adddd = col.getText().toString();
                                Log.d("discount",disc);
                                adddd = adddd.replace(" ", "");
                                String slll = slno.getText().toString();
                                if (adddd == null || adddd.equalsIgnoreCase("") || slll.equalsIgnoreCase("")) {
                                    if(disc == null || disc.equalsIgnoreCase("")) {
                                        AlertDialog.Builder alertbox = new AlertDialog.Builder(Collection.this,R.style.AlertDialogTheme);
                                        String enn = getString(R.string.entyer_amount);
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

                                    } else {
                                        save.setClickable(false);
                                        String cco = collect;
                                        if(cco == null || cco.equalsIgnoreCase("")){
                                            cco = "0";
                                        }
                                        if(adddd == null || adddd.equalsIgnoreCase("")){
                                            adddd = "0";
                                        }
                                        if(disc == null || disc.equalsIgnoreCase("")){
                                            disc = "0";
                                        }
                                        Integer cc12 = Integer.parseInt(cco);
                                        Integer as = Integer.parseInt(adddd);
                                        Integer ds = Integer.parseInt(disc);
                                        as = as + ds;
                                        Integer bn = (balanceamount) + cc12 ;
                                        Log.d("op1",String.valueOf(as));
                                        Log.d("op1",String.valueOf(bn));
                                        Log.d("op1",String.valueOf(cc12));
                                        if(as > bn){
                                            runOnUiThread(new Runnable()
                                            {
                                                @Override
                                                public void run()
                                                {
                                                    progre();
                                                }
                                            });
                                            String er2 = String.valueOf(bn);
                                            AlertDialog.Builder alertbox = new AlertDialog.Builder(Collection.this,R.style.AlertDialogTheme);
                                            String enn = getString(R.string.amountgreat);
                                            String enn1 = getString(R.string.amountgreat1);
                                            String war = getString(R.string.warning);
                                            String ook = getString(R.string.ok);
                                            alertbox.setMessage(enn+" "+"\u20B9"+" "+er2+" "+enn1);
                                            alertbox.setTitle(war);
                                            alertbox.setIcon(R.drawable.logo);

                                            alertbox.setPositiveButton(ook,
                                                    new DialogInterface.OnClickListener() {

                                                        public void onClick(DialogInterface arg0,
                                                                            int arg1) {

                                                        }
                                                    });
                                            alertbox.show();
                                        }
                                        else{
                                            if(ccid == null || ccid.equalsIgnoreCase("")){
                                                add();
                                            }else{
                                                add1();
                                            }
                                            Log.d("erer",formattedDate);
                                            Log.d("allcollection_date2",formattedDate);
                                            ((Callback)getApplication()).closed(ses);
                                            ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
                                            ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
                                            ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
                                            if(as.equals(bn)){
                                                runOnUiThread(new Runnable()
                                                {
                                                    @Override
                                                    public void run()
                                                    {
                                                        progre();
                                                    }
                                                });
                                                AlertDialog.Builder alertbox = new AlertDialog.Builder(Collection.this,R.style.AlertDialogTheme);
                                                String enn = getString(R.string.newdebitalert);
                                                String war = getString(R.string.warning);
                                                String ook = getString(R.string.ok);
                                                String ook1 = getString(R.string.cancel);
                                                alertbox.setCancelable(false);
                                                alertbox.setMessage(enn);
                                                alertbox.setTitle(war);
                                                alertbox.setIcon(R.drawable.logo);
                                                alertbox.setNegativeButton(ook1,
                                                        new DialogInterface.OnClickListener() {

                                                            public void onClick(DialogInterface arg0,
                                                                                int arg1) {
                                                                Toast.makeText(Collection.this,"Updated Successfully" ,Toast.LENGTH_LONG).show();
                                                                wm.removeViewImmediate(btn_floatView);
                                                                Intent nhnh = new Intent(Collection.this,Collection.class);
                                                                startActivity(nhnh);

                                                            }
                                                        });
                                                alertbox.setPositiveButton(ook,
                                                        new DialogInterface.OnClickListener() {

                                                            public void onClick(DialogInterface arg0,
                                                                                int arg1) {
                                                                String qww = slno.getText().toString();
                                                                Intent deb= new Intent(Collection.this, Newdebit.class);
                                                                deb.putExtra("idi",qww);
                                                                deb.putExtra("type","2");
                                                                startActivity(deb);
                                                                finish();
                                                            }
                                                        });
                                                alertbox.show();
                                            }else{
                                                runOnUiThread(new Runnable()
                                                {
                                                    @Override
                                                    public void run()
                                                    {
                                                        progre();
                                                    }
                                                });
                                                Toast.makeText(Collection.this,"Updated Successfully" ,Toast.LENGTH_LONG).show();
                                                wm.removeViewImmediate(btn_floatView);
                                                Intent intent = new Intent(Collection.this, Collection.class);
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                }
                                else {
//                        save.setClickable(false);
                                    String cco = collect;
                                    if(cco == null || cco.equalsIgnoreCase("")){
                                        cco = "0";
                                    }
                                    Integer cc12 = Integer.parseInt(cco);
                                    Integer as = Integer.parseInt(adddd);
                                    if(disc == null || disc.equalsIgnoreCase("")){
                                        disc = "0";
                                    }
                                    Integer ds = Integer.parseInt(disc);
                                    as = as + ds;
                                    if(balanceamount == null){
                                        balanceamount = 0;
                                    }
                                    Log.d("balaaa",String.valueOf(balanceamount));
                                    Log.d("balaaa1",String.valueOf(cc12));
                                    Integer bn = (balanceamount) + cc12 ;
                                    Log.d("op1",String.valueOf(as));
                                    Log.d("op1",String.valueOf(bn));
                                    Log.d("op1",String.valueOf(cc12));
                                    if(as > bn){
                                        runOnUiThread(new Runnable()
                                        {
                                            @Override
                                            public void run()
                                            {
                                                progre();
                                            }
                                        });
                                        String er2 = String.valueOf(bn);
                                        AlertDialog.Builder alertbox = new AlertDialog.Builder(Collection.this,R.style.AlertDialogTheme);
                                        String enn = getString(R.string.amountgreat);
                                        String enn1 = getString(R.string.amountgreat1);
                                        String war = getString(R.string.warning);
                                        String ook = getString(R.string.ok);
                                        alertbox.setMessage(enn+" "+"\u20B9"+" "+er2+" "+enn1);
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
                                        save.setClickable(false);
                                        if(ccid == null || ccid.equalsIgnoreCase("")){
                                            add();
                                        }else{
                                            add1();
                                        }
                                        Log.d("erer",formattedDate);
                                        Log.d("allcollection_date3",formattedDate);
                                        ((Callback)getApplication()).closed(ses);
                                        ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
                                        ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
                                        ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
                                        if(as.equals(bn)){
                                            runOnUiThread(new Runnable()
                                            {
                                                @Override
                                                public void run()
                                                {
                                                    progre();
                                                }
                                            });
                                            save.setClickable(false);
                                            AlertDialog.Builder alertbox = new AlertDialog.Builder(Collection.this,R.style.AlertDialogTheme);
                                            String enn = getString(R.string.newdebitalert);
                                            String war = getString(R.string.warning);
                                            String ook = getString(R.string.ok);
                                            String ook1 = getString(R.string.cancel);
                                            alertbox.setCancelable(false);
                                            alertbox.setMessage(enn);
                                            alertbox.setTitle(war);
                                            alertbox.setIcon(R.drawable.logo);
                                            alertbox.setNegativeButton(ook1,
                                                    new DialogInterface.OnClickListener() {

                                                        public void onClick(DialogInterface arg0,
                                                                            int arg1) {
                                                            Toast.makeText(Collection.this,"Updated Successfully" ,Toast.LENGTH_LONG).show();
                                                            wm.removeViewImmediate(btn_floatView);
                                                            Intent nhnh = new Intent(Collection.this,Collection.class);
                                                            startActivity(nhnh);
                                                        }
                                                    });
                                            alertbox.setPositiveButton(ook,
                                                    new DialogInterface.OnClickListener() {

                                                        public void onClick(DialogInterface arg0,
                                                                            int arg1) {
                                                            String qww = slno.getText().toString();
                                                            Intent deb= new Intent(Collection.this, Newdebit.class);
                                                            deb.putExtra("idi",qww);
                                                            deb.putExtra("type","2");
                                                            startActivity(deb);
                                                            finish();
                                                        }
                                                    });
                                            alertbox.show();
                                        }else{
                                            runOnUiThread(new Runnable()
                                            {
                                                @Override
                                                public void run()
                                                {
                                                    progre();
                                                }
                                            });
                                            save.setClickable(false);
                                            Toast.makeText(Collection.this,"Updated Successfully" ,Toast.LENGTH_LONG).show();
                                            wm.removeViewImmediate(btn_floatView);
                                            Intent intent = new Intent(Collection.this, Collection.class);
                                            startActivity(intent);
                                        }
                                    }
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


            }

//                cole = Integer.parseInt(adddd);

        });
        pho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ppooo.size()>0){
                    pphhoonnee(ppooo);
                }else{

                }
//                String p = pho.getText().toString();
//                if (p.equalsIgnoreCase("")) {
//                    p = "0";
//                }
////                Integer pp = Integer.parseInt(p);
//                if (p.equalsIgnoreCase("0")) {
//
//                } else {
//                    p = "+91"+p;
//                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + p));
//                    if (ContextCompat.checkSelfPermission(Collection.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(Collection.this,new String[]{Manifest.permission.CALL_PHONE} ,1);
//                    }
//                    else
//                    {
//                        startActivity(intent);
//                    }
//                }
            }
        });
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
            datt.setText(datedd);
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
        list();
        list1();
        populateRecyclerView();
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
        if(in == 0){
            Log.d("thee","thh");
            datt.setBackgroundResource(R.drawable.search_widget);
        }else{
            Log.d("thee","th1h");
            datt.setBackgroundResource(R.drawable.search_widget1);
//            recreate();
        }
        datt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (in == 0){
                    datePickerDialog =  new DatePickerDialog(Collection.this,R.style.DialogTheme, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH ));
                }else{
                    datePickerDialog =  new DatePickerDialog(Collection.this,R.style.DialogTheme1, date, myCalendar
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
                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat, Locale.US);

//                datt.setText(sdf1.format(myCalendar.getTime()));
            }
        });
        col.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                save.setClickable(true);
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        dit.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        ofe.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        slno.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                col.setFocusable(true);
                col.setFocusableInTouchMode(true);
                dit.setFocusable(true);
                dit.setFocusableInTouchMode(true);
                ofe.setFocusable(true);
                ofe.setFocusableInTouchMode(true);
                return false;
            }
        });
//        col.addTextChangedListener(new TextWatcher() {
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//                if (s.length() > 0) { //do your work here }
//            col.setHintTextColor((ContextCompat.getColor(getApplicationContext(), R.color.Black)));
//            Log.d("colorlll","1");
//                }
//            }
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
////                col.setHintTextColor((ContextCompat.getColor(getApplicationContext(), R.color.Gray)));
//                Log.d("colorlll","2");
//            }
//            public void afterTextChanged(Editable s) {
////                col.setHintTextColor((ContextCompat.getColor(getApplicationContext(), R.color.Gray)));
//                Log.d("colorlll","3");
//            }
//        });
        slno.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() >= 0)
                {
                    String bamount = slno.getText().toString();
                    if(bamount.equalsIgnoreCase("")){
                        namee.setText("");
                        opd.setText("");
                        deb.setText("");
                        loc.setText("");
                        pho.setText("");
                        cd.setText("");
                        tod.setText("");
                        col.setText("");
                        String enn = getString(R.string.total_days);
                        String enn1 = getString(R.string.installment);
                        total1.setText(enn+" : "+"");
                        instal.setText(enn1+" : "+"");
                        pad.setText("");
                        pam.setText("");
                        bad.setText("");
                        bam.setText("");
                        mid.setText("");
                        mama.setText("");
                        ofe.setText("");
                    }else {
                        idd = String.valueOf(bamount);
                        Log.d("iid",idd);
                        Log.d("iid","1");

//                        for(int i=0;i<=0;i++){
                        list();
//                            break;
//                        }
////                        calculate();
                    }
//                Toast.makeText(getApplicationContext(), String.valueOf(interr), Toast.LENGTH_SHORT).show();
                    return;
                }


            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
            public void afterTextChanged(Editable s) {
//                dateString = datt.getText().toString();
////                Log.d("datata",dateString);
//                String bamount = slno.getText().toString();
//                if(bamount.equalsIgnoreCase("")){
//                }else {
//                    idd = String.valueOf(bamount);
//                    Log.d("iid",idd);
//                    list();
//                }
////                Toast.makeText(getApplicationContext(), String.valueOf(interr), Toast.LENGTH_SHORT).show();
//                calculate();
            }
        });
//        balanceamount = 0;
//        col.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//                if (s.length() >= 0)
//                {
//                    String bamount = slno.getText().toString();
//                    if(bamount.equalsIgnoreCase("")){
//
//                    }else {
//                      Integer bb = Integer.parseInt(bamount);
//                      Log.d("erere",String.valueOf(balanceamount));
//                      if(bb > balanceamount){
//                          AlertDialog.Builder alertbox = new AlertDialog.Builder(Collection.this,R.style.AlertDialogTheme);
//                          String logmsg = getString(R.string.logout_alert);
//                          String cann = getString(R.string.cancel);
//                          String warr = getString(R.string.warning);
//                          String logo = getString(R.string.Logout);
//                          alertbox.setMessage(logmsg);
//                          alertbox.setTitle(warr);
//                          alertbox.setIcon(R.drawable.dailylogo);
//                          alertbox.setNeutralButton(cann,
//                                  new DialogInterface.OnClickListener() {
//                                      public void onClick(DialogInterface arg0,
//                                                          int arg1) {
//                                      }
//                                  });
//
//                          alertbox.show();
//                      }
//                    }
////                Toast.makeText(getApplicationContext(), String.valueOf(interr), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//
//            }
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//
//            }
//            public void afterTextChanged(Editable s) {
////
//            }
//        });

//        slno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    String bamount = slno.getText().toString();
//                    if(bamount.equalsIgnoreCase("")){
//                    }else {
//                        idd = String.valueOf(bamount);
//                        Log.d("iid",idd);
//                        list();
//                    }
////                    Toast.makeText(getApplicationContext(), String.valueOf(interr), Toast.LENGTH_SHORT).show();
////                    calclate();
//                }
//            }
//        });
        clos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
                formattedDate = df.format(c.getTime());
                String forrrddd = df1.format(c.getTime());
                datt.setText(forrrddd);
                list1();
                populateRecyclerView();
            }
        });
        collect_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(iii == null){

                }else{
                    popup1();
                    Log.d("iiid",iii);
                }
            }
        });
//        list1();
    }

    //pphhoonnee() - show user numbers
    //Params - Number array
    //Created on 25/01/2022
    //Return - NULL
    public void pphhoonnee(ArrayList<String> Names1){
        final Dialog dialog = new Dialog(Collection.this);
        //set layout custom
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popnumber);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);
        final RecyclerView rvcaddy = (RecyclerView) dialog.findViewById(R.id.rvAnimals);

        final mobilenumber_adapter1 adapter = new mobilenumber_adapter1(getApplicationContext(),Names1,Collection.this);
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
//            noo.setVisibility(View.VISIBLE);
        }else {
            rvcaddy.setVisibility(View.VISIBLE);
//            noo.setVisibility(View.GONE);
            adapter.setHasStableIds(true);
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

    //call() - Call numbers
    //Params - Number
    //Created on 25/01/2022
    //Return - NULL
    public void call(String animal) {
        String p = "+91"+animal;
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + p));
        if (ContextCompat.checkSelfPermission(Collection.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Collection.this,new String[]{Manifest.permission.CALL_PHONE} ,1);
        }
        else
        {
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    //onRestart() - Called when activity is restarted
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    protected void onRestart() {
        wm = (WindowManager) Collection.this.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        createFloatView();
        super.onRestart();
    }

    //onPause() - Called when activity is paused
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    protected void onPause() {
        if(btn_floatView.getWindowToken() != null){
            wm.removeViewImmediate(btn_floatView);
        }
        super.onPause();
    }

    //onDestroy() - Called when activity is destroyed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    protected void onDestroy() {
        if(btn_floatView.getWindowToken() != null){
            wm.removeViewImmediate(btn_floatView);
        }


        super.onDestroy();
    }


    //createFloatView() - Creates an button float
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void createFloatView()    {
        btn_floatView = new ImageView(Collection.this.getApplicationContext());
//        btn_floatView.setText("Floating window");
//        btn_floatView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("imageview","touch");
//            }
//        });
        Bitmap kangoo = null;
        if(in == 0){
            Log.d("thee","thh");
            kangoo = BitmapFactory.decodeResource(getResources(),
                    R.drawable.dailylogo);
        }else{
            Log.d("thee","th1h");
            kangoo = BitmapFactory.decodeResource(getResources(),
                    R.drawable.dailylogo);
//            recreate();
        }

//        btn_floatView.setImageBitmap(kangoo);
//        final WindowManager wm = (WindowManager) Collection.this.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        final WindowManager.LayoutParams   params = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //  Set Window Type
        //params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){//6.0+
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            params.type =  WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }

        /*  * If set to params.Type = WindowManager.LayoutParams.Type_Phone; then priority will reduce some,
         *                       */
        params.format = PixelFormat.RGBA_8888;
        //  Set the image format, the effect is the background transparent
        //  Set WINDOW FLAG
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        /*  * The following Flags properties is the same as "lock". The floating window is not touched, does not accept any events, and does not affect the following event responses.
         * wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL| LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCHABLE;
         * */
        //  Set the length of the floating window
        int widthwidth = (int) getResources().getDimension(R.dimen.register_height1);
        int heightheight = (int) getResources().getDimension(R.dimen.register_height1);

        params.width = widthwidth;
        params.height = heightheight;
        SharedPreferences prefxy = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        int pxx = prefxy.getInt("paramsx",0);
        int pyy = prefxy.getInt("paramsy",0);
        if(pxx == 0 && pyy == 0){
            params.x=0;
            params.y=-1080;
        }else{
            params.x = pxx;
            params.y = pyy;
        }
        //  Set the Touch listening of a floating window
//        final ImageView finalBtn_floatView = btn_floatView;
//        final Bitmap kangoo1 = BitmapFactory.decodeResource(getResources(),
//                R.drawable.account);
        btn_floatView.setImageBitmap(kangoo);
        btn_floatView.setOnTouchListener(new View.OnTouchListener() {
            int lastX, lastY;
            int paramX, paramY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("imageview","touch2");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        paramX = params.x;
                        paramY = params.y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        params.x = paramX + dx;
                        params.y = paramY + dy;
                        Log.d("imageviewparams",String.valueOf(params));
                        //  Update suspension window position
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putInt("paramsx",params.x);
                        editor.putInt("paramsy",params.y);
                        editor.apply();
                        wm.updateViewLayout(btn_floatView, params);
                        break;
                    case MotionEvent.ACTION_BUTTON_PRESS:
                        Log.d("imageview","touch3");
                        break;

                }                return false;
            }
        });
        btn_floatView.setOnClickListener(new DoubleTapListener(this));  //<-- Set listener
//        btn_floatView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });
        wm.addView(btn_floatView, params);
        wm.updateViewLayout(btn_floatView, params);
//        detecting = true;
    }

    //onDoubleClick() - Called when button is double clicked
    //Params - View
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onDoubleClick(View v) {
        // Toast to show double click
        popup();
        Log.d("imageview1","touch");
    }
    //popup1122() - get all current and NIP user
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void popup1122(){
        Names.clear();
        ID.clear();
        collectoo = 0;
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ii = pref.getString("selectedvalue","");
        if(ii == null || ii.equalsIgnoreCase("")){
            ii = sele12 ;
        }else{
        }
        Log.d("ffoorr",formattedDate);
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY1 = "SELECT cus.*,deb.customer_id,col.collection_amount as collect,sum(col.collection_amount)as paid ,deb.debit_amount,deb.debit_date,deb.id as did FROM dd_customers cus " +
                " LEFT JOIN dd_account_debit deb on deb.customer_id = cus.id" +
                " LEFT JOIN dd_collection col on deb.id = col.debit_id " +
                "  WHERE cus.tracking_id = ? AND deb.debit_date <= ? AND deb.active_status = 1 AND  cus.debit_type IN(0,1) GROUP BY cus.id ORDER BY cus.order_id_new ASC";
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{String.valueOf(ses),formattedDate});
//        String MY_QUERY = "SELECT (SELECT collection_amount from dd_collection where collection_date = ? AND customer_id = b.id) AS collect,b.customer_name" +
//                ",b.id,b.tracking_id,b.order_id,b.CID,b.debit_type,c.debit_amount,c.id as cidd FROM dd_customers b " +
//                "LEFT JOIN dd_collection a on b.id =  a.customer_id LEFT JOIN dd_account_debit c on b.id =  c.customer_id WHERE b.tracking_id = ? AND c.debit_date <= ? AND b.debit_type IN (0,1,2) GROUP BY b.id  ORDER BY b.order_id ASC ";
//        String[] whereArgs = new String[] {formattedDate,String.valueOf(ses),formattedDate};
//        Cursor cursor = database.rawQuery(MY_QUERY,whereArgs);
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    long id = cursor.getLong(index);
                    idd= String.valueOf(id);

                    index = cursor.getColumnIndexOrThrow("did");
                    long did = cursor.getLong(index);
                    didd= String.valueOf(did);

                    index = cursor.getColumnIndexOrThrow("collect");
                    String debbbb = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("CID");
                    String ccc = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String orderrr = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String typ = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debbbbi = cursor.getString(index);
                    Log.d("names121", String.valueOf(didd));
                    if(debbbbi != null) {
                        if (debbbb == null || debbbb.equalsIgnoreCase("0")) {
                            slllno = slllno + 1;
                            if (typ.equalsIgnoreCase("0")) {
                                Names.add(ccc + "," + Name+","+typ + "," + ccc);
                                ID.add(ccc + "," +didd);
                            }if (typ.equalsIgnoreCase("1")) {
                                Names.add(ccc + "," + Name+","+typ + "," + ccc);
                                ID.add(ccc + "," +didd);}
                            if (typ.equalsIgnoreCase("2")) {
                                Names.add(ccc + "," + Name+","+typ + "," + ccc);
                                ID.add(ccc + "," +didd);
                            }
                        } else {
                            slllno = slllno + 1;
                            if (typ.equalsIgnoreCase("0")) {
                                Names.add(ccc + "," + Name+","+typ + "," + ccc);
                                ID.add(ccc + "," +didd);
                            }if (typ.equalsIgnoreCase("1")) {
                                Names.add(ccc + "," + Name+","+typ + "," + ccc);
                                ID.add(ccc + "," +didd);}
                            if (typ.equalsIgnoreCase("2")) {
                                Names.add(ccc + "," + Name+","+typ + "," + ccc);
                                ID.add(ccc + "," +didd);
                            }

//                            Log.d("names12", String.valueOf(didd));
                        }
                    }
                }
                while (cursor.moveToNext());
                popup11222();
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                popup11222();
                cursor.close();
                sqlite.close();
                database.close();
            }
        }else{
            popup11222();
            cursor.close();
            sqlite.close();
            database.close();
        }
    }
    //popup11222() - get all NIPNIP user
    //Params - View
    //Created on 25/01/2022
    //Return - NULL
    public void popup11222(){
//        Names.clear();
//        ID.clear();
//        collectoo = 0;
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ii = pref.getString("selectedvalue","");
        if(ii == null || ii.equalsIgnoreCase("")){
            ii = sele12 ;
        }else{
        }
        Log.d("ffoorr",formattedDate);
        database = sqlite.getReadableDatabase();
        String MY_QUERY1 = "SELECT cus.*,deb.customer_id,col.collection_amount as collect,sum(col.collection_amount)as paid ,deb.debit_amount,deb.debit_date,deb.id as did FROM dd_customers cus " +
                " LEFT JOIN dd_account_debit deb on deb.customer_id = cus.id " +
                " LEFT JOIN dd_collection col on deb.id = col.debit_id " +
                "  WHERE cus.tracking_id = ? AND deb.debit_date <= ? AND deb.active_status = 1 AND  cus.debit_type IN(2) GROUP BY cus.id ORDER BY cus.order_id_new ASC";
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{String.valueOf(ses),formattedDate});
//        String MY_QUERY = "SELECT (SELECT collection_amount from dd_collection where collection_date = ? AND customer_id = b.id) AS collect,b.customer_name" +
//                ",b.id,b.tracking_id,b.order_id,b.CID,b.debit_type,c.debit_amount,c.id as cidd FROM dd_customers b " +
//                "LEFT JOIN dd_collection a on b.id =  a.customer_id LEFT JOIN dd_account_debit c on b.id =  c.customer_id WHERE b.tracking_id = ? AND c.debit_date <= ? AND b.debit_type IN (0,1,2) GROUP BY b.id  ORDER BY b.order_id ASC ";
//        String[] whereArgs = new String[] {formattedDate,String.valueOf(ses),formattedDate};
//        Cursor cursor = database.rawQuery(MY_QUERY,whereArgs);
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    long id = cursor.getLong(index);
                    idd= String.valueOf(id);

                    index = cursor.getColumnIndexOrThrow("did");
                    long did = cursor.getLong(index);
                    didd= String.valueOf(did);

                    index = cursor.getColumnIndexOrThrow("collect");
                    String debbbb = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("CID");
                    String ccc = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String orderrr = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String typ = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debbbbi = cursor.getString(index);
                    Log.d("names12", String.valueOf(didd));
                    if(debbbbi != null) {
                        if (debbbb == null || debbbb.equalsIgnoreCase("0")) {
                            slllno = slllno + 1;
                            if (typ.equalsIgnoreCase("0")) {
                                Names.add(ccc + "," + Name+","+typ + "," + ccc);
                                ID.add(ccc + "," +didd);
                            }if (typ.equalsIgnoreCase("1")) {
                                Names.add(ccc + "," + Name+","+typ + "," + ccc);
                                ID.add(ccc + "," +didd);}
                            if (typ.equalsIgnoreCase("2")) {
                                Names.add(ccc + "," + Name+","+typ + "," + ccc);
                                ID.add(ccc + "," +didd);
                            }
                        } else {
                            slllno = slllno + 1;
                            if (typ.equalsIgnoreCase("0")) {
                                Names.add(ccc + "," + Name+","+typ + "," + ccc);
                                ID.add(ccc + "," +didd);
                            }if (typ.equalsIgnoreCase("1")) {
                                Names.add(ccc + "," + Name+","+typ + "," + ccc);
                                ID.add(ccc + "," +didd);}
                            if (typ.equalsIgnoreCase("2")) {
                                Names.add(ccc + "," + Name+","+typ + "," + ccc);
                                ID.add(ccc + "," +didd);
                            }
//                            Log.d("names12", String.valueOf(didd));
                        }
                    }
                }
                while (cursor.moveToNext());
            }
        }
        final Dialog dialog = new Dialog(Collection.this);
        //set layout custom
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popnames);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);
        final RecyclerView rvcaddy = (RecyclerView) dialog.findViewById(R.id.rvAnimals);
        final MyRecyclerViewAdapter12 adapter = new MyRecyclerViewAdapter12(getApplicationContext(),Names, ID, 1,Collection.this);
        searchView = (EditText) dialog.findViewById(R.id.search);
        searchid = (EditText) dialog.findViewById(R.id.searchid);
        noo =(TextView) dialog.findViewById(R.id.no);
//        searchView.setQueryHint("Google Search");
        Button close = (Button) dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        searchid.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0)
                {
                    adapter.filter1(String.valueOf(s));
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            public void afterTextChanged(Editable s) {
//                dateString = datt.getText().toString();
//                Log.d("datata",dateString);
                adapter.filter1(String.valueOf(s));
            }
        });
        searchView.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0)
                {
                    adapter.filter(String.valueOf(s));
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            public void afterTextChanged(Editable s) {
//                dateString = datt.getText().toString();
//                Log.d("datata",dateString);
                adapter.filter(String.valueOf(s));
            }
        });
        if(Names.size() == 0){
            rvcaddy.setVisibility(View.GONE);
            noo.setVisibility(View.VISIBLE);
        }else {
            rvcaddy.setVisibility(View.VISIBLE);
            noo.setVisibility(View.GONE);
            rvcaddy.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvcaddy.setLayoutManager(mLayoutManager);
            rvcaddy.smoothScrollToPosition(Integer.parseInt(ii)); //use to focus the item with index
        }

        dialog.show();
    }

    //list1() - get closed debits
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
                    Log.d("ins31", String.valueOf(dabamo));
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

    //onBackPressed() - function called when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onBackPressed() {
        wm.removeViewImmediate(btn_floatView);
        Intent nn = new Intent(Collection.this,AllCollection.class);
        startActivity(nn);
        finish();
    }

    //inte() - Intent to refresh the page
    //Params - customer id , debit id and position
    //Created on 25/01/2022
    //Return - NULL
    public void inte(String d, String d1,String pos) {
        wm.removeViewImmediate(btn_floatView);
        Intent name = new Intent(Collection.this, Collection.class);
        name.putExtra("ID", d);
        name.putExtra("DID", d1);
        name.putExtra("selecte",pos);
        name.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(name);
    }

    public void delete_popup(String sSs1) {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(Collection.this,R.style.AlertDialogTheme);
        String logmsg = getString(R.string.are_you_sure_delete_collection);
        String cann = getString(R.string.cancel);
        String warr = getString(R.string.warning);
        String logo = getString(R.string.ok);
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
//                        SQLiteHelper sqlite = new SQLiteHelper(Collection.this);
//                        SQLiteDatabase database = sqlite.getWritableDatabase();
//                        String table = "dd_collection";
//                        String whereClause = "id=?";
//                        String[] whereArgs = new String[] {sSs1};
//                        database.delete(table, whereClause, whereArgs);
//                        sqlite.close();
//                        database.close();
//                        dialog.dismiss();
//                        popup1();
                    }
                });
        alertbox.show();
    }

    public class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener (Context ctx){
            gestureDetector = new GestureDetector(ctx, new GestureListener());
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                            result = true;
                        }
                    }
                    else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
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
//        dateString = sdf.format(myCalendar.getTime());
        formattedDate = sdf.format(myCalendar.getTime());
        datt.setText(sdf1.format(myCalendar.getTime()));
        Log.d("jk",formattedDate);
        editor.putString("coldate",formattedDate);
        editor.apply();
        list1();
        populateRecyclerView();

        if(idd.equalsIgnoreCase("") || idd.equalsIgnoreCase("0")){
        }else{
            Log.d("jk",formattedDate);
            list();
        }
//        mAdapter.filter(dateString);
//        Log.d("hjyj",dateString);
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
            Intent new1 = new Intent(Collection.this,MainActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.user) {
            Intent new1 = new Intent(Collection.this,HomeActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.debit) {
            Intent new1 = new Intent(Collection.this,Debit.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.collection) {
            wm.removeViewImmediate(btn_floatView);
            Intent new1 = new Intent(Collection.this,AllCollection.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.account) {
            Intent new1 = new Intent(Collection.this,Account.class);
            startActivity(new1);
            finish();
        }  else if (id == R.id.report) {
            Intent new1 = new Intent(Collection.this,ReportActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.customer) {
            Intent new1 = new Intent(Collection.this,Customer_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.settings) {
            Intent new1 = new Intent(Collection.this,Settings_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.tally) {
            Intent new1 = new Intent(Collection.this,Tally_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.employee) {
            Intent new1 = new Intent(Collection.this,Employee_details.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.today) {
            Intent new1 = new Intent(Collection.this,Today_report.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.logout) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Collection.this,R.style.AlertDialogTheme);
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
                            Intent i = new Intent(Collection.this,Splash.class);
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
    //today() - get total collection
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void today(){
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        String[] columns = {"debit_amount",
                "id", ""};
        String ii = "1";
        String SUM_QUERY = "SELECT sum(collection_amount) as paid FROM dd_collection WHERE customer_id = ?";
        Cursor cur = database.rawQuery(SUM_QUERY,new String[]{idd});

        if (cur != null) {
            if (cur.getCount() != 0) {
                cur.moveToFirst();
                do {
                    int index;
                    index = cur.getColumnIndexOrThrow("paid");
                    Integer orde = cur.getInt(index);
                    pam.setText("Paid :" + " " + String.valueOf(orde));
                    Log.d("paiddd", String.valueOf(orde));
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

    //popup() - get users of current and NIP
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void popup(){
        Names.clear();
        ID.clear();
        collectoo = 0;
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ii = pref.getString("selectedvalue","");
        if(ii == null || ii.equalsIgnoreCase("")){
            ii = sele12 ;
        }else{
        }
        Log.d("ffoorr",formattedDate);
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY1 = "SELECT cus.*,deb.customer_id,col.collection_amount as collect,sum(col.collection_amount)as paid ,deb.debit_amount,deb.debit_date,deb.id as did FROM dd_customers cus " +
                " LEFT JOIN dd_account_debit deb on deb.customer_id = cus.id " +
                "  LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id from dd_collection WHERE collection_date = ? GROUP BY customer_id ) col on  deb.id = col.debit_id  AND col.collection_date = ?" +
                "  WHERE cus.tracking_id = ? AND deb.debit_date <= ? AND deb.active_status = 1 AND  cus.debit_type IN(0,1) GROUP BY cus.id ORDER BY cus.order_id_new ASC";
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{formattedDate,formattedDate,String.valueOf(ses),formattedDate});
//        String MY_QUERY = "SELECT (SELECT collection_amount from dd_collection where collection_date = ? AND customer_id = b.id) AS collect,b.customer_name" +
//                ",b.id,b.tracking_id,b.order_id,b.CID,b.debit_type,c.debit_amount,c.id as cidd FROM dd_customers b " +
//                "LEFT JOIN dd_collection a on b.id =  a.customer_id LEFT JOIN dd_account_debit c on b.id =  c.customer_id WHERE b.tracking_id = ? AND c.debit_date <= ? AND b.debit_type IN (0,1,2) GROUP BY b.id  ORDER BY b.order_id ASC ";
//        String[] whereArgs = new String[] {formattedDate,String.valueOf(ses),formattedDate};
//        Cursor cursor = database.rawQuery(MY_QUERY,whereArgs);
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    long id = cursor.getLong(index);
                    idd= String.valueOf(id);

                    index = cursor.getColumnIndexOrThrow("did");
                    long did = cursor.getLong(index);
                    didd= String.valueOf(did);

                    index = cursor.getColumnIndexOrThrow("collect");
                    String debbbb = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("CID");
                    String ccc = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String orderrr = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String typ = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debbbbi = cursor.getString(index);
                    Log.d("names121", String.valueOf(didd));
                    if(debbbbi != null) {
                        if (debbbb == null || debbbb.equalsIgnoreCase("0")) {
                            slllno = slllno + 1;
                            if (typ.equalsIgnoreCase("0")) {
                                Names.add(ccc + "," + Name+","+typ + "," + ccc);
                                ID.add(ccc + "," +didd);
                            }if (typ.equalsIgnoreCase("1")) {
                                Names.add(ccc + "," + Name+","+typ + "," + ccc);
                                ID.add(ccc + "," +didd);}
                            if (typ.equalsIgnoreCase("2")) {
                                Names.add(ccc + "," + Name+","+typ + "," + ccc);
                                ID.add(ccc + "," +didd);
                            }
                        } else {


//                            Log.d("names12", String.valueOf(didd));
                        }
                    }
                }
                while (cursor.moveToNext());
                popup12();
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                popup12();
                cursor.close();
                sqlite.close();
                database.close();
            }
        }else{
            popup12();
            cursor.close();
            sqlite.close();
            database.close();
        }
    }

    //popup12() - get users of NIPNIP
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void popup12(){
//        Names.clear();
//        ID.clear();
//        collectoo = 0;
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ii = pref.getString("selectedvalue","");
        if(ii == null || ii.equalsIgnoreCase("")){
            ii = sele12 ;
        }else{
        }
        Log.d("ffoorr",formattedDate);
        database = sqlite.getReadableDatabase();
        String MY_QUERY1 = "SELECT cus.*,deb.customer_id,col.collection_amount as collect,sum(col.collection_amount)as paid ,deb.debit_amount,deb.debit_date,deb.id as did FROM dd_customers cus " +
                " LEFT JOIN dd_account_debit deb on deb.customer_id = cus.id " +
                "  LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id from dd_collection WHERE collection_date = ? GROUP BY customer_id ) col on  deb.id = col.debit_id  AND col.collection_date = ?" +
                "  WHERE cus.tracking_id = ? AND deb.debit_date <= ? AND deb.active_status = 1 AND  cus.debit_type IN(2) GROUP BY cus.id ORDER BY cus.order_id_new ASC";
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{formattedDate,formattedDate,String.valueOf(ses),formattedDate});
//        String MY_QUERY = "SELECT (SELECT collection_amount from dd_collection where collection_date = ? AND customer_id = b.id) AS collect,b.customer_name" +
//                ",b.id,b.tracking_id,b.order_id,b.CID,b.debit_type,c.debit_amount,c.id as cidd FROM dd_customers b " +
//                "LEFT JOIN dd_collection a on b.id =  a.customer_id LEFT JOIN dd_account_debit c on b.id =  c.customer_id WHERE b.tracking_id = ? AND c.debit_date <= ? AND b.debit_type IN (0,1,2) GROUP BY b.id  ORDER BY b.order_id ASC ";
//        String[] whereArgs = new String[] {formattedDate,String.valueOf(ses),formattedDate};
//        Cursor cursor = database.rawQuery(MY_QUERY,whereArgs);
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    long id = cursor.getLong(index);
                    idd= String.valueOf(id);

                    index = cursor.getColumnIndexOrThrow("did");
                    long did = cursor.getLong(index);
                    didd= String.valueOf(did);

                    index = cursor.getColumnIndexOrThrow("collect");
                    String debbbb = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("CID");
                    String ccc = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String orderrr = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String typ = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debbbbi = cursor.getString(index);
                    Log.d("names12", String.valueOf(didd));
                    if(debbbbi != null) {
                        if (debbbb == null || debbbb.equalsIgnoreCase("0")) {
                            slllno = slllno + 1;
                            if (typ.equalsIgnoreCase("0")) {
                                Names.add(ccc + "," + Name+","+typ + "," + ccc);
                                ID.add(ccc + "," +didd);
                            }if (typ.equalsIgnoreCase("1")) {
                                Names.add(ccc + "," + Name+","+typ + "," + ccc);
                                ID.add(ccc + "," +didd);}
                            if (typ.equalsIgnoreCase("2")) {
                                Names.add(ccc + "," + Name+","+typ + "," + ccc);
                                ID.add(ccc + "," +didd);
                            }
                        } else {

//                            Log.d("names12", String.valueOf(didd));
                        }
                    }
                }
                while (cursor.moveToNext());
            }
        }
        final Dialog dialog = new Dialog(Collection.this);
        //set layout custom
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popnames);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);
        final RecyclerView rvcaddy = (RecyclerView) dialog.findViewById(R.id.rvAnimals);
        final MyRecyclerViewAdapter12 adapter = new MyRecyclerViewAdapter12(getApplicationContext(),Names, ID, 1,Collection.this);
        searchView = (EditText) dialog.findViewById(R.id.search);
        searchid = (EditText) dialog.findViewById(R.id.searchid);
        noo =(TextView) dialog.findViewById(R.id.no);
//        searchView.setQueryHint("Google Search");
        Button close = (Button) dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        searchid.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0)
                {
                    adapter.filter1(String.valueOf(s));
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            public void afterTextChanged(Editable s) {
//                dateString = datt.getText().toString();
//                Log.d("datata",dateString);
                adapter.filter1(String.valueOf(s));
            }
        });
        searchView.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0)
                {
                    adapter.filter(String.valueOf(s));
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            public void afterTextChanged(Editable s) {
//                dateString = datt.getText().toString();
//                Log.d("datata",dateString);
                adapter.filter(String.valueOf(s));
            }
        });
        if(Names.size() == 0){
            rvcaddy.setVisibility(View.GONE);
            noo.setVisibility(View.VISIBLE);
        }else {
            rvcaddy.setVisibility(View.VISIBLE);
            noo.setVisibility(View.GONE);
            rvcaddy.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvcaddy.setLayoutManager(mLayoutManager);
            rvcaddy.smoothScrollToPosition(Integer.parseInt(ii)); //use to focus the item with index
        }
        dialog.show();
    }

    //add() - Add colletcion
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void add(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDateq = df.format(c.getTime());
        String cuid = slno.getText().toString();
        String cusname = namee.getText().toString();
        String colamoun = col.getText().toString();
        colamoun = colamoun.replace(" ", "");
        String off = ofe.getText().toString();
        String disc = dit.getText().toString();
        Log.d("idi",iii+" "+didi);
        Log.d("disscc",cusname+" "+formattedDate);
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        if(colamoun == null || colamoun.equalsIgnoreCase("")){
            colamoun ="0";
        }
        if(cuid == null){

        }else {
            ContentValues values = new ContentValues();
            values.put("customer_id", iii);
            values.put("customer_name", cusname);
            values.put("collection_amount", colamoun);
            values.put("other_fee", off);
            values.put("discount", disc);
            values.put("debit_id", didi);
            values.put("collection_date", formattedDate);
            values.put("created_date", formattedDateq);
            database.insert(TABLENAME5, null, values);
            database.close();
            sqlite.close();
            if (off.equalsIgnoreCase("") || off.equalsIgnoreCase("0")) {
//                Intent intent = new Intent(Collection.this, Collection.class);
//                startActivity(intent);
            }else {
                last();
                sqlite = new SQLiteHelper(this);
                database = sqlite.getWritableDatabase();
                ContentValues values1 = new ContentValues();
                values1.put("acc_type_id", "6");
                values1.put("amount", off);
                values1.put("accounting_date", formattedDate);
                values1.put("tracking_id", ses);
                values1.put("created_date", formattedDateq);
                values1.put("collection_id", iiddd);
                database.insert(TABLENAME, null, values1);
                database.close();
                sqlite.close();
//                Intent intent = new Intent(Collection.this, Collection.class);
//                startActivity(intent);
            }
        }
    }

    //add1() - update collection
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void add1(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDateq = df.format(c.getTime());
        String cuid = slno.getText().toString();
        String cusname = namee.getText().toString();
        String colamoun = col.getText().toString();
        colamoun = colamoun.replace(" ", "");
        String off = ofe.getText().toString();
        String disc = dit.getText().toString();
        Log.d("idi1",iii+" "+didi);
        Log.d("disscc1",cusname+" "+formattedDate);
        if(colamoun == null || colamoun.equalsIgnoreCase("")){
            colamoun ="0";
        }
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        if(cuid == null){

        }else {
            Log.d("uio",colamoun+" "+ccid);
            ContentValues values = new ContentValues();
            values.put("collection_amount", colamoun);
            values.put("other_fee", off);
            values.put("discount", disc);
            values.put("collection_date", formattedDate);
            values.put("updated_date", formattedDateq);
            database.update(TABLENAME5,values,"id = ?",new String[]{ccid});
            database.close();
            sqlite.close();
            if (off.equalsIgnoreCase("") || off.equalsIgnoreCase("0")) {
//                Intent intent = new Intent(Collection.this, Collection.class);
//                startActivity(intent);
            }else {
                othercharges_id();
                if(otherid > 0){
                    Log.d("uio",colamoun);
                    sqlite = new SQLiteHelper(this);
                    database = sqlite.getWritableDatabase();
                    //                last();
                    ContentValues values1 = new ContentValues();
                    values1.put("acc_type_id", "6");
                    values1.put("amount", off);
                    values1.put("accounting_date", formattedDate);
                    values1.put("updated_date", formattedDateq);
                    values1.put("collection_id", ccid);
                    database.update(TABLENAME, values1 ,"collection_id = ?",new String[]{ccid});
                    database.close();
                    sqlite.close();
//                    Intent intent = new Intent(Collection.this, Collection.class);
//                    startActivity(intent);
                }else{
                    last();
                    sqlite = new SQLiteHelper(this);
                    database = sqlite.getWritableDatabase();
                    ContentValues values1 = new ContentValues();
                    values1.put("acc_type_id", "6");
                    values1.put("amount", off);
                    values1.put("accounting_date", formattedDate);
                    values1.put("tracking_id", ses);
                    values1.put("created_date", formattedDateq);
                    values1.put("collection_id", iiddd);
                    database.insert(TABLENAME, null, values1);
                    database.close();
                    sqlite.close();
                }
            }
        }
    }

    //othercharges_id() - add or update other charges
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void othercharges_id(){
        otherid = 0;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY1 = "SELECT id FROM dd_accounting  WHERE collection_id = ? AND accounting_date = ?";
        Cursor cursor = database.rawQuery(MY_QUERY1,new String[]{ccid,formattedDate});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("id");
                    otherid = cursor.getInt(index);
                    Log.d("oth0",String.valueOf(otherid));
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
        }else {
            cursor.close();
            sqlite.close();
            database.close();
        }
    }

    //last() - gets last inserted id
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void last(){
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY1 = "SELECT id FROM dd_collection  ORDER BY id DESC LIMIT 0,1 ";
        Cursor cursor = database.rawQuery(MY_QUERY1,null);
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("id");
                    iiddd = cursor.getString(index);
                    Log.d("op0",iiddd);
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
        }else {
            cursor.close();
            sqlite.close();
            database.close();
        }
    }

    //populateRecyclerView() - gets collection datas to show
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerView(){
        Names.clear();
        paid_cust.clear();
        balance_cust.clear();
        paiidddd = 0;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        Log.d("datee1e",formattedDate);
        String MY_QUERY1 ="SELECT cus.*,deb.customer_id,col.collection_amount ,sum(col.collection_amount)as paid ,deb.debit_amount,deb.debit_date,deb.id as did FROM dd_customers cus " +
                " LEFT JOIN dd_account_debit deb on deb.customer_id = cus.id " +
                "  LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id from dd_collection WHERE collection_date = ? GROUP BY customer_id ) col on  deb.id = col.debit_id  AND col.collection_date = ? " +
                "  WHERE cus.tracking_id = ? AND deb.debit_date <= ? AND deb.active_status = 1 AND  cus.debit_type IN(0,1,2) GROUP BY cus.id ORDER BY cus.order_id_new ASC ";
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{formattedDate,formattedDate,String.valueOf(ses),formattedDate});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    String id = cursor.getString(index);
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

                    if(collect == null){
                        collect = "0";
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
//                    Integer paa = 0;
                    Log.d("colllecttt",String.valueOf(paa));
                    paiidddd = paiidddd + paa;
                    if(debit == null){
                        debit = "0";

                    }else{
                        if(paid == null ){
                            paid ="0";
                            balance_cust.add(ii1d);
                        }else if(paid =="0" || paid.equalsIgnoreCase("0")){
                            balance_cust.add(ii1d);
                        }else{
                            paid_cust.add(ii1d);
                        }

                    }

//                    totaaa.setText(paiidddd);
                    Log.d("ins32",String.valueOf(dabamo));
                    Integer u8 = dabamo + paiidddd ;
                    Log.d("colammm", String.valueOf(u8));
                    tott.setText(String.valueOf(u8));

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

        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }
    }

    //list() - list collection datas
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void list(){
        ppooo.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();

        if(idd == null) {
            idd ="0";
//            Toast.makeText(getApplicationContext(), idd, Toast.LENGTH_SHORT).show();
        }
        else {
            Log.d("uiiu",formattedDate);
//            Log.d("uiiu",didi);
            Integer ioi = Integer.parseInt(idd);
            if(ioi >5000) {
                MY_QUERY = "SELECT a.*,b.customer_name,b.id as ID,b.CID,b.order_id_new,b.debit_type,col.debit_id,b.location,b.phone_1,b.phone_2,col.collection_amount,col.discount,col.id as ccid FROM dd_account_debit a LEFT JOIN dd_customers b on b.id = a.customer_id" +
                        " LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id,discount,id from dd_collection WHERE collection_date = ? ) col on a.id = col.debit_id AND col.collection_date = ?" +
                        " WHERE b.order_id_new =? AND b.tracking_id = ? AND a.debit_date <= ? AND a.active_status = 1 ";
            }else{
                MY_QUERY = "SELECT a.*,b.customer_name,b.id as ID,b.CID,b.order_id_new,b.debit_type,col.debit_id,b.location,b.phone_1,b.phone_2,col.collection_amount,col.discount,col.id as ccid FROM dd_account_debit a LEFT JOIN dd_customers b on b.id = a.customer_id" +
                        " LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id,discount,id from dd_collection WHERE collection_date = ?   ) col on a.id = col.debit_id AND col.collection_date = ?" +
                        " WHERE b.CID =? AND b.tracking_id = ? AND a.debit_date <= ? AND a.active_status = 1 ";
            }
            Cursor cursor = database.rawQuery(MY_QUERY, new String[]{formattedDate,formattedDate,idd, String.valueOf(ses),formattedDate});
            Log.d("dae",String.valueOf(cursor.getCount()));
            if (cursor != null) {
                if (cursor.getCount() != 0) {
                    cursor.moveToFirst();
                    do {
                        int index;
                        index = cursor.getColumnIndexOrThrow("ID");
                        String orde = cursor.getString(index);
                        iii = cursor.getString(index);
                        index = cursor.getColumnIndexOrThrow("id");
                        didi = cursor.getString(index);
                        index = cursor.getColumnIndexOrThrow("ccid");
                        ccid = cursor.getString(index);

                        index = cursor.getColumnIndexOrThrow("customer_name");
                        String Name = cursor.getString(index);
                        nme = cursor.getString(index);
                        Log.d("op",nme);
                        index = cursor.getColumnIndexOrThrow("collection_amount");
                        collect = cursor.getString(index);
                        index = cursor.getColumnIndexOrThrow("discount");
                        String discount = cursor.getString(index);
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
                        if(Pho == null || Pho.equalsIgnoreCase("") || Pho.equalsIgnoreCase("null")){
                            Pho = "0";
                        }

//                Integer pp = Integer.parseInt(p);
                        if (Pho.equalsIgnoreCase("0")) {

                        } else {
                            ppooo.add(Pho);
                        }
                        if (Pho1.equalsIgnoreCase("0")) {
                        } else {
                            ppooo.add(Pho1);
                        }
                        index = cursor.getColumnIndexOrThrow("closeing_date");
                        Clo = cursor.getString(index);
                        index = cursor.getColumnIndexOrThrow("installment_amount");
                        installment = cursor.getString(index);
                        index = cursor.getColumnIndexOrThrow("debit_days");
                        totaldays = cursor.getString(index);
                        index = cursor.getColumnIndexOrThrow("CID");
                        String ccc = cursor.getString(index);
                        Log.d("ip",ccc+" "+iii);
                        index = cursor.getColumnIndexOrThrow("order_id_new");
                        String orderrr = cursor.getString(index);
                        index = cursor.getColumnIndexOrThrow("debit_type");
                        String typ = cursor.getString(index);
                        String myFormat1 = "dd/MM/yyyy";//In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                        try {
                            dbdda = debitdaaa;
                            Date debit = sdf.parse(dbdda);
                            dbdda = sdf1.format(debit);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        try {
                            cloo = Clo;
                            Date debit = sdf.parse(cloo);
                            cloo = sdf1.format(debit);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(collect == null){
                            collect = "";
                        }
                        if(discount == null){
                            discount = "";
                        }

//                        if (typ.equalsIgnoreCase("0")) {

//                            slno.setText(ccc);
//                        }
//                        else if (typ.equalsIgnoreCase("1")) {
//                            slno.setText(ccc);
//                        }
//                        else if (typ.equalsIgnoreCase("2")) {
//                            slno.setText(orderrr);
//                        }
//                        Log.d("dae",ddbt);
//                if(collect.equalsIgnoreCase("0")){
                        String yy = slno.getText().toString();
                        if(yy == null || yy.equalsIgnoreCase("")){
                            slno.setText(ccc);
                        }
                        namee.setText(Name);
                        opd.setText(dbdda);
                        deb.setText("\u20B9" + " " + ddbt);
                        loc.setText(Loc);
                        pho.setText(Pho);
                        cd.setText(cloo);
                        tod.setText(totaldays);
                        if(collect.equalsIgnoreCase("0")){
                            col.setText("");
                        }else{
                            col.setText(collect);
                        }
                        String enn = getString(R.string.days);
                        String enn1 = getString(R.string.installment);
                        total1.setText(totaldays);
                        instal.setText(installment);
                        dit.setText(discount);
                        if(collect == ""){
//                            save1.setVisibility(View.GONE);
//                            save.setVisibility(View.VISIBLE);
                        }else{
//                            save.setVisibility(View.GONE);
//                            save1.setVisibility(View.VISIBLE);
                        }
//                        calculate();
//                }else {
////                            namee.setText(Name);
////                            opd.setText(dbdda);
////                            deb.setText("\u20B9" + " " + ddbt);
////                            loc.setText(Loc);
////                            pho.setText(Pho);
////                            cd.setText(cloo);
////                            tod.setText(totaldays);
////                            col.setText(collect);
////                            String enn = getString(R.string.total_days);
////                            String enn1 = getString(R.string.installment);
////                            total1.setText(enn+" : "+totaldays);
////                            instal.setText(enn1+" : "+installment);
//                    AlertDialog.Builder alertbox = new AlertDialog.Builder(Collection.this);
//                    String logmsg = getString(R.string.collection_alert);
//                    String cann = getString(R.string.cancel);
//                    String warr = getString(R.string.warning);
//                    String logo = getString(R.string.ok);
//                    alertbox.setMessage(logmsg);
//                    alertbox.setTitle(warr);
//                    alertbox.setIcon(R.drawable.dailylogo);
//                    alertbox.setPositiveButton(logo,
//                            new DialogInterface.OnClickListener() {
//
//                                public void onClick(DialogInterface arg0,
//                                                    int arg1) {
//                                    Intent name = new Intent(Collection.this,Collectiondetail_activity.class);
//                                    name.putExtra("ID",iii);
//                                    name.putExtra("DID",didi);
//                                    startActivity(name);
//                                }
//                            });
//                    alertbox.setNegativeButton(cann,
//                            new DialogInterface.OnClickListener() {
//
//                                public void onClick(DialogInterface arg0,
//                                                    int arg1) {
//                                }
//                            });
//                    //                            alertbox.setCancelable(false);
//                    alertbox.show();
//                }
                    }
                    while (cursor.moveToNext());
                    cursor.close();
                    sqlite.close();
                    database.close();
                    calculate();
//                    calculate();
                }else{
                    cursor.close();
                    sqlite.close();
                    database.close();
                    namee.setText("");
                    opd.setText("");
                    deb.setText("");
                    loc.setText("");
                    pho.setText("");
                    cd.setText("");
                    tod.setText("");
                    col.setText("");
                    String enn = getString(R.string.days);
                    String enn1 = getString(R.string.installment);
                    total1.setText("");
                    instal.setText("");
                    pad.setText("");
                    pam.setText("");
                    bad.setText("");
                    bam.setText("");
                    mid.setText("");
                    mama.setText("");

                }
            }else{
                cursor.close();
                sqlite.close();
                database.close();
            }


        }
    }

    //calculate1() - get other charges
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void calculate1(){
        Log.d("iui",ccid);
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        String SUM_QUERY = "SELECT amount,collection_id FROM dd_accounting  WHERE acc_type_id = ? AND collection_id = ?";
        Cursor cur = database.rawQuery(SUM_QUERY,new String[]{"6",ccid});

        if (cur != null) {
            if (cur.getCount() != 0) {
                cur.moveToFirst();
                Log.d("paiddd77", "opo");
                do {
                    Log.d("paiddd77", "opo");
                    int index;
                    index = cur.getColumnIndexOrThrow("amount");
                    Integer orde = cur.getInt(index);
                    index = cur.getColumnIndexOrThrow("collection_id");
                    Integer orde1 = cur.getInt(index);
                    ofe.setText(String.valueOf(orde));
                    Log.d("paiddd77", String.valueOf(orde));

                }
                while (cur.moveToNext());
                cur.close();
                sqlite.close();
                database.close();
            }else{
                cur.close();
                sqlite.close();
                database.close();
                Log.d("paiddd77", "opo1");
                ofe.setText("");
            }
        }else{
            cur.close();
            sqlite.close();
            database.close();
            Log.d("paiddd77", "opo2");
            ofe.setText("");
        }
    }

    //calculate() - days caluclation
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void calculate(){
        paiamo = 0 ;
        Log.d("iui21",iii+" "+didi);
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        String[] columns = {"debit_amount",
                "id", ""};
        String ii = "1";
        String MY_QUERY = "SELECT a.*,SUM(b.collection_amount) as paid,SUM(b.discount) as disc,b.collection_date,b.customer_id,b.id as ded FROM dd_customers a LEFT JOIN dd_account_debit c on c.customer_id = a.id LEFT JOIN dd_collection b on  b.debit_id = c.id   where a.id = ? AND a.tracking_id = ? AND c.active_status = 1  ";
        String SUM_QUERY = "SELECT sum(c.collection_amount) as paid,SUM (c.discount) as disc FROM dd_customers d LEFT JOIN dd_account_debit a ON a.customer_id = d.id LEFT JOIN dd_collection c ON c.debit_id = a.id WHERE d.id = ? AND a.id = ? AND a.active_status = 1 AND d.tracking_id = ? GROUP BY a.id";
        Cursor cur = database.rawQuery(MY_QUERY,new String[]{iii,String.valueOf(ses)});

        if (cur != null) {
            if (cur.getCount() != 0) {
                cur.moveToFirst();
                do {
                    int index;
                    index = cur.getColumnIndexOrThrow("paid");
                    Integer orde = cur.getInt(index);
                    index = cur.getColumnIndexOrThrow("ded");
                    Integer orde1 = cur.getInt(index);
                    Log.d("paiddd211112", String.valueOf(orde1));
                    index = cur.getColumnIndexOrThrow("disc");
                    disco = cur.getInt(index);
                    pam.setText("\u20B9" + " " + String.valueOf(orde));

                    paiamo = paiamo + orde ;
                    paidamount = String.valueOf(paiamo);
                    Log.d("paiddd21", String.valueOf(paiamo));
                    if(ccid == null){

                    }else {
                        calculate1();
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
            }
        }else{
            cur.close();
            sqlite.close();
            database.close();
        }

        if(paidamount != "0" && installment != null){
            instamt = Integer.parseInt(installment);
            pamount = Integer.parseInt(paidamount);
            Double pa = new Double(pamount);
            Double ins = new Double(instamt);
            Integer paaiddd = 0;
            if(instamt == 0){
                paaiddd = 0;
            }else{
                paaiddd = pamount/instamt;
            }

//            Log.d("paiddd", String.valueOf(paiddays));
//    pp = String.format("%.2f", paiddays);
            paiddays = (double) pamount / instamt;
            if(String.valueOf(paiddays).equalsIgnoreCase("NaN")){
                paiddays = 0.0;
            }
//                  Log.d("paiddd", String.valueOf(instamt));
            DecimalFormat df = new DecimalFormat("####.##");
            paiddays = Double.valueOf(df.format(paiddays));
            pp = String.valueOf(paiddays);
            pdays = paaiddd;
            pad.setText(String.valueOf(pp));
        }
        if(ddbt != null && paidamount != "0"){
            debitam1 = Integer.parseInt(ddbt);
            balanceamount = debitam1 - pamount;
            balanceamount = balanceamount - disco;
            bam.setText("\u20B9"+" "+String.valueOf(balanceamount));
            Log.d("bbaaa",String.valueOf(balanceamount));
        }else{
            balanceamount = Integer.valueOf(ddbt);
        }
        if(totaldays != null ){
            if(paiddays == null){
                paiddays = 0.0;
            }
            Log.d("paiddd", String.valueOf(paiddays));
            Log.d("paiddd", String.valueOf(totaldays));
            totda = Double.parseDouble(totaldays);
            Log.d("paiddd", String.valueOf(totda));
            balanceday = totda - paiddays;
            DecimalFormat df = new DecimalFormat("####.##");
            balanceday = Double.valueOf(df.format(balanceday));
            String pp = df.format(balanceday);
            bad.setText(String.valueOf(balanceday));
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
                    missda1 = 0.0;
                    mid.setText(String.valueOf(missda1));
                    mama.setText("\u20B9" + " " + String.valueOf(misam));
                    toda = 0 ;
                    toda1 = 1;
                    tod.setText(String.valueOf(toda1));
                }else {
                    Log.d("dates", String.valueOf(diffInDays));
                    String tod1 = String.valueOf(diffInDays) ;
                    toda = Integer.parseInt(tod1);
                    toda1 = Integer.parseInt(tod1) + 1;
                    toda2 = Double.parseDouble(tod1);
                    tod.setText(String.valueOf(toda1));
//        Integer po = Integer.parseInt(pp);
                    missiday = paiddays;
                    Integer tota = Integer.parseInt(totaldays);
                    Double mii1 = new Double(toda);
                    Double mii2 = new Double(pdays);
                    misda =  toda - pdays;
                    Double mii;
                    mii = mii1 - mii2;
                    missda1 = toda2 - paiddays;
                    DecimalFormat df12 = new DecimalFormat("####.##");
                    missda1 = Double.valueOf(df12.format(missda1));
                    if(missda1 < 0.0 || missda1 == null){
                        missda1 = 0.0 ;
                    }
                    Log.d("mmm", String.valueOf(mii));
                    if (misda >= tota) {
                        misam = balanceamount;
//                        misda = balanceday.intValue();
//                        missed = String.valueOf(misda);
//                        misda = Integer.parseInt(missed);
                    } else {
                        misam = misda * instamt;
                        if(misam < 0){
                            misam = 0;
                        }
                    }
//            misda = pdays - toda;
//        if(misda > totaldays)
//            misam = misda * instamt;
                    mid.setText(String.valueOf(missda1));
                    mama.setText("\u20B9" + " " + String.valueOf(misam));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    //popup1() - gets collection history
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void popup1() {
        Names1.clear();
        ID1.clear();
        collectoo = 0;
        sll = 0;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();

//        String MY_QUERY = "SELECT a.* FROM  dd_account_debit b LEFT JOIN dd_collection a ON a.debit_id = b.id where a.customer_id = ? AND b.active_status = ?";
        String MY_QUERY = "SELECT a.*,b.id as uui,c.CID FROM  dd_customers c LEFT JOIN dd_account_debit b  " +
                "on b.customer_id = c.id LEFT JOIN dd_collection a ON a.debit_id = b.id where a.customer_id = ?" +
                " AND b.active_status = ?  ORDER BY a.collection_date DESC";

        String whereClause = "customer_id=? ";
        String[] whereArgs = new String[]{iii};
//        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{idd});
        String[] columns = {"customer_name",
                "id", "collection_amount", "collection_date", "customer_id"};
        String order = "collection_date";
        Cursor cursor = database.rawQuery(MY_QUERY, new String[]{iii, "1"});
//        Cursor cursor = database.query("dd_collection",
//                columns,
//                whereClause,
//                whereArgs,
//                null,
//                null,
//                order+" DESC");
        if (cursor != null) {
            if (cursor.getCount() != 0) {
//                sll = cursor.getCount();
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("CID");
                    iidcid = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("customer_id");
                    long id = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    long iid1 = cursor.getLong(index);
                    Log.d("uu991",String.valueOf(iid1));

                    index = cursor.getColumnIndexOrThrow("uui");
                    long iid = cursor.getLong(index);
                    Log.d("uu99",String.valueOf(iid));

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
                    if (Name == null) {
                        Name = "";
                    }
                    if (iidcid == null) {
                        iidcid = "";
                    }
                    collectoo = collectoo + tt;
//                    cdate = dateString;
                    Log.d("ui8",String.valueOf(collectoo));
                    if (collectoo == 0) {
                        Log.d("ui8","90");
                        AlertDialog.Builder alertbox = new AlertDialog.Builder(Collection.this,R.style.AlertDialogTheme);
                        String logmsg = getString(R.string.nocollection);
                        String cann = getString(R.string.ok);
                        String warr = getString(R.string.warning);
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
                    } else {
//                        collectoo = collectoo + tt;
                        if(dbamount.equalsIgnoreCase("0")){
                        }else{

                            sll = sll + 1;
                            Names1.add(String.valueOf(sll) + "," + dateString + "," + dbamount + "," + String.valueOf(iid1));
                            ID1.add(id);
                            Log.d("nam", String.valueOf(Names1));
                        }
//                        ppp();
                    }
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
                if(String.valueOf(Names1.size()) == null || String.valueOf(Names1.size()).equalsIgnoreCase("")){

                }else{
                    ppp();
                }
            }else{
                cursor.close();
                sqlite.close();
                database.close();
                Log.d("ui8","901");
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Collection.this,R.style.AlertDialogTheme);
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

    //ppp() - shows collection history
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void ppp(){
//        final Dialog dialog = new Dialog(Collection.this);
        //set layout custom
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        total.setText(String.valueOf(collectoo));
        String ty = getString(R.string.name1);
        String ty1 = getString(R.string.SN1);
        naam.setText(ty+" "+Name);
        nidd.setText(ty1+" "+iidcid);
        final MyRecyclerViewAdapter_col adapter = new MyRecyclerViewAdapter_col(getApplicationContext(),Names1, ID1,Collection.this);
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
//        dialog.show();

    }

    //progre() - Stop progressbar
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void progre(){
        dialog.cancel();
    }
}
