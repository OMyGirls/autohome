package cn.lwtAR;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import cn.lwtAR.view.CircleImageView;
import cn.sharerec.recorder.Recorder;
import cn.sharerec.recorder.impl.SystemRecorder;

public class UnityActivity extends BaseActivity {
    protected  static UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code
    public static Activity activity= null ;
    private FrameLayout fl_unity_view;
    private ImageView iv_back;
    private LinearLayout ll_photos;
    private ImageView iv_camera;
    private ImageView iv_scaning;
    private CircleImageView iv_photos;
    private FrameLayout fl_recording;
    private ImageView iv_record_stop;
    private ProgressBar progressbar_record;
    private ImageView splash_view;
    private ImageView iv_bg_alpha;

    private LinearLayout tab_bar;

    private LinearLayout iv_record;
    private boolean flag = true ;

    private static SystemRecorder recorder;

    private static Boolean firstEnter = true;

    // Setup activity layout
    @Override protected void onCreate (Bundle savedInstanceState)
    {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        //getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy
        setContentView(R.layout.unity_view_scan_content_camera);

        activity = this;
        fl_unity_view = (FrameLayout) findViewById(R.id.fl_unity_view);
        iv_scaning = (ImageView) findViewById(R.id.iv_scaning);
        iv_record = (LinearLayout) findViewById(R.id.iv_record);
        fl_recording = (FrameLayout) findViewById(R.id.fl_recording);
        iv_record_stop  = (ImageView) findViewById(R.id.iv_record_stop);
        splash_view = (ImageView) findViewById(R.id.splash_view);
        iv_bg_alpha = (ImageView) findViewById(R.id.iv_bg_alpha);
        iv_record_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!enableStop){
                    Toast.makeText(UnityActivity.this,"视频需大于3秒！",Toast.LENGTH_SHORT).show();
                    return;
                }
                stopRecording();
            }
        });
        progressbar_record = (ProgressBar) findViewById(R.id.progressbar_record);
        iv_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fl_recording.setVisibility(View.VISIBLE);
                tab_bar.setVisibility(View.GONE);
                UnityPlayer.UnitySendMessage("ShareRECControl","PlayVideo","");//开始录屏
                startTimer();
               // startRecorder();

            /*    if (flag){
                    UnityPlayer.UnitySendMessage("ShareRECControl","PlayVideo","");//开始录屏
                    flag = false;
                }
                else {
                    UnityPlayer.UnitySendMessage("ShareRECControl", "StoreVideo", "");//停止录屏
                    flag = true;
                }
                //iv_scaning.clearAnimation();*/


            }
        });
        iv_photos = (CircleImageView) findViewById(R.id.iv_photos);
        //iv_photos.setImageBitmap(BitmapFactory.decodeFile("/sdcard/Android/data/cn.lwtAR/files/image.png"));


        mUnityPlayer = new UnityPlayer(this);

        fl_unity_view.addView(mUnityPlayer);
        mUnityPlayer.requestFocus();

        init();
    }
    private void stopRecording(){
        fl_recording.setVisibility(View.GONE);
        tab_bar.setVisibility(View.VISIBLE);
        UnityPlayer.UnitySendMessage("ShareRECControl", "StoreVideo", "");//停止录屏
        // stopRecorder();
        resetRecordState();
    }

    private Timer timer;
    private int count;
    private int minRecordTIime = 3000;
    private int maxRecordTIime = 12000;
    private Boolean enableStop = false;
    private Boolean recording = false;
        private void startTimer() {
            recording = true;
            cancelTimer();
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    count += 1;
                    float recordTimes  =  count;
                    progressbar_record.setProgress((int) ((recordTimes/maxRecordTIime) * progressbar_record.getMax()));

                    enableStop = recordTimes >= minRecordTIime;

                    if(recordTimes >= maxRecordTIime){
                        cancelTimer();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stopRecording();
                            }
                        });
                    }
                }
            }, 1, 1);
        }
    private  void resetRecordState(){
        count = 0;
        recording = false;
        cancelTimer();
    }
    private  void cancelTimer(){
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void startRecorder() {
        if (recorder == null) {
            String appkey = "1899f2e892777";
            String appsecret = "2dc09694fffef4d36ebd5827ff51838d";
            recorder = new SystemRecorder(this, appkey, appsecret);
        }
        recorder.setMaxFrameSize(Recorder.LevelMaxFrameSize.LEVEL_1280_720);
        recorder.setVideoQuality(Recorder.LevelVideoQuality.LEVEL_HIGH);
        recorder.setMinDuration(10 * 1000);
        //recorder.setCacheFolder(data.getStringExtra("srec_key_cacheFolder"));
        recorder.setCacheFolder("/sdcard/ShareREC/cvr");
        if (recorder.isAvailable()) {
            Toast.makeText(UnityActivity.this,"开始录像2",Toast.LENGTH_SHORT).show();
            recorder.start();
        }
    }
    private void stopRecorder() {
        if (recorder != null) {
            recorder.stop();
            recorder = null;
        }
    }

    private void init(){
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UnityActivity.this , HomeActivity.class);
                startActivity(intent);

                    //UnityPlayer.UnitySendMessage("callUnity","EndScanAR","");
                    //UnityPlayer.UnitySendMessage("ShareRECControl","PlayVideo","");
                    /* flag = false;
                   if (flag){  }else {

                    UnityPlayer.UnitySendMessage("CaptureCamera","StoreVideo","........................");
                    flag = true;
                }*/
                //mUnityPlayer.clearFocus();
                /*mUnityPlayer.pause();
                UnityPlayer.UnitySendMessage("CaptureCamera","TakePhto","........................");
                fl_unity_view.removeAllViews();*/
              // mUnityPlayer.quit();
                //fl_unity_view.removeAllViews();
            }
        });
        ll_photos = (LinearLayout) findViewById(R.id.ll_photos);
        ll_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UnityActivity.this , PhotoActivity.class);
                startActivity(intent);
               // UnityPlayer.UnitySendMessage("ShareRECControl","StoreVideo","");
                //UnityPlayer.UnitySendMessage("CaptureCamera","TakePhto","........................");
            }
        });

        iv_camera = (ImageView) findViewById(R.id.iv_camera);
        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UnityPlayer.UnitySendMessage("CaptureCamera","TakePhto","........................");
            }
        });

        tab_bar = (LinearLayout) findViewById(R.id.tab_bar);
        ConnectUnityToAndroid.setStartedCallBack(new ConnectUnityToAndroid.StartedCallBack() {
            @Override
            public void callBabk() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(UnityActivity.this , HomeActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });
        ConnectUnityToAndroid.setScanCallBack(new ConnectUnityToAndroid.ScanCallBack() {
            @Override
            public void callBabk() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("yuan","callBabk()");
                        if(!recording) {
                            tab_bar.setVisibility(View.VISIBLE);
                            iv_scaning.clearAnimation();
                            iv_scaning.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });
        ConnectUnityToAndroid.setTakePhotosCallBack(new ConnectUnityToAndroid.TakePhotosCallBack() {
            @Override
            public void callBabk() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iv_photos.setImageBitmap(BitmapFactory.decodeFile("/sdcard/Android/data/cn.lwtAR/files/image.png"));
                    }
                });
               // iv_photos.setImageBitmap(BitmapFactory.decodeFile("/sdcard/Android/data/cn.lwtAR/files/image.png"));
            }
        });




    }

    // Quit Unity
    @Override protected void onDestroy ()
    {
        mUnityPlayer.quit();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("yuan","onStart");
        overridePendingTransition(R.anim.anim_activity_open_enter, R.anim.anim_activity_open_exit);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("yuan","onRestart");
       /* splash_view.setVisibility(View.GONE);
        new Thread(new Runnable(){
            public void run(){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iv_bg_alpha.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
        overridePendingTransition(R.anim.anim_activity_open_enter, R.anim.anim_activity_open_exit);*/
    }

    // Pause Unity
    @Override protected void onPause()
    {
        firstEnter = false;
        UnityPlayer.UnitySendMessage("DiaoYong", "stopTarget", "");//停止录屏
        super.onPause();
        mUnityPlayer.pause();

        overridePendingTransition(R.anim.anim_activity_close_enter, R.anim.anim_activity_close_exit);
    }

    // Resume Unity
    @Override protected void onResume()
    {
        Log.e("yuan","onResume");
        /**/if (!firstEnter){
            splash_view.setVisibility(View.GONE);
            new Thread(new Runnable(){
                    public void run(){
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iv_bg_alpha.setVisibility(View.GONE);
                            }
                        });
                    }
                 }).start();
        }
        UnityPlayer.UnitySendMessage("DiaoYong", "startTarget", "");//开始录屏
        iv_scaning.setVisibility(View.VISIBLE);
        tab_bar.setVisibility(View.GONE);
        fl_recording.setVisibility(View.GONE);
        resetRecordState();
        final AnimationSet aset_4=new AnimationSet(true);
        TranslateAnimation aa_4=new TranslateAnimation(Animation.RELATIVE_TO_PARENT,0,Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 1);
        aa_4.setDuration(6000);
        aset_4.addAnimation(aa_4);
        aa_4.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv_scaning.startAnimation(aset_4);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        iv_scaning.startAnimation(aset_4);
        super.onResume();
        mUnityPlayer.resume();
    }

    // This ensures the layout will be correct.
    @Override public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // Notify Unity of the focus change.
    @Override public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override public boolean onKeyUp(int keyCode, KeyEvent event)     { return mUnityPlayer.injectEvent(event); }
    @Override public boolean onKeyDown(int keyCode, KeyEvent event)   { return mUnityPlayer.injectEvent(event); }
    @Override public boolean onTouchEvent(MotionEvent event)          { return mUnityPlayer.injectEvent(event); }
    /*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }
}
