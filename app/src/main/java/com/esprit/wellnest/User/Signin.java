package com.esprit.wellnest.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.esprit.wellnest.R;
import com.esprit.wellnest.bdconfiguration.DBHelper;

public class Signin extends AppCompatActivity {
    EditText username,password;
    Button sigin;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        username = (EditText) findViewById(R.id.username3);
        password = (EditText) findViewById(R.id.password3);
        sigin = (Button) findViewById(R.id.buttonsignin);
        DB =new DBHelper(this);
        sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(Signin.this, "All fields required", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);

                    if (checkuserpass) {
                        String role = DB.getUserRole(user);
                        Toast.makeText(Signin.this, "Login Successful", Toast.LENGTH_SHORT).show();



                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", user);
                    editor.putString("role", role);
                    editor.apply();
                } else {
                    Toast.makeText(Signin.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    });

}
}
