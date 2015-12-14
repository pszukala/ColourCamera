package com.pszukala.colourcamera;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.gson.Gson;

public class SearchColourOnViewActivity extends Activity {

    private Camera mCamera = null;
    private CameraViewSearch mCameraView = null;
    private FrameLayout camera_view = null;
    private ColourWithName selectedColour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_colour_on_view);

        String jsonObj = getIntent().getStringExtra("selectedColourObj");
        Gson gson = new Gson();
        selectedColour = gson.fromJson(jsonObj, ColourWithName.class);

        cameraInit();

        //btn to close the application
        ImageButton closeButton = (ImageButton) findViewById(R.id.searchViewCloseBtn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
            mCameraView = new CameraViewSearch(this, mCamera, selectedColour);//create a SurfaceView to show camera data
            camera_view = (FrameLayout) findViewById(R.id.camera_view_search);
            camera_view.addView(mCameraView);//add the SurfaceView to the layout
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView searchCrosshair = (ImageView) findViewById(R.id.search_crosshair);
        mCameraView.init(searchCrosshair);
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
            finish();
            System.exit(0);
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }
}
