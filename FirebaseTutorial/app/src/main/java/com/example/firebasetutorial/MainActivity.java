package com.example.firebasetutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button btnLogOut;
    Button udpBtn;
    FirebaseAuth mAuth;
    FirebaseDatabase fDatabase;
    DatabaseReference dRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText userNameUpd = findViewById(R.id.userNameUpd);
        EditText userStepUpd = findViewById(R.id.userStepUpd);
        btnLogOut = findViewById(R.id.btnLogout);
        DBUser dbu = new DBUser();
        udpBtn = findViewById(R.id.udpBtn);
        mAuth = FirebaseAuth.getInstance();

        udpBtn.setOnClickListener(v ->
        {
            MyUser myUser = new MyUser(userNameUpd.getText().toString(), userStepUpd.getText().toString());
            dbu.add(myUser).addOnSuccessListener(suc->
            {
                Toast.makeText(this , "Record inserted successfully", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(er->
            {
                Toast.makeText(this, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
            });

        });

        btnLogOut.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}