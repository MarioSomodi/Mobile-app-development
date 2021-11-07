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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        btnPovratak = (Button) findViewById(R.id.btnPovratak);
        tvIme = (TextView) findViewById(R.id.tvIme);
        tvPredmet = (TextView) findViewById(R.id.tvPredmet);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tvIme.setText(extras.getString("ime"));
            tvPredmet.setText(extras.getString("predmet"));
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