package com.rampit.rask3.dailydiary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rampit.rask3.dailydiary.Adapter.Language_adapter;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



//Updated on 25/01/2022 by RAMPIT
//Register an user
//progressbar_load() - Load progressbar
//progre() - Stop progressbar
//enter_license() - Enter license popup
//show_filee() - Download backup from firebase
//DownloadingTask() - AsyncTask to Download backup from url
//importDB() - Import downloaded backup
//selected_language() - Change selcted language of app
//selected_language() - selct language of app
//popup() - Terms and condition popup
//WebViewClient() - open webpage for terms and condition
//onResume() - function done activity resumes



public class Register extends AppCompatActivity {

    EditText name,pass,cpass,city,pin,email,phone;
    Button submit,ress;
    Integer newv,checked = 0;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    public static String TABLENAME = "dd_user";
    private PopupWindow mPopupWindow;
    private static final int REQUEST_READ_PHONE_STATE = 1;
    static String llll, device_id, eer, actsta, device_id1, expiii;
    ImageButton eyes,eyes1;
    CheckBox sign_in_radio_id;
    TextView terms_cond;
    WebView webview;
    ProgressBar progressBar;
    Spinner spinner;
    String lann ,downloadUrl;
    Dialog dialog;
    File originalFile;
    TextView already_have;
    String inFileName,comname,file_id,folder_id;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ii = pref.getString("theme","");
        if(ii.equalsIgnoreCase("")){
            ii = "1";
        }
        Log.d("theelo",ii);
        Integer in = Integer.valueOf(ii) ;
        if(in == 0){
            Log.d("theelo","thh");
            setTheme(R.style.AppTheme1);
        }else{
            Log.d("theelo","th1h");
            setTheme(R.style.AppTheme11);
//            recreate();
        }
        String localeName = pref.getString("language","");
        if(localeName == null){
            localeName = "ta";
        }
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog = new Dialog(Register.this,R.style.CustomAlertDialog);
//        ActivityCompat.requestPermissions(Register.this, new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS}, 1);
//        ActivityCompat.requestPermissions(Register.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        List<String> list = new ArrayList<String>();
        list.add("Select language");
        list.add("English");
        list.add("Français");
        list.add("Tamil");
//        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
//            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//            final String imeiNumber1 = tm.getDeviceId(0);
//            final String imeiNumber2 = tm.getDeviceId(1);
//            Log.d("deee1", imeiNumber1);
//            Log.d("deee2", imeiNumber2);
////        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//
//            device_id = tm.getDeviceId();
//            Log.d("deee",device_id);
//            if(device_id == null || device_id.equalsIgnoreCase("")){
//            }else{
//                imei111();
//            }
//        } else {
//            //TODO
//        }
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_READ_PHONE_STATE);
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
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.setContentView(R.layout.activity_register);
        sign_in_radio_id = findViewById(R.id.sign_in_radio_id);
        terms_cond = findViewById(R.id.terms_cond);
        name = findViewById(R.id.name);
        pass = findViewById(R.id.password);
        cpass = findViewById(R.id.confirmpassword);
        eyes = findViewById(R.id.eyes);
        eyes1 = findViewById(R.id.eyes1);
        submit = findViewById(R.id.submit);
        ress = findViewById(R.id.reset);
        already_have = findViewById(R.id.already_have);
        already_have.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter_license();
            }
        });
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
                            sqlite = new SQLiteHelper(Register.this);
                            database = sqlite.getWritableDatabase();
                            ContentValues values1 = new ContentValues();
                            values1.put("language", lann);
                            database.update("dd_settings", values1,"ID = ?", new String[]{"1"});
                            sqlite.close();
                            database.close();
                            Intent reg = new Intent(Register.this,Register.class);
                            startActivity(reg);
//                            recreate();
                        }else if(typeid.equalsIgnoreCase("2")){
                            lann = "fr";
                            editor.putString("language",lann);
                            editor.apply();
                            spinner.setTag("null");
                            sqlite = new SQLiteHelper(Register.this);
                            database = sqlite.getWritableDatabase();
                            ContentValues values1 = new ContentValues();
                            values1.put("language", lann);
                            database.update("dd_settings", values1,"ID = ?", new String[]{"1"});
                            sqlite.close();
                            database.close();
                            Log.d("switch1",String.valueOf(lann));
                            Intent reg = new Intent(Register.this,Register.class);
                            startActivity(reg);
//                            recreate();
                        }else if(typeid.equalsIgnoreCase("3")){
                            lann = "ta";
                            editor.putString("language",lann);
                            editor.apply();
                            Log.d("switch1",String.valueOf(lann));
                            spinner.setTag("null");
                            sqlite = new SQLiteHelper(Register.this);
                            database = sqlite.getWritableDatabase();
                            ContentValues values1 = new ContentValues();
                            values1.put("language", lann);
                            database.update("dd_settings", values1,"ID = ?", new String[]{"1"});
                            sqlite.close();
                            database.close();
                            Intent reg = new Intent(Register.this,Register.class);
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
        select_language();
//        sqlite = new SQLiteHelper(this);
        ress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText("");
                pass.setText("");
                cpass.setText("");
            }
        });
//        languaaa();
        if(in == 0){
            eyes.setBackgroundResource(R.drawable.eye_orange);
            eyes1.setBackgroundResource(R.drawable.eye_orange);
            terms_cond.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
            sign_in_radio_id.setButtonDrawable(R.drawable.custom_checkbox);
//            pass.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_orange, 0);
//            cpass.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_orange, 0);
        }else{
            eyes1.setBackgroundResource(R.drawable.eye_blue);
            eyes1.setBackgroundResource(R.drawable.eye_blue);
            terms_cond.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.backblue)));
            sign_in_radio_id.setButtonDrawable(R.drawable.custom_checkbox1);
//            pass.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_blue, 0);
//            cpass.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.eye_blue, 0);
        }
        sign_in_radio_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sign_in_radio_id.isChecked()) {
                    checked = 1;
//                    Toast.makeText(getApplicationContext(),"First checkbox checked", Toast.LENGTH_SHORT).show();
                } else {
                    checked = 0;
//                    Toast.makeText(getApplicationContext(),"First checkbox Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        terms_cond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup();
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
        eyes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String naam = cpass.getText().toString();
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
        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                String cpa = cpass.getText().toString();
                String paa = pass.getText().toString();
                String nama = name.getText().toString();
                if(checked.equals(1)){
                    if(cpa.equalsIgnoreCase("") || paa.equalsIgnoreCase("") || nama.equalsIgnoreCase("")){
                        AlertDialog.Builder alertbox = new AlertDialog.Builder(Register.this,R.style.AlertDialogTheme);
                        String rer = getString(R.string.fill_all);
                        String rer1 = getString(R.string.warning);
                        String rer2= getString(R.string.ok);
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
                    else{
                        Log.d("passss",cpa+" "+paa);
                        if(cpa.equalsIgnoreCase(paa)){
                            String user = name.getText().toString();
                            String password = pass.getText().toString();
                            sqlite = new SQLiteHelper(Register.this);
                            database = sqlite.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            user = user.trim();
                            values.put("username",user);
                            values.put("password",password);
                            database.insert(TABLENAME,null,values);
                            sqlite.close();
                            database.close();
                            Toast.makeText(getApplicationContext(), " Your account created successfully ", Toast.LENGTH_SHORT).show();
                            Intent login = new Intent(Register.this,Enterotp.class);
                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                            login.putExtra("user",user);
                            login.putExtra("pass",password);
                            login.putExtra("otp","0");
                            startActivity(login,bundle);                    }else{
                            AlertDialog.Builder alertbox = new AlertDialog.Builder(Register.this,R.style.AlertDialogTheme);
                            String enn = getString(R.string.passs_miss);
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
                }else{
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(Register.this, R.style.AlertDialogTheme);
                    String enn = getString(R.string.please_check_term);
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

    //enter_license() - Enter license popup
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void enter_license(){
        Log.d("db_db","dbbb");
        final Dialog dialog1 = new Dialog(Register.this);
        //set layout custom
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.license_popup);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog1.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog1.getWindow().setAttributes(lp);
        final EditText username,password,otp,license,email,number,pdf_pass;
        Button download;
        TextView close;
        license = dialog1.findViewById(R.id.license);
        download = dialog1.findViewById(R.id.download);


        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String licc = license.getText().toString();

                if(licc == null || licc.equalsIgnoreCase("") || licc.equalsIgnoreCase("null")){
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(Register.this);
                    String enn = getString(R.string.enter_license);
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

                    show_filee(licc+"/admin/dd_backup","Admin");

                }

            }
        });



        dialog1.show();
    }

    //show_filee() - Download backup from firebase
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
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Register.this,R.style.AlertDialogTheme);
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

    //DownloadingTask() - AsyncTask to Download backup from url
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
                                + "DailyDiary_downloads");
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
                    originalFile = new File(FileUtils1.getRealPathFromURI_BelowAPI11(Register.this, uri));
                    Log.d("rtrt3", String.valueOf(originalFile));
                }
                // SDK >= 11 && SDK < 19
                else if (Build.VERSION.SDK_INT < 19) {
                    originalFile = new File(FileUtils1.getRealPathFromURI_API11to18(Register.this, uri));
//                        realPath = FileUtils1.getRealPathFromURI_API11to18(context, fileUri);
                    Log.d("rtrt2", String.valueOf(originalFile));
                }
                // SDK > 19 (Android 4.4) and up
                else {
                    originalFile = new File(FileUtils1.getRealPathFromURI_API19(Register.this, uri));
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

    //importDB() - Import downloaded backup
    //Params - output and input filename
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
//                String backupDBPath = "WV4.db";
//                File currentDB = new File(data, currentDBPath);
//                File backupDB = new File(sd, backupDBPath);
//                String outFileName = Environment.getExternalStorageDirectory()+"/WV4";
//                String inFileName = getApplicationContext().getDatabasePath("WV4").getPath();
//                final String currentDBPath ="/data/user/0/com.rampit.rask3.dailydiary/databases/WV4";
//                String  currentDBPath= "//user//" +"//0//" + "com.rampit.rask3.dailydiary"
//                        + "//databases//" + "WV4";
//                String backupDBPath  = "/BackupFolder/WV4";
//                File  backupDB= new File(data, currentDBPath);
//                File currentDB  = new File(sd, backupDBPath);
//                SQLiteHelper sql = new SQLiteHelper(ExportDB.this);
                File currentDB = new File(outFileName);
                File backupDB = new File(inFileName);
                Log.d("rtrtrtr",String.valueOf(currentDB));
                Log.d("rtrtrtr",String.valueOf(backupDB));

                SQLiteHelper sql = new SQLiteHelper(Register.this);

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
                                    Intent qw = new Intent (Register.this,Login.class);
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
                                    Intent qw = new Intent (Register.this,Login.class);
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

    //selected_language() - Change selcted language of app
    //Params - Language name
    //Created on 25/01/2022
    //Return - NULL
    public void selected_language(String lang){
        dialog.cancel();
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("language",lang);
        editor.apply();
        spinner.setTag("null");
        sqlite = new SQLiteHelper(Register.this);
        database = sqlite.getWritableDatabase();
        ContentValues values1 = new ContentValues();
        values1.put("language", lang);
        database.update("dd_settings", values1,"ID = ?", new String[]{"1"});
        sqlite.close();
        database.close();
        Log.d("switch1",String.valueOf(lang));
        Intent reg = new Intent(Register.this,Register.class);
        startActivity(reg);
    }

    //selected_language() - selct language of app
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void select_language(){
//        final Dialog dialog = new Dialog(Register.this,R.style.CustomDialog);
        //set layout custom
//        Log.d("district",String.valueOf(disss1));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.select_language);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        Language_adapter mAdapter;
        RecyclerView recyclerView;
        Button submit,closee,select_all;
        List<String> list = new ArrayList<String>();
        list.add("English");
        list.add("Français");
        list.add("Tamil");
        TextView close,editText;
        recyclerView = dialog.findViewById(R.id.recyclerview);
        close = dialog.findViewById(R.id.close);
        mAdapter = new Language_adapter(getApplicationContext(),list,Register.this,lann);
        mAdapter.setHasStableIds(true);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemViewCacheSize(1000);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter.onAttachedToRecyclerView(recyclerView);
        LinearLayoutManager layoutManager= new LinearLayoutManager(Register.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //popup() - Terms and condition popup
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void popup(){

        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();

        final Dialog dialog = new Dialog(Register.this);
        //set layout custom
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_terms);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        webview = (WebView)dialog.findViewById(R.id.webView);
        progressBar = (ProgressBar)dialog.findViewById(R.id.progressBar);
        Button accept = (Button)dialog.findViewById(R.id.accept);
        Button cancel = (Button)dialog.findViewById(R.id.cancel);
//        web.loadUrl("https://www.javatpoint.com/android-webview-example");
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl("https://www.knowledgebags.com/daily-diary-privacy-policy/");
       /* Button close = (Button) dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });*/
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_in_radio_id.setChecked(true);
                checked = 1;
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_in_radio_id.setChecked(false);
                checked = 0;
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    //WebViewClient() - open webpage for terms and condition
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

    //onResume() - function done activity resumes
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    protected void onResume() {
        super.onResume();

    }
    //    public void imei111(){
//        sqlite = new SQLiteHelper(this);
//        database = sqlite.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("IMEI", device_id);
//        database.update("dd_settings",  values,"ID = ?",new String[]{"1"});
//    }
}
