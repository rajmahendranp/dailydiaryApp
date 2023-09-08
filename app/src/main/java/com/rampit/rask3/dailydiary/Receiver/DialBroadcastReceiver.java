package com.rampit.rask3.dailydiary.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DialBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("DialB","reccc");
        String incomingNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        Log.d("NewOutgoingCallReceiver",
                ">>>>>> Intent.EXTRA_PHONE_NUMBER: "
                        + incomingNumber);

  if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

            Log.v("DialBroadcast Receiver","Number is: "+number);

        }

    }

}