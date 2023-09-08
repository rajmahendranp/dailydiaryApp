package com.rampit.rask3.dailydiary;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rampit.rask3.dailydiary.Adapter.MyRecyclerViewAdapter1;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdapterclosed;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.DecimalFormat;
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
//Show all closed accounts
//updateLabel2() - Update textview when date is selected
//updateLabel12() - Update textview when date is selected
//backupDatabase() - Export backup of database
//sendEmail1() - Send email the backup of database
//monthly_firebase1() - upload an backup before delet in firebase
//beforebal() - get all datas to be archieved
//beforebal1() - get all debit datas
//beforebal0() - get all collection datas
//beforebal11() - get all tally datas
//all_delete() - delete all datas
//popup() - select date popup
//updateLabel() - Update textview when date is selected
//updateLabel1() - Update textview when date is selected
//popup() - select date popup
//populateRecyclerView1() -  get all closed datas
//populateRecyclerView2() - get all closed datas
//lastcollection() - get all closed datas last collection amount
//lastcollection1() - get all closed datas last collection amount
//list() - get all closed datas calculations
//list1() - get all closed datas calculations
//progressbar_load() - Load progressbar
//progre() - Stop progressbar
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed



public class Closedaccount_activity extends AppCompatActivity {
    TextView from,to,noo,dateee,session,deb12,doc,inst,totcu1;
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> collect1 = new ArrayList<>();
    ArrayList<String> collect2 = new ArrayList<>();
    ArrayList<String> Names1 = new ArrayList<>();
    ArrayList<Long> ID1 = new ArrayList<>();
    Button search,clear;
    Integer ses,dabamo,docamo,intamo;
    String dateString,dateString1,timing,formattedDate,idi;
    Calendar myCalendar,myCalendar1;
    SQLiteHelper sqlite;
    RecyclerView recyclerView;
    SQLiteDatabase database;
    Integer balanceamount,debitam1,instamt,pamount,pdays,toda,misda,slllno,misam,collectoo,sll;
    Double paiddays,balanceday,totda,dbdate,todate,missiday,missiamo,todaaal;
    String idd,iii,nme,paidamount,installment,totaldays,ddbt,debitdaaa,pp,dbdda,Clo,cloo,xcol,xbal,missed,intrst,language,cloo1;
    RecyclerViewAdapterclosed mAdapter;
    String tablename ="dd_account_debit",Name,idicid;
    String column = "debit_date",lastcollection;
    ImageView seesim;
    Integer in,sno ;
    DatePickerDialog datePickerDialog,datePickerDialog1;
    Dialog dialog;



    Dialog dialog1;
    Calendar myCalendar2,myCalendar12;
    String dateString2,todateee,todaaaa,pdfname,pdfname1,lice;
    EditText dat,todate2;
    Button srch,archive_closed_account;
    SimpleDateFormat sdf1;
    File pdfFile;
    DatePickerDialog datePickerDialog2,datePickerDialog12;
    Integer debitted1;
    EditText dat1;
    ArrayList<String> Names5 = new ArrayList<>();
    ArrayList<String> Names2 = new ArrayList<>();
    ArrayList<String> Names3 = new ArrayList<>();
    ArrayList<String> Names4 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ii = pref.getString("theme","");
        if(ii.equalsIgnoreCase("")){
            ii = "1";
        }
        Log.d("thee",ii);
        in = Integer.valueOf(ii);
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
        dialog = new Dialog(Closedaccount_activity.this,R.style.AlertDialogTheme);
        dialog1 = new Dialog(Closedaccount_activity.this,R.style.AlertDialogTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_closed);
        setTitle(R.string.title_Closed_activity);
        ((Callback)getApplication()).datee();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        archive_closed_account =findViewById(R.id.archive_closed_account);
        archive_closed_account.setVisibility(View.GONE);
        String archive = pref.getString("archive","0");
        String iiiddd =  pref.getString("id","0");
//        if(archive.equalsIgnoreCase("4")){
//            archive_closed_account.setVisibility(View.VISIBLE);
//        }else{
//            if(iiiddd.equalsIgnoreCase("1")){
//                archive_closed_account.setVisibility(View.VISIBLE);
//            }else{
//                archive_closed_account.setVisibility(View.GONE);
//            }
//        }
        archive_closed_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup();
            }
        });
        from = findViewById(R.id.from_date);
        clear =findViewById(R.id.clear);
        to = findViewById(R.id.to_date);
        seesim = findViewById(R.id.sesimg);
        deb12 = findViewById(R.id.deb12);
        inst = findViewById(R.id.inst);
        totcu1 = findViewById(R.id.totcu1);
        doc = findViewById(R.id.doc);
        noo = findViewById(R.id.no);
        recyclerView = findViewById(R.id.re);
        search = findViewById(R.id.search);
        dabamo = 0;
        docamo = 0;
        intamo = 0 ;
        ses = pref.getInt("session", 1);
        String dd = pref.getString("Date","");
        language = pref.getString("language","");
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date debit = df1.parse(dd);
            formattedDate = df.format(debit);
            Log.d("deae",formattedDate);
            String ffff = df1.format(debit);
            from.setText(ffff);
            dateString = formattedDate;
            to.setText(ffff);
            dateString1 = formattedDate;
//            populateRecyclerView1();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateee = findViewById(R.id.da);
        session = findViewById(R.id.sess);
        ((Callback)getApplication()).closed(ses);
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
        myCalendar1 = Calendar.getInstance();
        SimpleDateFormat d3f = new SimpleDateFormat("yyyy/MM/dd");
        dateString = d3f.format(myCalendar1.getTime());
        dateString1 = d3f.format(myCalendar1.getTime());
//        list();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list();
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
        myCalendar = Calendar.getInstance();
//        myCalendar1 = Calendar.getInstance();
        sqlite = new SQLiteHelper(this);
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
        from.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (in == 0){
                    datePickerDialog =  new DatePickerDialog(Closedaccount_activity.this,R.style.DialogTheme, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH ));
                }else{
                    datePickerDialog =  new DatePickerDialog(Closedaccount_activity.this,R.style.DialogTheme1, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH ));
                }
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
                String myFormat = "yyyy/MM/dd";
                String myFormat1 = "dd/MM/yyyy";//In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                dateString = sdf.format(myCalendar.getTime());
                from.setText(sdf1.format(myCalendar.getTime()));
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
        to.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (in == 0){
                    datePickerDialog1 =  new DatePickerDialog(Closedaccount_activity.this,R.style.DialogTheme, date1, myCalendar
                            .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                            myCalendar1.get(Calendar.DAY_OF_MONTH ));
                }else{
                    datePickerDialog1 =  new DatePickerDialog(Closedaccount_activity.this,R.style.DialogTheme1, date1, myCalendar1
                            .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                            myCalendar1.get(Calendar.DAY_OF_MONTH ));
                }
                datePickerDialog1.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog1.show();
                String myFormat = "yyyy/MM/dd";
                String myFormat1 = "dd/MM/yyyy";//In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                dateString1 = sdf.format(myCalendar1.getTime());
                to.setText(sdf1.format(myCalendar1.getTime()));
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("bothdates",dateString+" "+dateString1);
//                populateRecyclerView();

                if(dateString.equalsIgnoreCase("")||dateString1.equalsIgnoreCase("")){}else{

                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    Date newda = null;
                    try {
                        newda = df.parse(dateString);
                        Date debit = df.parse(dateString1);
                        long diffInMillisec =  newda.getTime() - debit.getTime();
                        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                        Log.d("day5",String.valueOf(diffInDays));
                        if(diffInDays > 0){
                            AlertDialog.Builder alertbox = new AlertDialog.Builder(Closedaccount_activity.this,R.style.AlertDialogTheme);
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
                        }else{ Log.d("bothdates",dateString+" "+dateString1);
//                            list();
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            list();
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
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                from.setText("");
                to.setText("");
//                list1();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                list1();
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
        });
    }

    //Archeive  datas
//updateLabel2() - Update textview when date is selected
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void updateLabel2() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateString = sdf.format(myCalendar.getTime());
//        dat.setText(sdf1.format(myCalendar.getTime()));

    }
    //updateLabel12() - Update textview when date is selected
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void updateLabel12() {

        String myFormat = "yyyy/MM/dd";
        String mmm = "dd/MM/yyyy";//In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(mmm, Locale.US);
        dateString2 = sdf.format(myCalendar12.getTime());
        dat1.setText(sdf1.format(myCalendar12.getTime()));
//        todateee = sdf.format(myCalendar1.getTime());
//        todate.setText(sdf1.format(myCalendar1.getTime()));
    }

    //backupDatabase() - Export backup of database
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
        String todaaaa = dff.format(c.getInstance().getTime());
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
        pdfFile = backupDB;
        pdfname = "DD_RRP"+"-"+todaaaa+"_Backup_Mail";
        pdfname1 = "DD_RRP_Backup_Mail";
        if (confirm_OTP.InternetConnection.checkConnection(getApplicationContext())) {
            // Internet Available...
            Log.d("internet","on");
//            date32();

            sendEmail1();
        } else {
            // Internet Not Available...
            Log.d("internet","off");
            final AlertDialog.Builder alertbox = new AlertDialog.Builder(Closedaccount_activity.this,R.style.AlertDialogTheme);
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
                    lice = cursor.getString(index);
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

                }
                while (cursor.moveToNext());
            }
        }
    }

    private void populateRecyclerView5() {
//        Names.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY = "SELECT * FROM dd_collection where id = ?";
        String whereClause = "ID=?";
        String[] whereArgs = new String[] {"1"};
//        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{idd});
        String[] columns = {"debit_amount",
                "interest","id","document_charge","collection_amount","discount","other_fee","credit","debit","created_date","updated_date"};
//        String order = "collection_date";
        Cursor cursor = database.query("dd_delete",
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
                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String lice = cursor.getString(index);
                    Log.d("debit_amount_debit",lice);
                    index = cursor.getColumnIndexOrThrow("id");
                    String iid = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("interest");
                    String  emacc = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("document_charge");
                    String ema = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("collection_amount");
                    String exp = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("discount");
                    String lann = cursor.getString(index);
//                    Log.d("opop",exp1);
                    index = cursor.getColumnIndexOrThrow("credit");
                    String pdfpp = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit");
                    String pdfp1p = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("other_fee");
                    String pdfp2p = cursor.getString(index);



//                    pdfpass123 = cursor.getString(index);

                }
                while (cursor.moveToNext());
            }
        }
    }

    //sendEmail1() - Send email the backup of database
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void sendEmail1() {
        //Getting content for email
//        String email = "abilashmohanan38@gmail.com";
//        String email = "rampittech@gmail.com";
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String email = pref.getString("email","");
        String subject = "Subject";
        String message = "hiiiiiiiii";

        //Creating SendMail object
        Log.d("pdffile", String.valueOf(pdfFile));
        String ppp = String.valueOf(pdfFile);
        if(email.equalsIgnoreCase("")){
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Closedaccount_activity.this,R.style.AlertDialogTheme);
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

                            Intent i = new Intent(Closedaccount_activity.this,Settings_activity.class);
                            startActivity(i);
                            finish();
                        }
                    });
            alertbox.show();
        }else {
            SendMail_noprogress sm = new SendMail_noprogress(this, "rampittech@gmail.com", subject, message, ppp, pdfname);
            //Executing sendmail to send email
            sm.execute();
            SendMail_noprogress sm1 = new SendMail_noprogress(this, "diary.weekly.daily@gmail.com", subject, message, ppp, pdfname);
            //Executing sendmail to send email
            sm1.execute();
            if(lice == null || lice.equalsIgnoreCase("") || lice.equalsIgnoreCase("null")){
            }else{
                monthly_firebase1(Uri.fromFile(pdfFile),lice,"dd_backup");
            }
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    progre();
                }
            });
        }
    }

    //monthly_firebase1() - upload an backup before delet in firebase
    //Params - file uri , file name
    //Created on 25/01/2022
    //Return - NULL
    public void monthly_firebase1(Uri uri, String value, String filee){
        FirebaseStorage storage;
        storage = FirebaseStorage.getInstance();
        StorageMetadata metadata = new StorageMetadata.Builder()
//                .setContentType("image/jpeg")
                .build();
        StorageReference ref = storage.getReference().child(String.valueOf(lice)+"/"+"before_delete"+"/"+String.valueOf(filee));
        String finalValue = value;
        ref.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("dddata11","success");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("dddata11","failure"+" "+String.valueOf(e));

                    }
                });
    }

    //beforebal() - get all datas to be archieved
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void beforebal(){

        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String NIP = "SELECT SUM(b.debit_amount) as debitted11,SUM(b.document_charge) as docuu,SUM(b.interest)as interr, " +
                "(SELECT sum(b.debit_amount) FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE b.debit_date < ?  AND a.tracking_id = ? AND b.active_status = 1) as debitted1 ," +
                "(SELECT sum(b.debit_amount) FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE b.debit_date < ?  AND a.tracking_id = ? AND b.active_status = 0) as debitted ," +
                "(SELECT sum(b.document_charge) FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE b.debit_date < ?  AND a.tracking_id = ? AND b.active_status = 0) as document_charge ," +
                "(SELECT sum(b.interest) FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE b.debit_date < ?  AND a.tracking_id = ? AND b.active_status = 0) as interest ," +
                "(SELECT sum(a.amount) FROM dd_accounting a LEFT JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE a.accounting_date < ? AND a.tracking_id = ? AND b.acc_type = '1') as acc_credited ," +
                "(SELECT sum(a.amount) FROM dd_accounting a LEFT JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE a.accounting_date < ? AND a.tracking_id = ? AND b.acc_type = '2') as acc_debited ," +
                "(SELECT sum(a.collection_amount) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 0 AND a.collection_date < ?) as collected," +
                "(SELECT sum(a.discount) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 0  AND a.collection_date < ?) as discounted ," +
                "(SELECT sum(a.discount) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 0 AND a.collection_date <= ?) as closeddiscount " +
                " FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE b.debit_date < ?  AND a.tracking_id = ? ";
        String sess = String.valueOf(ses);
        Log.d("ssssseeeeee",sess+" "+dateString2);
        Cursor cursor = database.rawQuery(NIP, new String[]{dateString2,sess,dateString2,sess,dateString2,sess,dateString2,sess,dateString2,sess,dateString2,sess,sess,
                dateString2,sess,dateString2,sess,dateString2,dateString2,sess});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("debitted");
                    String debit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debitted1");
                    String debitted11 = cursor.getString(index);
//                    Log.d("sum_debit1",debit);
//                    Log.d("ccccooooo",debit);
//
                    index = cursor.getColumnIndexOrThrow("collected");
                    String collect = cursor.getString(index);
//                    Log.d("ccccooooo",collect);

                    index = cursor.getColumnIndexOrThrow("acc_debited");
                    String acc_debit = cursor.getString(index);
//                    Log.d("acc_debited",acc_debit);
                    index = cursor.getColumnIndexOrThrow("document_charge");
                    String docc = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("interest");
                    String intre = cursor.getString(index);
//
                    index = cursor.getColumnIndexOrThrow("acc_credited");
                    String acc_credit = cursor.getString(index);
//                    Log.d("acc_credited",acc_credit);

                    index = cursor.getColumnIndexOrThrow("discounted");
                    String discount = cursor.getString(index);
                    if(debitted11 == null || debitted11.equalsIgnoreCase("") || debitted11.equalsIgnoreCase("null")){
                        debitted1 = 0;
                    }else{
                        debitted1 =Integer.parseInt(debitted11);
                    }
                    Log.d("sum_debit1", String.valueOf(debitted1));
                    if(debit != null){
                    }else{
                        debit ="0";
                    }
//                    Log.d("ccccooooo",clodebit);
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
                    Integer interest12 = Integer.parseInt(intre);
                    Integer document_charge12 = Integer.parseInt(docc);
                    Integer discount12 = Integer.parseInt(discount);
                    Integer total_bal = interest12 + document_charge12 ;
                    total_bal = total_bal - discount12 ;
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(Closedaccount_activity.this,R.style.AlertDialogTheme);
                    String enn = getString(R.string.are_you_sure_delete)+" "+todateee+" , \u20B9"+" "+String.valueOf(total_bal);
                    String war = getString(R.string.warning);
                    String ook = getString(R.string.ok);
                    String cac = getString(R.string.cancel);
                    alertbox.setMessage(enn);
                    alertbox.setTitle(war);
                    alertbox.setIcon(R.drawable.dailylogo);
                    final String finalDebit = debit;
                    final String finalDocc = docc;
                    final String finalIntre = intre;
                    final String finalCollect = collect;
                    final String finalDiscount = discount;
                    alertbox.setPositiveButton(ook,
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                    Log.d("aaassaasas","asasasass");
                                    dialog1.cancel();
                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                                    String formattedDate = df.format(c.getTime());
                                    SQLiteHelper sqlite = new SQLiteHelper(Closedaccount_activity.this);
                                    SQLiteDatabase database = sqlite.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put("debit_amount", finalDebit);
                                    values.put("document_charge", finalDocc);
                                    values.put("interest", finalIntre);
                                    values.put("collection_amount", finalCollect);
                                    values.put("discount", finalDiscount);
                                    values.put("tracking_id", String.valueOf(ses));
//                    values.put("credit", acc_credit);
//                    values.put("debit", acc_debit);
                                    values.put("other_fee", "0");
//                    values.put("tracking_id", String.valueOf(ses));
                                    values.put("created_date", formattedDate);
                                    database.insert("dd_delete", null, values);
                                    database.close();
                                    sqlite.close();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressbar_load();
                                        }
                                    });

                                    new Timer().schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
//                                                    progressbar_load();
                                                    beforebal1();
                                                    beforebal0();
                                                    beforebal11();
                                                }
                                            });
                                        }
                                    }, 500);

//                                    beforebal111();
                                }
                            });

                    alertbox.setNegativeButton(cac,
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0,
                                                    int arg1) {


                                }
                            });
                    alertbox.show();


//                    Integer cdis = Integer.parseInt(clodiscount);
//                    Integer cdb = Integer.parseInt(clodebit);
//                    Integer cdo = Integer.parseInt(clodocc);
//                    Integer cin = Integer.parseInt(clointre);
                    Integer dbb = Integer.parseInt(debit);
//                    clodeb = cdb;
//                    clodoc = cdo;
//                    cloin = cin;
//                    dbb = dbb + cdb;
                    Integer dio = Integer.parseInt(discount);
                    Integer ac_d = Integer.parseInt(acc_debit);
                    Integer col = Integer.parseInt(collect);
                    col = col - dio ;
                    Log.d("needed10",String.valueOf(dio)+",,"+String.valueOf(col)+",,"+String.valueOf(collect));
                    Integer ac_c = Integer.parseInt(acc_credit);
                    Integer doo = Integer.parseInt(docc);
//                    doo = doo + cdo;
                    Integer in = Integer.parseInt(intre);
//                    in = in + cin;
//                    Integer dbed = dbb - in;
//                    Log.d("needed11",String.valueOf(cdb)+" "+String.valueOf(cdo)+" "+String.valueOf(cin));
//                    Log.d("needed12",String.valueOf(dbb)+" "+String.valueOf(doo)+" "+String.valueOf(in));
//                    Log.d("needed9",String.valueOf(cdis));
////                    Integer cloosed1 = cloosed - closeddisco ;
//                    Log.d("Callidc1212c",String.valueOf(cloosed1));
////                    befo = (ac_c+col+doo+cloosed1) - (dbed+ac_d);
//                    Integer ad12 = ac_c+col+doo+cloosed1;
//                    Integer ad121 = dbed+ac_d;
////                    Log.d("needed5", col+" "+doo+" "+ac_c+","+cloosed+","+cdis+" "+in);
//                    Log.d("needed2",dbed+" "+ac_d);
//                    Log.d("needed3", String.valueOf(ad12));
//                    Log.d("needed4",String.valueOf(ad121));
//                    yesterdaybal = String.valueOf(befo);
//                    Log.d("befobal",String.valueOf(befo));
//                    Integer aftercloseddisco = befo - dabamo;
//                    bef.setText("\u20B9"+" "+String.valueOf(aftercloseddisco));
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

    //beforebal1() - get all debit datas
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void beforebal1(){
        Names5.clear();
//        Names2.clear();
        SQLiteHelper sqlite = new SQLiteHelper(this);
        SQLiteDatabase database = sqlite.getReadableDatabase();
        String NIP =
//                "SELECT c.*,a.id as ID , sum(c.debit_amount) as sum_debit FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 0 AND c.debit_date < ?";
                "SELECT c.* , sum(c.debit_amount) as sum_debit FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id  WHERE  b.tracking_id = ? AND c.active_status = 0 AND c.debit_date < ? GROUP BY c.id";

        String sess = String.valueOf(ses);
        Log.d("ssssseeeeee1113",sess+" "+dateString2);
        Cursor cursor = database.rawQuery(NIP, new String[]{sess,dateString2});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("id");
                    String debit = cursor.getString(index);
                    Log.d("ccccooooo",debit);
//                    index = cursor.getColumnIndexOrThrow("ID");
//                    String debit1 = cursor.getString(index);
//                    Log.d("ccccooooo_debit1",debit1);
                    index = cursor.getColumnIndexOrThrow("sum_debit");
                    String sum_debit = cursor.getString(index);
//                    Log.d("sum_debit",sum_debit);
                    Names5.add(debit);
//                    Names2.add(debit1);

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

    //beforebal0() - get all collection datas
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void beforebal0(){
//        Names.clear();
        Names2.clear();
        SQLiteHelper sqlite = new SQLiteHelper(this);
        SQLiteDatabase database = sqlite.getReadableDatabase();
        String NIP =
                "SELECT c.*,a.id as ID , sum(c.debit_amount) as sum_debit FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 0 AND c.debit_date < ? GROUP BY a.id";
//                "SELECT c.* , sum(c.debit_amount) as sum_debit FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id  WHERE  b.tracking_id = ? AND c.active_status = 0 AND c.debit_date < ?";

        String sess = String.valueOf(ses);
        Log.d("ssssseeeeee1110",sess+" "+dateString2);
        Cursor cursor = database.rawQuery(NIP, new String[]{sess,dateString2});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("id");
                    String debit = cursor.getString(index);
//                    Log.d("ccccooooo",debit);
                    index = cursor.getColumnIndexOrThrow("ID");
                    String debit1 = cursor.getString(index);
//                    Log.d("ccccooooo_debit1",debit1);
                    index = cursor.getColumnIndexOrThrow("sum_debit");
                    String sum_debit = cursor.getString(index);
//                    Log.d("sum_debit",sum_debit);
//                    Names.add(debit);
                    Names2.add(debit1);

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

    //beforebal11() - get all tally datas
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void beforebal11(){
        Names3.clear();
        SQLiteHelper sqlite = new SQLiteHelper(this);
        SQLiteDatabase database = sqlite.getReadableDatabase();
        String NIP =
                "SELECT * FROM dd_tally WHERE created_date < ? AND tracking_id = ?";
        String sess = String.valueOf(ses);
        Log.d("ssssseeeeee1112",sess+" "+dateString2);
        Cursor cursor = database.rawQuery(NIP, new String[]{dateString2,sess});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("id");
                    String debit = cursor.getString(index);
//                    Log.d("talllyyyyyy",debit);

                    Names3.add(debit);


                }
                while (cursor.moveToNext());
                all_delete();
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                all_delete();
                cursor.close();
                sqlite.close();
                database.close();
            }
        }
        else{
            all_delete();
            cursor.close();
            sqlite.close();
            database.close();
        }
    }

    public void beforebal111(){
        Names4.clear();
        SQLiteHelper sqlite = new SQLiteHelper(this);
        SQLiteDatabase database = sqlite.getReadableDatabase();
        String NIP =
                "SELECT * FROM dd_accounting  WHERE accounting_date < ? AND tracking_id = ? ";
        String sess = String.valueOf(ses);
        Log.d("ssssseeeeee111",sess+" "+dateString);
        Cursor cursor = database.rawQuery(NIP, new String[]{dateString,sess});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("id");
                    String debit = cursor.getString(index);
                    Log.d("accounttttt",debit);

                    Names4.add(debit);


                }
                while (cursor.moveToNext());
                all_delete();
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                all_delete();
                cursor.close();
                sqlite.close();
                database.close();
            }
        }else{
            all_delete();
            cursor.close();
            sqlite.close();
            database.close();
        }
    }

    //all_delete() - delete all datas
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void all_delete(){
        Log.d("ssssseeeeee1112",dateString2+" "+String.valueOf(debitted1));
        if(debitted1 > 0){

            for(int i=0;i<Names5.size();i++){
                String s = Names5.get(i);
                SQLiteHelper sqlite = new SQLiteHelper(getApplicationContext());
                SQLiteDatabase database = sqlite.getReadableDatabase();
                String table = "dd_account_debit";
                String whereClause = "id=?";

//        database.delete(table, whereClause, whereArgs);
                String[] whereArgs = new String[] {s};
                database.delete(table, whereClause, whereArgs);
                sqlite.close();
                database.close();
            }

            for(int k=0;k<Names3.size();k++){
                String s = Names3.get(k);
                SQLiteHelper sqlite = new SQLiteHelper(getApplicationContext());
                SQLiteDatabase database = sqlite.getReadableDatabase();
                String table = "dd_tally";
                String whereClause = "id=?";
//        database.delete(table, whereClause, whereArgs);
                String[] whereArgs = new String[] {s};
                database.delete(table, whereClause, whereArgs);
                sqlite.close();
                database.close();
            }
            Integer ssii = Names2.size() - 1 ;
            if(ssii<=0){
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        progre();
                        Intent clos = new Intent(Closedaccount_activity.this,Closedaccount_activity.class);
                        startActivity(clos);
                    }
                });

//                AlertDialog.Builder alertbox = new AlertDialog.Builder(Closedaccount_activity.this, R.style.AlertDialogTheme);
//                String rer = getString(R.string.account_should_close);
//                String rer1 = getString(R.string.warning);
//                String rer2 = getString(R.string.ok);
//
//                alertbox.setMessage(rer);
//                alertbox.setTitle(rer1);
//                alertbox.setIcon(R.drawable.dailylogo);
//                alertbox.setPositiveButton(rer2,
//                        new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface arg0,
//                                                int arg1) {
//
//                            }
//
//                        });
//                alertbox.show();
            }
            else{
                for(int j=0;j<Names2.size();j++){
                    String s = Names2.get(j);
                    SQLiteHelper sqlite = new SQLiteHelper(getApplicationContext());
                    SQLiteDatabase database = sqlite.getReadableDatabase();
                    String table = "dd_collection";
                    String whereClause = "id=?";
//        database.delete(table, whereClause, whereArgs);
                    String[] whereArgs = new String[] {s};
                    database.delete(table, whereClause, whereArgs);
                    sqlite.close();
                    database.close();
                    Log.d("deleted_deleted",s+" "+String.valueOf(ssii)+" "+String.valueOf(j));
                    if(ssii.equals(j)){
                        Log.d("deleted_deleted_123",String.valueOf(ssii)+" "+String.valueOf(j));
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                progre();
                                Intent clos = new Intent(Closedaccount_activity.this,Closedaccount_activity.class);
                                startActivity(clos);
                            }
                        });

//                        AlertDialog.Builder alertbox = new AlertDialog.Builder(Closedaccount_activity.this, R.style.AlertDialogTheme);
//                        String rer = getString(R.string.account_should_close);
//                        String rer1 = getString(R.string.warning);
//                        String rer2 = getString(R.string.ok);
//
//                        alertbox.setMessage(rer);
//                        alertbox.setTitle(rer1);
//                        alertbox.setIcon(R.drawable.dailylogo);
//                        alertbox.setPositiveButton(rer2,
//                                new DialogInterface.OnClickListener() {
//
//                                    public void onClick(DialogInterface arg0,
//                                                        int arg1) {
//
//                                    }
//
//                                });
//                        alertbox.show();
                    }
                }
            }

//            progre();

        }
        else{
            for(int i=0;i<Names5.size();i++){
                String s = Names5.get(i);
                SQLiteHelper sqlite = new SQLiteHelper(getApplicationContext());
                SQLiteDatabase database = sqlite.getReadableDatabase();
                String table = "dd_account_debit";
                String whereClause = "id=?";

//        database.delete(table, whereClause, whereArgs);
                String[] whereArgs = new String[] {s};
                database.delete(table, whereClause, whereArgs);
                sqlite.close();
                database.close();
            }

            for(int k=0;k<Names3.size();k++){
                String s = Names3.get(k);
                SQLiteHelper sqlite = new SQLiteHelper(getApplicationContext());
                SQLiteDatabase database = sqlite.getReadableDatabase();
                String table = "dd_tally";
                String whereClause = "id=?";
//        database.delete(table, whereClause, whereArgs);
                String[] whereArgs = new String[] {s};
                database.delete(table, whereClause, whereArgs);
                sqlite.close();
                database.close();
            }
            Integer ssii = Names2.size() - 1 ;
            if(ssii<=0){
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        progre();
                        Intent clos = new Intent(Closedaccount_activity.this,Closedaccount_activity.class);
                        startActivity(clos);
                    }
                });

            }
            else{
                for(int j=0;j<Names2.size();j++){
                    String s = Names2.get(j);
                    SQLiteHelper sqlite = new SQLiteHelper(getApplicationContext());
                    SQLiteDatabase database = sqlite.getReadableDatabase();
                    String table = "dd_collection";
                    String whereClause = "id=?";
//        database.delete(table, whereClause, whereArgs);
                    String[] whereArgs = new String[] {s};
                    database.delete(table, whereClause, whereArgs);
                    sqlite.close();
                    database.close();
                    Log.d("deleted_deleted",s+" "+String.valueOf(ssii)+" "+String.valueOf(j));
                    if(ssii.equals(j)){
                        Log.d("deleted_deleted_123",String.valueOf(ssii)+" "+String.valueOf(j));
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                progre();
                                Intent clos = new Intent(Closedaccount_activity.this,Closedaccount_activity.class);
                                startActivity(clos);
                            }
                        });

                    }
                }
            }
        }

    }

    //popup() - select date popup
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void popup(){

//        sqlite = new SQLiteHelper(this);
//        database = sqlite.getWritableDatabase();
//        final Dialog dialog = new Dialog(Closedaccount_activity.this);
//        //set layout custom
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog1.setContentView(R.layout.popup_date);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog1.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog1.getWindow().setAttributes(lp);

        dat1 = (EditText) dialog1.findViewById(R.id.date);
        myCalendar12 = Calendar.getInstance();
        String myFormat1 = "dd/MM/yyyy";//In which you need put here
        sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        String myFormat11 = "yyyy/MM/dd";//In which you need put here
        SimpleDateFormat sdf11 = new SimpleDateFormat(myFormat11, Locale.US);
        Calendar calendar11 = Calendar.getInstance();
        calendar11.add(Calendar.DATE, -1);
        dateString2 = sdf11.format(calendar11.getTime());
        todateee = sdf1.format(calendar11.getTime());
        dat1.setText(sdf1.format(calendar11.getTime()));
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar12.set(Calendar.YEAR, year);
                myCalendar12.set(Calendar.MONTH, monthOfYear);
                myCalendar12.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel12();
            }

        };
        dat1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (in == 0){
                    datePickerDialog12 =  new DatePickerDialog(Closedaccount_activity.this,R.style.DialogTheme, date, myCalendar12
                            .get(Calendar.YEAR), myCalendar12.get(Calendar.MONTH),
                            myCalendar12.get(Calendar.DAY_OF_MONTH ));
                }
                else{
                    datePickerDialog12 =  new DatePickerDialog(Closedaccount_activity.this,R.style.DialogTheme1, date, myCalendar12
                            .get(Calendar.YEAR), myCalendar12.get(Calendar.MONTH),
                            myCalendar12.get(Calendar.DAY_OF_MONTH ));
                }
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, -1);
                datePickerDialog12.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog12.show();
                String myFormat = "yyyy/MM/dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//                dateString = sdf.format(myCalendar.getTime());
//                dat.setText(sdf1.format(myCalendar.getTime()));

            }
        });
        Button searchh = (Button)dialog1.findViewById(R.id.searchh);
        searchh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforebal();
            }
        });

        dialog1.show();
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
        from.setText(sdf1.format(myCalendar.getTime()));;
    }
    //updateLabel1() - Update textview when date is selected
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void updateLabel1() {

        String myFormat = "yyyy/MM/dd";
        String myFormat1 = "dd/MM/yyyy";//In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        dateString1 = sdf.format(myCalendar1.getTime());
        to.setText(sdf1.format(myCalendar1.getTime()));

    }

    //populateRecyclerView() - get all debits
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerView() {
        Names.clear();
        sno = 0;
        Log.d("nama","popopop");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = df.format(c.getTime());
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
//        Cursor cursor =database.rawQuery("SELECT SUM(a.collection_amount) as paid,b.customer_name,b.id as ID,c.*,(c.debit_amount-SUM(a.collection_amount)) as balance FROM dd_customers b " +
//                        "LEFT JOIN dd_collection a on b.id =  a.customer_id LEFT JOIN dd_account_debit c on b.id = c.customer_id WHERE ( c.debit_date BETWEEN ?  AND ?) AND" +
//                        " c.closeing_date < ? AND b.tracking_id = ? GROUP BY b.id HAVING balance <= 0 ORDER BY b.id;"
//                ,new String[]{dateString, dateString1,formattedDate, String.valueOf(ses)});
        Cursor cursor = database.rawQuery("SELECT a.customer_name,a.CID as ID,a.id as id1,b.*,SUM(c.collection_amount) as balance,SUM(b.debit_amount)as ddee,SUM(b.document_charge) as doccc,SUM(b.interest) as intre,a.debit_type FROM dd_customers  a " +
                        "LEFT JOIN dd_account_debit b on b.customer_id = a.id " +
                        "LEFT JOIN dd_collection c on c.debit_id = b.id WHERE a.tracking_id = ? AND b.updated_date BETWEEN ? AND ? AND b.active_status = 0 GROUP BY b.id  ORDER BY b.id",
                new String[]{String.valueOf(ses),dateString,dateString1});

        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("ID");
                    String id = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("id1");
                    String id1 = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String name = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debit = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("ddee");
                    String debit1 = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String typ = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("doccc");
                    String docu1 = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("intre");
                    String inter1 = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("document_charge");
                    String docu = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("interest");
                    String inter = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("balance");
                    String total = cursor.getString(index);
                    if(inter1 == null){
                        inter1 = "0";
                    }
                    if(docu1 == null){
                        docu1 = "0";
                    }
                    if(debit1 == null){
                        debit1 = "0";
                    }
                    deb12.setText(debit1);
                    doc.setText(docu1);
                    inst.setText(inter1);
                    sno = sno + 1;
                    Names.add(id+","+name+","+debit+","+docu+","+inter+","+total+","+typ+","+id1);
                    totcu1.setText(String.valueOf(Names.size()));
                    Log.d("nama",String.valueOf(Names));
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                cursor.close();
                sqlite.close();
                database.close();
                totcu1.setText("0");
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
            totcu1.setText("0");
        }


    }

    //populateRecyclerView1() -  get all closed datas
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerView1() {
        Names.clear();
        sno = 0;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = df.format(c.getTime());
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
//        Cursor cursor =database.rawQuery("SELECT SUM(a.collection_amount) as paid,b.customer_name,b.id as ID,c.*,(c.debit_amount-SUM(a.collection_amount)) as balance FROM dd_customers b " +
//                        "LEFT JOIN dd_collection a on b.id =  a.customer_id LEFT JOIN dd_account_debit c on b.id = c.customer_id WHERE ( c.debit_date BETWEEN ?  AND ?) AND" +
//                        " c.closeing_date < ? AND b.tracking_id = ? GROUP BY b.id HAVING balance <= 0 ORDER BY b.id;"
//                ,new String[]{dateString, dateString1,formattedDate, String.valueOf(ses)});
        Cursor cursor = database.rawQuery("SELECT a.customer_name,a.CID as ID,a.id as id1,b.*,SUM(c.collection_amount) as balance,SUM(b.debit_amount)as ddee,SUM(b.document_charge) as doccc,SUM(b.interest) as intre" +
                        " ,a.debit_type FROM dd_customers  a " +
                        "LEFT JOIN dd_account_debit b on b.customer_id = a.id " +
                        "LEFT JOIN dd_collection c on c.debit_id = b.id WHERE a.tracking_id = ? AND  b.active_status = 0 GROUP BY b.id",
                new String[]{String.valueOf(ses)});

        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("ID");
                    String id = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id1");
                    String id1 = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("document_charge");
                    String docu = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_type");
                    String typ = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("interest");
                    String inter = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("balance");
                    String total = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("ddee");
                    String debit1 = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("doccc");
                    String docu1 = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("intre");
                    String inter1 = cursor.getString(index);

                    deb12.setText(debit1);
                    doc.setText(docu1);
                    inst.setText(inter1);
                    sno = sno + 1;
                    Names.add(id+","+name+","+debit+","+docu+","+inter+","+total+","+typ+","+id1+","+lastcollection);
                    Log.d("op0",String.valueOf(Names));
                    totcu1.setText(String.valueOf(Names.size()));
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                cursor.close();
                sqlite.close();
                database.close();
                totcu1.setText("0");
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
            totcu1.setText("0");
        }


    }

    //populateRecyclerView2() - get all closed datas
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void populateRecyclerView2() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = df.format(c.getTime());
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
//        Cursor cursor =database.rawQuery("SELECT SUM(a.collection_amount) as paid,b.customer_name,b.id as ID,c.*,(c.debit_amount-SUM(a.collection_amount)) as balance FROM dd_customers b " +
//                        "LEFT JOIN dd_collection a on b.id =  a.customer_id LEFT JOIN dd_account_debit c on b.id = c.customer_id WHERE ( c.debit_date BETWEEN ?  AND ?) AND" +
//                        " c.closeing_date < ? AND b.tracking_id = ? GROUP BY b.id HAVING balance <= 0 ORDER BY b.id;"
//                ,new String[]{dateString, dateString1,formattedDate, String.valueOf(ses)});
        Cursor cursor = database.rawQuery("SELECT  collection_amount as vv,debit_id,id from dd_collection  ORDER BY id ", null);

        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("vv");
                    String xcol = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_id");
                    String xcol1 = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("id");
                    String xcol13 = cursor.getString(index);

                    Log.d("uioui",xcol+" "+xcol1+" "+xcol13);
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

    //lastcollection() - get all closed datas last collection amount
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void lastcollection (){
//        String MY_QUERY1 = "SELECT collection_amount FROM dd_collection WHERE created_date = ? AND tracking_id = ? ORDER BY order_id DESC LIMIT 0,1 ";
//        Names.clear();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = df.format(c.getTime());
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
//        Cursor cursor =database.rawQuery("SELECT SUM(a.collection_amount) as paid,b.customer_name,b.id as ID,c.*,(c.debit_amount-SUM(a.collection_amount)) as balance FROM dd_customers b " +
//                        "LEFT JOIN dd_collection a on b.id =  a.customer_id LEFT JOIN dd_account_debit c on b.id = c.customer_id WHERE ( c.debit_date BETWEEN ?  AND ?) AND" +
//                        " c.closeing_date < ? AND b.tracking_id = ? GROUP BY b.id HAVING balance <= 0 ORDER BY b.id;"
//                ,new String[]{dateString, dateString1,formattedDate, String.valueOf(ses)});
        Cursor cursor = database.rawQuery("SELECT a.collection_amount FROM dd_customers c LEFT JOIN  dd_account_debit b on b.customer_id = c.id INNER JOIN dd_collection  a on a.debit_id = b.id " +
                        "WHERE b.id = ? AND b.active_status = 0 AND c.tracking_id = ? ORDER BY a.id DESC LIMIT 0,1",
                new String[]{ iii,String.valueOf(ses)});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("collection_amount");
                    String collect = cursor.getString(index);
                    if(collect == null){
                        collect = "0";
                    }
                    lastcollection = collect;
                    collect2.add(collect);
                    Log.d("qw123123",collect);
                    lastcollection1();
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                collect2.add("0");
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

    //lastcollection1() - get all closed datas last collection amount
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void lastcollection1 (){
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT a.collection_amount,a.id FROM dd_account_debit b INNER JOIN dd_collection  a on a.debit_id = b.id " +
                        "WHERE b.id = ? AND b.active_status = 0 ORDER BY a.id ",
                new String[]{ "131"});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("collection_amount");
                    String collect = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("id");
                    String collect1 = cursor.getString(index);

                    if(collect == null){
                        collect = "0";
                    }
                    Log.d("qw12",collect+" "+collect1);
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

    //list() - get all closed datas calculations
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void list(){
        collect1.clear();
        dabamo = 0;
        docamo = 0;
        intamo = 0 ;
        populateRecyclerView();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        String mm = "SELECT a.customer_name,a.id as ID,a.location,a.phone_1,a.debit_type_updated,b.*,c.vv as collect,c.vv1 as discount,c.debit_id " +
                " ,a.debit_type FROM dd_customers  a " +
                "LEFT JOIN dd_account_debit b on b.customer_id = a.id "  +
                "LEFT JOIN (SELECT SUM(collection_amount) as vv,SUM(discount) as vv1,debit_id" +
                " from dd_collection GROUP BY debit_id ORDER BY debit_id ) c ON  b.id = c.debit_id  WHERE a.tracking_id = ? AND  b.active_status = 0 AND  b.updated_date" +
                " BETWEEN ? AND ? GROUP BY b.id  ORDER BY b.id";
        //   String mm = "SELECT b.*,sum(c.co)";
        Cursor cursor = database.rawQuery(mm, new String[]{String.valueOf(ses),dateString,dateString1});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("id");
                    String orde = cursor.getString(index);
                    Log.d("p9100",orde);
                    iii = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);
                    nme = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_date");
                    debitdaaa = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("interest");
                    intrst = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    ddbt = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("document_charge");
                    String ddbt1 = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("interest");
                    String ddbt2 = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("location");
                    String Loc = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("phone_1");
                    String Pho = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("updated_date");
                    Clo = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("collect");
                    xcol = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("discount");
                    String xdisc = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("installment_amount");
                    installment = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_days");
                    totaldays = cursor.getString(index);
                    if(ddbt == null ){
                        ddbt = "0";
                    }
                    if(ddbt1 == null ){
                        ddbt1 = "0";
                    }
                    if(xdisc == null ){
                        xdisc = "0";
                    }
                    if(ddbt2 == null ){
                        ddbt2 = "0";
                    }
                    Integer d1 = Integer.parseInt(ddbt);
                    Integer d2 = Integer.parseInt(ddbt1);
                    Integer d3 = Integer.parseInt(ddbt2);
                    dabamo = dabamo + d1;
                    docamo = docamo + d2 ;
                    intamo = intamo + d3 ;
                    deb12.setText(String.valueOf(dabamo));
                    doc.setText(String.valueOf(docamo));
                    inst.setText(String.valueOf(intamo));
                    String myFormat1 = "dd/MM/yyyy";//In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                    if(debitdaaa == null){
                    }else{
                        try {
                            dbdda = debitdaaa;
                            Date debit = sdf.parse(dbdda);
                            dbdda = sdf1.format(debit);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }}
                    if(Clo == null){}else{
                        try {
                            cloo1 = Clo;
                            cloo = Clo;
                            Date debit = sdf.parse(cloo);
                            cloo = sdf1.format(debit);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }}
                    if(xcol == null){
                        xcol ="0";
                    }
                    paidamount = String.valueOf(xcol);
                    if(installment == null){
                        installment ="0";
                    }
                    if(ddbt == null){
                        ddbt = "0";
                    }
                    if(totaldays == null){
                        totaldays = "0";
                    }
                    if(debitdaaa == null){
                        debitdaaa = "0";
                    }
                    if(totaldays.equalsIgnoreCase("0")){
                        collect1.add("0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0"+"," + "0" +","+ "0"+","+"0"+","+"0");
                    }else {
                        lastcollection();
                        instamt = Integer.parseInt(installment);
                        pamount = Integer.parseInt(paidamount);
                        Double pa = new Double(pamount);
                        Double ins = new Double(instamt);
                        Integer paaiddd = pamount / instamt;
                        paiddays = pa / ins;
                        Log.d("paiddd", String.valueOf(instamt));
//    pp = String.format("%.2f", paiddays);
//                            DecimalFormat df = new DecimalFormat("####.##");
                        pp = String.valueOf(paiddays);
                        pdays = paaiddd;

                        debitam1 = Integer.parseInt(ddbt);
                        balanceamount = debitam1 - pamount;

                        totda = Double.parseDouble(totaldays);
                        balanceday = totda - paiddays;
                        DecimalFormat df = new DecimalFormat("####.##");
                        xbal = df.format(balanceday);
                        try {
                            SimpleDateFormat dff = new SimpleDateFormat("yyyy/MM/dd");
                            Date newda = dff.parse(debitdaaa);
                            Date debit = dff.parse(Clo);
                            long diffInMillisec = debit.getTime() - newda.getTime() ;
                            long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                            Log.d("dates", String.valueOf(diffInDays));
                            totaldays = String.valueOf(diffInDays);
                            toda = Integer.parseInt(totaldays);
                            missiday = paiddays;
                            misda =  toda - pdays;
                            if(misda <= 0){
                                misda = 0 ;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
//                        Calendar c = Calendar.getInstance();
//                        SimpleDateFormat dff = new SimpleDateFormat("yyyy/MM/dd");
//                        String formattedDate = dff.format(c.getTime());
//                        try {
//                            Date newda = dff.parse(formattedDate);
//                            Date debit = dff.parse(debitdaaa);
//                            long diffInMillisec = newda.getTime() - debit.getTime();
//                            long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
//                            Log.d("dates", String.valueOf(diffInDays));
//                            if(diffInDays == 0){
//                                misda = 0;
//                                misam = 0;
//                            }else {
//                                String tod = String.valueOf(diffInDays);
//                                toda = Integer.parseInt(tod);
////                                todaaal = new doub
////        Integer po = Integer.parseInt(pp);
//                                missiday = paiddays;
//                                Integer tota = Integer.parseInt(totaldays);
//                                Double mii1 = new Double(toda);
//                                Double mii2 = new Double(pdays);
//                                misda =  toda - pdays;
////                                Double mii;
////                                mii = mii1 - mii2;
////                                Log.d("mmm", String.valueOf(mii));
////                                if (misda >= tota) {
////                                    misam = balanceamount;
////                                    misda = balanceday.intValue();
////                                    missed = String.valueOf(misda);
////                                    misda = Integer.parseInt(missed);
////                                } else {
////                                    misam = misda * instamt;
////                                }
//                            }
////                mid.setText("Missingdays :"+" "+String.valueOf(misda));
////                mama.setText("Missingamount :"+" "+String.valueOf(misam));
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
                        collect1.add(totaldays + "," + pp + "," + xbal + "," + String.valueOf(misda) + "," + String.valueOf(misam) + "," +
                                String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+"," +intrst+","+installment+","+xdisc+","+lastcollection);

                    }
//                        slno.setText("ID :" + " " + orde);
//                        namee.setText("NAME :" + " " + Name);
//                        opd.setText("Opening Date :" + " " + dbdda);
//                        deb.setText("Debit Amount :" + " " + ddbt);
//                        loc.setText("Location :" + " " + Loc);
//                        pho.setText("Phone :" + " " + Pho);
//                        cd.setText("Closing Date :" + " " + cloo);
//                        tod.setText("Total days :" + " " + totaldays);

                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                cursor.close();
                sqlite.close();
                database.close();
                deb12.setText("0");
                doc.setText("0");
                inst.setText("0");
            }
            Log.d("collleee",String.valueOf(collect1+" "+language));
            if(Names.size() == 0){
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        progre();
                    }
                });
                recyclerView.setVisibility(View.GONE);
                noo.setVisibility(View.VISIBLE);
            }else {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        progre();
                    }
                });
                recyclerView.setVisibility(View.VISIBLE);
                noo.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    mAdapter = new RecyclerViewAdapterclosed(getApplicationContext(), Names,collect1,collect2,language,in);
                }
                recyclerView.setAdapter(mAdapter);
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
            deb12.setText("0");
            doc.setText("0");
            inst.setText("0");
        }

    }

    //list1() - get all closed datas calculations
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void list1(){
        collect1.clear();
        dabamo = 0;
        docamo = 0;
        intamo = 0 ;
        populateRecyclerView1();
        populateRecyclerView2();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getWritableDatabase();
        String mm = "SELECT a.customer_name,a.id as ID,a.location,a.phone_1,a.debit_type_updated,b.*,c.vv as collect,c.vv1 as discount,c.debit_id,b.id as ddid " +
                " ,a.debit_type FROM dd_customers  a " +
                "LEFT JOIN dd_account_debit b on b.customer_id = a.id "  +
                "LEFT JOIN (SELECT SUM(collection_amount) as vv,SUM(discount) as vv1,debit_id from dd_collection GROUP BY debit_id ORDER BY debit_id ) c ON " +
                " c.debit_id = b.id  WHERE a.tracking_id = ? AND  b.active_status = 0  GROUP BY b.id ORDER BY b.id";
        String MY_QUERY = "SELECT b.*,a.customer_name,a.id as ID,a.location,a.phone_1,sum(c.collection_amount) as collect,a.debit_type_updated FROM dd_account_debit b LEFT JOIN dd_customers a on  a.id = b.customer_id  " +
                " Left JOIN (SELECT collection_amount,collection_date,customer_id,debit_id,discount from dd_collection GROUP BY debit_id ) c on c.debit_id = b.id  WHERE  a.tracking_id = ?  AND b.active_status = 0 GROUP BY b.id ";
//   String mm = "SELECT b.*,sum(c.co)";
        Cursor cursor = database.rawQuery(mm, new String[]{String.valueOf(ses)});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("id");
                    String orde = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_id");
                    String orde1 = cursor.getString(index);
                    Log.d("p9",orde1);
                    Log.d("p9",orde);
                    index = cursor.getColumnIndexOrThrow("ddid");
                    String orde2 = cursor.getString(index);
                    Log.d("p91",orde2);
                    iii = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);
                    nme = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_date");
                    debitdaaa = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("interest");
                    intrst = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    ddbt = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("document_charge");
                    String ddbt1 = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("interest");
                    String ddbt2 = cursor.getString(index);
//                    Log.d("ins1",ddbt);
                    index = cursor.getColumnIndexOrThrow("location");
                    String Loc = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("phone_1");
                    String Pho = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("updated_date");
                    Clo = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("collect");
                    xcol = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("discount");
                    String xdisc = cursor.getString(index);
                    Log.d("paiddd123", String.valueOf(xcol));
                    String vc = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("installment_amount");
                    installment = cursor.getString(index);
//                    Log.d("ins2",installment);
                    Log.d("ins3",vc);
                    index = cursor.getColumnIndexOrThrow("debit_days");
                    totaldays = cursor.getString(index);
                    if(ddbt == null ){
                        ddbt = "0";
                    }
                    if(ddbt1 == null ){
                        ddbt1 = "0";
                    }
                    if(ddbt2 == null ){
                        ddbt2 = "0";
                    }
                    Integer d1 = Integer.parseInt(ddbt);
                    Integer d2 = Integer.parseInt(ddbt1);
                    Integer d3 = Integer.parseInt(ddbt2);
                    dabamo = dabamo + d1;
                    docamo = docamo + d2 ;
                    intamo = intamo + d3 ;
                    deb12.setText(String.valueOf(dabamo));
                    doc.setText(String.valueOf(docamo));
                    inst.setText(String.valueOf(intamo));
                    String myFormat1 = "dd/MM/yyyy";//In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                    if(debitdaaa == null){
                    }else{
                        try {
                            dbdda = debitdaaa;
                            Date debit = sdf.parse(dbdda);
                            dbdda = sdf1.format(debit);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }}
                    if(Clo == null){}else{
                        try {
                            cloo1 = Clo;
                            cloo = Clo;
                            Date debit = sdf.parse(cloo);
                            cloo = sdf1.format(debit);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }}
                    if(xcol == null){
                        xcol ="0";
                    }
                    if(xdisc == null){
                        xdisc ="0";
                    }
                    paidamount = String.valueOf(xcol);
                    if(installment == null){
                        installment ="0";
                    }
                    if(ddbt == null){
                        ddbt = "0";
                    }
                    if(totaldays == null){
                        totaldays = "0";
                    }
                    if(debitdaaa == null){
                        debitdaaa = "0";
                    }
                    if(totaldays.equalsIgnoreCase("0")){
                        collect1.add("0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0"+"," + "0" +","+ "0"+","+"0"+","+"0");
                    }else {
                        lastcollection();
                        instamt = Integer.parseInt(installment);
                        pamount = Integer.parseInt(paidamount);
                        Double pa = new Double(pamount);
                        Double ins = new Double(instamt);
                        Integer paaiddd = pamount / instamt;
                        paiddays = pa / ins;
                        Log.d("paiddd", String.valueOf(instamt));
//    pp = String.format("%.2f", paiddays);
//                            DecimalFormat df = new DecimalFormat("####.##");
                        pp = String.valueOf(paiddays);
                        pdays = paaiddd;
                        debitam1 = Integer.parseInt(ddbt);
                        balanceamount = debitam1 - pamount;
                        totda = Double.parseDouble(totaldays);
                        balanceday = totda - paiddays;
                        DecimalFormat df = new DecimalFormat("####.##");
                        xbal = df.format(balanceday);
                        try {
                            SimpleDateFormat dff = new SimpleDateFormat("yyyy/MM/dd");
                            Date newda = dff.parse(debitdaaa);
                            Date debit = dff.parse(Clo);
                            long diffInMillisec = debit.getTime() - newda.getTime() ;
                            long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                            Log.d("dates", String.valueOf(diffInDays));
                            totaldays = String.valueOf(diffInDays);
                            toda = Integer.parseInt(totaldays);
                            missiday = paiddays;
                            misda =  toda - pdays;
                            if(misda <= 0){
                                misda = 0 ;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
//                        Calendar c = Calendar.getInstance();
//                        SimpleDateFormat dff = new SimpleDateFormat("yyyy/MM/dd");
//                        String formattedDate = dff.format(c.getTime());
//                        try {
//                            Date newda = dff.parse(formattedDate);
//                            Date debit = dff.parse(debitdaaa);
//                            long diffInMillisec = newda.getTime() - debit.getTime();
//                            long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
//                            Log.d("dates", String.valueOf(diffInDays));
//                            if(diffInDays == 0){
//                                misda = 0;
//                                misam = 0;
//                            }else {
//                                String tod = String.valueOf(diffInDays);
//                                toda = Integer.parseInt(tod);
////                                todaaal = new doub
////        Integer po = Integer.parseInt(pp);
//                                missiday = paiddays;
//                                Integer tota = Integer.parseInt(totaldays);
//                                Double mii1 = new Double(toda);
//                                Double mii2 = new Double(pdays);
//                                misda =  toda - pdays;
////                                Double mii;
////                                mii = mii1 - mii2;
////                                Log.d("mmm", String.valueOf(mii));
////                                if (misda >= tota) {
////                                    misam = balanceamount;
////                                    misda = balanceday.intValue();
////                                    missed = String.valueOf(misda);
////                                    misda = Integer.parseInt(missed);
////                                } else {
////                                    misam = misda * instamt;
////                                }
//                            }
////                mid.setText("Missingdays :"+" "+String.valueOf(misda));
////                mama.setText("Missingamount :"+" "+String.valueOf(misam));
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
                        collect1.add(totaldays + "," + pp + "," + xbal + "," + String.valueOf(misda) + "," +
                                String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," +
                                dbdda + "," + cloo+"," +intrst+","+installment+","+xdisc+","+lastcollection);
                        Log.d("collleee",String.valueOf(collect1+" "+language));
                    }
//                        slno.setText("ID :" + " " + orde);
//                        namee.setText("NAME :" + " " + Name);
//                        opd.setText("Opening Date :" + " " + dbdda);
//                        deb.setText("Debit Amount :" + " " + ddbt);
//                        loc.setText("Location :" + " " + Loc);
//                        pho.setText("Phone :" + " " + Pho);
//                        cd.setText("Closing Date :" + " " + cloo);
//                        tod.setText("Total days :" + " " + totaldays);
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                cursor.close();
                sqlite.close();
                database.close();
                deb12.setText("0");
                doc.setText("0");
                inst.setText("0");
            }
            Log.d("collleee",String.valueOf(collect1+" "+language));
            if(Names.size() == 0){
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        progre();
                    }
                });
                recyclerView.setVisibility(View.GONE);
                noo.setVisibility(View.VISIBLE);
            }else {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        progre();
                    }
                });
                recyclerView.setVisibility(View.VISIBLE);
                noo.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    mAdapter = new RecyclerViewAdapterclosed(getApplicationContext(), Names,collect1,collect2,language, in);
                }
                recyclerView.setAdapter(mAdapter);
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
            deb12.setText("0");
            doc.setText("0");
            inst.setText("0");
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
            Intent back = new Intent(Closedaccount_activity.this,ReportActivity.class);
            startActivity(back);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
