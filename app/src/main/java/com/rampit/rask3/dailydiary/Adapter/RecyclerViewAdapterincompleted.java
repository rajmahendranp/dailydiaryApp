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
import android.widget.TextView;

import com.rampit.rask3.dailydiary.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecyclerViewAdapterincompleted extends RecyclerView.Adapter<RecyclerViewAdapterincompleted.ViewHolder> implements Filterable {

    private List<String> mData;
    private List<String> mDatachange;
    private List<String> mId;
    private LayoutInflater mInflater;
    Context context;
    Integer innn;


    // data is passed into the constructor
    public RecyclerViewAdapterincompleted(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;

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
        View view = mInflater.inflate(R.layout.recyclerview_rowincompleted, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = mData.get(position);
        String name = mData.get(position);
        String[] currencies = name.split(",");
        String s = currencies[0];
        String sS = currencies[1];
        String sSs = currencies[2];
        String sS1 = currencies[3];
        String sSs2 = currencies[4];
        Log.d("curreeer",s);
        holder.mid.setText(s);
        holder.mTitle.setText(sS);
        holder.mcoll.setText(sSs);
        holder.mdeb.setText(sS1);
        holder.mbal.setText(sSs2);

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
        private TextView mTitle,mid,mcoll,mdeb,mbal;

        ViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.Name);
            mid = itemView.findViewById(R.id.slno);
            mcoll = itemView.findViewById(R.id.collection);
            mdeb = itemView.findViewById(R.id.debit);
            mbal = itemView.findViewById(R.id.balance);

        }

    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
}