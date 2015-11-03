package com.pszukala.colourcamera;

import android.content.Context;
import android.hardware.Camera;
import android.graphics.*;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import java.io.*;
import java.util.List;

/**
 * Created by Pszukala on 2015-10-19.
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {

    private SurfaceHolder mHolder;
    private Camera mCamera;
    private List<Camera.Size> mSupportedPreviewSizes;
    private Camera.Size mPreviewSize;
    private int crosshairSize;

    public CameraView(Context context, Camera camera){
        super(context);

        mCamera = camera;
        mCamera.setDisplayOrientation(90);

        mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
        //get the holder and set this class as the callback, so we can get camera data here
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
    }

    public void SetCrosshairSize(int size) {
        crosshairSize = size;
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio=(double)h / w;

        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);

        if (mSupportedPreviewSizes != null) {
            mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width, height);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try{
            //when the surface is created, we can set the camera to draw images in this surfaceholder
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.setPreviewCallback(this);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d("ERROR", "Camera error on surfaceCreated " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        //before changing the application orientation, you need to stop the preview, rotate and then start it again
        if(mHolder.getSurface() == null)//check if the surface is ready to receive camera data
            return;

        try{
            mCamera.stopPreview();
        } catch (Exception e){
            //this will happen when you are trying the camera if it's not running
        }

        //recreate the camera preview
        try{
            Camera.Parameters params = mCamera.getParameters();
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            params.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
            mCamera.setParameters(params);
            mCamera.setPreviewCallback(this);
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d("ERROR", "Camera error on surfaceChanged " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mCamera.stopPreview();
        mCamera.release();
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
<<<<<<< HEAD

=======
        if(crosshairSize > 0) {
            Camera.Parameters parameters = camera.getParameters();
            int width = parameters.getPreviewSize().width;
            int height = parameters.getPreviewSize().height;

            YuvImage yuv = new YuvImage(data, parameters.getPreviewFormat(), width, height, null);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            yuv.compressToJpeg(new Rect(0, 0, width, height), 50, out);

            byte[] bytes = out.toByteArray();
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
>>>>>>> origin/master
    }
}
