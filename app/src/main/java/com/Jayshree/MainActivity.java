package com.Jayshree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private String a,b,c,d;
    CardView bus,car,customer,booking;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        mFirebaseAuth = FirebaseAuth.getInstance();


        Intent i=getIntent();
         a=i.getStringExtra("name");
         b=i.getStringExtra("email");
         c=i.getStringExtra("number");
         d=i.getStringExtra("password");
        //cardview
        bus=findViewById(R.id.busCardView);
        car=findViewById(R.id.carCardView);
        customer=findViewById(R.id.customercareCardView);
        booking=findViewById(R.id.bookingCardView);

        bus.setOnClickListener(this);
        car.setOnClickListener(this);
        customer.setOnClickListener(this);
        booking.setOnClickListener(this);

        //cardview



        //navigation
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        //navigation
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.nav_home:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_profile:
                Intent intent2=new Intent(MainActivity.this,Profile.class);
                intent2.putExtra("name",a);
                intent2.putExtra("email",b);
                intent2.putExtra("number",c);
                intent2.putExtra("password",d);
                startActivity(intent2);
                break;
            case R.id.nav_logout:
                mFirebaseAuth.signOut();
                finish();
                startActivity(new Intent(this,Login.class));
                break;
            case R.id.nav_settings:
//                Intent intent4=new Intent(MainActivity.this,setting.class);
//                startActivity(intent4);
//                Toast.makeText(this,"loading...",Toast.LENGTH_SHORT).show();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;

            case R.id.nav_rate:
                Intent SendIntent = new Intent();
                SendIntent.setAction(Intent.ACTION_SEND);
                SendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                SendIntent.setType("text/plain");
                startActivity(SendIntent);
                Toast.makeText(this,"loading...",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_aboutus:
                Intent intent6=new Intent(MainActivity.this,Aboutus.class);
                startActivity(intent6);
                Toast.makeText(this,"loading...",Toast.LENGTH_SHORT).show();
                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;


    }
    public  void Call()
    {
        // Find the EditText by its unique ID
        String e = "9820695727";

        // show() method display the toast with message
        // "clicked"
        Toast.makeText(this, "Loading", Toast.LENGTH_LONG)
                .show();

        // Use format with "tel:" and phoneNumber created is
        // stored in u.
        Uri u = Uri.parse("tel:" + e);

        // Create the intent and set the data for the
        // intent as the phone number.
        Intent i = new Intent(Intent.ACTION_DIAL, u);

        try
        {
            // Launch the Phone app's dialer with a phone
            // number to dial a call.
            startActivity(i);
        }
        catch (SecurityException s)
        {
            // show() method display the toast with
            // exception message.
            Toast.makeText(this,"Error:"+ s, Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onClick(View view) {

        Intent i;

        switch (view.getId())
        {
            case R.id.busCardView:
                i=new Intent(this,Bus.class);
                i.putExtra("name",a);
                i.putExtra("email",b);
                i.putExtra("number",c);
                i.putExtra("password",d);
                startActivity(i);
                //finish();
                break;

            case R.id.carCardView:
//                i=new Intent(this,Car.class);
//                startActivity(i);
//                finish();
                break;
            case R.id.customercareCardView:
//                i=new Intent(this,Customer.class);
//                startActivity(i);
                Call();

                break;
            case R.id.bookingCardView:
//                i=new Intent(this,MainActivity.class);
//                startActivity(i);
                break;
            default: break;

        }

    }




}