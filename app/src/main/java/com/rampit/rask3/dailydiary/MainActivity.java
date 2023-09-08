package com.rampit.rask3.dailydiary;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


//Updated on 25/01/2022 by RAMPIT
//used to add and update company info
//setLocale() - set app language
//updateLabel() - Update textview when date is selected
//mainvalues() - get main info
//mainvalues1() - get main info
//expiiii1() - get license

public class MainActivity extends AppCompatActivity {

TextView expip;
EditText name,locate,dat,liccc;
Button submit,submit1;
SQLiteHelper sqlite;
SQLiteDatabase database;
Integer check;
CheckBox mor,eve;
Calendar myCalendar;
String dateString,License;
LinearLayout inlin,liclin;
    static String todate;
Calendar c;
String todaaaa,expiry;
SimpleDateFormat sdf1;
DatePicker dater;
    DatePickerDialog datePickerDialog;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public static String TABLENAME1 = "dd_main_info";
    TextView prin,dateee,session,user,expi,toto,save,expitext,totaltext,licensetext,email,emailcctext,passwordtext,usertext,languagetext,export,passeye;
    EditText em,lc,pased,emailed,licensed,emcc,ps,userr;
    Integer ses;
    String timing,typeid,adpr,edpr,depr,vipr,langua;
    ArrayList<String> Names = new ArrayList<>();
    Spinner spinner;
    Locale myLocale;
    String currentLanguage = "en", currentLang,iid,formattedDate;
    String ss,zero,one,two,three,four,five,six,seven,eight,nine,ten,lann,lann1,thhem;
    Integer showinng = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ii = pref.getString("theme","");
        if(ii.equalsIgnoreCase("")){
            ii = "1";
        }
        Log.d("thee",ii);
        final Integer in = Integer.valueOf(ii) ;
//        final Integer in = 0 ;
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

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        locate = findViewById(R.id.locat);
        dat = findViewById(R.id.date);
        submit = findViewById(R.id.submit);
        submit1 = findViewById(R.id.submit1);
        mor = findViewById(R.id.morning);
        expip = findViewById(R.id.expiry);
        liccc = findViewById(R.id.license);
        eve = findViewById(R.id.evening);
        inlin = findViewById(R.id.infoline);
        liclin = findViewById(R.id.licenseline);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        if(in == 0){
            mor.setButtonDrawable(R.drawable.custom_checkbox);
            eve.setButtonDrawable(R.drawable.custom_checkbox);
        }else{
            mor.setButtonDrawable(R.drawable.custom_checkbox1);
            eve.setButtonDrawable(R.drawable.custom_checkbox1);
        }

        myCalendar = Calendar.getInstance();
        mor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = 1;
                eve.setChecked(false);
                editor.putInt("session",check);
                editor.commit();
            }
        });
        eve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = 2;
                mor.setChecked(false);
                editor.putInt("session",check);
                editor.commit();
            }
        });
        Integer ch = pref.getInt("session",0);
        Log.d("chec",String.valueOf(ch));

//     sqlite = new SQLiteHelper(this);
        spinner = (Spinner) findViewById(R.id.spinner);
     mainvalues();
     mainvalues1();
     check = 1;
        c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String myFormat1 = "dd/MM/yyyy";//In which you need put here
        sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        todaaaa = sdf1.format(c.getTime());
        List<String> list = new ArrayList<String>();
        list.add("Select language");
        list.add("English");
        list.add("Français");
        list.add("Tamil");
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
        }else{
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
        lann1 = pref.getString("language","");
        if(lann == null || lann.equalsIgnoreCase("")){
            lann = "ta";
            Log.d("langu",lann);
//            spinner.setSelection(3);
        }else if(lann.equalsIgnoreCase("en")){
            lann = "en";
            lann1 = "en";
            Log.d("langu",lann);
            spinner.setSelection(1);
        }else if(lann.equalsIgnoreCase("fr")){
            lann1 = "fr";
            Log.d("langu",lann);
            spinner.setSelection(2);
        }else if(lann.equalsIgnoreCase("ta")){
            lann1 = "ta";
            Log.d("langu",lann);
            spinner.setSelection(3);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Integer nu = spinner
                        .getSelectedItemPosition();
                typeid = String.valueOf(nu);
                if(typeid.equalsIgnoreCase("1"))
                {   lann1 = "en";
                    langua = "en";
                }else if(typeid.equalsIgnoreCase("2")){
                    lann1 = "fr";
                    langua = "fr";
                }else if(typeid.equalsIgnoreCase("3")){
                    langua = "ta";
                    lann1 = "ta";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
//
//        try {
////            MainActivity.call_me();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        expii1();
//        expiiii();
        dat.setText(todaaaa);
//        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabel();
//            }
//
//        };
//        dat.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                DatePickerDialog datePickerDialog =  new DatePickerDialog(MainActivity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH ));
//                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//                datePickerDialog.show();
//                String myFormat = "yyyy/MM/dd"; //In which you need put here
//                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//                dateString = sdf.format(myCalendar.getTime());
//                dat.setText(sdf.format(myCalendar.getTime()));
//
//            }
//        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        dat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (in == 0){
                    datePickerDialog =  new DatePickerDialog(MainActivity.this,R.style.DialogTheme, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH ));
                }else{
                    datePickerDialog =  new DatePickerDialog(MainActivity.this,R.style.DialogTheme1, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH ));
                }

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//                datePickerDialog.setButton();
//                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
                datePickerDialog.show();
//                DatePicker dp = datePickerDialog.getDatePicker();
//
//                dp.setScaleY(Float.parseFloat("1"));
//                dp.setScaleX(Float.parseFloat("1.15"));
                String myFormat = "yyyy/MM/dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateString = sdf.format(myCalendar.getTime());
                dat.setText(sdf.format(myCalendar.getTime()));

            }
        });
        if(ch == 1){
            check = 1;
            mor.setChecked(true);
            eve.setChecked(false);
        }else  if(ch == 2){
            check = 2;
            eve.setChecked(true);
            mor.setChecked(false);
        }
    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String nam = name.getText().toString();
            String loc = locate.getText().toString();
            String da = dat.getText().toString();
            Log.d("checkii", String.valueOf(check));
            if(nam.equalsIgnoreCase("") || loc.equalsIgnoreCase("") || da.equalsIgnoreCase("") || check == 0 || lann1.equalsIgnoreCase("") || lann1 == null){
                final AlertDialog.Builder alertbox = new AlertDialog.Builder(MainActivity.this,R.style.AlertDialogTheme);
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
            }else {
                Log.d("datee",da);
                editor.putString("Date", da);
                editor.putString("NAME",nam);
                editor.putString("LOCATION",loc);
                editor.putInt("session",check);
                editor.commit();
                sqlite = new SQLiteHelper(MainActivity.this);
                database = sqlite.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("dd_name", nam);
                values.put("dd_location", loc);
                values.put("starting_date", da);
                database.insert(TABLENAME1, null, values);
                editor.putString("language",langua);
                editor.commit();
                String emm = email.getText().toString();
                sqlite.close();
                database.close();
                sqlite = new SQLiteHelper(MainActivity.this);
                database = sqlite.getWritableDatabase();

                Log.d("iid",langua);
                ContentValues values1 = new ContentValues();
                values1.put("email", emm);
                values1.put("language", langua);
                values1.put("updated_date", formattedDate);
                database.update("dd_settings", values1,"ID = ?", new String[]{"1"});
            sqlite.close();
            database.close();
                setLocale(langua);
//                ssee();
//                Intent login = new Intent(MainActivity.this, NavigationActivity.class);
//                startActivity(login);
            }
        }
    });
//        submit1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AsyncTask.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        //TODO your background code
//                        try {
//                            MainActivity.call_me();
//                            expii1();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
//            }
//        });

    }
    public void ssee (){
        Locale localeEn = new Locale("ta_IN");
        Locale.setDefault(localeEn);
        Configuration configEn = new Configuration();
        configEn.locale = localeEn;
        getApplicationContext().getResources().updateConfiguration(configEn, null);
//        this.recreate();
    }
    //setLocale() - set app language
    //Params - language string
    //Created on 25/01/2022
    //Return - NULL
    public void setLocale(String localeName) {
//        if (!localeName.equals(currentLanguage)) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            Log.d("o9p0",localeName);
            LocaleHelper.setLocale(MainActivity.this, "ta");
            Intent refresh = new Intent(this, NavigationActivity.class);
            refresh.putExtra(currentLang, localeName);
            startActivity(refresh);
            finish();

        } else {
            myLocale = new Locale(localeName);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            Intent refresh = new Intent(this, NavigationActivity.class);
            refresh.putExtra(currentLang, localeName);
            startActivity(refresh);
            finish();
        }
//        } else {
//            Toast.makeText(MainActivity.this, "Language already selected!", Toast.LENGTH_SHORT).show();
//        }
    }
    //updateLabel() - Update textview when date is selected
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";
        String myFormat1 = "dd/MM/yyyy";//In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        dateString = sdf.format(myCalendar.getTime());
        todaaaa = sdf.format(myCalendar.getTime());
        dat.setText(sdf1.format(myCalendar.getTime()));
    }
    //mainvalues() - get main info
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void mainvalues(){
        String[] columns1 = {
                "dd_name","dd_location","starting_date"};
        sqlite = new SQLiteHelper(MainActivity.this);
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
                    String USER = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("dd_location");
                    String PASS = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("starting_date");
                    String EMAIL = cursor.getString(index);

                    name.setText(USER);
                    locate.setText(PASS);
                    dat.setText(EMAIL);
                    editor.putString("NAME",USER);
                    editor.putString("LOCATION",PASS);
                    editor.apply();
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
    //mainvalues1() - get main info
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void mainvalues1(){
        String[] columns1 = {
                "email"};
        sqlite = new SQLiteHelper(MainActivity.this);
        database = sqlite.getWritableDatabase();
        Cursor cursor = database.query("dd_settings",
                columns1,
                "id = ?",
                new String[]{"1"},
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


                    index = cursor.getColumnIndexOrThrow("email");
                    String EMAIL = cursor.getString(index);

                    if(EMAIL==null ||EMAIL.equalsIgnoreCase("")){
                        EMAIL = "";
                    }
                    email.setText(EMAIL);

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
                    index = cursor.getColumnIndexOrThrow("expiry");
                    expiry = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("license");
                    License = cursor.getString(index);
                    Log.d("langg",expiry);

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    String formattedDate = df.format(c.getTime());

                    try {
                        Date newda = df.parse(formattedDate);
                        Date debit = df.parse(expiry);
                        long diffInMillisec =    debit.getTime() - newda.getTime();
                        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                        if(diffInDays <= 0){
                            Log.d("dates", String.valueOf(diffInDays));
                            AlertDialog.Builder alertbox = new AlertDialog.Builder(MainActivity.this,R.style.AlertDialogTheme);
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

                        }else {
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
    //expiiii1() - get license
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

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    String formattedDate = df.format(c.getTime());

                    try {
                        Date newda = df.parse(formattedDate);
                        Date debit = df.parse(expiry);
                        long diffInMillisec =    debit.getTime() - newda.getTime();
                        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                        if(diffInDays <= 0){
                            Log.d("dates", String.valueOf(diffInDays));
                            new Thread()
                            {
                                public void run()
                                {
                                    MainActivity.this.runOnUiThread(new Runnable()
                                    {
                                        public void run()
                                        {
                                            //Do your UI operations like dialog opening or Toast here
                                            AlertDialog.Builder alertbox1 = new AlertDialog.Builder(MainActivity.this,R.style.AlertDialogTheme);
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
                            Intent login = new Intent(MainActivity.this, NavigationActivity.class);
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
    public void expii1(){
        sqlite = new SQLiteHelper(MainActivity.this);
        database = sqlite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("expiry", todate);
        database.update("dd_settings",  values,"ID = ?",new String[]{"1"});
    expiiii1();
    }
    public static void call_me() throws Exception {
        String url = "http://knowledgebags.com/?slm_action=slm_check&secret_key=5d9c9a700b9aa6.64670171&license_key=5d9c9ac3c2b38";
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
        String expiii = myResponse.getString("date_expiry");
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date debit = df1.parse(expiii);
            todate = df.format(debit);
            Log.d("deae",todate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
