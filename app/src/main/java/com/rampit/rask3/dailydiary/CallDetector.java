package com.rampit.rask3.dailydiary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;


//Updated on 25/01/2022 by RAMPIT
//Outgoing and incoming calls ( NOT USED )

public class CallDetector {
    public static final String MY_PREF = "MY_PREF";
    public static final String NUMBER_KEY = "NUMBER_KEY";

    private SharedPreferences sharedPreferences;

    public class OutgoingDetector  extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            if(number.equalsIgnoreCase("555")){
                setResultData(null);
           Intent nn = new Intent(ctx,Splash.class);
           context.startActivity(nn);}
            Log.d("nummm",number);
            sharedPreferences = ctx.getSharedPreferences(MY_PREF,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(NUMBER_KEY,number);
            editor.commit();
        }

    }

    private Context ctx;
    private OutgoingDetector outgoingDetector;

    public CallDetector(Context ctx) {
        this.ctx = ctx;
        outgoingDetector = new OutgoingDetector();
    }

    public void start() {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        ctx.registerReceiver(outgoingDetector, intentFilter);
    }

    public void onDestroy(){
        ctx.unregisterReceiver(outgoingDetector);
    }
}
