package com.martin.proektnazadaca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostaroLiceActivity extends AppCompatActivity {
    MaterialToolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FrameLayout frameLayout;

    Button logout;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String userID;
    DatabaseReference reference;

    TextView ime;
    TextView email;
    TextView lice;


    View headerview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postaro_lice);


        mAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        frameLayout = findViewById(R.id.main_frameLayout);

        drawerLayout = findViewById(R.id.drawerLayout);

        navigationView = findViewById(R.id.navigation);

        headerview = LayoutInflater.from(this).inflate(R.layout.navheader, navigationView, false);
        navigationView.addHeaderView(headerview);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                        Intent mainA = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainA);
                    case R.id.menu_settings:
                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                    case R.id.logout:
                        mAuth.signOut();
                        Toast.makeText(getApplicationContext(), "Се одјавивте", Toast.LENGTH_SHORT).show();
                        Intent mainA = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainA);
                }
                return true;
            }
        });
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        ime = (TextView) headerview.findViewById(R.id.kime);
        email = (TextView) headerview.findViewById(R.id.kemail);
        lice = (TextView) headerview.findViewById(R.id.kkorisnik);
//        ime = findViewById(R.id.kime);
//        email = findViewById(R.id.kemail);
//        lice = findViewById(R.id.kkorisnik);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String ime1 = userProfile.FirstName;
                    String prezime1 = userProfile.LastName;
                    String email1 =  userProfile.Email;
                    String lice1 =  userProfile.PersonType;
                    String fullname = ime1+prezime1;
                    //View vi = inflater.inflate(R.layout.navheader, null);

                    if(fullname != null) {
                        ime.setText(fullname);
                        email.setText(email1);
                        lice.setText(lice1);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"ABE BABUS IMAS GRESKA", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Настана грешка", Toast.LENGTH_SHORT).show();
            }
        });
    }
}