package com.example.bongfeldt.activityapp;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Environment;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class ARS extends IntentService {

    public ARS() {
        super("ActivityRecognizedService");
    }

    public ARS(String name) {
        super(name);
    }

    private void handleDetectedActivities(List<DetectedActivity> probableActivities) {

        for( DetectedActivity activity : probableActivities ) {
            // is phone is still, check if we are charing
            String text = "";
            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();
            switch( activity.getType() ) {
                case DetectedActivity.IN_VEHICLE: {
                    Log.e( "ActivityRecogition", "In Vehicle: " + activity.getConfidence() );
                    text = "In Vehicle: " + activity.getConfidence();
                    break;
                }
                case DetectedActivity.ON_BICYCLE: {
                    Log.e( "ActivityRecogition", "On Bicycle: " + activity.getConfidence() );
                    text = "On Bicycle: " + activity.getConfidence();
                    break;
                }
                case DetectedActivity.ON_FOOT: {
                    Log.e( "ActivityRecogition", "On Foot: " + activity.getConfidence() );
                    text = "On Foot: " + activity.getConfidence();
                    break;
                }
                case DetectedActivity.RUNNING: {
                    Log.e( "ActivityRecogition", "Running: " + activity.getConfidence() );
                    text = "Running: " + activity.getConfidence();
                    break;
                }
                case DetectedActivity.STILL: {
                    Log.e( "ActivityRecogition", "Still: " + activity.getConfidence() );
                    text = "Still: " + activity.getConfidence();
                    break;
                }
                case DetectedActivity.TILTING: {
                    Log.e( "ActivityRecogition", "Tilting: " + activity.getConfidence() );
                    text = "Tilting: " + activity.getConfidence();
                    break;
                }
                case DetectedActivity.WALKING: {
                    Log.e( "ActivityRecogition", "Walking: " + activity.getConfidence() );
                    text = "Walking: " + activity.getConfidence();
                    break;
                }
                case DetectedActivity.UNKNOWN: {
                    Log.e( "ActivityRecogition", "Unknown: " + activity.getConfidence() );
                    text = "Unknown: " + activity.getConfidence();
                    break;
                }
            }
            text += " Time Stamp: " + ts;
            appendLog(text);
        }
    }

    public void appendLog(String text)
    {
        Log.e("appendLog", "appendLog");
        String test = String.valueOf(Environment.getExternalStorageDirectory());
        Log.e("appendLog", test);
        File logFile = new File(Environment.getExternalStorageDirectory(), "Jens");

        if (!logFile.exists())
        {
            Log.e("appendLog", "logFile exists");
            try
            {
                logFile.createNewFile();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                Log.e("appendLog", "catch");
                e.printStackTrace();
            }
        }
        try
        {
            Log.e("appendLog", "append");
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            Log.e("appendLog", "catch");
            e.printStackTrace();
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities( result.getProbableActivities() );
        }
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i("JensARS", "onCreate");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i("JensARS", "onDestroy");
    }
}
