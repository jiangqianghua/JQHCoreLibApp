package com.jqh.libgpuimage;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBoxBlurFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageCGAColorspaceFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageColorBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageDifferenceBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSepiaToneFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageTwoInputFilter;

public class SimpleActivity extends AppCompatActivity {
    GPUImageView gpuImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        Uri imageUri = Uri.parse("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1819216937,2118754409&fm=26&gp=0.jpg");
        gpuImageView = findViewById(R.id.gpuimageview);
        gpuImageView.setImage(imageUri); // this loads image on the current thread, should be run in a thread
        gpuImageView.setFilter(new GPUImageDifferenceBlendFilter());
    }
}
