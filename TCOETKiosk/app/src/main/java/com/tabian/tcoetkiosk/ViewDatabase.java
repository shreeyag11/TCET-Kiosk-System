package com.tabian.tcoetkiosk;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * Created by User on 2/7/2017.
 */

public class ViewDatabase extends AppCompatActivity {

    private static final String TAG = "ViewDatabase";
/*strings for user data in letter*/
    public static String sname;
    public static String sclass;
    public static String sdiv;
    public static String sdept;
    public static String srno;
    public static String sad1;
    public static String sad2;
    public static String sad3;
    public static String sad4;
    public static String sacad;
    public static String sphone;
    public static String semail;

    //add Firebase Database stuff
    public static FirebaseDatabase mFirebaseDatabase;
    public static FirebaseAuth mAuth;
    public static FirebaseAuth.AuthStateListener mAuthListener;
    public static DatabaseReference myRef;
    public static String userID;

    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_database_layout);
        mListView = (ListView) findViewById(R.id.listview);

        Button change = (Button)findViewById(R.id.btnChange);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AddToDatabase.class);
                startActivity(i);
            }
        });
Button cnf =(Button)findViewById(R.id.btnCnf);
cnf.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(),MainDashboard.class);
        startActivity(i);
    }
});
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
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void showData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            UserInformation uInfo = new UserInformation();
        /*    uInfo.setName(ds.child(userID).getValue(UserInformation.class).getName()); //set the name
            uInfo.setEmail(ds.child(userID).getValue(UserInformation.class).getEmail()); //set the email
            uInfo.setPhone_num(ds.child(userID).getValue(UserInformation.class).getPhone_num()); //set the phone_num*/
            uInfo.setPublicName(ds.child(userID).getValue(UserInformation.class).getPublicName());
            sname=ds.child(userID).getValue(UserInformation.class).getPublicName();
            uInfo.setPublicClass(ds.child(userID).getValue(UserInformation.class).getPublicClass());
            sclass=ds.child(userID).getValue(UserInformation.class).getPublicClass();
            uInfo.setPublicDept(ds.child(userID).getValue(UserInformation.class).getPublicDept());
            sdept=ds.child(userID).getValue(UserInformation.class).getPublicDiv();
            uInfo.setPublicDiv(ds.child(userID).getValue(UserInformation.class).getPublicDiv());
            sdiv=ds.child(userID).getValue(UserInformation.class).getPublicDept();
            uInfo.setPublicRno(ds.child(userID).getValue(UserInformation.class).getPublicRno());
            srno=ds.child(userID).getValue(UserInformation.class).getPublicRno();
            uInfo.setPublicAd1(ds.child(userID).getValue(UserInformation.class).getPublicAd1());
            sad1=ds.child(userID).getValue(UserInformation.class).getPublicAd1();
            uInfo.setPublicAd2(ds.child(userID).getValue(UserInformation.class).getPublicAd2());
            sad2=ds.child(userID).getValue(UserInformation.class).getPublicAd2();
            uInfo.setPublicAd3(ds.child(userID).getValue(UserInformation.class).getPublicAd3());
            sad3=ds.child(userID).getValue(UserInformation.class).getPublicAd3();
            uInfo.setPublicAd4(ds.child(userID).getValue(UserInformation.class).getPublicAd4());
            sad4=ds.child(userID).getValue(UserInformation.class).getPublicAd4();
            uInfo.setPublicPhone(ds.child(userID).getValue(UserInformation.class).getPublicPhone());
            sphone=ds.child(userID).getValue(UserInformation.class).getPublicPhone();
            uInfo.setPublicEmail(ds.child(userID).getValue(UserInformation.class).getPublicEmail());
            semail=ds.child(userID).getValue(UserInformation.class).getPublicEmail();
            uInfo.setPublicAcad(ds.child(userID).getValue(UserInformation.class).getPublicAcad());
            sacad=ds.child(userID).getValue(UserInformation.class).getPublicAcad();


            //display all the information
            Log.d(TAG, "showData: name: " + uInfo.getPublicName());
            Log.d(TAG, "showData: class: " + uInfo.getPublicClass());
            Log.d(TAG, "showData: dept: " + uInfo.getPublicDept());
            Log.d(TAG, "showData: div: " + uInfo.getPublicDiv());
            Log.d(TAG, "showData: roll no: " + uInfo.getPublicRno());
            Log.d(TAG, "showData: ad1: " + uInfo.getPublicAd1());
            Log.d(TAG, "showData: ad2: " + uInfo.getPublicAd2());
            Log.d(TAG, "showData: ad3: " + uInfo.getPublicAd3());
            Log.d(TAG, "showData: ad4: " + uInfo.getPublicAd4());
            Log.d(TAG, "showData: acad: " + uInfo.getPublicAcad());
            Log.d(TAG, "showData: phone: " + uInfo.getPublicPhone());
            Log.d(TAG, "showData: email: " + uInfo.getPublicEmail());

            ArrayList<String> array  = new ArrayList<>();
            array.add(uInfo.getPublicName());
            array.add(uInfo.getPublicClass());
            array.add(uInfo.getPublicDept());
            array.add(uInfo.getPublicDiv());
            array.add(uInfo.getPublicRno());
            array.add(uInfo.getPublicAd1());
            array.add(uInfo.getPublicAd2());
            array.add(uInfo.getPublicAd3());
            array.add(uInfo.getPublicAd4());
            array.add(uInfo.getPublicAcad());
            array.add(uInfo.getPublicPhone());
            array.add(uInfo.getPublicEmail());


            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
            mListView.setAdapter(adapter);

        }
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