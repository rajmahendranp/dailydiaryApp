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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdapterclosed;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdapterdelete;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


//Updated on 25/01/2022 by RAMPIT
//used to delete datas
//updateLabel12() - Update textview when date is selected
//popup() - get archeive date popup
//progressbar_load() - Load progressbar
//progre() - Stop progressbar
//updateLabel() - Update textview when date is selected
//updateLabel1() - Update textview when date is selected
//backupDatabase() - Export backup of database
//populateRecyclerView4() - get license
//populateRecyclerView5() - get and show deleted datas
//sendEmail1() - Send email the backup of database
//monthly_firebase1() - upload an backup before delet in firebase
//beforebal() - get all datas to be archieved
//beforebal1() - get all debit datas
//beforebal0() - get all collection datas
//beforebal11() - get all tally datas
//beforebal111() - get all account datas
//all_delete() - delete all closed datas

public class delete_datas extends AppCompatActivity {

    Dialog dialog;
    Calendar myCalendar,myCalendar1;
    String dateString,todateee,todaaaa,pdfname,pdfname1,lice;
    EditText dat,todate;
    Button srch;
    SimpleDateFormat sdf1;
    File pdfFile;
    DatePickerDialog datePickerDialog,datePickerDialog1;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    Integer ses,debitted1;
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> Names2 = new ArrayList<>();
    ArrayList<String> Names3 = new ArrayList<>();
    ArrayList<String> Names4 = new ArrayList<>();
    ArrayList<String> Names5 = new ArrayList<>();
    RecyclerView re;
    TextView noooo;
    RecyclerViewAdapterdelete mAdapter;
    Dialog dialog1;
    Calendar myCalendar2,myCalendar12;
    String dateString2;
    EditText todate2;
    Button archive_closed_account;
    DatePickerDialog datePickerDialog2,datePickerDialog12;
    EditText dat1;
    Integer in;
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
        String localeName = pref.getString("language", "");
        if (localeName == null) {
            localeName = "ta";
        }
        ses = pref.getInt("session", 1);
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dialog = new Dialog(delete_datas.this, R.style.AlertDialogTheme);
        dialog1 = new Dialog(delete_datas.this,R.style.AlertDialogTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_delete_datas);
        setTitle(R.string.final_report);
        ((Callback) getApplication()).datee();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myCalendar = Calendar.getInstance();
        myCalendar1 = Calendar.getInstance();
        re = findViewById(R.id.re);
        noooo = findViewById(R.id.no);
        archive_closed_account = findViewById(R.id.archive_closed_account);
        archive_closed_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup();
            }
        });
        String myFormat1 = "dd/MM/yyyy";//In which you need put here
        sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        String myFormat11 = "yyyy/MM/dd";//In which you need put here
        SimpleDateFormat sdf11 = new SimpleDateFormat(myFormat11, Locale.US);
        Calendar calendar11 = Calendar.getInstance();
        calendar11.add(Calendar.DATE, -1);
        dat = findViewById(R.id.date);
        dateString = sdf11.format(calendar11.getTime());
        dat.setText(sdf1.format(calendar11.getTime()));
        todate = findViewById(R.id.todate);
        todate.setVisibility(View.GONE);
        srch = findViewById(R.id.searchh);
        populateRecyclerView4();
        populateRecyclerView5();
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
                    datePickerDialog =  new DatePickerDialog(delete_datas.this,R.style.DialogTheme, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH ));
                }
                else{
                    datePickerDialog =  new DatePickerDialog(delete_datas.this,R.style.DialogTheme1, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH ));
                }
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, -1);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
                String myFormat = "yyyy/MM/dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//                dateString = sdf.format(myCalendar.getTime());
//                dat.setText(sdf1.format(myCalendar.getTime()));

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
                    datePickerDialog1 =  new DatePickerDialog(delete_datas.this,R.style.DialogTheme, date1, myCalendar1
                            .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                            myCalendar1.get(Calendar.DAY_OF_MONTH -1));
                }else{
                    datePickerDialog1 =  new DatePickerDialog(delete_datas.this,R.style.DialogTheme1, date1, myCalendar1
                            .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                            myCalendar1.get(Calendar.DAY_OF_MONTH -1));
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
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Log.d("ssssseeeeee",todateee+" "+dateString);
                        if(dateString==null){

                        }else{
                            progressbar_load();
//                            backupDatabase();
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            beforebal();
                                            beforebal1();
                                            beforebal0();
                                            beforebal11();
                                            beforebal111();
                                        }
                                    });
                                }
                            }, 500);

                        }
                    }
                });
            }
        });

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

    //popup() - get archeive date popup
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
                    datePickerDialog12 =  new DatePickerDialog(delete_datas.this,R.style.DialogTheme, date, myCalendar12
                            .get(Calendar.YEAR), myCalendar12.get(Calendar.MONTH),
                            myCalendar12.get(Calendar.DAY_OF_MONTH ));
                }
                else{
                    datePickerDialog12 =  new DatePickerDialog(delete_datas.this,R.style.DialogTheme1, date, myCalendar12
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
                AlertDialog.Builder alertbox = new AlertDialog.Builder(delete_datas.this, R.style.AlertDialogTheme);
                String rer = getString(R.string.please_check_battery);
                String rer1 = getString(R.string.warning);
                String rer2 = getString(R.string.ok);
                String rer3 = getString(R.string.cancel);
                alertbox.setCancelable(false);
                alertbox.setMessage(rer);
                alertbox.setTitle(rer1);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setNegativeButton(rer2,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressbar_load();
                                    }
                                });
                                Log.d("dateString2",dateString2);
//                                backupDatabase();
                                beforebal();
                            }

                        });
                alertbox.setPositiveButton(rer3,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                progre();
                            }

                        });

                alertbox.show();

            }
        });

        dialog1.show();
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
            final AlertDialog.Builder alertbox = new AlertDialog.Builder(delete_datas.this,R.style.AlertDialogTheme);
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

    //populateRecyclerView4() - get license
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

    //populateRecyclerView5() - get and show deleted datas
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    private void populateRecyclerView5() {
        Names5.clear();
        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
        String MY_QUERY = "SELECT * FROM dd_collection where id = ?";
        String whereClause = "tracking_id=?";
        String[] whereArgs = new String[]{String.valueOf(ses)};
//        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{idd});
        String[] columns = {"debit_amount",
                "interest","id","document_charge","collection_amount","discount","other_fee","credit","debit","created_date","updated_date","tracking_id"};
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
                    if(lice == null || lice.equalsIgnoreCase("") || lice.equalsIgnoreCase("null")){
                        lice = "0";
                    }
                    if(emacc == null || emacc.equalsIgnoreCase("") || emacc.equalsIgnoreCase("null")){
                        emacc = "0";
                    }
                    if(ema == null || ema.equalsIgnoreCase("") || ema.equalsIgnoreCase("null")){
                        ema = "0";
                    }
                    if(exp == null || exp.equalsIgnoreCase("") || exp.equalsIgnoreCase("null")){
                        exp = "0";
                    }
                    if(lann == null || lann.equalsIgnoreCase("") || lann.equalsIgnoreCase("null")){
                        lann = "0";
                    }
                    if(pdfpp == null || pdfpp.equalsIgnoreCase("") || pdfpp.equalsIgnoreCase("null")){
                        pdfpp = "0";
                    }
                    if(pdfp1p == null || pdfp1p.equalsIgnoreCase("") || pdfp1p.equalsIgnoreCase("null")){
                        pdfp1p = "0";
                    }
                    if(pdfp2p == null || pdfp2p.equalsIgnoreCase("") || pdfp2p.equalsIgnoreCase("null")){
                        pdfp2p = "0";
                    }

                    Names5.add(iid+",,,"+lice+",,,"+emacc+",,,"+ema+",,,"+exp+",,,"+lann+",,,"+pdfpp+",,,"+pdfp1p+",,,"+pdfp2p);

//                    pdfpass123 = cursor.getString(index);

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
            if(Names5.size() == 0){
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        progre();
                    }
                });
                re.setVisibility(View.GONE);
                noooo.setVisibility(View.VISIBLE);
            }else {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        progre();
                    }
                });
                re.setVisibility(View.VISIBLE);
                noooo.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    Log.d("Names5",String.valueOf(Names5.size()));
                    mAdapter = new RecyclerViewAdapterdelete(getApplicationContext(), Names5);
                    re.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    re.setLayoutManager(layoutManager);
                    re.setItemViewCacheSize(1000);
                    re.setHasFixedSize(true);
                    re.setNestedScrollingEnabled(false);
                    mAdapter.onAttachedToRecyclerView(re);
                }

            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
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
            AlertDialog.Builder alertbox = new AlertDialog.Builder(delete_datas.this,R.style.AlertDialogTheme);
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

                            Intent i = new Intent(delete_datas.this,Settings_activity.class);
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
                "(SELECT sum(b.debit_amount) FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE b.updated_date <= ?  AND a.tracking_id = ? AND b.active_status = 1) as debitted1 ," +
                "(SELECT sum(b.debit_amount) FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE b.updated_date <= ?  AND a.tracking_id = ? AND b.active_status = 0) as debitted ," +
                "(SELECT sum(b.document_charge) FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE b.updated_date <= ?  AND a.tracking_id = ? AND b.active_status = 0) as document_charge ," +
                "(SELECT sum(b.interest) FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE b.updated_date <= ?  AND a.tracking_id = ? AND b.active_status = 0) as interest ," +
                "(SELECT sum(a.amount) FROM dd_accounting a LEFT JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE a.accounting_date <= ? AND a.tracking_id = ? AND b.acc_type = '1') as acc_credited ," +
                "(SELECT sum(a.amount) FROM dd_accounting a LEFT JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE a.accounting_date <= ? AND a.tracking_id = ? AND b.acc_type = '2') as acc_debited ," +
                "(SELECT sum(a.collection_amount) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 0 AND c.updated_date <= ? ) as collected," +
                "(SELECT sum(a.discount) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 0 AND c.updated_date <= ? ) as discounted ," +
                "(SELECT sum(a.discount) FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 0 AND c.updated_date <= ? ) as closeddiscount " +
                " FROM dd_customers a LEFT JOIN dd_account_debit b on b.customer_id = a.id WHERE b.updated_date <= ?  AND a.tracking_id = ? ";
        String sess = String.valueOf(ses);
        Log.d("ssssseeeeee",sess+" "+dateString2);
        Cursor cursor = database.rawQuery(NIP, new String[]{dateString2,sess,dateString2,sess,dateString2,sess,dateString2,sess,dateString2,sess,dateString2,sess,sess,dateString2,
                sess,dateString2,sess,dateString2,dateString2,sess});
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
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(delete_datas.this,R.style.AlertDialogTheme);
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
                    String finalAcc_credit = acc_credit;
                    String finalAcc_debit = acc_debit;
                    alertbox.setPositiveButton(ook,
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                    Log.d("finalDebit",finalDebit);
                                    Log.d("finalDocc",finalDocc);
                                    Log.d("finalIntre",finalIntre);
                                    Log.d("finalCollect",finalCollect);
                                    Log.d("finalDiscount",finalDiscount);
                                    Log.d("finalAcc_credit",finalAcc_credit);
                                    Log.d("finalAcc_debit",finalAcc_debit);
                                    dialog1.cancel();
                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                                    String formattedDate = df.format(c.getTime());
                                    SQLiteHelper sqlite = new SQLiteHelper(delete_datas.this);
                                    SQLiteDatabase database = sqlite.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put("debit_amount", finalDebit);
                                    values.put("document_charge", finalDocc);
                                    values.put("interest", finalIntre);
                                    values.put("collection_amount", finalCollect);
                                    values.put("discount", finalDiscount);
                                    values.put("tracking_id", String.valueOf(ses));
                                    values.put("credit", finalAcc_credit);
                                    values.put("debit", finalAcc_debit);
                                    values.put("other_fee", "0");
//                    values.put("tracking_id", String.valueOf(ses));
                                    values.put("created_date", dateString2);
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
                                                    backupDatabase();
                                                    beforebal1();
                                                    beforebal0();
                                                    beforebal11();
                                                    beforebal111();
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

                                    progre();
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
        Names.clear();
//        Names2.clear();
        SQLiteHelper sqlite = new SQLiteHelper(this);
        SQLiteDatabase database = sqlite.getReadableDatabase();
        String NIP =
//                "SELECT c.*,a.id as ID , sum(c.debit_amount) as sum_debit FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 0 AND c.debit_date < ?";
                "SELECT c.* , sum(c.debit_amount) as sum_debit FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id  WHERE " +
                        " b.tracking_id = ? AND c.active_status = 0 AND c.updated_date <= ? GROUP BY c.id";

        String sess = String.valueOf(ses);
        Log.d("ssssseeeeee111",sess+" "+dateString2);
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
                    Log.d("sum_debit",sum_debit);
                    Names.add(debit);
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
                "SELECT c.*,a.id as ID , sum(c.debit_amount) as sum_debit FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id " +
                        "LEFT JOIN dd_collection a on a.debit_id = c.id WHERE  b.tracking_id = ? AND c.active_status = 0 AND c.updated_date <= ? GROUP BY a.id";
//                "SELECT c.* , sum(c.debit_amount) as sum_debit FROM dd_customers b LEFT JOIN dd_account_debit c on c.customer_id = b.id  WHERE  b.tracking_id = ? AND c.active_status = 0 AND c.debit_date < ?";

        String sess = String.valueOf(ses);
        Log.d("ssssseeeeee111",sess+" "+dateString2);
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
                "SELECT * FROM dd_tally WHERE created_date <= ? AND tracking_id = ?";
        String sess = String.valueOf(ses);
        Log.d("ssssseeeeee111",sess+" "+dateString2);
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

    //beforebal111() - get all account datas
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void beforebal111(){
        Names4.clear();
        SQLiteHelper sqlite = new SQLiteHelper(this);
        SQLiteDatabase database = sqlite.getReadableDatabase();
        String NIP =
                "SELECT * FROM dd_accounting  WHERE accounting_date <= ? AND tracking_id = ? ";
        String sess = String.valueOf(ses);
        Log.d("ssssseeeeee111",sess+" "+dateString2);
        Cursor cursor = database.rawQuery(NIP, new String[]{dateString2,sess});
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("id");
                    String debit = cursor.getString(index);
//                    Log.d("accounttttt",debit);

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

    //all_delete() - delete all closed datas
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void all_delete(){
        if(debitted1 > 0){


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
            for(int l=0;l<Names4.size();l++){
                String s = Names4.get(l);
                SQLiteHelper sqlite = new SQLiteHelper(getApplicationContext());
                SQLiteDatabase database = sqlite.getReadableDatabase();
                String table = "dd_accounting";
                String whereClause = "id=?";
//        database.delete(table, whereClause, whereArgs);
                String[] whereArgs = new String[] {s};
                database.delete(table, whereClause, whereArgs);
                sqlite.close();
                database.close();
            }
            Integer ssii = Names2.size() - 1 ;
            Integer ssii0 = Names2.size() ;
            if(ssii0<=0){
                Integer sass = Names.size() - 1 ;
                Integer sass0 = Names.size() ;
                if(sass0<=0){
                    progre();
                    Intent dddd = new Intent(delete_datas.this,delete_datas.class);
                    startActivity(dddd);
                    finish();
                }else{
                    for(int i=0;i<Names.size();i++){
                        String s = Names.get(i);
                        SQLiteHelper sqlite = new SQLiteHelper(getApplicationContext());
                        SQLiteDatabase database = sqlite.getReadableDatabase();
                        String table = "dd_account_debit";
                        String whereClause = "id=?";

//        database.delete(table, whereClause, whereArgs);
                        String[] whereArgs = new String[] {s};
                        database.delete(table, whereClause, whereArgs);
                        sqlite.close();
                        database.close();
                        if(sass.equals(i)){
                            progre();
                            AlertDialog.Builder alertbox = new AlertDialog.Builder(delete_datas.this, R.style.AlertDialogTheme);
                            String rer = getString(R.string.account_should_close);
                            String rer1 = getString(R.string.warning);
                            String rer2 = getString(R.string.ok);
                            alertbox.setCancelable(false);
                            alertbox.setMessage(rer);
                            alertbox.setTitle(rer1);
                            alertbox.setIcon(R.drawable.dailylogo);
                            alertbox.setPositiveButton(rer2,
                                    new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface arg0,
                                                            int arg1) {
                                            Intent dddd = new Intent(delete_datas.this,delete_datas.class);
                                            startActivity(dddd);
                                            finish();
                                        }

                                    });
                            alertbox.show();
                        }
                    }
                }



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
                        Integer sass = Names.size() - 1 ;
                        Integer sass0 = Names.size();
                        if(sass0<=0){
                            progre();
                            Intent dddd = new Intent(delete_datas.this,delete_datas.class);
                            startActivity(dddd);
                            finish();
                        }else{
                            for(int i=0;i<Names.size();i++){
                                String s1 = Names.get(i);
                                SQLiteHelper sqlite1 = new SQLiteHelper(getApplicationContext());
                                SQLiteDatabase database1 = sqlite1.getReadableDatabase();
                                String table1 = "dd_account_debit";
                                String whereClause1 = "id=?";

//        database.delete(table, whereClause, whereArgs);
                                String[] whereArgs1 = new String[] {s1};
                                database1.delete(table1, whereClause1, whereArgs1);
                                sqlite1.close();
                                database1.close();
                                if(sass.equals(i)){
                                    progre();
                                    AlertDialog.Builder alertbox = new AlertDialog.Builder(delete_datas.this, R.style.AlertDialogTheme);
                                    String rer = getString(R.string.account_should_close);
                                    String rer1 = getString(R.string.warning);
                                    String rer2 = getString(R.string.ok);
                                    alertbox.setCancelable(false);
                                    alertbox.setMessage(rer);
                                    alertbox.setTitle(rer1);
                                    alertbox.setIcon(R.drawable.dailylogo);
                                    alertbox.setPositiveButton(rer2,
                                            new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface arg0,
                                                                    int arg1) {
                                                    Intent dddd = new Intent(delete_datas.this,delete_datas.class);
                                                    startActivity(dddd);
                                                    finish();
                                                }

                                            });
                                    alertbox.show();
                                }
                            }
                        }

                    }
                }
            }

//            progre();

        }
        else{

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
            for(int l=0;l<Names4.size();l++){
                String s = Names4.get(l);
                SQLiteHelper sqlite = new SQLiteHelper(getApplicationContext());
                SQLiteDatabase database = sqlite.getReadableDatabase();
                String table = "dd_accounting";
                String whereClause = "id=?";
//        database.delete(table, whereClause, whereArgs);
                String[] whereArgs = new String[] {s};
                database.delete(table, whereClause, whereArgs);
                sqlite.close();
                database.close();
            }
            Integer ssii = Names2.size() - 1 ;
            Integer ssii0 = Names2.size();
            if(ssii0<=0){
                Integer saas = Names.size() - 1 ;
                Integer saas0 = Names.size() ;
                if(saas0<=0){
                    progre();
                    Intent dddd = new Intent(delete_datas.this,delete_datas.class);
                    startActivity(dddd);
                    finish();
                }else{
                    for(int i=0;i<Names.size();i++){
                        String s = Names.get(i);
                        SQLiteHelper sqlite = new SQLiteHelper(getApplicationContext());
                        SQLiteDatabase database = sqlite.getReadableDatabase();
                        String table = "dd_account_debit";
                        String whereClause = "id=?";

//        database.delete(table, whereClause, whereArgs);
                        String[] whereArgs = new String[] {s};
                        database.delete(table, whereClause, whereArgs);
                        sqlite.close();
                        database.close();
                        if(saas.equals(i)){
                            progre();
                            Intent dddd = new Intent(delete_datas.this,delete_datas.class);
                            startActivity(dddd);
                            finish();
                        }
                    }
                }

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

                        Integer saas = Names.size() - 1 ;
                        Integer saas0 = Names.size()  ;
                        if(saas0<=0){
                            progre();
                            Intent dddd = new Intent(delete_datas.this,delete_datas.class);
                            startActivity(dddd);
                            finish();
                        }else{
                            for(int i=0;i<Names.size();i++){
                                String s1 = Names.get(i);
                                SQLiteHelper sqlite1 = new SQLiteHelper(getApplicationContext());
                                SQLiteDatabase database1 = sqlite1.getReadableDatabase();
                                String table1 = "dd_account_debit";
                                String whereClause1 = "id=?";

//        database.delete(table, whereClause, whereArgs);
                                String[] whereArgs1 = new String[] {s1};
                                database1.delete(table1, whereClause1, whereArgs1);
                                sqlite1.close();
                                database1.close();
                                if(saas.equals(i)){
                                    progre();
                                    Intent dddd = new Intent(delete_datas.this,delete_datas.class);
                                    startActivity(dddd);
                                    finish();
                                }
                            }
                        }


//                        progre();
//                        Intent dddd = new Intent(delete_datas.this,delete_datas.class);
//                        startActivity(dddd);
//                        finish();
                    }
                }
            }
        }

    }

}
