package com.Jayshree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Otp2 extends AppCompatActivity {
    TextInputLayout password1,password2;
    String p1,p2;
    Button login;
    String phone_no;
    FirebaseAuth mFirebaseAuth;
    String nameFromDB,emailFromDB;
    DatabaseReference databaseReference;
    String only10digNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp2);

        password1=findViewById(R.id.password1);
        password2=findViewById(R.id.password2);
       login=findViewById(R.id.login);
        databaseReference= FirebaseDatabase.getInstance().getReference("users");
        phone_no=getIntent().getStringExtra("phone_no");
        only10digNo=getIntent().getStringExtra("only10dinphoneNo");


        isUser();
        //validateNewPassword(p1,p2);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validateNewPassword()){
                   // Toast.makeText(Otp2.this, "Error check internet,try again later", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    isUser();
                    Toast.makeText(Otp2.this, "Password changed", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Otp2.this,MainActivity.class);
                    i.putExtra("name",nameFromDB);
                    i.putExtra("email",emailFromDB);
                    i.putExtra("number",only10digNo);
                    i.putExtra("password",p1);

                    startActivity(i);
                    finish();
                }

            }
        });

    }

    private boolean validateNewPassword() {
        String noWhiteSpace="\\A\\w{4,20}\\z";
        p1=password1.getEditText().getText().toString().trim();
        p2=password2.getEditText().getText().toString().trim();
        if (p1.equals(p2)) {

            if (!p1.matches(noWhiteSpace) || !p2.matches(noWhiteSpace)) {
                password1.setError("This fields can not be empty");
                password2.setError("This fields can not be empty");
                password1.requestFocus();
                password2.requestFocus();
                return false;

            } else {
                password1.setError(null);
                password1.setErrorEnabled(false);
                password2.setError(null);
                password2.setErrorEnabled(false);

                databaseReference.child(only10digNo).child("password").setValue(p1);
                return true;
            }

        }
           else{
            password1.setError("Both passwords should be same");
            password1.requestFocus();
            password2.requestFocus();
            return false;
            }


    }


    private void isUser(){

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        Query checkUser=reference.orderByChild("number").equalTo(only10digNo);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    nameFromDB=dataSnapshot.child(only10digNo).child("name").getValue(String.class);
                    emailFromDB=dataSnapshot.child(only10digNo).child("email").getValue(String.class);

//                        Toast.makeText(Otp2.this,"Loading ... please wait",Toast.LENGTH_SHORT).show();
//                        Intent i=new Intent(getApplicationContext(),MainActivity.class);
//                        i.putExtra("name",nameFromDB);
//                        i.putExtra("email",emailFromDB);
//
//                        i.putExtra("password",passwordFromDB);
//
//                        startActivity(i);
//                        finish();

                    }
                    else{
                    Toast.makeText(Otp2.this,"Error Check your internet",Toast.LENGTH_SHORT).show();
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }
}