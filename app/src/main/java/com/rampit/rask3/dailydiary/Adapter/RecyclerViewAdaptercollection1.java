package com.rampit.rask3.dailydiary.Adapter;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.rampit.rask3.dailydiary.Callback;
import com.rampit.rask3.dailydiary.Quick_bulkupdate;
import com.rampit.rask3.dailydiary.Quick_pojo;
import com.rampit.rask3.dailydiary.R;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;
import com.rampit.rask3.dailydiary.bulk_close;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RecyclerViewAdaptercollection1 extends RecyclerView.Adapter<com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdaptercollection1.MyViewHolder> implements Filterable {

    private final Quick_bulkupdate Quick_bulkupdate;
    private ArrayList<String> data;
    private ArrayList<String> onlyid;
    private ArrayList<String> data1;
    private ArrayList<String> curr;
    private ArrayList<String> NNP;
    ArrayList<String> bulk = new ArrayList<>();
    private ArrayList<String> collection = new ArrayList<String>();
    private ArrayList<String> rem_collection = new ArrayList<String>();
    private ArrayList<String> datafull;
    private ArrayList<String> iid;
    private ArrayList<String> collect1;
    HashMap<Integer,String> edittextlist=new HashMap<>();
    HashMap<Integer,String> edittextlist12=new HashMap<>();
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    Integer in ;
    Context context;
    Integer  collection_amount,cam,cam1,fromord,toord,fromord1;
    String camount,formattedDate,customer_id,customer_name,debiittdate,todaydate,formattedDate1;
    int sele,clea;
    String selected,prevorder,cleared,prevCID;
    String num,nam,savn,id,idi,frompos,saa1,deid;
    public static String TABLENAME5 = "dd_collection";
    Integer sesss,lin1;
    Date dateb,datea;
    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
    String myFormat1 = "dd/MM/yyyy";//In which you need put here
    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
    Integer balanceamount,debitam1,instamt,pamount,pdays,toda,misda,misam;
    Double paiddays,balanceday,totda,missiday,missda1,toda2;
    String iii,nme,paidamount,installment,totaldays,ddbt,debitdaaa,pp,dbdda,Clo,cloo,xcol,xbal,Name,Loc,updatedated1,Pho;
    Integer disss,toda1,debitam,othe,mpho;
    Dialog dialog;
    Quick_pojo quick_pojo;




    public RecyclerViewAdaptercollection1(ArrayList<String> data, Integer ses, ArrayList<String> collect1, String dateString, Integer in, Context context, Quick_bulkupdate Quick_bulkupdate, Quick_pojo quick_pojo) {
        this.data = data;
//        this.onlyid = onlyid;
        datafull = collect1;
//        this.data1 = data1;
////        iid = ID;
//        curr = CURR;
//        this.NNP = NNP;
        sesss = ses;
        this.collect1 = collect1;
        formattedDate = dateString;
        formattedDate1 = dateString;
        this.context = context;
        this.Quick_bulkupdate = Quick_bulkupdate;
        this.in = in;
        Log.d("collect1_size",String.valueOf(collect1.size()));
        this.quick_pojo = quick_pojo ;
        try {
            Date debit = df1.parse(formattedDate1);
            formattedDate1 = df.format(debit);
            Log.d("deae1",formattedDate1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Filter getFilter() {
        return null;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        collect1 = new ArrayList<String>();
        if (charText.length() == 0) {
            // mCleanCopyDataset we always contains the unaltered and the filter (full) copy of the list of data
            collect1.addAll(datafull);
        } else {
            for (String item : datafull) {

                // we iterate through all the items on the list and if any item contains the search text, we add it to a new list mDataset
                String[] currencie = item.split(",");
                String d = currencie[4];
                if (d.contains(charText)) {
                    Log.d("fileterd1",item);
                    Log.d("fileterd2",d);
//                    Integer nh = Integer.parseInt(d);
//                    if(nh.equals(Integer.parseInt(charText))){
//                        Log.d("fileterd3",d);
//                    }
                    collect1.add(item);
                }
            }
        }
// method notifyDataSetChanged () allows you to update the list on the screen after filtration
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row33, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        holder.mTitle.setText(data.get(position));
//        holder.setIsRecyclable(false);

        if(in == 0){
//            holder.savv.setBackgroundResource(R.drawable.save_orange1);
//            holder.moree.setBackgroundResource(R.drawable.view_more1);
//            holder.mphone.setCompoundDrawablesWithIntrinsicBounds(R.drawable.phone12, 0, 0, 0);
        }else{
//            holder.savv.setBackgroundResource(R.drawable.save_blue1);
//            holder.moree.setBackgroundResource(R.drawable.view_more2);
//            holder.mphone.setCompoundDrawablesWithIntrinsicBounds(R.drawable.phone123, 0, 0, 0);
        }
        if(position %2 == 1)
        {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.spin5));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.Black));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }
        holder.collect.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        String name = collect1.get(position);
        Log.d("curreeer",name);
        final String[] currencies = name.split(",");
        final String s = currencies[4];
        String sS = currencies[9];
        final String sSs = currencies[2];
        final String sSsss = currencies[8];
        String sSss = currencies[10];
        String sSsss1 = currencies[11];
        final String sSsss12 = currencies[12];
        Log.d("collleee_collleee111",sS+" "+String.valueOf(sSsss12));
        String sSsss5 = currencies[5];
        final String typ = currencies[6];
        Log.d("ttyyppee",typ);
        Integer sSss0 = Integer.parseInt(sSss);
        Integer sSsss10 = Integer.parseInt(sSsss1);
        Integer sSsss50 = Integer.parseInt(sSsss5);
        Integer tototo = sSss0 + sSsss10 ;
        tototo = sSsss50 - tototo ;
        final Integer sSsss_sSsss = Integer.parseInt(sSsss);
        Log.d("total_remaining",String.valueOf(sSss0)+" "+String.valueOf(sSsss10)+" "+String.valueOf(sSsss50)+" "+String.valueOf(tototo));
//        String order_id = currencies[21];
//        Integer od = Integer.parseInt(order_id);
        holder.minst.setText(s);
        debiittdate = "sSsss1";

        holder.mid.setText(sSs);
        holder.mTitle.setText(sS);
        bulk.add(currencies[0]+",,,"+sSsss);
        if(edittextlist.get(Integer.parseInt(sSs))!=null) {
            holder.collect.setText(edittextlist.get(Integer.parseInt(sSs)));
            String ty = edittextlist.get(Integer.parseInt(sSs));
            holder.collect.setSelection(ty.length());
            Log.d("ertyu",String.valueOf(ty)+" "+String.valueOf(sSs));
//            holder.collect.setSelection();
        }
        else{
            if(typ.equalsIgnoreCase("3")){
                holder.collect.setText("0");
            }else{
                if(tototo<=0){
                    holder.collect.setText("0");
                }else{
                    holder.collect.setText(sSsss);
                }
            }
        }
//        holder.debi.setText(sSs);
//        if(edittextlist12.get(Integer.parseInt(s))!=null) {
//            String ty = edittextlist12.get(Integer.parseInt(s));
//            Log.d("po0909",ty);
//
//
//        }else{
////            holder.save12.setTextColor((ContextCompat.getColor(context, R.color.bulk_color)));
//        }
//        if(edittextlist.get(Integer.parseInt(s))!=null) {
//            holder.collect.setText(edittextlist.get(Integer.parseInt(s)));
//            String ty = edittextlist.get(Integer.parseInt(s));
//            holder.collect.setSelection(ty.length());
//            Log.d("ertyu",String.valueOf(ty)+" "+String.valueOf(s));
////            holder.collect.setSelection();
//        }
//        else {
//            if(sSsss.equalsIgnoreCase("0")) {
//                holder.collect.setText("");
//            }else{
//                holder.collect.setText(sSsss);
//                holder.collect.setSelection(sSsss.length());
//            }
//        }
        final String savn = currencies[2];
        final String deid = currencies[0];
        final String cus  = currencies[1];
        final String CID  = currencies[2];
        if(typ.equalsIgnoreCase("3")){
            holder.save.setVisibility(View.GONE);
            holder.save11.setVisibility(View.VISIBLE);
        }else{
            holder.save.setVisibility(View.VISIBLE);
            holder.save11.setVisibility(View.GONE);
        }
        final String nam = currencies[9];
        String colll = collect1.get(position);
        final String[] currencie = colll.split(",");
//        String d = currencie[15];
//        Log.d("po9089",d);
//        String d1 = currencie[1];
////        String d2 = currencie[2];
//        String d3 = currencie[3];
////        String d4 = currencie[4];
//        String d5 = currencie[5];
//        String d6 = currencie[6];
//        String d7= currencie[7];
//        String d8 = currencie[8];
//        String d9 = currencie[9];
//        String d10 = currencie[10];
//        String d11 = currencie[11];
//        String d12= currencie[12];
//        String d13 = currencie[13];
//        String d14 = currencie[14];
//        String d15 = currencie[15];
//        holder.mtot.setText(d);
//        holder.mpad.setText(d1);
//        holder.mbad.setText(d2);
//        holder.mmad.setText(d3);
//        holder.mmam.setText("\u20B9"+" "+d4);
//        holder.mpam.setText("\u20B9"+" "+d5);
//        holder.mbam.setText("\u20B9"+" "+d6);
//        holder.mdd.setText(d7);
//        holder.mcd.setText(d8);
//        holder.marea.setText(d10);
//        holder.mphone.setText(d11);
//        holder.mdisc.setText(d12);
//        holder.mother.setText(d13);
////        String enn = holder.itemView.getContext().getResources().getString(R.string.total_days);
////        holder.mdays.setText(enn+" : "+d14);
//        String enn1 = holder.itemView.getContext().getResources().getString(R.string.installment);
//        holder.minst.setText("\u20B9"+" "+d9);
//        String enn11 = holder.itemView.getContext().getResources().getString(R.string.days);
//        holder.mday.setText(d15)


//        holder.colle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String namee = collect1.get(position);
//                String[] currencies1 = namee.split(",");
//                savn = currencies1[23];
//                Quick_bulkupdate.popup1(savn);
//            }
//        });
        final Integer finalTototo = tototo;

        holder.save11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                String s = holder.collect.getText().toString();
                if(s == null || s.equalsIgnoreCase("") || s.equalsIgnoreCase("null")){
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(holder.itemView.getContext(),R.style.AlertDialogTheme);
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.spin5));
                    String enn = holder.itemView.getContext().getResources().getString(R.string.enter_amount_alert1);
                    String war = holder.itemView.getContext().getResources().getString(R.string.warning);
                    String ook = holder.itemView.getContext().getResources().getString(R.string.ok);
                    String del = holder.itemView.getContext().getResources().getString(R.string.delete);
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
                else{

                    if(rem_collection.size()<=0){
                        Log.d("save_save0",String.valueOf(sSsss12)+" "+s);
                        Integer bb = Integer.parseInt(s);
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                        String dated1 = df.format(c.getTime());
                        SQLiteHelper sqlite = new SQLiteHelper(Quick_bulkupdate);
                        SQLiteDatabase database = sqlite.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("customer_id", cus);
                        values.put("CID", CID);
                        values.put("amount", s);
                        values.put("debit_id",deid);
                        values.put("customer_name",nam);
                        values.put("created_date", dated1);
                        values.put("tracking_id",sesss);
                        database.insert("dd_remaining", null, values);

//                                        totbal.setText("\u20B9"+" "+balat);
//            mAdapter.notifyDataSetChanged();
                        sqlite.close();
                        database.close();
                    }
                    else{
                        for(int y1=0;y1<rem_collection.size();y1++) {
                            String vv00 = rem_collection.get(y1);
                            String[] vv100 = vv00.split(",,,");
                            String s200 = vv100[0];
                            if(s200.equalsIgnoreCase(sSs)){
                                rem_collection.remove(y1);
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                                String dated1 = df.format(c.getTime());
                                SQLiteHelper sqlite = new SQLiteHelper(Quick_bulkupdate);
                                SQLiteDatabase database = sqlite.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put("customer_id", cus);
                                values.put("CID", CID);
                                values.put("amount", s);
                                values.put("debit_id",deid);
                                values.put("customer_name",nam);
                                values.put("created_date", dated1);
                                values.put("tracking_id",sesss);
                                database.insert("dd_remaining", null, values);

//                                        totbal.setText("\u20B9"+" "+balat);
//            mAdapter.notifyDataSetChanged();
                                sqlite.close();
                                database.close();
                                break;
                            }else{
                            }
                        }
                    }
                }

            }
        });


        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                String s = holder.collect.getText().toString();
                if(s == null || s.equalsIgnoreCase("") || s.equalsIgnoreCase("null")){
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(holder.itemView.getContext(),R.style.AlertDialogTheme);
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.spin5));
                    String enn = holder.itemView.getContext().getResources().getString(R.string.enter_amount_alert1);
                    String war = holder.itemView.getContext().getResources().getString(R.string.warning);
                    String ook = holder.itemView.getContext().getResources().getString(R.string.ok);
                    String del = holder.itemView.getContext().getResources().getString(R.string.delete);
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
                else{

                    if(collection.size()<=0){
                        Log.d("save_save0",String.valueOf(sSsss12)+" "+s);
                        Integer bb = Integer.parseInt(s);
                        if(bb> finalTototo){
                        }else{

                            rem_collection.clear();
                            if(sSsss12.equalsIgnoreCase("0")){
                                Quick_bulkupdate.dattee(formattedDate, -1, cus, nam, s, deid,String.valueOf(position));
                            }else{
                                Quick_bulkupdate.dattee(formattedDate, 1, cus, nam, s, deid,String.valueOf(position));
                            }

//                            Quick_bulkupdate.list2();

                        }
                    }
                    else{
                        for(int y=0;y<collection.size();y++) {
                            String vv = collection.get(y);
                            String[] vv1 = vv.split(",,,");
                            String vv2 = vv1[2];
                            Log.d("save_save1",String.valueOf(vv2)+" "+s);
                            Integer bb = Integer.parseInt(s);
                            if(bb> finalTototo){
                            }else{

                                rem_collection.clear();
                                collection.remove(y);
                                if(sSsss12.equalsIgnoreCase("0")){
                                    Quick_bulkupdate.dattee(formattedDate, -1, cus, nam, s, deid,String.valueOf(position));
                                }else{
                                    Quick_bulkupdate.dattee(formattedDate, 1, cus, nam, s, deid,String.valueOf(position));
                                }


//                                Quick_bulkupdate.list2();
                                break;
                            }
                        }

                    }
                }
                Log.d("order_id111",String.valueOf(edittextlist));
            }
        });



/*
        holder.collect.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String namee121 = collect1.get(position);
                String[] currencies121 = namee121.split(",");
                String savn3451 = currencies121[19];
                Log.d("outerr",savn3451);
                Integer bh = Integer.parseInt(savn3451);
                Integer hh = null;
                if(s.length() > 0){
                    hh = Integer.parseInt(String.valueOf(s));
                }else{
                    hh = 0;
                }
                if(bh > 0){
                    if(bh.equals(hh)){
                        Log.d("outerr2",savn3451);
                    }else{
                        Log.d("outerr3",savn3451);
                        if (s.length() > 0) {
                            String namee12 = collect1.get(position);
                            String[] currencies12 = namee12.split(",");
                            String savn345 = currencies12[16];
                            if(in == 0){
                                holder.save12.setTextColor((ContextCompat.getColor(context, R.color.colorPrimary)));
                            }else{
                                holder.save12.setTextColor((ContextCompat.getColor(context, R.color.backblue)));
                            }
                            Log.d("sa1231", String.valueOf(s));
                            edittextlist12.put(Integer.valueOf(savn345), "blue");
                        }
                        else{
                            String namee12 = collect1.get(position);
                            String[] currencies12 = namee12.split(",");
                            String savn345 = currencies12[16];
                            holder.save12.setTextColor(ContextCompat.getColor(context, R.color.bulk_color));
                            edittextlist12.put(Integer.valueOf(savn345), "bulk");
                        }
                    }
                }else{
                    if (s.length() > 0) {
                        String namee12 = collect1.get(position);
                        String[] currencies12 = namee12.split(",");
                        String savn345 = currencies12[16];
                        if(in == 0){
                            holder.save12.setTextColor((ContextCompat.getColor(context, R.color.colorPrimary)));
                        }else{
                            holder.save12.setTextColor((ContextCompat.getColor(context, R.color.backblue)));
                        }
                        Log.d("sa1231", String.valueOf(s));
                        edittextlist12.put(Integer.valueOf(savn345), "blue");
                    }
                    else{
                        String namee12 = collect1.get(position);
                        String[] currencies12 = namee12.split(",");
                        String savn345 = currencies12[16];
                        holder.save12.setTextColor(ContextCompat.getColor(context, R.color.bulk_color));
                        edittextlist12.put(Integer.valueOf(savn345), "bulk");
                    }
                }

            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
            public void afterTextChanged(Editable s) {
                String namee121 = collect1.get(position);
                String[] currencies121 = namee121.split(",");
                String savn3451 = currencies121[19];
                Log.d("outerr11",savn3451);
                Integer bh = Integer.parseInt(savn3451);
                Integer hh = null;
                if(s.length() > 0){
                    hh = Integer.parseInt(String.valueOf(s));
                }else{
                    hh = 0;
                }
                if(bh > 0){
                    if(bh.equals(hh)){
                        Log.d("outerr21",savn3451);
                    }else{
                        Log.d("outerr31",savn3451);
                        if (s.length() <= 0) {
                            String hghg1 = collect1.get(position);
                            String[] nhnh1 = hghg1.split(",");
                            String uy12 = nhnh1[19];
                            Integer retert12 = Integer.parseInt(uy12);
                            String hghg = collect1.get(position);
                            String[] nhnh = hghg.split(",");
                            String uy = nhnh[6];
//                    String uy1 = nhnh[12];
//                    Log.d("disco1",uy1);
                            Integer retert = Integer.parseInt(uy);
                            retert = retert + retert12;
//                    Integer retert2 = Integer.parseInt(uy1);
                            String camount1 = "0";
                            Integer retert1 = Integer.parseInt(camount1);
//                    retert = retert - retert2 + retert12 ;
                            Log.d("rete",String.valueOf(retert));
                            if (retert1 > retert) {
                                String text = holder.collect.getText().toString();
                                holder.collect.setText(text.substring(0, text.length() - 1));
                                String text1 = holder.collect.getText().toString();
                                holder.collect.setSelection(text1.length());
                                AlertDialog.Builder alertbox = new AlertDialog.Builder(holder.itemView.getContext(),R.style.AlertDialogTheme);
                                String enn = holder.itemView.getContext().getResources().getString(R.string.amountgreat);
                                String enn1 = holder.itemView.getContext().getResources().getString(R.string.amountgreat1);
                                String war = holder.itemView.getContext().getResources().getString(R.string.warning);
                                String ook = holder.itemView.getContext().getResources().getString(R.string.ok);
                                String del = holder.itemView.getContext().getResources().getString(R.string.delete);
                                alertbox.setMessage(enn+" "+"\u20B9"+" "+String.valueOf(retert)+" "+enn1);
                                alertbox.setTitle(war);
                                alertbox.setIcon(R.drawable.dailylogo);
                                alertbox.setNeutralButton(ook,
                                        new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface arg0,
                                                                int arg1) {
                                            }
                                        });
                                alertbox.show();
                            } else {
//                dateString = datt.getText().toString();
//                Log.d("datata",dateString);


                                String namee = collect1.get(position);
                                String[] currencies1 = namee.split(",");
                                savn = currencies1[16];
                                Integer nn = Integer.parseInt(savn);
                                for(int i=0;i<onlyid.size();i++)
                                {
//                            Log.d("deae1asd", String.valueOf(i));
                                    Integer io = Integer.valueOf(onlyid.get(i));
                                    String hg = onlyid.get(i);
                                    if(hg.matches(savn))
                                    {
                                        Log.d("deae1asd", String.valueOf(i));
//                                Log.d("deae1asdqwqw", String.valueOf( Arrays.asList(onlyid).indexOf(io)));
                                    }
                                }
                                edittextlist.put(Integer.valueOf(savn), holder.collect.getText().toString());
                                nam = currencies1[17];
                                saa1 = currencies1[23];
                                deid = currencies1[24];
                                collection_amount = Integer.valueOf(currencies1[19]);
                                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                                SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
                                String myFormat1 = "dd/MM/yyyy";//In which you need put here
                                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                                try {
                                    Date debit = df1.parse(formattedDate);
                                    formattedDate = df.format(debit);
                                    Log.d("deae1", savn);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
//                        camount = holder.collect.getText().toString();
//                        Log.d("newdebit", camount);
//                        Log.d("newdebit", String.valueOf(collection_amount));
//                        Log.d("idad", savn);
//                        Log.d("idad", nam);
//                        Log.d("datat", formattedDate1);
                                sqlite = new SQLiteHelper(context);
                                database = sqlite.getWritableDatabase();
                                Calendar c = Calendar.getInstance();
                                String[] columns = {"collection_amount", "id"};
                                String order = "collection_date";
                                String whereClause = "customer_id=? AND collection_date = ?";
                                String[] whereArgs = new String[]{saa1, formattedDate1};
                                Cursor cursor = database.query("dd_collection",
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

                                            index = cursor.getColumnIndexOrThrow("collection_amount");
                                            collection_amount = cursor.getInt(index);

                                            index = cursor.getColumnIndexOrThrow("id");
                                            id = cursor.getString(index);
                                            if (collection_amount == null) {
                                                collection_amount = 0;
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
                                Integer sds = Integer.parseInt(savn);
                                if (sds > 5000) {
                                    bulk.add(saa1 + "," + "0" + "," + collection_amount + "," + nam + "," + deid);
                                } else {
                                    bulk.add(saa1 + "," + "0" + "," + collection_amount + "," + nam + "," + deid);
                                }
                            }
                        }
                        else {
                            String hghg1 = collect1.get(position);
                            String[] nhnh1 = hghg1.split(",");
                            String uy12 = nhnh1[19];
                            Integer retert12 = Integer.parseInt(uy12);
                            String hghg = collect1.get(position);
                            String[] nhnh = hghg.split(",");
                            String uy = nhnh[6];
//                    String uy1 = nhnh[12];
//                    Log.d("disco1",uy1);
                            Integer retert = Integer.parseInt(uy);
                            retert = retert + retert12;
//                    Integer retert2 = Integer.parseInt(uy1);
                            String camount1 = holder.collect.getText().toString();
                            Integer retert1 = Integer.parseInt(camount1);
//                    retert = retert - retert2 + retert12 ;
                            Log.d("rete",String.valueOf(retert));
                            if (retert1 > retert) {
                                String text = holder.collect.getText().toString();
                                holder.collect.setText(text.substring(0, text.length() - 1));
                                String text1 = holder.collect.getText().toString();
                                holder.collect.setSelection(text1.length());
                                AlertDialog.Builder alertbox = new AlertDialog.Builder(holder.itemView.getContext(),R.style.AlertDialogTheme);
                                String enn = holder.itemView.getContext().getResources().getString(R.string.amountgreat);
                                String enn1 = holder.itemView.getContext().getResources().getString(R.string.amountgreat1);
                                String war = holder.itemView.getContext().getResources().getString(R.string.warning);
                                String ook = holder.itemView.getContext().getResources().getString(R.string.ok);
                                String del = holder.itemView.getContext().getResources().getString(R.string.delete);
                                alertbox.setMessage(enn+" "+"\u20B9"+" "+String.valueOf(retert)+" "+enn1);
                                alertbox.setTitle(war);
                                alertbox.setIcon(R.drawable.dailylogo);
                                alertbox.setNeutralButton(ook,
                                        new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface arg0,
                                                                int arg1) {
                                            }
                                        });
                                alertbox.show();
                            } else {
//                dateString = datt.getText().toString();
//                Log.d("datata",dateString);
//                        edittextlist.put(position, holder.collect.getText().toString());
                                String namee = collect1.get(position);
                                String[] currencies1 = namee.split(",");
                                savn = currencies1[16];
                                edittextlist.put(Integer.valueOf(savn), holder.collect.getText().toString());
                                nam = currencies1[17];
                                saa1 = currencies1[23];
                                deid = currencies1[24];
                                collection_amount = Integer.valueOf(currencies1[19]);
                                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                                SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
                                String myFormat1 = "dd/MM/yyyy";//In which you need put here
                                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                                try {
                                    Date debit = df1.parse(formattedDate);
                                    formattedDate = df.format(debit);
                                    Log.d("deae1", savn);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                camount = holder.collect.getText().toString();
                                Log.d("newdebit", camount);
                                Log.d("newdebit", String.valueOf(collection_amount));
                                Log.d("idad", savn);
                                Log.d("idad", nam);
                                Log.d("datat", formattedDate1);
                                Calendar c = Calendar.getInstance();
                                sqlite = new SQLiteHelper(context);
                                database = sqlite.getWritableDatabase();
                                String[] columns = {"collection_amount", "id"};
                                String order = "collection_date";
                                String whereClause = "customer_id=? AND collection_date = ?";
                                String[] whereArgs = new String[]{saa1, formattedDate1};
                                Cursor cursor = database.query("dd_collection",
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

                                            index = cursor.getColumnIndexOrThrow("collection_amount");
                                            collection_amount = cursor.getInt(index);

                                            index = cursor.getColumnIndexOrThrow("id");
                                            id = cursor.getString(index);
                                            if (collection_amount == null) {
                                                collection_amount = 0;
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
                                Integer sds = Integer.parseInt(savn);
                                if (sds > 5000) {
                                    bulk.add(saa1 + "," + camount + "," + collection_amount + "," + nam + "," + deid);
                                } else {
                                    bulk.add(saa1 + "," + camount + "," + collection_amount + "," + nam + "," + deid);
                                }

                            }
                        }
                    }
                }else{
                    if (s.length() <= 0) {
                        String hghg1 = collect1.get(position);
                        String[] nhnh1 = hghg1.split(",");
                        String uy12 = nhnh1[19];
                        Integer retert12 = Integer.parseInt(uy12);
                        String hghg = collect1.get(position);
                        String[] nhnh = hghg.split(",");
                        String uy = nhnh[6];
//                    String uy1 = nhnh[12];
//                    Log.d("disco1",uy1);
                        Integer retert = Integer.parseInt(uy);
                        retert = retert + retert12;
//                    Integer retert2 = Integer.parseInt(uy1);
                        String camount1 = "0";
                        Integer retert1 = Integer.parseInt(camount1);
//                    retert = retert - retert2 + retert12 ;
                        Log.d("rete",String.valueOf(retert));
                        if (retert1 > retert) {
                            String text = holder.collect.getText().toString();
                            holder.collect.setText(text.substring(0, text.length() - 1));
                            String text1 = holder.collect.getText().toString();
                            holder.collect.setSelection(text1.length());
                            AlertDialog.Builder alertbox = new AlertDialog.Builder(holder.itemView.getContext(),R.style.AlertDialogTheme);
                            String enn = holder.itemView.getContext().getResources().getString(R.string.amountgreat);
                            String enn1 = holder.itemView.getContext().getResources().getString(R.string.amountgreat1);
                            String war = holder.itemView.getContext().getResources().getString(R.string.warning);
                            String ook = holder.itemView.getContext().getResources().getString(R.string.ok);
                            String del = holder.itemView.getContext().getResources().getString(R.string.delete);
                            alertbox.setMessage(enn+" "+"\u20B9"+" "+String.valueOf(retert)+" "+enn1);
                            alertbox.setTitle(war);
                            alertbox.setIcon(R.drawable.dailylogo);
                            alertbox.setNeutralButton(ook,
                                    new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface arg0,
                                                            int arg1) {
                                        }
                                    });
                            alertbox.show();
                        } else {
//                dateString = datt.getText().toString();
//                Log.d("datata",dateString);


                            String namee = collect1.get(position);
                            String[] currencies1 = namee.split(",");
                            savn = currencies1[16];
                            Integer nn = Integer.parseInt(savn);
                            for(int i=0;i<onlyid.size();i++)
                            {
//                            Log.d("deae1asd", String.valueOf(i));
                                Integer io = Integer.valueOf(onlyid.get(i));
                                String hg = onlyid.get(i);
                                if(hg.matches(savn))
                                {
                                    Log.d("deae1asd", String.valueOf(i));
//                                Log.d("deae1asdqwqw", String.valueOf( Arrays.asList(onlyid).indexOf(io)));
                                }
                            }
                            edittextlist.put(Integer.valueOf(savn), holder.collect.getText().toString());
                            nam = currencies1[17];
                            saa1 = currencies1[23];
                            deid = currencies1[24];
                            collection_amount = Integer.valueOf(currencies1[19]);
                            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                            SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
                            String myFormat1 = "dd/MM/yyyy";//In which you need put here
                            SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                            try {
                                Date debit = df1.parse(formattedDate);
                                formattedDate = df.format(debit);
                                Log.d("deae1", savn);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                        camount = holder.collect.getText().toString();
//                        Log.d("newdebit", camount);
//                        Log.d("newdebit", String.valueOf(collection_amount));
//                        Log.d("idad", savn);
//                        Log.d("idad", nam);
//                        Log.d("datat", formattedDate1);
                            SQLiteHelper sqlite = new SQLiteHelper(context);
                            SQLiteDatabase database = sqlite.getWritableDatabase();
                            Calendar c = Calendar.getInstance();
                            String[] columns = {"collection_amount", "id"};
                            String order = "collection_date";
                            String whereClause = "customer_id=? AND collection_date = ?";
                            String[] whereArgs = new String[]{saa1, formattedDate1};
                            Cursor cursor = database.query("dd_collection",
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

                                        index = cursor.getColumnIndexOrThrow("collection_amount");
                                        collection_amount = cursor.getInt(index);

                                        index = cursor.getColumnIndexOrThrow("id");
                                        id = cursor.getString(index);
                                        if (collection_amount == null) {
                                            collection_amount = 0;
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
                            Integer sds = Integer.parseInt(savn);
                            if (sds > 5000) {
                                bulk.add(saa1 + "," + "0" + "," + collection_amount + "," + nam + "," + deid);
                            } else {
                                bulk.add(saa1 + "," + "0" + "," + collection_amount + "," + nam + "," + deid);
                            }
                        }
                    }
                    else {
                        String hghg1 = collect1.get(position);
                        String[] nhnh1 = hghg1.split(",");
                        String uy12 = nhnh1[19];
                        Integer retert12 = Integer.parseInt(uy12);
                        String hghg = collect1.get(position);
                        String[] nhnh = hghg.split(",");
                        String uy = nhnh[6];
//                    String uy1 = nhnh[12];
//                    Log.d("disco1",uy1);
                        Integer retert = Integer.parseInt(uy);
                        retert = retert + retert12;
//                    Integer retert2 = Integer.parseInt(uy1);
                        String camount1 = holder.collect.getText().toString();
                        Integer retert1 = Integer.parseInt(camount1);
//                    retert = retert - retert2 + retert12 ;
                        Log.d("rete",String.valueOf(retert));
                        if (retert1 > retert) {
                            String text = holder.collect.getText().toString();
                            holder.collect.setText(text.substring(0, text.length() - 1));
                            String text1 = holder.collect.getText().toString();
                            holder.collect.setSelection(text1.length());
                            AlertDialog.Builder alertbox = new AlertDialog.Builder(holder.itemView.getContext(),R.style.AlertDialogTheme);
                            String enn = holder.itemView.getContext().getResources().getString(R.string.amountgreat);
                            String enn1 = holder.itemView.getContext().getResources().getString(R.string.amountgreat1);
                            String war = holder.itemView.getContext().getResources().getString(R.string.warning);
                            String ook = holder.itemView.getContext().getResources().getString(R.string.ok);
                            String del = holder.itemView.getContext().getResources().getString(R.string.delete);
                            alertbox.setMessage(enn+" "+"\u20B9"+" "+String.valueOf(retert)+" "+enn1);
                            alertbox.setTitle(war);
                            alertbox.setIcon(R.drawable.dailylogo);
                            alertbox.setNeutralButton(ook,
                                    new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface arg0,
                                                            int arg1) {
                                        }
                                    });
                            alertbox.show();
                        } else {
//                dateString = datt.getText().toString();
//                Log.d("datata",dateString);
//                        edittextlist.put(position, holder.collect.getText().toString());
                            String namee = collect1.get(position);
                            String[] currencies1 = namee.split(",");
                            savn = currencies1[16];
                            edittextlist.put(Integer.valueOf(savn), holder.collect.getText().toString());
                            nam = currencies1[17];
                            saa1 = currencies1[23];
                            deid = currencies1[24];
                            collection_amount = Integer.valueOf(currencies1[19]);
                            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                            SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
                            String myFormat1 = "dd/MM/yyyy";//In which you need put here
                            SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                            try {
                                Date debit = df1.parse(formattedDate);
                                formattedDate = df.format(debit);
                                Log.d("deae1", savn);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            camount = holder.collect.getText().toString();
                            Log.d("newdebit", camount);
                            Log.d("newdebit", String.valueOf(collection_amount));
                            Log.d("idad", savn);
                            Log.d("idad", nam);
                            Log.d("datat", formattedDate1);
                            Calendar c = Calendar.getInstance();
                            sqlite = new SQLiteHelper(context);
                            database = sqlite.getWritableDatabase();
                            String[] columns = {"collection_amount", "id"};
                            String order = "collection_date";
                            String whereClause = "customer_id=? AND collection_date = ?";
                            String[] whereArgs = new String[]{saa1, formattedDate1};
                            Cursor cursor = database.query("dd_collection",
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

                                        index = cursor.getColumnIndexOrThrow("collection_amount");
                                        collection_amount = cursor.getInt(index);

                                        index = cursor.getColumnIndexOrThrow("id");
                                        id = cursor.getString(index);
                                        if (collection_amount == null) {
                                            collection_amount = 0;
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
                            Integer sds = Integer.parseInt(savn);
                            if (sds > 5000) {
                                bulk.add(saa1 + "," + camount + "," + collection_amount + "," + nam + "," + deid);
                            } else {
                                bulk.add(saa1 + "," + camount + "," + collection_amount + "," + nam + "," + deid);
                            }

                        }
                    }
                }


            }
        });
*/

        holder.collect.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                Log.d("collectoooooo",String.valueOf(s)+" "+typ+" "+String.valueOf(finalTototo));
                if(typ.equalsIgnoreCase("3")){
                    String v = String.valueOf(s);
                    if(v == null || v.equalsIgnoreCase("") || v.equalsIgnoreCase("null")){
                        v="0";
                    }
                    Integer bb = Integer.parseInt(v);
                    Integer bb12 = Integer.parseInt(v);
//                        if(bb> finalTototo){
//                            bb = bb - finalTototo ;
                    Integer bv = rem_collection.size() - 1 ;
                    if(bv < 0){
//                                collection.set(y,sSs+",,,"+String.valueOf(s)+",,,"+"0");
                        if(bb12>0){
                            rem_collection.add(sSs+",,,"+String.valueOf(bb)+",,,"+sSsss12+",,,"+deid+",,,"+cus+",,,"+CID+",,,"+nam+",,,"+sesss);
                            edittextlist.put(Integer.valueOf(savn), v);
                        }
                    }else {
                        for(int y1=0;y1<rem_collection.size();y1++) {
                            String vv00 = rem_collection.get(y1);
                            String[] vv100 = vv00.split(",,,");
                            String s200 = vv100[0];
                            if(s200.equalsIgnoreCase(sSs)){
                                if(bb12>0){
                                    rem_collection.set(y1,sSs+",,,"+String.valueOf(bb)+",,,"+sSsss12+",,,"+deid+",,,"+cus+",,,"+CID+",,,"+nam+",,,"+sesss);
                                    edittextlist.put(Integer.valueOf(savn), v);
                                }else{
                                    rem_collection.remove(y1);
                                    break;
                                }
                            }else{
                                if(bv.equals(y1)){
                                    if(bb12>0){
                                        Log.d("remm93",s200+" "+sSs+" "+String.valueOf(bv)+" "+String.valueOf(y1));
                                        rem_collection.add(sSs+",,,"+String.valueOf(bb)+",,,"+sSsss12+",,,"+deid+",,,"+cus+",,,"+CID+",,,"+nam+",,,"+sesss);
                                        edittextlist.put(Integer.valueOf(savn), v);
                                    }
                                }

                            }
                        }

                    }
                    Log.d("remm11",String.valueOf(rem_collection));
//                        }
                }
                else{
                    if(collection.size()<=0){
                        String v = String.valueOf(s);
                        if(v == null || v.equalsIgnoreCase("") || v.equalsIgnoreCase("null")){
                            v="0";
                        }
                        if(v.equalsIgnoreCase(sSsss)){

                        }else{
                            Integer bb = Integer.parseInt(v);
                            Integer bb12 = Integer.parseInt(v);
                            if(bb> finalTototo){
                                bb = bb - finalTototo ;
                                Integer bv = rem_collection.size() - 1 ;
                                if(bv <= 0){
//                                collection.set(y,sSs+",,,"+String.valueOf(s)+",,,"+"0");
                                    if(bb12>0){
                                        rem_collection.add(sSs+",,,"+String.valueOf(bb)+",,,"+sSsss12+",,,"+deid+",,,"+cus+",,,"+CID+",,,"+nam+",,,"+sesss);
                                    }

                                }else {
                                    for(int y1=0;y1<rem_collection.size();y1++) {
                                        String vv00 = rem_collection.get(y1);
                                        String[] vv100 = vv00.split(",,,");
                                        String s200 = vv100[0];
                                        if(s200.equalsIgnoreCase(sSs)){
                                            if(bb12>0){
                                                rem_collection.set(y1,sSs+",,,"+String.valueOf(bb)+",,,"+sSsss12+",,,"+deid+",,,"+cus+",,,"+CID+",,,"+nam+",,,"+sesss);
                                            }else{
                                                rem_collection.remove(y1);
                                                break;
                                            }

                                        }else{
                                            if(bv.equals(y1)){
                                                if(bb12>0){
                                                    Log.d("remm92",s200+" "+sSs+" "+String.valueOf(bv)+" "+String.valueOf(y1));
                                                    rem_collection.add(sSs+",,,"+String.valueOf(bb)+",,,"+sSsss12+",,,"+deid+",,,"+cus+",,,"+CID+",,,"+nam+",,,"+sesss);
                                                }

                                            }
                                        }
                                    }

                                }

                                if(bb12>0){
                                    collection.add(sSs+",,,"+String.valueOf(finalTototo)+",,,"+sSsss12+",,,"+savn+",,,"+nam+",,,"+deid+",,,"+finalTototo);
                                    edittextlist.put(Integer.valueOf(savn), v);
                                }

                            }
                            else{
                                for(int y1=0;y1<rem_collection.size();y1++) {
                                    String vv00 = rem_collection.get(y1);
                                    String[] vv100 = vv00.split(",,,");
                                    String s200 = vv100[0];
                                    if(s200.equalsIgnoreCase(sSs)){
                                        rem_collection.remove(y1);
                                        break;
                                    }else{
                                    }
                                }
                                if(bb12>0){
                                    collection.add(sSs+",,,"+String.valueOf(s)+",,,"+sSsss12+",,,"+savn+",,,"+nam+",,,"+deid+",,,"+finalTototo);
                                    edittextlist.put(Integer.valueOf(savn), v);
                                }
                            }
                            Log.d("remm1",String.valueOf(rem_collection));

                            Log.d("MyList1000",String.valueOf(collection)+" "+String.valueOf(finalTototo));
                        }
                    }
                    else{
                        Integer col_s = collection.size() - 1 ;
                        for(int y=0;y<collection.size();y++){
                            String vv = collection.get(y);
                            String[] vv1 = vv.split(",,,");
                            String s2 = vv1[0];

                            if(s2.equalsIgnoreCase(sSs)){
                                String v = String.valueOf(s);
                                if(v == null || v.equalsIgnoreCase("") || v.equalsIgnoreCase("null")){
                                    v="0";
                                }
                                if(v.equalsIgnoreCase(sSsss)){
                                }else{
                                    Integer bb = Integer.parseInt(v);
                                    Integer bb12 = Integer.parseInt(v);
                                    if(bb> finalTototo) {
                                        bb = bb - finalTototo;
                                        Integer bv = rem_collection.size() - 1;
                                        if (bv < 0) {
//                                collection.set(y,sSs+",,,"+String.valueOf(s)+",,,"+"0");
                                            if (bb12 > 0) {
                                                rem_collection.add(sSs + ",,," + String.valueOf(bb) + ",,," + sSsss12 + ",,," + deid + ",,," + cus + ",,," + CID + ",,," + nam + ",,," + sesss);
                                            }
                                        } else {
                                            for (int y1 = 0; y1 < rem_collection.size(); y1++) {
                                                String vv00 = rem_collection.get(y1);
                                                String[] vv100 = vv00.split(",,,");
                                                String s200 = vv100[0];
                                                if (s200.equalsIgnoreCase(sSs)) {
                                                    if (bb12 > 0) {
                                                        rem_collection.set(y1, sSs + ",,," + String.valueOf(bb) + ",,," + sSsss12 + ",,," + deid + ",,," + cus + ",,," + CID + ",,," + nam + ",,," + sesss);
                                                    }else{
                                                        rem_collection.remove(y1);
                                                        break;
                                                    }
                                                } else {
                                                    if (bv.equals(y1)) {
                                                        if (bb12 > 0) {
                                                            Log.d("remm90",s200+" "+sSs+" "+String.valueOf(bv)+" "+String.valueOf(y1));
                                                            rem_collection.add(sSs + ",,," + String.valueOf(bb) + ",,," + sSsss12 + ",,," + deid + ",,," + cus + ",,," + CID + ",,," + nam + ",,," + sesss);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (bb12 > 0) {
                                            collection.set(y, sSs + ",,," + String.valueOf(finalTototo) + ",,," + sSsss12 + ",,," + savn + ",,," + nam + ",,," + deid + ",,," + finalTototo);
                                            edittextlist.put(Integer.valueOf(savn), v);
                                        }
                                    }
                                    else{
                                        for(int y1=0;y1<rem_collection.size();y1++) {
                                            String vv00 = rem_collection.get(y1);
                                            String[] vv100 = vv00.split(",,,");
                                            String s200 = vv100[0];
                                            if(s200.equalsIgnoreCase(sSs)){
                                                rem_collection.remove(y1);
                                                break;
                                            }else{

                                            }
                                        }
                                        edittextlist.put(Integer.valueOf(savn), v);
                                        collection.set(y,sSs+",,,"+String.valueOf(s)+",,,"+sSsss12+",,,"+savn+",,,"+nam+",,,"+deid+",,,"+finalTototo);
                                    }
                                    Log.d("remm2",String.valueOf(rem_collection));
                                    Log.d("MyList1001",String.valueOf(collection));
                                }
                                break;
                            }
                            else{
                                String v = String.valueOf(s);
                                if(v == null || v.equalsIgnoreCase("") || v.equalsIgnoreCase("null")){
                                    v="0";
                                }
                                if(v.equalsIgnoreCase(sSsss)){
                                }else{
                                    if(col_s.equals(y)){
                                        Integer bb = Integer.parseInt(v);
                                        Integer bb12 = Integer.parseInt(v);
                                        if(bb> finalTototo) {
                                            bb = bb - finalTototo;
                                            Integer bv = rem_collection.size() - 1;
                                            if (bv < 0) {
//                                collection.set(y,sSs+",,,"+String.valueOf(s)+",,,"+"0");
                                                if (bb12 > 0) {
                                                    rem_collection.add(sSs + ",,," + String.valueOf(bb) + ",,," + sSsss12 + ",,," + deid + ",,," + cus + ",,," + CID + ",,," + nam + ",,," + sesss);
                                                }
                                            }
                                            else {
                                                for (int y1 = 0; y1 < rem_collection.size(); y1++) {
                                                    String vv00 = rem_collection.get(y1);
                                                    String[] vv100 = vv00.split(",,,");
                                                    String s200 = vv100[0];
                                                    if (s200.equalsIgnoreCase(sSs)) {
                                                        if (bb12 > 0) {
                                                            rem_collection.set(y1, sSs + ",,," + String.valueOf(bb) + ",,," + sSsss12 + ",,," + deid + ",,," + cus + ",,," + CID + ",,," + nam + ",,," + sesss);
                                                        }else{
                                                            rem_collection.remove(y1);
                                                            break;
                                                        }
                                                    } else {
                                                        if (bv.equals(y1)) {
                                                            if (bb12 > 0) {
                                                                Log.d("remm9",s200+" "+sSs+" "+String.valueOf(bv)+" "+String.valueOf(y1));
                                                                rem_collection.add(sSs + ",,," + String.valueOf(bb) + ",,," + sSsss12 + ",,," + deid + ",,," + cus + ",,," + CID + ",,," + nam + ",,," + sesss);
                                                            }
                                                        }
                                                    }
                                                }

                                            }
                                            if (bb12 > 0) {
                                                edittextlist.put(Integer.valueOf(savn), v);
                                                collection.add(sSs + ",,," + String.valueOf(finalTototo) + ",,," + sSsss12 + ",,," + savn + ",,," + nam + ",,," + deid + ",,," + finalTototo);
                                            }
                                        }
                                        else{
                                            for(int y1=0;y1<rem_collection.size();y1++) {
                                                String vv00 = rem_collection.get(y1);
                                                String[] vv100 = vv00.split(",,,");
                                                String s200 = vv100[0];
                                                if(s200.equalsIgnoreCase(sSs)){
                                                    rem_collection.remove(y1);
                                                    break;
                                                }else{
                                                }
                                            }
                                            if(bb12>0) {
                                                edittextlist.put(Integer.valueOf(savn), v);
                                                collection.add(sSs + ",,," + String.valueOf(s) + ",,," + sSsss12 + ",,," + savn + ",,," + nam + ",,," + deid + ",,," + finalTototo);
                                            }
                                        }

                                        Log.d("remm3",String.valueOf(rem_collection));
                                        Log.d("MyList1002",String.valueOf(collection));
                                    }
                                }
                            }



                        }
                    }
                }

            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
            public void afterTextChanged(Editable s) {

            }
        });

        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.collect.setText("0");
            }
        });

    }





    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public ArrayList getArray(){
        return collection;
    }
    public ArrayList getArray1(){
        return rem_collection;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public View rowView;
        private TextView mTitle,mid,mtext,debi,close,save,save11;
        private TextView mtot,mpad,mbad,mmad,mmam,mpam,mbam,mcd,mdd,minst,marea,mphone,mother,mdisc,mdays,mday;
        private TextView mmtot,mmpaid,mmmiss,mmcol,mmbal,save12,moree12;
        private EditText collect;
        private View vv,vv2;
        private LinearLayout lo;
        private Button edit,nip,delet,savv,moree,colle;
        NestedScrollView Scroller;


        public MyViewHolder(View itemView) {
            super(itemView);

            lin1 = 0;
            rowView = itemView;
            vv = itemView.findViewById(R.id.vi);
            vv2 = itemView.findViewById(R.id.vi1);
            minst = itemView.findViewById(R.id.inst);
            save = itemView.findViewById(R.id.save);
            save11 = itemView.findViewById(R.id.save11);
            mid = itemView.findViewById(R.id.slno);
            mTitle = itemView.findViewById(R.id.Name);

            collect = itemView.findViewById(R.id.amount);
            mtext= itemView.findViewById(R.id.amount1);

            close = itemView.findViewById(R.id.closedd);

            Typeface ty = Typeface.createFromAsset(context.getAssets(),"fontaswesome1.ttf");
            save.setTypeface(ty);
            save11.setTypeface(ty);
            close.setTypeface(ty);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    String customer_id = String.valueOf(iid.get(getAdapterPosition()));
//                    Log.d("iidd",customer_id);

                }
            });

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

    @Override
    public int getItemCount() {

        return collect1.size();
    }

}


