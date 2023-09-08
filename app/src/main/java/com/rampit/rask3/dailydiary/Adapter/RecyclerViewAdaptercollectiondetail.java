package com.rampit.rask3.dailydiary.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.rampit.rask3.dailydiary.Collectiondetail_activity;
import com.rampit.rask3.dailydiary.R;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.util.ArrayList;
import java.util.Locale;


public class RecyclerViewAdaptercollectiondetail extends RecyclerView.Adapter<RecyclerViewAdaptercollectiondetail.MyViewHolder> implements Filterable {

    private final Collectiondetail_activity Collectiondetail_activity;
    private ArrayList<String> data;
    private ArrayList<String> datafull;
    private ArrayList<String> Col;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    Context context;
    String Name;
    int sele,clea;
    Integer debi,pai,bal;
    String num,dd,debitam,paidam,balam,collo,edi,del;
    ArrayList<Long> Id;
    ArrayList<Long> IId;
    Integer in;
    public static String TABLENAME = "dd_collection";

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle,mid,mam;
        private Button edit,nip,delet;
        View rowView;

        public MyViewHolder(View itemView) {
            super(itemView);


            rowView = itemView;
            mid = itemView.findViewById(R.id.slno);
            mTitle = itemView.findViewById(R.id.Name);
            mam = itemView.findViewById(R.id.debi);
            edit = itemView.findViewById(R.id.edit);
            delet = itemView.findViewById(R.id.delete);
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

    public RecyclerViewAdaptercollectiondetail(String edpr, String depr, ArrayList<String> data, ArrayList<Long> ID, ArrayList<Long> IID, ArrayList<String> col, Integer in, Context context, Collectiondetail_activity collectiondetail_activity) {
        this.data = data;
        datafull = data;
        this.context = context;
        Id = ID;
        IId = IID;
        Col = col;
        this.Collectiondetail_activity = collectiondetail_activity;
    edi = edpr;del = depr;
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_rowcolcus, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
//        holder.mTitle.setText(data.get(position));
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
        String sSs = currencies[2];
        Log.d("curreeer",s);
        holder.mid.setText(s);
        holder.mTitle.setText(sS);
        holder.mam.setText(sSs);
        if(edi.equalsIgnoreCase("0")){
holder.edit.setVisibility(View.GONE);
        }
        if(del.equalsIgnoreCase("0")){
            holder.delet.setVisibility(View.GONE);
        }
        if(in == 0){
            holder.edit.setBackgroundResource(R.drawable.edit);
            holder.delet.setBackgroundResource(R.drawable.delete);
        }else{
            holder.edit.setBackgroundResource(R.drawable.edit_blue);
            holder.delet.setBackgroundResource(R.drawable.delete_blue);
        }
        holder.delet.setVisibility(View.GONE);
        holder.edit.setVisibility(View.GONE);
        holder.delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pos = String.valueOf(IId.get(position));
                final String cuc = String.valueOf(Id.get(position));
                num  = pos.replaceAll("[^0-9]", "");
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
                                String table = "dd_collection";
                                String whereClause = "id=?";
                                String[] whereArgs = new String[] {num};
                                database.delete(table, whereClause, whereArgs);
                                sqlite.close();
                                database.close();
                                Intent name= new Intent(context, Collectiondetail_activity.class);
                                name.putExtra("ID",cuc);
                                context.startActivity(name);
                            }
                        });
                alertbox.show();
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlite = new SQLiteHelper(context);
                database = sqlite.getWritableDatabase();
                num = String.valueOf(Id.get(position));
                dd = String.valueOf(IId.get(position));
                collo = String.valueOf(Col.get(position));
                String SUM_QUERY = "SELECT a.*,sum(a.collection_amount) as paid,b.debit_amount FROM dd_collection a INNER JOIN dd_account_debit b on b.customer_id = a.customer_id WHERE a.customer_id = ?";
                Cursor cur = database.rawQuery(SUM_QUERY,new String[]{num});
                if (cur != null) {
                    if (cur.getCount() != 0) {
                        cur.moveToFirst();
                        do {
                            int index;
                            index = cur.getColumnIndexOrThrow("paid");
                             pai = cur.getInt(index);
                            index = cur.getColumnIndexOrThrow("debit_amount");
                            debi = cur.getInt(index);
                            index = cur.getColumnIndexOrThrow("customer_name");
                            Name = cur.getString(index);


                            bal = debi - pai;

                            debitam = String.valueOf(debi);
                            paidam = String.valueOf(pai);
                            balam = String.valueOf(bal);
                            popup(paidam,balam,debitam,Name,collo);
                        }
                        while (cur.moveToNext());
                        sqlite.close();
                        database.close();
                    }else{
                        sqlite.close();
                        database.close();
                    }
                }else{
                    sqlite.close();
                    database.close();
                }
            }
        });
    }

    //popup() - Popup to show days
    //Params - paid days ,balance days , debit , customer name ,collection
    //Created on 25/01/2022
    //Return - NULL
    public void popup(final String pai, final String bal, final String debi, String Name, final String coll){

        final Dialog dialog = new Dialog(Collectiondetail_activity);
        //set layout custom
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popdetail);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);
//        final RecyclerView rvcaddy = (RecyclerView) dialog.findViewById(R.id.rvAnimals);
        final TextView camount = (TextView) dialog.findViewById(R.id.paidamount);
        final TextView bamount = (TextView) dialog.findViewById(R.id.balanceamount);
        final TextView nm = (TextView) dialog.findViewById(R.id.name);
        final TextView damount = (TextView) dialog.findViewById(R.id.debitamount);
        final TextView collection = (EditText) dialog.findViewById(R.id.collection);
        final TextView save = (Button) dialog.findViewById(R.id.save);
        Button close = (Button) dialog.findViewById(R.id.close);
        if(in == 0){
            close.setBackgroundResource(R.drawable.close_button);
        }else{
            close.setBackgroundResource(R.drawable.close_blue);
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        camount.setText(pai);
        bamount.setText(bal);
        damount.setText(debi);
        nm.setText(Name);
        collection.setText(coll);
        collection.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String col = collection.getText().toString();
//                    String paidddd = camount.getText().toString();
//                    String baaaaaa = bamount.getText().toString();
                    Integer bala = Integer.parseInt(bal);
                    Integer paia = Integer.parseInt(pai);
                    Integer dddd = Integer.parseInt(debi);
                    Integer cola = Integer.parseInt(col);
                    Integer balaa = bala - cola;
                    Integer poi = paia + cola;

//                    String bal = String.valueOf(balaa);
//                    camount.setText("Paid amount :"+" "+String.valueOf());
                    bamount.setText(String.valueOf(balaa));
                    camount.setText(String.valueOf(poi));

                }
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlite = new SQLiteHelper(context);
                database = sqlite.getWritableDatabase();
                Log.d("colllc",dd+" "+num);
                String username = collection.getText().toString();
                ContentValues cv = new ContentValues();
                cv.put("collection_amount",username);
                database.update(TABLENAME, cv, "id= ?", new String[]{dd});
                sqlite.close();
                database.close();
                Intent name= new Intent(context, Collectiondetail_activity.class);
                name.putExtra("ID",num);
                context.startActivity(name);
            }
        });


        dialog.show();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }





}


