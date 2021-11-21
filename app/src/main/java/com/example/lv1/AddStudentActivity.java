package com.example.lv1;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;

import com.example.lv1.models.Storage;
import com.example.lv1.models.Student;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;

public class AddStudentActivity extends AppCompatActivity {

    private TextInputEditText etName;
    private TextInputEditText etSurname;
    private TextInputEditText etSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
    }

    public void AddNewStudent(View view){
        Storage StorageSingleton = Storage.getInstance();
        etName = (TextInputEditText) findViewById(R.id.etName);
        etSurname = (TextInputEditText) findViewById(R.id.etSurname);
        etSubject = (TextInputEditText) findViewById(R.id.etSubject);
        Student student = new Student(
            "Ime: " + (etName.getText() != null ? etName.getText().toString() : "N/A"),
            "Prezime: " + (etSurname.getText() != null ? etSurname.getText().toString() : "N/A"),
            "Predmet: " + (etSubject.getText() != null ? etSubject.getText().toString() : "N/A")
        );
        StorageSingleton.addStudent(student);
        Intent HomeAct = new Intent(AddStudentActivity.this, HomePageActivity.class);
        HomeAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(HomeAct);
        finish();
    }
}