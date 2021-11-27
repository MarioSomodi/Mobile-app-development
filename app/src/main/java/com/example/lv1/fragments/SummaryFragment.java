package com.example.lv1.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lv1.CreateNewRecordActivity;
import com.example.lv1.HomePageActivity;
import com.example.lv1.R;
import com.example.lv1.models.Course;
import com.example.lv1.models.Storage;
import com.example.lv1.models.Student;
import com.example.lv1.viewModels.StudentVM;
import com.example.lv1.viewModels.SummaryViewModel;

public class SummaryFragment extends Fragment {

    private Button btnHome;
    private TextView tvName;
    private TextView tvSubject;
    private TextView tvSurname;
    private TextView tvProfessor;
    private TextView tvAcademicYear;
    private TextView tvBirthday;
    private TextView tvLabHours;
    private TextView tvLectureHours;
    private SummaryViewModel viewModelSummary;

    public SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_summary, container, false);
        Storage StorageSingleton = Storage.getInstance();
        viewModelSummary = new ViewModelProvider(requireActivity()).get(SummaryViewModel.class);
        btnHome = (Button) v.findViewById(R.id.btnHome);
        tvName = (TextView) v.findViewById(R.id.tvName);
        tvSubject = (TextView) v.findViewById(R.id.tvSubject);
        tvSurname = (TextView) v.findViewById(R.id.tvSurname);
        tvProfessor = (TextView) v.findViewById(R.id.tvProfessor);
        tvAcademicYear = (TextView) v.findViewById(R.id.tvAcademicYear);
        tvBirthday = (TextView) v.findViewById(R.id.tvBirthday);
        tvLabHours = (TextView) v.findViewById(R.id.tvLabHours);
        tvLectureHours = (TextView) v.findViewById(R.id.tvLectureHours);
        viewModelSummary.change.observe(getViewLifecycleOwner(), list -> {
            Student student = null;
            Course course = null;
            student =  viewModelSummary.getStudent();
            tvName.setText("Ime: " + (student.name != null ? student.name : "N/A"));
            tvSurname.setText("Prezime: " + (student.surname != null ? student.surname : "N/A"));
            tvBirthday.setText("Datum rođenja: " + (student.birthday != null ? student.birthday : "N/A"));
            course = viewModelSummary.getCourse();
            tvSubject.setText("Predmet: " + (course.subject != null ? course.subject : "N/A"));
            tvProfessor.setText("Profesor: " + (course.proffesor != null ? course.proffesor : "N/A"));
            tvAcademicYear.setText("Akademska godina: " + (course.academicYear != null ? course.academicYear : "N/A"));
            tvLabHours.setText("Sati labaratorijskih vjezbi: " + (course.labHours != null ? course.labHours : "N/A"));
            tvLectureHours.setText("Sati predavanja: " + (course.lectureHours != null ? course.lectureHours : "N/A"));

        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student =  viewModelSummary.getStudent();
                Course course = viewModelSummary.getCourse();
                StudentVM studentVM = new StudentVM(
                        "Ime: " + (student.name != null ? student.name : "N/A"),
                        "Prezime: " + (student.surname != null ? student.surname : "N/A"),
                        "Predmet: " + (course.subject != null ? course.subject : "N/A")
                );
                StorageSingleton.addStudent(studentVM);
                Intent HomePageAct = new Intent(getActivity(), HomePageActivity.class);
                HomePageAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(HomePageAct);
            }
        });
        return v;
    }
}