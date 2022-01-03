package com.example.recyplast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;

public class InterfaceUser extends AppCompatActivity {
    Button demande;
    private FirebaseAuth mAuth;
    FusedLocationProviderClient fusedLocationProviderClient;
    DatabaseReference dbUsers;
    User user;
    double lat;
    double alt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface_user);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mAuth = FirebaseAuth.getInstance();

        demande = findViewById(R.id.demandeButton);
        demande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(InterfaceUser.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                     fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if(location != null){
                                lat = location.getLatitude();
                                alt = location.getAltitude();
                                Query query = FirebaseDatabase.getInstance("https://recyplast-d5bd2-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users")
                                                        .orderByChild("email")
                                                        .equalTo(mAuth.getCurrentUser().getEmail().toString());
                                query.addListenerForSingleValueEvent(valueEventListener);


                                Toast.makeText(InterfaceUser.this, " add ",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }else {
                    ActivityCompat.requestPermissions(InterfaceUser.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
            }
        });
    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    user = snapshot.getValue(User.class);

                    Demande demande = new Demande(mAuth.getCurrentUser().getEmail(),user.codePostal,lat,alt);
                    FirebaseDatabase.getInstance("https://recyplast-d5bd2-default-rtdb.europe-west1.firebasedatabase.app").getReference("Demande")
                            .child(user.codePostal+user.name)
                            .setValue(demande).addOnCompleteListener(InterfaceUser.this,new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(InterfaceUser.this, "Demande added successfully", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(InterfaceUser.this, "Demande not created", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
