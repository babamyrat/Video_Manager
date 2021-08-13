package com.example.videomanager.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videomanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
   private EditText userETLogin, passETLogin;
   private Button loginBtn;
   private TextView txtRegister;
   private ProgressBar progressBar;
   private FirebaseUser firebaseUser;

    //Firebase
    FirebaseAuth auth;

    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //Checking for user existence
        if (firebaseUser != null){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userETLogin = findViewById(R.id.editTextEmail);
        passETLogin = findViewById(R.id.editTextPass);
        loginBtn = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);
        progressBar = findViewById(R.id.progressBar);

        //Firebase
        auth = FirebaseAuth.getInstance();


        clickRegister();

        //Login Button
        loginBtn.setOnClickListener(view -> {
            String email_text = userETLogin.getText().toString();
            String pass_text = passETLogin.getText().toString();

            // Checking if it is empty
            if(TextUtils.isEmpty(email_text) || TextUtils.isEmpty(pass_text)){
                Toast.makeText(LoginActivity.this, "Please fill the Fields", Toast.LENGTH_SHORT).show();
            } else {
                auth.signInWithEmailAndPassword(email_text, pass_text)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Login failed! ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void clickRegister() {
        txtRegister.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
    }
}