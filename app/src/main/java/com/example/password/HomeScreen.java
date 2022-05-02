package com.example.password;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;

import com.example.password.models.Password;
import com.example.password.services.DatabaseService;
import com.example.password.services.Scrypt;
import com.example.password.services.SharedData;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.password.databinding.ActivityHomeScreenBinding;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
public class HomeScreen extends AppCompatActivity {

    private ActivityHomeScreenBinding binding;
     ArrayList<Password> passwords;
    ArrayList<Password> allPasswords;
     CustomAdapter adapter;
     ListView l;
    ActivityResultLauncher<Intent> startActivityForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
                    Intent data = result.getData();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (SharedData.darkMode){
            setTheme(R.style.Theme_Dark);
        }else{
            setTheme(R.style.Theme_Light);
        }
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAddPassword();
            }
        });
        ImageButton settings = findViewById(R.id.settingsBtn);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Settings.class);
                startActivityForResult.launch(intent);
            }
        });

        AutoCompleteTextView search =(AutoCompleteTextView) findViewById(R.id.filterTextview);
        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                filterData(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        refreshData();
        l = findViewById(R.id.listPasswords);
        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDialogModifyPassword(passwords.get(position));
                return false;
            }
        });
    }



    private void filterData(String query){
        passwords = new ArrayList<>();
        for (Password p : allPasswords){
            if (p.getTitle().toLowerCase().contains(query.toLowerCase())){
                passwords.add(p);
            }
        }
        adapter = new CustomAdapter(HomeScreen.this, passwords);
        l.setAdapter(adapter);
    }

    private void showDialogAddPassword(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_add_password);
        ImageView logo = dialog.findViewById(R.id.logoIconAdd);
        Password pass = new Password("","","","");

        EditText title = dialog.findViewById(R.id.titleAdd);
        title.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                pass.setTitle(s.toString());
                logo.setImageResource(pass.getIconId());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        Button addBtn = dialog.findViewById(R.id.AddPasswordBtn);
        EditText email = dialog.findViewById(R.id.emailAdd);
        EditText password = dialog.findViewById(R.id.passwordAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                pass.setUsername(email.getText().toString());
                pass.setPassword(password.getText().toString());
                System.out.println(pass.toString());
                DatabaseService.addNewPassword(pass);
                Toast.makeText(getApplicationContext(), "New Password added", Toast.LENGTH_SHORT).show();
                refreshData();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    private void showDialogModifyPassword(Password pass){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_modif_password);
        ImageView logo = dialog.findViewById(R.id.logoIconModif);
        EditText title = dialog.findViewById(R.id.titleModif);
        Button modifyBtn = dialog.findViewById(R.id.ModifyPasswordBtn);
        Button deleteBtn = dialog.findViewById(R.id.deletePasswordBtn);
        EditText email = dialog.findViewById(R.id.emailModif);
        EditText password = dialog.findViewById(R.id.passwordModif);
        logo.setImageResource(pass.getIconId());
        title.setText(pass.getTitle());
        email.setText(pass.getUsername());
        password.setText(pass.getPassword());


        title.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                pass.setTitle(s.toString());
                logo.setImageResource(pass.getIconId());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                pass.setUsername(email.getText().toString());
                pass.setPassword(password.getText().toString());
                System.out.println(pass.toString());
                DatabaseService.modifyPassword(pass);
                Toast.makeText(getApplicationContext(), "Password Modified", Toast.LENGTH_SHORT).show();
                refreshData();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                pass.setUsername(email.getText().toString());
                pass.setPassword(password.getText().toString());
                DatabaseService.deletePassword(pass);
                Toast.makeText(getApplicationContext(), "Password Deleted", Toast.LENGTH_SHORT).show();
                refreshData();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    void refreshData(){
        passwords = new ArrayList<Password>();

        DatabaseService.db.collection("users")
                .document(SharedData.currentUser.getId())
                .collection("passwords")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                passwords.add(new Password(document.getData()));
                            }
                            for(Password p : passwords){
                                System.out.println(p.toString());
                            }
                            adapter = new CustomAdapter(HomeScreen.this, passwords);
                            allPasswords = passwords;
                            l.setAdapter(adapter);
                        } else {
                            System.out.println("Error getting documents."+ task.getException());
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        startActivity(getIntent(),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        this.finish();



    }
}