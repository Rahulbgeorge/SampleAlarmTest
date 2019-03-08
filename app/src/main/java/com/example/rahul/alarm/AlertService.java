package com.example.rahul.alarm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class AlertService extends JobService {
    private static final String TAG = "AlertService";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");
        //THIS IS WHERE WE DO
        doBackgroundWork(params);

        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        Log.e(TAG, "doBackgroundWork: before triggering" );
        handleNow("something triggered");
        Log.e(TAG, "doBackgroundWork: after triggering" );

       /* new Thread(new Runnable() {
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
        }).start();*/
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }

    public void handleNow(final String message)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "5431";
            String description = "Random channel man";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("5431", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "5431")
                .setSmallIcon(R.drawable.poster2)
                .setContentTitle("Golden Night Discount!!")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(3378, mBuilder.build());

    }
}
