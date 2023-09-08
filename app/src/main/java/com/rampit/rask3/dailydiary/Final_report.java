package com.rampit.rask3.dailydiary;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

//Updated on 25/01/2022 by RAMPIT
//used to show final report
//InternetConnection1() - Check whether internet is on or not
//expand() - Expand an view
//collapse() - collapse an view
//progressbar_load() - Load progressbar
//progre() - Stop progressbar
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed
//updateLabel() - Update textview when date is selected
//updateLabel1() - Update textview when date is selected
//list1() - get closed collection
//closed() - get closed debit
//closeddiscount() - get closed discount
//beforebal() - get before balance amount
//populateRecyclerView() - get final balance amount
//current_balance() - get current balance amount
//nip_balance() - get NIP balance amount
//nipnip_balance() - get NIPNIP balance amount


public class Final_report extends AppCompatActivity {
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    RecyclerView recyclerView;
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> collection = new ArrayList<>();
    ArrayList<Long> ID = new ArrayList<>();
    ArrayList<Long> paid_cust = new ArrayList<>();
    ArrayList<Long> balance_cust = new ArrayList<>();
    public static String TABLE_NAME ="dd_collection";
    TextView tot,ppp,bbb,bef,tc,nic,ninic,totc,dbb,dcc,intt,oth,hg,lw,ex,tb;
    TextView yester,curreee,todayde,movenip,currentb,totrema;
    TextView ysrnip,todnipb,todnipi,tonipnip,nipbal;
    TextView ysrnipnip,todnipnipmove,tonipnipinc,nipnipb,curbal,nipbaltxt,nipnipdiss,curcolbal,totalcol,clocol,tdisc;
    EditText dat,todate;
    Button srch;
    Integer ses,befo,newc,curc,nipc,nipnipc,dab,totdab,movenipnpi,selectnipnip,movedniip,dabamo,lin1,lin2,lin3,cloosed,totrem,nippbal,curbal1,closeddisco,clodeb,clodoc,cloin;
    Calendar myCalendar,myCalendar1,c;
    String dateString,datedd,todateee,yesterdate,todaaaa,yesterdaybal,formattedDate,xcol;
    SimpleDateFormat sdf1;
    LinearLayout lo,lo2,lo3,cu,npp,nppnpp;
    ImageView iii,ii2,ii3;
    DatePickerDialog datePickerDialog,datePickerDialog1;
    Dialog dialog;
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
        final Integer in = Integer.valueOf(ii) ;
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
        dabamo = 0;
        closeddisco = 0;
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dialog = new Dialog(Final_report.this,R.style.AlertDialogTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_final_report);
        setTitle(R.string.final_report);
        ((Callback)getApplication()).datee();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        totalcol = findViewById(R.id.totalcoll);
        clocol = findViewById(R.id.clocol);
        lo = findViewById(R.id.lolo);
        lo2 = findViewById(R.id.lolo2);
        lo3 = findViewById(R.id.lolo3);
        srch = findViewById(R.id.searchh);
        bef = findViewById(R.id.beforee);
        totrema = findViewById(R.id.remain);
        tc = findViewById(R.id.today);
        totc = findViewById(R.id.total);
        nic = findViewById(R.id.nip);
        ninic = findViewById(R.id.nipnip);
        tdisc = findViewById(R.id.tdisc);
        dcc = findViewById(R.id.docuu);
        intt = findViewById(R.id.interest);
        hg = findViewById(R.id.dbam);
        curcolbal = findViewById(R.id.currentcol_bal);
        lw = findViewById(R.id.cdam);
        nipnipdiss = findViewById(R.id.nipnipdisc);
        ex = findViewById(R.id.expense);
        dat = findViewById(R.id.date);
        todate = findViewById(R.id.todate);
        todate.setEnabled(false);
        yester = findViewById(R.id.yeste);
        curreee = findViewById(R.id.curcol);
        curbal = findViewById(R.id.current_bal);
        todayde = findViewById(R.id.todb);
        movenip = findViewById(R.id.movenip);
        currentb = findViewById(R.id.totdis);
        ysrnip = findViewById(R.id.yesternip);
        todnipb = findViewById(R.id.todnip);
        todnipi = findViewById(R.id.tonipin);
        todnipnipmove = findViewById(R.id.movenipnip);
        nipbaltxt = findViewById(R.id.nipbaltext);
        nipbal =findViewById(R.id.nipbal);
        ysrnipnip = findViewById(R.id.yesternipnip);
        tonipnip = findViewById(R.id.todnipnip);
        tonipnipinc = findViewById(R.id.tonipnipin);
        nipnipb = findViewById(R.id.nipnipbal);
        iii = findViewById(R.id.img);
        ii2 = findViewById(R.id.img1);
        ii3 = findViewById(R.id.img2);
        myCalendar = Calendar.getInstance();
        myCalendar1 = Calendar.getInstance();
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),Final_report.this)) {
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
         lin1 = 0;
         lin2 = 1;
         lin3 = 2;
         cloosed = 0;
         cu = findViewById(R.id.cur);
        npp = findViewById(R.id.npp);
        nppnpp = findViewById(R.id.nppnpp);
        c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String myFormat1 = "dd/MM/yyyy";//In which you need put here
        sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        todaaaa = df.format(c.getTime());
//        try {
//            Date debit = df.parse(formattedDate);
//            datedd = sdf1.format(debit);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        dat.setText(datedd);
//        dateString = formattedDate;
        ses = pref.getInt("session", 1);
        String dd = pref.getString("Date","");
//        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date debit = df1.parse(dd);
            formattedDate = df.format(debit);
            Log.d("deae",formattedDate);
            String ffff = df1.format(debit);
            dat.setText(ffff);
            dateString = formattedDate;
            todate.setText(ffff);
            todateee = formattedDate;
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    progressbar_load();
                }
            });
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            list1();
                            closed();
                            closeddiscount();
                            beforebal();
                            populateRecyclerView();
                            current_balance();
                            nip_balance();
                            nipnip_balance();
                        }
                    });
                }
            }, 500);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        ((Callback)getApplication()).NIP(ses);
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
                    datePickerDialog =  new DatePickerDialog(Final_report.this,R.style.DialogTheme, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH ));
                }
                else{
                    datePickerDialog =  new DatePickerDialog(Final_report.this,R.style.DialogTheme1, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH ));
                }
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
                String myFormat = "yyyy/MM/dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateString = sdf.format(myCalendar.getTime());
                dat.setText(sdf1.format(myCalendar.getTime()));

            }
        });
        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (in == 0){
                    datePickerDialog1 =  new DatePickerDialog(Final_report.this,R.style.DialogTheme, date1, myCalendar1
                            .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                            myCalendar1.get(Calendar.DAY_OF_MONTH ));
                }else{
                    datePickerDialog1 =  new DatePickerDialog(Final_report.this,R.style.DialogTheme1, date1, myCalendar1
                            .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                            myCalendar1.get(Calendar.DAY_OF_MONTH ));
                }
                datePickerDialog1.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog1.show();
//                new DatePickerDialog(Final_report.this, date1, myCalendar1
//                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
//                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
                String myFormat = "yyyy/MM/dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                todateee = sdf.format(myCalendar1.getTime());
                String myFormat1 = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                todate.setText(sdf1.format(myCalendar1.getTime()));
            }
        });
        srch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    Date debit = df.parse(dateString);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(debit);
                    cal.add(Calendar.DATE,-1);
                    yesterdate = df.format(cal.getTime());
                    if(dateString != null && todateee != null){

                        Date newda = null;
                        try {
                            newda = df.parse(dateString);
                            Date debit1 = df.parse(todateee);
                            long diffInMillisec =  newda.getTime() - debit1.getTime();
                            long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                            Log.d("day5",String.valueOf(diffInDays));
                            if(diffInDays > 0){
                                AlertDialog.Builder alertbox = new AlertDialog.Builder(Final_report.this,R.style.AlertDialogTheme);
                                String enn = getString(R.string.fromtodate);
                                String war = getString(R.string.warning);
                                String ook = getString(R.string.ok);
                                alertbox.setMessage(enn);
                                alertbox.setTitle(war);
                                alertbox.setIcon(R.drawable.logo);
                                alertbox.setPositiveButton(ook,
                                        new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface arg0,
                                                                int arg1) {
                                            }
                                        });
                                alertbox.show();
                            }else{ Log.d("bothdates",dateString+" "+todateee);
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                list1();
                                                closed();
                                                closeddiscount();
                                                beforebal();
                                                populateRecyclerView();
                                                current_balance();
                                                nip_balance();
                                                nipnip_balance();
                                            }
                                        });
                                    }
                                }, 500);

                                runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        progressbar_load();
                                    }
                                });

                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        cu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lin1 == 0)
                {
                    expand(lo, 500, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lin1 = 1;
                    iii.setBackgroundResource(R.drawable.arrow_up);
                }
                else if(lin1 == 1)
                {
                    collapse(lo, 500, 0);
                    lin1 = 0;
                    iii.setBackgroundResource(R.drawable.arrow);

                }
            }
        });
        npp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lin2 == 1)
                {
                    expand(lo2, 500, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lin2 = 0;
                    ii2.setBackgroundResource(R.drawable.arrow_up);
                }
                else if(lin2 == 0)
                {
                    collapse(lo2, 500, 0);
                    lin2 = 1;
                    ii2.setBackgroundResource(R.drawable.arrow);

                }
            }
        });
        nppnpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lin3 == 2)
                {
                    expand(lo3, 500, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lin3 = 0;
                    ii3.setBackgroundResource(R.drawable.arrow_up);
                }
                else if(lin3 == 0)
                {

                    collapse(lo3, 500, 0);
                    lin3 = 2;
                    ii3.setBackgroundResource(R.drawable.arrow);

                }
            }
        });
//lastcollection1();

    }
    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Final_report account) {
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

    //expand() - Expand an view
    //Params - view , time taken and height to expand
    //Created on 25/01/2022
    //Return - NULL
    public static void expand(final View v, int duration, int targetHeight) {

        int prevHeight  = v.getHeight();

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();

    }
    //collapse() - collapse an view
    //Params - view , time taken and height to collapse
    //Created on 25/01/2022
    //Return - NULL
    public static void collapse(final View v, int duration, int targetHeight) {
        int prevHeight  = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
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
            Intent back = new Intent(Final_report.this,ReportActivity.class);
            startActivity(back);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //updateLabel() - Update textview when date is selected
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateString = sdf.format(myCalendar.getTime());
        dat.setText(sdf1.format(myCalendar.getTime()));

    }
    //updateLabel1() - Update textview when date is selected
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void updateLabel1() {

        String myFormat = "yyyy/MM/dd";
        String mmm = "dd/MM/yyyy";//In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(mmm, Locale.US);
        todateee = sdf.format(myCalendar1.getTime());
        todate.setText(sdf1.format(myCalendar1.getTime()));
    }
    public void lastcollection1 (){
        Integer nhnh = 0 ;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT b.id,a.collection_amount FROM dd_customers c LEFT JOIN dd_account_debit b on b.customer_id = c.id LEFT JOIN dd_collection a on a.debit_id = b.id " +
                        "WHERE a.collection_date = ? ORDER BY b.id ",
                new String[]{ "2019/12/08"});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("id");
                    String collect1 = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("collection_amount");
                    String collect11 = cursor.getString(index);
                    nhnh = nhnh + Integer.parseInt(collect11);


                    Log.d("qw12",collect1+" "+collect11);
                    Log.d("qw121",String.valueOf(nhnh));
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

    //list1() - get closed collection
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void list1(){
        dabamo = 0;
//        dateString = "'"+dateString+"'";
//        todateee = "'"+todateee+"'";
        Log.d("ins3",dateString+" "+todateee);
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        String mm = "SELECT c.vv as collect,c.debit_id " +
                " ,a.debit_type,b.id FROM dd_customers  a " +
                "LEFT JOIN dd_account_debit b on b.customer_id = a.id "  +
                "LEFT JOIN (SELECT SUM(collection_amount) as vv,debit_id,collection_date" +
                " from dd_collection WHERE collection_date BETWEEN ? AND ? GROUP BY debit_id ORDER BY debit_id  ) " +
                "c ON  b.id = c.debit_id  WHERE a.tracking_id = ? AND  b.active_status = 0 AND " +
                "c.collection_date BETWEEN ? AND ? AND a.debit_type_updated <= ? GROUP BY b.id ORDER BY b.id";
        String MY_QUERY = "SELECT b.*,a.customer_name,a.id as ID,a.location,a.phone_1,sum(c.collection_amount) as collect,a.debit_type_updated FROM dd_account_debit b LEFT JOIN dd_customers a on  a.id = b.customer_id  " +
                " Left JOIN (SELECT collection_amount,collection_date,customer_id,debit_id,discount from dd_collection GROUP BY debit_id ) c on c.debit_id = b.id  WHERE  a.tracking_id = ?  AND b.active_status = 0 GROUP BY b.id ";
//   String mm = "SELECT b.*,sum(c.co)";
        Cursor cursor = database.rawQuery(mm, new String[]{dateString,todateee,String.valueOf(ses),dateString,todateee,todateee});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("collect");
                    xcol = cursor.getString(index);
                    String vc = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("id");
                    String vc1 = cursor.getString(index);
                    Log.d("ins3",vc+" "+vc1);
                    if(vc == null ){
                        vc = "0";
                    }
                    Integer d1 = Integer.parseInt(vc);
                    dabamo = dabamo + d1;

                    clocol.setText("\u20B9"+""+String.valueOf(dabamo));
//                      deb12.setText(String.valueOf(dabamo));
//                    doc.setText(String.valueOf(docamo));
//                    inst.setText(String.valueOf(intamo));
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
                Log.d("needed",String.valueOf(dabamo));
            }else{
                cursor.close();
                sqlite.close();
                database.close();
                clocol.setText("\u20B9"+""+"0");
//                doc.setText("0");
//                inst.setText("0");
            }
        }
        else{
            cursor.close();
            sqlite.close();
            database.close();
            clocol.setText("\u20B9"+""+"0");
//            doc.setText("0");
//            inst.setText("0");
        }

    }

    //closed() - get closed debit
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void closed(){
        Log.d("cloossed",String.valueOf(cloosed)+" "+todateee);
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String NIP = "SELECT SUM(b.debit_amount) as debitted,SUM(b.document_charge) as docuu,SUM(b.interest)as interr " +
                " FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE b.updated_date <= ? AND  b.active_status = 0 AND a.tracking_id = ? ";
        String sess = String.valueOf(ses);
        Cursor cursor = database.rawQuery(NIP, new String[]{todateee,sess});
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
                    Log.d("Callidc1212c2",String.valueOf(intre) +" "+String.valueOf(sum_debit));
//                    Integer doo = Integer.parseInt(docc);
//                    Integer doo1 = Integer.parseInt(docc1);
//                    Integer in = Integer.parseInt(intre);
                    cloosed =  in ;
                    Log.d("cloossed",String.valueOf(doo));
                    Log.d("cloossed",String.valueOf(doo1));
                    Log.d("cloossed",String.valueOf(in));
                    Log.d("needed1",String.valueOf(cloosed));
//                    beforebal();
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

    //closeddiscount() - get closed discount
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void closeddiscount(){
        Log.d("dcssd",String.valueOf(cloosed)+" "+todateee+" "+dateString);
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String NIP = "SELECT SUM(col.collection_amount) as debitted,SUM(col.discount) as docuu,SUM(b.interest)as interr " +
                " FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id LEFT JOIN dd_collection col on col.debit_id = b.id " +
                " WHERE b.updated_date <= ? AND  b.active_status = 0 AND a.tracking_id = ? AND col.collection_date <=? ";
        String sess = String.valueOf(ses);
        Cursor cursor = database.rawQuery(NIP, new String[]{todateee,sess,todateee});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("docuu");
                    String intre = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("interr");
                    String docc1 = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debitted");
                    String docc= cursor.getString(index);

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
                    String sum_dis1 = pref.getString("sum_dis","0");
                    Integer sum_debit = Integer.parseInt(sum_debit1);
                    Integer sum_doc = Integer.parseInt(sum_doc1);
                    Integer sum_dis = Integer.parseInt(sum_dis1);
                    Integer doo = Integer.parseInt(docc);
                    Integer doo1 = Integer.parseInt(intre) ;

//                    Integer doo = Integer.parseInt(docc);
//                    Integer doo1 = Integer.parseInt(intre);
//                    Integer in = Integer.parseInt(intre);
//                    cloosed =  in ;
                    closeddisco = doo1;
                    Log.d("collection_amount",String.valueOf(doo));
                    Log.d("Discounted",String.valueOf(doo1));
//                    Log.d("cloossed",String.valueOf(in));
//                    Log.d("cloossed",String.valueOf(cloosed));
//                    beforebal();
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

    //beforebal() - get before balance amount
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void beforebal(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date debiwt = null;
        try {
            debiwt = df.parse(todaaaa);
            Calendar cal = Calendar.getInstance();
            cal.setTime(debiwt);
            cal.add(Calendar.DATE,-1);
            yesterdate = df.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("dateString_todate",String.valueOf(dateString)+",,"+String.valueOf(todateee));
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
        Cursor cursor = database.rawQuery(NIP, new String[]{dateString,sess,dateString,sess,dateString,sess,sess,dateString,sess,dateString,sess,dateString,todateee,sess,dateString,todateee,sess,
                dateString,todateee,sess,todateee,dateString,sess});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("debitted");
                    String debit = cursor.getString(index);
//                    Log.d("ccccooooo",debit);
//
                    index = cursor.getColumnIndexOrThrow("collected");
                    String collect = cursor.getString(index);
//                    Log.d("ccccooooo",collect);

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
                    Log.d("ccccooooo",clodebit);
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
                    Log.d("acc_cree",acc_credit+" "+acc_debit);
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

//                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
//                    String sum_debit1 = pref.getString("sum_debit","0");
//                    String sum_doc1 = pref.getString("sum_doc","0");
//                    String sum_dis1 = pref.getString("sum_dis","0");
//                    Integer sum_debit = Integer.parseInt(sum_debit1);
//                    Integer sum_doc = Integer.parseInt(sum_doc1);
//                    Integer sum_dis = Integer.parseInt(sum_dis1);
//                    Integer doo = Integer.parseInt(docc);
//                    Integer doo1 = Integer.parseInt(intre) + sum_dis;
//                    Integer cdis = Integer.parseInt(clodiscount) + sum_dis;
//                    Integer cdb = Integer.parseInt(clodebit) + sum_debit;
                    Log.d("needed00000",String.valueOf(clodiscount)+",,,"+clodebit+",,,"+clodocc+",,,"+clointre);
                    Integer cdis = Integer.parseInt(clodiscount);
                    Integer cdb = Integer.parseInt(clodebit) + sum_debit;
                    Integer cdo = Integer.parseInt(clodocc) + sum_doc;
                    Integer cin = Integer.parseInt(clointre) + sum_inter;
                    Integer dbb = Integer.parseInt(debit);
                    clodeb = cdb;
                    clodoc = cdo;
                    cloin = cin;
                    dbb = dbb + cdb;
                    Integer dio = Integer.parseInt(discount) ;
                    Integer ac_d = Integer.parseInt(acc_debit)+sum_deb;
                    Integer col = Integer.parseInt(collect);
                    col = col - dio ;
//                    col = col ;
                    Log.d("needed10",String.valueOf(dio)+",,"+String.valueOf(col)+",,"+String.valueOf(collect));
                    Integer ac_c = Integer.parseInt(acc_credit)+sum_cre;
                    Integer doo = Integer.parseInt(docc);
                    doo = doo + cdo;
                    Integer in = Integer.parseInt(intre);
                    in = in + cin;
                    Integer dbed = dbb - in;
                    Log.d("needed11",String.valueOf(cdb)+" "+String.valueOf(cdo)+" "+String.valueOf(cin));
                    Log.d("needed12",String.valueOf(dbb)+" "+String.valueOf(doo)+" "+String.valueOf(in));
                    Log.d("needed9",String.valueOf(cdis));
//                    sum_dis = closeddisco  ;
                    Integer cloosed1 = cloosed - closeddisco ;
                    cloosed1 = cloosed1 - sum_dis ;
                    Log.d("Callidc1212c1",String.valueOf(cloosed1) +" "+String.valueOf(sum_dis)+" "+String.valueOf(cloosed)+" "+String.valueOf(closeddisco));
                    befo = (ac_c+col+doo+cloosed1) - (dbed+ac_d);
                    Integer ad12 = ac_c+col+doo+cloosed1;
                    Integer ad121 = dbed+ac_d;
                    Log.d("needed5", col+" "+doo+" "+ac_c+","+cloosed1+","+cdis+" "+in);
                    Log.d("needed2",dbed+" "+ac_d);
                    Log.d("needed3", String.valueOf(ad12));
                    Log.d("needed4",String.valueOf(ad121));
                    yesterdaybal = String.valueOf(befo);
                    Log.d("befobal",String.valueOf(befo));
                    Integer aftercloseddisco = befo - dabamo;
                    bef.setText("\u20B9"+" "+String.valueOf(aftercloseddisco));
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

    //populateRecyclerView() - get final balance amount
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerView() {
        dab = 0;
        Names.clear();
        collection.clear();
        paid_cust.clear();
        balance_cust.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        Log.d("datast",yesterdate);

//        String MY_QUERY = "SELECT a.*,b.customer_name,b.id as ID,c.debit_amount FROM dd_collection a LEFT JOIN dd_customers b on b.id = a.customer_id LEFT JOIN dd_account_debit c on c.customer_id = a.customer_id ";
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

                    index = cursor.getColumnIndexOrThrow("todaycollectdisco");
                    String today_disco = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totalcloseddiso");
                    String total_closed_disco = cursor.getString(index);

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

                    index = cursor.getColumnIndexOrThrow("totalNIPNIPdisco");
                    String nipnip_disco = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totalNIPdisco");
                    String nip_disco = cursor.getString(index);

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
                    if(today_disco == null || today_disco.equalsIgnoreCase("")) {
                        today_disco = "0";
                    }
                    if(nip_disco == null || nip_disco.equalsIgnoreCase("")){
                        nip_disco = "0";
                    }
                    if(nipnip_disco == null || nipnip_disco.equalsIgnoreCase("")){
                        nipnip_disco = "0";
                    }
                    if(today == null ){
                        today ="0";
                        balance_cust.add(Long.valueOf(paid));
                    }else if(today =="0" || today.equalsIgnoreCase("0")){
                        balance_cust.add(Long.valueOf(paid));
                    }else{
                        paid_cust.add(Long.valueOf(paid));
                    }
                    if(debit == null){
                        debit = "0";
                    }
                    if(total_closed_disco == null){
                        total_closed_disco = "0";
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
//                    dabit = dabit+clodeb;
//                    documm = documm+clodoc;
//                    innn = innn+cloin;
                    Integer todi = Integer.parseInt(today_disco);
                    Integer nipdi = Integer.parseInt(nip_disco);
                    Integer nipnipdi = Integer.parseInt(nipnip_disco);
//                    Integer daabit = dabit -(innn+documm);
//                    befo = Integer.parseInt(yesterday);
                    newc = Integer.parseInt(credd);
                    curc = Integer.parseInt(today);
                    nipc = Integer.parseInt(nip);
                    nipnipc = Integer.parseInt(nipnip);
                    Integer tdi = Integer.parseInt(total_closed_disco);
                    Log.d("toodisss",String.valueOf(todi)+" "+String.valueOf(nipdi)+" "+String.valueOf(nipnipdi)+" "+String.valueOf(closeddisco)+" "+String.valueOf(tdisc));
                    Log.d("totaldebit",debbbt+" "+debbbtacc);
//                    Integer ddddis = todi + nipdi + nipnipdi + closeddisco;
                    Integer ddddis = todi + nipdi + nipnipdi ;


                    Integer totool =  curc + nipc + nipnipc + dabamo ;
                    totalcol.setText("\u20B9"+" "+String.valueOf(totool));
                    dab = dabit - innn;
                    totdab = Integer.parseInt(debbbtacc);
//                    bef.setText("Before balance :"+" "+yesterday);
                    tc.setText("\u20B9"+" "+credd);
                    tdisc.setText("\u20B9"+" "+ddddis);
                    totc.setText("\u20B9"+" "+today);
                    nic.setText("\u20B9"+" "+nip);
                    ninic.setText("\u20B9"+" "+nipnip);
                    dcc.setText("\u20B9"+" "+document);
                    intt.setText("\u20B9"+" "+interest);
                    hg.setText("\u20B9"+" "+String.valueOf(dabit));
                    lw.setText("\u20B9"+" "+debbbtacc);
                    Integer debitam1 = Integer.parseInt(debit);
                    Integer pamount = Integer.parseInt(paid);
                    Names.add(today+"   "+debit+" "+document+" "+interest+" "+nipnip+" "+nip+" "+credd+" "+debbbtacc+" "+befo);
                    collection.add(today);
                    Integer ght = (befo+newc+curc+nipc+nipnipc+documm);
                    Integer bbhb = (dab+totdab);
                    Log.d("needed7",String.valueOf(befo)+" "+String.valueOf(newc)+" "+String.valueOf(curc)+" "+String.valueOf(nipc)+" "+String.valueOf(nipnipc)+" "+documm);
//                    befo = Math.abs(befo);
                    Log.d("needed8",String.valueOf(dab)+" "+String.valueOf(totdab));
                    Integer currentbal = (befo+newc+curc+nipc+nipnipc+documm)-(dab+totdab);
                    Log.d("currentb",String.valueOf(currentbal));
                    Log.d("needed6", String.valueOf(ght)+" "+String.valueOf(bbhb));
                    if(currentbal <= 0){
                        curbal.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.Red)));
                    }else if(currentbal > 0){
                        curbal.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.Green)));
                    }
                    curbal.setText("\u20B9"+" "+String.valueOf(currentbal));
//                    collection.addAll(Names);
//                    Names.add(id+" "+Name+" "+paid);
                    Log.d("colammm", String.valueOf(collection));
                    Log.d("names", String.valueOf(Names));
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

    //current_balance() - get current balance amount
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void current_balance(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date debiwt = null;
        try {
            debiwt = df.parse(todaaaa);
            Calendar cal = Calendar.getInstance();
            cal.setTime(debiwt);
            cal.add(Calendar.DATE,-1);
            yesterdate = df.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY1 = "SELECT SUM(collection_amount) as paid," +
                "(SELECT SUM(a.debit_amount) from dd_customers b LEFT JOIN dd_account_debit a ON  a.customer_id = b.id Where b.tracking_id = ? AND b.debit_type = '0' AND a.active_status = 1 AND a.debit_date < ? ) AS beforecollect1 , " +
                "(SELECT SUM(a.collection_amount) from  dd_customers b  LEFT JOIN dd_account_debit c on b.id = c.customer_id LEFT JOIN dd_collection a ON a.debit_id = c.id  where a.collection_date < ? AND b.tracking_id = ? AND b.debit_type = '0' AND c.active_status = 1 ) AS beforecollect," +
                "(SELECT SUM(a.discount) from  dd_customers b  LEFT JOIN dd_account_debit c on b.id = c.customer_id LEFT JOIN dd_collection a ON a.debit_id = c.id  where a.collection_date < ? AND b.tracking_id = ? AND b.debit_type = '0' AND c.active_status = 1 ) AS beforecollect2," +
                "(SELECT SUM(a.debit_amount) from dd_customers b  LEFT JOIN dd_account_debit a on b.id = a.customer_id   Where a.debit_date BETWEEN ? AND ? AND b.debit_type = '0' AND b.tracking_id = ? ) as totaldebit," +
                "(SELECT SUM(b.debit_amount) from dd_customers a LEFT JOIN dd_account_debit b ON b.customer_id = a.id Where a.tracking_id = ? AND a.debit_type = '1' AND a.debit_type_updated BETWEEN ? AND ? ) AS NIPdebit ," +
                "(SELECT SUM(a.collection_amount)+SUM(a.discount) from  dd_customers b LEFT JOIN dd_account_debit c ON c.customer_id = b.id LEFT JOIN dd_collection a ON a.debit_id = c.id where (a.collection_date < ? )AND(b.debit_type_updated BETWEEN ? AND ?) AND b.tracking_id = ? AND b.debit_type = '1' AND c.active_status = 1 ) AS NIPmoved," +
                "(SELECT SUM(a.collection_amount) from dd_customers b LEFT JOIN dd_account_debit c ON c.customer_id = b.id LEFT JOIN dd_collection a ON a.debit_id = c.id  where a.collection_date BETWEEN ? AND ? AND b.debit_type = '0' AND b.tracking_id = ? AND c.active_status = 1 ) AS todaycollect, " +
                "(SELECT SUM(a.discount) from  dd_customers b LEFT JOIN dd_account_debit c ON c.customer_id = b.id LEFT JOIN dd_collection a ON a.debit_id = c.id where a.collection_date BETWEEN ? AND ? AND b.tracking_id = ? AND b.debit_type = '0' AND c.active_status = 1 ) AS totaldiscount " +
                "FROM   dd_collection    ";

        String sess = String.valueOf(ses);
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{sess,dateString,
                dateString,sess,dateString,sess,
                dateString,todateee,sess,
                sess,dateString,todateee,dateString,dateString,todateee,sess,
                dateString,todateee,sess,
                dateString,todateee,sess});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("beforecollect");
                    String yesterday = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("beforecollect1");
                    String yesterday1 = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("beforecollect2");
                    String yesterday2 = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("todaycollect");
                    String paid = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totaldebit");
                    String debit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("NIPmoved");
                    String nip = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("NIPdebit");
                    String nipdeb = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totaldiscount");
                    String disco = cursor.getString(index);

                    if(yesterday == null){
                        yesterday = "0";
                    }
                    if(yesterday2 == null){
                        yesterday2 = "0";
                    }

                    if(yesterday1 == null){
                        yesterday1 = "0";
                    }if(paid == null){
                        paid ="0";
                    }if(debit == null){
                        debit ="0";
                    }if(nip == null){
                        nip ="0";
                    }
                    if(nipdeb == null){
                        nipdeb ="0";
                    }
                    if(disco == null){
                        disco ="0";
                    }
                    Log.d("debbb",yesterday2);
                    Integer ysr2 = Integer.parseInt(yesterday2);
                    Integer ysr = Integer.parseInt(yesterday)+ysr2;
//                    Integer ysr = 1000;
                    Integer ysr1 = Integer.parseInt(yesterday1);
                    Integer pppd = Integer.parseInt(paid);
                    Integer bed = Integer.parseInt(debit);
                    Integer nipo = Integer.parseInt(nip);
                    Integer nipde = Integer.parseInt(nipdeb);
                    Integer curdi = Integer.parseInt(disco);
                    ysr = ysr1 - ysr;
                    if(nipde == 0){
                        movedniip = 0;
                         }else{
                        movedniip = nipde - nipo;
                       }
                     curbal1 = (ysr+bed)-(pppd+curdi);
                    curcolbal.setText("\u20B9"+" "+String.valueOf(curbal1));
                    Log.d("currentbalance",String.valueOf(movedniip));
                    Log.d("currentbalance",String.valueOf(curbal));

                    yester.setText("\u20B9"+" "+ysr);
                    curreee.setText("\u20B9"+" "+paid);
                    todayde.setText("\u20B9"+" "+debit);
                    movenip.setText("\u20B9"+" "+movedniip);
                    currentb.setText("\u20B9"+" "+disco);
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

    //nip_balance() - get NIP balance amount
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void nip_balance(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date debiwt = null;
        try {
            debiwt = df.parse(todaaaa);
            Calendar cal = Calendar.getInstance();
            cal.setTime(debiwt);
            cal.add(Calendar.DATE,-1);
            yesterdate = df.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY1 = "SELECT SUM(collection_amount) as paid," +
                "(SELECT SUM(a.debit_amount) from dd_customers b  LEFT JOIN dd_account_debit a ON b.id = a.customer_id  Where b.tracking_id = ? AND b.debit_type = '1' AND b.debit_type_updated < ? AND a.active_status = 1 ) AS yesterNIP, " +
                "(SELECT SUM(a.collection_amount) from  dd_customers b  LEFT JOIN dd_account_debit c on b.id = c.customer_id LEFT JOIN dd_collection a ON a.debit_id = c.id  where a.collection_date < ? AND b.debit_type_updated < ? AND b.tracking_id = ?  AND b.debit_type = '1' AND c.active_status = 1 ) AS yesterdayNIP," +
                "(SELECT SUM(a.discount) from  dd_customers b  LEFT JOIN dd_account_debit c on b.id = c.customer_id LEFT JOIN dd_collection a ON a.debit_id = c.id  where a.collection_date < ? AND b.debit_type_updated < ? AND b.tracking_id = ?  AND b.debit_type = '1' AND c.active_status = 1 ) AS yesterdayNIP1," +
                "(SELECT SUM(a.debit_amount) from dd_customers b  LEFT JOIN dd_account_debit a ON b.id = a.customer_id Where b.tracking_id = ? AND b.debit_type = '1' AND b.debit_type_updated BETWEEN ? AND ? AND a.active_status = 1 ) AS NIPdebit ," +
                "(SELECT SUM(a.collection_amount)+SUM(a.discount) from  dd_customers b  LEFT JOIN dd_account_debit c on b.id = c.customer_id LEFT JOIN dd_collection a ON a.debit_id = c.id  where (a.collection_date < ? )AND(b.debit_type_updated BETWEEN ? AND ?) AND b.tracking_id = ? AND b.debit_type = '1' AND c.active_status = 1 ) AS NIPmoved," +
                "(SELECT  SUM(a.collection_amount) as NIPinc from  dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id  LEFT JOIN dd_collection a ON a.debit_id = c.id where b.debit_type ='1' AND a.collection_date BETWEEN ? AND ? AND b.tracking_id = ? AND c.active_status = 1) as todayNIPincome, " +
                "(SELECT SUM(a.debit_amount) from  dd_customers b  LEFT JOIN dd_account_debit a ON b.id = a.customer_id Where b.tracking_id = ? AND b.debit_type = '2' AND b.debit_type_updated BETWEEN ? AND ? AND a.active_status = 1) AS NIPNIPdebit , " +
                "(SELECT SUM(a.collection_amount)+SUM(a.discount) from  dd_customers b LEFT JOIN  dd_account_debit c on b.id = c.customer_id LEFT JOIN dd_collection a ON a.debit_id = c.id  where a.collection_date < ? AND b.debit_type_updated BETWEEN ? AND ? AND b.tracking_id = ? AND b.debit_type = '2' AND c.active_status = 1 ) AS NIPNIPmoved," +
                "(SELECT SUM(a.discount) from  dd_customers b LEFT JOIN  dd_account_debit c on b.id = c.customer_id LEFT JOIN dd_collection a ON a.debit_id = c.id  where a.collection_date BETWEEN ? AND ? AND b.tracking_id = ? AND b.debit_type ='1' AND c.active_status = 1 ) AS totaldiscount " +
                "FROM   dd_collection    ";
        String sess = String.valueOf(ses);
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{sess,dateString,dateString,dateString,sess,dateString,dateString,sess,
                sess,dateString,todateee,dateString,dateString,todateee,sess
                ,dateString,todateee,sess
                ,sess,dateString,todateee,dateString,dateString,todateee,sess
        ,dateString,todateee,sess});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("yesterdayNIP");
                    String nip = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("yesterdayNIP1");
                    String nip12 = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("yesterNIP");
                    String nip1 = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("NIPNIPdebit");
                    String tonip = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("NIPmoved");
                    String tonipbal = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("NIPdebit");
                    String nipdeb = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("todayNIPincome");
                    String tonipinc = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totaldiscount");
                    String nipdisco = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("NIPNIPmoved");
                    String tonipnipp = cursor.getString(index);

                    if(nip == null){
                        nip ="0";
                    }
                    if(nip12 == null){
                        nip12 ="0";
                    }
                    if(tonip == null){
                        tonip ="0";
                    }
                if(tonipinc == null){
                        tonipinc ="0";
                    }if(tonipnipp == null){
                        tonipnipp ="0";
                    }
                    if(tonipbal == null){
                        tonipbal ="0";
                    }
                    if(nipdisco == null){
                        nipdisco ="0";
                    }
                    if(nipdeb == null){
                        nipdeb ="0";
                    }
                    if(nip1 == null){
                        nip1 ="0";
                    }
                    Log.d("nipp",nip+" "+nip1);
                    Integer ysr12 = Integer.parseInt(nip12);
                    Integer ysr = Integer.parseInt(nip)+ysr12;
                    Integer ysr1 = Integer.parseInt(nip1);
                    ysr =  ysr1 - ysr;
                    Integer pppd = Integer.parseInt(tonipbal);
                    Integer tonipd = Integer.parseInt(nipdeb);
                    Integer selectnip = tonipd - pppd ;
                    Integer bed = Integer.parseInt(tonipinc);
                    Integer nipo = Integer.parseInt(tonipnipp);
                    Integer nipdo = Integer.parseInt(nipdisco);
                    Integer nipnippde = Integer.parseInt(tonip);
                    Integer nipnipmo = Integer.parseInt(tonipnipp);
                    Log.d("nipnip",nipnippde+" "+nipnipmo);
                    if(nipnippde != 0){
                         movenipnpi = nipnippde - nipnipmo;
//                         ysr = ysr + movenipnpi;
                    }else{
                        movenipnpi = 0;
//                        ysr = ysr + movenipnpi;
                    }
                     nippbal = (ysr+selectnip)-(bed+nipdo);
                    Log.d("currentbalance",String.valueOf(nipnippde));
                    Log.d("currentbalance1",String.valueOf(nippbal));
//
                    ysrnip.setText("\u20B9"+" "+ysr);
                    todnipb.setText("\u20B9"+" "+selectnip);
                    todnipi.setText("\u20B9"+" "+tonipinc);
                    todnipnipmove.setText("\u20B9"+" "+String.valueOf(movenipnpi));
                    nipbal.setText("\u20B9"+" "+nipdisco);
                    nipbaltxt.setText("\u20B9"+" "+String.valueOf(nippbal));
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

    //nipnip_balance() - get NIPNIP balance amount
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void nipnip_balance(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date debiwt = null;
        try {
            debiwt = df.parse(todaaaa);
            Calendar cal = Calendar.getInstance();
            cal.setTime(debiwt);
            cal.add(Calendar.DATE,-1);
            yesterdate = df.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY1 = "SELECT SUM(collection_amount) as paid," +
                "(SELECT SUM(a.debit_amount) from dd_customers b LEFT JOIN dd_account_debit a  ON  a.customer_id = b.id Where b.tracking_id = ? AND b.debit_type = '2' AND b.debit_type_updated < ? AND a.active_status = 1 ) as yesterdayNIPNIP1, " +
                "(SELECT SUM(a.collection_amount) from  dd_customers b LEFT JOIN dd_account_debit c  on  c.customer_id = b.id LEFT JOIN dd_collection a ON a.debit_id = c.id  where a.collection_date < ?  AND b.debit_type_updated < ? AND b.tracking_id = ?  AND b.debit_type = '2' AND c.active_status = 1) AS yesterdayNIPNIP," +
                "(SELECT SUM(a.discount) from  dd_customers b LEFT JOIN dd_account_debit c  on  c.customer_id = b.id LEFT JOIN dd_collection a ON a.debit_id = c.id  where a.collection_date < ?  AND b.debit_type_updated < ? AND b.tracking_id = ?  AND b.debit_type = '2' AND c.active_status = 1) AS yesterdayNIPNIP2," +
                "(SELECT SUM(a.debit_amount) from  dd_customers b LEFT JOIN dd_account_debit a  ON  a.customer_id = b.id  Where b.tracking_id = ? AND b.debit_type = '2' AND b.debit_type_updated BETWEEN ? AND ? AND a.active_status = 1 ) AS selecteddebit, " +
                "(SELECT SUM(a.collection_amount)+SUM(a.discount) from  dd_customers b LEFT JOIN dd_account_debit c on  c.customer_id = b.id  LEFT JOIN dd_collection a ON a.debit_id = c.id  where a.collection_date < ? AND b.debit_type_updated BETWEEN ? AND ? AND b.tracking_id = ? AND b.debit_type = '2' AND c.active_status = 1 ) AS selectedNIPNIP," +
                "(SELECT  SUM(a.collection_amount) as NIPinc from  dd_customers b  LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a ON a.debit_id = c.id   where b.debit_type ='2' AND a.collection_date BETWEEN ? AND ? AND b.tracking_id = ? AND c.active_status = 1 ) as todayNIPNIPincome, " +
                "(SELECT SUM(a.discount) from  dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id  LEFT JOIN dd_collection a ON a.debit_id = c.id  where a.collection_date BETWEEN ? AND ? AND b.tracking_id = ? AND b.debit_type ='2' AND c.active_status = 1 ) AS totaldiscount " +
                "FROM   dd_collection ";
        String sess = String.valueOf(ses);
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{sess,dateString,
                dateString,dateString,sess, dateString,dateString,sess
                ,sess,dateString,todateee,dateString,dateString,todateee,sess
                ,dateString,todateee,sess
                ,dateString,todateee,sess});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("yesterdayNIPNIP");
                    String nipnip = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("yesterdayNIPNIP1");
                    String nipnip1 = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("yesterdayNIPNIP2");
                    String nipnip11 = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("selectedNIPNIP");
                    String toonipnip = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("selecteddebit");
                    String toonipnipdebit = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("todayNIPNIPincome");
                    String tonipnipincc = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totaldiscount");
                    String totdiscco = cursor.getString(index);


                    if(nipnip == null){
                        nipnip ="0";
                    }
                    if(nipnip1 == null){
                        nipnip1 ="0";
                    }
                    if(nipnip11 == null){
                        nipnip11 ="0";
                    }
                    if(toonipnip == null){
                        toonipnip ="0";
                    }if(tonipnipincc== null){
                        tonipnipincc ="0";
                    }if(totdiscco== null){
                        totdiscco ="0";
                    } if(toonipnipdebit == null){
                        toonipnipdebit ="0";
                    }
                    Integer ysr21 = Integer.parseInt(nipnip11);
                    Integer ysr = Integer.parseInt(nipnip)+ysr21;
                    Integer ysr1 = Integer.parseInt(nipnip1);
                    ysr = ysr1 - ysr;
                    Log.d("yester_NIPNIP",String.valueOf(nipnip11)+" "+String.valueOf(nipnip)+" "+String.valueOf(nipnip1));
                    Integer pppd = Integer.parseInt(toonipnip);
                    Integer bed = Integer.parseInt(tonipnipincc);
                    Integer diss = Integer.parseInt(totdiscco);
                    Integer sesde = Integer.parseInt(toonipnipdebit);
                    if(sesde == 0){
                        selectnipnip =0 ;
//                        ysr = ysr + selectnipnip;
                    }else{
                        selectnipnip =sesde - pppd;
//                        ysr = ysr + selectnipnip;
                    }
                    Integer nipnippbal = (ysr+selectnipnip)-(bed+diss);

                    ysrnipnip.setText("\u20B9"+" "+ysr);
                    tonipnip.setText("\u20B9"+" "+String.valueOf(selectnipnip));
                    tonipnipinc.setText("\u20B9"+" "+tonipnipincc);
                    nipnipdiss.setText("\u20B9"+" "+totdiscco);
                    nipnipb.setText("\u20B9"+" "+String.valueOf(nipnippbal));
                    totrem = curbal1+nippbal+nipnippbal;
                    totrema.setText(":"+"\u20B9"+" "+String.valueOf(totrem));
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            progre();
                        }
                    });
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
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    progre();
                }
            });
            cursor.close();
            sqlite.close();
            database.close();
        }

    }
}
