package com.Jayshree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;


public class Ticket extends AppCompatActivity {
    String name,time,plate_number,pickup,drop,date,refno,seatType;
    String Username,Usernumber;
    Button print;
    TextView sloc,eloc,Time,Date1,Date2,plate_no,dname,refnumber,seat,passengername;

    ArrayList<String> selectedseat = new ArrayList<>();
   // String[] Userinfo = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);




        sloc=findViewById(R.id.startingloc);
        eloc=findViewById(R.id.endingloc);
        Time=findViewById(R.id.bustimepickup);
        Date1=findViewById(R.id.busdatedrop);
        Date2=findViewById(R.id.busdatepickup);
        plate_no=findViewById(R.id.plateno);
        dname=findViewById(R.id.drivername);
        refnumber=findViewById(R.id.displayrefno);
        seat=findViewById(R.id.seatno);
        passengername=findViewById(R.id.passname);

        getdata();

        seat.setText(seatType);
        sloc.setText(pickup);
        eloc.setText(drop);
        Time.setText(time);
        Date1.setText(date);
        Date2.setText(date);
        plate_no.setText(plate_number);
        dname.setText(name);
        refnumber.setText(refno);
        passengername.setText(Username);

        print = findViewById(R.id.goback);
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent g=new Intent(Ticket.this,MainActivity.class);
                startActivity(g);
                finish();
            }
        });

    }
    private void getdata(){
        Intent a=getIntent();
        name=a.getStringExtra("name");
        pickup=a.getStringExtra("pickup");
        drop=a.getStringExtra("drop");
        time=a.getStringExtra("time");
        plate_number=a.getStringExtra("plate_number");
        date=a.getStringExtra("date");
        refno=a.getStringExtra("ApprovalRefNo");
        selectedseat =a.getStringArrayListExtra("seatType");
        Usernumber=a.getStringExtra("usernumber");
        Username=a.getStringExtra("username");

        //Log.i("Ticket.java ", ("user name: "+Username+" user number: "+Usernumber));

        StringBuffer sb = new StringBuffer();

        for (String s : selectedseat) {
            sb.append(s);
            sb.append(" ");
        }
        seatType = sb.toString();


        Log.i("Ticket.java ", String.valueOf(pickup+" "+drop+" "+date+" "+name+" "+time));
    }
}