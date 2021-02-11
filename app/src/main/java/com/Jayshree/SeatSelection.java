package com.Jayshree;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class SeatSelection extends AppCompatActivity {
    ImageButton aisle,middle,window;
    String Bname,Btime,Bplate_number,pickup,drop,date,totalseats;
    Button p;
    String wS,mS,aS;
    int windowS,middleS,aisleS;
     int totalbooked;
     int totalavailable;
    String username,usernumber;
    ArrayList<String> seatselected = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);
        getdata();
        seatInfo();
        aisle=findViewById(R.id.imageButton);
        middle=findViewById(R.id.imageButton3);
        window=findViewById(R.id.imageButton2);
        p=findViewById(R.id.button2);

        aisle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seatselected.clear();

                if(aisleS>0) {
                    seatselected.add("aisle");
                    Log.i("seat type: ", String.valueOf(seatselected));
                }
                else{
                    Toast.makeText(SeatSelection.this,"ALL aisle seats are booked",Toast.LENGTH_SHORT).show();
                }
            }
        });

        middle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seatselected.clear();

                if(middleS>0) {
                    seatselected.add("middle");
                    Log.i("seat type: ", String.valueOf(seatselected));
                }
                else{
                    Toast.makeText(SeatSelection.this,"ALL middle seats are booked",Toast.LENGTH_SHORT).show();
                }
            }
        });


        window.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seatselected.clear();

                if(windowS>0) {
                    Log.i("windowS value: ", String.valueOf(windowS));
                    seatselected.add("window");
                    Log.i("seat type: ", String.valueOf(seatselected));
                }
                else{
                    Toast.makeText(SeatSelection.this,"ALL window seats are booked",Toast.LENGTH_SHORT).show();
                }
            }
        });


        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(totalavailable>0) {

                    if (seatselected.isEmpty()) {
                        Toast.makeText(SeatSelection.this, "Please select seat type", Toast.LENGTH_SHORT).show();
                    } else {

                        Intent i = new Intent(SeatSelection.this, BookTicket.class);
                        //Intent i=new Intent(SeatSelection.this,Ticket.class);
                        i.putExtra("name", Bname);
                        i.putExtra("time", Btime);
                        i.putExtra("plate_number", Bplate_number);
                        i.putExtra("pickup", pickup);
                        i.putExtra("drop", drop);
                        i.putExtra("date", date);
                        i.putExtra("seatType", seatselected);
                        i.putExtra("Tseats", totalseats);
                        i.putExtra("windowS", wS);
                        i.putExtra("middleS", mS);
                        i.putExtra("aisleS", aS);
                        i.putExtra("username", username);
                        i.putExtra("usernumber", usernumber);
                        startActivity(i);
                        finish();
                    }
                }
                else
                {
                    Toast.makeText(SeatSelection.this, "All seats booked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void seatInfo() {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("seats");
        //ref1.child(totalseats).child("totalbooked").setValue(totalbooked);
        //ref1.child(totalseats).child("totalavailable").setValue(totalavailable);

       // Log.i("seatSelection outside", String.valueOf(totalbooked + " " + totalavailable ));
        Query checkBus1 = ref1.orderByChild("TotalSeats").equalTo(totalseats);
        checkBus1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    aS = dataSnapshot.child(totalseats).child("aisle").getValue(String.class);
                    wS = dataSnapshot.child(totalseats).child("window").getValue(String.class);
                    mS = dataSnapshot.child(totalseats).child("middle").getValue(String.class);

                    windowS=Integer.parseInt(wS);
                    middleS=Integer.parseInt(mS);
                    aisleS=Integer.parseInt(aS);

                    totalavailable=windowS+middleS+aisleS;
                    int x=Integer.parseInt(totalseats);

                    totalbooked=x-totalavailable;


                  //  Log.i("seatSelection inside", String.valueOf(totalbooked + " " + totalavailable ));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
        private void getdata(){
            Intent info = getIntent();
            Bname = info.getStringExtra("name");
            Btime = info.getStringExtra("time");
            Bplate_number = info.getStringExtra("plate_number");
            pickup = info.getStringExtra("pickup");
            drop = info.getStringExtra("drop");
            date = info.getStringExtra("date");
            totalseats=info.getStringExtra("Tseats");
            username=info.getStringExtra("username");
            usernumber=info.getStringExtra("usernumber");
            Log.i("seatSelection.java: ", String.valueOf(pickup + " " + drop + " " + date + " " + Bname + " " + Btime + " " + Bplate_number+" "+totalseats));
        }


    }
