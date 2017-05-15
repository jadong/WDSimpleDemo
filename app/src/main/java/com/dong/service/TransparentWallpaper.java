package com.dong.service;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import android.os.HandlerThread;
import android.service.wallpaper.WallpaperService;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.dong.util.AppUtils;

import java.util.Arrays;

/**
 * Created by zengwendong on 2017/5/12.
 */
public class TransparentWallpaper extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        return new CameraEngine();
    }

    class CameraEngine extends Engine {

        private SparseIntArray ORIENTATIONS = new SparseIntArray();

        private CameraManager mCameraManager;//摄像头管理器
        private Handler childHandler, mainHandler;
        private String mCameraID;//摄像头Id 0 为后  1 为前
        private CameraCaptureSession mCameraCaptureSession;
        private CameraDevice mCameraDevice;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            Log.i("CameraEngine", "--------onCreate--");

            ///为了使照片竖直显示
            ORIENTATIONS.append(Surface.ROTATION_0, 90);
            ORIENTATIONS.append(Surface.ROTATION_90, 0);
            ORIENTATIONS.append(Surface.ROTATION_180, 270);
            ORIENTATIONS.append(Surface.ROTATION_270, 180);

            openCamera();

            //启用触摸事件
            setTouchEventsEnabled(true);

        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.i("CameraEngine", "--------onDestroy--");
            closeCamera();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            Log.i("CameraEngine", "--------onVisibilityChanged--" + visible);
            if (visible) {
                openCamera();
            } else {
                closeCamera();
            }
        }

        /**
         * 初始化Camera2
         */
        private void openCamera() {
            HandlerThread handlerThread = new HandlerThread("Camera2");
            handlerThread.start();
            childHandler = new Handler(handlerThread.getLooper());
            mainHandler = new Handler(getMainLooper());
            mCameraID = "" + CameraCharacteristics.LENS_FACING_FRONT;//后摄像头

            //获取摄像头管理
            mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            try {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Log.i("CameraEngine", "--------openCamera--");
                //打开摄像头
                mCameraManager.openCamera(mCameraID, stateCallback, mainHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        /**
         * 摄像头创建监听
         */
        private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
            @Override
            public void onOpened(CameraDevice camera) {//打开摄像头
                Log.i("CameraEngine", "--------onOpened--" + camera);
                mCameraDevice = camera;
                //开启预览
                startPreview();
            }

            @Override
            public void onDisconnected(CameraDevice camera) {//关闭摄像头
                Log.i("CameraEngine", "--------onDisconnected--");
                closeCamera();
            }

            @Override
            public void onError(CameraDevice camera, int error) {//发生错误
                Log.i("CameraEngine", "--------onError--"+error);
                AppUtils.toast("相机开启失败");
            }
        };

        private void startPreview() {

            try {
                if (mCameraDevice == null) {
                    return;
                }
                // 创建预览需要的CaptureRequest.Builder
                final CaptureRequest.Builder previewRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                // 将SurfaceView的surface作为CaptureRequest.Builder的目标
                previewRequestBuilder.addTarget(getSurfaceHolder().getSurface());
                // 创建CameraCaptureSession，该对象负责管理处理预览请求和拍照请求
                mCameraDevice.createCaptureSession(Arrays.asList(getSurfaceHolder().getSurface()), new CameraCaptureSession.StateCallback() {
                    @Override
                    public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                        if (null == mCameraDevice) return;
                        // 当摄像头已经准备好时，开始显示预览
                        mCameraCaptureSession = cameraCaptureSession;
                        try {
                            // 自动对焦
                            previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                            // 打开闪光灯
                            //previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                            // 显示预览
                            CaptureRequest previewRequest = previewRequestBuilder.build();
                            mCameraCaptureSession.setRepeatingRequest(previewRequest, null, childHandler);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                    }
                }, childHandler);
                Log.i("CameraEngine", "--------startPreview");
            } catch (CameraAccessException e) {
                e.printStackTrace();
                Log.i("CameraEngine", "--------" + e.getMessage());
            }

        }

        public void closeCamera() {
            if (null != mCameraDevice) {
                Log.i("CameraEngine", "--------closeCamera");

                mCameraDevice.close();
                mCameraDevice = null;
            }
        }

    }
}
