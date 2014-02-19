
package com.ideabytes.bjpplayer;
 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Locale;
 
import octoshape.al;
import octoshape.mp;
import octoshape.osa2.OctoshapeSystem;
import octoshape.osa2.Problem;
import octoshape.osa2.StreamPlayer;
import octoshape.osa2.listeners.ProblemListener;
import octoshape.osa2.listeners.StreamPlayerListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.octoshape.android.client.MediaPlayerConstants;
import com.octoshape.android.client.OctoshapePortListener;
import com.octoshape.android.client.OctoshapeSystemCreator;
 
 
public class MiniAndroidPlayer extends Activity implements
        OnVideoSizeChangedListener {
 
    private static final String LOGTAG = "OctoAndroidPlayer";
    private static final String OCTOLINK = "octoshape://streams.octoshape.net/ideabytes/live/ib-ch2/2000k";
    
    private SurfaceView mSurface;
    private SurfaceHolder mHolder;
    private OctoshapeSystem os;
    private MediaPlayer mMediaPlayer, player;
    private LinkedList<Uri> urlQueue = new LinkedList<Uri>();
 
    private static String Name="OK";
    private static String datetime=null;
    private static String imei=null;
    static SimpleDateFormat sdf ;
    private static String activityStatus=null;
    private static String InternetStatus=null;
    private static String InternetSpeed=null;
    private static boolean isServiceFound ;
    private static String StreamStatus ;
    private static String active ;
   public  Dialog dialog;
    /**
     * Called when the activity is first created. Creating views, GUI, setting
     * listeners. Here the OSA is initialized.
     */
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
 
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
                 //set the new Content of your activity
                  setContentView(R.layout.video);
                  initOctoshapeSystem();
                  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

         		 StrictMode.setThreadPolicy(policy); 
         		 
                  // Setup views and holder to be used by MediaPlayer
                  mSurface = (SurfaceView) findViewById(R.id.surface);
                  mHolder = mSurface.getHolder();
                  mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
              //scheduleAlarm();
              dialog= new Dialog(MiniAndroidPlayer.this);
              LayoutInflater factory = LayoutInflater.from(MiniAndroidPlayer.this);
              final View view = factory.inflate(R.layout.sample, null);
              dialog.setTitle("Loading Please Wait...");
              dialog.setContentView(view);
              dialog.show();
              WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
              lp.copyFrom(dialog.getWindow().getAttributes());
              lp.width = WindowManager.LayoutParams.MATCH_PARENT;
              lp.height = WindowManager.LayoutParams.MATCH_PARENT;
             dialog.getWindow().setAttributes(lp);
             ConnectivityManager conMgr  = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
             NetworkInfo info = conMgr.getActiveNetworkInfo();
             if(info != null && info.isConnected())
             {
             }
             else
             {
                 new BackgroundSound().execute();
                 
                 
             }

   
    }
 
    /**
     * Creating OctoshapeSystem, adding ProblemListener for which we set to the
     * language of the OS and setting OctoshapePortListener triggering a
     * callback once the Octoshape service/client has started successfully.
     */
    public void initOctoshapeSystem() {
 
        // Create OctoshapeSystem
        os = OctoshapeSystemCreator.create(this, problemListener,
                new OctoshapePortListener() {
                    // called once the Octoshape service/client has started.
                    @Override
                    public void onPortBound(int port) {
                        setupStream(OCTOLINK).requestPlay();
                    }
                }, Locale.ENGLISH.getLanguage());
        // We set ProblemListener with the language the OS is configured with
        os.setProblemMessageLanguage(Locale.ENGLISH.getLanguage());
        os.setProblemListener(problemListener);
 
        // Adding AndroidMediaPlayer
        os.addPlayerNameAndVersion(MediaPlayerConstants.NATIVE_PLAYER, MediaPlayerConstants.NATIVE_PLAYER,
                "" + Build.VERSION.SDK_INT);
        // Launching OctoshapeSystem
        os.open();
        
    }
 
    /**
     * Adding a stream to the OctoMediaPlayer. Creates an StreamPlayer the
     * StreamPlayer instance is initiated with it's own UrlListener and
     * ProblemListener.
     *  
     * @param stream
     *            link (e.g., octoshape://ond.octoshape.com/demo/ios/bbb.mp4)
     * @return StreamPlayer on which we can request playback.
     */
    public StreamPlayer setupStream(final String stream) {
    	 
        Log.d(LOGTAG, "Setting up stream: " + stream);
        StreamPlayer sp = os.createStreamPlayer(stream);

        sp.setProblemListener(new ProblemListener() {
            @Override
            public void gotProblem(Problem p) {
            	Log.e("Video Playing ", "probelm in stream");
            	StreamStatus="0";
            	Log.e("Stream Stattus  ", "Not Ok");
            	active="yellow";
            	 updates();
            	 scheduleAlarm();
            	 ConnectivityManager conMgr  = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                 
            	Thread background = new Thread() {
                    public void run() {
                         
                        try {
                            // Thread will sleep for 5 seconds
                            sleep(1*1000);
                             
                            // After 5 seconds redirect to another intent
                            Intent i=new Intent(getBaseContext(),MiniAndroidPlayer.class);
                            startActivity(i);
                           
                            //Remove activity
                            
                             
                        } catch (Exception e) {
                         
                        }
                    }
                };
                 
                // start thread
                background.start();

                 
                Log.e(LOGTAG, stream+": "+p.getMessage() + " " + p.toString());
               
          	  //new BackgroundSound().execute(); 

                error(stream+": "+p.getMessage() + " " + p.toString());
        }
        });
        sp.setListener(new StreamPlayerListener() {
            private String playerId;
 
            /**
             * Receiving new URL from the streamplayer object either due to
             * requesting playback, seeking or experiencing a bitrate/resolution
             * changes requiring the re-initialization of the Player.
             *  
             * @param url
             *            to be passed to the media player
             * @param seekOffset
             *            offset we have seek to in milliseconds
             * @param playAfterBuffer
             *            if true the URL should be added to a list and played
             *            upon completion of the current URL.
             */
            @Override
            public void gotUrl(String url, long seekOffset,
                    boolean playAfterBuffer) {
                            
                Log.i(LOGTAG, "gotUrl");
                if (playAfterBuffer)
                    urlQueue.add(Uri.parse(url));
                else
                    playStream(Uri.parse(url), playerId);
            }
 
            /**
             * Resets an on-demand file duration previously reported in
             * resolvedOsaSeek(..) method
             */
            @Override
            public void gotNewOnDemandStreamDuration(long duration) {
                // TODO:
            }
 
            /**
             * Called if the stream is seekable using the media player's own
             * native seeking functionality (e.g., the Android MediaPlayer does
             * the seeking for us).
             */
            @Override
            public void resolvedNativeSeek(boolean isLive, String playerId) {
                Log.i(LOGTAG, "resolvedNativeSeek");
                this.playerId = playerId;
            }
 
            /**
             * Called if it is not possible to seek in the stream.
             */
            @Override
            public void resolvedNoSeek(boolean isLive, String playerId) {
                Log.i(LOGTAG, "resolvedNoSeek");
                this.playerId = playerId;
            }
 
            @Override
            /**
             * Called when stream support OsaSeek / DVR
             */
            public void resolvedOsaSeek(boolean isLive, long duration,
                    String playerId) {
                Log.i(LOGTAG, "resolvedOsaSeek");
                this.playerId = playerId;
            }
        });
        //sp.initialize(false);
        return sp;
    }
    /**
     * Setting up and playing a received media URL   
     *  
     * @param mediaUrl URL which needs to be passed to a media player
     * @param playerId the id of the media player which should be used to play
     *             the media Url  
     */
    protected void playStream(Uri mediaUrl, final String playerId) {
    	
        Log.d(LOGTAG, playerId + " now plays: " + mediaUrl);
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDisplay(mHolder);
            mMediaPlayer.setDataSource(this, mediaUrl);
            mMediaPlayer.setOnVideoSizeChangedListener(this);
            
            mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                	 if (!urlQueue.isEmpty())
                        playStream(urlQueue.removeFirst(), playerId);
                  
                }
            });
            mMediaPlayer.setOnErrorListener(new OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    error("MediaPlayer Error: " + what + ":" + extra);
                    return true;
                }
            });
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (Exception e) {
            Log.e(LOGTAG, "Error preparing MediaPlayer", e);
            error("Error preparing MediaPlayer: " + e.getMessage());
        }
    }
 
    /**
     * Handling onVideoSizeChanged called by the MediaPlayer after initiating
     * playback, scales video according to aspect ratio and display dimensions.
     */
    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
    	dialog.dismiss();
    	StreamStatus="Stream Ok";
    	Log.e("Stream Stattus", "Stream Ok");
    	active="true";
    	 updates();
    	scheduleAlarm();
        RelativeLayout.LayoutParams vLayout = (RelativeLayout.LayoutParams) mSurface
                .getLayoutParams();
               mSurface.setLayoutParams(vLayout);
    }
 
        /*float aspectRatio = (float) width / height;
        float screenRatio = (float) vLayout.width / vLayout.height;
        float topMargin = 0, leftMargin = 0;
 
        if (screenRatio < aspectRatio)
            topMargin = (float) vLayout.height
                    - ((float) vLayout.width / aspectRatio);
        else if (screenRatio > aspectRatio)
            leftMargin = (float) vLayout.width - (vLayout.height * aspectRatio);*/
 
        //vLayout.setMargins((int) leftMargin, (int) topMargin, 0, 0);
      
    @Override

public boolean onKeyDown(int keyCode, KeyEvent event)
{
	 if(keyCode == KeyEvent.KEYCODE_BACK)
    {
		 active="yellow";
		dialog.dismiss();
		Log.d(LOGTAG, "Back button pressed!");
        updates();
        shutdown();
    }
    
    
    return super.onKeyDown(keyCode, event);
}
    @SuppressLint("SimpleDateFormat")
    
    public  void updates()
    {
    	//scheduleAlarm();
    	
        Log.d("updates strea statys", ""+StreamStatus);
    	BufferedReader reader=null;
        String text = "";
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
            activityStatus=active;
          // intent.putExtra("activityStatus", isServiceFound);
           Log.i("Mini activityStatus ", ""+activityStatus);
           Log.i("StreamStatus ", ""+StreamStatus);
        
           if(ConnectivitySpeed.isConnectedFast(this)){
                 Log.d("connectivity speed  fast", ""+ConnectivitySpeed.isConnectedFast(this));
                 InternetSpeed="FAST";
                 Log.e("InternetSpeed", ""+InternetSpeed);
                 }
                 else if(ConnectivitySpeed.isConnectedMediumSpeed(this)){
                 Log.d("connectivity speed mediam", ""+ConnectivitySpeed.isConnectedMediumSpeed(this));
                 InternetSpeed="MEDIUM";
                 Log.e("InternetSpeed", ""+InternetSpeed);
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
    public void shutdown(){
        if (mMediaPlayer != null)
            mMediaPlayer.release();
        if (os != null) {
            os.close(new Runnable() {
                @Override
                public void run() {
                    MiniAndroidPlayer.this.finish();
                }
            });
        }
    }
    @Override
    protected void onStop() 
    {
        super.onStop();
        dialog.dismiss();
        Log.d(LOGTAG, "MY onStop is called");
        active="yellow";
        updates();
    	shutdown();
       
    }
    ProblemListener problemListener = new ProblemListener() {
        @Override
        public void gotProblem(Problem p) {
            Log.e(LOGTAG, p.getMessage() + "\n" + p.toString());
            error(p.getMessage());
            new BackgroundSound().execute();
        }
    };
 
    protected void error(final String error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Toast toast = Toast.makeText(MiniAndroidPlayer.this, error,
                        //Toast.LENGTH_LONG);
                //toast.show();
            }
        });
    }
public void scheduleAlarm() {
        
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        intent.putExtra("StreamStatus", StreamStatus);
        intent.putExtra("activeStatus", active);
        sendBroadcast(intent);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
            intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every 5 seconds
        long firstMillis = System.currentTimeMillis(); // first run of alarm is immediate
        int intervalMillis = 30000; // 60 seconds
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, intervalMillis, pIntent);
      }
public class BackgroundSound extends AsyncTask<Void, Void, Void> {
	 
    @Override
    protected Void doInBackground(Void... params) {
        mMediaPlayer = MediaPlayer.create(MiniAndroidPlayer.this, R.raw.test_cbr);
        // Set looping
        mMediaPlayer.setVolume(100,100);
        mMediaPlayer.start();
        mMediaPlayer.setLooping(true);
        return null;
    }
    protected void onPostExecute(Boolean result) {

        
            	mMediaPlayer.stop();
            	mMediaPlayer.release();
            	dialog.dismiss();
            
            
    	}
}//asynk
}//activity
