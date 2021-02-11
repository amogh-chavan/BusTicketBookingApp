package com.Jayshree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SelectedBus extends AppCompatActivity {
    private String Bname, Btime ,Bpickup ,Bdrop,Blocation,Bnumber,Bbus_track,Bseats,Bseats_available,Bseats_booked,Bplate_number;
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,seatText;
    CardView bt;
    String pickup,drop,date;
    String username,usernumber;
    String sT;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_bus);
        seatText=findViewById(R.id.seattext);


        getdata();
        bt=findViewById(R.id.bookticket);


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdata();
                Intent a=new Intent(SelectedBus.this,SeatSelection.class);
                //Intent a=new Intent(SelectedBus.this,BookTicket.class);
                a.putExtra("name",Bname);
                a.putExtra("time",Btime);
                a.putExtra("plate_number",Bplate_number);
                a.putExtra("pickup",pickup);
                a.putExtra("drop",drop);
                a.putExtra("date",date);
                a.putExtra("Tseats",Bseats);
                a.putExtra("username",username);
                a.putExtra("usernumber",usernumber);
                startActivity(a);
                finish();

            }
        });

        t1=findViewById(R.id.txt1);
        t2=findViewById(R.id.txt2);
        t3=findViewById(R.id.txt3);
        t4=findViewById(R.id.txt4);
        t5=findViewById(R.id.txt5);
        t6=findViewById(R.id.txt6);
        t7=findViewById(R.id.txt7);
        t8=findViewById(R.id.txt8);
        t9=findViewById(R.id.txt9);



        t1.setText("Driver name: "+Bname);
        t2.setText("Driver number: "+Bnumber);
        t3.setText("Bus Time: "+Btime);
        t4.setText("Route: "+Blocation);
        t5.setText("Pickup Locations: "+Bpickup);
        t6.setText("Drop Locations: "+Bdrop);
        t7.setText("Bus plate number: "+Bplate_number);
        t8.setText("Total seats: "+Bseats);

        Linkify.addLinks(t9, Linkify.WEB_URLS);
        t9.setText("Track Bus: "+Bbus_track);


        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("seats").child(Bseats).child("totalavailable");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sT=dataSnapshot.getValue().toString();

                    Log.i("selectedbus amogh check",sT);

                seatText.setText("Available seats "+sT);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getdata(){
        Intent info=getIntent();
        Bname=info.getStringExtra("name");
        Btime=info.getStringExtra("time");
        Bnumber=info.getStringExtra("number");
        Blocation=info.getStringExtra("location");
        Bpickup=info.getStringExtra("pickuplocation");
        Bdrop=info.getStringExtra("droplocation");
        Bbus_track=info.getStringExtra("bus_img");
        Bseats=info.getStringExtra("seats");
        Bseats_available=info.getStringExtra("seats_available");
        Bseats_booked=info.getStringExtra("seats_booked");
        Bplate_number=info.getStringExtra("plate_number");
        pickup=info.getStringExtra("pickup");
        drop=info.getStringExtra("drop");
        date=info.getStringExtra("date");
        username=info.getStringExtra("username");
        usernumber=info.getStringExtra("usernumber");
        Log.i("selectedbus ", String.valueOf(pickup+" "+drop+" "+date+" "+Bname+" "+Btime+" "+Bseats+" "+sT));

    }





}