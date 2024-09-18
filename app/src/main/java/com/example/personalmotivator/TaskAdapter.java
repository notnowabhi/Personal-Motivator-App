package com.example.personalmotivator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<String> tasks;

    public TaskAdapter(List<String> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each task item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind the task data to the TextView
        String task = tasks.get(position);
        holder.textViewTask.setText(task);
    }

    @Override
    public int getItemCount() {
        // Return the number of tasks
        return tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTask;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTask = itemView.findViewById(android.R.id.text1);
        }
    }

    // Method to update the task list and notify the adapter
    public void updateTasks(List<String> newTasks) {
        tasks.clear();
        tasks.addAll(newTasks);
        notifyDataSetChanged();
    }
}
