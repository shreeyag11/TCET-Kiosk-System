package com.tabian.tcoetkiosk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LOR extends AppCompatActivity {
    public  static String copies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lor);
        Button button=(Button)findViewById(R.id.btnLor);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et1 = (EditText)findViewById(R.id.etLor);
                copies = et1.getText().toString().trim();
                Intent i = new Intent(getApplicationContext(),LetterGenerator.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"Generating LOR", Toast.LENGTH_SHORT).show();
            }
            });

    }
}
