package com.rampit.rask3.dailydiary.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.rampit.rask3.dailydiary.Collectiondetail_activity;
import com.rampit.rask3.dailydiary.Customer_activity;
import com.rampit.rask3.dailydiary.R;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RecyclerViewAdaptercustomer extends RecyclerView.Adapter<RecyclerViewAdaptercustomer.MyViewHolder> implements Filterable {
    private Activity Customer_activity;
    private ArrayList<String> data;
    private ArrayList<String> datafull;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    Context context;
    String selected,cleared;
    int sele,clea;
    String num,date1,date2;
    Integer in;



    public RecyclerViewAdaptercustomer(ArrayList<String> data, Integer in, Context context, Customer_activity customer_activity) {
        this.data = data;
        datafull = data;
        this.context = context;
        this.Customer_activity = customer_activity;
        this.in = in;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle,mid,mdebti;
        private Button edit,nip,delet;
        View rowView;

        public MyViewHolder(View itemView) {
            super(itemView);


            rowView = itemView;
            mTitle = itemView.findViewById(R.id.Name);
            mid = itemView.findViewById(R.id.slno);
            mdebti = itemView.findViewById(R.id.debi);
            edit = itemView.findViewById(R.id.debit);
            delet = itemView.findViewById(R.id.collection);
//            sqlite = new SQLiteHelper(context);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String pos = String.valueOf(data.get(getAdapterPosition()));
//                    String number  = pos.replaceAll("[^0-9]", "");
//                    Log.d("newdebit",number);
//                    for(int i = 0; i < number.length(); i++) {
//                        int j = Character.digit(number.charAt(i), 10);
//                        num = String.valueOf(j);
////                    Log.d("editpos", String.valueOf(j));
//                        break;
//                    }
//                    Intent name= new Intent(context, Customer_activity.class);
//                    name.putExtra("ID",num);
//                    v.getContext().startActivity(name);
//                }
//            });
        }
    }
    @Override
    public Filter getFilter() {
        return null;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        data = new ArrayList<String>();
        if (charText.length() == 0) {
            // mCleanCopyDataset we always contains the unaltered and the filter (full) copy of the list of data
            data.addAll(datafull);
        } else {
            for (String item : datafull) {
                String[] currencie = item.split(",");
                String s = currencie[5];
                // we iterate through all the items on the list and if any item contains the search text, we add it to a new list mDataset
                if (s.contains(charText)) {
                    Log.d("fileterd1",item);
                    Log.d("fileterd2",s);
//                    Integer nh = Integer.parseInt(d);
//                    if(nh.equals(Integer.parseInt(charText))){
//                        Log.d("fileterd3",d);
//                    }
                    data.add(item);
                }
            }
        }
// method notifyDataSetChanged () allows you to update the list on the screen after filtration
        notifyDataSetChanged();
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row7, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        String name = data.get(position);
        String[] currencies = name.split(",");
        String s = currencies[0];
        String sS = currencies[1];
        String sSs = currencies[2];
        String sSs1 = currencies[5];
        Log.d("curreeer",s);
        holder.mid.setText(sSs1);
        holder.mTitle.setText(sS);
        holder.mdebti.setText(sSs);
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
            holder.delet.setBackgroundResource(R.drawable.debit);
            holder.edit.setBackgroundResource(R.drawable.collection);
        }else{
            holder.delet.setBackgroundResource(R.drawable.debit_blue);
            holder.edit.setBackgroundResource(R.drawable.collection_blue);
        }
        holder.delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String pos = String.valueOf(data.get(position));
//                String number  = pos.replaceAll("[^0-9]", "");
//                for(int i = 0; i < number.length(); i++) {
//                    int j = Character.digit(number.charAt(i), 10);
//                    num = String.valueOf(j);
//                    break;
//                }
                String name1= data.get(position);
                String[] currencies = name1.split(",");
                num = currencies[3];
                String num1 = currencies[4];
                Log.d("editpos",num);
                Intent name= new Intent(context, Collectiondetail_activity.class);
                name.putExtra("ID",num);
                name.putExtra("DID",num1);
                v.getContext().startActivity(name);
            }
        });
        //        holder.delet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String pos = String.valueOf(data.get(position));
//                num  = pos.replaceAll("[^0-9]", "");
//                Log.d("delete",num);
//                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
//                alertbox.setMessage("Are You Sure want to delete this value");
//                alertbox.setTitle("Warning");
//                alertbox.setIcon(R.drawable.logo);
//                alertbox.setNeutralButton("CANCEL",
//                        new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface arg0,
//                                                int arg1) {
//
//
//                            }
//                        });
//                alertbox.setPositiveButton("DELETE",
//                        new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface arg0,
//                                                int arg1) {
//                                String table = "dd_account_debit";
//                                String whereClause = "id=?";
//                                String[] whereArgs = new String[] {num};
//                                database.delete(table, whereClause, whereArgs);
//                                Intent name= new Intent(context, Debit.class);
//                                context.startActivity(name);
//                            }
//                        });
//                alertbox.show();
//            }
//        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String pos = String.valueOf(data.get(position));
//                String number  = pos.replaceAll("[^0-9]", "");
//                for(int i = 0; i < number.length(); i++) {
//                    int j = Character.digit(number.charAt(i), 10);
//                    num = String.valueOf(j);
////                    Log.d("editpos", String.valueOf(j));
//                    break;
//                }
//                String num = String.valueOf();
                String name1= data.get(position);
                String[] currencies = name1.split(",");
                num = currencies[3];
                String num1 = currencies[4];
                Log.d("editpos",num);
                Intent name= new Intent(context, Customer_activity.class);
                name.putExtra("ID",num);
                name.putExtra("DID",num1);
                v.getContext().startActivity(name);
            }
        });
    }
    //popup() -Show user details in popup
    //Params - pai d maount , missed amount , paid days , closing date , debit date , total days , info , name , slno , debit amount , theme and today collection
    //Created on 25/01/2022
    //Return - NULL
    public void popup(String paidamount, String balanc, String missed, String paidays, String closing, String debitdaaa, String totaldays, String infoo, String names12, String slno, String debit, Integer in, Integer toda){

  final Dialog dialog = new Dialog(Customer_activity);
        //set layout custom
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popcustomer);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);
//        final RecyclerView rvcaddy = (RecyclerView) dialog.findViewById(R.id.rvAnimals);
        final TextView ddate = (TextView) dialog.findViewById(R.id.debitdate);
        final TextView camount = (TextView) dialog.findViewById(R.id.creditedamount);
        final TextView slnno = (TextView) dialog.findViewById(R.id.slno);
        final TextView naaam = (TextView) dialog.findViewById(R.id.name);
        final TextView dbam = (TextView) dialog.findViewById(R.id.debitamount);
        final TextView bamount = (TextView) dialog.findViewById(R.id.balanceamount);
        final TextView cdate = (TextView) dialog.findViewById(R.id.closingdate);
        final TextView inf = (TextView) dialog.findViewById(R.id.info);
        final TextView tdays = (TextView) dialog.findViewById(R.id.totaldays);
        final TextView cdays = (TextView) dialog.findViewById(R.id.completeddays);
        final TextView mdays = (TextView) dialog.findViewById(R.id.missingdays);
        final TextView days1 = (TextView) dialog.findViewById(R.id.days);
        final Button close = (Button)dialog.findViewById(R.id.close);
        if(in == 0){
            close.setBackgroundResource(R.drawable.close);
        }else{
            close.setBackgroundResource(R.drawable.close_blue1);
        }
        String formattedDate = null;
        String formattedDate2 = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date debit2 = df.parse(debitdaaa);
            formattedDate = df1.format(debit2);
            Date debit22 = df.parse(closing);
            formattedDate2 = df1.format(debit22);
            Log.d("deae",formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        slnno.setText(slno);
        naaam.setText(names12);
        ddate.setText(formattedDate);
        dbam.setText("\u20B9"+" "+debit);
        camount.setText("\u20B9"+" "+paidamount);
        bamount.setText("\u20B9"+" "+balanc);
        cdate.setText(formattedDate2);
        mdays.setText(missed);
        tdays.setText(totaldays);
        cdays.setText(paidays);
        inf.setText(infoo);
        days1.setText(String.valueOf(toda));

        dialog.show();
    }
    @Override
    public int getItemCount() {
        return data.size();
    }





}


