package com.example.lv1;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;

public class PersonalInfoActivity extends AppCompatActivity {

    private Button btnUnosIme;
    private TextInputEditText etIme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        btnUnosIme = (Button) findViewById(R.id.btnUnosIme);
        etIme = (TextInputEditText) findViewById(R.id.etIme);
        btnUnosIme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PersonalInfoActivity.this, StudentInfoActivity.class);
                i.putExtra("ime", etIme.getText() != null ? etIme.getText().toString() : "");
                startActivity(i);
            }
        });
    }
}