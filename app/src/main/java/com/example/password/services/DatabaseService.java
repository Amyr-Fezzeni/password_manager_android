package com.example.password.services;

import androidx.annotation.NonNull;

import com.example.password.models.Password;
import com.example.password.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DatabaseService {
    static public FirebaseFirestore db = FirebaseFirestore.getInstance();
    static public ArrayList<Password> passwords = new ArrayList<>();
    static public void addNewPassword(Password pass) {

        db.collection("users")
                .document(SharedData.currentUser.getId())
                .collection("passwords")
                .add(pass.toJson())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        pass.setId(documentReference.getId());
                        db.collection("users")
                                .document(SharedData.currentUser.getId())
                                .collection("passwords")
                                .document(documentReference.getId())
                                .set(pass.toJson());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error adding document"+e);
                    }
                });
        getAllPasswords();
    }
    static public void modifyPassword(Password pass){

        db.collection("users")
                .document(SharedData.currentUser.getId())
                .collection("passwords")
                .document(pass.getId())
                .set(pass.toJson())
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error adding document"+e);
                    }
                });
        getAllPasswords();

    }
    static public void deletePassword(Password pass){
        System.out.println("=================================================================================");
        System.out.println(pass.toString());
        db.collection("users")
                .document(SharedData.currentUser.getId())
                .collection("passwords")
                .document(pass.getId())
                .delete();
        getAllPasswords();

    }

    static public void getAllPasswords() {
        passwords = new ArrayList<>();
        db.collection("users")
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

                        } else {
                            System.out.println("Error getting documents."+ task.getException());
                        }
                    }
                });

    }


}