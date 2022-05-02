package com.example.password;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.password.models.User;
import com.example.password.services.DatabaseService;
import com.example.password.services.SharedData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        EditText name = findViewById(R.id.namesignup);
        EditText email = findViewById(R.id.emailsignup);
        EditText password = findViewById(R.id.passwordsignup);
        Button login = findViewById(R.id.loginBtnSignup);
        Button signup = findViewById(R.id.signupBtn);


        signup.setOnClickListener(v -> {
            if (!name.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){

                DatabaseService.db.collection("users")
                        .whereEqualTo("email",email.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if(task.getResult().isEmpty()) {
                                        User user = new User("",name.getText().toString(),email.getText().toString(),password.getText().toString());
                                        DatabaseService.db.collection("users")
                                                .add(user)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        user.setId(documentReference.getId());
                                                        DatabaseService.db.collection("users")
                                                                .document(documentReference.getId())
                                                                .set(user.toJson());
                                                        SharedData.setUser(user);
                                                        Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                                                        startActivity(intent);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getApplicationContext(), "Something went wrong please try again later", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                    }else{

                                        Toast.makeText(getApplicationContext(), "Email already exist", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Something went wrong please try again later", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }else{
                Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            }

        });


        login.setOnClickListener(v -> {
            SignUp.this.finish();
        });
    }
}