package com.rampit.rask3.dailydiary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;

import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdapter;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.play.core.install.model.ActivityResult.RESULT_IN_APP_UPDATE_FAILED;


//Updated on 25/01/2022 by RAMPIT
//used to show dashboard
//curl_debita() - Check table name is there in DB
//curl_debit() - get date from dd_delete datble
//beforebal1() - get debit datas
//beforebal0() - get collection
//beforebal11() - get tally datas
//beforebal111() - get accounting datas
//InternetConnection1() - Check whether internet is on or not
//checkUpdate() - Check for app update in playstore
//startUpdateFlow() - Updat app from playstore
//onActivityResult() - Result from playstore
//License1() - get settings datas
//WriteBtn() - get info file
//show_filee() - upload info file
//info_firebase() - upload info file
//checkkk() -Check DB version
//dd_files_license() -Delete older files in firebase
//del() -Delete older files in firebase
//syncEvent1() - update orders
//syncEvent() -Reorder datas
//progressbar_load() - Load progressbar
//progressbar_stop() - Stop progressbar
//progressbar_load1() - Load progressbar
//progre() - Stop progressbar
//expiiii() -get expiry date
//onRestart() - Called when activity is restarted
//onStart() - Called when activity is started
//lastlogin() - put login time
//switch1() - on click function for all buttons
//switch2() - on click function for all buttons
//populateRecyclerView23() - get current final balance
//populateRecyclerView() - get Main info
//dd_modules() - get all modules
//onBackPressed() - function called when back button is pressed
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed
//onNavigationItemSelected() - when navigation item pressed


public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView name,pass,city,pin,email,phone,company;
    Button submit,usb,debb,colb,acb,repb,cusb,talb,empb,todbb,printb,importb,btn1,btn2;
    String ss,zero,one,two,three,four,five,six,seven,eight,nine,ten,eleven,formattedDate,dateString,dateString2,todateee;
    Integer ses;
    Spinner type,type1;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    public static String TABLENAME = "REGISTER";
    RecyclerView recyclerView;
    Cursor cursor;
    public static String TABLE_NAME ="NAMES";
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> Names1 = new ArrayList<>();
    ArrayList<String> Names0 = new ArrayList<>();
    ArrayList<String> Names04 = new ArrayList<>();
    ArrayList<String> Names02 = new ArrayList<>();
    ArrayList<String> Names03 = new ArrayList<>();
    ArrayList<String> dates_array = new ArrayList<>();
    ArrayList<Long> ID = new ArrayList<>();
    ArrayList<String> files_array = new ArrayList<>();
    ArrayList<String> files_array_id = new ArrayList<>();
    ArrayList<Sorting> arraylist = new ArrayList<Sorting>();
    RecyclerViewAdapter mAdapter;
    String from,to,idd,backpri;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    NavigationView navigationView;
    Menu nav_Menu;
    EditText editTextPath;
    LinearLayout lll,llll1;
    Button logou,profil,theme;
    LinearLayout row4;
    Integer befo,newc,curc,nipc,nipnipc,dab,totdab,movenipnpi,selectnipnip,movedniip,lin1,lin2,lin3,cloosed,ses21;
    Dialog dialog,dialog1;
    Integer oldv1,newv1,getvv,setingsa = 0;
    String licee,License,version,li_user,li_pass,todaaaa,pdfname;
    private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;
    CoordinatorLayout coordinatorLayout;
    private static final int FLEXIBLE_APP_UPDATE_REQ_CODE = 123;
    File pdfFile;
//    private AppUpdateManager appUpdateManager;
    private static final int IMMEDIATE_APP_UPDATE_REQ_CODE = 124;
    Integer showinng = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
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
        String localeName = pref.getString("language","");
        if(localeName == null){
            localeName = "ta";
        }
        ses21 = 0;
//        if (android.os.Build.VERSION.SDK_INT >= 22) {
////            forceLocale(this,localeName);
//            changeLang(localeName);
//        }else{
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();

        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
//        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_navigation);
        setTitle(R.string.title_activity_navigation);
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());
        //        installStateUpdatedListener = state -> {
//            if (state.installStatus() == InstallStatus.DOWNLOADED) {
//                popupSnackBarForCompleteUpdate();
//            } else if (state.installStatus() == InstallStatus.INSTALLED) {
//                removeInstallStateUpdateListener();
//            } else {
//                Toast.makeText(getApplicationContext(), "InstallStateUpdatedListener: state: " + state.installStatus(), Toast.LENGTH_LONG).show();
//            }
//        };
//        appUpdateManager.registerListener(installStateUpdatedListener);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!Settings.canDrawOverlays(this)) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:" + getPackageName()));
//                startActivityForResult(intent, 1);
//
//            }
//        }

        checkUpdate();
//        sqlite = new SQLiteHelper(NavigationActivity.this);
//        database = sqlite.getReadableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put("active_status", "0");
//        database.update("dd_account_debit", cv, "id = ?", new String[]{"548"});
//        sqlite.close();
//        database.close();
        /* PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int mCurrentVersion = pInfo.versionCode;
            Log.d("mCurrentVersion",String.valueOf(mCurrentVersion));
            if(mCurrentVersion >= 10 ){
                AlertDialog.Builder alertbox = new AlertDialog.Builder(NavigationActivity.this,R.style.AlertDialogTheme);
                String warr = getString(R.string.warning);
                String ok = getString(R.string.ok);
                String cancel = getString(R.string.cancel);
                alertbox.setMessage(R.string.reorder_alert);
                alertbox.setTitle(warr);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setPositiveButton(ok,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {

                            }
                        });
                alertbox.show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }*/
//        upppp();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int mCurrentVersion = pInfo.versionCode;
            SharedPreferences mSharedPreferences = getSharedPreferences("app_name",  Context.MODE_PRIVATE);
            SharedPreferences.Editor mEditor = mSharedPreferences.edit();
            mEditor.apply();
            int last_version = mSharedPreferences.getInt("last_version", -1);
            Log.d("last_version",String.valueOf(last_version)+" "+String.valueOf(mCurrentVersion));
            if(last_version != mCurrentVersion)
            {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(NavigationActivity.this,R.style.AlertDialogTheme);
                String warr = getString(R.string.warning);
                String ok = getString(R.string.ok);
                String cancel = getString(R.string.cancel);
                alertbox.setMessage(R.string.reorder_alert);
                alertbox.setTitle(warr);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setCancelable(false);
                alertbox.setPositiveButton(ok,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                syncEvent ss = new syncEvent();
                                ss.execute();
                            }
                        });
                alertbox.show();
            }else{
                ((Callback)getApplication()).reorder_data(getApplicationContext());
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        for(int i=0;i<4;i++){
            Log.d("iiiii",String.valueOf(i));
            Integer vvaa = -7 ;
            vvaa = vvaa + i ;
            Calendar calendar = Calendar.getInstance();
            String pattern1 = "dd-MM-yyyy";
            SimpleDateFormat dateFormat1 = new SimpleDateFormat(pattern1);
            calendar.add(Calendar.DAY_OF_YEAR, vvaa);
            Date newDate = calendar.getTime();
            String ss = dateFormat1.format(calendar.getTime());
            dates_array.add(ss);
            Log.d("getting_date",String.valueOf(newDate));
            Log.d("getting_date1",String.valueOf(dates_array));
        }
        dd_modules();
        ((Callback)getApplication()).lastlogin();
//        for(int y=0;y<3;y++){
//            if(y==0){
//                SQLiteHelper sqlite = new SQLiteHelper(this);
//                SQLiteDatabase database = sqlite.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put("active_status","0");
//                database.update("dd_account_debit", values, "id = ?", new String[]{"471"});
//                database.close();
//                sqlite.close();
//            }else if(y==1){
//                SQLiteHelper sqlite = new SQLiteHelper(this);
//                SQLiteDatabase database = sqlite.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put("active_status","0");
//                database.update("dd_account_debit", values, "id = ?", new String[]{"472"});
//                database.close();
//                sqlite.close();
//            }else if(y==2){
//                SQLiteHelper sqlite = new SQLiteHelper(this);
//                SQLiteDatabase database = sqlite.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put("active_status","0");
//                database.update("dd_account_debit", values, "id = ?", new String[]{"1186"});
//                database.close();
//                sqlite.close();
//            }
//        }
//        ((Callback)getApplication()).curl_brightness(NavigationActivity.this);
//        SQLiteHelper sqlite = new SQLiteHelper(this);
//        SQLiteDatabase database = sqlite.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("debit_type","1");
//        database.update("dd_customers", values, "id = ?", new String[]{"168"});
//        database.close();
//        sqlite.close();
       /* for(int hj=0;hj<=1;hj++){
            if(hj==0){
                SQLiteHelper sqlite = new SQLiteHelper(this);
                SQLiteDatabase database = sqlite.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("debit_type","3");
                database.update("dd_customers", values, "id = ?", new String[]{"61"});
                database.close();
                sqlite.close();
            }
            if(hj==1){
                SQLiteHelper sqlite = new SQLiteHelper(this);
                SQLiteDatabase database = sqlite.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("debit_type", "3");
                database.update("dd_customers", values, "id = ?", new String[]{"137"});
                database.close();
                sqlite.close();
            }
        }*/


        ((Callback)getApplication()).datee();
        ((Callback)getApplication()).emailid();
        ((Callback)getApplication()).emergency();
        ((Callback)getApplication()).deleteCache(getApplicationContext());

        ActivityCompat.requestPermissions(NavigationActivity.this,new String[]{android.Manifest.permission.PROCESS_OUTGOING_CALLS} ,1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        editTextPath = findViewById(R.id.path);
        LinearLayout layout = (LinearLayout) findViewById(R.id.nav2);
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.nav1);
        String ii1 = pref.getString("NAME","");
        company = findViewById(R.id.company);
        if(ii1.equalsIgnoreCase("")){
        }else{
            company.setText(ii1);
        }
        Intent login = getIntent();
        li_user = login.getStringExtra("user");
        li_pass = login.getStringExtra("pass");

//        SQLiteHelper sqlite012 = new SQLiteHelper(NavigationActivity.this);
//        SQLiteDatabase database012 = sqlite012.getReadableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put("debit_type", "3");
//        database012.update("dd_customers", cv, "id = ?", new String[]{"396"});
//        sqlite012.close();
//        database012.close();
//
//        for (int y=469;y<=472;y++){
//            SQLiteHelper sqlite0121 = new SQLiteHelper(NavigationActivity.this);
//            SQLiteDatabase database0121 = sqlite0121.getReadableDatabase();
//            ContentValues cv1 = new ContentValues();
//            cv1.put("active_status", "0");
//            database0121.update("dd_account_debit", cv1, "id = ?", new String[]{String.valueOf(y)});
//            sqlite0121.close();
//            database0121.close();
//        }
        dialog = new Dialog(NavigationActivity.this,R.style.CustomDialog);
        dialog1 = new Dialog(NavigationActivity.this,R.style.AlertDialogTheme);
        usb = findViewById(R.id.user_button);
        llll1 = findViewById(R.id.ll11);
        License1();
        debb = findViewById(R.id.debit_button);
        colb = findViewById(R.id.collection_button);
        acb = findViewById(R.id.account_button);
        repb = findViewById(R.id.reports_button);
        cusb = findViewById(R.id.customer_button);
        printb = findViewById(R.id.print_button);
        type = findViewById(R.id.spinner);
        type1 = findViewById(R.id.spinner1);
//        setb = findViewById(R.id.settings_button);
        talb = findViewById(R.id.tally_button);
        empb = findViewById(R.id.emplopyee_button);
        todbb = findViewById(R.id.today_button);
        name = findViewById(R.id.name);
        pass = findViewById(R.id.password);
        city = findViewById(R.id.city);
        pin = findViewById(R.id.pincode);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.mobile);
        submit = findViewById(R.id.logout);
        importb = findViewById(R.id.import_button);
        lll = (findViewById(R.id.linee));
        TextView pro = lll.findViewById(R.id.pro);
        logou = lll.findViewById(R.id.logout);
        profil = lll.findViewById(R.id.profile);
        editor = pref.edit();
        final SharedPreferences.Editor editor = pref.edit();
        backpri = pref.getString("backpri","");
        idd = pref.getString("id","");

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
                            if (InternetConnection1.checkConnection(getApplicationContext(),NavigationActivity.this)) {
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

        if(idd.equalsIgnoreCase("1")){
            Log.d("iidd",idd);
//            layout2.setVisibility(View.VISIBLE);
            layout.setVisibility(View.VISIBLE);
            zero = "4";
            one = "4";
            two = "4";
            three = "4";
            four = "4";
            five = "4";
            six = "4";
            seven = "4";
            eight = "4";
            nine = "4";
            ten = "4";
            eleven = "4";
        }
        else{
            Log.d("iidd",idd);
            layout.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            ((Callback)getApplication()).viewcheck(idd);
            zero = pref.getString("company","");
            one = pref.getString("user1","");
            two = pref.getString("debit","");
            three = pref.getString("collection","");
            four = pref.getString("account","");
            five = pref.getString("reports","");
            six = pref.getString("customer","");
            seven = pref.getString("printer","");
            eight = pref.getString("tally","");
            nine = pref.getString("employee_details","");
            ten = pref.getString("today_report","");
            eleven = pref.getString("settings","");
        }
        ses = pref.getInt("session", 1);
//        ((Callback)getApplication()).auto_checkk(String.valueOf(ses));
        Log.d("ses",String.valueOf(ses));
        ((Callback)getApplication()).CURR(ses);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        nav_Menu = navigationView.getMenu();
        if(zero.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.company).setVisible(false);
        }
        if(one.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.user).setVisible(false);
            usb.setBackgroundResource(R.drawable.no_user);
            usb.setEnabled(false);
        }
        if(two.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.debit).setVisible(false);
            debb.setBackgroundResource(R.drawable.no_deb);
            debb.setEnabled(false);
        }
        if(three.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.collection).setVisible(false);
            colb.setBackgroundResource(R.drawable.no_col);
            colb.setEnabled(false);

        }
        if(four.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.account).setVisible(false);
            acb.setBackgroundResource(R.drawable.no_acc);
            acb.setEnabled(false);

        }
        if(five.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.report).setVisible(false);
            repb.setBackgroundResource(R.drawable.no_rep);
            repb.setEnabled(false);

        }
        if(six.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.customer).setVisible(false);
            cusb.setBackgroundResource(R.drawable.no_emm);
            cusb.setEnabled(false);
        }
        if(eleven.equalsIgnoreCase("0")) {
            if(backpri.equalsIgnoreCase("1")){
            }else{
                nav_Menu.findItem(R.id.settings).setVisible(false);
                profil.setVisibility(View.GONE);
                pro.setVisibility(View.GONE);
            }
//            Names1.add("user1,user1,7");
        }
        if(eight.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.tally).setVisible(false);
            talb.setBackgroundResource(R.drawable.no_em);
            talb.setEnabled(false);
        }
        if(nine.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.employee).setVisible(false);
            empb.setBackgroundResource(R.drawable.no_emm);
            empb.setEnabled(false);

        }
        if(ten.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.today).setVisible(false);
            todbb.setBackgroundResource(R.drawable.no_tod);
            todbb.setEnabled(false);


        }
        if(one.equalsIgnoreCase("4")) {
            Names1.add("user1,user1,1");
        }
        if(two.equalsIgnoreCase("4")) {
            Names1.add("user1,user1,2");
        }
        if(three.equalsIgnoreCase("4")) {
            Names1.add("user1,user1,3");
        }
        if(four.equalsIgnoreCase("4")) {
            Names1.add("user1,user1,4");
        }
        if(five.equalsIgnoreCase("4")) {
            Names1.add("user1,user1,5");

        }
        if(six.equalsIgnoreCase("4")) {
            Names1.add("user1,user1,6");
        }
        if(seven.equalsIgnoreCase("4")) {
            Names1.add("user1,user1,7");
        }
//        if(eight.equalsIgnoreCase("4")) {
//            Names1.add("user1,user1,7");
//        }
        if(nine.equalsIgnoreCase("4")) {
            Names1.add("user1,user1,8");
        }
        if(ten.equalsIgnoreCase("4")) {
            Names1.add("user1,user1,9");

        }
//        View namebar = findViewById(R.id.ll11);
//        ((ViewGroup) namebar.getParent()).removeView(namebar);

        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout row = new LinearLayout(this);
        LinearLayout.LayoutParams params = (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1 ));
        params.setMargins(10, 10, 10, 0);
        LinearLayout.LayoutParams paramsla = ((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT ,1 )));
        paramsla.setMargins(10, 10, 10, 10);
        LinearLayout.LayoutParams paramsla1 = ((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT ,1 )));
        paramsla1.setMargins(0, 20, 0, 0);
        LinearLayout.LayoutParams params1 = (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1 ));
        params1.setMargins(10, 10, 10, 0);
        LinearLayout.LayoutParams params2 = (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1 ));
        params2.setMargins(10, 10, 10, 0);
        row.setLayoutParams(paramsla1);
        row.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout row1 = new LinearLayout(this);
        row1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT ,1));
        row1.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout row3 = new LinearLayout(this);
        row3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT ,1));
        row3.setOrientation(LinearLayout.HORIZONTAL);

            for(int j = 0;j < Names1.size();j++){
                String qw = Names1.get(j);
                if(j <= 2){
                    Log.d("nnn2",String.valueOf(j));
                    row4 = new LinearLayout(this);
                    row4.setLayoutParams(paramsla);
                    row4.setOrientation(LinearLayout.VERTICAL);
                    row4.setGravity(1);
                    String colll = Names1.get(j);
                    String[] currencie = colll.split(",");
                    String ddd = currencie[2];
                    btn1 = new Button(this);
                    btn2 = new Button(this);
                    btn1.setLayoutParams(params);
                    btn2.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT )));
                    btn2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.Black));
                    btn2.setTextSize(15);
                    if(in == 0){
                        switch1(ddd);
                        row4.setBackgroundResource(R.color.DarkGray);
                        btn2.setBackgroundResource(R.color.DarkGray);
                    }else{
                        switch2(ddd);
                        row4.setBackgroundResource(R.color.WhiteSmoke);
                        btn2.setBackgroundResource(R.color.WhiteSmoke);
                    }
                    row4.addView(btn1);
                    row4.addView(btn2);
                    row.addView(row4);
                    if(row.getParent() != null) {
                        ((ViewGroup)row.getParent()).removeView(row); // <- fix
                    }
                    layout.addView(row);
                }else if(j > 2 && j <= 5 ){
                    Log.d("nnn2",String.valueOf(j));
                    row4 = new LinearLayout(this);
                    row4.setLayoutParams(paramsla);
                    row4.setOrientation(LinearLayout.VERTICAL);
                    row4.setGravity(1);
                    String colll = Names1.get(j);
                    String[] currencie = colll.split(",");
                  String ddd = currencie[2];
                    btn1 = new Button(this);
                    btn2 = new Button(this);
                    btn2.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,  ViewGroup.LayoutParams.WRAP_CONTENT )));
                    btn1.setLayoutParams(params);
                    if(in == 0){
                        switch1(ddd);
                        row4.setBackgroundResource(R.color.DarkGray);
                        btn2.setBackgroundResource(R.color.DarkGray);
                    }else{
                        switch2(ddd);
                        row4.setBackgroundResource(R.color.WhiteSmoke);
                        btn2.setBackgroundResource(R.color.WhiteSmoke);
                    }
                    btn2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.Black));
                    btn2.setTextSize(15);
                    btn1.setLayoutParams(params1);
                    row4.addView(btn1);
                    row4.addView(btn2);
                    row1.addView(row4);
                    if(row1.getParent() != null) {
                        ((ViewGroup)row1.getParent()).removeView(row1); // <- fix
                    }
                    layout.addView(row1);
                }else if(j > 5){
                    Log.d("nnn2",String.valueOf(j));
                    row4 = new LinearLayout(this);
                    row4.setLayoutParams(paramsla);
                    row4.setOrientation(LinearLayout.VERTICAL);
                    row4.setGravity(1);
                    String colll = Names1.get(j);
                    String[] currencie = colll.split(",");
                    String ddd = currencie[2];
                    btn1 = new Button(this);
                    btn2 = new Button(this);
                    btn2.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,  ViewGroup.LayoutParams.WRAP_CONTENT )));
                    btn2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.Black));
                    btn2.setTextSize(15);
                    btn1.setLayoutParams(params);
                    if(in == 0){
                        switch1(ddd);
                        row4.setBackgroundResource(R.color.DarkGray);
                        btn2.setBackgroundResource(R.color.DarkGray);
                    }else{
                        switch2(ddd);
                        row4.setBackgroundResource(R.color.WhiteSmoke);
                        btn2.setBackgroundResource(R.color.WhiteSmoke);
                    }
                    btn1.setLayoutParams(params1);
                    row4.addView(btn1);
                    row4.addView(btn2);
                    row3.addView(row4);
                    if(row3.getParent() != null) {
                        ((ViewGroup)row3.getParent()).removeView(row3); // <- fix
                    }
                    layout.addView(row3);
                }

            }

        String sesid = pref.getString("id","");
        String module_i = "10";
        ((Callback)getApplication()).privilege(sesid,module_i);
        String adpr = pref.getString("add_privilege","");
        String edpr = pref.getString("edit_privilege","");
     if(adpr.equalsIgnoreCase("0")&&edpr.equalsIgnoreCase("0")){
         nav_Menu.findItem(R.id.tally).setVisible(false);
         talb.setBackgroundResource(R.drawable.no_em);
         talb.setEnabled(false);
     }
        List<String> spin = new ArrayList<>();
        String sell = getString(R.string.morning);
        String ccred = getString(R.string.evening);
        String selee = getString(R.string.select);

        spin.add(sell);
        spin.add(ccred);
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
            type1.setAdapter(spinnerArrayAdapter);
        }
        else{
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
            type1.setAdapter(spinnerArrayAdapter);
        }
//        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
//                android.R.layout.simple_spinner_dropdown_item, Names);
        if(ses == 1){
            type.setSelection(0);
        }
        else if(ses == 2){
            type.setSelection(1);
        }

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                // Object item = parentView.getItemAtPosition(position);

                Integer nu = type
                        .getSelectedItemPosition();
               String  typeid = String.valueOf(nu);
//                Intent rtr = new Intent(NavigationActivity.this,NavigationActivity.class);
//                    startActivity(rtr);
                if(typeid.equalsIgnoreCase("0")){
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    String todaaaa = df.format(c.getTime());
                    editor.putString("buldate",todaaaa);
                    editor.putString("coldate",todaaaa);
                    editor.putString("tallydate",todaaaa);
                    editor.putInt("session",1);
                    editor.apply();
                    ses21 = 1;
                    if(ses21.equals(ses)){
                    }else{
                    Intent rtr = new Intent(NavigationActivity.this,NavigationActivity.class);
                    startActivity(rtr);
                    }
                }
                else if(typeid.equalsIgnoreCase("1")){
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    String todaaaa = df.format(c.getTime());
                    editor.putString("buldate",todaaaa);
                    editor.putString("coldate",todaaaa);
                    editor.putString("tallydate",todaaaa);
                    editor.putInt("session",2);
                    editor.apply();
                    ses21 = 2 ;
                    if(ses21.equals(ses)){
                    }else{
                        Intent rtr = new Intent(NavigationActivity.this,NavigationActivity.class);
                        startActivity(rtr);
                    }
                }
                Log.d("typedd",String.valueOf(nu));
//
            }
            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }

        });

//        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
//                android.R.layout.simple_spinner_dropdown_item, Names);
        if(ses == 1){
            type1.setSelection(0);
        }else if(ses == 2){
            type1.setSelection(1);
        }
        type1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                // Object item = parentView.getItemAtPosition(position);

                Integer nu = type1
                        .getSelectedItemPosition();
                String  typeid = String.valueOf(nu);

                if(typeid.equalsIgnoreCase("0")){
                    editor.putInt("session",1);
                    editor.apply();
                    Intent rtr = new Intent(NavigationActivity.this,NavigationActivity.class);
                    startActivity(rtr);
                }
                else if(typeid.equalsIgnoreCase("1")){
                    editor.putInt("session",2);
                    editor.apply();
                }
                Log.d("typedd",String.valueOf(nu));
//
            }
            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }

        });
//        String module_ii = "5";
//        ((Callback)getApplication()).privilege(sesid,module_ii);
//        String adpr1 = pref.getString("add_privilege","");
//        String edpr1 = pref.getString("edit_privilege","");
//        if(adpr1.equalsIgnoreCase("0")&&edpr1.equalsIgnoreCase("0")){
//            nav_Menu.findItem(R.id.collection).setVisible(false);
//            colb.setBackgroundResource(R.drawable.no_col);
//            colb.setEnabled(false);
//        }
        Button theme = lll.findViewById(R.id.theme);
        if(in == 0){
            logou.setBackgroundResource(R.drawable.logout);
            profil.setBackgroundResource(R.drawable.user1);
            theme.setBackgroundResource(R.drawable.theme);
        }
        else{
            logou.setBackgroundResource(R.drawable.logoutblue);
            profil.setBackgroundResource(R.drawable.user_blue);
            theme.setBackgroundResource(R.drawable.theme1);
        }
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NavigationActivity.this,Settings_activity.class);
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
                    Intent i = new Intent(NavigationActivity.this,NavigationActivity.class);
                    startActivity(i);

                }
                else{
                    editor.putString("theme","0");
                    editor.apply();
                    Intent i = new Intent(NavigationActivity.this,NavigationActivity.class);
                    startActivity(i);

                }


            }
        });

        logou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(NavigationActivity.this,R.style.AlertDialogTheme);
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
                                Intent i = new Intent(NavigationActivity.this,Splash.class);
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
        String dsdsd = pref.getString("NAME","");
        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date debit = df1.parse(dd);
            formattedDate = df.format(debit);
            Log.d("deae",formattedDate);
            String ffff = df1.format(debit);
            dateString = formattedDate;
            todateee = formattedDate;
            closed(dateString,getApplicationContext());
            beforebal(dateString,getApplicationContext());
            populateRecyclerView23(dateString,todateee,getApplicationContext());
         } catch (ParseException e) {
            e.printStackTrace();
        }
        if(ses == 1){
            ss = getString(R.string.morning);
        }else if(ses == 2){
            ss = getString(R.string.evening);
        }
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        LinearLayout wq = headerView.findViewById(R.id.navdash);
        TextView nn = headerView.findViewById(R.id.name);
        TextView nnn = headerView.findViewById(R.id.date);
        TextView nnnn = headerView.findViewById(R.id.session);

        nn.setText(dsdsd);
        nnn.setText("Date :"+" "+dd);
        nnnn.setText("Session :"+" "+ss);
        ImageView logy = headerView.findViewById(R.id.logy);
        if(in == 0){
            logy.setBackgroundResource(R.drawable.logout);
        }else{
            logy.setBackgroundResource(R.drawable.logoutblue);
        }
        logy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(NavigationActivity.this,R.style.AlertDialogTheme);
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
                                Intent i = new Intent(NavigationActivity.this,Splash.class);
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
                Intent qq = new Intent(NavigationActivity.this,NavigationActivity.class);
                startActivity(qq);
            }
        });
        ((Callback)getApplication()).NIP(ses);
        ((Callback)getApplication()).closed(ses);

usb.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent mn = new Intent(NavigationActivity.this,HomeActivity.class);
        startActivity(mn);
        finish();
    }
});
        debb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mn = new Intent(NavigationActivity.this,Debit.class);
                startActivity(mn);
                finish();
            }
        });colb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mn = new Intent(NavigationActivity.this,AllCollection.class);
                startActivity(mn);
                finish();
            }
        });acb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mn = new Intent(NavigationActivity.this,Account.class);
                startActivity(mn);
                finish();
            }
        });repb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mn = new Intent(NavigationActivity.this,ReportActivity.class);
                startActivity(mn);
                finish();
            }
        });cusb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mn = new Intent(NavigationActivity.this,Customer_activity.class);
                startActivity(mn);
                finish();
            }
        });
        printb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mn = new Intent(NavigationActivity.this,Print_activity.class);
                startActivity(mn);
//                finish();
            }
        });
//        setb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent mn = new Intent(NavigationActivity.this,Settings_activity.class);
//                startActivity(mn);
//            }
//        });
        talb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mn = new Intent(NavigationActivity.this,Tally_activity.class);
                startActivity(mn);
                finish();
            }
        });empb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mn = new Intent(NavigationActivity.this,Employee_details.class);
                startActivity(mn);
                finish();
            }
        });todbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mn = new Intent(NavigationActivity.this,Today_report.class);
                startActivity(mn);
                finish();
            }
        });
        populateRecyclerView();

//        editor.putString("company",zero);
//        editor.putString("user",one);
//        editor.putString("debit",two);
//        editor.putString("collection",three);
//        editor.putString("account",four);
//        editor.putString("reports",five);
//        editor.putString("customer",six);
//        editor.putString("settings",seven);
//        editor.putString("tally",eight);
//        editor.putString("employee_details",nine);
//        editor.putString("today_report",ten);
//        editor.apply();
//        if(one == "0"){
//            nav_Menu.findItem(R.id.user).setVisible(false);
//        }
        Calendar c = Calendar.getInstance();
        String formattedDate = df.format(c.getTime());
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String NIP = "SELECT SUM(b.debit_amount) as debitted,SUM(b.document_charge) as docuu,SUM(b.interest)as interr, " +
                "(SELECT sum(a.amount) FROM dd_accounting a INNER JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE  a.tracking_id = ? AND b.acc_type = '1') as acc_credited ," +
                "(SELECT sum(a.amount) FROM dd_accounting a INNER JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE  a.tracking_id = ? AND b.acc_type = '2') as acc_debited ," +
                "(SELECT sum(a.collection_amount) FROM dd_customers b LEFT JOIN dd_account_debit c ON c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND  a.collection_date <= ? AND  b.debit_type IN (0,1,2) AND c.active_status = 1) as collected ," +
                "(SELECT sum(a.discount) FROM dd_customers b LEFT JOIN dd_account_debit c ON c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND  a.collection_date <= ? AND  b.debit_type IN (0,1,2) AND c.active_status = 1) as discounted" +
                " FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE b.debit_date <= ? AND  a.debit_type IN (0,1,2) AND a.tracking_id = ? AND b.active_status = 1 ";
        String sess = String.valueOf(ses);
        Cursor cursor = database.rawQuery(NIP, new String[]{sess,sess,sess,formattedDate,sess,formattedDate,formattedDate,sess});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("docuu");
                    String documented = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("interr");
                    String interested = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debitted");
                    String debit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("collected");
                    String collect = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("acc_debited");
                    String acc_debit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("acc_credited");
                    String acc_credit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("discounted");
                    String deee = cursor.getString(index);
                    if(documented != null){
                    }else{
                        documented ="0";
                    }if(interested != null){
                    }else{
                        interested ="0";
                    }
                    if(debit != null){
                    }else{
                        debit ="0";
                    }
                    if(deee != null){
                    }else{
                        deee ="0";
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
                    Integer doc = Integer.parseInt(documented);
                    Integer dis = Integer.parseInt(deee);
                    Integer intr = Integer.parseInt(interested);
                    Integer dbb = Integer.parseInt(debit);
                    Integer ac_d = Integer.parseInt(acc_debit);
                    Integer col = Integer.parseInt(collect);
                    col = col - dis;
                    Integer ac_c = Integer.parseInt(acc_credit);
                    Integer total = (ac_c+col+doc)-((dbb-intr)+ac_d);
                    Log.d("Callid", collect+" "+debit+" "+acc_debit+" "+acc_credit);
                    Log.d("Callid22",String.valueOf(total));

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
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.clear();
//                editor.putInt("isloginn",0);
//                editor.commit();
//                Intent i = new Intent(NavigationActivity.this,Splash.class);
//                startActivity(i);
//            }
//        });
//        Intent intent = new Intent(this, CallDetectionService.class);
//        startService(intent);
//        SharedPreferences sharedPreferences = getSharedPreferences(CallDetector.MY_PREF,MODE_PRIVATE);
//        String number = sharedPreferences.getString(CallDetector.NUMBER_KEY,"No Value Found");
//        phone.setText(number);
        importb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent ew = new Intent(NavigationActivity.this,ExportDB.class);
//                startActivity(ew);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 7);
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                startActivityForResult(Intent.createChooser(intent,"Select file or dir"), 1);
//                setResult(Activity.RESULT_OK);
//                import_data();
            }
        });
//        lastlogin();
        checkkk();
        progressbar_load();
        curl_debita("dd_delete",getApplicationContext());
    }


    //curl_debita() - Check table name is there in DB
    //Params - tablename and application context
    //Created on 25/01/2022
    //Return - NULL
    public boolean curl_debita(String tableName,Context context) {
        boolean isExist = false;
        SQLiteHelper sqlite = new SQLiteHelper(this);
        SQLiteDatabase database = sqlite.getReadableDatabase();
        Cursor cursor = database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                isExist = true;
                curl_debit(context);
            }
            cursor.close();
        }
        return isExist;
    }

    //curl_debit() - get date from dd_delete datble
    //Params -  application context
    //Created on 25/01/2022
    //Return - NULL
    public void curl_debit(final Context context){

        //Called each time when 1000 milliseconds (1 second) (the period parameter)
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        SharedPreferences prefs1 = context.getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = prefs1.edit();
        SQLiteHelper sqlite = new SQLiteHelper(context);
        SQLiteDatabase database = sqlite.getReadableDatabase();
        int ses = prefs1.getInt("session", 1);
        Log.d("dateString2",String.valueOf(ses));
        String NIP =
//                "SELECT c.*,a.id as ID , sum(c.debit_amount) as sum_debit FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 0 AND c.debit_date < ?";
                "SELECT * FROM dd_delete WHERE tracking_id = ? ";
        Cursor cursor = database.rawQuery(NIP, new String[] {String.valueOf(ses)});

        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("created_date");
                    dateString2 = cursor.getString(index);
                     Log.d("dateString2",dateString2);
                }
                while (cursor.moveToNext());
//                progressbar_stop();
                cursor.close();
                sqlite.close();
                database.close();
            }else{
//                progressbar_stop();
                cursor.close();
                sqlite.close();
                database.close();
            }
        }else{

            cursor.close();
            sqlite.close();
            database.close();
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(dateString2 == null || dateString2.equalsIgnoreCase("") || dateString2.equalsIgnoreCase("null")){
                            Log.d("dateString21","dateString2");
                            progressbar_stop();
                        }else{
                            beforebal1();
                            beforebal0();
                            beforebal11();
                            beforebal111();
                        }
                    }
                });
            }
        }, 2500);



//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//
//
//            }
//        }, 12000);
    }

    //beforebal1() - get debit datas
    //Params -  application context
    //Created on 25/01/2022
    //Return - NULL
    public void beforebal1(){
        Names0.clear();
//        Names2.clear();
        SQLiteHelper sqlite = new SQLiteHelper(this);
        SQLiteDatabase database = sqlite.getReadableDatabase();
        String NIP =
//                "SELECT c.*,a.id as ID , sum(c.debit_amount) as sum_debit FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 0 AND c.debit_date < ?";
                "SELECT c.* , sum(c.debit_amount) as sum_debit FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id  WHERE " +
                        " b.tracking_id = ? AND c.active_status = 0 AND c.updated_date <= ? GROUP BY c.id";

        String sess = String.valueOf(ses);
        Log.d("ssssseeeeee111",sess+" "+dateString2);
        Cursor cursor = database.rawQuery(NIP, new String[]{sess,dateString2});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("id");
                    String debit = cursor.getString(index);
                    Log.d("ccccooooo",debit);
//                    index = cursor.getColumnIndexOrThrow("ID");
//                    String debit1 = cursor.getString(index);
//                    Log.d("ccccooooo_debit1",debit1);
                    index = cursor.getColumnIndexOrThrow("sum_debit");
                    String sum_debit = cursor.getString(index);
                    Log.d("sum_debit",sum_debit);
                    Names0.add(debit);
//                    Names2.add(debit1);

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

    //beforebal0() - get collection
    //Params -  application context
    //Created on 25/01/2022
    //Return - NULL
    public void beforebal0(){
//        Names.clear();
        Names02.clear();
        SQLiteHelper sqlite = new SQLiteHelper(this);
        SQLiteDatabase database = sqlite.getReadableDatabase();
        String NIP =
                "SELECT c.*,a.id as ID , sum(c.debit_amount) as sum_debit FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id " +
                        "LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 0 AND c.updated_date <= ? GROUP BY a.id";
//                "SELECT c.* , sum(c.debit_amount) as sum_debit FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id  WHERE  b.tracking_id = ? AND c.active_status = 0 AND c.debit_date < ?";

        String sess = String.valueOf(ses);
        Log.d("ssssseeeeee111",sess+" "+dateString2);
        Cursor cursor = database.rawQuery(NIP, new String[]{sess,dateString2});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("id");
                    String debit = cursor.getString(index);
//                    Log.d("ccccooooo",debit);
                    index = cursor.getColumnIndexOrThrow("ID");
                    String debit1 = cursor.getString(index);
//                    Log.d("ccccooooo_debit1",debit1);
                    index = cursor.getColumnIndexOrThrow("sum_debit");
                    String sum_debit = cursor.getString(index);
//                    Log.d("sum_debit",sum_debit);
//                    Names.add(debit);
                    Names02.add(debit1);

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

    //beforebal11() - get tally datas
    //Params -  application context
    //Created on 25/01/2022
    //Return - NULL
    public void beforebal11(){
        Names03.clear();
        SQLiteHelper sqlite = new SQLiteHelper(this);
        SQLiteDatabase database = sqlite.getReadableDatabase();
        String NIP =
                "SELECT * FROM dd_tally WHERE created_date <= ? AND tracking_id = ?";
        String sess = String.valueOf(ses);
        Log.d("ssssseeeeee111",sess+" "+dateString2);
        Cursor cursor = database.rawQuery(NIP, new String[]{dateString2,sess});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("id");
                    String debit = cursor.getString(index);
//                    Log.d("talllyyyyyy",debit);

                    Names03.add(debit);


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

    //beforebal111() - get accounting datas
    //Params -  application context
    //Created on 25/01/2022
    //Return - NULL
    public void beforebal111(){
        Names04.clear();
        SQLiteHelper sqlite = new SQLiteHelper(this);
        SQLiteDatabase database = sqlite.getReadableDatabase();
        String NIP =
                "SELECT * FROM dd_accounting  WHERE accounting_date <= ? AND tracking_id = ? ";
        String sess = String.valueOf(ses);
        Log.d("ssssseeeeee111",sess+" "+dateString2);
        Cursor cursor = database.rawQuery(NIP, new String[]{dateString2,sess});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("id");
                    String debit = cursor.getString(index);
//                    Log.d("accounttttt",debit);

                    Names04.add(debit);


                }
                while (cursor.moveToNext());
                if(Names0.size() > 0 || Names02.size() > 0 || Names03.size() > 0 || Names04.size() > 0){
                    progressbar_stop();
                    Log.d("namessize",String.valueOf(Names0.size())+" "+String.valueOf(Names02.size())+" "+String.valueOf(Names03.size())+" "+String.valueOf(Names04.size()));
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(NavigationActivity.this,R.style.AlertDialogTheme);
                    String enn = getString(R.string.some_datas_need);
                    String war = getString(R.string.warning);
                    String ook = getString(R.string.ok);
                    alertbox.setMessage(enn);
                    alertbox.setTitle(war);
                    alertbox.setCancelable(false);
                    alertbox.setIcon(R.drawable.dailylogo);
                    alertbox.setPositiveButton(ook,
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                    progressbar_load();
                                    new Timer().schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    all_delete();
                                                }
                                            });
                                        }
                                    }, 500);



//
                                }
                            });
                alertbox.show();
                }
                else{

                    progressbar_stop();
                }

                cursor.close();
                sqlite.close();
                database.close();
            }else{
                if(Names0.size() > 0 || Names02.size() > 0 || Names03.size() > 0 || Names04.size() > 0){
                    Log.d("namessize",String.valueOf(Names0.size())+" "+String.valueOf(Names02.size())+" "+String.valueOf(Names03.size())+" "+String.valueOf(Names04.size()));
                    progressbar_stop();
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(NavigationActivity.this,R.style.AlertDialogTheme);
                    String enn = getString(R.string.some_datas_need);
                    String war = getString(R.string.warning);
                    String ook = getString(R.string.ok);
                    alertbox.setMessage(enn);
                    alertbox.setTitle(war);
                    alertbox.setCancelable(false);
                    alertbox.setIcon(R.drawable.dailylogo);
                    alertbox.setPositiveButton(ook,
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0,
                                                    int arg1) {

                                    progressbar_load();
                                    new Timer().schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    all_delete();
                                                }
                                            });
                                        }
                                    }, 500);
//                                    login.setMobileDataEnabled(login,true);
//                                    settings_activity.ssss();

                                }
                            });
                    alertbox.show();
                }
                else{

                    progressbar_stop();
                }
                cursor.close();
                sqlite.close();
                database.close();
            }
        }else{
            if(Names0.size() > 0 || Names02.size() > 0 || Names03.size() > 0 || Names04.size() > 0){
                Log.d("namessize",String.valueOf(Names0.size())+" "+String.valueOf(Names02.size())+" "+String.valueOf(Names03.size())+" "+String.valueOf(Names04.size()));
                progressbar_stop();
                AlertDialog.Builder alertbox = new AlertDialog.Builder(NavigationActivity.this,R.style.AlertDialogTheme);
                String enn = getString(R.string.some_datas_need);
                String war = getString(R.string.warning);
                String ook = getString(R.string.ok);
                alertbox.setMessage(enn);
                alertbox.setTitle(war);
                alertbox.setCancelable(false);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setPositiveButton(ook,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {

                                progressbar_load();
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                all_delete();
                                            }
                                        });
                                    }
                                }, 500);
//                                    login.setMobileDataEnabled(login,true);
//                                    settings_activity.ssss();

                            }
                        });
                alertbox.show();
            }
            else{
                progressbar_stop();
            }
            cursor.close();
            sqlite.close();
            database.close();
        }
    }

    //all_delete() -delete all datas
    //Params -  application context
    //Created on 25/01/2022
    //Return - NULL
    public void all_delete(){

            for(int k=0;k<Names03.size();k++){
                String s = Names03.get(k);
                SQLiteHelper sqlite = new SQLiteHelper(getApplicationContext());
                SQLiteDatabase database = sqlite.getReadableDatabase();
                String table = "dd_tally";
                String whereClause = "id=?";
//        database.delete(table, whereClause, whereArgs);
                String[] whereArgs = new String[] {s};
                database.delete(table, whereClause, whereArgs);
                sqlite.close();
                database.close();
            }
            for(int l=0;l<Names04.size();l++){
                String s = Names04.get(l);
                SQLiteHelper sqlite = new SQLiteHelper(getApplicationContext());
                SQLiteDatabase database = sqlite.getReadableDatabase();
                String table = "dd_accounting";
                String whereClause = "id=?";
//        database.delete(table, whereClause, whereArgs);
                String[] whereArgs = new String[] {s};
                database.delete(table, whereClause, whereArgs);
                sqlite.close();
                database.close();
            }
            Integer ssii = Names02.size() - 1 ;
        Integer ssii0 = Names02.size()  ;
            if(ssii0<=0){
                Integer sass = Names0.size() - 1 ;
                Integer sass0 = Names0.size()  ;
                 if(sass0<=0){
                            progressbar_stop();
                        }else{
                     for(int i=0;i<Names0.size();i++){
                         String s = Names0.get(i);
                         SQLiteHelper sqlite = new SQLiteHelper(getApplicationContext());
                         SQLiteDatabase database = sqlite.getReadableDatabase();
                         String table = "dd_account_debit";
                         String whereClause = "id=?";

//        database.delete(table, whereClause, whereArgs);
                         String[] whereArgs = new String[] {s};
                         database.delete(table, whereClause, whereArgs);
                         sqlite.close();
                         database.close();
                         if(sass.equals(i)){
                             progressbar_stop();
                             Names0.clear();
                             Names02.clear();
                             Names03.clear();
                             Names04.clear();
                             AlertDialog.Builder alertbox = new AlertDialog.Builder(NavigationActivity.this, R.style.AlertDialogTheme);
                             String rer = getString(R.string.account_should_close);
                             String rer1 = getString(R.string.warning);
                             String rer2 = getString(R.string.ok);
                             alertbox.setCancelable(false);
                             alertbox.setMessage(rer);
                             alertbox.setTitle(rer1);
                             alertbox.setIcon(R.drawable.dailylogo);
                             alertbox.setPositiveButton(rer2,
                                     new DialogInterface.OnClickListener() {

                                         public void onClick(DialogInterface arg0,
                                                             int arg1) {
                                         }

                                     });
                             alertbox.show();
                         }
                     }
                 }



            }
            else{
                for(int j=0;j<Names02.size();j++){
                    String s = Names02.get(j);
                    SQLiteHelper sqlite = new SQLiteHelper(getApplicationContext());
                    SQLiteDatabase database = sqlite.getReadableDatabase();
                    String table = "dd_collection";
                    String whereClause = "id=?";
//        database.delete(table, whereClause, whereArgs);
                    String[] whereArgs = new String[] {s};
                    database.delete(table, whereClause, whereArgs);
                    sqlite.close();
                    database.close();
                    Log.d("deleted_deleted",s+" "+String.valueOf(ssii)+" "+String.valueOf(j));
                    if(ssii.equals(j)){
                        Log.d("deleted_deleted_123",String.valueOf(ssii)+" "+String.valueOf(j));
                        Integer sass = Names0.size() - 1 ;
                        Integer sass0 = Names0.size() ;
                         if(sass0<=0){
                            progressbar_stop();
                             Names0.clear();
                             Names02.clear();
                             Names03.clear();
                             Names04.clear();
                        }else{
                              for(int i=0;i<Names0.size();i++){
                            String s1 = Names0.get(i);
                            SQLiteHelper sqlite1 = new SQLiteHelper(getApplicationContext());
                            SQLiteDatabase database1 = sqlite1.getReadableDatabase();
                            String table1 = "dd_account_debit";
                            String whereClause1 = "id=?";

//        database.delete(table, whereClause, whereArgs);
                            String[] whereArgs1 = new String[] {s1};
                            database1.delete(table1, whereClause1, whereArgs1);
                            sqlite1.close();
                            database1.close();
                            if(sass.equals(i)){
                                progressbar_stop();
                                Names0.clear();
                                Names02.clear();
                                Names03.clear();
                                Names04.clear();
                                AlertDialog.Builder alertbox = new AlertDialog.Builder(NavigationActivity.this, R.style.AlertDialogTheme);
                                String rer = getString(R.string.account_should_close);
                                String rer1 = getString(R.string.warning);
                                String rer2 = getString(R.string.ok);
                                alertbox.setCancelable(false);
                                alertbox.setMessage(rer);
                                alertbox.setTitle(rer1);
                                alertbox.setIcon(R.drawable.dailylogo);
                                alertbox.setPositiveButton(rer2,
                                        new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface arg0,
                                                                int arg1) {

                                            }

                                        });
                                alertbox.show();
                            }
                        }
                         }

                    }
                }
            }

//            progre();

    }

    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final NavigationActivity settings_activity) {
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
                AlertDialog.Builder alertbox = new AlertDialog.Builder(settings_activity,R.style.AlertDialogTheme);
                String enn = settings_activity.getString(R.string.on_internet);
                String war = settings_activity.getString(R.string.warning);
                String ook = settings_activity.getString(R.string.ok);
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

    //checkUpdate() - Check for app update in playstore
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void checkUpdate() {
        Log.d("app_update","No Update");
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                Log.d("app_update","Immediate");
                startUpdateFlow(appUpdateInfo);
            } else if  (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){
                Log.d("app_update","In-progress");
                startUpdateFlow(appUpdateInfo);
            }else{
                Log.d("app_update","No Update");
            }
        });
    }

    //startUpdateFlow() - Updat app from playstore
    //Params - AppUpdateInfo
    //Created on 25/01/2022
    //Return - NULL
    private void startUpdateFlow(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, this, NavigationActivity.IMMEDIATE_APP_UPDATE_REQ_CODE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }
    //onActivityResult() - Result from playstore
    //Params - AppUpdateInfo
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMMEDIATE_APP_UPDATE_REQ_CODE) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Update canceled by user! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Update success! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Update Failed! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
                checkUpdate();
            }
        }
    }

//    private void checkUpdate() {
//        Log.d("app_update1","No Update");
//        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
//        Log.d("app_update","No Update");
//        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
//            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
//                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
//                Log.d("app_update","Flexible");
//                startUpdateFlow(appUpdateInfo);
//            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
//                Log.d("app_update","Immediate");
//                popupSnackBarForCompleteUpdate();
//            }else{
//                Log.d("app_update","No Update");
//            }
//        });
//    }
//
//    private void startUpdateFlow(AppUpdateInfo appUpdateInfo) {
//        try {
//            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, this, FLEXIBLE_APP_UPDATE_REQ_CODE);
//        } catch (IntentSender.SendIntentException e) {
//            e.printStackTrace();
//        }
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == FLEXIBLE_APP_UPDATE_REQ_CODE) {
//            if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(getApplicationContext(), "Update canceled by user! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
//            } else if (resultCode == RESULT_OK) {
//                Toast.makeText(getApplicationContext(),"Update success! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(getApplicationContext(), "Update Failed! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
//                checkUpdate();
//            }
//        }
//    }
//
//    private void popupSnackBarForCompleteUpdate() {
//        Snackbar.make(findViewById(android.R.id.content).getRootView(), "New app is ready!", Snackbar.LENGTH_INDEFINITE)
//
//                .setAction("Install", view -> {
//                    if (appUpdateManager != null) {
//                        appUpdateManager.completeUpdate();
//                    }
//                })
//                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
//                .show();
//    }
//
//    private void removeInstallStateUpdateListener() {
//        if (appUpdateManager != null) {
//            appUpdateManager.unregisterListener(installStateUpdatedListener);
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        removeInstallStateUpdateListener();
//    }

    //License1() - get settings datas
    //Params - AppUpdateInfo
    //Created on 25/01/2022
    //Return - NULL
    public void License1(){
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY = "SELECT * FROM dd_collection where id = ?";
        String whereClause = "ID=?";
        String[] whereArgs = new String[] {"1"};
        String[] columns = {"license",
                "cc","ID","email","expiry","language","pdf_password","phone_number","version"};
        Cursor cursor = database.query("dd_settings",
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("license");
                    License = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("version");
                    version = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("email");
                    String email = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("cc");
                    String cc = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("expiry");
                    String expiry = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("language");
                    String language = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("pdf_password");
                    String password = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("phone_number");
                    String phone_number = cursor.getString(index);
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("dd_license", 0); // 0 - for private mode
                    String ii = pref.getString("dd_license","");
                    if(ii == null || ii.equalsIgnoreCase("") || ii.equalsIgnoreCase("null")){
                        SharedPreferences.Editor edd = pref.edit();
                        edd.putString("dd_license",License);
                        edd.apply();
                    }

                    String all_val = "License No :"+" "+License+"\n"+"Email :"+" "+email+"\n"+"Email CC :"+" "+cc+"\n"+"Expiry Date :"+" "+expiry+"\n"+"Language :"+" "+language+"\n"+"PDF Password :"+" "+password+"\n"+
                            "Phone Number :"+" "+phone_number+"\n"+"Username :"+" "+li_user+"\n"+"Password :"+" "+li_pass;
//                    WriteBtn(view,all_val,licc);
                    if(version == null || version.equalsIgnoreCase("")){
//                        License = "no";
                    }else {
                        Integer vv = Integer.parseInt(version);
                        if(vv>=13){
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((Callback)getApplication()).License1(NavigationActivity.this);
                                        }
                                    });
                                }
                            }, 2500);

                        }
                    }
                    if(License == null || License.equalsIgnoreCase("")){
//                        License = "no";
                    }else {

                        String pathh = License+"/info/info.txt";
                        show_filee(pathh,all_val);
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
    }

    //WriteBtn() - get info file
    //Params - view , file location ,license
    //Created on 25/01/2022
    //Return - NULL
    public void WriteBtn(View v,String all_val,String liccc) {
        // add-write text into file
//        File file = new File(Settings_activity.this.getFilesDir(), "text");
        File file = new File(Environment.getExternalStorageDirectory() + "/Documents");

        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File gpxfile = new File(file, "sample.txt");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(all_val);
            writer.flush();
            writer.close();
            info_firebase(Uri.fromFile(gpxfile),liccc,"info.txt");
//            Toast.makeText(Settings_activity.this, "Saved your text", Toast.LENGTH_LONG).show();
        } catch (Exception e) { }
    }

    //show_filee() - upload info file
    //Params -  file name ,info datas
    //Created on 25/01/2022
    //Return - NULL
    public void show_filee(String value , final String all_val){
//        progressbar_load();
        FirebaseStorage storage;
        storage = FirebaseStorage.getInstance();
//        // Create a storage reference from our app
//        StorageReference storageRef = storage.getReferenceFromUrl("gs://<your-bucket-name>");
//
//// Create a reference with an initial file path and name
//        StorageReference pathReference = storageRef.child("users/me/yourpics.png");
        StorageReference storageRef = storage.getReference();
        storageRef.child(String.valueOf(value)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Log.d("uuurrrllll",String.valueOf(uri));
//                filenaaamee = value ;
//                mMyTask = new DownloadFileFromURL().execute(String.valueOf(uri));
                WriteBtn(llll1,all_val,License);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.d("uuurrrllll","File Not Found");
                WriteBtn(llll1,all_val,License);
//                Toast.makeText(getApplicationContext(), "File Not Found",
//                        Toast.LENGTH_LONG).show();
//                cancel_loader();
            }
        });
    }

    //info_firebase() - upload info file
    //Params -  file uri ,file name ,name
    //Created on 25/01/2022
    //Return - NULL
    public void info_firebase(Uri uri, String value, String filee){
        FirebaseStorage storage;
        storage = FirebaseStorage.getInstance();
        StorageMetadata metadata = new StorageMetadata.Builder()
//                .setContentType("image/jpeg")
                .build();
        StorageReference ref = storage.getReference().child(String.valueOf(value)+"/"+"info"+"/"+String.valueOf(filee));
        String finalValue = value;
        ref.putFile(uri,metadata)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("dddata","success");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("dddata","failure"+" "+String.valueOf(e));

                    }
                });
    }

    //checkkk() -Check DB version
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void checkkk(){
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();

        String MY_QUERY = "SELECT * FROM dd_collection where id = ?";
        String whereClause = "ID=?";
        String[] whereArgs = new String[] {"1"};

//        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{idd});
        String[] columns = {"license",
                "cc","ID","email","expiry","language","password","oldversion","version"};
//        String order = "collection_date";
        Cursor cursor = database.query("dd_settings",
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("oldversion");
                    oldv1 = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("version");
                    newv1 = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("license");
                    licee = cursor.getString(index);
                    if(licee == null || licee.equalsIgnoreCase("") || licee.equalsIgnoreCase("null")){
                    }else{
//                        deleteee(0);
//                        check_list();
                        dd_files_license();
                    }
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("oldv1",oldv1);
                    editor.putInt("newv1",newv1);
                    Integer oldv = pref.getInt("oldv",0);
                    Integer newv = pref.getInt("newv",0);
                    editor.putInt("oldv",oldv1);
                    editor.putInt("newv",newv1);
                    editor.apply();
                    Log.d("oldd_neww",String.valueOf(oldv1)+" "+String.valueOf(newv1)+" : "+String.valueOf(oldv)+" "+String.valueOf(newv));
                   if(newv.equals(newv1)){
                        Log.d("oldd_neww1",String.valueOf(oldv1)+" "+String.valueOf(newv1)+" : "+String.valueOf(oldv)+" "+String.valueOf(newv));
                    }else{
                        if(newv1<=11){
                            Log.d("oldd_neww2",String.valueOf(oldv1)+" "+String.valueOf(newv1)+" : "+String.valueOf(oldv)+" "+String.valueOf(newv));
                            syncEvent1 syncEvent1 = new syncEvent1();
                            syncEvent1.execute();
                        }
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
    }

    //dd_files_license() -Delete older files in firebase
    //Params -  NULL
    //Created on 25/01/2022
    //Return - NULL
    public void dd_files_license(){
//        final SharedPreferences pref = mContext.getSharedPreferences("MyPref", 0); // 0 - for private mode
//        String id = pref.getString("userid", "");
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        files_array.clear();
        files_array_id.clear();
        arraylist.clear();
//        DocumentReference user = db.collection("PhoneBook").document("Contacts");
        CollectionReference citiesRef = db.collection("dd_files_license");
        citiesRef.whereEqualTo("license",licee)
//                .orderBy("created_date", Query.Direction.ASCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    QuerySnapshot document1 = task.getResult();
                    Integer cco = document1.size();
                    Integer vva = 0;
                    String tot = String.valueOf(cco);

                    if(cco<=0){

                    }else{
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            list.add(document.getId());
                            String id = document.getString("id");
                            String file = document.getString("filename");
                            String created_date = document.getString("for_order");
                            files_array.add(file+",,,"+created_date);
                            files_array_id.add(id);
                            arraylist.add(new Sorting(file,created_date,id));
                            Collections.sort(arraylist, Sorting.days_desc);
                            Log.d("license_file11",String.valueOf(arraylist));
                            vva = vva + 1;
                            if(vva.equals(cco)){

                                files_array.clear();
                                files_array_id.clear();
                                Integer noo = 0 ;
                                for(Sorting str: arraylist) {
                                    Log.d("dissss_array", String.valueOf(str));
                                    noo = noo + 1;
                                    String value = String.valueOf(str);
                                    String[] split = value.split(",,,");
                                    files_array.add(split[0]+",,,"+split[1]);
                                    files_array_id.add(split[2]);
                                    if(noo.equals(arraylist.size())){
                                        Integer nn = 0 ;
                                        nn = files_array.size() - 4 ;
                                        if(files_array.size()>4){
                                            setingsa = 0 ;
                                            del(nn);
                                        }
                                    }
                                }

                            }
                        }
                    }

                } else {
                    Log.d("gettingids", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    //del() -Delete older files in firebase
    //Params -  data count
    //Created on 25/01/2022
    //Return - NULL
    public void del(final Integer bb){
//        final String getdate = files_array.get(setingsa);
//        Log.d("setingsa0",getdate);
//        setingsa = setingsa + 1 ;
//        Log.d("setingsa",String.valueOf(setingsa)+" "+String.valueOf(bb));
//        if(setingsa.equals(bb)){
//        }else{
//            del(bb);
//        }

        FirebaseStorage storage;
//        getvv = bb ;
        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        // Create a reference to the file to delete
        String getdd = files_array.get(setingsa);
        String[] getdd1 = getdd.split(",,,");
//        final String getdate = files_array.get(setingsa);
        final String getdate = getdd1[0];
        Log.d("setingsa0",getdate);
        final String getid = files_array_id.get(setingsa);
        Log.d("setingsa01",getid);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("dd_files_license").document(getid)
                .delete().addOnSuccessListener(new OnSuccessListener< Void >() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
        StorageReference desertRef = storageRef.child(licee+"/"+"daily"+"/"+getdate);
        // Delete the file
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
                setingsa = setingsa + 1 ;
                Log.d("setingsa1",String.valueOf(setingsa)+" "+String.valueOf(bb));
                if(setingsa.equals(bb)){
                }else{
                    del(bb);
                }
//                if(getvv < dates_array.size()){
//                    del(getvv);
//                }else{
//                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Uh-oh, an error occurred!
                        Log.d("delete","File not found");
                        setingsa = setingsa + 1 ;
                        Log.d("setingsa2",String.valueOf(setingsa)+" "+String.valueOf(bb));
                        if(setingsa.equals(bb)){
                        }else{
                            del(bb);
                        }
                    }
                });
    }

    //syncEvent1() - update orders
    //Params -  NULL
    //Created on 25/01/2022
    //Return - NULL
    private class syncEvent1 extends AsyncTask<String, Integer, String> {

        //Add 1. Declare RelativeLaout
        private RelativeLayout relativeView;
        private ProgressBar progressBar;

        @Override
        protected String doInBackground(String... arg0) {
            //your operation task here
//            ((Callback)getApplication()).reorder_data(getApplicationContext());
            ((Callback)getApplication()).populateRecyclerView_new1(getApplicationContext(),"1");
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            progressbar_stop();
        }

        @Override
        protected void onPreExecute() {
            progressbar_load();
            //Add 3. add 2 lines of code here
//            relativeView = (RelativeLayout)findViewById(R.id.loadingevent);


        }

        // For this method, i don't need to use it yet,so, i left it here
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }

    //syncEvent() -Reorder datas
    //Params -  NULL
    //Created on 25/01/2022
    //Return - NULL
    private class syncEvent extends AsyncTask<String, Integer, String> {

        //Add 1. Declare RelativeLaout
        private RelativeLayout relativeView;
        private ProgressBar progressBar;

        @Override
        protected String doInBackground(String... arg0) {
            //your operation task here
            ((Callback)getApplication()).reorder_data(getApplicationContext());
//            ((Callback)getApplication()).populateRecyclerViewnip_new(ses);
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            progressbar_stop();
        }

        @Override
        protected void onPreExecute() {
            progressbar_load();
            //Add 3. add 2 lines of code here
//            relativeView = (RelativeLayout)findViewById(R.id.loadingevent);


        }

        // For this method, i don't need to use it yet,so, i left it here
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }

    //progressbar_load() - Load progressbar
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void progressbar_load(){
        //set layout custom
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.progressbar);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog1.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog1.setCancelable(false);
        dialog1.getWindow().setAttributes(lp);
        dialog1.show();

    }
    //progressbar_stop() - Stop progressbar
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void progressbar_stop(){
        dialog1.dismiss();
//        Intent nn = new Intent(Draganddrop.this,Draganddrop.class);
//        startActivity(nn);
//        finish();
    }
    //progressbar_load1() - Load progressbar
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void progressbar_load1(){
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
    //expiiii() -get expiry date
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void expiiii(){
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY = "SELECT * FROM dd_collection where id = ?";
        String whereClause = "ID=?";
        String[] whereArgs = new String[] {"1"};
        String[] columns = {"license",
                "cc","ID","email","expiry","language","password"};
        Cursor cursor = database.query("dd_settings",
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("expiry");
                    String dat = cursor.getString(index);
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
                    //    String myFormat1 = "dd/MM/yyyy";//In which you need put here
                    String formattedDate = df.format(c.getTime());
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    String ss2 = String.valueOf(c.getTime());
                    Log.d("dateddche", ss2);
                    String pattern = "dd/MM/yyyy";

                    SimpleDateFormat dateFormat1 = new SimpleDateFormat(pattern);
                    String ss = dateFormat1.format(c.getTime());
//                    String ss1 = pref.getString("lastlogin", null);
                    String ss1 = dat;
                    try {
                        Date newda = df1.parse(ss1);
                        String ss21 = df.format(newda);
                        Log.d("datedddiiffche",ss+" "+ss1+" "+ss21);
                        int dateDifference = (int) Callback.getDateDiff(new SimpleDateFormat("dd/MM/yyyy"), ss, ss21);
                        System.out.println("dateDifferenceche: " + dateDifference);
                        if(dateDifference <= 4 ){
                            String app_expo = getString(R.string.expiry_date_al);
                            String day = getString(R.string.days);
                            String val = app_expo+" "+String.valueOf(dateDifference)+" "+day;
                            AlertDialog.Builder alertbox = new AlertDialog.Builder(NavigationActivity.this,R.style.AlertDialogTheme);
                            String logmsg = getString(R.string.expirydate_alert);
                            String warr = getString(R.string.warning);
                            String logo = getString(R.string.ok);
                            alertbox.setMessage(val);
                            alertbox.setTitle(warr);
                            alertbox.setIcon(R.drawable.dailylogo);
                            alertbox.setPositiveButton(logo,
                                    new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface arg0,
                                                            int arg1) {

                                        }
                                    });
                            alertbox.show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
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
    }
    //onRestart() - Called when activity is restarted
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    protected void onRestart() {
        super.onRestart();
        expiiii();
        Log.d("resumes","122");
    }
    //onStart() - Called when activity is started
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    protected void onStart() {
        super.onStart();
        expiiii();
        Log.d("resumes","123");
    }
    //lastlogin() - put login time
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void lastlogin(){
//    sqlite = new SQLiteHelper(Login.this);
//    database = sqlite.getReadableDatabase();
        Calendar c = Calendar.getInstance();
        String ss2 = String.valueOf(c.getTime());
        Log.d("datedd",ss2);
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String hh = dateFormat.format(c.getTime());
        Log.d("datedd1",hh);
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        editor.putString("lastlogin",hh);
        editor.apply();
    }

    //switch1() - on click function for all buttons
    //Params - button id
    //Created on 25/01/2022
    //Return - NULL
public void switch1(String id){
    Log.d("iooppoi",id);
    switch (id){


        case "1":
            btn2.setText(getString(R.string.customer12));
            btn1.setBackgroundResource(R.drawable.customer);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,HomeActivity.class);
                    startActivity(mn);
                    finish();
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,HomeActivity.class);
                    startActivity(mn);
                    finish();
                }
            });
            row4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,HomeActivity.class);
                    startActivity(mn);
                    finish();
                }
            });
            break;
        case "2":
            btn2.setText(getString(R.string.debit210));
            btn1.setBackgroundResource(R.drawable.debit);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,Debit.class);
                    startActivity(mn);
                    finish();
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,Debit.class);
                    startActivity(mn);
                    finish();
                }
            });
            row4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,Debit.class);
                    startActivity(mn);
                    finish();
                }
            });
            break;
        case "3":
            btn2.setText(getString(R.string.collection12));
            btn1.setBackgroundResource(R.drawable.collection);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,AllCollection.class);
                    startActivity(mn);
                    finish();
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,AllCollection.class);
                    startActivity(mn);
                    finish();
                }
            });
            row4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,AllCollection.class);
                    startActivity(mn);
                    finish();
                }
            });
            break;
        case "4":
            btn2.setText(getString(R.string.account1));
            btn1.setBackgroundResource(R.drawable.account);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,Account.class);
                    startActivity(mn);
                    finish();
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,Account.class);
                    startActivity(mn);
                    finish();
                }
            });
            row4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,Account.class);
                    startActivity(mn);
                    finish();
                }
            });
            break;
        case "5":
            btn2.setText(getString(R.string.reports1));
            btn1.setBackgroundResource(R.drawable.reports);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,ReportActivity.class);
                    startActivity(mn);
                    finish();
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,ReportActivity.class);
                    startActivity(mn);
                    finish();
                }
            });
            row4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,ReportActivity.class);
                    startActivity(mn);
                    finish();
                }
            });
            break;
        case "6":
            btn2.setText(getString(R.string.individual));
            btn1.setBackgroundResource(R.drawable.user1);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,Customer_activity.class);
                    startActivity(mn);
                    finish();
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,Customer_activity.class);
                    startActivity(mn);
                    finish();
                }
            });
            row4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,Customer_activity.class);
                    startActivity(mn);
                    finish();
                }
            });
            break;
        case "7":
            btn2.setText(getString(R.string.print));
            btn1.setBackgroundResource(R.drawable.print);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,Print_activity.class);
                    startActivity(mn);
//                    finish();
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,Print_activity.class);
                    startActivity(mn);
//                    finish();
                }
            });
            row4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,Print_activity.class);
                    startActivity(mn);
//                    finish();
                }
            });
            break;
        case "8":
            btn2.setText(getString(R.string.employee_details2));
            btn1.setBackgroundResource(R.drawable.customer);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,Employee_details.class);
                    startActivity(mn);
                    finish();
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,Employee_details.class);
                    startActivity(mn);
                    finish();
                }
            });
            row4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,Employee_details.class);
                    startActivity(mn);
                    finish();
                }
            });
            break;
        case "9":
            btn2.setText(getString(R.string.today_report1));
            btn1.setBackgroundResource(R.drawable.today);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,Today_report.class);
                    startActivity(mn);
                    finish();
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mn = new Intent(NavigationActivity.this,Today_report.class);
                    startActivity(mn);
                    finish();
                }
            });
            row4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mn = new Intent(NavigationActivity.this,Today_report.class);
                startActivity(mn);
                finish();
            }
        });
            break;

    }
    }

    //switch2() - on click function for all buttons
    //Params - button id
    //Created on 25/01/2022
    //Return - NULL
    public void switch2(String id){
        Log.d("iooppoi",id);
        switch (id){

            case "1":
                btn2.setText(getString(R.string.customer12));
                btn1.setBackgroundResource(R.drawable.individual_blue);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,HomeActivity.class);
                        startActivity(mn);
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,HomeActivity.class);
                        startActivity(mn);
                    }
                });
                row4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,HomeActivity.class);
                        startActivity(mn);
                    }
                });
                break;
            case "2":
                btn2.setText(getString(R.string.debit210));
                btn1.setBackgroundResource(R.drawable.debit_blue);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,Debit.class);
                        startActivity(mn);
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,Debit.class);
                        startActivity(mn);
                    }
                });
                row4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,Debit.class);
                        startActivity(mn);
                    }
                });
                break;
            case "3":
                btn2.setText(getString(R.string.collection12));
                btn1.setBackgroundResource(R.drawable.collection_blue);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,AllCollection.class);
                        startActivity(mn);
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,AllCollection.class);
                        startActivity(mn);
                    }
                });
                row4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,AllCollection.class);
                        startActivity(mn);
                    }
                });
                break;
            case "4":
                btn2.setText(getString(R.string.account1));
                btn1.setBackgroundResource(R.drawable.account_blue);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,Account.class);
                        startActivity(mn);
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,Account.class);
                        startActivity(mn);
                    }
                });
                row4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,Account.class);
                        startActivity(mn);
                    }
                });
                break;
            case "5":
                btn2.setText(getString(R.string.reports1));
                btn1.setBackgroundResource(R.drawable.report_blue);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,ReportActivity.class);
                        startActivity(mn);
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,ReportActivity.class);
                        startActivity(mn);
                    }
                });
                row4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,ReportActivity.class);
                        startActivity(mn);
                    }
                });
                break;
            case "6":
                btn2.setText(getString(R.string.individual));
                btn1.setBackgroundResource(R.drawable.user_blue);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,Customer_activity.class);
                        startActivity(mn);
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,Customer_activity.class);
                        startActivity(mn);
                    }
                });
                row4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,Customer_activity.class);
                        startActivity(mn);
                    }
                });
                break;
            case "7":
                btn2.setText(getString(R.string.print));
                btn1.setBackgroundResource(R.drawable.print_blue);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,Print_activity.class);
                        startActivity(mn);
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,Print_activity.class);
                        startActivity(mn);
                    }
                });
                row4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,Print_activity.class);
                        startActivity(mn);
                    }
                });
                break;
            case "8":
                btn2.setText(getString(R.string.employee_details2));
                btn1.setBackgroundResource(R.drawable.individual_blue);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,Employee_details.class);
                        startActivity(mn);
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,Employee_details.class);
                        startActivity(mn);
                    }
                });
                row4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,Employee_details.class);
                        startActivity(mn);
                    }
                });
                break;
            case "9":
                btn2.setText(getString(R.string.today_report1));
                btn1.setBackgroundResource(R.drawable.todayreport_blue);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,Today_report.class);
                        startActivity(mn);
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,Today_report.class);
                        startActivity(mn);
                    }
                });
                row4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mn = new Intent(NavigationActivity.this,Today_report.class);
                        startActivity(mn);
                    }
                });
                break;

        }
    }
//@Override
//protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//    // TODO Auto-generated method stub
//
//    switch(requestCode){
//
//        case 7:
//
//            if(resultCode==RESULT_OK){
//                String outFileName = Environment.getExternalStorageDirectory()+"/LOGIN";
//                String PathHolder = data.getData().getPath();
//                String inFileName = getApplicationContext().getDatabasePath("LOGIN").getPath();
//            try {
//                sqlite.importDatabase(outFileName,inFileName,getApplicationContext());
//                sqlite.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//                Toast.makeText(NavigationActivity.this, PathHolder , Toast.LENGTH_LONG).show();
//
//            }
//            break;
//
//    }
//}
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
//            String picturePath = data.getDataString().toString();
//            String Fpath = data.getDataString().replace("%3A","/");
//            String ff =     Environment.getExternalStorageDirectory() + "/" + Fpath;
//            String inFileName = getApplicationContext().getDatabasePath("LOGIN").getPath();
//            try {
//                sqlite.importDatabase(ff,inFileName);
//                sqlite.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
////            editTextPath.setText(Fpath);
//        }
//
//    }

@Override
protected void attachBaseContext(Context base) {
    super.attachBaseContext(LocaleHelper.onAttach(base));
}
//    @Override
//    public void onResume() {
//        super.onResume();
//        Locale locale = new Locale("ta");
//        Locale.setDefault(locale);
//        Configuration config = getBaseContext().getResources().getConfiguration();
//        config.locale = locale;
//        getBaseContext().getResources().updateConfiguration(config,
//                getBaseContext().getResources().getDisplayMetrics());
////        Intent intent = new Intent(this, CallDetectionService.class);
////        startService(intent);
////        SharedPreferences sharedPreferences = getSharedPreferences(CallDetector.MY_PREF,MODE_PRIVATE);
////        String number = sharedPreferences.getString(CallDetector.NUMBER_KEY,"No Value Found");
////        phone.setText(number);
//    }

//closed() - get closed amount
//Params - date and application context
//Created on 25/01/2022
//Return - NULL
    public void closed(String dateString, Context context){
        sqlite = new SQLiteHelper(context);
        database = sqlite.getReadableDatabase();
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

                    index = cursor.getColumnIndexOrThrow("interr");
                    String docc1 = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debitted");
                    String intre = cursor.getString(index);

                    if(docc != null){
                    }else{
                        docc ="0";
                    }
                    if(docc1 != null){
                    }else{
                        docc1 ="0";
                    }
                    if(intre != null){
                    }else{
                        intre ="0";
                    }
                    Integer doo = Integer.parseInt(docc);
                    Integer doo1 = Integer.parseInt(docc1);
                    Integer in = Integer.parseInt(intre);
                    cloosed =  in ;
                    Log.d("cloossed",String.valueOf(doo));
                    Log.d("cloossed",String.valueOf(doo1));
                    Log.d("cloossed",String.valueOf(in));
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
    //beforebal() - get beforebalance debit amount
    // Params - date and Application context
    //Created on 25/01/2022
    //Return - NULL
    public void beforebal(String dateString, Context context){
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date debiwt = null;

        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String NIP = "SELECT SUM(b.debit_amount) as debitted,SUM(b.document_charge) as docuu,SUM(b.interest)as interr, " +
                "(SELECT sum(a.amount) FROM dd_accounting a LEFT JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE a.accounting_date < ? AND a.tracking_id = ? AND b.acc_type = '1') as acc_credited ," +
                "(SELECT sum(a.amount) FROM dd_accounting a LEFT JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE a.accounting_date < ? AND a.tracking_id = ? AND b.acc_type = '2') as acc_debited ," +
                "(SELECT sum(a.collection_amount) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 1 AND b.debit_type IN (0,1,2) AND a.collection_date < ?) as collected," +
                "(SELECT sum(a.discount) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 1 AND b.debit_type IN (0,1,2) AND a.collection_date < ?) as discounted ," +
                "(SELECT sum(c.debit_amount) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.debit_date BETWEEN ? AND ? AND c.active_status = 0) as currentclosed ," +
                "(SELECT sum(c.document_charge) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.debit_date BETWEEN ? AND ? AND c.active_status = 0) as currentcloseddocument ," +
                "(SELECT sum(c.interest) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND  c.debit_date BETWEEN ? AND ? AND c.active_status = 0) as currentclosedinterest ," +
                "(SELECT sum(a.discount) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 0 AND a.collection_date <= ?) as closeddiscount " +
                " FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE b.debit_date < ? AND  a.debit_type IN (0,1,2,3) AND a.tracking_id = ?  ";
        String sess = String.valueOf(ses);
        Cursor cursor = database.rawQuery(NIP, new String[]{dateString,sess,dateString,sess,sess,dateString,sess,dateString,sess,dateString,dateString,sess,dateString,dateString,sess,dateString,dateString,sess,dateString,dateString,sess});
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

                    index = cursor.getColumnIndexOrThrow("currentcloseddocument");
                    String clodocc = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("currentclosedinterest");
                    String clointre = cursor.getString(index);
//
                    index = cursor.getColumnIndexOrThrow("currentclosed");
                    String clodebit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("closeddiscount");
                    String clodiscount = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("discounted");
                    String discount = cursor.getString(index);
                    if(clodebit == null || clodebit.equalsIgnoreCase("")){
                        clodebit ="0";
                    }
                    if(clodocc == null || clodocc.equalsIgnoreCase("")){
                        clodocc ="0";
                    }
                    if(clointre == null || clointre.equalsIgnoreCase("")){
                        clointre ="0";
                    }
                    if(clodiscount == null || clodiscount.equalsIgnoreCase("")){
                        clodiscount ="0";
                    }
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
                    Integer cdis = Integer.parseInt(clodiscount);
                    Integer cdb = Integer.parseInt(clodebit);
                    Integer cdo = Integer.parseInt(clodocc);
                    Integer cin = Integer.parseInt(clointre);
                    Integer dbb = Integer.parseInt(debit);
                    dbb = dbb + cdb;
                    Integer dio = Integer.parseInt(discount);
                    Integer ac_d = Integer.parseInt(acc_debit);
                    Integer col = Integer.parseInt(collect);
                    col = col - dio ;
                    Integer ac_c = Integer.parseInt(acc_credit);
                    Integer doo = Integer.parseInt(docc);
                    doo = doo + cdo;
                    Integer in = Integer.parseInt(intre);
                    in = in + cin;
                    Integer dbed = dbb - in;
                    Integer cloosed1 = cloosed - cdis ;
                    Log.d("Callidc1212c",String.valueOf(cloosed1));
                    befo = (ac_c+col+doo+cloosed1) - (dbed+ac_d);
                    Integer ad12 = ac_c+col+doo+cloosed1;
                    Integer ad121 = dbed+ac_d;
                    Log.d("Callidc1212c", col+" "+doo+" "+ac_c+","+cloosed+","+cdis+" "+in);
                    Log.d("Callidc1212b",dbed+" "+ac_d);
                    Log.d("Callidc1212c", String.valueOf(ad12));
                    Log.d("Callidc1212b",String.valueOf(ad121));

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
//    public void closed(String dateString, Context context){
//        SQLiteHelper sqlite;
//        SQLiteDatabase database;
//        cloosed =0 ;
//        sqlite = new SQLiteHelper(context);
//        database = sqlite.getReadableDatabase();
//        Log.d("popo",dateString);
//        String NIP = "SELECT SUM(b.debit_amount) as debitted,SUM(b.document_charge) as docuu,SUM(b.interest)as interr " +
//                " FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE a.debit_type_updated <= ? AND  b.active_status = 0 AND a.tracking_id = ? ";
//        String sess = String.valueOf(ses);
//        Cursor cursor = database.rawQuery(NIP, new String[]{dateString,sess});
//        if (cursor != null){
//            if(cursor.getCount() != 0){
//                cursor.moveToFirst();
//                do{
//                    int index;
//
//                    index = cursor.getColumnIndexOrThrow("docuu");
//                    String docc = cursor.getString(index);
//
//                    index = cursor.getColumnIndexOrThrow("debitted");
//                    String intre = cursor.getString(index);
//
//                    if(docc != null){
//                    }else{
//                        docc ="0";
//                    }if(intre != null){
//                    }else{
//                        intre ="0";
//                    }
//                    Integer doo = Integer.parseInt(docc);
//                    Integer in = Integer.parseInt(intre);
//                    cloosed = doo + in ;
//                    Log.d("cloossed",String.valueOf(cloosed));
////                    beforebal();
//                }
//                while (cursor.moveToNext());
//            }
//        }
//    }
//
//    public void beforebal1(String dateString, Context context){
//        SQLiteHelper sqlite;
//        SQLiteDatabase database;
//        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//
//        sqlite = new SQLiteHelper(context);
//        database = sqlite.getReadableDatabase();
//        String NIP = "SELECT SUM(b.debit_amount) as debitted,SUM(b.document_charge) as docuu,SUM(b.interest)as interr, " +
//                "(SELECT sum(a.amount) FROM dd_accounting a INNER JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE a.accounting_date < ? AND a.tracking_id = ? AND b.acc_type = '1') as acc_credited ," +
//                "(SELECT sum(a.amount) FROM dd_accounting a INNER JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE a.accounting_date < ? AND a.tracking_id = ? AND b.acc_type = '2') as acc_debited ," +
//                "(SELECT sum(a.collection_amount) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 1 AND b.debit_type IN (0,1,2) AND a.collection_date < ?) as collected," +
//                "(SELECT sum(a.discount) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 1 AND b.debit_type IN (0,1,2) AND a.collection_date < ?) as discounted" +
//                " FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE b.debit_date < ? AND  a.debit_type IN (0,1,2) AND a.tracking_id = ? AND b.active_status = 1 ";
//        String sess = String.valueOf(ses);
//        Cursor cursor = database.rawQuery(NIP, new String[]{dateString,sess,dateString,sess,sess,dateString,sess,dateString,dateString,sess});
//        if (cursor != null){
//            if(cursor.getCount() != 0){
//                cursor.moveToFirst();
//                do{
//                    int index;
//
//                    index = cursor.getColumnIndexOrThrow("debitted");
//                    String debit = cursor.getString(index);
////
//                    index = cursor.getColumnIndexOrThrow("collected");
//                    String collect = cursor.getString(index);
//
//                    index = cursor.getColumnIndexOrThrow("acc_debited");
//                    String acc_debit = cursor.getString(index);
//
//                    index = cursor.getColumnIndexOrThrow("docuu");
//                    String docc = cursor.getString(index);
//
//                    index = cursor.getColumnIndexOrThrow("interr");
//                    String intre = cursor.getString(index);
////
//                    index = cursor.getColumnIndexOrThrow("acc_credited");
//                    String acc_credit = cursor.getString(index);
//                    index = cursor.getColumnIndexOrThrow("discounted");
//                    String discount = cursor.getString(index);
//                    if(debit != null){
//                    }else{
//                        debit ="0";
//                    }
//                    if(discount != null){
//                    }else{
//                        discount ="0";
//                    }
//                    if(collect != null){
//                    }else{
//                        collect ="0";
//                    }if(acc_debit != null){
//                    }else{
//                        acc_debit ="0";
//                    }if(acc_credit != null){
//                    }else{
//                        acc_credit ="0";
//                    }
//                    if(docc != null){
//                    }else{
//                        docc ="0";
//                    }if(intre != null){
//                    }else{
//                        intre ="0";
//                    }
//                    Integer dbb = Integer.parseInt(debit);
//                    Integer dio = Integer.parseInt(discount);
//                    Integer ac_d = Integer.parseInt(acc_debit);
//                    Integer col = Integer.parseInt(collect);
//                    col = col - dio ;
//                    Integer ac_c = Integer.parseInt(acc_credit);
//                    Integer doo = Integer.parseInt(docc);
//                    Integer in = Integer.parseInt(intre);
//                    Integer dbed = dbb - in;
//                    befo = (ac_c+col+doo+cloosed) - (dbed+ac_d);
//                    Log.d("Callidccna", collect+" "+debit+" "+acc_debit+" "+acc_credit+","+dio);
//                    Log.d("Callidna",debit+" "+docc+" "+intre);
//                    Log.d("Callidna",String.valueOf(befo));
////                    yesterdaybal = String.valueOf(befo);
////                    bef.setText("\u20B9"+" "+yesterdaybal);
//                }
//                while (cursor.moveToNext());
//            }
//        }
//    }

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
                "(SELECT SUM(b.amount) FROM dd_accounting_type a LEFT JOIN dd_accounting b on b.acc_type_id = a.id WHERE b.accounting_date BETWEEN ? AND ? AND b.tracking_id = ? AND a.acc_type = '1' ) as todaycredit," +
                "(SELECT SUM(a.collection_amount) from dd_customers b  LEFT JOIN  dd_account_debit c on  c.customer_id = b.id LEFT JOIN dd_collection a ON a.debit_id = c.id  where a.collection_date BETWEEN ? AND ? AND b.debit_type = '0' AND b.tracking_id = ? AND c.active_status = 1) AS todaycollect," +
                "(SELECT SUM(a.debit_amount) from dd_customers b LEFT JOIN dd_account_debit a  on a.customer_id = b.id  Where a.debit_date BETWEEN ? AND ? AND b.tracking_id = ? AND a.active_status = 1 ) as totaldebit," +
                "(SELECT SUM(a.document_charge) from dd_customers b LEFT JOIN dd_account_debit a  on a.customer_id = b.id Where a.debit_date BETWEEN ? AND ? AND b.tracking_id =? AND a.active_status = 1 ) as totaldocument ," +
                "(SELECT SUM(a.interest) from dd_customers b LEFT JOIN dd_account_debit a  on a.customer_id = b.id Where a.debit_date BETWEEN ? AND ? AND b.tracking_id = ? AND a.active_status = 1 ) as totalinterest," +
                "(SELECT SUM(a.collection_amount) from dd_customers b  LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a ON a.debit_id = c.id where a.collection_date BETWEEN ? AND ? AND b.debit_type ='2' AND b.tracking_id = ? AND c.active_status = 1 ) as totalNIPNIP ," +
                "(SELECT SUM(a.collection_amount) from dd_customers b  LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a ON a.debit_id = c.id where a.collection_date BETWEEN ? AND ? AND b.debit_type ='1' AND b.tracking_id = ? AND c.active_status = 1 ) as totalNIP ," +
                "(SELECT SUM(b.amount) FROM dd_accounting_type a LEFT JOIN dd_accounting b on b.acc_type_id = a.id WHERE b.accounting_date BETWEEN ? AND ? AND b.tracking_id = ? AND a.acc_type = '2' ) as totaldebittacc " +
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

    //populateRecyclerView() - get Main info
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView() {

        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String[] columns = {
                "NAME",
                "ID","ORDERID"};
        String[] columns1 = {
                "dd_name", "dd_location", "starting_date"};
//        String order = "ORDERID";
        Cursor cursor = database.query("dd_main_info",
                columns1,
                null,
                null,
                null,
                null,
                null);
        if (cursor != null) {
            if (cursor.getCount() != 0) {
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
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("dd_name");
                    String USER = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("dd_location");
                    String PASS = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("starting_date");
                    String EMAIL = cursor.getString(index);

//                    name.setText(USER);
//                    city.setText(PASS);
//                    email.setText(EMAIL);
//                    pin.setText(ss);
//
//                    Log.d("names", String.valueOf(Names));
//                    Log.d("names", String.valueOf(Names));
//                    Log.d("LOG_TAG_HERE", row_values);
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

    //dd_modules() - get all modules
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void dd_modules() {
        SQLiteHelper sqlite = new SQLiteHelper(this);
        SQLiteDatabase database = sqlite.getWritableDatabase();
        String MY_QUERY = "SELECT * FROM dd_modules ";
//            String MY_QUERY = "SELECT b.*,a.NAME as modulename FROM dd_modules a INNER JOIN dd_privilege b on a.ID = b.module_id WHERE b.ID = ? ";
//            String MY_QUERY = "SELECT * from dd_modules  ";
        Cursor cursor = database.rawQuery(MY_QUERY, null);
//            Cursor cursor = database.rawQuery(MY_QUERY, null);

        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                Log.d("cursor.getCount()", String.valueOf(cursor.getCount()));

                if(cursor.getCount()<=20){
                    for(int y=0;y<=3;y++){
                        if(y==0){
                            ContentValues values = new ContentValues();
                            values.put("NAME","Bulk Update");
                            database.insert("dd_modules",null,values);
                        }else if(y==1){
                            ContentValues values = new ContentValues();
                            values.put("NAME","Single Collection");
                            database.insert("dd_modules",null,values);
                        }else if(y==2){
                            ContentValues values = new ContentValues();
                            values.put("NAME","Quick Bulk update");
                            database.insert("dd_modules",null,values);
                        }else if(y==3){
                            ContentValues values = new ContentValues();
                            values.put("NAME","Archive");
                            database.insert("dd_modules",null,values);
                            sqlite.close();
                            database.close();
                        }

                    }
                }else  if(cursor.getCount()<=23){
                    ContentValues values = new ContentValues();
                    values.put("NAME","Archive");
                    database.insert("dd_modules",null,values);
                    sqlite.close();
                    database.close();
                }else{
                    sqlite.close();
                    database.close();
                }
            }
        }

    }

//    private void populateRecyclerView1() {
//        Integer iii = Integer.parseInt(idd);
//        if(idd != null && iii > 1 ) {
//            database = sqlite.getWritableDatabase();
////            String MY_QUERY = "SELECT a.*,b.NAME as modulename FROM dd_privilege a INNER JOIN dd_modules b on b.ID = a.module_id WHERE a.ID = ? ";
//            String MY_QUERY = "SELECT a.*,b.NAME as modulename from dd_privilege a INNER JOIN dd_modules b on b.ID = a.module_id WHERE a.user_id = ?";
//            Cursor cursor = database.rawQuery(MY_QUERY, new String[]{idd});
////            Cursor cursor = database.rawQuery(MY_QUERY, null);
//
//            if (cursor != null) {
//                if (cursor.getCount() != 0) {
//                    cursor.moveToFirst();
//                    do {
//                        int index;
//                        index = cursor.getColumnIndexOrThrow("modulename");
//                        String id = cursor.getString(index);
//                        index = cursor.getColumnIndexOrThrow("module_id");
//                        String idd = cursor.getString(index);
//                        index = cursor.getColumnIndexOrThrow("view_privilege");
//                        String vi = cursor.getString(index);
//
//                        Log.d("idid", id);
//                        Names.add(vi);
//
//                        Log.d("names", String.valueOf(Names));
//                    }
//                    while (cursor.moveToNext());
//                }
//            }
//             zero = Names.get(0);
//             one = Names.get(1);
//             two = Names.get(2);
//             three = Names.get(3);
//             four = Names.get(4);
//             five = Names.get(5);
//             six = Names.get(6);
//             seven = Names.get(7);
//             eight = Names.get(8);
//             nine = Names.get(9);
//             ten = Names.get(10);
//            Log.d("names", two);
//
//        }
//    }

public String import_data() {

//    try {
//        InputStream insertsStream = getApplicationContext().getAssets().open("copyy.sql");
//        BufferedReader insertReader = new BufferedReader(new InputStreamReader(insertsStream,StandardCharsets.UTF_8));
//
//        // Iterate through lines (assuming each insert has its own line and theres no other stuff)
//        while (insertReader.ready()) {
//            String insertStmt = insertReader.readLine();
////            insertStmt.split(";\n");
////            URLEncoder.encode(insertStmt, "UTF-8");
//            insertStmt.replaceAll("(\\/\\*([\\s\\S]*?)\\*\\/)|(--(.)*)|(\n)","");
//            insertStmt.split(";");
////            String normal=StringFormatter.convertUTF8ToString(normal);
////            byte ptext[] = insertStmt.getBytes(StandardCharsets.UTF_8);
////            String value = new String(ptext, StandardCharsets.UTF_8);
//            database.execSQL(insertStmt);
//
//        }
//        insertReader.close();

        // returning number of inserted rows
        try {
            FileInputStream fis = getApplicationContext().openFileInput("copyy.sql");
//            InputStream is = this.getApplicationContext().getAssets().open("copyy.sql");
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
                Log.d("er",sb.toString());
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        } catch (IOException e) {
            return "";
        }
//        InputStream is = this.getApplicationContext().getAssets().open("copyy.sql");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//        String line;
//        while ((line = reader.readLine()) != null) {
//            line.replaceAll("(\\/\\*([\\s\\S]*?)\\*\\/)|(--(.)*)|(\n)","");
//            line.split(";");
//            Log.i("SQL Script", line);
//            if (!line.isEmpty() && !line.trim().startsWith("--"))
//                database.execSQL(line);
////            db.execSQL();
//        }
//    } catch (IOException e) {
//        Log.e("SQL Script", e.getMessage());
//    }
//    Log.i("SQL Script", "script executed");
//    }
}

//onBackPressed() - function called when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onBackPressed() {
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
            Intent new1 = new Intent(NavigationActivity.this,MainActivity.class);
            startActivity(new1);
        } else if (id == R.id.user) {
            Intent new1 = new Intent(NavigationActivity.this,HomeActivity.class);
            startActivity(new1);
        } else if (id == R.id.debit) {
            Intent new1 = new Intent(NavigationActivity.this,Debit.class);
            startActivity(new1);

        } else if (id == R.id.collection) {
            Intent new1 = new Intent(NavigationActivity.this,AllCollection.class);
            startActivity(new1);
        }else if (id == R.id.account) {
            Intent new1 = new Intent(NavigationActivity.this,Account.class);
            startActivity(new1);

        }  else if (id == R.id.report) {
            Intent new1 = new Intent(NavigationActivity.this,ReportActivity.class);
            startActivity(new1);
        } else if (id == R.id.customer) {
            Intent new1 = new Intent(NavigationActivity.this,Customer_activity.class);
            startActivity(new1);
        }else if (id == R.id.settings) {
            Intent new1 = new Intent(NavigationActivity.this,Settings_activity.class);
            startActivity(new1);
        }else if (id == R.id.tally) {
            Intent new1 = new Intent(NavigationActivity.this,Tally_activity.class);
            startActivity(new1);
        }else if (id == R.id.employee) {
            Intent new1 = new Intent(NavigationActivity.this,Employee_details.class);
            startActivity(new1);
        }else if (id == R.id.today) {
            Intent new1 = new Intent(NavigationActivity.this,Today_report.class);
            startActivity(new1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
