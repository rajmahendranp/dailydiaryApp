package com.rampit.rask3.dailydiary;

import android.app.Activity;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


//Updated on 25/01/2022 by RAMPIT
//contains all common functions that can executed from anywhere in app
//printDifference() - get date difference between two dates
//drive() - check whether tablename exists or not
//drive() - get file and folder id
//checkForGooglePermissions() - Check whether required permissions provided by google account
//driveSetUp() - get logged in google account
//get_folder() - get folder id from firebase
//get_files1() -delete last file id from firebase
//exportDB() -Export current backup to firebase and external storage
//curl_internet() -run the google drive upload function periodically
//License1() -get license and DB version
//curl_debita() -check whether an table is available in database or not
//curl_debit() -get all deleted datas to shared preferrence
//stop_t() -Stop the timer
//deleteCache() -Delete application cache
//curl_brightness() - Set brightness for mobile ( NOT USED )
//auto_checkk() - get debit_type ( NOT USED )
//show_filee1() - upload to firebase server
//daily_firebase() - upload to firebase server
//putdata() - update data to firebase
//deleteDir() - Delete file from external storage
//MyApplicationActivityLifecycleListener() - Get total application lifecycle status
//onConfigurationChanged() - Default function
//onEnterForeground() - check whether app enters foreground
//onEnterBackground() - check whether app enters background
//updateTimerThread() - get timer
//lastlogin() - check last login time
//getDateDiff() - get day difference between two dates
//closeall() - clear preferrence and logout
//NIP() - Check for any NIP user to update
//CURR() - Check for any current user to update
//privilege() - get privilege of an user module
//updatenip() - update user to NIP
//updatecurr() - update user to current
//viewcheck() - get total view privileges for all modules
//closed() - Check for any user to close
//updateclose() - Update user to close
//syncEvent() - AsyncTask ( NOT USED )
//datee() - get today date
//closed1() - get closed debit amount
//beforebal() - get beforebalance debit amount
//populateRecyclerView23() - get total debits all datas
//emailid() - get email id
//emailid1() - get email id
//populateRecyclerView() -get current and NIP users
//populateRecyclerViewnip() -get NIPNIP users
//populateRecyclerView_new1() -get all session users
//updateorderid_new1() -update order id of all session users
//reorder_data() -reorder user by CID
//emergency() -just in case of any changes to be made by developer in database






public class Callback extends Application {
    //    Calendar myCalendar1;
    private static final AtomicBoolean applicationBackgrounded
            = new AtomicBoolean(true);

    private static final MyApplicationActivityLifecycleListener activityLifecycleListener
            = new MyApplicationActivityLifecycleListener();

    private static final long INTERVAL_BACKGROUND_STATE_CHANGE
            = 750L;
    final Integer nn = 0 ;
    private static WeakReference<Activity>
            currentActivityReference;
    String todaaaa,pdfname,License,my;
    File pdfFile;


    Integer hourss = 0 ;

    String file_id,folder_id,email,license;
    private static final int RC_AUTHORIZE_DRIVE = 100;
    private static final int REQUEST_PICK_IMAGE = 1;
    private static final int REQUEST_PICK_IMAGE1 = 1;
    Scope ACCESS_DRIVE_SCOPE = new Scope(Scopes.DRIVE_FILE);
    Scope SCOPE_EMAIL = new Scope(Scopes.EMAIL);
    Scope SCOPE_prfoirl = new Scope(Scopes.PROFILE);
    Scope SCOPE_openid = new Scope(Scopes.OPEN_ID);
    DriveServiceHelper mDriveServiceHelper;
    Drive googleDriveService;
    Timer t = new Timer();
    public void onCreate(){
        super.onCreate();
        this.registerActivityLifecycleCallbacks(
                Callback.activityLifecycleListener);
        drive("dd_drive");
        LocaleUtils.setLocale(new Locale("ta"));
        LocaleUtils.updateConfig(this, getBaseContext().getResources().getConfiguration());
    }

    //printDifference() - get date difference between two dates
    //Params - start and end date
    //Created on 25/01/2022
    //Return - NULL
    public void printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long mothsInMilli = daysInMilli * 30;
        long yearInMilli = mothsInMilli * 12;

        long elapsedYear = different / yearInMilli;
        different = different % yearInMilli;
        System.out.println("--------- elapsedYear : " + elapsedYear);

        long elapsedMonths = different / mothsInMilli;
        different = different % mothsInMilli;
        System.out.println("--------- elapsedMonths : " + elapsedMonths);

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;
        hourss = Integer.parseInt(String.valueOf(elapsedHours));
        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

//        duration = String.valueOf(elapsedDays);
        String yy = String.valueOf(elapsedYear);
        String mm = String.valueOf(elapsedMonths);
        String dd = String.valueOf(elapsedDays);
//        years = Integer.parseInt(yy);
//        months = Integer.parseInt(mm);
//        days = Integer.parseInt(dd);
//
        Log.d("duration",String.valueOf(elapsedYear)+" "+String.valueOf(elapsedMonths)+" "+String.valueOf(elapsedDays)+" "+String.valueOf(elapsedHours)+" "+String.valueOf(elapsedMinutes)+" "+String.valueOf(elapsedSeconds));
        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
    }

    //drive() - check whether tablename exists or not
    //Params - tablename
    //Created on 25/01/2022
    //Return - tablename exists or not
    public boolean drive(String tableName) {
        boolean isExist = false;
        SQLiteHelper sqlite = new SQLiteHelper(this);
        SQLiteDatabase database = sqlite.getReadableDatabase();
        Cursor cursor = database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                isExist = true;
                drive();
            }
            cursor.close();
        }
        return isExist;
    }

    //drive() - get file and folder id
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

    //checkForGooglePermissions() - Check whether required permissions provided by google account
    //Params - Application context
    //Created on 25/01/2022
    //Return - NULL
    private void checkForGooglePermissions(Context context) {

        if (!GoogleSignIn.hasPermissions(
                GoogleSignIn.getLastSignedInAccount(getApplicationContext()),
                ACCESS_DRIVE_SCOPE,
                SCOPE_EMAIL,
                SCOPE_prfoirl,
                SCOPE_openid)) {
            Log.d("Bitmap1","nope");
//            progre();
//            GoogleSignIn.requestPermissions(
//                   context,
//                    RC_AUTHORIZE_DRIVE,
//                    GoogleSignIn.getLastSignedInAccount(getApplicationContext()),
//                    ACCESS_DRIVE_SCOPE,
//                    SCOPE_EMAIL ,
//                    SCOPE_prfoirl,
//                    SCOPE_openid);
        } else {
            Log.d("Bitmap1","yes");
//            Toast.makeText(this, "Permission to access Drive and Email has been granted", Toast.LENGTH_SHORT).show();
            driveSetUp(context);

        }

    }

    //driveSetUp() - get logged in google account
    //Params - Application context
    //Created on 25/01/2022
    //Return - NULL
    private void driveSetUp(final Context context) {

        GoogleSignInAccount mAccount = GoogleSignIn.getLastSignedInAccount(context);
        email = mAccount.getEmail();
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
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences pref_folder = getApplicationContext().getSharedPreferences("folder", 0); // 0 - for private mode
        String fold = pref_folder.getString("folder_name","DailyDiary backups");
        get_folder(fold,context);
//        if(folder_id == null || folder_id.equalsIgnoreCase("") || folder_id.equalsIgnoreCase("null")){
//            mDriveServiceHelper.createFolder("DailyDiary backups",  "")
//                    .addOnSuccessListener(new OnSuccessListener<GoogleDriveFileHolder>() {
//                        @Override
//                        public void onSuccess(GoogleDriveFileHolder googleDriveFileHolder) {
////                        progre();
//                            Calendar myCalendar = Calendar.getInstance();
//                            String myFormat = "yyyy/MM/dd"; //In which you need put here
//                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//                            String dateString = sdf.format(myCalendar.getTime());
//                            SQLiteHelper sqlite = new SQLiteHelper(context);
//                            SQLiteDatabase database = sqlite.getWritableDatabase();
//                            ContentValues values = new ContentValues();
//                            values.put("folder_id", String.valueOf(googleDriveFileHolder.getId()));
//                            values.put("created_date", dateString);
//                            database.insert("dd_drive", null, values);
//                            sqlite.close();
//                            database.close();
//                            folder_id = String.valueOf(googleDriveFileHolder.getId());
//                            exportDB(context);
//                            Log.i("Bitmap1", "Successfully Uploaded. File Id :" + googleDriveFileHolder.getId());
////                            Toast.makeText(Settings_activity.this, "Successfully Uploaded Drive ", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
////                        progre();
//                            Log.i("Bitmap2", "Failed to Upload. File Id :" + e.getMessage());
////                            Toast.makeText(Settings_activity.this, "Failed to Upload ", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//        }
//        else{
//            exportDB(context);
//        }

    }

    //get_folder() - get folder id from firebase
    //Params - folder name and Application context
    //Created on 25/01/2022
    //Return - NULL
    public void get_folder(String folder_nnnn,Context context){
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
                                                FirebaseApp.initializeApp(context);
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
//                                                                exportDB(context);
                                                                get_files1(context);
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
//                                            progre();
                                            Log.i("Bitmap23", "Failed to Upload. File Id :" + e.getMessage());
//                                            Toast.makeText(ExportDB.this, "Failed to Upload ", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                        else{
//                            exportDB(context);
                            get_files1(context);
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
                                                        FirebaseApp.initializeApp(context);
                                                        FirebaseDatabase database111 = FirebaseDatabase.getInstance();
                                                        final DatabaseReference myRef11 = database111.getReference("dd_folders");
                                                        String mGroupId = myRef11.push().getKey();
                                                        Log.d("mGroupId",String.valueOf(mGroupId));
                                                        myRef11.child(mGroupId).child("id").setValue(mGroupId);
                                                        Calendar myCalendar = Calendar.getInstance();
                                                        String myFormat = "yyyy/MM/dd"; //In which you need put here
                                                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                                        String dateString = sdf.format(myCalendar.getTime());
//                                                        exportDB(context);
                                                        get_files1(context);
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

                                                    Log.i("Bitmap23", "Failed to Upload. File Id :" + e.getMessage());
//                                                    Toast.makeText(ExportDB.this, "Failed to Upload ", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }
                                else{
//                                    exportDB(context);
                                    get_files1(context);
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

    //get_files1() -delete last file id from firebase
    //Params - Application context
    //Created on 25/01/2022
    //Return - NULL
    public void get_files1(Context context){
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

                        exportDB(context);

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

                                exportDB(context);

                            }

                        }


                    }

                } else {
                    Log.d("gettingids", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    //exportDB() -Export current backup to firebase and external storage
    //Params - Application context
    //Created on 25/01/2022
    //Return - NULL
    private void exportDB(final Context context) {
        // TODO Auto-generated method stub
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        String comname = pref.getString("NAME","");
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
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                String myFormat1 = "dd/MM/yyyy";//In which you need put here
                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
//        todaaaa = sdf1.format(c.getTime());
                DateFormat dff = new SimpleDateFormat("MMM_d_yyyy");
                String todaaaa = dff.format(c.getInstance().getTime());
                File Directory = new File("/sdcard/DailyDiary/");
                Directory.mkdirs();
                String outFileName = Environment.getExternalStorageDirectory()+"/Dailydiary_downloads/"+"/DD_RPP"+"_"+comname+"-"+todaaaa+"_Backup_Mail";
//                String outFileName = Environment.getExternalStorageDirectory()+"/Backup";
//                String outFileName = Environment.getExternalStorageDirectory()+"/LOGIN";
                String inFileName = getApplicationContext().getDatabasePath("LOGIN").getPath();
                final String currentDBPath ="/data/user/0/com.rampit.rask3.dailydiary/databases/LOGIN";
//                String  currentDBPath= "/data/" +"//user//" +"//0//" + "com.rampit.rask3.dailydiaryv1"
//                        + "//databases//" + "LOGIN";
                String backupDBPath  = "/BackupFolder/LOGIN";
                File currentDB = new File( inFileName);
                File backupDB = new File( outFileName);
                SQLiteHelper sql = new SQLiteHelper(context);
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
                                        Calendar myCalendar = Calendar.getInstance();
                                        String myFormat = "yyyy/MM/dd"; //In which you need put here
                                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                        String dateString = sdf.format(myCalendar.getTime());
                                        SQLiteHelper sqlite = new SQLiteHelper(context);
                                        SQLiteDatabase database = sqlite.getWritableDatabase();
                                        ContentValues values = new ContentValues();
                                        values.put("file_id", String.valueOf(googleDriveFileHolder.getId()));
                                        values.put("created_date", dateString);
//                                        database.insert("dd_drive", null, values);
                                        database.update("dd_drive", values, "id = ?", new String[]{"1"});
                                        sqlite.close();
                                        database.close();
                                        SharedPreferences prefs1 = context.getSharedPreferences("drive", 0);
                                        SharedPreferences.Editor editor = prefs1.edit();
                                        Calendar c = Calendar.getInstance();
                                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                        String formattedDate = df.format(c.getTime());
                                        editor.putString("last_upload",formattedDate);
                                        editor.apply();

                                        Log.i("Bitmap1", "Successfully Uploaded. File Id :" + googleDriveFileHolder.getId());
//                                        Toast.makeText(Settings_activity.this, "Successfully Uploaded Drive ", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
//                                        progre();
                                        Log.i("Bitmap2", "Failed to Upload. File Id :" + e.getMessage());
//                                        Toast.makeText(Settings_activity.this, "Failed to Upload ", Toast.LENGTH_SHORT).show();
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

    //curl_internet() -run the google drive upload function periodically
    //Params - Application context
    //Created on 25/01/2022
    //Return - NULL
    public void curl_internet(final Context context){
        Timer t = new Timer();
//Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {
                                  @Override
                                  public void run() {
                                       SharedPreferences pref00 = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                      SharedPreferences pref_folder = getApplicationContext().getSharedPreferences("folder", 0); // 0 - for private mode
                                      String fold = pref_folder.getString("folder_name","DailyDiary backups");


                                      //Called each time when 1000 milliseconds (1 second) (the period parameter)
                                      final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                                      SharedPreferences prefs1 = context.getSharedPreferences("drive", 0);
                                      SharedPreferences.Editor editor = prefs1.edit();
                                      String last_upload = prefs1.getString("last_upload","");
                                      String drive_auto_backup = prefs1.getString("drive_auto_backup","0");
                                      String drive_auto_backup_time = prefs1.getString("drive_auto_backup_time","5");
                                      Integer drive_auto_backup_time1 = Integer.parseInt(drive_auto_backup_time);

                                      NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();
                                      SharedPreferences dd_license = getApplicationContext().getSharedPreferences("dd_license", 0); // 0 - for private mode
                                      String dd_license1 = dd_license.getString("dd_license","0");
                                      Log.d("folder_name",fold+" "+last_upload);
                                      SQLiteHelper sqlite = new SQLiteHelper(context);
                                      SQLiteDatabase database = sqlite.getReadableDatabase();
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
                                                  String License = cursor.getString(index);

                                                  license = License;
                                                  SharedPreferences pref = getApplicationContext().getSharedPreferences("dd_license", 0); // 0 - for private mode
                                                  String ii = pref.getString("dd_license","");
                                                  Log.d("folder_name11",fold+" "+last_upload+" "+License+" "+ii);

                                                  if(License == null || License.equalsIgnoreCase("") || ii.equalsIgnoreCase("0")){
                                                      if (activeNetworkInfo != null) { // connected to the internet
//                                          Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();
                                                          String sss = String.valueOf(activeNetworkInfo.getTypeName());
                                                          Log.d("y7",sss);
                                                          if(last_upload == null || last_upload.equalsIgnoreCase("") || last_upload.equalsIgnoreCase("null")){

                                                              if(drive_auto_backup.equalsIgnoreCase("1")){
                                                                  checkForGooglePermissions(context);
                                                                  Calendar c = Calendar.getInstance();
                                                                  String pattern = "dd/MM/yyyy HH:mm:ss";
                                                                  SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                                                  String formattedDate = df.format(c.getTime());
                                                                  SimpleDateFormat dateFormat1 = new SimpleDateFormat(pattern);
                                                                  String ss = dateFormat1.format(c.getTime());
//                                              String ss1 =debit_date;
                                                                  SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                                                  try {
                                                                      Date date = format.parse(ss);
                                                                      Date date1 = format.parse(formattedDate);
                                                                      printDifference(date,date1);
                                                                      System.out.println(date);
                                                                  } catch (ParseException e) {
                                                                      e.printStackTrace();
                                                                  }
                                                              }

                                                          }
                                                          else{
                                                              Log.d("last_upload",last_upload+" "+drive_auto_backup_time);
                                                              Calendar c = Calendar.getInstance();
                                                              String pattern = "dd/MM/yyyy HH:mm:ss";
                                                              SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                                              String formattedDate = df.format(c.getTime());
                                                              SimpleDateFormat dateFormat1 = new SimpleDateFormat(pattern);
                                                              SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                                              try {
                                                                  Date date = format.parse(last_upload);
                                                                  Date date1 = format.parse(formattedDate);
                                                                  printDifference(date,date1);
                                                                  System.out.println(date);
                                                                  Log.d("hourss",String.valueOf(hourss));
                                                                  if(hourss>=drive_auto_backup_time1){
                                                                      checkForGooglePermissions(context);
                                                                  }
                                                              } catch (ParseException e) {
                                                                  e.printStackTrace();
                                                              }
                                                          }


                                                      }else{
                                                          Log.d("y7","null");
//                                          Show_loader(context,true,true);


                                                      }
                                                  }
                                                  else if(ii.equalsIgnoreCase(License)){
                                                      if (activeNetworkInfo != null) { // connected to the internet
//                                          Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();
                                                          String sss = String.valueOf(activeNetworkInfo.getTypeName());
                                                          Log.d("y7213",sss);
                                                          if(last_upload == null || last_upload.equalsIgnoreCase("") || last_upload.equalsIgnoreCase("null")){

                                                              if(drive_auto_backup.equalsIgnoreCase("1")){
                                                                  checkForGooglePermissions(context);
                                                                  Calendar c = Calendar.getInstance();
                                                                  String pattern = "dd/MM/yyyy HH:mm:ss";
                                                                  SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                                                  String formattedDate = df.format(c.getTime());
                                                                  SimpleDateFormat dateFormat1 = new SimpleDateFormat(pattern);
                                                                  String ss = dateFormat1.format(c.getTime());
//                                              String ss1 =debit_date;
                                                                  SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                                                  try {
                                                                      Date date = format.parse(ss);
                                                                      Date date1 = format.parse(formattedDate);
                                                                      printDifference(date,date1);
                                                                      System.out.println(date);
                                                                  } catch (ParseException e) {
                                                                      e.printStackTrace();
                                                                  }
                                                              }

                                                          }
                                                          else{
                                                              Log.d("last_upload",last_upload+" "+drive_auto_backup_time);
                                                              Calendar c = Calendar.getInstance();
                                                              String pattern = "dd/MM/yyyy HH:mm:ss";
                                                              SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                                              String formattedDate = df.format(c.getTime());
                                                              SimpleDateFormat dateFormat1 = new SimpleDateFormat(pattern);
                                                              SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                                              try {
                                                                  Date date = format.parse(last_upload);
                                                                  Date date1 = format.parse(formattedDate);
                                                                  printDifference(date,date1);
                                                                  System.out.println(date);
                                                                  Log.d("hourss",String.valueOf(hourss));
//                                                                  drive_auto_backup_time1
                                                                  if(hourss>=drive_auto_backup_time1){
                                                                      checkForGooglePermissions(context);
                                                                  }
                                                              } catch (ParseException e) {
                                                                  e.printStackTrace();
                                                              }
                                                          }


                                                      }else{
                                                          Log.d("y7","null");
//                                          Show_loader(context,true,true);


                                                      }
                                                  }
                                                  else{
                                                      Log.d("last_upload",last_upload+" "+drive_auto_backup_time);
                                                  }
                                              }
                                              while (cursor.moveToNext());
                                              cursor.close();
                                              sqlite.close();
                                              database.close();
                                          }else{
                                              if(dd_license1.equalsIgnoreCase("0")){
                                                  if (activeNetworkInfo != null) { // connected to the internet
//                                          Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();
                                                      String sss = String.valueOf(activeNetworkInfo.getTypeName());
                                                      Log.d("y7",sss);
                                                      if(last_upload == null || last_upload.equalsIgnoreCase("") || last_upload.equalsIgnoreCase("null")){

                                                          if(drive_auto_backup.equalsIgnoreCase("1")){
                                                              checkForGooglePermissions(context);
                                                              Calendar c = Calendar.getInstance();
                                                              String pattern = "dd/MM/yyyy HH:mm:ss";
                                                              SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                                              String formattedDate = df.format(c.getTime());
                                                              SimpleDateFormat dateFormat1 = new SimpleDateFormat(pattern);
                                                              String ss = dateFormat1.format(c.getTime());
//                                              String ss1 =debit_date;
                                                              SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                                              try {
                                                                  Date date = format.parse(ss);
                                                                  Date date1 = format.parse(formattedDate);
                                                                  printDifference(date,date1);
                                                                  System.out.println(date);
                                                              } catch (ParseException e) {
                                                                  e.printStackTrace();
                                                              }
                                                          }

                                                      }
                                                      else{
                                                          Log.d("last_upload",last_upload+" "+drive_auto_backup_time);
                                                          Calendar c = Calendar.getInstance();
                                                          String pattern = "dd/MM/yyyy HH:mm:ss";
                                                          SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                                          String formattedDate = df.format(c.getTime());
                                                          SimpleDateFormat dateFormat1 = new SimpleDateFormat(pattern);
                                                          SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                                          try {
                                                              Date date = format.parse(last_upload);
                                                              Date date1 = format.parse(formattedDate);
                                                              printDifference(date,date1);
                                                              System.out.println(date);
                                                              Log.d("hourss",String.valueOf(hourss));
                                                              if(hourss>=drive_auto_backup_time1){
                                                                  checkForGooglePermissions(context);
                                                              }
                                                          } catch (ParseException e) {
                                                              e.printStackTrace();
                                                          }
                                                      }


                                                  }else{
                                                      Log.d("y7","null");
//                                          Show_loader(context,true,true);


                                                  }
                                              }
                                              else{

                                              }
                                              cursor.close();
                                              sqlite.close();
                                              database.close();
                                          }
                                      }else{
                                          if(dd_license1.equalsIgnoreCase("0")){
                                              if (activeNetworkInfo != null) { // connected to the internet
//                                          Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();
                                                  String sss = String.valueOf(activeNetworkInfo.getTypeName());
                                                  Log.d("y7",sss);
                                                  if(last_upload == null || last_upload.equalsIgnoreCase("") || last_upload.equalsIgnoreCase("null")){

                                                      if(drive_auto_backup.equalsIgnoreCase("1")){
                                                          checkForGooglePermissions(context);
                                                          Calendar c = Calendar.getInstance();
                                                          String pattern = "dd/MM/yyyy HH:mm:ss";
                                                          SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                                          String formattedDate = df.format(c.getTime());
                                                          SimpleDateFormat dateFormat1 = new SimpleDateFormat(pattern);
                                                          String ss = dateFormat1.format(c.getTime());
//                                              String ss1 =debit_date;
                                                          SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                                          try {
                                                              Date date = format.parse(ss);
                                                              Date date1 = format.parse(formattedDate);
                                                              printDifference(date,date1);
                                                              System.out.println(date);
                                                          } catch (ParseException e) {
                                                              e.printStackTrace();
                                                          }
                                                      }

                                                  }
                                                  else{
                                                      Log.d("last_upload",last_upload+" "+drive_auto_backup_time);
                                                      Calendar c = Calendar.getInstance();
                                                      String pattern = "dd/MM/yyyy HH:mm:ss";
                                                      SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                                      String formattedDate = df.format(c.getTime());
                                                      SimpleDateFormat dateFormat1 = new SimpleDateFormat(pattern);
                                                      SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                                      try {
                                                          Date date = format.parse(last_upload);
                                                          Date date1 = format.parse(formattedDate);
                                                          printDifference(date,date1);
                                                          System.out.println(date);
                                                          Log.d("hourss",String.valueOf(hourss));
                                                          if(hourss>=drive_auto_backup_time1){
                                                              checkForGooglePermissions(context);
                                                          }
                                                      } catch (ParseException e) {
                                                          e.printStackTrace();
                                                      }
                                                  }


                                              }else{
                                                  Log.d("y7","null");
//                                          Show_loader(context,true,true);


                                              }
                                          }
                                          else{

                                          }
                                          cursor.close();
                                          sqlite.close();
                                          database.close();
                                      }






                                  }
                              },
//Set how long before to start calling the TimerTask (in milliseconds)
                0,
//Set the amount of time between each execution (in milliseconds)
                900000);
    }

    //License1() -get license and DB version
    //Params - Application context
    //Created on 25/01/2022
    //Return - NULL
    public void License1(Context context){
        t = new Timer();
//Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {
                                  @Override
                                  public void run() {
        SQLiteHelper sqlite = new SQLiteHelper(context);
        SQLiteDatabase database = sqlite.getReadableDatabase();
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
                    String License = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("version");
                    String version = cursor.getString(index);
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

                    if(version == null || version.equalsIgnoreCase("")){
//                        License = "no";
                    }else {
                        Integer vv = Integer.parseInt(version);
                        if(vv>=15){
                            Log.d("dd_licccccc",String.valueOf(vv));
                            curl_debita("dd_delete",context);
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
},
//Set how long before to start calling the TimerTask (in milliseconds)
        0,
//Set the amount of time between each execution (in milliseconds)
        12000);

    }

    //curl_debita() -check whether an table is available in database or not
    //Params - table name and Application context
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

    //curl_debit() -get all deleted datas to shared preferrence
    //Params - Application context
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
                                      String NIP =
//                "SELECT c.*,a.id as ID , sum(c.debit_amount) as sum_debit FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 0 AND c.debit_date < ?";
                                              "SELECT  sum(debit_amount) as sum_debit,sum(document_charge) as sum_doc,sum(interest) as sum_inter,sum(collection_amount) as sum_col,sum(discount) as sum_dis,sum(other_fee) as sum_oth" +
                                                      ",sum(credit) as sum_cre,sum(debit) as sum_deb FROM dd_delete WHERE tracking_id = ? ";
                                      Cursor cursor = database.rawQuery(NIP, new String[] {String.valueOf(ses)});

                                      if (cursor != null){
                                          if(cursor.getCount() != 0){
                                              cursor.moveToFirst();
                                              do{
                                                  int index;
                                                  index = cursor.getColumnIndexOrThrow("sum_debit");
                                                  String sum_debit = cursor.getString(index);
//                                                  Log.d("debit_amount_debit",sum_debit);
                                                  index = cursor.getColumnIndexOrThrow("sum_doc");
                                                  String sum_doc = cursor.getString(index);
                                                  index = cursor.getColumnIndexOrThrow("sum_inter");
                                                  String  sum_inter = cursor.getString(index);
                                                  index = cursor.getColumnIndexOrThrow("sum_col");
                                                  String sum_col = cursor.getString(index);
                                                  index = cursor.getColumnIndexOrThrow("sum_dis");
                                                  String sum_dis = cursor.getString(index);
                                                  index = cursor.getColumnIndexOrThrow("sum_oth");
                                                  String sum_oth = cursor.getString(index);
                                                  index = cursor.getColumnIndexOrThrow("sum_cre");
                                                  String sum_cre = cursor.getString(index);
                                                  index = cursor.getColumnIndexOrThrow("sum_deb");
                                                  String sum_deb = cursor.getString(index);
                                                  editor.putString("sum_debit",sum_debit);
                                                  editor.putString("sum_doc",sum_doc);
                                                  editor.putString("sum_inter",sum_inter);
                                                  editor.putString("sum_col",sum_col);
                                                  editor.putString("sum_dis",sum_dis);
                                                  editor.putString("sum_oth",sum_oth);
                                                  editor.putString("sum_cre",sum_cre);
                                                  editor.putString("sum_deb",sum_deb);
                                                  editor.apply();



//                    pdfpass123 = cursor.getString(index);

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

    //stop_t() -Stop the timer
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void stop_t(){
        if(t != null) {
            t.cancel();
        }
    }

    //deleteCache() -Delete application cache
    //Params - Application context
    //Created on 25/01/2022
    //Return - NULL
    public static void deleteCache(Context context) {
        Glide.get(context).clearMemory();

        try {
            File dir = context.getCacheDir();
          deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}


    }

    //curl_brightness() - Set brightness for mobile ( NOT USED )
    //Params - Application context
    //Created on 25/01/2022
    //Return - NULL
    public void curl_brightness(Context context){
        Timer t = new Timer();
//Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {
                                  @Override
                                  public void run() {
                                      //Called each time when 1000 milliseconds (1 second) (the period parameter)


                                      Integer brightness =
                                              Settings.System.getInt(getApplicationContext().getContentResolver(),
                                                      Settings.System.SCREEN_BRIGHTNESS, 0);
                                      Log.d("brightness",String.valueOf(brightness));
                                      if(brightness<=150){
                                          int bb = 150 ;
                                          Log.d("brightness1",String.valueOf(bb));
                                          Settings.System.putInt(context.getContentResolver(),
                                                  Settings.System.SCREEN_BRIGHTNESS, bb);
                                      }





                                  }
                              },
//Set how long before to start calling the TimerTask (in milliseconds)
                0,
//Set the amount of time between each execution (in milliseconds)
                12000);
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

    //auto_checkk() - get debit_type ( NOT USED )
    //Params - Session
    //Created on 25/01/2022
    //Return - NULL
    public void auto_checkk(String ses) {
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
                my = "SELECT b.customer_name,b.id as ID,b.CID,b.debit_type,b.tracking_id,b.order_id_new,b.phone_1,b.location,b.previous_order_id as prevoid FROM" +
                        " dd_customers b LEFT JOIN dd_account_debit a on a.customer_id = b.id WHERE  b.tracking_id = ? AND a.active_status = 0 ORDER BY b.id ";
            Cursor cursor = database.rawQuery(my,new String[]{String.valueOf(ses)});
            if (cursor != null) {
                if (cursor.getCount() != 0) {
                    cursor.moveToFirst();
                    do {
                        int index;
                        index = cursor.getColumnIndexOrThrow("debit_type");
                        Integer debittyppe = cursor.getInt(index);
//                        index = cursor.getColumnIndexOrThrow("active_status");
//                        Integer actived = cursor.getInt(index);
                        index = cursor.getColumnIndexOrThrow("CID");
                        String cid1 = cursor.getString(index);
                        index = cursor.getColumnIndexOrThrow("customer_name");
                        String customer_name = cursor.getString(index);
//                        Log.d("ioi2",cid1);
                        if(debittyppe.equals(0) || debittyppe.equals(1) || debittyppe.equals(2)){
//                            if(actived.equals(0)){
                                Log.d("actived", String.valueOf(debittyppe)+" "+cid1);
                                Log.d("customer_name",customer_name);
//                            }else{
//                            }
                        }else{
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
            }else {
                cursor.close();
                sqlite.close();
                database.close();
            }

    }

    //show_filee1() - upload to firebase server
    //Params - filename , license and SharedPreferences
    //Created on 25/01/2022
    //Return - NULL
    public void show_filee1(String value ,String liccc , SharedPreferences pref){
//        progressbar_load();

//        SharedPreferences pref = currentActivityReference.get().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String isLoading = pref.getString("isLoading","no");
        Log.d("value_lic",value+" "+liccc+" "+isLoading);
        if( isLoading.equalsIgnoreCase("uploading") || isLoading.equalsIgnoreCase("yes")){
        }else{
            License = liccc ;
            FirebaseStorage storage;
            storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            storageRef.child(String.valueOf(value)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    Log.d("uuurrrllll_got_call",String.valueOf(uri));
//                    SharedPreferences pref = currentActivityReference.get().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    Calendar c = Calendar.getInstance();
                    DateFormat dff = new SimpleDateFormat("dd/MM/yyyy hh:mm:aaa");
                    String todaaaa = dff.format(c.getInstance().getTime());
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("isLoading","yes");
                    editor.putString("isLoaded_time",todaaaa);
                    editor.apply();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            Log.d("uuurrrllll_got_call","File Not Found");
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
                            Log.d("backupname_call",backupDBPath);
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
                            pdfFile = backupDB;
                            pdfname = "DD_RRP"+"-"+todaaaa+"_Backup_Mail";
                            SimpleDateFormat dff10 = new SimpleDateFormat("dd-MM-yyyy");
                            String formattedDate = dff10.format(c.getTime());
                            daily_firebase(Uri.fromFile(backupDB),License,formattedDate,pref);
                        }
                    });
        }
    }

    //daily_firebase() - upload to firebase server
    //Params - file uri ,filename , filename and SharedPreferences
    //Created on 25/01/2022
    //Return - NULL
    public void daily_firebase(Uri uri, String value, String filee,SharedPreferences pref){
        Log.d("dddata_call",value+" "+filee);
        FirebaseStorage storage;
        storage = FirebaseStorage.getInstance();
        StorageMetadata metadata = new StorageMetadata.Builder()
//                .setContentType("image/jpeg")
                .build();
//        SharedPreferences pref = currentActivityReference.get().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("isLoading","uploading");
        editor.apply();
        StorageReference ref = storage.getReference().child(String.valueOf(License)+"/"+"daily"+"/"+String.valueOf(filee));
        String finalValue = value;
        ref.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("dddata_call","success");
//                        SharedPreferences pref = currentActivityReference.get().getSharedPreferences("MyPref", 0); // 0 - for private mode
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
//                        myRef11.child(mGroupId).child("license").setValue(License);
//                        myRef11.child(mGroupId).child("filename").setValue(filee);
//                        myRef11.child(mGroupId).child("created_date").setValue(curdate1);
//                        myRef11.child(mGroupId).child("updated_date").setValue("");
                        SharedPreferences.Editor editor = pref.edit();
                        Calendar c = Calendar.getInstance();
                        DateFormat dff = new SimpleDateFormat("dd/MM/yyyy hh:mm:aaa");
                        String todaaaa = dff.format(c.getInstance().getTime());
                        editor.putString("isLoading","yes");
                        editor.putString("isLoaded_time",todaaaa);
                        editor.apply();

                        dd_files_license_upload(license,filee,pref);

                     /*   FirebaseFirestore db1 = FirebaseFirestore.getInstance();
                        Map<String, Object> city111 = new HashMap<>();
                        city111.put("id", mGroupId);
                        city111.put("license", License);
                        city111.put("filename",filee);
                        city111.put("created_date", curdate1);
                        city111.put("for_order", curdate11);
                        city111.put("updated_date", "");
                        db1.collection("dd_files_license").document(String.valueOf(mGroupId))
                                .set(city111)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("ggg", "DocumentSnapshot successfully written!");
                                        SharedPreferences.Editor editor = pref.edit();
                                        Calendar c = Calendar.getInstance();
                                        DateFormat dff = new SimpleDateFormat("dd/MM/yyyy hh:mm:aaa");
                                        String todaaaa = dff.format(c.getInstance().getTime());
                                        editor.putString("isLoading","yes");
                                        editor.putString("isLoaded_time",todaaaa);
                                        editor.apply();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("ggg", "Error writing document", e);
                                        SharedPreferences.Editor editor = pref.edit();
                                        Calendar c = Calendar.getInstance();
                                        DateFormat dff = new SimpleDateFormat("dd/MM/yyyy hh:mm:aaa");
                                        String todaaaa = dff.format(c.getInstance().getTime());
                                        editor.putString("isLoading","yes");
                                        editor.putString("isLoaded_time",todaaaa);
                                        editor.apply();
                                    }
                                });*/
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("dddata_call","failure"+" "+String.valueOf(e));
//                        SharedPreferences pref = currentActivityReference.get().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("isLoading","fail");
                        editor.apply();
                    }
                });
    }

    //dd_files_license_upload() - upload backup data to firebase
    //Params - license, filename
    //Created on 25/01/2022
    //Return - NULL
    public void dd_files_license_upload(String ss0,String datetime,SharedPreferences pref){
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
                                        SharedPreferences.Editor editor = pref.edit();
                                        Calendar c = Calendar.getInstance();
                                        DateFormat dff = new SimpleDateFormat("dd/MM/yyyy hh:mm:aaa");
                                        String todaaaa = dff.format(c.getInstance().getTime());
                                        editor.putString("isLoading","yes");
                                        editor.putString("isLoaded_time",todaaaa);
                                        editor.apply();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("ggg", "Error writing document", e);
                                        SharedPreferences.Editor editor = pref.edit();
                                        Calendar c = Calendar.getInstance();
                                        DateFormat dff = new SimpleDateFormat("dd/MM/yyyy hh:mm:aaa");
                                        String todaaaa = dff.format(c.getInstance().getTime());
                                        editor.putString("isLoading","yes");
                                        editor.putString("isLoaded_time",todaaaa);
                                        editor.apply();
//                                        progre();
//                                        Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
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
                                            SharedPreferences.Editor editor = pref.edit();
                                            Calendar c = Calendar.getInstance();
                                            DateFormat dff = new SimpleDateFormat("dd/MM/yyyy hh:mm:aaa");
                                            String todaaaa = dff.format(c.getInstance().getTime());
                                            editor.putString("isLoading","yes");
                                            editor.putString("isLoaded_time",todaaaa);
                                            editor.apply();
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

    //putdata() - update data to firebase
    // Params - filename
    //Created on 25/01/2022
    //Return - NULL
    public void putdata(String filee){
        FirebaseDatabase database111 = FirebaseDatabase.getInstance();
        final DatabaseReference myRef11 = database111.getReference("dd_files_license");
        String mGroupId = myRef11.push().getKey();
        Log.d("mGroupId",String.valueOf(mGroupId));
        myRef11.child(mGroupId).child("id").setValue(mGroupId);
        DateFormat dff1 = new SimpleDateFormat("ddMMyyyyHHmmss");
        Calendar cal = Calendar.getInstance();
        String curdate1 = dff1.format(cal.getInstance().getTime());
        FirebaseFirestore db1 = FirebaseFirestore.getInstance();
        Map<String, Object> city111 = new HashMap<>();
        city111.put("id", mGroupId);
        city111.put("license", License);
        city111.put("filename",filee);
        city111.put("created_date", curdate1);
        city111.put("updated_date", "");
        db1.collection("dd_files_license").document(String.valueOf(mGroupId))
                .set(city111)
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

    //deleteDir() - Delete file from external storage
    // Params - File location
    //Created on 25/01/2022
    //Return - deleted or not
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    //MyApplicationActivityLifecycleListener() - Get total application lifecycle status
    // Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private static class MyApplicationActivityLifecycleListener
            implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
            Log.d("APP_STATUS","CREATED");
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {
            Log.d("APP_STATUS","STARTED");
        }

        private void determineForegroundStatus() {
            if(applicationBackgrounded.get()){
                Callback.onEnterForeground();
                applicationBackgrounded.set(false);
            }
        }

        private void determineBackgroundStatus() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(!applicationBackgrounded.get() &&
                            currentActivityReference == null) {

                        applicationBackgrounded.set(true);
                        Callback.onEnterBackground();
                    }
                }
            }, INTERVAL_BACKGROUND_STATE_CHANGE);
        }
        @Override
        public void onActivityResumed(Activity activity) {
            Callback.currentActivityReference =
                    new WeakReference<>(activity);
            determineForegroundStatus();
//        Log.d("APP_STATUS1","RESUMED");
        }
        @Override
        public void onActivityPaused(Activity activity) {
            Callback.currentActivityReference = null;
            determineBackgroundStatus();
//        Log.d("APP_STATUS1","PAUESD");
        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {
            Log.d("APP_STATUS","STOPPED");
        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
            Log.d("APP_STATUS","SAVED");
        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
            Log.d("APP_STATUS","DESTROYED");
        }
    }

    //onConfigurationChanged() - Default function
    // Params - Configuration
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleUtils.updateConfig(this, newConfig);
    }

    Calendar myCalendar1 = Calendar.getInstance();
    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    //    String myFormat1 = "dd/MM/yyyy";//In which you need put here
    String formattedDate = df.format(c.getTime());
    String id,adddd,edittt,deleteee,viewww,did;
    SQLiteHelper sqlite;
    Set<String> set = new HashSet<String>();
    Context context;
    Integer see,see2,see1,cle,cle1,cloosed,closeddisco,befo,sesssion;

    SQLiteDatabase database;
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> Names1 = new ArrayList<>();
    ArrayList<String> morr = new ArrayList<>();
    ArrayList<String> evee = new ArrayList<>();
    String ss,zero,one,two,three,four,five,six,seven,eight,nine,ten,eleven,twelve,thirteen,fourteen,fifteen,sixteen,seventeen,eighteen,nineteen,twenty,twentyone,twentytwo,twentythree;
    static Integer nnn = 0;
    static private long startTime = 0L;

    static  private Handler customHandler = new Handler();

    static long timeInMilliseconds = 0L;
    static  long timeSwapBuff = 0L;
    static  long updatedTime = 0L;

    //onEnterForeground() - check whether app enters foreground
    // Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static void onEnterForeground() {
        timeSwapBuff += timeInMilliseconds;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeSwapBuff);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeSwapBuff);
        long hours = minutes / 60;
        customHandler.removeCallbacks(updateTimerThread);
        Log.d("APP_STATUS1","FOREGROUND"+"  "+String.valueOf(timeSwapBuff)+" "+String.valueOf(hours)+" "+String.valueOf(minutes)+" "+String.valueOf(seconds));
        timeInMilliseconds = 0L;
        timeSwapBuff = 0L;
        if(hours>=6){
            SharedPreferences pref = currentActivityReference.get().getSharedPreferences("MyPref", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.putInt("isloginn",0);
            editor.commit();
            Intent i = new Intent(currentActivityReference.get(),Splash.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            currentActivityReference.get().startActivity(i);
        }

        //This is where you'll handle logic you want to execute when your application enters the foreground
    }
    //onEnterBackground() - check whether app enters background
    // Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static void onEnterBackground() {
        if(nnn.equals(0)){
            nnn = nnn + 1 ;
        }else{
            startTime = SystemClock.uptimeMillis();
            customHandler.postDelayed(updateTimerThread, 0);
            nnn = nnn + 1 ;
            Log.d("APP_STATUS1","BACKGROUND");
        }
        //This is where you'll handle logic you want to execute when your application enters the background
    }

    //updateTimerThread() - get timer
    // Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private static  Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
//            timerValue.setText("" + mins + ":"
//                    + String.format("%02d", secs) + ":"
//                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }

    };

    //lastlogin() - check last login time
    // Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void lastlogin() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        //    String myFormat1 = "dd/MM/yyyy";//In which you need put here
        String formattedDate = df.format(c.getTime());
        SharedPreferences pref = getApplicationContext().getSharedPreferences("login", 0); // 0 - for private mode
        String ss2 = String.valueOf(c.getTime());
        Log.d("datedd", ss2);
        String pattern = "dd/MM/yyyy";

        SimpleDateFormat dateFormat1 = new SimpleDateFormat(pattern);
        String ss = dateFormat1.format(c.getTime());
        String ss1 = pref.getString("lastlogin", null);

        if(ss1 == null || ss1.equalsIgnoreCase("") || ss1.equalsIgnoreCase("null")){
            ss1 = ss ;
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("lastlogin",ss);
            editor.apply();
        }
        Log.d("datedddiiff_login",ss+" "+ss1);
        int dateDifference = (int) getDateDiff(new SimpleDateFormat("dd/MM/yyyy"), ss1, ss);
        System.out.println("dateDifference_login: " + dateDifference);
        if(dateDifference > 0){
            closeall();
        }
    }

    //getDateDiff() - get day difference between two dates
    // Params - SimpleDateFormat , old date and new date
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

    //closeall() - clear preferrence and logout
    // Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void closeall(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.putInt("isloginn",0);
        editor.commit();
        Intent i = new Intent(getApplicationContext(),Splash.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    //NIP() - Check for any NIP user to update
    // Params - session
    //Created on 25/01/2022
    //Return - NULL
    public void NIP(Integer ses) {
        Log.d("formm",formattedDate);
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String NIP = "SELECT (c.debit_amount - SUM(a.collection_amount)) as paid,c.*,b.id AS ID,b.customer_name FROM dd_account_debit c " +
                "INNER JOIN dd_customers b on b.id = c.customer_id INNER JOIN dd_collection a on a.customer_id = b.id WHERE ( c.closeing_date < ? AND b.tracking_id = ? ) GROUP BY b.id  ";
     String NIPP = "SELECT a.*,SUM (b.collection_amount + b.discount) as paid,c.id as ID,c.CID FROM dd_customers c LEFT JOIN  dd_account_debit a on a.customer_id = c.id LEFT JOIN dd_collection b on b.debit_id = a.id " +
             " WHERE a.closeing_date < ? AND c.tracking_id = ? AND c.debit_type = ? AND a.active_status = 1 GROUP BY c.id";
//        Cursor cursor = database.rawQuery(NIP, new String[]{formattedDate,String.valueOf(ses)});
Cursor cursor = database.rawQuery(NIPP,new String[]{formattedDate, String.valueOf(ses) , "0"});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("ID");
                    id = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("CID");
                    String id1 = cursor.getString(index);
//
                    index = cursor.getColumnIndexOrThrow("closeing_date");
                    String closee = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("paid");
                    String total = cursor.getString(index);
                    if(total == null){
                        total = "0";
                    }
                    if(debit == null){
                        debit = "0";
                    }
                    Integer dee = Integer.parseInt(debit);
                    Integer tot = Integer.parseInt(total);
                    Integer ppp = dee - tot;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    Calendar calendar = Calendar.getInstance();
                   Date myDate = null;
                    try {
                        myDate = sdf.parse(closee);
                        Log.d(",myy", String.valueOf(myDate)+" "+closee+" "+id1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
//            daa = daa - 1;

                    Log.d("nipdate",String.valueOf(myDate));
                    calendar.setTime(myDate);
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    Date newDate = calendar.getTime();
                    SimpleDateFormat sss = new SimpleDateFormat("dd/MM/yyyy");
                    String newd = sdf.format(newDate);
                    Log.d("nipdate",newd+" "+id1);
                    if(ppp > 0){
//                        cursor.close();
//                        sqlite.close();
//                        database.close();
                        updatenip(ses,newd);
                    }else if(ppp <= 0){
//                        updateclose(ses);
                    }
//                    if(id != null){
//                        updatenip(ses);
//                    }
                    Log.d("Callidnip", debit+" "+total+" "+id+" "+closee);
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

    //CURR() - Check for any current user to update
    // Params - session
    //Created on 25/01/2022
    //Return - NULL
    public void CURR(Integer ses) {
        Log.d("formm",formattedDate);
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();

        String NIP = "SELECT (c.debit_amount - SUM(a.collection_amount)) as paid,c.*,b.id AS ID,b.customer_name FROM dd_account_debit c " +
                "INNER JOIN dd_customers b on b.id = c.customer_id INNER JOIN dd_collection a on a.customer_id = b.id WHERE ( c.closeing_date < ? AND b.tracking_id = ? ) GROUP BY b.id  ";
        String NIPP = "SELECT a.*,SUM (b.collection_amount + b.discount) as paid,c.id as ID,c.CID FROM dd_customers c LEFT JOIN  dd_account_debit a on a.customer_id = c.id LEFT JOIN dd_collection b on b.debit_id = a.id " +
                " WHERE a.closeing_date > ? AND c.tracking_id = ? AND c.debit_type = ? AND a.active_status = 1 GROUP BY c.id";
//        Cursor cursor = database.rawQuery(NIP, new String[]{formattedDate,String.valueOf(ses)});
        Cursor cursor = database.rawQuery(NIPP,new String[]{formattedDate, String.valueOf(ses) , "1"});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("ID");
                    id = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("CID");
                    String id1 = cursor.getString(index);
//
                    index = cursor.getColumnIndexOrThrow("closeing_date");
                    String closee = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("paid");
                    String total = cursor.getString(index);
                    if(total == null){
                        total = "0";
                    }
                    if(debit == null){
                        debit = "0";
                    }
                    Integer dee = Integer.parseInt(debit);
                    Integer tot = Integer.parseInt(total);
                    Integer ppp = dee - tot;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    Calendar calendar = Calendar.getInstance();
                    Date myDate = null;
                    try {
                        myDate = sdf.parse(closee);
                        Log.d(",myy", String.valueOf(myDate)+" "+closee+" "+id1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
//            daa = daa - 1;

                    Log.d("nipdate",String.valueOf(myDate));
                    calendar.setTime(myDate);
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    Date newDate = calendar.getTime();
                    SimpleDateFormat sss = new SimpleDateFormat("dd/MM/yyyy");
                    String newd = sdf.format(newDate);
                    Log.d("currdate",newd+" "+id1);
                    if(ppp > 0){
                        updatecurr(ses,newd);
                    }else if(ppp <= 0){

//                        updateclose(ses);
                    }
//                    if(id != null){
//                        updatenip(ses);
//                    }
                    Log.d("Callidnip", debit+" "+total+" "+id+" "+closee);
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
//    public void current(Integer ses) {
//        Log.d("formm",formattedDate);
//        sqlite = new SQLiteHelper(this);
//        database = sqlite.getReadableDatabase();
//        String NIP = "SELECT (c.debit_amount - SUM(a.collection_amount)) as paid,c.*,b.id AS ID,b.customer_name FROM dd_account_debit c " +
//                "INNER JOIN dd_customers b on b.id = c.customer_id INNER JOIN dd_collection a on a.customer_id = b.id WHERE ( c.closeing_date < ? AND b.tracking_id = ? ) GROUP BY b.id  ";
//        String NIPP = "SELECT a.*,SUM (b.collection_amount + b.discount) as paid,c.id as ID FROM dd_customers c LEFT JOIN  dd_account_debit a on a.customer_id = c.id LEFT JOIN dd_collection b on b.customer_id = a.customer_id " +
//                " WHERE a.closeing_date < ? AND c.tracking_id = ? AND c.debit_type = ? GROUP BY c.id";
////        Cursor cursor = database.rawQuery(NIP, new String[]{formattedDate,String.valueOf(ses)});
//        Cursor cursor = database.rawQuery(NIPP,new String[]{formattedDate, String.valueOf(ses) , "0"});
//        if (cursor != null) {
//            if (cursor.getCount() != 0) {
//                cursor.moveToFirst();
//                do {
//                    int index;
//
//                    index = cursor.getColumnIndexOrThrow("ID");
//                    id = cursor.getString(index);
////
//                    index = cursor.getColumnIndexOrThrow("closeing_date");
//                    String closee = cursor.getString(index);
//
//                    index = cursor.getColumnIndexOrThrow("debit_amount");
//                    String debit = cursor.getString(index);
//
//                    index = cursor.getColumnIndexOrThrow("paid");
//                    String total = cursor.getString(index);
//                    if(total == null){
//                        total = "0";
//                    }
//                    if(debit == null){
//                        debit = "0";
//                    }
//                    Integer dee = Integer.parseInt(debit);
//                    Integer tot = Integer.parseInt(total);
//                    Integer ppp = dee - tot;
//                    if(ppp > 0){
//                        updatenip(ses,closee);
//                    }else if(ppp <= 0){
//                        updateclose(ses);
//                    }
////                    if(id != null){
////                        updatenip(ses);
////                    }
//                    Log.d("Callidnip", debit+" "+total+" "+id+" "+closee);
//                }
//                while (cursor.moveToNext());
//            }
//        }
//    }
/*public void closedid(Integer ses,String id) {
    Log.d("formm",formattedDate);
    sqlite = new SQLiteHelper(this);
    database = sqlite.getReadableDatabase();

    String NIPP = "SELECT a.*,SUM (b.collection_amount ) as paid, SUM( b.discount ) as disco,c.id as ID,c.order_id FROM dd_customers c LEFT JOIN  dd_account_debit a on a.customer_id = c.id LEFT JOIN dd_collection b on b.debit_id = a.id " +
            " WHERE  c.tracking_id = ?  AND c.id = ? AND c.debit_type IN (0,1,2) AND a.active_status = 1  GROUP BY c.id";
//        Cursor cursor = database.rawQuery(NIP, new String[]{formattedDate,String.valueOf(ses)});
    Cursor cursor = database.rawQuery(NIPP,new String[]{String.valueOf(ses),id});
    if (cursor != null) {
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                int index;

                index = cursor.getColumnIndexOrThrow("ID");
                id = cursor.getString(index);
                index = cursor.getColumnIndexOrThrow("id");
                did = cursor.getString(index);
//
                index = cursor.getColumnIndexOrThrow("closeing_date");
                String closee = cursor.getString(index);

                index = cursor.getColumnIndexOrThrow("order_id");
                String orderr = cursor.getString(index);

                index = cursor.getColumnIndexOrThrow("debit_amount");
                String debit = cursor.getString(index);

                index = cursor.getColumnIndexOrThrow("paid");
                String total = cursor.getString(index);

                index = cursor.getColumnIndexOrThrow("disco");
                String total1 = cursor.getString(index);

                if(total == null || total.equalsIgnoreCase("")){
                    total = "0";
                }
                if(total1 == null || total1.equalsIgnoreCase("")){
                    total1 = "0";
                }
                if(debit == null){
                    debit = "0";
                }
                Integer dee = Integer.parseInt(debit);
                Integer tot = Integer.parseInt(total);
                Integer tot1 = Integer.parseInt(total1);
                tot = tot + tot1 ;
                Integer ppp = dee - tot;
                if(ppp <= 0){
                    Integer ooo = Integer.parseInt(orderr);
                    if(ooo > 5000){
                        last1(String.valueOf(ses),orderr,id,did);
                    }else{
                        last(String.valueOf(ses),orderr,id,did);
                    }
//                        updateclose(ses);
                }
//                    if(id != null){
//                        updatenip(ses);
//                    }
                Log.d("Callidclose", debit+" "+total+" "+total1+" "+id+" "+closee);
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
        database.close();        }
}*/

    //privilege() - get privilege of an user module
    // Params - user and module id
    //Created on 25/01/2022
    //Return - NULL
    public void privilege(String user_id,String module_id) {
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String uss = user_id;
        String mod = module_id;
        Log.d("privil","oiuoiu");
        Log.d("Callid",uss+" "+mod);
        String priv = "SELECT * FROM dd_privilege WHERE user_id = ? AND module_id =?";
//        String NIP = "SELECT (c.debit_amount - SUM(a.collection_amount)) as paid,c.*,b.id AS ID,b.customer_name FROM dd_account_debit c INNER JOIN dd_customers b on b.id = c.customer_id INNER JOIN dd_collection a on a.customer_id = b.id WHERE ( c.closeing_date < ? ) GROUP BY b.id HAVING paid > 0 ";
        Cursor cursor = database.rawQuery(priv, new String[]{user_id,module_id});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("add_privilege");
                     adddd = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("edit_privilege");
                     edittt = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("delete_privilege");
                     deleteee = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("view_privilege");
                     viewww = cursor.getString(index);
//                    if(id != null){
//                        updatenip();
//                    }
                    Log.d("Callid1", adddd);
                    Log.d("Callid2", edittt);
                    Log.d("Callid3", deleteee);
                    Log.d("Callid4", viewww);
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                adddd = "1";
                edittt = "2";
                deleteee = "3";
                viewww = "4";
                Log.d("Callid1", adddd);
                Log.d("Callid2", edittt);
                Log.d("Callid3", deleteee);
                Log.d("Callid4", viewww);
                cursor.close();
                sqlite.close();
                database.close();
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        editor.putString("add_privilege",adddd);
        editor.putString("edit_privilege",edittt);
        editor.putString("delete_privilege",deleteee);
        editor.putString("view_privilege",viewww);
        editor.apply();
    }
    //updatenip() - update user to NIP
    // Params - session and date
    //Created on 25/01/2022
    //Return - NULL
    private void updatenip(Integer ses,String close) {
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("debit_type","1");
        cv.put("debit_type_updated",close);
        database.update("dd_customers", cv, "id = ? AND tracking_id = ?", new String[]{id, String.valueOf(ses)});
        sqlite.close();
        database.close();
    }
    //updatecurr() - update user to current
    // Params - session and date
    //Created on 25/01/2022
    //Return - NULL
    private void updatecurr(Integer ses,String close) {
        Log.d("currdate123",ses+" "+close+" "+id);
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("debit_type","0");
        cv.put("debit_type_updated",close);
        database.update("dd_customers", cv, "id = ? AND tracking_id = ?", new String[]{id, String.valueOf(ses)});
        sqlite.close();
        database.close();
    }
    //viewcheck() - get total view privileges for all modules
    // Params - user id
    //Created on 25/01/2022
    //Return - NULL
    void viewcheck(String idd) {
        String add_col = "null",edit_col = "null",view_col = "null",delete_col = "null";
        Integer iii = Integer.parseInt(idd);
        Log.d("names", String.valueOf(iii));
        if(idd != null  ) {
            Names.clear();
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
//            String MY_QUERY = "SELECT a.*,b.NAME as modulename FROM dd_privilege a INNER JOIN dd_modules b on b.ID = a.module_id WHERE a.ID = ? ";
            String MY_QUERY = "SELECT a.*,b.NAME as modulename from dd_privilege a INNER JOIN dd_modules b on b.ID = a.module_id WHERE a.user_id = ?";
            Cursor cursor = database.rawQuery(MY_QUERY, new String[]{idd});
//            Cursor cursor = database.rawQuery(MY_QUERY, null);

            if (cursor != null) {
                if (cursor.getCount() != 0) {
                    cursor.moveToFirst();
                    do {
                        int index;
                        index = cursor.getColumnIndexOrThrow("modulename");
                        String id = cursor.getString(index);

                        index = cursor.getColumnIndexOrThrow("view_privilege");
                        String vi = cursor.getString(index);

                        index = cursor.getColumnIndexOrThrow("add_privilege");
                        String aad = cursor.getString(index);

                        index = cursor.getColumnIndexOrThrow("edit_privilege");
                        String edd = cursor.getString(index);

                        index = cursor.getColumnIndexOrThrow("delete_privilege");
                        String dell = cursor.getString(index);

                        Log.d("idid", vi);

                        if(id == null && vi == null){}
                        else {
                            if(id.equalsIgnoreCase("Bulk Update")){
                                Log.d("bulkupdate_add", aad);
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                final SharedPreferences.Editor editor = pref.edit();
                                editor.putString("bulkupdate_add",aad);
                                editor.putString("bulkupdate_edit",edd);
                                editor.putString("bulkupdate_delete",dell);
                                editor.apply();
                            }else if(id.equalsIgnoreCase("Single Collection")){
                                Log.d("singlecollection_add", aad);
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                final SharedPreferences.Editor editor = pref.edit();
                                editor.putString("singlecollection_add",aad);
                                editor.putString("singlecollection_edit",edd);
                                editor.putString("singlecollection_delete",dell);
                                editor.apply();
                            }else if(id.equalsIgnoreCase("Quick Bulk update")){
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                final SharedPreferences.Editor editor = pref.edit();
                                editor.putString("quickbulkupdate_add",aad);
                                editor.putString("quickbulkupdate_edit",edd);
                                editor.putString("quickbulkupdate_delete",dell);
                                editor.apply();
                            }else if(id.equalsIgnoreCase("collection")){
                                add_col = aad;
                                edit_col = edd;
                                delete_col = dell;
                                view_col = vi;
                            }


                            Names.add(vi);
                            set.add(vi);
                            Log.d("names", String.valueOf(Names.size()));
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
            Log.d("getting_size",String.valueOf(Names.size()));
            if(Names.size()<=20){
                for(int i=21;i<= 24;i++){
                    Integer modid = i;
                    if(modid.equals(24)){
                        SQLiteHelper sqlite = new SQLiteHelper(this);
                        SQLiteDatabase database = sqlite.getWritableDatabase();
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat dff = new SimpleDateFormat("yyyy/MM/dd");
                        String formattedDate = dff.format(c.getTime());
                        ContentValues values = new ContentValues();
                        values.put("module_id",String.valueOf(modid));
                        values.put("add_privilege",String.valueOf(0));
                        values.put("view_privilege",String.valueOf(0));
                        values.put("edit_privilege",String.valueOf(0));
                        values.put("delete_privilege",String.valueOf(0));
                        values.put("user_id",idd);
                        values.put("created_date",formattedDate);
                        database.insert("dd_privilege",null,values);
                        Log.d("forloop1",String.valueOf(modid));
                        Names.add(view_col);
                        sqlite.close();
                        database.close();
                    }else{
                        SQLiteHelper sqlite = new SQLiteHelper(this);
                        SQLiteDatabase database = sqlite.getWritableDatabase();
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat dff = new SimpleDateFormat("yyyy/MM/dd");
                        String formattedDate = dff.format(c.getTime());
                        ContentValues values = new ContentValues();
                        values.put("module_id",String.valueOf(modid));
                        values.put("add_privilege",String.valueOf(add_col));
                        values.put("view_privilege",String.valueOf(view_col));
                        values.put("edit_privilege",String.valueOf(edit_col));
                        values.put("delete_privilege",String.valueOf(delete_col));
                        values.put("user_id",idd);
                        values.put("created_date",formattedDate);
                        database.insert("dd_privilege",null,values);
                        Log.d("forloop",String.valueOf(modid));
                        Names.add(view_col);
                        sqlite.close();
                        database.close();
                    }

                    if(modid.equals(24)){
                        zero = Names.get(0);
                        one = Names.get(1);
                        two = Names.get(2);
                        three = Names.get(3);
                        four = Names.get(4);
                        five = Names.get(5);
                        six = Names.get(6);
                        seven = Names.get(7);
                        eight = Names.get(8);
                        nine = Names.get(9);
                        ten = Names.get(10);
                        eleven = Names.get(11);
                        twelve = Names.get(12);
                        thirteen = Names.get(13);
                        fourteen = Names.get(14);
                        fifteen = Names.get(15);
                        sixteen = Names.get(16);
                        seventeen = Names.get(17);
                        eighteen = Names.get(18);
                        nineteen = Names.get(19);
                        twenty = Names.get(20);
                        twentyone = Names.get(21);
                        twentytwo = Names.get(22);
                        twentythree = Names.get(23);
                        Log.d("namesve", three+","+four+","+five);

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        final SharedPreferences.Editor editor = pref.edit();
                        editor.putString("company",zero);
                        editor.putString("user1",one);
                        editor.putString("debit",two);
                        editor.putString("collection",three);
                        editor.putString("account",four);
                        editor.putString("reports",five);
                        editor.putString("customer",six);
                        editor.putString("settings",seven);
                        editor.putString("tally",eight);
                        editor.putString("employee_details",nine);
                        editor.putString("today_report",ten);
                        editor.putString("current_account",eleven);
                        editor.putString("closed_account",twelve);
                        editor.putString("today_account",thirteen);
                        editor.putString("current_incompleted_account",fourteen);
                        editor.putString("NIP_account",fifteen);
                        editor.putString("NIPNIP_account",sixteen);
                        editor.putString("total_customer",seventeen);
                        editor.putString("final_report",eighteen);
                        editor.putString("printer",nineteen);
                        editor.putString("bulkupdate",twenty);
                        editor.putString("singlecollection",twentyone);
                        editor.putString("quickbulkupdate",twentytwo);
                        editor.putString("archive",twentythree);

//            Log.d("names", String.valueOf(set.size()));
//            editor.putStringSet("VIEW",set);
                        editor.apply();
                    }
                    Log.d("forloop",String.valueOf(modid));
                }
            }
            else{
                if(Names.size() > 0) {
                    zero = Names.get(0);
                    one = Names.get(1);
                    two = Names.get(2);
                    three = Names.get(3);
                    four = Names.get(4);
                    five = Names.get(5);
                    six = Names.get(6);
                    seven = Names.get(7);
                    eight = Names.get(8);
                    nine = Names.get(9);
                    ten = Names.get(10);
                    eleven = Names.get(11);
                    twelve = Names.get(12);
                    thirteen = Names.get(13);
                    fourteen = Names.get(14);
                    fifteen = Names.get(15);
                    sixteen = Names.get(16);
                    seventeen = Names.get(17);
                    eighteen = Names.get(18);
                    nineteen = Names.get(19);
                    twenty = Names.get(20);
                    twentyone = Names.get(21);
                    twentytwo = Names.get(22);
                    twentythree = Names.get(23);
                    Log.d("namesve", three+","+four+","+five);
                }
                else {
                    zero = "1";
                    one = "2";
                    two = "3";
                    three = "4";
                    four = "5";
                    five = "6";
                    six = "7";
                    seven = "8";
                    eight = "9";
                    nine = "10";
                    ten = "11";
                    eleven = "12";
                    twelve = "13";
                    thirteen = "14";
                    fourteen = "15";
                    fifteen = "16";
                    sixteen = "17";
                    seventeen = "18";
                    eighteen = "19";
                    nineteen = "20";
                    twenty = "21";
                    twentyone = "22";
                    twentytwo = "23";
                    twentythree = "24";

                    Log.d("namesve", nineteen);
                }
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                final SharedPreferences.Editor editor = pref.edit();
                editor.putString("company",zero);
                editor.putString("user1",one);
                editor.putString("debit",two);
                editor.putString("collection",three);
                editor.putString("account",four);
                editor.putString("reports",five);
                editor.putString("customer",six);
                editor.putString("settings",seven);
                editor.putString("tally",eight);
                editor.putString("employee_details",nine);
                editor.putString("today_report",ten);
                editor.putString("current_account",eleven);
                editor.putString("closed_account",twelve);
                editor.putString("today_account",thirteen);
                editor.putString("current_incompleted_account",fourteen);
                editor.putString("NIP_account",fifteen);
                editor.putString("NIPNIP_account",sixteen);
                editor.putString("total_customer",seventeen);
                editor.putString("final_report",eighteen);
                editor.putString("printer",nineteen);
                editor.putString("bulkupdate",twenty);
                editor.putString("singlecollection",twentyone);
                editor.putString("quickbulkupdate",twentytwo);
                editor.putString("archive",twentythree);

//            Log.d("names", String.valueOf(set.size()));
//            editor.putStringSet("VIEW",set);
                editor.apply();
            }



        }
    }
    //closed() - Check for any user to close
    // Params - session
    //Created on 25/01/2022
    //Return - NULL
    public void closed(Integer ses) {
        Log.d("formm",formattedDate);
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String NIP = "SELECT (c.debit_amount - SUM(a.collection_amount)) as paid,c.*,b.id AS ID,b.customer_name FROM dd_account_debit c " +
                "INNER JOIN dd_customers b on b.id = c.customer_id INNER JOIN dd_collection a on a.customer_id = b.id WHERE ( c.closeing_date < ? AND b.tracking_id = ? ) GROUP BY b.id  ";
        String NIPP = "SELECT a.*,SUM (b.collection_amount ) as paid, SUM( b.discount ) as disco,c.id as ID,c.order_id_new FROM dd_customers c LEFT JOIN  dd_account_debit a on a.customer_id = c.id LEFT JOIN dd_collection b on b.debit_id = a.id " +
                " WHERE  c.tracking_id = ?  AND c.debit_type IN (0,1,2) AND a.active_status = 1  GROUP BY c.id";
//        Cursor cursor = database.rawQuery(NIP, new String[]{formattedDate,String.valueOf(ses)});
        Cursor cursor = database.rawQuery(NIPP,new String[]{String.valueOf(ses)});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("ID");
                    id = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("id");
                    did = cursor.getString(index);
//
                    index = cursor.getColumnIndexOrThrow("closeing_date");
                    String closee = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String orderr = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("paid");
                    String total = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("disco");
                    String total1 = cursor.getString(index);

                    if(total == null || total.equalsIgnoreCase("")){
                        total = "0";
                    }
                    if(total1 == null || total1.equalsIgnoreCase("")){
                        total1 = "0";
                    }
                    if(debit == null){
                        debit = "0";
                    }
                    Integer dee = Integer.parseInt(debit);
                    Integer tot = Integer.parseInt(total);
                    Integer tot1 = Integer.parseInt(total1);
                    tot = tot + tot1 ;
                    Integer ppp = dee - tot;
                    if(ppp <= 0){
                        Integer ooo = Integer.parseInt(orderr);
                        updateclose(ses,did);
//                        if(ooo > 5000){
//                            last1(String.valueOf(ses),orderr,id,did);
//                        }else{
//                            last(String.valueOf(ses),orderr,id,did);
//                        }
//                        updateclose(ses);
                    }
//                    if(id != null){
//                        updatenip(ses);
//                    }
                    Log.d("Callidclose", debit+" "+total+" "+total1+" "+id+" "+closee);
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
            database.close();        }
    }
//    private void last(String ses, String order, String id, String did){
//
//        sqlite = new SQLiteHelper(this);
//        database = sqlite.getWritableDatabase();
//        String MY_QUERY1 = "SELECT order_id FROM dd_customers WHERE debit_type IN (0,1) AND tracking_id = ? ORDER BY order_id DESC LIMIT 0,1 ";
////        Cursor cursor = database.rawQuery(NIP, new String[]{formattedDate,String.valueOf(ses)});
//        Cursor cursor = database.rawQuery(MY_QUERY1,new String[]{String.valueOf(ses)});
//        if (cursor != null) {
//            if (cursor.getCount() != 0) {
//                cursor.moveToFirst();
//                do {
//                    int index;
//
//
//                    index = cursor.getColumnIndexOrThrow("order_id");
//                    String orderr = cursor.getString(index);
//
//                    if(orderr == null){
//                        orderr = "0";
//                    }
//                    Log.d("ooor",orderr+","+order);
//                    Integer ooo = Integer.valueOf(order);
//                    check(ses,order,id,did,orderr);
////                    upddate(order,orderr,ses,id,did);
//
//
//                }
//                while (cursor.moveToNext());
//                cursor.close();
//                sqlite.close();
//                database.close();
//            }else{
//                cursor.close();
//                sqlite.close();
//                database.close();
//            }
//        }else{
//            cursor.close();
//            sqlite.close();
//            database.close();
//        }
//    }
//    private void last1(String ses, String order, String id,String did){
//
//        sqlite = new SQLiteHelper(this);
//        database = sqlite.getWritableDatabase();
//        String MY_QUERY1 = "SELECT order_id FROM dd_customers WHERE debit_type IN (2) AND tracking_id = ? ORDER BY order_id DESC LIMIT 0,1 ";
////        Cursor cursor = database.rawQuery(NIP, new String[]{formattedDate,String.valueOf(ses)});
//        Cursor cursor = database.rawQuery(MY_QUERY1,new String[]{String.valueOf(ses)});
//        if (cursor != null) {
//            if (cursor.getCount() != 0) {
//                cursor.moveToFirst();
//                do {
//                    int index;
//
//
//                    index = cursor.getColumnIndexOrThrow("order_id");
//                    String orderr = cursor.getString(index);
//
//                    if(orderr == null){
//                        orderr = "0";
//                    }
//                    Log.d("ooor",orderr+","+order);
//                    Integer ooo = Integer.valueOf(order);
//                    check(ses,order,id,did, orderr);
////                    upddate(order,orderr,ses,id,did);
//
//
//                }
//                while (cursor.moveToNext());
//                sqlite.close();
//                database.close();
//            }else{
//                cursor.close();
//                sqlite.close();
//                database.close();
//            }
//        }
//        else{
//            cursor.close();
//            sqlite.close();
//            database.close();        }
//    }
//    private  void check(String ses, String order, String id, String did, String orderr){
//        sqlite = new SQLiteHelper(this);
//        database = sqlite.getWritableDatabase();
//        Log.d("getcursorcount3",String.valueOf(ses)+" "+String.valueOf(order));
//        String MY_QUERY1 = "SELECT * FROM dd_customers WHERE tracking_id = ? AND previous_order_id = ? ORDER BY order_id  ";
////        Cursor cursor = database.rawQuery(NIP, new String[]{formattedDate,String.valueOf(ses)});
//        Cursor cursor = database.rawQuery(MY_QUERY1,new String[]{String.valueOf(ses),order});
//        if (cursor != null) {
//            if (cursor.getCount() != 0) {
//                Log.d("getcursorcount",String.valueOf(cursor.getCount()));
//                upddate(order,orderr,ses,id,did,String.valueOf(cursor.getCount()));
//                cursor.close();
//                sqlite.close();
//                database.close();
//            }
//            else{
//                Log.d("getcursorcount1",String.valueOf(cursor.getCount()));
//                upddate(order,orderr,ses,id,did,String.valueOf(cursor.getCount()));
//                cursor.close();
//                sqlite.close();
//                database.close();
//            }
//        }else{
//            Log.d("getcursorcount2",String.valueOf(cursor.getCount()));
//            upddate(order,orderr,ses,id,did,String.valueOf(cursor.getCount()));
//            cursor.close();
//            sqlite.close();
//            database.close();
//        }
//    }
//    private void upddate(String selected, String cleared, String ses, String id, String did , String prev) {
//        Integer see = Integer.valueOf(selected);
//        Integer cle = Integer.valueOf(cleared);
//        see1 = see ;
//        cle1 = cle ;
//        Log.d("namessssss54", String.valueOf(see));
//        Log.d("namessssss54", String.valueOf(cle));
////        Log.d("ooor",id+","+see1+","+cle1);
//        if (see1 < cle1) {
//            Log.d("namessssss54", String.valueOf(see));
//            Log.d("namessssss54", String.valueOf(cle));
//            see2 = see1 + 1;
////            for (int i = see2; i <= cle1;i++){
////                sqlite = new SQLiteHelper(this);
////                database = sqlite.getWritableDatabase();
////                Log.d("forrloop",String.valueOf(i));
////                ContentValues cv = new ContentValues();
////                cv.put("order_id",i-1);
////                String[] args =  new String[]{String.valueOf(i), String.valueOf(ses)};
////                database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
////                Log.d("forrloop111",String.valueOf(i));
////                Log.d("forrloop222",String.valueOf(i+1));
////                sqlite.close();
////                database.close();
////            }
//            sqlite = new SQLiteHelper(this);
//            database = sqlite.getWritableDatabase();
//            ContentValues cv1 = new ContentValues();
//            cv1.put("order_id","-1");
//            cv1.put("previous_order_id",selected);
////            cv1.put("previous_order_id_total",prev);
//            database.update("dd_customers", cv1,  "id=? AND order_id=? AND tracking_id = ?", new String[]{id,String.valueOf(see1), String.valueOf(ses)});
//            updateclose(Integer.valueOf(ses),did);
//            sqlite.close();
//            database.close();
//        }else if (see1.equals(cle1)){
//            Log.d("ooor",id+","+see1);
//            sqlite = new SQLiteHelper(this);
//            database = sqlite.getWritableDatabase();
//            ContentValues cv1 = new ContentValues();
//            cv1.put("order_id","-1");
//            cv1.put("previous_order_id",selected);
////            cv1.put("previous_order_id_total",prev);
//            database.update("dd_customers", cv1,  "id=? AND order_id=? AND tracking_id = ?", new String[]{id,String.valueOf(see1), String.valueOf(ses)});
//            updateclose(Integer.valueOf(ses),did);
//            sqlite.close();
//            database.close();
//
//        }
//    }


    //updateclose() - Update user to close
    // Params - session and debit id
    //Created on 25/01/2022
    //Return - NULL
    private void updateclose(Integer ses,String did) {
        Log.d("did3000",did+" "+id);
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("debit_type","3");
        cv.put("debit_type_updated",formattedDate);
        database.update("dd_customers", cv, "id = ? AND tracking_id = ?", new String[]{id, String.valueOf(ses)});
        ContentValues cv1 = new ContentValues();
        cv1.put("active_status","0");
        cv1.put("updated_date",formattedDate);
        database.update("dd_account_debit", cv1, "id = ?", new String[]{did});
        sqlite.close();
        database.close();
    }

    //syncEvent() - AsyncTask ( NOT USED )
    // Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private class syncEvent extends AsyncTask<String, Integer, String> {

        //Add 1. Declare RelativeLaout
        private RelativeLayout relativeView;
        private ProgressBar progressBar;


        @Override
        protected String doInBackground(String... arg0) {
            //your operation task here
            if (see1 < cle1) {
                Log.d("namessssss54", String.valueOf(see));
                Log.d("namessssss54", String.valueOf(cle));
                see2 = see1 + 1;
                for (int i = see2; i <= cle1;i++){
                    sqlite = new SQLiteHelper(Callback.this);
                    database = sqlite.getWritableDatabase();
                    Log.d("forrloop",String.valueOf(i));
                    ContentValues cv = new ContentValues();
                    cv.put("order_id",i-1);
                    String[] args =  new String[]{String.valueOf(i), String.valueOf(sesssion)};
                    database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
                    Log.d("forrloop111",String.valueOf(i));
                    Log.d("forrloop222",String.valueOf(i+1));
                    sqlite.close();
                    database.close();
                }
                sqlite = new SQLiteHelper(Callback.this);
                database = sqlite.getWritableDatabase();
                ContentValues cv1 = new ContentValues();
                cv1.put("order_id","-1");
                cv1.put("previous_order_id",see1);
                database.update("dd_customers", cv1,  "id=? AND order_id=? AND tracking_id = ?", new String[]{id,String.valueOf(see1), String.valueOf(sesssion)});
                updateclose(Integer.valueOf(sesssion),did);
                sqlite.close();
                database.close();
            }else if (see1.equals(cle1)){
                Log.d("ooor",id+","+see1);
                sqlite = new SQLiteHelper(Callback.this);
                database = sqlite.getWritableDatabase();
                ContentValues cv1 = new ContentValues();
                cv1.put("order_id","-1");
                cv1.put("previous_order_id",see1);
                database.update("dd_customers", cv1,  "id=? AND order_id=? AND tracking_id = ?", new String[]{id,String.valueOf(see1), String.valueOf(sesssion)});
                updateclose(Integer.valueOf(sesssion),did);
                sqlite.close();
                database.close();

            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
//            Bulkupdate bulk = new ;
//            bulk.progressbar_stop1();
        }

        @Override
        protected void onPreExecute() {
//            bulk.progressbar_load1();
            //Add 3. add 2 lines of code here
//            relativeView = (RelativeLayout)findViewById(R.id.loadingevent);


        }

        // For this method, i don't need to use it yet,so, i left it here
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }

    //datee() - get today date
    // Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void datee (){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        //    String myFormat1 = "dd/MM/yyyy";//In which you need put here
        String formattedDate = df.format(c.getTime());
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        editor.putString("Date",formattedDate);
        editor.apply();
    }

    //closed1() - get closed debit amount
    // Params - date and session
    //Created on 25/01/2022
    //Return - NULL
    public void closed1(String dateString,String ses){
        cloosed =0;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String NIP = "SELECT SUM(b.debit_amount) as debitted,SUM(b.document_charge) as docuu,SUM(b.interest)as interr " +
                " FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE b.updated_date <= ? AND  b.active_status = 0 AND a.tracking_id = ? ";
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
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    String sum_debit1 = pref.getString("sum_debit","0");
                    String sum_doc1 = pref.getString("sum_doc","0");
                    String sum_inter1 = pref.getString("sum_inter","0");
                    Integer sum_debit = Integer.parseInt(sum_debit1);
                    Integer sum_doc = Integer.parseInt(sum_doc1);
                    Integer sum_inter = Integer.parseInt(sum_inter1);

                    Integer doo = Integer.parseInt(docc)+ sum_doc;
                    Integer doo1 = Integer.parseInt(docc1)+ sum_inter;
                    Integer in = Integer.parseInt(intre) + sum_debit;

//                    Integer doo = Integer.parseInt(docc);
//                    Integer doo1 = Integer.parseInt(docc1);
//                    Integer in = Integer.parseInt(intre);
                    cloosed =  in ;
                    Log.d("cloossed111",String.valueOf(doo));
                    Log.d("cloossed1111",String.valueOf(doo1));
                    Log.d("cloossed11111",String.valueOf(in));
                    Log.d("cloossed111111",String.valueOf(cloosed));
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

    //beforebal() - get beforebalance debit amount
    // Params - date and session
    //Created on 25/01/2022
    //Return - NULL
    public void beforebal(String dateString,String ses ){
        SQLiteDatabase database;
        SQLiteHelper sqlite;
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String NIP = "SELECT SUM(b.debit_amount) as debitted11,SUM(b.document_charge) as docuu,SUM(b.interest)as interr, " +
                "(SELECT sum(b.debit_amount) FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE b.debit_date < ?  AND a.tracking_id = ?) as debitted ," +
                "(SELECT sum(a.amount) FROM dd_accounting a LEFT JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE a.accounting_date < ? AND a.tracking_id = ? AND b.acc_type = '1') as acc_credited ," +
                "(SELECT sum(a.amount) FROM dd_accounting a LEFT JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE a.accounting_date < ? AND a.tracking_id = ? AND b.acc_type = '2') as acc_debited ," +
                "(SELECT sum(a.collection_amount) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 1 AND a.collection_date < ?) as collected," +
                "(SELECT sum(a.discount) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 1  AND a.collection_date < ?) as discounted ," +
                "(SELECT sum(c.debit_amount) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id  WHERE  b.tracking_id = ? AND c.debit_date BETWEEN ? AND ? AND c.active_status = 0) as currentclosed ," +
                "(SELECT sum(c.document_charge) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id  WHERE  b.tracking_id = ? AND c.debit_date BETWEEN ? AND ? AND c.active_status = 0) as currentcloseddocument ," +
                "(SELECT sum(c.interest) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id  WHERE  b.tracking_id = ? AND  c.debit_date BETWEEN ? AND ? AND c.active_status = 0) as currentclosedinterest ," +
                "(SELECT sum(a.discount) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 0 AND a.collection_date <= ?) as closeddiscount " +
                " FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE b.debit_date < ?  AND a.tracking_id = ? ";
        String sess = String.valueOf(ses);
        Cursor cursor = database.rawQuery(NIP, new String[]{dateString,sess,dateString,sess,dateString,sess,sess,dateString,sess,dateString,sess,dateString,dateString,sess,dateString,dateString,sess,
                dateString,dateString,sess,dateString,dateString,sess});
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
//                    Integer cdis = Integer.parseInt(clodiscount);
//                    Integer cdb = Integer.parseInt(clodebit);
//                    Integer cdo = Integer.parseInt(clodocc);
//                    Integer cin = Integer.parseInt(clointre);
//                    Integer dbb = Integer.parseInt(debit);
//                    dbb = dbb + cdb;
//                    Integer dio = Integer.parseInt(discount);
//                    Integer ac_d = Integer.parseInt(acc_debit);
//                    Integer col = Integer.parseInt(collect);
//                    col = col - dio ;
////                    Log.d("needed10",String.valueOf(dio)+",,"+String.valueOf(col)+",,"+String.valueOf(collect));
//                    Integer ac_c = Integer.parseInt(acc_credit);
//                    Integer doo = Integer.parseInt(docc);
//                    doo = doo + cdo;
//                    Integer in = Integer.parseInt(intre);
//                    in = in + cin;
//                    Integer dbed = dbb - in;
//                    Integer cloosed1 = cloosed - cdis ;
//                    Log.d("Callidc1212c",String.valueOf(cloosed1));
//                    befo = (ac_c+col+doo+cloosed1) - (dbed+ac_d);




                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    String sum_debit1 = pref.getString("sum_debit","0");
                    String sum_doc1 = pref.getString("sum_doc","0");
                    String sum_dis1 = pref.getString("sum_dis","0");
                    String sum_oth1 = pref.getString("sum_oth","0");
                    String sum_inter1 = pref.getString("sum_inter","0");
                    String sum_cre1 = pref.getString("sum_cre","0");
                    String sum_deb1 = pref.getString("sum_deb","0");
                    String sum_col1 = pref.getString("sum_col","0");
                    Integer sum_col = Integer.parseInt(sum_col1);
                    Integer sum_debit = Integer.parseInt(sum_debit1);
                    Integer sum_doc = Integer.parseInt(sum_doc1);
                    Integer sum_dis = Integer.parseInt(sum_dis1);
                    Integer sum_oth = Integer.parseInt(sum_oth1);
                    Integer sum_inter = Integer.parseInt(sum_inter1);
                    Integer sum_cre = Integer.parseInt(sum_cre1);
                    Integer sum_deb = Integer.parseInt(sum_deb1);
                    Integer cdis = Integer.parseInt(clodiscount)+sum_dis;
                    Integer cdb = Integer.parseInt(clodebit) + sum_debit;
                    Integer cdo = Integer.parseInt(clodocc) + sum_doc;
                    Integer cin = Integer.parseInt(clointre) + sum_inter;
                    Integer dbb = Integer.parseInt(debit);
                    dbb = dbb + cdb;
                    Integer dio = Integer.parseInt(discount);
                    Integer ac_d = Integer.parseInt(acc_debit)+sum_deb;
                    Integer col = Integer.parseInt(collect);
                    col = col - dio ;
//                    Log.d("needed10",String.valueOf(dio)+",,"+String.valueOf(col)+",,"+String.valueOf(collect));
                    Integer ac_c = Integer.parseInt(acc_credit)+sum_cre;
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
//                    yesterdaybal = String.valueOf(befo);
//                    bef.setText("\u20B9"+" "+yesterdaybal);
                }
                while (cursor.moveToNext());
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
    public void populateRecyclerView23(String dateString, String todateee,String ses) {
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        Log.d("dateedstring",dateString+" "+todateee);
        Log.d("allcollection_date5",dateString+" "+todateee);
//        String MY_QUERY = "SELECT a.*,b.customer_name,b.id as ID,c.debit_amount FROM dd_collection a INNER JOIN dd_customers b on b.id = a.customer_id INNER JOIN dd_account_debit c on c.customer_id = a.customer_id ";
        String MY_QUERY1 = "SELECT SUM(collection_amount) as paid," +
                "(SELECT SUM(b.amount) FROM dd_accounting_type a LEFT JOIN dd_accounting b on b.acc_type_id = a.id WHERE b.accounting_date BETWEEN ? AND ? AND b.tracking_id = ? AND a.acc_type = '1' ) as todaycredit," +
                "(SELECT SUM(a.collection_amount) from dd_customers b  LEFT JOIN  dd_account_debit c on  c.customer_id = b.id LEFT JOIN dd_collection a ON a.debit_id = c.id  where a.collection_date BETWEEN ? AND ? AND b.debit_type = '0' AND b.tracking_id = ? AND c.active_status = 1) AS todaycollect," +
                "(SELECT SUM(a.discount) from dd_customers b  LEFT JOIN  dd_account_debit c on  c.customer_id = b.id LEFT JOIN dd_collection a ON a.debit_id = c.id  where a.collection_date BETWEEN ? AND ? AND b.debit_type = '0' AND b.tracking_id = ? AND c.active_status = 1) AS todaycollectdisco," +
                "(SELECT SUM(a.debit_amount) from dd_customers b LEFT JOIN dd_account_debit a  on a.customer_id = b.id  Where a.debit_date BETWEEN ? AND ? AND b.tracking_id = ? AND a.active_status = 1 ) as totaldebit," +
                "(SELECT SUM(a.document_charge) from dd_customers b LEFT JOIN dd_account_debit a  on a.customer_id = b.id Where a.debit_date BETWEEN ? AND ? AND b.tracking_id =? AND a.active_status = 1 ) as totaldocument ," +
                "(SELECT SUM(a.interest) from dd_customers b LEFT JOIN dd_account_debit a  on a.customer_id = b.id Where a.debit_date BETWEEN ? AND ? AND b.tracking_id = ? AND a.active_status = 1 ) as totalinterest," +
                "(SELECT SUM(a.collection_amount) from dd_customers b  LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a ON a.debit_id = c.id where a.collection_date BETWEEN ? AND ? AND b.debit_type ='2' AND b.tracking_id = ? AND c.active_status = 1 ) as totalNIPNIP ," +
                "(SELECT SUM(a.discount) from dd_customers b  LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a ON a.debit_id = c.id where a.collection_date BETWEEN ? AND ? AND b.debit_type ='2' AND b.tracking_id = ? AND c.active_status = 1 ) as totalNIPNIPdisco ," +
                "(SELECT SUM(a.collection_amount) from dd_customers b  LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a ON a.debit_id = c.id where a.collection_date BETWEEN ? AND ? AND b.debit_type ='1' AND b.tracking_id = ? AND c.active_status = 1 ) as totalNIP ," +
                "(SELECT SUM(a.discount) from dd_customers b  LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a ON a.debit_id = c.id where a.collection_date BETWEEN ? AND ? AND b.debit_type ='1' AND b.tracking_id = ? AND c.active_status = 1 ) as totalNIPdisco ," +
                "(SELECT SUM(b.amount) FROM dd_accounting_type a LEFT JOIN dd_accounting b on b.acc_type_id = a.id WHERE b.accounting_date BETWEEN ? AND ? AND b.tracking_id = ? AND a.acc_type = '2' ) as totaldebittacc ," +
                "(SELECT SUM(a.discount) from dd_customers b  LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a ON a.debit_id = c.id  WHERE  c.active_status = 0 AND b.tracking_id = ? AND a.collection_date <=? ) as totalcloseddiso " +
                "FROM   dd_collection    ";
        String sess = String.valueOf(ses);
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{dateString,todateee,sess,
                dateString,todateee,sess,
                dateString,todateee,sess,
                dateString,todateee,sess,
                dateString,todateee,sess,
                dateString,todateee,sess,
                dateString,todateee,sess,
                dateString,todateee,sess,
                dateString,todateee,sess,
                dateString,todateee,sess,
                dateString,todateee,sess,
                sess,todateee});
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
                    Integer vfv = befo+newc+curc+nipc+nipnipc+documm ;
                    Integer vfv1 = dab+totdab ;
                    Log.d("dfer",vfv+" "+vfv1);
                    Integer currentbal = (befo+newc+curc+nipc+nipnipc+documm)-(dab+totdab);
                    Log.d("dfer", String.valueOf(currentbal));
                    Log.d("ppp", String.valueOf(currentbal));
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
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

    //emailid() - get email id
    // Params -NULL
    //Created on 25/01/2022
    //Return - NULL
    public void emailid() {

        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
//        Log.d("email1","rampittech@gamail.com");
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
//            Log.d("email2","rampittech@gamail.com");
            if(cursor.getCount() != 0){
//                Log.d("email3","rampittech@gamail.com");
                cursor.moveToFirst();
                do{
//                    Log.d("email4","rampittech@gamail.com");
                    int index;

                    index = cursor.getColumnIndexOrThrow("license");
                    String lice = cursor.getString(index);


                    index = cursor.getColumnIndexOrThrow("cc");
                    String  emacc = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("email");
                     String emailid = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("expiry");
                    String exp = cursor.getString(index);
if(emailid == null || emailid.equalsIgnoreCase("")){
//    emailid = "diary.weekly.daily@gmail.com";
}
                    if(emacc == null || emacc.equalsIgnoreCase("")){
//                        emacc = "diary.weekly.daily@gmail.com";
                    }
//                    Log.d("email",emailid);
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    final SharedPreferences.Editor editor = pref.edit();
                    editor.putString("email",emailid);
                    editor.putString("emailcc",emacc);
                    editor.apply();
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                cursor.close();
                sqlite.close();
                database.close();
                Log.d("email","diary.weekly.daily@gmail.com");
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                final SharedPreferences.Editor editor = pref.edit();
//                editor.putString("email","diary.weekly.daily@gmail.com");
//                editor.putString("emailcc","diary.weekly.daily@gmail.com");
//                editor.apply();

            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }
    }
    //emailid1() - get email id
    // Params -NULL
    //Created on 25/01/2022
    //Return - NULL
    public void emailid1() {
Log.d("yuyu","opo");
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
//        Log.d("email1","rampittech@gamail.com");
        String MY_QUERY = "SELECT * FROM dd_collection where id = ?";
        String whereClause = "ID=?";
        String[] whereArgs = new String[] {"1"};

        String[] columns = {"license",
                "cc","ID","email","expiry","language","password","version","oldversion"};

        Cursor cursor = database.query("dd_settings",
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        if (cursor != null){
//            Log.d("email2","rampittech@gamail.com");
            if(cursor.getCount() != 0){
//                Log.d("email3","rampittech@gamail.com");
                cursor.moveToFirst();
                do {
//                    Log.d("email4","rampittech@gamail.com");
                    int index;

                    index = cursor.getColumnIndexOrThrow("version");
                    String ver = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("oldversion");
                    String oldver = cursor.getString(index);
                    Integer vv = Integer.parseInt(ver);
                    Integer ov = Integer.parseInt(oldver);
                    if(ov < vv){
                        sqlite.onUpgrade(database,ov,vv);
                    }
                    Log.d("verr",String.valueOf(ver)+" "+String.valueOf(oldver));

                }
                while (cursor.moveToNext());
            }else{

            }
        }
    }

    //populateRecyclerView() -get current and NIP users
    // Params - Session
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerView(Integer ses) {
        Names.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String[] columns = {"debit_amount",
                "id",""};
        String MY_QUERY = "SELECT a.*,b.customer_name,b.debit_type,b.CID,b.order_id_new,b.id as ID FROM dd_account_debit a INNER JOIN dd_customers b on b.id = a.customer_id " +
                "WHERE b.tracking_id = ? AND b.debit_type IN (0,1) AND a.active_status = 1 GROUP BY b.id ORDER BY b.order_id_new ASC";

        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{String.valueOf(ses)});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("ID");
                    String idid = cursor.getString(index);
                    Names.add(idid);
                 }
                while (cursor.moveToNext());
                Log.d("namess1234",String.valueOf(Names));
//                updateorderid();
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

    //populateRecyclerViewnip() -get NIPNIP users
    // Params - Session
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerViewnip(Integer ses) {
        Names1.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String[] columns = {"debit_amount",
                "id",""};
        String MY_QUERY = "SELECT a.*,b.customer_name,b.debit_type,b.CID,b.order_id_new,b.id as ID FROM dd_account_debit a INNER JOIN dd_customers b on b.id = a.customer_id " +
                "WHERE b.tracking_id = ? AND b.debit_type IN (2) AND a.active_status = 1 GROUP BY b.id ORDER BY b.order_id_new ASC";

        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{String.valueOf(ses)});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("ID");
                    String idid = cursor.getString(index);
                    Names1.add(idid);
                }
                while (cursor.moveToNext());
                Log.d("namess1234",String.valueOf(Names1));
//                updateorderidnip();
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

 /*   public void updateorderid(){
        for(int i=0;i < Names.size();i++){
            Log.d("namess12345",String.valueOf(Names.get(i)));
            Integer id = Integer.valueOf(Names.get(i));
            Integer iplus = i+1;
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("order_id",iplus);
            String[] args =  new String[]{String.valueOf(id)};
            database.update("dd_customers",cv, "id = ?",args);
            sqlite.close();
            database.close();
        }
    }
    public void updateorderidnip(){
        for(int i=0;i < Names1.size();i++){
            Log.d("namess12345",String.valueOf(Names1.get(i)));
            Integer id = Integer.valueOf(Names1.get(i));
            Integer iplus = i+1;
            iplus = 5000 + iplus;
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("order_id",iplus);
            String[] args =  new String[]{String.valueOf(id)};
            database.update("dd_customers",cv, "id = ?",args);
            sqlite.close();
            database.close();
        }
    }
    public void populateRecyclerView_new(Integer ses) {
        Names.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String[] columns = {"debit_amount",
                "id",""};
        String MY_QUERY = "SELECT customer_name,debit_type,CID,order_id_new,id as ID FROM dd_customers  " +
                "WHERE tracking_id = ? ORDER BY CID ASC";

        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{String.valueOf(ses)});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("ID");
                    String idid = cursor.getString(index);
                    Names.add(idid);
                    Log.d("namess1234",String.valueOf(Names));
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
        updateorderid_new();
    }

    //populateRecyclerViewnip() -get NIPNIP users
    // Params - Session
    //Created on 25/01/2022
    //Return - NULL
    public void updateorderid_new(){
        for(int i=0;i < Names.size();i++){
            Log.d("namess12345",String.valueOf(Names.get(i)));
//            Integer id = Integer.valueOf(Names.get(i));
//            Integer iplus = i+1;
//            Log.d("iplus",String.valueOf(iplus));
//            sqlite = new SQLiteHelper(this);
//            database = sqlite.getWritableDatabase();
//            ContentValues cv = new ContentValues();
//            cv.put("order_id_new",iplus);
//            String[] args =  new String[]{String.valueOf(id)};
//            database.update("dd_customers",cv, "id = ?",args);
//            sqlite.close();
//            database.close();
        }
    }*/

    //populateRecyclerView_new1() -get all session users
    // Params - Application context and string
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerView_new1(Context context,String one) {
        morr.clear();
        evee.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String[] columns = {"debit_amount",
                "id",""};
        String MY_QUERY = "SELECT customer_name,debit_type,CID,order_id_new,id as ID,tracking_id FROM dd_customers  " +
                "ORDER BY CID ASC";

        Cursor cursor = database.rawQuery(MY_QUERY,null);
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("ID");
                    String idid = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("tracking_id");
                    String tracking_id = cursor.getString(index);
                    if(tracking_id.equalsIgnoreCase("1")){
                        morr.add(idid);
                    }else if(tracking_id.equalsIgnoreCase("2")){
                        evee.add(idid);
                    }

                }
                while (cursor.moveToNext());
                Log.d("namess1234",String.valueOf(Names));
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
        updateorderid_new1(context,one);
    }

    //updateorderid_new1() -update order id of all session users
    // Params - Application context and string
    //Created on 25/01/2022
    //Return - NULL
    public void updateorderid_new1(final Context context,String one){
        Context ccc = context ;
        for(int i=0;i < morr.size();i++){
            Log.d("namess12345",String.valueOf(morr.get(i)));
            Integer id = Integer.valueOf(morr.get(i));
            Integer iplus = i+1;
            Log.d("iplus",String.valueOf(iplus));
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("order_id_new",iplus);
            String[] args =  new String[]{String.valueOf(id)};
            database.update("dd_customers",cv, "id = ?",args);
            sqlite.close();
            database.close();
        }
        for(int j=0;j < evee.size();j++){
            Log.d("namess12345",String.valueOf(evee.get(j)));
            Integer id = Integer.valueOf(evee.get(j));
            Integer iplus = j+1;
            Log.d("iplus",String.valueOf(iplus));
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("order_id_new",iplus);
            String[] args =  new String[]{String.valueOf(id)};
            database.update("dd_customers",cv, "id = ?",args);
            sqlite.close();
            database.close();
        }
        if(one.equalsIgnoreCase("1")){

        }else if(one.equalsIgnoreCase("2")){
//            final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
//            SharedPreferences.Editor mEditor1 = pref.edit();
////                mEditor1.clear();
//            mEditor1.clear();
//            mEditor1.putInt("isloginn",0);
//            mEditor1.commit();
//            Intent i = new Intent(context,Splash.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);
            closeall();
        }

//                Context.finish();
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                // this code will be executed after 2 seconds
////                                        Log.d("Status ",status);
//
//            }
//        }, 1000);



    }

   /* public void clearData(Context Context)
    {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int mCurrentVersion = pInfo.versionCode;
            SharedPreferences mSharedPreferences = getSharedPreferences("app_name",  Context.MODE_PRIVATE);
            SharedPreferences.Editor mEditor = mSharedPreferences.edit();
            mEditor.apply();
            int last_version = mSharedPreferences.getInt("last_version", -1);
            Log.d("last_version",String.valueOf(last_version)+" "+String.valueOf(mCurrentVersion));
            if(last_version != mCurrentVersion)
            {
                final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor mEditor1 = pref.edit();
//                mEditor1.clear();
                mEditor1.clear();
                mEditor1.putInt("isloginn",0);
                mEditor1.commit();
                Intent i = new Intent(Context,Splash.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Context.startActivity(i);
//                Context.finish();
                //clear all your data like database, share preference, local file
                //Note : Don't delete last_version value in share preference
            }
            mEditor.putInt("last_version", mCurrentVersion);
            mEditor.commit();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }*/

    //reorder_data() -reorder user by CID
    // Params - Application context
    //Created on 25/01/2022
    //Return - NULL
    public void reorder_data(Context Context)
    {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int mCurrentVersion = pInfo.versionCode;
            SharedPreferences mSharedPreferences = getSharedPreferences("app_name",  Context.MODE_PRIVATE);
            SharedPreferences.Editor mEditor = mSharedPreferences.edit();
            mEditor.apply();
            int last_version = mSharedPreferences.getInt("last_version", -1);
            Log.d("last_version_reorder",String.valueOf(last_version)+" "+String.valueOf(mCurrentVersion));
            if(last_version == -1){
                final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor mEditor1 = pref.edit();
//                mEditor1.clear();
                mEditor1.clear();
                mEditor1.putInt("isloginn",0);
                mEditor1.commit();
                Intent i = new Intent(getApplicationContext(),Splash.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(i);
//                Context.finish();
            }else {
                if (last_version != mCurrentVersion) {
                    if(last_version <= 11) {
                        populateRecyclerView_new1(context,"2");
                    }else{
                        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        SharedPreferences.Editor mEditor1 = pref.edit();
//                mEditor1.clear();
                        mEditor1.clear();
                        mEditor1.putInt("isloginn",0);
                        mEditor1.commit();
                        Intent i = new Intent(getApplicationContext(),Splash.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(i);
//                Context.finish();
                    }
//                    final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
//                    SharedPreferences.Editor mEditor1 = pref.edit();
////                mEditor1.clear();
//                    mEditor1.clear();
//                    mEditor1.putInt("isloginn", 0);
//                    mEditor1.commit();
//                    Intent i = new Intent(Context, Splash.class);
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    Context.startActivity(i);
//                Context.finish();
                    //clear all your data like database, share preference, local file
                    //Note : Don't delete last_version value in share preference
                }
            }
            mEditor.putInt("last_version", mCurrentVersion);
            mEditor.commit();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    //emergency() -just in case of any changes to be made by developer in database
    // Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void emergency(){
        Log.d("emergency","emer");
    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
    final SharedPreferences.Editor editor = pref.edit();
    Integer ses = pref.getInt("session",1);
//        sqlite = new SQLiteHelper(this);
//        database = sqlite.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//            cv.put("order_id","28");
//            cv.put("debit_type","0");
//            String[] args =  new String[]{"29"};
//            database.update("dd_customers", cv,  "id=?",args);
//            sqlite.close();
//            database.close();
//        sqlite = new SQLiteHelper(this);
//        database = sqlite.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//            cv.put("customer_id","16");
//            String[] args =  new String[]{"63"};
//            database.update("dd_collection",cv, "  debit_id = ? ",args);
//            sqlite.close();
//            database.close();
//        Log.d("forrloop123123","pppp");
    for (int i = 5001; i <= 5002;i++){
//        database = sqlite.getWritableDatabase();
        Log.d("forrloop123123",String.valueOf(i-13));
//        if(i == 66){


//
//        sqlite = new SQLiteHelper(this);
//        database = sqlite.getWritableDatabase();
//            database.delete("dd_collection", "id = ? ",new String[]{"294"});
//        if(i ==5001){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","1");
//            cv.put("debit_type","1");
//            String[] args =  new String[]{String.valueOf(ses),"133"};
//            database.update("dd_customers",cv, " tracking_id = ? AND CID = ? ",args);
////            sqlite.close();
////            database.close();
//        }
//        ContentValues cv = new ContentValues();
//        cv.put("active_status","1");
//        cv.put("customer_id","133");
//        String[] args =  new String[]{"1"};
//        database.update("dd_account_debit",cv, "  id = ? ",args);
//        sqlite.close();
//        database.close();
//
//        ContentValues cv1 = new ContentValues();
//        cv1.put("active_status","1");
//        cv1.put("customer_id","133");
//        String[] args1 =  new String[]{"1"};
//        database.update("dd_account_debit",cv1, "  id = ? ",args1);
//        sqlite.close();
//        database.close();





//        if(i ==106){
//            sqlite = new SQLiteHelper(this);
//            database = sqlite.getWritableDatabase();
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            Integer ji = i+1;
//            String[] args =  new String[]{String.valueOf(ji),String.valueOf(ses)};
//            database.update("dd_customers",cv, "  order_id  = ? AND tracking_id = ?",args);
//            sqlite.close();
//            database.close();
//        }
//        if(i == 141){
//            ContentValues cv = new ContentValues();
//            cv.put("debit_type",-1);
//            String[] args =  new String[]{"457"};
//            database.update("dd_customers",cv, " tracking_id = 2 AND id = ? ",args);
//        }
//        if(i == 64){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","3");
//            String[] args =  new String[]{"1","1"};
//            database.update("dd_customers",cv, "order_id = ? AND id = ? AND tracking_id = 2",args);
//        }
//        if(i == 64){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","4");
//            String[] args =  new String[]{"2","2"};
//            database.update("dd_customers",cv, "order_id = ? AND id = ? AND tracking_id = 2",args);
//        }
//        if(i == 64){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","5");
//            String[] args =  new String[]{"3","3"};
//            database.update("dd_customers",cv, "order_id = ? AND id = ? AND tracking_id = 2",args);
//        }
//        if(i == 66){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","201");
//            cv.put("debit_type","0");
//            String[] args =  new String[]{"267"};
//            database.update("dd_customers",cv, "id = ? AND tracking_id = 2  ",args);
//        }
//        if(i == 66){
//            ContentValues cv = new ContentValues();
//            cv.put("active_status","1");
//            String[] args =  new String[]{"333"};
//            database.update("dd_customers",cv, "id = ? AND tracking_id = 2  ",args);
//        }
//        if(i == 66){
//
//        database.delete("dd_account_debit", "id = ? ",new String[]{"346"});
//            database.delete("dd_collection", "id = ? ",new String[]{"3291"});
//        database.delete("dd_collection", "id = ? ",new String[]{"3290"});
//        }
//        if(i == 66){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","207");
//            String[] args =  new String[]{"219","401"};
//            database.delete("dd_account_debit", "id = ? ",new String[]{"345"});
//        }
//        if(i == 66){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","-1");
//            cv.put("debit_type","3");
//            String[] args =  new String[]{"333"};
//            database.update("dd_customers",cv, "id = ? AND tracking_id = 2 ",args);
//        }
//        if(i == 45){

//        }

        //        if(i == 189){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i-1);
//            String[] args =  new String[]{String.valueOf(i)};
//            database.update("dd_customers",cv, "order_id = ? AND tracking_id = 2 ",args);
//        }
//        if(i == 190){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","36");
//            String[] args =  new String[]{"62"};
//            database.update("dd_customers",cv, "id = ? ",args);
//        }
//        if(i == 189){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","37");
//            String[] args =  new String[]{"63"};
//            database.update("dd_customers",cv, "id = ? ",args);
//        }
//        if(i == 190){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","38");
//            String[] args =  new String[]{"65"};
//            database.update("dd_customers",cv, "id = ? ",args);
//        }
//        if(i == 189){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","39");
//            String[] args =  new String[]{"67"};
//            database.update("dd_customers",cv, "id = ? ",args);
//        }
//        if(i == 190){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","40");
//            String[] args =  new String[]{"68"};
//            database.update("dd_customers",cv, "id = ? ",args);
//        }

        //        if(i == 185){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","-1");
//            String[] args =  new String[]{"389"};
//            database.update("dd_customers", cv,  "id=?",args);
//        }
//        if(i == 186){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","-1");
//            String[] args =  new String[]{"457"};
//            database.update("dd_customers", cv,  "id=?",args);
//        }

//        if(i == 159){

//        }
//        if(i == 127){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","186");
//            String[] args =  new String[]{"185",String.valueOf(ses),"451"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 128){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","187");
//            String[] args =  new String[]{"185",String.valueOf(ses),"452"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 129){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","188");
//            String[] args =  new String[]{"185",String.valueOf(ses),"453"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 130){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","189");
//            String[] args =  new String[]{"185",String.valueOf(ses),"455"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 131){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","190");
//            String[] args =  new String[]{"185",String.valueOf(ses),"458"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 132){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","191");
//            String[] args =  new String[]{"185",String.valueOf(ses),"459"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 133){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","192");
//            String[] args =  new String[]{"185",String.valueOf(ses),"465"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 134){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","193");
//            String[] args =  new String[]{"185",String.valueOf(ses),"466"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 135){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","194");
//            String[] args =  new String[]{"185",String.valueOf(ses),"467"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 136){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","195");
//            String[] args =  new String[]{"185",String.valueOf(ses),"468"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 137){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","196");
//            String[] args =  new String[]{"185",String.valueOf(ses),"469"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 138){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","197");
//            String[] args =  new String[]{"185",String.valueOf(ses),"470"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 139){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","198");
//            String[] args =  new String[]{"185",String.valueOf(ses),"471"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 140){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","199");
//            String[] args =  new String[]{"185",String.valueOf(ses),"475"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 141){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","200");
//            String[] args =  new String[]{"185",String.valueOf(ses),"490"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 142){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","201");
//            String[] args =  new String[]{"185",String.valueOf(ses),"495"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 143){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","185");
//            String[] args =  new String[]{"186",String.valueOf(ses),"451"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 161){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","192");
//            String[] args =  new String[]{"194",String.valueOf(ses),"455"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 162){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","193");
//            String[] args =  new String[]{"195",String.valueOf(ses),"459"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 163){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","194");
//            String[] args =  new String[]{"196",String.valueOf(ses),"466"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 164){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","195");
//            String[] args =  new String[]{"197",String.valueOf(ses),"469"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 165){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","196");
//            String[] args =  new String[]{"198",String.valueOf(ses),"471"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 166){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","197");
//            String[] args =  new String[]{"199",String.valueOf(ses),"422"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 167){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","198");
//            String[] args =  new String[]{"199",String.valueOf(ses),"490"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 168){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","199");
//            String[] args =  new String[]{"200",String.valueOf(ses),"367"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 169){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","200");
//            String[] args =  new String[]{"201",String.valueOf(ses),"5"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 170){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","201");
//            String[] args =  new String[]{"192",String.valueOf(ses),"422"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID  = ?",args);
//        }
//        if(i == 153){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"156",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }


//        if(i == 202){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"203",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? ",args);
//        }
//        if(i == 175){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","182");
//            String[] args =  new String[]{"184",String.valueOf(ses),"431"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID = ?",args);
//        }
//        if(i == 176){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","183");
//            String[] args =  new String[]{"184",String.valueOf(ses),"434"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID = ? ",args);
//        }
//        if(i == 177){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","176");
//            String[] args =  new String[]{"175",String.valueOf(ses),"417"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID = ? ",args);
//        }
//        if(i == 178){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","177");
//            String[] args =  new String[]{"176",String.valueOf(ses),"420"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID = ? ",args);
//        }
//        if(i == 179){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","178");
//            String[] args =  new String[]{"177",String.valueOf(ses),"421"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID = ? ",args);
//        }
//        if(i == 180){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","179");
//            String[] args =  new String[]{"178",String.valueOf(ses),"424"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID = ? ",args);
//        }
//        if(i == 181){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","180");
//            String[] args =  new String[]{"179",String.valueOf(ses),"425"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID = ? ",args);
//        }
//        if(i == 182){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","181");
//            String[] args =  new String[]{"180",String.valueOf(ses),"430"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID = ? ",args);
//        }

//        if(i == 202){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","216");
//            String[] args =  new String[]{"207",String.valueOf(ses),"495"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID = ?",args);
//        }
//        if(i == 203){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","217");
//            String[] args =  new String[]{"208",String.valueOf(ses),"471"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID = ?",args);
//        }
//        if(i == 204){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id","214");
//            String[] args =  new String[]{"207",String.valueOf(ses),"343"};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ? AND CID = ?",args);
//        }

//        if(i == 200){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"201",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 201){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"201",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 3){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"4",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        } if(i == 4){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"5",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 5){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"6",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 6){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"7",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 7){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"8",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 8){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"9",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 9){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"10",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 10){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"11",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 11){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"12",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 12){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"13",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 13){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"14",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 14){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"15",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 15){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"16",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 16){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"17",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 17){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"19",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 18){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"20",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 19){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"21",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 20){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"22",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 21){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"24",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 22){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"25",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 23){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"26",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        if(i == 18){
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i);
//            String[] args =  new String[]{"20",String.valueOf(ses)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//        }
//        Log.d("forrloop1",String.valueOf(i));
//        Log.d("forrloop2",String.valueOf(i-1));
    }

}
//    public static void trimCache(Context context) {
//        try {
//            File dir = context.getCacheDir();
//            if (dir != null && dir.isDirectory()) {
//                deleteDir(dir);
//            }
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//    }
//    public static boolean deleteDir(File dir) {
//        if (dir != null && dir.isDirectory()) {
//            String[] children = dir.list();
//            for (int i = 0; i < children.length; i++) {
//                boolean success = deleteDir(new File(dir, children[i]));
//                if (!success) {
//                    return false;
//                }
//            }
//        }
//
//        // The directory is now empty so delete it
//        return dir.delete();
//    }
}
