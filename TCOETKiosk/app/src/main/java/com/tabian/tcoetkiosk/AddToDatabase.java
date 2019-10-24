package com.tabian.tcoetkiosk;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by User on 2/16/2017.
 */

public class AddToDatabase extends AppCompatActivity {

    private static final String TAG = "AddToDatabase";

    private Button btnSubmit;
    private EditText name1, uclass, dept,div, rno, ad1,ad2,ad3,ad4,acad,phone,email1;
    private String userID;

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_to_database_layout);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
       /* mName = (EditText) findViewById(R.id.etName);
        mEmail = (EditText) findViewById(R.id.etEmail);
        mPhoneNum = (EditText) findViewById(R.id.etPhone);*/
        name1 = (EditText)findViewById(R.id.etName);
        uclass = (EditText)findViewById(R.id.etClass);
        dept = (EditText)findViewById(R.id.etDept);
        div = (EditText)findViewById(R.id.etDiv);
        rno = (EditText)findViewById(R.id.etRno);
        ad1 = (EditText)findViewById(R.id.etAd1);
        ad2 = (EditText)findViewById(R.id.etAd2);
        ad3 = (EditText)findViewById(R.id.etAd3);
        ad4 = (EditText)findViewById(R.id.etAd4);
        acad = (EditText)findViewById(R.id.etAcad);
        phone = (EditText)findViewById(R.id.etPhone);
        email1 = (EditText)findViewById(R.id.etEmail);




        //declare the database reference object. This is what we use to access the database.
        //NOTE: Unless you are signed in, this will not be useable.
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d(TAG, "onDataChange: Added information to database: \n" +
                        dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Submit pressed.");
                String name = name1.getText().toString();
                String uclass1 = uclass.getText().toString();
                String udept = dept.getText().toString();
                String udiv = div.getText().toString();
                String urno = rno.getText().toString();
                String uad1 = ad1.getText().toString();
                String uad2 = ad2.getText().toString();
                String uad3 = ad3.getText().toString();
                String uad4 = ad4.getText().toString();
                String uacad = acad.getText().toString();
                String uphone = phone.getText().toString();
                String uemail = email1.getText().toString();



                Log.d(TAG, "onClick: Attempting to submit to database: \n" +
                        "name: " + name1 + "\n" +
                        "email: " + email1 + "\n" +
                        "phone number: " + phone + "\n"
                );

                //handle the exception if the EditText fields are null
                if(!name.equals("") && !email1.equals("") && !phone.equals("")){
                    UserInformation userInformation = new UserInformation(name,uclass1,udept,udiv,urno,uad1,uad2,uad3,uad4,uacad,uphone,uemail);
                    myRef.child("users").child(userID).setValue(userInformation);
                    toastMessage("New Information has been saved.");
                    name1.setText("");
                    uclass.setText("");
                    dept.setText("");
                    div.setText("");
                    rno.setText("");
                    ad1.setText("");
                    ad2.setText("");
                    ad3.setText("");
                    ad4.setText("");
                    acad.setText("");
                    email1.setText("");
                    phone.setText("");
                }else{
                    toastMessage("Fill out all the fields");
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}