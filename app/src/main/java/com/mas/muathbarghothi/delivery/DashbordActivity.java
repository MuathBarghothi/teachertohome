package com.mas.muathbarghothi.delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashbordActivity extends AppCompatActivity {
FirebaseAuth firebaseAuth;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);
       actionBar=getSupportActionBar();
        actionBar.setTitle("profile");

firebaseAuth=FirebaseAuth.getInstance();
        BottomNavigationView navigationView=findViewById(R.id.botom_navig);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);
        actionBar.setTitle("Home");
        HomeFragment fragment1=new HomeFragment();
        FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content,fragment1,"");
        ft1.commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    actionBar.setTitle("Home");
                    HomeFragment fragment1=new HomeFragment();
                    FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
                    ft1.replace(R.id.content,fragment1,"");
                    ft1.commit();
                    return true;
                case R.id.nav_acount:
                    actionBar.setTitle("profile");
                    ProfileFragment fragment2=new ProfileFragment();
                    FragmentTransaction ft2=getSupportFragmentManager().beginTransaction();
                    ft2.replace(R.id.content,fragment2,"");
                    ft2.commit();
                    return true;
                case R.id.nav_users:
                    actionBar.setTitle("Users");

                 UsersFragment fragment3=new UsersFragment();
                    FragmentTransaction ft3=getSupportFragmentManager().beginTransaction();
                    ft3.replace(R.id.content,fragment3,"");
                    ft3.commit();
                    return true;

            }
            return false;
        }
    };

    public void checkuserstutes(){
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user != null){

        }else{
            startActivity(new Intent(DashbordActivity.this,MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        checkuserstutes();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if(id==R.id.action_logout){
            firebaseAuth.signOut();
            checkuserstutes();
            }
        return super.onOptionsItemSelected(item);
    }
}