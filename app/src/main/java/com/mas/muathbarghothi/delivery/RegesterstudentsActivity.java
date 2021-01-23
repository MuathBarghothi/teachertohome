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

public class RegesterstudentsActivity extends AppCompatActivity {
    EditText memailstud,mpasswordstude;
    Button RRstu;
    ProgressDialog progressDialogstu;
    private FirebaseAuth mAuthstu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regesterstudents);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        memailstud=findViewById(R.id.emailtextstudents);
        mpasswordstude=findViewById(R.id.passwordstudents);
        RRstu=findViewById(R.id.RRRs);
        mAuthstu= FirebaseAuth.getInstance();
        progressDialogstu = new ProgressDialog(this);
        progressDialogstu.setMessage("Rigester Student....");
RRstu.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String Email=memailstud.getText().toString().trim();
        String password=mpasswordstude.getText().toString().trim();
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            memailstud.setError("this Email is invalid");
            memailstud.setFocusable(true);


        }else if(password.length()<6){
            mpasswordstude.setError("password is short");
            mpasswordstude.setFocusable(true);
        }else{
            regesterUsersstudent(Email,password);
        }
    }


});
    }

    private void regesterUsersstudent(String email, final String password) {
        progressDialogstu.show();
        mAuthstu.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuthstu.getCurrentUser();
                            String email=user.getEmail();
                            String uid=user.getUid();

                            HashMap<Object,String> hashMap=new HashMap<>();
                            hashMap.put("email",email);
                            hashMap.put("uid",uid);
                            hashMap.put("password",password);
                            hashMap.put("name","");
                            hashMap.put("phone","");
                            hashMap.put("uid","");
                            hashMap.put("log","1");
                            FirebaseDatabase database=FirebaseDatabase.getInstance();

                            DatabaseReference reference =database.getReference("login");
                            reference.child(uid).setValue(hashMap);

                            Toast.makeText(RegesterstudentsActivity.this,"Regersed"+user.getEmail(),Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegesterstudentsActivity.this, DashbordActivity.class));
                            progressDialogstu.dismiss();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialogstu.dismiss();
                            Toast.makeText(RegesterstudentsActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialogstu.dismiss();
                Toast.makeText(RegesterstudentsActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}







