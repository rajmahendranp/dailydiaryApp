package com.rampit.rask3.dailydiary.Adapter;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.rampit.rask3.dailydiary.Debit;
import com.rampit.rask3.dailydiary.ItemMoveCallback;
import com.rampit.rask3.dailydiary.Newdebit;
import com.rampit.rask3.dailydiary.R;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class RecyclerViewAdapterdebit extends RecyclerView.Adapter<RecyclerViewAdapterdebit.MyViewHolder> implements Filterable, ItemMoveCallback.ItemTouchHelperContract {

    private ArrayList<String> data;
    private ArrayList<String> datafull;
    private ArrayList<String> datafull1;
    private ArrayList<String> iid;
    private ArrayList<String> collect1;
    ArrayList<String> Names1 = new ArrayList<>();
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    Context context;
    String selected,cleared,color;
    int sele,clea;
    String num,edi,del;
    String sess;
    Integer in ;
    Debit debit;
    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(data, i, i + 1);
                Log.i("hjbhk",fromPosition+" "+toPosition);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(data, i, i - 1);
                Log.i("hjbhk",fromPosition+" "+toPosition);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRowSelected(MyViewHolder myViewHolder,int position) {
        myViewHolder.rowView.setBackgroundColor(Color.GRAY);
        selected = String.valueOf(position);
        sele = position;
        Log.d("selected",selected);
    }
    @Override
    public void onRowClear(MyViewHolder myViewHolder,int position) {
        myViewHolder.rowView.setBackgroundColor(Color.WHITE);
        cleared = String.valueOf(position);
        clea = position;
//        Intent nn = new Intent(context,HomeActivity.class);
//        nn.putExtra("selected",selected);
//        nn.putExtra("cleared",cleared);
//        context.startActivity(nn);
//        Intent intent = new Intent("custom-messagee");
//        //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
//        intent.putExtra("selected",selected);
//        intent.putExtra("cleared",cleared);
//        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
    public void oncle(MyViewHolder myViewHolder,int from,int to){
        myViewHolder.rowView.setBackgroundColor(Color.WHITE);
        Log.d("cleared",String.valueOf(from));
        Log.d("cleared",String.valueOf(to));
        cleared = String.valueOf(to);
        clea = to;
        selected = String.valueOf(from);
//        Intent nn = new Intent(context,HomeActivity.class);
//        nn.putExtra("selected",selected);
//        nn.putExtra("cleared",cleared);
//        context.startActivity(nn);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle,mid,mDoc,mInt,mDeb;
        private Button nip;
        private TextView delet,mtot,mpad,mbad,mmad,mmam,mpam,mbam,mcd,mdd,mintr,minst,mday,moree,edit;
        View rowView;
        private View vv,vv2;
        private LinearLayout lo;
        private Button savv;


        public MyViewHolder(View itemView) {
            super(itemView);
            rowView = itemView;

            lo = itemView.findViewById(R.id.lire);
            mtot = itemView.findViewById(R.id.tdays);
            mpad = itemView.findViewById(R.id.pdays);
            mbad = itemView.findViewById(R.id.bdays);
            mmad = itemView.findViewById(R.id.mdays);
            mmam = itemView.findViewById(R.id.bmamount);
            mintr = itemView.findViewById(R.id.intrr);
            minst = itemView.findViewById(R.id.intst);
            mpam = itemView.findViewById(R.id.paid);
            mbam = itemView.findViewById(R.id.bamount);
            mdd = itemView.findViewById(R.id.odate);
            mcd = itemView.findViewById(R.id.cdate);
            mTitle = itemView.findViewById(R.id.Name);
            mDeb = itemView.findViewById(R.id.debitt);
            mid = itemView.findViewById(R.id.slno);
            mDoc = itemView.findViewById(R.id.docu);
            mInt = itemView.findViewById(R.id.intree);
            mday = itemView.findViewById(R.id.days);
            edit = itemView.findViewById(R.id.edit);
            delet = itemView.findViewById(R.id.delete);
//            sqlite = new SQLiteHelper(context);
//            database = sqlite.getWritableDatabase();
            moree = itemView.findViewById(R.id.more);

            vv = itemView.findViewById(R.id.vi);
            vv2 = itemView.findViewById(R.id.vi1);
            Typeface ty = Typeface.createFromAsset(context.getAssets(),"fontaswesome1.ttf");
            moree.setTypeface(ty);
            edit.setTypeface(ty);
            delet.setTypeface(ty);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    String pos = String.valueOf(data.get(getAdapterPosition()));
//                    String number  = pos.replaceAll("[^0-9]", "");
//                    Log.d("newdebit",number);
//                    for(int i = 0; i < number.length(); i++) {
//                        int j = Character.digit(number.charAt(i), 10);
//                        num = String.valueOf(j);
////                    Log.d("editpos", String.valueOf(j));
//                        break;
//                    }
//                    num = iid.get(getAdapterPosition());
//                    color = Names1.get(getAdapterPosition());
//                    Intent nan= new Intent(context, Debit.class);
//                    nan.putExtra("ididi",num);
//                    nan.putExtra("co",color);
//                    Log.d("col",color);
//                    v.getContext().startActivity(nan);

                }
            });

        }
    }

    public RecyclerViewAdapterdebit(ArrayList<String> data,ArrayList<String> data1, ArrayList<String> collect1, Integer in, Context context, String edpr, String depr, ArrayList<String> ID, Integer ses, Debit debit) {
        this.data = data;
        datafull = collect1;
        datafull1 = data1 ;
        iid = ID;
        this.context = context;
        edi = edpr;
        del = depr;
        this.collect1 = collect1;
        this.in = in ;
        this.debit = debit;
        sess = String.valueOf(ses);
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
                String d = currencie[15];
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
    public void filter1(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        collect1 = new ArrayList<String>();
        if (charText.length() == 0) {
            // mCleanCopyDataset we always contains the unaltered and the filter (full) copy of the list of data
            collect1.addAll(datafull);
        } else {
            for (String item : datafull) {

                // we iterate through all the items on the list and if any item contains the search text, we add it to a new list mDataset
                String[] currencie = item.split(",");
                String d = currencie[13];
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row2, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String name = collect1.get(position);
        if(in == 0){
//            holder.edit.setBackgroundResource(R.drawable.edit);
//            holder.delet.setBackgroundResource(R.drawable.delete);
//            holder.moree.setBackgroundResource(R.drawable.view_more);
        }else{
//            holder.delet.setBackgroundResource(R.drawable.delete_blue);
//            holder.edit.setBackgroundResource(R.drawable.edit_blue);
//            holder.moree.setBackgroundResource(R.drawable.viewmore_blue);
        }
        String[] currencies = name.split(",");
        String s = currencies[12];
        String sS = currencies[13];
        String sSs = currencies[14];
        String sSss = currencies[18];
        String sSssss = currencies[19];
        String sSsssss = currencies[15];
        String sSsssss1 = currencies[16];
        Integer nn =Integer.parseInt(sSsssss1);

        Log.d("curreeer",s);
        if(nn > 5000){
            holder.mid.setText(sSsssss1);
        }else{
            holder.mid.setText(sSsssss);
        }
        holder.mTitle.setText(sS);
        holder.mDeb.setText(sSs);
        holder.mDoc.setText(sSss);
        String colll = collect1.get(position);
        String[] currencie = colll.split(",");
        String d = currencie[0];
        String d1 = currencie[1];
        String d2 = currencie[2];
        String d3 = currencie[3];
        String d4 = currencie[4];
        String d5 = currencie[5];
        String d6 = currencie[6];
        String d7= currencie[7];
        String d8 = currencie[8];
        String d9 = currencie[9];
        String d10 = currencie[10];
        String d11 = currencie[11];
        holder.mtot.setText(d);
        holder.mpad.setText(d1);
        holder.mbad.setText(d2);
        holder.mmad.setText(d3);
        holder.mmam.setText("\u20B9"+" "+d4);
        holder.mpam.setText("\u20B9"+" "+d5);
        holder.mbam.setText("\u20B9"+" "+d6);
        holder.mdd.setText(d7);
        holder.mcd.setText(d8);
        holder.mintr.setText(d9);
        holder.minst.setText(d10);
        holder.mday.setText(d11);
        Log.d("curreeer",d11);
        if(sSssss.equalsIgnoreCase("-1")){
//            holder.ll.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.mTitle.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mDeb.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
//            holder.mInt.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mDoc.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mtot.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mpad.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mbad.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mmad.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mmam.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mpam.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mbam.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mdd.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mcd.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mintr.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.minst.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mday.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
//            color = "white";
            Names1.add("white");

        }else if(sSssss.equalsIgnoreCase("0")){
//            holder.ll.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.mTitle.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mDeb.setTextColor(ContextCompat.getColor(context,R.color.White));
//            holder.mInt.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mDoc.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mtot.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mpad.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mbad.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mmad.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mmam.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mpam.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mbam.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mdd.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mcd.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mintr.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.minst.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mday.setTextColor(ContextCompat.getColor(context,R.color.White));
//            color = "white";
            Names1.add("white");

        }else if(sSssss.equalsIgnoreCase("1")){
            holder.mTitle.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mDeb.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
//            holder.mInt.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mDoc.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mtot.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mpad.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mbad.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mmad.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mmam.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mpam.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mbam.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mdd.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mcd.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mintr.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.minst.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mday.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
//        color = "blue";
            Names1.add("blue");
        }else if(sSssss.equalsIgnoreCase("2")){
//            holder.ll.setBackgroundColor(Color.parseColor("#DC143C"));
            holder.mTitle.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mDeb.setTextColor(ContextCompat.getColor(context,R.color.Red));
//            holder.mInt.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mDoc.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mtot.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mpad.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mbad.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mmad.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mmam.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mpam.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mbam.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mdd.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mcd.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mintr.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.minst.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mday.setTextColor(ContextCompat.getColor(context,R.color.Red));
//        color = "red";
            Names1.add("red");
        }
        holder.moree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.vv.getVisibility() == View.VISIBLE){
                    expand(holder.lo, 500, LinearLayout.LayoutParams.WRAP_CONTENT);
                    holder.vv.setVisibility(View.GONE);
                    holder.vv2.setVisibility(View.VISIBLE);
                    if(in == 0){
                        String tty = context.getString(R.string.arrow_up);
                        holder.moree.setText(tty);
//                        holder.moree.setBackgroundResource(R.drawable.view_up);
                    }else{
                        String tty = context.getString(R.string.arrow_up);
                        holder.moree.setText(tty);
//                        holder.moree.setBackgroundResource(R.drawable.viewup_blue);
                    }
                }else if(holder.vv2.getVisibility() == View.VISIBLE){
                    collapse(holder.lo, 500, 0);
                    holder.vv.setVisibility(View.VISIBLE);
                    holder.vv2.setVisibility(View.GONE);
                    if(in == 0){
                        String tty = context.getString(R.string.arrow_down);
                        holder.moree.setText(tty);
//                        holder.moree.setBackgroundResource(R.drawable.view_up);
                    }else{
                        String tty = context.getString(R.string.arrow_down);
                        holder.moree.setText(tty);
//                        holder.moree.setBackgroundResource(R.drawable.viewup_blue);
                    }
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
        if(edi.equalsIgnoreCase("0")){
            holder.edit.setVisibility(View.INVISIBLE);
        }
        if(del.equalsIgnoreCase("0")){
            holder.delet.setVisibility(View.INVISIBLE);
        }
        holder.delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String pos = String.valueOf(data.get(position));
//                num  = pos.replaceAll("[^0-9]", "");
                String name11 = collect1.get(position);
                String[] currencies1 = name11.split(",");
                num = currencies1[12];
                String cussss = currencies[17];
//                num = data.get(position);
                Log.d("delete",num+" "+cussss);

                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext(),R.style.AlertDialogTheme);
                String enn = v.getContext().getResources().getString(R.string.delete_value);
                String war = v.getContext().getResources().getString(R.string.warning);
                String ook = v.getContext().getResources().getString(R.string.cancel);
                String del = v.getContext().getResources().getString(R.string.delete);
                alertbox.setMessage(enn);
                alertbox.setTitle(war);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setNeutralButton(ook,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                            }
                        });
                alertbox.setPositiveButton(del,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
//                                sqlite = new SQLiteHelper(this);
                                String name1 = collect1.get(position);
                                String[] currencies = name1.split(",");
                                String s = currencies[17];
                                String s1 = currencies[16];
                                sqlite = new SQLiteHelper(context);
                                database = sqlite.getReadableDatabase();
//                                Log.d("er33", String.valueOf(s));
                                String MY_QUERY = "SELECT SUM(a.collection_amount) as collect FROM  dd_account_debit b LEFT JOIN dd_collection a ON a.debit_id = b.id where a.customer_id = ? AND b.active_status = ?  ORDER BY a.collection_date DESC";

                                Cursor cursor = database.rawQuery(MY_QUERY,new String[]{s,"1"});

                                if (cursor != null){
                                    if(cursor.getCount() != 0){
                                        cursor.moveToFirst();
                                        do{
                                            int index;



                                            index = cursor.getColumnIndexOrThrow("collect");
                                            String dbamount = cursor.getString(index);
                                            Integer tt = cursor.getInt(index);

                                            if(dbamount == null){
                                                Log.d("er33", "opp1");
                                                sqlite.close();
                                                database.close();
                                                sqlite = new SQLiteHelper(context);
                                                database = sqlite.getWritableDatabase();
                                                String table = "dd_account_debit";
                                                String whereClause = "id=?";
                                                String[] whereArgs = new String[] {num};
                                                database.delete(table, whereClause, whereArgs);

                                                ContentValues cv = new ContentValues();
                                                cv.put("debit_type","-1");
                                                String[] args =  new String[]{String.valueOf(s)};
                                                database.update("dd_customers", cv,  "id=? ",args);
                                                sqlite.close();
                                                database.close();

//                                                String MY_QUERY1 = "SELECT * FROM dd_customers WHERE debit_type IN (0,1) AND tracking_id = ? ORDER BY order_id DESC LIMIT 0,1 ";
//                                                Cursor cursor1 = database.rawQuery(MY_QUERY1,new String[]{sess});
//                                                if (cursor1 != null) {
//                                                    if (cursor1.getCount() != 0) {
//                                                        cursor1.moveToFirst();
//                                                        do {
//                                                            int index1;
//                                                            index1 = cursor1.getColumnIndexOrThrow("order_id");
//                                                            String ordered = cursor1.getString(index1);
//                                                            index1 = cursor1.getColumnIndexOrThrow("id");
//                                                            String ordered1 = cursor1.getString(index1);
//
//                                                            Log.d("er33qw", String.valueOf(ordered));
//                                                            debit.updatee(s1,ordered,ordered1);
//                                                        }
//                                                        while (cursor1.moveToNext());
//                                                        database.close();
//                                                        sqlite.close();
//                                                        cursor.close();
//                                                    }else{
//                                                        database.close();
//                                                        sqlite.close();
//                                                        cursor.close();
//                                                    }
//                                                }else{
//                                                    database.close();
//                                                    sqlite.close();
//                                                    cursor.close();
//                                                }
                                                String tabb = "dd_customers";
                                                Intent name= new Intent(context, Debit.class);
                                                name.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                context.startActivity(name);
                                            }else{
                                                if(tt.equals(0)){
                                                    Log.d("er33", String.valueOf(tt));
                                                    sqlite.close();
                                                    database.close();
                                                    sqlite = new SQLiteHelper(context);
                                                    database = sqlite.getWritableDatabase();
                                                    Log.d("er33", "opp");
                                                    String table = "dd_account_debit";
                                                    String whereClause = "id=?";
                                                    String[] whereArgs = new String[] {num};
                                                    database.delete(table, whereClause, whereArgs);

//                                                    String MY_QUERY1 = "SELECT * FROM dd_customers WHERE debit_type IN (0,1) AND tracking_id = ? ORDER BY order_id DESC LIMIT 0,1 ";
//                                                    Cursor cursor1 = database.rawQuery(MY_QUERY1,new String[]{sess});
//                                                    if (cursor1 != null) {
//                                                        if (cursor1.getCount() != 0) {
//                                                            cursor1.moveToFirst();
//                                                            do {
//                                                                int index1;
//                                                                index1 = cursor1.getColumnIndexOrThrow("order_id");
//                                                                String ordered = cursor1.getString(index1);
//                                                                index1 = cursor1.getColumnIndexOrThrow("id");
//                                                                String ordered1 = cursor1.getString(index1);
//
//                                                                Log.d("er33qw", String.valueOf(ordered));
//                                                                debit.updatee(s1,ordered,ordered1);
//                                                            }
//                                                            while (cursor1.moveToNext());
//                                                        }
//                                                    }
                                                    Log.d("er33", "opp");
                                                    ContentValues cv = new ContentValues();
                                                    cv.put("debit_type","-1");
                                                    String[] args =  new String[]{String.valueOf(s)};
                                                    database.update("dd_customers", cv,  "id=? ",args);
                                                    sqlite.close();
                                                    database.close();
                                                    String tabb = "dd_customers";
                                                    Intent name= new Intent(context, Debit.class);
                                                    name.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    context.startActivity(name);
//                                                    debit.alert();
                                                }else{
                                                    Log.d("er331", String.valueOf(tt));
                                                    debit.alert();
                                                }
                                            }
                                        }
                                        while (cursor.moveToNext());
                                        database.close();
                                        sqlite.close();
                                        cursor.close();
                                    }else{
                                        sqlite.close();
                                        database.close();
                                        sqlite = new SQLiteHelper(context);
                                        database = sqlite.getWritableDatabase();
                                        Log.d("er33", "opp");
                                        String table = "dd_account_debit";
                                        String whereClause = "id=?";
                                        String[] whereArgs = new String[] {num};
                                        database.delete(table, whereClause, whereArgs);

                                        ContentValues cv = new ContentValues();
                                        cv.put("debit_type","-1");
                                        String[] args =  new String[]{String.valueOf(s)};
                                        database.update("dd_customers", cv,  "id=? ",args);
                                        sqlite.close();
                                        database.close();
//                                        String MY_QUERY1 = "SELECT * FROM dd_customers WHERE debit_type IN (0,1) AND tracking_id = ? ORDER BY order_id DESC LIMIT 0,1 ";
//                                        Cursor cursor1 = database.rawQuery(MY_QUERY1,new String[]{sess});
//                                        if (cursor1 != null) {
//                                            if (cursor1.getCount() != 0) {
//                                                cursor1.moveToFirst();
//                                                do {
//                                                    int index1;
//                                                    index1 = cursor1.getColumnIndexOrThrow("order_id");
//                                                    String ordered = cursor1.getString(index1);
//                                                    index1 = cursor1.getColumnIndexOrThrow("id");
//                                                    String ordered1 = cursor1.getString(index1);
//
//                                                    Log.d("er33qw", String.valueOf(ordered));
//                                                    debit.updatee(s1,ordered,ordered1);
//                                                }
//                                                while (cursor1.moveToNext());
//                                                database.close();
//                                                sqlite.close();
//                                                cursor.close();
//                                            }else{
//                                                database.close();
//                                                sqlite.close();
//                                                cursor.close();
//                                            }
//                                        }else{
//                                            database.close();
//                                            sqlite.close();
//                                            cursor.close();
//                                        }
                                        String tabb = "dd_customers";
                                        Intent name= new Intent(context, Debit.class);
                                        name.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(name);

                                    }
                                }else{
                                    sqlite.close();
                                    database.close();
                                    sqlite = new SQLiteHelper(context);
                                    database = sqlite.getWritableDatabase();
                                    Log.d("er33", "opp");
                                    String table = "dd_account_debit";
                                    String whereClause = "id=?";
                                    String[] whereArgs = new String[] {num};
                                    database.delete(table, whereClause, whereArgs);

                                    ContentValues cv = new ContentValues();
                                    cv.put("debit_type","-1");
                                    String[] args =  new String[]{String.valueOf(s)};
                                    database.update("dd_customers", cv,  "id=? ",args);
                                    sqlite.close();
                                    database.close();
//                                    String MY_QUERY1 = "SELECT * FROM dd_customers WHERE debit_type IN (0,1) AND tracking_id = ? ORDER BY order_id DESC LIMIT 0,1 ";
//                                    Cursor cursor1 = database.rawQuery(MY_QUERY1,new String[]{sess});
//                                    if (cursor1 != null) {
//                                        if (cursor1.getCount() != 0) {
//                                            cursor1.moveToFirst();
//                                            do {
//                                                int index1;
//                                                index1 = cursor1.getColumnIndexOrThrow("order_id");
//                                                String ordered = cursor1.getString(index1);
//                                                index1 = cursor1.getColumnIndexOrThrow("id");
//                                                String ordered1 = cursor1.getString(index1);
//
//                                                Log.d("er33qw", String.valueOf(ordered));
//                                                debit.updatee(s1,ordered,ordered1);
//                                            }
//                                            while (cursor1.moveToNext());
//                                            database.close();
//                                            sqlite.close();
//                                            cursor.close();
//                                        }else{
//                                            database.close();
//                                            sqlite.close();
//                                            cursor.close();
//                                        }
//                                    }else{
//                                        database.close();
//                                        sqlite.close();
//                                        cursor.close();
//                                    }
                                    String tabb = "dd_customers";
                                    Intent name= new Intent(context, Debit.class);
                                    name.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(name);

                                }

                            }
                        });
                alertbox.show();
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String pos = String.valueOf(data.get(position));
//                String number  = pos.replaceAll("[^0-9]", "");
//                for(int i = 0; i < number.length(); i++) {
//                    int j = Character.digit(number.charAt(i), 10);
//                    num = String.valueOf(j);
////                    Log.d("editpos", String.valueOf(j));
//                 break;
//                }
                String name1 = collect1.get(position);
                String[] currencies = name1.split(",");
                Integer oo = Integer.valueOf(currencies[16]);
                if(oo > 5000){
                    num = currencies[16];
                }else{
                    num = currencies[15];
                }
//                num = iid.get(position);
//                String num = String.valueOf();
                Log.d("editpos",num);
                Intent deb= new Intent(context, Newdebit.class);
                deb.putExtra("idi",num);
                deb.putExtra("type","1");
                deb.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(deb);
            }
        });
    }


    @Override
    public int getItemCount() {
        return collect1.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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



}


