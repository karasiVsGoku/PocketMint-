package com.example.pocketmint;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private Button login_btn, make_an_account_btn;
    private EditText username_input, email_input, password_input, confirm_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username_input = findViewById(R.id.username_input);
        email_input = findViewById(R.id.email_input);
        password_input = findViewById(R.id.password_input);
        confirm_input = findViewById(R.id.confirm_input);


        login_btn = findViewById(R.id.login_button);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        make_an_account_btn = findViewById(R.id.make_an_account_button);
        make_an_account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = username_input.getText().toString();
                String email = email_input.getText().toString();
                String password = password_input.getText().toString();
                String confirm_password = confirm_input.getText().toString();


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "You must put an email", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(username)) {
                    Toast.makeText(RegisterActivity.this, "You must put a username", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "You must put a password", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(confirm_password)) {
                    Toast.makeText(RegisterActivity.this, "You must confirm your password", Toast.LENGTH_SHORT).show();

                } else if (password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Your password is too weak", Toast.LENGTH_SHORT).show();

                } else if (!password.equals(confirm_password)) {
                    Toast.makeText(RegisterActivity.this, "passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else {
                    createAnAccount(email, username, password);
                }
            }
        });


    }

    private void createAnAccount(String email, String username, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // adds the account the the firebase authenticator
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser currentUser = mAuth.getCurrentUser();

                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");

                    // adds the username to the realtime database
                    userRef.child(currentUser.getUid()).setValue(new User(username)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "You've created an account!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });


                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed, please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}