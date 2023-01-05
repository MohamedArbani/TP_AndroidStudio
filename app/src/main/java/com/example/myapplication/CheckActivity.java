package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        TextView tv3 = findViewById(R.id.textView3);
        TextView tv4 = findViewById(R.id.textView4);

        Intent intent = getIntent();
        String chg1 = intent.getStringExtra("challenge1Str");
        String chg2 = intent.getStringExtra("challenge2Str");

        tv3.setText(chg1);
        tv4.setText(chg2);

    }

    public void okBtnClick(View view){

        EditText sommeTxt = findViewById(R.id.sommeTxt);
        TextView tv3 = findViewById(R.id.textView3);
        TextView tv4 = findViewById(R.id.textView4);

        int tv3Value = Integer.parseInt(tv3.getText().toString());
        int tv4Value = Integer.parseInt(tv4.getText().toString());

        if(!sommeTxt.getText().toString().equals("")){
            Intent mainIntent = new Intent(this, MainActivity.class);
            int somme = Integer.parseInt(sommeTxt.getText().toString());

            mainIntent.putExtra("sommeStr", sommeTxt.getText().toString());
            mainIntent.putExtra("tv3Str", ""+tv3Value);
            mainIntent.putExtra("tv4Str", ""+tv4Value);

            setResult(1,mainIntent);
            finish();

        }
    }

    public void cancelBtnClick(View view){
        Intent myIntent = new Intent(this, MainActivity.class);
        Toast.makeText(getApplicationContext(), "l’opération a été annulée", Toast.LENGTH_SHORT).show();
        startActivity(myIntent);
    }
}