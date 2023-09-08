package com.rampit.rask3.dailydiary;

import android.Manifest;
import android.app.AlertDialog;
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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;

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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.rampit.rask3.dailydiary.Receiver.GMTtoEST;
import com.rampit.rask3.dailydiary.Receiver.SntpClient;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static android.os.Build.VERSION_CODES.N;


//Updated on 25/01/2022 by RAMPIT
//Check OTP
//License1() - get license of user and upload an backup
//deleteotp() - update otp
//onRequestPermissionsResult() - request read external storage
//lastlogin() - put login time
//onRequestPermissionsResult() - request read external storage
//InternetConnection() - Check whether internet is on or not
//mainvalues() - get main info
//savin() - get user details and store in preferrence
//expiiiid() - called when license is over
//expiiii() - check expiry date
//expiiii1() - check expiry date and move to dashboard
//expii1() - intent to Confirm_OTP
//call_me() -get expiry date from server


public class confirm_OTP extends AppCompatActivity {
    private static final int REQUEST_READ_PHONE_STATE = 1;
    EditText otp1, otp2;
    TextView expip, liccc;
    Calendar myCalendar;
    String dateString, License, uusee, poo;
    LinearLayout inlin, liclin;
    static String todate;
    Calendar c;
    String todaaaa, expiry;
    Button log, submit1;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    Cursor cursor;
    TextView forgot;
    public static String TABLENAME = "dd_user";
    String user;
    String pass;
    String otp;
    String USER;
    String PASS;
    String OTP;
    String ID;
    static String llll,device_id,eer,actsta,device_id1,expiii,product_ref;
    SharedPreferences.Editor editor;
    static Integer yy ;
    String dateFromNtpServer = "", formattedDate,vaaa;
    Long time, newTime;
    private PopupWindow mPopupWindow;
    ImageButton eyes;
     String imeiNumber1,imeiNumber2;
     Integer oldv1,newv1;

    @RequiresApi(api = Build.VERSION_CODES.M)
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
//        Integer in = 0;
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
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
            //TODO
        }
//        ((Callback)getApplication()).curl_brightness(confirm_OTP.this);
        ((Callback)getApplication()).lastlogin();
        yy = 0;
        License1();
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//        String imeiNumber1 = tm.getDeviceId(0);
//         String imeiNumber2 = tm.getDeviceId(1);
//      if(Build.VERSION.SDK_INT >= 28){
//
//      }
  Integer buildver = Build.VERSION.SDK_INT;
  Log.d("buildver",String.valueOf(buildver));
  if(buildver > 28){
       imeiNumber1 =  Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
       imeiNumber2 =  Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
       device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
      Log.d("deee1",imeiNumber1);
      Log.d("deee2",imeiNumber2);
  }else{
       imeiNumber1 =  tm.getDeviceId();
       imeiNumber2 = tm.getDeviceId();
       device_id = tm.getDeviceId();
      Log.d("deee1",imeiNumber1);
      Log.d("deee2",imeiNumber2);
  }
//        Log.d("deee1",imeiNumber1);
//        Log.d("deee2",imeiNumber2);
//        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("deeeandroidid",android_id);
        if(device_id == null || device_id.equalsIgnoreCase("")){
        }else{
//                imei111();
        }
        if (android.os.Build.VERSION.SDK_INT >= N) {
            Locale myLocale = new Locale(localeName);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.setLocale(myLocale);
            res.updateConfiguration(conf, dm);
        }else{
            Locale myLocale = new Locale(localeName);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.confirm_otp);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }

      /*  SQLiteHelper sqlite1 = new SQLiteHelper(this);
        SQLiteDatabase database1 = sqlite1.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("debit_type",3);
        database1.update("dd_customers", cv,  "id=? ", new String[]{"337"});
        sqlite1.close();
        database1.close();

        SQLiteHelper sqlite11 = new SQLiteHelper(this);
        SQLiteDatabase database11 = sqlite11.getWritableDatabase();
        ContentValues cv1 = new ContentValues();
        cv1.put("debit_type",3);
        database11.update("dd_customers", cv1,  "id=? ", new String[]{"466"});
        sqlite11.close();
        database11.close();*/

        otp1 = findViewById(R.id.otppass);
        log = findViewById(R.id.loginpass);
        forgot = findViewById(R.id.forgot);
        inlin = findViewById(R.id.infoline);
        liclin = findViewById(R.id.licenseline);
        expip = findViewById(R.id.expiry);
        liccc = findViewById(R.id.license);
        submit1 = findViewById(R.id.submit1);
        eyes = findViewById(R.id.eyes);
        Intent login1 = getIntent();
        vaaa = login1.getStringExtra("vaa");

        Intent login = getIntent();

        user = login.getStringExtra("user");
        pass = login.getStringExtra("pass");
        vaaa = login.getStringExtra("vaa");
//        vaaa ="1";
//        sqlite = new SQLiteHelper(this);
//        Log.d("otp",user);
//        database = sqlite.getWritableDatabase();
        editor=pref.edit();
        if(in == 0){
            eyes.setBackgroundResource(R.drawable.eye_orange);
        }else{
            eyes.setBackgroundResource(R.drawable.eye_blue);

//            otp1.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_blue, 0);
        }
        eyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String naam = otp1.getText().toString();
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
        if(vaaa == null || vaaa.equalsIgnoreCase("")) {
            vaaa = "1";
        }
        if (InternetConnection.checkConnection(getApplicationContext())) {
            // Internet Available...
            Log.d("internet","on");
//            date32();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            formattedDate = df.format(c.getTime());
            Log.d("internet",formattedDate);
            if(vaaa.equalsIgnoreCase("2")){
//                inlin.setVisibility(View.GONE);
//                liclin.setVisibility(View.VISIBLE);
                expiiiid();
            }else{
                expiiii();
                mainvalues();
            }
        }
        else {
            // Internet Not Available...
            Log.d("internet","off");
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            formattedDate = df.format(c.getTime());
            Log.d("internet",formattedDate);
            if(vaaa.equalsIgnoreCase("2")){
//                inlin.setVisibility(View.GONE);
//                liclin.setVisibility(View.VISIBLE);
                expiiiid();
            }else{
                expiiii();
                mainvalues();
            }
        }


        forgot.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                String us = pref.getString("user","");
                String psa = pref.getString("pass","");
                deleteotp(us,psa);
                Intent login = new Intent(confirm_OTP.this,Login.class);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                login.putExtra("user",us);
                login.putExtra("pass",psa);
                login.putExtra("va","1");
                startActivity(login,bundle);
                finish();
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                String oo1 = otp1.getText().toString();
                if(oo1.equalsIgnoreCase("")){
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(confirm_OTP.this,R.style.AlertDialogTheme);
                    String rer = getString(R.string.otpmiss);
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
                }else{
                    sqlite = new SQLiteHelper(confirm_OTP.this);
                    database = sqlite.getWritableDatabase();
                    String username = otp1.getText().toString();
                    cursor = database.rawQuery("SELECT * FROM " + SQLiteHelper.TABLENAME12 + " WHERE " + SQLiteHelper.USNAM + "=? AND " + SQLiteHelper.USPASS + " =? AND " + SQLiteHelper.USOTP + " =?", new String[]{user, pass, username});
                    if (cursor != null) {
                        if (cursor.getCount() > 0) {
                            Toast.makeText(getApplicationContext(), " successfull", Toast.LENGTH_SHORT).show();
                            savin();
                            if(uusee== null && poo == null){
                                cursor.close();
                                sqlite.close();
                                database.close();
                                Intent login = new Intent(confirm_OTP.this, MainActivity.class);
                                startActivity(login);
                                finish();
                            }else {
                                cursor.close();
                                sqlite.close();
                                database.close();
                                Intent login = new Intent(confirm_OTP.this, NavigationActivity.class);
                                login.putExtra("user", user);
                                login.putExtra("pass", pass);
                                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                                startActivity(login, bundle);
                                editor.putString("NAME",uusee);
                                editor.putString("LOCATION",poo);
                                editor.apply();
                                finish();
                            }
                            cursor.close();
                            sqlite.close();
                            database.close();
                        } else {
                            cursor.close();
                            sqlite.close();
                            database.close();
                            Toast.makeText(getApplicationContext(), "incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        cursor.close();
                        sqlite.close();
                        database.close();
                    }
                }
            }
        });
        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llll = liccc.getText().toString();
                if (llll.equalsIgnoreCase("")) {
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(confirm_OTP.this,R.style.AlertDialogTheme);
                    String rer = getString(R.string.enterlicense);
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
                } else {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            //TODO your background code
                            try {
                                confirm_OTP.call_me();
//                                if(eer.equalsIgnoreCase("success")){
//                                    confirm_OTP.call_me();
////                                   expii1();
//                                }else if(eer.equalsIgnoreCase("error")){
//                                    confirm_OTP.call_me();
//                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if(actsta.equalsIgnoreCase("active") && device_id1.equalsIgnoreCase(imeiNumber1) || device_id1.equalsIgnoreCase(imeiNumber2) && product_ref.equalsIgnoreCase("DailyDiary")){
//                            if(actsta.equalsIgnoreCase("active") && device_id1.equalsIgnoreCase(device_id)){
                                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                                SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date debit = df1.parse(expiii);
                                    todate = df.format(debit);
                                    Log.d("deae",todate);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                expii1();
                            }
                            else if(product_ref != ("DailyDiary")){
//                                progre();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder alertbox = new AlertDialog.Builder(confirm_OTP.this,R.style.AlertDialogTheme);
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
                            else if(actsta.equalsIgnoreCase("blocked")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder alertbox = new AlertDialog.Builder(confirm_OTP.this,R.style.AlertDialogTheme);
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
                                                    }
                                                });
                                        alertbox.show();
                                    }
                                });
                            }
                            else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder alertbox = new AlertDialog.Builder(confirm_OTP.this,R.style.AlertDialogTheme);
                                        String rer = getString(R.string.expirydate_alert);
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

                        }
                    });
//                    AsyncTask.execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            //TODO your background code
//                            try {
//                                confirm_OTP.call_me();
//                                JSONObject postData = new JSONObject();
//                                try {
//                                    postData.put("deviceID", device_id);
//                                    String url = "http://knowledgebags.com/?slm_action=slm_check&secret_key=5d9c9a700b9aa6.64670171&license_key="+llll;
//                                    new SendDeviceDetails().execute(url, postData.toString());
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                                expii1();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    });
                }
            }
        });
        lastlogin();
//        checkkk();
    }

    //License1() - get license of user and upload an backup
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void License1(){
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY = "SELECT * FROM dd_collection where id = ?";
        String whereClause = "ID=?";
        String[] whereArgs = new String[] {"1"};
        String[] columns = {"license",
                "cc","ID","email","expiry","language","pdf_password","phone_number"};
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

                    if(License == null || License.equalsIgnoreCase("")){
//                        License = "no";
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("isLoading","no");
                        editor.apply();
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat dff = new SimpleDateFormat("dd-MM-yyyy");
                        String formattedDate = dff.format(c.getTime());
                        String pathh1 = "License"+"/daily/"+formattedDate;

//                        ((Callback)getApplication()).show_filee1(pathh1,"License",pref);
                    }else {


                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat dff = new SimpleDateFormat("dd-MM-yyyy");
                        String formattedDate = dff.format(c.getTime());
                        String pathh1 = License+"/daily/"+formattedDate;
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("server", 0); // 0 - for private mode
                        ((Callback)getApplication()).show_filee1(pathh1,License,pref);
//                        show_filee1(pathh1);
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


                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("oldv1",oldv1);
                    editor.putInt("newv1",newv1);
                    editor.apply();
                    Integer oldv = pref.getInt("oldv",0);
                    Integer newv = pref.getInt("newv",0);
                    Log.d("oldd_neww",String.valueOf(oldv1)+" "+String.valueOf(newv1)+" : "+String.valueOf(oldv)+" "+String.valueOf(newv));
                   if(newv.equals(newv1)){

                    }else{
                        if(newv1<=11){
                            ((Callback)getApplication()).populateRecyclerView_new1(getApplicationContext(),"1");
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

    //deleteotp() - update otp
    //Params - user name and password
    //Created on 25/01/2022
    //Return - NULL
    public void deleteotp(String us,String psa){
        sqlite = new SQLiteHelper(confirm_OTP.this);
        database = sqlite.getWritableDatabase();
        String table = "dd_user";
        String whereClause = "username = ? AND password = ?";
        String[] whereArgs = new String[] {us,psa};
       ContentValues vv = new ContentValues();
       vv.put("otp","");
        database.update(table,vv,whereClause,whereArgs);
    sqlite.close();
    database.close();
    }

    //lastlogin() - put login time
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void lastlogin(){
//        sqlite = new SQLiteHelper(Login.this);
//        database = sqlite.getReadableDatabase();

        Calendar c = Calendar.getInstance();
        String ss2 = String.valueOf(c.getTime());
        Log.d("datedd",ss2);
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String hh = dateFormat.format(c.getTime());
        Log.d("datedd1",hh);

//        vv.put("last_login_time",);
    }

    //onRequestPermissionsResult() - request read external storage
    //Params - filled by android itself
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
        public static boolean checkConnection(Context context) {
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
            }
            return false;
        }
    }

    //mainvalues() - get main info
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void mainvalues(){
        String[] columns1 = {
                "dd_name","dd_location","starting_date"};
        sqlite = new SQLiteHelper(confirm_OTP.this);
        database = sqlite.getWritableDatabase();
        Cursor cursor = database.query("dd_main_info",
                columns1,
                null,
                null,
                null,
                null,
                null);
        if (cursor != null){
            if(cursor.getCount() != 0){
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
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("dd_name");
                    uusee = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("dd_location");
                    poo = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("starting_date");
                    String EMAIL = cursor.getString(index);

//                    name.setText(USER);
//                    locate.setText(PASS);
//                    dat.setText(EMAIL);
//                    Log.d("uuss",USER);


//                    String nmmm = pref.
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
    //savin() - get user details and store in preferrence
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void savin(){
        sqlite = new SQLiteHelper(confirm_OTP.this);
        database = sqlite.getWritableDatabase();
        cursor = database.rawQuery("SELECT * FROM "+SQLiteHelper.TABLENAME12+" WHERE "+SQLiteHelper.USNAM+"=? AND "+SQLiteHelper.USPASS+" =?",new String[]{user,pass});
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



                    index = cursor.getColumnIndexOrThrow("username");
                    USER = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("password");
                    PASS = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("otp");
                    OTP = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("ID");
                    ID = cursor.getString(index);

                    Log.d("ididi",ID);

                    editor.putString("user",USER);
                    editor.putString("pass",PASS);
                    editor.putString("otp",OTP);
                    editor.putString("id",ID);
                    editor.putInt("isloginn",1);
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
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }
    }
    //expiiiid() - called when license is over
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void expiiiid(){
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
                    expiry = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("license");
                    License = cursor.getString(index);

                    Log.d("langg",expiry);
                    Log.d("dates", String.valueOf(formattedDate));


                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    try {
                        Date newda = df.parse(formattedDate);
                        Date debit = df.parse(expiry);
                        long diffInMillisec = debit.getTime() - newda.getTime();
                        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);

                        inlin.setVisibility(View.GONE);
                        liclin.setVisibility(View.VISIBLE);
                        liccc.setText(License);
                        expip.setText(expiry);

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
    //expiiii() - check expiry date
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void expiiii(){
        Log.d("internet2",formattedDate);
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
                    expiry = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("license");
                    License = cursor.getString(index);

                    Log.d("langg",expiry);
                    Log.d("dates", String.valueOf(formattedDate));


                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    try {
                        Date newda = df.parse(formattedDate);
                        Date debit = df.parse(expiry);
                        long diffInMillisec = debit.getTime() - newda.getTime();
                        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                        if (diffInDays < 0) {
                            Log.d("dates", String.valueOf(diffInDays));
                            AlertDialog.Builder alertbox = new AlertDialog.Builder(confirm_OTP.this, R.style.AlertDialogTheme);
                            String logmsg = getString(R.string.expirydate_alert);
                            String cann = getString(R.string.cancel);
                            String warr = getString(R.string.warning);
                            String ook = getString(R.string.ok);
                            alertbox.setMessage(logmsg);
                            alertbox.setTitle(warr);
                            alertbox.setIcon(R.drawable.dailylogo);
                            alertbox.setCancelable(false);
                            alertbox.setNeutralButton(ook,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface arg0,
                                                            int arg1) {
                                            inlin.setVisibility(View.GONE);
                                            liclin.setVisibility(View.VISIBLE);
                                            liccc.setText(License);
                                            expip.setText(expiry);

                                        }
                                    });
                            alertbox.show();

                        } else {
                            Log.d("dates", String.valueOf(diffInDays));

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
    //expiiii1() - check expiry date and move to dashboard
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void expiiii1(){
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
//        String MY_QUERY = "SELECT * FROM dd_collection where id = ?";
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
                    expiry = cursor.getString(index);
                    Log.d("langg",expiry);

                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

                    try {
                        Date newda = df.parse(formattedDate);
                        Date debit = df.parse(expiry);
                        long diffInMillisec =    debit.getTime() - newda.getTime();
                        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                        if(diffInDays < 0){
                            Log.d("dates", String.valueOf(diffInDays));
                            new Thread()
                            {
                                public void run()
                                {
                                    confirm_OTP.this.runOnUiThread(new Runnable()
                                    {
                                        public void run()
                                        {
                                            //Do your UI operations like dialog opening or Toast here
                                            AlertDialog.Builder alertbox1 = new AlertDialog.Builder(confirm_OTP.this,R.style.AlertDialogTheme);
                                            String logmsg = getString(R.string.expirydate_alert);
                                            String cann = getString(R.string.cancel);
                                            String warr = getString(R.string.warning);
                                            String ook = getString(R.string.ok);
                                            alertbox1.setMessage(logmsg);
                                            alertbox1.setTitle(warr);
                                            alertbox1.setIcon(R.drawable.dailylogo);
//                                            alertbox1.setCancelable(false);
                                            alertbox1.show();
                                        }
                                    });
                                }
                            }.start();
                        }else {
                            Log.d("dates", String.valueOf(diffInDays));
                            cursor.close();
                            sqlite.close();
                            database.close();
                            Intent login = new Intent(confirm_OTP.this, NavigationActivity.class);
                            login.putExtra("user", user);
                            login.putExtra("pass", pass);
                            startActivity(login);
                            finish();
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
    //expii1() - intent to Confirm_OTP
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void expii1(){
        sqlite = new SQLiteHelper(confirm_OTP.this);
        database = sqlite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("expiry", todate);
        values.put("license", llll);
        database.update("dd_settings",  values,"ID = ?",new String[]{"1"});
        sqlite.close();
        database.close();
        expiiii1();
    }
    //    public void imei111(){
//        sqlite = new SQLiteHelper(this);
//        database = sqlite.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("IMEI", device_id);
//        database.update("dd_settings",  values,"ID = ?",new String[]{"1"});
//    }


    //call_me() -get expiry date from server
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static void call_me() throws Exception {
//        http://www.knowledgebags.com/?secret_key=5d9c9a700b9aa6.64670171&slm_action=slm_activate&license_key=5dd14f6b498af&registered_domain=asdfjweruowefu2342384084
//        String url = "http://knowledgebags.com/?slm_action=slm_check&secret_key=5d9c9a700b9aa6.64670171&license_key="+llll;
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
        String[] separated = device_id1.split(",");
        String deer = separated[3];
        String[] separated1 = deer.split(":");
        String deer1 = separated1[1];
        device_id1 = deer1.replace("\"", "");
        product_ref = myResponse.getString("product_ref");
        System.out.println("registered_domains- "+myResponse.getString("registered_domains"));
        System.out.println("registered_domains- "+device_id1);

    }
    public static void call_me2() throws Exception {
//        http://www.knowledgebags.com/?secret_key=5d9c9a700b9aa6.64670171&slm_action=slm_activate&license_key=5dd14f6b498af&registered_domain=asdfjweruowefu2342384084
//        String url = "http://knowledgebags.com/?slm_action=slm_check&secret_key=5d9c9a700b9aa6.64670171&license_key="+llll;

//        String url = "http://www.knowledgebags.com/?secret_key=5d9c9a700b9aa6.64670171&slm_action=slm_activate&license_key="+llll+"&registered_domain="+device_id;
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
    private class SendDeviceDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = "";

            HttpURLConnection httpURLConnection = null;
            try {

                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes("PostData=" + params[1]);
                wr.flush();
                wr.close();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }
    public void onBackPressed() {
    }
}
