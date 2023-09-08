package com.rampit.rask3.dailydiary.Adapter;

import android.animation.ValueAnimator;
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
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rampit.rask3.dailydiary.Account;
import com.rampit.rask3.dailydiary.Addaccount;
import com.rampit.rask3.dailydiary.R;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.util.ArrayList;
import java.util.Locale;



public class RecyclerViewAdapteraccounting extends RecyclerView.Adapter<RecyclerViewAdapteraccounting.MyViewHolder> implements Filterable {

    private ArrayList<String> data;
    private ArrayList<String> datafull;

    SQLiteHelper sqlite;
    SQLiteDatabase database;
    Context context;
    String selected,cleared;
    int sele,clea;
    String num,edi,del;
    Integer in ;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle,mid,mdate,mtype,mtyna,mnote,marrow;
        private Button edit,nip,delet;
        View rowView;
        LinearLayout vv1,vv2;
        private View vv,vv3;
        public MyViewHolder(View itemView) {
            super(itemView);
            rowView = itemView;
            mTitle = itemView.findViewById(R.id.dateac);
            mid = itemView.findViewById(R.id.slno);
            mtype = itemView.findViewById(R.id.actype);
            mtyna = itemView.findViewById(R.id.acname);
            mnote = itemView.findViewById(R.id.acnote);
            edit = itemView.findViewById(R.id.edit);
            delet = itemView.findViewById(R.id.delete);
            marrow = itemView.findViewById(R.id.more);
            vv1 = itemView.findViewById(R.id.vvv1);
            vv2 = itemView.findViewById(R.id.vvv2);
            vv = itemView.findViewById(R.id.vi);
            vv3 = itemView.findViewById(R.id.vi1);
//            sqlite = new SQLiteHelper(context);
//            database = sqlite.getWritableDatabase();
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
//                    Intent nan= new Intent(context, Debit.class);
//                    nan.putExtra("ID",num);
//                    v.getContext().startActivity(nan);
//
//                }
//            });

        }
    }
    public RecyclerViewAdapteraccounting(ArrayList<String> data, Integer in, Context context, String edpr, String depr) {
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
//                if (item.toLowerCase(Locale.getDefault()).contains(charText)) {
                    if (item.toLowerCase(Locale.getDefault()).contains(charText)) {
                        data.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row5, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if(in == 0){
            holder.edit.setBackgroundResource(R.drawable.edit);
            holder.delet.setBackgroundResource(R.drawable.delete);
            holder.marrow.setBackgroundResource(R.drawable.view_more);
        }else{
            holder.edit.setBackgroundResource(R.drawable.edit_blue);
            holder.delet.setBackgroundResource(R.drawable.delete_blue);
            holder.marrow.setBackgroundResource(R.drawable.viewmore_blue);
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
        String name = data.get(position);
        String[] currencies = name.split(",");
        String s = currencies[0];
        String sS = currencies[1];
        String sS1 = currencies[2];
        String sSs = currencies[3];
        String sSs1 = currencies[4];
        String sSs11 = currencies[5];
        String sSs111 = currencies[6];
        Log.d("curreeer",s);
        holder.mid.setText(sSs1);
        holder.mTitle.setText(sS);
        holder.mtype.setText(sSs);
        holder.mtyna.setText(sS1);
        holder.mnote.setText(sSs111);
        if(sSs11.equalsIgnoreCase("1")){
            holder.mid.setTextColor((ContextCompat.getColor(context, R.color.Green)));
            holder.mTitle.setTextColor((ContextCompat.getColor(context, R.color.Green)));
            holder.mtype.setTextColor((ContextCompat.getColor(context, R.color.Green)));
            holder.mtyna.setTextColor((ContextCompat.getColor(context, R.color.Green)));
            holder.mnote.setTextColor((ContextCompat.getColor(context, R.color.Green)));

        } else if (sSs11.equalsIgnoreCase("2")){
            if(sSs11.equalsIgnoreCase("2")){
                holder.mid.setTextColor((ContextCompat.getColor(context, R.color.accountred)));
                holder.mTitle.setTextColor((ContextCompat.getColor(context, R.color.accountred)));
                holder.mtype.setTextColor((ContextCompat.getColor(context, R.color.accountred)));
                holder.mtyna.setTextColor((ContextCompat.getColor(context, R.color.accountred)));
                holder.mnote.setTextColor((ContextCompat.getColor(context, R.color.accountred)));
            }
        }
        holder.marrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.vv.getVisibility() == View.VISIBLE){
                    expand(holder.vv2, 100, LinearLayout.LayoutParams.WRAP_CONTENT);
                    holder.vv.setVisibility(View.GONE);
                    holder.vv3.setVisibility(View.VISIBLE);
                    if(in == 0){
                        holder.marrow.setBackgroundResource(R.drawable.view_up);
                    }else{
                        holder.marrow.setBackgroundResource(R.drawable.viewup_blue);
                    }
                }else if(holder.vv3.getVisibility() == View.VISIBLE){
                    collapse(holder.vv2, 100, 0);
                    holder.vv.setVisibility(View.VISIBLE);
                    holder.vv3.setVisibility(View.GONE);
                    if(in == 0){
                        holder.marrow.setBackgroundResource(R.drawable.view_more);
                    }else{
                        holder.marrow.setBackgroundResource(R.drawable.viewmore_blue);
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
//    }
//        holder.mTitle.setText(data.get(position));
        if(edi.equalsIgnoreCase("0")){
            holder.edit.setVisibility(View.GONE);
        }
        if(del.equalsIgnoreCase("0")){
            holder.delet.setVisibility(View.GONE);
        }
        holder.delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pos = data.get(position);
                String[] currencies = pos.split(",");
                num = currencies[0];
//                for(int i = 0; i < number.length(); i++) {
//                        int j = Character.digit(number.charAt(i), 10);
//                        num = String.valueOf(j);
//                    Log.d("delete",num);
////                    Log.d("editpos", String.valueOf(j));
//                        break;
//                    }
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
                                String table = "dd_accounting";
                                String whereClause = "id=?";
                                String[] whereArgs = new String[] {num};
                                database.delete(table, whereClause, whereArgs);
                                sqlite.close();
                                database.close();
                                Intent name= new Intent(context, Account.class);
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

                String pos = data.get(position);
                String[] currencies = pos.split(",");
                String s = currencies[0];
//                String number  = pos.replaceAll("[^0-9]", "");
//                for(int i = 0; i < number.length(); i++) {
//                    int j = Character.digit(number.charAt(i), 10);
//                    num = String.valueOf(j);
////                    Log.d("editpos", String.valueOf(j));
//                    break;
//                }
//                String num = String.valueOf();
                Log.d("editpos",s);
                Intent name= new Intent(context, Addaccount.class);
                name.putExtra("ID",s);
                name.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(name);
            }
        });
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
    @Override
    public int getItemCount() {
        return data.size();
    }





}


