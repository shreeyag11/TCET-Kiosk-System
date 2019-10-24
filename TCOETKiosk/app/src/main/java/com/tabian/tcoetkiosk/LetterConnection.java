package com.tabian.tcoetkiosk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class LetterConnection extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    public static int check=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_connection);

        Spinner spinner = (findViewById(R.id.spinner));
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(getApplicationContext(),R.array.planets_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            String text = parent.getItemAtPosition(position).toString();
            if (text.equals("Transcript")) {
                check=1;
                Intent i = new Intent(getApplicationContext(),Transcript.class);
                startActivity(i);
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }
            else if (text.equals("Letter of Recommendation")) {
                check=2;
                Intent i = new Intent(getApplicationContext(),LOR.class);
                startActivity(i);
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }
            else if (text.equals("Provisional Degree Certificate")) {
                check=3;
                Intent i = new Intent(getApplicationContext(),LetterGenerator.class);
                startActivity(i);
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }
            else if (text.equals("Character Certificate")) {
                check=4;
                Intent i = new Intent(getApplicationContext(),LetterGenerator.class);
                startActivity(i);
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }
            else if (text.equals("Bonafide Certificate")) {
                check=5;
                Intent i = new Intent(getApplicationContext(),LetterGenerator.class);
                startActivity(i);
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }
            else if (text.equals("ID Card Letter")) {
                check=6;
                Intent i = new Intent(getApplicationContext(),LetterGenerator.class);
                startActivity(i);
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }
            else if (text.equals("Re-Evaluation Marksheet")) {
                check=7;
                Intent i = new Intent(getApplicationContext(),LetterGenerator.class);
                startActivity(i);
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }
            else if (text.equals("Transfer Certificate (TC)")) {
                check=8;
                Intent i = new Intent(getApplicationContext(),LetterGenerator.class);
                startActivity(i);
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }
            else if (text.equals("Leaving Certificate")) {
                check=9;
                Intent i = new Intent(getApplicationContext(),LetterGenerator.class);
                startActivity(i);
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }
            else if (text.equals("Leave Certificate (Medical Leave)")) {
                check=10;
                Intent i = new Intent(getApplicationContext(),LetterGenerator.class);
                startActivity(i);
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }
            else if (text.equals("Others")) {
                check=11;
                Intent i = new Intent(getApplicationContext(),Others.class);
                startActivity(i);
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }



            /*



            else if (text.equals("No Objection Certificate(NOC)")) {
                check=8;
                Intent i = new Intent(getApplicationContext(),LetterGenerator.class);
                startActivity(i);
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }





            else if (text.equals("Migration Certificate")) {
                check=13;
                Intent i = new Intent(getApplicationContext(),LetterGenerator.class);
                startActivity(i);
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }
            else if (text.equals("Duplicate Marksheet")) {
                check=15;
                Intent i = new Intent(getApplicationContext(),LetterGenerator.class);
                startActivity(i);
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }
            else if (text.equals("Verification Education")) {
                check=16;
                Intent i = new Intent(getApplicationContext(),LetterGenerator.class);
                startActivity(i);
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }
            else if (text.equals("Duplicate fee Receipt")) {
                check=17;
                Intent i = new Intent(getApplicationContext(),LetterGenerator.class);
                startActivity(i);
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }
            else if (text.equals("Internship")) {
                check=18;
                Intent i = new Intent(getApplicationContext(),LetterGenerator.class);
                startActivity(i);
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }else if (text.equals("Duplicate Hallticket")) {
                check=19;
                Intent i = new Intent(getApplicationContext(),LetterGenerator.class);
                startActivity(i);
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }*/
            else if (text.equals("Others")) {
                check=11;
                Intent i = new Intent(getApplicationContext(),LetterGenerator.class);
                startActivity(i);
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            }
        else{
                Toast.makeText(parent.getContext(), "Please select a letter", Toast.LENGTH_SHORT).show();
            }
        }
        

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
