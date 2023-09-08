package com.rampit.rask3.dailydiary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdaptertotalaccounts;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;



//Updated on 25/01/2022 by RAMPIT
//Show all accounts
//InternetConnection1() - Check whether internet is on or not
//closing_acc() - Intent to clossing account activity
//onBackPressed() - function called when back button is pressed
//populateRecyclerView() - get all debit account of customers
//backupDatabase() - get backup of current database
//sendEmail1() - Send email
//progressbar_load() - Load progressbar
//progressbar_stop1() - Stop progressbar
//progressbar_stop() - Stop progressbar

public class Totalaccounts extends AppCompatActivity {

    TextView addd,dateee,session,user,noo;
    Integer ses;
    String timing,adpr,language;
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> Names2 = new ArrayList<>();
    ArrayList<String> ooqw = new ArrayList<>();
    ArrayList<String> CURR = new ArrayList<>();
    ArrayList<String> NNP = new ArrayList<>();
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    RecyclerView recyclerView;
    RecyclerViewAdaptertotalaccounts mAdapter;
    ImageView seesim,reorder;
    Cursor cursor;
    Integer in;
    String s,idi,dateString,datedd,dated1,selected,cleared,datead,idicid;
    Integer cle1,see1,see2,see,cle,lastcurorder,lastnipniporder,dayses;
    Dialog dialog;
    Calendar c;
    String todaaaa,pdfname;
    private File pdfFile;
    CheckBox active_accounts_greater;
    Integer showinng = 0 ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
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
        setContentView(R.layout.activity_totalaccount);
        setTitle(R.string.title_user_activity);
        ((Callback)getApplication()).datee();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //auto_email aa = new auto_email();
        //      aa.vall(getApplicationContext());
        dayses = pref.getInt("day",0);
        adpr = pref.getString("add_privilege","");
        ses = pref.getInt("session", 1);
        language = pref.getString("language","");
        String dd = pref.getString("Date","");
        dateee = findViewById(R.id.da);
        noo = findViewById(R.id.no);
        seesim = findViewById(R.id.sesimg);
        addd = findViewById(R.id.add);
        session = findViewById(R.id.sess);
        recyclerView = findViewById(R.id.re);
        reorder = findViewById(R.id.reorder);
        active_accounts_greater = findViewById(R.id.active_accounts_greater);
        active_accounts_greater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    // checked
                    mAdapter.filter("1");
                }
                else
                {
                    // not checked
                    mAdapter.filter("0");
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
                            final SharedPreferences pref11 = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                            String isLoading = pref11.getString("isLoading","no");
                            String isLoaded_time = pref11.getString("isLoaded_time","no");
                            Log.d("isLoading",isLoading+" "+isLoaded_time);
                            if (InternetConnection1.checkConnection(getApplicationContext(),Totalaccounts.this)) {
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
        lastcurorder = 1;
        lastnipniporder = 5001;
        dateee.setText(dd);
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
        dialog = new Dialog(Totalaccounts.this,R.style.CustomDialog);
        dayy.setText(weekday_name);
        if(in == 0){
            addd.setBackgroundResource(R.drawable.add);
            if(ses == 1){
                timing = getString(R.string.morning);
                seesim.setBackgroundResource(R.drawable.sun);
            }else if(ses == 2){
                timing = getString(R.string.evening);
                seesim.setBackgroundResource(R.drawable.moon);
            }
        }
        else{
            addd.setBackgroundResource(R.drawable.add_blue);
            if(ses == 1){
                timing = getString(R.string.morning);
                seesim.setBackgroundResource(R.drawable.sun_blue);
            }else if(ses == 2){
                timing = getString(R.string.evening);
                seesim.setBackgroundResource(R.drawable.moon_blue);
            }
        }
        session.setText(timing);
        progressbar_load();
        populateRecyclerView();
        Intent nn = getIntent();
        selected = nn.getStringExtra("selected");
        cleared = nn.getStringExtra("cleared");
        idi = nn.getStringExtra("idi");
//        Log.d("naid",idi);
        if(selected != null && cleared != null && idi != null){
//            upddate();
//            list1();
//            list();
        }

    }
    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Totalaccounts account) {
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

    //closing_acc() - Intent to clossing account activity
    //Params - customer id
    //Created on 25/01/2022
    //Return - NULL
    public  void closing_acc(String iidd){
        Intent totalaccounts = new Intent(Totalaccounts.this,Closing_account_activity.class);
        totalaccounts.putExtra("cid",iidd);
        startActivity(totalaccounts);
        finish();
    }
    //onBackPressed() - function called when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onBackPressed() {
        Intent un = new Intent(Totalaccounts.this,Settings_activity.class);
        startActivity(un);
        finish();
    }
    //populateRecyclerView() - get all debit account of customers
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView() {
        Names.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY = "SELECT a.*,COUNT (b.id) as count , sum(case when b.active_status LIKE 1 then 1 end) as active, " +
                "    sum(case when b.active_status LIKE 0 then 1 end) as inactive   FROM dd_customers a " +
                "LEFT JOIN dd_account_debit b ON b.customer_id = a.id " +
                " GROUP BY a.id ";
//        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{String.valueOf(ses)});
        Cursor cursor = database.rawQuery(MY_QUERY,null);
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);
                    Log.d("Name_name_name",Name);
                    index = cursor.getColumnIndexOrThrow("id");
                    long id = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("CID");
                    long cid = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("order_id");
                    String order = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("tracking_id");
                    String tracking_id = cursor.getString(index);
                    Integer tracking_id_int = cursor.getInt(index);

                    index = cursor.getColumnIndexOrThrow("count");
                    String count = cursor.getString(index);


                    index = cursor.getColumnIndexOrThrow("active");
                    String active = cursor.getString(index);
                    Integer active_int = cursor.getInt(index);

                    index = cursor.getColumnIndexOrThrow("inactive");
                    String inactive = cursor.getString(index);
                    if(active == null || active.equalsIgnoreCase("") || active.equalsIgnoreCase("null")){
                        active = "0" ;
                    }
                    if(inactive == null || inactive.equalsIgnoreCase("") || inactive.equalsIgnoreCase("null")){
                        inactive = "0" ;
                    }
                    if(active_int >1){
                        c = Calendar.getInstance();
                        DateFormat dff = new SimpleDateFormat("MMM_d_yyyy_hh_mm_aaa");
                        todaaaa = dff.format(c.getTime());
//                        backupDatabase();
                    }
                    Log.d("tracking",tracking_id);
                    if(tracking_id_int.equals(1)){
                        tracking_id = getString(R.string.morning);
                    }else if(tracking_id_int.equals(2)){
                        tracking_id =  getString(R.string.evening);
                    }

//                    if(typ.equalsIgnoreCase("0")){
                    Names.add(id+",,,"+Name+",,,"+count+",,,"+active+",,,"+inactive+",,,"+tracking_id);
                    Log.d("countcount",active+" "+inactive+" "+count+" "+String.valueOf(Names.size()));
//                        Names2.add(id+","+Name+","+order+","+typ+","+cid);
//                        lastcurorder = Integer.valueOf(order);
//                        CURR.add(typ);
//                    }else if(typ.equalsIgnoreCase("1")){
//                        Names.add(id+","+Name+","+order+","+typ+","+cid);
//                        Names2.add(id+","+Name+","+order+","+typ+","+cid);
//                        lastcurorder = Integer.valueOf(order);
//                        CURR.add(typ);
//                    }else if(typ.equalsIgnoreCase("2")){
//                        Names.add(id+","+Name+","+order+","+typ+","+cid);
//                        Names2.add(id+","+Name+","+order+","+typ+","+cid);
//                        lastnipniporder = Integer.valueOf(order);
//                        NNP.add(typ);
//                    }

//                    Log.d("names", String.valueOf(Names));
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
        if(Names.size() == 0){
            progressbar_stop1();
            recyclerView.setVisibility(View.GONE);
            noo.setVisibility(View.VISIBLE);
        }else {
            progressbar_stop1();
            recyclerView.setVisibility(View.VISIBLE);
            noo.setVisibility(View.GONE);
            mAdapter = new RecyclerViewAdaptertotalaccounts(getApplicationContext(),Names,Totalaccounts.this);
//            mAdapter.setHasStableIds(true);
            mAdapter.setHasStableIds(true);
//            recyclerView.setAdapter(mAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(mAdapter);
        }
    }
    //backupDatabase() - get backup of current database
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void backupDatabase() {
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
////        final String inFileName =getApplicationContext().getDatabasePath("WEEKLY").getPath();
//        final String inFileName ="/data/user/0/com.rampit.rask3.dailydiary/databases/WEEKLY";
////        String inFileName = "/data/data/com.myapp.main/databases/MYDB";
//        File dbFile = new File(inFileName);
//        FileInputStream fis = new FileInputStream(dbFile);
//
//        String outFileName = Environment.getExternalStorageDirectory()+"/WEEKLY";
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
        if (confirm_OTP.InternetConnection.checkConnection(getApplicationContext())) {
            // Internet Available...
            Log.d("internet","on");
//            date32();

            sendEmail1();
        }
        else {
            // Internet Not Available...
            Log.d("internet","off");
            final AlertDialog.Builder alertbox = new AlertDialog.Builder(Totalaccounts.this,R.style.AlertDialogTheme);
            String enn = getString(R.string.on_internet);
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
//            alertbox.show();

        }

    }
    //sendEmail1() - Send email
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void sendEmail1() {
        //Getting content for email
//        String email = "abilashmohanan38@gmail.com";
//        String email = "rampittech@gmail.com";
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String user = pref.getString("user","");
        String pass = pref.getString("pass","");
        String email = "rampittech@gmail.com";
        String subject = "Subject";
        String message = "Username :"+" "+user +"\n"+"Password :"+" "+pass;

        //Creating SendMail object
        Log.d("pdffile", String.valueOf(pdfFile));
        String ppp = String.valueOf(pdfFile);
        if(email.equalsIgnoreCase("")){
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Totalaccounts.this,R.style.AlertDialogTheme);
            String logmsg = getString(R.string.enteremail);
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

                            Intent i = new Intent(Totalaccounts.this,Settings_activity.class);
                            startActivity(i);
                            finish();
                        }
                    });
            alertbox.show();
        }else {
            SendMail_noprogress sm = new SendMail_noprogress(this, email, subject, message, ppp, pdfname);
            //Executing sendmail to send email
            sm.execute();
        }
    }

    //    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.settings_activity, menu);
//
//        MenuItem getItem = menu.findItem(R.id.action_back);
//        if (getItem != null) {
//            AppCompatButton button = (AppCompatButton) getItem.getActionView();
//            if(in == 0){
//                button.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bordered_button123) );
//            }else{
//                button.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bordered_button12) );
//            }
//            button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.White));
//            button.setText(getString(R.string.reorder));
//            Typeface ty = Typeface.createFromAsset(getApplicationContext().getAssets(),"opensans.ttf");
//            button.setTypeface(ty);
//            button.setTextSize(13);
////            button.setLeft(10);
//            button.setTop(10);
//            button.setHeight(10);
//            button.setWidth(25);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                                    ((Callback)getApplication()).populateRecyclerView(ses);
////                    ((Callback)getApplication()).populateRecyclerViewnip(ses);
////                    progressbar_load1();
//                    syncEvent ss = new syncEvent();
//                    ss.execute();
//
//                }
//            });
//            //Set a ClickListener, the text,
//            //the background color or something like that
//        }
//        return super.onCreateOptionsMenu(menu);
//    }
//@Override
//public boolean onCreateOptionsMenu(Menu menu) {
//    // Inflate the menu; this adds items to the action bar if it is present.
//    getMenuInflater().inflate(R.menu.back_button1, menu);
//    return true;
//}


    //progressbar_load() - Load progressbar
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void progressbar_load(){
        //set layout custom
        Log.d("start_start","1");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
    //progressbar_stop1() - Stop progressbar
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void progressbar_stop1(){
        dialog.dismiss();
        Log.d("start_start","0");
    }
    //progressbar_stop() - Stop progressbar
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void progressbar_stop(){
        dialog.dismiss();
        Intent nn = new Intent(Totalaccounts.this,Totalaccounts.class);
        startActivity(nn);
        finish();
    }
}
