package com.example.password;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.ActivityNavigator;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.password.models.Password;
import com.example.password.models.User;
import com.example.password.services.DatabaseService;
import com.example.password.services.Scrypt;
import com.example.password.services.SharedData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.material.color.MaterialColors;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (SharedData.darkMode){
            setTheme(R.style.Theme_Dark);
        }else{
            setTheme(R.style.Theme_Light);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button changePassword = findViewById(R.id.changePasswordBtn);
        Button logout = findViewById(R.id.logoutBtn);
        TextView name = findViewById(R.id.nameSettings);
        TextView email = findViewById(R.id.emailSettings);
        Switch darkMode = findViewById(R.id.darkmodeswitch);
        ImageButton back = findViewById(R.id.backBtnSettingd);
        name.setText(SharedData.currentUser.getName());
        email.setText(SharedData.currentUser.getEmail());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        darkMode.setChecked(SharedData.darkMode);
        darkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedData.changeDarkMode(isChecked);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }

        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogChangePassword();
            }
        });


    }
    @Override
    public void finish() {
        super.finish();
        ActivityNavigator.applyPopAnimationsToPendingTransition(this);
    }


    private void showDialogChangePassword(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_change_password);




        Button changePasswordBtn = dialog.findViewById(R.id.changePasswordBtn);
        EditText oldPassword = dialog.findViewById(R.id.oldPassword);
        EditText newPassword = dialog.findViewById(R.id.newPassword);
        EditText confirmPassword = dialog.findViewById(R.id.ConfirmPassword);
        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oldPassword.getText().toString().isEmpty() && newPassword.getText().toString().isEmpty() && confirmPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else if (! newPassword.getText().toString().equals(confirmPassword.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "new password doesn't match", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (SharedData.currentUser.getPassword().equals(Scrypt.crypt(oldPassword.getText().toString()))) {

                        SharedData.currentUser.setPassword(newPassword.getText().toString());
                    DatabaseService.db.collection("users")
                            .document(SharedData.currentUser.getId())
                            .set(SharedData.currentUser.toJson());
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Password changed", Toast.LENGTH_SHORT).show();
                }
                else {
                        Toast.makeText(getApplicationContext(), "Something went wrong please try again later", Toast.LENGTH_SHORT).show();
                    }
                }




            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }


}