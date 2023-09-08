package com.rampit.rask3.dailydiary.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.rampit.rask3.dailydiary.Activity_privilege;
import com.rampit.rask3.dailydiary.Add_user;
import com.rampit.rask3.dailydiary.R;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;
import com.rampit.rask3.dailydiary.user_activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class RecyclerViewAdapteruser extends RecyclerView.Adapter<RecyclerViewAdapteruser.ViewHolder> implements Filterable {

    private List<String> mData;
    private List<String> mDatachange;
    private List<String> mId;
    private LayoutInflater mInflater;
    Context context;
    String num,adpr;
    Integer innn;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    static String languu;
    Integer in;
    // data is passed into the constructor
    public RecyclerViewAdapteruser(Context context, Integer in, String language, List<String> data, String adpr) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
        mDatachange = mData;
        this.adpr = adpr;
        this.in = in;
        languu = language;
        Log.d("langu",languu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setResources(context,language);
        }
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
        View view = mInflater.inflate(R.layout.recyclerview_row10, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String animal = mData.get(position);
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
        String name = mData.get(position);
        String[] currencies = name.split(",");
        String s = currencies[0];
        String sS = currencies[1];
        Log.d("curreeer",s);
        if(in == 0){
            holder.edit.setBackgroundResource(R.drawable.edit);
            holder.delet.setBackgroundResource(R.drawable.delete);
            holder.priv.setBackgroundResource(R.drawable.login_selector);
        }else{
            holder.edit.setBackgroundResource(R.drawable.edit_blue);
            holder.delet.setBackgroundResource(R.drawable.delete_blue);
            holder.priv.setBackgroundResource(R.drawable.bordered_button);
        }
        holder.mid.setText(s);
        holder.mTitlte.setText(sS);
        if(adpr.equalsIgnoreCase("0")){
            holder.priv.setVisibility(View.INVISIBLE);
        }
        holder.priv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String number  = animal.replaceAll("[^0-9]", "");
//
                String name1 = mData.get(position);
                String[] currencies = name1.split(",");
                String s = currencies[0];
                Log.d("newdebit",s);
                Intent name= new Intent(context, Activity_privilege.class);
                name.putExtra("ID",s);
                name.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(name);
            }
        });
        holder.delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pos = String.valueOf(mData.get(position));
                String name = mData.get(position);
                String[] currencies = name.split(",");
                String s = currencies[0];
//                num  = pos.replaceAll("[^0-9]", "");
                num = s;
                Log.d("delete",num);
                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext(),R.style.AlertDialogTheme);
                String enn = v.getContext().getResources().getString(R.string.delete_value);
                String war = v.getContext().getResources().getString(R.string.warning);
                String ook = v.getContext().getResources().getString(R.string.cancel);
                String del = v.getContext().getResources().getString(R.string.delete);
                alertbox.setMessage(enn);
                alertbox.setTitle(war);
                alertbox.setIcon(R.drawable.dailylogo);
                alertbox.setNeutralButton(ook,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                            }
                        });
                alertbox.setPositiveButton(del,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                sqlite = new SQLiteHelper(context);
                                database = sqlite.getWritableDatabase();
                                String table = "dd_user";
                                String whereClause = "id=?";
                                String[] whereArgs = new String[] {num};
                                database.delete(table, whereClause, whereArgs);
                                sqlite.close();
                                database.close();
                                Intent name= new Intent(context, user_activity.class);
                                name.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(name);
                            }
                        });
                alertbox.show();
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pos = String.valueOf(mData.get(position));
                String number  = pos.replaceAll("[^0-9]", "");
                for(int i = 0; i < number.length(); i++) {
                    int j = Character.digit(number.charAt(i), 10);
                    num = String.valueOf(j);
//                    Log.d("editpos", String.valueOf(j));
                    break;
                }
//                String num = String.valueOf();
                Log.d("editpos",num);
                Intent name= new Intent(context, Add_user.class);
                name.putExtra("ID",num);
                name.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(name);
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
        TextView mTitlte,mid;
        Button priv;
        private Button edit,nip,delet;

        ViewHolder(View itemView) {
            super(itemView);
            mTitlte = itemView.findViewById(R.id.Name);
            mid = itemView.findViewById(R.id.slno);
            priv = itemView.findViewById(R.id.privilege);
            edit = itemView.findViewById(R.id.edit);
            delet = itemView.findViewById(R.id.delete);
//            sqlite = new SQLiteHelper(context);
//            database = sqlite.getWritableDatabase();
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