package com.rampit.rask3.dailydiary;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.rampit.rask3.dailydiary.Adapter.MyRecyclerViewAdapter;
import com.rampit.rask3.dailydiary.Adapter.MyRecyclerViewAdapter4;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdapter;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


//Updated on 25/01/2022 by RAMPIT
//Create and update debit
//InternetConnection1() - Check whether internet is on or not
//progressbar_load() - Load progressbar
//progre() - Stop progressbar
//eddd() - get debit details
//list() - get debit details on SNO change
//set() - set datas to textviews
//add() - add or update debit data
//total_collection() - get total collection of debit
//update() -  Update debit
//popup() - Show customers in popup
//updateLabel() - Update textview when date is selected
//calculate() - Calculate debit values
//calculate() - Calculate debit values
//onOptionsItemSelected() - when navigation item pressed
//onBackPressed() - function called when back button is pressed

public class Newdebit extends AppCompatActivity {

    EditText dat,location,debit,doccharge,inter,day,phone,locate,sl;
    TextView namee,sl1,namee1,install,clsed,noo,dateee,session,deb_text;
    Button cancel,addd,bug_fix;
    EditText searchView,searchid;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    public static String TABLENAME4 = "dd_account_debit";
    PopupWindow popupWindow;
    LinearLayout linearLayout1,bug_fix_layout;
    String idd,idd1,didd,newd,dateString,dateString2,dateString3,dattta,ediid,editeddbdt,editedcldt,editeddbdt1,editedcldt1,timing,prevorder,cusdebit,adpr,edpr,depr,vipr;
    RecyclerViewAdapter mAdapter;
    MyRecyclerViewAdapter adapter;
    Integer dbamount,dccharge,interr,daa,add,divide,ses,slllno,orddd;
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<Long> ID = new ArrayList<>();
    Long oid;
    Date myDate,newdate;
    Calendar myCalendar;
    ImageView seesim;
    Integer in,actived,debittyppe,ty,prevoid;
    String cid1,namm,phooo,loccc,daaab,doco,inyt,opened,closed,installed,tottda,my;
    String cid11,namm1,phooo1,loccc1,daaab1,doco1,inyt1,opened1,closed1,installed1,tottda1,my1,MY_QUERY1,bug_fix_id="0";
    private PopupWindow mPopupWindow;
    Dialog dialog;
    DatePickerDialog datePickerDialog;
    Integer showinng = 0 ;
    @Override
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
        debittyppe = 0;
        prevoid = 0 ;
//        idd = "0";
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
        dialog = new Dialog(Newdebit.this,R.style.AlertDialogTheme);
        setContentView(R.layout.new_debit);
        setTitle(R.string.new_debit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((Callback)getApplication()).datee();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }

        sl = findViewById(R.id.slno);
        namee = findViewById(R.id.name);
        sl1 = findViewById(R.id.slno1);
        namee1 = findViewById(R.id.name1);
        dat = findViewById(R.id.date);
        debit = findViewById(R.id.debitam);
        day = findViewById(R.id.days);
        deb_text = findViewById(R.id.deb_text);
        seesim = findViewById(R.id.sesimg);
        install = findViewById(R.id.installment);
        doccharge = findViewById(R.id.document);
        phone = findViewById(R.id.phone);
        locate = findViewById(R.id.location);
        inter = findViewById(R.id.interest);
        addd = findViewById(R.id.add1);
        clsed = findViewById(R.id.close);
        cancel = findViewById(R.id.cancel1);
        bug_fix_layout = findViewById(R.id.bug_fix_layout);
        bug_fix = findViewById(R.id.bug_fix);
        bug_fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("bug_fix_id",bug_fix_id);
                sqlite = new SQLiteHelper(Newdebit.this);
                database = sqlite.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("debit_type","-1");
                String[] args =  new String[]{String.valueOf(bug_fix_id)};
                database.update("dd_customers", cv,  "id=? ",args);
                sqlite.close();
                database.close();
                list();
            }
        });
        LinearLayout linear = findViewById(R.id.linearLayout1);
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),Newdebit.this)) {
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
        myCalendar = Calendar.getInstance();
        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        final LinearLayout lllooo =findViewById(R.id.linearLayout1);
        lllooo.getViewTreeObserver().addOnGlobalLayoutListener(new  ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                lllooo.getWindowVisibleDisplayFrame(r);
                int screenHeight = lllooo.getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;
                if (keypadHeight > screenHeight * 0.15) {
                    addd.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.GONE);
//                    Toast.makeText(Newdebit.this,"Keyboard is showing",Toast.LENGTH_LONG).show();
                } else {
                    addd.setVisibility(View.GONE);
                    cancel.setVisibility(View.GONE);
//                    Toast.makeText(Newdebit.this,"keyboard closed",Toast.LENGTH_LONG).show();
                }
            }
        });
//        sqlite = new SQLiteHelper(this);
        slllno = 0;
        dbamount = 0;
        daa = 0;
        interr = 0;
        dccharge = 0;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        dateString3 = df.format(c.getTime());
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
        ses = pref.getInt("session", 1);
        String dd = pref.getString("Date","");
        dateee = findViewById(R.id.da);
        session = findViewById(R.id.sess);
        dateee.setText(dd);
        if(in == 0){
            namee1.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.search_orange, 0);
            if(ses == 1){
                timing = getString(R.string.morning);
                seesim.setBackgroundResource(R.drawable.sun);
                deb_text.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.info_orange, 0);
            }else if(ses == 2){
                timing = getString(R.string.evening);
                seesim.setBackgroundResource(R.drawable.moon);
                deb_text.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.info_orange, 0);
            }
        }else{
            namee1.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.search_blue, 0);
            deb_text.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.info_blue, 0);
            if(ses == 1){
                timing = getString(R.string.morning);
                seesim.setBackgroundResource(R.drawable.sun_blue);
            }else if(ses == 2){
                timing = getString(R.string.evening);
                seesim.setBackgroundResource(R.drawable.moon_blue);
            }
        }
        String sesid = pref.getString("id","");
        String module_i = "3";
        ((Callback)getApplication()).privilege(sesid,module_i);
        adpr = pref.getString("add_privilege","");
        edpr = pref.getString("edit_privilege","");
        depr = pref.getString("delete_privilege","");
        vipr = pref.getString("view_privilege","");

        session.setText(timing);

//
//
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dd = new Intent(Newdebit.this,Debit.class);
                startActivity(dd);
                finish();
            }
        });
        addd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(daa == 0 || daa <= 1 || dbamount == 0){
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(Newdebit.this,R.style.AlertDialogTheme);
                    String enn = getString(R.string.no_more);
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
                }else{
                    addd.setClickable(false);
                    Log.d("opoioi",String.valueOf(ediid));
                    add();

                }}
        });
//        debit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                 String bamount = debit.getText().toString();
//                 if(bamount.equalsIgnoreCase("")){}else{
//                   dbamount = Integer.parseInt(bamount);
////                    Toast.makeText(getApplicationContext(), String.valueOf(dbamount), Toast.LENGTH_SHORT).show();
////                    calclate();
//                }}
//            }
//        });
        debit.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0)
                { //do your work here }
                    String bamount = debit.getText().toString();
                    dbamount = Integer.parseInt(bamount);
                    if (bamount.trim().equals("")) {
//                        debit.setText("0");
//                    calclate();
                    }else{
//                        Toast.makeText(getApplicationContext(), String.valueOf(daa), Toast.LENGTH_SHORT).show();
                        calclate();
                    }
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {
                String bamount = debit.getText().toString();
//                daa = Integer.parseInt(bamount);
                if (bamount.trim().equals("")) {
//                    debit.setText(" ");
                    dbamount = 0;

//                    calclate();
                }
            }
        });
        doccharge.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0)
                { //do your work here }
                    String bamount = doccharge.getText().toString();
                    String bamount1 = debit.getText().toString();

                    if(bamount.equalsIgnoreCase("") || bamount1.equalsIgnoreCase("")){
                    }else {
                        Integer ii = Integer.parseInt(bamount);
                        Integer ii1 = Integer.parseInt(bamount1);
                        if(ii > ii1){
                            String text = doccharge.getText().toString();
                            doccharge.setText(text.substring(0, text.length() - 1));
                            String text1 = doccharge.getText().toString();
                            doccharge.setSelection(text1.length());
                            AlertDialog.Builder alertbox = new AlertDialog.Builder(Newdebit.this,R.style.AlertDialogTheme);
                            String enn = getString(R.string.lessdebit);
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
                        }else{
                            dccharge = Integer.parseInt(bamount);
                        }
                    }
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {
                String bamount = doccharge.getText().toString();
//                daa = Integer.parseInt(bamount);
                if (bamount.trim().equals("")) {
//                    debit.setText(" ");
                    dccharge = 0;

//                    calclate();
                }
            }
        });
        inter.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0)
                { //do your work here }
                    String bamount = inter.getText().toString();
                    String bamount1 = debit.getText().toString();

                    if(bamount.equalsIgnoreCase("") || bamount1.equalsIgnoreCase("")){
                    }else {
                        Integer ii = Integer.parseInt(bamount);
                        Integer ii1 = Integer.parseInt(bamount1);
                        if(ii > ii1){
                            String text = inter.getText().toString();
                            inter.setText(text.substring(0, text.length() - 1));
                            String text1 = inter.getText().toString();
                            inter.setSelection(text1.length());
                            AlertDialog.Builder alertbox = new AlertDialog.Builder(Newdebit.this,R.style.AlertDialogTheme);
                            String enn = getString(R.string.lessdebit);
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
                        }else{
                            interr = Integer.parseInt(bamount);
                        }
                    }
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {
                String bamount = inter.getText().toString();
//                daa = Integer.parseInt(bamount);
                if (bamount.trim().equals("")) {
//                    debit.setText(" ");
                    interr = 0;

//                    calclate();
                }
            }
        });
        inter.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
//                    Toast.makeText(getApplicationContext(), String.valueOf(interr), Toast.LENGTH_SHORT).show();
//                    calclate();
                }
            }
        });
        doccharge.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String bamount = doccharge.getText().toString();
                    if(bamount.equalsIgnoreCase("")){}
                    else{
//                        dccharge = Integer.parseInt(bamount);
                    }
//                    }Toast.makeText(getApplicationContext(), String.valueOf(dccharge), Toast.LENGTH_SHORT).show();
//                    calclate();
                }
            }
        });
        deb_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer ddd = dbamount + interr + dccharge;
//                    MessageBox("Hello World");
//                Dialog dialog = new Dialog(getApplicationContext());
                Log.d("rtrt",String.valueOf(ddd));
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
                tt.setText(logmsg);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    mPopupWindow.showAsDropDown(view, 0, 0, Gravity.END);
                } else {
                    mPopupWindow.showAsDropDown(view, view.getWidth() - mPopupWindow.getWidth(), 0);
                }
//                mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
//                mPopupWindow.setOutsideTouchable(true);
//                mPopupWindow.setFocusable(true);
//                mPopupWindow.showAtLocation(deb_text, Gravity.CENTER,0,0);
            }
        });
//        day.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    String bamount = day.getText().toString();
//                    daa = Integer.parseInt(bamount);
//                    Toast.makeText(getApplicationContext(), String.valueOf(daa), Toast.LENGTH_SHORT).show();
//                    calclate();
//                }
//            }
//        });
        day.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0)
                { //do your work here }
                    String bamount = day.getText().toString();
                    daa = Integer.parseInt(bamount);
                    if (bamount.trim().equals("")) {
//                        dat.setText("0");
//                    calclate();
                    }else{
                        Toast.makeText(getApplicationContext(), String.valueOf(daa), Toast.LENGTH_SHORT).show();
                        calclate();
                    }
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            public void afterTextChanged(Editable s) {
                String bamount = day.getText().toString();
//                daa = Integer.parseInt(bamount);
                if (bamount.trim().equals("")) {
//                    dat.setText("0");
                    daa = 0;
//                    calclate();
                }
            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel( view);
//                InputMethodManager inputMethodManager= (InputMethodManager) Newdebit.this.getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
//                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
            }

        };

        long da = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        dateString = sdf.format(da);
        String myFormat1 = "dd/MM/yyyy";//In which you need put here
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        try {
            Date debit = sdf.parse(dateString);
            dattta = sdf1.format(debit);
            myCalendar.setTime(debit);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(sesid.equalsIgnoreCase("1") ) {
//            dat.setText(dattta);
            dat.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    if (in == 0){
                        datePickerDialog =  new DatePickerDialog(Newdebit.this,R.style.DatePicker, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH ));
                    }else{
                        datePickerDialog =  new DatePickerDialog(Newdebit.this,R.style.DatePicker1, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH ));
                    }
                    InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//                    datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,
//                            "OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog,
//                                                    int which) {
//                                    if (which == DialogInterface.BUTTON_POSITIVE) {
//                                        DatePicker datePicker = datePickerDialog
//                                                .getDatePicker();
//                                        date.onDateSet(datePicker,
//                                                datePicker.getYear(),
//                                                datePicker.getMonth(),
//                                                datePicker.getDayOfMonth());
//                                        InputMethodManager inputMethodManager= (InputMethodManager) Newdebit.this.getSystemService(Context.INPUT_METHOD_SERVICE);
//                                        inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
//                                        addd.setVisibility(View.VISIBLE);
//                                        cancel.setVisibility(View.GONE);
//                                    }
//                                }
//                            });
                    datePickerDialog.show();
                    String myFormat = "yyyy/MM/dd"; //In which you need put here
                    String myFormat1 = "dd/MM/yyyy";//In which you need put here
                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
//                try {
//                    Date debit = sdf.parse(dateString);
//                    dattta = sdf1.format(debit);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    String date = sdf1.format(myCalendar.getTime());
                    Log.d("dadada", date);
                    dat.setText(date);
                }
            });
        }
        else{
//            add.setVisibility(View.GONE);
            dat.setText(dattta);
            dat.setClickable(false);
            dat.setFocusable(false);
            dat.setFocusableInTouchMode(false);
        }
        sl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup();
            }
        });
        namee1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup();
            }
        });
//        sl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popup();
//            }
//        });
        sl.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() >= 0)
                {
                    String bamount = sl.getText().toString();
                    if(bamount.equalsIgnoreCase("")){
//                        sl.setText("");
                        namee.setText("");
                        phone.setText("");
                        locate.setText("");
                        debit.setText("");
                        doccharge.setText("");
                        inter.setText("");
                        clsed.setText("");
                        dat.setText("");
                        install.setText("");
                        day.setText("");
//                        cusdebit = sl.getText().toString();
                    }else {
                        idd = String.valueOf(bamount);
                        Log.d("iid",idd);
//                        Log.d("iid","1");
//                        for(int i=0;i<=0;i++){
                        list();
//                            break;
//                        }
////                        calculate();
                    }
//                Toast.makeText(getApplicationContext(), String.valueOf(interr), Toast.LENGTH_SHORT).show();
                    return;
                }


            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
            public void afterTextChanged(Editable s) {
//                dateString = datt.getText().toString();
////                Log.d("datata",dateString);
//                String bamount = slno.getText().toString();
//                if(bamount.equalsIgnoreCase("")){
//                }else {
//                    idd = String.valueOf(bamount);
//                    Log.d("iid",idd);
//                    list();
//                }
////                Toast.makeText(getApplicationContext(), String.valueOf(interr), Toast.LENGTH_SHORT).show();
//                calculate();
            }
        });

        namee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup();
            }
        });
        Intent deb = getIntent();
        String ediid2 = deb.getStringExtra("idi");
        String rtt = deb.getStringExtra("type");
        if(ediid2 != null){

            ty = Integer.parseInt(rtt);
            if(ty == 1){
                ediid = ediid2 ;
                Log.d("debitedit",ediid);
                eddd();
            }else if(ty == 2){
                idd = ediid2 ;
                Log.d("debitedit1",idd);
                list();
            }
        }
        list();
    }
    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Newdebit account) {
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
    public void MessageBox(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //eddd() - get debit details
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void eddd(){
        addd.setText(R.string.update);
        Toast.makeText(getApplicationContext(), ediid, Toast.LENGTH_SHORT).show();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        Integer  fg = Integer.parseInt(ediid);
        if(fg > 5000){
            MY_QUERY1 = "SELECT a.*,b.customer_name,b.id as ID,b.tracking_id,b.order_id_new,b.phone_2,b.location,b.CID FROM " +
                    "dd_customers b LEFT JOIN dd_account_debit a on b.id =  a.customer_id WHERE b.order_id_new = ? AND b.tracking_id = ?  AND a.active_status = '1' ";
        }else{
            MY_QUERY1 = "SELECT a.*,b.customer_name,b.id as ID,b.tracking_id,b.order_id_new,b.phone_2,b.location,b.CID FROM dd_customers b LEFT JOIN dd_account_debit a on b.id =  a.customer_id WHERE b.CID = ? AND b.tracking_id = ? AND a.active_status = '1' ";
            Log.d("uiio1",String.valueOf(ediid)+" "+String.valueOf(ses));
        } String[] whereArgs1 = new String[] {ediid,String.valueOf(ses)};
        Cursor cursor1 = database.rawQuery(MY_QUERY1,whereArgs1);
        if (cursor1 != null) {
            if (cursor1.getCount() != 0) {
                cursor1.moveToFirst();
                do {
                    Log.d("uiio",ediid);
                    int index;
                    index = cursor1.getColumnIndexOrThrow("id");
                    ediid = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("ID");
                    String cusid = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("CID");
                    String cusid21 = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("customer_name");
                    String cusname = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("customer_id");
                    cusdebit = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("debit_amount");
                    daaab = cursor1.getString(index);
                    daaab1 = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("document_charge");
                    String doc_chr = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("interest");
                    String in_rest = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("debit_days");
                    String deb_dys = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("closeing_date");
                    String cls_dt = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("debit_date");
                    String db_dt = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("installment_amount");
                    String ins_am = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("phone_2");
                    String phhho = cursor1.getString(index);
                    index = cursor1.getColumnIndexOrThrow("location");
                    String loccc = cursor1.getString(index);
                    Date bb = null;
                    Date cc = null;
                    SimpleDateFormat ss = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat sss = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar calendar = Calendar.getInstance();
                    try {
                        bb = ss.parse(db_dt);
                        cc = ss.parse(cls_dt);
                        Log.d("debitedit22", String.valueOf(bb));
                        myCalendar.setTime(bb);
                        editedcldt = sss.format(cc);
                        editeddbdt = sss.format(bb);
                        editedcldt1 = ss.format(cc);
                        editeddbdt1 = ss.format(bb);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(daaab == null){
//                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//                            String myFormat1 = "dd/MM/yyyy";//In which you need put here
//                            SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
//                            try {
//                                Date debit = sdf.parse(dateString3);
//                               myCalendar.setTime(debit);
//                             } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
                    }else{
//
                        sl.setText(cusid21);
                        namee.setText(cusname);
                        Log.d("debitedit",editeddbdt);
                        debit.setText(daaab);
                        doccharge.setText(doc_chr);
                        inter.setText(in_rest);
                        day.setText(deb_dys);
                        clsed.setText(editedcldt);
                        dat.setText(editeddbdt);
                        install.setText(ins_am);
                        dccharge = Integer.parseInt(doc_chr);
                        dbamount = Integer.parseInt(daaab);
                        interr = Integer.parseInt(in_rest);
                        daa = Integer.parseInt(deb_dys);
                        phone.setText(phhho);
                        locate.setText(loccc);
                    }
                }
                while (cursor1.moveToNext());
                cursor1.close();
                sqlite.close();
                database.close();
            }else{
                cursor1.close();
                sqlite.close();
                database.close();
            }
        }else{
            cursor1.close();
            sqlite.close();
            database.close();
        }
    }

    //list() - get debit details on SNO change
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void list() {
//        Log.d("ioiwere",idd);
        if(idd == null || idd.equalsIgnoreCase("0")){
            Intent name = getIntent();
            idd = name.getStringExtra("ID");
            idd1 = name.getStringExtra("ID");
        }else{ }

        if (idd != null) {
            Log.d("ioi",idd);
            Toast.makeText(getApplicationContext(), idd, Toast.LENGTH_SHORT).show();
            sqlite = new SQLiteHelper(this);
            database = sqlite.getWritableDatabase();
            String[] columns = {"customer_name",
                    "id","phone_1","location","CID"};
            String order = "order_id";
            String whereClause = "id=? AND tracking_id = ?";
            String[] whereArgs = new String[]{idd, String.valueOf(ses)};
            Integer ifd = Integer.parseInt(idd);
            if(ifd > 5000){
                my = "SELECT a.*,b.customer_name,b.id as ID,b.CID,b.debit_type,b.tracking_id,b.order_id_new,b.phone_1,b.location,b.previous_order_id as prevoid FROM" +
                        " dd_customers b LEFT JOIN dd_account_debit a on b.id =  a.customer_id WHERE b.order_id_new = ? AND b.tracking_id = ? ORDER BY a.id DESC LIMIT 1 ";
                Log.d("ioi1",idd);
            }else{
                my = "SELECT a.*,b.customer_name,b.id as ID,b.CID,b.debit_type,b.tracking_id,b.order_id_new,b.phone_1,b.location,b.previous_order_id as prevoid FROM" +
                        " dd_customers b LEFT JOIN dd_account_debit a on a.customer_id =b.id WHERE b.CID = ? AND b.tracking_id = ? ORDER BY a.id DESC LIMIT 1 ";
                Log.d("ioi2",idd);
            }
//            String my = "SELECT a.*,b.customer_name,b.id as ID,b.CID,b.tracking_id,b.order_id,b.phone_1,b.location FROM dd_customers b LEFT JOIN dd_account_debit a on b.id =  a.customer_id WHERE b.id = ? AND b.tracking_id = ?";
//            Cursor cursor = database.query("dd_customers",
//                    columns,
//                    whereClause,
//                    whereArgs,
//                    null,
//                    null,
//                    null);
            Cursor cursor = database.rawQuery(my,new String[]{idd,String.valueOf(ses)});
            if (cursor != null) {
                if (cursor.getCount() != 0) {
                    cursor.moveToFirst();
                    do {
                        int index;
                        index = cursor.getColumnIndexOrThrow("debit_type");
                        debittyppe = cursor.getInt(index);
                        index = cursor.getColumnIndexOrThrow("prevoid");
                        prevoid = cursor.getInt(index);
                        Log.d("ioi2", String.valueOf(debittyppe));
                        Log.d("ioi2prev", String.valueOf(prevoid));
                        if(debittyppe.equals(3)){
                            bug_fix_layout.setVisibility(View.GONE);
                            bug_fix_id = "0";
                            index = cursor.getColumnIndexOrThrow("CID");
                            cid1 = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("id");
                            ediid = cursor.getString(index);
//                            Log.d("ioi21closed",ediid);
//    idd = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("ID");
                            idd1 = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("customer_name");
                            namm = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("phone_1");
                            phooo = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("location");
                            loccc = cursor.getString(index);
                            daaab1 = "0";
                            if(loccc == null){
                                loccc = " ";
                            }
                            if(phooo == null){
                                phooo = " ";
                            }
                            String n = sl.getText().toString();
                            if(n == null || n.equalsIgnoreCase("")){
                                sl.setText(idd);
                                Log.d("ioi3",cid1);
                            }
                            namee.setText(namm);
                            phone.setText(phooo);
                            locate.setText(loccc);
                            debit.setText("");
                            doccharge.setText("");
                            inter.setText("");
                            clsed.setText("");
                            install.setText("");
                            dat.setText(dateString3);
                            day.setText("");
                            Calendar c1 = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                            String dateString11 = sdf.format(c1.getTime());

                            dateString = dateString11;
                            try {
                                Date debit111 = sdf.parse(dateString11);
                                myCalendar.setTime(debit111);
                                Log.d("ioi3",String.valueOf(dateString11));
                                Log.d("ioi3",String.valueOf(debit111));
//        opened = dateString2;


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        else  if(debittyppe.equals(-1)){
                            bug_fix_layout.setVisibility(View.GONE);
                            bug_fix_id = "0";
                            index = cursor.getColumnIndexOrThrow("CID");
                            cid1 = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("id");
                            ediid = cursor.getString(index);
//                            Log.d("ioi21closed",ediid);
//    idd = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("ID");
                            idd1 = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("customer_name");
                            namm = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("phone_1");
                            phooo = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("location");
                            loccc = cursor.getString(index);
                            daaab1 = "0";
                            if(loccc == null){
                                loccc = " ";
                            }
                            if(phooo == null){
                                phooo = " ";
                            }
                            String n = sl.getText().toString();
                            if(n == null || n.equalsIgnoreCase("")){
                                sl.setText(idd);
                                Log.d("ioi3",cid1);
                            }
                            namee.setText(namm);
                            phone.setText(phooo);
                            locate.setText(loccc);
                            debit.setText("");
                            doccharge.setText("");
                            inter.setText("");
                            clsed.setText("");
                            install.setText("");
                            dat.setText(dateString3);
                            day.setText("");
                            Calendar c1 = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                            String dateString11 = sdf.format(c1.getTime());

                            dateString = dateString11;
                            try {
                                Date debit111 = sdf.parse(dateString11);
                                myCalendar.setTime(debit111);
                                Log.d("ioi3",String.valueOf(dateString11));
                                Log.d("ioi3",String.valueOf(debit111));
//        opened = dateString2;


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            index = cursor.getColumnIndexOrThrow("CID");
                            cid1 = cursor.getString(index);
                            Log.d("ioi2",cid1);
                            index = cursor.getColumnIndexOrThrow("id");
                            ediid = cursor.getString(index);
//    idd = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("ID");
                            idd1 = cursor.getString(index);
                            if(ediid == null){
                                ediid ="0";
                            }
                            Log.d("ioi21",ediid);
                            index = cursor.getColumnIndexOrThrow("customer_name");
                            namm = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("phone_1");
                            phooo = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("location");
                            loccc = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("debit_amount");
                            daaab = cursor.getString(index);
                            daaab1 = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("document_charge");
                            doco = cursor.getString(index);
                            doco1 = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("interest");
                            inyt = cursor.getString(index);
                            inyt1 = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("debit_date");
                            opened = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("closeing_date");
                            closed = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("installment_amount");
                            installed = cursor.getString(index);
                            installed1 = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("debit_days");
                            tottda = cursor.getString(index);
                            tottda1 = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("active_status");
                            actived = cursor.getInt(index);
                            Log.d("actived", String.valueOf(actived));
                            if(actived.equals(0)){
                                bug_fix_layout.setVisibility(View.VISIBLE);
                                bug_fix_id = idd1 ;
                            }else{
                                bug_fix_layout.setVisibility(View.GONE);
                            }
                            index = cursor.getColumnIndexOrThrow("order_id_new");
                            String actived1 = cursor.getString(index);

                            Log.d("ioi22", String.valueOf(actived1));
                            if (phooo == null) {
                                phooo = "";
                            }
                            if (loccc == null) {
                                loccc = "";
                            }
                            if (daaab == null) {
                                daaab = "";
                                daaab1 = "0";
                            }
                            if (doco == null) {
                                doco = "0";
                                doco1 = "";
                            }
                            if (inyt == null) {
                                inyt = "0";
                                inyt1 = "";
                            }
                            if (installed == null) {
                                installed = "0";
                                installed1 = "";
                            }
                            if (tottda == null) {
                                tottda = "0";
                                tottda1 = "";
                            }
                            interr = Integer.parseInt(inyt);
                            dccharge = Integer.parseInt(doco);
                            if (opened == null || opened.equalsIgnoreCase("")) {
                                Calendar c = Calendar.getInstance();
//                            opened = dateString;
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                                SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                                String dateString1 = sdf.format(c.getTime());
                                String dateString2 = sdf1.format(c.getTime());
                                dateString = dateString1 ;
                                try {
                                    Date debit1 = sdf.parse(dateString1);
                                    myCalendar.setTime(debit1);
                                    opened = dateString2;
                                    Log.d("popo", String.valueOf(debit1));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                dateString = opened;
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                                String myFormat1 = "dd/MM/yyyy";//In which you need put here
                                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                                try {
                                    Date debit = sdf.parse(opened);
                                    myCalendar.setTime(debit);
                                    opened = sdf1.format(debit);
                                    Log.d("popo1", String.valueOf(debit));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Log.d("popo1","0987");
                            }
                            if (closed == null) {
                                closed = "";
                            } else {
                                newd = closed;
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                                String myFormat1 = "dd/MM/yyyy";//In which you need put here
                                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                                try {
                                    Date debit = sdf.parse(closed);
                                    closed = sdf1.format(debit);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            Log.d("4e5rre", namm);
                            set();
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
                    String n = sl.getText().toString();
                    if(n == null || n.equalsIgnoreCase("")){
                        sl.setText(idd);
                        Log.d("ioi4",idd);
                    }
                    namee.setText("");
                    phone.setText("");
                    locate.setText("");
                    debit.setText("");
                    doccharge.setText("");
                    inter.setText("");
                    clsed.setText("");
                    install.setText("");
                    dat.setText(dateString3);
                    day.setText("");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    try {
                        Date debit = sdf.parse(dateString3);
                        myCalendar.setTime(debit);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                cursor.close();
                sqlite.close();
                database.close();
            }
        }else{
            Log.d("opoioielse",String.valueOf(ediid));
        }
    }

    //set() - set datas to textviews
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void set(){
//                        sl.setText(cid1);
        Log.d("uiui",opened);
        String deed = sl.getText().toString();
        if(deed == null || deed.equalsIgnoreCase("")){
            sl.setText(idd);
        }
        namee.setText(namm);
        phone.setText(phooo);
        locate.setText(loccc);
        debit.setText(daaab);
        doccharge.setText(doco1);
        inter.setText(inyt1);
        clsed.setText(closed);
        dat.setText(opened);
        install.setText(installed1);
        day.setText(tottda1);
        cusdebit = idd1;
    }

    //add() - add or update debit datas
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void add(){
        Log.d("ioi2", String.valueOf(debittyppe));
//        Log.d("edddd",ediid);
        Log.d("opoioi",String.valueOf(daaab));
        Log.d("opoioi",String.valueOf(ediid));
        Log.d("opoioi",dateString);
        if (daaab1 == null || daaab1.equalsIgnoreCase("0")) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            String formattedDate = df.format(c.getTime());
            String dbam = String.valueOf(dbamount);
            String dc = String.valueOf(dccharge);
            String inte = String.valueOf(interr);
            String dbda = String.valueOf(daa);
            String inst = String.valueOf(divide);
            Integer inte1 = Integer.parseInt(inst);
            if(inte1<=0){
                addd.setClickable(true);
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Newdebit.this,R.style.AlertDialogTheme);
                String logmsg = getString(R.string.interest_alert);
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
                            }
                        });
                alertbox.show();
            }else{
                sqlite = new SQLiteHelper(this);
                database = sqlite.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("debit_date", dateString);
                values.put("debit_amount", dbam);
                values.put("document_charge", dc);
                values.put("interest", inte);
                values.put("debit_days", dbda);
                values.put("closeing_date", newd);
                values.put("installment_amount", inst);
                values.put("customer_id", idd1);
                values.put("active_status","1");
                values.put("created_date", formattedDate);
                database.insert(TABLENAME4, null, values);
                sqlite = new SQLiteHelper(this);
                database = sqlite.getWritableDatabase();
                ContentValues value1 = new ContentValues();
//                value1.put("order_id",prevoid);
                value1.put("debit_type","0");
                Log.d("dddd", String.valueOf(prevoid)+" "+idd1+" "+idd + " "+String.valueOf(ses));
                database.update("dd_customers",value1,"id = ?  AND tracking_id = ?",new String[]{idd1, String.valueOf(ses)});
                sqlite.close();
                database.close();
                Intent intent = new Intent(Newdebit.this, Debit.class);
                startActivity(intent);
                finish();
               /* if(debittyppe.equals(3) && prevoid > 0){
                    Log.d("ioi20", String.valueOf(debittyppe));
                    if(prevoid > 5000){
                        Log.d("ioi20", String.valueOf(debittyppe));
                        order();
                        sqlite = new SQLiteHelper(this);
                        database = sqlite.getWritableDatabase();
                        ContentValues value1 = new ContentValues();
                        value1.put("order_id",orddd);
                        value1.put("debit_type","0");
                        Log.d("dddd", String.valueOf(orddd)+" "+idd1+" "+idd + " "+String.valueOf(ses));
                        database.update("dd_customers",value1,"CID = ? AND tracking_id = ?",new String[]{idd, String.valueOf(ses)});
                        sqlite.close();
                        database.close();
                        Intent intent = new Intent(Newdebit.this, Debit.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Log.d("ioi20", String.valueOf(prevoid)+" "+String.valueOf(orddd));
                        order();
                        for (int i = orddd; i >= prevoid;i--){
                            database = sqlite.getWritableDatabase();
                            Log.d("forrloop",String.valueOf(i));
                            ContentValues cv = new ContentValues();
                            cv.put("order_id",i+1);
                            String[] args =  new String[]{String.valueOf(i), String.valueOf(ses)};
                            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
                            Log.d("forrloop1",String.valueOf(i));
                            Log.d("forrloop2",String.valueOf(i+1));
                        }
                        sqlite = new SQLiteHelper(this);
                        database = sqlite.getWritableDatabase();
                        ContentValues value1 = new ContentValues();
                        value1.put("order_id",prevoid);
                        value1.put("debit_type","0");
                        Log.d("dddd", String.valueOf(prevoid)+" "+idd1+" "+idd + " "+String.valueOf(ses));
                        database.update("dd_customers",value1,"id = ?  AND tracking_id = ?",new String[]{idd1, String.valueOf(ses)});
                        sqlite.close();
                        database.close();
                        Intent intent = new Intent(Newdebit.this, Debit.class);
                        startActivity(intent);
                        finish();
                    }

                }
                else{

                    order();
                    Log.d("ioi20", String.valueOf(debittyppe));
                    sqlite = new SQLiteHelper(this);
                    database = sqlite.getWritableDatabase();
                    ContentValues value1 = new ContentValues();
                    value1.put("order_id",orddd);
                    value1.put("debit_type","0");
                    Log.d("dddd", String.valueOf(orddd)+" "+idd1+" "+idd + " "+String.valueOf(ses));
                    database.update("dd_customers",value1,"CID = ? AND tracking_id = ?",new String[]{idd, String.valueOf(ses)});
                    sqlite.close();
                    database.close();
                    Intent intent = new Intent(Newdebit.this, Debit.class);
                    startActivity(intent);
                    finish();
                }*/
            }

        }
        else{
            Log.d("debitedit22",ediid+" "+cusdebit+" "+dateString+" "+newd);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            String formattedDate = df.format(c.getTime());
            String dbam = String.valueOf(dbamount);
            String dc = String.valueOf(dccharge);
            String inte = String.valueOf(interr);
            String dbda = String.valueOf(daa);
            calclate();
            String inst = String.valueOf(divide);
            Integer inte1 = Integer.parseInt(inst);
            if(inte1<=0){
                addd.setClickable(true);
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Newdebit.this,R.style.AlertDialogTheme);
                String logmsg = getString(R.string.interest_alert);
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
                            }
                        });
                alertbox.show();
            }else{
                total_collection(ediid,dbam,dc,inte,dbda,inst,formattedDate);

            }
        }
    }
/*
    public void order(){
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        String MY_QUERY1 = "SELECT order_id FROM dd_customers WHERE (debit_type= '0' OR debit_type = '1') AND tracking_id = ? ORDER BY order_id DESC LIMIT 0,1   ";
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{String.valueOf(ses)});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("order_id");
                    prevorder = cursor.getString(index);
                    Log.d("orsderdf",prevorder);
                    if (prevorder.trim().equals(null)) {
                        prevorder = "0";
                        orddd = Integer.parseInt(prevorder);
                        orddd = orddd + 1;
                        Log.d("orderrre3", String.valueOf(orddd));
                    } else if(prevorder.equalsIgnoreCase("-1")){
                        orddd = Integer.parseInt(prevorder);
                        orddd = 1;
                        Log.d("orderrre4", String.valueOf(orddd));

                    }else{
                        orddd = Integer.parseInt(prevorder);
                        orddd = orddd + 1;
                        Log.d("orderrre2", String.valueOf(orddd));

                    }

                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }
            else {
                cursor.close();
                sqlite.close();
                database.close();
                orddd = 1;
                Log.d("orderrre1", String.valueOf(orddd));
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }

    }
*/

//total_collection() - get total collection of debit
//Params - debit id , debit amount , document ,interest , debit date , installment , created date
//Created on 25/01/2022
//Return - NULL
public void total_collection(String dbid,String dbam,String dc,String inte,String dbda,String inst,String formattedDate){
    sqlite = new SQLiteHelper(this);
    database = sqlite.getWritableDatabase();
    String SUM_QUERY = "SELECT SUM(collection_amount) as collected FROM dd_collection  WHERE debit_id = ? ";
    Cursor cur = database.rawQuery(SUM_QUERY,new String[]{dbid});

    if (cur != null) {
        if (cur.getCount() != 0) {
            cur.moveToFirst();
            Log.d("paiddd77", "opo");
            do {
                Log.d("paiddd77", "opo");
                int index;

                index = cur.getColumnIndexOrThrow("collected");
                Integer orde1 = cur.getInt(index);
                Log.d("paiddd77", String.valueOf(orde1));
                update(ediid,dbam,dc,inte,dbda,inst,formattedDate, String.valueOf(orde1));
            }
            while (cur.moveToNext());
            cur.close();
            sqlite.close();
            database.close();

        }else{
            cur.close();
            sqlite.close();
            database.close();
            Log.d("paiddd77", "opo1");
        }
    }else{
        cur.close();
        sqlite.close();
        database.close();
        Log.d("paiddd77", "opo2");
        update(ediid,dbam,dc,inte,dbda,inst,formattedDate,"0");
    }
//    Log.d("debitedit22",dbam+" "+dc+" "+inte+" "+dbda+" "+inst);
//    sqlite = new SQLiteHelper(this);
//    database = sqlite.getWritableDatabase();
//    ContentValues values = new ContentValues();
//    values.put("debit_date", dateString);
//    values.put("debit_amount", dbam);
//    values.put("document_charge", dc);
//    values.put("interest", inte);
//    values.put("debit_days", dbda);
//    values.put("closeing_date", newd);
//    values.put("installment_amount", inst);
//    values.put("customer_id", cusdebit);
//    values.put("updated_date", formattedDate);
//    database.update(TABLENAME4, values, "id= ?", new String[]{ediid});
//    sqlite.close();
//    database.close();
//    Intent intent = new Intent(Newdebit.this, Debit.class);
//    startActivity(intent);
//    finish();
}

    //update() -  Update debit
//Params - debit id , debit amount , document ,interest , debit date , installment , created date
//Created on 25/01/2022
//Return - NULL
public void update(String dbid,String dbam,String dc,String inte,String dbda,String inst,String formattedDate,String coll){
    Log.d("debitedit22",dbam+" "+dc+" "+inte+" "+dbda+" "+inst);
    Integer ddeeb = Integer.parseInt(dbam);
    Integer coll1 = Integer.parseInt(coll);
    String warrr = getString(R.string.debit_greater)+" "+coll;
    if(coll1>=ddeeb){
        addd.setClickable(true);
        AlertDialog.Builder alertbox = new AlertDialog.Builder(Newdebit.this,R.style.AlertDialogTheme);
        String warr = getString(R.string.warning);
        String logo = getString(R.string.ok);
        alertbox.setMessage(warrr);
        alertbox.setTitle(warr);
        alertbox.setIcon(R.drawable.dailylogo);
        alertbox.setPositiveButton(logo,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0,
                                        int arg1) {
                    }
                });
        alertbox.show();
    }
    else{
           Log.d("debitedit22",dbam+" "+dc+" "+inte+" "+dbda+" "+inst);
    sqlite = new SQLiteHelper(this);
    database = sqlite.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("debit_date", dateString);
    values.put("debit_amount", dbam);
    values.put("document_charge", dc);
    values.put("interest", inte);
    values.put("debit_days", dbda);
    values.put("closeing_date", newd);
    values.put("installment_amount", inst);
    values.put("customer_id", cusdebit);
    values.put("updated_date", formattedDate);
    database.update(TABLENAME4, values, "id= ?", new String[]{ediid});
    sqlite.close();
    database.close();
    Intent intent = new Intent(Newdebit.this, Debit.class);
    startActivity(intent);
    finish();
    }
}

    //popup() - Show customers in popup
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void popup(){
        Names.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();

        String MY_QUERY = "SELECT b.customer_name,b.id,b.tracking_id,b.order_id_new,b.CID,b.debit_type,c.debit_amount FROM dd_customers b " +
                "LEFT JOIN dd_account_debit c on b.id =  c.customer_id WHERE b.tracking_id = ? GROUP BY b.id  ORDER BY b.CID ASC ";
        String[] whereArgs = new String[] {String.valueOf(ses)};
        Cursor cursor = database.rawQuery(MY_QUERY,whereArgs);
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    long id = cursor.getLong(index);
                    idd = String.valueOf(id);
                    idd1 = String.valueOf(id);


//                    index = cursor.getColumnIndexOrThrow("ID");
//                    long did = cursor.getLong(index);
//                    didd= String.valueOf(did);

                    index = cursor.getColumnIndexOrThrow("CID");
                    String ccc = cursor.getString(index);
                    long cci = cursor.getLong(index);
                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String orderrr = cursor.getString(index);
                    long oid = cursor.getLong(index);
                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String typ = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debbbbi = cursor.getString(index);
                    if(debbbbi == null ) {
                        slllno = slllno + 1;
                        if (typ.equalsIgnoreCase("-1")) {
                            Names.add(ccc + "," + Name+","+typ+","+cci);
                            ID.add(cci);
                        } if (typ.equalsIgnoreCase("0")) {
                            Names.add(ccc + "," + Name+","+typ+","+cci);
                            ID.add(cci);
                        }
                        if (typ.equalsIgnoreCase("1")) {
                            Names.add(ccc + "," + Name+","+typ+","+cci);
                            ID.add(cci);
                        }if (typ.equalsIgnoreCase("2")) {
                            Names.add(orderrr + "," + Name+","+typ+","+cci);
                            ID.add(oid);
                        }if (typ.equalsIgnoreCase("3")) {
                            Names.add(ccc + "," + Name+","+typ+","+cci);
                            ID.add(cci);
                        }
                        Log.d("names_names_n1", String.valueOf(id));
                    }else if(typ.equalsIgnoreCase("3")){
                        slllno = slllno + 1;
                        if (typ.equalsIgnoreCase("-1")) {
                            Names.add(ccc + "," + Name+","+typ+","+cci);
                            ID.add(cci);
                        } if (typ.equalsIgnoreCase("0")) {
                            Names.add(ccc + "," + Name+","+typ+","+cci);
                            ID.add(cci);
                        }if (typ.equalsIgnoreCase("1")) {
                            Names.add(ccc + "," + Name+","+typ+","+cci);
                            ID.add(cci);
                        }if (typ.equalsIgnoreCase("2")) {
                            Names.add(orderrr + "," + Name+","+typ+","+cci);
                            ID.add(oid);
                        }if (typ.equalsIgnoreCase("3")) {
                            Names.add(ccc + "," + Name+","+typ +","+cci);
                            ID.add(cci);
                        }
//                        ID.add(cci);
                        Log.d("names_names_n2", String.valueOf(id));
                    }else{
                        Log.d("names_names_n3", String.valueOf(id)+" "+typ);
                        if (typ.equalsIgnoreCase("-1")) {
                            slllno = slllno + 1;
                            Names.add(ccc + "," + Name+","+typ+","+cci);
                            ID.add(cci);
                        }
//                        if (typ.equalsIgnoreCase("0")) {
//                            slllno = slllno + 1;
//                            Names.add(ccc + "," + Name+","+typ+","+cci);
//                            ID.add(cci);
//                        }
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
        final Dialog dialog = new Dialog(Newdebit.this);
        //set layout custom
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popnames);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);
        final RecyclerView rvcaddy = (RecyclerView) dialog.findViewById(R.id.rvAnimals);
        final MyRecyclerViewAdapter4 adapter = new MyRecyclerViewAdapter4(getApplicationContext(),Names, ID, 0);
        searchView = (EditText) dialog.findViewById(R.id.search);
        searchid = (EditText) dialog.findViewById(R.id.searchid);
        noo =(TextView) dialog.findViewById(R.id.no);
//        searchView.setQueryHint("Google Search");
        Button close = (Button) dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        searchid.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0)
                {
                    adapter.filter(String.valueOf(s));
                }

            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
            public void afterTextChanged(Editable s) {
//                dateString = datt.getText().toString();
//                Log.d("datata",dateString);
                adapter.filter(String.valueOf(s));

            }
        });

        searchView.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0)
                {
                    adapter.filter(String.valueOf(s));
                }

            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
            public void afterTextChanged(Editable s) {
//                dateString = datt.getText().toString();
//                Log.d("datata",dateString);

                adapter.filter(String.valueOf(s));

            }
        });
        if(Names.size() == 0){
            rvcaddy.setVisibility(View.GONE);
            noo.setVisibility(View.VISIBLE);
        }else {
            rvcaddy.setVisibility(View.VISIBLE);
            noo.setVisibility(View.GONE);
            rvcaddy.setAdapter(adapter);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvcaddy.setLayoutManager(mLayoutManager);
        }
        dialog.show();
    }

    //updateLabel() - Update textview when date is selected
    //Params - Running view
    //Created on 25/01/2022
    //Return - NULL
    private void updateLabel(View view) {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        String myFormat1 = "dd/MM/yyyy";
        Log.d("dayeee","datttt");
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        dateString = sdf.format(myCalendar.getTime());
        dateString2 = sdf.format(myCalendar.getTime());
        dat.setText(sdf1.format(myCalendar.getTime()));
        calclate();
        debit.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        addd.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.GONE);
    }

    //calculate() - Calculate debit values
    //Params -NULL
    //Created on 25/01/2022
    //Return - NULL
    public void calclate(){
        if( dbamount != null  && daa != null && daa > 9){

            add = dccharge+dbamount+interr;
            divide = dbamount/daa;
            install.setText(String.valueOf(divide));
//    Toast.makeText(getApplicationContext(), String.valueOf(divide), Toast.LENGTH_SHORT).show();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Calendar calendar = Calendar.getInstance();
            if(editeddbdt1 != null){
                dateString = editeddbdt1;}
            if(dateString2 != null){
                dateString = dateString2;
            }
            Log.d(",myy", dateString);
            try {
                myDate = sdf.parse(dateString);
                Log.d(",myy", String.valueOf(myDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            daa = daa - 1;
            Integer dd11 = daa - 1;
            Log.d("newd",String.valueOf(dd11));
            calendar.setTime(myDate);
            calendar.add(Calendar.DAY_OF_YEAR, dd11);
            Date newDate = calendar.getTime();
            SimpleDateFormat sss = new SimpleDateFormat("dd/MM/yyyy");
            newd = sdf.format(newDate);
            String bvf = sss.format(newDate);
            Log.d("newd",bvf);
//    Toast.makeText(getApplicationContext(),newd,Toast.LENGTH_SHORT).show();
            clsed.setText(bvf);
        }
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
            Intent back = new Intent(Newdebit.this,Debit.class);
            startActivity(back);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //onBackPressed() - function called when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onBackPressed() {
        Intent na = new Intent(Newdebit.this,Debit.class);
        startActivity(na);
        finish();
    }
}
