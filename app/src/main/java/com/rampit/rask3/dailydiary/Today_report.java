package com.rampit.rask3.dailydiary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
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
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.mail.Session;



//Updated on 25/01/2022 by RAMPIT
//Shows an days total report
//InternetConnection1() - Check whether internet is on or not
//progressbar_load() - Load progressbar
//progre() - Stop progressbar
//populateRecyclerView4() - get Settings details
//sendEmail() - Send email
//sendEmail1() - Send email
//backupDatabase() - Backup current database
//createPdfWrapper() - Check for read and writes permission
//createPdf() - Create PDF
//createPdf1() - Create PDF
//getCell1() - Change text size , color and font and alignment
//getCell12() - Change text size , color and font and alignment
//getCell11() - Change text size , color and font and alignment
//zero1() - get tally details
//populateRecyclerView41() - get account details
//sumcollection1() - get collection amount
//updateLabel() - Update textview when date is selected
//updateLabel1() - Update textview when date is selected
//closed() - get closed debit
//closeddiscount() - get closed discount
//beforebal() - get before balance
//populateRecyclerView2() - get final balance
//populateRecyclerView() - get final balance
//populateRecyclerView1() - get final balance
//current_balance() - get current balance
//nip_balance() - get NIP balance
//nipnip_balance() - get NIPNIP balance
//list1() - Show all balance details
//onBackPressed() - function called when back button is pressed
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed
//onNavigationItemSelected() - when navigation item pressed


public class Today_report extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "PdfCreatorActivity";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    private File pdfFile;
    ArrayList<Daily_POJO> MyList1;
    List<String> list;
    View vv,vv1;
    Daily_POJO Daily_POJO;
    Context context;
    Daily_POJO name;
    Daily_POJO price;
    Daily_POJO url;
    Daily_POJO type;
    Daily_POJO date;
    Daily_POJO slno;
    PdfPTable table,table1,table2,table3;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    RecyclerView recyclerView;
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> Names1 = new ArrayList<>();
    ArrayList<String> collection = new ArrayList<>();
    ArrayList<String> tally = new ArrayList<>();
    ArrayList<Long> ID = new ArrayList<>();
    ArrayList<Long> paid_cust = new ArrayList<>();
    ArrayList<Long> balance_cust = new ArrayList<>();
    public static String TABLE_NAME ="dd_collection";
    TextView tot,ppp,bbb,bef,tc,nic,ninic,totc,dbb,dcc,intt,oth,hg,lw,ex,tb,newcre,curball,nipbal,nipnipbal,totbal,todnip,todnipnip,discount,totcb,clocol,view_note;
    EditText dat,todate;
    Button srch,backupp;
    Calendar myCalendar,myCalendar1;
    String dateString,yesterdaybal,datedd,todateee,yesterdate,size,bsize,psize,fdate,tdate,pdfdate,user_pass,owner_pass,todaaaa,tod1
            ,outFileName1,currentb,nipb,nipnipb,totalb,disc1,moven,movenn,acda;
    SimpleDateFormat sdf1;
    String ss,zero,one,two,three,four,five,six,seven,eight,nine,ten,pdfname,balat,timing,xcol,strConBamini,nme,formattedDate;
    NavigationView navigationView;
    Menu nav_Menu;
    Session session;
    Integer ses,befo,newc,curc,nipc,nipnipc,dab,totdab,movenipnpi,selectnipnip,movedniip,lin1,lin2,lin3,disccc,iidd,cloosed,vd,dabamo,colllectod,closeddisco,disc11;
    SharedPreferences.Editor editor;
    SharedPreferences pref ;
    LinearLayout lll,li1,li2,maill;
    Button logou,profil;
    DatePickerDialog datePickerDialog,datePickerDialog1;
    Font sdfer,sdfer1;
    PdfPCell cell2;
    Dialog dialog;
    Integer showinng = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
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
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        String localeName = pref.getString("language","");
        if(localeName == null){
            localeName = "ta";
        }
        dabamo =0 ;
        closeddisco = 0;
        disc11 = 0;
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        dialog = new Dialog(Today_report.this,R.style.AlertDialogTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_today_report);
        setTitle(R.string.title_activity_today_report);
        ((Callback)getApplication()).datee();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        ActivityCompat.requestPermissions(Today_report.this,new String[]{Manifest.permission.INTERNET} ,1);
        ActivityCompat.requestPermissions(Today_report.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} ,1);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        editor = pref.edit();
        disccc = 0 ;
        befo =0;
        lll = (findViewById(R.id.linee));
        TextView pro = lll.findViewById(R.id.pro);
        clocol = findViewById(R.id.clocol);
        logou = lll.findViewById(R.id.logout);
        profil = lll.findViewById(R.id.profile);
        zero = pref.getString("company","");
        one = pref.getString("user1","");
        two = pref.getString("debit","");
        three = pref.getString("collection","");
        four = pref.getString("account","");
        five = pref.getString("reports","");
        six = pref.getString("customer","");
        seven = pref.getString("settings","");
        eight = pref.getString("tally","");
        nine = pref.getString("employee_details","");
        ten = pref.getString("today_report","");
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        nav_Menu = navigationView.getMenu();
        view_note = findViewById(R.id.view);
        if(zero.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.company).setVisible(false);
        }
        if(one.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.user).setVisible(false);
        }
        if(two.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.debit).setVisible(false);
        }
        if(three.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.collection).setVisible(false);
        }
        if(four.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.account).setVisible(false);
        }
        if(five.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.report).setVisible(false);
        }
        if(six.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.customer).setVisible(false);
        }
        if(seven.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.settings).setVisible(false);
            pro.setVisibility(View.GONE);
            profil.setVisibility(View.GONE);
        }
        if(eight.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.tally).setVisible(false);
        }
        if(nine.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.employee).setVisible(false);
        }
        if(ten.equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.today).setVisible(false);
        }
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
                            if (InternetConnection1.checkConnection(getApplicationContext(),Today_report.this)) {
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
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        formattedDate = df.format(c.getTime());
        ((Callback)getApplication()).closed(ses);
        ((Callback)getApplication()).closed1(formattedDate, String.valueOf(ses));
        ((Callback)getApplication()).beforebal(formattedDate, String.valueOf(ses));
        ((Callback)getApplication()).populateRecyclerView23(formattedDate, formattedDate ,String.valueOf(ses));
        context = this;
        curball = findViewById(R.id.curbal);
        nipbal = findViewById(R.id.nipbal);
        nipnipbal = findViewById(R.id.nipnipbal);
        totbal = findViewById(R.id.totbal);
        todnip = findViewById(R.id.todnip);
        todnipnip = findViewById(R.id.todnipnip);
        discount = findViewById(R.id.discount);
        totcb = findViewById(R.id.totcolbal);
        li1 = findViewById(R.id.li1);
        li2 = findViewById(R.id.li2);
        vv = findViewById(R.id.vi3);
        vv1 = findViewById(R.id.vi4);
//        lll = (findViewById(R.id.linee));
//        logou = lll.findViewById(R.id.logout);
//        profil = lll.findViewById(R.id.profile);
        Button theme = lll.findViewById(R.id.theme);
        if(in == 0){
            logou.setBackgroundResource(R.drawable.logout);
            profil.setBackgroundResource(R.drawable.user1);
            theme.setBackgroundResource(R.drawable.theme);
        }else{
            logou.setBackgroundResource(R.drawable.logoutblue);
            profil.setBackgroundResource(R.drawable.user_blue);
            theme.setBackgroundResource(R.drawable.theme1);
        }
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Today_report.this,Settings_activity.class);
                startActivity(i);
                finish();
            }
        });
        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String err = pref.getString("theme","");
                if(err.equalsIgnoreCase("")){
                    err = "1";
                }
                Integer trt = Integer.parseInt(err);
                if(trt.equals(0)){
                    editor.putString("theme","1");
                    editor.apply();
                    Intent i = new Intent(Today_report.this,NavigationActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    editor.putString("theme","0");
                    editor.apply();
                    Intent i = new Intent(Today_report.this,NavigationActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
        logou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Today_report.this,R.style.AlertDialogTheme);
                String logmsg = getString(R.string.logout_alert);
                String cann = getString(R.string.cancel);
                String warr = getString(R.string.warning);
                String logo = getString(R.string.Logout);
                alertbox.setMessage(logmsg);
                alertbox.setTitle(warr);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setNeutralButton(cann,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {

                            }
                        });
                alertbox.setPositiveButton(logo,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                editor.clear();
                                editor.putInt("isloginn",0);
                                editor.commit();
                                Intent i = new Intent(Today_report.this,Splash.class);
                                startActivity(i);
                                finish();
                            }
                        });
                alertbox.show();
            }
        });
        user_pass = "rampit";
        owner_pass = "rampit";
        Daily_POJO = new Daily_POJO();
        srch = findViewById(R.id.searchh);
        maill = findViewById(R.id.mail);
        backupp = findViewById(R.id.backup);
        tot = findViewById(R.id.total);
        ppp = findViewById(R.id.pid);
        bbb = findViewById(R.id.bal);
        newcre = findViewById(R.id.credit);
        bef = findViewById(R.id.beforee);
        tc = findViewById(R.id.today);
        nic = findViewById(R.id.nip);
        ninic = findViewById(R.id.nipnip);
        totc = findViewById(R.id.totalc);
        dbb = findViewById(R.id.debit);
        dcc = findViewById(R.id.docuu);
        intt = findViewById(R.id.interest);
        oth = findViewById(R.id.other);
        hg = findViewById(R.id.high);
        lw = findViewById(R.id.low);
        ex = findViewById(R.id.expense);
        tb = findViewById(R.id.totalb);
        dat = findViewById(R.id.date);
        todate = findViewById(R.id.todate);
        todate.setEnabled(false);
        myCalendar = Calendar.getInstance();
        myCalendar1 = Calendar.getInstance();
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
        String myFormat1 = "dd/MM/yyyy";//In which you need put here
        sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        c = Calendar.getInstance();
        todaaaa = df.format(c.getTime());
        tod1 = sdf1.format(c.getTime());
        todateee = df.format(c.getTime());

//        String formattedDate = df.format(c.getTime());
//        try {
//            Date debit = df.parse(formattedDate);
//            datedd = sdf1.format(debit);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        dat.setText(datedd);
//        dateString = formattedDate;
        String sesid = pref.getString("id","");
         iidd = Integer.parseInt(sesid);
        if(iidd > 1){
            li1.setVisibility(View.GONE);
            li2.setVisibility(View.GONE);
            vv.setVisibility(View.GONE);
            vv1.setVisibility(View.GONE);
        }
        ses = pref.getInt("session", 1);
        nme = pref.getString("NAME","");
        String dd = pref.getString("Date","");
        String dsdsd = pref.getString("NAME","");
        if(ses == 1){
            ss = getString(R.string.morning);
        }else if(ses == 2){
            ss = getString(R.string.evening);
        }
        TextView dateee = findViewById(R.id.da);
        TextView session = findViewById(R.id.sess);
        ImageView seesim = findViewById(R.id.sesimg);
        dateee.setText(dd);
        if(in == 0){
            if(ses == 1){
                timing = getString(R.string.morning);
                seesim.setBackgroundResource(R.drawable.sun);
            }else if(ses == 2){
                timing = getString(R.string.evening);
                seesim.setBackgroundResource(R.drawable.moon);
            }
        }
        else{
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
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        TextView nn = headerView.findViewById(R.id.name);
        TextView nnn = headerView.findViewById(R.id.date);
        TextView nnnn = headerView.findViewById(R.id.session);
        LinearLayout wq = headerView.findViewById(R.id.navdash);
        ImageView logy = headerView.findViewById(R.id.logy);
        if(in == 0){
            logy.setBackgroundResource(R.drawable.logout);
        }else{
            logy.setBackgroundResource(R.drawable.logoutblue);
        }
        logy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Today_report.this,R.style.AlertDialogTheme);
                String logmsg = getString(R.string.logout_alert);
                String cann = getString(R.string.cancel);
                String warr = getString(R.string.warning);
                String logo = getString(R.string.Logout);
                alertbox.setMessage(logmsg);
                alertbox.setTitle(warr);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setNeutralButton(cann,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                            }
                        });
                alertbox.setPositiveButton(logo,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                editor.clear();
                                editor.putInt("isloginn",0);
                                editor.commit();
                                Intent i = new Intent(Today_report.this,Splash.class);
                                startActivity(i);
                                finish();
                            }
                        });
                alertbox.show();
            }
        });
        wq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent qq = new Intent(Today_report.this,NavigationActivity.class);
                startActivity(qq);
                finish();
            }
        });

        nn.setText(dsdsd);
        nnn.setText("Date :"+" "+dd);
        nnnn.setText("Session :"+" "+ss);
        ((Callback)getApplication()).NIP(ses);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
         balat = pref.getString("totalbal","");
         String  balat1212 = pref.getString("totalbal","");
         Log.d("tot_bal_tot",String.valueOf(balat1212));
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
                    datePickerDialog =  new DatePickerDialog(Today_report.this,R.style.DialogTheme, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH ));
                }else{
                    datePickerDialog =  new DatePickerDialog(Today_report.this,R.style.DialogTheme1, date, myCalendar
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
        todate.setText(tod1);
        todate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (in == 0){
                    datePickerDialog1 =  new DatePickerDialog(Today_report.this,R.style.DialogTheme, date1, myCalendar1
                            .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                            myCalendar1.get(Calendar.DAY_OF_MONTH ));
                }else{
                    datePickerDialog1 =  new DatePickerDialog(Today_report.this,R.style.DialogTheme1, date1, myCalendar1
                            .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                            myCalendar1.get(Calendar.DAY_OF_MONTH ));
                }
                datePickerDialog1.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog1.show();
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
//        try {

//            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//            Date debit = df.parse(dateString);
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(debit);
//            cal.add(Calendar.DATE,-1);
//            yesterdate = df.format(cal.getTime());
            if(dateString != null && todateee != null){
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                Date newda = null;
                try {
                    newda = df.parse(dateString);
                    Date debit = df.parse(todateee);
                    long diffInMillisec =  newda.getTime() - debit.getTime();
                    long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                    Log.d("day5",String.valueOf(diffInDays));
                    if(diffInDays > 0){
                        AlertDialog.Builder alertbox = new AlertDialog.Builder(Today_report.this,R.style.AlertDialogTheme);
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
                                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                                        balat = pref.getString("totalbal","");
                                        totbal.setText("\u20B9"+""+String.valueOf(balat));
                                        list1();
                                        current_balance();
                                        nip_balance();
                                        closeddiscount();
                                        nipnip_balance();
                                        closed();
                                        beforebal();
                                        populateRecyclerView1();
                                        populateRecyclerView2();
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
//                populateRecyclerView();
            }else{
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Today_report.this,R.style.AlertDialogTheme);
                String logmsg = getString(R.string.enterdate);
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

//                                Intent i = new Intent(Today_report.this,Settings_activity.class);
//                                startActivity(i);
                            }
                        });
                alertbox.show();
            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

    }
});
        backupp.setOnClickListener(new View.OnClickListener() {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {

//        database_backup();
            backupDatabase();
    }
});
        populateRecyclerView4();
        maill.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//        try {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(dateString == null || dateString.equalsIgnoreCase("") || todateee == null || todateee.equalsIgnoreCase("")){
                            Calendar cc = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                            dateString = df.format(cc.getTime());
                            todateee = df.format(cc.getTime());
                            dat.setText(sdf1.format(cc.getTime()));
                        }
                        else{

                        }
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                        balat = pref.getString("totalbal","");
                        totbal.setText("\u20B9"+""+String.valueOf(balat));
                        list1();
                        zero1();
                        sumcollection1();
                        populateRecyclerView41();
                        current_balance();
                        nip_balance();
                        closeddiscount();
                        nipnip_balance();
                        closed();
                        beforebal();
                        populateRecyclerView1();
                        populateRecyclerView2();
                        if(MyList1.size()<=0){

                        }else {
                            if (confirm_OTP.InternetConnection.checkConnection(getApplicationContext())) {
                                // Internet Available...
                                Log.d("internet","on");
//            date32();
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                                sendEmail();
                            } else {
                                // Internet Not Available...
                                Log.d("internet","off");
                                final AlertDialog.Builder alertbox = new AlertDialog.Builder(Today_report.this,R.style.AlertDialogTheme);
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
                                alertbox.show();

                            }

                        }
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

//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        final String username = "diary.weekly.daily@gmail.com";
//        final String passwor = "rampit@1";
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//
//        session = Session.getInstance(props,
//                new javax.mail.Authenticator() {
//                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
//                        return new javax.mail.PasswordAuthentication(username, passwor);
//                    }
//                });
//        smtp3();
//        try {
//            createPdfWrapper();
////            email();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
    }
});
    }
    //InternetConnection1() - Check whether internet is on or not
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public static class InternetConnection1 {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context, final Today_report account) {
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

    //populateRecyclerView4() - get Settings details
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView4() {
//        Names.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY = "SELECT * FROM dd_collection where id = ?";
        String whereClause = "ID=?";
        String[] whereArgs = new String[] {"1"};
//        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{idd});
        String[] columns = {"license",
                "cc","ID","email","expiry","language","password","pdf_password"};
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
                    index = cursor.getColumnIndexOrThrow("license");
                    String lice = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("ID");
                    String iid = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("cc");
                    String  emacc = cursor.getString(index);
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
//                    pdfpass123 = cursor.getString(index);
                    user_pass = cursor.getString(index);
                    if(user_pass == null || user_pass.equalsIgnoreCase("")){
                        user_pass = "rampit@1";
                    }
                    Log.d("poiiuiy",user_pass);
                    if(pdfpp == null){
                    }
                }
                while (cursor.moveToNext());
            }
        }
    }

    //sendEmail() - Send email
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void sendEmail() {
        //Getting content for email
//        String email = "abilashmohanan38@gmail.com";
//        String email = "diary.weekly.daily@gmail.com";
        String email = pref.getString("email","");
        String emai2 = pref.getString("emailcc","");
        String subject = "Subject";
        String message = "hiiiiiiiii";

        //Creating SendMail object
        Log.d("pdffile", String.valueOf(pdfFile));
        if(email.equalsIgnoreCase("") || (emai2.equalsIgnoreCase(""))){
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Today_report.this,R.style.AlertDialogTheme);
            String logmsg = getString(R.string.enteremail1);
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

                            Intent i = new Intent(Today_report.this,Settings_activity.class);
                            startActivity(i);
                            finish();
                        }
                    });
            alertbox.show();
        }else {
            try {
                createPdfWrapper();
                SendMail sm = new SendMail(this, email, emai2, subject, message, pdfFile, pdfname);

                //Executing sendmail to send email
                sm.execute();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        }

    //sendEmail1() - Send email
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void sendEmail1() {
        //Getting content for email
//        String email = "abilashmohanan38@gmail.com";
//        String email = "diary.weekly.daily@gmail.com";
        String email = pref.getString("email","");
        String subject = "Subject";
        String message = "hiiiiiiiii";

        //Creating SendMail object
        Log.d("pdffile", String.valueOf(pdfFile));
        String ppp = String.valueOf(pdfFile);
        if(email.equalsIgnoreCase("")){
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Today_report.this,R.style.AlertDialogTheme);
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

                            Intent i = new Intent(Today_report.this,Settings_activity.class);
                            startActivity(i);
                            finish();
                        }
                    });
            alertbox.show();
        }else {
            SendMail1 sm = new SendMail1(this, email, subject, message, ppp, pdfname);

            //Executing sendmail to send email
            sm.execute();
        }
    }

    //backupDatabase() - Backup current database
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
        File Directory = new File("/sdcard/DailyDiary_downloads/");
        Directory.mkdirs();
        String currentDBPath = "/data/"+ "com.rampit.rask3.dailydiary" +"/databases/LOGIN";
        String backupDBPath = "/Dailydiary_downloads/DD_RRP"+"-"+todaaaa+"_Backup_Mail";
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
////        final String inFileName =getApplicationContext().getDatabasePath("LOGIN").getPath();
//        final String inFileName ="/data/user/0/com.rampit.rask3.dailydiary/databases/LOGIN";
////        String inFileName = "/data/data/com.myapp.main/databases/MYDB";
//        File dbFile = new File(inFileName);
//        FileInputStream fis = new FileInputStream(dbFile);
//
//        String outFileName = Environment.getExternalStorageDirectory()+"/LOGIN";
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
        } else {
            // Internet Not Available...
            Log.d("internet","off");
            final AlertDialog.Builder alertbox = new AlertDialog.Builder(Today_report.this,R.style.AlertDialogTheme);
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
            alertbox.show();

        }

    }

    //createPdfWrapper() - Check for read and writes permission
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void createPdfWrapper() throws FileNotFoundException, DocumentException {

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Today_report.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
//                    showMessageOKCancel("You need to allow access to Storage",
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                                REQUEST_CODE_ASK_PERMISSIONS);
//                                    }
//                                }
//                            });
//                    return;
//                }
//                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                        REQUEST_CODE_ASK_PERMISSIONS);
//            }
//            return;
        } else {
            if(iidd < 1){
                createPdf();
            }else{
                createPdf1();
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
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,0);
        DateFormat dff = new SimpleDateFormat("MMM-d-yyyy-HH_mm_ss");
        pdfdate = dff.format(cal.getInstance().getTime());
        String todaydate = df.format(cal.getTime());
         pdfname ="Daily_"+pdfdate+"dailyreport"+".pdf";

        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
        OutputStream output = new FileOutputStream(pdfFile);

        Document document = new Document(PageSize.A4,10f,10f,10f,0f);

            table = new PdfPTable(new float[]{300,1000});

        table.getWidthPercentage();
        Log.d("tabala",String.valueOf(table.getWidthPercentage()));
//        table.getDefaultCell().setRotation(90);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.getDefaultCell().setFixedHeight(20);
        table.setTotalWidth(PageSize.A4.getWidth());
//        table.setLockedWidth(true);
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell("Name");
        table.addCell("Value");



        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();

        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }

        for (int i = 0; i < MyList1.size(); i++) {

            name = MyList1.get(i);
            String namen = name.getfrom_date();
            String todate = name.getto_date();
            String bal_cust = name.getbalance_customers();
            String total_cust = name.gettotal_customers();
            String paid_cust = name.getpaid_customers();
            String bef_bal = name.getbefore_balance();
            String tod_col = name.gettoday_collection();
            String nip_col = name.getnip_collection();
            String nipnip_col = name.getnipnip_collection();
            String tot_col = name.gettotal_collection();
            String deb = name.getdebit();
            String intrr = name.getinterest();
            String doccar = name.getdocument_charge();
            String ohr = name.getother();
            String hgh = name.gethigh_amount();
            String lov = name.getlow_amount();
            String ex = name.getexpense();
            String cre = name.getnew_credit();
            String tot_bal = name.gettotal_balance();
            String clo_bal = name.getclosed_collection();
            String ty = getString(R.string.yesterday_balance);
            String ty1 = getString(R.string.new_credit);
            String ty2 = getString(R.string.today_collection);
            String ty3 = getString(R.string.nip_collection);
            String ty4 = getString(R.string.nipnip_collection);
            String ty5 = getString(R.string.closed_amount);
            String ty6 = getString(R.string.total_collection);
            String ty7 = getString(R.string.debit1);
            String ty8 = getString(R.string.document_charge);
            String ty9 = getString(R.string.interest);
            String ty10 = getString(R.string.otherincome);
            String ty11 = getString(R.string.high);
            String ty12 = getString(R.string.low_amount);
            String ty13 = getString(R.string.expense);
            String ty14 = getString(R.string.total_balance11);
            String ty15 = getString(R.string.total_customer);
            String ty16 = getString(R.string.paid_customers);
            String ty17 = getString(R.string.balance_customers);

            table.addCell(ty);
            table.addCell(bef_bal);
            table.addCell(ty1);
            table.addCell(cre);
            table.addCell(ty2);
            table.addCell(tod_col);
            table.addCell(ty3);
            table.addCell(nip_col);
            table.addCell(ty4);
            table.addCell(clo_bal);
            table.addCell(ty5);
            table.addCell(nipnip_col);
            table.addCell(ty6);
            table.addCell(tot_col);
            table.addCell(ty7);
            table.addCell(deb);
            table.addCell(ty8);
            table.addCell(doccar);
            table.addCell(ty9);
            table.addCell(intrr);
            table.addCell(ty10);
            table.addCell(ohr);
            table.addCell(ty11);
            table.addCell(hgh);
            table.addCell(ty12);
            table.addCell(lov);
            table.addCell(ty13);
            table.addCell(ex);
            table.addCell(ty14);
            table.addCell(tot_bal);
            table.addCell(ty15);
            table.addCell(total_cust);
            table.addCell(ty16);
            table.addCell(String.valueOf(paid_cust));
            table.addCell(ty17);
            table.addCell(bal_cust);






        }
        PdfWriter pdfWriter = PdfWriter.getInstance(document, output);
        pdfWriter.setEncryption(user_pass.getBytes(), owner_pass.getBytes(),
                PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
        class RotateEvent extends PdfPageEventHelper {
            public void onStartPage(PdfWriter writer, Document document) {
                writer.addPageDictEntry(PdfName.ROTATE, PdfPage.LANDSCAPE);
            }
        }

        // Rotates each page to landscape

//        pdfWriter.setPageEvent(new RotateEvent());
//        pdfWriter.addPageDictEntry(PdfName.ROTATE, PdfPage.SEASCAPE);
        document.open();
        Date debit = null;
        Date todat = null;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
        try {
            debit = sdf1.parse(dateString);
            todat = sdf1.parse(todateee);
            fdate = df.format(debit);
             tdate = df.format(todat);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.NORMAL, BaseColor.RED);
        Font g = new Font(Font.FontFamily.TIMES_ROMAN, 15.0f, Font.NORMAL, BaseColor.RED);
        Chunk glue = new Chunk(new VerticalPositionMark());
        Paragraph p = new Paragraph("Date :"+" "+todaydate);
//        p.setSpacingBefore(50);
        p.setSpacingAfter(10);
        p.add(new Chunk(glue));
        p.add("From & To Date :"+fdate+" - "+tdate +"\n");
        p.setFont(g);
        String seso = "Morning";
        if(ses.equals(1)){
            seso = "Morning";
        }else if(ses.equals(2)){
            seso = "Evening";
        }
        Paragraph p21 = new Paragraph("Session :"+" "+seso);
//        p.setSpacingBefore(50);
        p21.setSpacingAfter(10);
        p21.setFont(g);
        Paragraph p1 = new Paragraph(nme +"\n",g);
        p1.setAlignment(Element.ALIGN_CENTER);
        document.add(p21);
        document.add(p1);
        document.add(p);
        document.add(table);

//        for (int i = 0; i < MyList1.size(); i++) {
//            document.add(new Paragraph(String.valueOf(MyList1.get(i))));
//        }
        document.close();
        Log.e("pdff", Names.toString());
//        previewpdf();
    }

    //createPdf1() - Create PDF
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void createPdf1() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,0);
        DateFormat dff = new SimpleDateFormat("MMM-d-yyyy-HH_mm_ss");
        pdfdate = dff.format(cal.getInstance().getTime());
        String todaydate = df.format(cal.getTime());
        pdfname ="Daily_"+pdfdate+"dailyreport"+".pdf";

        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
        OutputStream output = new FileOutputStream(pdfFile);

        Document document = new Document(PageSize.A4,10f,10f,10f,0f);

        table = new PdfPTable(new float[]{300,1000});

        table.getWidthPercentage();
        Log.d("tallyy",String.valueOf(tally.toString()));
        Log.d("tabala",String.valueOf(table.getWidthPercentage()));
//        table.getDefaultCell().setRotation(90);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.getDefaultCell().setFixedHeight(20);
        table.setTotalWidth(PageSize.A4.getWidth());
//        table.setLockedWidth(true);
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell("Name");
        table.addCell("Value");



        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();

        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }

        for (int i = 0; i < MyList1.size(); i++) {

            name = MyList1.get(i);
            String namen = name.getfrom_date();
            String todate = name.getto_date();
            String bal_cust = name.getbalance_customers();
            String total_cust = name.gettotal_customers();
            String paid_cust = name.getpaid_customers();
            String bef_bal = name.getbefore_balance();
            String tod_col = name.gettoday_collection();
            String nip_col = name.getnip_collection();
            String nipnip_col = name.getnipnip_collection();
            String tot_col = name.gettotal_collection();
            String deb = name.getdebit();
            String intrr = name.getinterest();
            String doccar = name.getdocument_charge();
            String ohr = name.getother();
            String hgh = name.gethigh_amount();
            String lov = name.getlow_amount();
            String ex = name.getexpense();
            String cre = name.getnew_credit();
            String tot_bal = name.gettotal_balance();
            String cub = name.getcurrent_balance();
            String nib = name.getnip_balance();
            String nnib = name.getnipnip_balance();
            String mon = name.getmoved_nip();
            String monn = name.getmoved_nipnip();
            String dis = name.getdiscount();
            String cba = name.getcollection_balance();
            String clc = name.getclosed_collection();
            String ty = getString(R.string.yesterday_balance);
            String ty1 = getString(R.string.new_credit);
            String ty2 = getString(R.string.today_collection);
            String ty3 = getString(R.string.nip_collection);
            String ty4 = getString(R.string.nipnip_collection);
            String ty5 = getString(R.string.closed_amount);
            String ty6 = getString(R.string.total_collection);
            String ty7 = getString(R.string.debit1);
            String ty8 = getString(R.string.document_charge);
            String ty9 = getString(R.string.interest);
            String ty10 = getString(R.string.otherincome);
            String ty11 = getString(R.string.high);
            String ty12 = getString(R.string.low_amount);
            String ty13 = getString(R.string.expense);
            String ty14 = getString(R.string.total_balance11);
            String ty15 = getString(R.string.total_customer);
            String ty16 = getString(R.string.paid_customers);
            String ty17 = getString(R.string.balance_customers);
            String ty18 = getString(R.string.current_balance);
            String ty19 = getString(R.string.low_amount);
            String ty20 = getString(R.string.nip_balance);
            String ty21 = getString(R.string.nipnip_balance);
            String ty22 = getString(R.string.total_collection_balance);
            String ty23 = getString(R.string.today_nip_income);
            String ty24 = getString(R.string.today_nipnip_income);
            String ty25 = getString(R.string.discount);
            String ty26 = getString(R.string.total_balance11);
//            table.addCell(ty);
            table.addCell(getCell1(String.valueOf(ty) , PdfPCell.ALIGN_CENTER));
            table.addCell(bef_bal);
//            table.addCell(ty1);
            table.addCell(getCell1(String.valueOf(ty1) , PdfPCell.ALIGN_CENTER));
            table.addCell(cre);
//            table.addCell(ty2);
            table.addCell(getCell1(String.valueOf(ty2) , PdfPCell.ALIGN_CENTER));
            table.addCell(tod_col);
//            table.addCell(ty3);
            table.addCell(getCell1(String.valueOf(ty3) , PdfPCell.ALIGN_CENTER));
            table.addCell(nip_col);
//            table.addCell(ty4);
            table.addCell(getCell1(String.valueOf(ty4) , PdfPCell.ALIGN_CENTER));
            table.addCell(nipnip_col);
//            table.addCell(ty5);
            table.addCell(getCell1(String.valueOf(ty5) , PdfPCell.ALIGN_CENTER));
            table.addCell(clc);
//            table.addCell(ty6);
            table.addCell(getCell1(String.valueOf(ty6) , PdfPCell.ALIGN_CENTER));
            table.addCell(tot_col);
//            table.addCell(ty7);
            table.addCell(getCell1(String.valueOf(ty7) , PdfPCell.ALIGN_CENTER));
            table.addCell(deb);
//            table.addCell(ty8);
            table.addCell(getCell1(String.valueOf(ty8) , PdfPCell.ALIGN_CENTER));
            table.addCell(doccar);
//            table.addCell(ty9);
            table.addCell(getCell1(String.valueOf(ty9) , PdfPCell.ALIGN_CENTER));
            table.addCell(intrr);
//            table.addCell(ty10);
            table.addCell(getCell1(String.valueOf(ty10) , PdfPCell.ALIGN_CENTER));
            table.addCell(ohr);
//            table.addCell(ty11);
            table.addCell(getCell1(String.valueOf(ty11) , PdfPCell.ALIGN_CENTER));
            table.addCell(hgh);
//            table.addCell(ty12);
            table.addCell(getCell1(String.valueOf(ty12) , PdfPCell.ALIGN_CENTER));
            table.addCell(lov);
//            table.addCell(ty13);
            table.addCell(getCell1(String.valueOf(ty13) , PdfPCell.ALIGN_CENTER));
            table.addCell(ex);
//            table.addCell(ty14);
            table.addCell(getCell1(String.valueOf(ty14) , PdfPCell.ALIGN_CENTER));
            table.addCell(tot_bal);
//            table.addCell(ty15);
            table.addCell(getCell1(String.valueOf(ty15) , PdfPCell.ALIGN_CENTER));
            table.addCell(total_cust);
//            table.addCell(ty16);
            table.addCell(getCell1(String.valueOf(ty16) , PdfPCell.ALIGN_CENTER));
            table.addCell(String.valueOf(paid_cust));
//            table.addCell(ty17);
            table.addCell(getCell1(String.valueOf(ty17) , PdfPCell.ALIGN_CENTER));
            table.addCell(bal_cust);

//            table.addCell(ty18);
            table.addCell(getCell1(String.valueOf(ty18) , PdfPCell.ALIGN_CENTER));
            table.addCell(cub);
//            table.addCell(ty19);
            table.addCell(getCell1(String.valueOf(ty20) , PdfPCell.ALIGN_CENTER));
            table.addCell(nib);
//            table.addCell(ty20);
            table.addCell(getCell1(String.valueOf(ty21) , PdfPCell.ALIGN_CENTER));
            table.addCell(nnib);
//            table.addCell(ty21);
            table.addCell(getCell1(String.valueOf(ty22) , PdfPCell.ALIGN_CENTER));
            table.addCell(cba);
//            table.addCell(ty22);
            table.addCell(getCell1(String.valueOf(ty23) , PdfPCell.ALIGN_CENTER));
            table.addCell(mon);
//            table.addCell(ty23);
            table.addCell(getCell1(String.valueOf(ty24) , PdfPCell.ALIGN_CENTER));
            table.addCell(monn);
//            table.addCell(ty24);
            table.addCell(getCell1(String.valueOf(ty25) , PdfPCell.ALIGN_CENTER));
            table.addCell(dis);
//            table.addCell(ty25);
            table.addCell(getCell1(String.valueOf(ty26) , PdfPCell.ALIGN_CENTER));
            table.addCell(tot_bal);





        }

        table1 = new PdfPTable(new float[]{750,750});
        table1.setSpacingBefore(10);
        table1.setSpacingAfter(10);
        table1.getWidthPercentage();
        Log.d("tabala",String.valueOf(table1.getWidthPercentage()));
//        table.getDefaultCell().setRotation(90);
        table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table1.getDefaultCell().setFixedHeight(40);
        table1.setTotalWidth(PageSize.A4.getWidth());
//        table.setLockedWidth(true);
        table1.setWidthPercentage(100);
        table1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table1.addCell(getCell11("No.of.Currency", PdfPCell.ALIGN_CENTER));
        table1.addCell(getCell11("Amount", PdfPCell.ALIGN_CENTER));

        table1.setHeaderRows(1);

        PdfPCell[] cells1 = table1.getRow(0).getCells();

        for (int j = 0; j < cells1.length; j++) {
            cells1[j].setBackgroundColor(BaseColor.GRAY);
        }

           Integer twoth = Integer.valueOf(tally.get(0));
           Integer oneth = Integer.valueOf(tally.get(1));
           Integer fivehun = Integer.valueOf(tally.get(2));
           Integer twohun = Integer.valueOf(tally.get(3));
           Integer ondhun = Integer.valueOf(tally.get(4));
           Integer fifty = Integer.valueOf(tally.get(5));
           Integer twenty = Integer.valueOf(tally.get(6));
           Integer ten = Integer.valueOf(tally.get(7));
           Integer five = Integer.valueOf(tally.get(8));
           Integer cus1 = Integer.valueOf(tally.get(9));
           Integer cus2 = Integer.valueOf(tally.get(10));
           Integer tt = twoth * 2000;
        Integer ot = oneth * 1000;
        Integer fh = fivehun * 500;
        Integer th = twohun * 200;
        Integer oh = ondhun * 100;
        Integer fif = fifty * 50;
        Integer tw = twenty * 20;
        Integer te = ten * 10;
        Integer fv = five * 5;
        Integer cs = cus1 * cus2;
        Integer toto = ot+fh+th+oh+fif+tw+te+fv+cs;
           table1.addCell(getCell11(String.valueOf(twoth)+" * "+ "2000" , PdfPCell.ALIGN_CENTER));
           table1.addCell(getCell11(String.valueOf(tt) , PdfPCell.ALIGN_CENTER));
           table1.addCell(getCell11(String.valueOf(oneth)+" * "+ "1000" , PdfPCell.ALIGN_CENTER));
           table1.addCell(getCell11(String.valueOf(ot) , PdfPCell.ALIGN_CENTER));
           table1.addCell(getCell11(String.valueOf(fivehun)+" * "+ "500" , PdfPCell.ALIGN_CENTER));
           table1.addCell(getCell11(String.valueOf(fh) , PdfPCell.ALIGN_CENTER));
           table1.addCell(getCell11(String.valueOf(twohun)+" * "+ "200" , PdfPCell.ALIGN_CENTER));
           table1.addCell(getCell11(String.valueOf(th) , PdfPCell.ALIGN_CENTER));
           table1.addCell(getCell11(String.valueOf(ondhun)+" * "+ "100" , PdfPCell.ALIGN_CENTER));
           table1.addCell(getCell11(String.valueOf(oh) , PdfPCell.ALIGN_CENTER));
           table1.addCell(getCell11(String.valueOf(fifty)+" * "+ "50" , PdfPCell.ALIGN_CENTER));
           table1.addCell(getCell11(String.valueOf(fif) , PdfPCell.ALIGN_CENTER));
           table1.addCell(getCell11(String.valueOf(twenty)+" * "+ "20" , PdfPCell.ALIGN_CENTER));
           table1.addCell(getCell11(String.valueOf(tw) , PdfPCell.ALIGN_CENTER));
           table1.addCell(getCell11(String.valueOf(ten)+" * "+ "10" , PdfPCell.ALIGN_CENTER));
           table1.addCell(getCell11(String.valueOf(te) , PdfPCell.ALIGN_CENTER));
           table1.addCell(getCell11(String.valueOf(five)+" * "+ "5" , PdfPCell.ALIGN_CENTER));
           table1.addCell(getCell11(String.valueOf(fv) , PdfPCell.ALIGN_CENTER));
           table1.addCell(getCell11(String.valueOf(cus1)+" * "+ String.valueOf(cus2) , PdfPCell.ALIGN_CENTER));
           table1.addCell(getCell11(String.valueOf(cs) , PdfPCell.ALIGN_CENTER));
           String mnn = getString(R.string.total);
        table1.addCell(getCell11(mnn , PdfPCell.ALIGN_CENTER));
        table1.addCell(getCell11(String.valueOf(toto) , PdfPCell.ALIGN_CENTER));


        table2 = new PdfPTable(new float[]{750,750});

        table2.getWidthPercentage();
        Log.d("tabala",String.valueOf(table2.getWidthPercentage()));
//        table.getDefaultCell().setRotation(90);
        table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table2.getDefaultCell().setFixedHeight(40);
        table2.setTotalWidth(PageSize.A4.getWidth());
//        table.setLockedWidth(true);
        table2.setWidthPercentage(100);
        table2.setHorizontalAlignment(Element.ALIGN_LEFT);
        String op = getString(R.string.today_collection1);
        String op1 = getString(R.string.tally1);
        String op2 = getString(R.string.total_balance1);
        table2.addCell(getCell12(op , PdfPCell.ALIGN_CENTER));
        table2.addCell(getCell12(String.valueOf(colllectod) , PdfPCell.ALIGN_CENTER));
        table2.addCell(getCell12(op1 , PdfPCell.ALIGN_CENTER));
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String balat = pref.getString("totalbal","");
        table2.addCell(getCell12(String.valueOf(toto), PdfPCell.ALIGN_CENTER));
        table2.addCell(getCell12(op2 , PdfPCell.ALIGN_CENTER));
        table2.addCell(getCell12(balat , PdfPCell.ALIGN_CENTER));

        table3 = new PdfPTable(new float[]{750,750});
        table3.getWidthPercentage();
        Log.d("tabala",String.valueOf(table3.getWidthPercentage()));
//        table.getDefaultCell().setRotation(90);
        table3.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table3.getDefaultCell().setFixedHeight(40);
        table3.setTotalWidth(PageSize.A4.getWidth());
//        table.setLockedWidth(true);
        table3.setWidthPercentage(100);
        table3.setHorizontalAlignment(Element.ALIGN_LEFT);
        for(int z = 0;z < Names1.size();z++){
            Log.d("zzzzz",Names1.get(z));
            String name = Names1.get(z);
            String[] currencies = name.split(",");
            String s = currencies[2];
            String sS = currencies[3];
            table3.addCell(getCell12(s , PdfPCell.ALIGN_CENTER));
            table3.addCell(getCell12(sS , PdfPCell.ALIGN_CENTER));
        }


        PdfWriter pdfWriter = PdfWriter.getInstance(document, output);
        pdfWriter.setEncryption(user_pass.getBytes(), owner_pass.getBytes(),
                PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
        class RotateEvent extends PdfPageEventHelper {
            public void onStartPage(PdfWriter writer, Document document) {
                writer.addPageDictEntry(PdfName.ROTATE, PdfPage.LANDSCAPE);
            }
        }
        // Rotates each page to landscape

//        pdfWriter.setPageEvent(new RotateEvent());
//        pdfWriter.addPageDictEntry(PdfName.ROTATE, PdfPage.SEASCAPE);
        document.open();
        Date debit = null;
        Date todat = null;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
        try {
            debit = sdf1.parse(dateString);
            todat = sdf1.parse(todateee);
            fdate = df.format(debit);
            tdate = df.format(todat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.NORMAL, BaseColor.RED);
        Font g = new Font(Font.FontFamily.TIMES_ROMAN, 15.0f, Font.NORMAL, BaseColor.RED);
        Chunk glue = new Chunk(new VerticalPositionMark());
        Paragraph p = new Paragraph("Date :"+" "+todaydate);
//        p.setSpacingBefore(50);
//        p.setSpacingAfter(10);
        p.add(new Chunk(glue));
        p.add("From & To Date :"+fdate+" - "+tdate +"\n");
        p.setFont(g);
        String seso = "Morning";
        if(ses.equals(1)){
            seso = "Morning";
        }else if(ses.equals(2)){
            seso = "Evening";
        }

        Paragraph p21 = new Paragraph("Session :"+" "+seso);
//        p.setSpacingBefore(50);
        p21.setSpacingAfter(10);
        p21.setFont(g);

        Paragraph p0 = new Paragraph("Date :"+" "+todaydate);
//        p.setSpacingBefore(50);
//        p0.setSpacingAfter(5);
        p0.add(new Chunk(glue));
        p0.add("From & To Date :"+fdate+" - "+tdate +"\n");
        p0.setFont(g);
        try {
            BaseFont basefont = BaseFont.createFont("res/font/bamini.ttf", BaseFont.IDENTITY_H, true);
            sdfer = new Font(basefont, 10, Font.NORMAL, BaseColor.BLUE);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Log.d("uy12",text);
        if(nme.matches(".*[a-zA-Z]+.*")){
            strConBamini = nme ;
            Log.d("uy","uy");
            BaseFont basefont = null;
            try {
                basefont = BaseFont.createFont("res/font/open_regular.ttf", BaseFont.IDENTITY_H, true);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sdfer = new Font(basefont, 15, Font.BOLD, BaseColor.RED);
            cell2 = new PdfPCell(new Phrase(strConBamini,sdfer));
        }else{
            Log.d("uy1","uy1");
            strConBamini = TamilUtil.convertToTamil(TamilUtil.BAMINI, nme);
            BaseFont basefont = null;
            try {
                basefont = BaseFont.createFont("res/font/bamini.ttf", BaseFont.IDENTITY_H, true);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sdfer = new Font(basefont, 15, Font.NORMAL, BaseColor.RED);
            cell2 = new PdfPCell(new Phrase(strConBamini,sdfer));
        }
        Paragraph p1 = new Paragraph(new Phrase(strConBamini,sdfer));
        p1.setAlignment(Element.ALIGN_CENTER);
        Paragraph p11 = new Paragraph(new Phrase(strConBamini,sdfer));
        p11.setAlignment(Element.ALIGN_CENTER);

        document.add(p1);
        document.add(p);
        document.add(p21);
        document.add(table);
        document.newPage();
        document.add(p11);
        document.add(p0);
        document.add(p21);
        document.add(table1);
        table2.setSpacingBefore(30);
        table2.setSpacingAfter(30);
        document.add(table2);
        document.newPage();
        document.add(p11);
        document.add(p0);
        document.add(p21);
        table3.setSpacingBefore(30);
        table3.setSpacingAfter(30);
        document.add(table3);
        document.close();
        Log.e("pdff", Names.toString());
    }

    //getCell1() - Change text size , color and font and alignment
    //Params - test and allignment
    //Created on 25/01/2022
    //Return - NULL
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
        Log.d("uy12",text);
        if(text.matches(".*[a-zA-Z]+.*")){
            strConBamini = text ;
            Log.d("uy","uy");
            BaseFont basefont = null;
            try {
                basefont = BaseFont.createFont("res/font/open_regular.ttf", BaseFont.IDENTITY_H, true);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sdfer = new Font(basefont, 10, Font.BOLD, BaseColor.BLACK);
            cell2 = new PdfPCell(new Phrase(strConBamini,sdfer));
        }else{
            Log.d("uy1","uy1");
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
            sdfer = new Font(basefont, 10, Font.BOLD, BaseColor.BLACK);
            cell2 = new PdfPCell(new Phrase(strConBamini,sdfer));
        }
        cell2.setPadding(0);
        cell2.setHorizontalAlignment(alignment);
        cell2.setBorder(PdfPCell.BOX);
        return cell2;
    }

    //getCell12() - Change text size , color and font and alignment
    //Params - test and allignment
    //Created on 25/01/2022
    //Return - NULL
    public PdfPCell getCell12(String text, int alignment) {
//        table.AddCell(new PdfPCell(new Phrase(yourDatabaseValue,fontH1)));
//        Typeface tf = Typeface.createFromAsset(this.getAssets(),"fonts/tamil.ttf");
        try {
//            BaseFont urName = BaseFont.createFont("res/font/tamilb.ttf", "UTF-8", BaseFont.EMBEDDED);
//            sdfer = new Font(urName, 20.0f, Font.NORMAL, BaseColor.RED);
////            String fontpath = Environment.GetEnvironmentVariable("SystemRoot") + "\\fonts\\ARIALUNI.TTF";
            BaseFont basefont = BaseFont.createFont("res/font/bamini.ttf", BaseFont.IDENTITY_H, true);
            sdfer = new Font(basefont, 15, Font.NORMAL, BaseColor.BLUE);
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
        Log.d("uy12",text);
        if(text.matches(".*[a-zA-Z]+.*")){
            strConBamini = text ;
            Log.d("uy","uy");
            BaseFont basefont = null;
            try {
                basefont = BaseFont.createFont("res/font/open_regular.ttf", BaseFont.IDENTITY_H, true);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sdfer = new Font(basefont, 15, Font.BOLD, BaseColor.BLACK);
            cell2 = new PdfPCell(new Phrase(strConBamini,sdfer));
        }else{
            Log.d("uy1","uy1");
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
            sdfer = new Font(basefont, 15, Font.BOLD, BaseColor.BLACK);
            cell2 = new PdfPCell(new Phrase(strConBamini,sdfer));
        }
        cell2.setPadding(5);
        cell2.setHorizontalAlignment(alignment);
        cell2.setVerticalAlignment(alignment);
        cell2.setBorder(PdfPCell.BOX);
        cell2.setBorderColor(BaseColor.WHITE);
        cell2.setFixedHeight(30);
        return cell2;
    }

    //getCell11() - Change text size , color and font and alignment
    //Params - test and allignment
    //Created on 25/01/2022
    //Return - NULL
    public PdfPCell getCell11(String text, int alignment) {
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
        Log.d("uy12",text);

            strConBamini = text ;
            Log.d("uy","uy");
            BaseFont basefont = null;
            try {
                basefont = BaseFont.createFont("res/font/open_regular.ttf", BaseFont.IDENTITY_H, true);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sdfer = new Font(basefont, 15, Font.BOLD, BaseColor.BLACK);
            cell2 = new PdfPCell(new Phrase(strConBamini,sdfer));

        cell2.setPadding(0);
        cell2.setHorizontalAlignment(alignment);
        cell2.setVerticalAlignment(alignment);
        cell2.setBorder(PdfPCell.BOX);

        cell2.setFixedHeight(30);
        return cell2;
    }

    //zero1() - get tally details
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void zero1(){
        tally.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        String SUM_QUERY = "SELECT * FROM dd_tally WHERE created_date = ? AND tracking_id = ?";
        Cursor cur = database.rawQuery(SUM_QUERY,new String[]{formattedDate,String.valueOf(ses)});
        if (cur != null) {
            if (cur.getCount() != 0) {
                cur.moveToFirst();
                do {
                    int index;
//                    CASE amount WHEN SUM(amount)  IS NULL THEN amount = '1' ELSE amount  END

                    index = cur.getColumnIndexOrThrow("id");
                    String id = cur.getString(index);

                    index = cur.getColumnIndexOrThrow("two_thousand");
                    String tt = cur.getString(index);
                    Integer tt1 = cur.getInt(index);

                    index = cur.getColumnIndexOrThrow("one_thousand");
                    String ot = cur.getString(index);
                    Integer ot1 = cur.getInt(index);

                    index = cur.getColumnIndexOrThrow("five_hundred");
                    String fh = cur.getString(index);
                    Integer fh1 = cur.getInt(index);

                    index = cur.getColumnIndexOrThrow("two_hundred");
                    String th = cur.getString(index);
                    Integer th1 = cur.getInt(index);

                    index = cur.getColumnIndexOrThrow("one_hundred");
                    String oh = cur.getString(index);
                    Integer oh1 = cur.getInt(index);

                    index = cur.getColumnIndexOrThrow("fifty");
                    String ft = cur.getString(index);
                    Integer ft1 = cur.getInt(index);

                    index = cur.getColumnIndexOrThrow("twenty");
                    Integer twh1 = cur.getInt(index);
                    String twh = cur.getString(index);

                    index = cur.getColumnIndexOrThrow("ten");
                    String t = cur.getString(index);
                    Integer t1 = cur.getInt(index);

                    index = cur.getColumnIndexOrThrow("five");
                    String f = cur.getString(index);
                    Integer f1 = cur.getInt(index);

                    index = cur.getColumnIndexOrThrow("custom1");
                    String c1 = cur.getString(index);
                    Integer c11 = cur.getInt(index);

                    index = cur.getColumnIndexOrThrow("custom2");
                    String c2 = cur.getString(index);
                    Integer c21 = cur.getInt(index);

                    tally.add(String.valueOf(tt1));
                    tally.add(String.valueOf(ot1));
                    tally.add(String.valueOf(fh1));
                    tally.add(String.valueOf(th1));
                    tally.add(String.valueOf(oh1));
                    tally.add(String.valueOf(ft1));
                    tally.add(String.valueOf(twh));
                    tally.add(String.valueOf(t1));
                    tally.add(String.valueOf(f1));
                    tally.add(String.valueOf(c11));
                    tally.add(String.valueOf(c21));

//                    Log.d("debittt",String.valueOf(odd)+" "+String.valueOf(oddd));
                    Log.d("debittt1123",f);
//                    paidamount = String.valueOf(od);
//                    twot.setText(tt);
//                    onet.setText(ot);
//                    fivehu.setText(fh);
//                    twoh.setText(th);
//                    hond.setText(oh);
//                    fif.setText(ft);
//                    twen.setText(twh);
//                    tn.setText(t);
//                    fiv.setText(f);
//                    cus1.setText(c2);
//                    cus2.setText(c1);
//                    twt.setText(String.valueOf(2000 * tt1));
//                    oneto.setText(String.valueOf(1000 * ot1));
//                    fv.setText(String.valueOf(500 * fh1));
//                    hu.setText(String.valueOf(100 * oh1));
//                    tw.setText(String.valueOf(200 * th1));
//                    fi.setText(String.valueOf(50 * ft1));
//                    twe.setText(String.valueOf(20 * twh1));
//                    te.setText(String.valueOf(10 * t1));
//                    fii.setText(String.valueOf(5 * f1));
//                    cu.setText(String.valueOf(c21 * c11));
//                    calculate();
                }
                while (cur.moveToNext());
                cur.close();
                sqlite.close();
                database.close();
            }else{
                tally.add("0");
                tally.add("0");
                tally.add("0");
                tally.add("0");
                tally.add("0");
                tally.add("0");
                tally.add("0");
                tally.add("0");
                tally.add("0");
                tally.add("0");
                tally.add("0");
                cur.close();
                sqlite.close();
                database.close();
            }
        }else{
            tally.add("0");
            tally.add("0");
            tally.add("0");
            tally.add("0");
            tally.add("0");
            tally.add("0");
            tally.add("0");
            tally.add("0");
            tally.add("0");
            tally.add("0");
            tally.add("0");
            cur.close();
            sqlite.close();
            database.close();
        }
    }

    //populateRecyclerView41() - get account details
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView41() {
        Names1.clear();
        String USER;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY = "SELECT a.*,b.acc_type_name,b.acc_type,SUM(a.amount) as total FROM dd_accounting a LEFT JOIN dd_accounting_type b " +
                "on b.id = a.acc_type_id WHERE a.tracking_id = ? AND a.accounting_date = ? GROUP BY a.id ORDER BY a.accounting_date DESC";
        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{String.valueOf(ses),formattedDate});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("accounting_date");
                    String ate = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    long id = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("acc_type_name");
                    String acc1 = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("acc_type");
                    Integer accty = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("note");
                    String acc11 = cursor.getString(index);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    String myFormat1 = "dd/MM/yyyy";//In which you need put here
                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                    try {
                        Date debit = sdf.parse(ate);
                        acda = sdf1.format(debit);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
//                    if(acc.equalsIgnoreCase("1")){
//                        acc ="Credit";
//                    }else if(acc.equalsIgnoreCase("2")){
//                        acc = "Debit";
//                    }
                    index = cursor.getColumnIndexOrThrow("amount");
                    String amm = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("total");
                    String am_to = cursor.getString(index);
                    Integer ana = Integer.parseInt(am_to);

//                    if(accty == 1){
//                        totcr = totcr + Integer.valueOf(amm);
//                    }else{
//                        totde = totde + Integer.valueOf(amm);
//                    }
//                    amoun1 = totcr - totde;
                    //                    String parsedStr = acc1.replaceAll("(.{5})", "$1\n");

//                    index = cursor.getColumnIndexOrThrow("amount");
//                    String tot = cursor.getString(index);
//                    if(acda == null){
//                        acda = "0";
//                    }
                    if(acc1 == null){
                        acc1 = "0";
                    }
                    if(acc11 == null || acc11.equalsIgnoreCase("")){
                        acc11 = "NO";
                    }
                    Log.d("o0",acc11);
                    if(amm == null){
                        amm = "0";
                    }
//                    if(sl == null){
//                        sl = 0;
//                    }
                    if(accty == null){
                        accty = 0;
                    }
                    if(acc1.equalsIgnoreCase("Investment")){
                        String nnaa = getApplicationContext().getString(R.string.investment);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Finance")){
                        String nnaa = getString(R.string.finance);
                        USER=nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Chittu")){
                        String nnaa = getString(R.string.chittu2);
                        USER = nnaa ;
                    }
                    else if(acc1.equalsIgnoreCase("Transfer_Hand")){
                        String nnaa = getString(R.string.transferhand);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Anamattu")){
                        String nnaa = getString(R.string.anamattu);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Other_Income")){
                        String nnaa = getString(R.string.otherincome);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Employee_credit")){
                        String nnaa = getString(R.string.employeecredit);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("High")){
                        String nnaa = getString(R.string.high);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Profit")){
                        String nnaa = getString(R.string.profit);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Expense")){
                        String nnaa = getString(R.string.expense);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Petrol")){
                        String nnaa = getString(R.string.petrol);
                        USER = nnaa;
                    }
                    else if(acc1.equals("Rent")){
                        String nnaa = getApplicationContext().getString(R.string.rent1);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Debit_Hand")){
                        String nnaa = getString(R.string.debithand);
                        USER=nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Vehicle_Expense")){
                        String nnaa = getString(R.string.vehicleexpense);
                        USER = nnaa ;
                    }
                    else if(acc1.equalsIgnoreCase("Other_Expense")){
                        String nnaa = getString(R.string.otherexpense);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Donation_Expense")){
                        String nnaa = getString(R.string.donationexpense);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Other_Income")){
                        String nnaa = getString(R.string.otherincome);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Employee_Expense")){
                        String nnaa = getString(R.string.employeeexpense);
                        USER = nnaa;
                    }
                    else if(acc1.equalsIgnoreCase("Low")){
                        String nnaa = getString(R.string.low_amount);
                        USER = nnaa;
                    }
                    else  if(acc1.equalsIgnoreCase("Lose")){
                        String nnaa = getString(R.string.lose);
                        USER = nnaa;
                    }
                    else{
                        USER = acc1;
                    }

                    if(amm.equalsIgnoreCase("0")){

                    }
                    else {
//                        sl = sl + 1;
                        Names1.add(id + "," + acda + "," + USER + "," + amm + "," +accty+","+acc11);
//                        Amountt.add(amm);
                    }
                    String fg = getString(R.string.total_balance1);
//                    total.setText(fg+" : "+"\u20B9"+balat);

                }
                while (cursor.moveToNext());
                Log.d("names33", String.valueOf(Names1.toString()));
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
//        if(Names.size() == 0){
//            recyclerView.setVisibility(View.GONE);
//            noo.setVisibility(View.VISIBLE);
//        }else {
//            recyclerView.setVisibility(View.VISIBLE);
//            noo.setVisibility(View.GONE);
//            mAdapter = new RecyclerViewAdapteraccounting(Names,in,getApplicationContext(),edpr,depr);
//            recyclerView.setAdapter(mAdapter);
////            mAdapter.filter(dateString);
//        }
    }

    //sumcollection1() - get collection amount
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void sumcollection1() {
        colllectod = 0;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        Log.d("daa", formattedDate);
        String SUM_QUERY = "SELECT sum(a.collection_amount) as collect FROM dd_customers c LEFT JOIN  dd_account_debit b on b.customer_id = c.id LEFT JOIN dd_collection a on a.debit_id = b.id   " +
                "WHERE a.collection_date = ? AND c.tracking_id = ?";
        Cursor cur = database.rawQuery(SUM_QUERY, new String[]{formattedDate, String.valueOf(ses)});
        if (cur != null) {
            if (cur.getCount() != 0) {
                cur.moveToFirst();
                do {
                    int index;
//                    CASE amount WHEN SUM(amount)  IS NULL THEN amount = '1' ELSE amount  END

                    index = cur.getColumnIndexOrThrow("collect");
                    colllectod = cur.getInt(index);
                    Log.d("debittt112123aa12", String.valueOf(colllectod));

                }
                while (cur.moveToNext());
                cur.close();
                sqlite.close();
                database.close();
            } else {
                cur.close();
                sqlite.close();
                database.close();
            }
        } else {
            cur.close();
            sqlite.close();
            database.close();
        }

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
    //closed() - get closed debit
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void closed(){
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String NIP ="SELECT SUM(b.debit_amount) as debitted,SUM(b.document_charge) as docuu,SUM(b.interest)as interr " +
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

//                    Integer doo = Integer.parseInt(docc);
//                    Integer doo1 = Integer.parseInt(docc1);
//                    Integer in = Integer.parseInt(intre);
                    cloosed =  in ;
                    Log.d("cloossed",String.valueOf(doo));
                    Log.d("cloossed",String.valueOf(doo1));
                    Log.d("cloossed",String.valueOf(in));
                    Log.d("cloossed",String.valueOf(cloosed));
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

 /*
    public void closeddiscount(){
        Log.d("dcssd",String.valueOf(cloosed)+" "+todateee+" "+dateString);
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String NIP = "SELECT SUM(col.collection_amount) as debitted,SUM(col.discount) as docuu,SUM(b.interest)as interr " +
                " FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id LEFT JOIN dd_collection col on col.debit_id = b.id " +
                " WHERE b.updated_date <= ? AND  b.active_status = 0 AND a.tracking_id = ? AND col.collection_date BETWEEN ? AND ? ";
        String sess = String.valueOf(ses);
        Cursor cursor = database.rawQuery(NIP, new String[]{todateee,sess,dateString,todateee});
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
                    Integer doo1 = Integer.parseInt(intre) + sum_dis;
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
//
                    index = cursor.getColumnIndexOrThrow("collected");
                    String collect = cursor.getString(index);

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
//                    Integer cdis = Integer.parseInt(clodiscount);
//                    Integer cdb = Integer.parseInt(clodebit);
//                    Integer cdo = Integer.parseInt(clodocc);
//                    Integer cin = Integer.parseInt(clointre);
//                    Integer dbb = Integer.parseInt(debit);
//                    dbb = dbb + cdb;
//                    Integer dio = Integer.parseInt(discount);
//                    Integer ac_d = Integer.parseInt(acc_debit);
//                    Integer col = Integer.parseInt(collect);
//                    col = col - dio ;
//                    Integer ac_c = Integer.parseInt(acc_credit);
//                    Integer doo = Integer.parseInt(docc);
//                    doo = doo + cdo;
//                    Integer in = Integer.parseInt(intre);
//                    in = in + cin;
//                    Integer dbed = dbb - in;
//                    Integer cloosed1 = cloosed - cdis ;
//                    Log.d("Callidc1212c",String.valueOf(cloosed1));
//                    befo = (ac_c+col+doo+cloosed1) - (dbed+ac_d);


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

//                    Integer cdis = Integer.parseInt(clodiscount) + sum_dis;
//                    Integer cdb = Integer.parseInt(clodebit) + sum_debit;

                    Integer cdis = Integer.parseInt(clodiscount);
                    Integer cdb = Integer.parseInt(clodebit) + sum_debit;
                    Integer cdo = Integer.parseInt(clodocc) + sum_doc;
                    Integer cin = Integer.parseInt(clointre) + sum_inter;
                    Integer dbb = Integer.parseInt(debit);
//                    clodeb = cdb;
//                    clodoc = cdo;
//                    cloin = cin;
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
                    Integer cloosed1 = cloosed - cdis ;
                    Log.d("Callidc1212c",String.valueOf(cloosed1));
                    befo = (ac_c+col+doo+cloosed1) - (dbed+ac_d);
                    Integer ad12 = ac_c+col+doo+cloosed1;
                    Integer ad121 = dbed+ac_d;
                    Log.d("Callidc1212c", col+" "+doo+" "+ac_c+","+cloosed+","+cdis+" "+in);
                    Log.d("Callidc1212b",dbed+" "+ac_d);
                    Log.d("Callidc1212c", String.valueOf(ad12));
                    Log.d("Callidc1212b",String.valueOf(ad121));
                    yesterdaybal = String.valueOf(befo);
                    Integer hhhgg = befo - dabamo;
                    Log.d("beforeeeeee", String.valueOf(hhhgg));
                    bef.setText("\u20B9"+" "+hhhgg);
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
*/

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

    //beforebal() - get before balance
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
//                    clodeb = cdb;
//                    clodoc = cdo;
//                    cloin = cin;
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

    //populateRecyclerView2() - get final balance
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerView2() {
        Names.clear();
        collection.clear();
        paid_cust.clear();
        balance_cust.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
//        Log.d("datast",yesterdate);

//        String MY_QUERY = "SELECT a.*,b.customer_name,b.id as ID,c.debit_amount FROM dd_collection a LEFT JOIN dd_customers b on b.id = a.customer_id LEFT JOIN dd_account_debit c on c.customer_id = a.customer_id ";
        String MY_QUERY1 = "SELECT SUM(collection_amount) as paid," +
                "(SELECT SUM(b.amount) FROM dd_accounting_type a LEFT JOIN dd_accounting b on b.acc_type_id = a.id WHERE b.accounting_date BETWEEN ? AND ? AND b.tracking_id = ? AND a.acc_type = '1' ) as todaycredit," +

                "(SELECT SUM(a.collection_amount) from dd_account_debit c LEFT JOIN dd_collection a ON a.debit_id = c.id LEFT JOIN dd_customers b on b.id = a.customer_id where a.collection_date BETWEEN ? AND ? AND b.debit_type = '0' AND b.tracking_id = ? AND c.active_status = 1 ) AS todaycollect," +
                "(SELECT SUM(a.debit_amount) from dd_account_debit a LEFT JOIN dd_customers b on b.id = a.customer_id Where a.debit_date BETWEEN ? AND ? AND b.tracking_id = ? AND a.active_status = 1) as totaldebit," +
                "(SELECT SUM(a.document_charge) from dd_account_debit a LEFT JOIN dd_customers b on b.id = a.customer_id Where a.debit_date BETWEEN ? AND ? AND b.tracking_id =? AND a.active_status = 1 ) as totaldocument ," +
                "(SELECT SUM(a.interest) from dd_account_debit a LEFT JOIN dd_customers b ON b.id = a.customer_id Where a.debit_date BETWEEN ? AND ? AND b.tracking_id = ? AND a.active_status = 1 ) as totalinterest," +
                "(SELECT SUM(a.collection_amount) from dd_account_debit c LEFT JOIN dd_collection a ON a.debit_id = c.id LEFT JOIN dd_customers b on b.id = a.customer_id where  a.collection_date BETWEEN ? AND ? AND b.debit_type ='2' AND b.tracking_id = ? AND c.active_status = 1) as totalNIPNIP ," +
                "(SELECT SUM(a.collection_amount) from dd_account_debit c LEFT JOIN dd_collection a ON a.debit_id = c.id LEFT JOIN dd_customers b on b.id = a.customer_id where  a.collection_date BETWEEN ? AND ? AND b.debit_type ='1' AND b.tracking_id = ? AND c.active_status = 1 ) as totalNIP, " +
                "(SELECT SUM(b.amount) FROM dd_accounting_type a LEFT JOIN dd_accounting b on b.acc_type_id = a.id WHERE b.accounting_date BETWEEN ? AND ? AND b.tracking_id = ? AND a.acc_type = '2' ) as totaldebittacc ," +
                "(SELECT SUM(amount) FROM dd_accounting WHERE accounting_date BETWEEN ? AND ? AND tracking_id =? AND acc_type_id = '8' ) as totalhigh ," +
                "(SELECT SUM(amount) FROM dd_accounting WHERE accounting_date BETWEEN ? AND ? AND tracking_id = ? AND acc_type_id = '19' ) as totallow ," +
                "(SELECT SUM(a.other_fee) from dd_account_debit c LEFT JOIN dd_collection a ON a.debit_id = c.id LEFT JOIN dd_customers b on b.id = a.customer_id Where a.collection_date BETWEEN ? AND ? AND b.tracking_id = ? AND c.active_status = 1 ) as totalotherfee " +
                "FROM   dd_collection    ";
        String sess = String.valueOf(ses);
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{dateString,todateee,sess,dateString,todateee,sess,dateString,todateee,sess,dateString,todateee,sess,dateString,todateee,sess
                ,dateString,todateee,sess,dateString,todateee,sess,dateString,todateee,sess,dateString,todateee,sess,dateString,todateee,sess,dateString,todateee,sess});
        if (cursor != null){
            if(cursor.getCount() != 0){
                MyList1 = new ArrayList<Daily_POJO>();
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("todaycollect");
                    String today = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totalotherfee");
                    String totalother = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("todaycollect");
                    String paid = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totaldebit");
                    String debit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totaldocument");
                    String document = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totalhigh");
                    String higg = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totallow");
                    String loww = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totalinterest");
                    String interest = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totalNIPNIP");
                    String nipnip = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totalNIP");
                    String nip = cursor.getString(index);

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
                    if(today == null ){
                        today ="0";
                        balance_cust.add(Long.valueOf(paid));
                    }else if(today =="0"){
                        balance_cust.add(Long.valueOf(paid));
                    }else{
                        paid_cust.add(Long.valueOf(paid));
                    }
                    if(debit == null){
                        debit = "0";
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
                    if(totalother == null){
                        totalother = "0";
                    }
                    if(higg == null){
                        higg = "0";
                    }
                    if(loww == null){
                        loww = "0";
                    }
                    Integer innn = Integer.parseInt(interest);
                    Integer documm = Integer.parseInt(document);
                    Integer dabit = Integer.parseInt(debit);
//                    Integer daabit = dabit -(innn+documm);
//                    befo = Integer.parseInt(yesterday);
                    newc = Integer.parseInt(credd);
                    curc = Integer.parseInt(today);
                    nipc = Integer.parseInt(nip);
                    nipnipc = Integer.parseInt(nipnip);
                    dab = dabit - innn;
                    Integer totalcoll = nipc+nipnipc+curc;
                    totdab = Integer.parseInt(debbbtacc);

//                    bef.setText("Before balance :"+" "+yesterday);
                    newcre.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.White)));
                    tc.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.White)));
                    nic.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.White)));
                    ninic.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.White)));
                    dbb.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.White)));
                    ex.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.White)));
                    newcre.setText("\u20B9"+" "+credd);
                    oth.setText("\u20B9"+" "+totalother);
                    hg.setText("\u20B9"+" "+higg);
                    lw.setText("\u20B9"+" "+loww);
                    tc.setText("\u20B9"+" "+curc);
                    Integer trt = totalcoll + dabamo;
                    Log.d("trt",String.valueOf(trt));
                    totc.setText("\u20B9"+" "+String.valueOf(trt));
                    nic.setText("\u20B9"+" "+nip);
                    ninic.setText("\u20B9"+" "+nipnip);
                    dcc.setText("\u20B9"+" "+document);
                    intt.setText("\u20B9"+" "+interest);
                    dbb.setText("\u20B9"+" "+String.valueOf(dabit));
                    ex.setText("\u20B9"+" "+debbbtacc);
                    Integer debitam1 = Integer.parseInt(debit);
                    Integer pamount = Integer.parseInt(paid);
                    Names.add(today+"   "+paid+" "+debit+" "+document+" "+interest+" "+nipnip+" "+nip+" "+debbbt+" "+credd);
                    collection.add(today);
                    Integer currentbal = (befo+newc+curc+nipc+nipnipc+documm)-(dab+totdab);
                    Log.d("tytyt",String.valueOf(currentbal));

                    if(currentbal <= 0){
                        tb.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.Red)));
                    }else if(currentbal > 0){
                        tb.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.Green)));
                    }
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
//                    balat = pref.getString("totalbal","");
                    balat = pref.getString("totalbal", "");
                    tb.setText("\u20B9"+" "+String.valueOf(currentbal));
                    totbal.setText("\u20B9"+""+String.valueOf(currentbal));
                    Integer ddiiss = disc11;
                    Log.d("discooo1234", String.valueOf(ddiiss));
//                    Integer diccc = ddiiss + closeddisco ;
//                    collection.addAll(Names);
//                    Names.add(id+" "+Name+" "+paid);
                    Log.d("discooo1234", String.valueOf(ddiiss));
                    Log.d("colammm", String.valueOf(collection));
                    Log.d("names1", String.valueOf(Names));
                    Integer yesss = Integer.parseInt(yesterdaybal);
                    Integer yesterrr = yesss - dabamo ;
                    Daily_POJO.setbefore_balance(String.valueOf(yesterrr));
                    Daily_POJO.settoday_collection(today);
                    Daily_POJO.settotal_collection(String.valueOf(trt));
                    Daily_POJO.setclosed_collection(String.valueOf(dabamo));
                    Daily_POJO.setnip_collection(nip);
                    Daily_POJO.setnipnip_collection(nipnip);
                    Daily_POJO.setdebit(debit);
                    Daily_POJO.setdocument_charge(document);
                    Daily_POJO.setinterest(interest);
                    Daily_POJO.setother(totalother);
                    Daily_POJO.sethigh_amount(higg);
                    Daily_POJO.setlow_amount(loww);
                    Daily_POJO.setfrom_date(dateString);
                    Daily_POJO.setto_date(todateee);
                    Daily_POJO.settotal_customers(size);
                    Daily_POJO.setpaid_customers(psize);
                    Daily_POJO.setbalance_customers(bsize);
                    Daily_POJO.setexpense(debbbtacc);
                    Daily_POJO.settotal_balance(String.valueOf(currentbal));
                    Daily_POJO.setnew_credit(String.valueOf(credd));

                    Daily_POJO.setcurrent_balance(currentb);
                    Daily_POJO.setnip_balance(nipb);
                    Daily_POJO.setnipnip_balance(nipnipb);
                    Daily_POJO.setmoved_nip(moven);
                    Daily_POJO.setmoved_nipnip(movenn);
                    Daily_POJO.setdiscount(String.valueOf(ddiiss));
                    Daily_POJO.setcollection_balance(String.valueOf(vd));
                    MyList1.add(Daily_POJO);

                    Daily_POJO = new Daily_POJO();
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
//        String size = String.valueOf(Names.size());
//        String psize = String.valueOf(paid_cust.size());
//        String bsize = String.valueOf(balance_cust.size());

    }

    //populateRecyclerView() - get final balance
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerView() {
        Names.clear();
        collection.clear();
        paid_cust.clear();
        balance_cust.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        Log.d("datast",yesterdate);

//        String MY_QUERY = "SELECT a.*,b.customer_name,b.id as ID,c.debit_amount FROM dd_collection a LEFT JOIN dd_customers b on b.id = a.customer_id LEFT JOIN dd_account_debit c on c.customer_id = a.customer_id ";
        String MY_QUERY1 = "SELECT SUM(a.collection_amount) as paid," +
                "SUM(a.collection_amount)-(SELECT SUM(a.collection_amount) from dd_account_debit c LEFT JOIN dd_collection a ON a.debit_id = c.id LEFT JOIN dd_customers b on b.id = a.customer_id where a.collection_date = ? AND b.tracking_id = ? AND c.active_status = 1 ) AS beforecollect," +
                "(SELECT SUM(a.collection_amount) from dd_account_debit c LEFT JOIN dd_collection a ON a.debit_id = c.id LEFT JOIN dd_customers b on b.id = a.customer_id  where a.collection_date BETWEEN ? AND ? AND b.tracking_id = ? AND c.active_status = 1 ) AS todaycollect," +
                "(SELECT SUM(a.debit_amount) from dd_account_debit a LEFT JOIN dd_customers b on b.id = a.customer_id  Where a.debit_date BETWEEN ? AND ? AND b.tracking_id = ? AND a.active_status = 1 ) as totaldebit," +
                "(SELECT SUM(a.document_charge) from dd_account_debit a LEFT JOIN dd_customers b on b.id = a.customer_id Where a.debit_date BETWEEN ? AND ? AND b.tracking_id = ? AND a.active_status = 1 ) as totaldocument ,(SELECT SUM(a.interest) from dd_account_debit a LEFT JOIN dd_customers b on b.id = a.customer_id Where a.debit_date BETWEEN ? AND ? AND b.tracking_id = ? AND a.active_status = 1) as totalinterest," +
                "(SELECT SUM(a.other_fee) from dd_account_debit c LEFT JOIN dd_collection a ON a.debit_id = c.id LEFT JOIN dd_customers b on b.id = a.customer_id LEFT JOIN dd_customers b on b.id = a.customer_id Where a.collection_date BETWEEN ? AND ? AND b.tracking_id = ? AND c.active_status = 1 ) as totalotherfee ," +
                "(SELECT SUM(a.collection_amount) from dd_account_debit c LEFT JOIN dd_collection a ON a.debit_id = c.id LEFT JOIN dd_customers b on b.id = a.customer_id where b.debit_type ='2' AND a.collection_date BETWEEN ? AND ? AND b.tracking_id = ? AND c.active_status = 1) as totalNIPNIP ," +
                "(SELECT SUM(a.collection_amount) from dd_account_debit c LEFT JOIN dd_collection a ON a.debit_id = c.id LEFT JOIN dd_customers b on b.id = a.customer_id where b.debit_type ='1' AND a.collection_date BETWEEN ? AND ? AND b.tracking_id = ? ) as totalNIP," +
                "(SELECT sum(a.amount) FROM dd_accounting a LEFT JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE  a.tracking_id = ? AND b.acc_type = '1' AND a.accounting_date BETWEEN ? AND ?) as todaycredit," +
                "(SELECT sum(a.amount) FROM dd_accounting a LEFT JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE  a.tracking_id = ? AND b.acc_type = '2' AND a.accounting_date BETWEEN ? AND ?) as todaydebit " +
                "FROM   dd_collection a LEFT JOIN dd_customers b on b.id = a.customer_id WHERE b.tracking_id = ?   ";
        String sesd = String.valueOf(ses);
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{dateString,sesd,dateString,todateee,sesd,dateString,todateee,sesd,dateString,todateee,sesd,dateString,todateee,sesd,
                dateString,todateee,sesd,dateString,todateee,sesd,dateString,todateee,sesd,sesd,dateString,todateee,sesd,dateString,todateee,sesd});
        if (cursor != null){
            if(cursor.getCount() != 0){
                MyList1 = new ArrayList<Daily_POJO>();
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("todaycollect");
                    String today = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("beforecollect");
                    String yesterday = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("paid");
                    String paid = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totaldebit");
                    String debit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totaldocument");
                    String document = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totalinterest");
                    String interest = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totalotherfee");
                    String other = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totalNIPNIP");
                    String nipnip = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("totalNIP");
                    String nip = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("todaydebit");
                    String debbbt = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("todaycredit");
                    String credd = cursor.getString(index);

                    if(paid == null){
                        paid ="0";
                    }else{
                    }
                    if(today == null ){
                        today ="0";
                        balance_cust.add(Long.valueOf(paid));
                    }else if(today =="0"){
                        balance_cust.add(Long.valueOf(paid));
                    }else{
                        paid_cust.add(Long.valueOf(paid));
                    }
                    if(debit == null){
                        debit = "0";
                    }
                    if(debbbt == null){
                        debbbt = "0";
                    }if(credd == null){
                        credd = "0";
                    }if(nip == null){
                        nip = "0";
                    }if(nipnip == null){
                        nipnip = "0";
                    }if(yesterday == null){
                        yesterday = "0";
                    }
                    if(document == null){
                        document = "0";
                    }if(interest == null){
                        interest = "0";
                    }if(other == null){
                        other = "0";
                    }if(paid == null){
                        paid = "0";
                    }
                    bef.setText("\u20B9"+" "+yesterday);
                    tc.setText("\u20B9"+" "+today);
                    Integer yyttt = Integer.parseInt(paid) + Integer.parseInt(nip) + Integer.parseInt(nipnip);
                    Integer trt = yyttt + dabamo;
                    totc.setText("\u20B9"+" "+trt);
//                    totc.setText("\u20B9"+" "+paid);
                    nic.setText("\u20B9"+" "+nip);
                    ninic.setText("\u20B9"+" "+nipnip);
                    dbb.setText("\u20B9"+" "+debit);
                    dcc.setText("\u20B9"+" "+document);
                    intt.setText("\u20B9"+" "+interest);
                    oth.setText("\u20B9"+" "+other);
                    hg.setText("\u20B9"+" "+credd);
                    lw.setText("\u20B9"+" "+debbbt);
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                    balat = pref.getString("totalbal","");
                    tb.setText("\u20B9"+" "+balat);
                    totbal.setText("\u20B9"+""+String.valueOf(balat));
                    Integer debitam1 = Integer.parseInt(debit);
                    Integer pamount = Integer.parseInt(paid);
                    Names.add(today+" "+yesterday+"   "+paid+" "+debit+" "+document+" "+interest+" "+other+" "+nipnip+" "+nip+" "+debbbt+" "+credd);
                    collection.add(today);
                    Log.d("colammm", String.valueOf(collection));
                    Log.d("names2", String.valueOf(Names));
                    size = String.valueOf(Names.size());
                    psize = String.valueOf(paid_cust.size());
                    bsize = String.valueOf(balance_cust.size());
                    Integer ddiiss = disc11;
//                    Integer diccc = ddiiss + closeddisco ;
                    Integer yesss = Integer.parseInt(yesterday);
                    Integer yesterrr = yesss - dabamo ;
                    Log.d("discooo123", String.valueOf(ddiiss));
                    Daily_POJO.setbefore_balance(String.valueOf(yesterrr));
                    Daily_POJO.settoday_collection(today);
                    Daily_POJO.settotal_collection(String.valueOf(trt));
                    Daily_POJO.setclosed_collection(String.valueOf(dabamo));
                    Daily_POJO.setnip_collection(nip);
                    Daily_POJO.setnipnip_collection(nipnip);
                    Daily_POJO.setdebit(debit);
                    Daily_POJO.setdocument_charge(document);
                    Daily_POJO.setinterest(interest);
                    Daily_POJO.setother(other);
                    Daily_POJO.sethigh_amount(debbbt);
                    Daily_POJO.setlow_amount(credd);
                    Daily_POJO.setfrom_date(dateString);
                    Daily_POJO.setto_date(todateee);
                    Daily_POJO.settotal_customers(size);
                    Daily_POJO.setpaid_customers(psize);
                    Daily_POJO.setbalance_customers(bsize);
                    Daily_POJO.setexpense("0");
                    Daily_POJO.settotal_balance("0");
                    Daily_POJO.setcurrent_balance(currentb);
                    Daily_POJO.setnip_balance(nipb);
                    Daily_POJO.setnipnip_balance(nipnipb);
                    Daily_POJO.setmoved_nip(moven);
                    Daily_POJO.setmoved_nipnip(movenn);
                    Daily_POJO.setdiscount(String.valueOf(ddiiss));
                    MyList1.add(Daily_POJO);

                    Daily_POJO = new Daily_POJO();
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
//        tot.setText(size);
//        ppp.setText(psize);
//        bbb.setText(bsize);

    }

    //populateRecyclerView1() - get final balance
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerView1() {
        Names.clear();
        collection.clear();
        paid_cust.clear();
        balance_cust.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String dad = dat.getText().toString();
        view_note.setText(dad);
//        String MY_QUERY = "SELECT a.*,b.customer_name,b.id as ID,c.debit_amount FROM dd_collection a LEFT JOIN dd_customers b on b.id = a.customer_id LEFT JOIN dd_account_debit c on c.customer_id = a.customer_id ";

//        String MY_QUERY1 = "SELECT SUM(a.collection_amount) as paid,b.customer_name,b.id as ID,b.debit_type,b.CID,b.order_id,c.debit_amount,a.customer_id," +
//                "(SELECT collection_amount from dd_collection where collection_date = ? AND customer_id = b.id) AS collect FROM  dd_customers b LEFT JOIN dd_collection a on b.id =  a.customer_id " +
//                "LEFT JOIN dd_account_debit c on b.id = c.customer_id WHERE b.tracking_id = ? AND c.debit_date <= ?  GROUP BY b.id ORDER BY b.order_id ASC";
        String MY_QUERY1 = "SELECT cus.*,deb.customer_id,col.collection_amount as collect ,sum(col.collection_amount)as paid ,deb.debit_amount,deb.debit_date,deb.id as did FROM dd_customers cus " +
                " LEFT JOIN dd_account_debit deb on deb.customer_id = cus.id " +
                "  LEFT JOIN (SELECT collection_amount,collection_date,customer_id,debit_id from dd_collection WHERE collection_date = ? GROUP BY customer_id ) col on  deb.id = col.debit_id  AND col.collection_date = ?" +
                "  WHERE cus.tracking_id = ? AND deb.debit_date <= ? AND deb.active_status = 1 AND  cus.debit_type IN(0,1,2) GROUP BY cus.id ORDER BY cus.order_id_new ASC";

        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{dateString,dateString,String.valueOf(ses),dateString});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String typ = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String orderrr = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("collect");
                    String today = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("customer_id");
                    long id = cursor.getLong(index);

                    index = cursor.getColumnIndexOrThrow("CID");
                    String iid = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("paid");
                    String paid = cursor.getString(index);
                    if(paid == null){
                        paid ="0";
                    }else{
                    }

                    if(debit == null){
                        debit = "0";
                    }else{

                        if(today == null ){
                            today ="0";
                            balance_cust.add(id);
                        }else if(today =="0"){
                            balance_cust.add(id);
                        }else{
                            paid_cust.add(id);
                        }
                        Names.add(iid);
//                        Integer tott = Integer.parseInt(today);
//                        Log.d("ghghgh",String.valueOf(tot));
//                        xcoll = xcoll + tott;
//                        Log.d("ghghgh",String.valueOf(xcoll));
//                        totco.setText(String.valueOf(xcoll));
//                        Integer debitam1 = Integer.parseInt(debit);
//                        Integer pamount = Integer.parseInt(paid);
//                        Integer balanceamount = debitam1 - pamount;
//                        String balance = String.valueOf(balanceamount);
//                        if (typ.equalsIgnoreCase("0")) {
//                            Names.add(iid+","+Name+","+debit+","+paid+","+balanceamount+","+typ);
//                        }
//                        if (typ.equalsIgnoreCase("1")) {
//                            Names.add(iid+","+Name+","+debit+","+paid+","+balanceamount+","+typ);    }
//                        if (typ.equalsIgnoreCase("2")) {
//                            Names.add(orderrr+","+Name+","+debit+","+paid+","+balanceamount+","+typ);
//                        }
//
//
//                        ID.add(String.valueOf(id));
//                        collection.add(today);
                        Log.d("colammm", String.valueOf(collection));
                        Log.d("oooddd", String.valueOf(orderrr));
                        Log.d("names3", String.valueOf(Names));

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
         size = String.valueOf(Names.size());
         psize = String.valueOf(paid_cust.size());
         bsize = String.valueOf(balance_cust.size());
         tot.setText(size);
         ppp.setText(psize);
         bbb.setText(bsize);
    }

    //current_balance() - get current balance
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void current_balance(){
        disc11 = 0;
        disccc = 0;
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
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{sess,dateString,dateString,sess,dateString,sess,
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
//                    Integer curba1 = (ysr+bed)-(pppd+curdi);
                    disccc = disccc + curdi ;
                    Log.d("dddddiiisccc1",String.valueOf(disccc));

                    Integer curbal = (ysr+bed)-(pppd+curdi);
//                    curcolbal.setText("\u20B9"+" "+String.valueOf(curbal));
                    Log.d("currentbalance",String.valueOf(movedniip));
                    Log.d("currentbalance",String.valueOf(curbal));
                    currentb = String.valueOf(curbal);
                    moven = String.valueOf(movedniip);

                    curball.setText("\u20B9"+" "+String.valueOf(curbal));
//                    curreee.setText("\u20B9"+" "+paid);
//                    todayde.setText("\u20B9"+" "+debit);
                    todnip.setText("\u20B9"+" "+movedniip);
//                    currentb.setText("\u20B9"+" "+disco);
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

    //nip_balance() - get NIP balance
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
                    if(nipnippde != 0){
                        movenipnpi = nipnippde - nipnipmo;
//                         ysr = ysr + movenipnpi;
                    }else{
                        movenipnpi = 0;
//                        ysr = ysr + movenipnpi;
                    }
                    Integer nippbal = (ysr+selectnip)-(bed+nipdo);
                    Log.d("currentbalance",ysr+" "+ysr1);

                    nipb = String.valueOf(nippbal);
                    movenn = String.valueOf(movenipnpi);
                    disccc = disccc + nipdo ;
                    Log.d("dddddiiisccc2",String.valueOf(disccc));

//                    ysrnip.setText("\u20B9"+" "+ysr);
//                    todnipb.setText("\u20B9"+" "+selectnip);
//                    todnipi.setText("\u20B9"+" "+tonipinc);
                    todnipnip.setText("\u20B9"+" "+String.valueOf(movenipnpi));
//                    nipbal.setText("\u20B9"+" "+nipdisco);
                    nipbal.setText("\u20B9"+" "+String.valueOf(nippbal));

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

    //nipnip_balance() - get NIPNIP balance
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void nipnip_balance(){
//        disc1 = "0";

//        disccc = 0;
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
                    disccc = disccc + diss ;
                    Log.d("dddddiiisccc3",String.valueOf(disccc));
                    disccc = disccc + closeddisco;
//                    ysrnipnip.setText("\u20B9"+" "+ysr);
//                    tonipnip.setText("\u20B9"+" "+String.valueOf(selectnipnip));
//                    tonipnipinc.setText("\u20B9"+" "+tonipnipincc);
//                    nipnipdiss.setText("\u20B9"+" "+totdiscco);
                    disc1 = String.valueOf(disccc);
                    disc11 = disccc;
                    nipnipb = String.valueOf(nipnippbal);
                    discount.setText("\u20B9"+" "+String.valueOf(disccc));
                    nipnipbal.setText("\u20B9"+" "+String.valueOf(nipnippbal));
                    Integer cub = Integer.parseInt(currentb);
                    Integer npb = Integer.parseInt(nipb);
                     vd = cub + npb + nipnippbal ;
                    totcb.setText("\u20B9"+String.valueOf(vd));
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

    //list1() - Show all balance details
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void list1(){
        dabamo = 0;
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        String mm = "SELECT a.customer_name,a.id as ID,a.location,a.phone_1,a.debit_type_updated,b.*,c.vv as collect,c.debit_id " +
                " ,a.debit_type FROM dd_customers  a " +
                "LEFT JOIN dd_account_debit b on b.customer_id = a.id "  +
                "LEFT JOIN (SELECT SUM(collection_amount) as vv,debit_id,collection_date from dd_collection WHERE collection_date BETWEEN ? AND ? GROUP BY debit_id ORDER BY debit_id  ) " +
                "c ON  b.id = c.debit_id  WHERE a.tracking_id = ? AND  b.active_status = 0 AND c.collection_date BETWEEN ? AND ? GROUP BY b.id ORDER BY b.id";
        String MY_QUERY = "SELECT b.*,a.customer_name,a.id as ID,a.location,a.phone_1,sum(c.collection_amount) as collect,a.debit_type_updated FROM dd_account_debit b LEFT JOIN dd_customers a on  a.id = b.customer_id  " +
                " Left JOIN (SELECT collection_amount,collection_date,customer_id,debit_id,discount from dd_collection GROUP BY debit_id ) c on c.debit_id = b.id  WHERE  a.tracking_id = ?  AND b.active_status = 0 GROUP BY b.id ";
//   String mm = "SELECT b.*,sum(c.co)";
        Cursor cursor = database.rawQuery(mm, new String[]{dateString,todateee,String.valueOf(ses),dateString,todateee});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("collect");
                    xcol = cursor.getString(index);
                    String vc = cursor.getString(index);
                    Log.d("ins3",vc);
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
            }else{
                cursor.close();
                sqlite.close();
                database.close();
                clocol.setText("\u20B9"+""+"0");
//                doc.setText("0");
//                inst.setText("0");
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
            clocol.setText("\u20B9"+""+"0");
//            doc.setText("0");
//            inst.setText("0");
        }

    }

    //onBackPressed() - function called when back button is pressed
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public void onBackPressed() {
        Intent na = new Intent(Today_report.this,NavigationActivity.class);
        startActivity(na);
        finish();
    }
    //onCreateOptionsMenu() - when navigation menu created
    //Params - Menu
    //Created on 25/01/2022
    //Return - NULL
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

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

        return super.onOptionsItemSelected(item);
    }
    //onNavigationItemSelected() - when navigation item pressed
    //Params - selected item
    //Created on 25/01/2022
    //Return - NULL
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        if (id == R.id.company) {
            // Handle the camera action
            Intent new1 = new Intent(Today_report.this,MainActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.user) {
            Intent new1 = new Intent(Today_report.this,HomeActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.debit) {
            Intent new1 = new Intent(Today_report.this,Debit.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.collection) {
            Intent new1 = new Intent(Today_report.this,AllCollection.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.account) {
            Intent new1 = new Intent(Today_report.this,Account.class);
            startActivity(new1);
            finish();
        }  else if (id == R.id.report) {
            Intent new1 = new Intent(Today_report.this,ReportActivity.class);
            startActivity(new1);
            finish();
        } else if (id == R.id.customer) {
            Intent new1 = new Intent(Today_report.this,Customer_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.settings) {
            Intent new1 = new Intent(Today_report.this,Settings_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.tally) {
            Intent new1 = new Intent(Today_report.this,Tally_activity.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.employee) {
            Intent new1 = new Intent(Today_report.this,Employee_details.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.today) {
            Intent new1 = new Intent(Today_report.this,Today_report.class);
            startActivity(new1);
            finish();
        }else if (id == R.id.logout) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Today_report.this,R.style.AlertDialogTheme);
            alertbox.setMessage("Are You Sure want to logout");
            alertbox.setTitle("Warning");
            alertbox.setIcon(R.drawable.logo);
            alertbox.setNeutralButton("CANCEL",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {


                        }
                    });
            alertbox.setPositiveButton("LOGOUT",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {

                            editor.clear();
                            editor.putInt("isloginn",0);
                            editor.commit();
                            Intent i = new Intent(Today_report.this,Splash.class);
                            startActivity(i);
                            finish();
                        }
                    });
            alertbox.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
