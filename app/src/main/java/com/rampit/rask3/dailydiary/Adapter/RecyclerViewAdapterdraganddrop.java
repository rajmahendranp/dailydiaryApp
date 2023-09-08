package com.rampit.rask3.dailydiary.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rampit.rask3.dailydiary.Draganddrop;
import com.rampit.rask3.dailydiary.ItemMoveCallback4;
import com.rampit.rask3.dailydiary.R;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RecyclerViewAdapterdraganddrop extends RecyclerView.Adapter<RecyclerViewAdapterdraganddrop.MyViewHolder> implements Filterable, ItemMoveCallback4.ItemTouchHelperContract {

    private final Draganddrop bulkupdate;
    private ArrayList<String> data;
    private ArrayList<String> data1;
    private ArrayList<String> curr;
    private ArrayList<String> NNP;
    ArrayList<String> bulk = new ArrayList<>();
    private ArrayList<String> collection;
    private ArrayList<String> datafull;
    private ArrayList<String> iid;
    private ArrayList<String> collect1;
    HashMap<Integer,String> edittextlist=new HashMap<>();
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    Integer in ;
    Context context;
    Integer  collection_amount,cam,cam1,fromord,toord,fromord1;
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
    String iii,nme,paidamount,installment,totaldays,ddbt,debitdaaa,pp,dbdda,Clo,cloo,xcol,xbal,Name;
    Integer disss,toda1,debitam,lastcurorder,lastnipniporder;

    @Override
    public void onRowMoved(RecyclerViewAdapterdraganddrop.MyViewHolder myViewHolder,int fromPosition, int toPosition) {
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
        String hhh = data.get(toPosition);
        String[] currencies = hhh.split(",");
        String ssss = currencies[2];
        Integer hjh = Integer.parseInt(ssss);
        String hhh1 = data.get(fromPosition);
        String[] currencies1 = hhh1.split(",");
        String ssss1 = currencies1[2];
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
    public void onRowSelected(RecyclerViewAdapterdraganddrop.MyViewHolder myViewHolder, int position) {
        myViewHolder.rowView.setBackgroundColor(Color.GRAY);
        selected = String.valueOf(position);
        sele = position;
        String animal = data.get(position);
        String[] currencies = animal.split(",");

        String sss = currencies[0];
        String ssss = currencies[2];
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
    public void onRowClear(RecyclerViewAdapterdraganddrop.MyViewHolder myViewHolder, int position) {

    }

    @Override
    public void oncle(RecyclerViewAdapterdraganddrop.MyViewHolder myViewHolder, int from, int to) {
        myViewHolder.rowView.setBackgroundColor(Color.GRAY);
        Log.d("CURRE",String.valueOf(curr.size()));
        Log.d("NIPNIP",String.valueOf(NNP.size()));
        Log.d("cleared",String.valueOf(from));
        Log.d("cleared1",String.valueOf(to));
        Log.d("qwqw",idi);
        String hhh = data1.get(to);
        String[] currencies = hhh.split(",");
        String ssss12 = currencies[2];
        final Integer hjh = Integer.parseInt(ssss12);
        String hhh1 = data1.get(from);
        String[] currencies1 = hhh1.split(",");
        String ssss1000 = currencies1[2];
        fromord1 = Integer.parseInt(ssss1000);
        Log.d("selclear",String.valueOf(ssss12)+" "+String.valueOf(ssss1000));
        if(fromord1 <= 5000 && hjh >= 5000){
            AlertDialog.Builder alertbox = new AlertDialog.Builder(myViewHolder.rowView.getContext());
            myViewHolder.rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.spin5));
            String enn = myViewHolder.rowView.getContext().getResources().getString(R.string.cannot_swipe);
            String war = myViewHolder.rowView.getContext().getResources().getString(R.string.warning);
            String ook = myViewHolder.rowView.getContext().getResources().getString(R.string.ok);
            String del = myViewHolder.rowView.getContext().getResources().getString(R.string.delete);
            alertbox.setMessage(enn);
            alertbox.setTitle(war);
            alertbox.setIcon(R.drawable.dailylogo);
            alertbox.setNeutralButton(ook,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {
                            fromord1 = fromord1 - 1;
//hjh = hjh - 1;
                            cleared = String.valueOf(curr.size() - 1);
                            selected = String.valueOf(fromord1);
                            Intent nn = new Intent(context,Draganddrop.class);
                            nn.putExtra("selected",selected);
                            nn.putExtra("cleared",cleared);
                            nn.putExtra("idi",idi);
                            context.startActivity(nn);
                            Log.d("recyc1",String.valueOf(fromord1+" "+String.valueOf(hjh)));
                            Log.d("obulk3",cleared+" "+selected);
                        }
                    });
            alertbox.show();

        }else if(fromord1 >= 5000 && hjh <= 5000){
            AlertDialog.Builder alertbox = new AlertDialog.Builder(myViewHolder.rowView.getContext());
            myViewHolder.rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.spin5));
            String enn = myViewHolder.rowView.getContext().getResources().getString(R.string.cannot_swipe);
            String war = myViewHolder.rowView.getContext().getResources().getString(R.string.warning);
            String ook = myViewHolder.rowView.getContext().getResources().getString(R.string.ok);
            String del = myViewHolder.rowView.getContext().getResources().getString(R.string.delete);
            alertbox.setMessage(enn);
            alertbox.setTitle(war);
            alertbox.setIcon(R.drawable.dailylogo);
            alertbox.setNeutralButton(ook,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {
                            fromord1 =fromord1 - 1;
                            cleared = "5000";
                            selected = String.valueOf(fromord1);
                            Intent nn = new Intent(context,Draganddrop.class);
                            nn.putExtra("selected",selected);
                            nn.putExtra("cleared",cleared);
                            nn.putExtra("idi",idi);
                            context.startActivity(nn);
                            Log.d("recyc2",String.valueOf(fromord1+" "+String.valueOf(hjh)));
                            Log.d("obulk3",cleared+" "+selected);
                        }
                    });
            alertbox.show();

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

            Intent nn = new Intent(context,Draganddrop.class);
            nn.putExtra("selected",selected);
            nn.putExtra("cleared",cleared);
            nn.putExtra("idi",idi);
            nn.putExtra("selectedvaaa",String.valueOf(to));
            nn.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(nn);
        }
    }



    public RecyclerViewAdapterdraganddrop(ArrayList<String> names, ArrayList<String> data, ArrayList<String> CURR, ArrayList<String> NNP, Integer ses, Context context, Integer in, Draganddrop bulkupdate, Integer lastcurorder, Integer lastnipniporder) {
        this.data = data;
//        this.collection = collection;
        datafull = data;
        this.data1 = names;
//        iid = ID;
        curr = CURR;
        this.NNP = NNP;
        sesss = ses;
        this.collect1 = collect1;
        this.context = context;
        this.bulkupdate = bulkupdate;
        this.in = in;
        this.lastcurorder = lastcurorder;
        this.lastnipniporder = lastnipniporder;
//        try {
//            Date debit = df1.parse(formattedDate1);
//            formattedDate1 = df.format(debit);
//            Log.d("deae1",formattedDate1);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }
    @Override
    public Filter getFilter() {
        return null;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        data = new ArrayList<String>();
        if (charText.length() == 0) {
            // mCleanCopyDataset we always contains the unaltered and the filter (full) copy of the list of data
            data.addAll(datafull);
        } else {
            for (String item : datafull) {

                // we iterate through all the items on the list and if any item contains the search text, we add it to a new list mDataset
                String[] currencie = item.split(",");
                String d = currencie[0];
                if (d.contains(charText)) {
                    Log.d("fileterd1",item);
                    Log.d("fileterd2",d);
//                    Integer nh = Integer.parseInt(d);
//                    if(nh.equals(Integer.parseInt(charText))){
//                        Log.d("fileterd3",d);
//                    }
                    data.add(item);
                }
            }
        }
// method notifyDataSetChanged () allows you to update the list on the screen after filtration
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_rowdraganddrop, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        holder.mTitle.setText(data.get(position));
//        holder.setIsRecyclable(false);
        holder.save12.setTextColor(ContextCompat.getColor(context, R.color.bulk_color));
        if(in == 0){
//            holder.savv.setBackgroundResource(R.drawable.save_orange1);
        }else{
//            holder.savv.setBackgroundResource(R.drawable.save_blue1);
        }
        if(position %2 == 1)
        {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.spin5));
        }
        else
        {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.Black));
        }
        holder.collect.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0)
                {
                    if(in == 0){
                        holder.save12.setTextColor((ContextCompat.getColor(context, R.color.colorPrimary)));
                        holder.dragg.setTextColor((ContextCompat.getColor(context, R.color.colorPrimary)));
                    }else{
                        holder.save12.setTextColor((ContextCompat.getColor(context, R.color.backblue)));
                        holder.dragg.setTextColor((ContextCompat.getColor(context, R.color.backblue)));
                    }
                }else{
                    holder.save12.setTextColor(ContextCompat.getColor(context, R.color.bulk_color));

                }

            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
            public void afterTextChanged(Editable s) {
//                dateString = datt.getText().toString();
//                Log.d("datata",dateString);

            }
        });
holder.save12.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Log.d("popiop",lastcurorder+" "+lastnipniporder);
        int color=holder.save12.getCurrentTextColor();
        String hexColor = String.format("#%06X", (0xFFFFFF & color));
        if(hexColor.equalsIgnoreCase("#1564C0") || hexColor.equalsIgnoreCase("#f15a31")){
            holder.save12.setTextColor(ContextCompat.getColor(context, R.color.bulk_color));
        }
        Log.d("hexco",hexColor);
        String colle = holder.collect.getText().toString();
        if(colle == null || colle.equalsIgnoreCase("") || colle.equalsIgnoreCase("0")){
            AlertDialog.Builder alertbox = new AlertDialog.Builder(holder.itemView.getContext(),R.style.AlertDialogTheme);
            String enn = holder.itemView.getContext().getResources().getString(R.string.enter_value);
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
        }else{
            String ddaa = data.get(position);
            String[] currencies = ddaa.split(",");
            String s1id = currencies[0];
            String s2oid = currencies[2];
            Integer ooi = Integer.parseInt(colle);
            Integer ooi1 = Integer.parseInt(s2oid);
            if(ooi1<=5000 && ooi >= 5000){
                AlertDialog.Builder alertbox = new AlertDialog.Builder(holder.itemView.getContext(),R.style.AlertDialogTheme);
                String enn = holder.itemView.getContext().getResources().getString(R.string.amountgreat12);
                String war = holder.itemView.getContext().getResources().getString(R.string.warning);
                String ook = holder.itemView.getContext().getResources().getString(R.string.ok);
                String del = holder.itemView.getContext().getResources().getString(R.string.delete);
                alertbox.setMessage(enn+" "+"5000");
                alertbox.setTitle(war);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setNeutralButton(ook,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                            }
                        });
                alertbox.show();
            }else if(ooi1>=5000 && ooi <= 5000){
                AlertDialog.Builder alertbox = new AlertDialog.Builder(holder.itemView.getContext(),R.style.AlertDialogTheme);
                String enn = holder.itemView.getContext().getResources().getString(R.string.amountgreat121);
                String war = holder.itemView.getContext().getResources().getString(R.string.warning);
                String ook = holder.itemView.getContext().getResources().getString(R.string.ok);
                String del = holder.itemView.getContext().getResources().getString(R.string.delete);
                alertbox.setMessage(enn+" "+"5000");
                alertbox.setTitle(war);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setNeutralButton(ook,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                            }
                        });
                alertbox.show();
            }else{

                if(ooi >5000){
                    if(ooi <= lastnipniporder){
                        Intent nn = new Intent(context,Draganddrop.class);
                        nn.putExtra("selected",s2oid);
                        nn.putExtra("cleared",colle);
                        nn.putExtra("idi",s1id);
                        nn.putExtra("selectedvaaa",String.valueOf(colle));
                        nn.addFlags(FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(nn);
                        Log.d("popiop",s1id+" "+s2oid);
                    }else{
                        AlertDialog.Builder alertbox = new AlertDialog.Builder(holder.itemView.getContext(),R.style.AlertDialogTheme);
                        String enn = holder.itemView.getContext().getResources().getString(R.string.amountgreat121);
                        String war = holder.itemView.getContext().getResources().getString(R.string.warning);
                        String ook = holder.itemView.getContext().getResources().getString(R.string.ok);
                        String del = holder.itemView.getContext().getResources().getString(R.string.delete);
                        alertbox.setMessage(enn+" "+String.valueOf(lastnipniporder));
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
                }else{
                    if(ooi <= lastcurorder){
                        Intent nn = new Intent(context,Draganddrop.class);
                        nn.putExtra("selected",s2oid);
                        nn.putExtra("cleared",colle);
                        nn.putExtra("idi",s1id);
                        nn.putExtra("selectedvaaa",String.valueOf(colle));
                        nn.addFlags(FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(nn);
                        Log.d("popiop",s1id+" "+s2oid);
                    }else{
                        AlertDialog.Builder alertbox = new AlertDialog.Builder(holder.itemView.getContext(),R.style.AlertDialogTheme);
                        String enn = holder.itemView.getContext().getResources().getString(R.string.amountgreat121);
                        String war = holder.itemView.getContext().getResources().getString(R.string.warning);
                        String ook = holder.itemView.getContext().getResources().getString(R.string.ok);
                        String del = holder.itemView.getContext().getResources().getString(R.string.delete);
                        alertbox.setMessage(enn+" "+String.valueOf(lastcurorder));
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
        }

    }
});
        String name = data.get(position);
        Log.d("curreeer",name);
        String[] currencies = name.split(",");
        final String s = currencies[0];
        String sS = currencies[1];
        String sSs = currencies[2];
        String nn = currencies[3];
        String nnnj = currencies[4];
holder.collect.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String op = holder.collect.getHint().toString();

    }
});

        Log.d("curreeer",sSs);
        if(nn.equalsIgnoreCase("-1")){
//            holder.ll.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.mTitle.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.collect.setHintTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.collect.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.Yellow));

        }else if(nn.equalsIgnoreCase("0")){
//            holder.ll.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.mTitle.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.collect.setHintTextColor(ContextCompat.getColor(context,R.color.White));
            holder.collect.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.White));

        }else if(nn.equalsIgnoreCase("1")){
//            holder.ll.setBackgroundColor(Color.parseColor("#B0E0E6"));
            holder.mTitle.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.collect.setHintTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.collect.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));

        }else if(nn.equalsIgnoreCase("2")){
//            holder.ll.setBackgroundColor(Color.parseColor("#DC143C"));
            holder.mTitle.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.collect.setHintTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.collect.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.Red));

        }else if(nn.equalsIgnoreCase("3")){
//            holder.ll.setBackgroundColor(Color.parseColor("#DC143C"));
            holder.mTitle.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.collect.setHintTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.collect.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.Green));

        }
        holder.collect.setHint(sSs);
        Integer oo = Integer.parseInt(sSs);
        if(oo <= 5000){
            holder.mid.setText(nnnj);
        }else{
            holder.mid.setText(sSs);
        }
        holder.mTitle.setText(sS);
//        holder.debi.setText(sSs);


    }
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }

//collection() - get collection of user in selected date
//Params - customer id and collection date
//Created on 25/01/2022
//Return - NULL
    public void collection(String san,String date){
        Log.d("rtrtr",String.valueOf(san));
        Calendar c = Calendar.getInstance();
        sqlite = new SQLiteHelper(context);
        database = sqlite.getWritableDatabase();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String todate = df.format(c.getTime());
        String[] columns = {"collection_amount","id"};
        String order = "collection_date";
        String whereClause = "customer_id=? AND collection_date = ?";
        String[] whereArgs = new String[] {san,date};
        String MY_QUERY = "SELECT a.* FROM dd_collection a LEFT JOIN dd_account_debit b  ON b.id = a.debit_id WHERE a.customer_id = ? AND a.collection_date = ? AND b.active_status = 1";
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
                    if(cam == null){
                        cam = -1 ;
                    }
                    Log.d("rtrtr",String.valueOf(cam));
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
        private TextView mTitle,mid,mtext,debi;
        private TextView mtot,mpad,mbad,mmad,mmam,mpam,mbam,mcd,mdd,minst,marea,mphone,mother,mdisc,mdays,mday,save12,dragg;
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
            dragg = itemView.findViewById(R.id.drag);

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
            save12 = itemView.findViewById(R.id.save12);
            Typeface ty = Typeface.createFromAsset(context.getAssets(),"fontaswesome1.ttf");
            save12.setTypeface(ty);
            dragg.setTypeface(ty);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    String customer_id = String.valueOf(iid.get(getAdapterPosition()));
//                    Log.d("iidd",customer_id);

                }
            });

        }
    }
@Override
    public int getItemCount() {
        return data.size();
    }





}


