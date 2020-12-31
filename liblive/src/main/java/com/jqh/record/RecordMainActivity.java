package com.jqh.record;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;

import com.jqh.liblive.R;

public class RecordMainActivity extends AppCompatActivity {

    private TextureView textureView;

    private CameraHelper cameraHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_main);
        textureView = findViewById(R.id.surface_view);

        cameraHelper = new CameraHelper(960, 720);
    }

    public void startRecord(View view){
        cameraHelper.startPreview(textureView.getSurfaceTexture());
    }

    public void stopRecord(View view){
        cameraHelper.stopPreview();
    }

    public void switchCamera(View view){
        cameraHelper.switchCamera();
    }


}
