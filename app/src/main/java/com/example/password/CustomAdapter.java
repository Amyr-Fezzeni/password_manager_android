package com.example.password;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.password.models.Password;


public class CustomAdapter extends ArrayAdapter {

    Activity context;
    Password[] passwords;

    CustomAdapter(Activity cntx, Password[] passwords){
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



        result = convertView;



        Password pass =  passwords[position];

        title.setText(pass.getTitle());
        username.setText(pass.getUsername());
        String stars = "";
        for (int i = 0; i < pass.getPassword().length(); i++) {

            stars += "*";
        }
       password.setText(stars);

//        if(_note >= 10){
//            image.setImageResource(R.drawable.happy);
//        }
//        else{
//            image.setImageResource(R.drawable.sad);
//        }
//        result.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, getMessage(position, _note), Toast.LENGTH_LONG).show();
//            }
//        });
        return result;
//    }

    }
}