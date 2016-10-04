package iii.org.tw.mysendfile;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class CameraActivity extends AppCompatActivity {
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        CheckPermission(Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int num = Camera.getNumberOfCameras();
        Log.d("Abner","Camera : " +num);

        //-----取得Camera物件實體
        camera = Camera.open();
        Camera.Parameters params = camera.getParameters();

//        //-----底下兩行搭配下面兩個class使用
//        camera.takePicture(new shutter(),null,new MyJPEGCallBack());
//        camera.release();

    }

//    private class shutter implements Camera.ShutterCallback {
//        @Override
//        public void onShutter() {
//            //-----當按下快門時發生的事情
//        }
//    }
//
//    private class MyJPEGCallBack implements Camera.PictureCallback {
//        @Override
//        public void onPictureTaken(byte[] data, Camera camera) {
//            //-----取得照片後要做的事情
//        }
//    }

    private int checkCameraNumber(){
        //-----因為在Manifest檔裡面已經有宣告沒有相機的手機無法下載此程式
        //-----所以這行可以不必加上去判斷
//        PackageManager packageManager = getPackageManager();
//        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
//
//        }
        return Camera.getNumberOfCameras();


    }


    //-----寫一個方法可以讓6.0以上的機種逐一允許危險權限
    private void CheckPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{permission},1);
            }
        }
    }
}
