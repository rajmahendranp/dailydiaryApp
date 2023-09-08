package com.rampit.rask3.dailydiary.Adapter;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rampit.rask3.dailydiary.Addnames;
import com.rampit.rask3.dailydiary.HomeActivity;
import com.rampit.rask3.dailydiary.ItemMoveCallback1;
import com.rampit.rask3.dailydiary.R;
import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Filterable,ItemMoveCallback1.ItemTouchHelperContract {

    private ArrayList<String> data;
    private ArrayList<String> datafull;
    private ArrayList<String> ni;
    private ArrayList<String> iidd;
    private ArrayList<String> moreve;
    private ArrayList<String> OID;
    List<ArrayList> Namess = new ArrayList<>();
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    Context context;
    String selected,prevorder,cid,cleared,prevCID;
    int sele,clea;
    Integer ordddd,sess,CIDorder,in;
    String num,edi,del,acttt,ooo;

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(data, i, i + 1);
                Log.i("hjbhk",fromPosition+" "+toPosition);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(data, i, i - 1);
                Log.i("hjbhk",fromPosition+" "+toPosition);
            }
        }
        notifyItemMoved(fromPosition, toPosition);

    }
    @Override
    public void onRowSelected(MyViewHolder myViewHolder, int position) {
        myViewHolder.rowView.setBackgroundColor(Color.GRAY);
        selected = String.valueOf(position);
        sele = position;
        String animal = data.get(position);
        String[] currencies = animal.split(",");
        String s = currencies[2];
        Integer ss = Integer.parseInt(s);
        if(ss >= 5001){
        AlertDialog.Builder alertbox = new AlertDialog.Builder(myViewHolder.rowView.getContext(),R.style.AlertDialogTheme);
            myViewHolder.rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.spin5));
            String enn = myViewHolder.rowView.getContext().getResources().getString(R.string.cannot_swipe);
            String war = myViewHolder.rowView.getContext().getResources().getString(R.string.warning);
            String ook = myViewHolder.rowView.getContext().getResources().getString(R.string.ok);
            String del = myViewHolder.rowView.getContext().getResources().getString(R.string.delete);
        alertbox.setMessage(enn);
        alertbox.setTitle(war);
        alertbox.setIcon(R.drawable.dailylogo);
        alertbox.setNeutralButton(ook,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0,
                                        int arg1) {
                    }
                });
        alertbox.show();

        }
        Log.d("selected",s);
    }

    @Override
    public void onRowClear(MyViewHolder myViewHolder, int position) {
//        myViewHolder.rowView.setBackgroundColor(Color.WHITE);
        cleared = String.valueOf(position);
        clea = position;
//        Intent nn = new Intent(context,HomeActivity.class);
//        nn.putExtra("selected",selected);
//        nn.putExtra("cleared",cleared);
//        context.startActivity(nn);
//        Intent intent = new Intent("custom-messagee");
//        //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
//        intent.putExtra("selected",selected);
//        intent.putExtra("cleared",cleared);
//        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public void oncle(MyViewHolder myViewHolder, int from, int to) {
        myViewHolder.rowView.setBackgroundColor(Color.GRAY);
        Log.d("cleared",String.valueOf(from));
        Log.d("cleared",String.valueOf(to));
        cleared = String.valueOf(to);
        clea = to;
        selected = String.valueOf(from);
        Intent nn = new Intent(context,HomeActivity.class);
        nn.putExtra("selected",selected);
        nn.putExtra("cleared",cleared);
        context.startActivity(nn);
    }
//    private final StartDragListener mStartDragListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle,mId,current,closed;
        private Button edit,nip,delet,curre,mo,ev;
        View rowView;
        private LinearLayout ll;
        public MyViewHolder(View itemView) {
            super(itemView);

            ll = itemView.findViewById(R.id.line);
            rowView = itemView;
            mTitle = itemView.findViewById(R.id.Name);
            current = itemView.findViewById(R.id.current);
            closed = itemView.findViewById(R.id.closed);
            mId = itemView.findViewById(R.id.id);
            edit = itemView.findViewById(R.id.edit);
            delet = itemView.findViewById(R.id.delete);
            nip = itemView.findViewById(R.id.nip);
            mo = itemView.findViewById(R.id.morn);
            ev = itemView.findViewById(R.id.even);
            curre = itemView.findViewById(R.id.currr);
//            sqlite = new SQLiteHelper(context);
//            database = sqlite.getWritableDatabase();
            }
    }

    public RecyclerViewAdapter(ArrayList<String> data, ArrayList<String> ID, ArrayList<String> AID, ArrayList<String> tim, ArrayList<String> OID, Integer ses, Integer in, Context context, String edpr, String depr) {
        this.data = data;
        datafull = data;
        this.ni = ID;
        this.iidd = AID;
        this.OID = OID;
        this.moreve = tim;
        this.context = context;
        this.sess = ses;
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
                String[] currencie = item.split(",");
                String d = currencie[1];
                if (d.contains(charText)) {
                    Log.d("fileterd1",item);
                    Log.d("fileterd2",d);
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
    public void filter1(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        data = new ArrayList<String>();
        if (charText.length() == 0) {
            // mCleanCopyDataset we always contains the unaltered and the filter (full) copy of the list of data
            data.addAll(datafull);
        } else {
            for (String item : datafull) {

                // we iterate through all the items on the list and if any item contains the search text, we add it to a new list mDataset
                String[] currencie = item.split(",");
                String d = currencie[4];
                if (d.contains(charText)) {
                    Log.d("fileterd1",item);
                    Log.d("fileterd2",d);
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

//        orderr();
        holder.setIsRecyclable(false);
        String animal = data.get(position);
        String[] currencies = animal.split(",");
        String s = currencies[0];
        String sS = currencies[1];
        String nn = currencies[5];
        Log.d("curreeer",s);
        holder.mId.setText(s);
        holder.mTitle.setText(sS);
        if(in == 0){
            holder.edit.setBackgroundResource(R.drawable.edit);
            holder.delet.setBackgroundResource(R.drawable.delete);
        }else{
            holder.delet.setBackgroundResource(R.drawable.delete_blue);
            holder.edit.setBackgroundResource(R.drawable.edit_blue);
        }
        if(position %2 == 1)
        {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.spin5));
//            holder.itemView.setBackgroundColor(Color.parseColor("#171717"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.Black));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }
//        String nn = ni.get(position);
        Log.d("nnn",nn);
        if(nn.equalsIgnoreCase("-1")){
//            holder.ll.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.mTitle.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.mId.setTextColor(ContextCompat.getColor(context,R.color.Yellow));
            holder.nip.setVisibility(View.GONE);
            holder.curre.setVisibility(View.GONE);
            holder.current.setVisibility(View.VISIBLE);
            holder.closed.setVisibility(View.GONE);
        }else if(nn.equalsIgnoreCase("0")){
//            holder.ll.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.mTitle.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.mId.setTextColor(ContextCompat.getColor(context,R.color.White));
            holder.nip.setVisibility(View.GONE);
            holder.curre.setVisibility(View.GONE);
            holder.current.setVisibility(View.VISIBLE);
            holder.closed.setVisibility(View.GONE);
        }else if(nn.equalsIgnoreCase("1")){
//            holder.ll.setBackgroundColor(Color.parseColor("#B0E0E6"));
            holder.mTitle.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.mId.setTextColor(ContextCompat.getColor(context,R.color.RoyalBlue));
            holder.nip.setVisibility(View.VISIBLE);
            holder.curre.setVisibility(View.GONE);
            holder.current.setVisibility(View.GONE);
            holder.closed.setVisibility(View.GONE);
        }else if(nn.equalsIgnoreCase("2")){
//            holder.ll.setBackgroundColor(Color.parseColor("#DC143C"));
            holder.mTitle.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.mId.setTextColor(ContextCompat.getColor(context,R.color.Red));
            holder.nip.setVisibility(View.GONE);
            holder.curre.setVisibility(View.VISIBLE);
            holder.current.setVisibility(View.GONE);
            holder.closed.setVisibility(View.GONE);
        }else if(nn.equalsIgnoreCase("3")){
//            holder.ll.setBackgroundColor(Color.parseColor("#DC143C"));
            holder.mTitle.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.mId.setTextColor(ContextCompat.getColor(context,R.color.Green));
            holder.nip.setVisibility(View.GONE);
            holder.curre.setVisibility(View.GONE);
            holder.current.setVisibility(View.GONE);
            holder.closed.setVisibility(View.VISIBLE);
        }
        String tiii = moreve.get(position);
        if(tiii.equalsIgnoreCase("1")){
            holder.mo.setVisibility(View.GONE);
            holder.ev.setVisibility(View.VISIBLE);
        }else if(tiii.equalsIgnoreCase("2")){
            holder.mo.setVisibility(View.VISIBLE);
            holder.ev.setVisibility(View.GONE);
        }
//        String nam  = animal.replaceAll("[NIPNIP,CURRENT,NIP]", "");
        Log.d("naaaaa",animal);
//        holder.mTitle.setText(animal);
        if(edi.equalsIgnoreCase("0")){
            holder.edit.setVisibility(View.GONE);
        }
        if(del.equalsIgnoreCase("0")){
            holder.delet.setVisibility(View.GONE);
        }
        holder.delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pos = String.valueOf(iidd.get(position));
//                 num  = pos.replaceAll("[^0-9]", "");
                String animal = data.get(position);
                String[] currencies = animal.split(",");
                num = currencies[3];
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
                                database = sqlite.getReadableDatabase();
                                String table = "dd_customers";
                                String whereClause = "id=?";
                                String[] whereArgs = new String[] {num};
                                database.delete(table, whereClause, whereArgs);
                                Intent name= new Intent(context, HomeActivity.class);
                                context.startActivity(name);
                                sqlite.close();
                                database.close();
                            }
                        });
                alertbox.show();
            }
        });
        holder.mo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 acttt = ni.get(position);
                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext(),R.style.AlertDialogTheme);
                String enn = v.getContext().getResources().getString(R.string.move_morning);
                String war = v.getContext().getResources().getString(R.string.warning);
                String ook = v.getContext().getResources().getString(R.string.cancel);
                String del = v.getContext().getResources().getString(R.string.ok);
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
                                if(acttt.equalsIgnoreCase("0") || acttt.equalsIgnoreCase("1")){
//                                    String ooo = OID.get(position);
                                    String animal = data.get(position);
                                    String[] currencies = animal.split(",");
                                   String ooo = currencies[2];
                                    Log.d("orderrr1",ooo);
                                    orderr(ooo);
                                    sqlite = new SQLiteHelper(context);
                                    database = sqlite.getWritableDatabase();
                                    String MY_QUERY1 = "SELECT order_id_new FROM dd_customers WHERE debit_type IN (0,1) AND tracking_id = ? ORDER BY order_id_new DESC LIMIT 0,1 ";
                                    Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{"1"});
                                    if (cursor != null) {
                                        if (cursor.getCount() != 0) {
                                            cursor.moveToFirst();
                                            do {
                                                int index;
                                                index = cursor.getColumnIndexOrThrow("order_id_new");
                                                prevorder = cursor.getString(index);
                                                Log.d("orsderdf",prevorder);
                                                if (prevorder.trim().equals(null)) {
                                                    prevorder = "0";
                                                    ordddd = Integer.parseInt(prevorder);
                                                    ordddd = ordddd + 1;
                                                    Log.d("orderrre", String.valueOf(ordddd));
                                                } else {
                                                    ordddd = Integer.parseInt(prevorder);
                                                    ordddd = ordddd + 1;
                                                    Log.d("orderrre", String.valueOf(ordddd));
                                                }
                                            }
                                            while (cursor.moveToNext());
                                            cursor.close();
                                            sqlite.close();
                                            database.close();
                                        }
                                        else {
                                            cursor.close();
                                            sqlite.close();
                                            database.close();
                                            ordddd = 0 + 1;
                                            Log.d("orderrre", String.valueOf(ordddd));
                                        }
                                    }else{
                                        cursor.close();
                                        sqlite.close();
                                        database.close();
                                    }
                                }
                                else if(acttt.equalsIgnoreCase("2")){
//                                    String ooo = OID.get(position);
                                    String animal = data.get(position);
                                    String[] currencies = animal.split(",");
                                    String ooo = currencies[2];
                                    Log.d("orderrr1",ooo);
                                    orderr1(ooo);
                                    sqlite = new SQLiteHelper(context);
                                    database = sqlite.getWritableDatabase();
                                    String MY_QUERY1 = "SELECT order_id_new FROM dd_customers WHERE debit_type = '2' AND tracking_id = ? ORDER BY order_id_new DESC LIMIT 0,1 ";
                                    Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{"1"});
                                    if (cursor != null) {
                                        if (cursor.getCount() != 0) {
                                            cursor.moveToFirst();
                                            do {
                                                int index;
                                                index = cursor.getColumnIndexOrThrow("order_id_new");
                                                prevorder = cursor.getString(index);
                                                Log.d("orsderdf",prevorder);
                                                if (prevorder.trim().equals(null)) {
                                                    prevorder = "0";
                                                    ordddd = Integer.parseInt(prevorder);
                                                    ordddd = ordddd + 1;
                                                    Log.d("orderrre", String.valueOf(ordddd));
                                                } else {
                                                    ordddd = Integer.parseInt(prevorder);
                                                    ordddd = ordddd + 1;
                                                    Log.d("orderrre", String.valueOf(ordddd));
                                                }
                                            }
                                            while (cursor.moveToNext());
                                            cursor.close();
                                            sqlite.close();
                                            database.close();
                                        }
                                        else {
                                            cursor.close();
                                            sqlite.close();
                                            database.close();
                                            ordddd = 5000 + 1;
                                            Log.d("orderrre", String.valueOf(ordddd));
                                        }
                                    }else{
                                        cursor.close();
                                        sqlite.close();
                                        database.close();
                                    }
                                }
                                cidorder();
                                sqlite = new SQLiteHelper(context);
                                database = sqlite.getWritableDatabase();
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                                String todate = df.format(c.getTime());
                                String animal = data.get(position);
                                String[] currencies = animal.split(",");
                                num = currencies[3];
                                String ccc = currencies[4];
                                Log.d("ordered",num+" "+ordddd);
                                String table = "dd_customers";
                                ContentValues cv = new ContentValues();
                                cv.put("tracking_id","1");
                                cv.put("updated_date",todate);
                                cv.put("order_id",ordddd);
                                cv.put("CID",CIDorder);
                                cv.put("order_id_new",CIDorder);
                                database.update(table, cv, "id = ? AND tracking_id = ?", new String[]{num,String.valueOf(sess)});
                                String naamm = holder.itemView.getContext().getString(R.string.noname);
                                ContentValues values = new ContentValues();
                                values.put("order_id", "-1");
                                values.put("CID", ccc);
                                values.put("order_id_new", ccc);
                                values.put("customer_name", naamm);
                                values.put("tracking_id", sess);
                                values.put("created_date", todate);
                                values.put("debit_type", "-1");
                                database.insert(table, null, values);
                                Toast.makeText(context,"Changed to Morning....!!!",Toast.LENGTH_SHORT).show();
                                sqlite.close();
                                database.close();
                                Intent newd = new Intent(context,HomeActivity.class);
                                newd.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(newd);

                                     }
                        });
                alertbox.show();
            }
        });
        holder.ev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acttt = ni.get(position);
                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext(),R.style.AlertDialogTheme);
                String enn = v.getContext().getResources().getString(R.string.move_evening);
                String war = v.getContext().getResources().getString(R.string.warning);
                String ook = v.getContext().getResources().getString(R.string.cancel);
                String del = v.getContext().getResources().getString(R.string.ok);
                alertbox.setMessage(enn);
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
                                if(acttt.equalsIgnoreCase("0") || acttt.equalsIgnoreCase("1")){
//                                    String ooo = OID.get(position);
                                    String animal = data.get(position);
                                    String[] currencies = animal.split(",");
                                    String ooo = currencies[2];
                                    Log.d("orderrr1",ooo);
                                    orderr(ooo);
                                    sqlite = new SQLiteHelper(context);
                                    database = sqlite.getWritableDatabase();
                                    String MY_QUERY1 = "SELECT order_id_new FROM dd_customers WHERE debit_type IN (0,1) AND tracking_id = ? ORDER BY order_id_new DESC LIMIT 0,1 ";
                                    Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{"2"});
                                    if (cursor != null) {
                                        if (cursor.getCount() != 0) {
                                            cursor.moveToFirst();
                                            do {
                                                int index;
                                                index = cursor.getColumnIndexOrThrow("order_id_new");
                                                prevorder = cursor.getString(index);
                                                Log.d("orsderdf",prevorder);
                                                if (prevorder.trim().equals(null)) {
                                                    prevorder = "0";
                                                    ordddd = Integer.parseInt(prevorder);
                                                    ordddd = ordddd + 1;
//                                                    Log.d("orderrre", String.valueOf(ordddd));
                                                } else {
                                                    ordddd = Integer.parseInt(prevorder);
                                                    ordddd = ordddd + 1;
//                                                    Log.d("orderrre", String.valueOf(ordddd));
                                                }
                                            }
                                            while (cursor.moveToNext());
                                            cursor.close();
                                            sqlite.close();
                                            database.close();
                                        }
                                        else {
                                            cursor.close();
                                            sqlite.close();
                                            database.close();
                                            ordddd = 0 + 1;
                                            Log.d("orderrre", String.valueOf(ordddd));
                                        }
                                    }else{
                                        cursor.close();
                                        sqlite.close();
                                        database.close();
                                    }
                                }else if(acttt.equalsIgnoreCase("2")){
//                                    String ooo = OID.get(position);
                                    String animal = data.get(position);
                                    String[] currencies = animal.split(",");
                                    String ooo = currencies[2];
                                    Log.d("orderrr1",ooo);
                                    orderr1(ooo);
                                    sqlite = new SQLiteHelper(context);
                                    database = sqlite.getWritableDatabase();
                                    String MY_QUERY1 = "SELECT order_id_new FROM dd_customers WHERE debit_type = '2' AND tracking_id = ? ORDER BY order_id_new DESC LIMIT 0,1 ";
                                    Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{"2"});
                                    if (cursor != null) {
                                        if (cursor.getCount() != 0) {
                                            cursor.moveToFirst();
                                            do {
                                                int index;
                                                index = cursor.getColumnIndexOrThrow("order_id_new");
                                                prevorder = cursor.getString(index);
                                                Log.d("orsderdf",prevorder);
                                                if (prevorder.trim().equals(null)) {
                                                    prevorder = "0";
                                                    ordddd = Integer.parseInt(prevorder);
                                                    ordddd = ordddd + 1;
                                                    Log.d("orderrre", String.valueOf(ordddd));
                                                } else {
                                                    ordddd = Integer.parseInt(prevorder);
                                                    ordddd = ordddd + 1;
                                                    Log.d("orderrre", String.valueOf(ordddd));
                                                }
                                            }
                                            while (cursor.moveToNext());
                                            cursor.close();
                                            sqlite.close();
                                            database.close();
                                        }
                                        else {
                                            ordddd = 5000 + 1;
                                            Log.d("orderrre", String.valueOf(ordddd)); cursor.close();
                                            sqlite.close();
                                            database.close();
                                        }
                                    }else{
                                        cursor.close();
                                        sqlite.close();
                                        database.close();
                                    }
                                }
                                cidorder1();
                                sqlite = new SQLiteHelper(context);
                                database = sqlite.getWritableDatabase();
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                                String todate = df.format(c.getTime());
                                String animal = data.get(position);
                                String[] currencies = animal.split(",");
                                num = currencies[3];
                                String ccc = currencies[4];
                                Log.d("ordered",num+" "+ordddd);
                                String table = "dd_customers";
                                ContentValues cv = new ContentValues();
                                cv.put("tracking_id","2");
                                cv.put("updated_date",todate);
                                cv.put("order_id",ordddd);
                                cv.put("order_id_new",CIDorder);
                                cv.put("CID",CIDorder);
                                database.update(table, cv, "id = ? AND tracking_id = ?", new String[]{num,String.valueOf(sess)});
                                String naamm = holder.itemView.getContext().getString(R.string.noname);
                                ContentValues values = new ContentValues();
                                values.put("order_id", "-1");
                                values.put("CID", ccc);
                                values.put("order_id_new", ccc);
                                values.put("customer_name", naamm);
                                values.put("tracking_id", sess);
                                values.put("created_date", todate);
                                values.put("debit_type", "-1");
                                database.insert(table, null, values);
                                Toast.makeText(context,"Changed to Evening....!!!",Toast.LENGTH_SHORT).show();
                                sqlite.close();
                                database.close();
                                Intent newd = new Intent(context,HomeActivity.class);
                                newd.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(newd);
                            }
                        });
                alertbox.show();
            }
        });
        holder.curre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String animal = data.get(position);
                String[] currencies = animal.split(",");
                ooo = currencies[2];
// ooo = OID.get(position);
Log.d("orderrr1",ooo);
                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext(),R.style.AlertDialogTheme);
                String enn = v.getContext().getResources().getString(R.string.moveto_nip);
                String war = v.getContext().getResources().getString(R.string.warning);
                String ook = v.getContext().getResources().getString(R.string.cancel);
                String del = v.getContext().getResources().getString(R.string.ok);
                alertbox.setMessage(enn);
                alertbox.setMessage(R.string.moveto_nip);
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
                                orderr1(ooo);
                                sqlite = new SQLiteHelper(context);
                                database = sqlite.getWritableDatabase();
                                String MY_QUERY1 = "SELECT order_id_new FROM dd_customers WHERE debit_type IN (0,1) AND tracking_id = ? ORDER BY order_id_new DESC LIMIT 0,1 ";
                                Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{String.valueOf(sess)});
                                if (cursor != null) {
                                    if (cursor.getCount() != 0) {
                                        cursor.moveToFirst();
                                        do {
                                            int index;
                                            index = cursor.getColumnIndexOrThrow("order_id_new");
                                            prevorder = cursor.getString(index);
                                            Log.d("orderrr1",prevorder);
                                            if (prevorder.trim().equals(null)) {
                                                prevorder = "0";
                                                ordddd = Integer.parseInt(prevorder);
                                                ordddd = ordddd + 1;
                                                Log.d("orderrre", String.valueOf(ordddd));
                                            } else {
                                                ordddd = Integer.parseInt(prevorder);
                                                ordddd = ordddd + 1;
                                                Log.d("orderrre", String.valueOf(ordddd));
                                            }
                                        }
                                        while (cursor.moveToNext());
                                        cursor.close();
                                        sqlite.close();
                                        database.close();
                                    }
                                    else {
                                        cursor.close();
                                        sqlite.close();
                                        database.close();
                                        ordddd = 0 + 1;
                                        Log.d("orderrre", String.valueOf(ordddd));
                                    }
                                }else{
                                    cursor.close();
                                    sqlite.close();
                                    database.close();
                                }
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                                String todate = df.format(c.getTime());
//                orderr();
                                sqlite = new SQLiteHelper(context);
                                database = sqlite.getWritableDatabase();
                                String pos = String.valueOf(data.get(position));
//                num  = pos.replaceAll("[^0-9]", "");
//                                num = iidd.get(position);
                                String animal = data.get(position);
                                String[] currencies = animal.split(",");
                                num = currencies[3];
                                String status = "1";
                                String table = "dd_customers";
                                ContentValues cv = new ContentValues();
                                cv.put("debit_type",status);
                                cv.put("order_id",String.valueOf(ordddd));
                                cv.put("debit_type_updated",todate);
                                database.update(table, cv, "id = ? AND tracking_id = ?", new String[]{num,String.valueOf(sess)});
                                Toast.makeText(context,"NIP Changed....!!!",Toast.LENGTH_SHORT).show();
                                sqlite.close();
                                database.close();
                                Intent newd = new Intent(context,HomeActivity.class);
                                newd.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(newd);
                            }
                        });
                alertbox.show();

            }
        });
        holder.nip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                 ooo = OID.get(position);
                String animal = data.get(position);
                String[] currencies = animal.split(",");
                ooo = currencies[2];
                Log.d("orderrr1",ooo);
                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext(),R.style.AlertDialogTheme);
                String enn = v.getContext().getResources().getString(R.string.moveto_nipnip);
                String war = v.getContext().getResources().getString(R.string.warning);
                String ook = v.getContext().getResources().getString(R.string.cancel);
                String del = v.getContext().getResources().getString(R.string.ok);
                alertbox.setMessage(enn);
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
                                orderr(ooo);
                                sqlite = new SQLiteHelper(context);
                                database = sqlite.getWritableDatabase();
                                String MY_QUERY1 = "SELECT order_id FROM dd_customers WHERE debit_type = '2' AND tracking_id = ? ORDER BY order_id DESC LIMIT 0,1 ";
                                Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{String.valueOf(sess)});
                                if (cursor != null) {
                                    if (cursor.getCount() != 0) {
                                        cursor.moveToFirst();
                                        do {
                                            int index;
                                            index = cursor.getColumnIndexOrThrow("order_id");
                                            prevorder = cursor.getString(index);
                                            Log.d("orderrr1",prevorder);
                                            if (prevorder.trim().equals(null)) {
                                                prevorder = "0";
                                                ordddd = Integer.parseInt(prevorder);
                                                ordddd = ordddd + 1;
                                                Log.d("orderrre", String.valueOf(ordddd));
                                            } else {
                                                ordddd = Integer.parseInt(prevorder);
                                                ordddd = ordddd + 1;
                                                Log.d("orderrre", String.valueOf(ordddd));
                                            }
                                        }
                                        while (cursor.moveToNext());
                                        cursor.close();
                                        sqlite.close();
                                        database.close();
                                    }
                                    else {
                                        cursor.close();
                                        sqlite.close();
                                        database.close();
                                        ordddd = 5000 + 1;
                                        Log.d("orderrre", String.valueOf(ordddd));
                                    }
                                }else{
                                    cursor.close();
                                    sqlite.close();
                                    database.close();
                                }
//                orderr();
                                sqlite = new SQLiteHelper(context);
                                database = sqlite.getWritableDatabase();
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                                String todate = df.format(c.getTime());
//                orderr();
                                String pos = String.valueOf(data.get(position));
//                num  = pos.replaceAll("[^0-9]", "");
//                                num = iidd.get(position);
                                String animal = data.get(position);
                                String[] currencies = animal.split(",");
                                num = currencies[3];
                                String status = "2";
                                String table = "dd_customers";
                                ContentValues cv = new ContentValues();
                                cv.put("debit_type",status);
                                cv.put("order_id",String.valueOf(ordddd));
                                cv.put("debit_type_updated",todate);
                                database.update(table, cv, "id = ? AND tracking_id = ?", new String[]{num,String.valueOf(sess)});
                                Toast.makeText(context,"NIP NIP Changed....!!!",Toast.LENGTH_SHORT).show();
                                sqlite.close();
                                database.close();
                                Intent newd = new Intent(context,HomeActivity.class);
                                newd.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(newd);
                            }
                        });
                alertbox.show();

            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            String pos = String.valueOf(data.get(position));
//                String number  = pos.replaceAll("[^0-9]", "");
//            String number = iidd.get(position);
                String animal = data.get(position);
                String[] currencies = animal.split(",");
                String number = currencies[3];
                Log.d("editpos",String.valueOf(animal));

                Intent name= new Intent(context, Addnames.class);
                name.putExtra("ID",number);
                v.getContext().startActivity(name);
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    //orderr() -update order id
    //Params - order id
    //Created on 25/01/2022
    //Return - NULL
    public void orderr(String ord){
        sqlite = new SQLiteHelper(context);
        database = sqlite.getWritableDatabase();
        String MY_QUERY1 = "SELECT order_id_new FROM dd_customers WHERE debit_type IN (0,1) AND tracking_id = ? ORDER BY order_id_new DESC LIMIT 0,1 ";
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{String.valueOf(sess)});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    prevorder = cursor.getString(index);
                    Log.d("orderrr1",prevorder);
                    if (prevorder.trim().equals(null)) {
                        prevorder = "0";
                        ordddd = Integer.parseInt(prevorder);
                        ordddd = ordddd + 1;
                        Log.d("orderrre", String.valueOf(ordddd));
                    } else {
                        ordddd = Integer.parseInt(prevorder);
                        ordddd = ordddd + 1;
                        Log.d("orderrre", String.valueOf(ordddd));
                    }
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }
            else {
                cursor.close();
                sqlite.close();
                database.close();
                ordddd = 0 + 1;
                Log.d("orderrre", String.valueOf(ordddd));
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }
        Integer from1 = Integer.parseInt(ord);
        Integer to = Integer.parseInt(prevorder);
        Log.d("forrr",String.valueOf(from1)+" "+String.valueOf(to));
        from1 = from1 + 1;
//        for(int i=from1;i<=to;i++){
//            sqlite = new SQLiteHelper(context);
//            database = sqlite.getWritableDatabase();
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i-1);
//            String[] args =  new String[]{String.valueOf(i),String.valueOf(sess)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//            sqlite.close();
//            database.close();
//            Log.d("forrrr1",String.valueOf(i));
//            Log.d("forrrr",String.valueOf(i-1));
//        }
    }
    //orderr1() - update order id
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void orderr1(String ord){
        sqlite = new SQLiteHelper(context);
        database = sqlite.getWritableDatabase();
        String MY_QUERY1 = "SELECT order_id_new FROM dd_customers WHERE debit_type = '2' AND tracking_id = ? ORDER BY order_id_new DESC LIMIT 0,1 ";
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{String.valueOf(sess)});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    prevorder = cursor.getString(index);
                    Log.d("orderrr1",prevorder);
                    if (prevorder.trim().equals(null)) {
                        prevorder = "0";
                        ordddd = Integer.parseInt(prevorder);
                        ordddd = ordddd + 1;
                        Log.d("orderrre", String.valueOf(ordddd));
                    } else {
                        ordddd = Integer.parseInt(prevorder);
                        ordddd = ordddd + 1;
                        Log.d("orderrre", String.valueOf(ordddd));
                    }
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }
            else {
                cursor.close();
                sqlite.close();
                database.close();
                ordddd = 5000 + 1;
                Log.d("orderrre", String.valueOf(ordddd));
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }
        Integer from1 = Integer.parseInt(ord);
        Integer to = Integer.parseInt(prevorder);
        Log.d("forrr",String.valueOf(from1)+" "+String.valueOf(to));
        from1 = from1 + 1;
//        for(int i=from1;i<=to;i++){
//            sqlite = new SQLiteHelper(context);
//            database = sqlite.getWritableDatabase();
//            ContentValues cv = new ContentValues();
//            cv.put("order_id",i-1);
//            String[] args =  new String[]{String.valueOf(i),String.valueOf(sess)};
//            database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//            Log.d("forrrr1",String.valueOf(i));
//            Log.d("forrrr",String.valueOf(i-1));
//            sqlite.close();
//            database.close();
//        }

    }
    //cidorder() - update CID
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void cidorder(){
        sqlite = new SQLiteHelper(context);
        database = sqlite.getWritableDatabase();
        String MY_QUERY1 = "SELECT CID FROM dd_customers WHERE tracking_id = ? ORDER BY CID DESC LIMIT 0,1 ";
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{"1"});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("CID");
                    cid = cursor.getString(index);
                    Log.d("cciidd1",cid);
                    if (cid.trim().equals(null)) {
                        cid = "0";
                        CIDorder = Integer.parseInt(cid);
                        CIDorder = CIDorder + 1;
                        Log.d("cciidd", String.valueOf(CIDorder));
                    }else{
                        CIDorder = Integer.parseInt(cid);
                        CIDorder = CIDorder + 1;
                        Log.d("cciidd", String.valueOf(CIDorder));
                    }
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }
            else {
                cursor.close();
                sqlite.close();
                database.close();
                CIDorder = 0 + 1;
                Log.d("cciidd", String.valueOf(CIDorder));
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }
    }
    //cidorder1() - update CID
    //Params - NULL
    //Created on 25/01/2022
    //Return - NULL
    public void cidorder1(){
        sqlite = new SQLiteHelper(context);
        database = sqlite.getWritableDatabase();
        String MY_QUERY1 = "SELECT CID FROM dd_customers WHERE tracking_id = ? ORDER BY CID DESC LIMIT 0,1 ";
        Cursor cursor = database.rawQuery(MY_QUERY1, new String[]{"2"});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;
                    index = cursor.getColumnIndexOrThrow("CID");
                    cid = cursor.getString(index);
                    Log.d("cciidd1",cid);
                    if (cid.trim().equals(null)) {
                        cid = "0";
                        CIDorder = Integer.parseInt(cid);
                        CIDorder = CIDorder + 1;
                        Log.d("cciidd", String.valueOf(CIDorder));
                    }else{
                        CIDorder = Integer.parseInt(cid);
                        CIDorder = CIDorder + 1;
                        Log.d("cciidd", String.valueOf(CIDorder));
                    }
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }
            else {
                cursor.close();
                sqlite.close();
                database.close();
                CIDorder = 0 + 1;
                Log.d("cciidd", String.valueOf(CIDorder));
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }
    }



}


