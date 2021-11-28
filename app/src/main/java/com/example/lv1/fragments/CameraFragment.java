package com.example.lv1.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.lv1.R;
import com.example.lv1.interfaces.ICameraFragmentToActivity;
import com.example.lv1.interfaces.IOnBackPressed;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraFragment extends Fragment implements IOnBackPressed {

    private static final String TAG = "CameraActivity";
    public static final String internalStorageDir = "/PMA";
    public static final String imageFormat = ".jpg";
    public static final String[] CAMERA_PERMISSIONS =  new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private int REQUEST_CODE_PERMISSIONS = 1001;

    Activity activity;
    Button flash;
    Button takePicture;
    Button changeCamera;
    ImageView imagePreview;
    ConstraintLayout previewLayout;
    Button deletePicture;
    Button savePicture;
    PreviewView viewFinder;
    boolean backPressed = true;

    ExecutorService cameraExecutor;
    CameraSelector cameraSelector;
    ImageCapture imageCapture;
    ProcessCameraProvider cameraProvider;
    ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    ICameraFragmentToActivity ICameraFragmentToActivity;
    Bitmap imageToSave;

    int lenceFacing;
    Camera camera;
    String imagePath = " ";
    boolean isFlashOn = false;
    Fragment f;

    public CameraFragment() {
        f = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_camera, container, false);

        activity = getActivity();
        flash = v.findViewById(R.id.flash);
        takePicture = v.findViewById(R.id.takePicture);
        changeCamera = v.findViewById(R.id.changeCamera);
        deletePicture = v.findViewById(R.id.deleteImage);
        savePicture = v.findViewById(R.id.submitImage);
        imagePreview = v.findViewById(R.id.previewImage);
        viewFinder = v.findViewById(R.id.camera_layout);
        previewLayout = v.findViewById(R.id.previewLayout);

        takePicture.setOnClickListener(new OnClick(takePicture));
        flash.setOnClickListener(new OnClick(flash));
        changeCamera.setOnClickListener(new OnClick(changeCamera));
        deletePicture.setOnClickListener(new OnClick(deletePicture));
        savePicture.setOnClickListener(new OnClick(savePicture));

        cameraExecutor = Executors.newSingleThreadExecutor();
        cameraProviderFuture = ProcessCameraProvider.getInstance(activity);
        flash.setEnabled(false);
        if(allPermissionGranted()){
            startCamera();
        }else{
            activity.requestPermissions(CAMERA_PERMISSIONS, 1);
        }
        return v;
    }

    @Override
    public boolean onBackPressed() {
        if (backPressed) {
            activity.findViewById(R.id.pager).setVisibility(View.VISIBLE);
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction trans = fm.beginTransaction();
            trans.remove(f);
            trans.commit();
            fm.popBackStack();
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_PERMISSIONS){
            if(allPermissionGranted()){
                startCamera();
            }else{
                activity.requestPermissions(CAMERA_PERMISSIONS, 1);
            }
        }
    }

    private boolean allPermissionGranted(){
        for(String permission : CAMERA_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void startCamera(){
        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try{
                    cameraProvider = cameraProviderFuture.get();
                    lenceFacing = CameraSelector.LENS_FACING_BACK;
                    initPreview(cameraProvider, lenceFacing);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }catch(ExecutionException e){
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(activity));
    }

    private void initPreview(ProcessCameraProvider cameraProvider, int lenceFacing){
        cameraProvider.unbindAll();
        activity.runOnUiThread(() -> {
            viewFinder.setVisibility(View.VISIBLE);
            takePicture.setVisibility(View.VISIBLE);
            changeCamera.setVisibility(View.VISIBLE);
            flash.setVisibility(View.VISIBLE);
            previewLayout.setVisibility(View.GONE);
        });
        try{
            Preview preview = new Preview.Builder().build();
            cameraSelector = new CameraSelector.Builder()
                    .requireLensFacing(lenceFacing)
                    .build();
            ImageCapture.Builder builder = new ImageCapture.Builder();
            imageCapture = builder
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                    .setTargetRotation(Surface.ROTATION_90)
                    .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                    .build();
            preview.setSurfaceProvider(viewFinder.getSurfaceProvider());
            camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        ICameraFragmentToActivity = (ICameraFragmentToActivity) context;
    }

    public void showImage(Activity activity, Bitmap bitmap, ImageView imagePreview){
        activity.runOnUiThread(() -> {
            Glide.with(activity)
                    .asBitmap()
                    .load(bitmap)
                    .into(new BitmapImageViewTarget(imagePreview){
                        @Override
                        protected void setResource(Bitmap resource) {
                            super.setResource(resource);
                        }
                    });
            imagePreview.setRotation(90);
        });
    }

    public Bitmap imageProxyToBitmap(ImageProxy imageProxy){
        ByteBuffer buffer = imageProxy.getPlanes()[0].getBuffer();
        buffer.rewind();
        byte[] bytes = new byte[buffer.capacity()];
        buffer.get(bytes);
        byte[] clonedBytes = bytes.clone();
        return BitmapFactory.decodeByteArray(clonedBytes, 0, clonedBytes.length);
    }

    public File getInternalStorage(String directory, String type, String dirType){
        Date date = new Date();
        String milisec = String.valueOf(date.getTime());
        File photoDire = new File(Environment.getExternalStoragePublicDirectory(dirType) + directory);
        if(!photoDire.exists()) photoDire.mkdir();
        return new File(photoDire, milisec + type);
    }

    public String saveToInternalMemory(Bitmap bitmap){
        File mypath = getInternalStorage(internalStorageDir, imagePath, Environment.DIRECTORY_PICTURES);
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(mypath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }

    private class OnClick implements View.OnClickListener {
        View view;

        private OnClick (View v) {
            view = v;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.takePicture:
                    imagePath = "";
                    try{
                        imageCapture.takePicture(cameraExecutor, new ImageCapture.OnImageCapturedCallback() {
                            @Override
                            public void onCaptureSuccess(@NonNull ImageProxy image) {
                                showImage(activity, imageProxyToBitmap(image), imagePreview);

                                activity.runOnUiThread(() -> {
                                    viewFinder.setVisibility(View.GONE);
                                    previewLayout.setVisibility(View.VISIBLE);
                                });
                                imageToSave = imageProxyToBitmap(image);
                                image.close();
                            }

                            @Override
                            public void onError(@NonNull ImageCaptureException exception) {
                                super.onError(exception);
                            }
                        });
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                case R.id.flash:
                    try{
                        if (isFlashOn){
                            camera.getCameraControl().enableTorch(false);
                            isFlashOn = false;
                            flash.setEnabled(false);
                        }else{
                            camera.getCameraControl().enableTorch(true);
                            isFlashOn = true;
                            flash.setEnabled(true);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case R.id.changeCamera:
                    cameraProvider.unbindAll();
                    if(lenceFacing == CameraSelector.LENS_FACING_FRONT){
                        initPreview(cameraProvider, CameraSelector.LENS_FACING_BACK);
                        lenceFacing = CameraSelector.LENS_FACING_BACK;
                    }else{
                        initPreview(cameraProvider, CameraSelector.LENS_FACING_FRONT);
                        lenceFacing = CameraSelector.LENS_FACING_FRONT;
                    }
                    break;
                case R.id.deleteImage:
                    activity.runOnUiThread(() -> {
                        imagePath = "";
                        viewFinder.setVisibility(View.VISIBLE);
                        previewLayout.setVisibility(View.GONE);
                    });
                    cameraProvider.unbindAll();
                    initPreview(cameraProvider, CameraSelector.LENS_FACING_BACK);
                    lenceFacing = CameraSelector.LENS_FACING_BACK;
                    break;
                case R.id.submitImage:
                    Log.i(TAG, "onClick: Spremi kliknut");
                    imagePath = saveToInternalMemory(imageToSave);
                    Log.i(TAG, "onClick: spremljeno");
                    ICameraFragmentToActivity.sendUrl(imagePath);
                    activity.findViewById(R.id.pager).setVisibility(View.VISIBLE);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction trans = fm.beginTransaction();
                    trans.remove(f);
                    trans.commit();
                    fm.popBackStack();
                    break;

            }
        }
    }
}