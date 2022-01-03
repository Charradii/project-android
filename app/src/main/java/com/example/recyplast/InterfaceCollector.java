package com.example.recyplast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InterfaceCollector extends AppCompatActivity {

    ListView listDemandes;
    ArrayList<Demande> arrayDemande;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface_collector);

        listDemandes = (ListView)findViewById(R.id.list);


        loadDataInListView();

    }

    public void loadDataInListView() {
         arrayDemande = (ArrayList<Demande>) getAllDemandes();

        Adapter adapter= new Adapter(this,arrayDemande);

        listDemandes.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public List<Demande> getAllDemandes(){
        ArrayList<Demande> listDemande = new ArrayList<Demande>();
        List<Demande> list = new ArrayList<>();

        Query query = FirebaseDatabase.getInstance("https://recyplast-d5bd2-default-rtdb.europe-west1.firebasedatabase.app").getReference("Demande")
                .orderByChild("isCollected")
                .equalTo(false);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {


                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Demande demande = snapshot.getValue(Demande.class);

                        Toast.makeText(InterfaceCollector.this, "/"+demande.userEmail+"/", Toast.LENGTH_LONG).show();
                        list.add(demande);

                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }
        );
        listDemande =listToArrayList(list);
        return listDemande;
    }
    public static  <T> ArrayList<T>  listToArrayList(List<T> list) {
        ArrayList<T> arrayList = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                arrayList.add(list.get(i));
                i++;
            }
            // or simply
            // arrayList.addAll(list);
        }
        return  arrayList;
    }
}