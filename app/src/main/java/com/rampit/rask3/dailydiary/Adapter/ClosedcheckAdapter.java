package com.rampit.rask3.dailydiary.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.rampit.rask3.dailydiary.Collection;
import com.rampit.rask3.dailydiary.Newdebit;
import com.rampit.rask3.dailydiary.R;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;
import com.rampit.rask3.dailydiary.closed_check;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ClosedcheckAdapter extends RecyclerView.Adapter<ClosedcheckAdapter.ViewHolder> implements Filterable {

    private List<String> mData;
    private List<String> mDatachange;
    private List<String> mId;
    private LayoutInflater mInflater;
    Context context;
    Integer innn;
    closed_check closed_check;

    // data is passed into the constructor
    public ClosedcheckAdapter(Context context, List<String> data , closed_check closed_check) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
        this.closed_check = closed_check ;
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
        View view = mInflater.inflate(R.layout.recyclerview_row_closedcheck, parent, false);
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
        final String[] currencies = animal.split(",");
        String s = currencies[0];
        String sS = currencies[1];
        String sSs = currencies[2];
        Log.d("curreeer_slno",s);


        holder.mid.setText(s);
        holder.myTextView.setText(sS);
        holder.debit.setText(sSs);
        holder.clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               closed_check.close(String.valueOf(currencies[7]));
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
        TextView myTextView,mid,debit;
        Button clean;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.Name);
            mid = itemView.findViewById(R.id.slno);
            debit = itemView.findViewById(R.id.debitt);
            clean = itemView.findViewById(R.id.clean);
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