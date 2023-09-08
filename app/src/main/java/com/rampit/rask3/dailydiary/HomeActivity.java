package com.rampit.rask3.dailydiary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentCallbacks2;
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

import androidx.annotation.RequiresApi;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdapter;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;



//Updated on 25/01/2022 by RAMPIT
//used to show all customers
//InternetConnection1() - Check whether internet is on or not
//progressbar_load() - Load progressbar
//progre() - Stop progressbar
//onTrimMemory() - Trim memory size
//upddate() - Update order id
//populateRecyclerView() - get all customers
//onBackPressed() - function called when back button is pressed
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed
//onNavigationItemSelected() - when navigation item pressed


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ComponentCallbacks2 {
    TextView name,pass,city,pin,email,phone,noo,dataaaa,sesss,totalcus,nondebit,nipcus,nipnipcus,dayy,currcus;
    Button submit,add,edit;
    String ss,s,idi,selected,cleared,adpr,edpr,depr,vipr;
    Integer ses,cle1,see1,see2,see,cle,in;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    RecyclerView recyclerView;
    SharedPreferences.Editor editor;
    Cursor cursor;
    public static String TABLE_NAME ="NAMES";
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> ID = new ArrayList<String>();
    ArrayList<String> AID = new ArrayList<String>();
    ArrayList<String> tim = new ArrayList<String>();
    ArrayList<String> OID = new ArrayList<String>();
    ArrayList<String> cuR = new ArrayList<String>();
    ArrayList<String> niP = new ArrayList<String>();
    ArrayList<String> niPP = new ArrayList<String>();
    ArrayList<String> no_deb = new ArrayList<String>();
    List<String> spin = new ArrayList<>();
    RecyclerViewAdapter mAdapter;
    LinearLayout lll;
    Button logou,profil;
    String from,to,typeid;
    Spinner type;
    private SearchView searchView;
    NavigationView navigationView;
    Menu nav_Menu;
    EditText sear,sear2;
    String zero,one,two,three,four,five,six,seven,eight,nine,ten;
    Calendar myCalendar,myCalendar1,c;
    SimpleDateFormat sdf1;
    String todaaaa;
    ImageView seesim;
    TextView cu,ni,nini,nd,act89;
    FloatingActionButton fab;
    private static final String TAG = "HomeActivity";
    Dialog dialog;
    Integer showinng = 0;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
//        SharedPreferences sharedPreferences = getSharedPreferences(CallDetector.MY_PREF,MODE_PRIVATE);
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
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
        dialog = new Dialog(HomeActivity.this,R.style.AlertDialogTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_home);
        setTitle(R.string.title_activity_home);
        ((Callback)getApplication()).datee();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        String sesid = pref.getString("id","");
        String module_i = "2";
        seesim = findViewById(R.id.sesimg);
        ((Callback)getApplication()).privilege(sesid,module_i);
        adpr = pref.getString("add_privilege","");
        edpr = pref.getString("edit_privilege","");
        depr = pref.getString("delete_privilege","");
        vipr = pref.getString("view_privilege","");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((Callback)getApplication()).deleteCache(getApplicationContext());
        add = findViewById(R.id.add);
        currcus = findViewById(R.id.cur_cus);
//        Intent intent = new Intent(this, CallDetectionService.class);
//        startService(intent);
//        String number = sharedPreferences.getString(CallDetector.NUMBER_KEY,"No Value Found");
        totalcus = findViewById(R.id.total_cus);
        nipcus = findViewById(R.id.nip_cus);
        nipnipcus = findViewById(R.id.nipnip_cus);
        nondebit = findViewById(R.id.nodeb_cus);
        cu  = findViewById(R.id.cu);
        ni = findViewById(R.id.ni);
        nini = findViewById(R.id.nini);
        nd = findViewById(R.id.nd);
        fab = findViewById(R.id.fab_arrow);
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),HomeActivity.this)) {
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
//        act89 = findViewById(R.id.act89);
//        Typeface ty78 = Typeface.createFromAsset(this.getAssets(),"fontaswesome1.ttf");
//        act89.setTypeface(ty78);

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
        spin.add("Select");
        spin.add("Current");
        spin.add("NIP");
        spin.add("NIP NIP");
//        add.setText(number);
        type = findViewById(R.id.spinner);
//        final List<String> plantsList = new ArrayList<>(R.array.spinnerItems);
        // Initializing an ArrayAdapter
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
//        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.spinnerItems, R.layout.spinner_design);
//        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
//        type.setAdapter(adapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                // Object item = parentView.getItemAtPosition(position);

                Integer nu = type
                        .getSelectedItemPosition();
                typeid = String.valueOf(nu);

                if(typeid.equalsIgnoreCase("1"))
                {   typeid = "Current";
//                adapter2();
                }else if(typeid.equalsIgnoreCase("2")){
                    typeid ="NIP";
//                adapter3();
                }else if(typeid.equalsIgnoreCase("3")){
                    typeid ="NIPNIP";
//                    adapter4();
                }else if(typeid.equalsIgnoreCase("0")){
//                adapter1();
                }
                Log.d("typedd",String.valueOf(nu));
//
            }
            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }
        });
        if(adpr.equalsIgnoreCase("0")){
add.setVisibility(View.GONE);
        }
        noo = findViewById(R.id.no);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent name = new Intent(HomeActivity.this,Addnames.class);
                name.putExtra("ID","0");
                startActivity(name);
                finish();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
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
//        searchView = (SearchView)findViewById(R.id.searchView);
//        int id = searchView.getContext()
//                .getResources()
//                .getIdentifier("android:id/search_src_text", null, null);
//        TextView textView = (TextView) searchView.findViewById(id);
//        textView.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.spin3)));
//        textView.setHint("Search");
//        int linlayId = getResources().getIdentifier("android:id/search_plate", null, null);
//        View view = searchView.findViewById(linlayId);
//        Drawable drawColor = getResources().getDrawable(R.drawable.search_widget);
//        view.setBackground( drawColor );
//        searchView = (SearchView) findViewById(R.id.searchView);
//        Drawable d = getResources().getDrawable(R.drawable.search_widget);
//        searchView.setBackground(d);
        c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String myFormat1 = "dd/MM/yyyy";//In which you need put here
        sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        todaaaa = df.format(c.getTime());

        sear = findViewById(R.id.search);
        sear2 = findViewById(R.id.search1);
        if(in == 0){
            Log.d("thee","thh");
           sear.setBackgroundResource(R.drawable.search_widget);
           sear.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.White)));
            sear2.setBackgroundResource(R.drawable.search_widget);
            sear2.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.White)));
            add.setBackgroundResource(R.drawable.add);
        }else{
            Log.d("thee","th1h");
            sear.setBackgroundResource(R.drawable.search_widget1);
            sear.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.Black)));
            sear2.setBackgroundResource(R.drawable.search_widget1);
            sear2.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.Black)));
            add.setBackgroundResource(R.drawable.add_blue);
//            recreate();
        }
        sear.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        sear2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        sear.addTextChangedListener(new TextWatcher() {

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
        sear2.addTextChangedListener(new TextWatcher() {

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
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String text) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String text) {
//                if(Names.size() == 0){
//                }else {
//                    mAdapter.filter(text);
//                }
//                return false;
//            }
//        });
        lll = (findViewById(R.id.linee));
        logou = lll.findViewById(R.id.logout);
        profil = lll.findViewById(R.id.profile);
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
                Intent i = new Intent(HomeActivity.this,Settings_activity.class);
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
                    Intent i = new Intent(HomeActivity.this,NavigationActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    editor.putString("theme","0");
                    editor.apply();
                    Intent i = new Intent(HomeActivity.this,NavigationActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        logou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(HomeActivity.this,R.style.AlertDialogTheme);
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
                                Intent i = new Intent(HomeActivity.this,Splash.class);
                                startActivity(i);
                                finish();
                            }
                        });
                alertbox.show();
            }
        });
//        sqlite = new SQLiteHelper(this);
        ses = pref.getInt("session", 1);
        String dd = pref.getString("Date","");
        dataaaa = findViewById(R.id.da);
        sesss = findViewById(R.id.sess);
        dataaaa.setText(dd);
        ((Callback)getApplication()).NIP(ses);
        if(in == 0){
            if(ses == 1){
                sesss.setText(getString(R.string.morning));
                seesim.setBackgroundResource(R.drawable.sun);
            }else if(ses == 2){
                sesss.setText(getString(R.string.evening));
                seesim.setBackgroundResource(R.drawable.moon);
            }
        }else{
            if(ses == 1){
                sesss.setText(getString(R.string.morning));
                seesim.setBackgroundResource(R.drawable.sun_blue);
            }else if(ses == 2){
                sesss.setText(getString(R.string.evening));
                seesim.setBackgroundResource(R.drawable.moon_blue);
            }
        }
        if (ses == 1) {
            ss = getString(R.string.morning);
        } else if (ses == 2) {
            ss = getString(R.string.evening);
        }
        String dsdsd = pref.getString("NAME","");
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        TextView nn1 = headerView.findViewById(R.id.name);
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
                AlertDialog.Builder alertbox = new AlertDialog.Builder(HomeActivity.this,R.style.AlertDialogTheme);
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
                                Intent i = new Intent(HomeActivity.this,Splash.class);
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
                Intent qq = new Intent(HomeActivity.this,NavigationActivity.class);
                startActivity(qq);
                finish();
            }
        });

        nn1.setText(dsdsd);
        nnn.setText("Date :"+" "+dd);
        nnnn.setText("Session :"+" "+ss);
        final SharedPreferences.Editor editor = pref.edit();
//        editor.putInt("isloginn", 1);
//        editor.commit();
        Log.d("ses", String.valueOf(ses));
        recyclerView = findViewById(R.id.re);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        fab.hide();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if (dy > 0 ||dy<0)
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
        Intent nn = getIntent();
         selected = nn.getStringExtra("selected");
         cleared = nn.getStringExtra("cleared");
        if(selected != null && cleared != null){
            upddate();
        }
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

    }
    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final HomeActivity account) {
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
    //onTrimMemory() - Trim memory size
    //Params - Level by android itself
    //Created on 25/01/2022
    //Return - NULL
    public void onTrimMemory(int level){
        Log.v(TAG, "onTrimMemory(" + level + ")");
        switch(level){

            case ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN:
                Log.v(TAG, "TRIM_MEMORY_UI_HIDDEN");
                break;
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE:
                Log.v(TAG, "TRIM_MEMORY_RUNNING_MODERATE");
                break;
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW:
                Log.v(TAG, "TRIM_MEMORY_RUNNING_LOW");
                break;
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL:
                Log.v(TAG, "TRIM_MEMORY_RUNNING_CRITICA");
                break;
            case ComponentCallbacks2.TRIM_MEMORY_BACKGROUND:
                Log.v(TAG, "TRIM_MEMORY_BACKGROUND");
                break;
            case ComponentCallbacks2.TRIM_MEMORY_MODERATE:
                Log.v(TAG, "TRIM_MEMORY_MODERATE");
                break;
            case ComponentCallbacks2.TRIM_MEMORY_COMPLETE:
                Log.v(TAG, "TRIM_MEMORY_COMPLETE");
                break;

            default:
                Log.v(TAG, "default");
        }
    }

    LinearLayoutManager layoutManager = new LinearLayoutManager(this) {

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(HomeActivity.this) {

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
    //upddate() - Update order id
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void upddate(){
        see = Integer.valueOf(selected);
        Integer cle = Integer.valueOf(cleared);
        see1 = see + 1;
        s = String.valueOf(see1);
        cle1 = cle + 1;
        Log.d("namessssss54", String.valueOf(see+1));
        Log.d("namessssss54", String.valueOf(cle+1));
        if (see1 < cle1) {
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
                        idi = cursor.getString(index);
                        Log.d("namesii1", idi);
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
            see2 = see1 + 1;
            for (int i = see2; i <= cle1;i++){
                sqlite = new SQLiteHelper(this);
                database = sqlite.getWritableDatabase();
                Log.d("forrloop",String.valueOf(i));
                ContentValues cv = new ContentValues();
                cv.put("order_id",i-1);
                String[] args =  new String[]{String.valueOf(i),String.valueOf(ses)};
                database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
                sqlite.close();
                database.close();
                Log.d("forrloop1",String.valueOf(i));
                Log.d("forrloop2",String.valueOf(i-1));
            }
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
            ContentValues cv1 = new ContentValues();
            cv1.put("order_id",cle1);
            database.update("dd_customers", cv1,  "id=? AND order_id=? AND tracking_id = ?", new String[]{idi,String.valueOf(see1),String.valueOf(ses)});
            sqlite.close();
            database.close();
            Toast.makeText(HomeActivity.this,see1 +" "+cle1+" "+"smaller" ,Toast.LENGTH_SHORT).show();
        }else if (see1 > cle1){
            sqlite = new SQLiteHelper(this);
            database = sqlite.getReadableDatabase();
            String MY_QUERY1 = "SELECT * FROM dd_customers WHERE order_id = ? AND tracking_id = ?";
            Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{String.valueOf(see+1),String.valueOf(ses)});
            if (cursor != null){
                if(cursor.getCount() != 0){
                    cursor.moveToFirst();
                    do{
                        int index;
                        index = cursor.getColumnIndexOrThrow("id");
                        idi = cursor.getString(index);
                        Log.d("namesii1", idi);
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
                cv.put("order_id",i+1);
                String[] args =  new String[]{String.valueOf(i),String.valueOf(ses)};
                database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
                Log.d("forrloop1",String.valueOf(i));
                Log.d("forrloop2",String.valueOf(i+1));
                sqlite.close();
                database.close();
            }
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
            ContentValues cv1 = new ContentValues();
            cv1.put("order_id",cle1);
            database.update("dd_customers", cv1,  "id=? AND order_id=? AND tracking_id = ?", new String[]{idi,String.valueOf(see1),String.valueOf(ses)});
            Toast.makeText(HomeActivity.this,see1 +" "+cle1+" "+idi ,Toast.LENGTH_SHORT).show();
            sqlite.close();
            database.close();
        }
    }
//    public void filter(String charText) {
//        charText = charText.toLowerCase(Locale.getDefault());
//        ArrayList data = new ArrayList<String>();
//        if (charText.length() == 0) {
//            // mCleanCopyDataset we always contains the unaltered and the filter (full) copy of the list of data
//            data.addAll(Names);
//        } else {
//            for (String item : Names) {
//
//                // we iterate through all the items on the list and if any item contains the search text, we add it to a new list mDataset
////                if (item.toLowerCase(Locale.getDefault()).contains(charText)) {
////                    data.add(item);
////                }
//                String[] currencie = item.split(",");
//                String d = currencie[1];
//                if (d.contains(charText)) {
//                    Log.d("fileterd1",item);
//                    Log.d("fileterd2",d);
////                    Integer nh = Integer.parseInt(d);
////                    if(nh.equals(Integer.parseInt(charText))){
////                        Log.d("fileterd3",d);
////                    }
//                    data.add(item);
//                }
//            }
//        }
//        mAdapter.updateList(data);
//// method notifyDataSetChanged () allows you to update the list on the screen after filtration
////        notifyDataSetChanged();
//    }
//    public void filter1(String charText) {
//        charText = charText.toLowerCase(Locale.getDefault());
//        ArrayList data = new ArrayList<String>();
//        if (charText.length() == 0) {
//            // mCleanCopyDataset we always contains the unaltered and the filter (full) copy of the list of data
//            data.addAll(Names);
//        } else {
//            for (String item : Names) {
//
//                // we iterate through all the items on the list and if any item contains the search text, we add it to a new list mDataset
//                String[] currencie = item.split(",");
//                String d = currencie[0];
//                if (d.contains(charText)) {
//                    Log.d("fileterd1",item);
//                    Log.d("fileterd2",d);
////                    Integer nh = Integer.parseInt(d);
////                    if(nh.equals(Integer.parseInt(charText))){
////                        Log.d("fileterd3",d);
////                    }
//                    data.add(item);
//                }
//            }
//        }
//        mAdapter.updateList(data);
//// method notifyDataSetChanged () allows you to update the list on the screen after filtration
////        notifyDataSetChanged();
//    }


//populateRecyclerView() - get all customers
//Params - selected item
//Created on 25/01/2022
//Return - NULL
    private void populateRecyclerView() {
        Names.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String[] columns = {"customer_name",
                "id", "order_id", "tracking_id", "debit_type","CID"};
        String whereClause = "tracking_id=?";
        String[] whereArgs = new String[]{String.valueOf(ses)};
        String order = "order_id";
        Cursor cursor = database.rawQuery("SELECT a.*,b.active_status FROM dd_customers a LEFT JOIN dd_account_debit b ON b.customer_id = a.id" +
                " WHERE a.tracking_id = ? GROUP BY a.id ORDER BY a.order_id_new ASC, a.created_date ASC ",new String[]{String.valueOf(ses)});
        //        Cursor cursor = database.query("dd_customers",
//                columns,
//                whereClause,
//                whereArgs,
//                null,
//                null,
//                order + " ASC");
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    long id = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("CID");
                    String cusid = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String orderrr = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String typp = cursor.getString(index);
                    String typ = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("active_status");
                    String active = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("tracking_id");
                    String traaa = cursor.getString(index);
                    if (typ.equalsIgnoreCase("-1")) {
                        typ = "CURRENT";
                        Names.add(cusid+","+Name+","+orderrr+","+id+","+cusid+","+typp);
//                        cuR.add(cusid + " " + Name+" "+orderrr);
                        no_deb.add(cusid + " " + Name+" "+orderrr);
                    }
                    if(active == null){
                        active = "0";
                    }
                    if (typ.equalsIgnoreCase("0") && active.equalsIgnoreCase("0")) {
                        Log.d("qwerty", String.valueOf(id));
                    }

                    if (typ.equalsIgnoreCase("0")) {
                        typ = "CURRENT";
                        Names.add(cusid+","+Name+","+orderrr+","+id+","+cusid+","+typp);
                        cuR.add(cusid + " " + Name+" "+orderrr);
                    }
                    if (typ.equalsIgnoreCase("1")) {
                        typ = "NIP";
                        Names.add(cusid+","+Name+","+orderrr+","+id+","+cusid+","+typp);
                        niP.add(cusid + " " + Name+" "+orderrr);
                    }
                    if (typ.equalsIgnoreCase("2")) {
                        typ = "NIPNIP";
                        Names.add(cusid+","+Name+","+orderrr+","+id+","+cusid+","+typp);
                        niPP.add(cusid + " " + Name+" "+orderrr);
                    }
                    if (typ.equalsIgnoreCase("3")) {
                        typ = "NIPNIP";
                        Names.add(cusid+","+Name+","+orderrr+","+id+","+cusid+","+typp);
//                        niPP.add(cusid + " " + Name+" "+orderrr);
                        no_deb.add(cusid + " " + Name+" "+orderrr);
                    }

                    Log.d("names1234", String.valueOf(cuR.size()));
                    ID.add(typp);
                    AID.add(String.valueOf(id));
                    OID.add(orderrr);
                    tim.add(traaa);
                    String nod = String.valueOf(no_deb.size());
                    String nod1 = String.valueOf(cuR.size());
                    String nod2 = String.valueOf(niP.size());
                    String nod3 = String.valueOf(niPP.size());
                    Integer tottt = cuR.size()+niP.size()+niPP.size()+Integer.parseInt(nod);
                    nondebit.setText(nod);
                    currcus.setText(nod1);
                    nipcus.setText(nod2);
                    nipnipcus.setText(nod3);
                    String t = getString(R.string.total_customer);
                    totalcus.setText(t+" : "+String.valueOf(tottt));

                    Log.d("namesmulti", String.valueOf(Names));
                    Log.d("namesmulti1",String.valueOf(cursor.getCount()));
                    Log.d("CID",cusid);
                    Log.d("names1", orderrr+" "+traaa+" "+cusid);
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
        if (Names.size() == 0) {
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
        } else {
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
            mAdapter = new RecyclerViewAdapter(Names, ID, AID,tim,OID,ses,in, getApplicationContext(), edpr, depr);
//            ItemTouchHelper.Callback callback =
//                    new ItemMoveCallback1(mAdapter);
//            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//            touchHelper.attachToRecyclerView(recyclerView);
            mAdapter.setHasStableIds(true);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setItemViewCacheSize(1000);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            mAdapter.onAttachedToRecyclerView(recyclerView);
            recyclerView.setLayoutManager(layoutManager);

        }
    }
//public void adapter1() {
//    if (Names.size() == 0) {
//        recyclerView.setVisibility(View.GONE);
//        noo.setVisibility(View.VISIBLE);
//    } else {
//        recyclerView.setVisibility(View.VISIBLE);
//        noo.setVisibility(View.GONE);
//        mAdapter = new RecyclerViewAdapter(Names, ID, AID,tim,OID,ses, getApplicationContext(), edpr, depr);
//        ItemTouchHelper.Callback callback =
//                new ItemMoveCallback1(mAdapter);
//        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//        touchHelper.attachToRecyclerView(recyclerView);
//        recyclerView.setAdapter(mAdapter);
//    }
//}
//    public void adapter2() {
//        if (cuR.size() == 0) {
//            recyclerView.setVisibility(View.GONE);
//            noo.setVisibility(View.VISIBLE);
//        } else {
//            recyclerView.setVisibility(View.VISIBLE);
//            noo.setVisibility(View.GONE);
//            mAdapter = new RecyclerViewAdapter(cuR, ID, AID, tim,OID,ses, getApplicationContext(), edpr, depr);
//            ItemTouchHelper.Callback callback =
//                    new ItemMoveCallback1(mAdapter);
//            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//            touchHelper.attachToRecyclerView(recyclerView);
//            recyclerView.setAdapter(mAdapter);
//        }
//    }
//    public void adapter3(){
//        if (niP.size() == 0) {
//            recyclerView.setVisibility(View.GONE);
//            noo.setVisibility(View.VISIBLE);
//        } else {
//            recyclerView.setVisibility(View.VISIBLE);
//            noo.setVisibility(View.GONE);
//            mAdapter = new RecyclerViewAdapter(niP, ID, AID, tim,OID,ses, getApplicationContext(), edpr, depr);
//            ItemTouchHelper.Callback callback = new ItemMoveCallback1(mAdapter);
//            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//            touchHelper.attachToRecyclerView(recyclerView);
//            recyclerView.setAdapter(mAdapter);
//        }
//    }
//    public void adapter4(){
//        if (niPP.size() == 0) {
//            recyclerView.setVisibility(View.GONE);
//            noo.setVisibility(View.VISIBLE);
//        } else {
//            recyclerView.setVisibility(View.VISIBLE);
//            noo.setVisibility(View.GONE);
//            mAdapter = new RecyclerViewAdapter(niPP, ID, AID, tim,OID,ses, getApplicationContext(), edpr, depr);
//            ItemTouchHelper.Callback callback =
//                    new ItemMoveCallback1(mAdapter);
//            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//            touchHelper.attachToRecyclerView(recyclerView);
//            recyclerView.setAdapter(mAdapter);
//        }
//    }

    //onBackPressed() - function called when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onBackPressed() {
        Intent nn = new Intent(HomeActivity.this,NavigationActivity.class);
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
            Intent new1 = new Intent(HomeActivity.this,MainActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.user) {
            Intent new1 = new Intent(HomeActivity.this,HomeActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.debit) {
            Intent new1 = new Intent(HomeActivity.this,Debit.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.collection) {
            Intent new1 = new Intent(HomeActivity.this,AllCollection.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.account) {
            Intent new1 = new Intent(HomeActivity.this,Account.class);
            startActivity(new1);
            finish();
        }  else if (id == R.id.report) {
            Intent new1 = new Intent(HomeActivity.this,ReportActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.customer) {
            Intent new1 = new Intent(HomeActivity.this,Customer_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.settings) {
            Intent new1 = new Intent(HomeActivity.this,Settings_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.tally) {
            Intent new1 = new Intent(HomeActivity.this,Tally_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.employee) {
            Intent new1 = new Intent(HomeActivity.this,Employee_details.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.today) {
            Intent new1 = new Intent(HomeActivity.this,Today_report.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.logout) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(HomeActivity.this,R.style.AlertDialogTheme);
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
                            Intent i = new Intent(HomeActivity.this,Splash.class);
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
