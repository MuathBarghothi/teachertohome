package com.mas.muathbarghothi.delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegesterActivity extends AppCompatActivity {
EditText memail,mpassword;
Button regbtn;
ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        memail = findViewById(R.id.emailtext);
        mpassword = findViewById(R.id.password);
        regbtn = findViewById(R.id.RRR);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Rigester user....");


  regbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String Email=memail.getText().toString().trim();
    String password=mpassword.getText().toString().trim();
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                memail.setError("rr");
                memail.setFocusable(true);


                }else if(password.length()<6){
        mpassword.setError("pasword is short");
        mpassword.setFocusable(true);
        }else{
        regesterUsers(Email,password);
        }
        }


        });
}

    private void regesterUsers(String email, final String password) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            String email=user.getEmail();
                         String uid=user.getUid();
                            HashMap<Object,String> hashMap=new HashMap<>();
                            hashMap.put("email",email);
                            hashMap.put("password",password);
                            hashMap.put("uid",uid);
                            hashMap.put("name","");
                            hashMap.put("phone","");
                            hashMap.put("uid","");
                            hashMap.put("univer","");
                            hashMap.put("place","");
                            hashMap.put("expir","");
                            hashMap.put("log","2");



                            FirebaseDatabase database=FirebaseDatabase.getInstance();

                            DatabaseReference reference =database.getReference("login");
                            reference.child(uid).setValue(hashMap);



                            Toast.makeText(RegesterActivity.this,"Regersed"+user.getEmail(),Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegesterActivity.this, dashbord_teacher.class));

                            progressDialog.dismiss();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(RegesterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegesterActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}



