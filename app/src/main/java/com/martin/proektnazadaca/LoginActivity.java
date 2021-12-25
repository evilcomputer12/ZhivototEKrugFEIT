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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText loginEmail;
    EditText loginPasswd;
    TextView register;
    TextView reset;
    Button loginBtn;
    ProgressBar pbar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        loginEmail = (EditText) findViewById(R.id.login_email);
        loginPasswd = (EditText) findViewById(R.id.login_password);
        register = (TextView) findViewById(R.id.newUser);
        reset = (TextView) findViewById(R.id.accReset);
        loginBtn = (Button) findViewById(R.id.login1);
        pbar = (ProgressBar) findViewById(R.id.pbar2);

        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent registerPage = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerPage);
            }
        });

        reset.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent resetPage = new Intent(getApplicationContext(), PasswordReset.class);
                startActivity(resetPage);
            }
        });

    }

    private void loginUser() {
        String email = loginEmail.getText().toString();
        String password = loginPasswd.getText().toString();

        if (TextUtils.isEmpty(email)) {
            loginEmail.setError("Не внесовте e-mail адреса !");
            loginEmail.requestFocus();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loginEmail.setError("Не внесовте валидна e-mail адреса !");
            loginEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            loginPasswd.setError("Не внесовте лозинка !");
            loginPasswd.requestFocus();
        } else {
            pbar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Успешно се најавивте !", Toast.LENGTH_SHORT).show();
                        pbar.setVisibility(View.INVISIBLE);
                        Intent aktivnost12 = new Intent(getApplicationContext(), LoggedIn.class);
                        startActivity(aktivnost12);
                    } else {
                        pbar.setVisibility(View.INVISIBLE);
                        Toast.makeText(LoginActivity.this, "Грешка при најава !" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}