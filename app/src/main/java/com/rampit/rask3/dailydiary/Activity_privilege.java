package com.rampit.rask3.dailydiary;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.sym.Name;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdapterprivilege;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdapteruser;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;


//Updated on 25/01/2022 by RAMPIT
//used to update privileges of user
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed
//onNavigationItemSelected() - when navigation item pressed
//forlooo() - add or update all privileges in for loop
//intent_user() - Intent to user_activity
//populateRecyclerView() - get all privileges of user


public class Activity_privilege extends AppCompatActivity {

    SQLiteHelper sqlite;
    SQLiteDatabase database;
    RecyclerView recyclerView;
    TextView dateee,session,noo;
    Integer ses;
    String timing,ID,iidi,language;
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> iid = new ArrayList<>();
    ArrayList<String> iidii = new ArrayList<>();
    ArrayList<String> modd = new ArrayList<>();
    RecyclerViewAdapterprivilege mAdapter;
    ImageView seesim;
    Button add;
    Integer in;
    public static String TABLENAME = "dd_privilege";
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
        setContentView(R.layout.activity_privilege);
        setTitle(R.string.title_privilege_activity);
        ((Callback)getApplication()).datee();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.re);
//        sqlite = new SQLiteHelper(this);
        seesim = findViewById(R.id.sesimg);
        language = pref.getString("language","");
        ses = pref.getInt("session", 1);
        iidi = pref.getString("id", "");
        String dd = pref.getString("Date","");
        dateee = findViewById(R.id.da);
        noo = findViewById(R.id.no);
        add = findViewById(R.id.add_module);
        session = findViewById(R.id.sess);
        dateee.setText(dd);
        if(in == 0){
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
        session.setText(timing);
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
        Intent name = getIntent();
        ID = name.getStringExtra("ID");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            populateRecyclerView();
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//        iidii.clear();
                forlooo();
            }
        });
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
            Intent back = new Intent(Activity_privilege.this,user_activity.class);
            startActivity(back);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //forlooo() - add or update all privileges in for loop
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void forlooo(){
        iidii = mAdapter.getArrayList();
        Log.d("Arraylist",String.valueOf(iidii.size()));

        for(int i =0;i < iidii.size();i++){
            String vall = iidii.get(i);
            Integer hh = 2;
            for(int j = 0;j< iidii.size();j++) {
                String[] currencies = vall.split(",");
                String s = currencies[0];
                String sS = currencies[1];
                Log.d("Arraylist10101", ID);
                if(sS.equalsIgnoreCase("1")){
                    sqlite = new SQLiteHelper(this);
                    database = sqlite.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("add_privilege",sS);
                    database.update("dd_privilege", cv,  "user_id=? AND module_id=?", new String[]{ID,s});
                    Log.d("Arraylist", s+" "+sS);
                    sqlite.close();
                    database.close();
                }
                if(sS.equalsIgnoreCase("2")){
                    sqlite = new SQLiteHelper(this);
                    database = sqlite.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("edit_privilege",sS);
                    database.update("dd_privilege", cv,  "user_id=? AND module_id=?", new String[]{ID,s});
                    Log.d("Arraylist1", s+sS);
                    sqlite.close();
                    database.close();}
                if(sS.equalsIgnoreCase("3")){
                    sqlite = new SQLiteHelper(this);
                    database = sqlite.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("delete_privilege",sS);
                    database.update("dd_privilege", cv,  "user_id=? AND module_id=?", new String[]{ID,s});
                    Log.d("Arraylist2", s+sS);
                    sqlite.close();
                    database.close();}
                if(sS.equalsIgnoreCase("4")){
                    sqlite = new SQLiteHelper(this);
                    database = sqlite.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("view_privilege",sS);
                    database.update("dd_privilege", cv,  "user_id=? AND module_id=?", new String[]{ID,s});
                    Log.d("Arraylist2", s+sS);
                    sqlite.close();
                    database.close();}
                if(sS.equalsIgnoreCase("0")){
                    sqlite = new SQLiteHelper(this);
                    database = sqlite.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("add_privilege",sS);
                    database.update("dd_privilege", cv,  "user_id=? AND module_id=?", new String[]{ID,s});
                    Log.d("Arraylist", s+sS);
                    sqlite.close();
                    database.close();}
                if(sS.equalsIgnoreCase("00")){
                    sqlite = new SQLiteHelper(this);
                    database = sqlite.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("edit_privilege","0");
                    database.update("dd_privilege", cv,  "user_id=? AND module_id=?", new String[]{ID,s});
                    Log.d("Arraylist1", s+sS);
                    sqlite.close();
                    database.close();}
                if(sS.equalsIgnoreCase("000")){
                    sqlite = new SQLiteHelper(this);
                    database = sqlite.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("delete_privilege","0");
                    database.update("dd_privilege", cv,  "user_id=? AND module_id=?", new String[]{ID,s});
                    Log.d("Arraylist2", s+sS);
                    sqlite.close();
                    database.close();}
                if(sS.equalsIgnoreCase("0000")){
                    sqlite = new SQLiteHelper(this);
                    database = sqlite.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("view_privilege","0");
                    database.update("dd_privilege", cv,  "user_id=? AND module_id=?", new String[]{ID,s});
                    Log.d("Arraylist2", s+sS);
                    sqlite.close();
                    database.close();}
                break;
            }
            if(iidii.size() == i+1) {
                Log.d("sizeee",String.valueOf(iidii.size())+" "+String.valueOf(i+1));
                intent_user();
            }
        }

    }

    //intent_user() - Intent to user_activity
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void intent_user(){
//    ((Callback)getApplication()).viewcheck(iidi);
        Intent newd = new Intent(Activity_privilege.this,user_activity.class);
        startActivity(newd);
        finish();
    }
    //populateRecyclerView() - get all privileges of user
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void populateRecyclerView() {
        if(ID != null) {
            Names.clear();
            modd.clear();
            iid.clear();
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
//            String MY_QUERY = "SELECT a.*,b.NAME as modulename FROM dd_privilege a INNER JOIN dd_modules b on b.ID = a.module_id WHERE a.ID = ? ";
            String MY_QUERY = "SELECT a.*,b.NAME as modulename from dd_privilege a LEFT JOIN dd_modules b on b.ID = a.module_id WHERE a.user_id = ?";
            Cursor cursor = database.rawQuery(MY_QUERY, new String[]{ID});
//            Cursor cursor = database.rawQuery(MY_QUERY, null);

            if (cursor != null) {
                if (cursor.getCount() != 0) {
                    cursor.moveToFirst();
                    do {
                        int index;
                        index = cursor.getColumnIndexOrThrow("modulename");
                        String id = cursor.getString(index);
                        index = cursor.getColumnIndexOrThrow("module_id");
                        String idd = cursor.getString(index);
                        index = cursor.getColumnIndexOrThrow("add_privilege");
                        String adddd = cursor.getString(index);
                        index = cursor.getColumnIndexOrThrow("edit_privilege");
                        String edd = cursor.getString(index);
                        index = cursor.getColumnIndexOrThrow("delete_privilege");
                        String dd = cursor.getString(index);
                        index = cursor.getColumnIndexOrThrow("view_privilege");
                        String vi = cursor.getString(index);
                        if (id ==null||id.equalsIgnoreCase("")||id.equalsIgnoreCase("null")){

                        }else{
                            Log.d("idid", id);
//                            sqlite = new SQLiteHelper(this);
//                            database = sqlite.getWritableDatabase();

//                            sqlite.close();
//                            database.close();
//                            if(idd.equalsIgnoreCase("24")){
//
//                            }else{
                                Names.add(id+","+adddd+","+edd+","+dd+","+vi);
                                modd.add(adddd+" "+edd+" "+dd);
                                iid.add(idd);
//                            }
                            Log.d("names.size", String.valueOf(Names.size()));
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

            if(Names.size() == 0){
                recyclerView.setVisibility(View.GONE);
                noo.setVisibility(View.VISIBLE);
            }else {
                if(Names.size()<=20){
                    for(int i=21;i<= 24;i++){
                        Integer modid = i;
                        SQLiteHelper sqlite = new SQLiteHelper(this);
                        SQLiteDatabase database = sqlite.getWritableDatabase();
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat dff = new SimpleDateFormat("yyyy/MM/dd");
                        String formattedDate = dff.format(c.getTime());
                        ContentValues values = new ContentValues();
                        values.put("module_id",String.valueOf(modid));
                        values.put("user_id",ID);
                        values.put("created_date",formattedDate);
                        database.insert("dd_privilege",null,values);
                        Log.d("forloop",String.valueOf(modid));

                        sqlite.close();
                        database.close();
                        if(modid.equals(24)){
                            populateRecyclerView();
                        }
                        Log.d("forloop",String.valueOf(modid));
                    }
                }
                else if (Names.size()<=23){
                    SQLiteHelper sqlite = new SQLiteHelper(this);
                    SQLiteDatabase database = sqlite.getWritableDatabase();
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat dff = new SimpleDateFormat("yyyy/MM/dd");
                    String formattedDate = dff.format(c.getTime());
                    ContentValues values = new ContentValues();
                    values.put("module_id",String.valueOf(24));
                    values.put("user_id",ID);
                    values.put("created_date",formattedDate);
                    database.insert("dd_privilege",null,values);
                    Log.d("forloop",String.valueOf(24));

                    sqlite.close();
                    database.close();
                    populateRecyclerView();
                    Log.d("forloop",String.valueOf(24));
                }
                else{
//                    Integer ss = Names.size();
//                    Integer ss1 = iid.size();
//                    Names.remove(ss);
//                    iid.remove(ss1);
                    recyclerView.setVisibility(View.VISIBLE);
                    noo.setVisibility(View.GONE);
                    Log.d("langg",language);
                    mAdapter = new RecyclerViewAdapterprivilege(getApplicationContext(),in,Names,iid,language);
                    recyclerView.setAdapter(mAdapter);
                }


            }
        }
    }

}
