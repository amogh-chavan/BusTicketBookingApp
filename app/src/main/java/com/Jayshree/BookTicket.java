package com.Jayshree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookTicket extends AppCompatActivity {
    String Bname,time,plate_no,date,pickup,drop;
    ArrayList<String> selectedseat = new ArrayList<>();
    TextView amount, name, upivirtualid;
    EditText note;
    Button send;
    String TAG ="bookticket";
    String username,usernumber;
    final int UPI_PAYMENT = 0;

    int totalbooked;
    int totalavailable;

    String totalseats;
    String wS,mS,aS;
    int windowS,middleS,aisleS;
    DatabaseReference databaseReference;

    //for ticket booking
    DatabaseReference DatabaseReference;
    FirebaseDatabase rootnode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);
        databaseReference= FirebaseDatabase.getInstance().getReference("seats");

        send = (Button) findViewById(R.id.send);
        amount = findViewById(R.id.amount_et);
        note = (EditText)findViewById(R.id.note);
        name =  findViewById(R.id.name);
        upivirtualid =findViewById(R.id.upi_id);

        getdata();




        //seatInfo();
      //  Toast.makeText(BookTicket.this,"value of windowS"+windowS,Toast.LENGTH_SHORT).show();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting the values from the EditTexts
                if (TextUtils.isEmpty(name.getText().toString().trim())){
                    Toast.makeText(BookTicket.this," Name is invalid", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(upivirtualid.getText().toString().trim())){
                    Toast.makeText(BookTicket.this," UPI ID is invalid", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(note.getText().toString().trim())){
                    Toast.makeText(BookTicket.this," Note is invalid", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(amount.getText().toString().trim())){
                    Toast.makeText(BookTicket.this," Amount is invalid", Toast.LENGTH_SHORT).show();
                }else{

                    payUsingUpi(name.getText().toString(), upivirtualid.getText().toString(),
                            note.getText().toString(), amount.getText().toString());

                }


            }
        });
    }


    void payUsingUpi(  String name,String upiId, String note, String amount) {
        Log.e("main ", "name "+name +"--up--"+upiId+"--"+ note+"--"+amount);
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                //.appendQueryParameter("mc", "")
                //.appendQueryParameter("tid", "02125412")
                //.appendQueryParameter("tr", "25584584")
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(BookTicket.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("main ", "response "+resultCode );
        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.e("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.e("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    //when user simply back without payment
                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

//    E/main: response -1
//    E/UPI: onActivityResult: txnId=PTM593a9f0049724e3fa2ac4ce5d349e0aa&responseCode=0&ApprovalRefNo=026451519695&Status=SUCCESS
//    E/UPIPAY: upiPaymentDataOperation: txnId=PTM593a9f0049724e3fa2ac4ce5d349e0aa&responseCode=0&ApprovalRefNo=026451519695&Status=SUCCESS
//    E/UPI: payment successfull: 026451519695

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(BookTicket.this)) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String[] response = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String[] equalStr = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                getdata();
                //seatInfo();
                minusSeat();



                //amogh

                StringBuffer sb = new StringBuffer();

                for (String s : selectedseat) {
                    sb.append(s);
                    sb.append(" ");
                }
                String seatType = sb.toString();

                //amogh
                //Code to handle successful transaction here.

                Toast.makeText(BookTicket.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "payment successfull: "+approvalRefNo);

                //amogh ticket booking
                rootnode = FirebaseDatabase.getInstance();
                DatabaseReference = rootnode.getReference("tickets");

                Booking information = new Booking(username,usernumber,pickup,drop,date,
                        time,seatType,approvalRefNo,plate_no,Bname);
                DatabaseReference.child(approvalRefNo).setValue(information);



                //amogh

                Intent t=new Intent(BookTicket.this,Ticket.class);
                t.putExtra("ApprovalRefNo" ,approvalRefNo);
                t.putExtra("name",Bname);
                t.putExtra("time",time);
                t.putExtra("plate_number",plate_no);
                t.putExtra("pickup",pickup);
                t.putExtra("drop",drop);
                t.putExtra("date",date);
                t.putExtra("seatType",selectedseat);
                t.putExtra("username",username);
                t.putExtra("usernumber",usernumber);
                startActivity(t);
                finish();


            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(BookTicket.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "Cancelled by user: "+approvalRefNo);

            }
            else {
                Toast.makeText(BookTicket.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "failed payment: "+approvalRefNo);

            }
        } else {
            Log.e("UPI", "Internet issue: ");

            Toast.makeText(BookTicket.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }


private boolean isConnectionAvailable(BookTicket bookTicket){
        ConnectivityManager connectivityManager= (ConnectivityManager) bookTicket.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiCon= connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileCon= connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

    if((wifiCon != null &&wifiCon.isConnected()) || (mobileCon != null && mobileCon.isConnected()) )
    {
        return true;
    }
    else{
        return false;
    }

}
private void getdata(){
    Intent i=getIntent();
    Bname=i.getStringExtra("name");
    time=i.getStringExtra("time");
    plate_no=i.getStringExtra("plate_number");
    date=i.getStringExtra("date");
    pickup=i.getStringExtra("pickup");
    drop=i.getStringExtra("drop");
    selectedseat= i.getStringArrayListExtra("seatType");
    totalseats=i.getStringExtra("Tseats");
    wS=i.getStringExtra("windowS");
    aS=i.getStringExtra("aisleS");
    mS=i.getStringExtra("middleS");

    username=i.getStringExtra("username");
    usernumber=i.getStringExtra("usernumber");

    windowS=Integer.parseInt(wS);
    aisleS=Integer.parseInt(aS);
    middleS=Integer.parseInt(mS);

    totalavailable=windowS+middleS+aisleS;
    int xyz=Integer.parseInt(totalseats);
    totalbooked=xyz-totalavailable;
    databaseReference.child(totalseats).child("totalavailable").setValue(totalavailable+"");
    databaseReference.child(totalseats).child("totalbooked").setValue(totalbooked+"");




    Log.i("Bookticket.java: ", String.valueOf(pickup+" "+drop+" "+date+" "+Bname+" "+time+" "+selectedseat+"  total seats:"+totalseats+ " "+middleS+" "+aisleS+" "+windowS + " "+totalavailable+" "+totalbooked));
}



    private boolean minusSeat() {

       // seatInfo();
        getdata();
        if(selectedseat.contains("aisle")){
            String str1 = Integer.toString(aisleS-1);
            databaseReference.child(totalseats).child("aisle").setValue(str1);
            return true;
        }
        else if(selectedseat.contains("middle"))
        {
            String str2 = Integer.toString(middleS-1);
            databaseReference.child(totalseats).child("middle").setValue(str2);
            return true;
        }
        else if(selectedseat.contains("window")){
            String str3 = Integer.toString(windowS-1);
            databaseReference.child(totalseats).child("window").setValue(str3);
            return true;
        }
        else
        {
            return false;
        }



    }

}