package com.example.password;

import android.os.Bundle;

import com.example.password.models.Password;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ListView;

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

        setSupportActionBar(binding.toolbar);

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_screen);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
}