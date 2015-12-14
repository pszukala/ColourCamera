package com.pszukala.colourcamera;

import android.content.Context;
import android.hardware.Camera;
import android.graphics.*;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pszukala on 2015-10-19.
 */
public class CameraViewSearch extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {

    private SurfaceHolder mHolder;
    private Camera mCamera;
    private List<Camera.Size> mSupportedPreviewSizes;
    private Camera.Size mPreviewSize;
    private ColourWithName searchedColour;
    private int allowedMSE = 15;
    private int pixelDistance = 4;
    private ImageView searchCrosshair;
    private int searchDistance;
    private int crosshairSize;
    private int frameSkip = 0;

    public CameraViewSearch(Context context, Camera camera, ColourWithName sColour){
        super(context);

        setCamera(camera);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);

        searchedColour = sColour;
    }

    public void setCamera(Camera camera)
    {
        mCamera = camera;
        if (mCamera != null){
            mCamera.setDisplayOrientation(90);
            mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
        }
    }

    public void init(ImageView searchCrosshair)
    {
        this.searchCrosshair = searchCrosshair;
        crosshairSize = searchCrosshair.getHeight();
        searchDistance = (int) (crosshairSize/(pixelDistance*2));
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
        frameSkip++;
        if(frameSkip == 5) {
            frameSkip = 0;
            if (searchCrosshair != null) {
                Camera.Parameters parameters = camera.getParameters();
                int width = parameters.getPreviewSize().width;
                int height = parameters.getPreviewSize().height;

                int listWidth = width / pixelDistance;
                int listHeight = height / pixelDistance;

                Point[][] matchedPixels = new Point[listWidth][listHeight];

                YuvImage yuv = new YuvImage(data, parameters.getPreviewFormat(), width, height, null);

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                yuv.compressToJpeg(new Rect(0, 0, width, height), 50, out);

                byte[] bytes = out.toByteArray();
                final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                int xIt = 0;
                int yIt = 0;
                for (int x = pixelDistance * 4; x < (width - width / 20); x += pixelDistance) {
                    for (int y = pixelDistance * 4; y < (height - height / 20); y += pixelDistance) {
                        int pixel = bitmap.getPixel(x, y);
                        if ((searchedColour.redMSE(Color.red(pixel)) < allowedMSE) &&
                                (searchedColour.greenMSE(Color.green(pixel)) < allowedMSE) &&
                                (searchedColour.blueMSE(Color.blue(pixel)) < allowedMSE)) {
                            matchedPixels[xIt][yIt] = new Point(x, y, searchedColour.computeMSE(Color.red(pixel), Color.green(pixel), Color.blue(pixel)));
                        }
                        yIt++;
                    }
                    xIt++;
                    yIt = 0;
                }

                Point matchedPixel = null;
                int minNeighbours = searchDistance*2;
                int minMSE = allowedMSE;

                for (int x = searchDistance; x < (listWidth - searchDistance); x++) {
                    for (int y = searchDistance; y < (listHeight - searchDistance); y++) {
                        if (matchedPixels[x][y] != null) {
                            int neighbours = numberOfNeighbours(x, y, matchedPixels);
                            if (neighbours > minNeighbours) {
                                minNeighbours = neighbours;
                                matchedPixel = matchedPixels[x][y];
                            }
                            else if (neighbours == minNeighbours && minMSE > matchedPixels[x][y].MSE){
                                minMSE = matchedPixels[x][y].MSE;
                                matchedPixel = matchedPixels[x][y];
                            }
                        }
                    }
                }

                if (matchedPixel != null) {
                    ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) searchCrosshair.getLayoutParams();
                    lp.rightMargin = matchedPixel.y - crosshairSize / 2;
                    lp.topMargin = matchedPixel.x - crosshairSize / 2;
                    searchCrosshair.setVisibility(View.VISIBLE);
                    requestLayout();
                } else {
                    searchCrosshair.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    public int numberOfNeighbours(int x, int y, Point[][] matchedPixels){
        int count = 0;

        for(int it=-searchDistance; it<searchDistance; it++){

            if(matchedPixels[x+it][y] != null)
                count++;

            if(matchedPixels[x][y+it] != null)
                count++;

            if(matchedPixels[x+it][y+it] != null)
                count++;

            if(matchedPixels[x-it][y+it] != null)
                count++;
        }

        return count;
    }
}
