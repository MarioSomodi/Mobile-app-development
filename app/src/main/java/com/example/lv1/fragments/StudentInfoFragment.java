package com.example.lv1.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lv1.R;
import com.example.lv1.managers.ApiManager;
import com.example.lv1.models.APIResponse;
import com.example.lv1.models.Course;
import com.example.lv1.models.CourseAPI;
import com.example.lv1.models.Instructor;
import com.example.lv1.models.Storage;
import com.example.lv1.viewModels.SummaryViewModel;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentInfoFragment extends Fragment implements Callback<APIResponse>, AdapterView.OnItemSelectedListener {

    private TextInputEditText etAcademicYear;
    private TextInputEditText etLabHours;
    private ImageView imgView2;
    private TextInputEditText etLectureHours;
    private Spinner subjectSpinner;
    private Spinner spinnerProffesor;
    private SummaryViewModel viewModelSummary;
    private List<String> subjectTitles =  new ArrayList<String>();
    private List<String> instructors =  new ArrayList<String>();

    APIResponse apiResponse;

    public StudentInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApiManager.getInstance().service().getCourses().enqueue(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_info, container, false);
        viewModelSummary = new ViewModelProvider(requireActivity()).get(SummaryViewModel.class);

        etAcademicYear = (TextInputEditText) v.findViewById(R.id.etAcademicYear);
        imgView2 = v.findViewById(R.id.imgView2);
        subjectSpinner = v.findViewById(R.id.spinnerSubject);
        spinnerProffesor = v.findViewById(R.id.spinnerProffesor);
        etLabHours = (TextInputEditText) v.findViewById(R.id.etLabHours);
        etLectureHours = (TextInputEditText) v.findViewById(R.id.etLectureHours);
        Storage StorageSingleton = Storage.getInstance();
        viewModelSummary.profileImage.observe(getViewLifecycleOwner(), list -> {
            imgView2.setImageBitmap(viewModelSummary.profileImage.getValue());
            if(viewModelSummary.rotation.getValue().booleanValue()){
                imgView2.setRotation(90);
            }else{
                imgView2.setRotation(0);
            }
        });

        subjectSpinner.setOnItemSelectedListener(this);
        spinnerProffesor.setOnItemSelectedListener(this);
        subjectTitles.add(0, "Predmet");
        instructors.add(0, "Profesor");
        ArrayAdapter<String> dataAdapterInstructors = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, instructors){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                }
                return true;
            }
            // Change color item
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View mView = super.getDropDownView(position, convertView, parent);
                TextView mTextView = (TextView) mView;
                if (position == 0) {
                    mTextView.setTextColor(Color.GRAY);
                } else {
                    mTextView.setTextColor(Color.WHITE);
                }
                return mView;
            }
        };
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, subjectTitles){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                }
                return true;
            }
            // Change color item
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View mView = super.getDropDownView(position, convertView, parent);
                TextView mTextView = (TextView) mView;
                if (position == 0) {
                    mTextView.setTextColor(Color.GRAY);
                } else {
                    mTextView.setTextColor(Color.WHITE);
                }
                return mView;
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterInstructors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner.setAdapter(dataAdapter);
        spinnerProffesor.setAdapter(dataAdapterInstructors);
        spinnerProffesor.setSelection(0);
        subjectSpinner.setSelection(0);
        etAcademicYear.addTextChangedListener(new TextChange(etAcademicYear));
        etLabHours.addTextChangedListener(new TextChange(etLabHours));
        etLectureHours.addTextChangedListener(new TextChange(etLectureHours));
        return v;
    }

    @Override
    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
        if (response.isSuccessful() && response.body() != null){
            apiResponse = (APIResponse) response.body();
            for(CourseAPI course : apiResponse.courses){
                if(course.title != null && !course.title.trim().isEmpty()){
                    subjectTitles.add(course.title);
                }
                if(course.instructors !=  null){
                    for(Instructor instructor : course.instructors){
                        if(course.title != null && !course.title.trim().isEmpty()){
                            instructors.add(instructor.name);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onFailure(Call<APIResponse> call, Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinnerProffesor:
                if(position != 0){
                    spinnerProffesor.setSelection(position, true);
                    viewModelSummary.setCourseProperty(instructors.get(position), 2);
                }
                break;
            case R.id.spinnerSubject:
                if(position != 0){
                    subjectSpinner.setSelection(position, true);
                    viewModelSummary.setCourseProperty(subjectTitles.get(position), 1);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                case R.id.etAcademicYear:
                    viewModelSummary.setCourseProperty(s.toString(), 3);
                    break;
                case R.id.etLabHours:
                    viewModelSummary.setCourseProperty(s.toString(), 4);
                    break;
                case R.id.etLectureHours:
                    viewModelSummary.setCourseProperty(s.toString(), 5);
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) { }
    }
}