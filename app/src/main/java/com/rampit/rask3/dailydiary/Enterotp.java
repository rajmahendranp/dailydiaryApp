package com.rampit.rask3.dailydiary;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
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

import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.util.Locale;

//Updated on 25/01/2022 by RAMPIT
//used to enter and save OTP of user

public class Enterotp extends AppCompatActivity {
    EditText otp1,otp2;
    Button log,newlog;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    Cursor cursor;
    public static String TABLENAME = "dd_user";
    String user,pass,otp;
    private PopupWindow mPopupWindow;
    ImageButton eyes,eyes1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ii = pref.getString("theme","");
        if(ii.equalsIgnoreCase("")){
            ii = "1";
        }
        Log.d("thee",ii);
        Integer in = Integer.valueOf(ii);
//        Integer in = 0;
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
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
//        ((Callback)getApplication()).curl_brightness(Enterotp.this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_enterotp);
        otp1 = findViewById(R.id.otppass);
        otp2 = findViewById(R.id.cotp);
        log = findViewById(R.id.loginpass);
        newlog = findViewById(R.id.login);
        eyes = findViewById(R.id.eyes);
        eyes1 = findViewById(R.id.eyes1);
        if(in == 0){
            eyes.setBackgroundResource(R.drawable.eye_orange);
            eyes1.setBackgroundResource(R.drawable.eye_orange);
//            otp1.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_orange, 0);
//            otp2.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_orange, 0);
        }else{
            eyes.setBackgroundResource(R.drawable.eye_blue);
            eyes1.setBackgroundResource(R.drawable.eye_blue);
//            otp1.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_blue, 0);
//            otp2.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_blue, 0);
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
        eyes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String naam = otp2.getText().toString();
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
        Intent login = getIntent();
        user = login.getStringExtra("user");
        pass = login.getStringExtra("pass");
        otp = login.getStringExtra("otp");
//        sqlite = new SQLiteHelper(this);
//        Log.d("otp",otp);
//        database = sqlite.getWritableDatabase();
        newlog.setVisibility(View.VISIBLE);
        newlog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                String oo1 = otp1.getText().toString();
                String oo2 = otp2.getText().toString();
                if (oo1.equalsIgnoreCase("") || oo2.equalsIgnoreCase("")) {
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(Enterotp.this, R.style.AlertDialogTheme);
                    String rer = getString(R.string.fill_all);
                    String rer1 = getString(R.string.warning);
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
                    if (oo1.equalsIgnoreCase(oo2)) {
                        sqlite = new SQLiteHelper(Enterotp.this);
                        database = sqlite.getReadableDatabase();
                        String username = otp1.getText().toString();
                        ContentValues cv = new ContentValues();
                        cv.put("otp", username);
                        database.update(TABLENAME, cv, "username = ?", new String[]{user});
                        sqlite.close();
                        database.close();
                        Intent login = new Intent(Enterotp.this, confirm_OTP.class);
                        login.putExtra("user", user);
                        login.putExtra("pass", pass);
                        Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                                android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                        startActivity(login, bundle);
                        finish();
                    } else {
                        AlertDialog.Builder alertbox = new AlertDialog.Builder(Enterotp.this, R.style.AlertDialogTheme);
                        String rer = getString(R.string.otpmiss);
                        String rer1 = getString(R.string.warning);
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
                }
            }
        });

    }

    public void onBackPressed() {
    }
}
