package com.martin.proektnazadaca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText regName;
    EditText regLName;
    EditText regPhone;
    Spinner spinner;
    EditText regEmail;
    EditText regPasswd;
    TextView login;
    Button registerBtn;
    ProgressBar pbar;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        regEmail = (EditText) findViewById(R.id.register_email);
        regPasswd = (EditText) findViewById(R.id.register_password);
        regName = (EditText) findViewById(R.id.register_name);
        regLName = (EditText) findViewById(R.id.register_lname);
        regPhone = (EditText) findViewById(R.id.register_phone);
        spinner = (Spinner) findViewById(R.id.spinner);
        pbar = (ProgressBar) findViewById(R.id.pbar1);
        login = (TextView) findViewById(R.id.acc);
        registerBtn = (Button) findViewById(R.id.register);

        mAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v1) {
                Intent loginPage = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginPage);
            }
        });

    }
    private void createUser() {
        String email = regEmail.getText().toString();
        String password  = regPasswd.getText().toString();
        String phone = regPhone.getText().toString();
        String name  = regName.getText().toString();
        String lname = regLName.getText().toString();
        String lice = spinner.getSelectedItem().toString();
        if(TextUtils.isEmpty(name)) {
            regName.setError("???? ???????????????? ?????? !");
            regName.requestFocus();
        }
        else if(TextUtils.isEmpty(lname)) {
            regLName.setError("???? ???????????????? ?????????????? !");
            regLName.requestFocus();
        }
        else if(TextUtils.isEmpty(phone)) {
            regPhone.setError("???? ???????????????? ???????????????????? ???????? !");
            regPhone.requestFocus();
        }
        else if(TextUtils.isEmpty(email)){
            regEmail.setError("???? ???????????????? e-mail ???????????? !");
            regEmail.requestFocus();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            regEmail.setError("???? ???????????????? ?????????????? e-mail ???????????? !");
            regEmail.requestFocus();
        }
        else if(TextUtils.isEmpty(password)){
            regPasswd.setError("???? ???????????????? ?????????????? !");
            regPasswd.requestFocus();
        }else if(password.length() < 6) {
            regPasswd.setError("?????????????? ?????????????? ???? ???????????? ???? 6 ?????????????????? !");
            regPasswd.requestFocus();
        }else if((phone.startsWith("007") && phone.length()<9) || (phone.startsWith("++389") && phone.length()<13)){
            regPhone.setError("???? ???????????????? ?????????????? ???????????????????? ???????? +389 ?????? 07");
            regPhone.requestFocus();
        }else if(TextUtils.isEmpty(lice)){
            TextView errorText = (TextView)spinner.getSelectedView();
            errorText.setError("");
            errorText.setText("???????????????? ?????? ???? ????????????????");//changes the selected item text to this
            spinner.requestFocus();
        }
        else{
            pbar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        User user = new User(name, lname, phone, lice, email, "NaN", "1000 Skopje", "0","0",mAuth.getCurrentUser().getUid());
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    pbar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(RegisterActivity.this, "?????????????? ???? ?????????????????????????? !", Toast.LENGTH_SHORT).show();
                                    Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(login);
                                } else {
                                    Toast.makeText(RegisterActivity.this, "???????????? ?????? ???????????????????????? !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        pbar.setVisibility(View.INVISIBLE);
                        Toast.makeText(RegisterActivity.this, "???????????? ?????? ???????????????????????? !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}