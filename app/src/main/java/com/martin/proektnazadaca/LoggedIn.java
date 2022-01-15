package com.martin.proektnazadaca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoggedIn extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;
    String userID;
    DatabaseReference reference, reference1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        mAuth = FirebaseAuth.getInstance();

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAuth.signOut();
//                Toast.makeText(getApplicationContext(), "Се одјавивте", Toast.LENGTH_SHORT).show();
//                Intent mainA = new Intent(LoggedIn.this, MainActivity.class);
//                startActivity(mainA);
//            }
//        });
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String lice1 =  userProfile.PersonType;
                    Toast.makeText(LoggedIn.this, lice1, Toast.LENGTH_SHORT).show();
                    if (lice1.equals("Повозрасно Лице")) {
                        Intent elder = new Intent(getApplicationContext(), PostaroLiceActivity.class);
                        startActivity(elder);
                    }else if(lice1.equals("Волонтер")){
                        Intent volonter = new Intent(getApplicationContext(), VolonterActivity.class);
                        startActivity(volonter);
                    }else if(lice1.equals("Организатор")) {
                        Intent organizator = new Intent(getApplicationContext(), OrganizatorActivity.class);
                        startActivity(organizator);
                    }
//                    }else{
//                        Intent login = new Intent(getApplicationContext(), LoginActivity.class);
//                        startActivity(login);
//                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Настана грешка, обидете се повторно !", Toast.LENGTH_SHORT).show();
            }
        });

            reference1 = FirebaseDatabase.getInstance().getReference("Izvestai");

            Query query = reference1.orderByChild("za").equalTo(userID);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    float ocena2 = 0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String ocena = snapshot.child("ocena").getValue(String.class);
                        i = i+1;
                        float ocena1 = Float.parseFloat(ocena);
                        ocena2 += ocena1;
                        //int br_oceni= Integer.parseInt(String.valueOf(snapshot.getChildrenCount()));
                        float final_ocena = ocena2/i;
                        FirebaseDatabase.getInstance().getReference("Users").child(userID).child("Rating").setValue(String.valueOf(final_ocena)).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                } else {
                                }
                            }
                        });

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });





    }
}