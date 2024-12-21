package com.example.edgefinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    List<data> items;

    // Adapter constructor
    public MyAdapter(Context context, List<data> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for list items
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Binding data to views
        data currentItem = items.get(position);
        holder.id.setText(String.valueOf(currentItem.getId()));  // Assuming getter methods exist
        holder.name.setText(currentItem.getName());  // Using the getters for data
        holder.course.setText(currentItem.getNote());
    }

    @Override
    public int getItemCount() {
        return items.size();  // Return the size of the list
    }

    // ViewHolder class to hold the views
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, course, id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            course = itemView.findViewById(R.id.course);
        }
    }
}
