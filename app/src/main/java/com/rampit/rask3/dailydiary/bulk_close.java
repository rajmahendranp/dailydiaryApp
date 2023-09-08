package com.rampit.rask3.dailydiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

//Updated on 25/01/2022 by RAMPIT
//used go to close debit
//closed() - get all debit collection completed
//updateclose() - close an debit


public class bulk_close {
    Calendar myCalendar1 = Calendar.getInstance();
    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    //    String myFormat1 = "dd/MM/yyyy";//In which you need put here
    String formattedDate = df.format(c.getTime());
    String id,adddd,edittt,deleteee,viewww,did;
    SQLiteHelper sqlite;
    Set<String> set = new HashSet<String>();
    Context context;
    Integer see,see2,see1,cle,cle1,cloosed,befo,sesssion,ses22 = 0,ses1;

    SQLiteDatabase database;
    ArrayList<String> Names = new ArrayList<>();

    //closed() - get all debit collection completed
    //Params - session and application context
    //Created on 25/01/2022
    //Return - NULL
    public void closed(Integer ses, Context cntx) {
        Names.clear();
        Log.d("formm",formattedDate);
        context = cntx;
        ses1 = ses;
        sqlite = new SQLiteHelper(cntx);
        database = sqlite.getReadableDatabase();
        String NIP = "SELECT (c.debit_amount - SUM(a.collection_amount)) as paid,c.*,b.id AS ID,b.customer_name FROM dd_account_debit c " +
                "INNER JOIN dd_customers b on b.id = c.customer_id INNER JOIN dd_collection a on a.customer_id = b.id WHERE ( c.closeing_date < ? AND b.tracking_id = ? ) GROUP BY b.id  ";
        String NIPP = "SELECT a.*,SUM (b.collection_amount ) as paid, SUM( b.discount ) as disco,c.id as ID,c.order_id_new " +
                "FROM dd_customers c LEFT JOIN  dd_account_debit a on a.customer_id = c.id LEFT JOIN dd_collection b on b.debit_id = a.id " +
                " WHERE  c.tracking_id = ?  AND c.debit_type IN (0,1,2) AND a.active_status = 1  GROUP BY c.id ORDER BY c.id DESC";
//        Cursor cursor = database.rawQuery(NIP, new String[]{formattedDate,String.valueOf(ses)});
        Cursor cursor = database.rawQuery(NIPP,new String[]{String.valueOf(ses)});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;

                    index = cursor.getColumnIndexOrThrow("ID");
                    id = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    did = cursor.getString(index);
//
                    index = cursor.getColumnIndexOrThrow("closeing_date");
                    String closee = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("order_id_new");
                    String orderr = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("debit_amount");
                    String debit = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("paid");
                    String total = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("disco");
                    String total1 = cursor.getString(index);

                    if(total == null || total.equalsIgnoreCase("")){
                        total = "0";
                    }
                    if(total1 == null || total1.equalsIgnoreCase("")){
                        total1 = "0";
                    }
                    if(debit == null){
                        debit = "0";
                    }

                    Integer dee = Integer.parseInt(debit);
                    Integer tot = Integer.parseInt(total);
                    Integer tot1 = Integer.parseInt(total1);
                    tot = tot + tot1 ;
                    Integer ppp = dee - tot;
                    if(ppp <= 0){
                        Integer ooo = Integer.parseInt(orderr);
                        updateclose(ses,did);
//                        if(ooo > 5000){
//                            ses22 = ses22 + 1;
//                            Log.d("formm",String.valueOf(ses22));
//                            last1(String.valueOf(ses),orderr,id,did);
//                        }else{
//                            ses22 = ses22 + 1;
//                            Log.d("formm",String.valueOf(ses22));
//                            last(String.valueOf(ses),orderr,id,did);
//                        }
//                        updateclose(ses);
                    }

//                    if(id != null){
//                        updatenip(ses);
//                    }
                    Log.d("Callidclose", debit+" "+total+" "+total1+" "+id+" "+closee);
                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                cursor.close();
                sqlite.close();
                database.close();
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();        }
    }
  /*  private void last(String ses, String order, String id, String did){

        sqlite = new SQLiteHelper(context);
        database = sqlite.getWritableDatabase();
        String MY_QUERY1 = "SELECT order_id FROM dd_customers WHERE debit_type IN (0,1) AND tracking_id = ? ORDER BY order_id DESC LIMIT 0,1 ";
//        Cursor cursor = database.rawQuery(NIP, new String[]{formattedDate,String.valueOf(ses)});
        Cursor cursor = database.rawQuery(MY_QUERY1,new String[]{String.valueOf(ses)});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;


                    index = cursor.getColumnIndexOrThrow("order_id");
                    String orderr = cursor.getString(index);

                    if(orderr == null){
                        orderr = "0";
                    }
                    Log.d("ooor",orderr+","+order);
                    Integer ooo = Integer.valueOf(order);
                    upddate(order,orderr,ses,id,did);


                }
                while (cursor.moveToNext());
                cursor.close();
                sqlite.close();
                database.close();
            }else{
                cursor.close();
                sqlite.close();
                database.close();
            }
        }else{
            cursor.close();
            sqlite.close();
            database.close();
        }
    }
    private void last1(String ses, String order, String id,String did){

        sqlite = new SQLiteHelper(context);
        database = sqlite.getWritableDatabase();
        String MY_QUERY1 = "SELECT order_id FROM dd_customers WHERE debit_type IN (2) AND tracking_id = ? ORDER BY order_id DESC LIMIT 0,1 ";
//        Cursor cursor = database.rawQuery(NIP, new String[]{formattedDate,String.valueOf(ses)});
        Cursor cursor = database.rawQuery(MY_QUERY1,new String[]{String.valueOf(ses)});
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    int index;


                    index = cursor.getColumnIndexOrThrow("order_id");
                    String orderr = cursor.getString(index);

                    if(orderr == null){
                        orderr = "0";
                    }
                    Log.d("ooor",orderr+","+order);
                    Integer ooo = Integer.valueOf(order);
                    upddate(order,orderr,ses,id,did);


                }
                while (cursor.moveToNext());
                sqlite.close();
                database.close();
            }else{
                cursor.close();
                sqlite.close();
                database.close();
            }
        }
        else{
            cursor.close();
            sqlite.close();
            database.close();        }
    }*/
  /*  private void upddate(String selected, String cleared, String ses, String id, String did) {
        Integer see = Integer.valueOf(selected);
        Integer cle = Integer.valueOf(cleared);
        see1 = see ;
        cle1 = cle ;
        Log.d("namessssss54", String.valueOf(see));
        Log.d("namessssss54", String.valueOf(cle));
//        Log.d("ooor",id+","+see1+","+cle1);
        if (see1 < cle1) {
            Log.d("namessssss54", String.valueOf(see));
            Log.d("namessssss54", String.valueOf(cle));
            see2 = see1 + 1;
//            for (int i = see2; i <= cle1;i++){
//                sqlite = new SQLiteHelper(context);
//                database = sqlite.getWritableDatabase();
//                Log.d("forrloop",String.valueOf(i));
//                ContentValues cv = new ContentValues();
//                cv.put("order_id",i-1);
//                String[] args =  new String[]{String.valueOf(i), String.valueOf(ses)};
//                database.update("dd_customers", cv,  "order_id=? AND tracking_id = ?",args);
//                Log.d("forrloop111",String.valueOf(i));
//                Log.d("forrloop222",String.valueOf(i+1));
//                sqlite.close();
//                database.close();
//            }
            sqlite = new SQLiteHelper(context);
            database = sqlite.getWritableDatabase();
            ContentValues cv1 = new ContentValues();
            cv1.put("order_id","-1");
            cv1.put("previous_order_id",selected);
            database.update("dd_customers", cv1,  "id=? AND order_id=? AND tracking_id = ?", new String[]{id,String.valueOf(see1), String.valueOf(ses)});
            updateclose(Integer.valueOf(ses),did);
            sqlite.close();
            database.close();

        }else if (see1.equals(cle1)){
            Log.d("ooor",id+","+see1);
            sqlite = new SQLiteHelper(context);
            database = sqlite.getWritableDatabase();
            ContentValues cv1 = new ContentValues();
            cv1.put("order_id","-1");
            cv1.put("previous_order_id",selected);
            database.update("dd_customers", cv1,  "id=? AND order_id=? AND tracking_id = ?", new String[]{id,String.valueOf(see1), String.valueOf(ses)});
            updateclose(Integer.valueOf(ses),did);
            sqlite.close();
            database.close();

        }
    }*/
  //updateclose() - close an debit
  //Params - session and debit id
  //Created on 25/01/2022
  //Return - NULL
    private void updateclose(Integer ses,String did) {
        Log.d("did3000",did+" "+id);
        sqlite = new SQLiteHelper(context);
        database = sqlite.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("debit_type","3");
        cv.put("debit_type_updated",formattedDate);
        database.update("dd_customers", cv, "id = ? AND tracking_id = ?", new String[]{id, String.valueOf(ses)});
        ContentValues cv1 = new ContentValues();
        cv1.put("active_status","0");
        cv1.put("updated_date",formattedDate);
        database.update("dd_account_debit", cv1, "id = ?", new String[]{did});
        sqlite.close();
        database.close();
        ((Callback)context).populateRecyclerView(ses);
        ((Callback)context).populateRecyclerViewnip(ses);

    }
}
