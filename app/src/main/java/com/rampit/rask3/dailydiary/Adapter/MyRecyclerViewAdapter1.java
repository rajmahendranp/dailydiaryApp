package com.rampit.rask3.dailydiary.Adapter;

import android.app.AlertDialog;
import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.rampit.rask3.dailydiary.Employee_details;
import com.rampit.rask3.dailydiary.R;
import com.rampit.rask3.dailydiary.Splash;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyRecyclerViewAdapter1 extends RecyclerView.Adapter<MyRecyclerViewAdapter1.ViewHolder> implements Filterable {

    private List<String> mData;
    private List<String> mDatachange;
    private List<Long> mId;
    private LayoutInflater mInflater;
    Context context;
    Integer ssize = 0 ;
    String dateeee = null;



    // data is passed into the constructor
    public MyRecyclerViewAdapter1(Context context, List<String> data, ArrayList<Long> ID) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
        this.mId = ID;
        mDatachange = mData;
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
        View view = mInflater.inflate(R.layout.recyclerview_rowcollectionalert, parent, false);
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
        holder.mid.setTextColor(ContextCompat.getColor(context,R.color.White));
        holder.myTextView.setTextColor(ContextCompat.getColor(context,R.color.White));
        holder.mamount.setTextColor(ContextCompat.getColor(context,R.color.White));
        String animal = mData.get(position);
        String[] currencies = animal.split(",");
        String s = currencies[0];
        String sS = currencies[1];
        String sSs = currencies[2];
//        String sSs1 = currencies[3];
        Log.d("curreeer", sS);
       if(dateeee == null || dateeee.equalsIgnoreCase("") || dateeee.equalsIgnoreCase("null")){
           dateeee = sS;
           Log.d("dateated_1", sS+" "+dateeee);
           holder.delete.setVisibility(View.GONE);
       }else{
           Log.d("dateated_200", sS+" "+dateeee);
           if(dateeee.equalsIgnoreCase(sS)){
               Log.d("dateated_2", sS+" "+dateeee);
               holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.Red));
               holder.delete.setVisibility(View.GONE);
               holder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
//                       Log.d("dateated_210", sS+" "+sSs1);
                       AlertDialog.Builder alertbox = new AlertDialog.Builder(context,R.style.AlertDialogTheme);
                       String logmsg = holder.itemView.getResources().getString(R.string.are_you_sure_delete_collection);
                       String cann = holder.itemView.getResources().getString(R.string.cancel);
                       String warr = holder.itemView.getResources().getString(R.string.warning);
                       String logo = holder.itemView.getResources().getString(R.string.ok);
                       alertbox.setMessage(logmsg);
                       alertbox.setTitle(warr);
                       alertbox.setIcon(R.drawable.dailylogo);
                       alertbox.setNeutralButton(cann,
                               new DialogInterface.OnClickListener() {

                                   public void onClick(DialogInterface arg0,
                                                       int arg1) {

                                   }
                               });
                       alertbox.setPositiveButton(logo,
                               new DialogInterface.OnClickListener() {

                                   public void onClick(DialogInterface arg0,
                                                       int arg1) {

                                   }
                               });
                       alertbox.show();
                   }
               });
//               holder.delete.setVisibility(View.VISIBLE);
           }else{
               Log.d("dateated_3", sS+" "+dateeee);
               dateeee = sS;
               holder.delete.setVisibility(View.GONE);
           }
       }
        holder.mid.setText(String.valueOf(ssize));
        holder.myTextView.setText(sS);
        holder.mamount.setText(sSs);
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
        TextView myTextView, mid,mamount,delete;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.date);
            mid = itemView.findViewById(R.id.slno);
            mamount = itemView.findViewById(R.id.amount);
            delete = itemView.findViewById(R.id.delete);
            // convenience method for getting data at click position

            // allows clicks events to be caught
        }
    }
}