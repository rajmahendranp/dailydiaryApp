package com.rampit.rask3.dailydiary.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.rampit.rask3.dailydiary.Activity_privilege;
import com.rampit.rask3.dailydiary.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecyclerViewAdapterprivilege extends RecyclerView.Adapter<RecyclerViewAdapterprivilege.ViewHolder> implements Filterable {

    private List<String> mData;
    private List<String> mDatachange;
    private List<String> mId;
    ArrayList<String> privil = new ArrayList<>();
    private LayoutInflater mInflater;
    Context context;
    Integer innn,in;
    String nnaa;
    static String languu;


    // data is passed into the constructor
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public RecyclerViewAdapterprivilege(Context context, Integer in, ArrayList<String> names, ArrayList<String> iid, String language) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = names;
        this.mId = iid;
        mDatachange = mData;
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
        View view = mInflater.inflate(R.layout.recyclerview_row11, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
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
            holder.ad.setButtonDrawable(R.drawable.custom_checkbox);
            holder.ed.setButtonDrawable(R.drawable.custom_checkbox);
            holder.de.setButtonDrawable(R.drawable.custom_checkbox);
            holder.vi.setButtonDrawable(R.drawable.custom_checkbox);
        }else{
            holder.ad.setButtonDrawable(R.drawable.custom_checkbox1);
            holder.ed.setButtonDrawable(R.drawable.custom_checkbox1);
            holder.de.setButtonDrawable(R.drawable.custom_checkbox1);
            holder.vi.setButtonDrawable(R.drawable.custom_checkbox1);
        }
        String nnqaa = holder.itemView.getRootView().getContext().getResources().getString(R.string.user1);
        Log.d("tyty12",String.valueOf(position));
        String animal = mData.get(position);
        String[] currencies = animal.split(",");
        String nam = currencies[0];
//        String nam  = animal.replaceAll("[^a-zA-Z]", "");
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        String ii = pref.getString("quickbulk","0");
        Log.d("priv_position",String.valueOf(position));
        if(ii.equalsIgnoreCase("0") && position == 22){
            holder.itemView.setVisibility(View.GONE);
        }else{
            holder.itemView.setVisibility(View.VISIBLE);
        }
        holder.myTextView.setText(nam);
        if(position == 23){
            holder.ad.setVisibility(View.INVISIBLE);
            holder.ed.setVisibility(View.INVISIBLE);
            holder.de.setVisibility(View.INVISIBLE);
            holder.itemView.setVisibility(View.GONE);
        }
        if(nam.equalsIgnoreCase("company")){
            String nnaa = context.getString(R.string.company1);
            Log.d("tyty",nnaa);
            holder.myTextView.setText(nnaa);
        }
        if(nam.equalsIgnoreCase("user")){
            String nnaa = holder.itemView.getContext().getString(R.string.user);
            Log.d("tyty",nnaa);
            holder.myTextView.setText(nnaa);
        }
        if(nam.equalsIgnoreCase("debit")){
            String nnaa = holder.itemView.getContext().getString(R.string.debit1);
            Log.d("tyty",nnaa);
            holder.myTextView.setText(nnaa);
        }
        if(nam.equalsIgnoreCase("collection")){
            String nnaa = holder.itemView.getContext().getString(R.string.collection1);
            Log.d("tyty",nnaa);
            holder.myTextView.setText(nnaa);
        }
        if(nam.equalsIgnoreCase("account")){
            String nnaa = holder.itemView.getContext().getString(R.string.account1);
            Log.d("tyty",nnaa);
            holder.myTextView.setText(nnaa);
        }
        if(nam.equalsIgnoreCase("reports")){
            String nnaa = holder.itemView.getContext().getString(R.string.reports1);
            Log.d("tyty",nnaa);
            holder.myTextView.setText(nnaa);
        }
        if(nam.equalsIgnoreCase("customer")){
            String nnaa = holder.itemView.getContext().getString(R.string.customer1);
            Log.d("tyty",nnaa);
            holder.myTextView.setText(nnaa);
        }
        if(nam.equalsIgnoreCase("settings")){
            String nnaa = holder.itemView.getContext().getString(R.string.settings1);
            Log.d("tyty",nnaa);
            holder.myTextView.setText(nnaa);
        }
        if(nam.equalsIgnoreCase("tally")){
            String nnaa = holder.itemView.getContext().getString(R.string.tally1);
            Log.d("tyty",nnaa);
            holder.myTextView.setText(nnaa);
        }
        if(nam.equalsIgnoreCase("employee_details")){
            String nnaa = holder.itemView.getContext().getString(R.string.employee_details1);
            Log.d("tyty",nnaa);
            holder.myTextView.setText(nnaa);
        }
        if(nam.equalsIgnoreCase("today_report")){
            String nnaa = holder.itemView.getContext().getString(R.string.today_report1);
            Log.d("tyty",nnaa);
            holder.myTextView.setText(nnaa);
        }
        if(nam.equalsIgnoreCase("current_account")){
            String nnaa = holder.itemView.getContext().getString(R.string.current_account);
            Log.d("tyty",nnaa);
            holder.myTextView.setText(nnaa);
        }
        if(nam.equalsIgnoreCase("closed_account")){
            String nnaa = holder.itemView.getContext().getString(R.string.closed_account);
            Log.d("tyty",nnaa);
            holder.myTextView.setText(nnaa);
        }
        if(nam.equalsIgnoreCase("today_account")){
            String nnaa = holder.itemView.getContext().getString(R.string.today_account);
            Log.d("tyty",nnaa);
            holder.myTextView.setText(nnaa);
        }
        if(nam.equalsIgnoreCase("current_incompleted_account")){
            String nnaa = holder.itemView.getContext().getString(R.string.current_incompleted_account);
            Log.d("tyty",nnaa);
            holder.myTextView.setText(nnaa);
        }
        if(nam.equalsIgnoreCase("NIP_account")){
            String nnaa = holder.itemView.getContext().getString(R.string.nip_account);
            Log.d("tyty",nnaa);
            holder.myTextView.setText(nnaa);
        }
        if(nam.equalsIgnoreCase("NIPNIP_account")){
            String nnaa = holder.itemView.getContext().getString(R.string.nip_nip_account);
            Log.d("tyty",nnaa);
            holder.myTextView.setText(nnaa);
        }
        if(nam.equalsIgnoreCase("total_customer")){
            String nnaa = holder.itemView.getContext().getString(R.string.total_customer);
            Log.d("tyty",nnaa);
            holder.myTextView.setText(nnaa);
        }
        if(nam.equalsIgnoreCase("final_report")){
            String nnaa = holder.itemView.getContext().getString(R.string.final_report);
            Log.d("tyty",nnaa);
            holder.myTextView.setText(nnaa);
        }
        if(nam.equalsIgnoreCase("print")){
            String nnaa = holder.itemView.getContext().getString(R.string.print);
            Log.d("tyty",nnaa);
            holder.myTextView.setText(nnaa);
        }
//        if(nam.equalsIgnoreCase("Print")){
//            String nnaa = holder.itemView.getContext().getString(R.string.print);
//            Log.d("tyty",nnaa);
//            holder.myTextView.setText(nnaa);
//        }


        String animal1 = mData.get(position);
        String[] currencies1 = animal1.split(",");
        String num = currencies1[1];
        String num1 = currencies1[2];
        String num2 = currencies1[3];
        String num3 = currencies1[4];
        Log.d("vvii",num3);
//        for(int i = 0; i < number.length(); i++) {
//            int j = Character.digit(number.charAt(i), 10);
//            String num = String.valueOf(j);
        if(num.equalsIgnoreCase("1")){
            holder.ad.setChecked(true);
        }
        if(num1.equalsIgnoreCase("2")){
            holder.ed.setChecked(true);
        }
        if(num2.equalsIgnoreCase("3")){
            holder.de.setChecked(true);
        }
        if(num3.equalsIgnoreCase("4")){
            holder.vi.setChecked(true);
        }
//        }
        holder.ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                String ii = mId.get(position);
                if(checked){
                    String check = "1";
                    privil.add(ii+","+check);
                }else{
                    String check = "0";
                    privil.add(ii+","+check);
                }
            }
        });
        holder.ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                String ii = mId.get(position);
                if(checked){
                    String check = "2";
                    privil.add(ii+","+check);
                }else{
                    String check = "00";
                    privil.add(ii+","+check);
                }
            }
        });
        holder.de.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                String ii = mId.get(position);
                if(checked){
                    String check = "3";
                    privil.add(ii+","+check);
                }else{
                    String check = "000";
                    privil.add(ii+","+check);
                }
            }
        });
        holder.vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                String ii = mId.get(position);
                if(checked){
                    String check = "4";
                    privil.add(ii+","+check);
                }else{
                    String check = "0000";
                    privil.add(ii+","+check);
                }
            }
        });

    }
    public ArrayList<String> getArrayList(){
        return privil;
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

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;
        CheckBox ad,de,ed,vi;

        ViewHolder(View itemView) {
            super(itemView);
            ad = itemView.findViewById(R.id.add);
            ed = itemView.findViewById(R.id.edit);
            de = itemView.findViewById(R.id.delete);
            vi = itemView.findViewById(R.id.view);
            myTextView = itemView.findViewById(R.id.Name);
        }

    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
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