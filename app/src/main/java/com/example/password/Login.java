package com.example.password;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;

import com.example.password.models.User;
import com.example.password.services.DatabaseService;
import com.example.password.services.Scrypt;
import com.example.password.services.SharedData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        Button connectBtn = findViewById(R.id.connectBtn);
        Button signupBtn = findViewById(R.id.createAccountBtn);
        email.setText("anotique@gmail.com");
        password.setText("azerty");

        connectBtn.setOnClickListener(v -> {
            if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
                DatabaseService.db.collection("users")
                        .whereEqualTo("email",email.getText().toString())
                        .whereEqualTo("password", Scrypt.crypt(password.getText().toString()))
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if(!task.getResult().isEmpty()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            SharedData.setUser(User.fromJson(document.getData()));
                                            Intent intent = new Intent(Login.this, HomeScreen.class);
                                            startActivity(intent);
                                            break;
                                        }
                                    }else{
                                        Snackbar.make(v, "Email or password incorrect", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                    }
                                } else {
                                    System.out.println("Error getting documents."+ task.getException());
                                }
                            }
                        });

            }
        });

        signupBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, SignUp.class);
            startActivity(intent);
        });

    }
}