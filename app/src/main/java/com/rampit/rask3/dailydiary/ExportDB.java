package com.rampit.rask3.dailydiary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
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
import com.rampit.rask3.dailydiary.Adapter.Files_adapter;
import com.rampit.rask3.dailydiary.Adapter.Language_adapter;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdapter_num;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdapter_num1;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

//Updated on 25/01/2022 by RAMPIT
//used to import and export database
//checkForGooglePermissions2() - Check whether gmail is signed in
//driveSetUp2() - Setup drive
//InternetConnection11() - Check whether internet is on or not
//backupDatabase() - Used to take backup of database
//getDateDiff() - Used to get days between two dates
//lastlogin1() - upload file to firebase
//InternetConnection1() - Check whether internet is
//ssss() - Intent to on internet on or not
//populateRecyclerView() - get license from dd_settings
//importDB_set() - Import database from selected file
//InternetConnection1() - Check whether internet is on or not
//select_type_popup() - Select type of backup need to download from firebase
//show_filee() - download  database from firebase
//DownloadingTask() - AsyncTask to download file from url
//dd_files_license() - Get file data from firebase
//date_select() - Select data to download database from firebase
//drive() - get file and folder id from dd_drive
//get_folder() - get  folder id from firebase
//get_files1() - get files from firebase
//progressbar_load() - Load progressbar
//progre() - Stop progressbar
//filename_popup() - enter folder name
//navv() - intent to splash
//checkForGooglePermissions() - Check whether gmail is signed in
//checkForGooglePermissions1() - Check whether gmail is signed in
//driveSetUp() - Setup driver
//driveSetUp1() - Setup driver
//exportDB_set() - Export DB
//get_files() - get files for email
//select_language() - Select files to Sync
//download_sync_drive() - Download and sync to app
//onActivityResult() - get files from external storage
//isGoogleDriveUri() - check whether file is from drive
//getDriveFilePath() - get exact path file from drive
//importDB() - import selected backup to app
//gif() - load GIF
//popup() - show popup
//popup1() - show popup
//exporting database
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed

public class ExportDB  extends AppCompatActivity {
    Button expo,imp;
    LinearLayout lolol,down,up,ddrriivvee;
    Calendar c;
    File originalFile;
    String todaaaa,email;
    String inFileName,comname,file_id,folder_id;
    private static final int CAMERA_REQUEST = 1888;
    private ProgressDialog progressDialog;
    Dialog dialog;
    private static final int RC_AUTHORIZE_DRIVE = 100;
    private static final int REQUEST_PICK_IMAGE = 1;
    private static final int REQUEST_PICK_IMAGE1 = 1;
    Scope ACCESS_DRIVE_SCOPE = new Scope(Scopes.DRIVE_FILE);
    Scope SCOPE_EMAIL = new Scope(Scopes.EMAIL);
    Scope SCOPE_prfoirl = new Scope(Scopes.PROFILE);
    Scope SCOPE_openid = new Scope(Scopes.OPEN_ID);
    DriveServiceHelper mDriveServiceHelper;
    Drive googleDriveService;
    TextView upload_drive;
    TextView download_sync_drive;
    TextView download_backup;
    TextView upload_backup,save,textView2,textView3,textView21,textView31,textView311,edit_folder;
    String license, checkbox_value = "0",downloadUrl,pdfname,typeid;
    ArrayList<String> files_array = new ArrayList<>();
    ArrayList<String> files_array_id = new ArrayList<>();
    File pdfFile;
    static Integer yy ;
    Spinner type;
    CheckBox on_off;
    ArrayList<String> files_ids = new ArrayList<>();
    ProgressBar progressBar_cyclic;
    Integer showinng = 0 ;
    EditText folder_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
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
        comname = pref.getString("NAME","");
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dialog = new Dialog(ExportDB.this);
        c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String myFormat1 = "dd/MM/yyyy";//In which you need put here
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
//        todaaaa = sdf1.format(c.getTime());
        DateFormat dff = new SimpleDateFormat("MMM_d_yyyy_hh_mm_aaa");
        todaaaa = dff.format(c.getInstance().getTime());


        setContentView(R.layout.activity_export);
        setTitle(R.string.importexport);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        ActivityCompat.requestPermissions(ExportDB.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE} ,1);
        expo = findViewById(R.id.print_button);
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ExportDB.this, new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.INTERNET,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                //TODO
            }
        }else{
            ActivityCompat.requestPermissions(ExportDB.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} ,1);
            ActivityCompat.requestPermissions(ExportDB.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE} ,1);
        }
        drive();
        folder_name = findViewById(R.id.folder_name);
        SharedPreferences pref_folder = getApplicationContext().getSharedPreferences("folder", 0); // 0 - for private mode
        String ffooll = pref_folder.getString("folder_name","DailyDiary backups");
        folder_name.setText(ffooll);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView21 = findViewById(R.id.textView21);
        textView31 = findViewById(R.id.textView31);
        textView311 = findViewById(R.id.textView311);
        edit_folder = findViewById(R.id.edit_folder);
        edit_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filename_popup();
            }
        });
        progressBar_cyclic = findViewById(R.id.progressBar_cyclic);
        ddrriivvee = findViewById(R.id.ddrriivvee);
        on_off = findViewById(R.id.white);
        on_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    // checked
//                    checkbox_value = "1";
                    final SharedPreferences pref = getApplicationContext().getSharedPreferences("drive", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("drive_auto_backup","1");
                    editor.apply();
                    ddrriivvee.setVisibility(View.VISIBLE);
                }
                else
                {
                    ddrriivvee.setVisibility(View.GONE);
//                    checkbox_value = "0";
                    final SharedPreferences pref = getApplicationContext().getSharedPreferences("drive", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("drive_auto_backup","0");
                    editor.apply();
                    // not checked
                }
            }

        });
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
                            if (InternetConnection11.checkConnection(getApplicationContext(),ExportDB.this)) {
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
                            final SharedPreferences pref110 = getApplicationContext().getSharedPreferences("drive", 0); // 0 - for private mode
                            String last_upload = pref110.getString("last_upload","no");
                            String drive_auto_backup = pref110.getString("drive_auto_backup","0");
                            Log.d("last_uploadd",last_upload+" "+drive_auto_backup);
                            if(drive_auto_backup.equalsIgnoreCase("1")){
                                textView21.setVisibility(View.VISIBLE);
                                textView31.setVisibility(View.VISIBLE);
                            if(last_upload.equalsIgnoreCase("no")){
                                textView21.setVisibility(View.GONE);
                                textView31.setVisibility(View.GONE);
                            }else{
                                textView21.setText(getString(R.string.backup_status)+getString(R.string.uploaded_successfully));
                                textView31.setText(getString(R.string.updated_time)+last_upload);
                            }
                            }else{
                                textView21.setVisibility(View.GONE);
                                textView31.setVisibility(View.GONE);
                            }
                            String isLoading = pref11.getString("isLoading","no");
                            String isLoaded_time = pref11.getString("isLoaded_time","no");
                            Log.d("isLoading_daily",isLoading+" "+isLoaded_time);
                            if (InternetConnection1.checkConnection(getApplicationContext(),ExportDB.this)) {
                                // Internet Available...
                                Log.d("isLoading_daily","on"+" "+isLoading);
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
                                            Log.d("isLoading_daily",isLoading);
                                            // Stuff that updates the UI
//                                    fab.hide();
                                            progressBar_cyclic.setVisibility(View.GONE);
//            textView2.setVisibility(View.GONE);
                                            textView3.setVisibility(View.VISIBLE);
                                            textView2.setText(getString(R.string.backup_status)+getString(R.string.uploaded_successfully));
                                            textView3.setText(getString(R.string.updated_time)+isLoaded_time);
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
//            textView2.setVisibility(View.GONE);
                                            textView3.setVisibility(View.VISIBLE);
                                            textView2.setText(getString(R.string.backup_status)+getString(R.string.not_uploaded));
                                            textView3.setText(getString(R.string.updated_time)+isLoaded_time);
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
//            textView2.setVisibility(View.GONE);
                                            textView3.setVisibility(View.VISIBLE);
                                            textView2.setText(getString(R.string.backup_status)+getString(R.string.failed));
                                            textView3.setText(getString(R.string.updated_time)+isLoaded_time);
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
                                            textView3.setVisibility(View.VISIBLE);
                                            textView2.setText(getString(R.string.backup_status)+getString(R.string.uploading));
                                            textView3.setText(getString(R.string.updated_time)+isLoaded_time);
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
                                        textView3.setVisibility(View.GONE);
                                        textView2.setText(getString(R.string.backup_status)+getString(R.string.check_internet));
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
        type = findViewById(R.id.spinner);
        final List<String> spin = new ArrayList<>();
        String sell = getString(R.string.select);
        String ccred = getString(R.string.credit21);
        String ddebb = getString(R.string.debit21);

//        spin.add(sell);
        spin.add("5");
        spin.add("6");
        spin.add("7");
        spin.add("8");
        spin.add("9");
        spin.add("10");
        spin.add("11");
        spin.add("12");
        spin.add("13");
        spin.add("14");
        spin.add("15");
        spin.add("16");
        spin.add("17");
        spin.add("18");
        spin.add("19");
        spin.add("20");
        spin.add("21");
        spin.add("22");
        spin.add("23");
        spin.add("24");
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

        }
//        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
//                android.R.layout.simple_spinner_dropdown_item, Names);
        final SharedPreferences prefs1 = getApplicationContext().getSharedPreferences("drive", 0);
        String drive_auto_backup = prefs1.getString("drive_auto_backup","0");
        String drive_auto_backup_time = prefs1.getString("drive_auto_backup_time","5");
        if(drive_auto_backup.equalsIgnoreCase("1")){
            on_off.setChecked(true);
            ddrriivvee.setVisibility(View.VISIBLE);
            if (!GoogleSignIn.hasPermissions(
                    GoogleSignIn.getLastSignedInAccount(getApplicationContext()),
                    ACCESS_DRIVE_SCOPE,
                    SCOPE_EMAIL,
                    SCOPE_prfoirl,
                    SCOPE_openid)) {
                textView311.setVisibility(View.VISIBLE);
                textView311.setText(getString(R.string.please_sign));
            }else{
                textView311.setVisibility(View.GONE);
            }
        }
        Integer iiinnnnttt = Integer.parseInt(drive_auto_backup_time)-5;
        type.setSelection(iiinnnnttt);
        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = spin.get(Integer.parseInt(typeid));
                final SharedPreferences pref = getApplicationContext().getSharedPreferences("drive", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("drive_auto_backup_time",val);
                editor.apply();
                checkForGooglePermissions2();
            }
        });
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                // Object item = parentView.getItemAtPosition(position);

                Integer nu = type
                        .getSelectedItemPosition();
                typeid = String.valueOf(nu);



                Log.d("typedd",String.valueOf(nu));
            }
            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }

        });

        upload_backup = findViewById(R.id.upload_backup);
        download_backup = findViewById(R.id.download_backup);
        download_sync_drive = findViewById(R.id.download_sync_drive);
        upload_drive = findViewById(R.id.upload_drive);
        upload_drive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences dd_license = getApplicationContext().getSharedPreferences("dd_license", 0); // 0 - for private mode
                String dd_license1 = dd_license.getString("dd_license","0");
                Log.d("dd_licenseeee",dd_license1+" "+license);
                if(dd_license1.equalsIgnoreCase("0")){
                    progressbar_load();
                    checkForGooglePermissions();
                }else if(license.equalsIgnoreCase(dd_license1)){
                    progressbar_load();
                    checkForGooglePermissions();
                }else{
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(ExportDB.this,R.style.AlertDialogTheme);
                    String enn = getString(R.string.you_dont_have_permission1);
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

                                }
                            });

                    alertbox.show();
                }
            }
        });
        download_sync_drive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(ExportDB.this,R.style.AlertDialogTheme);
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
                                progressbar_load();
                                checkForGooglePermissions1();
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
        });

        upload_backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressbar_load();
                backupDatabase();
            }
        });


        download_backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String lic = license;
                if(lic == null || lic.equalsIgnoreCase("") ||  lic.equalsIgnoreCase("null") ){
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(ExportDB.this,R.style.AlertDialogTheme);
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
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(ExportDB.this,R.style.AlertDialogTheme);
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
                                    select_type_popup(lic,"Daily");
                                }
                            });
                    alertbox.setNegativeButton(cancel,
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                }
                            });
                    alertbox.show();

//                    show_filee(lic+"/admin/dd_backup","Admin");

                }
            }
        });

        imp = findViewById(R.id.import_button);
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);
//        lolol = findViewById(R.id.lolol);
//creating a new folder for the database to be backuped to
//        File direct = new File(Environment.getExternalStorageDirectory() + "/Exam Creator");
//
//        if(!direct.exists())
//        {
//            if(direct.mkdir())
//            {
//                //directory is created;
//            }
//
//        }
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                exportDB();
                Log.d("importttt","expoooo");
//                ActivityCompat.requestPermissions(ExportDB.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} ,1);
                exportDB();
//                ActivityCompat.requestPermissions(ExportDB.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE} ,1);
            }
        });
        expo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("importttt","expoooo");
//                ActivityCompat.requestPermissions(ExportDB.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} ,1);
                exportDB();
//                ActivityCompat.requestPermissions(ExportDB.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE} ,1);

            }
        });
//        down.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ActivityCompat.requestPermissions(ExportDB.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} ,1);
////                importDB();
////                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//////                intent = new Intent(Intent.ACTION_GET_CONTENT);
////                intent.setType("*/*");
////                startActivityForResult(intent,CAMERA_REQUEST);
//                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
//                chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
//                chooseFile.setType("*/*");
//                startActivityForResult(
//                        Intent.createChooser(chooseFile, "Choose a file"),
//                        CAMERA_REQUEST
//                );
//
////
////                ActivityCompat.requestPermissions(ExportDB.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE} ,1);
//            }
//        });
        imp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ActivityCompat.requestPermissions(ExportDB.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} ,1);
//                importDB();
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
////                intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");
//                startActivityForResult(intent,CAMERA_REQUEST);
                Log.d("importttt","imppoo");
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
                chooseFile.setType("*/*");
                startActivityForResult(
                        Intent.createChooser(chooseFile, "Choose a file"),
                        CAMERA_REQUEST
                );

            }
        });
        down.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                    Toast.makeText(getApplicationContext(), String.valueOf(interr), Toast.LENGTH_SHORT).show();
//                    calclate();
                }
            }
        });
//                up.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//                    public void onFocusChange(View v, boolean hasFocus) {
//                        if (hasFocus) {
//                            InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
//                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
////                    Toast.makeText(getApplicationContext(), String.valueOf(interr), Toast.LENGTH_SHORT).show();
////                    calclate();
//                        }
//                    }
//                });
//                imp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//                    public void onFocusChange(View v, boolean hasFocus) {
//                        if (hasFocus) {
//                            InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
//                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
////                    Toast.makeText(getApplicationContext(), String.valueOf(interr), Toast.LENGTH_SHORT).show();
////                    calclate();
//                        }
//                    }
//                });
//                expo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//                    public void onFocusChange(View v, boolean hasFocus) {
//                        if (hasFocus) {
//                            InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
//                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
////                    Toast.makeText(getApplicationContext(), String.valueOf(interr), Toast.LENGTH_SHORT).show();
////                    calclate();
//                        }
//                    }
//                });
        if(in == 0){
            imp.setBackgroundResource(R.drawable.download_orange);
            expo.setBackgroundResource(R.drawable.upload_orange);
        }
        else{
            imp.setBackgroundResource(R.drawable.download_blue);
            expo.setBackgroundResource(R.drawable.upload_blue);
        }
        populateRecyclerView();
    }
    //checkForGooglePermissions2() - Check whether gmail is signed in
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void checkForGooglePermissions2() {

        if (!GoogleSignIn.hasPermissions(
                GoogleSignIn.getLastSignedInAccount(getApplicationContext()),
                ACCESS_DRIVE_SCOPE,
                SCOPE_EMAIL,
                SCOPE_prfoirl,
                SCOPE_openid)) {
            progre();
            GoogleSignIn.requestPermissions(
                    ExportDB.this,
                    RC_AUTHORIZE_DRIVE,
                    GoogleSignIn.getLastSignedInAccount(getApplicationContext()),
                    ACCESS_DRIVE_SCOPE,
                    SCOPE_EMAIL ,
                    SCOPE_prfoirl,
                    SCOPE_openid);
        } else {
            Toast.makeText(this, "Permission to access Drive and Email has been granted", Toast.LENGTH_SHORT).show();
            driveSetUp2();

        }

    }

    //driveSetUp2() - Setup drive
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void driveSetUp2() {

        GoogleSignInAccount mAccount = GoogleSignIn.getLastSignedInAccount(ExportDB.this);
        email = mAccount.getEmail();
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("drive_mail",email);
        editor.apply();
        GoogleAccountCredential credential =
                GoogleAccountCredential.usingOAuth2(
                        getApplicationContext(), Collections.singleton(Scopes.DRIVE_FILE));
        credential.setSelectedAccount(mAccount.getAccount());
        googleDriveService = new Drive.Builder(
                AndroidHttp.newCompatibleTransport(),
                new GsonFactory(),
                credential)
                .setApplicationName("Dailydiary")
                .build();
        mDriveServiceHelper = new DriveServiceHelper(googleDriveService);




    }
    //InternetConnection11() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection11 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final ExportDB account) {
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

    //backupDatabase() - Used to take backup of database
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
        String currentDBPath = "/data/"+ "com.rampit.rask3.dailydiary" +"/databases/LOGIN";
        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Dailydiary_downloads");
        if (!docsFolder.exists()) {
            if (!docsFolder.mkdir()) {
//                Toast.makeText(context, "no directory", Toast.LENGTH_LONG).show();
            } else {
//                Log.i(TAG, "Created a new directory for PDF");
            }
        }
        String backupDBPath = "/Dailydiary_downloads/DD_RRP_Backup_Mail";
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
        if (InternetConnection.checkConnection(getApplicationContext(),ExportDB.this)) {
            // Internet Available...
            Log.d("internet","on");
//            date32();
            if(license == null || license.equalsIgnoreCase("") || license.equalsIgnoreCase("null")){
                progre();
                AlertDialog.Builder alertbox = new AlertDialog.Builder(ExportDB.this,R.style.AlertDialogTheme);
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
                                ssss();
                            }
                        });
                alertbox.show();
            }else{

                lastlogin1(backupDB);
            }
        } else {
            progre();
            // Internet Not Available...
            Log.d("internet","off");
            AlertDialog.Builder alertbox = new AlertDialog.Builder(ExportDB.this,R.style.AlertDialogTheme);
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

    //getDateDiff() - Used to get days between two dates
    //Params - date format , old and new date
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

    //lastlogin1() - upload file to firebase
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
        ref.putFile(Uri.fromFile(backupDB))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        progre();
//                        Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("server", 0);
                        SharedPreferences.Editor editor = pref.edit();
                        Calendar c = Calendar.getInstance();
                        DateFormat dff = new SimpleDateFormat("dd/MM/yyyy hh:mm:aaa");
                        String todaaaa = dff.format(c.getInstance().getTime());
                        editor.putString("isLoading","yes");
                        editor.putString("isLoaded_time",todaaaa);
                        editor.apply();
                        textView2.setVisibility(View.VISIBLE);
                        textView3.setVisibility(View.VISIBLE);
                        textView2.setText(getString(R.string.backup_status)+getString(R.string.uploaded_successfully));
                        textView3.setText(getString(R.string.updated_time)+todaaaa);
                        dd_files_license_upload(finalValue,ss0);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progre();
                        Log.d("dddata","failure"+" "+String.valueOf(e));
                        Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    //dd_files_license_upload() - add or update data in firebase
    //Params - filename and date
    //Created on 25/01/2022
    //Return - NULL
    public void dd_files_license_upload(String ss0,String datetime){
        Log.d("licccccccccc",license+" "+ss0);
//        final SharedPreferences pref = mContext.getSharedPreferences("MyPref", 0); // 0 - for private mode
//        String id = pref.getString("userid", "");
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
//        DocumentReference user = db.collection("PhoneBook").document("Contacts");
        CollectionReference citiesRef = db.collection("dd_files_license");
        citiesRef.whereEqualTo("license",license)
                .whereEqualTo("filename",datetime)
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
                        city111.put("license", ss0);
                        city111.put("filename",datetime);
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
                    }else{
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

    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final ExportDB settings_activity) {
            final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected to the internet
                Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();
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
                alertbox.show();
            }
            return false;
        }
    }

    //ssss() - Intent to on internet
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void ssss(){
        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
    }

    //populateRecyclerView() - get license from dd_settings
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView() {
        SQLiteHelper sqlite = new SQLiteHelper(this);
        SQLiteDatabase database = sqlite.getReadableDatabase();

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


//Log.d("eeee",exp);


                }
                while (cursor.moveToNext());
            }
        }
    }

    //importDB_set() - Import database from selected file
    //Params - Output file and input file location
    //Created on 25/01/2022
    //Return - NULL
    private void importDB_set(String outFileName,String inFileName) {
        // TODO Auto-generated method stub
        ((Callback)getApplication()).stop_t();
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
//                final String currentDBPath ="/data/user/0/com.rampit.rask3.dailydiary/databases/LOGIN";
//                String  currentDBPath= "//user//" +"//0//" + "com.rampit.rask3.dailydiary"
//                        + "//databases//" + "LOGIN";
//                String backupDBPath  = "/BackupFolder/LOGIN";
//                File  backupDB= new File(data, currentDBPath);
//                File currentDB  = new File(sd, backupDBPath);
//                SQLiteHelper sql = new SQLiteHelper(ExportDB.this);
                File currentDB = new File(outFileName);
                File backupDB = new File(inFileName);
                Log.d("rtrtrtr",String.valueOf(currentDB));
                Log.d("rtrtrtr",String.valueOf(backupDB));

                SQLiteHelper sql = new SQLiteHelper(ExportDB.this);

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
                        Log.d("trr1","yyy");
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

    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final ExportDB settings_activity) {
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

    //select_type_popup() - Select type of backup need to download from firebase
    //Params - license and type
    //Created on 25/01/2022
    //Return - NULL
    public void select_type_popup(final String license , final  String tyypp){
//        final Dialog dialog = new Dialog(license_activity.this,R.style.CustomAlertDialog);
        //set layout custom
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.confimed_popup);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        final CheckBox daily_check,weekly_check,monthly_check,admin_check;
        Button submit;
        TextView close;
        admin_check = dialog.findViewById(R.id.admin_check);
        daily_check = dialog.findViewById(R.id.daily_check);
        weekly_check = dialog.findViewById(R.id.weekly_check);
        monthly_check = dialog.findViewById(R.id.monthly_check);
        submit = dialog.findViewById(R.id.submit);
        close = dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        daily_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    // checked
                    weekly_check.setChecked(false);
                    monthly_check.setChecked(false);
                    admin_check.setChecked(false);
                    checkbox_value = "1";
                }
                else
                {
                    checkbox_value = "0";
                    // not checked
                }
            }

        });
        weekly_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    // checked
                    daily_check.setChecked(false);
                    monthly_check.setChecked(false);
                    admin_check.setChecked(false);
                    checkbox_value = "2";
                }
                else
                {
                    checkbox_value = "0";
                    // not checked
                }
            }

        });

        monthly_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    // checked
                    weekly_check.setChecked(false);
                    daily_check.setChecked(false);
                    admin_check.setChecked(false);
                    checkbox_value = "3";
                }
                else
                {
                    checkbox_value = "0";
                    // not checked
                }
            }

        });

        admin_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    // checked
                    weekly_check.setChecked(false);
                    daily_check.setChecked(false);
                    monthly_check.setChecked(false);
                    checkbox_value = "4";
                }
                else
                {
                    checkbox_value = "0";
                    // not checked
                }
            }

        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("uuurrrllll_dddd",String.valueOf(checkbox_value));
                if(checkbox_value.equalsIgnoreCase("1")){
                    dialog.cancel();
                    progressbar_load();
                    dd_files_license(license,tyypp);
//                    date_select(license+"/daily/");
//                    show_filee(license+"/daily/dd_backup");
                }else if(checkbox_value.equalsIgnoreCase("2")){
                    dialog.cancel();
//                    SharedPreferences pref = getApplicationContext().getSharedPreferences("database_pref", 0); // 0 - for private mode
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putString("database_name","Weekly");
//                    editor.apply();
                    show_filee(license+"/weekly/dd_backup","Weekly");
                }else if(checkbox_value.equalsIgnoreCase("3")){
                    dialog.cancel();
//                    SharedPreferences pref = getApplicationContext().getSharedPreferences("database_pref", 0); // 0 - for private mode
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putString("database_name","Monthly");
//                    editor.apply();
                    show_filee(license+"/monthly/dd_backup","Monthly");
                }
                else if(checkbox_value.equalsIgnoreCase("4")){
                    dialog.cancel();
//                    SharedPreferences pref = getApplicationContext().getSharedPreferences("database_pref", 0); // 0 - for private mode
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putString("database_name","Monthly");
//                    editor.apply();
                    show_filee(license+"/admin/dd_backup","Admin");
                }
            }
        });

        dialog.show();
    }

    //show_filee() - download  database from firebase
    //Params - filename and type
    //Created on 25/01/2022
    //Return - NULL
    public void show_filee(String value , final String typ){
        progressbar_load();
        FirebaseStorage storage;
        storage = FirebaseStorage.getInstance();
        Log.d("uuurrrllll_dddd",String.valueOf(value));
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
                Log.d("uuurrrllll_dddd",String.valueOf(uri));
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
                Log.d("uuurrrllll_dddd","File Not Found");
                AlertDialog.Builder alertbox = new AlertDialog.Builder(ExportDB.this,R.style.AlertDialogTheme);
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

    //DownloadingTask() - AsyncTask to download file from url
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
                c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                c.connect();//connect the URL Connection

                //If Connection response is not OK then show Logs
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e("dddooowwwnn", "Server returned HTTP " + c.getResponseCode()
                            + " " + c.getResponseMessage());

                }


                //Get File if SD card is present
//                if (new CheckForSDCard().isSDCardPresent()) {

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
                    originalFile = new File(FileUtils1.getRealPathFromURI_BelowAPI11(ExportDB.this, uri));
                    Log.d("rtrt3", String.valueOf(originalFile));
                }
                // SDK >= 11 && SDK < 19
                else if (Build.VERSION.SDK_INT < 19) {
                    originalFile = new File(FileUtils1.getRealPathFromURI_API11to18(ExportDB.this, uri));
//                        realPath = FileUtils1.getRealPathFromURI_API11to18(context, fileUri);
                    Log.d("rtrt2", String.valueOf(originalFile));
                }
                // SDK > 19 (Android 4.4) and up
                else {
                    originalFile = new File(FileUtils1.getRealPathFromURI_API19(ExportDB.this, uri));
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

    //dd_files_license() - Get file data from firebase
    //Params - license and type
    //Created on 25/01/2022
    //Return - NULL
    public void dd_files_license(final String license , final String tyypp){
//        final SharedPreferences pref = mContext.getSharedPreferences("MyPref", 0); // 0 - for private mode
//        String id = pref.getString("userid", "");
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        files_array.clear();
        files_array_id.clear();

//        DocumentReference user = db.collection("PhoneBook").document("Contacts");
        CollectionReference citiesRef = db.collection("dd_files_license");
        citiesRef.whereEqualTo("license",license)
                .orderBy("created_date", Query.Direction.ASCENDING)
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
                        dialog.cancel();
                        Log.d("uuurrrllll","File Not Found");
                        AlertDialog.Builder alertbox = new AlertDialog.Builder(ExportDB.this,R.style.AlertDialogTheme);
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
                    }else{
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            list.add(document.getId());
                            String id = document.getString("id");
                            String file = document.getString("filename");
                            String license = document.getString("license");
                            String created_date = document.getString("created_date");

                            SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmmss");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy ");
                            SimpleDateFormat dateFormat1 = new SimpleDateFormat("HH");
                            SimpleDateFormat dateFormat2 = new SimpleDateFormat("mm");
                            try {
                                Date date = format.parse(created_date);
                                String dateTime = dateFormat.format(date);
                                String dateTime1 = dateFormat1.format(date);
                                String dateTime2 = dateFormat2.format(date);
                                files_array_id.add(id+",,,"+dateTime+",,,"+file+",,,"+dateTime1+",,,"+dateTime2);
                                System.out.println(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            files_array.add(file);

                            vva = vva + 1;
                            if(vva.equals(cco)){

                                date_select(license+"/daily/","Daily");

                            }
                        }
                    }

                } else {
                    Log.d("gettingids", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    //date_select() - Select data to download database from firebase
    //Params - license and type
    //Created on 25/01/2022
    //Return - NULL
    public void date_select(final String license , final String tyypp){
//        final Dialog dialog = new Dialog(license_activity.this,R.style.CustomAlertDialog);
        //set layout custom
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.recycle_popup);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        ArrayList<String> dates_array = new ArrayList<>();
        dialog.getWindow().setAttributes(lp);
        RecyclerView days_recycle;
        TextView close;
        RecyclerViewAdapter_num1 mAdapter;
        close = dialog.findViewById(R.id.close);
        days_recycle = dialog.findViewById(R.id.days_recycle);
        for(int i=0;i<4;i++){
            Log.d("iiiii",String.valueOf(i));
            Integer vvaa = 0 ;
            vvaa = vvaa - i ;
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
        progre();
        mAdapter = new RecyclerViewAdapter_num1(getApplicationContext(),files_array,files_array_id,ExportDB.this,license,tyypp);
        mAdapter.setHasStableIds(true);
        days_recycle.setAdapter(mAdapter);
        days_recycle.setItemViewCacheSize(1000);
        days_recycle.setHasFixedSize(true);
        days_recycle.setNestedScrollingEnabled(false);
        mAdapter.onAttachedToRecyclerView(days_recycle);
        LinearLayoutManager layoutManager= new LinearLayoutManager(ExportDB.this, LinearLayoutManager.VERTICAL, false);
        days_recycle.setLayoutManager(layoutManager);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //drive() - get file and folder id from dd_drive
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void drive(){
        SQLiteHelper sqlite = new SQLiteHelper(this);
        SQLiteDatabase database = sqlite.getReadableDatabase();
        String MY_QUERY = "SELECT * FROM dd_drive  WHERE id = 1";

        Cursor cursor = database.rawQuery(MY_QUERY,null);
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("file_id");
                    file_id = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("folder_id");
                    folder_id = cursor.getString(index);
//                     Log.d("folder_id",folder_id);
//                    Log.d("file_id",file_id);
//                    sll = sll + 1;


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
            file_id ="";
            folder_id ="";

            cursor.close();
            sqlite.close();
            database.close();
        }
//    toto.setText(":"+" "+String.valueOf(Names.size()));
//        totaltext.setText(":"+" "+String.valueOf(Names.size()));
    }
    //get_folder() - get  folder id from firebase
    //Params - folder name
    //Created on 25/01/2022
    //Return - NULL
    public void get_folder(String folder_nnnn){
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences pref_folder = getApplicationContext().getSharedPreferences("folder", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref_folder.edit();
        editor.putString("folder_name",folder_nnnn);
        editor.apply();
        Log.d("file_id",email);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
//        DocumentReference user = db.collection("PhoneBook").document("Contacts");
        CollectionReference citiesRef = db.collection("dd_folders");
        citiesRef.whereEqualTo("email",email)
                .whereEqualTo("id",license)
                .whereEqualTo("folder_name",folder_nnnn)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    QuerySnapshot document1 = task.getResult();
                    Integer cco = document1.size();
                    Integer vva = 0;
                    String tot = String.valueOf(cco);

                    if(cco<=0){
                        Log.d("folder_idfolder_id", "folder_id");
                        folder_id = "";
                        if(folder_id == null || folder_id.equalsIgnoreCase("") || folder_id.equalsIgnoreCase("null")){
                            mDriveServiceHelper.createFolder(folder_nnnn,  "")
                                    .addOnSuccessListener(new OnSuccessListener<GoogleDriveFileHolder>() {
                                        @Override
                                        public void onSuccess(GoogleDriveFileHolder googleDriveFileHolder) {
//                        progre();
                                            if(license == null || license.equalsIgnoreCase("") || license.equalsIgnoreCase("null")){

                                            }
                                            else{
                                                Log.d("licensssssseeeee",String.valueOf(license));
                                                FirebaseApp.initializeApp(ExportDB.this);
                                                FirebaseDatabase database111 = FirebaseDatabase.getInstance();
                                                final DatabaseReference myRef11 = database111.getReference("dd_folders");
                                                String mGroupId = myRef11.push().getKey();
                                                Log.d("mGroupId",String.valueOf(mGroupId));
                                                myRef11.child(mGroupId).child("id").setValue(mGroupId);
                                                Calendar myCalendar = Calendar.getInstance();
                                                String myFormat = "yyyy/MM/dd"; //In which you need put here
                                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                                String dateString = sdf.format(myCalendar.getTime());
                                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                Map<String, Object> city11 = new HashMap<>();
                                                city11.put("id", license);
                                                city11.put("email", email);
                                                city11.put("folder_name",folder_nnnn );
                                                city11.put("folder_id", String.valueOf(googleDriveFileHolder.getId()));
                                                city11.put("created_date", dateString);
                                                city11.put("updated_date", "");
                                                db.collection("dd_folders").document(String.valueOf(mGroupId))
                                                        .set(city11)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d("ggg", "DocumentSnapshot successfully written!");
//                                                                exportDB_set();
                                                                folder_id = String.valueOf(googleDriveFileHolder.getId());
                                                                get_files1();
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w("ggg", "Error writing document", e);
                                                            }
                                                        });
                                            }
//                                            Calendar myCalendar = Calendar.getInstance();
//                                            String myFormat = "yyyy/MM/dd"; //In which you need put here
//                                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//                                            String dateString = sdf.format(myCalendar.getTime());
//                                            SQLiteHelper sqlite = new SQLiteHelper(ExportDB.this);
//                                            SQLiteDatabase database = sqlite.getWritableDatabase();
//                                            ContentValues values = new ContentValues();
//                                            values.put("folder_id", String.valueOf(googleDriveFileHolder.getId()));
//                                            values.put("created_date", dateString);
//                                            database.insert("dd_drive", null, values);
//                                            sqlite.close();
//                                            database.close();
//                                            folder_id = String.valueOf(googleDriveFileHolder.getId());
//                                            exportDB_set();
//                                            Log.i("Bitmap1", "Successfully Uploaded. Folder Id :" + googleDriveFileHolder.getId());
//                                            Toast.makeText(ExportDB.this, "Successfully Uploaded Drive ", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progre();
                                            Log.i("Bitmap23", "Failed to Upload. File Id :" + e.getMessage());
                                            Toast.makeText(ExportDB.this, "Failed to Upload ", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                        else{
//                            exportDB_set();
                            get_files1();
                        }
                    }else{
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            list.add(document.getId());
//                            Long id1 = document.getLong("id");
//                            Long user_id1 = document.getLong("user_id");
//                            Long user_id11 = document.getLong("admin_id");
                            String id =document.getString("id");
                            String uid = document.getString("folder_id");
                            String file_name = document.getString("file_name");
                            String uid1 = document.getString("folder_id");
                      folder_id = uid;
                            vva = vva + 1 ;
                            if(cco.equals(vva)){
                                Log.d("folder_idfolder_id", folder_id);
                                if(folder_id == null || folder_id.equalsIgnoreCase("") || folder_id.equalsIgnoreCase("null")){
                                    mDriveServiceHelper.createFolder(folder_nnnn,  "")
                                            .addOnSuccessListener(new OnSuccessListener<GoogleDriveFileHolder>() {
                                                @Override
                                                public void onSuccess(GoogleDriveFileHolder googleDriveFileHolder) {
//                        progre();
                                                    if(license == null || license.equalsIgnoreCase("") || license.equalsIgnoreCase("null")){

                                                    }
                                                    else{
                                                        Log.d("licensssssseeeee",String.valueOf(license));
                                                        FirebaseApp.initializeApp(ExportDB.this);
                                                        FirebaseDatabase database111 = FirebaseDatabase.getInstance();
                                                        final DatabaseReference myRef11 = database111.getReference("dd_folders");
                                                        String mGroupId = myRef11.push().getKey();
                                                        Log.d("mGroupId",String.valueOf(mGroupId));
                                                        myRef11.child(mGroupId).child("id").setValue(mGroupId);
                                                        Calendar myCalendar = Calendar.getInstance();
                                                        String myFormat = "yyyy/MM/dd"; //In which you need put here
                                                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                                        String dateString = sdf.format(myCalendar.getTime());
//                                                        exportDB_set();
                                                        get_files1();
                                                    }
//                                            Calendar myCalendar = Calendar.getInstance();
//                                            String myFormat = "yyyy/MM/dd"; //In which you need put here
//                                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//                                            String dateString = sdf.format(myCalendar.getTime());
//                                            SQLiteHelper sqlite = new SQLiteHelper(ExportDB.this);
//                                            SQLiteDatabase database = sqlite.getWritableDatabase();
//                                            ContentValues values = new ContentValues();
//                                            values.put("folder_id", String.valueOf(googleDriveFileHolder.getId()));
//                                            values.put("created_date", dateString);
//                                            database.insert("dd_drive", null, values);
//                                            sqlite.close();
//                                            database.close();
//                                            folder_id = String.valueOf(googleDriveFileHolder.getId());
//                                            exportDB_set();
//                                            Log.i("Bitmap1", "Successfully Uploaded. Folder Id :" + googleDriveFileHolder.getId());
//                                            Toast.makeText(ExportDB.this, "Successfully Uploaded Drive ", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progre();
                                                    Log.i("Bitmap23", "Failed to Upload. File Id :" + e.getMessage());
                                                    Toast.makeText(ExportDB.this, "Failed to Upload ", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }
                                else{
//                                    exportDB_set();
                                    get_files1();
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

    //get_files1() - get files from firebase
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void get_files1(){
        Log.d("file_id",email);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
//        DocumentReference user = db.collection("PhoneBook").document("Contacts");
        CollectionReference citiesRef = db.collection("dd_files");
        citiesRef.whereEqualTo("email",email)
                .whereEqualTo("id",license)
                .whereEqualTo("folder_id",folder_id)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    QuerySnapshot document1 = task.getResult();
                    Integer cco = document1.size();
                    Integer vva = 0;
                    String tot = String.valueOf(cco);

                    if(cco<=0){
                        Log.d("folder_idfolder_id", "folder_id");

                            exportDB_set();

                    }else{
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            list.add(document.getId());
//                            Long id1 = document.getLong("id");
//                            Long user_id1 = document.getLong("user_id");
//                            Long user_id11 = document.getLong("admin_id");
                            String id =document.getString("id");
                            String uid = document.getString("folder_id");
                            String file_id = document.getString("file_id");
                            Log.d("file_idfile_id", file_id);

                            mDriveServiceHelper.deleteFolderFile(file_id)
                                    .addOnSuccessListener(new OnSuccessListener() {
                                        @Override
                                        public void onSuccess(Object o) {
                                            Log.d("file_idfile_id", file_id+" DELETED ");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("file_idfile_id", file_id+" DELETE FAILED ");
                                        }
                                    });
                            vva = vva + 1 ;
                            if(cco.equals(vva)){
                                Log.d("folder_idfolder_id", folder_id);

                                    exportDB_set();

                            }

                        }


                    }

                } else {
                    Log.d("gettingids", "Error getting documents: ", task.getException());
                }
            }
        });
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

    //filename_popup() - enter folder name
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void filename_popup(){
        //set layout custom
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.filename_popup);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.setCancelable(true);
        dialog.getWindow().setAttributes(lp);
        final EditText folder_name1;
        Button save;
        folder_name1 = dialog.findViewById(R.id.folder_name);
        save = dialog.findViewById(R.id.save);
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences pref_folder = getApplicationContext().getSharedPreferences("folder", 0); // 0 - for private mode
        final String foll = pref_folder.getString("folder_name","DailyDiary backups");
        folder_name1.setText(foll);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fol_name = folder_name1.getText().toString();
                String field = fol_name.replaceAll("\\s+", "");
                Log.d("fol_field",fol_name.length()+" "+field.length());
                if (field == null || field.equalsIgnoreCase("") || field.equalsIgnoreCase("null")){
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(ExportDB.this,R.style.AlertDialogTheme);
                    String enn = getString(R.string.enter_folder_name);
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
                else{
//                    progressbar_load();
                    SharedPreferences pref_folder = getApplicationContext().getSharedPreferences("folder", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref_folder.edit();
                    editor.putString("folder_name",fol_name);
                    editor.apply();
                    dialog.dismiss();
                    folder_name.setText(fol_name);
//                    get_folder(fol_name);

                   /* if(fol_name.equalsIgnoreCase(foll)){
                        if(folder_id == null || folder_id.equalsIgnoreCase("") || folder_id.equalsIgnoreCase("null")){
                            mDriveServiceHelper.createFolder(fol_name,  "")
                                    .addOnSuccessListener(new OnSuccessListener<GoogleDriveFileHolder>() {
                                        @Override
                                        public void onSuccess(GoogleDriveFileHolder googleDriveFileHolder) {
//                        progre();
                                            if(license == null || license.equalsIgnoreCase("") || license.equalsIgnoreCase("null")){

                                            }
                                            else{
                                                Log.d("licensssssseeeee",String.valueOf(license));
                                                FirebaseApp.initializeApp(ExportDB.this);
                                                FirebaseDatabase database111 = FirebaseDatabase.getInstance();
                                                final DatabaseReference myRef11 = database111.getReference("dd_folders");
                                                String mGroupId = myRef11.push().getKey();
                                                Log.d("mGroupId",String.valueOf(mGroupId));
                                                myRef11.child(mGroupId).child("id").setValue(mGroupId);
                                                Calendar myCalendar = Calendar.getInstance();
                                                String myFormat = "yyyy/MM/dd"; //In which you need put here
                                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                                String dateString = sdf.format(myCalendar.getTime());
                                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                Map<String, Object> city11 = new HashMap<>();
                                                city11.put("id", license);
                                                city11.put("email", email);
                                                city11.put("folder_name",fol_name );
                                                city11.put("folder_id", String.valueOf(googleDriveFileHolder.getId()));
                                                city11.put("created_date", dateString);
                                                city11.put("updated_date", "");
                                                db.collection("dd_folders").document(String.valueOf(mGroupId))
                                                        .set(city11)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d("ggg", "DocumentSnapshot successfully written!");

                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w("ggg", "Error writing document", e);
                                                            }
                                                        });
                                            }
//                                            Calendar myCalendar = Calendar.getInstance();
//                                            String myFormat = "yyyy/MM/dd"; //In which you need put here
//                                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//                                            String dateString = sdf.format(myCalendar.getTime());
//                                            SQLiteHelper sqlite = new SQLiteHelper(ExportDB.this);
//                                            SQLiteDatabase database = sqlite.getWritableDatabase();
//                                            ContentValues values = new ContentValues();
//                                            values.put("folder_id", String.valueOf(googleDriveFileHolder.getId()));
//                                            values.put("created_date", dateString);
//                                            database.insert("dd_drive", null, values);
//                                            sqlite.close();
//                                            database.close();
//                                            folder_id = String.valueOf(googleDriveFileHolder.getId());
//                                            exportDB_set();
//                                            Log.i("Bitmap1", "Successfully Uploaded. Folder Id :" + googleDriveFileHolder.getId());
//                                            Toast.makeText(ExportDB.this, "Successfully Uploaded Drive ", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progre();
                                            Log.i("Bitmap23", "Failed to Upload. File Id :" + e.getMessage());
                                            Toast.makeText(ExportDB.this, "Failed to Upload ", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                        else{
                            exportDB_set();
                        }
                    }
                    else {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("folder_name",fol_name);
                        editor.apply();
                        mDriveServiceHelper.createFolder(fol_name,  "")
                                .addOnSuccessListener(new OnSuccessListener<GoogleDriveFileHolder>() {
                                    @Override
                                    public void onSuccess(GoogleDriveFileHolder googleDriveFileHolder) {
//                        progre();
                                        if(license == null || license.equalsIgnoreCase("") || license.equalsIgnoreCase("null")){

                                        }else{
                                            Log.d("licensssssseeeee",String.valueOf(license));
                                            FirebaseApp.initializeApp(ExportDB.this);
                                            FirebaseDatabase database111 = FirebaseDatabase.getInstance();
                                            final DatabaseReference myRef11 = database111.getReference("dd_folders");
                                            String mGroupId = myRef11.push().getKey();
                                            Log.d("mGroupId",String.valueOf(mGroupId));
                                            myRef11.child(mGroupId).child("id").setValue(mGroupId);
                                            Calendar myCalendar = Calendar.getInstance();
                                            String myFormat = "yyyy/MM/dd"; //In which you need put here
                                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                            String dateString = sdf.format(myCalendar.getTime());
                                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                                            Map<String, Object> city11 = new HashMap<>();
                                            city11.put("id", license);
                                            city11.put("email", email);
                                            city11.put("folder_name",fol_name );
                                            city11.put("folder_id", String.valueOf(googleDriveFileHolder.getId()));
                                            city11.put("created_date", dateString);
                                            city11.put("updated_date", "");
                                            db.collection("dd_folders").document(String.valueOf(mGroupId))
                                                    .set(city11)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d("ggg", "DocumentSnapshot successfully written!");

                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.w("ggg", "Error writing document", e);
                                                        }
                                                    });
                                        }
                                        Calendar myCalendar = Calendar.getInstance();
                                        String myFormat = "yyyy/MM/dd"; //In which you need put here
                                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                        String dateString = sdf.format(myCalendar.getTime());
                                        SQLiteHelper sqlite = new SQLiteHelper(ExportDB.this);
                                        SQLiteDatabase database = sqlite.getWritableDatabase();
                                        ContentValues values = new ContentValues();
                                        values.put("folder_id", String.valueOf(googleDriveFileHolder.getId()));
                                        values.put("created_date", dateString);
                                        database.insert("dd_drive", null, values);
                                        sqlite.close();
                                        database.close();
                                        folder_id = String.valueOf(googleDriveFileHolder.getId());
                                        exportDB_set();
                                        Log.i("Bitmap1", "Successfully Uploaded. File Id :" + googleDriveFileHolder.getId());
                                        Toast.makeText(ExportDB.this, "Successfully Uploaded Drive ", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progre();
                                        Log.i("Bitmap22", "Failed to Upload. File Id :" + e.getMessage());
                                        Toast.makeText(ExportDB.this, "Failed to Upload ", Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }*/

                }
            }
        });
        dialog.show();

    }

    //navv() - intent to splash
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void navv(){
        progre();
        String ddo = getString(R.string.downloadcom);
        Toast.makeText(getBaseContext(), ddo, Toast.LENGTH_LONG)
                .show();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.putInt("isloginn",0);
        editor.commit();
        Intent qw = new Intent (ExportDB.this,Splash.class);
        startActivity(qw);
    }

    //checkForGooglePermissions() - Check whether gmail is signed in
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void checkForGooglePermissions() {

        if (!GoogleSignIn.hasPermissions(
                GoogleSignIn.getLastSignedInAccount(getApplicationContext()),
                ACCESS_DRIVE_SCOPE,
                SCOPE_EMAIL,
                SCOPE_prfoirl,
                SCOPE_openid)) {
            progre();
            GoogleSignIn.requestPermissions(
                    ExportDB.this,
                    RC_AUTHORIZE_DRIVE,
                    GoogleSignIn.getLastSignedInAccount(getApplicationContext()),
                    ACCESS_DRIVE_SCOPE,
                    SCOPE_EMAIL ,
                    SCOPE_prfoirl,
                    SCOPE_openid);
        } else {
            Toast.makeText(this, "Permission to access Drive and Email has been granted1", Toast.LENGTH_SHORT).show();
            driveSetUp();

        }

    }

    //checkForGooglePermissions1() - Check whether gmail is signed in
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void checkForGooglePermissions1() {

        if (!GoogleSignIn.hasPermissions(
                GoogleSignIn.getLastSignedInAccount(getApplicationContext()),
                ACCESS_DRIVE_SCOPE,
                SCOPE_EMAIL,
                SCOPE_prfoirl,
                SCOPE_openid)) {
            progre();
            GoogleSignIn.requestPermissions(
                    ExportDB.this,
                    RC_AUTHORIZE_DRIVE,
                    GoogleSignIn.getLastSignedInAccount(getApplicationContext()),
                    ACCESS_DRIVE_SCOPE,
                    SCOPE_EMAIL ,
                    SCOPE_prfoirl,
                    SCOPE_openid);
        } else {
            Toast.makeText(this, "Permission to access Drive and Email has been granted", Toast.LENGTH_SHORT).show();

            driveSetUp1();
        }

    }

    //driveSetUp() - Setup driver
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void driveSetUp() {

        GoogleSignInAccount mAccount = GoogleSignIn.getLastSignedInAccount(ExportDB.this);
        email = mAccount.getEmail();
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("drive_mail",email);
        editor.apply();
        GoogleAccountCredential credential =
                GoogleAccountCredential.usingOAuth2(
                        getApplicationContext(), Collections.singleton(Scopes.DRIVE_FILE));
        credential.setSelectedAccount(mAccount.getAccount());
        googleDriveService = new Drive.Builder(
                AndroidHttp.newCompatibleTransport(),
                new GsonFactory(),
                credential)
                .setApplicationName("Dailydiary")
                .build();
        mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
//        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences pref_folder = getApplicationContext().getSharedPreferences("folder", 0); // 0 - for private mode
        final String foll = pref_folder.getString("folder_name","DailyDiary backups");
        progressbar_load();
        get_folder(foll);
//        filename_popup();



//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//        String myFormat1 = "dd/MM/yyyy";//In which you need put here
//        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
////        todaaaa = sdf1.format(c.getTime());
//        DateFormat dff = new SimpleDateFormat("MMM_d_yyyy_hh_mm_aaa");
//        todaaaa = dff.format(c.getInstance().getTime());
//        File Directory = new File("/sdcard/DailyDiary/");
//        Directory.mkdirs();
//        String outFileName = Environment.getExternalStorageDirectory()+"/DailyDiary/"+"/DD_RPP"+"_"+"comname"+"-"+todaaaa+"_Backup_Mail";
////                String outFileName = Environment.getExternalStorageDirectory()+"/Backup";
////                String outFileName = Environment.getExternalStorageDirectory()+"/LOGIN";
//        String inFileName = getApplicationContext().getDatabasePath("LOGIN").getPath();
//        File backupDB = new File( outFileName);
//        mDriveServiceHelper.downloadFile(backupDB, "1W5Ll_r_8TMs0JRtdmUY9XynJpKXl8wZw")
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        progre();
//                        Toast.makeText(Settings_activity.this, "Successfully Downloaded to DailyDiary Folder ", Toast.LENGTH_SHORT).show();
//                    }
//
//        })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        progre();
//                        Toast.makeText(Settings_activity.this, "Failed to Upload ", Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    //driveSetUp1() - Setup driver
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void driveSetUp1() {

        GoogleSignInAccount mAccount = GoogleSignIn.getLastSignedInAccount(ExportDB.this);
        email = mAccount.getEmail();
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("drive_mail",email);
        editor.apply();
        GoogleAccountCredential credential =
                GoogleAccountCredential.usingOAuth2(
                        getApplicationContext(), Collections.singleton(Scopes.DRIVE_FILE));
        credential.setSelectedAccount(mAccount.getAccount());
        googleDriveService = new Drive.Builder(
                AndroidHttp.newCompatibleTransport(),
                new GsonFactory(),
                credential)
                .setApplicationName("Dailydiary")
                .build();
        mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
        get_files();



    }

    //exportDB_set() - Export DB
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void exportDB_set() {
        // TODO Auto-generated method stub
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        final String comname = pref.getString("NAME","");
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                Log.d("importttt_db","expoooo11");
                String path = Environment.getDataDirectory().getAbsolutePath().toString() + "/storage/emulated/0/appFolder";
                File mFolder = new File(path);
                if (!mFolder.exists()) {
                    mFolder.mkdir();
                }
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                String myFormat1 = "dd/MM/yyyy";//In which you need put here
                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
//        todaaaa = sdf1.format(c.getTime());
                DateFormat dff = new SimpleDateFormat("MMM_d_yyyy");
                todaaaa = dff.format(c.getInstance().getTime());
                File Directory = new File("/sdcard/DailyDiary_downloads/");
                Directory.mkdirs();
                String outFileName = Environment.getExternalStorageDirectory()+"/DailyDiary_downloads/"+"/DD_RPP"+"_"+comname+"-"+todaaaa+"_Backup_Mail";
//                String outFileName = Environment.getExternalStorageDirectory()+"/Backup";
//                String outFileName = Environment.getExternalStorageDirectory()+"/LOGIN";
                String inFileName = getApplicationContext().getDatabasePath("LOGIN").getPath();
                final String currentDBPath ="/data/user/0/com.rampit.rask3.dailydiary/databases/LOGIN";
//                String  currentDBPath= "/data/" +"//user//" +"//0//" + "com.rampit.rask3.dailydiary"
//                        + "//databases//" + "LOGIN";
                String backupDBPath  = "/BackupFolder/LOGIN";
                File currentDB = new File( inFileName);
                File backupDB = new File( outFileName);
                SQLiteHelper sql = new SQLiteHelper(ExportDB.this);
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                try {
                    dst.transferFrom(src, 0, src.size());

                } finally {
                    try {
                        src.close();
                        dst.close();
                        sql.close();
//                        Toast.makeText(getBaseContext(), backupDB.toString()+""+"back",
//                                Toast.LENGTH_LONG).show();
//                        popup("2");

                        mDriveServiceHelper.uploadFile(backupDB, "*/*", folder_id)
                                .addOnSuccessListener(new OnSuccessListener<GoogleDriveFileHolder>() {
                                    @Override
                                    public void onSuccess(GoogleDriveFileHolder googleDriveFileHolder) {
//                                        progre();
                                        FirebaseApp.initializeApp(ExportDB.this);
                                        FirebaseDatabase database111 = FirebaseDatabase.getInstance();
                                        final DatabaseReference myRef11 = database111.getReference("dd_files");
                                        String mGroupId = myRef11.push().getKey();
                                        Log.d("mGroupId",String.valueOf(mGroupId));
                                        myRef11.child(mGroupId).child("id").setValue(mGroupId);
                                        Calendar myCalendar = Calendar.getInstance();
                                        String myFormat = "yyyy/MM/dd"; //In which you need put here
                                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                        String dateString = sdf.format(myCalendar.getTime());
                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                                        Map<String, Object> city11 = new HashMap<>();
                                        city11.put("id", license);
                                        city11.put("email", email);
                                        city11.put("folder_id",folder_id);
                                        city11.put("file_name","DD_RPP"+"_"+comname+"-"+todaaaa+"_Backup_Mail");
                                        city11.put("file_id", String.valueOf(googleDriveFileHolder.getId()));
                                        city11.put("created_date", dateString);
                                        city11.put("updated_date", "");
                                        db.collection("dd_files").document(String.valueOf(mGroupId))
                                                .set(city11)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("ggg", "DocumentSnapshot successfully written!");

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w("ggg", "Error writing document", e);
                                                    }
                                                });
//                                        Calendar myCalendar = Calendar.getInstance();
//                                        String myFormat = "yyyy/MM/dd"; //In which you need put here
//                                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//                                        String dateString = sdf.format(myCalendar.getTime());
                                        SQLiteHelper sqlite = new SQLiteHelper(ExportDB.this);
                                        SQLiteDatabase database = sqlite.getWritableDatabase();
                                        ContentValues values = new ContentValues();
                                        values.put("file_id", String.valueOf(googleDriveFileHolder.getId()));
                                        values.put("created_date", dateString);
//                                        database.insert("dd_drive", null, values);
                                        database.update("dd_drive", values, "id = ?", new String[]{"1"});
                                        sqlite.close();
                                        database.close();
                                        progre();
                                        final SharedPreferences prefs1 = getApplicationContext().getSharedPreferences("drive", 0); // 0 - for private mode
                                        SharedPreferences.Editor editor = prefs1.edit();
                                        Calendar c = Calendar.getInstance();
                                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                        String formattedDate = df.format(c.getTime());
                                        editor.putString("last_upload",formattedDate);
                                        editor.apply();
                                        textView21.setVisibility(View.VISIBLE);
                                        textView31.setVisibility(View.VISIBLE);
                                        textView21.setText(getString(R.string.backup_status)+getString(R.string.uploaded_successfully));
                                        textView31.setText(getString(R.string.updated_time)+formattedDate);
                                        Log.i("Bitmap1", "Successfully Uploaded. File Id :" + googleDriveFileHolder.getId());
                                        Toast.makeText(ExportDB.this, "Successfully Uploaded Drive ", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progre();
                                        Log.i("Bitmap2", "Failed to Upload. File Id :" + e.getMessage());
                                        Toast.makeText(ExportDB.this, "Failed to Upload ", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    } catch (IOException e) {
                        Log.e("Exception", "File write failed: " + e.toString());
                    }
                }

            }
        } catch (Exception e) {

//            Toast.makeText(getBaseContext(), e.toString()+""+"impo", Toast.LENGTH_LONG)
//                    .show();

        }
    }

    //get_files() - get files for email
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void get_files(){
        progressbar_load();
        files_ids.clear();
        Log.d("file_id",email);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
//        DocumentReference user = db.collection("PhoneBook").document("Contacts");
        CollectionReference citiesRef = db.collection("dd_files");
        citiesRef.whereEqualTo("email",email)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    QuerySnapshot document1 = task.getResult();
                    Integer cco = document1.size();
                    Integer vva = 0;
                    String tot = String.valueOf(cco);

                    if(cco<=0){
                        progre();
                        AlertDialog.Builder alertbox = new AlertDialog.Builder(ExportDB.this,R.style.AlertDialogTheme);
                        String enn = getString(R.string.no_backup);
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
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            list.add(document.getId());
//                            Long id1 = document.getLong("id");
//                            Long user_id1 = document.getLong("user_id");
//                            Long user_id11 = document.getLong("admin_id");
                            String id =document.getString("id");
                            String uid = document.getString("file_id");
                            String file_name = document.getString("file_name");
                            String uid1 = document.getString("folder_id");
                            files_ids.add(uid+",,,"+uid1+",,,"+file_name+",,,"+id);
                            Log.d("file_id",uid+" "+uid1+" "+email);
                            vva = vva + 1 ;
                            if(cco.equals(vva)){
                                progre();
                                select_language();
                            }

                        }


                    }

                } else {
                    Log.d("gettingids", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    //select_language() - Select files to Sync
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void select_language(){
//        final Dialog dialog = new Dialog(Register.this,R.style.CustomDialog);
        //set layout custom
//        Log.d("district",String.valueOf(disss1));
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.files_popup);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        Files_adapter mAdapter;
        RecyclerView recyclerView;
        Button submit,closee,select_all;

        recyclerView = dialog.findViewById(R.id.files_recycle);
        closee = dialog.findViewById(R.id.close);
        mAdapter = new Files_adapter(getApplicationContext(),files_ids,ExportDB.this,"lann",mDriveServiceHelper);
        mAdapter.setHasStableIds(true);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemViewCacheSize(1000);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter.onAttachedToRecyclerView(recyclerView);
        LinearLayoutManager layoutManager= new LinearLayoutManager(ExportDB.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        closee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //download_sync_drive() - Download and sync to app
    //Params - file id and DriveServiceHelper
    //Created on 25/01/2022
    //Return - NULL
    public void download_sync_drive(String file_id,DriveServiceHelper mDriveServiceHelper){
        progressbar_load();
        if(file_id == null || file_id.equalsIgnoreCase("") || file_id.equalsIgnoreCase("null")){
            progre();
            AlertDialog.Builder alertbox = new AlertDialog.Builder(ExportDB.this,R.style.AlertDialogTheme);
            String enn = getString(R.string.no_backup);
            String war = getString(R.string.warning);
            String ook = getString(R.string.ok);
            alertbox.setMessage(enn);
            alertbox.setTitle(war);
            alertbox.setIcon(R.drawable.dailylogo);
            alertbox.setNeutralButton(ook,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {
                        }
                    });
            alertbox.show();
        }
        else{
            Log.d("filessss_id",file_id);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            String myFormat1 = "dd/MM/yyyy";//In which you need put here
            SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
//        todaaaa = sdf1.format(c.getTime());
            DateFormat dff = new SimpleDateFormat("MMM_d_yyyy_hh_mm_aaa");
            todaaaa = dff.format(c.getInstance().getTime());
            File Directory = new File("/sdcard/DailyDiary_downloads/");
            Directory.mkdirs();
            final String outFileName = Environment.getExternalStorageDirectory()+"/DailyDiary_downloads/"+"/DD_RPP"+"_"+comname+"-"+todaaaa+"_Backup_Mail";
//                String outFileName = Environment.getExternalStorageDirectory()+"/Backup";
//                String outFileName = Environment.getExternalStorageDirectory()+"/LOGIN";
            final String inFileName = getApplicationContext().getDatabasePath("LOGIN").getPath();
            final File backupDB = new File( outFileName);
            mDriveServiceHelper.downloadFile(backupDB, file_id)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progre();
                            importDB_set(outFileName, inFileName);
                            Toast.makeText(ExportDB.this, "Successfully Downloaded to DailyDiary Folder ", Toast.LENGTH_SHORT).show();
                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progre();
                            Toast.makeText(ExportDB.this, "Failed to Upload ", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    //onActivityResult() - get files from external storage
    //Params - filled by android itself
    //Created on 25/01/2022
    //Return - NULL
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
//        switch(requestCode){

//            case 7:
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
//                    String path = null;
            Uri uri = data.getData();
//                    if (Build.VERSION.SDK_INT < 11){
//                        path = RealPathUtils.getRealPathFromURI_BelowAPI11(ExportDB.this, uri);
//                    Toast.makeText(ExportDB.this, path, Toast.LENGTH_LONG).show();
//                }
//                        // SDK >= 11 && SDK < 19
//                    else if (Build.VERSION.SDK_INT < 19){
//                        path = RealPathUtils.getRealPathFromURI_API11to18(ExportDB.this, uri);
//                    Toast.makeText(ExportDB.this, path, Toast.LENGTH_LONG).show();
//                }
//                        // SDK > 19 (Android 4.4)
//                    else {
//                        path = RealPathUtils.getRealPathFromURI_API19(ExportDB.this, uri);
//                        Toast.makeText(ExportDB.this, path, Toast.LENGTH_LONG).show();
//                    }


//                    Log.d(TAG, "File Path: " + path);
            // Get the file instance
            Log.d("rtrt", String.valueOf(uri));
//                    if(Build.a)
            final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(getApplicationContext(), uri)) {
//            / MediaProvider
                Log.d("popoop","090");
//                        if (isMediaDocument(uri)) {
//                            Log.d("popoop1","090");
                if (isGoogleDriveUri(uri)) {
                    getDriveFilePath(uri, getApplicationContext());
                }else{
                    if (Build.VERSION.SDK_INT < 11) {
//                        realPath = FileUtils1.getRealPathFromURI_BelowAPI11(context, fileUri);
                        originalFile = new File(FileUtils1.getRealPathFromURI_BelowAPI11(this, uri));
                        Log.d("rtrt3", String.valueOf(originalFile));
                    }
                    // SDK >= 11 && SDK < 19
                    else if (Build.VERSION.SDK_INT < 19) {
                        originalFile = new File(FileUtils1.getRealPathFromURI_API11to18(this, uri));
//                        realPath = FileUtils1.getRealPathFromURI_API11to18(context, fileUri);
                        Log.d("rtrt2", String.valueOf(originalFile));
                    }
                    // SDK > 19 (Android 4.4) and up
                    else {
                        originalFile = new File(FileUtils1.getRealPathFromURI_API19(this, uri));
                        String uiu = String.valueOf(originalFile);
                        String numm = uiu.replace("/storage/emulated/0/LOGIN", "LOGIN");
                        Log.d("rtrt1", String.valueOf(numm));
                        Log.d("rtrt1", String.valueOf(originalFile));
//                        realPath = FileUtils1.getRealPathFromURI_API19(context, fileUri);
                    }
                }
//                        }
            }
            else {


                if (Build.VERSION.SDK_INT < 11) {
//                        realPath = FileUtils1.getRealPathFromURI_BelowAPI11(context, fileUri);
                    originalFile = new File(FileUtils1.getRealPathFromURI_BelowAPI11(this, uri));
                    Log.d("rtrt3", String.valueOf(originalFile));
                }
                // SDK >= 11 && SDK < 19
                else if (Build.VERSION.SDK_INT < 19) {
                    originalFile = new File(FileUtils1.getRealPathFromURI_API11to18(this, uri));
//                        realPath = FileUtils1.getRealPathFromURI_API11to18(context, fileUri);
                    Log.d("rtrt2", String.valueOf(originalFile));
                }
                // SDK > 19 (Android 4.4) and up
                else {
                    originalFile = new File(FileUtils1.getRealPathFromURI_API19(this, uri));
                    String uiu = String.valueOf(originalFile);
                    String numm = uiu.replace("/storage/emulated/0/LOGIN", "LOGIN");
                    Log.d("rtrt1", String.valueOf(numm));
                    Log.d("rtrt1", String.valueOf(originalFile));
//                        realPath = FileUtils1.getRealPathFromURI_API19(context, fileUri);
                }
            }
//    File file = new File(getRealPathFromURI1(uri));

            String outFileName = Environment.getExternalStorageDirectory() + "/LOGIN";
//                    String PathHolder = data.getData().getPath();
            inFileName = getApplicationContext().getDatabasePath("LOGIN").getPath();

//
//                    Uri uri1 = data.getData();
//                    File file = new File(uri1.toString());
//                    Uri uri = FileProvider.getUriForFile(ExportDB.this,
//                            BuildConfig.APPLICATION_ID + ".provider",
//                            file);
//                    File myFile = new File(uri.toString());
            String path = originalFile.toString();
            if(path.matches("com.google.android.apps.docs.storage")){
                Log.d("oiopi",path);
            }
            else{
                importDB(path, inFileName);
            }
//    String path1 = file.toString();
            //                    try {
////                        sqlite.importDatabase(outFileName,inFileName,getApplicationContext());
////                        sqlite.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Toast.makeText(ExportDB.this, path , Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getBaseContext(), "back`11",
                    Toast.LENGTH_LONG).show();
        }
//        }
    }
    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case 7:
//                if (resultCode == RESULT_OK) {
//                    // Get the Uri of the selected file
//                    Uri uri = data.getData();
//                    String uriString = uri.toString();
//                    File myFile = new File(uriString);
//                    String path = myFile.getAbsolutePath();
//                    String displayName = null;
//
//                    if (uriString.startsWith("content://")) {
//                        Cursor cursor = null;
//                        try {
//                            cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
//                            if (cursor != null && cursor.moveToFirst()) {
//                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                                Toast.makeText(ExportDB.this, displayName, Toast.LENGTH_LONG).show();
//                            }
//                        } finally {
//                            cursor.close();
//                        }
//
//                    } else if (uriString.startsWith("file://")) {
//                        displayName = myFile.getName();
//                        Toast.makeText(ExportDB.this, displayName , Toast.LENGTH_LONG).show();
//                    }
//                }
//                break;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
    //isGoogleDriveUri() - check whether file is from drive
    //Params - file uri
    //Created on 25/01/2022
    //Return - NULL
    private static boolean isGoogleDriveUri(Uri uri) {
        return "com.google.android.apps.docs.storage".equals(uri.getAuthority()) || "com.google.android.apps.docs.storage.legacy".equals(uri.getAuthority());
    }

    //getDriveFilePath() - get exact path file from drive
    //Params - file uri and application context
    //Created on 25/01/2022
    //Return - NULL
    private  void getDriveFilePath(Uri uri, Context context) {
        Uri returnUri = uri;
        Cursor returnCursor = context.getContentResolver().query(returnUri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
        File file = new File(context.getCacheDir(), name);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();

            //int bufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            Log.e("File Size", "Size " + file.length());
            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getPath());
            Log.e("File Size", "Size " + file.length());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        originalFile = file;
        inFileName = String.valueOf(file.getPath());
        Log.d("p090", inFileName);
//        return file.getPath();
    }
    private String getRealPathFromURI1(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
//    Toast.makeText(ExportDB.this, result , Toast.LENGTH_LONG).show();
        return result;
    }
    private String getFilePath(Context cntx, Uri uri) {
        Cursor cursor = null;
        try {
            String[] arr = { MediaStore.Images.Media.DATA };
            cursor = cntx.getContentResolver().query(uri,  arr, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        Log.d("ress",result);
        return result;
    }

    //importDB() - import selected backup to app
    //Params - output and input location
    //Created on 25/01/2022
    //Return - NULL
    private void importDB(String outFileName,String inFileName) {
        // TODO Auto-generated method stub
        ((Callback)getApplication()).stop_t();
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
//                final String currentDBPath ="/data/user/0/com.rampit.rask3.dailydiary/databases/LOGIN";
//                String  currentDBPath= "//user//" +"//0//" + "com.rampit.rask3.dailydiary"
//                        + "//databases//" + "LOGIN";
//                String backupDBPath  = "/BackupFolder/LOGIN";
//                File  backupDB= new File(data, currentDBPath);
//                File currentDB  = new File(sd, backupDBPath);
//                SQLiteHelper sql = new SQLiteHelper(ExportDB.this);
                File currentDB = new File(outFileName);
                File backupDB = new File(inFileName);
                Log.d("rtrtrtr",String.valueOf(currentDB));
                Log.d("rtrtrtr",String.valueOf(backupDB));

                SQLiteHelper sql = new SQLiteHelper(ExportDB.this);

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
                        Log.d("trr1","yyy");
                        if (android.os.Build.VERSION.SDK_INT <= 23) {
                            progre();
//                            popup1("1");
                            runOnUiThread(new Runnable() {
                                @SuppressLint("RestrictedApi")
                                @Override
                                public void run() {
                                    String ddo = getString(R.string.downloadcom);
                                    Toast.makeText(getBaseContext(), ddo, Toast.LENGTH_LONG)
                                            .show();
                                    final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.clear();
                                    editor.putInt("isloginn",0);
                                    editor.commit();
                                    Intent qw = new Intent (ExportDB.this,Login.class);
                                    startActivity(qw);

                                }
                            });


                        }else{
                            progre();
//                            popup("1");
                            runOnUiThread(new Runnable() {
                                @SuppressLint("RestrictedApi")
                                @Override
                                public void run() {
                                    String ddo = getString(R.string.downloadcom);
                                    Toast.makeText(getBaseContext(), ddo, Toast.LENGTH_LONG)
                                            .show();
                                    final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.clear();
                                    editor.putInt("isloginn",0);
                                    editor.commit();
                                    Intent qw = new Intent (ExportDB.this,Login.class);
                                    startActivity(qw);

                                }
                            });

                        }
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

    //gif() - load GIF
    //Params - Dialog and type of dialog
    //Created on 25/01/2022
    //Return - NULL
    public void gif(final Dialog dialog, final String aa){
        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {

                dialog.dismiss();
                if(aa.equalsIgnoreCase("1")){
                    String ddo = getString(R.string.downloadcom);
                    Toast.makeText(getBaseContext(), ddo, Toast.LENGTH_LONG)
                            .show();
                    final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear();
                    editor.putInt("isloginn",0);
                    editor.commit();
                    Intent qw = new Intent (ExportDB.this,Login.class);
                    startActivity(qw);
                }else if(aa.equalsIgnoreCase("2")){
                    String ddo = getString(R.string.uploadcom);
                    Toast.makeText(getBaseContext(), ddo, Toast.LENGTH_LONG)
                            .show();

                }
            }
        }, 5000);


    }

    //popup() - show popup
    //Params - type of dialog
    //Created on 25/01/2022
    //Return - NULL
    public void popup(String aa){
        Log.d("popup1","ssss");
        final Dialog dialog = new Dialog(ExportDB.this);
        //set layout custom
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.gifpop);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        PlayGifView pGif = (PlayGifView) dialog.findViewById(R.id.viewGif);
        pGif.setImageResource(R.drawable.loader);
        TextView loadd = (TextView) dialog.findViewById(R.id.loading);
        gif(dialog,aa);
        dialog.show();

    }

    //popup1() - show popup
    //Params - type of dialog
    //Created on 25/01/2022
    //Return - NULL
    public void popup1(String aa){

        Log.d("popup111","ssss");
        progressDialog = ProgressDialog.show(ExportDB.this,"Loading","Please wait...",false,false);
        gif(progressDialog,aa);
        progressDialog.show();

    }
    static void exportDB1(){
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        String currentDBPath = "/data/"+ "com.rampit.rask3.dailydiary" +"/databases/LOGIN";
        String backupDBPath = "LOGIN.db";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
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
    }
    static void importDB1(){
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        String currentDBPath = "/data/"+ "com.rampit.rask3.dailydiary" +"/databases/LOGIN";
        String backupDBPath = "LOGIN.db";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(backupDB).getChannel();
            destination = new FileOutputStream(currentDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
//            Toast.makeText(ExportDB.this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch(IOException e) {
            e.printStackTrace();
//            Toast.makeText(this, "Exported Failed!", Toast.LENGTH_LONG).show();
        }
    }
    //exporting database
    //exportDB() - Export DB
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void exportDB() {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                Log.d("importttt","expoooo11");
                String path = Environment.getDataDirectory().getAbsolutePath().toString() + "/storage/emulated/0/appFolder";
                File mFolder = new File(path);
                if (!mFolder.exists()) {
                    mFolder.mkdir();
                }
                File Directory = new File("/sdcard/DailyDiary_downloads/");
                Directory.mkdirs();
                String outFileName = Environment.getExternalStorageDirectory()+"/DailyDiary_downloads/"+"/DD_RPP"+"_"+comname+"-"+todaaaa+"_Backup_Mail";
//                String outFileName = Environment.getExternalStorageDirectory()+"/Backup";
//                String outFileName = Environment.getExternalStorageDirectory()+"/LOGIN";
                String inFileName = getApplicationContext().getDatabasePath("LOGIN").getPath();
                final String currentDBPath ="/data/user/0/com.rampit.rask3.dailydiary/databases/LOGIN";
//                String  currentDBPath= "/data/" +"//user//" +"//0//" + "com.rampit.rask3.dailydiary"
//                        + "//databases//" + "LOGIN";
                String backupDBPath  = "/BackupFolder/LOGIN";
                File currentDB = new File( inFileName);
                File backupDB = new File( outFileName);
                SQLiteHelper sql = new SQLiteHelper(ExportDB.this);
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                try {
                    dst.transferFrom(src, 0, src.size());

                } finally {
                    try {
                        src.close();
                        dst.close();
                        sql.close();
//                        Toast.makeText(getBaseContext(), backupDB.toString()+""+"back",
//                                Toast.LENGTH_LONG).show();
//                        popup("2");
                        if (android.os.Build.VERSION.SDK_INT <= 23) {
//                            popup1("2");
                            runOnUiThread(new Runnable() {
                                @SuppressLint("RestrictedApi")
                                @Override
                                public void run() {
                                    String ddo = getString(R.string.uploadcom);
                                    Toast.makeText(getBaseContext(), ddo, Toast.LENGTH_LONG)
                                            .show();

                                }
                            });

                        }else{
//                            popup("2");
                            runOnUiThread(new Runnable() {
                                @SuppressLint("RestrictedApi")
                                @Override
                                public void run() {
                                    String ddo = getString(R.string.uploadcom);
                                    Toast.makeText(getBaseContext(), ddo, Toast.LENGTH_LONG)
                                            .show();

                                }
                            });
                        }
                    } catch (IOException e) {
                        Log.e("Exception", "File write failed: " + e.toString());
                    }
                }

            }
        } catch (Exception e) {

//            Toast.makeText(getBaseContext(), e.toString()+""+"impo", Toast.LENGTH_LONG)
//                    .show();

        }
    }
    //onCreateOptionsMenu() - when navigation menu created
    //Params - Menu
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.back_button, menu);

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
        if (id == R.id.action_back) {
            Intent back = new Intent(ExportDB.this,Settings_activity.class);
            startActivity(back);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

