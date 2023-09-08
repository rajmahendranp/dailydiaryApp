package com.rampit.rask3.dailydiary;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;
//import com.testfairy.TestFairy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


//Updated on 25/01/2022 by RAMPIT
//Splash page
//backupDatabase11111() - take backup of current backup
//lastlogin11_000() - Download file from firebase
//sample_firebase1() - upload file to firebase
//checkSystemWritePermission() - Check permission
//openAndroidPermissionsMenu() - get amdroid permission
//languaaa() - get amdroid permission
//setLocale() - set language to app
//setLocale1() - set language to app
//onBackPressed() - function called when back button is pressed


public class Splash extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2500;
    Integer loged,oldv,newv;
    Cursor cursor;
    String user,pass,otp,USER,PASS,OTP,ID,language,expiry;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    Locale myLocale;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String lann,currentLanguage = "en", currentLang;
    private static final int REQUEST_READ_PHONE_STATE = 1;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
//        TestFairy.begin(this, "SDK-xMK0FWLq");
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash);
        ((Callback)getApplication()).curl_internet(Splash.this);
//        ((Callback)getApplication()).clearData(getApplicationContext());
//        sqlite = new SQLiteHelper(this);
//        database = sqlite.getWritableDatabase();
        languaaa();
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
            //TODO
        }
//        FirebaseApp.initializeApp(this);
//        signInAnonymously();
//        checkSystemWritePermission();
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        loged = pref.getInt("isloginn",2);
        user = pref.getString("user","");
        pass = pref.getString("pass","");
        otp = pref.getString("otp","");
//        language = pref.getString("language","");
//        if(language.equalsIgnoreCase("null")){
//            language = "ta";
//            Log.d("logggin", String.valueOf(loged));
//        }
        Log.d("logggin", String.valueOf(loged));
        if(loged == 2){
//        language = "ta";
        }
//        setLocale(language);
        Log.d("logggin", user);Log.d("logggin", pass);Log.d("logggin", otp);
        if (loged == 1)
        {
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    sqlite = new SQLiteHelper(Splash.this);
                    database = sqlite.getReadableDatabase();
                    cursor = database.rawQuery("SELECT * FROM "+SQLiteHelper.TABLENAME12+" WHERE "+SQLiteHelper.USNAM+"=? AND "+SQLiteHelper.USPASS+" =? ",new String[]{user,pass});

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
                                myLocale = new Locale(lann);
                                Resources res = getResources();
                                DisplayMetrics dm = res.getDisplayMetrics();
                                Configuration conf = res.getConfiguration();
                                conf.locale = myLocale;
                                res.updateConfiguration(conf, dm);
                                if(OTP == null || OTP.equalsIgnoreCase("")){
                                    Intent login = new Intent(Splash.this,Enterotp.class);
                                    login.putExtra("user",USER);
                                    login.putExtra("pass",PASS);
                                    Splash.this.startActivity(login);
                                }else{
                                    Intent login = new Intent(Splash.this,confirm_OTP.class);
                                    login.putExtra("user",USER);
                                    login.putExtra("pass",PASS);
                                    Splash.this.startActivity(login);
                                }
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
            }, SPLASH_DISPLAY_LENGTH);
        }else if(loged == 0 || loged == 2) {
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    sqlite = new SQLiteHelper(Splash.this);
                    database = sqlite.getReadableDatabase();
                   Cursor cursor = database.rawQuery("SELECT * FROM "+SQLiteHelper.TABLENAME12+" WHERE "+SQLiteHelper.USID+"=? ",new String[]{"1"});
                    if (cursor != null) {
                        if (cursor.getCount() != 0) {
                            cursor.moveToFirst();
                            do {
                                int index;

                                index = cursor.getColumnIndexOrThrow("username");
                                USER = cursor.getString(index);

                                index = cursor.getColumnIndexOrThrow("password");
                                PASS = cursor.getString(index);

                                index = cursor.getColumnIndexOrThrow("ID");
                                ID = cursor.getString(index);
//                                Log.d("kjkjikjhuikjh", ID);
                                if(USER.equalsIgnoreCase("")){

                                }else{
                                    myLocale = new Locale(lann);
                                    Resources res = getResources();
                                    DisplayMetrics dm = res.getDisplayMetrics();
                                    Configuration conf = res.getConfiguration();
                                    conf.locale = myLocale;
                                    res.updateConfiguration(conf, dm);
//            Intent refresh = new Intent(this, confirm_OTP.class);
//            refresh.putExtra(currentLang, localeName);
//            startActivity(refresh);
                                    Intent login = new Intent(Splash.this,Login.class);
                                    login.putExtra("user",USER);
                                    login.putExtra("pass",PASS);
                                    login.putExtra("id",ID);
                                    Splash.this.startActivity(login);}
                            }
                            while (cursor.moveToNext());
                            cursor.close();
                            sqlite.close();
                            database.close();
                        }else {
                            cursor.close();
                            sqlite.close();
                            database.close();
//                            Log.d("kjkjikjhuikjh", "opikopk");
                            myLocale = new Locale(lann);
                            Resources res = getResources();
                            DisplayMetrics dm = res.getDisplayMetrics();
                            Configuration conf = res.getConfiguration();
                            conf.locale = myLocale;
                            res.updateConfiguration(conf, dm);
                            Intent login = new Intent(Splash.this,Register.class);
                            Splash.this.startActivity(login);
                        }
                    }else{
                        cursor.close();
                        sqlite.close();
                        database.close();
                    }

                }
            }, SPLASH_DISPLAY_LENGTH);
        }else{
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    Intent login = new Intent(Splash.this,Register.class);
                    Splash.this.startActivity(login);

                }
            }, SPLASH_DISPLAY_LENGTH);

        }
    }

    //backupDatabase11111() - take backup of current backup
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void backupDatabase11111() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String myFormat1 = "dd/MM/yyyy";//In which you need put here
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
//        todaaaa = sdf1.format(c.getTime());
        DateFormat dff = new SimpleDateFormat("MMM_d_yyyy_hh_mm_aaa");
//        todaaaa = dff.format(c.getInstance().getTime());
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
        String backupDBPath = "/Dailydiary_downloads/DD_RRB_Backup_Mail";
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
        // Internet Available...
        Log.d("internet","on");
//            date32();


        lastlogin11_000(backupDB);



    }

    //lastlogin11_000() - Download file from firebase
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void lastlogin11_000(File backupDB){
        Log.d("internet00","on"+" "+"sample");
        runOnUiThread(new Runnable() {
            @SuppressLint("RestrictedApi")
            @Override
            public void run() {
                // Stuff that updates the UI
//                                    fab.hide();
                sample_firebase1(Uri.fromFile(backupDB),"sample","Sample");
//                daily_firebase1(Uri.fromFile(backupDB),license,"Sample");
            }
        });


    }

    //sample_firebase1() - upload file to firebase
    //Params - file uri , file name
    //Created on 25/01/2022
    //Return - NULL
    public void sample_firebase1(Uri uri, String value, String filee){
        FirebaseApp.initializeApp(Splash.this);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference ref = storage.getReference().child("sample1"+"/"+String.valueOf(filee));
        Log.d("internet","on"+"va");
        ref.putFile(uri)
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

    //checkSystemWritePermission() - Check permission
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private boolean checkSystemWritePermission() {
        boolean retVal = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            retVal = Settings.System.canWrite(this);
            Log.d("TAG", "Can Write Settings: " + retVal);
            if(retVal){
                ///Permission granted by the user
//                ((Callback)getApplication()).curl_brightness(Splash.this);
            }else{
                //permission not granted navigate to permission screen
                openAndroidPermissionsMenu();
            }
        }
        return retVal;
    }

    //openAndroidPermissionsMenu() - get amdroid permission
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void openAndroidPermissionsMenu() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + this.getPackageName()));
        startActivity(intent);
    }

    //languaaa() - get amdroid permission
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
                    index = cursor.getColumnIndexOrThrow("language");
                    lann = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("expiry");
                    expiry = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("oldversion");
                    oldv = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("version");
                    newv = cursor.getInt(index);
                    if(lann == null){lann ="ta";}
                    Log.d("oldd_1",String.valueOf(oldv));
                    Log.d("neww",String.valueOf(newv));
                    Log.d("langg21",lann);
//                    Log.d("oldd",String.valueOf(oldv));
//                    Log.d("neww",String.valueOf(newv));
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
//                        ((Callback)getApplication()).populateRecyclerView_new1(getApplicationContext());
                    }
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                        setLocale(lann);
                    }else{
                        setLocale1(lann);
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
                lann = "ta";
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
            lann = "ta";
        }
    }

    //setLocale() - set language to app
    //Params - Language name
    //Created on 25/01/2022
    //Return - NULL
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setLocale(String localeName) {
        Log.d("langg12",localeName);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        editor.putString("language",localeName);
        editor.commit();
//        if (!localeName.equals(currentLanguage)) {
        myLocale = new Locale(localeName);
        Locale.setDefault(myLocale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(myLocale);
        res.updateConfiguration(conf, dm);
//            Intent refresh = new Intent(this, confirm_OTP.class);
//            refresh.putExtra(currentLang, localeName);
//            startActivity(refresh);
//        } else {
//            Toast.makeText(Splash.this, "Language already selected!", Toast.LENGTH_SHORT).show();
//        }
    }

    //setLocale1() - set language to app
    //Params - Language name
    //Created on 25/01/2022
    //Return - NULL
    public void setLocale1(String localeName) {
        Log.d("langg12",localeName);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        editor.putString("language",localeName);
        editor.commit();
//        if (!localeName.equals(currentLanguage)) {
        myLocale = new Locale(localeName);
//        Locale.setDefault(myLocale);
//        Resources res = getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        Configuration conf = res.getConfiguration();
//        conf.locale = myLocale;
//        res.updateConfiguration(conf, dm);
        LocaleHelper.setLocale(Splash.this, localeName);
//        recreate();
    }

    //onBackPressed() - function called when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void onBackPressed() {
    }
}