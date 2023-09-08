package com.rampit.rask3.dailydiary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;



//Updated on 25/01/2022 by RAMPIT
//Settings page
//InternetConnection11() - Check whether internet is on or not
//show_filee() - get download url of backup from firebase
//DownloadingTask() - AsyncTask to download backup from url
//importDB() - Import database to application
//navv() - Upload database to firebase
//backupDatabase() - Take backup of current database
//getDateDiff() - get days difference between two dates
//lastlogin1() - Upload info file to firebase
//dd_files_license() - add or update data in firebase
//InternetConnection() - Check whether internet is on or not
//InternetConnection1() - Check whether internet is on or not
//ssss() - On internet in settings page
//info_firebase() - download info file from firebase
//WriteBtn() - upload info file to firebase
//total() - get all customers
//progressbar_load() - Load progressbar
//progre() - Stop progressbar
//populateRecyclerView() - get all settings data
//populateRecyclerView1() - get all user data
//populateRecyclerView3() - get all user data
//setLocale() - set language to app


public class Settings_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView prin, dateee, session, user, expi, toto, save, expitext, totaltext, licensetext, emailtext, emailcctext, passwordtext, usertext, languagetext, export, passeye, em1, emcc1 ,save12 ,emtext , emcctext;
    EditText em, lc, pased, emailed, licensed, emcc, ps, userr , pdfpasss  ,phonenum;
    Integer ses;
    String timing, typeid, adpr, edpr, depr, vipr, langua;
    ArrayList<String> Names = new ArrayList<>();
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    Spinner spinner;
    Locale myLocale;
    String currentLanguage = "en", currentLang, iid, formattedDate;
    String ss, zero, one, two, three, four, five, six, seven, eight, nine, ten, lann, thhem;
    NavigationView navigationView;
    Menu nav_Menu;
    LinearLayout lll, editline, textline;
    Button logou, profil;
    SharedPreferences.Editor editor;
    ImageView seesim;
    Integer thhh;
    CheckBox white, black;
    static String todate;
    static String llll,device_id,eer,actsta,device_id1,expiii,product_ref,subscr_id,cod,activedd;
    private PopupWindow mPopupWindow;
    TextView pdfpass , tally_textview ,test_report , closed_check , upload_backup , textView2 , textView3 , download_backup , close_accounts , renew;
    String pdfpass123,backpri,userid,todaaaa,pdfname,license;
    Dialog dialog;
    File pdfFile,originalFile;
    ProgressBar progressBar_cyclic;
    static Integer yy ;
    Integer showinng = 0 ;
    String downloadUrl , inFileName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ii = pref.getString("theme", "");
        if (ii.equalsIgnoreCase("")) {
            ii = "1";
        }
        Log.d("thee", ii);
        Integer in = Integer.valueOf(ii);
        if (in == 0) {
            Log.d("thee", "thh");
            setTheme(R.style.AppTheme1);
        } else {
            Log.d("thee", "th1h");
            setTheme(R.style.AppTheme11);
//            recreate();
        }
        String localeName = pref.getString("language", "");
        if (localeName == null) {
            localeName = "ta";
        }
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Integer buildver = Build.VERSION.SDK_INT;
        Log.d("buildver",String.valueOf(buildver));

        if(buildver > 28){

            device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
//            Log.d("deee1",imeiNumber1);
//            Log.d("deee2",imeiNumber2);
        }else{
            device_id = tm.getDeviceId();
//            Log.d("deee1",imeiNumber1);
//            Log.d("deee2",imeiNumber2);
        }
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_settings_activity);
        setTitle(R.string.title_activity_settings_activity);
        FirebaseApp.initializeApp(this);
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
        renew = findViewById(R.id.renew);
        renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                renew_lic();
            }
        });
        close_accounts = findViewById(R.id.close_accounts);
        String archive = pref.getString("archive","0");
        String iiiddd =  pref.getString("id","0");
        if(archive.equalsIgnoreCase("4")){
            close_accounts.setVisibility(View.VISIBLE);
        }else{
            if(iiiddd.equalsIgnoreCase("1")){
                close_accounts.setVisibility(View.VISIBLE);
            }else{
                close_accounts.setVisibility(View.GONE);
            }
        }
        close_accounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dele = new Intent(Settings_activity.this,delete_datas.class);
                startActivity(dele);
            }
        });
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        progressBar_cyclic = findViewById(R.id.progressBar_cyclic);
        dialog = new Dialog(Settings_activity.this);
        seesim = findViewById(R.id.sesimg);
        editor = pref.edit();
        download_backup = findViewById(R.id.download_backup);
//        download_backup.setVisibility(View.GONE);
        export = findViewById(R.id.expo);
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
                            final SharedPreferences pref11 = getApplicationContext().getSharedPreferences("server", 0); // 0 - for private mode
                            String isLoading = pref11.getString("isLoading","no");
                            String isLoaded_time = pref11.getString("isLoaded_time","no");
                            Log.d("isLoading",isLoading+" "+isLoaded_time);
                            if (InternetConnection11.checkConnection(getApplicationContext(),Settings_activity.this)) {
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
        final Handler handler1 = new Handler();
        Timer timer1 = new Timer();
        TimerTask doAsynchronousTask1 = new TimerTask() {
            @Override
            public void run() {
                handler1.post(new Runnable() {
                    public void run() {
                        try {
                            //your method here
                            final SharedPreferences pref11 = getApplicationContext().getSharedPreferences("server", 0); // 0 - for private mode
                            String isLoading = pref11.getString("isLoading","no");
                            String isLoaded_time = pref11.getString("isLoaded_time","no");
                            Log.d("isLoading",isLoading+" "+isLoaded_time);
                            if (InternetConnection1.checkConnection(getApplicationContext(),Settings_activity.this)) {
                                // Internet Available...
                                Log.d("internet","on");
                                runOnUiThread(new Runnable() {
                                    @SuppressLint("RestrictedApi")
                                    @Override
                                    public void run() {
                                        // Stuff that updates the UI
//                                    fab.hide();
//                                      progre();
                                    }
                                });
//            date32();
                                if(isLoading.equalsIgnoreCase("yes")){
                                    runOnUiThread(new Runnable() {
                                        @SuppressLint("RestrictedApi")
                                        @Override
                                        public void run() {
                                            // Stuff that updates the UI
//                                    fab.hide();
                                            progressBar_cyclic.setVisibility(View.GONE);
                                            textView2.setVisibility(View.GONE);
                                            textView3.setVisibility(View.GONE);
//            textView2.setVisibility(View.GONE);
//                                            textView3.setVisibility(View.VISIBLE);
//                                            textView2.setText(getString(R.string.backup_status)+getString(R.string.uploaded_successfully));
//                                            textView3.setText(getString(R.string.updated_time)+isLoaded_time);
                                        }
                                    });

                                }
                                else if(isLoading.equalsIgnoreCase("no")){
                                    runOnUiThread(new Runnable() {
                                        @SuppressLint("RestrictedApi")
                                        @Override
                                        public void run() {
                                            // Stuff that updates the UI
//                                    fab.hide();
                                            progressBar_cyclic.setVisibility(View.GONE);
                                            textView2.setVisibility(View.GONE);
                                            textView3.setVisibility(View.GONE);
//            textView2.setVisibility(View.GONE);
//                                            textView3.setVisibility(View.VISIBLE);
//                                            textView2.setText(getString(R.string.backup_status)+getString(R.string.not_uploaded));
//                                            textView3.setText(getString(R.string.updated_time)+isLoaded_time);
                                        }
                                    });

                                }
                                else if(isLoading.equalsIgnoreCase("fail")){
                                    runOnUiThread(new Runnable() {
                                        @SuppressLint("RestrictedApi")
                                        @Override
                                        public void run() {
                                            // Stuff that updates the UI
//                                    fab.hide();
                                            progressBar_cyclic.setVisibility(View.GONE);
                                            textView2.setVisibility(View.GONE);
                                            textView3.setVisibility(View.GONE);
//            textView2.setVisibility(View.GONE);
//                                            textView3.setVisibility(View.VISIBLE);
//                                            textView2.setText(getString(R.string.backup_status)+getString(R.string.failed));
//                                            textView3.setText(getString(R.string.updated_time)+isLoaded_time);
                                        }
                                    });

                                }
                                else if(isLoading.equalsIgnoreCase("uploading")){
//            progressBar_cyclic.setVisibility(View.GONE);
//            textView2.setVisibility(View.GONE);
                                    runOnUiThread(new Runnable() {
                                        @SuppressLint("RestrictedApi")
                                        @Override
                                        public void run() {
                                            // Stuff that updates the UI
//                                    fab.hide();
                                            progressBar_cyclic.setVisibility(View.GONE);
                                            textView2.setVisibility(View.GONE);
                                            textView3.setVisibility(View.GONE);

//                                            textView3.setVisibility(View.VISIBLE);
//                                            textView2.setText(getString(R.string.backup_status)+getString(R.string.uploading));
//                                            textView3.setText(getString(R.string.updated_time)+isLoaded_time);
                                        }
                                    });

                                }
                            }
                            else {
                                progre();
                                // Internet Not Available...
                                Log.d("internet","off");
                                runOnUiThread(new Runnable() {
                                    @SuppressLint("RestrictedApi")
                                    @Override
                                    public void run() {
                                        // Stuff that updates the UI
//                                    fab.hide();
                                        progressBar_cyclic.setVisibility(View.GONE);
                                        textView2.setVisibility(View.GONE);
                                        textView3.setVisibility(View.GONE);

//                                        progressBar_cyclic.setVisibility(View.GONE);
//                                        textView3.setVisibility(View.GONE);
//                                        textView2.setText(getString(R.string.backup_status)+getString(R.string.check_internet));
//                                        textView3.setText(R.string.updated_time+isLoaded_time);
                                    }
                                });


                            }

                        } catch (Exception e) {
                        }
                    }
                });
            }
        };
        timer1.schedule(doAsynchronousTask1, 0, 10000); //execute in every 10 minutes

        backpri = pref.getString("backpri","");
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
        lann = pref.getString("language","");
        thhem = pref.getString("theme","");
        lll = (findViewById(R.id.linee));
        TextView pro = lll.findViewById(R.id.pro);
        logou = lll.findViewById(R.id.logout);
        profil = lll.findViewById(R.id.profile);
        if(thhem.equalsIgnoreCase("")){
            thhh = 1;
        }else{
            thhh = Integer.parseInt(thhem);
        }

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
            if(backpri.equalsIgnoreCase("1")){
            }else{
                nav_Menu.findItem(R.id.settings).setVisible(false);
                profil.setVisibility(View.GONE);
                pro.setVisibility(View.GONE);
            }
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
        currentLanguage = getIntent().getStringExtra(currentLang);

        save12 = findViewById(R.id.licsave);
        em1 = findViewById(R.id.em12);
        emcc1 = findViewById(R.id.emcc12);
        spinner = (Spinner) findViewById(R.id.spinner);
        user = findViewById(R.id.user);
//        lll = (findViewById(R.id.linee));
        upload_backup = findViewById(R.id.upload_backup);
        closed_check = findViewById(R.id.closed_check);
        test_report = findViewById(R.id.test_report);
        phonenum = findViewById(R.id.phonenumber);
        pdfpass = findViewById(R.id.pdfpasseye);
        pdfpasss = findViewById(R.id.pdfpassword);
        editline = (findViewById(R.id.edit_linear));
        textline = (findViewById(R.id.text_linear));
        licensetext = (findViewById(R.id.license_text));
        emailtext = (findViewById(R.id.email_text));
        emailcctext = (findViewById(R.id.email_cc_text));
        expitext = findViewById(R.id.expiry_text);
        passwordtext = (findViewById(R.id.password_text));
        usertext = (findViewById(R.id.user_text));
        languagetext = (findViewById(R.id.spinner_text));
        emtext = findViewById(R.id.emtext);
        emcctext = findViewById(R.id.emcctext);
        totaltext = findViewById(R.id.totol_text);
//        logou = lll.findViewById(R.id.logout);
        passeye = findViewById(R.id.passeye);
//        profil = lll.findViewById(R.id.profile);
        save = findViewById(R.id.save);
        pased = findViewById(R.id.password);
        licensed = findViewById(R.id.license);
        expi = findViewById(R.id.expiry);
        userr = findViewById(R.id.username);
        emailed = findViewById(R.id.email);
        emcc = findViewById(R.id.email_cc);
        white = findViewById(R.id.white);
        black = findViewById(R.id.black);
        tally_textview = findViewById(R.id.tally);
        if(thhh == 0){
            black.setChecked(true);
        }else if(thhh == 1){
            white.setChecked(true);
        }
        download_backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lic = licensed.getText().toString();
                if(lic == null || lic.equalsIgnoreCase("") ||  lic.equalsIgnoreCase("null") ){
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
                    String enn = getString(R.string.no_license);
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
                }else{
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
                    String enn = getString(R.string.are_you_sure);
                    String war = getString(R.string.warning);
                    String ook = getString(R.string.ok);
                    String cancel = getString(R.string.cancel);
                    alertbox.setMessage(enn);
                    alertbox.setTitle(war);
                    alertbox.setIcon(R.drawable.dailylogo);
                    alertbox.setPositiveButton(ook,
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                    show_filee(lic+"/admin/dd_backup","Admin");
                                }
                            });
                    alertbox.setNegativeButton(cancel,
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                }
                            });
                    alertbox.show();

                }
            }
        });
        closed_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mmo = new Intent(Settings_activity.this,closed_check.class);
                startActivity(mmo);
            }
        });
        test_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent test_report = new Intent(Settings_activity.this,test_tab_activity.class);
                startActivity(test_report);
                finish();
            }
        });
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
               Intent export = new Intent(Settings_activity.this,ExportDB.class);
               startActivity(export);
//                finish();
            }
        });
        upload_backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar_load();
                backupDatabase();
            }
        });
        tally_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent tally = new Intent(Settings_activity.this,Tally_activity.class);
               startActivity(tally);
            }
        });
        export.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                    Toast.makeText(getApplicationContext(), String.valueOf(interr), Toast.LENGTH_SHORT).show();
//                    calclate();
                }
            }
        });
        Button theme = lll.findViewById(R.id.theme);
        if(in == 0){
            logou.setBackgroundResource(R.drawable.logout);
            profil.setBackgroundResource(R.drawable.user1);
            theme.setBackgroundResource(R.drawable.theme);
            passeye.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_orange, 0);
            pdfpass.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_orange, 0);
            emcc1.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_orange, 0);
            em1.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_orange, 0);
            emcctext.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_orange, 0);
            emtext.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_orange, 0);
            save12.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.save_orange1, 0);
        }
        else{
            logou.setBackgroundResource(R.drawable.logoutblue);
            profil.setBackgroundResource(R.drawable.user_blue);
            theme.setBackgroundResource(R.drawable.theme1);
            passeye.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_blue, 0);
            pdfpass.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_blue, 0);
            emcc1.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_blue, 0);
            em1.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_blue, 0);
            emcctext.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_blue, 0);
            emtext.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_blue, 0);
            save12.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.save_blue1, 0);
        }
        emtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String naam = emailtext.getText().toString();
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
            }
        });
        emcctext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String naam = emailcctext.getText().toString();
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
            }
        });
        em1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String naam = em.getText().toString();
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
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pass = pased.getText().toString();
                final String licc = licensed.getText().toString();
                String emm = emailed.getText().toString();
                String emc = emcc.getText().toString();
                String exxx = expi.getText().toString();
                String usse = userr.getText().toString();
                String pdfpas = pdfpasss.getText().toString();
                String phonnne = phonenum.getText().toString();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                String formattedDate = df.format(c.getTime());
                SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date debit = df1.parse(exxx);
                    exxx = df.format(debit);
                    Log.d("deae",exxx);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//               setLocale("en");
                Log.d("setttt",pass+" "+licc+" "+emm+" "+emc+" "+exxx+" "+langua+" "+pdfpas);
                if(pass.equalsIgnoreCase("") || usse.equalsIgnoreCase("")) {}else{
                    populateRecyclerView3();
                }
                if(iid == null){
                    editor.putString("language",langua);
                    editor.commit();
                    if(licc == null || licc.equalsIgnoreCase("") || licc.equalsIgnoreCase("null")){

                    }else{
                        if(pass.equalsIgnoreCase("") || usse.equalsIgnoreCase("")) {
                            String all_val = "License No :"+" "+licc+"\n"+"Email :"+" "+emm+"\n"+"Email CC :"+" "+emc+"\n"+"Expiry Date :"+" "+exxx+"\n"+"Language :"+" "+langua+"\n"+"PDF Password :"+" "+pdfpas+"\n"+
                                    "Phone Number :"+" "+phonnne+"\n";
                            WriteBtn(view,all_val,licc);
                        }else{
                            String all_val = "License No :"+" "+licc+"\n"+"Email :"+" "+emm+"\n"+"Email CC :"+" "+emc+"\n"+"Expiry Date :"+" "+exxx+"\n"+"Language :"+" "+langua+"\n"+"PDF Password :"+" "+pdfpas+"\n"+
                                    "Phone Number :"+" "+phonnne+"\n"+"Username :"+" "+usse+"\n"+"Password :"+" "+pass;
                            WriteBtn(view,all_val,licc);
                        }
                    }
                    database = sqlite.getWritableDatabase();
                    ContentValues values = new ContentValues();
//                    values.put("license", licc);
                    values.put("email", emm);
                    values.put("cc", emc);
                    values.put("expiry", exxx);
                    values.put("language", langua);
                    values.put("pdf_password", pdfpas);
                    values.put("phone_number", phonnne);
                    values.put("created_date", formattedDate);
                    database.insert("dd_settings", null, values);

//                    if(licc == null){
//
//                    }else{
//                        AsyncTask.execute(new Runnable() {
//                            @Override
//                            public void run() {
//                                //TODO your background code
//                                try {
//                                    llll = licc;
//                                    Settings_activity.call_me2();
//                                    if(eer.equalsIgnoreCase("success")){
//                                        Settings_activity.call_me();
////                                   expii1();
//                                    }else if(eer.equalsIgnoreCase("error")){
//                                        Settings_activity.call_me();
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                                if(actsta.equalsIgnoreCase("active") && device_id1.equalsIgnoreCase(device_id)){
//                                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//                                    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
//                                    try {
//                                        Date debit = df1.parse(expiii);
//                                        todate = df.format(debit);
//                                        Log.d("deae",todate);
//
//                                    } catch (ParseException e) {
//                                        e.printStackTrace();
//                                    }
//                                    expii1();
//                                }
//
//                            }
//                        });
//                    }
                }else {
                    if(licc == null || licc.equalsIgnoreCase("") || licc.equalsIgnoreCase("null")){

                    }else{
                        if(pass.equalsIgnoreCase("") || usse.equalsIgnoreCase("")) {
                            String all_val = "License No :"+" "+licc+"\n"+"Email :"+" "+emm+"\n"+"Email CC :"+" "+emc+"\n"+"Expiry Date :"+" "+exxx+"\n"+"Language :"+" "+langua+"\n"+"PDF Password :"+" "+pdfpas+"\n"+
                                    "Phone Number :"+" "+phonnne+"\n";
                            WriteBtn(view,all_val,licc);
                        }else{
                            String all_val = "License No :"+" "+licc+"\n"+"Email :"+" "+emm+"\n"+"Email CC :"+" "+emc+"\n"+"Expiry Date :"+" "+exxx+"\n"+"Language :"+" "+langua+"\n"+"PDF Password :"+" "+pdfpas+"\n"+
                                    "Phone Number :"+" "+phonnne+"\n"+"Username :"+" "+usse+"\n"+"Password :"+" "+pass;
                            WriteBtn(view,all_val,licc);
                        }
                    }
                    editor.putString("language", langua);
                    editor.commit();
                    database = sqlite.getWritableDatabase();
                    Log.d("iid", langua);
                    ContentValues values = new ContentValues();
//                    values.put("license", licc);
                    values.put("email", emm);
                    values.put("cc", emc);
                    values.put("expiry", exxx);
                    values.put("language", langua);
                    values.put("pdf_password", pdfpas);
                    values.put("phone_number", phonnne);
                    values.put("updated_date", formattedDate);
                    database.update("dd_settings", values, "ID = ?", new String[]{"1"});


//                    if (licc == null) {
//
//                    } else {
//                        AsyncTask.execute(new Runnable() {
//                            @Override
//                            public void run() {
//                                //TODO your background code
//                                try {
//                                    llll = licc;
//                                    Settings_activity.call_me2();
//                                    if(eer.equalsIgnoreCase("success")){
//                                        Settings_activity.call_me();
////                                   expii1();
//                                    }else if(eer.equalsIgnoreCase("error")){
//                                        Settings_activity.call_me();
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                                if(actsta.equalsIgnoreCase("active") && device_id1.equalsIgnoreCase(device_id)){
//                                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//                                    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
//                                    try {
//                                        Date debit = df1.parse(expiii);
//                                        todate = df.format(debit);
//                                        Log.d("deae",todate);
//
//                                    } catch (ParseException e) {
//                                        e.printStackTrace();
//                                    }
//                                    expii1();
//                                }
//
//                            }
//                        });
//                    }
                }
//               Intent login = new Intent(Settings_activity.this, Settings_activity.class);
//               startActivity(login);
            }
        });
        save12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String licc = licensed.getText().toString();

                if(licc == null){

                }else{
                    progressbar_load();
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            //TODO your background code
                            try {
                                llll = licc;
                                Settings_activity.call_me2();
                                if(eer.equalsIgnoreCase("success")){
                                    Settings_activity.call_me();
//                                   expii1();
                                }else if(eer.equalsIgnoreCase("error")){
                                    Settings_activity.call_me();
                                }
                            } catch (Exception e) {
                                progre();
                                AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
                                String warr = getString(R.string.warning);
                                String logo = getString(R.string.ok);
                                alertbox.setMessage(String.valueOf(e));
                                alertbox.setTitle(warr);
                                alertbox.setIcon(R.drawable.dailylogo);
                                alertbox.setPositiveButton(logo,
                                        new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface arg0,
                                                                int arg1) {
                                            }
                                        });
                                alertbox.show();
                                e.printStackTrace();
                            }
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
//                                            if(actsta.equalsIgnoreCase("active") && device_id1.equalsIgnoreCase(device_id)&& product_ref.equalsIgnoreCase("DailyDiary")){
                                                if(actsta.equalsIgnoreCase("active") && product_ref.equalsIgnoreCase("DailyDiary")){
                                                progre();
                                                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                                                SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                                                try {
                                                    Date debit = df1.parse(expiii);
                                                    todate = df.format(debit);
                                                    Log.d("deae",todate);

                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                Log.d("deae","expii");
                                                expii1();
                                            }else if(!product_ref.equals("DailyDiary")){
                                                progre();
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        String DailyDiary = "DailyDiary";
                                                        Log.d("result",String.valueOf(product_ref.length())+" "+String.valueOf(DailyDiary.length()));
                                                        AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
                                                        String rer = getString(R.string.enter_dailydiary_key);
                                                        String rer1= getString(R.string.warning);
                                                        String rer2 = getString(R.string.ok);

                                                        alertbox.setMessage(rer);
                                                        alertbox.setTitle(rer1);
                                                        alertbox.setIcon(R.drawable.dailylogo);
                                                        alertbox.setNeutralButton(rer2,
                                                                new DialogInterface.OnClickListener() {

                                                                    public void onClick(DialogInterface arg0,
                                                                                        int arg1) {
                                                                    }
                                                                });
                                                        alertbox.show();
                                                    }
                                                });
                                            }
                                            else if(actsta.equalsIgnoreCase("active")){
                                                progre();
                                                AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
                                                String logmsg = getString(R.string.device_id_no);
                                                String warr = getString(R.string.warning);
                                                String logo = getString(R.string.ok);
                                                alertbox.setMessage(logmsg);
                                                alertbox.setTitle(warr);
                                                alertbox.setIcon(R.drawable.dailylogo);
                                                alertbox.setPositiveButton(logo,
                                                        new DialogInterface.OnClickListener() {

                                                            public void onClick(DialogInterface arg0,
                                                                                int arg1) {
                                                            }
                                                        });
                                                alertbox.show();
                                                Log.d("deae","expii1");
                                            }
                                            else if(!actsta.equals("active")){
                                                progre();
                                                AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
                                                String logmsg = getString(R.string.license_no_active);
                                                String warr = getString(R.string.warning);
                                                String logo = getString(R.string.ok);
                                                alertbox.setMessage(logmsg);
                                                alertbox.setTitle(warr);
                                                alertbox.setIcon(R.drawable.dailylogo);
                                                alertbox.setPositiveButton(logo,
                                                        new DialogInterface.OnClickListener() {

                                                            public void onClick(DialogInterface arg0,
                                                                                int arg1) {
                                                            }
                                                        });
                                                alertbox.show();
                                                Log.d("deae","expii1");
                                            }
                                            else{
                                                progre();
                                                AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
                                                String logmsg = getString(R.string.no_response);
                                                String warr = getString(R.string.warning);
                                                String logo = getString(R.string.ok);
                                                alertbox.setMessage(logmsg);
                                                alertbox.setTitle(warr);
                                                alertbox.setIcon(R.drawable.dailylogo);
                                                alertbox.setPositiveButton(logo,
                                                        new DialogInterface.OnClickListener() {

                                                            public void onClick(DialogInterface arg0,
                                                                                int arg1) {
                                                            }
                                                        });
                                                alertbox.show();
                                                Log.d("deae","expii2");
                                            }
                                        }
                                    });
                                }
                            }, 7000);

                        }
                    });
                }
            }
        });
        emcc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String naam = emcc.getText().toString();
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
            }
        });
        final boolean[] isPressed = {false};
        passeye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPressed[0])
                    pased.setTransformationMethod(new PasswordTransformationMethod());
                else
                    pased.setTransformationMethod(null);
                isPressed[0] = !isPressed[0];
            }
        });
        pdfpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPressed[0])
                    pdfpasss.setTransformationMethod(new PasswordTransformationMethod());
                else
                    pdfpasss.setTransformationMethod(null);
                isPressed[0] = !isPressed[0];
            }
        });
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Settings_activity.this,Settings_activity.class);
                startActivity(i);

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
                    Intent i = new Intent(Settings_activity.this,NavigationActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    editor.putString("theme","0");
                    editor.apply();
                    Intent i = new Intent(Settings_activity.this,NavigationActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        });

        logou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
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
                                Intent i = new Intent(Settings_activity.this,Splash.class);
                                startActivity(i);
                                finish();
                            }
                        });
                alertbox.show();
            }
        });
        List<String> list = new ArrayList<String>();
        list.add("Select language");
        list.add("English");
        list.add("Français");
        list.add("Tamil");

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
        if(in == 0){
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    this,R.layout.spinner_design,list){
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
            spinner.setAdapter(spinnerArrayAdapter);
        }
        else{
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    this,R.layout.spinner_design1,list){
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
            spinner.setAdapter(spinnerArrayAdapter);

        }
        if(lann.equalsIgnoreCase("null") || lann.equalsIgnoreCase("en")){
            lann = "en";
            Log.d("langu",lann);
            spinner.setSelection(1);
        }else if(lann.equalsIgnoreCase("fr")){
            Log.d("langu",lann);
            spinner.setSelection(2);
        }else if(lann.equalsIgnoreCase("ta")){
            Log.d("langu",lann);
            spinner.setSelection(3);
        }
        if(in == 0){
            white.setButtonDrawable(R.drawable.custom_checkbox);
            black.setButtonDrawable(R.drawable.custom_checkbox);
        }else{
            white.setButtonDrawable(R.drawable.custom_checkbox1);
            black.setButtonDrawable(R.drawable.custom_checkbox1);
        }
        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(white.isChecked())
                {
                    black.setChecked(false);
                    editor.putString("theme","1");
                    editor.commit();
                    Log.d("thee","0");
//                    recreate();
                    Intent ff = new Intent(Settings_activity.this,Settings_activity.class);
                    startActivity(ff);

                }
                else
                {
                }
            }
        });
        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(black.isChecked())
                {
                    white.setChecked(false);
                    editor.putString("theme","0");
                    editor.commit();
//                    recreate();
                    Intent ff = new Intent(Settings_activity.this,Settings_activity.class);
                    startActivity(ff);
                    Log.d("thee","1");
                }
                else
                {

                }
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    Integer nu = spinner
                            .getSelectedItemPosition();
                    typeid = String.valueOf(nu);
                    if(typeid.equalsIgnoreCase("1"))
                    {   langua = "en";
                    }else if(typeid.equalsIgnoreCase("2")){
                        langua = "fr";
                    }else if(typeid.equalsIgnoreCase("3")){
                        langua = "ta";

                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

//        pased = findViewById(R.id.psaa);
//        emailed = findViewById(R.id.emll);
//        licensed = findViewById(R.id.licc);
        em = findViewById(R.id.email);
        toto = findViewById(R.id.totol);
        lc = findViewById(R.id.license);
        ps = findViewById(R.id.password);
        String sesid = pref.getString("id","");
        String module_i = "8";
        ((Callback)getApplication()).privilege(sesid,module_i);
        adpr = pref.getString("add_privilege","");
        edpr = pref.getString("edit_privilege","");
        depr = pref.getString("delete_privilege","");
        vipr = pref.getString("view_privilege","");
        Log.d("addd",adpr);
        populateRecyclerView();
        if(edpr.equalsIgnoreCase("0") ){
//            emailed.setVisibility(View.GONE);
//            licensed.setVisibility(View.GONE);
//            pased.setVisibility(View.GONE);
//            ps.setVisibility(View.INVISIBLE);
//            lc.setVisibility(View.INVISIBLE);
//            em.setVisibility(View.INVISIBLE);
//            emcc.setVisibility(View.INVISIBLE);
            editline.setVisibility(View.GONE);
            textline.setVisibility(View.VISIBLE);
            save.setVisibility(View.GONE);
            user.setVisibility(View.GONE);
            if(backpri.equalsIgnoreCase("1")){
//                editline.setVisibility(View.GONE);
//                textline.setVisibility(View.GONE);
            }else{
//                textline.setVisibility(View.GONE);
                export.setVisibility(View.GONE);
            }
        }
        if(vipr.equalsIgnoreCase("0") ){
//            emailed.setVisibility(View.GONE);
//            licensed.setVisibility(View.GONE);
//            pased.setVisibility(View.GONE);
//            ps.setVisibility(View.INVISIBLE);
//            lc.setVisibility(View.INVISIBLE);
//            em.setVisibility(View.INVISIBLE);
//            emcc.setVisibility(View.INVISIBLE);
            editline.setVisibility(View.GONE);
            textline.setVisibility(View.GONE);
            save.setVisibility(View.GONE);
            user.setVisibility(View.GONE);
            if(backpri.equalsIgnoreCase("1")){
//                editline.setVisibility(View.GONE);
//                textline.setVisibility(View.GONE);
            }else{
//                textline.setVisibility(View.GONE);
                export.setVisibility(View.GONE);
            }
        }
        ses = pref.getInt("session", 1);
        String dd = pref.getString("Date","");
        dateee = findViewById(R.id.da);
        session = findViewById(R.id.sess);
        dateee.setText(dd);
        if (in == 0) {
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
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
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
                                editor.putInt("isloginn",0);
                                editor.commit();
                                Intent i = new Intent(Settings_activity.this,Splash.class);
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
                Intent qq = new Intent(Settings_activity.this,NavigationActivity.class);
                startActivity(qq);
                finish();
            }
        });

        nn.setText(dsdsd);
        nnn.setText("Date :" + " " + dd);
        nnnn.setText("Session :" + " " + ss);
        total();
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

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newd = new Intent(Settings_activity.this,user_activity.class);
                startActivity(newd);
                finish();
            }
        });
        session.setText(timing);
       prin = findViewById(R.id.print);
       prin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent neew = new Intent(Settings_activity.this,Print_activity.class);
               startActivity(neew);
               finish();
           }
       });
        save.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                    Toast.makeText(getApplicationContext(), String.valueOf(interr), Toast.LENGTH_SHORT).show();
//                    calclate();
                }
            }
        });

    }
    //InternetConnection11() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection11 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Settings_activity account) {
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

    //show_filee() - get download url of backup from firebase
    //Params - filename and type
    //Created on 25/01/2022
    //Return - NULL
    public void show_filee(String value , final String typ){
        progressbar_load();
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
                SharedPreferences pref = getApplicationContext().getSharedPreferences("database_pref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("database_name",typ);
                editor.apply();

                downloadUrl = String.valueOf(uri);


                    DownloadingTask mytask = new DownloadingTask();
                    mytask.execute();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                dialog.cancel();
                Log.d("uuurrrllll","File Not Found");
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
                String enn = getString(R.string.no_backup);
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
//                Toast.makeText(getApplicationContext(), "File Not Found",
//                        Toast.LENGTH_LONG).show();
//                cancel_loader();
            }
        });
    }

    //DownloadingTask() - AsyncTask to download backup from url
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private class DownloadingTask extends AsyncTask<Void, Void, Void> {

        File apkStorage = null;
        File outputFile = null;

        @Override
        protected void onPreExecute() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    progressbar_load();
                }
            });

            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void result) {


            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                URL url = new URL(downloadUrl);//Create Download URl
                HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
                c.setRequestMethod("GET");//SpopulateRecyclerView_new1populateRecyclerView_new1et Request Method to "GET" since we are grtting data
                c.connect();//connect the URL Connection

                //If Connection response is not OK then show Logs
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e("dddooowwwnn", "Server returned HTTP " + c.getResponseCode()
                            + " " + c.getResponseMessage());

                }


                //Get File if SD card is present
//                if (new CheckForSDCard().isSDCardPresent()) {
                File Directory = new File("/sdcard/DailyDiary_downloads/");
                Directory.mkdirs();
                apkStorage = new File(
                        Environment.getExternalStorageDirectory() + "/"
                                + "Dailydiary_downloads");
//                } else
//                    Toast.makeText(getApplicationContext(), "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

                //If File is not present create directory
                if (!apkStorage.exists()) {
                    apkStorage.mkdir();
                    Log.e("dddooowwwnn", "Directory Created.");
                }

                outputFile = new File(apkStorage, "test.db");//Create Output file in Main File

                //Create New File if not present
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                    Log.e("dddooowwwnn", "File Created");
                }

                FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                InputStream is = c.getInputStream();//Get InputStream for connection

                byte[] buffer = new byte[1024];//Set buffer type
                int len1 = 0;//init length
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);//Write new file
                }
                Uri uri = Uri.fromFile(outputFile);
                if (Build.VERSION.SDK_INT < 11) {
//                        realPath = FileUtils1.getRealPathFromURI_BelowAPI11(context, fileUri);
                    originalFile = new File(FileUtils1.getRealPathFromURI_BelowAPI11(Settings_activity.this, uri));
                    Log.d("rtrt3", String.valueOf(originalFile));
                }
                // SDK >= 11 && SDK < 19
                else if (Build.VERSION.SDK_INT < 19) {
                    originalFile = new File(FileUtils1.getRealPathFromURI_API11to18(Settings_activity.this, uri));
//                        realPath = FileUtils1.getRealPathFromURI_API11to18(context, fileUri);
                    Log.d("rtrt2", String.valueOf(originalFile));
                }
                // SDK > 19 (Android 4.4) and up
                else {
                    originalFile = new File(FileUtils1.getRealPathFromURI_API19(Settings_activity.this, uri));
                    String uiu = String.valueOf(originalFile);
                    String numm = uiu.replace("/storage/emulated/0/LOGIN", "LOGIN");
                    Log.d("rtrt1", String.valueOf(numm));
                    Log.d("rtrt1", String.valueOf(originalFile));
//                        realPath = FileUtils1.getRealPathFromURI_API19(context, fileUri);
                }
                inFileName = getApplicationContext().getDatabasePath("LOGIN").getPath();
                String path = originalFile.toString();

                Log.e("dddooowwwnn_ddddd", String.valueOf(inFileName)+" "+path);
                //Close all connection after doing task
                fos.close();
                is.close();
//                dd_settings(path);
                importDB(path, inFileName);
//                image_firebase(uri,"checking3","checking1");
            } catch (Exception e) {

                //Read exception if something went wrong
                e.printStackTrace();
                outputFile = null;
                Log.e("dddooowwwnn", "Download Error Exception " + e.getMessage());
            }

            return null;
        }
    }

    //importDB() - Import database to application
    //Params - Input and output filename
    //Created on 25/01/2022
    //Return - NULL
    private void importDB(String outFileName,String inFileName) {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data  = Environment.getDataDirectory();

            if (sd.canWrite()) {
                File sd1 = Environment.getExternalStorageDirectory();
                File data1 = Environment.getDataDirectory();
                FileChannel src = null;
                FileChannel dst = null;
                String currentDBPath = "/data/"+ "com.rampit.rask3.dailydiary" +"/databases/LOGIN";
//                String backupDBPath = "LOGIN.db";
//                File currentDB = new File(data, currentDBPath);
//                File backupDB = new File(sd, backupDBPath);
//                String outFileName = Environment.getExternalStorageDirectory()+"/LOGIN";
//                String inFileName = getApplicationContext().getDatabasePath("LOGIN").getPath();
//                final String currentDBPath ="/data/user/0/com.rampit.rask3.dailydiaryv2/databases/LOGIN";
//                String  currentDBPath= "//user//" +"//0//" + "com.rampit.rask3.dailydiaryv2"
//                        + "//databases//" + "LOGIN";
//                String backupDBPath  = "/BackupFolder/LOGIN";
//                File  backupDB= new File(data, currentDBPath);
//                File currentDB  = new File(sd, backupDBPath);
//                SQLiteHelper sql = new SQLiteHelper(ExportDB.this);
                File currentDB = new File(outFileName);
                File backupDB = new File(inFileName);
                Log.d("rtrtrtr",String.valueOf(currentDB));
                Log.d("rtrtrtr",String.valueOf(backupDB));

                SQLiteHelper sql = new SQLiteHelper(Settings_activity.this);

                src = new FileInputStream(currentDB).getChannel();
                dst = new FileOutputStream(backupDB).getChannel();
//                File dbshm = new File(backupDB.getPath() + "-shm");
//                File dbwal = new File(backupDB.getPath()+ "-wal");
//                if (dbshm.exists()) {
//                    dbshm.delete();
//                }
//                if (dbwal.exists()) {
//                    dbwal.delete();
//                }
                try {
                    dst.transferFrom(src, 0, src.size());
                    Log.d("trr","yyy");
                } finally {
                    try {

                        src.close();
                        dst.close();
                        sql.close();
//                        Toast.makeText(getBaseContext(), currentDB.toString(),
//                                Toast.LENGTH_LONG).show();
//                        if (Build.VERSION.SDK_INT <= 23) {
//                            popup1("1");
//                        }else{
                        Log.d("trr1_nav","yyy");
                        navv();
//                            intee();
//                        dd_settings1();
//                            populateRecyclerView();
//                            Log.d("camein1","yes");
//                            new Handler().postDelayed(new Runnable()
//                            {
//                                public void run()
//                                {
//                                    Log.d("camein2","yes");
//                                    dialog.dismiss();
//                                        String ddo = getString(R.string.downloadcom);
//                                        Toast.makeText(getBaseContext(), ddo, Toast.LENGTH_LONG)
//                                                .show();
//                                        Intent qw = new Intent (Settings_activity.this,NavigationActivity.class);
//                                        startActivity(qw);
//
//                                }
//                            }, 5000);

//                            popup("1");
//                        }
//                        ((Callback)getApplication()).emailid1();
//                        lolol.setVisibility(View.INVISIBLE);

                    } catch (IOException e) {
                        Log.e("Exception", "File write failed: " + e.toString());
                    }
                }

            }
        } catch (Exception e) {

//            Toast.makeText(getBaseContext(), e.toString()+""+"impo1", Toast.LENGTH_LONG)
//                    .show();

        }
    }

    //navv() - Upload database to firebase
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void navv(){
       Log.d("Nav_nav","yes");
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.putInt("isloginn",0);
        editor.commit();
//        progre();
//        String ddo = getString(R.string.downloadcom);
//        Toast.makeText(getBaseContext(), ddo, Toast.LENGTH_LONG)
//                .show();
        String lic = licensed.getText().toString();
        FirebaseStorage storage;
//        getvv = bb ;
        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        // Create a reference to the file to delete
        StorageReference desertRef = storageRef.child(lic+"/admin/dd_backup");
        // Delete the file
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
                Intent qw = new Intent (Settings_activity.this,Login.class);
                startActivity(qw);
                finish();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Uh-oh, an error occurred!
                        Intent qw = new Intent (Settings_activity.this,Login.class);
                        startActivity(qw);
                        finish();
                    }
                });

    }

    //backupDatabase() - Take backup of current database
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void backupDatabase() {
        populateRecyclerView();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String myFormat1 = "dd/MM/yyyy";//In which you need put here
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
//        todaaaa = sdf1.format(c.getTime());
        DateFormat dff = new SimpleDateFormat("MMM_d_yyyy_hh_mm_aaa");
        todaaaa = dff.format(c.getInstance().getTime());
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        File Directory = new File("/sdcard/DailyDiary_downloads/");
        Directory.mkdirs();
        String currentDBPath = "/data/"+ "com.rampit.rask3.dailydiary" +"/databases/LOGIN";
        String backupDBPath = "/Dailydiary_downloads/DD_RRP"+"-"+todaaaa+"_Backup_Mail";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        Log.d("backupname",backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
//            Toast.makeText(ExportDB.this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch(IOException e) {
            e.printStackTrace();
//            Toast.makeText(this, "Exported Failed!", Toast.LENGTH_LONG).show();
        }
//        //Open your local db as the input stream
////        final String inFileName =getApplicationContext().getDatabasePath("LOGIN").getPath();
//        final String inFileName ="/data/user/0/com.rampit.rask3.dailydiary/databases/LOGIN";
////        String inFileName = "/data/data/com.myapp.main/databases/MYDB";
//        File dbFile = new File(inFileName);
//        FileInputStream fis = new FileInputStream(dbFile);
//
//        String outFileName = Environment.getExternalStorageDirectory()+"/LOGIN";
//        //Open the empty db as the output stream
//        OutputStream output = new FileOutputStream(outFileName);
//        //transfer bytes from the inputfile to the outputfile
//        byte[] buffer = new byte[1024];
//        int length;
//
//        while ((length = fis.read(buffer))>0){
//            output.write(buffer, 0, length);
//        }
//        //Close the streams
//        output.flush();
//        output.close();

//        PrintWriter w = new PrintWriter(new OutputStreamWriter(output, "UTF-8"));
//        w.print(buffer, 0, length);
//        w.flush();
//        w.close();
//        fis.close();
        pdfFile = backupDB;
        pdfname = "DD_RRP"+"-"+todaaaa+"_Backup_Mail";
        if (InternetConnection.checkConnection(getApplicationContext(),Settings_activity.this)) {
            // Internet Available...
            Log.d("internet","on");
//            date32();
            if(license == null || license.equalsIgnoreCase("") || license.equalsIgnoreCase("null")){
                progre();
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
                String enn = getString(R.string.no_license);
                String war = getString(R.string.warning);
                String ook = getString(R.string.ok);
                alertbox.setMessage(enn);
                alertbox.setTitle(war);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setPositiveButton(ook,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
//                                ssss();
//                                license = "License" ;
//                                lastlogin1(backupDB);
                            }
                        });
                alertbox.show();
            }else{
                Calendar c1 = Calendar.getInstance();
                SimpleDateFormat dff1 = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = dff1.format(c1.getTime());
                String pathh1 = license+"/daily/"+formattedDate;
//                ((Callback)getApplication()).show_filee1(pathh1,license);
                lastlogin1(backupDB);
            }
        }
        else {
            progre();
            // Internet Not Available...
            Log.d("internet","off");
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
            String enn = getString(R.string.on_internet);
            String war = getString(R.string.warning);
            String ook = getString(R.string.ok);
            alertbox.setMessage(enn);
            alertbox.setTitle(war);
            alertbox.setIcon(R.drawable.dailylogo);
            alertbox.setPositiveButton(ook,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {
                            ssss();
                        }
                    });
            alertbox.show();

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
    //lastlogin1() - Upload info file to firebase
    //Params - File location
    //Created on 25/01/2022
    //Return - NULL
    public void lastlogin1(File backupDB) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        //    String myFormat1 = "dd/MM/yyyy";//In which you need put here
        String formattedDate = df.format(c.getTime());
        SharedPreferences pref = getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
        String ss2 = String.valueOf(c.getTime());
        Log.d("datedd", ss2);
        String pattern = "dd/MM/yyyy";
        String pattern1 = "dd-MM-yyyy";
        SimpleDateFormat dateFormat1 = new SimpleDateFormat(pattern);
        String ss = dateFormat1.format(c.getTime());
        String ss1 = pref.getString("lastlogin", null);
        Log.d("datedddiiff_login",ss+" "+ss1);
        int dateDifference = (int) getDateDiff(new SimpleDateFormat("dd/MM/yyyy"), ss1, ss);
        System.out.println("dateDifference_login: " + dateDifference);
        SimpleDateFormat dateFormat11 = new SimpleDateFormat(pattern1);
        String ss0 = dateFormat11.format(c.getTime());
        FirebaseStorage storage;
        storage = FirebaseStorage.getInstance();
//        Uri.fromFile(backupDB),license,ss0
        StorageMetadata metadata = new StorageMetadata.Builder()
//                .setContentType("image/jpeg")
                .build();
        StorageReference ref = storage.getReference().child(String.valueOf(license)+"/"+"daily"+"/"+String.valueOf(ss0));
        String finalValue = license;
        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("server", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref1.edit();
        editor.putString("isLoading","uploading");
        editor.apply();
        ref.putFile(Uri.fromFile(backupDB))

                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        progre();
                        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("server", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref1.edit();
                        Calendar c = Calendar.getInstance();
                        DateFormat dff = new SimpleDateFormat("dd/MM/yyyy hh:mm:aaa");
                        String todaaaa = dff.format(c.getInstance().getTime());
                        editor.putString("isLoading","yes");
                        editor.putString("isLoaded_time",todaaaa);
                        editor.apply();
                        DateFormat dff1 = new SimpleDateFormat("yyyyMMddHHmmss");
                        Calendar cal = Calendar.getInstance();
                        String curdate1 = dff1.format(cal.getInstance().getTime());
                        dd_files_license(ss0,curdate1);
//                        Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progre();
                        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref1.edit();
                        editor.putString("isLoading","fail");
                        editor.apply();
                        Log.d("dddata","failure"+" "+String.valueOf(e));
                        Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    //dd_files_license() - add or update data in firebase
    //Params - filename and date
    //Created on 25/01/2022
    //Return - NULL
    public void dd_files_license(String ss0,String datetime){
        Log.d("licccccccccc",license+" "+ss0);
//        final SharedPreferences pref = mContext.getSharedPreferences("MyPref", 0); // 0 - for private mode
//        String id = pref.getString("userid", "");
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
//        DocumentReference user = db.collection("PhoneBook").document("Contacts");
        CollectionReference citiesRef = db.collection("dd_files_license");
        citiesRef.whereEqualTo("license",license)
                .whereEqualTo("filename",ss0)
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
                        Log.d("licccccccccc","licccccccccc");
                        DateFormat dff1 = new SimpleDateFormat("yyyyMMddHHmmss");
                        DateFormat dff11 = new SimpleDateFormat("yyyyMMdd");
                        Calendar cal = Calendar.getInstance();
                        String curdate1 = dff1.format(cal.getInstance().getTime());
                        String curdate11 = dff11.format(cal.getInstance().getTime());
                        FirebaseDatabase database111 = FirebaseDatabase.getInstance();
                        final DatabaseReference myRef11 = database111.getReference("dd_files_license");
                        String mGroupId = myRef11.push().getKey();
                        Log.d("mGroupId",String.valueOf(mGroupId));
                        myRef11.child(mGroupId).child("id").setValue(mGroupId);


                        FirebaseFirestore db1 = FirebaseFirestore.getInstance();
                        Map<String, Object> city111 = new HashMap<>();
                        city111.put("id", mGroupId);
                        city111.put("license", license);
                        city111.put("filename",ss0);
                        city111.put("created_date", curdate1);
                        city111.put("for_order", curdate11);
                        city111.put("updated_date", "");
                        db1.collection("dd_files_license").document(String.valueOf(mGroupId))
                                .set(city111)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("ggg", "DocumentSnapshot successfully written!");
                                        progre();
                                        Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("ggg", "Error writing document", e);
                                        progre();
                                        Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                    else{
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            list.add(document.getId());
                            String id = document.getString("id");
                            String file = document.getString("filename");
                            String license = document.getString("license");
                            Log.d("licccccccccc",id);
                            DateFormat dff1 = new SimpleDateFormat("yyyyMMddHHmmss");
                            DateFormat dff11 = new SimpleDateFormat("yyyyMMdd");
                            Calendar cal = Calendar.getInstance();
                            String curdate1 = dff1.format(cal.getInstance().getTime());
                            String curdate11 = dff11.format(cal.getInstance().getTime());
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            DocumentReference userss = db.collection("dd_files_license").document(String.valueOf(id));
                            userss.update("created_date", curdate1)
                                    .addOnSuccessListener(new OnSuccessListener< Void >() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    });
                            vva = vva + 1;
                            if(vva.equals(cco)){
                                progre();
                                Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                } else {
                    Log.d("gettingids", "Error getting documents: ", task.getException());
                }
            }
        });
    }
    //InternetConnection() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Settings_activity settings_activity) {
            final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected to the internet
//                Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();
                String sss = String.valueOf(activeNetworkInfo.getTypeName());
                Log.d("y7",sss);

                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    yy = 1;
                    return true;
                } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's data plan
                    yy = 2;
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
                alertbox.setCancelable(true);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setPositiveButton(ook,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                try {
//                                    login.setMobileDataEnabled(login,true);
                                    settings_activity.ssss();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                alertbox.show();
            }
            return false;
        }
    }
    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Settings_activity settings_activity) {
            final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected to the internet
//                Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();
                String sss = String.valueOf(activeNetworkInfo.getTypeName());
                Log.d("y7",sss);

                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    yy = 1;
                    return true;
                } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's data plan
                    yy = 2;
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
                                    settings_activity.ssss();
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

    //ssss() - On internet in settings page
    //Params - filename and date
    //Created on 25/01/2022
    //Return - NULL
    public void ssss(){
        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
    }

    //info_firebase() - download info file from firebase
    //Params - file uri , file name
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

    //WriteBtn() - upload info file to firebase
    //Params - view ,datas , license
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

    //total() - get all customers
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void total(){
        Names.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String[] columns = {"debit_amount",
                "id",""};
        String MY_QUERY = "SELECT customer_name FROM dd_customers  WHERE tracking_id = ?";

        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{String.valueOf(ses)});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);

//                    sll = sll + 1;

                    Names.add(Name);


                    Log.d("names", String.valueOf(Names));
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
//    toto.setText(":"+" "+String.valueOf(Names.size()));
//        totaltext.setText(":"+" "+String.valueOf(Names.size()));
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

    //progre() - Stop progressbar
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void progre(){
        dialog.cancel();
    }

    //populateRecyclerView() - get all settings data
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView() {
        Names.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();

        String MY_QUERY = "SELECT * FROM dd_collection where id = ?";
        String whereClause = "ID=?";
        String[] whereArgs = new String[] {"1"};

//        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{idd});
        String[] columns = {"license",
                "cc","ID","email","expiry","language","password","pdf_password","phone_number"};
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

                    index = cursor.getColumnIndexOrThrow("license");
                    String lice = cursor.getString(index);
                    license = lice ;

                    index = cursor.getColumnIndexOrThrow("ID");
                    iid = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("cc");
                    String  emacc = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("email");
                    String ema = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("expiry");
                    String exp = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("phone_number");
                    String phone12 = cursor.getString(index);

//                    index = cursor.getColumnIndexOrThrow("newws");
//                    Integer rtr = cursor.getInt(index);

                    index = cursor.getColumnIndexOrThrow("language");
                    lann = cursor.getString(index);
//                    Log.d("opop",exp1);
                    index = cursor.getColumnIndexOrThrow("pdf_password");
                    String pdfpp = cursor.getString(index);
                    pdfpass123 = cursor.getString(index);

                    if(lann == null){
                        lann = "ta";
                    }
                    if(phone12 == null){
                        phone12 = "";
                    }
                    if(pdfpp == null){
                    }
                    langua =lann;
//Log.d("rtrtrt",String.valueOf(rtr));
                    if(langua.equalsIgnoreCase("en")){
                        spinner.setSelection(1);
                    }else if(langua.equalsIgnoreCase("fr")){
                        spinner.setSelection(2);
                    }else if(langua.equalsIgnoreCase("ta")){
                        spinner.setSelection(3);
                    }
                    index = cursor.getColumnIndexOrThrow("password");
                    String pass = cursor.getString(index);
//Log.d("eeee",exp);
                    if(exp == null){
                        formattedDate = "";
                    }else {
                        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                        try {
                            Calendar c = Calendar.getInstance();
                            String formattedDate1 = df.format(c.getTime());

                            Date newda = df.parse(formattedDate1);
                            Date debit = df.parse(exp);
                            formattedDate = df1.format(debit);
                            long diffInMillisec =    debit.getTime() - newda.getTime();
                            long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                            String remmmm = String.valueOf(diffInDays+1);
                            if(Integer.parseInt(remmmm) <=2){
                                renew.setVisibility(View.VISIBLE);
                            }
                            Log.d("deae", String.valueOf(diffInDays+1));
                            toto.setText(":"+" "+String.valueOf(diffInDays+1));
                            totaltext.setText(String.valueOf(diffInDays+1));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    pdfpasss.setText(pdfpp);
                    licensed.setText(lice);
                    emailed.setText(ema);
                    emcc.setText(emacc);
                    expi.setText(formattedDate);
                    phonenum.setText(phone12);
                    licensetext.setText(lice);
                    emailtext.setText(ema);
                    emailcctext.setText(emacc);
                    expitext.setText(formattedDate);

                    languagetext.setText(lann);
                    populateRecyclerView1();
                }
                while (cursor.moveToNext());
            }
        }
    }

    //populateRecyclerView1() - get all user data
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView1() {
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String whereClause = "ID=?";
        String[] whereArgs = new String[] {"1"};
        String[] columns = {"password","username"};
        Cursor cursor = database.query("dd_user",
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
                    index = cursor.getColumnIndexOrThrow("password");
                    String pass = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("username");
                    String usss = cursor.getString(index);
                    if(pass.equalsIgnoreCase("")){
                        pass = "";
                    }
                    if(usss.equalsIgnoreCase("")){
                        usss = "";
                    }
                    userr.setText(usss);
                    usertext.setText(usss);
                    pased.setText(pass);
                    passwordtext.setText(pass);
                    Log.d("passss",pass);
                }
                while (cursor.moveToNext());
            }
        }
    }

    //populateRecyclerView3() - get all user data
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView3() {
        String nm = userr.getText().toString();
        String pass = pased.getText().toString();
        database = sqlite.getWritableDatabase();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = df.format(c.getTime());
        String MY_QUERY = "SELECT * FROM dd_user WHERE username = ? AND id > 1  ";
        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{nm});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("ID");
                    userid = cursor.getString(index);
                    Log.d("idid",userid);
                }
                while (cursor.moveToNext());
            }
        }
        if(userid != null){
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
            String enn = getString(R.string.user_exists);
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
        }else{
            database = sqlite.getWritableDatabase();
            ContentValues values3 = new ContentValues();
            values3.put("username", nm);
            values3.put("password", pass);
            values3.put("updated_date", formattedDate);
            Log.d("uui",nm+" "+pass);
            database.update("dd_user", values3, "ID = ?", new String[]{"1"});
            setLocale(langua);
        }
    }

    //setLocale() - set language to app
    //Params - language name
    //Created on 25/01/2022
    //Return - NULL
    public void setLocale(String localeName) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            Log.d("o9p0",localeName);
//            Locale localeEn = new Locale("en_US");
//            Locale.setDefault(localeEn);
//            Configuration configEn = new Configuration();
//            configEn.locale = localeEn;
//            getApplicationContext().getResources().updateConfiguration(configEn, null);
//            this.recreate();
            LocaleHelper1.setLocale(Settings_activity.this, "ta");
            recreate();
          } else {
            String language = "ta";
            String country = "rIN";
            Locale locale = new Locale(language , country);
            Locale myLocale = new Locale(localeName);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            Intent refresh = new Intent(this, NavigationActivity.class);
            refresh.putExtra(localeName, localeName);
            startActivity(refresh);
            finish();
         }
//        if (!localeName.equals(currentLanguage)) {

//        } else {
//            Toast.makeText(Settings_activity.this, "Language already selected!", Toast.LENGTH_SHORT).show();
//        }
    }
//    public void expii1(){
//        sqlite = new SQLiteHelper(Settings_activity.this);
//        database = sqlite.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("expiry", todate);
//        values.put("license", llll);
//        database.update("dd_settings",  values,"ID = ?",new String[]{"1"});
//        sqlite.close();
//        database.close();
//        Toast.makeText(getApplicationContext(),"Key Updated",Toast.LENGTH_LONG).show();
//        Intent login123 = new Intent(Settings_activity.this, NavigationActivity.class);
//        startActivity(login123);
//        finish();
//      }

    //expii1() - add or update license and expiry
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void expii1(){
        sqlite = new SQLiteHelper(Settings_activity.this);
        database = sqlite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("expiry", todate);
        values.put("license", llll);
        database.update("dd_settings",  values,"ID = ?",new String[]{"1"});
        sqlite.close();
        database.close();
        if(subscr_id == null || subscr_id.equalsIgnoreCase("") || subscr_id.equalsIgnoreCase("null")
               )
        {
                SQLiteHelper sqlite = new SQLiteHelper(Settings_activity.this);
                SQLiteDatabase database = sqlite.getWritableDatabase();
                ContentValues values1 = new ContentValues();
                values1.put("quick_bulkupdate", "0");
                database.update("dd_user", values1,"ID = ?", new String[]{"1"});
                sqlite.close();
                database.close();
                Log.d("subscr_id","subscr_id");
                final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                final SharedPreferences.Editor editor = pref.edit();
                editor.putString("quickbulk", String.valueOf(0));
                editor.commit();
            }
        else if (  subscr_id.equalsIgnoreCase("quick_bulk_update")){
                Log.d("subscr_id",subscr_id);
                SQLiteHelper sqlite = new SQLiteHelper(Settings_activity.this);
                SQLiteDatabase database = sqlite.getWritableDatabase();
                ContentValues values1 = new ContentValues();
                values1.put("quick_bulkupdate", "1");
                database.update("dd_user", values1,"ID = ?", new String[]{"1"});
                sqlite.close();
                database.close();
                final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                final SharedPreferences.Editor editor = pref.edit();
                editor.putString("quickbulk", String.valueOf(1));
                editor.commit();
            }
        else{
            SQLiteHelper sqlite = new SQLiteHelper(Settings_activity.this);
            SQLiteDatabase database = sqlite.getWritableDatabase();
            ContentValues values1 = new ContentValues();
            values1.put("quick_bulkupdate", "0");
            database.update("dd_user", values1,"ID = ?", new String[]{"1"});
            sqlite.close();
            database.close();
            Log.d("subscr_id","subscr_id");
            final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
            final SharedPreferences.Editor editor = pref.edit();
            editor.putString("quickbulk", String.valueOf(0));
            editor.commit();
        }
        Toast.makeText(getApplicationContext(),"Key Updated",Toast.LENGTH_LONG).show();
        Intent login123 = new Intent(Settings_activity.this, NavigationActivity.class);
        startActivity(login123);
        finish();
      }

    //renew_lic() - Renew license
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void renew_lic(){
        //set layout custom
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_code);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.setCancelable(false);
        dialog.getWindow().setAttributes(lp);
        final EditText date;
        Button renew;
        renew = dialog.findViewById(R.id.renew);
        date = dialog.findViewById(R.id.date);
        renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cod = date.getText().toString();
                Log.d("renew_code",cod);
                {
                    progressbar_load();
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            //TODO your background code
                            try {
                                llll = licensed.getText().toString();
                                Settings_activity.call_me3();

                            } catch (final Exception e) {
                                progre();
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
                                        String warr = getString(R.string.warning);
                                        String logo = getString(R.string.ok);
                                        alertbox.setMessage(getString(R.string.invalid_code));
                                        alertbox.setTitle(warr);
                                        alertbox.setIcon(R.drawable.dailylogo);
                                        alertbox.setPositiveButton(logo,
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface arg0,
                                                                        int arg1) {
                                                    }
                                                });
                                        alertbox.show();
                                        e.printStackTrace();
                                    }
                                });
                                Log.d("ERROR",String.valueOf(e));

                            }
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            if(activedd == null || activedd.equalsIgnoreCase("") || activedd.equalsIgnoreCase("null")){
                                                progre();
                                                AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
                                                String warr = getString(R.string.warning);
                                                String logo = getString(R.string.ok);
                                                alertbox.setMessage(getString(R.string.invalid_code));
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
                                            else if(activedd.equalsIgnoreCase("1")){
                                                final String licc = licensed.getText().toString();
                                                if(licc == null){
                                                }
                                                else{
                                                    progressbar_load();
                                                    AsyncTask.execute(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            //TODO your background code
                                                            try {
                                                                llll = licc;
                                                                Settings_activity.call_me2();
                                                                if(eer.equalsIgnoreCase("success")){
                                                                    Settings_activity.call_me();
//                                   expii1();
                                                                }else if(eer.equalsIgnoreCase("error")){
                                                                    Settings_activity.call_me();
                                                                }
                                                            } catch (Exception e) {
                                                                progre();
                                                                AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
                                                                String warr = getString(R.string.warning);
                                                                String logo = getString(R.string.ok);
                                                                alertbox.setMessage(String.valueOf(e));
                                                                alertbox.setTitle(warr);
                                                                alertbox.setIcon(R.drawable.dailylogo);
                                                                alertbox.setPositiveButton(logo,
                                                                        new DialogInterface.OnClickListener() {

                                                                            public void onClick(DialogInterface arg0,
                                                                                                int arg1) {
                                                                            }
                                                                        });
                                                                alertbox.show();
                                                                e.printStackTrace();
                                                            }
                                                            new Timer().schedule(new TimerTask() {
                                                                @Override
                                                                public void run() {
                                                                    runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
//                                                                            if(actsta.equalsIgnoreCase("active") && device_id1.equalsIgnoreCase(device_id)){
                                                                                if(actsta.equalsIgnoreCase("active") ){
                                                                                progre();
                                                                                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                                                                                SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                                                                                try {
                                                                                    Date debit = df1.parse(expiii);
                                                                                    todate = df.format(debit);
                                                                                    Log.d("deae",todate);

                                                                                } catch (ParseException e) {
                                                                                    e.printStackTrace();
                                                                                }
                                                                                Log.d("deae","expii");
                                                                                expii1();
                                                                            }
                                                                            else if(actsta.equalsIgnoreCase("active")){
                                                                                progre();
                                                                                AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
                                                                                String logmsg = getString(R.string.device_id_no);
                                                                                String warr = getString(R.string.warning);
                                                                                String logo = getString(R.string.ok);
                                                                                alertbox.setMessage(logmsg);
                                                                                alertbox.setTitle(warr);
                                                                                alertbox.setIcon(R.drawable.dailylogo);
                                                                                alertbox.setPositiveButton(logo,
                                                                                        new DialogInterface.OnClickListener() {

                                                                                            public void onClick(DialogInterface arg0,
                                                                                                                int arg1) {
                                                                                            }
                                                                                        });
                                                                                alertbox.show();
                                                                                Log.d("deae","expii1");
                                                                            }
                                                                            else if(!actsta.equals("active")){
                                                                                progre();
                                                                                AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
                                                                                String logmsg = getString(R.string.license_no_active);
                                                                                String warr = getString(R.string.warning);
                                                                                String logo = getString(R.string.ok);
                                                                                alertbox.setMessage(logmsg);
                                                                                alertbox.setTitle(warr);
                                                                                alertbox.setIcon(R.drawable.dailylogo);
                                                                                alertbox.setPositiveButton(logo,
                                                                                        new DialogInterface.OnClickListener() {

                                                                                            public void onClick(DialogInterface arg0,
                                                                                                                int arg1) {
                                                                                            }
                                                                                        });
                                                                                alertbox.show();
                                                                                Log.d("deae","expii1");
                                                                            }
                                                                            else{
                                                                                progre();
                                                                                AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
                                                                                String logmsg = getString(R.string.no_response);
                                                                                String warr = getString(R.string.warning);
                                                                                String logo = getString(R.string.ok);
                                                                                alertbox.setMessage(logmsg);
                                                                                alertbox.setTitle(warr);
                                                                                alertbox.setIcon(R.drawable.dailylogo);
                                                                                alertbox.setPositiveButton(logo,
                                                                                        new DialogInterface.OnClickListener() {

                                                                                            public void onClick(DialogInterface arg0,
                                                                                                                int arg1) {
                                                                                            }
                                                                                        });
                                                                                alertbox.show();
                                                                                Log.d("deae","expii2");
                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                            }, 7000);

                                                        }
                                                    });
                                                }
                                            }
                                            else if(activedd.equalsIgnoreCase("2")){
                                                progre();
                                                AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
                                                String warr = getString(R.string.warning);
                                                String logo = getString(R.string.ok);
                                                alertbox.setMessage(getString(R.string.code_already_used));
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
                                            else{
                                                progre();
                                                AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
                                                String warr = getString(R.string.warning);
                                                String logo = getString(R.string.ok);
                                                alertbox.setMessage(getString(R.string.invalid_code));
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
                                        }
                                    });
                                }
                            }, 7000);

                        }
                    });
                }
            }
        });

        dialog.show();

    }

    //call_me() - get expiry date from server
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static void call_me() throws Exception {
//        http://www.knowledgebags.com/?secret_key=5d9c9a700b9aa6.64670171&slm_action=slm_activate&license_key=5dd14f6b498af&registered_domain=asdfjweruowefu2342384084
        String url = "http://knowledgebags.com/?slm_action=slm_check&secret_key=5d9c9a700b9aa6.64670171&license_key="+llll;
//        String url = "http://www.knowledgebags.com/?secret_key=5d9c9a700b9aa6.64670171&slm_action=slm_activate&license_key="+llll+"&registered_domain="+device_id;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is POST
        con.setRequestMethod("POST");
        //add request header
//        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print in String
        System.out.println(response.toString());
        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response.toString());
        System.out.println("result after Reading JSON Response");
        System.out.println("result- "+myResponse.getString("result"));
        System.out.println("date_expiry- "+myResponse.getString("date_expiry"));
        expiii = myResponse.getString("date_expiry");
        actsta = myResponse.getString("status");
        device_id1 = myResponse.getString("registered_domains");
        subscr_id = myResponse.getString("subscr_id");
        product_ref = myResponse.getString("txn_id");
        String[] separated = device_id1.split(",");
        String deer = separated[3];
        String[] separated1 = deer.split(":");
        String deer1 = separated1[1];
        device_id1 = deer1.replace("\"", "");
        System.out.println("registered_domains- "+myResponse.getString("registered_domains"));
        System.out.println("registered_domains- "+device_id1);
        System.out.println("result "+product_ref);
    }

    //call_me2() - get expiry date from server
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static void call_me2() throws Exception {
//        http://www.knowledgebags.com/?secret_key=5d9c9a700b9aa6.64670171&slm_action=slm_activate&license_key=5dd14f6b498af&registered_domain=asdfjweruowefu2342384084
//        String url = "http://knowledgebags.com/?slm_action=slm_check&secret_key=5d9c9a700b9aa6.64670171&license_key="+llll;
        String url = "http://www.knowledgebags.com/?secret_key=5d9c9a700b9aa6.64670171&slm_action=slm_activate&license_key="+llll+"&registered_domain="+device_id;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is POST
        con.setRequestMethod("POST");
        //add request header
//        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print in String
        System.out.println(response.toString());
        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response.toString());
        System.out.println("result after Reading JSON Response");
        System.out.println("result- "+myResponse.getString("result"));
        eer = myResponse.getString("result");

//        System.out.println("date_expiry- "+myResponse.getString("date_expiry"));
//        String expiii = myResponse.getString("date_expiry");
//        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date debit = df1.parse(expiii);
//            todate = df.format(debit);
//            Log.d("deae",todate);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

    }

    //call_me3() - get expiry date from server
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static void call_me3() throws Exception {
//        http://www.knowledgebags.com/?secret_key=5d9c9a700b9aa6.64670171&slm_action=slm_activate&license_key=5dd14f6b498af&registered_domain=asdfjweruowefu2342384084
        String url = "http://knowledgebags.com/key-activation/?slm_action=slm_renewal&secret_key=5d9c9a700b9aa6.64670171&license_key="+llll+"&activation_code="+cod;
//        String url = "http://www.knowledgebags.com/?secret_key=5d9c9a700b9aa6.64670171&slm_action=slm_activate&license_key="+llll+"&registered_domain="+device_id;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is POST
        con.setRequestMethod("POST");
        //add request header
//        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print in String
        System.out.println(response.toString());
        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response.toString());
        System.out.println("result after Reading JSON Response");
        System.out.println("result- "+myResponse.getString("result"));
        System.out.println("renewed- "+myResponse.getString("renewed"));
        activedd = myResponse.getString("renewed");

    }

    //onBackPressed() - function called when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onBackPressed() {
       Intent na = new Intent(Settings_activity.this,NavigationActivity.class);
       startActivity(na);
        finish();
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
            Intent new1 = new Intent(Settings_activity.this,MainActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.user) {
            Intent new1 = new Intent(Settings_activity.this,HomeActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.debit) {
            Intent new1 = new Intent(Settings_activity.this,Debit.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.collection) {
            Intent new1 = new Intent(Settings_activity.this,AllCollection.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.account) {
            Intent new1 = new Intent(Settings_activity.this,Account.class);
            startActivity(new1);
            finish();
        }  else if (id == R.id.report) {
            Intent new1 = new Intent(Settings_activity.this,ReportActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.customer) {
            Intent new1 = new Intent(Settings_activity.this,Customer_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.settings) {
            Intent new1 = new Intent(Settings_activity.this,Settings_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.tally) {
            Intent new1 = new Intent(Settings_activity.this,Tally_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.employee) {
            Intent new1 = new Intent(Settings_activity.this,Employee_details.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.today) {
            Intent new1 = new Intent(Settings_activity.this,Today_report.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.logout) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Settings_activity.this,R.style.AlertDialogTheme);
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
                            Intent i = new Intent(Settings_activity.this,Splash.class);
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
