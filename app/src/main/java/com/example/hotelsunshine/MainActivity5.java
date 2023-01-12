package com.example.hotelsunshine;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity5 extends AppCompatActivity {

    Database myDb;

    Calendar myCalendar;
    EditText edCheckin, edCheckout;

    Button bCancel, bProceed, bViewAll, bUpdate, bDelete;
    FloatingActionButton openMenu;

    EditText etFirstName, etLastName, etEmail, etNumber;

    DrawerLayout drawerLayout;
    NavigationView navigationView;


    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        String fname, lname, email, number, checkin, checkout;

        myDb = new Database(this);

        bProceed = findViewById(R.id.proceedButton);
        bCancel = findViewById(R.id.cancelButton);
        bViewAll = findViewById(R.id.viewAllButton);
        bUpdate = findViewById(R.id.updateButton);
        bDelete = findViewById(R.id.deleteButton);


        etFirstName = findViewById(R.id.firstname);
        etLastName = findViewById(R.id.lastName);
        etEmail = findViewById(R.id.email);
        etNumber = findViewById(R.id.number);


        edCheckin = (EditText) findViewById(R.id.checkin);
        edCheckout = (EditText) findViewById(R.id.checkout);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);        openMenu = findViewById(R.id.float1);


        //Drawer
        openMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        calendar();
    }

        //Date Picker
        public void calendar() {
            Calendar myCalendar = Calendar.getInstance();
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }

                private void updateLabel() {
                    String myFormat = "MM/dd/yy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    edCheckin.setText(sdf.format(myCalendar.getTime()));
                }
            };

            edCheckin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(MainActivity5.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

            DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel(myCalendar, edCheckout);
                }

                private void updateLabel(Calendar myCalendar, EditText editText) {
                    String myFormat = "MM/dd/yy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    editText.setText(sdf.format(myCalendar.getTime()));
                }
            };

            edCheckout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(MainActivity5.this, date2, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
        }

        //Add Data
        @Override
        protected void onResume() {

            super.onResume();
            bProceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isAllFieldsChecked = CheckAllFields();

                    if(isAllFieldsChecked){
                        Intent intent = new Intent(MainActivity5.this,MainActivity6.class);
                        boolean isInserted =  myDb.insertData(
                                etFirstName.getText().toString(),
                                etLastName.getText().toString(),
                                etEmail.getText().toString(),
                                etNumber.getText().toString(),
                                edCheckin.getText().toString(),
                                edCheckout.getText().toString());
                        if(isInserted==true) {
                            intent.putExtra("fname", etFirstName.getText().toString());
                            intent.putExtra("lname", etLastName.getText().toString());
                            intent.putExtra("email", etEmail.getText().toString());
                            intent.putExtra("number", etNumber.getText().toString());
                            intent.putExtra("checkin", edCheckin.getText().toString());
                            intent.putExtra("checkout", edCheckout.getText().toString());
                            Toast.makeText(MainActivity5.this,"Data Inserted",Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity5.this,"Data Not Inserted",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

            bCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity5.this.finish();
                    System.exit(0);
                }
            });

            //View All Data
            bViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cursor res = myDb.getAllData();
                    if(res.getCount() == 0) {
                        showMessage("Error", "Nothing found");
                        return;
                    }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            //Log.d("Room Records", "Rooms" + res.getString(0) + res.getString(1) + res.getString(2) + res.getString(3) + res.getString(4) + res.getString(5) + res.getString(6) + res.getString(7));
                            buffer.append("ID: " + res.getString(0) + "\n");
                            buffer.append("First Name: " + res.getString(1) + "\n");
                            buffer.append("Last Name: " + res.getString(2) + "\n");
                            buffer.append("Email: " + res.getString(3) + "\n");
                            buffer.append("Mobile Number: " + res.getString(4) + "\n");
                            buffer.append("Check In Date: " + res.getString(5) + "\n");
                            buffer.append("Check Out Date: " + res.getString(6) + "\n");
                        }
                        showMessage("Data",buffer.toString());
                }
            });


            //Update Data
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
                    if(rowId >= 1){
                        Toast.makeText(MainActivity5.this, "Successfully Updated", Toast.LENGTH_LONG).show();
                    }
                }
            });


            //Delete Data
            bDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int rowId = myDb.deleteInfo(
                            etFirstName.getText().toString()
                    );
                    if(rowId >= 1)
                        Toast.makeText(MainActivity5.this, "Successfully Deleted", Toast.LENGTH_LONG).show();
                }
            });
        }

    private void showMessage(String title,String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


    //Validations
    private boolean CheckAllFields() {
        if(etFirstName.length() == 0){
            etFirstName.setError("This field is required");
            return false;
        }
        if(etLastName.length() == 0){
            etLastName.setError("This field is required");
            return false;
        }
        if (etEmail.length() == 0) {
            etEmail.setError("Email is required");
            return false;
        }
        if(etNumber.length() == 0){
            etNumber.setError("Mobile Number is required");
            return false;
        }
        if(edCheckin.length() == 0){
            edCheckin.setError("This field is required");
            return false;
        }
        if(edCheckout.length() == 0){
            edCheckout.setError("This field is required");
            return false;
        }
        else if(etNumber.length() < 10){
            etNumber.setError("Please enter a valid mobile number");
            return false;
        }
        return true;
    }


}