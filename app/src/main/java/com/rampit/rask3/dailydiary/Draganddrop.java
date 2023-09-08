package com.rampit.rask3.dailydiary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
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
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdapterdraganddrop;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

//Updated on 25/01/2022 by RAMPIT
//used to drag and drop users
//InternetConnection1() - Check whether internet is on or not
//uppd() - Update user order id on drag and drop
//upddate() - Update user order id in database on drag and drop
//onBackPressed() - function called when back button is pressed
//populateRecyclerView() - All customers
//progressbar_load1() - Load progressbar
//progressbar_load() - Load progressbar
//progressbar_stop() - Stop progressbar

public class Draganddrop extends AppCompatActivity {

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
    RecyclerViewAdapterdraganddrop mAdapter;
    ImageView seesim,reorder;
    Cursor cursor;
    Integer in;
    String s,idi,dateString,datedd,dated1,selected,cleared,datead,idicid;
    Integer cle1,see1,see2,see,cle,lastcurorder,lastnipniporder;
     Dialog dialog;
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
        setContentView(R.layout.activity_draganddrop);
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
        lastcurorder = 1;
        lastnipniporder = 5001;
        dateee.setText(dd);
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),Draganddrop.this)) {
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
        dialog = new Dialog(Draganddrop.this,R.style.CustomDialog);
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
        }else{
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
//        progressbar_load();
        populateRecyclerView();

        Intent nn = getIntent();
        selected = nn.getStringExtra("selected");
        cleared = nn.getStringExtra("cleared");
        idi = nn.getStringExtra("idi");
//        Log.d("naid",idi);
        if(selected != null && cleared != null && idi != null){
            upddate();
//            list1();
//            list();
        }
        reorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Draganddrop.this,R.style.AlertDialogTheme);
                String logmsg = getString(R.string.customer_arrangement);
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

//                                syncEvent ss = new syncEvent();
//                                ss.execute();
                                progressbar_load();
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                uppd();
                                            }
                                        });
                                    }
                                }, 500);


                            }
                        });
                alertbox.setNegativeButton(cann,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {

                            }
                        });
                alertbox.show();

            }
        });

//        reorder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder alertbox = new AlertDialog.Builder(Draganddrop.this,R.style.AlertDialogTheme);
//                String warr = getString(R.string.warning);
//                String ok = getString(R.string.ok);
//                String cancel = getString(R.string.cancel);
//                alertbox.setMessage(R.string.reorder_alert);
//                alertbox.setTitle(warr);
//                alertbox.setIcon(R.drawable.dailylogo);
//                alertbox.setCancelable(false);
//                alertbox.setPositiveButton(ok,
//                        new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface arg0,
//                                                int arg1) {
//                                syncEvent ss = new syncEvent();
//                                ss.execute();
//                            }
//                        });
//                alertbox.setNegativeButton(cancel,
//                        new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface arg0,
//                                                int arg1) {
////                                syncEvent ss = new syncEvent();
////                                ss.execute();
//                            }
//                        });
//                alertbox.show();
////                syncEvent ss = new syncEvent();
////                ss.execute();
//            }
//        });

    }
    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Draganddrop account) {
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

    //uppd() - Update user order id on drag and drop
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public  void uppd(){
//        progressbar_load();
        Integer vv = Names.size() - 1 ;
        for(int y=0;y<Names.size();y++){
            String vaa = Names.get(y);
            String[] vvaaa = vaa.split(",");
            Log.d("getvaluessss",vvaaa[0]);
            Integer iplus = y+1;
           SQLiteHelper sqlite = new SQLiteHelper(this);
           SQLiteDatabase database = sqlite.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("order_id_new",iplus);
            String[] args =  new String[]{String.valueOf(vvaaa[0])};
            database.update("dd_customers",cv, "id = ?",args);
            sqlite.close();
            database.close();
            if(vv.equals(y)){

                populateRecyclerView();
                progressbar_stop();
            }
        }
    }

    //upddate() - Update user order id in database on drag and drop
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void upddate() {
        see = Integer.valueOf(selected);
        Integer cle = Integer.valueOf(cleared);
//        editor.putString("selectedvalue1", selectedvaaa);
//        editor.apply();
        see1 = see ;
        s = String.valueOf(see1);
        cle1 = cle ;
        Log.d("namessssss54", String.valueOf(see));
        Log.d("namessssss54", String.valueOf(cle));

        if (see1 < cle1) {
            sqlite = new SQLiteHelper(this);
            database = sqlite.getReadableDatabase();
            String MY_QUERY1 = "SELECT * FROM dd_customers WHERE order_id_new = ? AND tracking_id = ?";
            Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{String.valueOf(see1),String.valueOf(ses)});
            if (cursor != null){
                if(cursor.getCount() != 0){
                    cursor.moveToFirst();
                    do{
                        int index;
                        index = cursor.getColumnIndexOrThrow("id");
//                        idi = cursor.getString(index);
                        Log.d("namessssss", idi);
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
            Log.d("namessssss", idi);
            see2 = see1 + 1;
            for (int i = see2; i <= cle1;i++){
                sqlite = new SQLiteHelper(this);
                database = sqlite.getWritableDatabase();
                Log.d("forrloop",String.valueOf(i));
                ContentValues cv = new ContentValues();
                cv.put("order_id_new",i-1);
                String[] args =  new String[]{String.valueOf(i), String.valueOf(ses)};
                database.update("dd_customers", cv,  "order_id_new=? AND tracking_id = ?",args);
                Log.d("forrloop1",String.valueOf(i));
                Log.d("forrloop2",String.valueOf(i+1));
                sqlite.close();
                database.close();
            }
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
            ContentValues cv1 = new ContentValues();
            cv1.put("order_id_new",cle1);
            database.update("dd_customers", cv1,  "id=? AND order_id_new=? AND tracking_id = ?", new String[]{idi,String.valueOf(see1), String.valueOf(ses)});
            sqlite.close();
            database.close();
            Toast.makeText(Draganddrop.this,see1 +" "+cle1+" "+"smaller" ,Toast.LENGTH_SHORT).show();
            Intent nji = new Intent(Draganddrop.this,Draganddrop.class);
            startActivity(nji);
        }

        else if (see1 > cle1){
            sqlite = new SQLiteHelper(this);
            database = sqlite.getReadableDatabase();
            String MY_QUERY1 = "SELECT * FROM dd_customers WHERE order_id_new = ? AND tracking_id = ?";
            Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{String.valueOf(see),String.valueOf(ses)});
            if (cursor != null){
                if(cursor.getCount() != 0){
                    cursor.moveToFirst();
                    do{
                        int index;
                        index = cursor.getColumnIndexOrThrow("id");
//                        idi = cursor.getString(index);
                        Log.d("namessssss", idi);
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
            see2 = see1 - 1;
            for (int i = see2; i >= cle1;i--){
                sqlite = new SQLiteHelper(this);
                database = sqlite.getWritableDatabase();
                Log.d("forrloop",String.valueOf(i));
                ContentValues cv = new ContentValues();
                cv.put("order_id_new",i+1);
                String[] args =  new String[]{String.valueOf(i), String.valueOf(ses)};
                database.update("dd_customers", cv,  "order_id_new =? AND tracking_id = ?",args);
                Log.d("forrloop3",String.valueOf(i));
                Log.d("forrloop4",String.valueOf(i+1));
                sqlite.close();
                database.close();
            }
            Log.d("forrloop5",String.valueOf(cle1)+" "+String.valueOf(see1)+" "+String.valueOf(idi));
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
            ContentValues cv1 = new ContentValues();
            cv1.put("order_id_new",cle1);
            database.update("dd_customers", cv1,  "id=? AND order_id_new =? AND tracking_id = ?", new String[]{idi,String.valueOf(see1), String.valueOf(ses)});
            Toast.makeText(Draganddrop.this,see1 +" "+cle1+" "+idi ,Toast.LENGTH_SHORT).show();
            database.close();
            sqlite.close();
            Intent nji = new Intent(Draganddrop.this,Draganddrop.class);
            startActivity(nji);

        }

    }

    //onBackPressed() - function called when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onBackPressed() {
        Intent un = new Intent(Draganddrop.this,AllCollection.class);
        startActivity(un);
        finish();
    }

    //populateRecyclerView() - All customers
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView() {
        Names.clear();
        Names2.clear();
        CURR.clear();
        NNP.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY = "SELECT * FROM dd_customers WHERE tracking_id = ? ORDER BY order_id_new ASC";
        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{String.valueOf(ses)});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;
                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    long id = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("CID");
                    long cid = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("order_id");
                    String order = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String order_id_new = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String typ = cursor.getString(index);
                    if(order_id_new == null || order_id_new.equalsIgnoreCase("") || order_id_new.equalsIgnoreCase("null")){
                        order_id_new = "0";
                    }
                    if(typ.equalsIgnoreCase("0")){
                        Names.add(id+","+Name+","+order_id_new+","+typ+","+cid+","+order_id_new);
                        Names2.add(id+","+Name+","+order_id_new+","+typ+","+cid+","+order_id_new);
                        lastcurorder = Integer.valueOf(order_id_new);
                        CURR.add(typ);
                    }else if(typ.equalsIgnoreCase("1")){
                        Names.add(id+","+Name+","+order_id_new+","+typ+","+cid+","+order_id_new);
                        Names2.add(id+","+Name+","+order_id_new+","+typ+","+cid+","+order_id_new);
                        lastcurorder = Integer.valueOf(order_id_new);
                        CURR.add(typ);
                    }else if(typ.equalsIgnoreCase("2")){
                        Names.add(id+","+Name+","+order_id_new+","+typ+","+cid+","+order_id_new);
                        Names2.add(id+","+Name+","+order_id_new+","+typ+","+cid+","+order_id_new);
                        lastnipniporder = Integer.valueOf(order_id_new);
                        NNP.add(typ);
                    }else{
                        Names.add(id+","+Name+","+order_id_new+","+typ+","+cid+","+order_id_new);
                        Names2.add(id+","+Name+","+order_id_new+","+typ+","+cid+","+order_id_new);
                    }

                    Log.d("order_id_new", String.valueOf(order_id_new));
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
//            progressbar_stop();
            recyclerView.setVisibility(View.GONE);
            noo.setVisibility(View.VISIBLE);
        }else {
//            progressbar_stop();
            recyclerView.setVisibility(View.VISIBLE);
            noo.setVisibility(View.GONE);
            mAdapter = new RecyclerViewAdapterdraganddrop(Names,Names2,CURR,NNP,ses,getApplicationContext(),in,Draganddrop.this,lastcurorder,lastnipniporder);
            ItemTouchHelper.Callback callback =
                    new ItemMoveCallback4(mAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(recyclerView);
//            mAdapter.setHasStableIds(true);
            recyclerView.setAdapter(mAdapter);
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
//    getMenuInflater().inflate(R.menu.back_button2, menu);
//
//    return true;
//}
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
//                            progressbar_load1();
//                            syncEvent ss = new syncEvent();
//                            ss.execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //progressbar_load1() - Load progressbar
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void progressbar_load1(){

        final Dialog dialog = new Dialog(Draganddrop.this,R.style.CustomDialog);
        //set layout custom
//        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        dialog.setContentView(R.layout.progressbar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.setCancelable(false);
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // this code will be executed after 2 seconds
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Stuff that updates the UI
//                                    fab.hide();
                                ((Callback)getApplication()).populateRecyclerView(ses);
                                ((Callback)getApplication()).populateRecyclerViewnip(ses);

                            }
                        });
                        dialog.dismiss();
                        Intent back = new Intent(Draganddrop.this,Draganddrop.class);
                        startActivity(back);
//                finish();
                    }
                }, 200);

            }
        }, 500);
    }

    private class syncEvent extends AsyncTask<String, Integer, String> {

        //Add 1. Declare RelativeLaout
        private RelativeLayout relativeView;
        private ProgressBar progressBar;
        private ProgressDialog progressDialog;
        @Override
        protected String doInBackground(String... arg0) {
            //your operation task here
            Log.d("checking_drag","yes");
            uppd();
//            ((Callback)getApplication()).populateRecyclerView_new(ses);
//            ((Callback)getApplication()).populateRecyclerViewnip_new(ses);
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            Log.d("checking_drag","stop");
            progressbar_stop();
        }

        @Override
        protected void onPreExecute() {
            Log.d("checking_drag","start");
//            progressDialog = ProgressDialog.show(Draganddrop.this, "","Loadingg....");
            progressbar_load();
            //Add 3. add 2 lines of code here
//            relativeView = (RelativeLayout)findViewById(R.id.loadingevent);


        }

        // For this method, i don't need to use it yet,so, i left it here
        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d("checking_drag",String.valueOf(values));
            super.onProgressUpdate(values);

        }
    }
    //progressbar_load() - Load progressbar
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void progressbar_load(){
        //set layout custom
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

    //progressbar_stop() - Stop progressbar
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void progressbar_stop(){
        dialog.dismiss();
        Intent nn = new Intent(Draganddrop.this,Draganddrop.class);
        startActivity(nn);
        finish();
    }
}
