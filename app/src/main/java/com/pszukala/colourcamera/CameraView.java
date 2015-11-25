package com.pszukala.colourcamera;

import android.content.Context;
import android.hardware.Camera;
import android.graphics.*;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.*;
import java.util.List;

/**
 * Created by Pszukala on 2015-10-19.
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {

    private SurfaceHolder mHolder;
    private ImageView colorBox;
    private TextView rgbText, rgbName;
    private Camera mCamera;
    private List<Camera.Size> mSupportedPreviewSizes;
    private Camera.Size mPreviewSize;
    private int crosshairSize = 0;
    private RGBtoColourName rgbToName;

    public CameraView(Context context, Camera camera){
        super(context);

        setCamera(camera);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
        rgbToName = new RGBtoColourName(context);
    }

    public void setCamera(Camera camera)
    {
        mCamera = camera;
        if (mCamera != null){
            mCamera.setDisplayOrientation(90);
            mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
        }
    }

    public void init(int size, ImageView box, TextView rgbTxt, TextView nameTxt) {
        crosshairSize = size;
        colorBox = box;
        rgbText = rgbTxt;
        rgbName = nameTxt;
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
            if(mCamera != null) {
                //when the surface is created, we can set the camera to draw images in this surfaceholder
                mCamera.setPreviewDisplay(surfaceHolder);
                mCamera.setPreviewCallback(this);
                mCamera.startPreview();
            }
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
            if(mCamera != null) {
                mCamera.stopPreview();
            }
        } catch (Exception e){
            //this will happen when you are trying the camera if it's not running
        }

        //recreate the camera preview
        try{
            if(mCamera != null) {
                Camera.Parameters params = mCamera.getParameters();
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                params.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
                mCamera.setParameters(params);
                mCamera.setPreviewCallback(this);
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();
            }
        } catch (IOException e) {
            Log.d("ERROR", "Camera error on surfaceChanged " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        if(crosshairSize > 0) {
            Camera.Parameters parameters = camera.getParameters();
            int width = parameters.getPreviewSize().width;
            int height = parameters.getPreviewSize().height;

            YuvImage yuv = new YuvImage(data, parameters.getPreviewFormat(), width, height, null);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            yuv.compressToJpeg(new Rect(0, 0, width, height), 50, out);

            byte[] bytes = out.toByteArray();
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

            int redSum = 0;
            int greenSum = 0;
            int blueSum = 0;
            int pixelCount = 0;

            for(int x = (width/2 - crosshairSize/2); x < (width/2 + crosshairSize/2); x++){
                for(int y = (height/2 - crosshairSize/2); y < (height/2 + crosshairSize/2); y++){
                    pixelCount++;
                    int pixel = bitmap.getPixel(x, y);
                    redSum += Color.red(pixel);
                    greenSum += Color.green(pixel);
                    blueSum += Color.blue(pixel);
                }
            }

            int avgRed = redSum/pixelCount;
            int avgGreen = greenSum/pixelCount;
            int avgBlue = blueSum/pixelCount;

            String text = "R: " + avgRed + " G: " + avgGreen + " B: " + avgBlue;
            String cName = rgbToName.getColourNameFromRgb( avgRed, avgGreen, avgBlue);

            colorBox.setBackgroundColor(Color.rgb(avgRed, avgGreen, avgBlue));
            rgbText.setText(text);
            rgbName.setText(cName);
        }
    }
}
