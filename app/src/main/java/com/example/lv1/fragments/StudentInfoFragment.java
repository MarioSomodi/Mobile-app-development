package com.example.lv1.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lv1.R;
import com.example.lv1.models.Course;
import com.example.lv1.viewModels.SummaryViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class StudentInfoFragment extends Fragment {

    private TextInputEditText etSubject;
    private TextInputEditText etProfessor;
    private TextInputEditText etAcademicYear;
    private TextInputEditText etLabHours;
    private TextInputEditText etLectureHours;
    private SummaryViewModel viewModelSummary;

    public StudentInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_info, container, false);
        viewModelSummary = new ViewModelProvider(requireActivity()).get(SummaryViewModel.class);
        etSubject = (TextInputEditText) v.findViewById(R.id.etSubject);
        etProfessor = (TextInputEditText) v.findViewById(R.id.etProfessor);
        etAcademicYear = (TextInputEditText) v.findViewById(R.id.etAcademicYear);
        etLabHours = (TextInputEditText) v.findViewById(R.id.etLabHours);
        etLectureHours = (TextInputEditText) v.findViewById(R.id.etLectureHours);

        etSubject.addTextChangedListener(new TextChange(etSubject));
        etProfessor.addTextChangedListener(new TextChange(etProfessor));
        etAcademicYear.addTextChangedListener(new TextChange(etAcademicYear));
        etLabHours.addTextChangedListener(new TextChange(etLabHours));
        etLectureHours.addTextChangedListener(new TextChange(etLectureHours));

        return v;
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
                case R.id.etSubject:
                    viewModelSummary.setCourseProperty(s.toString(), 1);
                    break;
                case R.id.etProfessor:
                    viewModelSummary.setCourseProperty(s.toString(), 2);
                    break;
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