package com.example.lv1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {

    private Button btnPovratak;
    private TextView tvIme;
    private TextView tvPredmet;
    private TextView tvPrezime;
    private TextView tvProfesor;
    private TextView tvAkGod;
    private TextView tvDatRod;
    private TextView tvLVH;
    private TextView tvPH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        btnPovratak = (Button) findViewById(R.id.btnPovratak);
        tvIme = (TextView) findViewById(R.id.tvIme);
        tvPredmet = (TextView) findViewById(R.id.tvPredmet);
        tvPrezime = (TextView) findViewById(R.id.tvPrezime);
        tvProfesor = (TextView) findViewById(R.id.tvProfesor);
        tvAkGod = (TextView) findViewById(R.id.tvAkGod);
        tvDatRod = (TextView) findViewById(R.id.tvDatumRodenja);
        tvLVH = (TextView) findViewById(R.id.tvLVH);
        tvPH = (TextView) findViewById(R.id.tvPH);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tvIme.setText("Ime: "+extras.getString("ime"));
            tvPrezime.setText("Prezime: "+extras.getString("prezime"));
            tvPredmet.setText("Predmet: "+extras.getString("predmet"));
            tvProfesor.setText("Profesor: "+extras.getString("profesor"));
            tvAkGod.setText("Akademska godina: "+extras.getString("akGod"));
            tvDatRod.setText("Datum rodenja: "+extras.getString("datumRod"));
            tvLVH.setText("Broj sati lv-a: "+extras.getString("LVH"));
            tvPH.setText("Broj sati predavanja: "+extras.getString("PH"));
        }
        btnPovratak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SummaryActivity.this, PersonalInfoActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });

    }
}