package com.Jayshree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Profile extends AppCompatActivity {
    String e;
    String name,email,password,number;
    TextInputLayout Nname,Nnumber,Nemail,Npassword;
    Button update;
    DatabaseReference databaseReference;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        databaseReference= FirebaseDatabase.getInstance().getReference("users");


        Nname=findViewById(R.id.textInputLayout);
        Nnumber=findViewById(R.id.textInputLayout2);
        Nemail=findViewById(R.id.Uemail);
        Npassword=findViewById(R.id.Upassword);
        update=findViewById(R.id.button);
       // e=Nemail.getEditText().getText().toString();
        showAllData();
    }

    private void showAllData() {

        Intent info=getIntent();
        name=info.getStringExtra("name");
        email=info.getStringExtra("email");
        number=info.getStringExtra("number");
        password=info.getStringExtra("password");


        Nname.getEditText().setText(name);
        Nemail.getEditText().setText(email);
        Npassword.getEditText().setText(password);
        Nnumber.getEditText().setText(number);


    }

    public void update(View view){
        if(isNameChanged() || isPasswordChanged() ||isEmailChanged() )
        {
            Toast.makeText(this, "Data has been updated, please login again to see changes", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Data is same and can not be updated", Toast.LENGTH_SHORT).show();
        }

    }



    private boolean isEmailChanged() {

        if(!email.equals(Nemail.getEditText().getText().toString())){

            if (Nemail.getEditText().getText().toString().isEmpty()) {
                Nemail.setError("Please enter email id");
                Nemail.requestFocus();
                return false;
            } else {
                if (Nemail.getEditText().getText().toString().matches(emailPattern)) {

                    Nemail.setError(null);
                    Nemail.setErrorEnabled(false);
                    databaseReference.child(number).child("email").setValue(Nemail.getEditText().getText().toString());
                    email=Nemail.getEditText().getText().toString();
                    return true;
                } else {
                    Nemail.setError("Please enter valid email id");
                    Nemail.requestFocus();
                    return false;
                }
            }


        }
        else
        {
            return false;
        }
    }

    private boolean isPasswordChanged() {
        if(!password.equals(Npassword.getEditText().getText().toString())){
            String noWhiteSpace="\\A\\w{4,20}\\z";
            if(!Npassword.getEditText().getText().toString().matches(noWhiteSpace)){
                Npassword.setError("white spaces are not allowed");
                return false;
            }
            else {
                databaseReference.child(number).child("password").setValue(Npassword.getEditText().getText().toString());
                password = Npassword.getEditText().getText().toString();
                return true;
            }
        }
        else
        {
            return false;
        }

    }

    private boolean isNameChanged() {
        if(!name.equals(Nname.getEditText().getText().toString())){

            if(!Nname.getEditText().getText().toString().matches("[a-zA-Z ]+"))
            {
                Nname.requestFocus();
                Nname.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                return false;
            }
            else {

                databaseReference.child(number).child("name").setValue(Nname.getEditText().getText().toString());
                name = Nname.getEditText().getText().toString();
                return true;
            }
        }
        else
        {
            return false;
        }
    }
}