package com.example.password;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.password.models.Password;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.password.databinding.ActivityHomeScreenBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityHomeScreenBinding binding;
    ArrayList<Password> passwords;
    CustomAdapter adapter;
    ListView l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        setSupportActionBar(binding.toolbar);


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAddPassword();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        l = findViewById(R.id.listPasswords);
        passwords = new ArrayList<Password>();
        passwords.add(new Password("1","Facebook","anotique@gmail.com","password"));

        passwords.add(new Password("2","Google","amyrfezzeni@gmail.com","password"));

        passwords.add(new Password("3","Youtube","anotique@gmail.com","password"));

        passwords.add(new Password("4","Linkedin","amyrfezzeni@gmail.com","password"));
        adapter = new CustomAdapter(HomeScreen.this, passwords);
        l.setAdapter(adapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_screen);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
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
        Button addBtn = dialog.findViewById(R.id.addPasswordBtn);
        EditText email = dialog.findViewById(R.id.emailAdd);
        EditText password = dialog.findViewById(R.id.passwordAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                pass.setUsername(email.getText().toString());
                pass.setPassword(password.getText().toString());
                System.out.println(pass.toString());
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

}