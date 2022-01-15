package com.martin.proektnazadaca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.circularreveal.cardview.CircularRevealCardView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ManagerActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference reference, reference2;
    private FirebaseUser volonterUser;
    HashMap hashMap, delVolonter;


    Button odbiVolonter, prifatiVolonter, zavrsi, nazad;

    String volonterId;
    String id;
    LinearLayout volonterButtons, volonterInfo, nemaVolonter;
    TextView imeAktivnost,kraenRok,vremeNaKreiranje,lokacija,itnost,povtorlivost,imeIPrezime,telefonskiBroj,email_adresa,status,volonterText, opisText;
    TextView imePrezime, tel, rejting, email;
    CircleImageView imageProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        TextView test = findViewById(R.id.imeAktivnost);


        imeAktivnost = findViewById(R.id.imeAktivnost);
        kraenRok = findViewById(R.id.kraenRok);
        vremeNaKreiranje = findViewById(R.id.vremeNaKreiranje);
        lokacija = findViewById(R.id.lokacija);
        itnost = findViewById(R.id.itnost);
        povtorlivost = findViewById(R.id.povtorlivost);
        imeIPrezime = findViewById(R.id.imeIPrezime);
        telefonskiBroj = findViewById(R.id.telefonskiBroj);
        email_adresa = findViewById(R.id.email_adresa);
        status = findViewById(R.id.status);
        volonterText = findViewById(R.id.volonterText);
        opisText = findViewById(R.id.opisText);

        imePrezime = findViewById(R.id.volonterIme);
        tel = findViewById(R.id.volonterTel);
        email = findViewById(R.id.volonterEmail);
        rejting = findViewById(R.id.volonterRejting);
        imageProfile = findViewById(R.id.imageProfile);


        volonterButtons = findViewById(R.id.volonterButtons);
        zavrsi = findViewById(R.id.zavrsi);
        nemaVolonter = findViewById(R.id.nemaVolonter);
        volonterInfo = findViewById(R.id.volonterInfo);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        test.setText(id);

        reference = FirebaseDatabase.getInstance().getReference("Tasks");
        reference2 = FirebaseDatabase.getInstance().getReference("Users");



        reference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Aktivnost aktivnost = snapshot.getValue(Aktivnost.class);


                if (aktivnost != null) {

                    String imeAktivnost1 = aktivnost.imeAktivnost;
                    String kraenRok1 = aktivnost.kraenRokAktivnost;
                    String vremeNaKreiranje1 = aktivnost.timeOfRecordCreation;
                    String lokacija1 = aktivnost.lokacijaAktivnost;
                    String itnost1 = aktivnost.urgencyAktivnost;
                    String povtorlivost1 = aktivnost.recuringAktivnost;
                    String imeIPrezime1 = aktivnost.liceImeAktivnost;
                    String telefonskiBroj1 = aktivnost.telefonLiceAktivnost;
                    String email_adresa1 = aktivnost.emailLiceAktivnost;
                    String status1 = aktivnost.status;
                    String opisText1 = aktivnost.opisAktivnost;
                    volonterId = aktivnost.getVolonter();

                    if(status1.equals("Активна")){
                        volonterInfo.setVisibility(View.GONE);
                        volonterButtons.setVisibility(View.GONE);
                        zavrsi.setVisibility(View.GONE);
                        nemaVolonter.setVisibility(View.VISIBLE);
                    } else if (status1.equals("Пријавен волонтер")){
                        volonterInfo.setVisibility(View.VISIBLE);
                        volonterButtons.setVisibility(View.VISIBLE);
                        zavrsi.setVisibility(View.GONE);
                        nemaVolonter.setVisibility(View.GONE);
                    } else if (status1.equals("Закажана активност")){
                        volonterInfo.setVisibility(View.VISIBLE);
                        volonterButtons.setVisibility(View.GONE);
                        zavrsi.setVisibility(View.VISIBLE);
                        nemaVolonter.setVisibility(View.GONE);
                    } else if (status1.equals("Завршена активност")){
                        volonterInfo.setVisibility(View.VISIBLE);
                        volonterButtons.setVisibility(View.GONE);
                        zavrsi.setVisibility(View.GONE);
                        nemaVolonter.setVisibility(View.GONE);
                    }

                    imeAktivnost.setText(imeAktivnost1);
                    kraenRok.setText(kraenRok1);
                    vremeNaKreiranje.setText(vremeNaKreiranje1);
                    lokacija.setText(lokacija1);
                    itnost.setText(itnost1);
                    povtorlivost.setText(povtorlivost1);
                    imeIPrezime.setText(imeIPrezime1);
                    telefonskiBroj.setText(telefonskiBroj1);
                    email_adresa.setText(email_adresa1);
                    status.setText(status1);
                    volonterText.setText(opisText1);

                    //opisText.setText();



                    if(volonterId != null){

                        reference2.child(volonterId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot2) {

                                User volonter = snapshot2.getValue(User.class);

                                if(volonter != null){

                                    String imePrezimeS = volonter.FirstName + " " + volonter.LastName;
                                    String telS = volonter.Phone;
                                    String emailS = volonter.Email;
                                    String rejtingS = volonter.Rating;
                                    String imgUriString = volonter.ProfilePic;

                                    Uri uri = Uri.parse(imgUriString);

                                    Picasso.get().load(uri).into(imageProfile);

                                    //Toast.makeText(UrediAktivnost.this, imePrezimeS + " " + telS , Toast.LENGTH_SHORT).show();

                                    imePrezime.setText(imePrezimeS);
                                    tel.setText(telS);
                                    email.setText(emailS);
                                    rejting.setText(rejtingS);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(ManagerActivity.this, "Настана грешка"+error.toString(), Toast.LENGTH_SHORT).show();

                            }


                        });

                    }

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        nazad = findViewById(R.id.nazad);
        nazad.setOnClickListener(this);


        odbiVolonter = findViewById(R.id.volonterOdbi);
        odbiVolonter.setOnClickListener(this);

        prifatiVolonter = findViewById(R.id.volonterPrifati);
        prifatiVolonter.setOnClickListener(this);

        zavrsi = findViewById(R.id.zavrsi);
        zavrsi.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nazad:
                startActivity(new Intent(this, PostaroLiceActivity.class));
                break;
            case R.id.volonterOdbi:
                hashMap = new HashMap();
                delVolonter = new HashMap();

                delVolonter.put("volonter", "0");

                hashMap.put("status", "Активна");

                reference.child(id).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        reference.child(id).updateChildren(delVolonter).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(ManagerActivity.this, "Одбиен Волонтер", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                });

                startActivity(new Intent(this, PostaroLiceActivity.class));
                break;
            case R.id.volonterPrifati:
                hashMap = new HashMap();
                hashMap.put("status", "Закажана активност");
                reference.child(id).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(ManagerActivity.this, "Прифатен волонтер", Toast.LENGTH_SHORT).show();
                    }
                });
                startActivity(new Intent(this, PostaroLiceActivity.class));
                break;
            case R.id.zavrsi:
                hashMap = new HashMap();
                hashMap.put("status", "Завршена активност");

                reference.child(id).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(ManagerActivity.this, "Завршена активност", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ManagerActivity.this, Zavrsi_Aktivnost.class);
                        intent.putExtra("id", volonterId);
                        startActivity(intent);
                    }
                });
                break;
        }
    }
}