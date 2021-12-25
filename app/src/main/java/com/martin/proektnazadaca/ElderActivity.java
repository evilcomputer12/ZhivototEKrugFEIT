package com.martin.proektnazadaca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ElderActivity extends AppCompatActivity {
    Button logout;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String userID;
    DatabaseReference reference;

    TextView ime;
    TextView prezime;
    TextView telefon;
    TextView lice;
    TextView email;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elder);
        mAuth = FirebaseAuth.getInstance();
        logout = (Button) findViewById(R.id.logout1);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(getApplicationContext(), "Се одјавивте", Toast.LENGTH_SHORT).show();
                Intent mainA = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainA);
            }
        });
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        ime = (TextView) findViewById(R.id.ime1);
        prezime = (TextView) findViewById(R.id.prezime1);
        email = (TextView) findViewById(R.id.meil1);
        telefon = (TextView) findViewById(R.id.telefon1);
        lice = (TextView) findViewById(R.id.korisnik1);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String ime1 = userProfile.FirstName;
                    String prezime1 = userProfile.LastName;
                    String email1 =  userProfile.Email;
                    String telefon1 = userProfile.Phone;
                    String lice1 =  userProfile.PersonType;
                    ime.setText("Име: " +  ime1 );
                    prezime.setText("Презиме: " + prezime1 );
                    email.setText("E-mail: " + email1 );
                    telefon.setText("Телефон: " + telefon1 );
                    lice.setText("Тип на корисник: " + lice1 );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Настана грешка", Toast.LENGTH_SHORT).show();
            }
        });
    }
}