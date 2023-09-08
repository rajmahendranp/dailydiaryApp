package com.rampit.rask3.dailydiary.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.rampit.rask3.dailydiary.ExportDB;
import com.rampit.rask3.dailydiary.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecyclerViewAdapter_num1 extends RecyclerView.Adapter<com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdapter_num1.ViewHolder> implements Filterable {

    private List<String> mData;
    private List<String> mDatachange;
    private List<String> mId;
    private LayoutInflater mInflater;
    Context context;
    Integer innn;
    ExportDB ExportDB;
    String path , tyypp;

    // data is passed into the constructor
    public RecyclerViewAdapter_num1(Context context, List<String> data , List<String> data2 , ExportDB ExportDB , String path , String tyypp) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
        this.mDatachange = data2;
        this.ExportDB =  ExportDB;
        this.path = path ;
        this.tyypp = tyypp ;
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
        View view = mInflater.inflate(R.layout.recyclerview_row_num, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String numm = mDatachange.get(position);
        Log.d("datessss",numm);
        final String[] spl = numm.split(",,,");
        String three = spl[3];
        String four = spl[4];
        Integer three_i = Integer.parseInt(three);
        String time = "";

        if(three_i >= 12 && three_i <= 23){
            three_i = three_i - 12 ;
            time = "PM";
        }else{
            time = "AM";
        }
        holder.num.setText(spl[1]+" "+String.valueOf(three_i)+":"+four+" "+time);
//        holder.num.setText(spl[1]);
        holder.num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mm = spl[2];
                Log.d("file_name",mm+" "+path+" "+String.valueOf(position));

                ExportDB.show_filee(path+mm,"Daily");
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
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }



    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView num;


        ViewHolder(View itemView) {
            super(itemView);

            num = itemView.findViewById(R.id.num);

        }

    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
}