package com.rampit.rask3.dailydiary;

import android.content.Context;
import android.util.Log;

import java.util.Date;

//Updated on 25/01/2022 by RAMPIT
//Outgoing and incoming calls ( NOT USED )

public class CallReceiver extends PhonecallReceiver {

    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {
        Log.d("phonee","1");
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        Log.d("phonee","2");
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.d("phonee","3");
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.d("phonee","4");
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        Log.d("phonee","5");
    }

}