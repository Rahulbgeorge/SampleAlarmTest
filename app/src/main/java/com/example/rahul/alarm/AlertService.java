package com.example.rahul.alarm;

import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.util.Log;

public class AlertService extends JobService {
    private static final String TAG = "ExampleJobService";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");
        //THIS IS WHERE WE DO
        doBackgroundWork(params);

        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Log.e(TAG, "run: " + i);
                    if (jobCancelled) {
                        return;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                new DatabaseHelper(getBaseContext()).pushDate();
                SharedPreferences obb=getSharedPreferences("alert",MODE_PRIVATE);
                SharedPreferences.Editor editor=obb.edit();
                editor.putInt("iteration",obb.getInt("iteration",0)+1);
                editor.apply();
                Log.e(TAG, "Job finished");
                jobFinished(params, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }
}
