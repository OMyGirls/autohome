package cn.lwtAR;

import android.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;

import java.io.File;

/**
 * Created by Administrator on 2016/11/11.
 */

public class ConnectUnityToAndroid {

    public static StartedCallBack startedCallBack;
    public static ScanCallBack scanCallBack;
    public static TakePhotosCallBack takePhotosCallBack;

    public static StartedCallBack getStartedCallBack() {
        return startedCallBack;
    }

    public static void setStartedCallBack(StartedCallBack startedCallBack) {
        ConnectUnityToAndroid.startedCallBack = startedCallBack;
    }

    public static ScanCallBack getScanCallBack() {
        return scanCallBack;
    }

    public static void setScanCallBack(ScanCallBack scanCallBack) {
        ConnectUnityToAndroid.scanCallBack = scanCallBack;
    }

    public static TakePhotosCallBack getTakePhotosCallBack() {
        return takePhotosCallBack;
    }

    public static void setTakePhotosCallBack(TakePhotosCallBack takePhotosCallBack) {
        ConnectUnityToAndroid.takePhotosCallBack = takePhotosCallBack;
    }

    public static void takePhotosSucess(String s){
        /*AlertDialog.Builder builder = new AlertDialog.Builder(UnityPlayer.currentActivity)
                .setMessage("拍照成功！")
                .setPositiveButton("确定", null);
        builder.show();*/
        if(takePhotosCallBack!=null){
            takePhotosCallBack.callBabk();
        }
        File file = new File("/sdcard/Android/data/cn.lwtAR/files/image.png");
//newPath like "mnt/sda/sda1/我的照片.png"
        file.renameTo(new File("/sdcard/Android/data/cn.lwtAR/files/"+System.currentTimeMillis()+".png"));
    }
    public static void scanSucess(){
       /* AlertDialog.Builder builder = new AlertDialog.Builder(UnityPlayer.currentActivity)
                .setMessage("扫描成功！")
                .setPositiveButton("确定",null);
        builder.show();*/
        Log.e("yuan",""+(scanCallBack!=null));
        if(scanCallBack!=null){
            scanCallBack.callBabk();
        }
    }
    public static void unityStarted(){
       /* AlertDialog.Builder builder = new AlertDialog.Builder(UnityPlayer.currentActivity)
                .setMessage("启动成功")
                .setPositiveButton("确定",null);
        builder.show();*/
       /* try {
            Log.e("yuan","scanCallBack--sleep");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        if(startedCallBack!=null){
            startedCallBack.callBabk();
    }
    }
    public static void recordSucess(String s){
        AlertDialog.Builder builder = new AlertDialog.Builder(UnityPlayer.currentActivity)
                .setMessage(s)
                .setPositiveButton("确定",null);
        builder.show();
    }
    interface  StartedCallBack{
        void callBabk();
    }
    interface  ScanCallBack{
        void callBabk();
    }
    interface  TakePhotosCallBack{
        void callBabk();
    }
}
