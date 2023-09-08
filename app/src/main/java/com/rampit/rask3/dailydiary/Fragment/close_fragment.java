package com.rampit.rask3.dailydiary.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.rampit.rask3.dailydiary.Adapter.Close_acc_adapter;
import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdapterdelete;
import com.rampit.rask3.dailydiary.R;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;
import com.rampit.rask3.dailydiary.test_tab_activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

//Updated on 25/01/2022 by RAMPIT
//Show debit that was active after closed
//progressbar_load() - Load progressbar
//progressbar_stop() - Stop progressbar
//closed() - get closed debit
//closed1() - get closed debit


public class close_fragment extends Fragment {
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
    Integer ses,dayses,debitted1;
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> Names2 = new ArrayList<>();
    ArrayList<String> Names3 = new ArrayList<>();
    ArrayList<String> Names4 = new ArrayList<>();
    ArrayList<String> Names5 = new ArrayList<>();
    RecyclerView re;
    TextView noooo;
    RecyclerViewAdapterdelete mAdapter;
    Close_acc_adapter mAdapter111;

    Dialog dialog1;
    Calendar myCalendar2,myCalendar12;
    String dateString2;
    EditText todate2;
    Button archive_closed_account;
    DatePickerDialog datePickerDialog2,datePickerDialog12;
    EditText dat1;
    Integer in;
    Context mContext;
    test_tab_activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        test_tab_activity activity = (test_tab_activity) getActivity();
        this.activity = activity;
        return inflater.inflate(R.layout.activity_closed_datas, parent, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        SharedPreferences pref = mContext.getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ii = pref.getString("theme", "");
        if (ii.equalsIgnoreCase("")) {
            ii = "1";
        }
        Log.d("thee", ii);
        in = Integer.valueOf(ii);
//        if (in == 0) {
//            Log.d("thee", "thh");
//            setTheme(R.style.AppTheme1);
//        } else {
//            Log.d("thee", "th1h");
//            setTheme(R.style.AppTheme11);
////            recreate();
//        }
        String localeName = pref.getString("language", "");
        if (localeName == null) {
            localeName = "ta";
        }
        ses = pref.getInt("session", 1);
        dayses = pref.getInt("day",0);
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dialog = new Dialog(activity, R.style.AlertDialogTheme);
        dialog1 = new Dialog(activity, R.style.AlertDialogTheme);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_delete_datas);
        re = view.findViewById(R.id.re);
        noooo = view.findViewById(R.id.no);
        Log.d("closedddddddd","ee");
        closed();

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
    //closed() - get closed debit
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void closed() {
        Names5.clear();
        sqlite = new SQLiteHelper(mContext);
        database = sqlite.getReadableDatabase();
        SharedPreferences pref = mContext.getSharedPreferences("MyPref", 0); // 0 - for private mode
        Integer dayses = pref.getInt("day",0);
        String NIP = "SELECT (c.debit_amount - SUM(a.collection_amount)) as paid,c.*,b.id AS ID,b.customer_name FROM dd_account_debit c " +
                "INNER JOIN dd_customers b on b.id = c.customer_id INNER JOIN dd_collection a on a.customer_id = b.id WHERE ( c.closeing_date < ? AND b.tracking_id = ? ) GROUP BY b.id  ";
        String NIPP = "SELECT a.*,SUM (b.collection_amount ) as paid, SUM( b.discount ) as disco,c.id as ID,c.order_id_new,c.debit_type,c.customer_name " +
                "FROM dd_customers c LEFT JOIN  dd_account_debit a on a.customer_id = c.id LEFT JOIN dd_collection b on b.debit_id = a.id " +
                " WHERE  c.debit_type = 3 AND a.active_status = 1  GROUP BY c.id";
//        Cursor cursor = database.rawQuery(NIP, new String[]{formattedDate,String.valueOf(ses)});
//        Cursor cursor = database.rawQuery(NIPP,new String[]{String.valueOf(ses) ,String.valueOf(dayses)});
        Cursor cursor = database.rawQuery(NIPP,null);
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("ID");
                    String id = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("id");
                    String did = cursor.getString(index);
//
                    index = cursor.getColumnIndexOrThrow("closeing_date");
                    String closee = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String customer_name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String orderr = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("paid");
                    String total = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("disco");
                    String total1 = cursor.getString(index);

                    if(total == null || total.equalsIgnoreCase("")){
                        total = "0";
                    }
                    if(total1 == null || total1.equalsIgnoreCase("")){
                        total1 = "0";
                    }
                    if(debit == null){
                        debit = "0";
                    }
                    Integer dee = Integer.parseInt(debit);
                    Integer tot = Integer.parseInt(total);
                    Integer tot1 = Integer.parseInt(total1);
                    tot = tot + tot1 ;
                    Integer ppp = dee - tot;
                    Log.d("clse_clse",String.valueOf(dee)+" "+String.valueOf(tot)+" "+String.valueOf(tot1)+" "+String.valueOf(ppp));
                    if(ppp > 0){
                        Integer ooo = Integer.parseInt(orderr);
                        Names5.add(id+",,,"+did+",,,"+debit+",,,"+total+",,,"+total1+",,,"+customer_name);
                    }

                    Log.d("Callidclose_close", debit+" "+total+" "+total1+" "+id+" "+closee);
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
        if(Names5.size()<=0){
            noooo.setVisibility(View.VISIBLE);
            re.setVisibility(View.GONE);
        }else{
            noooo.setVisibility(View.GONE);
            re.setVisibility(View.VISIBLE);
            mAdapter111 = new Close_acc_adapter(mContext,Names5, com.rampit.rask3.dailydiary.Fragment.close_fragment.this);
//                ItemTouchHelper.Callback callback = new ItemMoveCallback(mAdapter);
//                ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//                touchHelper.attachToRecyclerView(recyclerView);
            mAdapter111.setHasStableIds(true);
            re.setAdapter(mAdapter111);
            re.setItemViewCacheSize(1000);
            re.setHasFixedSize(true);
            re.setNestedScrollingEnabled(false);
            mAdapter111.onAttachedToRecyclerView(re);
            re.setLayoutManager(layoutManager);
        }
    }

    //closed1() - get closed debit
    //Params - Application context
    //Created on 25/01/2022
    //Return - NULL
    public void closed1(Context mContext) {
        Names5.clear();
        SQLiteHelper sqlite = new SQLiteHelper(mContext);
        SQLiteDatabase database = sqlite.getReadableDatabase();
        SharedPreferences pref = mContext.getSharedPreferences("MyPref", 0); // 0 - for private mode
        Integer dayses = pref.getInt("day",0);
        String NIP = "SELECT (c.debit_amount - SUM(a.collection_amount)) as paid,c.*,b.id AS ID,b.customer_name FROM dd_account_debit c " +
                "INNER JOIN dd_customers b on b.id = c.customer_id INNER JOIN dd_collection a on a.customer_id = b.id WHERE ( c.closeing_date < ? AND b.tracking_id = ? ) GROUP BY b.id  ";
        String NIPP = "SELECT a.*,SUM (b.collection_amount ) as paid, SUM( b.discount ) as disco,c.id as ID,c.order_id_new,c.debit_type,c.customer_name " +
                "FROM dd_customers c LEFT JOIN  dd_account_debit a on a.customer_id = c.id LEFT JOIN dd_collection b on b.debit_id = a.id " +
                " WHERE  c.debit_type = 3 AND a.active_status = 1  GROUP BY c.id";
//        Cursor cursor = database.rawQuery(NIP, new String[]{formattedDate,String.valueOf(ses)});
//        Cursor cursor = database.rawQuery(NIPP,new String[]{String.valueOf(ses) ,String.valueOf(dayses)});
        Cursor cursor = database.rawQuery(NIPP,null);
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("ID");
                    String id = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("id");
                    String did = cursor.getString(index);
//
                    index = cursor.getColumnIndexOrThrow("closeing_date");
                    String closee = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String customer_name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String orderr = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("paid");
                    String total = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("disco");
                    String total1 = cursor.getString(index);

                    if(total == null || total.equalsIgnoreCase("")){
                        total = "0";
                    }
                    if(total1 == null || total1.equalsIgnoreCase("")){
                        total1 = "0";
                    }
                    if(debit == null){
                        debit = "0";
                    }
                    Integer dee = Integer.parseInt(debit);
                    Integer tot = Integer.parseInt(total);
                    Integer tot1 = Integer.parseInt(total1);
                    tot = tot + tot1 ;
                    Integer ppp = dee - tot;
                    Log.d("clse_clse",String.valueOf(dee)+" "+String.valueOf(tot)+" "+String.valueOf(tot1)+" "+String.valueOf(ppp));
                    if(ppp > 0){
                        Integer ooo = Integer.parseInt(orderr);
                        Names5.add(id+",,,"+did+",,,"+debit+",,,"+total+",,,"+total1+",,,"+customer_name);
                    }

                    Log.d("Callidclose_close", debit+" "+total+" "+total1+" "+id+" "+closee);
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
        if(Names5.size()<=0){
            noooo.setVisibility(View.VISIBLE);
            re.setVisibility(View.GONE);
        }else{
            noooo.setVisibility(View.GONE);
            re.setVisibility(View.VISIBLE);
          mAdapter111.notifyDataSetChanged();
        }
    }


    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext) {
        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(mContext) {

                private static final float SPEED = 5f;// Change this value (default=25f)

                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return SPEED / displayMetrics.densityDpi;
                }

            };
            smoothScroller.setTargetPosition(position);
            startSmoothScroll(smoothScroller);
        }
    };

}
