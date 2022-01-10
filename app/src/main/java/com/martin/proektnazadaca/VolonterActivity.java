package com.martin.proektnazadaca;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.maps.android.SphericalUtil;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class VolonterActivity extends AppCompatActivity {
    MaterialToolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FrameLayout frameLayout;

    Button logout;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseStorage storage;
    StorageReference storageReference;

    String userID;
    DatabaseReference reference;

    TextView ime;
    TextView email;
    TextView lice;
    TextView phoneNum;
    TextView location;

    CircleImageView im;

    View headerview;

    ImageView editName;
    ImageView editEmail;
    ImageView editPhone;
    ImageView editLocation;


    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;



    RecyclerView recyclerView;

    AdapterListaPostariLica adapterPostariLiceLista;

    ArrayList<Users> userList;

    String cuserLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volonter);

        mAuth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("Users");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        frameLayout = findViewById(R.id.main_frameLayout1);

        drawerLayout = findViewById(R.id.drawerLayout1);

        navigationView = findViewById(R.id.navigation1);

        headerview = LayoutInflater.from(this).inflate(R.layout.navheader1, navigationView, false);
        navigationView.addHeaderView(headerview);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        recyclerView = findViewById(R.id.recView1);

        reference = FirebaseDatabase.getInstance().getReference("Users");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();

        adapterPostariLiceLista = new AdapterListaPostariLica(this, userList);
        recyclerView.setAdapter(adapterPostariLiceLista);



        Query query = reference.orderByChild("PersonType").equalTo("Повозрасно Лице");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    //Users users = dataSnapshot.getValue(Users.class);
                    String name = dataSnapshot.child("FirstName").getValue(String.class);
                    String lname = dataSnapshot.child("LastName").getValue(String.class);
                    String profpic = dataSnapshot.child("ProfilePic").getValue(String.class);
                    String location = dataSnapshot.child("Location").getValue(String.class);
                    String utype = dataSnapshot.child("PersonType").getValue(String.class);
                    //String distance = dataSnapshot.child("Distance").getValue(String.class);
                    String rating = dataSnapshot.child("Rating").getValue(String.class);
                    String uid1 = dataSnapshot.child("Uid").getValue(String.class);
                    LatLng endLocation = getLocationFromAddress(getApplicationContext(), cuserLocation);
                    LatLng beginLocation = getLocationFromAddress(getApplicationContext(),location);
                    String distance = String.valueOf(distanceBetween(beginLocation, endLocation).intValue());
                    Users users = new Users(name,lname,utype,profpic,location,distance,rating,uid1);
                    Collections.sort(userList, new Comparator<Users>() {
                        @Override
                        public int compare(Users o1, Users o2) {
                            return o1.getDist().compareToIgnoreCase(o2.getDist());
                        }
                    });
                    userList.add(users);
                }
                adapterPostariLiceLista.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                        Intent mainA1 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainA1);
                }
                return true;
            }
        });
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        ime = (TextView) headerview.findViewById(R.id.kime1);
        email = (TextView) headerview.findViewById(R.id.kemail1);
        lice = (TextView) headerview.findViewById(R.id.kkorisnik1);
        im = (CircleImageView) headerview.findViewById(R.id.kprofil1);
        phoneNum = (TextView) headerview.findViewById(R.id.kphone1);
        location = (TextView) headerview.findViewById(R.id.klokacija1);

        editName = (ImageView) headerview.findViewById(R.id.editName1);
        editEmail = (ImageView) headerview.findViewById(R.id.editEmail1);
        editPhone = (ImageView) headerview.findViewById(R.id.editPhone1);
        editLocation = (ImageView) headerview.findViewById(R.id.editLokacija1);






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
                    String image = userProfile.ProfilePic;
                    String phone = userProfile.Phone;
                    cuserLocation =  userProfile.Location;
                    String fullname = ime1+" "+prezime1;
                    //View vi = inflater.inflate(R.layout.navheader, null);
                    ime.setText(fullname);
                    email.setText(email1);
                    lice.setText(lice1);
                    phoneNum.setText(phone);
                    location.setText(cuserLocation);


                    final String userKey = mAuth.getCurrentUser().getUid();
                    String profilePicture = userKey+".jpg";
                    StorageReference storageRef = storageReference.child(profilePicture);
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(im);
                            FirebaseDatabase.getInstance().getReference("Users").child(userKey).child("ProfilePic").setValue(String.valueOf(uri)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                    } else {
                                    }
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            im.setImageResource(R.drawable.account_circle_24);
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Настана грешка", Toast.LENGTH_SHORT).show();
            }
        });

        updateProfile();

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CropImage.activity().setAspectRatio(1,1).start(PostaroLiceActivity.this);
                Intent intent = ImageManager.startImageCropper(getApplicationContext());
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri resultUri = ImageManager.activityResult(requestCode, resultCode, data, getApplicationContext());
        //im.setImageURI(resultUri);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultUri != null) {
                uploadPicture(resultUri);
            }
        }if (requestCode == 777) {
            if (resultCode == RESULT_OK) {
                String returnAddress = data.getStringExtra("address");
                final String userKey = mAuth.getCurrentUser().getUid();

                FirebaseDatabase.getInstance().getReference("Users").child(userKey).child("Location").setValue(returnAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Успешна промена на вашата адреса", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Настана грешка обидете се повторно", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User userProfile = snapshot.getValue(User.class);

                        if(userProfile != null){
                            String address = userProfile.Location;
                            location.setText(address);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Настана грешка", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        }
    }




    private void uploadPicture(Uri imageUri) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Сликата се прикачува...");
        pd.show();
        final String userKey = mAuth.getCurrentUser().getUid();
        String profilePicture = userKey+".jpg";
        StorageReference storageRef = storageReference.child(profilePicture);
        storageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(im);
                    }
                });
                pd.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "Сликата е прикачена", Snackbar.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Неуспешно прикачување на слика", Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Progress: "+(int)progressPercent+"%");
            }
        });


    }


    private void updateProfile(){
        ime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editName.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        editName.setVisibility(View.INVISIBLE);
                    }
                }, 5000);
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editEmail.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        editEmail.setVisibility(View.INVISIBLE);
                    }
                }, 5000);
            }
        });
        phoneNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPhone.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        editPhone.setVisibility(View.INVISIBLE);
                    }
                }, 5000);

            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLocation.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        editPhone.setVisibility(View.INVISIBLE);
                    }
                }, 5000);

            }
        });

        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "bla", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(VolonterActivity.this);
                builder.setTitle("Промена на Име и Презиме");
                builder.setMessage("Внесете ново име и презиме");
                EditText input1 = new EditText(VolonterActivity.this);
                builder.setView(input1);
                String text = input1.getText().toString();
                if(TextUtils.isEmpty(text)) {
                    input1.setError("Не внесовте Име/Презиме !");
                    input1.requestFocus();
                }
                builder.setPositiveButton("Потврдете", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String userKey = mAuth.getCurrentUser().getUid();
                        String text = input1.getText().toString();
                        int idx = text.lastIndexOf(' ');
                        //if (idx == -1)
//                            throw new IllegalArgumentException("Внесовте само име/презиме: " + text);
                        String firstName = text.substring(0, idx);
                        String lastName  = text.substring(idx + 1);
                        FirebaseDatabase.getInstance().getReference("Users").child(userKey).child("FirstName").setValue(firstName).addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Успешна промена на вашето име", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Настана грешка обидете се повторно", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        FirebaseDatabase.getInstance().getReference("Users").child(userKey).child("LastName").setValue(lastName).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Успешна промена на вашето презиме", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Настана грешка обидете се повторно", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User userProfile = snapshot.getValue(User.class);

                                if(userProfile != null){
                                    String ime1 = userProfile.FirstName;
                                    String prezime1 = userProfile.LastName;
                                    String fullname = ime1+" "+prezime1;
                                    //View vi = inflater.inflate(R.layout.navheader, null);
                                    ime.setText(fullname);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getApplicationContext(), "Настана грешка", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                });
                builder.setNegativeButton("Излез", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog ad = builder.create();
                ad.show();

            }



        });
        editEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VolonterActivity.this);
                builder.setTitle("Промена на E-mail");
                builder.setMessage("Внесете нов E-mail");
                EditText input1 = new EditText(VolonterActivity.this);
                builder.setView(input1);

                String text = input1.getText().toString();
                if(!Patterns.EMAIL_ADDRESS.matcher(text).matches()){
                    input1.setError("Не внесовте валидна e-mail адреса !");
                    input1.requestFocus();
                }
                builder.setPositiveButton("Потврдете", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String userKey = mAuth.getCurrentUser().getUid();
                        String text = input1.getText().toString();
                        FirebaseDatabase.getInstance().getReference("Users").child(userKey).child("Email").setValue(text).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Успешна промена на вашета Е-mail адреса", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Настана грешка обидете се повторно", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User userProfile = snapshot.getValue(User.class);

                                if(userProfile != null){
                                    String Email = userProfile.Email;
                                    //View vi = inflater.inflate(R.layout.navheader, null);
                                    email.setText(Email);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getApplicationContext(), "Настана грешка", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                builder.setNegativeButton("Излез", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog ad = builder.create();
                ad.show();

            }
        });
        editPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VolonterActivity.this);
                builder.setTitle("Промена на телефонски број");
                builder.setMessage("Внесете нов телефонски број");
                EditText input1 = new EditText(VolonterActivity.this);
                builder.setView(input1);
                String text = input1.getText().toString();

                if (!((text.startsWith("007") && text.length() < 9) || (text.startsWith("++389") && text.length() < 13))) {
                    input1.setError("Не внесовте валиден телефонски број +389 или 07");
                    input1.requestFocus();
                }
                builder.setPositiveButton("Потврдете", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String userKey = mAuth.getCurrentUser().getUid();
                        String text = input1.getText().toString();
                        FirebaseDatabase.getInstance().getReference("Users").child(userKey).child("Phone").setValue(text).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Успешна промена на вашиот телефонски број", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Настана грешка обидете се повторно", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User userProfile = snapshot.getValue(User.class);

                                if(userProfile != null){
                                    String Phone = userProfile.Phone;
                                    //View vi = inflater.inflate(R.layout.navheader, null);
                                    phoneNum.setText(Phone);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getApplicationContext(), "Настана грешка", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Излез", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog ad = builder.create();
                ad.show();
            }
        });
        editLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lokacijaAktivity = new Intent(getApplicationContext(), GetLocation.class);
                startActivityForResult(lokacijaAktivity, 777);
            }
        });
    }

    public static Double distanceBetween(LatLng point1, LatLng point2) {
        if (point1 == null || point2 == null) {
            return null;
        }

        return SphericalUtil.computeDistanceBetween(point1, point2);
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
}

