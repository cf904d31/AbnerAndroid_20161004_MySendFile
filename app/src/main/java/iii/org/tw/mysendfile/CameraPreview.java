package iii.org.tw.mysendfile;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by user on 2016/10/4.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    public CameraPreview(Context context, Camera camera) {
        super(context);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
