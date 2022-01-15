package com.martin.proektnazadaca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileDetail extends AppCompatActivity {
    RecyclerView recyclerView;

    AdapterAktivnostiVolonter adapterAktivnostiVolonter;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference reference, reqRef, friendRef;
    FirebaseUser mUser;
    Aktivnost aktivnosti;



    ArrayList<Aktivnost> taskList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_detail);

        recyclerView = findViewById(R.id.recVIew3);

        reference = FirebaseDatabase.getInstance().getReference("Tasks");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskList = new ArrayList<>();

        adapterAktivnostiVolonter = new AdapterAktivnostiVolonter(this, taskList);
        recyclerView.setAdapter(adapterAktivnostiVolonter);

        String uuid = getIntent().getStringExtra("uid");
        String status = getIntent().getStringExtra("Status");
        //String rating1 = getIntent().getStringExtra("Rating");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    aktivnosti = dataSnapshot.getValue(Aktivnost.class);
                    if(aktivnosti.getStatus().equals("Активна")){
                        taskList.add(aktivnosti);
                    }
                }
                adapterAktivnostiVolonter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}