package com.rampit.rask3.dailydiary.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.rampit.rask3.dailydiary.Employee_payment;
import com.rampit.rask3.dailydiary.R;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.util.ArrayList;
import java.util.Locale;

;

public class RecyclerViewAdapteremployeepay extends RecyclerView.Adapter<RecyclerViewAdapteremployeepay.MyViewHolder> implements Filterable {

    private ArrayList<String> data;
    private ArrayList<String> datafull;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    Context context;
    String selected,cleared,edi,del;
    int sele,clea;
    String num,ses1;
    Integer in;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle,mid,mtype,mamount;
        private Button edit,nip,delet;
        View rowView;

        public MyViewHolder(View itemView) {
            super(itemView);


            rowView = itemView;
            mTitle = itemView.findViewById(R.id.Name);
            mid = itemView.findViewById(R.id.slno);
            mtype = itemView.findViewById(R.id.type);
            mamount = itemView.findViewById(R.id.amount);
            edit = itemView.findViewById(R.id.edit);
            delet = itemView.findViewById(R.id.delete);
//            sqlite = new SQLiteHelper(context);
//            database = sqlite.getWritableDatabase();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    String pos = String.valueOf(data.get(getAdapterPosition()));
//                    String number  = pos.replaceAll("[^0-9]", "");
//                    Log.d("newdebit",number);
//                    for(int i = 0; i < number.length(); i++) {
//                        int j = Character.digit(number.charAt(i), 10);
//                        num = String.valueOf(j);
////                    Log.d("editpos", String.valueOf(j));
//                        break;
//                    }
//                    Intent nan= new Intent(context, Debit.class);
//                    nan.putExtra("ididi",num);
//                    v.getContext().startActivity(nan);

                }
            });

        }
    }

    public RecyclerViewAdapteremployeepay(ArrayList<String> data, Integer in, Context context, String edpr, String depr) {
        this.data = data;
        datafull = data;
        this.context = context;
        edi = edpr;
        del = depr;
        this.in = in;
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

                // we iterate through all the items on the list and if any item contains the search text, we add it to a new list mDataset
                if (item.toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(item);
                }
            }
        }
// method notifyDataSetChanged () allows you to update the list on the screen after filtration
        notifyDataSetChanged();
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row9, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mTitle.setText(data.get(position));
        String name = data.get(position);
        String[] currencies = name.split(",");
        String s = currencies[0];
        String sS = currencies[1];
        String sSs = currencies[2];
        String sSss = currencies[3];
        String sSsss = currencies[4];

        Log.d("curreeer",s);
        holder.mid.setText(sSsss);
        holder.mTitle.setText(sS);
        holder.mtype.setText(sSs);
        holder.mamount.setText(sSss);
        if(in == 0){
            holder.delet.setBackgroundResource(R.drawable.delete);
        }else{
            holder.delet.setBackgroundResource(R.drawable.delete_blue);
        }
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
        if(edi.equalsIgnoreCase("0")){
            holder.edit.setVisibility(View.GONE);
        }
        if(del.equalsIgnoreCase("0")){
            holder.delet.setVisibility(View.GONE);
        }
        holder.delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pos = String.valueOf(data.get(position));
                String name = data.get(position);
                String[] currencies = name.split(",");
                String s = currencies[0];
                ses1 = currencies[5];

                num  = s;
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
                                String table = "dd_employee_payment";
                                String whereClause = "id=?";
                                String[] whereArgs = new String[] {num};
                                database.delete(table, whereClause, whereArgs);
                                sqlite.close();
                                database.close();
                                Intent name= new Intent(context, Employee_payment.class);
                                name.putExtra("ID",ses1);
                                context.startActivity(name);
                            }
                        });
                alertbox.show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }





}


