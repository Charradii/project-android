package com.example.recyplast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {
    Context adapterContext;
    ArrayList<Demande> arrayList;
    public Adapter(Context context,  ArrayList<Demande> listDemande){

        adapterContext = context;
        arrayList=listDemande;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position){
        return this.arrayList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater rowInflater= (LayoutInflater)adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView =rowInflater.inflate(R.layout.customlistview,null);

        TextView num = convertView.findViewById(R.id.number);
        TextView name = convertView.findViewById(R.id.name);
        Demande demande = arrayList.get(position);
        num.setText(String.valueOf(position));
        name.setText(demande.userEmail);
        return  convertView;
    }
}
