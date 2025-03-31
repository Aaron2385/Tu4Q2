package com.example.tu4q2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    private ArrayList<String> data;
    public CustomAdapter(ArrayList<String> data){ //constructor read in data
        this.data = data;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //infalte rowlayout and return a row in the list
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        return new MyViewHolder(rowItem);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String s = data.get(position); // changed from data[position]
        holder.textView.setText(s);

        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "position : " + position + " text : " + s, Toast.LENGTH_SHORT).show();
        });
    }
    @Override
    public int getItemCount() {
        return data.size(); // changed from data.length
    }


    //viewHolder class to display a row
    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        private TextView textView;

        public MyViewHolder(View view) { //constructor
            super(view);

            this.textView = view.findViewById(R.id.label);
        }
    }
}