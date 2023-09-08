package com.rampit.rask3.dailydiary.Adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rampit.rask3.dailydiary.Closedaccount_activity;
import com.rampit.rask3.dailydiary.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecyclerViewAdapterdelete extends RecyclerView.Adapter<RecyclerViewAdapterdelete.ViewHolder> implements Filterable {

    private List<String> mData;
    private List<String> mDatachange;
    private List<String> mId;
    private ArrayList<String> collect1;
    private ArrayList<String> collect2;
    String selected,cleared,color;
    int sele,clea;
    private LayoutInflater mInflater;
    Context context;
    Integer innn;
    static String languu;
    Integer in;
    Closedaccount_activity closedaccount;

    // data is passed into the constructor
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public RecyclerViewAdapterdelete(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
        this.in = in;
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
        View view = mInflater.inflate(R.layout.recyclerview_rowdelete, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
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

//        holder.colhis.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String namee = mData.get(position);
//                String[] currencies1 = namee.split(",");
//                String savn = currencies1[7];
////                Closedaccount_activity.popup1(savn);
//            }
//        });
        String animal = mData.get(position);
        String name = mData.get(position);
        Log.d("Names5",name);
        String[] currencie = name.split(",,,");
        String d = currencie[0];
        String d1 = currencie[1];
        String d2 = currencie[2];
        String d3 = currencie[3];
        String d4 = currencie[4];
        String d5 = currencie[5];
        String d6 = currencie[6];
        String d7= currencie[7];
        String d8 = currencie[8];
        holder.slno.setText(holder.itemView.getResources().getString(R.string.sno)+"\n"+d);
        holder.debit_amount.setText(holder.itemView.getResources().getString(R.string.deb)+"\n"+d1);
        holder.interest.setText(holder.itemView.getResources().getString(R.string.in)+"\n"+d2);
        holder.document_charge.setText(holder.itemView.getResources().getString(R.string.doc)+"\n"+d3);
        holder.collection_amount.setText(holder.itemView.getResources().getString(R.string.col)+"\n"+d4);
        holder.discount.setText(holder.itemView.getResources().getString(R.string.dis)+"\n"+d5);
        holder.credit.setText(holder.itemView.getResources().getString(R.string.cre)+"\n"+d6);
        holder.debit.setText(holder.itemView.getResources().getString(R.string.deb1)+"\n"+d7);
        holder.other_fee.setText(holder.itemView.getResources().getString(R.string.oth)+"\n"+d8);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }



    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView slno,debit_amount,interest,document_charge,collection_amount,discount,credit,debit,other_fee;
       
        ViewHolder(View itemView) {
            super(itemView);
            slno = itemView.findViewById(R.id.slno);
            debit_amount = itemView.findViewById(R.id.debit_amount);
            interest = itemView.findViewById(R.id.interest);
            document_charge = itemView.findViewById(R.id.document_charge);
            collection_amount = itemView.findViewById(R.id.collection_amount);
            discount = itemView.findViewById(R.id.discount);
            credit = itemView.findViewById(R.id.credit);
            debit = itemView.findViewById(R.id.debit);
            other_fee = itemView.findViewById(R.id.other_fee);
        }

    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
    }
    //expand() - Expand an view
    //Params - view , time taken and height to expand
    //Created on 25/01/2022
    //Return - NULL
    public static void expand(final View v, int duration, int targetHeight) {

        int prevHeight  = v.getHeight();

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();

    }
    //collapse() - collapse an view
    //Params - view , time taken and height to collapse
    //Created on 25/01/2022
    //Return - NULL
    public static void collapse(final View v, int duration, int targetHeight) {
        int prevHeight  = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }
    //setResources() - Set language to app
    //Params - application context and language name
    //Created on 25/01/2022
    //Return - NULL
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void setResources(Context context, String language) {
        Locale locale = null;
        Log.d("langu",languu);
        if (language.equalsIgnoreCase("en")) {
            locale = new Locale("en");
        }else if (language.equalsIgnoreCase("ta")){
            locale = new Locale("ta");
        }
        Resources res=context.getResources();
        DisplayMetrics dm=res.getDisplayMetrics();
        android.content.res.Configuration configuration=res.getConfiguration();
        configuration.setLocale(locale);
        res.updateConfiguration(configuration,dm);
    }
    // allows clicks events to be caught
}