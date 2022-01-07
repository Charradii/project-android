package com.example.recyplast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InterfaceCollector extends AppCompatActivity implements RecyclerViewInterface{

    RecyclerView recyclerView;
    DatabaseReference database;
    Adapter adapter;
    ArrayList<Demande> demandes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface_collector);

        recyclerView = findViewById(R.id.demandeList);
        database = FirebaseDatabase.getInstance("https://recyplast-d5bd2-default-rtdb.europe-west1.firebasedatabase.app").getReference("Demande");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        demandes = new ArrayList<>();
        adapter = new Adapter(this, demandes,this);
        recyclerView.setAdapter(adapter);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Demande demande = dataSnapshot.getValue(Demande.class);
                    demandes.add(demande);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onItemClick(int position) {

        Intent i = new Intent(InterfaceCollector.this,ShowLocation.class);
        i.putExtra("lat",demandes.get(position).lat);
        i.putExtra("alt",demandes.get(position).alt);
        startActivity(i);

    }
}
