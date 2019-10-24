package com.tabian.tcoetkiosk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Others extends AppCompatActivity {
    public  static String sub,content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);
        Button button=(Button)findViewById(R.id.btnOthers);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et1 = (EditText)findViewById(R.id.etSubject);
                EditText et2 = (EditText)findViewById(R.id.etContent);
                sub = et1.getText().toString().trim();
                content = et2.getText().toString().trim();
                Intent i = new Intent(getApplicationContext(),LetterGenerator.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"Generating Letter", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
