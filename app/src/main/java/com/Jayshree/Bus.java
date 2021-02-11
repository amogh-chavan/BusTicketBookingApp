package com.Jayshree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


public class Bus extends AppCompatActivity  {
    String item,item2,sDate;
    Button search,datebtn;
    TextView i,sdate;
    String a,b,c,d;
    TextInputLayout startingdropdown,droppingdropdown;
    AutoCompleteTextView autoCompleteTextView,autoCompleteTextView2;
    String check;
    ArrayList<String> buses = new ArrayList<>();

    String[] Locations =new String[]
            {"Airoli Circle",
                    "Andheri",
                    "Dombivli(gaondevi mandir)",
                    "Dombivli(garda circle)",
                    "Dombivli(kasturi plaza)",
                    "Dombivli(Manpada pump)",
                    "Dombivli(palava)",
                    "Dombivli(parasmani circle)",
                    "Dombivli(Shivaji stahue)",
                    "Dombivli(Suyog hotel viconaka)",
                    "Dombivli(venktesh pump)",
                    "Kalyan(Sajanand chowk)",
                    "Mumbai(C.S.T)",
                    "Mumbai(dadar)",
                    "Mumbai(ghatkhopar)",
                    "Mumbai(Ghowandi)",
                    "Mumbai(Kanjurmarg)",
                    "Mumbai(kurla)",
                    "Mumbai(Masjid,Ruhani Hotel)",
                    "Mumbai(Nariman Point)NCPA",
                    "Mumbai(Sion)",
                    "Mumbai(Vikroli)",
                    "Mumbai Bhandup(Airoli Naka)",
                    "Shilphata"
            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);


        Intent x=getIntent();
        a=x.getStringExtra("name");
        b=x.getStringExtra("email");
        c=x.getStringExtra("number");
        d=x.getStringExtra("password");

        datebtn=findViewById(R.id.datebtn);
        startingdropdown=findViewById(R.id.text_input_layout1);
        autoCompleteTextView=findViewById(R.id.filled_exposed_dropdown);
        droppingdropdown=findViewById(R.id.text_input_layout2);
        autoCompleteTextView2=findViewById(R.id.filled_exposed_dropdown2);
        sdate=findViewById(R.id.textView8);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        Bus.this,
                        R.layout.dropdown_item,
                        Locations);

        autoCompleteTextView.setAdapter(adapter);
        final ArrayAdapter<String> adapter2 =
                new ArrayAdapter<>(
                        Bus.this,
                        R.layout.dropdown_item,
                        Locations);

        autoCompleteTextView2.setAdapter(adapter2);


        //date
        search=findViewById(R.id.search);


        i=findViewById(R.id.info);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 item = adapterView.getItemAtPosition(i).toString();

            }
        });

        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item2 = adapterView.getItemAtPosition(i).toString();
            }
        });

        selectDate();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if(check!=null) {
                datebtn.setError(null);

                if(item!=null && item2!=null) {

                    if ((item.startsWith("Dombivli") || item.startsWith("Shilphata")) &&
                            (item2.startsWith("Mumbai") || item2.startsWith("Airoli"))) {
                        buscheckDomtoMum();
                    }
                    if ((item2.startsWith("Dombivli") || item2.startsWith("Shilphata")) &&
                            (item.startsWith("Mumbai") || item.startsWith("Airoli"))) {
                        buscheckMumtoDom();
                    }
//                if((item.startsWith("Dombivli") || item.startsWith("Shilphata")) && (item2.startsWith("Andheri"))){
//                    //buscheckDomtoAndi();
//                }

                    if ((item.startsWith("Dombivli") || item.startsWith("Shilphata")) &&
                            (item2.startsWith("Dombivli") || item2.startsWith("Shilphata"))) {
                        droppingdropdown.requestFocus();
                        startingdropdown.requestFocus();
                        Toast.makeText(Bus.this, "invalid location", Toast.LENGTH_LONG).show();
                    }

                    if ((item.startsWith("Mumbai") || item.startsWith("Airoli") || item.startsWith("Kalyan")) &&
                            (item2.startsWith("Mumbai") || item2.startsWith("Airoli") || item2.startsWith("Kalyan"))) {
                        droppingdropdown.requestFocus();
                        startingdropdown.requestFocus();
                        Toast.makeText(Bus.this, "invalid location", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(Bus.this,"locations can not be empty",Toast.LENGTH_SHORT).show();
                }

            }
            else {
                datebtn.requestFocus();
                datebtn.setError("Please select date");
            }
            }

        });

    }



    private void buscheckDomtoMum(){
        selectDate();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("bus");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                buses.clear();
                for(DataSnapshot snapshot : dataSnapshot.child("Dombivli_Mumbai").getChildren()){
                    buses.add(snapshot.getValue().toString());

                }

                Toast.makeText(Bus.this,"Loading ... please wait",Toast.LENGTH_SHORT).show();
                selectDate();
                Intent i=new Intent(Bus.this,Buslist.class);
                i.putExtra("item",item);
                i.putExtra("item2",item2);
                i.putExtra("Buses",buses);
                i.putExtra("date",sDate);
                i.putExtra("name",a);
                i.putExtra("email",b);
                i.putExtra("number",c);
                i.putExtra("password",d);
                Log.i("bus.java time: ", String.valueOf(item+" "+item2+" "+sDate));
                startActivity(i);
                //finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


}

    private void buscheckMumtoDom(){
        selectDate();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("bus");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                buses.clear();
                for(DataSnapshot snapshot : dataSnapshot.child("Mumbai_Dombivli").getChildren()){
                    buses.add(snapshot.getValue().toString());
                }
                Log.i("bus value: ", String.valueOf(buses));
                Toast.makeText(Bus.this,"Loading ... please wait",Toast.LENGTH_SHORT).show();
                selectDate();
                Intent i=new Intent(Bus.this,Buslist.class);
                i.putExtra("item",item);
                i.putExtra("item2",item2);
                i.putExtra("Buses",buses);
                i.putExtra("date",sDate);

                startActivity(i);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


private void selectDate(){
    Calendar calendar=Calendar.getInstance();
    final int year =calendar.get(Calendar.YEAR);
    final int month=calendar.get(Calendar.MONTH);
    final int day=calendar.get(Calendar.DAY_OF_MONTH);

    datebtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final DatePickerDialog datePickerDialog=new DatePickerDialog(Bus.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
                     sDate= dayofmonth + "/" + month + "/" + year;
                    check=sDate;

                    sdate.setText("Selected Date: "+sDate);
                }
            },year,month,day
            );
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() -1000);
            datePickerDialog.show();
        }
    });
}

}