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
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

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


import com.rampit.rask3.dailydiary.Bulkupdate;
import com.rampit.rask3.dailydiary.Callback;
import com.rampit.rask3.dailydiary.ItemMoveCallback2;
import com.rampit.rask3.dailydiary.R;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;
import com.rampit.rask3.dailydiary.bulk_close;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RecyclerViewAdaptercollection extends RecyclerView.Adapter<RecyclerViewAdaptercollection.MyViewHolder> implements Filterable, ItemMoveCallback2.ItemTouchHelperContract {

    private final Bulkupdate bulkupdate;
    private ArrayList<String> data;
    private ArrayList<String> onlyid;
    private ArrayList<String> data1;
    private ArrayList<String> curr;
    private ArrayList<String> NNP;
    ArrayList<String> bulk = new ArrayList<>();
    private ArrayList<String> collection;
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

    @Override
    public void onRowMoved(RecyclerViewAdaptercollection.MyViewHolder myViewHolder,int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(collect1, i, i + 1);
                Log.i("hjbhk",fromPosition+" "+toPosition);

            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(collect1, i, i - 1);
                Log.i("hjbhk",fromPosition+" "+toPosition);
            }

        }
        String hhh = collect1.get(toPosition);
        String[] currencies = hhh.split(",");
        String ssss = currencies[21];
        Integer hjh = Integer.parseInt(ssss);
        String hhh1 = collect1.get(fromPosition);
        String[] currencies1 = hhh1.split(",");
        String ssss1 = currencies1[21];
        fromord1 = Integer.parseInt(ssss1);
        if(fromord1 <= 5000 && hjh >= 5000){
//            AlertDialog.Builder alertbox = new AlertDialog.Builder(myViewHolder.rowView.getContext());
//            myViewHolder.rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.spin5));
//            String enn = myViewHolder.rowView.getContext().getResources().getString(R.string.cannot_swipe);
//            String war = myViewHolder.rowView.getContext().getResources().getString(R.string.warning);
//            String ook = myViewHolder.rowView.getContext().getResources().getString(R.string.ok);
//            String del = myViewHolder.rowView.getContext().getResources().getString(R.string.delete);
//            alertbox.setMessage(enn);
//            alertbox.setTitle(war);
//            alertbox.setIcon(R.drawable.dailylogo);
//            alertbox.setNeutralButton(ook,
//                    new DialogInterface.OnClickListener() {
//
//                        public void onClick(DialogInterface arg0,
//                                            int arg1) {
//                        }
//                    });
//            alertbox.show();
            Log.d("recyc",String.valueOf(fromord1+" "+String.valueOf(hjh)));
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRowSelected(RecyclerViewAdaptercollection.MyViewHolder myViewHolder, int position) {
        myViewHolder.rowView.setBackgroundColor(Color.GRAY);
        selected = String.valueOf(position);
        sele = position;
        String animal = collect1.get(position);
        String[] currencies = animal.split(",");

        String sss = currencies[23];
        String ssss = currencies[21];
        Integer ss = Integer.parseInt(sss);
        idi = sss;
        frompos = ssss;
        fromord1 = Integer.valueOf(ssss);
//        if(ss >= 5001){
//            AlertDialog.Builder alertbox = new AlertDialog.Builder(myViewHolder.rowView.getContext());
//            myViewHolder.rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.spin5));
//            String enn = myViewHolder.rowView.getContext().getResources().getString(R.string.cannot_swipe);
//            String war = myViewHolder.rowView.getContext().getResources().getString(R.string.warning);
//            String ook = myViewHolder.rowView.getContext().getResources().getString(R.string.ok);
//            String del = myViewHolder.rowView.getContext().getResources().getString(R.string.delete);
//            alertbox.setMessage(enn);
//            alertbox.setTitle(war);
//            alertbox.setIcon(R.drawable.dailylogo);
//            alertbox.setNeutralButton(ook,
//                    new DialogInterface.OnClickListener() {
//
//                        public void onClick(DialogInterface arg0,
//                                            int arg1) {
//                        }
//                    });
//            alertbox.show();
//
//        }
        Log.d("selected", String.valueOf(position));
    }

    @Override
    public void onRowClear(MyViewHolder myViewHolder, int position) {

    }

    @Override
    public void oncle(MyViewHolder myViewHolder, int from, int to) {
        myViewHolder.rowView.setBackgroundColor(Color.GRAY);
        Log.d("CURRE",String.valueOf(curr.size()));
        Log.d("NIPNIP",String.valueOf(NNP.size()));
        Log.d("cleared",String.valueOf(from));
        Log.d("cleared1",String.valueOf(to));
        Log.d("qwqw",idi);
        String hhh = data1.get(to);
        String[] currencies = hhh.split(",");
        String ssss12 = currencies[21];
        Integer hjh = Integer.parseInt(ssss12);
        String hhh1 = data1.get(from);
        String[] currencies1 = hhh1.split(",");
        String ssss1000 = currencies1[21];
        fromord1 = Integer.parseInt(ssss1000);
        Log.d("selclear",String.valueOf(ssss12)+" "+String.valueOf(ssss1000));
        if(fromord1 <= 5000 && hjh >= 5000){
//            AlertDialog.Builder alertbox = new AlertDialog.Builder(myViewHolder.rowView.getContext());
//            myViewHolder.rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.spin5));
//            String enn = myViewHolder.rowView.getContext().getResources().getString(R.string.cannot_swipe);
//            String war = myViewHolder.rowView.getContext().getResources().getString(R.string.warning);
//            String ook = myViewHolder.rowView.getContext().getResources().getString(R.string.ok);
//            String del = myViewHolder.rowView.getContext().getResources().getString(R.string.delete);
//            alertbox.setMessage(enn);
//            alertbox.setTitle(war);
//            alertbox.setIcon(R.drawable.dailylogo);
//            alertbox.setNeutralButton(ook,
//                    new DialogInterface.OnClickListener() {
//
//                        public void onClick(DialogInterface arg0,
//                                            int arg1) {
//                        }
//                    });
//            alertbox.show();
            fromord1 = fromord1 - 1;
//hjh = hjh - 1;
            cleared = String.valueOf(curr.size() - 1);
            selected = String.valueOf(fromord1);
            Intent nn = new Intent(context,Bulkupdate.class);
            nn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            nn.putExtra("selected",selected);
            nn.putExtra("cleared",cleared);
            nn.putExtra("idi",idi);
            context.startActivity(nn);
            Log.d("recyc1",String.valueOf(fromord1+" "+String.valueOf(hjh)));
            Log.d("obulk3",cleared+" "+selected);
        }else if(fromord1 >= 5000 && hjh <= 5000){
            fromord1 =fromord1 - 1;
            cleared = "5000";
            selected = String.valueOf(fromord1);
            Intent nn = new Intent(context,Bulkupdate.class);
            nn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            nn.putExtra("selected",selected);
            nn.putExtra("cleared",cleared);
            nn.putExtra("idi",idi);
            context.startActivity(nn);
            Log.d("recyc2",String.valueOf(fromord1+" "+String.valueOf(hjh)));
            Log.d("obulk3",cleared+" "+selected);
        }else{
            Log.d("recyc",String.valueOf(fromord1+" "+String.valueOf(hjh)));
            if(fromord1 > hjh){
                fromord = Integer.parseInt(frompos);
//            toord = from - to ;
                toord =  hjh;
                fromord = fromord1 ;
                toord = toord ;
                Log.d("obulk2",fromord+" "+toord);
            }else if(fromord1 < hjh){
                fromord = Integer.parseInt(frompos);
//            toord = to - from ;
                toord =  hjh ;
                fromord = fromord1 ;
                toord = toord  ;
                Log.d("obulk1",fromord+" "+toord);
            }

            if(fromord >=5000 && toord >=5000){
                cleared = String.valueOf(toord);
                clea = to;
                selected = String.valueOf(fromord);
                Log.d("obulk4",cleared+" "+selected);
            }
            else {
                cleared = String.valueOf(toord);
                clea = to;
                selected = String.valueOf(fromord);
                Log.d("obulk3",cleared+" "+selected);
            }

            Intent nn = new Intent(context,Bulkupdate.class);
            nn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            nn.putExtra("selected",selected);
            nn.putExtra("cleared",cleared);
            nn.putExtra("idi",idi);
            nn.putExtra("selectedvaaa",String.valueOf(to));
            nn.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(nn);
        }
    }



    public RecyclerViewAdaptercollection(ArrayList<String> onlyid, ArrayList<String> data, ArrayList<String> data1, ArrayList<String> CURR, ArrayList<String> NNP, Integer ses, ArrayList<String> collect1, String dateString, Integer in, Context context, Bulkupdate bulkupdate) {
        this.data = data;
        this.onlyid = onlyid;
        datafull = collect1;
        this.data1 = data1;
//        iid = ID;
        curr = CURR;
        this.NNP = NNP;
        sesss = ses;
        this.collect1 = collect1;
        formattedDate = dateString;
        formattedDate1 = dateString;
        this.context = context;
        this.bulkupdate = bulkupdate;
        this.in = in;
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row3, parent, false);
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
        String[] currencies = name.split(",");
        final String s = currencies[16];
        String sS = currencies[17];
        String sSs = currencies[18];
        String sSsss = currencies[19];
        String sSss = currencies[20];
        String sSsss1 = currencies[22];
        String order_id = currencies[21];
//        Integer od = Integer.parseInt(order_id);
        debiittdate = sSsss1;
        Log.d("order_id111",s);
        holder.mid.setText(s);
        holder.mTitle.setText(sS);
        holder.debi.setText(sSs);
        if(edittextlist12.get(Integer.parseInt(s))!=null) {
            String ty = edittextlist12.get(Integer.parseInt(s));
            Log.d("po0909",ty);
            if(ty.equalsIgnoreCase("blue")){
                if(in == 0){
                    holder.save12.setTextColor((ContextCompat.getColor(context, R.color.colorPrimary)));
                }else{
                    holder.save12.setTextColor((ContextCompat.getColor(context, R.color.backblue)));
                }
            }else{
                holder.save12.setTextColor((ContextCompat.getColor(context, R.color.bulk_color)));

            }

        }else{
            holder.save12.setTextColor((ContextCompat.getColor(context, R.color.bulk_color)));
        }
        if(edittextlist.get(Integer.parseInt(s))!=null) {
            holder.collect.setText(edittextlist.get(Integer.parseInt(s)));
            String ty = edittextlist.get(Integer.parseInt(s));
            holder.collect.setSelection(ty.length());
            Log.d("ertyu",String.valueOf(ty)+" "+String.valueOf(s));
//            holder.collect.setSelection();
        }
        else {
            if(sSsss.equalsIgnoreCase("0")) {
                holder.collect.setText("");
            }else{
                holder.collect.setText(sSsss);
                holder.collect.setSelection(sSsss.length());
            }
        }
        if(sSs.equalsIgnoreCase("0")){
            holder.mtext.setVisibility(View.VISIBLE);
            holder.collect.setVisibility(View.GONE);
            holder.save12.setVisibility(View.INVISIBLE);
            holder.moree.setVisibility(View.INVISIBLE);
        }else{

        }
        if(sSss.equalsIgnoreCase("0")){
            holder.mTitle.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.debi.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.collect.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mtot.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mpad.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mbad.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mmad.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mmam.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mpam.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mbam.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mdd.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mcd.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.minst.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mdisc.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mother.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mmtot.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mmpaid.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mmmiss.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mmcol.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mmbal.setTextColor(ContextCompat.getColor(context,R.color.White));
        }
        else if(sSss.equalsIgnoreCase("1")){
            holder.mTitle.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.debi.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.collect.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mtot.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mpad.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mday.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mbad.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mmad.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mmam.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mpam.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mbam.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mdd.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mcd.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.minst.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mdisc.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mother.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mmtot.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mmpaid.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mmmiss.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mmcol.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mmbal.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
        }
        else if(sSss.equalsIgnoreCase("2")){
            holder.mTitle.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.debi.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.collect.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mtot.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mpad.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mbad.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mday.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mmad.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mmam.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mpam.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mbam.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mdd.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mcd.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.minst.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mdisc.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mother.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mmtot.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mmpaid.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mmmiss.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mmcol.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mmbal.setTextColor(ContextCompat.getColor(context,R.color.Red));
        }
        else if(sSss.equalsIgnoreCase("3")){
            holder.mTitle.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.debi.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.collect.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.mtot.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.mpad.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.mbad.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.mmad.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.mmam.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.mpam.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.mbam.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.mdd.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.mcd.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.minst.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.mday.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.mdisc.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.mother.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.mmtot.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.mmpaid.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mmmiss.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.mmcol.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mmbal.setTextColor(ContextCompat.getColor(context,R.color.Green));
        }
        else{
        }
        String colll = collect1.get(position);
        final String[] currencie = colll.split(",");
        String d = currencie[15];
        Log.d("po9089",d);
        String d1 = currencie[1];
//        String d2 = currencie[2];
        String d3 = currencie[3];
//        String d4 = currencie[4];
        String d5 = currencie[5];
        String d6 = currencie[6];
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
        holder.mmtot.setText(d);
        holder.mmpaid.setText(d1);
        holder.mmmiss.setText(d3);
        holder.mmcol.setText(d5);
        holder.mmbal.setText(d6);
        holder.mphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p = holder.mphone.getText().toString();
                if (p.equalsIgnoreCase("")) {
                    p = "0";
                }
//                Integer pp = Integer.parseInt(p);
                if (p.equalsIgnoreCase("0")) {

                } else {
                    p = "+91"+p;
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + p));
                    if (ContextCompat.checkSelfPermission(bulkupdate, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(bulkupdate,new String[]{Manifest.permission.CALL_PHONE} ,1);
                    }
                    else
                    {
                        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            }
        });
        //        Log.d("curreeer",d);


//        holder.collect.setText(collection.get(position));
//        holder.collect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                holder.collect.setFocusable(true);
//                holder.collect.setFocusableInTouchMode(true);
//
//            }
//        });







//        holder.collect.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//                if (s.length() > 0)
//                {
//                }else{
//                }
//            }
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//
//            }
//            public void afterTextChanged(Editable s) {
//                if (s.length() <= 0) {
//                    String hghg1 = data.get(position);
//                    String[] nhnh1 = hghg1.split(",");
//                    String uy12 = nhnh1[3];
//                    Integer retert12 = Integer.parseInt(uy12);
//                    String hghg = collect1.get(position);
//                    String[] nhnh = hghg.split(",");
//                    String uy = nhnh[6];
////                    String uy1 = nhnh[12];
////                    Log.d("disco1",uy1);
//                    Integer retert = Integer.parseInt(uy);
//                    retert = retert + retert12;
////                    Integer retert2 = Integer.parseInt(uy1);
//                    String camount1 = "0";
//                    Integer retert1 = Integer.parseInt(camount1);
////                    retert = retert - retert2 + retert12 ;
//                    Log.d("rete",String.valueOf(retert));
//                    if (retert1 > retert) {
//                        String text = holder.collect.getText().toString();
//                        holder.collect.setText(text.substring(0, text.length() - 1));
//                        String text1 = holder.collect.getText().toString();
//                        holder.collect.setSelection(text1.length());
//                        AlertDialog.Builder alertbox = new AlertDialog.Builder(holder.itemView.getContext(),R.style.AlertDialogTheme);
//                        String enn = holder.itemView.getContext().getResources().getString(R.string.amountgreat);
//                        String enn1 = holder.itemView.getContext().getResources().getString(R.string.amountgreat1);
//                        String war = holder.itemView.getContext().getResources().getString(R.string.warning);
//                        String ook = holder.itemView.getContext().getResources().getString(R.string.ok);
//                        String del = holder.itemView.getContext().getResources().getString(R.string.delete);
//                        alertbox.setMessage(enn+" "+"\u20B9"+" "+String.valueOf(retert)+" "+enn1);
//                        alertbox.setTitle(war);
//                        alertbox.setIcon(R.drawable.dailylogo);
//                        alertbox.setNeutralButton(ook,
//                                new DialogInterface.OnClickListener() {
//
//                                    public void onClick(DialogInterface arg0,
//                                                        int arg1) {
//                                    }
//                                });
//                        alertbox.show();
//                    } else {
////                dateString = datt.getText().toString();
////                Log.d("datata",dateString);
//                        edittextlist.put(position, holder.collect.getText().toString());
//                        String namee = data.get(position);
//                        String[] currencies1 = namee.split(",");
//                        savn = currencies1[0];
//                        nam = currencies1[1];
//                        saa1 = currencies1[7];
//                        deid = currencies1[8];
//                        collection_amount = Integer.valueOf(currencies1[3]);
//                        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//                        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
//                        String myFormat1 = "dd/MM/yyyy";//In which you need put here
//                        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
//                        try {
//                            Date debit = df1.parse(formattedDate);
//                            formattedDate = df.format(debit);
//                            Log.d("deae1", savn);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
////                        camount = holder.collect.getText().toString();
////                        Log.d("newdebit", camount);
////                        Log.d("newdebit", String.valueOf(collection_amount));
////                        Log.d("idad", savn);
////                        Log.d("idad", nam);
////                        Log.d("datat", formattedDate1);
//                        Calendar c = Calendar.getInstance();
//                        sqlite = new SQLiteHelper(context);
//                        database = sqlite.getReadableDatabase();
//                        String[] columns = {"collection_amount", "id"};
//                        String order = "collection_date";
//                        String whereClause = "customer_id=? AND collection_date = ?";
//                        String[] whereArgs = new String[]{saa1, formattedDate1};
//                        Cursor cursor = database.query("dd_collection",
//                                columns,
//                                whereClause,
//                                whereArgs,
//                                null,
//                                null,
//                                null);
//                        if (cursor != null) {
//                            if (cursor.getCount() != 0) {
//                                cursor.moveToFirst();
//                                do {
//                                    int index;
//
//                                    index = cursor.getColumnIndexOrThrow("collection_amount");
//                                    collection_amount = cursor.getInt(index);
//
//                                    index = cursor.getColumnIndexOrThrow("id");
//                                    id = cursor.getString(index);
//                                    if (collection_amount == null) {
//                                        collection_amount = 0;
//                                    }
//                                }
//                                while (cursor.moveToNext());
//                                cursor.close();
//                                sqlite.close();
//                                database.close();
//                            }else{
//                                cursor.close();
//                                sqlite.close();
//                                database.close();
//                            }
//                        }else{
//                            cursor.close();
//                            sqlite.close();
//                            database.close();
//                        }
//                        Integer sds = Integer.parseInt(savn);
//                        if (sds > 5000) {
//                            bulk.add(saa1 + "," + "0" + "," + collection_amount + "," + nam + "," + deid);
//                        } else {
//                            bulk.add(saa1 + "," + "0" + "," + collection_amount + "," + nam + "," + deid);
//                        }
//                    }
//                } else {
//                    String hghg1 = data.get(position);
//                    String[] nhnh1 = hghg1.split(",");
//                    String uy12 = nhnh1[3];
//                    Integer retert12 = Integer.parseInt(uy12);
//                    String hghg = collect1.get(position);
//                    String[] nhnh = hghg.split(",");
//                    String uy = nhnh[6];
//                    if(in == 0){
//                        holder.save12.setTextColor((ContextCompat.getColor(context, R.color.colorPrimary)));
//                    }else{
//                        holder.save12.setTextColor((ContextCompat.getColor(context, R.color.backblue)));
//                    }
////                    String uy1 = nhnh[12];
////                    Log.d("disco1",uy1);
//                    Integer retert = Integer.parseInt(uy);
//                    retert = retert + retert12;
////                    Integer retert2 = Integer.parseInt(uy1);
//                    String camount1 = holder.collect.getText().toString();
//                    Integer retert1 = Integer.parseInt(camount1);
////                    retert = retert - retert2 + retert12 ;
//                    Log.d("rete",String.valueOf(retert));
//                    if (retert1 > retert) {
//                        String text = holder.collect.getText().toString();
//                        holder.collect.setText(text.substring(0, text.length() - 1));
//                        String text1 = holder.collect.getText().toString();
//                        holder.collect.setSelection(text1.length());
//                        AlertDialog.Builder alertbox = new AlertDialog.Builder(holder.itemView.getContext(),R.style.AlertDialogTheme);
//                        String enn = holder.itemView.getContext().getResources().getString(R.string.amountgreat);
//                        String enn1 = holder.itemView.getContext().getResources().getString(R.string.amountgreat1);
//                        String war = holder.itemView.getContext().getResources().getString(R.string.warning);
//                        String ook = holder.itemView.getContext().getResources().getString(R.string.ok);
//                        String del = holder.itemView.getContext().getResources().getString(R.string.delete);
//                        alertbox.setMessage(enn+" "+"\u20B9"+" "+String.valueOf(retert)+" "+enn1);
//                        alertbox.setTitle(war);
//                        alertbox.setIcon(R.drawable.dailylogo);
//                        alertbox.setNeutralButton(ook,
//                                new DialogInterface.OnClickListener() {
//
//                                    public void onClick(DialogInterface arg0,
//                                                        int arg1) {
//                                    }
//                                });
//                        alertbox.show();
//                    } else {
////                dateString = datt.getText().toString();
////                Log.d("datata",dateString);
//                        edittextlist.put(position, holder.collect.getText().toString());
//                        String namee = data.get(position);
//                        String[] currencies1 = namee.split(",");
//                        savn = currencies1[0];
//                        nam = currencies1[1];
//                        saa1 = currencies1[7];
//                        deid = currencies1[8];
//                        collection_amount = Integer.valueOf(currencies1[3]);
//                        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//                        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
//                        String myFormat1 = "dd/MM/yyyy";//In which you need put here
//                        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
//                        try {
//                            Date debit = df1.parse(formattedDate);
//                            formattedDate = df.format(debit);
//                            Log.d("deae1", savn);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        camount = holder.collect.getText().toString();
//                        Log.d("newdebit", camount);
//                        Log.d("newdebit", String.valueOf(collection_amount));
//                        Log.d("idad", savn);
//                        Log.d("idad", nam);
//                        Log.d("datat", formattedDate1);
//                        Calendar c = Calendar.getInstance();
//                        sqlite = new SQLiteHelper(context);
//                        database = sqlite.getReadableDatabase();
//                        String[] columns = {"collection_amount", "id"};
//                        String order = "collection_date";
//                        String whereClause = "customer_id=? AND collection_date = ?";
//                        String[] whereArgs = new String[]{saa1, formattedDate1};
//                        Cursor cursor = database.query("dd_collection",
//                                columns,
//                                whereClause,
//                                whereArgs,
//                                null,
//                                null,
//                                null);
//                        if (cursor != null) {
//                            if (cursor.getCount() != 0) {
//                                cursor.moveToFirst();
//                                do {
//                                    int index;
//
//                                    index = cursor.getColumnIndexOrThrow("collection_amount");
//                                    collection_amount = cursor.getInt(index);
//
//                                    index = cursor.getColumnIndexOrThrow("id");
//                                    id = cursor.getString(index);
//                                    if (collection_amount == null) {
//                                        collection_amount = 0;
//                                    }
//                                }
//                                while (cursor.moveToNext());
//                                cursor.close();
//                                sqlite.close();
//                                database.close();
//                            }else{
//                                cursor.close();
//                                sqlite.close();
//                                database.close();
//                            }
//                        }else{
//                            cursor.close();
//                            sqlite.close();
//                            database.close();
//                        }
//                        Integer sds = Integer.parseInt(savn);
//                        if (sds > 5000) {
//                            bulk.add(saa1 + "," + camount + "," + collection_amount + "," + nam + "," + deid);
//                        } else {
//                            bulk.add(saa1 + "," + camount + "," + collection_amount + "," + nam + "," + deid);
//                        }
//
//                    }
//                }
//            }
//        });
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
                                    if(bulk.size()<=0){
//                                        bulk.add(saa1 + "," + "0" + "," + collection_amount + "," + nam + "," + deid);
                                    }else{
                                        Integer bbu_si = bulk.size() - 1 ;
                                        for(int bbuull = 0; bbuull<bulk.size();bbuull++){
                                            String bu = bulk.get(bbuull);
                                            String[] bbuu = bu.split(",");
                                            String zero = bbuu[0];
                                            if(zero.equalsIgnoreCase(saa1)){
                                                bulk.set(bbuull,saa1 + "," + "0" + "," + collection_amount + "," + nam + "," + deid);
                                                break;
                                            }else{
                                                if(bbu_si.equals(bbuull)){
//                                                    bulk.add(saa1 + "," + "0" + "," + collection_amount + "," + nam + "," + deid);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if(bulk.size()<=0){
//                                        bulk.add(saa1 + "," + "0" + "," + collection_amount + "," + nam + "," + deid);
                                    }else{
                                        Integer bbu_si = bulk.size() - 1 ;
                                        for(int bbuull = 0; bbuull<bulk.size();bbuull++){
                                            String bu = bulk.get(bbuull);
                                            String[] bbuu = bu.split(",");
                                            String zero = bbuu[0];
                                            if(zero.equalsIgnoreCase(saa1)){
                                                bulk.set(bbuull,saa1 + "," + "0" + "," + collection_amount + "," + nam + "," + deid);
                                                break;
                                            }else{
                                                if(bbu_si.equals(bbuull)){
//                                                    bulk.add(saa1 + "," + "0" + "," + collection_amount + "," + nam + "," + deid);
                                                }
                                            }
                                        }
                                    }
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
                            }
                            else {
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

                                    if(bulk.size()<=0){
                                        bulk.add(saa1 + "," + camount+ "," + collection_amount + "," + nam + "," + deid);
                                    }else{
                                        Integer bbu_si = bulk.size() - 1 ;
                                        for(int bbuull = 0; bbuull<bulk.size();bbuull++){
                                            String bu = bulk.get(bbuull);
                                            String[] bbuu = bu.split(",");
                                            String zero = bbuu[0];
                                            if(zero.equalsIgnoreCase(saa1)){
                                                bulk.set(bbuull,saa1 + "," + camount + "," + collection_amount + "," + nam + "," + deid);
                                                break;
                                            }else{
                                                if(bbu_si.equals(bbuull)){
                                                    bulk.add(saa1 + "," + camount + "," + collection_amount + "," + nam + "," + deid);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if(bulk.size()<=0){
                                        bulk.add(saa1 + "," + camount + "," + collection_amount + "," + nam + "," + deid);
                                    }else{
                                        Integer bbu_si = bulk.size() - 1 ;
                                        for(int bbuull = 0; bbuull<bulk.size();bbuull++){
                                            String bu = bulk.get(bbuull);
                                            String[] bbuu = bu.split(",");
                                            String zero = bbuu[0];
                                            if(zero.equalsIgnoreCase(saa1)){
                                                bulk.set(bbuull,saa1 + "," + camount + "," + collection_amount + "," + nam + "," + deid);
                                                break;
                                            }else{
                                                if(bbu_si.equals(bbuull)){
                                                    bulk.add(saa1 + "," + camount + "," + collection_amount + "," + nam + "," + deid);
                                                }
                                            }
                                        }
                                    }
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

//                                bulk.add(saa1 + "," + "0" + "," + collection_amount + "," + nam + "," + deid);
                                if(bulk.size()<=0){
                                    bulk.add(saa1 + "," + "0" + "," + collection_amount + "," + nam + "," + deid);
                                }else{
                                    Integer bbu_si = bulk.size() - 1 ;
                                    for(int bbuull = 0; bbuull<bulk.size();bbuull++){
                                        String bu = bulk.get(bbuull);
                                        String[] bbuu = bu.split(",");
                                        String zero = bbuu[0];
                                        if(zero.equalsIgnoreCase(saa1)){
                                            bulk.set(bbuull,saa1 + "," + "0" + "," + collection_amount + "," + nam + "," + deid);
                                            break;
                                        }else{
                                            if(bbu_si.equals(bbuull)){
                                                bulk.add(saa1 + "," + "0" + "," + collection_amount + "," + nam + "," + deid);
                                            }
                                        }
                                    }
                                }
                            } else {
//                                bulk.add(saa1 + "," + "0" + "," + collection_amount + "," + nam + "," + deid);
                                if(bulk.size()<=0){
//                                    bulk.add(saa1 + "," + "0" + "," + collection_amount + "," + nam + "," + deid);
                                }else{
                                    Integer bbu_si = bulk.size() - 1 ;
                                    for(int bbuull = 0; bbuull<bulk.size();bbuull++){
                                        String bu = bulk.get(bbuull);
                                        String[] bbuu = bu.split(",");
                                        String zero = bbuu[0];
                                        if(zero.equalsIgnoreCase(saa1)){
                                            bulk.set(bbuull,saa1 + "," + "0" + "," + collection_amount + "," + nam + "," + deid);
                                            break;
                                        }else{
                                            if(bbu_si.equals(bbuull)){
//                                                bulk.add(saa1 + "," + "0" + "," + collection_amount + "," + nam + "," + deid);
                                            }
                                        }
                                    }
                                }
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
//                                bulk.add(saa1 + "," + camount + "," + collection_amount + "," + nam + "," + deid);
                                if(bulk.size()<=0){
//                                    bulk.add(saa1 + "," + "0" + "," + collection_amount + "," + nam + "," + deid);
                                    bulk.add(saa1 + "," + camount + "," + collection_amount + "," + nam + "," + deid);
                                }else{
                                    Integer bbu_si = bulk.size() - 1 ;
                                    for(int bbuull = 0; bbuull<bulk.size();bbuull++){
                                        String bu = bulk.get(bbuull);
                                        String[] bbuu = bu.split(",");
                                        String zero = bbuu[0];
                                        if(zero.equalsIgnoreCase(saa1)){
                                            bulk.set(bbuull,saa1 + "," + camount + "," + collection_amount + "," + nam + "," + deid);
                                            break;
                                        }else{
                                            if(bbu_si.equals(bbuull)){
                                                bulk.add(saa1 + "," + camount + "," + collection_amount + "," + nam + "," + deid);
                                            }
                                        }
                                    }
                                }
                            } else {
//                                bulk.add(saa1 + "," + camount + "," + collection_amount + "," + nam + "," + deid);
                                if(bulk.size()<=0){
                                    bulk.add(saa1 + "," + camount + "," + collection_amount + "," + nam + "," + deid);
                                }else{
                                    Integer bbu_si = bulk.size() - 1 ;
                                    for(int bbuull = 0; bbuull<bulk.size();bbuull++){
                                        String bu = bulk.get(bbuull);
                                        String[] bbuu = bu.split(",");
                                        String zero = bbuu[0];
                                        if(zero.equalsIgnoreCase(saa1)){
                                            bulk.set(bbuull,saa1 + "," + camount + "," + collection_amount + "," + nam + "," + deid);
                                            break;
                                        }else{
                                            if(bbu_si.equals(bbuull)){
                                                bulk.add(saa1 + "," + camount + "," + collection_amount + "," + nam + "," + deid);
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }


            }
        });
//        holder.collect.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    String namee = data.get(position);
//                    String[] currencies1 = namee.split(",");
//                    savn = currencies1[0];
//                    nam = currencies1[1];
//                    collection_amount = Integer.valueOf(currencies1[3]);
//                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//                    SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
//                    String myFormat1 = "dd/MM/yyyy";//In which you need put here
//                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
//                    try {
//                        Date debit = df1.parse(formattedDate);
//                        formattedDate = df.format(debit);
//                        Log.d("deae1",savn);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    camount = holder.collect.getText().toString();
//                    Log.d("newdebit",camount);
//                    Log.d("newdebit", String.valueOf(collection_amount));
//                    Log.d("idad",savn);
//                    Log.d("idad",nam);
//                    Log.d("datat",formattedDate);
//                    Calendar c = Calendar.getInstance();
//
//                    String[] columns = {"collection_amount","id"};
//                    String order = "collection_date";
//                    String whereClause = "customer_id=? AND collection_date = ?";
//                    String[] whereArgs = new String[] {savn,formattedDate};
//                    sqlite = new SQLiteHelper(context);
//                    database = sqlite.getWritableDatabase();
//                    Cursor cursor = database.query("dd_collection",
//                            columns,
//                            whereClause,
//                            whereArgs,
//                            null,
//                            null,
//                            null);
//                    if (cursor != null) {
//                        if (cursor.getCount() != 0) {
//                            cursor.moveToFirst();
//                            do {
//                                int index;
//
//                                index = cursor.getColumnIndexOrThrow("collection_amount");
//                                collection_amount = cursor.getInt(index);
//
//                                index = cursor.getColumnIndexOrThrow("id");
//                                id = cursor.getString(index);
//                                if(collection_amount == null){
//                                    collection_amount =0 ;
//                                }
//                            }
//                            while (cursor.moveToNext());
//                        }
//
//                    }
////                    bulk.add(0,savn+","+camount+","+collection_amount+","+nam);
////                    bulkupdate.dattee(formattedDate,collection_amount,savn,nam,camount);
//                }else if(hasFocus){
////                    String namee = data.get(position);
////                    String[] currencies1 = namee.split(",");
////                    savn = currencies1[0];
////                    nam = currencies1[1];
////                    collection_amount = Integer.valueOf(currencies1[3]);
////                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
////                    SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
////                    String myFormat1 = "dd/MM/yyyy";//In which you need put here
////                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
////                    try {
////                        Date debit = df1.parse(formattedDate);
////                        formattedDate = df.format(debit);
////                        Log.d("deae1",savn);
////                    } catch (ParseException e) {
////                        e.printStackTrace();
////                    }
////                    camount = holder.collect.getText().toString();
////                    Log.d("newdebit",camount);
////                    Log.d("newdebit", String.valueOf(collection_amount));
////                    Log.d("idad",savn);
////                    Log.d("idad",nam);
////                    Log.d("datat",formattedDate);
////                    Calendar c = Calendar.getInstance();
////
////                    String[] columns = {"collection_amount","id"};
////                    String order = "collection_date";
////                    String whereClause = "customer_id=? AND collection_date = ?";
////                    String[] whereArgs = new String[] {savn,formattedDate};
////                    Cursor cursor = database.query("dd_collection",
////                            columns,
////                            whereClause,
////                            whereArgs,
////                            null,
////                            null,
////                            null);
////                    if (cursor != null) {
////                        if (cursor.getCount() != 0) {
////                            cursor.moveToFirst();
////                            do {
////                                int index;
////
////                                index = cursor.getColumnIndexOrThrow("collection_amount");
////                                collection_amount = cursor.getInt(index);
////
////                                index = cursor.getColumnIndexOrThrow("id");
////                                id = cursor.getString(index);
////                                if(collection_amount == null){
////                                    collection_amount =0 ;
////                                }
////                            }
////                            while (cursor.moveToNext());
////                        }
////
////                    }
////                    bulk.add(savn+","+camount+","+collection_amount+","+nam);
//                }
//            }
//        });
        holder.colle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namee = collect1.get(position);
                String[] currencies1 = namee.split(",");
                savn = currencies1[23];
                bulkupdate.popup1(savn);
            }
        });
        holder.moree12.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if(holder.vv.getVisibility() == View.VISIBLE){
//                   Scroller = itemView.FindViewById<NestedScrollView>(Quota.Resource.Id.bodyScroller);
//                   holder.Scroller.setNestedScrollingEnabled(true);
//                   collapse(holder.lo, 500, 0);
                    String namee = collect1.get(position);
                    String[] currencies1 = namee.split(",");
                    String deid = currencies1[23];
                    String deid1 = currencies1[24];
                    Log.d("ddfdf",deid);
                    list(deid,deid1);
//                   collect1.add(totaldays + "," + String.valueOf(paiddays) + "," +
//                           String.valueOf(balanceday) + ","
//                           + String.valueOf(missda1) + "," +
//                           String.valueOf(misam) + "," +
//                           String.valueOf(paidamount) + "," +
//                           String.valueOf(balanceamount) + "," +
//                           dbdda + "," + cloo+","+installment+","+Loc
//                           +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1);
                    holder.mtot.setText(String.valueOf(totaldays));
                    holder.mpad.setText(String.valueOf(paiddays));
                    holder.mbad.setText(String.valueOf(balanceday));
                    holder.mmad.setText(String.valueOf(missda1));
                    holder.mmam.setText("\u20B9"+" "+String.valueOf(misam));
                    holder.mpam.setText("\u20B9"+" "+String.valueOf(paidamount));
                    holder.mbam.setText("\u20B9"+" "+String.valueOf(balanceamount));
                    holder.mdd.setText(String.valueOf(dbdda));
                    holder.mcd.setText(String.valueOf(cloo));
                    holder.marea.setText(String.valueOf(Loc));
                    holder.mphone.setText(Pho);
                    holder.mdisc.setText(String.valueOf(disss));
                    holder.mother.setText(String.valueOf(othe));
//        String enn = holder.itemView.getContext().getResources().getString(R.string.total_days);
//        holder.mdays.setText(enn+" : "+d14);
                    String enn1 = holder.itemView.getContext().getResources().getString(R.string.installment);
                    holder.minst.setText("\u20B9"+" "+String.valueOf(installment));
                    String enn11 = holder.itemView.getContext().getResources().getString(R.string.days);
                    holder.mday.setText(String.valueOf(toda1));
                    expand(holder.lo, 500, LinearLayout.LayoutParams.WRAP_CONTENT);
                    holder.vv.setVisibility(View.GONE);
                    holder.vv2.setVisibility(View.VISIBLE);
                    if(in == 0){
//                        holder.moree.setBackgroundResource(R.drawable.view_up12);
                    }else{
//                        holder.moree.setBackgroundResource(R.drawable.view_up123);
                    }
                    String tty = context.getString(R.string.arrow_up);
                    holder.moree12.setText(tty);
                }
                else if(holder.vv2.getVisibility() == View.VISIBLE){
//                   holder.Scroller.setNestedScrollingEnabled(true);
                    collapse(holder.lo, 500, 0);
                    holder.vv.setVisibility(View.VISIBLE);
                    holder.vv2.setVisibility(View.GONE);
                    if(in == 0){
//                        holder.moree.setBackgroundResource(R.drawable.view_more1);
                    }else{
//                        holder.moree.setBackgroundResource(R.drawable.view_more2);
                    }
                    String tty = context.getString(R.string.arrow_down);
                    holder.moree12.setText(tty);
                }
//               Log.d("kko",String.valueOf(holder.moree.getBackground()));
//                if (lin1 == 0)
//                {
//                                       lin1 = 1;
//                }
//                else if(lin1 == 1)
//                {
//                    lin1 = 0;
//
//                }
            }
        });
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.collect.setText("0");
            }
        });
        holder.save12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collapse(holder.lo, 50, 0);
                holder.vv.setVisibility(View.VISIBLE);
                holder.vv2.setVisibility(View.GONE);
                bulkupdate.progressbar_load1();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((AppCompatActivity) bulkupdate).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(in == 0){
//                    holder.moree.setBackgroundResource(R.drawable.view_more1);
                                }else{
//                    holder.moree.setBackgroundResource(R.drawable.view_more2);
                                }
                                String tty = context.getString(R.string.arrow_down);
                                holder.moree12.setText(tty);
                                camount = holder.collect.getText().toString();
                                int color=holder.save12.getCurrentTextColor();
                                String hexColor = String.format("#%06X", (0xFFFFFF & color));
                                String namee12 = collect1.get(position);
                                String[] currencies12 = namee12.split(",");
                                String savn345 = currencies12[16];
                                if(hexColor.equalsIgnoreCase("#1564C0") || hexColor.equalsIgnoreCase("#f15a31")){
                                    holder.save12.setTextColor(ContextCompat.getColor(context, R.color.bulk_color));
                                    edittextlist12.put(Integer.valueOf(savn345), "bulk");
                                    Log.d("sa1234",hexColor);
                                }
                                Log.d("hexco",hexColor);
                                if (camount.equalsIgnoreCase("")) {
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
                                else {
                                    String namee = collect1.get(position);
                                    String[] currencies1 = namee.split(",");
                                    savn = currencies1[23];
                                    deid = currencies1[24];
                                    nam = currencies1[17];
                                    String ccii = currencies1[15];
                                    collection_amount = Integer.valueOf(currencies1[19]);
//        String[] columns = {"collection_amount","id"};
//        String order = "collection_date";
//        String whereClause = "customer_id=? AND collection_date = ?";
//        String[] whereArgs = new String[] {savn,formattedDate};
//        Cursor cursor = database.query("dd_collection",
//                columns,
//                whereClause,
//                whereArgs,
//                null,
//                null,
//                null);
//        if (cursor != null) {
//            if (cursor.getCount() != 0) {
//                cursor.moveToFirst();
//                do {
//                    int index;
//
//                    index = cursor.getColumnIndexOrThrow("collection_amount");
//                    collection_amount = cursor.getInt(index);
//
//                }
//                while (cursor.moveToNext());
//            }
//
//        }
                                    collection1(savn, formattedDate1);
                                    camount = holder.collect.getText().toString();
                                    Log.d("newdebit", camount);
                                    Log.d("newdebit", String.valueOf(cam1));
                                    Log.d("idad", savn);
                                    Log.d("idad", nam);
                                    Log.d("datat", formattedDate1);
                                    bulkupdate.dattee(formattedDate, cam1, savn, nam, camount, deid);
                                    bulkupdate.populateRecyclerView();
                                    final SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
                                    Integer ii = pref.getInt("searchid",0);
                                    Integer jj = Integer.parseInt(ccii);
                                    Log.d("idad",String.valueOf(ii));
                                    if(ii.equals(jj)){
                                        if(ii > 0){
                                            bulkupdate.listo(ii);
                                        }else{
                                            bulkupdate.list2();
                                        }
                                    }else{
                                        bulkupdate.list2();
                                    }
//                    notifyDataSetChanged();
//        Intent name= new Intent(context, Bulkupdate.class);
//        name.putExtra("newda",formattedDate);
//        v.getContext().startActivity(name);
                                }
                                //context.refreshInbox();
                            }
                        });

                    }
                }, 500);

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

    private class syncEvent extends AsyncTask<String, Integer, String> {

        //Add 1. Declare RelativeLaout
        private RelativeLayout relativeView;
        private ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... arg0) {
            //your operation task here
            Log.d("updatearray",String.valueOf(bulk)+" "+String.valueOf(bulk.size()));
            for(int i =0;i<bulk.size();i++){
                String nameee = bulk.get(i);
                String[] currencies1 = nameee.split(",");
                String san = currencies1[0];
                String na = currencies1[1];
                Integer co = Integer.valueOf(currencies1[2]);
                String namm = currencies1[3];
                String debid = currencies1[4];
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                String todate = df.format(c.getTime());
                collection(san,updatedated1);
                Log.d("updatearray",san+" "+na+" "+String.valueOf(co)+" "+String.valueOf(cam)+" "+updatedated1);
                if( cam <= -1){
                    Log.d("updatearray1",san+" "+na+" "+String.valueOf(co));
                    sqlite = new SQLiteHelper(context);
                    database = sqlite.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("customer_id", san);
                    values.put("customer_name", namm);
                    values.put("collection_amount", na);
                    values.put("other_fee", "0");
                    values.put("discount", "0");
                    values.put("collection_date", updatedated1);
                    values.put("debit_id",debid);
                    values.put("created_date", todate);
                    database.insert(TABLENAME5, null, values);
                    sqlite.close();
                    database.close();

//            notifyDataSetChanged();
                }else {
                    Log.d("updatearray2",san+" "+na+" "+String.valueOf(co));
                    sqlite = new SQLiteHelper(context);
                    database = sqlite.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("collection_amount",na);
                    database.update(TABLENAME5, cv, "customer_id=? AND collection_date= ? AND debit_id = ?", new String[]{san,updatedated1,debid});
                    sqlite.close();
                    database.close();
//            notifyDataSetChanged();
                }
            }
            Log.d("allcollection_date1",updatedated1);
            ((Callback)bulkupdate.getApplication()).closed1(updatedated1, String.valueOf(sesss));
            ((Callback)bulkupdate.getApplication()).beforebal(updatedated1, String.valueOf(sesss));
            ((Callback) bulkupdate.getApplication()).populateRecyclerView23(updatedated1, updatedated1 ,String.valueOf(sesss));
//            bulkupdate.closed11(sesss);
//            ((Callback)bulkupdate.getApplication()).closed11(sesss);
            new bulk_close().closed(sesss,context);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

//            bulkupdate.progressbar_stop();
//            dialog.dismiss();
            bulkupdate.progressbar_stop();
        }

        @Override
        protected void onPreExecute() {
            Log.d("updatearray1234",String.valueOf(bulk)+" "+String.valueOf(bulk.size()));
//            progressDialog = ProgressDialog.show(bulkupdate, "","Loadingg....");
//            dialog = new Dialog(bulkupdate,R.style.AlertDialogTheme);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setContentView(R.layout.progressbar);
//            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//            lp.copyFrom(dialog.getWindow().getAttributes());
//            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//            lp.gravity = Gravity.CENTER;
//            dialog.setCancelable(false);
//            dialog.getWindow().setAttributes(lp);
//            dialog.show();
            bulkupdate.progressbar_load3();
            //Add 3. add 2 lines of code here
//            relativeView = (RelativeLayout)findViewById(R.id.loadingevent);


        }

        // For this method, i don't need to use it yet,so, i left it here
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }

    private class syncEvent2 extends AsyncTask<String, Integer, String> {

        //Add 1. Declare RelativeLaout
        private RelativeLayout relativeView;
        private ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... arg0) {
            //your operation task here
            Log.d("updatearray",String.valueOf(bulk)+" "+String.valueOf(bulk.size()));
            for(int i =0;i<bulk.size();i++){
                String nameee = bulk.get(i);
                String[] currencies1 = nameee.split(",");
                String san = currencies1[0];
                String na = currencies1[1];
                Integer co = Integer.valueOf(currencies1[2]);
                String namm = currencies1[3];
                String debid = currencies1[4];
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                String todate = df.format(c.getTime());
                collection(san,updatedated1);
                Log.d("updatearray",san+" "+na+" "+String.valueOf(co)+" "+String.valueOf(cam)+" "+updatedated1);
                if( cam <= -1){
                    Log.d("updatearray1",san+" "+na+" "+String.valueOf(co));
                    sqlite = new SQLiteHelper(context);
                    database = sqlite.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("customer_id", san);
                    values.put("customer_name", namm);
                    values.put("collection_amount", na);
                    values.put("other_fee", "0");
                    values.put("discount", "0");
                    values.put("collection_date", updatedated1);
                    values.put("debit_id",debid);
                    values.put("created_date", todate);
                    database.insert(TABLENAME5, null, values);
                    sqlite.close();
                    database.close();

//            notifyDataSetChanged();
                }else {
                    Log.d("updatearray2",san+" "+na+" "+String.valueOf(co));
                    sqlite = new SQLiteHelper(context);
                    database = sqlite.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("collection_amount",na);
                    database.update(TABLENAME5, cv, "customer_id=? AND collection_date= ? AND debit_id = ?", new String[]{san,updatedated1,debid});
                    sqlite.close();
                    database.close();
//            notifyDataSetChanged();
                }
            }
            Log.d("allcollection_date1",updatedated1);
            ((Callback)bulkupdate.getApplication()).closed1(updatedated1, String.valueOf(sesss));
            ((Callback)bulkupdate.getApplication()).beforebal(updatedated1, String.valueOf(sesss));
            ((Callback) bulkupdate.getApplication()).populateRecyclerView23(updatedated1, updatedated1 ,String.valueOf(sesss));
//            bulkupdate.closed11(sesss);
//            ((Callback)bulkupdate.getApplication()).closed11(sesss);
            new bulk_close().closed(sesss,context);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

//            bulkupdate.progressbar_stop();
//            dialog.dismiss();


//            bulkupdate.progressbar_stop();

            edittextlist12.clear();
            bulk.clear();
            bulkupdate.progressbar_stop1();
        }

        @Override
        protected void onPreExecute() {
            Log.d("updatearray1234",String.valueOf(bulk)+" "+String.valueOf(bulk.size()));
//            progressDialog = ProgressDialog.show(bulkupdate, "","Loadingg....");
//            dialog = new Dialog(bulkupdate,R.style.AlertDialogTheme);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setContentView(R.layout.progressbar);
//            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//            lp.copyFrom(dialog.getWindow().getAttributes());
//            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//            lp.gravity = Gravity.CENTER;
//            dialog.setCancelable(false);
//            dialog.getWindow().setAttributes(lp);
//            dialog.show();
            bulkupdate.progressbar_load3();
            //Add 3. add 2 lines of code here
//            relativeView = (RelativeLayout)findViewById(R.id.loadingevent);


        }

        // For this method, i don't need to use it yet,so, i left it here
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }


    private class syncEvent1 extends AsyncTask<String, Integer, String> {

        //Add 1. Declare RelativeLaout
        private RelativeLayout relativeView;
        private ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... arg0) {
            //your operation task here
            Log.d("updatearray",String.valueOf(bulk)+" "+String.valueOf(bulk.size()));
            for(int i =0;i<bulk.size();i++){
                String nameee = bulk.get(i);
                String[] currencies1 = nameee.split(",");
                String san = currencies1[0];
                String na = currencies1[1];
                Integer co = Integer.valueOf(currencies1[2]);
                String namm = currencies1[3];
                String debid = currencies1[4];
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                String todate = df.format(c.getTime());
                collection(san,updatedated1);
                Log.d("updatearray",san+" "+na+" "+String.valueOf(co)+" "+String.valueOf(cam)+" "+updatedated1);
                if( cam <= -1){
                    Log.d("updatearray1",san+" "+na+" "+String.valueOf(co));
                    sqlite = new SQLiteHelper(context);
                    database = sqlite.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("customer_id", san);
                    values.put("customer_name", namm);
                    values.put("collection_amount", na);
                    values.put("other_fee", "0");
                    values.put("discount", "0");
                    values.put("collection_date", updatedated1);
                    values.put("debit_id",debid);
                    values.put("created_date", todate);
                    database.insert(TABLENAME5, null, values);
                    sqlite.close();
                    database.close();

//            notifyDataSetChanged();
                }else {
                    Log.d("updatearray2",san+" "+na+" "+String.valueOf(co));
                    sqlite = new SQLiteHelper(context);
                    database = sqlite.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("collection_amount",na);
                    database.update(TABLENAME5, cv, "customer_id=? AND collection_date= ? AND debit_id = ?", new String[]{san,updatedated1,debid});
                    sqlite.close();
                    database.close();
//            notifyDataSetChanged();
                }
            }
            Log.d("allcollection_date1",updatedated1);
            ((Callback)bulkupdate.getApplication()).closed1(updatedated1, String.valueOf(sesss));
            ((Callback)bulkupdate.getApplication()).beforebal(updatedated1, String.valueOf(sesss));
            ((Callback) bulkupdate.getApplication()).populateRecyclerView23(updatedated1, updatedated1 ,String.valueOf(sesss));
//            bulkupdate.closed11(sesss);
//            ((Callback)bulkupdate.getApplication()).closed11(sesss);
            new bulk_close().closed(sesss,context);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

//            bulkupdate.progressbar_stop();
//            dialog.dismiss();
            edittextlist12.clear();
            bulk.clear();
            bulkupdate.progressbar_stop1();
        }

        @Override
        protected void onPreExecute() {
            Log.d("updatearray1234",String.valueOf(bulk)+" "+String.valueOf(bulk.size()));
//            progressDialog = ProgressDialog.show(bulkupdate, "","Loadingg....");
//            dialog = new Dialog(bulkupdate,R.style.AlertDialogTheme);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setContentView(R.layout.progressbar);
//            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//            lp.copyFrom(dialog.getWindow().getAttributes());
//            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//            lp.gravity = Gravity.CENTER;
//            dialog.setCancelable(false);
//            dialog.getWindow().setAttributes(lp);
//            dialog.show();
            bulkupdate.progressbar_load3();
            //Add 3. add 2 lines of code here
//            relativeView = (RelativeLayout)findViewById(R.id.loadingevent);


        }

        // For this method, i don't need to use it yet,so, i left it here
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }

    public void updatt(String dated1){
        updatedated1 = dated1;
        if(bulk.size()<=0){
            Log.d("updatearray00",String.valueOf(bulk.size()));
            bulkupdate.progressbar_stop();

        }else{
            syncEvent ss = new syncEvent();
            ss.execute();
            Log.d("updatearray0",String.valueOf(bulk)+" "+String.valueOf(bulk.size()));
        }
//        for(int i =0;i<bulk.size();i++){
//            String nameee = bulk.get(i);
//            String[] currencies1 = nameee.split(",");
//            String san = currencies1[0];
//            String na = currencies1[1];
//            Integer co = Integer.valueOf(currencies1[2]);
//            String namm = currencies1[3];
//            String debid = currencies1[4];
//            Calendar c = Calendar.getInstance();
//            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//            String todate = df.format(c.getTime());
//            collection(san,dated1);
//            Log.d("updatearray",san+" "+na+" "+String.valueOf(co)+" "+String.valueOf(cam)+" "+dated1);
//            if( cam <= -1){
//                Log.d("updatearray1",san+" "+na+" "+String.valueOf(co));
//                sqlite = new SQLiteHelper(context);
//                database = sqlite.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put("customer_id", san);
//                values.put("customer_name", namm);
//                values.put("collection_amount", na);
//                values.put("other_fee", "0");
//                values.put("discount", "0");
//                values.put("collection_date", dated1);
//                values.put("debit_id",debid);
//                values.put("created_date", todate);
//                database.insert(TABLENAME5, null, values);
//                sqlite.close();
//                database.close();
//
////            notifyDataSetChanged();
//            }else {
//                Log.d("updatearray2",san+" "+na+" "+String.valueOf(co));
//                sqlite = new SQLiteHelper(context);
//                database = sqlite.getWritableDatabase();
//                ContentValues cv = new ContentValues();
//                cv.put("collection_amount",na);
//                database.update(TABLENAME5, cv, "customer_id=? AND collection_date= ? AND debit_id = ?", new String[]{san,dated1,debid});
//                sqlite.close();
//                database.close();
////            notifyDataSetChanged();
//            }
//        }
//        ((Callback)bulkupdate.getApplication()).closed1(dated1, String.valueOf(sesss));
//        ((Callback)bulkupdate.getApplication()).beforebal(dated1, String.valueOf(sesss));
//        ((Callback) bulkupdate.getApplication()).populateRecyclerView23(dated1, dated1 ,String.valueOf(sesss));
    }

    public void updatt1(String dated1){
        updatedated1 = dated1;
        if(bulk.size()<=0){
            Log.d("updatearray00",String.valueOf(bulk.size()));
            bulkupdate.progressbar_stop1();

        }else{
            syncEvent2 ss = new syncEvent2();
            ss.execute();
            Log.d("updatearray0",String.valueOf(bulk)+" "+String.valueOf(bulk.size()));
        }
//        for(int i =0;i<bulk.size();i++){
//            String nameee = bulk.get(i);
//            String[] currencies1 = nameee.split(",");
//            String san = currencies1[0];
//            String na = currencies1[1];
//            Integer co = Integer.valueOf(currencies1[2]);
//            String namm = currencies1[3];
//            String debid = currencies1[4];
//            Calendar c = Calendar.getInstance();
//            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//            String todate = df.format(c.getTime());
//            collection(san,dated1);
//            Log.d("updatearray",san+" "+na+" "+String.valueOf(co)+" "+String.valueOf(cam)+" "+dated1);
//            if( cam <= -1){
//                Log.d("updatearray1",san+" "+na+" "+String.valueOf(co));
//                sqlite = new SQLiteHelper(context);
//                database = sqlite.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put("customer_id", san);
//                values.put("customer_name", namm);
//                values.put("collection_amount", na);
//                values.put("other_fee", "0");
//                values.put("discount", "0");
//                values.put("collection_date", dated1);
//                values.put("debit_id",debid);
//                values.put("created_date", todate);
//                database.insert(TABLENAME5, null, values);
//                sqlite.close();
//                database.close();
//
////            notifyDataSetChanged();
//            }else {
//                Log.d("updatearray2",san+" "+na+" "+String.valueOf(co));
//                sqlite = new SQLiteHelper(context);
//                database = sqlite.getWritableDatabase();
//                ContentValues cv = new ContentValues();
//                cv.put("collection_amount",na);
//                database.update(TABLENAME5, cv, "customer_id=? AND collection_date= ? AND debit_id = ?", new String[]{san,dated1,debid});
//                sqlite.close();
//                database.close();
////            notifyDataSetChanged();
//            }
//        }
//        ((Callback)bulkupdate.getApplication()).closed1(dated1, String.valueOf(sesss));
//        ((Callback)bulkupdate.getApplication()).beforebal(dated1, String.valueOf(sesss));
//        ((Callback) bulkupdate.getApplication()).populateRecyclerView23(dated1, dated1 ,String.valueOf(sesss));
    }

    public void list(String cidd,String didddd){
//        populateRecyclerView1();
        mpho = 0;
        debitam = 0;
//        collect1.clear();
        sqlite = new SQLiteHelper(context);
        database = sqlite.getWritableDatabase();
        String MY_QUERY = "SELECT b.*,a.customer_name,a.id as ID,a.location,a.phone_1,sum(c.collection_amount) as collect,c.other_fee,c.discount FROM dd_customers a LEFT JOIN dd_account_debit b on  b.customer_id = a.id" +
                " Left JOIN dd_collection c on c.debit_id = b.id WHERE  a.tracking_id = ? AND a.debit_type IN(0,1,2) AND a.id = ? AND b.id = ? AND b.active_status = 1  GROUP BY a.id  ";
//   String mm = "SELECT b.*,sum(c.co)";
        Cursor cursor = database.rawQuery(MY_QUERY, new String[]{String.valueOf(sesss),cidd,didddd});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("id");
                    String orde = cursor.getString(index);
                    iii = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("customer_name");
                    String Name = cursor.getString(index);
                    nme = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_date");
                    debitdaaa = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    ddbt = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("location");
                    Loc = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("phone_1");
                    Pho = cursor.getString(index);
                    Log.d("phonee",Pho);
                    mpho = cursor.getInt(index);
                    Log.d("phonee1",String.valueOf(mpho));
                    index = cursor.getColumnIndexOrThrow("other_fee");
                    String Oth = cursor.getString(index);
                    othe = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("discount");
                    String Dis = cursor.getString(index);
                    disss = cursor.getInt(index);
                    index = cursor.getColumnIndexOrThrow("closeing_date");
                    Clo = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("collect");
                    xcol = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("installment_amount");
                    installment = cursor.getString(index);
                    index = cursor.getColumnIndexOrThrow("debit_days");
                    totaldays = cursor.getString(index);
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
                            cloo = Clo;
                            Date debit = sdf.parse(cloo);
                            cloo = sdf1.format(debit);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }}
                    if(xcol == null){
                        xcol ="0";
                    }
                    if(Loc == null || Loc.equalsIgnoreCase("")){
                        Loc = " No location";
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
                    if(Loc == null){
                        Loc = " ";
                    }
                    if(Pho == null){
                        Pho = " ";
                    }
                    if(Oth == null){
                        Oth = " ";
                    }
                    if(Dis == null){
                        Dis = "0";
                    }
                    Integer da = Integer.parseInt(ddbt);
                    debitam = debitam + da ;
                    Log.d("tototo",totaldays);
                    if(totaldays.equalsIgnoreCase("0")){
//                        collect1.add("0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0" + "," + "0"+","+"0"+ "," + "0" + "," + "0" + "," + "0" + "," + "0"+","+"0"+","+"0");
                        Log.d("collleee",String.valueOf(collect1));
                    }else {
                        instamt = Integer.parseInt(installment);
                        pamount = Integer.parseInt(paidamount);
                        Double pa = new Double(pamount);
                        Double ins = new Double(instamt);
                        Integer paaiddd = pamount / instamt;
                        paiddays = (double) pamount / instamt;
                        if(String.valueOf(paiddays).equalsIgnoreCase("NaN")){
                            paiddays = 0.0;
                        }
//                  Log.d("paiddd", String.valueOf(instamt));
                        DecimalFormat df = new DecimalFormat("####.##");
                        paiddays = Double.valueOf(df.format(paiddays));
                        pp = String.valueOf(paiddays);
                        pdays = paaiddd;

                        debitam1 = Integer.parseInt(ddbt);
                        balanceamount = debitam1 - pamount;
                        balanceamount = balanceamount - disss;

                        totda = Double.parseDouble(totaldays);
                        balanceday = totda - paiddays;
                        balanceday = Double.valueOf(df.format(balanceday));

                        xbal = df.format(balanceday);

                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat dff = new SimpleDateFormat("yyyy/MM/dd");
                        String formattedDate = dff.format(c.getTime());
                        try {
                            Date newda = dff.parse(formattedDate);
                            Date debit = dff.parse(debitdaaa);
                            long diffInMillisec = newda.getTime() - debit.getTime();
                            long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
                            Log.d("dates", String.valueOf(diffInDays));
                            if(diffInDays == 0){
                                misda = 0;
                                misam = 0;
                                toda = 0 ;
                                toda1 = 1;
                                missda1 =0.0;
                            }else {
                                String tod = String.valueOf(diffInDays);
                                toda = Integer.parseInt(tod) ;
                                toda1 = Integer.parseInt(tod) + 1;
                                toda2 = Double.parseDouble(tod);
//                                todaaal = new doub
//        Integer po = Integer.parseInt(pp);
                                missiday = paiddays;
                                Integer tota = Integer.parseInt(totaldays);
                                Double mii1 = new Double(toda);
                                Double mii2 = new Double(pdays);
                                misda =  toda - pdays;
                                Double mii;
                                mii = mii1 - mii2;
                                missda1 = toda2 - paiddays;
                                DecimalFormat df1 = new DecimalFormat("####.##");
                                missda1 = Double.valueOf(df1.format(missda1));
                                if(missda1 < 0.0 || missda1 == null){
                                    missda1 = 0.0 ;
                                }
                                Log.d("mmm", String.valueOf(mii));
                                if (misda >= balanceday) {
                                    misam = balanceamount;
//                                        misda = balanceday.intValue();
//                                        missed = String.valueOf(misda);
//                        misda = Integer.parseInt(missed);
                                } else {
                                    misam = misda * instamt;
                                    if(misam < 0){
                                        misam = 0;
                                    }
                                }
                            }
//                mid.setText("Missingdays :"+" "+String.valueOf(misda));
//                mama.setText("Missingamount :"+" "+String.valueOf(misam));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

//                        collect1.add(totaldays + "," + String.valueOf(paiddays) + "," + String.valueOf(balanceday) + "," + String.valueOf(missda1) + "," + String.valueOf(misam) + "," + String.valueOf(paidamount) + "," + String.valueOf(balanceamount) + "," + dbdda + "," + cloo+","+installment+","+Loc
//                                +","+Pho+","+Dis+","+Oth+","+totaldays+","+toda1);


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
            }
//            Log.d("collleee",String.valueOf(collect1));

        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }

    }

    public void collection(String san,String date){
        Log.d("rtrtr",String.valueOf(san));
        sqlite = new SQLiteHelper(context);
        database = sqlite.getWritableDatabase();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String todate = df.format(c.getTime());
        String[] columns = {"collection_amount","id"};
        String order = "collection_date";
        String whereClause = "customer_id=? AND collection_date = ?";
        String[] whereArgs = new String[] {san,date};
        String MY_QUERY = "SELECT a.*,cus.debit_type FROM dd_collection a LEFT JOIN dd_account_debit b  ON b.id = a.debit_id LEFT JOIN dd_customers cus" +
                " ON cus.id = b.customer_id WHERE a.customer_id = ? AND a.collection_date = ? AND b.active_status = 1";
        Cursor cursor = database.rawQuery(MY_QUERY,whereArgs);
//    Cursor cursor = database.query("dd_collection",
//            columns,
//            whereClause,
//            whereArgs,
//            null,
//            null,
//            null);
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("collection_amount");
                    cam = cursor.getInt(index);

                    index = cursor.getColumnIndexOrThrow("debit_type");
                    Integer debit = cursor.getInt(index);

                    if(cam == null){
                        cam = -1 ;
                    }
                    Log.d("rtrtr_debit",String.valueOf(cam)+" "+String.valueOf(debit));
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                cursor.close();
                sqlite.close();
                database.close();
                cam = -1 ;
                Log.d("rtrtr",String.valueOf(cam));

            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public View rowView;
        private TextView mTitle,mid,mtext,debi,close;
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
//            Scroller = itemView.findViewById(R.id.scroll);
            mmtot = itemView.findViewById(R.id.totdays);
            mmpaid = itemView.findViewById(R.id.paidays);
            mmmiss = itemView.findViewById(R.id.misdays);
            mmcol = itemView.findViewById(R.id.colle);
            mmbal = itemView.findViewById(R.id.balan);
            vv = itemView.findViewById(R.id.vi);
            colle = itemView.findViewById(R.id.collection_history);
            vv2 = itemView.findViewById(R.id.vi1);
            lo = itemView.findViewById(R.id.lire);
            mtot = itemView.findViewById(R.id.tdays);
//            mdays = itemView.findViewById(R.id.tot_days);
            mday = itemView.findViewById(R.id.days);
            mpad = itemView.findViewById(R.id.pdays);
            mbad = itemView.findViewById(R.id.bdays);
            mmad = itemView.findViewById(R.id.mdays);
            mmam = itemView.findViewById(R.id.bmamount);
            mpam = itemView.findViewById(R.id.paid);
            mbam = itemView.findViewById(R.id.bamount);
            minst = itemView.findViewById(R.id.instal);
            mdd = itemView.findViewById(R.id.odate);
            mcd = itemView.findViewById(R.id.cdate);
            mid = itemView.findViewById(R.id.slno);
            mTitle = itemView.findViewById(R.id.Name);
            mdisc = itemView.findViewById(R.id.discount);
            mother = itemView.findViewById(R.id.other);
            marea = itemView.findViewById(R.id.location);
            mphone = itemView.findViewById(R.id.phone);
            debi = itemView.findViewById(R.id.debi);
            edit = itemView.findViewById(R.id.edit);
            delet = itemView.findViewById(R.id.delete);
            collect = itemView.findViewById(R.id.amount);
            mtext= itemView.findViewById(R.id.amount1);
//            sqlite = new SQLiteHelper(context);
//            database = sqlite.getWritableDatabase();
            savv = itemView.findViewById(R.id.save);
            moree = itemView.findViewById(R.id.more);
            moree12 = itemView.findViewById(R.id.more1);
            close = itemView.findViewById(R.id.closedd);
            save12 = itemView.findViewById(R.id.save12);
            Typeface ty = Typeface.createFromAsset(context.getAssets(),"fontaswesome1.ttf");
            save12.setTypeface(ty);
            moree12.setTypeface(ty);
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
    //collection1() -get collection data
    //Params - customer id and collection date
    //Created on 25/01/2022
    //Return - NULL
    public void collection1(String san,String date){
        Log.d("rtrtr",String.valueOf(san)+date);
        sqlite = new SQLiteHelper(context);
        database = sqlite.getWritableDatabase();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String todate = df.format(c.getTime());
        String[] columns = {"collection_amount","id"};
        String order = "collection_date";
        String whereClause = "customer_id=? AND collection_date = ?";
        String[] whereArgs = new String[] {san,date};
        String MY_QUERY = "SELECT a.* FROM dd_collection a LEFT JOIN dd_account_debit b  ON b.id = a.debit_id WHERE a.customer_id = ? AND a.collection_date = ? AND b.active_status = 1";
        Cursor cursor = database.rawQuery(MY_QUERY,whereArgs);
//        Cursor cursor = database.query("dd_collection",
//                columns,
//                whereClause,
//                whereArgs,
//                null,
//                null,
//                null);
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("collection_amount");
                    cam1 = cursor.getInt(index);
                    if(cam1 == null){
                        cam1 = -1 ;
                    }
                    Log.d("rtrtr",String.valueOf(cam1));
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                cursor.close();
                sqlite.close();
                database.close();
                cam1 = -1 ;
                Log.d("rtrtr",String.valueOf(cam1));

            }

        }else{
            cursor.close();
            sqlite.close();
            database.close();
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


