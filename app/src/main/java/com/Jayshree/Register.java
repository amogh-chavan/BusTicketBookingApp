package com.Jayshree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
   TextInputLayout name,email,number,password;
    FirebaseAuth mFirebaseAuth;
    Button btnSignUp;
    DatabaseReference databaseReference;
    FirebaseDatabase rootnode;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.password1);
        password=findViewById(R.id.password2);
        number=findViewById(R.id.number);
        name=findViewById(R.id.name);
        btnSignUp=findViewById(R.id.materialButton);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String emailval = email.getEditText().getText().toString().trim();
                final String phone_no = number.getEditText().getText().toString().trim();
                final String nameval = name.getEditText().getText().toString();
                final String pwdval = password.getEditText().getText().toString().trim();

                if(!validateemail() | !validatename() | !validatenumber() | !validatepassword()){
                    Toast.makeText(Register.this, "Error check internet,try again later", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    rootnode = FirebaseDatabase.getInstance();
                    databaseReference = rootnode.getReference("users");

                    User information = new User(nameval, phone_no, emailval, pwdval);
                    databaseReference.child(phone_no).setValue(information);


                    Toast.makeText(Register.this, "Registration complete", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Register.this,MainActivity.class);
                    i.putExtra("name",nameval);
                    i.putExtra("email",emailval);
                    i.putExtra("number",phone_no);
                    i.putExtra("password",pwdval);
                    startActivity(i);
                    finish();

                }
            }
        });
    }
    private Boolean validateemail() {
        final String emailval = email.getEditText().getText().toString().trim();
        if (emailval.isEmpty()) {
            email.setError("Please enter email id");
            email.requestFocus();
            return false;
        } else {
            if (emailval.trim().matches(emailPattern)) {

                email.setError(null);
                email.setErrorEnabled(false);
                return true;
            } else {
                email.setError("Please enter valid email id");
                email.requestFocus();
                return false;
            }
        }
    }
    private  Boolean validatenumber(){
        final String phone_no = number.getEditText().getText().toString().trim();
        if (phone_no.isEmpty()) {
            number.setError("Please enter your phone number");
            number.requestFocus();
            return false;
        }
        else if(phone_no.toString().length()<10){
            number.setError("Please enter valid phone number");
            number.requestFocus();
            return false;
        }
        else{
            number.setError(null);
            number.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatename(){
        final String nameval = name.getEditText().getText().toString();
        if (nameval.isEmpty()) {
            name.setError("Please enter your name ");
            name.requestFocus();
            return false;
        }
        else if(!nameval.matches("[a-zA-Z ]+"))
        {
            name.requestFocus();
            name.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            return false;
        }
        else{
            name.setError(null);
            name.setErrorEnabled(false);
            return true;
        }
    }
//    public static boolean isValidPassword(final String password) {
//
//        Pattern pattern;
//        Matcher matcher;
//        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
//        pattern = Pattern.compile(PASSWORD_PATTERN);
//        matcher = pattern.matcher(password);
//
//        return matcher.matches();
//
//    }

    private Boolean validatepassword(){
        final String pwdval = password.getEditText().getText().toString().trim();
        String noWhiteSpace="\\A\\w{4,20}\\z";
        if (pwdval.isEmpty()) {
            password.setError("Please enter your password");
            password.requestFocus();
            return false;

        }
//        else if(pwdval.length()<8 &&!isValidPassword(pwdval)){
//            password.setError("Password not valid");
//            password.requestFocus();
//            return false;
//        }
        else if(!pwdval.matches(noWhiteSpace)){
            password.setError("white spaces are not allowed");
            return false;
        }
        else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }

    }


}
