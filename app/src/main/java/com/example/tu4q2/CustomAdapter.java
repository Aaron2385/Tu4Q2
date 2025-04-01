package com.example.tu4q2;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Model> myModelList; // change from ArrayList<String> to ArrayList<Model>

    public CustomAdapter(ArrayList<Model> data) {
        this.myModelList = data;
    }

    public ArrayList<Model> getModelList() {
        return myModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        return new MyViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model model = myModelList.get(position);
        holder.textView.setText(model.getText());

        // Set background based on selection state
        if (model.isSelected()) {
            holder.itemView.setBackgroundColor(Color.GRAY);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }

        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "position : " + position + " text : " + model.getText(), Toast.LENGTH_SHORT).show();
        });

        holder.itemView.setOnLongClickListener(v1 -> {
            if (model.isSelected()) {
                holder.itemView.setBackgroundColor(Color.WHITE);
                model.setSelected(false);
            } else {
                holder.itemView.setBackgroundColor(Color.GRAY);
                model.setSelected(true);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return myModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public MyViewHolder(View view) {
            super(view);
            this.textView = view.findViewById(R.id.label);
        }

    }

}
