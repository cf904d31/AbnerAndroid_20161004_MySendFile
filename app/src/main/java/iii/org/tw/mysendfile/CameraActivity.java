package iii.org.tw.mysendfile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

public class CameraActivity extends AppCompatActivity {
    private Camera camera;
    private FrameLayout fram;
    private CameraPreview preview;

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

        //-----設定
        List<Camera.Size> sizes = params.getSupportedPictureSizes();
        for (Camera.Size size :sizes){
            Log.d("Abner", size.width + "x" +size.height);
        }

        params.setPictureSize(160,120);
        camera.setParameters(params);




        fram = (FrameLayout) findViewById(R.id.fram);
        fram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(new shutter(),null,new MyJPEGCallBack());
            }
        });
        preview = new CameraPreview(this,camera);
        fram.addView(preview);

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


    private class shutter implements Camera.ShutterCallback {
        @Override
        public void onShutter() {
            //-----當按下快門時發生的事情
            Log.d("Abner","按下快門");
        }
    }

    private class MyJPEGCallBack implements Camera.PictureCallback {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //-----取得照片後要做的事情
            Intent it = new Intent();
            it.putExtra("pic",data);
            setResult(1,it);
            finish();
        }
    }

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
