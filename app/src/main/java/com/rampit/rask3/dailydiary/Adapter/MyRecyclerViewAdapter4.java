package com.rampit.rask3.dailydiary.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.rampit.rask3.dailydiary.Collection;
import com.rampit.rask3.dailydiary.Newdebit;
import com.rampit.rask3.dailydiary.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyRecyclerViewAdapter4 extends RecyclerView.Adapter<MyRecyclerViewAdapter4.ViewHolder> implements Filterable {

    private List<String> mData;
    private List<String> mDatachange;
    private List<Long> mId;
    private LayoutInflater mInflater;
    Context context;
    Integer innn;


    // data is passed into the constructor
    public MyRecyclerViewAdapter4(Context context, List<String> data, ArrayList<Long> ID, Integer integer) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
        this.innn = integer;
        this.mId = ID;
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
        View view = mInflater.inflate(R.layout.recyclerview_row1, parent, false);
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
        String[] currencies = animal.split(",");
        String s = currencies[0];
        String sS = currencies[1];
        String sSs = currencies[2];
        Log.d("curreeer",s);
        if(sSs.equalsIgnoreCase("-1")){
            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.myTextView.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
        }
        if(sSs.equalsIgnoreCase("0")){
            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.myTextView.setTextColor(ContextCompat.getColor(context,R.color.White));
        }
        if(sSs.equalsIgnoreCase("1")){
            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.myTextView.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
        }
        if(sSs.equalsIgnoreCase("2")){
            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.myTextView.setTextColor(ContextCompat.getColor(context,R.color.Red));
        }if(sSs.equalsIgnoreCase("3")){
            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.myTextView.setTextColor(ContextCompat.getColor(context,R.color.Green));
        }

        holder.mid.setText(s);
        holder.myTextView.setText(sS);
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

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.Name);
            mid = itemView.findViewById(R.id.slno);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(innn == 0) {
                        String pos = String.valueOf(mData.get(getAdapterPosition()));
//                        String number = pos.replaceAll("[^0-9]", "");
                        Log.d("newdebit", pos);
                        String[] currencie = pos.split(",");
                        String d = currencie[3];
                        Intent name = new Intent(context, Newdebit.class);
                        name.putExtra("ID", d);
                        name.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        v.getContext().startActivity(name);
                    }else if (innn == 1 ){
                        String pos = String.valueOf(mData.get(getAdapterPosition()));
                        String[] currencie = pos.split(",");
                        String d = currencie[3];
//                        String number = pos.replaceAll("[^0-9]", "");
                        Log.d("newdebit", pos);
                        Intent name = new Intent(context, Collection.class);
                        name.putExtra("ID", d);
                        name.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        v.getContext().startActivity(name);
                    }
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