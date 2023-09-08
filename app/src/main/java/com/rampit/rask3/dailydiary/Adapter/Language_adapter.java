package com.rampit.rask3.dailydiary.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.rampit.rask3.dailydiary.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Language_adapter extends RecyclerView.Adapter<Language_adapter.ViewHolder> implements Filterable {

    private List<String> mData;
    private List<String> mDatachange;
    private LayoutInflater mInflater;
    Context context;
    Register register;
    String lann ;



    // data is passed into the constructor
    public Language_adapter(Context context, List<String> data, Register register,String lann) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
        mDatachange = mData;
        this.register = register;
        this.lann = lann;
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
        View view = mInflater.inflate(R.layout.recyclerview_row_language, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.background_color));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.background_color));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }

//        holder.mid.setTextColor((ContextCompat.getColor(context, R.color.black)));

//        holder.mid.setTextColor(ContextCompat.getColor(context,R.color.White));
        final SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ii = pref.getString("theme","");
        if(ii.equalsIgnoreCase("")){
            ii = "1";
        }
        Log.d("theelo",ii);
        Integer in = Integer.valueOf(ii) ;
        if(in == 0){
            Log.d("theelo","thh");
            if(lann.equalsIgnoreCase("en") && position == 0){
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            }
            if(lann.equalsIgnoreCase("fr") && position == 1){
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            }
            if(lann.equalsIgnoreCase("ta") && position == 2){
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            }
        }else{
            Log.d("theelo","th1h");
            if(lann.equalsIgnoreCase("en") && position == 0){
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.backblue));
            }
            if(lann.equalsIgnoreCase("fr") && position == 1){
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.backblue));
            }
            if(lann.equalsIgnoreCase("ta") && position == 2){
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.backblue));
            }
//            recreate();
        }

        String animal = mData.get(position);
        holder.mid.setText(animal);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 0){
                    register.selected_language("en");
                }else  if(position == 1){
                    register.selected_language("fr");
                }else  if(position == 2){
                    register.selected_language("ta");
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
        TextView  mid,tickk;

        ViewHolder(View itemView) {
            super(itemView);
            tickk = itemView.findViewById(R.id.tickk);
            mid = itemView.findViewById(R.id.state_textview);

            // convenience method for getting data at click position

            // allows clicks events to be caught
        }
    }
}