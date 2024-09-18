package com.example.personalmotivator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<String> taskList;
    private Context context;

    public TaskAdapter(Context context) {
        this.context = context;
        this.taskList = TaskData.getInstance().getTasks(); // Get tasks from TaskData singleton
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task_card, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        String task = taskList.get(position);
        holder.taskNameTextView.setText(task); // Set task name in TextView

        // Set up button click listeners
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition(); // Get the updated position
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    // Remove the task from the list
                    TaskData.getInstance().getTasks().remove(adapterPosition);

                    // Notify adapter that the data has changed
                    notifyItemRemoved(adapterPosition);
                    notifyItemRangeChanged(adapterPosition, taskList.size());
                }
            }
        });

        // Button 2 functionality will be added later
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskNameTextView;
        Button buttonDelete, buttonTwo;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskNameTextView = itemView.findViewById(R.id.text_view_task_name);
            buttonDelete = itemView.findViewById(R.id.button_one); // Button 1
            buttonTwo = itemView.findViewById(R.id.button_two); // Button 2
        }
    }
}
