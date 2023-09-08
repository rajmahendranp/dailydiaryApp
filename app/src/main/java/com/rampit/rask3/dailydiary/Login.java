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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;


//Updated on 25/01/2022 by RAMPIT
//used to Login users
//onStart() - Called when activity is started
//onResume() - Called when activity is resumed
//onRestart() - Called when activity is restarted
//progressbar_load() - Load progressbar
//progre() - Stop progressbar
//lastlogin1() - Upload file to firebase
//lastlogin11() -  Upload file to firebase
//getDateDiff() - get days difference between two dates
//lastlogin() - put login time
//userpass() - get admin username and password
//email() - Send email backup
//populateRecyclerView() - get license
//backupDatabase() - upload backup to external storage
//backupDatabase1() - upload backup to external storage
//daily_firebase() - upload backup to firebase in daily
//weekly_firebase() - upload backup to firebase in weekly
//monthly_firebase() - upload backup to firebase in monthly
//daily_firebase() - upload backup to firebase in daily
//weekly_firebase() - upload backup to firebase in weekly
//monthly_firebase() - upload backup to firebase in monthly
//sendEmail1() - Send USer name and password to email
//monthly_firebase() - upload backup to Email
//onBackPressed() - function to be done when back button is pressed
//languaaa() - Check for SQlite DB updates
//onRequestPermissionsResult() - Request user permissions
//InternetConnection() - Check whether internet is on or not
//ssss() - On internet in settings page
//expiiii() - get user license
//call_me() - get license expiry date from sever
//call_me1() - get license expiry date from sever


public class Login extends AppCompatActivity {

    private static final int RC_AUTHORIZE_DRIVE = 100;
    private static final int REQUEST_PICK_IMAGE = 1;
    private static final int REQUEST_PICK_IMAGE1 = 1;
    EditText user, pass;
    Button log, rese;
    TextView forgo;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    String USER, PASS, OTP, ID, fuser, fpass, fid, emailid,License;
    Cursor cursor;
    Integer loged, oldv, newv ,oldv1, newv1;
    private PopupWindow mPopupWindow;
    static String llll, device_id, eer, actsta, device_id1, expiii ,subscr_id;
    private static final int REQUEST_READ_PHONE_STATE = 1;
    ImageButton eyes;
    static Integer yy ;
    String valu,todaaaa,pdfname,pdfname1;
    File pdfFile;
    Spinner spinner;
    Dialog dialog;
    String lann ,license , loading = "0";
    Scope ACCESS_DRIVE_SCOPE = new Scope(Scopes.DRIVE_FILE);
    Scope SCOPE_EMAIL = new Scope(Scopes.EMAIL);
    Scope SCOPE_prfoirl = new Scope(Scopes.PROFILE);
    Scope SCOPE_openid = new Scope(Scopes.OPEN_ID);
    DriveServiceHelper mDriveServiceHelper;
    Drive googleDriveService;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ApplySharedPref")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ii = pref.getString("theme", "");
        if (ii.equalsIgnoreCase("")) {
            ii = "1";
        }
        Log.d("theelo", ii);
        Integer in = Integer.valueOf(ii);
        if (in == 0) {
            Log.d("theelo", "thh");
            setTheme(R.style.AppTheme1);
        } else {
            Log.d("theelo", "th1h");
            setTheme(R.style.AppTheme11);
//            recreate();
        }
        String localeName = pref.getString("language", "");
        if (localeName == null) {
            localeName = "ta";
        }
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
            //TODO
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!Settings.canDrawOverlays(this)) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:" + getPackageName()));
//                startActivityForResult(intent, 1);
//
//            }
//        }

//        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
////
//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_READ_PHONE_STATE);
//        } else {
//            //TODO
//        }
        yy = 0;
        List<String> list = new ArrayList<String>();
        list.add("Select language");
        list.add("English");
        list.add("Français");
        list.add("Tamil");

//        lastlogin1();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        //    String myFormat1 = "dd/MM/yyyy";//In which you need put here
        String formattedDate = df.format(c.getTime());
        int dateDifference11 = (int) getDateDiff(new SimpleDateFormat("dd/MM/yyyy"), "10/02/2021", formattedDate);
        Log.d("date_d",String.valueOf(dateDifference11));
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        final String imeiNumber1 = tm.getDeviceId(0);
//        final String imeiNumber2 = tm.getDeviceId(1);
//        Log.d("deee1", imeiNumber1);
//        Log.d("deee2", imeiNumber2);
//        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Integer buildver = Build.VERSION.SDK_INT;
        Log.d("buildver",String.valueOf(buildver));
        if(buildver > 28){
            device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            Log.d("deee1",device_id);
//            Log.d("deee2",imeiNumber2);
        }else{
            device_id = tm.getDeviceId();
//            Log.d("deee1",imeiNumber1);
            Log.d("deee2",device_id);
        }
//        device_id = tm.getDeviceId();
//
//        device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
//        Log.d("deee",device_id);
        if(device_id == null || device_id.equalsIgnoreCase("")){
        }else{
            languaaa();
//            imei111();
        }
//        checkForGooglePermissions();
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dialog = new Dialog(Login.this,R.style.AlertDialogTheme);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);
        log = findViewById(R.id.login);
        rese = findViewById(R.id.reset);
        forgo = findViewById(R.id.forgot);
        eyes = findViewById(R.id.eyes1);
        if(in == 0){
            eyes.setBackgroundResource(R.drawable.eye_orange);
//            pass.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_orange, 0);
        }else{
            eyes.setBackgroundResource(R.drawable.eye_blue);
//            pass.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_blue, 0);
        }
        spinner = (Spinner) findViewById(R.id.spinner);
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
        lann = pref.getString("language","");
        if(lann == null || lann.equalsIgnoreCase("")){
            lann = "ta";
            Log.d("langu1",lann);
//            spinner.setSelection(3);
        }else if(lann.equalsIgnoreCase("en")){
            lann = "en";
            Log.d("langu",lann);
            spinner.setSelection(1);

        }else if(lann.equalsIgnoreCase("fr")){
            lann = "fr";
            Log.d("langu",lann);
            spinner.setSelection(2);
        }else if(lann.equalsIgnoreCase("ta")){
            lann = "ta";
            Log.d("langu",lann);
            spinner.setSelection(3);
        }
        spinner.setTag("null");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Integer nu = spinner
                        .getSelectedItemPosition();
                if(nu.equals(0)){

                }else{
                    if (spinner.getTag() != null) {
                        spinner.setTag(null);
                        Log.d("switch1",String.valueOf(nu));
                        return;
                    }else{
                        String typeid = String.valueOf(nu);
                        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        if(typeid.equalsIgnoreCase("1"))
                        {   lann = "en";
                            editor.putString("language",lann);
                            editor.apply();
                            Log.d("switch1",String.valueOf(lann));
                            spinner.setTag("null");
                            sqlite = new SQLiteHelper(Login.this);
                            database = sqlite.getWritableDatabase();
                            ContentValues values1 = new ContentValues();
                            values1.put("language", lann);
                            database.update("dd_settings", values1,"ID = ?", new String[]{"1"});
                            sqlite.close();
                            database.close();
                            Intent reg = new Intent(Login.this,Login.class);
                            startActivity(reg);
//                            recreate();
                        }else if(typeid.equalsIgnoreCase("2")){
                            lann = "fr";
                            editor.putString("language",lann);
                            editor.apply();
                            spinner.setTag("null");
                            sqlite = new SQLiteHelper(Login.this);
                            database = sqlite.getWritableDatabase();
                            ContentValues values1 = new ContentValues();
                            values1.put("language", lann);
                            database.update("dd_settings", values1,"ID = ?", new String[]{"1"});
                            sqlite.close();
                            database.close();
                            Log.d("switch1",String.valueOf(lann));
                            Intent reg = new Intent(Login.this,Login.class);
                            startActivity(reg);
//                            recreate();
                        }else if(typeid.equalsIgnoreCase("3")){
                            lann = "ta";
                            editor.putString("language",lann);
                            editor.apply();
                            Log.d("switch1",String.valueOf(lann));
                            spinner.setTag("null");
                            sqlite = new SQLiteHelper(Login.this);
                            database = sqlite.getWritableDatabase();
                            ContentValues values1 = new ContentValues();
                            values1.put("language", lann);
                            database.update("dd_settings", values1,"ID = ?", new String[]{"1"});
                            sqlite.close();
                            database.close();
                            Intent reg = new Intent(Login.this,Login.class);
                            startActivity(reg);
//                            recreate();
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        eyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String naam = pass.getText().toString();
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
//        if(in == 0){
//            user.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.search_orange, 0);
//            pass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.search_orange, 0);
//        }else{
//            user.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.search_blue, 0);
//            pass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.search_blue, 0);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            pass.setBackgroundTintList(ContextCompat.getColorStateList(Login.this, R.color.Gray));
//        }

        Intent login = getIntent();
        fuser = login.getStringExtra("user");
        fpass = login.getStringExtra("pass");
        valu = login.getStringExtra("value");
        if(valu == null || valu.equalsIgnoreCase("")){
            valu = "0";
        }
        final SharedPreferences.Editor editor = pref.edit();
        ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS}, 1);
        userpass();
//    sqlite = new SQLiteHelper(this);
//    database = sqlite.getWritableDatabase();
//        String use = pref.getString("user","");
//Log.d("username",use);
        forgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email();
            }
        });
        rese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setText("");
                pass.setText("");
//                uploadFile(view);
//                progressbar_load();
//                exportDB();

            }
        });
        if (Login.InternetConnection.checkConnection(getApplicationContext(),Login.this)) {
            expiiii();
            if(License.equalsIgnoreCase("no")||License.equalsIgnoreCase("")){
                actsta = "active";
            }else {
                llll = License;
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        //TODO your background code
                        try {
                            Login.call_me2();
                            if(eer.equalsIgnoreCase("success")){
                                Login.call_me();
//                                   expii1();
                            }else if(eer.equalsIgnoreCase("error")){
                                Login.call_me();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            // Internet Available...
            Log.d("internet","on");
//            date32();
        }
        else {
            // Internet Not Available...
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Login.this,R.style.AlertDialogTheme);
            String warr = getString(R.string.warning);
            String ok = getString(R.string.ok);
            String cancel = getString(R.string.cancel);
            alertbox.setMessage(R.string.on_internet);
            alertbox.setTitle(warr);
            alertbox.setIcon(R.drawable.dailylogo);
            alertbox.setCancelable(false);
            alertbox.setPositiveButton(ok,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {

                        }
                    });
            alertbox.show();

        }
        log.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                progressbar_load();
                final String username = user.getText().toString();
                final String password = pass.getText().toString();
                if(username.equalsIgnoreCase("") || password.equalsIgnoreCase("")){
                    backupDatabase1();
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(Login.this,R.style.AlertDialogTheme);
                    String enn = getString(R.string.fill_all);
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
                }else{

                }

                if(valu.equalsIgnoreCase("1")){

                }else{
                    if(actsta == null || actsta.equalsIgnoreCase("") || actsta.equalsIgnoreCase("null")){
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        if(actsta == null || actsta.equalsIgnoreCase("") || actsta.equalsIgnoreCase("null")){
                                            Log.d("activeeee","yesss");
                                        }else{
                                            if(actsta.equalsIgnoreCase("active")  ){
                                                Log.d("activeeee","yesss1");
//                            if(actsta.equalsIgnoreCase("active") && device_id1.equalsIgnoreCase(device_id)){
                                                if(License.equalsIgnoreCase("no")||License.equalsIgnoreCase("")){
                                                    SQLiteHelper sqlite = new SQLiteHelper(Login.this);
                                                    SQLiteDatabase database = sqlite.getWritableDatabase();
                                                    ContentValues values1 = new ContentValues();
                                                    values1.put("quick_bulkupdate", "1");
                                                    database.update("dd_user", values1,"ID = ?", new String[]{"1"});
                                                    sqlite.close();
                                                    database.close();
                                                    editor.putString("quickbulk", String.valueOf(1));
                                                    editor.commit();
                                                }
                                                else{
                                                    if(subscr_id == null || subscr_id.equalsIgnoreCase("") || subscr_id.equalsIgnoreCase("null") ||
                                                            subscr_id.equalsIgnoreCase("Dailydiary")){
                                                        SQLiteHelper sqlite = new SQLiteHelper(Login.this);
                                                        SQLiteDatabase database = sqlite.getWritableDatabase();
                                                        ContentValues values1 = new ContentValues();
                                                        values1.put("quick_bulkupdate", "0");
                                                        database.update("dd_user", values1,"ID = ?", new String[]{"1"});
                                                        sqlite.close();
                                                        database.close();
                                                        editor.putString("quickbulk", String.valueOf(0));
                                                        editor.commit();
                                                    }else if (  subscr_id.equalsIgnoreCase("quick_bulk_update")){
                                                        SQLiteHelper sqlite = new SQLiteHelper(Login.this);
                                                        SQLiteDatabase database = sqlite.getWritableDatabase();
                                                        ContentValues values1 = new ContentValues();
                                                        values1.put("quick_bulkupdate", "1");
                                                        database.update("dd_user", values1,"ID = ?", new String[]{"1"});
                                                        sqlite.close();
                                                        database.close();
                                                        editor.putString("quickbulk", String.valueOf(1));
                                                        editor.commit();
                                                    }else{
                                                        SQLiteHelper sqlite = new SQLiteHelper(Login.this);
                                                        SQLiteDatabase database = sqlite.getWritableDatabase();
                                                        ContentValues values1 = new ContentValues();
                                                        values1.put("quick_bulkupdate", "0");
                                                        database.update("dd_user", values1,"ID = ?", new String[]{"1"});
                                                        sqlite.close();
                                                        database.close();
                                                        editor.putString("quickbulk", String.valueOf(0));
                                                        editor.commit();
                                                    }
                                                }
                                                Log.d("user_pass",username+","+password);
                                                sqlite = new SQLiteHelper(Login.this);
                                                database = sqlite.getReadableDatabase();
                                                cursor = database.rawQuery("SELECT * FROM "+SQLiteHelper.TABLENAME12+" WHERE "+SQLiteHelper.USNAM+"=? AND "+SQLiteHelper.USPASS+" =?",new String[]{username,password});
                                                if (cursor != null) {
                                                    if (cursor.getCount() != 0) {
                                                        cursor.moveToFirst();
                                                        do {
                                                            int index;

                                                            index = cursor.getColumnIndexOrThrow("username");
                                                            USER = cursor.getString(index);
                                                            index = cursor.getColumnIndexOrThrow("password");
                                                            PASS = cursor.getString(index);
                                                            index = cursor.getColumnIndexOrThrow("otp");
                                                            OTP = cursor.getString(index);
                                                            index = cursor.getColumnIndexOrThrow("ID");
                                                            ID = cursor.getString(index);
//                                                            index = cursor.getColumnIndexOrThrow("quick_bulkupdate");
                                                            Integer quick_bulkupda = 0;
                                                            String quick_bulkupdate = "0";
                                                            if(quick_bulkupdate == null ||quick_bulkupdate.equalsIgnoreCase("")){
                                                                quick_bulkupda = 0;
                                                            }

                                                            index = cursor.getColumnIndexOrThrow("backup_privilege");
                                                            Integer backpri = cursor.getInt(index);
                                                            String bbackp = cursor.getString(index);
                                                            if(bbackp == null ||bbackp.equalsIgnoreCase("")){
                                                                backpri = 1;
                                                            }
                                                            if(OTP == null || OTP.equalsIgnoreCase("")){
                                                                editor.putString("user",USER);
                                                                editor.putString("pass",PASS);
                                                                editor.putString("otp",OTP);
                                                                editor.putString("id",ID);
                                                                editor.putString("backpri", String.valueOf(backpri));
//                                                                editor.putString("quickbulk", String.valueOf(quick_bulkupda));
                                                                editor.putInt("isloginn",1);
                                                                editor.commit();
                                                                Toast.makeText(getApplicationContext(),"Login successfull",Toast.LENGTH_SHORT).show();
//                                    cursor.close();
//                                    sqlite.close();
//                                    database.close();
                                                                lastlogin();
                                                                Intent login = new Intent(Login.this,Enterotp.class);
                                                                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                                                                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                                                                login.putExtra("user",USER);
                                                                login.putExtra("pass",PASS);
                                                                login.putExtra("otp",OTP);
                                                                login.putExtra("ID",ID);
                                                                login.putExtra("vaa","1");
                                                                editor.putString("backpri", String.valueOf(backpri));
//                                                                editor.putString("quickbulk", String.valueOf(quick_bulkupda));
                                                                startActivity(login,bundle);

                                                            }
                                                            else{
                                                                editor.putString("user",USER);
                                                                editor.putString("pass",PASS);
                                                                editor.putString("otp",OTP);
                                                                editor.putString("id",ID);
                                                                editor.putString("backpri", String.valueOf(backpri));
//                                                                editor.putString("quickbulk", String.valueOf(quick_bulkupda));
                                                                editor.putInt("isloginn",1);
                                                                editor.commit();
                                                                Toast.makeText(getApplicationContext(),"Login successfull",Toast.LENGTH_SHORT).show();
//                                    cursor.close();
//                                    sqlite.close();
//                                    database.close();
                                                                lastlogin();
                                                                backupDatabase();
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
                                                        backupDatabase1();
                                                        AlertDialog.Builder alertbox = new AlertDialog.Builder(Login.this,R.style.AlertDialogTheme);
                                                        String enn = getString(R.string.usepassmis);
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
                                                }
                                                else{
                                                    cursor.close();
                                                    sqlite.close();
                                                    database.close();
                                                    Toast.makeText(getApplicationContext(),"Username or password is incorrect",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            else if(actsta.equalsIgnoreCase("blocked")){
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if(License.equalsIgnoreCase("no")||License.equalsIgnoreCase("")){
                                                            SQLiteHelper sqlite = new SQLiteHelper(Login.this);
                                                            SQLiteDatabase database = sqlite.getWritableDatabase();
                                                            ContentValues values1 = new ContentValues();
                                                            values1.put("quick_bulkupdate", "1");
                                                            database.update("dd_user", values1,"ID = ?", new String[]{"1"});
                                                            sqlite.close();
                                                            database.close();
                                                            editor.putString("quickbulk", String.valueOf(1));
                                                            editor.commit();
                                                        }else{
                                                            if(subscr_id == null || subscr_id.equalsIgnoreCase("") || subscr_id.equalsIgnoreCase("null") || subscr_id.equalsIgnoreCase("Dailydiary")){
                                                                SQLiteHelper sqlite = new SQLiteHelper(Login.this);
                                                                SQLiteDatabase database = sqlite.getWritableDatabase();
                                                                ContentValues values1 = new ContentValues();
                                                                values1.put("quick_bulkupdate", "0");
                                                                database.update("dd_user", values1,"ID = ?", new String[]{"1"});
                                                                sqlite.close();
                                                                database.close();
                                                                editor.putString("quickbulk", String.valueOf(0));
                                                                editor.commit();
                                                            }else if (  subscr_id.equalsIgnoreCase("quick_bulk_update")){
                                                                SQLiteHelper sqlite = new SQLiteHelper(Login.this);
                                                                SQLiteDatabase database = sqlite.getWritableDatabase();
                                                                ContentValues values1 = new ContentValues();
                                                                values1.put("quick_bulkupdate", "1");
                                                                database.update("dd_user", values1,"ID = ?", new String[]{"1"});
                                                                sqlite.close();
                                                                database.close();
                                                                editor.putString("quickbulk", String.valueOf(1));
                                                                editor.commit();
                                                            }else{
                                                                SQLiteHelper sqlite = new SQLiteHelper(Login.this);
                                                                SQLiteDatabase database = sqlite.getWritableDatabase();
                                                                ContentValues values1 = new ContentValues();
                                                                values1.put("quick_bulkupdate", "0");
                                                                database.update("dd_user", values1,"ID = ?", new String[]{"1"});
                                                                sqlite.close();
                                                                database.close();
                                                                editor.putString("quickbulk", String.valueOf(0));
                                                                editor.commit();
                                                            }
                                                        }
                                                        backupDatabase1();
                                                        AlertDialog.Builder alertbox = new AlertDialog.Builder(Login.this,R.style.AlertDialogTheme);
                                                        String rer = getString(R.string.keyblocked);
                                                        String rer1= getString(R.string.warning);
                                                        String rer2 = getString(R.string.ok);
                                                        alertbox.setMessage(rer);
                                                        alertbox.setTitle(rer1);
                                                        alertbox.setIcon(R.drawable.dailylogo);
                                                        alertbox.setNeutralButton(rer2,
                                                                new DialogInterface.OnClickListener() {

                                                                    public void onClick(DialogInterface arg0,
                                                                                        int arg1) {
                                                                        Intent logined = new Intent(Login.this,confirm_OTP.class);
                                                                        Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                                                                                android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                                                                        logined.putExtra("vaa","2");
                                                                        startActivity(logined,bundle);
                                                                    }
                                                                });
                                                        alertbox.show();
                                                    }
                                                });
                                            }
                                        }
                                    }
                                });
                            }
                        }, 5000);
                    }else{
                        if(actsta.equalsIgnoreCase("active")  ){
//                            if(actsta.equalsIgnoreCase("active") && device_id1.equalsIgnoreCase(device_id)){
                            if(License.equalsIgnoreCase("no")||License.equalsIgnoreCase("")){
                                SQLiteHelper sqlite = new SQLiteHelper(Login.this);
                                SQLiteDatabase database = sqlite.getWritableDatabase();
                                ContentValues values1 = new ContentValues();
                                values1.put("quick_bulkupdate", "1");
                                database.update("dd_user", values1,"ID = ?", new String[]{"1"});
                                sqlite.close();
                                database.close();
                                editor.putString("quickbulk", String.valueOf(1));
                                editor.commit();
                            }else{
                                if(subscr_id == null || subscr_id.equalsIgnoreCase("") || subscr_id.equalsIgnoreCase("null") || subscr_id.equalsIgnoreCase("Dailydiary")){
                                    SQLiteHelper sqlite = new SQLiteHelper(Login.this);
                                    SQLiteDatabase database = sqlite.getWritableDatabase();
                                    ContentValues values1 = new ContentValues();
                                    values1.put("quick_bulkupdate", "0");
                                    database.update("dd_user", values1,"ID = ?", new String[]{"1"});
                                    sqlite.close();
                                    database.close();
                                    editor.putString("quickbulk", String.valueOf(0));
                                    editor.commit();
                                }else if (  subscr_id.equalsIgnoreCase("quick_bulk_update")){
                                    SQLiteHelper sqlite = new SQLiteHelper(Login.this);
                                    SQLiteDatabase database = sqlite.getWritableDatabase();
                                    ContentValues values1 = new ContentValues();
                                    values1.put("quick_bulkupdate", "1");
                                    database.update("dd_user", values1,"ID = ?", new String[]{"1"});
                                    sqlite.close();
                                    database.close();
                                    Log.d("bul_qbul","yes");
                                    editor.putString("quickbulk", String.valueOf(1));
                                    editor.commit();
                                }else{
                                    SQLiteHelper sqlite = new SQLiteHelper(Login.this);
                                    SQLiteDatabase database = sqlite.getWritableDatabase();
                                    ContentValues values1 = new ContentValues();
                                    values1.put("quick_bulkupdate", "0");
                                    database.update("dd_user", values1,"ID = ?", new String[]{"1"});
                                    sqlite.close();
                                    database.close();
                                    editor.putString("quickbulk", String.valueOf(0));
                                    editor.commit();
                                }
                            }
                            sqlite = new SQLiteHelper(Login.this);
                            database = sqlite.getReadableDatabase();
                            cursor = database.rawQuery("SELECT * FROM "+SQLiteHelper.TABLENAME12+" WHERE "+SQLiteHelper.USNAM+"=? AND "+SQLiteHelper.USPASS+" =?",new String[]{username,password});
                            if (cursor != null) {
                                if (cursor.getCount() != 0) {
                                    cursor.moveToFirst();
                                    do {
                                        int index;

                                        index = cursor.getColumnIndexOrThrow("username");
                                        USER = cursor.getString(index);
                                        index = cursor.getColumnIndexOrThrow("password");
                                        PASS = cursor.getString(index);
                                        index = cursor.getColumnIndexOrThrow("otp");
                                        OTP = cursor.getString(index);
                                        index = cursor.getColumnIndexOrThrow("ID");
                                        ID = cursor.getString(index);
                                        index = cursor.getColumnIndexOrThrow("backup_privilege");
                                        Integer backpri = cursor.getInt(index);
                                        String bbackp = cursor.getString(index);
                                        if(bbackp == null ||bbackp.equalsIgnoreCase("")){
                                            backpri = 1;
                                        }
//                                    index = cursor.getColumnIndexOrThrow("quick_bulkupdate");
                                        Integer quick_bulkupda = 0;
                                        String quick_bulkupdate = "0";
                                        if(quick_bulkupdate == null ||quick_bulkupdate.equalsIgnoreCase("")){
                                            quick_bulkupda = 0;
                                        }
                                        if(OTP == null || OTP.equalsIgnoreCase("")){
                                            editor.putString("user",USER);
                                            editor.putString("pass",PASS);
                                            editor.putString("otp",OTP);
                                            editor.putString("id",ID);
                                            editor.putString("backpri", String.valueOf(backpri));
//                                        editor.putString("quickbulk", String.valueOf(quick_bulkupda));
                                            editor.putInt("isloginn",1);
                                            editor.commit();
                                            Toast.makeText(getApplicationContext(),"Login successfull",Toast.LENGTH_SHORT).show();
//                                    cursor.close();
//                                    sqlite.close();
//                                    database.close();
                                            lastlogin();
                                            Intent login = new Intent(Login.this,Enterotp.class);
                                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                                                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                                            login.putExtra("user",USER);
                                            login.putExtra("pass",PASS);
                                            login.putExtra("otp",OTP);
                                            login.putExtra("ID",ID);
                                            login.putExtra("vaa","1");
                                            editor.putString("backpri", String.valueOf(backpri));
//                                        editor.putString("quickbulk", String.valueOf(quick_bulkupda));
                                            startActivity(login,bundle);

                                        }else{
                                            editor.putString("user",USER);
                                            editor.putString("pass",PASS);
                                            editor.putString("otp",OTP);
                                            editor.putString("id",ID);
                                            editor.putString("backpri", String.valueOf(backpri));
//                                        editor.putString("quickbulk", String.valueOf(quick_bulkupda));
                                            editor.putInt("isloginn",1);
                                            editor.commit();
                                            Toast.makeText(getApplicationContext(),"Login successfull",Toast.LENGTH_SHORT).show();
//                                    cursor.close();
//                                    sqlite.close();
//                                    database.close();
                                            lastlogin();
                                            backupDatabase();
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
                                    backupDatabase1();
                                    AlertDialog.Builder alertbox = new AlertDialog.Builder(Login.this,R.style.AlertDialogTheme);
                                    String enn = getString(R.string.usepassmis);
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
                            }else{
                                cursor.close();
                                sqlite.close();
                                database.close();
                                Toast.makeText(getApplicationContext(),"Username or password is incorrect",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(actsta.equalsIgnoreCase("blocked")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(License.equalsIgnoreCase("no")||License.equalsIgnoreCase("")){
                                        SQLiteHelper sqlite = new SQLiteHelper(Login.this);
                                        SQLiteDatabase database = sqlite.getWritableDatabase();
                                        ContentValues values1 = new ContentValues();
                                        values1.put("quick_bulkupdate", "1");
                                        database.update("dd_user", values1,"ID = ?", new String[]{"1"});
                                        sqlite.close();
                                        database.close();
                                        editor.putString("quickbulk", String.valueOf(1));
                                        editor.commit();
                                    }else{
                                        if(subscr_id == null || subscr_id.equalsIgnoreCase("") || subscr_id.equalsIgnoreCase("null") || subscr_id.equalsIgnoreCase("Dailydiary")){
                                            SQLiteHelper sqlite = new SQLiteHelper(Login.this);
                                            SQLiteDatabase database = sqlite.getWritableDatabase();
                                            ContentValues values1 = new ContentValues();
                                            values1.put("quick_bulkupdate", "0");
                                            database.update("dd_user", values1,"ID = ?", new String[]{"1"});
                                            sqlite.close();
                                            database.close();
                                            editor.putString("quickbulk", String.valueOf(0));
                                            editor.commit();
                                        } else if (  subscr_id.equalsIgnoreCase("quick_bulk_update")){
                                            SQLiteHelper sqlite = new SQLiteHelper(Login.this);
                                            SQLiteDatabase database = sqlite.getWritableDatabase();
                                            ContentValues values1 = new ContentValues();
                                            values1.put("quick_bulkupdate", "1");
                                            database.update("dd_user", values1,"ID = ?", new String[]{"1"});
                                            sqlite.close();
                                            database.close();
                                            editor.putString("quickbulk", String.valueOf(1));
                                            editor.commit();
                                        }else{
                                            SQLiteHelper sqlite = new SQLiteHelper(Login.this);
                                            SQLiteDatabase database = sqlite.getWritableDatabase();
                                            ContentValues values1 = new ContentValues();
                                            values1.put("quick_bulkupdate", "0");
                                            database.update("dd_user", values1,"ID = ?", new String[]{"1"});
                                            sqlite.close();
                                            database.close();
                                            editor.putString("quickbulk", String.valueOf(0));
                                            editor.commit();
                                        }
                                    }
                                    backupDatabase1();
                                    AlertDialog.Builder alertbox = new AlertDialog.Builder(Login.this,R.style.AlertDialogTheme);
                                    String rer = getString(R.string.keyblocked);
                                    String rer1= getString(R.string.warning);
                                    String rer2 = getString(R.string.ok);
                                    alertbox.setMessage(rer);
                                    alertbox.setTitle(rer1);
                                    alertbox.setIcon(R.drawable.dailylogo);
                                    alertbox.setNeutralButton(rer2,
                                            new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface arg0,
                                                                    int arg1) {
                                                    Intent logined = new Intent(Login.this,confirm_OTP.class);
                                                    Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                                                            android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                                                    logined.putExtra("vaa","2");
                                                    startActivity(logined,bundle);
                                                }
                                            });
                                    alertbox.show();
                                }
                            });
                        }
                    }
                }

//            else{
//                sqlite = new SQLiteHelper(Login.this);
//                database = sqlite.getReadableDatabase();
//            cursor = database.rawQuery("SELECT * FROM "+SQLiteHelper.TABLENAME12+" WHERE "+SQLiteHelper.USNAM+"=? AND "+SQLiteHelper.USPASS+" =?",new String[]{username,password});
//            if (cursor != null) {
//                if (cursor.getCount() != 0) {
//                    cursor.moveToFirst();
//                    do {
//                        int index;
//
//                        index = cursor.getColumnIndexOrThrow("username");
//                        USER = cursor.getString(index);
//                        index = cursor.getColumnIndexOrThrow("password");
//                        PASS = cursor.getString(index);
//                        index = cursor.getColumnIndexOrThrow("otp");
//                        OTP = cursor.getString(index);
//                        index = cursor.getColumnIndexOrThrow("ID");
//                        ID = cursor.getString(index);
//                        index = cursor.getColumnIndexOrThrow("backup_privilege");
//                        Integer backpri = cursor.getInt(index);
//                        String bbackp = cursor.getString(index);
//                        if(bbackp == null ||bbackp.equalsIgnoreCase("")){
//                            backpri = 1;
//                        }
//                        if(OTP == null || OTP.equalsIgnoreCase("")){
//                            editor.putString("user",USER);
//                            editor.putString("pass",PASS);
//                            editor.putString("otp",OTP);
//                            editor.putString("id",ID);
//                            editor.putString("backpri", String.valueOf(backpri));
//                            editor.putInt("isloginn",1);
//                            editor.commit();
//                            Toast.makeText(getApplicationContext(),"Login successfull",Toast.LENGTH_SHORT).show();
//                            cursor.close();
//                            sqlite.close();
//                            database.close();
//                            lastlogin();
//                            Intent login = new Intent(Login.this,Enterotp.class);
//                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
//                                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
//                            login.putExtra("user",USER);
//                            login.putExtra("pass",PASS);
//                            login.putExtra("otp",OTP);
//                            login.putExtra("ID",ID);
//                            editor.putString("backpri", String.valueOf(backpri));
//                            startActivity(login,bundle);
//
//                        }else{
//                            editor.putString("user",USER);
//                            editor.putString("pass",PASS);
//                            editor.putString("otp",OTP);
//                            editor.putString("id",ID);
//                            editor.putString("backpri", String.valueOf(backpri));
//                            editor.putInt("isloginn",1);
//                            editor.commit();
//                            Toast.makeText(getApplicationContext(),"Login successfull",Toast.LENGTH_SHORT).show();
//                            cursor.close();
//                            sqlite.close();
//                            database.close();
//                            lastlogin();
//                            Intent login = new Intent(Login.this,confirm_OTP.class);
//                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
//                                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
//                            login.putExtra("user",USER);
//                            login.putExtra("pass",PASS);
//                            login.putExtra("otp",OTP);
//                            login.putExtra("ID",ID);
//                            startActivity(login,bundle);
//                        }
//                    }
//                    while (cursor.moveToNext());
//
//                    cursor.close();
//                    sqlite.close();
//                    database.close();
//                }else{
//                    cursor.close();
//                    sqlite.close();
//                    database.close();
//                    AlertDialog.Builder alertbox = new AlertDialog.Builder(Login.this,R.style.AlertDialogTheme);
//                    String enn = getString(R.string.usepassmis);
//                    String war = getString(R.string.warning);
//                    String ook = getString(R.string.ok);
//                    alertbox.setMessage(enn);
//                    alertbox.setTitle(war);
//                    alertbox.setIcon(R.drawable.dailylogo);
//                    alertbox.setNeutralButton(ook,
//                            new DialogInterface.OnClickListener() {
//
//                                public void onClick(DialogInterface arg0,
//                                                    int arg1) {
//                                }
//                            });
//                    alertbox.show();
//                }
//                }else{
//                cursor.close();
//                sqlite.close();
//                database.close();
//                    Toast.makeText(getApplicationContext(),"Username or password is incorrect",Toast.LENGTH_SHORT).show();
//                }
//            }
            }

        });

    }
    //onStart() - Called when activity is started
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    protected void onStart() {
        if (Login.InternetConnection.checkConnection(getApplicationContext(),Login.this)) {

        }else{
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Login.this,R.style.AlertDialogTheme);
            String warr = getString(R.string.warning);
            String ok = getString(R.string.ok);
            String cancel = getString(R.string.cancel);
            alertbox.setMessage(R.string.on_internet);
            alertbox.setTitle(warr);
            alertbox.setIcon(R.drawable.dailylogo);
            alertbox.setCancelable(false);
            alertbox.setPositiveButton(ok,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {

                        }
                    });
            alertbox.show();
        }
        super.onStart();
    }
    //onResume() - Called when activity is resumed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    protected void onResume() {
        if (Login.InternetConnection.checkConnection(getApplicationContext(),Login.this)) {

        }else{
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Login.this,R.style.AlertDialogTheme);
            String warr = getString(R.string.warning);
            String ok = getString(R.string.ok);
            String cancel = getString(R.string.cancel);
            alertbox.setMessage(R.string.on_internet);
            alertbox.setTitle(warr);
            alertbox.setIcon(R.drawable.dailylogo);
            alertbox.setCancelable(false);
            alertbox.setPositiveButton(ok,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {

                        }
                    });
            alertbox.show();
        }
        super.onResume();
    }
    //onRestart() - Called when activity is restarted
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    protected void onRestart() {
        if (Login.InternetConnection.checkConnection(getApplicationContext(),Login.this)) {

        }else{
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Login.this,R.style.AlertDialogTheme);
            String warr = getString(R.string.warning);
            String ok = getString(R.string.ok);
            String cancel = getString(R.string.cancel);
            alertbox.setMessage(R.string.on_internet);
            alertbox.setTitle(warr);
            alertbox.setIcon(R.drawable.dailylogo);
            alertbox.setCancelable(false);
            alertbox.setPositiveButton(ok,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {

                        }
                    });
            alertbox.show();
        }
        super.onRestart();
    }

    //    public void imei111(){
//        sqlite = new SQLiteHelper(this);
//        database = sqlite.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("IMEI", device_id);
//        database.update("dd_settings",  values,"ID = ?",new String[]{"1"});
//    }

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

    //lastlogin1() - Upload file to firebase
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
        if(ss1 == null || ss1.equalsIgnoreCase("") || ss1.equalsIgnoreCase("null")){
            SharedPreferences pref0 = getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref0.edit();
            editor.putString("lastlogin",ss);
            editor.apply();
            loading = "1" ;
            daily_firebase12(Uri.fromFile(backupDB),license,ss0);
        }else{
            if(dateDifference > 0){
                SharedPreferences pref0 = getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref0.edit();
                editor.putString("lastlogin",ss);
                editor.apply();
                loading = "1" ;
                daily_firebase12(Uri.fromFile(backupDB),license,ss0);
            }else{
//                SharedPreferences pref0 = getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
//                SharedPreferences.Editor editor = pref0.edit();
//                editor.putString("lastlogin",ss);
//                editor.apply();
//                loading = "1" ;
//                daily_firebase(Uri.fromFile(backupDB),license,ss0);

                Intent login = new Intent(Login.this,confirm_OTP.class);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                login.putExtra("user",USER);
                login.putExtra("pass",PASS);
                login.putExtra("otp",OTP);
                login.putExtra("ID",ID);
                login.putExtra("vaa","1");
                startActivity(login,bundle);
            }
        }
        String ss11 = pref.getString("lastlogin_weekly", "null");
        Log.d("datedddiiff_login",ss+" "+ss1);
        if(ss11 == null || ss11.equalsIgnoreCase("") || ss11.equalsIgnoreCase("null")){
            SharedPreferences pref0 = getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref0.edit();
            editor.putString("lastlogin_weekly",ss);
            editor.apply();
            loading = "2" ;
            weekly_firebase(Uri.fromFile(backupDB),license,"dd_backup");
        }else{
            int dateDifference1 = (int) getDateDiff(new SimpleDateFormat("dd/MM/yyyy"), ss11, ss);
            if(dateDifference1 > 7){
                SharedPreferences pref0 = getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref0.edit();
                editor.putString("lastlogin_weekly",ss);
                editor.apply();
                loading = "2" ;
                weekly_firebase(Uri.fromFile(backupDB),license,"dd_backup");
            }else{
                Intent login = new Intent(Login.this,confirm_OTP.class);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                login.putExtra("user",USER);
                login.putExtra("pass",PASS);
                login.putExtra("otp",OTP);
                login.putExtra("ID",ID);
                login.putExtra("vaa","1");
                startActivity(login,bundle);
            }
        }
        String ss111 = pref.getString("lastlogin_monthly", "null");
        Log.d("datedddiiff_login",ss+" "+ss1);
        if(ss111 == null || ss111.equalsIgnoreCase("") || ss111.equalsIgnoreCase("null")){
            SharedPreferences pref0 = getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref0.edit();
            editor.putString("lastlogin_monthly",ss);
            editor.apply();
            loading = "3" ;
            monthly_firebase(Uri.fromFile(backupDB),license,"dd_backup");
        }else{
            int dateDifference11 = (int) getDateDiff(new SimpleDateFormat("dd/MM/yyyy"), ss111, ss);
            if(dateDifference11 > 30){
                SharedPreferences pref0 = getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref0.edit();
                editor.putString("lastlogin_monthly",ss);
                editor.apply();
                loading = "3" ;
                monthly_firebase(Uri.fromFile(backupDB),license,"dd_backup");
            }else{
                Intent login = new Intent(Login.this,confirm_OTP.class);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                login.putExtra("user",USER);
                login.putExtra("pass",PASS);
                login.putExtra("otp",OTP);
                login.putExtra("ID",ID);
                login.putExtra("vaa","1");
                startActivity(login,bundle);
            }
        }


    }

    //lastlogin11() -  Upload file to firebase
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void lastlogin11(File backupDB) {
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
        if(ss1 == null || ss1.equalsIgnoreCase("") || ss1.equalsIgnoreCase("null")){
            SharedPreferences pref0 = getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref0.edit();
            editor.putString("lastlogin",ss);
            editor.apply();
            loading = "1" ;
            daily_firebase1(Uri.fromFile(backupDB),license,ss0);
        }else{
            if(dateDifference > 0){
                SharedPreferences pref0 = getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref0.edit();
                editor.putString("lastlogin",ss);
                editor.apply();
                loading = "1" ;
                daily_firebase1(Uri.fromFile(backupDB),license,ss0);
            }else{

            }
        }
        String ss11 = pref.getString("lastlogin_weekly", "null");
        Log.d("datedddiiff_login",ss+" "+ss1);
        if(ss11 == null || ss11.equalsIgnoreCase("") || ss11.equalsIgnoreCase("null")){
            SharedPreferences pref0 = getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref0.edit();
            editor.putString("lastlogin_weekly",ss);
            editor.apply();
            loading = "2" ;
            weekly_firebase1(Uri.fromFile(backupDB),license,"dd_backup");
        }else{
            int dateDifference1 = (int) getDateDiff(new SimpleDateFormat("dd/MM/yyyy"), ss11, ss);
            if(dateDifference1 > 7){
                SharedPreferences pref0 = getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref0.edit();
                editor.putString("lastlogin_weekly",ss);
                editor.apply();
                loading = "2" ;
                weekly_firebase1(Uri.fromFile(backupDB),license,"dd_backup");
            }
        }
        String ss111 = pref.getString("lastlogin_monthly", "null");
        Log.d("datedddiiff_login",ss+" "+ss1);
        if(ss111 == null || ss111.equalsIgnoreCase("") || ss111.equalsIgnoreCase("null")){
            SharedPreferences pref0 = getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref0.edit();
            editor.putString("lastlogin_monthly",ss);
            editor.apply();
            loading = "3" ;
            monthly_firebase1(Uri.fromFile(backupDB),license,"dd_backup");
        }else{
            int dateDifference11 = (int) getDateDiff(new SimpleDateFormat("dd/MM/yyyy"), ss111, ss);
            if(dateDifference11 > 30){
                SharedPreferences pref0 = getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref0.edit();
                editor.putString("lastlogin_monthly",ss);
                editor.apply();
                loading = "3" ;
                monthly_firebase1(Uri.fromFile(backupDB),license,"dd_backup");
            }
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
//        editor.apply();
    }

    //userpass() - get admin username and password
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void userpass(){
        sqlite = new SQLiteHelper(Login.this);
        database = sqlite.getReadableDatabase();
        cursor = database.rawQuery("SELECT * FROM "+SQLiteHelper.TABLENAME12+" WHERE id = ? ",new String[]{"1"});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("username");
                    USER = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("password");
                    PASS = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("otp");
                    OTP = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("ID");
                    ID = cursor.getString(index);
                    if(fuser == null || fuser.equalsIgnoreCase("") || fpass == null || fpass.equalsIgnoreCase("")){
                        fuser = USER;
                        fpass = PASS;
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
            Toast.makeText(getApplicationContext(),"Username or password is incorrect",Toast.LENGTH_SHORT).show();
        }
    }

    //email() - Send email backup
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void email(){
        populateRecyclerView();
        Log.d("usewrandpass",fuser+" "+fpass);
        String email = "rampittech@gmail.com";
        String subject = getString(R.string.getpass);
        String mm = getString(R.string.yourpass);
        String mm1 = getString(R.string.and);
        String message = mm+" "+"'"+fuser+"'"+ mm1 +"'"+fpass+"'";
        //Creating SendMail object
//        Log.d("pdffile", String.valueOf(pdfFile));
        if(emailid.equalsIgnoreCase("0") || emailid == null){
            emailid = "rampittech@gmail.com";
            Sendmail2 sm = new Sendmail2(this, emailid, subject, message);
            //Executing sendmail to send email
            sm.execute();
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Login.this,R.style.AlertDialogTheme);
            String logmsg = getString(R.string.enteremaillogin);
            String cann = getString(R.string.cancel);
            String warr = getString(R.string.warning);
            String logo = getString(R.string.ok);
            alertbox.setMessage(logmsg);
            alertbox.setTitle(warr);
            alertbox.setIcon(R.drawable.dailylogo);

            alertbox.setPositiveButton(logo,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {

//                            Intent i = new Intent(Today_report.this,Settings_activity.class);
//                            startActivity(i);
                        }
                    });
            alertbox.show();
        }else{
            Sendmail2 sm = new Sendmail2(this, emailid, subject, message);
            //Executing sendmail to send email

            Sendmail2 sm1 = new Sendmail2(this, "rampittech@gmail.com", subject, message);
            //Executing sendmail to send email
            sm1.execute();
            sm.execute();
        }
//        String mailId="abilashmohanan38@gmail.com";
//        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
//                Uri.fromParts("mailto",mailId, null));
//        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Forogot Password");
//        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,"Your paswword is"+" "+fpass);
//// or get fancy with HTML like this
////        emailIntent.putExtra(
////                Intent.EXTRA_TEXT,
////                Html.fromHtml(new StringBuilder()
////                        .append("<p><b>Some Content</b></p>")
////                        .append("<a>http://www.google.com</a>")
////                        .append("<small><p>More content</p></small>")
////                        .toString())
////        );
////        emailIntent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(pdfFile));
//        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    //populateRecyclerView() - get license
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView() {

        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();

        String MY_QUERY = "SELECT * FROM dd_collection where id = ?";
        String whereClause = "ID=?";
        String[] whereArgs = new String[] {"1"};

//        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{idd});
        String[] columns = {"license",
                "cc","ID","email","expiry","language","password"};
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
                    license = cursor.getString(index);


                    index = cursor.getColumnIndexOrThrow("cc");
                    String  emacc = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("email");
                    emailid = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("expiry");
                    String exp = cursor.getString(index);

                    if(emailid == null){
                        emailid ="0";
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

    //backupDatabase() - upload backup to external storage
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
        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Dailydiary_downloads");
        if (!docsFolder.exists()) {
            if (!docsFolder.mkdir()) {
//                Toast.makeText(context, "no directory", Toast.LENGTH_LONG).show();
            } else {
//                Log.i(TAG, "Created a new directory for PDF");
            }
        }
        String currentDBPath = "/data/"+ "com.rampit.rask3.dailydiary" +"/databases/LOGIN";
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
        if(License == null || License.equalsIgnoreCase("") || License.equalsIgnoreCase("null") || License.equalsIgnoreCase("no")){
            pdfname = "DD_RRP"+"-"+todaaaa+"_Backup_Mail";
        }else{
            pdfname = "DD_RRP"+"-"+License+"-"+todaaaa+"_Backup_Mail";
        }
//        pdfname = "DD_RRP"+"-"+todaaaa+"_Backup_Mail";
        pdfname1 = "DD_RRP_Backup_Mail";
        if (InternetConnection.checkConnection(getApplicationContext(),Login.this)) {
            // Internet Available...

//            date32();
            if(license == null || license.equalsIgnoreCase("") || license.equalsIgnoreCase("null")){
                Log.d("internet_111","on");
                sendEmail1();
//                license = "abi" ;
//                sendEmail11();
//                lastlogin1(backupDB);
            }else{
                Log.d("internet_1111","on");
                sendEmail11();
                lastlogin1(backupDB);
            }
        } else {
            // Internet Not Available...
            Log.d("internet","off");
            Intent login = new Intent(Login.this,confirm_OTP.class);
            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
            login.putExtra("user",USER);
            login.putExtra("pass",PASS);
            login.putExtra("otp",OTP);
            login.putExtra("ID",ID);
            login.putExtra("vaa","1");
            startActivity(login,bundle);
//            final AlertDialog.Builder alertbox = new AlertDialog.Builder(Login.this,R.style.AlertDialogTheme);
//            String enn = getString(R.string.on_internet);
//            String war = getString(R.string.warning);
//            String ook = getString(R.string.ok);
//            alertbox.setMessage(enn);
//            alertbox.setTitle(war);
//            alertbox.setIcon(R.drawable.dailylogo);
//            alertbox.setNeutralButton(ook,
//                    new DialogInterface.OnClickListener() {
//
//                        public void onClick(DialogInterface arg0,
//                                            int arg1) {
//
//                        }
//                    });
//            alertbox.show();

        }

    }

    //backupDatabase1() - upload backup to external storage
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void backupDatabase1() {
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
        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Dailydiary_downloads");
        if (!docsFolder.exists()) {
            if (!docsFolder.mkdir()) {
//                Toast.makeText(context, "no directory", Toast.LENGTH_LONG).show();
            } else {
//                Log.i(TAG, "Created a new directory for PDF");
            }
        }
        String currentDBPath = "/data/"+ "com.rampit.rask3.dailydiary" +"/databases/LOGIN";
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
        if(License == null || License.equalsIgnoreCase("") || License.equalsIgnoreCase("null") || License.equalsIgnoreCase("no")){
            pdfname = "DD_RRP"+"-"+todaaaa+"_Backup_Mail";
        }else{
            pdfname = "DD_RRP"+"-"+License+"-"+todaaaa+"_Backup_Mail";
        }
        pdfname1 = "DD_RRP_Backup_Mail";
        if (InternetConnection.checkConnection(getApplicationContext(),Login.this)) {
            // Internet Available...
            Log.d("internet","on");
//            date32();
            if(license == null || license.equalsIgnoreCase("") || license.equalsIgnoreCase("null")){
                sendEmail11();
//                license = "abi" ;
//                sendEmail11();
//                lastlogin1(backupDB);
            }else{
                sendEmail11();
                lastlogin11(backupDB);
            }
        } else {
            // Internet Not Available...

//            final AlertDialog.Builder alertbox = new AlertDialog.Builder(Login.this,R.style.AlertDialogTheme);
//            String enn = getString(R.string.on_internet);
//            String war = getString(R.string.warning);
//            String ook = getString(R.string.ok);
//            alertbox.setMessage(enn);
//            alertbox.setTitle(war);
//            alertbox.setIcon(R.drawable.dailylogo);
//            alertbox.setNeutralButton(ook,
//                    new DialogInterface.OnClickListener() {
//
//                        public void onClick(DialogInterface arg0,
//                                            int arg1) {
//
//                        }
//                    });
//            alertbox.show();

        }

    }

    //daily_firebase() - upload backup to firebase in daily
    //Params - File uri , filename
    //Created on 25/01/2022
    //Return - NULL
    public void daily_firebase(Uri uri, String value, String filee){
        Log.d("dddata11",filee);
        FirebaseStorage storage;
        storage = FirebaseStorage.getInstance();
        StorageMetadata metadata = new StorageMetadata.Builder()
//                .setContentType("image/jpeg")
                .build();
        StorageReference ref = storage.getReference().child(String.valueOf(license)+"/"+"daily"+"/"+String.valueOf(filee));
        String finalValue = value;
        ref.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("dddata11","success"+" "+loading);
//                        if(loading.equalsIgnoreCase("1")){
//                            Intent login = new Intent(Login.this,confirm_OTP.class);
//                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
//                                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
//                            login.putExtra("user",USER);
//                            login.putExtra("pass",PASS);
//                            login.putExtra("otp",OTP);
//                            login.putExtra("ID",ID);
//                            login.putExtra("vaa","1");
//                            startActivity(login,bundle);
//                        }

//                        DateFormat dff1 = new SimpleDateFormat("yyyyMMddHHmmss");
//                        DateFormat dff11 = new SimpleDateFormat("yyyyMMdd");
//                        Calendar cal = Calendar.getInstance();
//                        String curdate1 = dff1.format(cal.getInstance().getTime());
//                        String curdate11 = dff11.format(cal.getInstance().getTime());
//                        FirebaseDatabase database111 = FirebaseDatabase.getInstance();
//                        final DatabaseReference myRef11 = database111.getReference("dd_files_license");
//                        String mGroupId = myRef11.push().getKey();
//                        Log.d("mGroupId",String.valueOf(mGroupId));
//                        myRef11.child(mGroupId).child("id").setValue(mGroupId);
//
//
//                        FirebaseFirestore db1 = FirebaseFirestore.getInstance();
//                        Map<String, Object> city111 = new HashMap<>();
//                        city111.put("id", mGroupId);
//                        city111.put("license", license);
//                        city111.put("filename",filee);
//                        city111.put("created_date", curdate1);
//                        city111.put("for_order", curdate11);
//                        city111.put("updated_date", "");
//                        db1.collection("dd_files_license").document(String.valueOf(mGroupId))
//                                .set(city111)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        Log.d("ggg", "DocumentSnapshot successfully written!");
//                                        progre();
//                                        Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Log.w("ggg", "Error writing document", e);
//                                        progre();
//                                        Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("dddata","failure"+" "+String.valueOf(e));
//                        if(loading.equalsIgnoreCase("1")){
//                            Intent login = new Intent(Login.this,confirm_OTP.class);
//                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
//                                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
//                            login.putExtra("user",USER);
//                            login.putExtra("pass",PASS);
//                            login.putExtra("otp",OTP);
//                            login.putExtra("ID",ID);
//                            login.putExtra("vaa","1");
//                            startActivity(login,bundle);
//                        }
                    }
                });
        if(loading.equalsIgnoreCase("1")){
            Intent login = new Intent(Login.this,confirm_OTP.class);
            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
            login.putExtra("user",USER);
            login.putExtra("pass",PASS);
            login.putExtra("otp",OTP);
            login.putExtra("ID",ID);
            login.putExtra("vaa","1");
            startActivity(login,bundle);
        }

    }

    //daily_firebase() - upload backup to firebase in daily
    //Params - File uri , filename
    //Created on 25/01/2022
    //Return - NULL
    public void daily_firebase12(Uri uri, String value, String filee){
        Log.d("dddata11",filee);
        FirebaseStorage storage;
        storage = FirebaseStorage.getInstance();
        StorageMetadata metadata = new StorageMetadata.Builder()
//                .setContentType("image/jpeg")
                .build();
        StorageReference ref = storage.getReference().child(String.valueOf(license)+"/"+"daily"+"/"+String.valueOf(filee));
        String finalValue = value;
        ref.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("dddata11","success"+" "+loading);
//                        if(loading.equalsIgnoreCase("1")){
//                            Intent login = new Intent(Login.this,confirm_OTP.class);
//                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
//                                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
//                            login.putExtra("user",USER);
//                            login.putExtra("pass",PASS);
//                            login.putExtra("otp",OTP);
//                            login.putExtra("ID",ID);
//                            login.putExtra("vaa","1");
//                            startActivity(login,bundle);
//                        }
//                        DateFormat dff1 = new SimpleDateFormat("yyyyMMddHHmmss");
//                        DateFormat dff11 = new SimpleDateFormat("yyyyMMdd");
//                        Calendar cal = Calendar.getInstance();
//                        String curdate1 = dff1.format(cal.getInstance().getTime());
//                        String curdate11 = dff11.format(cal.getInstance().getTime());
//                        FirebaseDatabase database111 = FirebaseDatabase.getInstance();
//                        final DatabaseReference myRef11 = database111.getReference("dd_files_license");
//                        String mGroupId = myRef11.push().getKey();
//                        Log.d("mGroupId",String.valueOf(mGroupId));
//                        myRef11.child(mGroupId).child("id").setValue(mGroupId);
//
//
//                        FirebaseFirestore db1 = FirebaseFirestore.getInstance();
//                        Map<String, Object> city111 = new HashMap<>();
//                        city111.put("id", mGroupId);
//                        city111.put("license", license);
//                        city111.put("filename",filee);
//                        city111.put("created_date", curdate1);
//                        city111.put("for_order", curdate11);
//                        city111.put("updated_date", "");
//                        db1.collection("dd_files_license").document(String.valueOf(mGroupId))
//                                .set(city111)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        Log.d("ggg", "DocumentSnapshot successfully written!");
//                                        progre();
//                                        Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Log.w("ggg", "Error writing document", e);
//                                        progre();
//                                        Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                                    }
//                                });

                        dd_files_license_upload(license,filee);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("dddata","failure"+" "+String.valueOf(e));
//                        if(loading.equalsIgnoreCase("1")){
//                            Intent login = new Intent(Login.this,confirm_OTP.class);
//                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
//                                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
//                            login.putExtra("user",USER);
//                            login.putExtra("pass",PASS);
//                            login.putExtra("otp",OTP);
//                            login.putExtra("ID",ID);
//                            login.putExtra("vaa","1");
//                            startActivity(login,bundle);
//                        }
                    }
                });
        if(loading.equalsIgnoreCase("1")){
            Intent login = new Intent(Login.this,confirm_OTP.class);
            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
            login.putExtra("user",USER);
            login.putExtra("pass",PASS);
            login.putExtra("otp",OTP);
            login.putExtra("ID",ID);
            login.putExtra("vaa","1");
            startActivity(login,bundle);
        }

    }

    //dd_files_license_upload() - upload backup data to firebase
    //Params - license, filename
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
//                                        progre();
//                                        Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("ggg", "Error writing document", e);
//                                        progre();
//                                        Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
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
//                                progre();
//                                Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                } else {
                    Log.d("gettingids", "Error getting documents: ", task.getException());
                }
            }
        });
    }


    //weekly_firebase() - upload backup to firebase in weekly
    //Params - File uri , filename
    //Created on 25/01/2022
    //Return - NULL
    public void weekly_firebase(Uri uri, String value, String filee){
        FirebaseStorage storage;
        storage = FirebaseStorage.getInstance();
        StorageMetadata metadata = new StorageMetadata.Builder()
//                .setContentType("image/jpeg")
                .build();
        StorageReference ref = storage.getReference().child(String.valueOf(license)+"/"+"weekly"+"/"+String.valueOf(filee));
        String finalValue = value;
        ref.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("dddata","success");
//                        if(loading.equalsIgnoreCase("2")){
//                            Intent login = new Intent(Login.this,confirm_OTP.class);
//                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
//                                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
//                            login.putExtra("user",USER);
//                            login.putExtra("pass",PASS);
//                            login.putExtra("otp",OTP);
//                            login.putExtra("ID",ID);
//                            login.putExtra("vaa","1");
//                            startActivity(login,bundle);
//                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("dddata","failure"+" "+String.valueOf(e));
//                        if(loading.equalsIgnoreCase("2")){
//                            Intent login = new Intent(Login.this,confirm_OTP.class);
//                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
//                                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
//                            login.putExtra("user",USER);
//                            login.putExtra("pass",PASS);
//                            login.putExtra("otp",OTP);
//                            login.putExtra("ID",ID);
//                            login.putExtra("vaa","1");
//                            startActivity(login,bundle);
//                        }
                    }
                });
        if(loading.equalsIgnoreCase("2")){
            Intent login = new Intent(Login.this,confirm_OTP.class);
            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
            login.putExtra("user",USER);
            login.putExtra("pass",PASS);
            login.putExtra("otp",OTP);
            login.putExtra("ID",ID);
            login.putExtra("vaa","1");
            startActivity(login,bundle);
        }
    }

    //monthly_firebase() - upload backup to firebase in monthly
    //Params - File uri , filename
    //Created on 25/01/2022
    //Return - NULL
    public void monthly_firebase(Uri uri, String value, String filee){
        FirebaseStorage storage;
        storage = FirebaseStorage.getInstance();
        StorageMetadata metadata = new StorageMetadata.Builder()
//                .setContentType("image/jpeg")
                .build();
        StorageReference ref = storage.getReference().child(String.valueOf(license)+"/"+"monthly"+"/"+String.valueOf(filee));
        String finalValue = value;
        ref.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("dddata11","success");
//                        if(loading.equalsIgnoreCase("3")){
//                            Intent login = new Intent(Login.this,confirm_OTP.class);
//                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
//                                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
//                            login.putExtra("user",USER);
//                            login.putExtra("pass",PASS);
//                            login.putExtra("otp",OTP);
//                            login.putExtra("ID",ID);
//                            login.putExtra("vaa","1");
//                            startActivity(login,bundle);
//                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("dddata11","failure"+" "+String.valueOf(e));
//                        if(loading.equalsIgnoreCase("3")){
//                            Intent login = new Intent(Login.this,confirm_OTP.class);
//                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
//                                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
//                            login.putExtra("user",USER);
//                            login.putExtra("pass",PASS);
//                            login.putExtra("otp",OTP);
//                            login.putExtra("ID",ID);
//                            login.putExtra("vaa","1");
//                            startActivity(login,bundle);
//                        }
                    }
                });
        if(loading.equalsIgnoreCase("3")) {
            Intent login = new Intent(Login.this, confirm_OTP.class);
            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
            login.putExtra("user", USER);
            login.putExtra("pass", PASS);
            login.putExtra("otp", OTP);
            login.putExtra("ID", ID);
            login.putExtra("vaa", "1");
            startActivity(login, bundle);
        }
    }

    //daily_firebase() - upload backup to firebase in daily
    //Params - File uri , filename
    //Created on 25/01/2022
    //Return - NULL
    public void daily_firebase1(Uri uri, String value, String filee){
        FirebaseStorage storage;
        storage = FirebaseStorage.getInstance();
        StorageMetadata metadata = new StorageMetadata.Builder()
//                .setContentType("image/jpeg")
                .build();
        StorageReference ref = storage.getReference().child(String.valueOf(license)+"/"+"daily"+"/"+String.valueOf(filee));
        String finalValue = value;
        ref.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("dddata","success"+" "+loading);
                        if(loading.equalsIgnoreCase("1")){
//                            Intent login = new Intent(Login.this,confirm_OTP.class);
//                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
//                                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
//                            login.putExtra("user",USER);
//                            login.putExtra("pass",PASS);
//                            login.putExtra("otp",OTP);
//                            login.putExtra("ID",ID);
//                            login.putExtra("vaa","1");
//                            startActivity(login,bundle);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("dddata","failure"+" "+String.valueOf(e));
                        if(loading.equalsIgnoreCase("1")){
//                            Intent login = new Intent(Login.this,confirm_OTP.class);
//                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
//                                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
//                            login.putExtra("user",USER);
//                            login.putExtra("pass",PASS);
//                            login.putExtra("otp",OTP);
//                            login.putExtra("ID",ID);
//                            login.putExtra("vaa","1");
//                            startActivity(login,bundle);
                        }
                    }
                });
    }

    //weekly_firebase() - upload backup to firebase in weekly
    //Params - File uri , filename
    //Created on 25/01/2022
    //Return - NULL
    public void weekly_firebase1(Uri uri, String value, String filee){
        FirebaseStorage storage;
        storage = FirebaseStorage.getInstance();
        StorageMetadata metadata = new StorageMetadata.Builder()
//                .setContentType("image/jpeg")
                .build();
        StorageReference ref = storage.getReference().child(String.valueOf(license)+"/"+"weekly"+"/"+String.valueOf(filee));
        String finalValue = value;
        ref.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("dddata","success");
                        if(loading.equalsIgnoreCase("2")){
//                            Intent login = new Intent(Login.this,confirm_OTP.class);
//                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
//                                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
//                            login.putExtra("user",USER);
//                            login.putExtra("pass",PASS);
//                            login.putExtra("otp",OTP);
//                            login.putExtra("ID",ID);
//                            login.putExtra("vaa","1");
//                            startActivity(login,bundle);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("dddata","failure"+" "+String.valueOf(e));
                        if(loading.equalsIgnoreCase("2")){
//                            Intent login = new Intent(Login.this,confirm_OTP.class);
//                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
//                                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
//                            login.putExtra("user",USER);
//                            login.putExtra("pass",PASS);
//                            login.putExtra("otp",OTP);
//                            login.putExtra("ID",ID);
//                            login.putExtra("vaa","1");
//                            startActivity(login,bundle);
                        }
                    }
                });
    }

    //monthly_firebase() - upload backup to firebase in monthly
    //Params - File uri , filename
    //Created on 25/01/2022
    //Return - NULL
    public void monthly_firebase1(Uri uri, String value, String filee){
        FirebaseStorage storage;
        storage = FirebaseStorage.getInstance();
        StorageMetadata metadata = new StorageMetadata.Builder()
//                .setContentType("image/jpeg")
                .build();
        StorageReference ref = storage.getReference().child(String.valueOf(license)+"/"+"monthly"+"/"+String.valueOf(filee));
        String finalValue = value;
        ref.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("dddata11","success");
                        if(loading.equalsIgnoreCase("3")){
//                            Intent login = new Intent(Login.this,confirm_OTP.class);
//                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
//                                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
//                            login.putExtra("user",USER);
//                            login.putExtra("pass",PASS);
//                            login.putExtra("otp",OTP);
//                            login.putExtra("ID",ID);
//                            login.putExtra("vaa","1");
//                            startActivity(login,bundle);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("dddata11","failure"+" "+String.valueOf(e));
                        if(loading.equalsIgnoreCase("3")){
//                            Intent login = new Intent(Login.this,confirm_OTP.class);
//                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
//                                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
//                            login.putExtra("user",USER);
//                            login.putExtra("pass",PASS);
//                            login.putExtra("otp",OTP);
//                            login.putExtra("ID",ID);
//                            login.putExtra("vaa","1");
//                            startActivity(login,bundle);
                        }
                    }
                });
    }

    //sendEmail1() - Send USer name and password to email
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void sendEmail1() {
        //Getting content for email
//        String email = "abilashmohanan38@gmail.com";
//        String email = "rampittech@gmail.com";
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ii1 = pref.getString("NAME","no");
        String email = "rampittech@gmail.com";
        String subject = ii1+" "+USER+" "+PASS;
        String message = "hiiiiiiiii";

        //Creating SendMail object
        Log.d("pdffile_kkkk", String.valueOf(pdfFile)+" "+email);
        String ppp = String.valueOf(pdfFile);

        if(emailid.equalsIgnoreCase("")){
//            AlertDialog.Builder alertbox = new AlertDialog.Builder(Login.this,R.style.AlertDialogTheme);
//            String logmsg = getString(R.string.enteremail);
//            String cann = getString(R.string.cancel);
//            String warr = getString(R.string.warning);
//            String logo = getString(R.string.ok);
//            alertbox.setMessage(logmsg);
//            alertbox.setTitle(warr);
//            alertbox.setIcon(R.drawable.dailylogo);
//
//            alertbox.setPositiveButton(logo,
//                    new DialogInterface.OnClickListener() {
//
//                        public void onClick(DialogInterface arg0,
//                                            int arg1) {
//
//                            Intent i = new Intent(Login.this,Settings_activity.class);
//                            startActivity(i);
//                            finish();
//                        }
//                    });
//            alertbox.show();
            SendMail3 sm = new SendMail3(this, email, subject, message, ppp, pdfname);
            //Executing sendmail to send email
            sm.execute();
            Intent login = new Intent(Login.this,confirm_OTP.class);
            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
            login.putExtra("user",USER);
            login.putExtra("pass",PASS);
            login.putExtra("otp",OTP);
            login.putExtra("ID",ID);
            login.putExtra("vaa","1");
            startActivity(login,bundle);
        }
        else {
            SendMail3 sm = new SendMail3(this, email, subject, message, ppp, pdfname);
            //Executing sendmail to send email
            sm.execute();
            SendMail4 sm1 = new SendMail4(this, emailid, "Daily Backup", message, ppp, pdfname);
            //Executing sendmail to send email
            sm1.execute();
            Intent login = new Intent(Login.this,confirm_OTP.class);
            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
            login.putExtra("user",USER);
            login.putExtra("pass",PASS);
            login.putExtra("otp",OTP);
            login.putExtra("ID",ID);
            login.putExtra("vaa","1");
            startActivity(login,bundle);
        }
    }

    //monthly_firebase() - upload backup to Email
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void sendEmail11() {
        //Getting content for email
//        String email = "abilashmohanan38@gmail.com";
//        String email = "rampittech@gmail.com";
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ii1 = pref.getString("NAME","no");
        String email = "rampittech@gmail.com";
        String subject = ii1+" "+USER+" "+PASS;
        String message = "hiiiiiiiii";

        //Creating SendMail object
        Log.d("pdffile", String.valueOf(pdfFile)+" "+email);
        String ppp = String.valueOf(pdfFile);
        if(emailid.equalsIgnoreCase("")){
//            AlertDialog.Builder alertbox = new AlertDialog.Builder(Login.this,R.style.AlertDialogTheme);
//            String logmsg = getString(R.string.enteremail);
//            String cann = getString(R.string.cancel);
//            String warr = getString(R.string.warning);
//            String logo = getString(R.string.ok);
//            alertbox.setMessage(logmsg);
//            alertbox.setTitle(warr);
//            alertbox.setIcon(R.drawable.dailylogo);
//
//            alertbox.setPositiveButton(logo,
//                    new DialogInterface.OnClickListener() {
//
//                        public void onClick(DialogInterface arg0,
//                                            int arg1) {
//
//                            Intent i = new Intent(Login.this,Settings_activity.class);
//                            startActivity(i);
//                            finish();
//                        }
//                    });
//            alertbox.show();
            SendMail3 sm = new SendMail3(this, email, subject, message, ppp, pdfname);
            //Executing sendmail to send email
            sm.execute();
//            Intent login = new Intent(Login.this,confirm_OTP.class);
//            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
//                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
//            login.putExtra("user",USER);
//            login.putExtra("pass",PASS);
//            login.putExtra("otp",OTP);
//            login.putExtra("ID",ID);
//            login.putExtra("vaa","1");
//            startActivity(login,bundle);
        }else {
            SendMail3 sm = new SendMail3(this, email, subject, message, ppp, pdfname);
            //Executing sendmail to send email
            sm.execute();
            SendMail4 sm1 = new SendMail4(this, emailid, "Daily Backup", message, ppp, pdfname);
            //Executing sendmail to send email
            sm1.execute();

        }
    }

    //onBackPressed() - function to be done when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    //languaaa() - Check for SQlite DB updates
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void languaaa(){
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
                    oldv = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("version");
                    newv = cursor.getInt(index);

                    Log.d("oldd_2",String.valueOf(oldv));
                    Log.d("neww",String.valueOf(newv));
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("oldv",oldv);
                    editor.putInt("newv",newv);
                    editor.apply();
                    if(oldv <= newv){
                        SQLiteHelper sqlite = new SQLiteHelper(this);
                        SQLiteDatabase database = sqlite.getWritableDatabase();
                        Log.d("oldd",String.valueOf(oldv));
                        Log.d("neww",String.valueOf(newv));
                        sqlite.onUpgrade(database,oldv,newv);
//    ((Callback)getApplication()).datee();
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

    //onRequestPermissionsResult() - Request user permissions
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO
                }
                break;
            default:
                break;
        }
    }
    //InternetConnection() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Login login) {
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
                AlertDialog.Builder alertbox = new AlertDialog.Builder(login,R.style.AlertDialogTheme);
                String enn = login.getString(R.string.on_internet);
                String war = login.getString(R.string.warning);
                String ook = login.getString(R.string.ok);
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
                                    login.ssss();
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

    //ssss() - On internet in settings page
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void ssss(){
        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
    }


    private void setMobileDataEnabled(Context context, boolean enabled) throws Exception{
        final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Class conmanClass = null;
        try {
            conmanClass = Class.forName(conman.getClass().getName());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
        iConnectivityManagerField.setAccessible(true);
        final Object iConnectivityManager = iConnectivityManagerField.get(conman);
        final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
        final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        setMobileDataEnabledMethod.setAccessible(true);
        setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
    }

    //expiiii() - get user license
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
                    index = cursor.getColumnIndexOrThrow("license");
                    License = cursor.getString(index);
                    if(License == null || License.equalsIgnoreCase("")){
                        License = "no";
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

    //call_me() - get license expiry date from sever
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
        String[] separated = device_id1.split(",");
        String deer = separated[3];
        String[] separated1 = deer.split(":");
        String deer1 = separated1[1];
        device_id1 = deer1.replace("\"", "");
        System.out.println("registered_domains- "+myResponse.getString("registered_domains"));
        System.out.println("registered_domains- "+device_id1);

    }

    //call_me2() - get license expiry date from sever
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

}
