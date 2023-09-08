package com.rampit.rask3.dailydiary.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.rampit.rask3.dailydiary.Account;
import com.rampit.rask3.dailydiary.Addaccount;
import com.rampit.rask3.dailydiary.R;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyRecyclerAdapter3 extends RecyclerView.Adapter<MyRecyclerAdapter3.ViewHolder> implements Filterable {

    private List<String> mData;
    private List<String> mDatachange;
    private List<Long> mId;
    private LayoutInflater mInflater;
    Context context;
    Integer innn;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    ArrayList<String> Names = new ArrayList<>();
    private final Account bulkupdate;
    Integer ses;

    // data is passed into the constructor
    public MyRecyclerAdapter3(Context context, List<String> data, Account addaccount, Integer ses) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
        mDatachange = mData;
        bulkupdate = addaccount;
        this.ses = ses;
    }
    @Override
    public Filter getFilter() {
        return null;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mData = new ArrayList<String>();
        if (charText.length() == 0) {
            // mCleanCopyDataset we always contains the unaltered and the filter (full) copy of the list of data
            mData.addAll(mDatachange);
        } else {
            for (String item : mDatachange) {

                // we iterate through all the items on the list and if any item contains the search text, we add it to a new list mDataset
                if (item.toLowerCase(Locale.getDefault()).contains(charText)) {
                    mData.add(item);
                }
            }
        }
// method notifyDataSetChanged () allows you to update the list on the screen after filtration
        notifyDataSetChanged();
    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_rowpopacc, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
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
        String animal = mData.get(position);
        String[] currencies = animal.split(",");
        String s = currencies[0];
        String sS = currencies[1];
        Integer sss = Integer.valueOf(s);
//        String sSs = currencies[2];
        Log.d("curreeer",s);
//        if(sSs.equalsIgnoreCase("-1")){
//            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
//            holder.myTextView.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
//        }
//        if(sSs.equalsIgnoreCase("0")){
        if(sss >=21){
            holder.del.setVisibility(View.VISIBLE);
        }
        holder.mid.setTextColor(ContextCompat.getColor(context,R.color.White));
        holder.myTextView.setTextColor(ContextCompat.getColor(context,R.color.White));
//        }

//        if(sSs.equalsIgnoreCase("2")){
//            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.Red));
//            holder.myTextView.setTextColor(ContextCompat.getColor(context,R.color.Red));
//        }
        holder.mid.setText(s);
        holder.myTextView.setText(sS);
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String animal = mData.get(position);
                String[] currencies = animal.split(",");
                final String s = currencies[0];
//                populateRecyclerView(s);
//                bulkupdate.delete(s);
                Log.d("idid",s);
                sqlite = new SQLiteHelper(context);
                database = sqlite.getReadableDatabase();
                Cursor cursor = database.rawQuery("SELECT sum(a.amount) as amount FROM dd_accounting a INNER JOIN dd_accounting_type b on b.id = a.acc_type_id WHERE  a.tracking_id = ? AND a.acc_type_id = ? ",
                        new String[]{String.valueOf(ses),s});
                if (cursor != null) {
                    if (cursor.getCount() != 0) {
                        cursor.moveToFirst();
                        do {
                            int index;
//                    index = cursor.getColumnIndexOrThrow("id");
//                    String id = cursor.getString(index);
                            index = cursor.getColumnIndexOrThrow("amount");
                            String total = cursor.getString(index);
                            if(total == null){
                                total = "0";
                            }
                            Integer tott = Integer.parseInt(total);
                            if(tott == 0){
                                AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext(),R.style.AlertDialogTheme);
                                String enn = view.getContext().getResources().getString(R.string.delete_value);
                                String war = view.getContext().getResources().getString(R.string.warning);
                                String ook = view.getContext().getResources().getString(R.string.cancel);
                                String del = view.getContext().getResources().getString(R.string.delete);
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
                                                sqlite = new SQLiteHelper(context);
                                                database = sqlite.getReadableDatabase();
                                                Log.d("ddee",s);
                                                String table = "dd_accounting_type";
                                                String whereClause = "id=?";
//        database.delete(table, whereClause, whereArgs);
                                                String[] whereArgs = new String[] {s};
                                                database.delete(table, whereClause, whereArgs);
                                                sqlite.close();
                                                database.close();
                                                Intent name= new Intent(context, Addaccount.class);
                                                context.startActivity(name);
                                            }
                                        });
                                alertbox.show();
                            }else{
                                Log.d("idid",total);
                                AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext(),R.style.AlertDialogTheme);
                                String enn = view.getContext().getResources().getString(R.string.account_alert);
                                String war = view.getContext().getResources().getString(R.string.warning);
                                String ook = view.getContext().getResources().getString(R.string.cancel);
                                String del = view.getContext().getResources().getString(R.string.delete);
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
        });
//        holder.myTextView.setText(animal);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView,mid;
        Button del;
        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.Name);
            mid = itemView.findViewById(R.id.slno);
            del = itemView.findViewById(R.id.delete);
//            sqlite = new SQLiteHelper(context);
//            database = sqlite.getWritableDatabase();

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(innn == 0) {
//                        String pos = String.valueOf(mId.get(getAdapterPosition()));
////                        String number = pos.replaceAll("[^0-9]", "");
//                        Log.d("newdebit", pos);
//                        Intent name = new Intent(context, Newdebit.class);
//                        name.putExtra("ID", pos);
//                        name.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        v.getContext().startActivity(name);
//                    }else if (innn == 1 ){
//                        String pos = String.valueOf(mId.get(getAdapterPosition()));
////                        String number = pos.replaceAll("[^0-9]", "");
//                        Log.d("newdebit", pos);
//                        Intent name = new Intent(context, Collection.class);
//                        name.putExtra("ID", pos);
//                        name.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        v.getContext().startActivity(name);
//                    }
//                }
//            });
        }

    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
    }
    public void populateRecyclerView(String s) {
//        Names.clear();

//    if(Names.size() == 0){
//        recyclerView.setVisibility(View.GONE);
//        noo.setVisibility(View.VISIBLE);
//    }else {
//        recyclerView.setVisibility(View.VISIBLE);
//        noo.setVisibility(View.GONE);
//        mAdapter = new RecyclerViewAdaptercurrent(getApplicationContext(), Names);
//        recyclerView.setAdapter(mAdapter);
//    }

    }

    // allows clicks events to be caught
}