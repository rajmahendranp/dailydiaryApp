package com.rampit.rask3.dailydiary;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;


//Updated on 25/01/2022 by RAMPIT
//Outgoing and incoming calls ( NOT USED )

public class CallDetectionService extends Service {

    private CallDetector callDetector;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        callDetector = new CallDetector(this);
        int r = super.onStartCommand(intent, flags, startId);
        callDetector.start();
        return r;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        callDetector = new CallDetector(this);
//        callDetector.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}