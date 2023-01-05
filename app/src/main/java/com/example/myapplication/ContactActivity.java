package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {

    int Perm_CTC = 50;
    int CALL_Perm = 78;
    Uri contactUri;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.READ_CONTACTS}, Perm_CTC);
        setContentView(R.layout.activity_contact);
    }


    public void getContact(View view) {
        String uri= "content://contacts/people";
        Intent contactIntent = new Intent(Intent.ACTION_PICK, Uri.parse(uri));
        startActivityForResult(contactIntent, Perm_CTC);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check the permission type using the requestCode
        if (requestCode == Perm_CTC) {
            //the array is empty if not granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "GRANTED CALL", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Perm_CTC) {

            TextView resultContact = findViewById(R.id.resultContact);

            Button dc = findViewById(R.id.buttonDC);

            if(data != null){
                contactUri = data.getData();
                resultContact.setText(contactUri.toString());

                //Enable Details Contact Button
                dc.setEnabled(true);

                Button call = findViewById(R.id.buttonCall);
                //Enable Details Contact Button
                call.setEnabled(true);

                // Affichez le contact uri dans une Toast
                Toast.makeText(this, "Contact uri : "+contactUri+" got successfully" , Toast.LENGTH_LONG).show();
            }else {
                resultContact.setText(null);
                Toast.makeText(this, "Operation canceled", Toast.LENGTH_LONG).show();
            }
        }

        if (requestCode == CALL_Perm){
            Log.i("LIFECYCLE ", getLocalClassName() + " : ici onActivityResult: ");
            Intent intent = data;

            if (intent!=null){
                ActivityCompat.requestPermissions(ContactActivity.this, new String[]
                        {Manifest.permission.CALL_PHONE}, CALL_Perm);
                EditText txt = findViewById(R.id.editTxtPhone);
                if(this.number==null) this.number="0610203040";
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + this.number)));
            }
        }
    }

    @SuppressLint("Range")
    public void afficherDetailsContact(View view){
        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = getContentResolver().query(contactUri, projection, null,null, null);

        //cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

       String contactID = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
                new String[]{contactID},null);

        this.number = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));

//        String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
//        Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
//        cursor.moveToFirst();
//
//        cursor.moveToFirst();
//        int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID);
//        String number = cursor.getString(column);


        cursorPhone.close();
        cursor.close();
        Toast.makeText(this, "Phone number est : "+this.number, Toast.LENGTH_LONG).show();
    }

    @Override
    public void finish() {
        super.onBackPressed();
    }

    public void callNumber(View view) {
        Intent myIntent = new Intent("login.ACTION");
        startActivityForResult(myIntent,CALL_Perm);
    }
}