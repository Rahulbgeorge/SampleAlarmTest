package com.example.rahul.alarm;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static String TAG="MAINACTIVITY";
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView ab=findViewById(R.id.textView);
        ListView lv=findViewById(R.id.lv);
        Object obj[]=new DatabaseHelper(this).displayData();
        String[] stringArray = Arrays.copyOf(obj, obj.length, String[].class);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);

        lv.setAdapter(adapter);
        ab.setText(getSharedPreferences("alert",MODE_PRIVATE).getInt("iteration",0)+"");
    }

    public void setAlarm(View view)
    {
        ComponentName componentName = new ComponentName(this, AlertService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
//                .setMinimumLatency(1000*60)
                .setPeriodic(15*60*1000)
                .build();



        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.e(TAG, "Job scheduled");
        } else {
            Log.e(TAG, "Job scheduling failed");
        }


    }


    public void endAlarm(View view)
    {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.e(TAG, "Job cancelled");
    }

    public void chargerService(View view)
    {

    }
}


