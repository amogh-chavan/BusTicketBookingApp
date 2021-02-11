package com.Jayshree;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity {

    TextInputLayout number,password;
    Button sp,login;
    TextView forgotpass,errorblock;
    FirebaseAuth mFirebaseAuth;
    String nameFromDB,numberFromDB,emailFromDB,passwordFromDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        number = findViewById(R.id.password1);
        password = findViewById(R.id.password2);
        sp=(Button) findViewById(R.id.signup);
        login = (Button) findViewById(R.id.login);
        forgotpass = (TextView) findViewById(R.id.forgotpassword);
        errorblock = (TextView) findViewById(R.id.errorbox);
        mFirebaseAuth = FirebaseAuth.getInstance();


        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Forgotpassword.class);
//                i.putExtra("name",nameFromDB);
//                i.putExtra("email",emailFromDB);
//                i.putExtra("number",numberFromDB);
//                i.putExtra("password",passwordFromDB);

                startActivity(i);
                finish();

            }
        });


        sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(Login.this, Register.class);
                startActivity(ii);
            }
        });


    }

    private Boolean validateNumber(){
        String numbervall = number.getEditText().getText().toString().trim();

        if (numbervall.isEmpty()) {
            number.setError("Field can't be empty");
            number.requestFocus();
            return false;
        }
        else{
            number.setError(null);
            number.setErrorEnabled(false);
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

    private Boolean validatePassword(){
        String passwordvall = password.getEditText().getText().toString().trim();

        String noWhiteSpace="\\A\\w{4,20}\\z";
        if (passwordvall.isEmpty()) {
            password.setError("Please enter your password");
            password.requestFocus();
            return false;

        }
//        else if(passwordvall.length()<8 &&!isValidPassword(passwordvall)){
//            password.setError("Password not valid");
//            password.requestFocus();
//            return false;
//        }
        else if(!passwordvall.matches(noWhiteSpace)){
            password.setError("white spaces are not allowed");
            return false;
        }
        else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }

    }

    public void loginuser(View view){
        if(!validateNumber() | !validatePassword()){
            return;
        }
        else
        {
            isUser();
        }
    }

    private void isUser(){
        final String userenterednumber=number.getEditText().getText().toString().trim();
        final String userenteredpassword = password.getEditText().getText().toString().trim();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        Query checkUser=reference.orderByChild("number").equalTo(userenterednumber);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    number.setError(null);
                    number.setErrorEnabled(false);

                    passwordFromDB=dataSnapshot.child(userenterednumber).child("password").getValue(String.class);
                    if(passwordFromDB.equals(userenteredpassword)){
                        password.setError(null);
                        password.setErrorEnabled(false);

                         nameFromDB=dataSnapshot.child(userenterednumber).child("name").getValue(String.class);
                         numberFromDB=dataSnapshot.child(userenterednumber).child("number").getValue(String.class);
                         emailFromDB=dataSnapshot.child(userenterednumber).child("email").getValue(String.class);
                        Toast.makeText(Login.this,"Loading ... please wait",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),MainActivity.class);
                        i.putExtra("name",nameFromDB);
                        i.putExtra("email",emailFromDB);
                        i.putExtra("number",numberFromDB);
                        i.putExtra("password",passwordFromDB);

                        startActivity(i);
                        finish();

                    }
                    else{
                        password.setError("Wrong password");
                        password.requestFocus();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                number.setError("No such user exits, sign up by clicking below button");
                number.requestFocus();

            }
        });

    }

}