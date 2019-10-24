package com.tabian.tcoetkiosk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import static com.tabian.tcoetkiosk.MainActivityTesting.mAuth;
import static com.tabian.tcoetkiosk.ViewDatabase.mFirebaseDatabase;
import static com.tabian.tcoetkiosk.ViewDatabase.userID;

public class MainDashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
public static String st1,st2,st3,st4,st5,st6,st7,st8,st9,st10,st11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

   /*     ViewDatabase.mAuth = FirebaseAuth.getInstance();
        ViewDatabase.mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
*/

        Button btnEdit = (Button) findViewById(R.id.btnEdit);
        Button btnGenerate = (Button) findViewById(R.id.btnGenerate);
      /*  Button btnTrack = (Button) findViewById(R.id.btnTrack);*/
        Button btnView = (Button) findViewById(R.id.btnView);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Edit Information", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        TextView hello = (TextView)findViewById(R.id.hello);

        hello.setText("Hello "+FirebaseAuth.getInstance().getCurrentUser().getEmail());
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AddToDatabase.class);
                startActivity(i);
            }
        });

        /*btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Tracking.class);
                startActivity(i);
            }
        });*/

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),LetterConnection.class);
                startActivity(i);
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ViewDatabase.class);
                startActivity(i);
            }
        });


        st1= UserInformation.publicName;
st2= UserInformation.publicClass;
st3=UserInformation.publicDept;
st4=UserInformation.publicAd1;
st5=UserInformation.publicAd2;

Toast.makeText(getApplicationContext(),ViewDatabase.sname+ViewDatabase.sclass+ViewDatabase.sdiv,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            Intent i=new Intent(getApplicationContext(),ViewDatabase.class);
            startActivity(i);
        } else if (id == R.id.generateLetter) {
            Intent i=new Intent(getApplicationContext(),LetterConnection.class);
            startActivity(i);
        } else if (id == R.id.edit_info) {
            Intent i = new Intent(getApplicationContext(),AddToDatabase.class);
            startActivity(i);

        /*} else if (id == R.id.track) {
            Intent i = new Intent(getApplicationContext(),Tracking.class);
            startActivity(i);*/

        } else if (id == R.id.contact) {
            Intent i = new Intent(getApplicationContext(),Contact.class);
            startActivity(i);

        } else if (id == R.id.logout) {
            mAuth.signOut();
            Toast.makeText(getApplicationContext(),"Signedout Successfully.",Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(),MainActivityTesting.class);
            finish();
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
