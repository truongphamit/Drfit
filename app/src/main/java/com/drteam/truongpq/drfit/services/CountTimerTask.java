package com.drteam.truongpq.drfit.services;

import android.util.Log;

import java.util.TimerTask;

/**
 * Created by phamtruong on 10/4/16.
 */

public class CountTimerTask extends TimerTask {
    private long time;
    @Override
    public void run() {
        ++time;
        Log.e("Time", time + "");
    }
}
