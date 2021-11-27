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
import com.example.lv1.models.Student;
import com.example.lv1.viewModels.SummaryViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class PersonalInfoFragment extends Fragment {

    private TextInputEditText etName;
    private TextInputEditText etSurname;
    private TextInputEditText etBirthday;
    private SummaryViewModel viewModelSummary;

    public PersonalInfoFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_personal_info, container, false);
        viewModelSummary = new ViewModelProvider(requireActivity()).get(SummaryViewModel.class);
        etName = (TextInputEditText) v.findViewById(R.id.etName);
        etSurname = (TextInputEditText) v.findViewById(R.id.etSurname);
        etBirthday = (TextInputEditText) v.findViewById(R.id.etBirthday);

        etName.addTextChangedListener(new TextChange(etName));
        etSurname.addTextChangedListener(new TextChange(etSurname));
        etBirthday.addTextChangedListener(new TextChange(etBirthday));

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