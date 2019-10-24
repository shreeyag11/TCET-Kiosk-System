package com.tabian.tcoetkiosk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Transcript extends AppCompatActivity {
public  static String copies,semstrart,semend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transcript);
        Button button=(Button)findViewById(R.id.btnTranscript);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et1 = (EditText)findViewById(R.id.etCopies);
                EditText et2 = (EditText)findViewById(R.id.etFrom);
                EditText et3 = (EditText)findViewById(R.id.etTo);
                copies = et1.getText().toString().trim();
                semstrart = et2.getText().toString().trim();
                semend = et3.getText().toString().trim();
/*Toast.makeText(getApplicationContext(),copies,Toast.LENGTH_LONG).show();*/
                Intent i = new Intent(getApplicationContext(),LetterGenerator.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"Generating Transcript", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
