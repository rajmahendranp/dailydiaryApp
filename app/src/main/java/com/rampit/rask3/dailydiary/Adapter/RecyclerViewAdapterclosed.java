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

public class RecyclerViewAdapterclosed extends RecyclerView.Adapter<RecyclerViewAdapterclosed.ViewHolder> implements Filterable {

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
    public RecyclerViewAdapterclosed(Context context, List<String> data, ArrayList<String> collect1, ArrayList<String> collect2, String language, Integer in) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
        this.collect1 = collect1;
        this.collect2 = collect2;
        languu = language;
        Log.d("langu",languu);
        setResources(context,language);
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
        View view = mInflater.inflate(R.layout.recyclerview_rowclose, parent, false);
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
        if(in == 0){
            holder.moree.setBackgroundResource(R.drawable.view_more);
//            holder.colhis.setBackgroundColor(ContextCompat.getColor(context, R.color.spin5));
        }else{
            holder.moree.setBackgroundResource(R.drawable.viewmore_blue);
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
        String[] currencies = name.split(",");
        String s = currencies[0];
        String sS = currencies[1];
        String sSs = currencies[2];
        String sSss = currencies[3];
        String sSsss = currencies[4];
        String sSssss = currencies[5];

        Log.d("curreeer",name);
        holder.mid.setText(s);
        holder.mTitle.setText(sS);
        holder.mDeb.setText(sSs);
        holder.mDoc.setText(sSss);
//        holder.mInt.setText(sSsss);
        String colll = collect1.get(position);
//        String col2 = collect2.get(position);


        String[] currencie = colll.split(",");
        String d = currencie[0];
        String d1 = currencie[1];
        String d2 = currencie[2];
        String d3 = currencie[3];
        String d4 = currencie[4];
        String d5 = currencie[5];
        String d6 = currencie[6];
        String d7= currencie[7];
        String d8 = currencie[8];
        String d9 = currencie[9];
        String d10 = currencie[10];
        String d11 = currencie[11];
        String lastcoll = currencie[12];
        Log.d("ccooll",lastcoll);
        holder.mtot.setText(d);
        holder.mpad.setText(d1);
        holder.mbad.setText(d2);
        holder.mmad.setText(d3);
        holder.mmam.setText("\u20B9"+" "+d4);
        holder.mpam.setText("\u20B9"+" "+d5);
        holder.mbam.setText("\u20B9"+" "+d6);
        holder.mdd.setText(d7);
        holder.mcd.setText(d8);
        holder.mintr.setText(d9);
        holder.minst.setText(d10);
        holder.hlast.setText("\u20B9"+" "+lastcoll);
        holder.mdisc.setText("\u20B9"+" "+d11);
        Log.d("curreeer",d);
        if(sSssss.equalsIgnoreCase("0")){
//            holder.ll.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.mTitle.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mDeb.setTextColor(ContextCompat.getColor(context,R.color.White));
//            holder.mInt.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mDoc.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mtot.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mpad.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mbad.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mmad.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mmam.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mpam.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mbam.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mdd.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mcd.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mintr.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.minst.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.hlast.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mdisc.setTextColor(ContextCompat.getColor(context,R.color.White));
//            color = "white";
//            Names1.add("white");

        }
        else if(sSssss.equalsIgnoreCase("1")){
            holder.mTitle.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mDeb.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
//            holder.mInt.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mDoc.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mtot.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mpad.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mbad.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mmad.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mmam.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mpam.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mbam.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mdd.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mcd.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mintr.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.minst.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.hlast.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mdisc.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));

            //        color = "blue";
//            Names1.add("blue");
        }
        else if(sSssss.equalsIgnoreCase("2")){
//            holder.ll.setBackgroundColor(Color.parseColor("#DC143C"));
            holder.mTitle.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mid.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mDeb.setTextColor(ContextCompat.getColor(context,R.color.Red));
//            holder.mInt.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mDoc.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mtot.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mpad.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mbad.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mmad.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mmam.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mpam.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mbam.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mdd.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mcd.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mintr.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.minst.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.hlast.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mdisc.setTextColor(ContextCompat.getColor(context,R.color.Red));

//        color = "red";
//            Names1.add("red");
        }
        holder.moree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.vv.getVisibility() == View.VISIBLE){
                    expand(holder.lo, 500, LinearLayout.LayoutParams.WRAP_CONTENT);
                    holder.vv.setVisibility(View.GONE);
                    holder.vv2.setVisibility(View.VISIBLE);
                    if(in == 0){
                        holder.moree.setBackgroundResource(R.drawable.view_up);
                    }else{
                        holder.moree.setBackgroundResource(R.drawable.viewup_blue);
                    }
                }else if(holder.vv2.getVisibility() == View.VISIBLE){
                    collapse(holder.lo, 500, 0);
                    holder.vv.setVisibility(View.VISIBLE);
                    holder.vv2.setVisibility(View.GONE);
                    if(in == 0){
                        holder.moree.setBackgroundResource(R.drawable.view_more);
                    }else{
                        holder.moree.setBackgroundResource(R.drawable.viewmore_blue);
                    }
                }
//               Log.d("kko",String.valueOf(holder.moree.getBackground()));
//                if (lin1 == 0)
//                {
//                                       lin1 = 1;
//                }
//                else if(lin1 == 1)
//                {
//                    lin1 = 0;
//
//                }
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }



    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle,mid,mDoc,mInt,mDeb;
        private TextView mtot,mpad,mbad,mmad,mmam,mpam,mbam,mcd,mdd,mintr,minst,hlast,mdisc;
        View rowView;
        private View vv,vv2;
        private LinearLayout lo;
        private Button colhis,moree;
        ViewHolder(View itemView) {
            super(itemView);
            lo = itemView.findViewById(R.id.lire);
            mtot = itemView.findViewById(R.id.tdays);
            mpad = itemView.findViewById(R.id.pdays);
            mbad = itemView.findViewById(R.id.bdays);
            mmad = itemView.findViewById(R.id.mdays);
            mmam = itemView.findViewById(R.id.bmamount);
            mintr = itemView.findViewById(R.id.intrr);
            minst = itemView.findViewById(R.id.intst);
            mpam = itemView.findViewById(R.id.paid);
            mbam = itemView.findViewById(R.id.bamount);
            mdd = itemView.findViewById(R.id.odate);
            mcd = itemView.findViewById(R.id.cdate);
            mTitle = itemView.findViewById(R.id.Name);
            mDeb = itemView.findViewById(R.id.debitt);
            mid = itemView.findViewById(R.id.slno);
            mDoc = itemView.findViewById(R.id.docu);
            mdisc = itemView.findViewById(R.id.disc);
//            colhis = itemView.findViewById(R.id.collection_history);
            hlast = itemView.findViewById(R.id.lastcollection);
//            mInt = itemView.findViewById(R.id.interst);
            moree = itemView.findViewById(R.id.more);
            vv = itemView.findViewById(R.id.vi);
            vv2 = itemView.findViewById(R.id.vi1);
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