package com.martin.proektnazadaca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VolonterAktivnostVoProcess extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    AdapterVoProcessAktivnostiVolonter myAdapter;
    ArrayList<Aktivnost> list;


    private FirebaseUser volonter;
    private DatabaseReference reference;

    private String volonterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volonter_aktivnost_vo_process);

        recyclerView = findViewById(R.id.activities3);
        database = FirebaseDatabase.getInstance().getReference("Tasks");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new AdapterVoProcessAktivnostiVolonter(this,list);
        recyclerView.setAdapter(myAdapter);

        volonter = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        volonterId = volonter.getUid();



        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){



                    Aktivnost aktv = dataSnapshot.getValue(Aktivnost.class);
                    if(aktv.volonter.equals(volonterId)){
                        list.add(aktv);
                    }

                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
