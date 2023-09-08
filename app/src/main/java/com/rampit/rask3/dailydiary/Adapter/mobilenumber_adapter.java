package com.rampit.rask3.dailydiary.Adapter;


import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rampit.rask3.dailydiary.Bulkupdate;
import com.rampit.rask3.dailydiary.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class mobilenumber_adapter extends RecyclerView.Adapter<mobilenumber_adapter.ViewHolder> implements Filterable {

    private List<String> mData;
    private List<String> mDatachange;
    private List<Long> mId;
    private LayoutInflater mInflater;
    Context context;
    Integer ssize = 0 ;
    Bulkupdate bulkupdate;


    // data is passed into the constructor
    public mobilenumber_adapter(Context context, List<String> data , Bulkupdate bulkupdate) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
        mDatachange = mData;
        this.bulkupdate = bulkupdate;
        ssize = mData.size() + 1;
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
        View view = mInflater.inflate(R.layout.recyclerview_rowmobile, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.spin5));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.Black));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }
        ssize = ssize - 1 ;
        holder.myTextView.setTextColor(ContextCompat.getColor(context,R.color.White));
        String animal = mData.get(position);
        holder.myTextView.setText(animal);
        holder.par.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bulkupdate.call(animal);
            }
        });
//        holder.myTextView.setText(animal);
    }

    // total number of rows
    @Override
    public int getItemCount() {

        return mData.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;
        LinearLayout par;
        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.num);
            par = itemView.findViewById(R.id.par);

            // convenience method for getting data at click position

            // allows clicks events to be caught
        }
    }
}