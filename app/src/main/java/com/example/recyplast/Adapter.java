package com.example.recyplast;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    Context context;
    ArrayList<Demande> list;
    private final RecyclerViewInterface recyclerViewInterface;

    public Adapter(Context context,ArrayList<Demande> list, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.list = list;
        this.recyclerViewInterface=recyclerViewInterface;

    }


    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        Demande demande = list.get(position);
        holder.email.setText(demande.userEmail);

        holder.collected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                demande.isCollected=true;
                holder.collected.setEnabled(false);
            }
        });

        holder.direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerViewInterface != null){
                    int pos = holder.getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        recyclerViewInterface.onItemClick(pos);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface ItemClickListener{
        void onItemClick(Demande demande);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView email;
        Button direction;
        Button collected;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface){
            super(itemView);
            email = itemView.findViewById(R.id.emaill);
            direction = itemView.findViewById(R.id.direction);
            collected = itemView.findViewById(R.id.collected);

        }
    }
}
