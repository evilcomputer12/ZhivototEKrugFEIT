package com.martin.proektnazadaca;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;





public class PostaroLiceActivity extends AppCompatActivity {
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

    CircleImageView im;

    View headerview;

    ImageView editName;
    ImageView editEmail;
    ImageView editPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postaro_lice);


        mAuth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("Users");

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
                        Intent mainA1 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainA1);
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
        im = (CircleImageView) headerview.findViewById(R.id.kprofil);
        phoneNum = (TextView) headerview.findViewById(R.id.kphone);

        editName = (ImageView) headerview.findViewById(R.id.editName);
        editEmail = (ImageView) headerview.findViewById(R.id.editEmail);
        editPhone = (ImageView) headerview.findViewById(R.id.editPhone);



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
                    String fullname = ime1+" "+prezime1;
                    //View vi = inflater.inflate(R.layout.navheader, null);
                    ime.setText(fullname);
                    email.setText(email1);
                    lice.setText(lice1);
                    phoneNum.setText(phone);


                    final String userKey = mAuth.getCurrentUser().getUid();
                    String profilePicture = userKey+".jpg";
                    StorageReference storageRef = storageReference.child(profilePicture);
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(im);
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
        if(resultUri != null) {
            uploadPicture(resultUri);
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

        FirebaseDatabase.getInstance().getReference("Users").child(userKey).child("ProfilePic").setValue(profilePicture).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                } else {
                }
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

        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "bla", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(PostaroLiceActivity.this);
                builder.setTitle("Промена на Име и Презиме");
                builder.setMessage("Внесете ново име и презиме");
                EditText input1 = new EditText(PostaroLiceActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(PostaroLiceActivity.this);
                builder.setTitle("Промена на E-mail");
                builder.setMessage("Внесете нов E-mail");
                EditText input1 = new EditText(PostaroLiceActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(PostaroLiceActivity.this);
                builder.setTitle("Промена на телефонски број");
                builder.setMessage("Внесете нов телефонски број");
                EditText input1 = new EditText(PostaroLiceActivity.this);
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
    }
}