package com.example.hotelsunshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity6 extends AppCompatActivity {

    String fname, lname, email, number, checkin, checkout;
    Button bUpdate, bDelete;
    Database myDb;
    EditText etFirstName, etLastName, etEmail, etNumber, edCheckin, edCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        TextView textView = findViewById(R.id.tv1);
        bUpdate = findViewById(R.id.updateButton);
        bDelete = findViewById(R.id.deleteButton);
        myDb = new Database(this);
        etFirstName = findViewById(R.id.firstname);
        etLastName = findViewById(R.id.lastName);
        etEmail = findViewById(R.id.email);
        etNumber = findViewById(R.id.number);


        edCheckin = (EditText) findViewById(R.id.checkin);
        edCheckout = (EditText) findViewById(R.id.checkout);

        Intent intent = getIntent();
        fname = intent.getStringExtra("fname");
        lname = intent.getStringExtra("lname");
        email = intent.getStringExtra("email");
        number = intent.getStringExtra("number");
        checkin = intent.getStringExtra("checkin");
        checkout = intent.getStringExtra("checkout");

        textView.setText(
                "First Name: " +fname+
                "\nLast Name: " +lname+
                 "\nEmail: " +email+
                 "\nNumber: " +number+
                 "\nCheck In Date: " +checkin+
                 "\nCheck Out Date: " +checkout
        );
    }

    @Override
    protected void onResume() {

        super.onResume();
        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rowId = myDb.updateInfo(
                        etFirstName.getText().toString(),
                        etLastName.getText().toString(),
                        etEmail.getText().toString(),
                        etNumber.getText().toString(),
                        edCheckin.getText().toString(),
                        edCheckout.getText().toString());
                if(rowId >= 1)
                    Toast.makeText(MainActivity6.this, "Successfully Updated", Toast.LENGTH_LONG).show();
            }
        });

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rowId = myDb.deleteInfo(
                        etFirstName.getText().toString()
                );
                if(rowId >= 1)
                    Toast.makeText(MainActivity6.this, "Successfully Deleted", Toast.LENGTH_LONG).show();
            }
        });
    }
}