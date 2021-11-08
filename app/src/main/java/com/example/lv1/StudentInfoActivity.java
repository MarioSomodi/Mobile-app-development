package com.example.lv1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class StudentInfoActivity extends AppCompatActivity {

    private Button btnUnosPredmet;
    private TextInputEditText etPredmet;
    private TextInputEditText etProfesor;
    private TextInputEditText etAkGod;
    private TextInputEditText etLVH;
    private TextInputEditText etPH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        btnUnosPredmet = (Button) findViewById(R.id.btnUnosPredmet);
        etPredmet = (TextInputEditText) findViewById(R.id.etPredmet);
        etProfesor = (TextInputEditText) findViewById(R.id.etProfesor);
        etAkGod = (TextInputEditText) findViewById(R.id.etAkGod);
        etLVH = (TextInputEditText) findViewById(R.id.etLVH);
        etPH = (TextInputEditText) findViewById(R.id.etPH);

        btnUnosPredmet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StudentInfoActivity.this, SummaryActivity.class);
                i.putExtra("predmet", etPredmet.getText() != null ? etPredmet.getText().toString() : "");
                i.putExtra("profesor", etProfesor.getText() != null ? etProfesor.getText().toString() : "");
                i.putExtra("akGod", etAkGod.getText() != null ? etAkGod.getText().toString() : "");
                i.putExtra("LVH", etLVH.getText() != null ? etLVH.getText().toString() : "");
                i.putExtra("PH", etPH.getText() != null ? etPH.getText().toString() : "");
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    i.putExtra("ime", extras.getString("ime"));
                    i.putExtra("prezime", extras.getString("prezime"));
                    i.putExtra("datumRod", extras.getString("datumRod"));
                }
                startActivity(i);
            }
        });
    }
}