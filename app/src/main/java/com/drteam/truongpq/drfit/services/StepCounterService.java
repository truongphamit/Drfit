package com.drteam.truongpq.drfit.services;

/**
 * Created by phamtruong on 9/29/16.
 */

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

import com.drteam.truongpq.drfit.R;
import com.github.lzyzsd.circleprogress.ArcProgress;

import java.util.Timer;

public class StepCounterService extends Service {
    public static int STEP_COUNTER_SERVICE_NOTIFICATIN_ID = 2;

    public Boolean FLAG = false;

    private SensorManager mSensorManager;
    private StepDetector detector;

    private PowerManager mPowerManager;
    private WakeLock mWakeLock;

    private CountTimerTask countTimerTask;

    public Boolean getFLAG() {
        return FLAG;
    }

    public void setFLAG(Boolean FLAG) {
        this.FLAG = FLAG;
    }

    private final IBinder myBinder = new MyLocalBinder();

    public class MyLocalBinder extends Binder {
        public StepCounterService getService() {
            return StepCounterService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return myBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FLAG = true;

        mSensorManager.registerListener(detector,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);

        startCountTimerTask();

        mPowerManager = (PowerManager) this
                .getSystemService(Context.POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP, "S");
        mWakeLock.acquire();

        Notification notification = new Notification.Builder(this)
                .setContentTitle("DrFit")
                .setTicker("DrFit")
                .setContentText("Counting your steps.")
                .setSmallIcon(R.drawable.ic_walk)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .build();
        startForeground(STEP_COUNTER_SERVICE_NOTIFICATIN_ID, notification);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        detector = new StepDetector(this);

        mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
    }

    public void setCallBack(final ArcProgress arcProgress) {
        detector.setCallBack(new StepDetector.CallBack() {
            @Override
            public void execute(int step) {
                arcProgress.setProgress(step);
            }
        });
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        FLAG = false;
        if (detector != null) {
            mSensorManager.unregisterListener(detector);
        }

        if (mWakeLock != null) {
            mWakeLock.release();
        }

        stopCountTimerTask();
    }

    public void startCountTimerTask() {
        if (countTimerTask != null) countTimerTask.cancel();
        countTimerTask = new CountTimerTask();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(countTimerTask, 0, 1000);
    }

    public void stopCountTimerTask() {
        if (countTimerTask != null) countTimerTask.cancel();
    }

    public void stop() {
        FLAG = false;
        if (detector != null) {
            mSensorManager.unregisterListener(detector);
        }
    }

    public void resume() {

    }
}
