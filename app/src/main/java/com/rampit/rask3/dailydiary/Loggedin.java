package com.rampit.rask3.dailydiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rampit.rask3.dailydiary.SQLite.SQLiteHelper;

//Updated on 25/01/2022 by RAMPIT
//Sample Login page ( NOT USED )

public class Loggedin extends AppCompatActivity {

    TextView name,pass,city,pin,email,phone;
    Button submit;
    String ss;
    Integer ses;
    SQLiteHelper sqlite;
    SQLiteDatabase database;
    public static String TABLENAME = "REGISTER";
    public static String TABLENAME1 = "dd_main_info";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggedin);
        name = findViewById(R.id.name);
        pass = findViewById(R.id.password);
        city = findViewById(R.id.city);
        pin = findViewById(R.id.pincode);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.mobile);
        submit = findViewById(R.id.logout);
        sqlite = new SQLiteHelper(this);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        editor.putInt("isloginn",1);
        editor.commit();
        ses = pref.getInt("session", 1);
        if(ses == 1){
            ss = getString(R.string.morning);
        }else if(ses == 2){
            ss = getString(R.string.evening);
        }
        Log.d("ses",String.valueOf(ses));
        populateRecyclerView();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.putInt("isloginn",0);
                editor.commit();
                Intent i = new Intent(Loggedin.this,Splash.class);
                startActivity(i);
            }
        });
    }
    private void populateRecyclerView()
    {

        sqlite = new SQLiteHelper(this);
        database = sqlite.getReadableDatabase();
//        String[] columns = {
//                "USERNAME","PASSWORD","EMAIL","CITY","PINCODE","MOBILE"};
        String[] columns1 = {
                "dd_name","dd_location","starting_date"};
        String[] columns2 = {
                "customer_name","location","created_date"};
        String[] columns3 = {
                "debit_amount","document_charge","created_date"};
        String[] columns4 = {
                "collection_amount","id","created_date"};
        String[] columns5 = {
                "amount","id","created_date"};
        Intent ne = getIntent();
        String id = ne.getStringExtra("id");
        String whereClause = "id=?";
        String[] whereArgs = new String[] {id};
        //        String order = "ORDERID";
        Cursor cursor = database.query("dd_employee",
                columns5,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        if (cursor != null){
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
//                while(cursor.moveToNext()) {
//                    int index;
//
//                    for (int i = 0; i < cursor.getColumnCount(); i++) {
//                        index = cursor.getColumnIndexOrThrow("NAME");
//                        String firstName = cursor.getString(index);
//
//
//                        index = cursor.getColumnIndexOrThrow("ID");
//                        long id = cursor.getLong(index);
//                        Names.add(firstName);
//                        //... do something with data
//                    }
//                    Log.d("names", String.valueOf(Names));
//                }
                do{
                    int index;

                    index = cursor.getColumnIndexOrThrow("amount");
                    String USER = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("id");
                    String PASS = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow("created_date");
                    String EMAIL = cursor.getString(index);

                    name.setText(USER);
                    city.setText(PASS);
                    email.setText(EMAIL);
//                    pin.setText(ss);
//
//                    Log.d("names", String.valueOf(Names));
//                    Log.d("names", String.valueOf(Names));
//                    Log.d("LOG_TAG_HERE", row_values);
                }
                while (cursor.moveToNext());
            }
        }


    }


}
