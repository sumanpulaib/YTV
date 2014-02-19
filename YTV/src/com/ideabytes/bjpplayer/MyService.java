package com.ideabytes.bjpplayer;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;

import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MyService extends Service {
	private static final String TAG = "MyService";
	private static final String MainDirectory=Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.ideabytes.bjpplayer/IBPlayer";
	private static final String FilePath=MainDirectory+"/Status.txt";
	
	private static  String path;
	private static String Name="OK";
	private String datetime=null;
	private String imei=null;
	SimpleDateFormat sdf ;
	private static final int delay = 1000; // delay for 1 sec before first start
	private static final int period = 20000; // repeat check every 10 sec.
	private Timer timer;
	private static boolean ReadisServiceFound ;
	private static boolean ReadisConnected ;
	private static boolean isServiceFound ;
	private static boolean isStreamFound ;
	private static boolean isConnected ;
	private static String activityStatus=null;
	private static String InternetStatus=null;
	private static String InternetSpeed=null;
	public  Dialog dialog;
	
	public MyService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
    public void onCreate() {
        //Toast.makeText(this, "The new Service was Created", Toast.LENGTH_LONG).show();
		 Log.i(TAG, "new Service created");
		 StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		 StrictMode.setThreadPolicy(policy); 
		 
		 }
	
  @SuppressLint("SimpleDateFormat")
	@Override
    public void onStart(Intent intent, int startId) {
	  String StreamStatus=intent.getStringExtra("StreamStatus");
	  String a=intent.getStringExtra("activityStatus");
    	 Log.d(TAG, "service on start");
    	//Getting activity forground info
    	 ActivityManager activityManager = (ActivityManager)this.getSystemService (Context.ACTIVITY_SERVICE); 
		    List<RunningTaskInfo> services = activityManager.getRunningTasks(Integer.MAX_VALUE); 
		    isServiceFound = false;
		    
		    isStreamFound=false;
		    for (int i = 0; i < services.size(); i++) { 
		        if (services.get(i).topActivity.toString().equalsIgnoreCase("ComponentInfo{com.ideabytes.bjpplayer/com.ideabytes.bjpplayer.MiniAndroidPlayer}")) {
		        	 Log.i("my serivce ", "activity in foreground");
		            isServiceFound = true;
		            
		        }
		      } 
		    
		    isConnected=isConnectingToInternet();
		    //Writing status activity and connection to file
	        
	        CreateIBPlayerDirectoryIfRequired();
	        Log.d("Folder Path", ""+path);
			 WriteStatusFile(isServiceFound,isConnected);
			 try {
				LoadData();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
	//Auto R-Launch
    	 if(ReadisServiceFound){
    		 Log.i(TAG, "in if");	 
    	 }
    	 else{
    		 Intent i = new Intent(MyService.this,MiniAndroidPlayer.class);
  		   
    		 i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
    		 startActivity(i);
    	 Log.i(TAG, " in else called MiniFlashPlayer");	 
  }
    	 
 //Getting Connectivity Speed
  if(ConnectivitySpeed.isConnectedFast(this)){
  Log.d("connectivity speed  fast", ""+ConnectivitySpeed.isConnectedFast(this));
  InternetSpeed="FAST";
  Log.e("InternetSpeed", InternetSpeed);
  }
  else if(ConnectivitySpeed.isConnectedMediumSpeed(this)){
  Log.d("connectivity speed mediam", ""+ConnectivitySpeed.isConnectedMediumSpeed(this));
  InternetSpeed="MEDIUM";
  Log.e("InternetSpeed", InternetSpeed);
  }
  else if(ConnectivitySpeed.isConnectedSlow(this)){
  Log.d("connectivity speed slow", ""+ConnectivitySpeed.isConnectedSlow(this));
  InternetSpeed="SLOW";
  Log.e("InternetSpeed", InternetSpeed);
  }
  else if(ConnectivitySpeed.isConnectedVeryFast(this)){
  Log.d("connectivity speed very fast", ""+ConnectivitySpeed.isConnectedVeryFast(this));
  InternetSpeed="VERY FAST";
  Log.e("InternetSpeed", InternetSpeed);
  }
  else{
  Log.d("connectivity speed very slow", ""+ConnectivitySpeed.isConnectedVerySlow(this));
  InternetSpeed="VERY SLOW";
  Log.e("InternetSpeed", InternetSpeed);
  }
    	
    	//to  get current date time
    	Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());
         sdf = new SimpleDateFormat("yyyy-MM-dd h:mm:ss a");
        datetime = sdf.format(c.getTime());
        Log.i("Current Date Time", ""+datetime);
       
       //to get imei number of device 
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
     imei = telephonyManager.getDeviceId();
        Log.i("IMEI number", ""+telephonyManager.getDeviceId());
        String text = "";
        BufferedReader reader=null;
        activityStatus=a;
       // intent.putExtra("activityStatus", isServiceFound);
        Log.i("my serivce activityStatus ", ""+activityStatus);
      //network status connected or not 
     final boolean netStatus=isConnectingToInternet();
     Log.i("my serivce netStatus ", ""+netStatus);
     InternetStatus=String.valueOf(netStatus);
        // Send data
      try
      {
    	  String data = URLEncoder.encode("name", "UTF-8")
		             + "=" + URLEncoder.encode(Name, "UTF-8");

          data += "&" + URLEncoder.encode("datetime", "UTF-8") + "="
                      + URLEncoder.encode(datetime, "UTF-8");
          data += "&" + URLEncoder.encode("imei", "UTF-8") + "="
                  + URLEncoder.encode(imei, "UTF-8");
          data += "&" + URLEncoder.encode("activityStatus", "UTF-8") + "="
                 + URLEncoder.encode(activityStatus, "UTF-8");
          data += "&" + URLEncoder.encode("InternetSpeed", "UTF-8") + "="
                  + URLEncoder.encode(InternetSpeed, "UTF-8");
          data += "&" + URLEncoder.encode("StreamStatus", "UTF-8") + "="
                  + URLEncoder.encode(StreamStatus, "UTF-8");
          // Defined URL  where to send data
          URL url = new URL("http://183.82.9.60/ytv/httppost1.php");
       // Send POST data request

        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write( data );
        wr.flush();
    
        // Get the server response
         
      reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      StringBuilder sb = new StringBuilder();
      String line = null;
      
      // Read Server Response
      while((line = reader.readLine()) != null)
          {
                 // Append server response in string
                 sb.append(line + "\n");
          }
          
          
          text = sb.toString();
          Log.i("Server Connection", text);
          try
          {
          if(text!=null){
         // Toast.makeText(this, text, Toast.LENGTH_LONG).show();
          }
          
          }
          catch(Exception e)
          {
        	  Toast.makeText(this, "No Server Connection", Toast.LENGTH_LONG).show();    
          }
      }
      catch(Exception ex)
      {
         Log.e("error",""+ex.toString());  
      }
      finally
      {
          try
          {

              reader.close();
          }

          catch(Exception ex) {}
      }
      
    }
/*	@Override
	public void onStart(Intent intent, int startId) {
		handleCommand(intent);
		
	}
 // To make this Service work in pre Level 5 APIs just remove this method
    @SuppressLint("SimpleDateFormat")
    public int onStartCommand(Intent intent, int flags, int startId) {
    	
    	
    	// For time consuming an long tasks you can launch a new thread here...
      //  Toast.makeText(this, " Service Started", Toast.LENGTH_LONG).show();
     // Create data variable for sent values to server
    	//to  get current date time
    	Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());
         sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
        datetime = sdf.format(c.getTime());
        Log.i("Current Date Time", ""+datetime);
        
       //to get imei number of device 
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
     imei = telephonyManager.getDeviceId();
        Log.i("IMEI number", ""+telephonyManager.getDeviceId());
        String text = "";
        BufferedReader reader=null;

        // Send data
      try
      {
    	  String data = URLEncoder.encode("name", "UTF-8")
		             + "=" + URLEncoder.encode(Name, "UTF-8");

          data += "&" + URLEncoder.encode("datetime", "UTF-8") + "="
                      + URLEncoder.encode(datetime, "UTF-8");
          data += "&" + URLEncoder.encode("imei", "UTF-8") + "="
                  + URLEncoder.encode(imei, "UTF-8");
          data += "&" + URLEncoder.encode("activityStatus", "UTF-8") + "="
                  + URLEncoder.encode(activityStatus, "UTF-8");
          // Defined URL  where to send data
          URL url = new URL("http://183.82.9.60/food/httppost1.php");
       // Send POST data request

        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write( data );
        wr.flush();
    
        // Get the server response
         
      reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      StringBuilder sb = new StringBuilder();
      String line = null;
      
      // Read Server Response
      while((line = reader.readLine()) != null)
          {
                 // Append server response in string
                 sb.append(line + "\n");
          }
          
          
          text = sb.toString();
          Log.i("Server Connection", text);
          try
          {
          if(text!=null){
         // Toast.makeText(this, text, Toast.LENGTH_LONG).show();
          }
          
          }
          catch(Exception e)
          {
        	  Toast.makeText(this, "No Server Connection", Toast.LENGTH_LONG).show();    
          }
      }
      catch(Exception ex)
      {
         Log.e("error",""+ex.toString());  
      }
      finally
      {
          try
          {

              reader.close();
          }

          catch(Exception ex) {}
      }
     
   handleCommand(intent);
    return START_STICKY;
    }
     
    // handles a Start command
    private void handleCommand(Intent intent) {
    Log.d(TAG, "service is starting");
     
    if (timer == null) {
    timer = new Timer();
    timer.schedule(new TimerTask() {
    public void run() {
    checkActivityForeground();
    }
    }, delay, period);
    }
    }
     
    protected void checkActivityForeground() {
    Log.d(TAG, "start checking for Activity in foreground");
    Intent intent = new Intent();
    intent.setAction(MiniFlashPlayer.UE_ACTION);
    sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
     
    @Override
    public void onReceive(Context context, Intent intent) {
    int result = getResultCode();
     
    if (result != Activity.RESULT_CANCELED) { // Activity caught it
    Log.d(TAG, "An activity caught the broadcast, result " + result);
    activityInForeground();
    Intent i=new Intent(context,MyService.class);
    intent.putExtra("status",activityStatus);
    Log.i("My Service1 status", ""+status);
    context.startService(i);
    return;
    }
    Log.d(TAG, "No activity did catch the broadcast.");
    Intent i=new Intent(context,MyService.class);
    intent.putExtra("status",activityStatus);
    Log.i("My Service1 status", ""+status);
    context.startService(i);
    noActivityInForeground();
    }
    }, null, Activity.RESULT_CANCELED, null, null);
    }
     
    protected void activityInForeground() {
    	// TODO something you want to happen when an Activity is in the foreground
    Log.d(TAG, "starting method which gets called when an SureveillanceActivity is in foreground");
     status=true;
     activityStatus=String.valueOf(status);
    Log.i(TAG+"  activityStatus", activityStatus);

    }
     
    protected void noActivityInForeground() {
    	// TODO something you want to happen when no Activity is in the foreground
    Log.d(TAG, "not in foreground");
     status=false;
     activityStatus=String.valueOf(status);
     Log.i(TAG+"  activityStatus", activityStatus);
    stopSelf(); // quit
    }*/
    @Override
    public void onDestroy() {
      //  Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
    	 Log.i(TAG, "service Stopped");
    }
    public static boolean WriteStatusFile(boolean Astatus,boolean Cstatus) {
    	try {
    		FileWriter outFile = new FileWriter(FilePath);			
    		PrintWriter out = new PrintWriter(outFile);
    		out.println("activityStatus="+Astatus);
    		out.println("connectivityStatus="+Cstatus);
    		out.flush();
    		out.close();
    	} 
    	catch (Exception e) {
    		e.printStackTrace();
    		return false;
        }
    	return true;
    }
    public static boolean CreateIBPlayerDirectoryIfRequired() {
    	File directory = new File(MainDirectory);
    	path=directory.getAbsolutePath();
    	if (!directory.isDirectory()) {
    		if (!directory.mkdirs()) {
    			return false;
    		}
    	}
    	return true;
    }
    /**
     * Checking for all possible internet providers
     * **/
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null)
                  for (int i = 0; i < info.length; i++)
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }

          }
          return false;
    }
    public static void LoadData() throws FileNotFoundException {
        String property = null;
		String value = null;
		
			BufferedReader input;
			
				input = new BufferedReader(new FileReader(FilePath));
			
			try {
			    String line = null; //not declared within while loop
			    while (( line = input.readLine()) != null) {
			    	String[] x = line.split("=");
			    	property = x[0];
			    	value = x[1];
			    	if (property.equals("activityStatus")) {
			    		ReadisServiceFound=Boolean.parseBoolean(value);
			    		Log.i("data read ReadisServiceFound=", ""+ReadisServiceFound);
			   		
			    	}
			    	else if (property.equals("connectivityStatus")) {
			    		ReadisConnected=Boolean.parseBoolean(value);
			    		 Log.i("data read ReadisConnected=", ""+ReadisConnected);
			    	}
			    }}
			    	catch (Exception e) {
						e.printStackTrace();
					}
    }
   	

	}
