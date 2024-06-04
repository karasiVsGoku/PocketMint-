package com.example.pocketmint;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    private Button register_btn, login_btn;
    private EditText email_input, password_input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email_input = findViewById(R.id.email_input);
        password_input = findViewById(R.id.password_input);

        register_btn = findViewById(R.id.register_button);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        login_btn = findViewById(R.id.login_button);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_input.getText().toString();
                String password = password_input.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "You must put an email", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "You must put a password", Toast.LENGTH_SHORT).show();

                } else {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(LoginActivity.this, SplashScreenActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null)
        {
            try {
                startActivity(new Intent(LoginActivity.this, SplashScreenActivity.class));
                finish();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}