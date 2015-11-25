package com.pszukala.colourcamera;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.hardware.Camera;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private Camera mCamera = null;
    private CameraView mCameraView = null;
    FrameLayout camera_view = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraInit();

        //btn to close the application
        ImageButton closeButton = (ImageButton) findViewById(R.id.closeBtn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCamera.stopPreview();
                mCameraView.setCamera(null);
                mCamera.release();
                camera_view.removeAllViews();
                camera_view = null;
                mCamera = null;
                mCameraView = null;
                System.exit(0);
            }
        });

        ImageButton menuButton = (ImageButton) findViewById(R.id.menuBtn);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }

    public void cameraInit()
    {
        try {
            mCamera = Camera.open();//you can use open(int) to use different cameras
            //set camera to continually auto-focus

        } catch (Exception e) {
            Log.d("ERROR", "Failed to get camera: " + e.getMessage());
        }

        if (mCamera != null) {
            mCameraView = new CameraView(this, mCamera);//create a SurfaceView to show camera data
            camera_view = (FrameLayout) findViewById(R.id.camera_view);
            camera_view.addView(mCameraView);//add the SurfaceView to the layout
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        int crosshairSize = findViewById(R.id.crosshair).getHeight();
        ImageView box = (ImageView) findViewById(R.id.colorBox);
        TextView rgbTxt = (TextView) findViewById(R.id.rgbText);
        TextView nameTxt = (TextView) findViewById(R.id.rgbName);
        mCameraView.init(crosshairSize, box, rgbTxt, nameTxt);
    }


    @Override
    public void onPause() {
        super.onPause();

        if (mCamera != null) {
            mCamera.stopPreview();
            mCameraView.setCamera(null);
            mCamera.release();
            camera_view.removeAllViews();
            camera_view = null;
            mCamera = null;
            mCameraView = null;
            System.exit(0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mCamera == null) {
            cameraInit();
        }
    }
}
