package com.jqh.record;

import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;

import java.util.List;

public class CameraHelper implements Camera.PreviewCallback {

    private static final String TAG = "CameraHelper";
    private int width, height;
    private int mCameraId;
    private Camera mCamera;
    private byte[] buffer, i420;
    private Camera.PreviewCallback mPreviewCallback;
    private SurfaceTexture mSurfaceTexture;

    public CameraHelper(int width, int height) {
        this.width = width;
        this.height = height;
        mCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
    }

    public void switchCamera(){
        if (mCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
            mCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        } else {
            mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        stopPreview();
        startPreview(mSurfaceTexture);
    }

    public void stopPreview(){
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }


    public void startPreview(SurfaceTexture surfaceTexture) {

        stopPreview();
        try{
            this.mSurfaceTexture = surfaceTexture;
            mCamera = Camera.open(mCameraId);
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPreviewFormat(ImageFormat.NV21);
            boolean isSupportSize = false;
            List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
            for(Camera.Size supportedPreviewSize : supportedPreviewSizes){
                if (supportedPreviewSize.width == width && supportedPreviewSize.height == height){
                    isSupportSize = true;
                    break;
                }
            }
            if (!isSupportSize){
                Camera.Size size = supportedPreviewSizes.get(0);
                width = size.width;
                height = size.height;
            }

            parameters.setPreviewSize(width, height);
            if(mCameraId == Camera.CameraInfo.CAMERA_FACING_BACK)
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            mCamera.setDisplayOrientation(90);

            mCamera.setParameters(parameters);

            buffer = new byte[width * height * 3 /2];
            i420 = new byte[width * height * 3 /2];

            mCamera.addCallbackBuffer(buffer);
            mCamera.setPreviewCallbackWithBuffer(this);

            mCamera.setPreviewTexture(this.mSurfaceTexture);
            mCamera.startPreview();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int getWidth(){return width;}

    public int getHeight(){return height;}


    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {

    }

    private void nv21ToI420(byte[] data){
        System.arraycopy(data,0, i420,0,width*height);
        int index = width * height;
        for(int i = width * height; i < data.length; i += 2) {
            i420[index++] = data[i + 1];
        }
        for(int i = width * height; i < data.length; i += 2) {
            i420[index++] = data[i];
        }

    }
}
