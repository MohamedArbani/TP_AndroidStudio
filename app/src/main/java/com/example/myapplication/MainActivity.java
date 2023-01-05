package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static boolean result=false;
    private int CALL_Perm;
    int somme;

//    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    Log.i("LIFECYCLE ", getLocalClassName() + " : ici onActivityResult: ");
//
//                    if (result.getResultCode()== 78){
//                        Intent intent = result.getData();
//
//                        if (intent!=null){
//                            ActivityCompat.requestPermissions(MainActivity.this, new String[]
//                                    {Manifest.permission.CALL_PHONE}, CALL_Perm);
//                            EditText txt = findViewById(R.id.editTxtPhone);
//                            String number = txt.getText().toString();
//                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number)));
//                        }
//                    }
//                }
//            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove the toolbar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }

    public void callNumber(View view){
        //Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
        Intent myIntent = new Intent("login.ACTION");
        startActivityForResult(myIntent,78);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CALL_Perm) {
            //the array is empty if not granted
            if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "GRANTED CALL",Toast.LENGTH_SHORT).show();
        }
    }

    public void searchUrl(View view){

        EditText chg1 = findViewById(R.id.challenge1Txt);
        EditText chg2 = findViewById(R.id.challenge2Txt);

        if(!chg1.getText().toString().equals("") && !chg2.getText().toString().equals("") && !chg1.getText().toString().equals("0") && !chg2.getText().toString().equals("0")){
            Intent checkIntent = new Intent(this, CheckActivity.class);

            checkIntent.putExtra("challenge1Str", chg1.getText().toString());
            checkIntent.putExtra("challenge2Str", chg2.getText().toString());

            startActivityForResult(checkIntent,1);

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 78){
            Log.i("LIFECYCLE ", getLocalClassName() + " : ici onActivityResult: ");
                Intent intent = data;

                if (intent!=null){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]
                            {Manifest.permission.CALL_PHONE}, CALL_Perm);
                    EditText txt = findViewById(R.id.editTxtPhone);
                    String number = txt.getText().toString();
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number)));
                }
        }

        if(resultCode==1){
            EditText chg1 = findViewById(R.id.challenge1Txt);
            EditText chg2 = findViewById(R.id.challenge2Txt);

            this.somme = Integer.parseInt(data.getExtras().getString("sommeStr"));

            int tv3Value = Integer.parseInt(chg1.getText().toString());
            int tv4Value = Integer.parseInt(chg2.getText().toString());

            if (tv3Value + tv4Value == somme) {
                Toast.makeText(getApplicationContext(), "Bravo! vous avez gagné le challenge", Toast.LENGTH_SHORT).show();
                EditText url = findViewById(R.id.edtTxtUrl);
                String urlName = url.getText().toString();
                if (!urlName.startsWith("http://") && !urlName.startsWith("https://")) {
                    urlName = "http://" + urlName;
                }
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlName));
                startActivity(browserIntent);
            } else
                Toast.makeText(getApplicationContext(), "Erreur !La valeur " + somme + "est erroné, la vrai valeur est : " + (tv3Value + tv4Value), Toast.LENGTH_SHORT).show();
        }
    }

    public void persoActivity(View view){
        Intent myIntent = new Intent(this, PersoActivity.class);
        startActivity(myIntent);
    }

    public void toContactPage(View view) {
        Intent myIntent = new Intent(this, ContactActivity.class);
        startActivity(myIntent);
    }
}