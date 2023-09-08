package com.rampit.rask3.dailydiary;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//Updated on 25/01/2022 by RAMPIT
//used to test activity ( NOT USED )

public class main_activity1 extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private TextView textView;
    private boolean detecting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ActivityCompat.requestPermissions(main_activity1.this,new String[]{android.Manifest.permission.PROCESS_OUTGOING_CALLS} ,1);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, CallDetectionService.class);
        if(!detecting){
            detecting=true;
            button.setText("Turn off detection");
            startService(intent);
        }else{
            detecting=false;
            button.setText("Turn on detection");
            stopService(intent);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
//        SharedPreferences sharedPreferences = getSharedPreferences(CallDetector.MY_PREF,MODE_PRIVATE);
//        String number = sharedPreferences.getString(CallDetector.NUMBER_KEY,"No Value Found");
//        textView.setText(number);
    }
}