package com.example.lv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class StudentInfoActivity extends AppCompatActivity {

    private Button btnUnosPredmet;
    private TextInputEditText etPredmet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        btnUnosPredmet = (Button) findViewById(R.id.btnUnosPredmet);
        etPredmet = (TextInputEditText) findViewById(R.id.etPredmet);
        btnUnosPredmet.setOnClickListener(v -> {
            Intent i = new Intent(StudentInfoActivity.this, SummaryActivity.class);
            i.putExtra("predmet", etPredmet.getText() != null ? etPredmet.getText().toString() : "");
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                i.putExtra("ime", extras.getString("ime"));
            }
            startActivity(i);
        });
    }
}