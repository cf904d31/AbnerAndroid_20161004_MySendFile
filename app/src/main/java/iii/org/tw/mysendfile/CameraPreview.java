package iii.org.tw.mysendfile;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by user on 2016/10/4.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private Camera mcamera;
    private SurfaceHolder holder;

    public CameraPreview(Context context, Camera camera) {
        super(context);

        mcamera = camera;

        holder = getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mcamera.setPreviewDisplay(holder);
            mcamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (holder.getSurface() == null) {
            return;
        }

        try {
            mcamera.stopPreview();
        } catch (Exception e) {

        }

        try {
            mcamera.setPreviewDisplay(holder);
            mcamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mcamera.release();
        mcamera = null;
    }
}
