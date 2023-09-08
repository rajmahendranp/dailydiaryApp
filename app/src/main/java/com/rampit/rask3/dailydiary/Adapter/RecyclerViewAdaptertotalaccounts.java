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
import com.rampit.rask3.dailydiary.Totalaccounts;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecyclerViewAdaptertotalaccounts extends RecyclerView.Adapter<RecyclerViewAdaptertotalaccounts.ViewHolder> implements Filterable {

    private List<String> mData;
    private List<String> mDatachange;
    private LayoutInflater mInflater;
    Context context;
    Totalaccounts totalaccounts;


    // data is passed into the constructor
    public RecyclerViewAdaptertotalaccounts(Context context, List<String> data , Totalaccounts totalaccounts) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
        this.totalaccounts = totalaccounts;
        mDatachange = mData;
    }
    @Override
    public Filter getFilter() {
        return null;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mData = new ArrayList<String>();
        if (charText.equalsIgnoreCase("0")) {
            // mCleanCopyDataset we always contains the unaltered and the filter (full) copy of the list of data
            mData.addAll(mDatachange);
        } else {
            for (String item : mDatachange) {
                String[] currencie = item.split(",,,");
                String d = currencie[3];
                Integer dint = Integer.parseInt(d);
                if (dint>1) {
                    mData.add(item);
                }
                // we iterate through all the items on the list and if any item contains the search text, we add it to a new list mDataset
//                if (item.toLowerCase(Locale.getDefault()).contains(charText)) {
//
//                }
            }
        }
// method notifyDataSetChanged () allows you to update the list on the screen after filtration
        notifyDataSetChanged();
    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row_totalcounts, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
        final String s = currencies[0];
        String sS = currencies[1];
        String sSs = currencies[2];
        String sSs1 = currencies[3];
        String sSs2 = currencies[4];
        String sSs3 = currencies[5];
        Log.d("curreeer",s);
//        if(sSs.equalsIgnoreCase("-1")){
//            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
//            holder.myTextView.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
//        }
//        if(sSs.equalsIgnoreCase("0")){
//            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.White));
//            holder.myTextView.setTextColor(ContextCompat.getColor(context,R.color.White));
//        }
//        if(sSs.equalsIgnoreCase("1")){
//            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
//            holder.myTextView.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
//        }
//        if(sSs.equalsIgnoreCase("2")){
//            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.Red));
//            holder.myTextView.setTextColor(ContextCompat.getColor(context,R.color.Red));
//        }if(sSs.equalsIgnoreCase("3")){
//            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.Green));
//            holder.myTextView.setTextColor(ContextCompat.getColor(context,R.color.Green));
//        }

        holder.mid.setText(s);
        holder.myTextView.setText(sS);
        holder.total_a.setText(sSs);
        holder.active.setText(sSs1);
        holder.inactive.setText(sSs2);
        holder.session.setText(sSs3);

        final Integer accttt = Integer.parseInt(sSs1);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(accttt>1){
                    totalaccounts.closing_acc(s);
                }
            }
        });



//        holder.myTextView.setText(animal);
    }

    // total number of rows
    @Override
    public int getItemCount() {

        Log.d("countcount_size",String.valueOf(mData.size()));
        return mData.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView,mid,total_a,active,inactive,session;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.Name);
            mid = itemView.findViewById(R.id.slno);
            total_a = itemView.findViewById(R.id.total);
            active = itemView.findViewById(R.id.active);
            inactive = itemView.findViewById(R.id.inactive);
            session = itemView.findViewById(R.id.session);

        }

    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
}