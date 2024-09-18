package com.example.personalmotivator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        // Inflate the layout for each task card item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind the task data to the views
        String task = tasks.get(position);
        holder.textViewTaskName.setText(task);

        // You can add click listeners for the buttons if needed later
        holder.buttonOne.setOnClickListener(v -> {
            // Button 1 functionality (to be added later)
        });

        holder.buttonTwo.setOnClickListener(v -> {
            // Button 2 functionality (to be added later)
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTaskName;
        Button buttonOne;
        Button buttonTwo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTaskName = itemView.findViewById(R.id.text_view_task_name);
            buttonOne = itemView.findViewById(R.id.button_one);
            buttonTwo = itemView.findViewById(R.id.button_two);
        }
    }
}
