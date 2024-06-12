package com.example.pocketmint;

import static java.security.AccessController.getContext;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DebtInformationActivity extends AppCompatActivity {

    private final Calendar calendar = Calendar.getInstance();
    private EditText datePicker, reasonTV;
    private TextView receiverText;
    private String receiver_id;
    private Button add_debt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_debt_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        datePicker = findViewById(R.id.date_input);
        receiverText = findViewById(R.id.receiver_text);
        reasonTV = findViewById(R.id.reason_input);
        add_debt = findViewById(R.id.add_debt);

        SharedPreferences prefs = getSharedPreferences("INFO", Context.MODE_PRIVATE);
        receiver_id = prefs.getString("receiver", "none");
        FirebaseDatabase.getInstance().getReference("Users").child(receiver_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                receiverText.setText("Receiver : " + user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        selectedDate();

        add_debt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String reason = reasonTV.getText().toString();
                    Debt debt = new Debt(receiver_id, currentUser.getUid(), reason, datePicker.getText().toString());

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Debts");
                    reference.child(debt.getId()).setValue(debt);
                    startActivity(new Intent(DebtInformationActivity.this, MainActivity.class));
                } catch (Exception e) {
                    Toast.makeText(DebtInformationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void selectedDate() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                datePicker.setText(updateDate());

            }
        };

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DebtInformationActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private String updateDate() {
        String format = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }



}