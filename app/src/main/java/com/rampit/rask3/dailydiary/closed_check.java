package com.rampit.rask3.dailydiary;

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
import android.os.Build;
import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rampit.rask3.dailydiary.Adapter.ClosedcheckAdapter;
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

//Updated on 25/01/2022 by RAMPIT
//Close an user manually
//close() - update an user to closed
//list() - get all closed debits
//progressbar_load() - Load progressbar
//progre() - Stop progressbar
//onCreateOptionsMenu() - when navigation menu created
//onOptionsItemSelected() - when navigation item pressed
//onBackPressed() - function called when back button is pressed



public class closed_check extends AppCompatActivity {

    EditText dat,location,debit,doccharge,inter,day,phone,locate,sl;
    TextView clsed,noo,dateee,session,deb_text;
    EditText sl1;
    EditText searchView,searchid;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    public static String TABLENAME4 = "dd_account_debit";
    PopupWindow popupWindow;
    LinearLayout linearLayout1;
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
    String cid11,namm1,phooo1,loccc1,daaab1,doco1,inyt1,opened1,closed1,installed1,tottda1,my1,MY_QUERY1;
    private PopupWindow mPopupWindow;
    Dialog dialog;
    RecyclerView recyclerView;
    TextView no;
    DatePickerDialog datePickerDialog;
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
        dialog = new Dialog(closed_check.this,R.style.AlertDialogTheme);
        setContentView(R.layout.closed_check);
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


        sl1 = findViewById(R.id.slno1);
        clsed = findViewById(R.id.close);
        myCalendar = Calendar.getInstance();
        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        recyclerView = findViewById(R.id.re);
        no = findViewById(R.id.no);
        final LinearLayout lllooo =findViewById(R.id.linearLayout1);
        lllooo.getViewTreeObserver().addOnGlobalLayoutListener(new  ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                lllooo.getWindowVisibleDisplayFrame(r);
                int screenHeight = lllooo.getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;
                if (keypadHeight > screenHeight * 0.15) {
//                    addd.setVisibility(View.VISIBLE);
//                    cancel.setVisibility(View.GONE);
//                    Toast.makeText(closed_check.this,"Keyboard is showing",Toast.LENGTH_LONG).show();
                } else {
//                    addd.setVisibility(View.GONE);
//                    cancel.setVisibility(View.GONE);
//                    Toast.makeText(closed_check.this,"keyboard closed",Toast.LENGTH_LONG).show();
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
      /*  if(in == 0){
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
        }*/
        String sesid = pref.getString("id","");
        String module_i = "3";
        ((Callback)getApplication()).privilege(sesid,module_i);
        adpr = pref.getString("add_privilege","");
        edpr = pref.getString("edit_privilege","");
        depr = pref.getString("delete_privilege","");
        vipr = pref.getString("view_privilege","");

        session.setText(timing);

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


        sl1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() >= 0)
                {
                    String bamount = sl1.getText().toString();
                    if(bamount.equalsIgnoreCase("")){

                    }else {
                        idd = String.valueOf(bamount);
                        Log.d("iid",idd);
                        list();
                    }
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



    }

    //close() - update an user to closed
    //Params - userid
    //Created on 25/01/2022
    //Return - NULL
    public void close(String id){
        SQLiteHelper sqlite = new SQLiteHelper(closed_check.this);
        SQLiteDatabase database = sqlite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("debit_type","3");
        database.update("dd_customers", values, "id = ?", new String[]{id});
        database.close();
        sqlite.close();
        Intent mmr = new Intent(closed_check.this,closed_check.class);
        startActivity(mmr);
        finish();
    }

    //list() - get all closed debits
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void list() {
        Names.clear();
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
        Cursor cursor = database.rawQuery("SELECT c.id as d_id,a.customer_name,a.CID as ID,a.id as id1,b.*,SUM(c.collection_amount) as balance,SUM(b.debit_amount)as ddee,SUM(b.document_charge) as doccc,SUM(b.interest) as intre,a.debit_type FROM dd_customers  a " +
                        "LEFT JOIN dd_account_debit b on b.customer_id = a.id " +
                        "LEFT JOIN dd_collection c on c.debit_id = b.id WHERE a.tracking_id = ? AND a.CID = ?  AND b.active_status = 0 AND a.debit_type IN (0,1,2) GROUP BY b.id  ORDER BY b.id DESC LIMIT 1",
                new String[]{String.valueOf(ses),idd});

        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("ID");
                    String id = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("id1");
                    String id1 = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("d_id");
                    String d_id1 = cursor.getString(index);
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
                    Names.add(id+","+name+","+debit+","+docu+","+inter+","+total+","+typ+","+id1+","+d_id1);
                    Log.d("nama,iid",String.valueOf(Names));
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
        if(Names.size()<=0){
            recyclerView.setVisibility(View.GONE);
            no.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            no.setVisibility(View.GONE);
            ClosedcheckAdapter mAdapter = new ClosedcheckAdapter(getApplicationContext(), Names,closed_check.this);
            recyclerView.setAdapter(mAdapter);
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
            Intent back = new Intent(closed_check.this,Debit.class);
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
        Intent na = new Intent(closed_check.this,Debit.class);
        startActivity(na);
        finish();
    }
}
