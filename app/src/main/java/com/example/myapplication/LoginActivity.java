package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view){
        EditText txtName =  findViewById(R.id.edtTxtName);
        EditText txtPassword = findViewById(R.id.edtTxtPassword);

        String username = txtName.getText().toString();
        String password = txtPassword.getText().toString();

        TextView txt = findViewById(R.id.txtViewError);

        if (username.equals("admin") && password.equals("admin")){
            Intent intent = new Intent();
            setResult(78,intent);
            intent.putExtra("result", true );
            super.onBackPressed();
        }else {
            txt.setText("wrong username or password");
        }
    }

    public void finish(View view){
        super.onBackPressed();
    }
}