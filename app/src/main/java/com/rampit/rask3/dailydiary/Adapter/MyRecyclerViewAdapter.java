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

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

    private List<String> mData;
    private List<String> mDatachange;
    private List<String> mId;
    private LayoutInflater mInflater;
    Context context;
    Integer innn;


    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, List<String> data, ArrayList<String> ID, Integer integer) {
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
        Log.d("curreeer_slno",s);
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
                        String[] currencie = pos.split(",");
                        String d = currencie[3];
                        String d1 = currencie[1];
//                        String number = pos.replaceAll("[^0-9]", "");
                        Log.d("newdebit", pos);
                        Intent name = new Intent(context, Newdebit.class);
                        name.putExtra("ID", d);
                        name.putExtra("DID", d1);

                        name.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        v.getContext().startActivity(name);
                    }else if (innn == 1 ){
                        String pos = String.valueOf(mData.get(getAdapterPosition()));
                        String[] currencie = pos.split(",");
                        String d = currencie[3];
                        String d1 = currencie[1];
//                        String number = pos.replaceAll("[^0-9]", "");
                        Log.d("newdebit", pos);
                        Intent name = new Intent(context, Collection.class);
                        name.putExtra("ID", d);
                        name.putExtra("DID", d1);
                        name.putExtra("selecte",String.valueOf(getAdapterPosition()));
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

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    // allows clicks events to be caught
}