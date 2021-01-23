package com.mas.muathbarghothi.delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    EditText memaillogin,mpasswordlogin;
    Button btnlog;
   private FirebaseAuth mAuth;
     Switch active;



    //ProgressDialog preo;

    DatabaseReference ref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnlog = findViewById(R.id.buttonlogin);
        memaillogin = findViewById(R.id.emaillogin);
        mpasswordlogin = findViewById(R.id.passwordlogin);
        active = findViewById(R.id.active);

        mAuth = FirebaseAuth.getInstance();

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=memaillogin.getText().toString();
                String password =mpasswordlogin.getText().toString();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    memaillogin.setError("Email not correct");
                }else{
                    Loginuser(email,password);
                }

            }
        });

    }

    private void Loginuser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                           // FirebaseUser user = mAuth.getCurrentUser();
                           //FirebaseDatabase database = FirebaseDatabase.getInstance();
                             //DatabaseReference myRef = database.getReference("login");
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference uidRef = rootRef.child("login").child(uid);
                            ValueEventListener valueEventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if (snapshot.child("log").getValue(String.class).equals("1")) {
                                        startActivity(new Intent(LoginActivity.this, DashbordActivity.class));
                                        finish();
                                    } else if (snapshot.child("log").getValue(String.class).equals("2")) {
                                        startActivity(new Intent(LoginActivity.this, dashbord_teacher.class));
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "no item", Toast.LENGTH_LONG).show();


                                    }
                                }





                               @Override
                               public void onCancelled(@NonNull DatabaseError error) {

                               }
                        };
                            uidRef.addListenerForSingleValueEvent(valueEventListener);
                            } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(LoginActivity.this, e.getMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        });

    }
}