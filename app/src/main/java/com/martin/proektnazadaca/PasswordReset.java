package com.martin.proektnazadaca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordReset extends AppCompatActivity {
    private EditText emailReset;
    private Button resetButton;
    private ProgressBar pbar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        emailReset = (EditText) findViewById(R.id.reset_email);
        resetButton = (Button) findViewById(R.id.reset);
        pbar = (ProgressBar) findViewById(R.id.pbar);

        mAuth = FirebaseAuth.getInstance();

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

    }
    private void resetPassword() {
        String email = emailReset.getText().toString().trim();

        if(email.isEmpty()){
            emailReset.setError("Задолжително внесување на e-mail адреса!");
            emailReset.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailReset.setError("Внесете валидна e-mail адреса!");
            emailReset.requestFocus();
            return;
        }
        pbar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    pbar.setVisibility(View.INVISIBLE);
                    Intent login = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(login);
                    Toast.makeText(PasswordReset.this, "Проверете ја вашата e-mail адреса за инструкции за ресетирање на вашата лозинка !", Toast.LENGTH_LONG).show();
                }else{
                    pbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(PasswordReset.this, "Настана грешка! Обидете се повторно", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}