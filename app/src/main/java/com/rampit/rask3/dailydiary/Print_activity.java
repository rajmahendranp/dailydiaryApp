package com.rampit.rask3.dailydiary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.text.SpannableStringBuilder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;



//Updated on 25/01/2022 by RAMPIT
//used to create PDF
//InternetConnection1() - Check whether internet is on or not
//progressbar_load() - Load progressbar
//progre() - Stop progressbar
//onResume() - Called when activity is resumed
//onStop() - Called when activity is stopped
//onStart() - Called when activity is started
//onPause() - Called when activity is paused
//ch() - check for selected options for print
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed
//createPdfWrapper() - check for Read and write permission
//createPdf() - Create PDF
//createPdf1() - Create PDF
//getCell() - change font name  , size and color and text size
//getCell1() - change font name  , size and color and text size
//previewpdf() - Show PDF after creation
//populateRecyclerView() - get all details of customers and debit order by order_id_new Ascending
//populateRecyclerView2() - get all details of customers and debit order by CID Ascending
//populateRecyclerView3() - get all details of customers and debit order by order_id_new Descending
//populateRecyclerView4() - get all settings details


public class Print_activity extends AppCompatActivity {
    CheckBox pday, tday, bday, bamount, pamountt, mamount, mday, deb, odate, cdate, days , installme;
    RadioButton horizontal, vertical, byid, byorder;
    String balance, bdays, pdays, tdays, orie, mdays, deb1, odat, cdat, pam, mamo, timing, pppdr, MY_QUERY , installme1;
    Integer bal, bd, pd, td, ses, toda, toda1, misda, slllno, misam, preview;

    TextView dateee, session;
    Button show;
    Typeface font1, font2;
    SpannableStringBuilder SS;
    private static final String TAG = "PdfCreatorActivity";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    private File pdfFile;
    ImageView imgdownload;
    Paragraph p3;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    ArrayList<PDF_pojo> MyList1;
    ArrayList<String> ch = new ArrayList<>();
    ArrayList<String> check = new ArrayList<>();
    List<String> list;
    ArrayList<String> Names = new ArrayList<>();
    String[] datesss;
    String ppd;
    PDF_pojo PDF_pojo;
    Context context;
    PDF_pojo name;
    PDF_pojo price;
    PDF_pojo url;
    PDF_pojo type;
    PDF_pojo date;
    PDF_pojo slno;
    String orientation, tda, pda, bda, bam, user_pass, owner_pass, missed, dated, dated1, dayss;
    Integer oreo, size, dateloopcount, addz;
    PdfPTable table;
    SimpleDateFormat df;
    Calendar c;
    Integer balanceamount, debitam1, instamt, pamount, checkk;
    Double paiddays, balanceday, balancamo, toda2, totda, dbdate, todate, missda1;
    String idd, iii, nme, paidamount, installment, totaldays, ddbt, locc, pp, formattedDate, today, orderby, timing1, missiday, strConBamini;
    ImageView seesim;
    Integer in;
    Font sdfer, sdfer1;
    PdfPCell cell2;
    String pdfpass123;
    Dialog dialog;
    Integer showinng = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ii = pref.getString("theme", "");
        if (ii.equalsIgnoreCase("")) {
            ii = "1";
        }
        Log.d("thee", ii);
        in = Integer.valueOf(ii);

        if (in == 0) {
            Log.d("thee", "thh");
            setTheme(R.style.AppTheme1);
        } else {
            Log.d("thee", "th1h");
            setTheme(R.style.AppTheme11);
//            recreate();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String localeName = pref.getString("language", "");
        if (localeName == null) {
            localeName = "ta";
        }
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dialog = new Dialog(Print_activity.this,R.style.AlertDialogTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.print_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.title_print);
        ((Callback) getApplication()).datee();
        ((Callback) getApplication()).emailid();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        context = this;
        PDF_pojo = new PDF_pojo();
        checkk = 0;
        user_pass = pdfpass123;
        owner_pass = "rampit@1";
//        DoLOgin doLOgin=new DoLOgin();
//        doLOgin.execute();
        c = Calendar.getInstance();
        list = new ArrayList<String>();
        df = new SimpleDateFormat("dd/MM");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dff = new SimpleDateFormat("MMM-d-yyyy-HH_mm_ss");
        today = dff.format(Calendar.getInstance().getTime());
        Log.d("dadaaa", today);
        formattedDate = sdf.format(c.getTime());
        seesim = findViewById(R.id.sesimg);
        pday = findViewById(R.id.paiddays);
        tday = findViewById(R.id.totaldays);
        bday = findViewById(R.id.balancedays);
        bamount = findViewById(R.id.balanceamount);
        mday = findViewById(R.id.missingdays);
        deb = findViewById(R.id.debit);
        pamountt = findViewById(R.id.paidamount);
        mamount = findViewById(R.id.missingamount);
        odate = findViewById(R.id.openingdate);
        cdate = findViewById(R.id.closingdate);
        days = findViewById(R.id.days);
        show = findViewById(R.id.show);
        horizontal = findViewById(R.id.horizz);
        vertical = findViewById(R.id.vertii);
        byid = findViewById(R.id.iddd);
        byorder = findViewById(R.id.orderid);
        installme = findViewById(R.id.installment);
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),Print_activity.this)) {
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
        if (in == 0) {
            deb.setButtonDrawable(R.drawable.custom_checkbox);
            pamountt.setButtonDrawable(R.drawable.custom_checkbox);
            mamount.setButtonDrawable(R.drawable.custom_checkbox);
            odate.setButtonDrawable(R.drawable.custom_checkbox);
            cdate.setButtonDrawable(R.drawable.custom_checkbox);
            mday.setButtonDrawable(R.drawable.custom_checkbox);
            bamount.setButtonDrawable(R.drawable.custom_checkbox);
            bday.setButtonDrawable(R.drawable.custom_checkbox);
            tday.setButtonDrawable(R.drawable.custom_checkbox);
            pday.setButtonDrawable(R.drawable.custom_checkbox);
            installme.setButtonDrawable(R.drawable.custom_checkbox);
            byid.setButtonDrawable(R.drawable.custom_radio);
            byorder.setButtonDrawable(R.drawable.custom_radio);
            vertical.setButtonDrawable(R.drawable.custom_radio);
            horizontal.setButtonDrawable(R.drawable.custom_radio);
            days.setButtonDrawable(R.drawable.custom_checkbox);
        }
        else {
            deb.setButtonDrawable(R.drawable.custom_checkbox1);
            pamountt.setButtonDrawable(R.drawable.custom_checkbox1);
            mamount.setButtonDrawable(R.drawable.custom_checkbox1);
            odate.setButtonDrawable(R.drawable.custom_checkbox1);
            cdate.setButtonDrawable(R.drawable.custom_checkbox1);
            mday.setButtonDrawable(R.drawable.custom_checkbox1);
            bamount.setButtonDrawable(R.drawable.custom_checkbox1);
            bday.setButtonDrawable(R.drawable.custom_checkbox1);
            tday.setButtonDrawable(R.drawable.custom_checkbox1);
            installme.setButtonDrawable(R.drawable.custom_checkbox1);
            pday.setButtonDrawable(R.drawable.custom_checkbox1);
            byid.setButtonDrawable(R.drawable.custom_radio1);
            byorder.setButtonDrawable(R.drawable.custom_radio1);
            vertical.setButtonDrawable(R.drawable.custom_radio1);
            horizontal.setButtonDrawable(R.drawable.custom_radio1);
            days.setButtonDrawable(R.drawable.custom_checkbox1);
        }
        pdays = "0";
        tdays = "0";
        bdays = "0";
        balance = "0";
        mdays = "0";
        pam = "0";
        odat = "0";
        cdat = "0";
        deb1 = "0";
        mamo = "0";
        orie = "0";
        pppdr = "0";
        dayss = "0";
        installme1 = "0";
        ses = pref.getInt("session", 1);
        nme = pref.getString("NAME", "");
        locc = pref.getString("LOCATION", "");
        String dd = pref.getString("Date", "");
        dateee = findViewById(R.id.da);
        session = findViewById(R.id.sess);
        dateee.setText(dd);
        TextView dayy = (TextView) findViewById(R.id.day);
        String weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        if (weekday_name.equalsIgnoreCase("Monday")) {
            weekday_name = getString(R.string.monday);
        } else if (weekday_name.equalsIgnoreCase("Tuesday")) {
            weekday_name = getString(R.string.tuesday);
        } else if (weekday_name.equalsIgnoreCase("Wednesday")) {
            weekday_name = getString(R.string.wednesday);
        } else if (weekday_name.equalsIgnoreCase("Thursday")) {
            weekday_name = getString(R.string.thursday);
        } else if (weekday_name.equalsIgnoreCase("Friday")) {
            weekday_name = getString(R.string.friday);
        } else if (weekday_name.equalsIgnoreCase("Saturday")) {
            weekday_name = getString(R.string.saturday);
        } else if (weekday_name.equalsIgnoreCase("Sunday")) {
            weekday_name = getString(R.string.sunday);
        }
        dayy.setText(weekday_name);
        if (in == 0) {
            if (ses == 1) {
                timing = getString(R.string.morning);
                timing = "Morning";
                seesim.setBackgroundResource(R.drawable.sun);
                timing1 = "M";
            } else if (ses == 2) {
                timing = getString(R.string.evening);
                seesim.setBackgroundResource(R.drawable.moon);
                timing = "Evening";
                timing1 = "E";
            }
        } else {
            if (ses == 1) {
                timing = getString(R.string.morning);
                timing = "Morning";
                seesim.setBackgroundResource(R.drawable.sun_blue);
                timing1 = "M";
            } else if (ses == 2) {
                timing = getString(R.string.evening);
                timing = "Evening";
                seesim.setBackgroundResource(R.drawable.moon_blue);
                timing1 = "E";
            }
        }
        session.setText(timing);

        byid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (byid.isChecked()) {
                    pppdr = "1";
                    byorder.setChecked(false);
                    Toast.makeText(getApplicationContext(), pppdr, Toast.LENGTH_SHORT).show();
                } else {
                    pppdr = "0";
                }
            }
        });
        byorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (byorder.isChecked()) {
                    pppdr = "2";
                    byid.setChecked(false);
                    Toast.makeText(getApplicationContext(), pppdr, Toast.LENGTH_SHORT).show();
                } else {
                    pppdr = "0";
                }
            }
        });
        horizontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (horizontal.isChecked()) {
                    orie = "1";
                    vertical.setChecked(false);
                    Toast.makeText(getApplicationContext(), orie, Toast.LENGTH_SHORT).show();
                } else {
                    orie = "0";
                }
            }
        });
        vertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vertical.isChecked()) {
                    orie = "2";
                    horizontal.setChecked(false);
                    Toast.makeText(getApplicationContext(), orie, Toast.LENGTH_SHORT).show();
                } else {
                    orie = "0";
                }
            }
        });
        pday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pday.isChecked()) {
                    pdays = "2";
                    checkk = checkk + 1;
                    ch();
//                    Toast.makeText(getApplicationContext(),"First checkbox checked", Toast.LENGTH_SHORT).show();
                } else {
                    pdays = "0";
                    checkk = checkk - 1;
//                    Toast.makeText(getApplicationContext(),"First checkbox Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bday.isChecked()) {
                    bdays = "3";
                    checkk = checkk + 1;
                    ch();
//                    Toast.makeText(getApplicationContext(),"First checkbox checked", Toast.LENGTH_SHORT).show();
                } else {
                    bdays = "0";
                    checkk = checkk - 1;
//                    Toast.makeText(getApplicationContext(),"First checkbox Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tday.isChecked()) {
                    tdays = "1";
                    checkk = checkk + 1;
                    ch();
//                    Toast.makeText(getApplicationContext(),"First checkbox checked", Toast.LENGTH_SHORT).show();
                } else {
                    tdays = "0";
                    checkk = checkk - 1;
//                    Toast.makeText(getApplicationContext(),"First checkbox Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        installme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (installme.isChecked()) {
                    installme1 = "12";
                    checkk = checkk + 1;
                    ch();
//                    Toast.makeText(getApplicationContext(),"First checkbox checked", Toast.LENGTH_SHORT).show();
                } else {
                    installme1 = "0";
                    checkk = checkk - 1;
//                    Toast.makeText(getApplicationContext(),"First checkbox Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bamount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bamount.isChecked()) {
                    balance = "4";
                    checkk = checkk + 1;
                    ch();
//                    Toast.makeText(getApplicationContext(),"First checkbox checked", Toast.LENGTH_SHORT).show();
                } else {
                    balance = "0";
                    checkk = checkk - 1;
//                    Toast.makeText(getApplicationContext(),"First checkbox Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mday.isChecked()) {
                    mdays = "5";
                    checkk = checkk + 1;
                    ch();
//                    Toast.makeText(getApplicationContext(),"First checkbox checked", Toast.LENGTH_SHORT).show();
                } else {
                    mdays = "0";
                    checkk = checkk - 1;
//                    Toast.makeText(getApplicationContext(),"First checkbox Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mamount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mamount.isChecked()) {
                    mamo = "6";
                    checkk = checkk + 1;
                    ch();
//                    Toast.makeText(getApplicationContext(),"First checkbox checked", Toast.LENGTH_SHORT).show();
                } else {
                    mamo = "0";
                    checkk = checkk - 1;
//                    Toast.makeText(getApplicationContext(),"First checkbox Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        pamountt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pamountt.isChecked()) {
                    pam = "7";
                    checkk = checkk + 1;
                    ch();
//                    Toast.makeText(getApplicationContext(),"First checkbox checked", Toast.LENGTH_SHORT).show();
                } else {
                    pam = "0";
                    checkk = checkk - 1;
//                    Toast.makeText(getApplicationContext(),"First checkbox Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        odate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (odate.isChecked()) {
                    odat = "8";
                    checkk = checkk + 1;
                    ch();
//                    Toast.makeText(getApplicationContext(),"First checkbox checked", Toast.LENGTH_SHORT).show();
                } else {
                    odat = "0";
                    checkk = checkk - 1;
//                    Toast.makeText(getApplicationContext(),"First checkbox Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cdate.isChecked()) {
                    cdat = "9";
                    checkk = checkk + 1;
                    ch();
//                    Toast.makeText(getApplicationContext(),"First checkbox checked", Toast.LENGTH_SHORT).show();
                } else {
                    cdat = "0";
                    checkk = checkk - 1;
//                    Toast.makeText(getApplicationContext(),"First checkbox Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        deb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deb.isChecked()) {
                    deb1 = "10";
                    checkk = checkk + 1;
                    ch();
//                    Toast.makeText(getApplicationContext(),"First checkbox checked", Toast.LENGTH_SHORT).show();
                } else {
                    deb1 = "0";
                    checkk = checkk - 1;
//                    Toast.makeText(getApplicationContext(),"First checkbox Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (days.isChecked()) {
                    dayss = "11";
                    checkk = checkk + 1;
                    ch();
//                    Toast.makeText(getApplicationContext(),"First checkbox checked", Toast.LENGTH_SHORT).show();
                } else {
                    dayss = "0";
                    checkk = checkk - 1;
//                    Toast.makeText(getApplicationContext(),"First checkbox Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        preview = 0;
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            Intent pdf = new Intent(Print_activity.this,Demo_pdf.class);
//            pdf.putExtra("orderby",pppdr);
//            pdf.putExtra("orientation",orie);
//            pdf.putExtra("totald",tdays);
//            pdf.putExtra("paidd",pdays);
//            pdf.putExtra("balanced",bdays);
//            pdf.putExtra("balancea",balance);
//            startActivity(pdf);
//            MyList1.clear();
//            MyList1.isEmpty();
//            Toast.makeText(getApplicationContext(),String.valueOf(MyList1.isEmpty()),Toast.LENGTH_SHORT).show();
                if (checkk > 5) {
                    Toast.makeText(getApplicationContext(), String.valueOf(checkk), Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(Print_activity.this, R.style.AlertDialogTheme);
                    String logmsg = getString(R.string.select_onlyfive);
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
                }
                else {
                    orientation = orie;
                    tda = tdays;
                    pda = pdays;
                    bda = bdays;
                    bam = balance;
                    orderby = pppdr;
                    oreo = Integer.parseInt(orientation);
                    if (oreo == 0 || pppdr.equalsIgnoreCase("0")) {
                        AlertDialog.Builder alertbox = new AlertDialog.Builder(Print_activity.this, R.style.AlertDialogTheme);
                        String logmsg = getString(R.string.select_following1);
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
                    } else {
                        Toast.makeText(getApplicationContext(), balance + " " + tdays + " " + pdays + " " + bdays, Toast.LENGTH_SHORT).show();
                        check.clear();
                        if (tda.equalsIgnoreCase("1")) {
                            check.add(tda);
                        }
                        if (pda.equalsIgnoreCase("2")) {
                            check.add(pda);
                        }
                        if (bda.equalsIgnoreCase("3")) {
                            check.add(bda);
                        }
                        if (bam.equalsIgnoreCase("4")) {
                            check.add(bam);
                        }
                        addz = 0;
                        size = check.size();
                        if (orderby.equalsIgnoreCase("1")) {
//                            populateRecyclerView2();
//                            populateRecyclerView3();
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            populateRecyclerView2();
                                            populateRecyclerView3();
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
                        } else if (orderby.equalsIgnoreCase("2")) {
//                            populateRecyclerView();
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            populateRecyclerView();
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
                    }
                }
            }
        });
//        Intent pdf = getIntent();
//        orientation = pdf.getStringExtra("orientation");
//        tda = pdf.getStringExtra("totald");
//        pda = pdf.getStringExtra("paidd");
//        bda = pdf.getStringExtra("balanced");
//        bam = pdf.getStringExtra("balancea");
//        orderby = pdf.getStringExtra("orderby");
//        oreo = Integer.parseInt(orientation);
//        populateRecyclerView4();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        populateRecyclerView4();
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
    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Print_activity account) {
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
    //onResume() - Called when activity is resumed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onResume() {
        super.onResume();

    }
    //onStop() - Called when activity is stopped
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    protected void onStop() {
        Log.d("print_screen","stop");
        finish();
        super.onStop();
    }
    //onStart() - Called when activity is started
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    protected void onStart() {
        Log.d("print_screen","start");
        super.onStart();
    }
    //onPause() - Called when activity is paused
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    protected void onPause() {
        Log.d("print_screen","pause");
        super.onPause();
    }
    //ch() - check for selected options for print
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void ch() {
        if (checkk > 5) {
            Toast.makeText(getApplicationContext(), String.valueOf(checkk), Toast.LENGTH_SHORT).show();
        } else {
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
            Intent back = new Intent(Print_activity.this, NavigationActivity.class);
            startActivity(back);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //createPdfWrapper() - check for Read and write permission
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void createPdfWrapper() throws FileNotFoundException, DocumentException {
        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Print_activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            if (oreo == 1) {
                createPdf1();
//                previewpdf();
            } else if (oreo == 2) {
                createPdf();
//                previewpdf();
            }
        }
    }

    //createPdf() - Create PDF
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        String pdfname = "Daily_"+today + "Vertical" + ".pdf";
        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
        OutputStream output = new FileOutputStream(pdfFile);

        Document document = new Document(PageSize.A4, 10f, 10f, 10f, 0f);
        if (odat.equalsIgnoreCase("8") && cdat.equalsIgnoreCase("9")) {
            dateloopcount = 7;
            if (checkk == 1) {
                table = new PdfPTable(new float[]{100, 200, 140, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount + 3;
            } else if (checkk == 2) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount + 2;
            } else if (checkk == 3) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 140, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount;
            } else if (checkk == 4) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 140, 140, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount - 1;
            } else if (checkk == 5) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 140, 140, 140, 80, 80, 80, 80});
                dateloopcount = dateloopcount - 3;
            } else if (checkk == 0) {
                table = new PdfPTable(new float[]{100, 200, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount + 4;
            }
        } else if (odat.equalsIgnoreCase("8") || cdat.equalsIgnoreCase("9")) {
            dateloopcount = 7;
            if (checkk == 1) {
                table = new PdfPTable(new float[]{100, 200, 140, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount + 3;
            } else if (checkk == 2) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount + 1;
            } else if (checkk == 3) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 140, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount;
            } else if (checkk == 4) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 140, 140, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount - 2;
            } else if (checkk == 5) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 140, 140, 140, 80, 80, 80, 80});
                dateloopcount = dateloopcount - 3;
            } else if (checkk == 0) {
                table = new PdfPTable(new float[]{100, 200, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount + 4;
            }
        } else {
            dateloopcount = 8;
            if (checkk == 1) {
                table = new PdfPTable(new float[]{100, 200, 140, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount + 2;
            } else if (checkk == 2) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount + 1;
            } else if (checkk == 3) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 140, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount - 1;
            } else if (checkk == 4) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 140, 140, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount - 2;
            } else if (checkk == 5) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 140, 140, 140, 80, 80, 80, 80});
                dateloopcount = dateloopcount - 4;
            } else if (checkk == 0) {
                table = new PdfPTable(new float[]{100, 200, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount + 4;
            }
        }
        table.getWidthPercentage();
        Log.d("tabala", String.valueOf(table.getWidthPercentage()));
//        table.getDefaultCell().setRotation(90);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(20);
        table.setTotalWidth(PageSize.A4.getWidth());
//        table.setLockedWidth(true);
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell("SL");
        table.addCell("Name");
        if (odat.equalsIgnoreCase("8")) {
            table.addCell("OD");
        }
        if (cdat.equalsIgnoreCase("9")) {
            table.addCell("CD");

        }
        if (deb1.equalsIgnoreCase("10")) {
            table.addCell("DE");
        }
        if (tda.equalsIgnoreCase("1")) {
            table.addCell("DA");

        }
        if (pda.equalsIgnoreCase("2")) {
            table.addCell("PD");

        }
        if (bda.equalsIgnoreCase("3")) {
            table.addCell("BD");

        }
        if (bam.equalsIgnoreCase("4")) {
            table.addCell("BA");
        }
        if (mdays.equalsIgnoreCase("5")) {
            table.addCell("MD");

        }
        if (mamo.equalsIgnoreCase("6")) {
            table.addCell("MA");

        }
        if (pam.equalsIgnoreCase("7")) {
            table.addCell("PA");

        }
        if (dayss.equalsIgnoreCase("11")) {
            table.addCell("TD");
        }
        if (installme1.equalsIgnoreCase("12")) {
            table.addCell("IN");
        }


        for (int i = 0; i < dateloopcount; i++) {

            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE, i);
            String day = df.format(calendar.getTime());
            Log.d("aaa", day);
            table.addCell(day);
        }


        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();

        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
        Log.d("myliss", String.valueOf(MyList1));
        for (int i = 0; i < MyList1.size(); i++) {
            name = MyList1.get(i);
            type = MyList1.get(i);
            date = MyList1.get(i);
            url = MyList1.get(i);
            price = MyList1.get(i);
            slno = MyList1.get(i);
            String namen = name.getItem_name();
            String pricen = price.getItem_price();
            String daten = date.getCreatedAt();
            String typen = type.getItem_type_code();
            String urln = url.getItem_URL();
            String sll = url.getslno();
            String oo = name.getopendate();
            String cl = name.getclosedate();
            String de = name.getdebit();
            String pa = name.getpaid();
            String ma = name.getmissamo();
            String md = name.getmissday();
            String dd = name.getdays();
            String ins = name.getInstallement();
//            Phrase one = new Phrase(timing,h);
//            table.addCell(timing1);
//            table.addCell(String.valueOf(sll));
//            table.addCell(String.valueOf(namen));
            table.addCell(getCell(String.valueOf(sll), PdfPCell.ALIGN_CENTER));
            table.addCell(getCell(String.valueOf(namen), PdfPCell.ALIGN_CENTER));
            if (odat.equalsIgnoreCase("8")) {
//                table.addCell(String.valueOf(oo));
                table.addCell(getCell(String.valueOf(oo), PdfPCell.ALIGN_CENTER));
            }
            if (cdat.equalsIgnoreCase("9")) {
//                table.addCell(String.valueOf(cl));
                table.addCell(getCell(String.valueOf(cl), PdfPCell.ALIGN_CENTER));
            }
            if (deb1.equalsIgnoreCase("10")) {
//                table.addCell(String.valueOf(de));
                table.addCell(getCell(String.valueOf(de), PdfPCell.ALIGN_CENTER));
            }
            if (tda.equalsIgnoreCase("1")) {
//                table.addCell(String.valueOf(pricen));
                table.addCell(getCell(String.valueOf(pricen), PdfPCell.ALIGN_CENTER));
            }
            if (pda.equalsIgnoreCase("2")) {
//                table.addCell(String.valueOf(urln));
                table.addCell(getCell(String.valueOf(urln), PdfPCell.ALIGN_CENTER));
            }
            if (bda.equalsIgnoreCase("3")) {
//                table.addCell(String.valueOf(typen));
                table.addCell(getCell(String.valueOf(typen), PdfPCell.ALIGN_CENTER));
            }
            if (bam.equalsIgnoreCase("4")) {
//                table.addCell(String.valueOf(daten));
                table.addCell(getCell(String.valueOf(daten), PdfPCell.ALIGN_CENTER));
            }
            if (mdays.equalsIgnoreCase("5")) {
//                table.addCell(String.valueOf(md));
                table.addCell(getCell(String.valueOf(md), PdfPCell.ALIGN_CENTER));
            }
            if (mamo.equalsIgnoreCase("6")) {
//                table.addCell(String.valueOf(ma));
                table.addCell(getCell(String.valueOf(ma), PdfPCell.ALIGN_CENTER));
            }
            if (pam.equalsIgnoreCase("7")) {
                table.addCell(getCell(String.valueOf(pa), PdfPCell.ALIGN_CENTER));

            }
            if (dayss.equalsIgnoreCase("11")) {
                table.addCell(getCell(String.valueOf(dd), PdfPCell.ALIGN_CENTER));

            }
            if (installme1.equalsIgnoreCase("12")) {
                table.addCell(getCell(String.valueOf(ins), PdfPCell.ALIGN_CENTER));

            }
            for (int j = 0; j < dateloopcount; j++) {
                table.addCell("");
            }

        }
//        System.out.println("Done");
        PdfWriter pdfWriter = PdfWriter.getInstance(document, output);
        pdfWriter.setEncryption(user_pass.getBytes(), owner_pass.getBytes(),
                PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
//        PdfWriter.getInstance(document, output);
        class RotateEvent extends PdfPageEventHelper {
            public void onStartPage(PdfWriter writer, Document document) {
                writer.addPageDictEntry(PdfName.ROTATE, PdfPage.LANDSCAPE);
            }
        }

        // Rotates each page to landscape

//        pdfWriter.setPageEvent(new RotateEvent());
//        pdfWriter.addPageDictEntry(PdfName.ROTATE, PdfPage.SEASCAPE);
        document.open();
        Log.d("sessss", timing);
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.NORMAL, BaseColor.RED);
        Font g = new Font(Font.FontFamily.TIMES_ROMAN, 15.0f, Font.NORMAL, BaseColor.RED);
        Paragraph p = new Paragraph("Date :" + " " + formattedDate + "\n", g);
        p.setAlignment(Element.ALIGN_LEFT);
        Paragraph p3 = new Paragraph("Session :" + " " + timing + "\n\n", g);
        p3.setAlignment(Element.ALIGN_LEFT);
        Paragraph p1 = new Paragraph("Company :" + " " + nme + "\n", g);
        p1.setAlignment(Element.ALIGN_CENTER);
        Paragraph p2 = new Paragraph("Location :" + " " + locc + "\n", g);
        p2.setAlignment(Element.ALIGN_CENTER);
        Paragraph p24 = new Paragraph("" + "\n");
        p24.setAlignment(Element.ALIGN_CENTER);
//        Chunk glue = new Chunk(new VerticalPositionMark());
//        Paragraph p = new Paragraph("Text to the left");
//        p.add(new Chunk(glue));
//        p.add("Text to the right");
//        document.add(p);
        PdfPTable table1 = new PdfPTable(4);
        table1 = new PdfPTable(new float[]{150, 550, 150, 150});
        table1.setWidthPercentage(100);
        table1.getDefaultCell().setFixedHeight(13);
//        table1.addCell(getCell(timing, PdfPCell.ALIGN_CENTER));
//        table1.addCell(getCell(timing , PdfPCell.ALIGN_CENTER));
//        table1.addCell(getCell(timing , PdfPCell.ALIGN_CENTER));
//        table1.addCell(getCell(timing , PdfPCell.ALIGN_CENTER));
//        table1.addCell(getCell(timing , PdfPCell.ALIGN_CENTER));
//        table1.addCell(getCell("aliasing" , PdfPCell.ALIGN_CENTER));
//        table1.addCell(getCell("இது முதல் பேரா " , PdfPCell.ALIGN_CENTER));
//        table1.addCell(getCell("இது முதல் பேரா " , PdfPCell.ALIGN_CENTER));
        table1.addCell(getCell1(" ", PdfPCell.ALIGN_CENTER));
//        table1.addCell(getCell1("Company :", PdfPCell.ALIGN_CENTER));
        table1.addCell(getCell1(nme, PdfPCell.ALIGN_CENTER));
        table1.addCell(getCell1("OD :" + " " + "Opening Date ", PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1("CD :" + " " + "Closing Date", PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1(" ", PdfPCell.ALIGN_CENTER));
        table1.addCell(getCell1("Location :" + " " + locc, PdfPCell.ALIGN_CENTER));
        table1.addCell(getCell1("TD :" + " " + "Total Days", PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1("DE :" + " " + "Debit", PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1("Date :" + " " + formattedDate, PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1(" ", PdfPCell.ALIGN_CENTER));
        table1.addCell(getCell1("PD :" + " " + "Paid Days ", PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1("PA :" + " " + "Paid Amount", PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1("Session :" + " " + timing, PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1(" ", PdfPCell.ALIGN_CENTER));
        table1.addCell(getCell1("BD :" + " " + "Balance Days", PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1("BA :" + " " + "Balance Amount", PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1(" ", PdfPCell.ALIGN_CENTER));
        table1.addCell(getCell1(" " + "\n", PdfPCell.ALIGN_CENTER));
        table1.addCell(getCell1("MD :" + " " + "Missing Days", PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1("MA :" + " " + "Missing Amount", PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1("IN :" + " " + "Installment Amount", PdfPCell.ALIGN_LEFT));

        document.add(table1);
        document.add(p24);
//        document.add(p1);
//        document.add(p2);
//        document.add(p);
//        document.add(p3);
        document.add(table);

//        for (int i = 0; i < MyList1.size(); i++) {
//            document.add(new Paragraph(String.valueOf(MyList1.get(i))));
//        }

        document.close();

//        preview = preview + 1;
//        if(preview == Names.size());
//        {
//            previewpdf();
//        }
        Log.e("pdff", String.valueOf(Names.size()));
//        previewpdf();
    }

    //createPdf1() - Create PDF
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void createPdf1() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            if (!docsFolder.mkdir()) {
                Toast.makeText(context, "no directory", Toast.LENGTH_LONG).show();
            } else {
                Log.i(TAG, "Created a new directory for PDF");
            }
        }
//        if (!dir.exists()) { if ( !dir.mkdir()) {Toast(... could not make dir ....); return;}; }
        String pdfname = "Daily_"+today + "Horizontal" + ".pdf";
//        previewpdf();
//        PdfFont fontLatha
//                = PdfFont.create("C:\\WINDOWS\\Fonts\\latha.ttf",
//                10,
//                PdfEncodings.UTF_16BE,
//                PdfFont.EMBED_SUBSET);
        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);

        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document(PageSize.A4.rotate(), 10f, 10f, 10f, 0f);
        if (odat.equalsIgnoreCase("8") && cdat.equalsIgnoreCase("9")) {
            dateloopcount = 14;
            if (checkk == 1) {
                table = new PdfPTable(new float[]{100, 200, 140, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount + 3;
            } else if (checkk == 2) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount + 2;
            } else if (checkk == 3) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 140, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount;
            } else if (checkk == 4) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 140, 140, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount - 1;
            } else if (checkk == 5) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 140, 140, 140, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount - 3;
            } else if (checkk == 0) {
                table = new PdfPTable(new float[]{100, 200, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount + 4;
            }
        } else if (odat.equalsIgnoreCase("8") || cdat.equalsIgnoreCase("9")) {
            dateloopcount = 14;
            if (checkk == 1) {
                table = new PdfPTable(new float[]{100, 200, 140, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount + 3;
            } else if (checkk == 2) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount + 1;
            } else if (checkk == 3) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 140, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount;
            } else if (checkk == 4) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 140, 140, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount - 2;
            } else if (checkk == 5) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 140, 140, 140, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount - 3;
            } else if (checkk == 0) {
                table = new PdfPTable(new float[]{100, 200, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount + 4;
            }
        } else {
            dateloopcount = 15;
            if (checkk == 1) {
                table = new PdfPTable(new float[]{100, 200, 140, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount + 2;
            } else if (checkk == 2) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount + 1;
            } else if (checkk == 3) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 140, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount - 1;
            } else if (checkk == 4) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 140, 140, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount - 2;
            } else if (checkk == 5) {
                table = new PdfPTable(new float[]{100, 200, 140, 140, 140, 140, 140, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount - 4;
            } else if (checkk == 0) {
                table = new PdfPTable(new float[]{100, 200, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80});
                dateloopcount = dateloopcount + 4;
            }
        }
        table.getWidthPercentage();
        Log.d("tabala", String.valueOf(table.getWidthPercentage()));
//        table.getDefaultCell().setRotation(90);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(20);
        table.setTotalWidth(PageSize.A4.getWidth());
//        table.setLockedWidth(true);
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell("SL");
        table.addCell("Name");
        if (odat.equalsIgnoreCase("8")) {
            table.addCell("OD");
        }
        if (cdat.equalsIgnoreCase("9")) {
            table.addCell("CD");
        }
        if (deb1.equalsIgnoreCase("10")) {
            table.addCell("DE");
        }
        if (tda.equalsIgnoreCase("1")) {
            table.addCell("DA");

        }
        if (pda.equalsIgnoreCase("2")) {
            table.addCell("PD");

        }
        if (bda.equalsIgnoreCase("3")) {
            table.addCell("BD");

        }
        if (bam.equalsIgnoreCase("4")) {
            table.addCell("BA");
        }
        if (mdays.equalsIgnoreCase("5")) {
            table.addCell("MD");

        }
        if (mamo.equalsIgnoreCase("6")) {
            table.addCell("MA");

        }
        if (pam.equalsIgnoreCase("7")) {
            table.addCell("PA");

        }
        if (dayss.equalsIgnoreCase("11")) {
            table.addCell("TD");

        }if (installme1.equalsIgnoreCase("12")) {
            table.addCell("IN");

        }
        for (int i = 0; i < dateloopcount; i++) {

            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE, i);
            String day = df.format(calendar.getTime());
            Log.d("aaa", day);
            table.addCell(day);
        }
        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();

        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
        Log.d("myliss", String.valueOf(MyList1));
        for (int i = 0; i < MyList1.size(); i++) {
            name = MyList1.get(i);
            type = MyList1.get(i);
            date = MyList1.get(i);
            url = MyList1.get(i);
            price = MyList1.get(i);
            slno = MyList1.get(i);
            String namen = name.getItem_name();
            String pricen = price.getItem_price();
            String daten = date.getCreatedAt();
            String typen = type.getItem_type_code();
            String urln = url.getItem_URL();
            String sll = url.getslno();
            String oo = name.getopendate();
            String cl = name.getclosedate();
            String de = name.getdebit();
            String pa = name.getpaid();
            String ma = name.getmissamo();
            String md = name.getmissday();
            String dd = name.getdays();
            String ins = name.getInstallement();
//            Phrase one = new Phrase(timing,h);
//            table.addCell(timing1);
//            table.addCell(String.valueOf(sll));
//            table.addCell(String.valueOf(namen));
            table.addCell(getCell(String.valueOf(sll), PdfPCell.ALIGN_CENTER));
            table.addCell(getCell(String.valueOf(namen), PdfPCell.ALIGN_CENTER));
            if (odat.equalsIgnoreCase("8")) {
//                table.addCell(String.valueOf(oo));
                table.addCell(getCell(String.valueOf(oo), PdfPCell.ALIGN_CENTER));
            }
            if (cdat.equalsIgnoreCase("9")) {
//                table.addCell(String.valueOf(cl));
                table.addCell(getCell(String.valueOf(cl), PdfPCell.ALIGN_CENTER));
            }
            if (deb1.equalsIgnoreCase("10")) {
//                table.addCell(String.valueOf(de));
                table.addCell(getCell(String.valueOf(de), PdfPCell.ALIGN_CENTER));
            }
            if (tda.equalsIgnoreCase("1")) {
//                table.addCell(String.valueOf(pricen));
                table.addCell(getCell(String.valueOf(pricen), PdfPCell.ALIGN_CENTER));
            }
            if (pda.equalsIgnoreCase("2")) {
//                table.addCell(String.valueOf(urln));
                table.addCell(getCell(String.valueOf(urln), PdfPCell.ALIGN_CENTER));
            }
            if (bda.equalsIgnoreCase("3")) {
//                table.addCell(String.valueOf(typen));
                table.addCell(getCell(String.valueOf(typen), PdfPCell.ALIGN_CENTER));
            }
            if (bam.equalsIgnoreCase("4")) {
//                table.addCell(String.valueOf(daten));
                table.addCell(getCell(String.valueOf(daten), PdfPCell.ALIGN_CENTER));
            }
            if (mdays.equalsIgnoreCase("5")) {
//                table.addCell(String.valueOf(md));
                table.addCell(getCell(String.valueOf(md), PdfPCell.ALIGN_CENTER));
            }
            if (mamo.equalsIgnoreCase("6")) {
//                table.addCell(String.valueOf(ma));
                table.addCell(getCell(String.valueOf(ma), PdfPCell.ALIGN_CENTER));
            }
            if (pam.equalsIgnoreCase("7")) {
                table.addCell(getCell(String.valueOf(pa), PdfPCell.ALIGN_CENTER));

            }
            if (dayss.equalsIgnoreCase("11")) {
                table.addCell(getCell(String.valueOf(dd), PdfPCell.ALIGN_CENTER));

            }
            if (installme1.equalsIgnoreCase("12")) {
                table.addCell(getCell(String.valueOf(ins), PdfPCell.ALIGN_CENTER));

            }

            for (int j = 0; j < dateloopcount; j++) {
                table.addCell("");
            }

        }
//        System.out.println("Done");
        PdfWriter pdfWriter = PdfWriter.getInstance(document, output);
        pdfWriter.setEncryption(user_pass.getBytes(), owner_pass.getBytes(),
                PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
//        PdfWriter.getInstance(document, output);
        class RotateEvent extends PdfPageEventHelper {
            public void onStartPage(PdfWriter writer, Document document) {
                writer.addPageDictEntry(PdfName.ROTATE, PdfPage.LANDSCAPE);
            }
        }

        // Rotates each page to landscape

//        pdfWriter.setPageEvent(new RotateEvent());
//        pdfWriter.addPageDictEntry(PdfName.ROTATE, PdfPage.SEASCAPE);
        document.open();
        Log.d("sessss", timing);
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.NORMAL, BaseColor.RED);
        Font g = new Font(Font.FontFamily.TIMES_ROMAN, 15.0f, Font.NORMAL, BaseColor.RED);
        Paragraph p = new Paragraph("Date :" + " " + formattedDate + "\n", g);
        p.setAlignment(Element.ALIGN_LEFT);
        Paragraph p3 = new Paragraph("Session :" + " " + timing + "\n\n", g);
        p3.setAlignment(Element.ALIGN_LEFT);
        Paragraph p1 = new Paragraph("Company :" + " " + nme + "\n", g);
        p1.setAlignment(Element.ALIGN_CENTER);
        Paragraph p2 = new Paragraph("Location :" + " " + locc + "\n", g);
        p2.setAlignment(Element.ALIGN_CENTER);
        Paragraph p24 = new Paragraph("" + "\n");
        p24.setAlignment(Element.ALIGN_CENTER);
//        Chunk glue = new Chunk(new VerticalPositionMark());
//        Paragraph p = new Paragraph("Text to the left");
//        p.add(new Chunk(glue));
//        p.add("Text to the right");
//        document.add(p);
        PdfPTable table1 = new PdfPTable(4);
        table1 = new PdfPTable(new float[]{150, 550, 150, 150});
        table1.setWidthPercentage(100);
        table1.getDefaultCell().setFixedHeight(13);
//        table1.addCell(getCell(timing, PdfPCell.ALIGN_CENTER));
//        table1.addCell(getCell(timing , PdfPCell.ALIGN_CENTER));
//        table1.addCell(getCell(timing , PdfPCell.ALIGN_CENTER));
//        table1.addCell(getCell(timing , PdfPCell.ALIGN_CENTER));
//        table1.addCell(getCell(timing , PdfPCell.ALIGN_CENTER));
//        table1.addCell(getCell("aliasing" , PdfPCell.ALIGN_CENTER));
//        table1.addCell(getCell("இது முதல் பேரா " , PdfPCell.ALIGN_CENTER));
//        table1.addCell(getCell("இது முதல் பேரா " , PdfPCell.ALIGN_CENTER));
        table1.addCell(getCell1(" ", PdfPCell.ALIGN_CENTER));
//        table1.addCell(getCell1("Company :", PdfPCell.ALIGN_CENTER));
        table1.addCell(getCell1(nme, PdfPCell.ALIGN_CENTER));
        table1.addCell(getCell1("OD :" + " " + "Opening Date ", PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1("CD :" + " " + "Closing Date", PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1(" ", PdfPCell.ALIGN_CENTER));
        table1.addCell(getCell1("Location :" + " " + locc, PdfPCell.ALIGN_CENTER));
        table1.addCell(getCell1("TD :" + " " + "Total Days", PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1("DE :" + " " + "Debit", PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1("Date :" + " " + formattedDate, PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1(" ", PdfPCell.ALIGN_CENTER));
        table1.addCell(getCell1("PD :" + " " + "Paid Days ", PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1("PA :" + " " + "Paid Amount", PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1("Session :" + " " + timing, PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1(" ", PdfPCell.ALIGN_CENTER));
        table1.addCell(getCell1("BD :" + " " + "Balance Days", PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1("BA :" + " " + "Balance Amount", PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1(" ", PdfPCell.ALIGN_CENTER));
        table1.addCell(getCell1(" " + "\n", PdfPCell.ALIGN_CENTER));
        table1.addCell(getCell1("MD :" + " " + "Missing Days", PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1("MA :" + " " + "Missing Amount", PdfPCell.ALIGN_LEFT));
        table1.addCell(getCell1("IN :" + " " + "Installment Amount", PdfPCell.ALIGN_LEFT));

        document.add(table1);
        document.add(p24);
//        document.add(p1);
//        document.add(p2);
//        document.add(p);
//        document.add(p3);
        document.add(table);


//        for (int i = 0; i < MyList1.size(); i++) {
//            document.add(new Paragraph(String.valueOf(MyList1.get(i))));
//        }
        document.close();
//        preview = preview + 1;
//        if(preview == Names.size());
//        {
//            previewpdf();
//        }
        Log.e("pdff", String.valueOf(Names.size()));

    }

    //getCell() - change font name  , size and color and text size
    //Params - string and alignment
    //Created on 25/01/2022
    //Return - PDF cell
    public PdfPCell getCell(String text, int alignment) {
//        table.AddCell(new PdfPCell(new Phrase(yourDatabaseValue,fontH1)));
//        Typeface tf = Typeface.createFromAsset(this.getAssets(),"fonts/tamil.ttf");
        try {
//            BaseFont urName = BaseFont.createFont("res/font/tamilb.ttf", "UTF-8", BaseFont.EMBEDDED);
//            sdfer = new Font(urName, 20.0f, Font.NORMAL, BaseColor.RED);
////            String fontpath = Environment.GetEnvironmentVariable("SystemRoot") + "\\fonts\\ARIALUNI.TTF";
            BaseFont basefont = BaseFont.createFont("res/font/bamini.ttf", BaseFont.IDENTITY_H, true);
            sdfer = new Font(basefont, 20, Font.NORMAL, BaseColor.BLACK);
//            String fontName = "bamini.ttf";
//            InputStream is = context.getAssets().open(fontName);
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            int a = is.read(buffer);
//            BaseFont customFont = BaseFont.createFont(fontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, buffer, buffer);
//            sdfer = new Font(customFont, 12);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Paragraph pr1 = new Paragraph("இது முதல் பேரா", sdfer);

//        text = text.setTypeface(tf);
//         String LATIN_STRING = "a b c d e f g h i j k l m n o p q r s t u v w x y z " +
//                 "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";

//        if(text.matches("^[a-zA-Z0-9]*$ :") ){
        Log.d("uy12", text);

//        if(text.matches("^[A-Za-z0-9_./ -]+$")){
        if (text.matches(".*[a-zA-Z0-9_/]+.*")) {
            strConBamini = text;
            Log.d("uy", "uy");
            BaseFont basefont = null;
            BaseFont basefont1 = null;

            try {
                basefont = BaseFont.createFont("res/font/open_regular.ttf", BaseFont.IDENTITY_H, true);
                basefont1 = BaseFont.createFont("res/font/bamini.ttf", BaseFont.IDENTITY_H, true);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sdfer = new Font(basefont, 10, Font.BOLD, BaseColor.BLACK);
            sdfer1 = new Font(basefont1, 10, Font.BOLD, BaseColor.BLACK);
            Phrase nj = new Phrase();
            Integer er = strConBamini.length();
            for (int i = 0; i < er; i++) {
                String yty = String.valueOf(strConBamini.charAt(i));
                if (yty.matches("^[A-Za-z0-9_./ -]+$")) {
                    nj.setFont(sdfer);
                    nj.add(yty);
                    Log.d("op78", "op78");
                } else {
                    nj.setFont(sdfer1);
                    nj.add(yty);
                    Log.d("op78", "op781");
                }
            }
            Log.d("op781", String.valueOf(nj));
//            cell2 = new PdfPCell(new Phrase(strConBamini));
            cell2 = new PdfPCell();
            cell2.setPhrase(nj);
        } else {
            Log.d("uy1", "uy1");
            strConBamini = TamilUtil.convertToTamil(TamilUtil.BAMINI, text);
            BaseFont basefont = null;
            try {
                basefont = BaseFont.createFont("res/font/bamini.ttf", BaseFont.IDENTITY_H, true);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sdfer = new Font(basefont, 10, Font.BOLD, BaseColor.BLACK);
            Integer er = strConBamini.length();
            Log.d("lentgh", String.valueOf(er));
            Log.d("lentgh", strConBamini);
            cell2 = new PdfPCell(new Phrase(strConBamini, sdfer));
        }
        cell2.setPadding(0);
        cell2.setHorizontalAlignment(alignment);
        cell2.setBorder(PdfPCell.BOX);
        return cell2;
    }

    //getCell1() - change font name  , size and color and text size
    //Params - string and alignment
    //Created on 25/01/2022
    //Return - PDF cell
    public PdfPCell getCell1(String text, int alignment) {
//        table.AddCell(new PdfPCell(new Phrase(yourDatabaseValue,fontH1)));
//        Typeface tf = Typeface.createFromAsset(this.getAssets(),"fonts/tamil.ttf");
        try {
//            BaseFont urName = BaseFont.createFont("res/font/tamilb.ttf", "UTF-8", BaseFont.EMBEDDED);
//            sdfer = new Font(urName, 20.0f, Font.NORMAL, BaseColor.RED);
////            String fontpath = Environment.GetEnvironmentVariable("SystemRoot") + "\\fonts\\ARIALUNI.TTF";
            BaseFont basefont = BaseFont.createFont("res/font/bamini.ttf", BaseFont.IDENTITY_H, true);
            sdfer = new Font(basefont, 10, Font.NORMAL, BaseColor.BLUE);
//            String fontName = "bamini.ttf";
//            InputStream is = context.getAssets().open(fontName);
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            int a = is.read(buffer);
//            BaseFont customFont = BaseFont.createFont(fontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, buffer, buffer);
//            sdfer = new Font(customFont, 12);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Paragraph pr1 = new Paragraph("இது முதல் பேரா", sdfer);

//        text = text.setTypeface(tf);
//         String LATIN_STRING = "a b c d e f g h i j k l m n o p q r s t u v w x y z " +
//                 "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";

//        if(text.matches("^[a-zA-Z0-9]*$ :") ){
        Log.d("uy12", text);
        if (text.matches(".*[a-zA-Z]+.*")) {
            strConBamini = text;
            Log.d("uy", "uy");
            BaseFont basefont = null;
            try {
                basefont = BaseFont.createFont("res/font/open_regular.ttf", BaseFont.IDENTITY_H, true);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sdfer = new Font(basefont, 10, Font.BOLD, BaseColor.BLACK);
            cell2 = new PdfPCell(new Phrase(strConBamini, sdfer));
        } else {
            Log.d("uy1", "uy1");
//            text.replaceAll("[a-zA-Z]","");
            strConBamini = TamilUtil.convertToTamil(TamilUtil.BAMINI, text);
            BaseFont basefont = null;
            try {
                basefont = BaseFont.createFont("res/font/bamini.ttf", BaseFont.IDENTITY_H, true);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            Integer er = strConBamini.length();
//            Log.d("lentgh",String.valueOf(er));
            sdfer = new Font(basefont, 10, Font.NORMAL, BaseColor.BLACK);
            cell2 = new PdfPCell(new Phrase(strConBamini, sdfer));
        }
        cell2.setPadding(0);
        cell2.setHorizontalAlignment(alignment);
        cell2.setBorder(PdfPCell.NO_BORDER);
        return cell2;
    }

    //previewpdf() - Show PDF after creation
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void previewpdf(){
        PackageManager packageManager = context.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
//        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_ALL);
//        if (list.size() > 0) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
//            Uri uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", pdfFile);
        Uri uri = Uri.fromFile(pdfFile);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent intent1 = Intent.createChooser(intent, "Open File");
//            startActivity(intent);
        getApplicationContext().startActivity(intent);

//        } else {
////            Toast.makeText(context, "Download a PDF Viewer to see the generated PDF", Toast.LENGTH_SHORT).show();
////        }
    }

    //populateRecyclerView() - get all details of customers and debit order by order_id_new Ascending
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView() {
//        MyList1.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String[] columns = {"debit_amount",
                "id", ""};

        MY_QUERY = "SELECT SUM(a.collection_amount) as paid,b.location,b.customer_name,b.CID,b.id as ID,b.order_id_new,c.debit_amount,b.debit_type,c.debit_date,c.closeing_date,c.installment_amount," +
                "c.debit_days FROM  dd_customers b LEFT JOIN dd_account_debit c on b.id = c.customer_id LEFT JOIN dd_collection a on c.id =  a.debit_id " +
                "WHERE b.tracking_id = ? AND b.debit_type IN(0,1,2) AND c.active_status = 1  GROUP BY b.id ORDER BY b.order_id_new ASC";
        Cursor cursor = database.rawQuery(MY_QUERY, new String[]{String.valueOf(ses)});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                MyList1 = new ArrayList<PDF_pojo>();
                MyList1.clear();
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);
//                    nme = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("location");
//                    locc = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("ID");
                    long id = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String dbamount = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("installment_amount");
                    String installment = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_days");
                    String totaldays = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("paid");
                    String paidamount = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String debittt_tyyy = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String order = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_date");
                    String debittt_date = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("closeing_date");
                    String closing_date = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("CID");
                    String cid = cursor.getString(index);

                    if (dbamount == null) {
                        AlertDialog.Builder alertbox = new AlertDialog.Builder(Print_activity.this, R.style.AlertDialogTheme);
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
                    } else {
                        if (totaldays != null && paidamount != null) {
                            if (paidamount != null && installment != null) {
                                instamt = Integer.parseInt(installment);
                                pamount = Integer.parseInt(paidamount);
                                Integer paaiddd = pamount / instamt;
                                paiddays = (double) pamount / instamt;
                                if (String.valueOf(paiddays).equalsIgnoreCase("NaN")) {
                                    paiddays = 0.0;
                                }
//                  Log.d("paiddd", String.valueOf(instamt));
                                DecimalFormat df = new DecimalFormat("####.##");
                                paiddays = Double.valueOf(df.format(paiddays));
                                Log.d("paiddd", String.valueOf(paiddays));
                                ppd = df.format(paiddays);
                                pdays = String.valueOf(paaiddd);
//                  pdays = Integer.parseInt(ppd);
                            }
                            if (dbamount != null) {
                                debitam1 = Integer.parseInt(dbamount);
                                balanceamount = debitam1 - pamount;

//                                baldays = (double) pamount / instamt;
                            }
                            if (totaldays != null) {
                                totda = Double.parseDouble(totaldays);
                                balanceday = totda - paiddays;
                                DecimalFormat df = new DecimalFormat("####.##");
                                balanceday = Double.valueOf(df.format(balanceday));
                                pp = df.format(balanceday);
                            }
                            if (debittt_date != null) {
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                                String formattedDate = df.format(c.getTime());

                                try {
                                    Date newda = df.parse(formattedDate);
                                    Date debit = df.parse(debittt_date);
                                    long diffInMillisec = newda.getTime() - debit.getTime();
                                    long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                                    if (diffInDays == 0) {
                                        missda1 = 0.0;
                                        misam = 0;
                                        toda1 = 1;
//                                    mid.setText(String.valueOf(misda));
//                                    mama.setText("\u20B9" + " " + String.valueOf(misam));
                                    } else {
                                        Log.d("dates", String.valueOf(diffInDays));
                                        String tod = String.valueOf(diffInDays);
                                        toda = Integer.parseInt(tod);
                                        toda1 = Integer.parseInt(tod) + 1;
                                        totda = Double.parseDouble(totaldays);
                                        toda2 = Double.parseDouble(tod);
                                        missda1 = toda2 - paiddays;
                                        DecimalFormat df21 = new DecimalFormat("####.##");
                                        missda1 = Double.valueOf(df21.format(missda1));
                                        if (missda1 < 0.0) {
                                            missda1 = 0.0;
                                        }
                                        Double mm = Double.parseDouble(pdays);
//        Integer po = Integer.parseInt(pp);

                                        missiday = pdays;
                                        Integer tota = Integer.parseInt(totaldays);
                                        Double mii1 = new Double(toda);
                                        Double mii2 = new Double(pdays);
                                        misda = toda - Integer.parseInt(pdays);
                                        Double mii;
                                        mii = mii1 - mii2;
                                        Log.d("mmm21", String.valueOf(mii));
                                        if (misda >= balanceday) {
                                            misam = balanceamount;
//                                            misda = balanceday.intValue();
//                                            missed = String.valueOf(misda);
//                                            misda = Integer.parseInt(missed);
                                        } else {
                                            misam = misda * instamt;
                                            if (misam < 0) {
                                                misam = 0;
                                            }
                                        }

//            misda = pdays - toda;
//        if(misda > totaldays)
//            misam = misda * instamt;
//                                    mid.setText(String.valueOf(misda));
//                                    mama.setText("\u20B9" + " " + String.valueOf(misam));
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                try {

                                    SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
                                    SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
                                    Date debit1 = df1.parse(debittt_date);
                                    String de = df2.format(debit1);
                                    Date debit2 = df2.parse(de);
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(debit2);
//                            cal.add(Calendar.DATE,-1);
                                    int fourDigYear = cal.get(Calendar.YEAR);
                                    String yrStr = Integer.toString(fourDigYear).substring(2);
                                    int year = Integer.parseInt(yrStr);
                                    Date clo = df1.parse(closing_date);
                                    String cl = df2.format(clo);
                                    Date clo2 = df2.parse(cl);
                                    Calendar cal2 = Calendar.getInstance();
                                    cal2.setTime(clo2);
//                            cal.add(Calendar.DATE,-1);
                                    int fourDigYear1 = cal2.get(Calendar.YEAR);
                                    String yrStr1 = Integer.toString(fourDigYear1).substring(2);
                                    int year1 = Integer.parseInt(yrStr1);
                                    SimpleDateFormat df3 = new SimpleDateFormat("dd/MM");
                                    Log.d("year", String.valueOf(year));
                                    Date debit = df1.parse(debittt_date);
                                    Date debbb = df1.parse(closing_date);
                                    dated = df3.format(debit) + "/" + year;
                                    dated1 = df3.format(debbb) + "/" + year1;

//                                Log.d("deae",dated1);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            Names.add(cid + "   " + Name + "     " + totaldays + " " + ppd + " " + balanceamount + " " + pp);
                            if (orderby.equalsIgnoreCase("1")) {
                                PDF_pojo.setItem_name(Name);
                                PDF_pojo.setItem_price(totaldays);
                                PDF_pojo.setItem_URL(String.valueOf(paiddays));
                                PDF_pojo.setItem_type_code(String.valueOf(balanceday));
                                PDF_pojo.setCreatedAt(String.valueOf(balanceamount));
                                PDF_pojo.setopendate(dated);
                                PDF_pojo.setclosedate(dated1);
                                PDF_pojo.setpaid(String.valueOf(pamount));
                                PDF_pojo.setdebit(dbamount);
                                PDF_pojo.setdays(String.valueOf(toda1));
                                PDF_pojo.setmissamo(String.valueOf(misam));
                                PDF_pojo.setmissday(String.valueOf(missda1));
                                PDF_pojo.setInstallement(String.valueOf(installment));
                                if (debittt_tyyy.equalsIgnoreCase("0") || debittt_tyyy.equalsIgnoreCase("1")) {
                                    PDF_pojo.setslno(String.valueOf(cid));
                                } else if (debittt_tyyy.equalsIgnoreCase("2")) {
                                    PDF_pojo.setslno(String.valueOf(cid));
                                }
                                MyList1.add(PDF_pojo);
                                PDF_pojo = new PDF_pojo();
                                Log.d("names1", String.valueOf(Names));
                            } else if (orderby.equalsIgnoreCase("2")) {
                                Log.d("namesp", String.valueOf(pdays));
                                PDF_pojo.setItem_name(Name);
                                PDF_pojo.setItem_price(totaldays);
                                PDF_pojo.setItem_URL(String.valueOf(paiddays));
                                PDF_pojo.setItem_type_code(String.valueOf(balanceday));
                                PDF_pojo.setCreatedAt(String.valueOf(balanceamount));
                                PDF_pojo.setopendate(dated);
                                PDF_pojo.setclosedate(dated1);
                                PDF_pojo.setpaid(String.valueOf(pamount));
                                PDF_pojo.setdebit(dbamount);
                                PDF_pojo.setdays(String.valueOf(toda1));
                                PDF_pojo.setmissamo(String.valueOf(misam));
                                PDF_pojo.setmissday(String.valueOf(missda1));
                                PDF_pojo.setInstallement(String.valueOf(installment));
                                if (debittt_tyyy.equalsIgnoreCase("0") || debittt_tyyy.equalsIgnoreCase("1")) {
                                    PDF_pojo.setslno(String.valueOf(cid));
                                } else if (debittt_tyyy.equalsIgnoreCase("2")) {
                                    PDF_pojo.setslno(String.valueOf(cid));
                                }
                                MyList1.add(PDF_pojo);
                                PDF_pojo = new PDF_pojo();
                                Log.d("names2", String.valueOf(Names));
                            }

                        } else if (paidamount == null && dbamount != null) {
                            pdays = "0";
                            balanceamount = 0;
                            paidamount = "0";
                            pp = "0";
                            if (paidamount != null && installment != null) {
                                installment = "0";
                                instamt = Integer.parseInt(installment);
                                pamount = Integer.parseInt(paidamount);
                                paiddays = (double) pamount / instamt;
                                if (String.valueOf(paiddays).equalsIgnoreCase("NaN")) {
                                    paiddays = 0.0;
                                }
//                  Log.d("paiddd", String.valueOf(instamt));
                                DecimalFormat df = new DecimalFormat("####.##");
                                paiddays = Double.valueOf(df.format(paiddays));
                                Log.d("paiddd", String.valueOf(paiddays));
                                pp = df.format(paiddays);
//                  pdays = Integer.parseInt(pp);
                            }
                            if (dbamount != null) {
                                debitam1 = Integer.parseInt(dbamount);
                                balanceamount = debitam1 - pamount;
                            }
                            if (totaldays != null) {
                                totda = Double.parseDouble(totaldays);
                                balanceday = totda - paiddays;
                                DecimalFormat df = new DecimalFormat("####.##");
                                balanceday = Double.valueOf(df.format(balanceday));
                                pp = df.format(balanceday);
                                if (pp.equalsIgnoreCase("NaN")) {
                                    pp = totaldays;
                                }
                            }
                            if (debittt_date != null) {
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                                String formattedDate = df.format(c.getTime());

                                try {
                                    Date newda = df.parse(formattedDate);
                                    Date debit = df.parse(debittt_date);
                                    long diffInMillisec = newda.getTime() - debit.getTime();
                                    long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                                    if (diffInDays == 0) {
                                        missda1 = 0.0;
                                        misam = 0;
                                        toda1 = 1;
//                                    mid.setText(String.valueOf(misda));
//                                    mama.setText("\u20B9" + " " + String.valueOf(misam));
                                    } else {
                                        Log.d("dates", String.valueOf(diffInDays));
                                        String tod = String.valueOf(diffInDays);
                                        toda = Integer.parseInt(tod);
                                        toda1 = Integer.parseInt(tod) + 1;
//        Integer po = Integer.parseInt(pp);
                                        toda2 = Double.parseDouble(tod);
                                        missda1 = toda2 - paiddays;
                                        DecimalFormat df21 = new DecimalFormat("####.##");
                                        missda1 = Double.valueOf(df21.format(missda1));
                                        if (missda1 < 0.0) {
                                            missda1 = 0.0;
                                        }
                                        missiday = pdays;
                                        Integer tota = Integer.parseInt(totaldays);
                                        Double mii1 = new Double(toda);
                                        Double mii2 = new Double(pdays);
                                        misda = toda - Integer.parseInt(pdays);
                                        Double mii;
                                        mii = mii1 - mii2;
                                        Log.d("mmm", String.valueOf(mii));
                                        if (misda >= balanceday) {
                                            misam = balanceamount;
//                                            misda = balanceday.intValue();
//                                            missed = String.valueOf(misda);
//                                            misda = Integer.parseInt(missed);
                                        } else {
                                            misam = misda * instamt;
                                            if (misam < 0) {
                                                misam = 0;
                                            }
                                        }
//            misda = pdays - toda;
//        if(misda > totaldays)
//            misam = misda * instamt;
//                                    mid.setText(String.valueOf(misda));
//                                    mama.setText("\u20B9" + " " + String.valueOf(misam));
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            try {
                                SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
                                SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
                                Date debit1 = df1.parse(debittt_date);
                                String de = df2.format(debit1);
                                Date debit2 = df2.parse(de);
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(debit2);
//                            cal.add(Calendar.DATE,-1);
                                int fourDigYear = cal.get(Calendar.YEAR);
                                String yrStr = Integer.toString(fourDigYear).substring(2);
                                int year = Integer.parseInt(yrStr);
                                Date clo = df1.parse(closing_date);
                                String cl = df2.format(clo);
                                Date clo2 = df2.parse(cl);
                                Calendar cal2 = Calendar.getInstance();
                                cal2.setTime(clo2);
//                            cal.add(Calendar.DATE,-1);
                                int fourDigYear1 = cal2.get(Calendar.YEAR);
                                String yrStr1 = Integer.toString(fourDigYear1).substring(2);
                                int year1 = Integer.parseInt(yrStr1);
                                SimpleDateFormat df3 = new SimpleDateFormat("dd/MM");
                                Log.d("year", String.valueOf(year));
                                Date debit = df1.parse(debittt_date);
                                Date debbb = df1.parse(closing_date);
                                dated = df3.format(debit) + "/" + year;
                                dated1 = df3.format(debbb) + "/" + year1;
//                                Log.d("deae",dated1);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Names.add(id + "   " + Name + "     " + totaldays + " " + pdays + " " + balanceamount + " " + pp);

                            if (orderby.equalsIgnoreCase("1")) {
                                PDF_pojo.setItem_name(Name);
                                PDF_pojo.setItem_price(totaldays);
                                PDF_pojo.setItem_URL(String.valueOf(paiddays));
                                PDF_pojo.setItem_type_code(String.valueOf(balanceday));
                                PDF_pojo.setCreatedAt(String.valueOf(balanceamount));
                                PDF_pojo.setslno(cid);
                                PDF_pojo.setopendate(dated);
                                PDF_pojo.setclosedate(dated1);
                                PDF_pojo.setpaid(String.valueOf(pamount));
                                PDF_pojo.setdebit(dbamount);
                                PDF_pojo.setdays(String.valueOf(toda1));
                                PDF_pojo.setmissamo(String.valueOf(misam));
                                PDF_pojo.setmissday(String.valueOf(missda1));
                                PDF_pojo.setInstallement(String.valueOf(installment));
                                MyList1.add(PDF_pojo);
                                PDF_pojo = new PDF_pojo();
                                Log.d("names3", String.valueOf(Names));
                            } else if (orderby.equalsIgnoreCase("2")) {
                                PDF_pojo.setItem_name(Name);
                                PDF_pojo.setItem_price(totaldays);
                                PDF_pojo.setItem_URL(String.valueOf(paiddays));
                                PDF_pojo.setItem_type_code(String.valueOf(balanceday));
                                PDF_pojo.setCreatedAt(String.valueOf(balanceamount));
                                PDF_pojo.setslno(cid);
                                PDF_pojo.setopendate(dated);
                                PDF_pojo.setdays(String.valueOf(toda1));
                                PDF_pojo.setclosedate(dated1);
                                PDF_pojo.setpaid(String.valueOf(pamount));
                                PDF_pojo.setdebit(dbamount);
                                PDF_pojo.setmissamo(String.valueOf(misam));
                                PDF_pojo.setmissday(String.valueOf(missda1));
                                PDF_pojo.setInstallement(String.valueOf(installment));
                                MyList1.add(PDF_pojo);

                                PDF_pojo = new PDF_pojo();
                                Log.d("names4", String.valueOf(Names));
                            }
                        }
                    }
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            } else {
                cursor.close();
                sqlite.close();
                database.close();
            }
            if (Names.size() > 0) {
                try {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            progre();
                        }
                    });
                    createPdfWrapper();
                    previewpdf();
                    pday.setChecked(false);
                    tday.setChecked(false);
                    bday.setChecked(false);
                    bamount.setChecked(false);
                    pamountt.setChecked(false);
                    mamount.setChecked(false);
                    mday.setChecked(false);
                    deb.setChecked(false);
                    odate.setChecked(false);
                    cdate.setChecked(false);
                    horizontal.setChecked(false);
                    vertical.setChecked(false);
                    byid.setChecked(false);
                    byorder.setChecked(false);
                    days.setChecked(false);
//                    Intent yu = new Intent(Print_activity.this,Print_activity.class);
//                    startActivity(yu);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        } else {
            cursor.close();
            sqlite.close();
            database.close();
        }


    }

    //populateRecyclerView2() - get all details of customers and debit order by CID Ascending
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView2() {
//        MyList1.clear();
        Names.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String[] columns = {"debit_amount", "id", ""};

        MY_QUERY = "SELECT SUM(a.collection_amount) as paid,b.location,b.customer_name,b.CID,b.id as ID,b.order_id_new,c.debit_amount,b.debit_type,c.installment_amount,c.debit_days,c.debit_date,c.closeing_date FROM  dd_customers b " +
                " LEFT JOIN dd_account_debit c on b.id = c.customer_id LEFT JOIN dd_collection a on c.id =  a.debit_id " +
                "WHERE b.tracking_id = ? AND b.debit_type IN (0,1) AND c.active_status = 1 GROUP BY b.id ORDER BY b.CID ASC";


        Cursor cursor = database.rawQuery(MY_QUERY, new String[]{String.valueOf(ses)});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                MyList1 = new ArrayList<PDF_pojo>();
                MyList1.clear();
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);
//                    nme = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("location");
//                    locc = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("ID");
                    long id = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String dbamount = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("installment_amount");
                    String installment = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_days");
                    String totaldays = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("paid");
                    String paidamount = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String debittt_tyyy = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String order = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_date");
                    String debittt_date = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("closeing_date");
                    String closing_date = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("CID");
                    String cid = cursor.getString(index);
                    Log.d("namess12wq", cid + " " + debittt_tyyy);
                    if (dbamount == null) {
                        AlertDialog.Builder alertbox = new AlertDialog.Builder(Print_activity.this, R.style.AlertDialogTheme);
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
                    } else {
                        if (totaldays != null && paidamount != null) {
                            if (paidamount != null && installment != null) {
                                instamt = Integer.parseInt(installment);
                                pamount = Integer.parseInt(paidamount);
                                paiddays = (double) pamount / instamt;
                                if (String.valueOf(paiddays).equalsIgnoreCase("NaN")) {
                                    paiddays = 0.0;
                                }
                                Double pa = new Double(pamount);
                                Double ins = new Double(instamt);
                                Integer paaiddd = pamount / instamt;
                                paiddays = pa / ins;
                                Log.d("paiddd", String.valueOf(instamt));
//    pp = String.format("%.2f", paiddays);
                                DecimalFormat df = new DecimalFormat("####.##");
                                paiddays = Double.valueOf(df.format(paiddays));
                                pp = String.valueOf(paiddays);
                                pdays = String.valueOf(paaiddd);
//                  Log.d("paiddd", String.valueOf(instamt));
//                            DecimalFormat df = new DecimalFormat("####.##");
//                            Log.d("paiddd", String.valueOf(paiddays));
                                ppd = df.format(paiddays);
//                  pdays = Integer.parseInt(pp);
                            }
                            if (dbamount != null) {
                                debitam1 = Integer.parseInt(dbamount);
                                balanceamount = debitam1 - pamount;
                            }
                            if (totaldays != null) {
                                totda = Double.parseDouble(totaldays);
                                balanceday = totda - paiddays;
                                DecimalFormat df = new DecimalFormat("####.##");
                                balanceday = Double.valueOf(df.format(balanceday));
                                pp = df.format(balanceday);
                            }
                            if (debittt_date != null) {
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                                String formattedDate = df.format(c.getTime());
                                try {
                                    Date newda = df.parse(formattedDate);
                                    Date debit = df.parse(debittt_date);
                                    long diffInMillisec = newda.getTime() - debit.getTime();
                                    long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                                    if (diffInDays == 0) {
                                        missda1 = 0.0;
                                        misam = 0;
                                        toda1 = 1;
//                                    mid.setText(String.valueOf(misda));
//                                    mama.setText("\u20B9" + " " + String.valueOf(misam));
                                    } else {
                                        Log.d("dates", String.valueOf(diffInDays));
                                        String tod = String.valueOf(diffInDays);
                                        toda = Integer.parseInt(tod);
                                        toda1 = Integer.parseInt(tod) + 1;
//        Integer po = Integer.parseInt(pp);
                                        toda2 = Double.parseDouble(tod);
                                        Log.d("names221", String.valueOf(toda2) + " " + String.valueOf(paiddays));
                                        missda1 = toda2 - paiddays;
                                        DecimalFormat df1 = new DecimalFormat("####.##");
                                        missda1 = Double.valueOf(df1.format(missda1));
                                        if (missda1 < 0.0) {
                                            missda1 = 0.0;
                                        }
                                        missiday = pdays;
                                        Integer tota = Integer.parseInt(totaldays);
                                        Double mii1 = new Double(toda);
                                        Double mii2 = new Double(pdays);
                                        misda = toda - Integer.parseInt(pdays);
                                        Double mii;
                                        mii = mii1 - mii2;
                                        Log.d("mmm", String.valueOf(mii));
                                        if (misda >= balanceday) {
                                            misam = balanceamount;
//                                            misda = balanceday.intValue();
//                                            missed = String.valueOf(misda);
//                                            misda = Integer.parseInt(missed);
                                        } else {
                                            misam = misda * instamt;
                                            if (misam < 0) {
                                                misam = 0;
                                            }
                                        }
//            misda = pdays - toda;
//        if(misda > totaldays)
//            misam = misda * instamt;
//                                    mid.setText(String.valueOf(misda));
//                                    mama.setText("\u20B9" + " " + String.valueOf(misam));
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            try {
                                SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
                                SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
                                Date debit1 = df1.parse(debittt_date);
                                String de = df2.format(debit1);
                                Date debit2 = df2.parse(de);
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(debit2);
//                            cal.add(Calendar.DATE,-1);
                                int fourDigYear = cal.get(Calendar.YEAR);
                                String yrStr = Integer.toString(fourDigYear).substring(2);
                                int year = Integer.parseInt(yrStr);
                                Date clo = df1.parse(closing_date);
                                String cl = df2.format(clo);
                                Date clo2 = df2.parse(cl);
                                Calendar cal2 = Calendar.getInstance();
                                cal2.setTime(clo2);
//                            cal.add(Calendar.DATE,-1);
                                int fourDigYear1 = cal2.get(Calendar.YEAR);
                                String yrStr1 = Integer.toString(fourDigYear1).substring(2);
                                int year1 = Integer.parseInt(yrStr1);
                                SimpleDateFormat df3 = new SimpleDateFormat("dd/MM");
                                Log.d("year", String.valueOf(year));
                                Date debit = df1.parse(debittt_date);
                                Date debbb = df1.parse(closing_date);
                                dated = df3.format(debit) + "/" + year;
                                dated1 = df3.format(debbb) + "/" + year1;
//                                Log.d("deae",dated1);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Names.add(cid + "   " + Name + "     " + totaldays + " " + ppd + " " + balanceamount + " " + pp);
                            Log.d("namess", String.valueOf(missda1));

                            PDF_pojo.setItem_name(Name);
                            PDF_pojo.setItem_price(totaldays);
                            PDF_pojo.setItem_URL(String.valueOf(paiddays));
                            PDF_pojo.setItem_type_code(String.valueOf(balanceday));
                            PDF_pojo.setCreatedAt(String.valueOf(balanceamount));
                            PDF_pojo.setopendate(dated);
                            PDF_pojo.setclosedate(dated1);
                            PDF_pojo.setpaid(String.valueOf(pamount));
                            PDF_pojo.setdebit(dbamount);
                            PDF_pojo.setdays(String.valueOf(toda1));
                            PDF_pojo.setmissamo(String.valueOf(misam));
                            PDF_pojo.setmissday(String.valueOf(missda1));
                            PDF_pojo.setInstallement(String.valueOf(installment));
                            if (debittt_tyyy.equalsIgnoreCase("0")) {
                                PDF_pojo.setslno(String.valueOf(cid));
                            } else if (debittt_tyyy.equalsIgnoreCase("1")) {
                                PDF_pojo.setslno(String.valueOf(cid));
                            } else if (debittt_tyyy.equalsIgnoreCase("2")) {
                                PDF_pojo.setslno(String.valueOf(cid));
                            }
                            MyList1.add(PDF_pojo);

                            PDF_pojo = new PDF_pojo();
                            Log.d("names", String.valueOf(Names));

                        } else if (paidamount == null && dbamount != null) {
                            pdays = "0";
                            balanceamount = 0;
                            paidamount = "0";
                            pp = "0";
                            if (paidamount != null && installment != null) {
                                installment = "0";
                                instamt = Integer.parseInt(installment);
                                pamount = Integer.parseInt(paidamount);
                                paiddays = (double) pamount / instamt;

                                if (String.valueOf(paiddays).equalsIgnoreCase("NaN")) {
                                    paiddays = 0.0;
                                }
//                  Log.d("paiddd", String.valueOf(instamt));
                                DecimalFormat df = new DecimalFormat("####.##");
                                paiddays = Double.valueOf(df.format(paiddays));
                                Log.d("paiddd", String.valueOf(paiddays));
                                pp = df.format(paiddays);
//                  pdays = Integer.parseInt(pp);
                            }
                            if (dbamount != null) {
                                debitam1 = Integer.parseInt(dbamount);
                                balanceamount = debitam1 - pamount;
                            }
                            if (totaldays != null) {
                                totda = Double.parseDouble(totaldays);
                                balanceday = totda - paiddays;
                                DecimalFormat df = new DecimalFormat("####.##");
                                balanceday = Double.valueOf(df.format(balanceday));
                                pp = df.format(balanceday);
                                if (pp.equalsIgnoreCase("NaN")) {
                                    pp = totaldays;
                                }
                                if (debittt_date != null) {
                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
                                    String formattedDate = df1.format(c.getTime());

                                    try {
                                        Date newda = df1.parse(formattedDate);
                                        Date debit = df1.parse(debittt_date);
                                        long diffInMillisec = newda.getTime() - debit.getTime();
                                        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                                        if (diffInDays == 0) {
                                            missda1 = 0.0;
                                            misam = 0;
                                            toda1 = 1;
//                                    mid.setText(String.valueOf(misda));
//                                    mama.setText("\u20B9" + " " + String.valueOf(misam));
                                        } else {
                                            Log.d("dates", String.valueOf(diffInDays));
                                            String tod = String.valueOf(diffInDays);
                                            toda = Integer.parseInt(tod);
                                            toda1 = Integer.parseInt(tod) + 1;
//        Integer po = Integer.parseInt(pp);
                                            toda2 = Double.parseDouble(tod);
                                            missda1 = toda2 - paiddays;
                                            DecimalFormat df3 = new DecimalFormat("####.##");
                                            missda1 = Double.valueOf(df3.format(missda1));
                                            if (missda1 < 0.0) {
                                                missda1 = 0.0;
                                            }
                                            missiday = pdays;
                                            Integer tota = Integer.parseInt(totaldays);
                                            Double mii1 = new Double(toda);
                                            Double mii2 = new Double(pdays);
                                            misda = toda - Integer.parseInt(pdays);
                                            Double mii;
                                            mii = mii1 - mii2;
                                            Log.d("mmm", String.valueOf(mii));
                                            if (misda >= balanceday) {
                                                misam = balanceamount;
//                                                misda = balanceday.intValue();
//                                                missed = String.valueOf(misda);
//                                                misda = Integer.parseInt(missed);
                                            } else {
                                                misam = misda * instamt;
                                                if (misam < 0) {
                                                    misam = 0;
                                                }
                                            }
//            misda = pdays - toda;
//        if(misda > totaldays)
//            misam = misda * instamt;
//                                    mid.setText(String.valueOf(misda));
//                                    mama.setText("\u20B9" + " " + String.valueOf(misam));
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            try {
                                SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
                                SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
                                Date debit1 = df1.parse(debittt_date);
                                String de = df2.format(debit1);
                                Date debit2 = df2.parse(de);
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(debit2);
//                            cal.add(Calendar.DATE,-1);
                                int fourDigYear = cal.get(Calendar.YEAR);
                                String yrStr = Integer.toString(fourDigYear).substring(2);
                                int year = Integer.parseInt(yrStr);
                                Date clo = df1.parse(closing_date);
                                String cl = df2.format(clo);
                                Date clo2 = df2.parse(cl);
                                Calendar cal2 = Calendar.getInstance();
                                cal2.setTime(clo2);
//                            cal.add(Calendar.DATE,-1);
                                int fourDigYear1 = cal2.get(Calendar.YEAR);
                                String yrStr1 = Integer.toString(fourDigYear1).substring(2);
                                int year1 = Integer.parseInt(yrStr1);
                                SimpleDateFormat df3 = new SimpleDateFormat("dd/MM");
                                Log.d("year", String.valueOf(year));
                                Date debit = df1.parse(debittt_date);
                                Date debbb = df1.parse(closing_date);
                                dated = df3.format(debit) + "/" + year;
                                dated1 = df3.format(debbb) + "/" + year1;
//                                Log.d("deae",dated1);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Names.add(id + "   " + Name + "     " + totaldays + " " + pdays + " " + balanceamount + " " + pp);

                            PDF_pojo.setItem_name(Name);
                            PDF_pojo.setItem_price(totaldays);
                            PDF_pojo.setItem_URL(String.valueOf(paiddays));
                            PDF_pojo.setItem_type_code(String.valueOf(balanceday));
                            PDF_pojo.setCreatedAt(String.valueOf(balanceamount));
                            PDF_pojo.setopendate(dated);
                            PDF_pojo.setdays(String.valueOf(toda1));
                            PDF_pojo.setclosedate(dated1);
                            PDF_pojo.setpaid(String.valueOf(pamount));
                            PDF_pojo.setdebit(dbamount);
                            PDF_pojo.setmissamo(String.valueOf(misam));
                            PDF_pojo.setmissday(String.valueOf(missda1));
                            PDF_pojo.setInstallement(String.valueOf(installment));
                            if (debittt_tyyy.equalsIgnoreCase("0")) {
                                PDF_pojo.setslno(String.valueOf(cid));
                            } else if (debittt_tyyy.equalsIgnoreCase("1")) {
                                PDF_pojo.setslno(String.valueOf(cid));
                            } else if (debittt_tyyy.equalsIgnoreCase("2")) {
                                PDF_pojo.setslno(String.valueOf(cid));
                            }
                            MyList1.add(PDF_pojo);

                            PDF_pojo = new PDF_pojo();
                            Log.d("names", String.valueOf(Names));

                        }
                    }
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            } else {
                cursor.close();
                sqlite.close();
                database.close();
            }
        } else {
            cursor.close();
            sqlite.close();
            database.close();
        }
    }

    //populateRecyclerView3() - get all details of customers and debit order by order_id_new Descending
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView3() {
//        MyList1.clear();
//        Names.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String[] columns = {"debit_amount", "id", ""};
        String MY_QUERY1 = "SELECT cus.location,cus.customer_name,cus.CID,cus.id as ID,cus.order_id,cus.debit_type,deb.customer_id,deb.installment_amount,deb.debit_days,sum(co.collection_amount)as paid ,deb.debit_amount,deb.debit_date FROM dd_customers cus " +
                " LEFT JOIN (SELECT id,tracking_id from dd_customers WHERE debit_type = '2' GROUP BY id ) col on cus.id = col.id AND col.tracking_id = ? " +
                " LEFT JOIN (SELECT id,tracking_id from dd_customers WHERE debit_type IN (0,1) GROUP BY id ) col1 on cus.id = col1.id AND col1.tracking_id = ? " +
                " LEFT JOIN dd_collection co on co.customer_id = cus.id LEFT JOIN dd_account_debit deb on deb.customer_id = cus.id WHERE cus.tracking_id = ? GROUP BY cus.id ORDER BY cus.CID ASC";
        MY_QUERY = "SELECT SUM(a.collection_amount) as paid,b.location,b.customer_name,b.CID,b.id as ID,b.order_id_new,c.debit_amount,b.debit_type,c.installment_amount,c.debit_days,c.debit_date,c.closeing_date " +
                "FROM  dd_customers b LEFT JOIN dd_account_debit c on b.id = c.customer_id  LEFT JOIN dd_collection a on c.id =  a.debit_id WHERE b.tracking_id = ? AND b.debit_type = '2' AND c.active_status = 1  " +
                "GROUP BY b.id ORDER BY b.order_id_new ASC";
        Cursor cursor = database.rawQuery(MY_QUERY, new String[]{String.valueOf(ses)});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
//                MyList1 = new ArrayList<PDF_pojo>();
//                MyList1.clear();
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);
//                    nme = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("location");
//                    locc = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("ID");
                    long id = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String dbamount = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("installment_amount");
                    String installment = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_days");
                    String totaldays = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("paid");
                    String paidamount = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String debittt_tyyy = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String order = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_date");
                    String debittt_date = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("closeing_date");
                    String closing_date = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("CID");
                    String cid = cursor.getString(index);
                    Log.d("namess12", cid + " " + debittt_tyyy);
                    if (dbamount == null) {
                        AlertDialog.Builder alertbox = new AlertDialog.Builder(Print_activity.this, R.style.AlertDialogTheme);
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
                    } else {
                        if (totaldays != null && paidamount != null) {
                            if (paidamount != null && installment != null) {
                                instamt = Integer.parseInt(installment);
                                pamount = Integer.parseInt(paidamount);
                                paiddays = (double) pamount / instamt;
                                if (String.valueOf(paiddays).equalsIgnoreCase("NaN")) {
                                    paiddays = 0.0;
                                }
//                  Log.d("paiddd", String.valueOf(instamt));
                                DecimalFormat df = new DecimalFormat("####.##");
                                paiddays = Double.valueOf(df.format(paiddays));
                                Log.d("paiddd", String.valueOf(paiddays));
                                ppd = df.format(paiddays);
//                  pdays = Integer.parseInt(pp);
                            }
                            if (dbamount != null) {
                                debitam1 = Integer.parseInt(dbamount);
                                balanceamount = debitam1 - pamount;
                            }
                            if (totaldays != null) {
                                totda = Double.parseDouble(totaldays);
                                balanceday = totda - paiddays;
                                DecimalFormat df = new DecimalFormat("####.##");
                                balanceday = Double.valueOf(df.format(balanceday));
                                pp = df.format(balanceday);
                            }
                            if (debittt_date != null) {
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
                                String formattedDate = df1.format(c.getTime());

                                try {
                                    Date newda = df1.parse(formattedDate);
                                    Date debit = df1.parse(debittt_date);
                                    long diffInMillisec = newda.getTime() - debit.getTime();
                                    long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                                    if (diffInDays == 0) {
                                        missda1 = 0.0;
                                        misam = 0;
                                        toda1 = 1;
//                                    mid.setText(String.valueOf(misda));
//                                    mama.setText("\u20B9" + " " + String.valueOf(misam));
                                    } else {
                                        Log.d("dates", String.valueOf(diffInDays));
                                        String tod = String.valueOf(diffInDays);
                                        toda = Integer.parseInt(tod);
                                        toda1 = Integer.parseInt(tod) + 1;
//        Integer po = Integer.parseInt(pp);
                                        toda2 = Double.parseDouble(tod);
                                        missda1 = toda2 - paiddays;
                                        DecimalFormat df = new DecimalFormat("####.##");
                                        missda1 = Double.valueOf(df.format(missda1));
                                        if (missda1 < 0.0) {
                                            missda1 = 0.0;
                                        }
                                        missiday = pdays;
                                        Integer tota = Integer.parseInt(totaldays);
                                        Double mii1 = new Double(toda);
                                        Double mii2 = new Double(pdays);
                                        misda = toda - Integer.parseInt(pdays);
                                        Double mii;
                                        mii = mii1 - mii2;
                                        Log.d("mmm", String.valueOf(mii));
                                        if (misda >= balanceday) {
                                            misam = balanceamount;
//                                            misda = balanceday.intValue();
//                                            missed = String.valueOf(misda);
//                                            misda = Integer.parseInt(missed);
                                        } else {
                                            misam = misda * instamt;
                                            if (misam < 0) {
                                                misam = 0;
                                            }
                                        }
//            misda = pdays - toda;
//        if(misda > totaldays)
//            misam = misda * instamt;
//                                    mid.setText(String.valueOf(misda));
//                                    mama.setText("\u20B9" + " " + String.valueOf(misam));
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            try {
                                SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
                                SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
                                Date debit1 = df1.parse(debittt_date);
                                String de = df2.format(debit1);
                                Date debit2 = df2.parse(de);
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(debit2);
//                            cal.add(Calendar.DATE,-1);
                                int fourDigYear = cal.get(Calendar.YEAR);
                                String yrStr = Integer.toString(fourDigYear).substring(2);
                                int year = Integer.parseInt(yrStr);
                                Date clo = df1.parse(closing_date);
                                String cl = df2.format(clo);
                                Date clo2 = df2.parse(cl);
                                Calendar cal2 = Calendar.getInstance();
                                cal2.setTime(clo2);
//                            cal.add(Calendar.DATE,-1);
                                int fourDigYear1 = cal2.get(Calendar.YEAR);
                                String yrStr1 = Integer.toString(fourDigYear1).substring(2);
                                int year1 = Integer.parseInt(yrStr1);
                                SimpleDateFormat df3 = new SimpleDateFormat("dd/MM");
                                Log.d("year", String.valueOf(year));
                                Date debit = df1.parse(debittt_date);
                                Date debbb = df1.parse(closing_date);
                                dated = df3.format(debit) + "/" + year;
                                dated1 = df3.format(debbb) + "/" + year1;
//                                Log.d("deae",dated1);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Names.add(cid + "   " + Name + "     " + totaldays + " " + ppd + " " + balanceamount + " " + pp);
                            Log.d("namess1212", String.valueOf(Names));

                            PDF_pojo.setItem_name(Name);
                            PDF_pojo.setItem_price(totaldays);
                            PDF_pojo.setItem_URL(String.valueOf(paiddays));
                            PDF_pojo.setItem_type_code(String.valueOf(balanceday));
                            PDF_pojo.setCreatedAt(String.valueOf(balanceamount));
                            PDF_pojo.setopendate(dated);
                            PDF_pojo.setclosedate(dated1);
                            PDF_pojo.setpaid(String.valueOf(pamount));
                            PDF_pojo.setdebit(dbamount);
                            PDF_pojo.setdays(String.valueOf(toda1));
                            PDF_pojo.setmissamo(String.valueOf(misam));
                            PDF_pojo.setmissday(String.valueOf(missda1));
                            PDF_pojo.setslno(String.valueOf(cid));
                            PDF_pojo.setInstallement(String.valueOf(installment));
                            MyList1.add(PDF_pojo);
                            PDF_pojo = new PDF_pojo();

                            Log.d("mylist1", String.valueOf(MyList1));
                        } else if (paidamount == null && dbamount != null) {
                            pdays = "0";
                            balanceamount = 0;
                            paidamount = "0";
                            pp = "0";
                            if (paidamount != null && installment != null) {
                                installment = "0";
                                instamt = Integer.parseInt(installment);
                                pamount = Integer.parseInt(paidamount);
                                paiddays = (double) pamount / instamt;
                                if (String.valueOf(paiddays).equalsIgnoreCase("NaN")) {
                                    paiddays = 0.0;
                                }
//                  Log.d("paiddd", String.valueOf(instamt));
                                DecimalFormat df = new DecimalFormat("####.##");
                                Log.d("paiddd", String.valueOf(paiddays));
                                pp = df.format(paiddays);
//                  pdays = Integer.parseInt(pp);
                            }
                            if (dbamount != null) {
                                debitam1 = Integer.parseInt(dbamount);
                                balanceamount = debitam1 - pamount;
                            }
                            if (totaldays != null) {
                                totda = Double.parseDouble(totaldays);
                                balanceday = totda - paiddays;
                                DecimalFormat df = new DecimalFormat("####.##");
                                balanceday = Double.valueOf(df.format(balanceday));
                                paiddays = Double.valueOf(df.format(paiddays));
                                pp = df.format(balanceday);
                                if (pp.equalsIgnoreCase("NaN")) {
                                    pp = totaldays;
                                }
                            }
                            if (debittt_date != null) {
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
                                String formattedDate = df1.format(c.getTime());

                                try {
                                    Date newda = df1.parse(formattedDate);
                                    Date debit = df1.parse(debittt_date);
                                    long diffInMillisec = newda.getTime() - debit.getTime();
                                    long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                                    if (diffInDays == 0) {
                                        missda1 = 0.0;
                                        misam = 0;
                                        toda1 = 1;
//                                    mid.setText(String.valueOf(misda));
//                                    mama.setText("\u20B9" + " " + String.valueOf(misam));
                                    } else {
                                        Log.d("dates", String.valueOf(diffInDays));
                                        String tod = String.valueOf(diffInDays);
                                        toda = Integer.parseInt(tod);
                                        toda1 = Integer.parseInt(tod) + 1;
//        Integer po = Integer.parseInt(pp);
                                        toda2 = Double.parseDouble(tod);
                                        missda1 = toda2 - paiddays;
                                        DecimalFormat df = new DecimalFormat("####.##");
                                        missda1 = Double.valueOf(df.format(missda1));
                                        if (missda1 < 0.0) {
                                            missda1 = 0.0;
                                        }
                                        missiday = pdays;
                                        Integer tota = Integer.parseInt(totaldays);
                                        Double mii1 = new Double(toda);
                                        Double mii2 = new Double(pdays);
                                        misda = toda - Integer.parseInt(pdays);
                                        Double mii;
                                        mii = mii1 - mii2;
                                        Log.d("mmm", String.valueOf(mii));
                                        if (misda >= balanceday) {
                                            misam = balanceamount;
//                                            misda = balanceday.intValue();
//                                            missed = String.valueOf(misda);
//                                            misda = Integer.parseInt(missed);
                                        } else {
                                            misam = misda * instamt;
                                            if (misam < 0) {
                                                misam = 0;
                                            }
                                        }
//            misda = pdays - toda;
//        if(misda > totaldays)
//            misam = misda * instamt;
//                                    mid.setText(String.valueOf(misda));
//                                    mama.setText("\u20B9" + " " + String.valueOf(misam));
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            try {
                                SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
                                SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
                                Date debit1 = df1.parse(debittt_date);
                                String de = df2.format(debit1);
                                Date debit2 = df2.parse(de);
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(debit2);
//                            cal.add(Calendar.DATE,-1);
                                int fourDigYear = cal.get(Calendar.YEAR);
                                String yrStr = Integer.toString(fourDigYear).substring(2);
                                int year = Integer.parseInt(yrStr);
                                Date clo = df1.parse(closing_date);
                                String cl = df2.format(clo);
                                Date clo2 = df2.parse(cl);
                                Calendar cal2 = Calendar.getInstance();
                                cal2.setTime(clo2);
//                            cal.add(Calendar.DATE,-1);
                                int fourDigYear1 = cal2.get(Calendar.YEAR);
                                String yrStr1 = Integer.toString(fourDigYear1).substring(2);
                                int year1 = Integer.parseInt(yrStr1);
                                SimpleDateFormat df3 = new SimpleDateFormat("dd/MM");
                                Log.d("year", String.valueOf(year));
                                Date debit = df1.parse(debittt_date);
                                Date debbb = df1.parse(closing_date);
                                dated = df3.format(debit) + "/" + year;
                                dated1 = df3.format(debbb) + "/" + year1;
//                                Log.d("deae",dated1);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Names.add(id + "   " + Name + "     " + totaldays + " " + pdays + " " + balanceamount + " " + pp);


                            PDF_pojo.setItem_name(Name);
                            PDF_pojo.setItem_price(totaldays);
                            PDF_pojo.setItem_URL(String.valueOf(paiddays));
                            PDF_pojo.setItem_type_code(String.valueOf(balanceday));
                            PDF_pojo.setCreatedAt(String.valueOf(balanceamount));
                            PDF_pojo.setslno(String.valueOf(cid));
                            PDF_pojo.setopendate(dated);
                            PDF_pojo.setclosedate(dated1);
                            PDF_pojo.setdays(String.valueOf(toda1));
                            PDF_pojo.setpaid(String.valueOf(pamount));
                            PDF_pojo.setdebit(dbamount);
                            PDF_pojo.setmissamo(String.valueOf(misam));
                            PDF_pojo.setmissday(String.valueOf(missda1));
                            PDF_pojo.setInstallement(String.valueOf(installment));
                            MyList1.add(PDF_pojo);
                            PDF_pojo = new PDF_pojo();

                        }
                    }
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            } else {
                cursor.close();
                sqlite.close();
                database.close();
            }

            if (Names.size() > 0) {
                try {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            progre();
                        }
                    });
                    createPdfWrapper();
                    previewpdf();
//                    CheckBox pday, tday, bday, bamount ,pamountt, mamount , mday, deb ,odate , cdate;
//                    RadioButton horizontal,vertical,byid,byorder;
                    pday.setChecked(false);
                    tday.setChecked(false);
                    bday.setChecked(false);
                    bamount.setChecked(false);
                    pamountt.setChecked(false);
                    mamount.setChecked(false);
                    mday.setChecked(false);
                    deb.setChecked(false);
                    odate.setChecked(false);
                    cdate.setChecked(false);
                    horizontal.setChecked(false);
                    vertical.setChecked(false);
                    byid.setChecked(false);
                    byorder.setChecked(false);
//                    Intent yu = new Intent(Print_activity.this,Print_activity.class);
//                    startActivity(yu);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        } else {
            cursor.close();
            sqlite.close();
            database.close();
        }


    }

    //populateRecyclerView4() - get all settings details
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView4() {
//        Names.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY = "SELECT * FROM dd_collection where id = ?";
        String whereClause = "ID=?";
        String[] whereArgs = new String[]{"1"};
//        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{idd});
        String[] columns = {"license",
                "cc", "ID", "email", "expiry", "language", "password", "pdf_password"};
//        String order = "collection_date";
        Cursor cursor = database.query("dd_settings",
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("license");
                    String lice = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("ID");
                    String iid = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("cc");
                    String emacc = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("email");
                    String ema = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("expiry");
                    String exp = cursor.getString(index);
//                    index = cursor.getColumnIndexOrThrow("IMEI");
//                    String exp1 = cursor.getString(index);
//                    index = cursor.getColumnIndexOrThrow("newws");
//                    Integer rtr = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("language");
                    String lann = cursor.getString(index);
//                    Log.d("opop",exp1);
                    index = cursor.getColumnIndexOrThrow("pdf_password");
                    String pdfpp = cursor.getString(index);
                    pdfpass123 = cursor.getString(index);
                    user_pass = cursor.getString(index);
                    if (user_pass == null || user_pass.equalsIgnoreCase("")) {
                        user_pass = "rampit@1";
                    }
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            progre();
                        }
                    });
                    Log.d("poiiuiy", user_pass);
                    if (pdfpp == null) {
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
