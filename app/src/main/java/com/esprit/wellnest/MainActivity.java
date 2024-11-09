package com.esprit.wellnest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.esprit.wellnest.User.Signin;
import com.esprit.wellnest.User.Signup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button signup,signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signup= findViewById(R.id.signup);
        signin = findViewById(R.id.signin);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                opensignup();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensignin();
            }
        });

    }
    public void opensignup(){
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }
    public void opensignin(){
        Intent intent = new Intent(this, Signin.class);
        startActivity(intent);

    }
}
