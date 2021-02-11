package com.Jayshree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Forgotpassword extends AppCompatActivity {
   TextInputLayout email;
    Button account;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        email=findViewById(R.id.findemail);
        account=(Button)findViewById(R.id.findaccount);
        firebaseAuth = FirebaseAuth.getInstance();

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneno = email.getEditText().getText().toString();
                String completephoneno="+91"+phoneno;
//                firebaseAuth.sendPasswordResetEmail(sendmail).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(Forgotpassword.this,"email to reset password has sent",Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(Forgotpassword.this," email not sent,check your internet"+e.getMessage(),Toast.LENGTH_SHORT).show();
//                    }
//                });

                Intent i=new Intent(Forgotpassword.this,Otp.class);
                i.putExtra("phone_no",completephoneno);
                i.putExtra("only10digphoneNo",phoneno);
                startActivity(i);
                finish();
            }
        });
    }
}