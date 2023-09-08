package com.rampit.rask3.dailydiary.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.rampit.rask3.dailydiary.Fragment.close_fragment;
import com.rampit.rask3.dailydiary.R;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Close_acc_adapter extends RecyclerView.Adapter<com.rampit.rask3.dailydiary.Adapter.Close_acc_adapter.ViewHolder> implements Filterable {

    private List<String> mData;
    private List<String> mDatachange;
    private LayoutInflater mInflater;
    Context context;
    Integer innn;
    close_fragment close_fragment;

    // data is passed into the constructor
    public Close_acc_adapter(Context context, List<String> data , close_fragment close_fragment) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
        this.close_fragment = close_fragment;
        mDatachange = mData;
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
//                if (item.toLowerCase(Locale.getDefault()).contains(charText)) {
//                    mData.add(item);
//                }
                String[] currencie = item.split(",");
                String d = currencie[1];
                if (d.contains(charText)) {
                    Log.d("fileterd1",item);
                    Log.d("fileterd2",d);
//                    Integer nh = Integer.parseInt(d);
//                    if(nh.equals(Integer.parseInt(charText))){
//                        Log.d("fileterd3",d);
//                    }
                    mData.add(item);
                }
            }
        }
// method notifyDataSetChanged () allows you to update the list on the screen after filtration
        notifyDataSetChanged();
    }
    public void filter1(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mData = new ArrayList<String>();
        if (charText.length() == 0) {
            // mCleanCopyDataset we always contains the unaltered and the filter (full) copy of the list of data
            mData.addAll(mDatachange);
        } else {
            for (String item : mDatachange) {

                // we iterate through all the items on the list and if any item contains the search text, we add it to a new list mDataset
//                if (item.toLowerCase(Locale.getDefault()).contains(charText)) {
//                    mData.add(item);
//                }
                String[] currencie = item.split(",");
                String d = currencie[0];
                if (d.contains(charText)) {
                    Log.d("fileterd1",item);
                    Log.d("fileterd2",d);
//                    Integer nh = Integer.parseInt(d);
//                    if(nh.equals(Integer.parseInt(charText))){
//                        Log.d("fileterd3",d);
//                    }
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
        View view = mInflater.inflate(R.layout.recyclerview_row_close, parent, false);
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
        String[] currencies = animal.split(",,,");
        String s = currencies[0];
        String sS = currencies[1];
        String sSs = currencies[2];
        final String d = currencies[3];
        final String d1 = currencies[4];
        final String d21 = currencies[5];
        Log.d("curreeer",s);


        holder.slno.setText(sS);
        holder.Name.setText(d21);
        holder.collection.setText(d);
        holder.discount.setText(d1);
        holder.debit.setText(sSs);
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                String formattedDate = df.format(c.getTime());
                SQLiteHelper sqlite = new SQLiteHelper(context);
                SQLiteDatabase database = sqlite.getWritableDatabase();
//            Log.d("iid",id);
                ContentValues values = new ContentValues();
                values.put("debit_type","1");
                database.update("dd_customers", values,"id = ?", new String[]{s});
                close_fragment.closed1(context);

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
        TextView slno,Name,debit,collection,discount;
        LinearLayout line;
        Button close;

        ViewHolder(View itemView) {
            super(itemView);
            slno = itemView.findViewById(R.id.slno);
            Name = itemView.findViewById(R.id.Name);
            debit = itemView.findViewById(R.id.debit);
            collection = itemView.findViewById(R.id.collection);
            discount = itemView.findViewById(R.id.discount);
            line = itemView.findViewById(R.id.line);
            close = itemView.findViewById(R.id.close);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
}