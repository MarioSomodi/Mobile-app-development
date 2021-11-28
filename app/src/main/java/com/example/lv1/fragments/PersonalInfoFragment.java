package com.example.lv1.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.ImageCapture;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lv1.R;
import com.example.lv1.models.Course;
import com.example.lv1.models.Student;
import com.example.lv1.viewModels.SummaryViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;
import java.io.IOException;

public class PersonalInfoFragment extends Fragment {

    public static final String[] CAMERA_PERMISSIONS =  new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private int REQUEST_CODE_PERMISSIONS = 1001;
    private TextInputEditText etName;
    private TextInputEditText etSurname;
    private TextInputEditText etBirthday;
    private ImageView imgView1;
    private Button btnOpenCamera;
    private Button btnSetDefaultImg;
    private Button btnOpenGallery;
    private SummaryViewModel viewModelSummary;
    private boolean defaultImg;

    public PersonalInfoFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_personal_info, container, false);
        getActivity().requestPermissions(CAMERA_PERMISSIONS, 1);
        viewModelSummary = new ViewModelProvider(requireActivity()).get(SummaryViewModel.class);

        imgView1 = v.findViewById(R.id.imgView1);
        etName = (TextInputEditText) v.findViewById(R.id.etName);
        etSurname = (TextInputEditText) v.findViewById(R.id.etSurname);
        etBirthday = (TextInputEditText) v.findViewById(R.id.etBirthday);
        btnOpenCamera = (Button) v.findViewById(R.id.btnOpenCamera);
        btnSetDefaultImg = v.findViewById(R.id.btnDefaultPhoto);
        btnOpenGallery = v.findViewById(R.id.btnOpenGallery);

        btnOpenGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        btnSetDefaultImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap img = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.favicon);
                viewModelSummary.setProfileImage(img, false);
            }
        });
        btnOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().findViewById(R.id.pager).setVisibility(View.GONE);
                Fragment fragment = new CameraFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(
                        getActivity().findViewById(R.id.camera_frame).getId(),
                        fragment
                );
                transaction.addToBackStack("camera");
                transaction.commit();
            }
        });
        viewModelSummary.profileImage.observe(getViewLifecycleOwner(), list -> {
            imgView1.setImageBitmap(viewModelSummary.profileImage.getValue());
            if(viewModelSummary.rotation.getValue().booleanValue()){
                imgView1.setRotation(90);
            }else{
                imgView1.setRotation(0);
            }
        });

        etName.addTextChangedListener(new TextChange(etName));
        etSurname.addTextChangedListener(new TextChange(etSurname));
        etBirthday.addTextChangedListener(new TextChange(etBirthday));

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uri = data.getData();
            try{
                Bitmap img = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                viewModelSummary.setProfileImage(img, false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_PERMISSIONS){
            if(!allPermissionGranted()){
                getActivity().requestPermissions(CAMERA_PERMISSIONS, 1);
            }
        }
    }

    private boolean allPermissionGranted(){
        for(String permission : CAMERA_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private class TextChange implements TextWatcher {
        View view;

        private TextChange (View v) {
            view = v;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            switch (view.getId()) {
                case R.id.etName:
                    viewModelSummary.setStudentProperty(s.toString(), 1);
                    break;
                case R.id.etSurname:
                    viewModelSummary.setStudentProperty(s.toString(), 2);
                    break;
                case R.id.etBirthday:
                    viewModelSummary.setStudentProperty(s.toString(), 3);
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) { }
    }
}