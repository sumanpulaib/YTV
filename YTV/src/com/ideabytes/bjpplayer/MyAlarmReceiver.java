package com.ideabytes.bjpplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyAlarmReceiver extends BroadcastReceiver {
	  public static final int REQUEST_CODE = 12345;
	  public static final String ACTION = "com.codepath.example.servicesdemo.alarm";

	  // Triggered by the Alarm periodically (starts the service to run task)
	  @Override
	  public void onReceive(Context context, Intent intent) {
	    Intent i = new Intent(context, MyService.class);
	    CharSequence StreamStatus = intent.getCharSequenceExtra("StreamStatus");
	    CharSequence activityStatus = intent.getCharSequenceExtra("activeStatus");   
	    i.putExtra("StreamStatus", StreamStatus);
	    i.putExtra("activityStatus", activityStatus);
	    Log.d("My Alarm Stream Status", ""+StreamStatus);
	    Log.d("My Alarm active Status", ""+activityStatus);
	    context.startService(i);
	    
	  }
	}
