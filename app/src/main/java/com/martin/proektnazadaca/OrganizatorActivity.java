package com.martin.proektnazadaca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrganizatorActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizator);
        mAuth = FirebaseAuth.getInstance();
        logout = (Button) findViewById(R.id.logout2);
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

        ime = (TextView) findViewById(R.id.ime2);
        prezime = (TextView) findViewById(R.id.prezime2);
        email = (TextView) findViewById(R.id.meil2);
        telefon = (TextView) findViewById(R.id.telefon2);
        lice = (TextView) findViewById(R.id.korisnik2);

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