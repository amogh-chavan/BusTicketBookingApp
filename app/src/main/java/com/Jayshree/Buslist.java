package com.Jayshree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Buslist extends AppCompatActivity {
    private String B1name, B1time ,B1pickup ,B1drop,B1location,B1number,B1bus_img,B1seats,B1seats_available,B1seats_booked,B1plate_number;
    private String B2name, B2time ,B2pickup ,B2drop,B2location,B2number,B2bus_img,B2seats,B2seats_available,B2seats_booked,B2plate_number;
     String bus1, bus2;
     String pickup,drop,date;
    CardView b1, b2, b3;
    String a,b,c,d;
    TextView b1h, b1t,b2t,b2h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buslist);

        b1 = findViewById(R.id.busno1);
        b2 = findViewById(R.id.busno2);
        b3 = findViewById(R.id.busno3);


        b1h = findViewById(R.id.bus1head);
        b1t = findViewById(R.id.bus1tail);
        b2h =findViewById(R.id.bus2head);
        b2t=findViewById(R.id.bus2tail);



        Intent i = getIntent();
        pickup=i.getStringExtra("item");
        drop=i.getStringExtra("item2");
        date=i.getStringExtra("date");
        a=i.getStringExtra("name");
        b=i.getStringExtra("email");
        c=i.getStringExtra("number");
        d=i.getStringExtra("password");


        Log.i("buslist.java: ", String.valueOf(pickup+" "+drop+" "+date));
        ArrayList<String> buses = i.getStringArrayListExtra("Buses");

        bus1 = buses.get(0);
        bus2 = buses.get(1);
        Log.i("bus value1: ", String.valueOf(bus1));
        Log.i("bus value2: ", String.valueOf(bus2));

        busInfo();


        selectedbus();


    }

    private void selectedbus(){
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Buslist.this,SelectedBus.class);
                i.putExtra("name",B1name);
                i.putExtra("number",B1number);
                i.putExtra("time",B1time);
                i.putExtra("location",B1location);
                i.putExtra("pickuplocation",B1pickup);
                i.putExtra("droplocation",B1drop);
                i.putExtra("bus_img",B1bus_img);
                i.putExtra("seats",B1seats);
                i.putExtra("seats_available",B1seats_available);
                i.putExtra("seats_booked",B1seats_booked);
                i.putExtra("plate_number",B1plate_number);
                i.putExtra("pickup",pickup);
                i.putExtra("drop",drop);
                i.putExtra("date",date);
                i.putExtra("username",a);
                i.putExtra("usernumber",c);


                startActivity(i);
                //finish();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii=new Intent(Buslist.this,SelectedBus.class);
                ii.putExtra("name",B2name);
                ii.putExtra("number",B2number);
                ii.putExtra("time",B2time);
                ii.putExtra("location",B2location);
                ii.putExtra("pickuplocation",B2pickup);
                ii.putExtra("droplocation",B2drop);
                ii.putExtra("bus_img",B2bus_img);
                ii.putExtra("seats",B2seats);
                ii.putExtra("seats_available",B2seats_available);
                ii.putExtra("seats_booked",B2seats_booked);
                ii.putExtra("plate_number",B2plate_number);
                ii.putExtra("pickup",pickup);
                ii.putExtra("drop",drop);
                ii.putExtra("date",date);
                ii.putExtra("username",a);
                ii.putExtra("usernumber",c);


                startActivity(ii);
              //  finish();
            }
        });
    }

    private void busInfo() {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("busInfo");

        Query checkBus1 = ref1.orderByChild("plate_number").equalTo(bus1);
        checkBus1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    B1name = dataSnapshot.child(bus1).child("Dname").getValue(String.class);
                    B1time = dataSnapshot.child(bus1).child("Time").getValue(String.class);
                    B1pickup = dataSnapshot.child(bus1).child("Pickuplocations").getValue(String.class);
                    B1drop = dataSnapshot.child(bus1).child("Droplocations").getValue(String.class);
                    B1location = dataSnapshot.child(bus1).child("location").getValue(String.class);
                    B1plate_number= dataSnapshot.child(bus1).child("plate_number").getValue(String.class);
                    B1seats_available  = dataSnapshot.child(bus1).child("seats_available").getValue(String.class);
                    B1seats_booked  = dataSnapshot.child(bus1).child("seats_booked").getValue(String.class);
                    B1seats  = dataSnapshot.child(bus1).child("seats").getValue(String.class);
                    B1bus_img = dataSnapshot.child(bus1).child("bus_image").getValue(String.class);
                    B1number  = dataSnapshot.child(bus1).child("Dnumber").getValue(String.class);


                    b1h.setText(B1time + "  "+B1location );
                    b1t.setText(B1pickup + "-"+B1drop);
                    Log.i("bus1 time: ", String.valueOf(B1time));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query checkBus2 = ref1.orderByChild("plate_number").equalTo(bus2);
        checkBus2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    B2name = dataSnapshot.child(bus2).child("Dname").getValue(String.class);
                    B2time = dataSnapshot.child(bus2).child("Time").getValue(String.class);
                    B2pickup = dataSnapshot.child(bus2).child("Pickuplocations").getValue(String.class);
                    B2drop = dataSnapshot.child(bus2).child("Droplocations").getValue(String.class);
                    B2location= dataSnapshot.child(bus2).child("location").getValue(String.class);
                    B2plate_number= dataSnapshot.child(bus2).child("plate_number").getValue(String.class);
                    B2seats_available  = dataSnapshot.child(bus2).child("seats_available").getValue(String.class);
                    B2seats_booked  = dataSnapshot.child(bus2).child("seats_booked").getValue(String.class);
                    B2seats  = dataSnapshot.child(bus2).child("seats").getValue(String.class);
                    B2bus_img = dataSnapshot.child(bus2).child("bus_image").getValue(String.class);
                    B2number  = dataSnapshot.child(bus2).child("Dnumber").getValue(String.class);


                    b2h.setText(B2time + "  "+B2location );
                    b2t.setText(B2pickup + "-"+B2drop);
                    Log.i("bus2 time: ", String.valueOf(B2time));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}