package com.example.firebasetutorial;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBUser {
    private DatabaseReference databaseReference;

    public DBUser(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(MyUser.class.getSimpleName());
    }
    public Task<Void> add(MyUser myUser){
        return databaseReference.push().setValue(myUser);
    }
}
