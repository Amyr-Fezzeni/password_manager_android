package com.example.password;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.password.models.Password;
import com.example.password.services.DatabaseService;

import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter {

    Activity context;
    ArrayList<Password> passwords;

    CustomAdapter(Activity cntx, ArrayList<Password> passwords){
        super(cntx, R.layout.fragment_custom_adapter, passwords);
        this.context = cntx;
        this.passwords = passwords;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View result;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView =  inflater.inflate(R.layout.fragment_custom_adapter, parent, false);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        ImageView image = (ImageView) convertView.findViewById(R.id.imgIcon);
        TextView username = (TextView) convertView.findViewById(R.id.username);
        TextView password = (TextView) convertView.findViewById(R.id.password);
        ImageButton visibilityBtn = (ImageButton) convertView.findViewById(R.id.visibilityBtn);
        ImageButton copyBtn = (ImageButton) convertView.findViewById(R.id.copyBtn);
        result = convertView;

        Password pass =  passwords.get(position);
        image.setImageResource(pass.getIconId());
        title.setText(pass.getTitle());
        username.setText(pass.getUsername());
        visibilityBtn.setActivated(true);
        password.setText(getStars(pass.getPassword()));
        visibilityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!visibilityBtn.isActivated()){
                   password.setText(getStars(pass.getPassword()));
                   visibilityBtn.setActivated(true);

               }else {
                   password.setText(pass.getPassword());
                   visibilityBtn.setActivated(false);
               }
            }
        });
        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Password copied on clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        result.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                Toast.makeText(CustomAdapter.super.getContext(), "Long Click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return result;


    }


    String getStars(String pass){
        String stars = "";
    for (int i = 0; i < pass.length(); i++) {

        stars += "*";
    }
    return stars;
}



}