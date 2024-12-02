package edu.sjsu.android.jams.questions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.sjsu.android.jams.R;

public class QuestionCategoryAdapter extends RecyclerView.Adapter<QuestionCategoryAdapter.ViewHolder> {

    private final List<QuestionCategory> questionCategoryList;
    private final OnItemClickListener clickListener;

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Constructor for the adapter
    public QuestionCategoryAdapter(List<QuestionCategory> items, OnItemClickListener listener) {
        questionCategoryList = items;
        clickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for each row (RecyclerView item)
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_prompt_category_content, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuestionCategory questionCategory = questionCategoryList.get(position);

        // Set the text of the button with the category name or a related value
        // In this case, just setting the number of questions as a placeholder
        holder.categoryButton.setText("Category " + questionCategory.getCategory() + " - ");
    }

    @Override
    public int getItemCount() {
        return questionCategoryList.size();
    }

    // ViewHolder that holds the reference to the UI components
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final Button categoryButton;

        public ViewHolder(View view, OnItemClickListener clickListener) {
            super(view);
            categoryButton = view.findViewById(R.id.question_category);

            // Set the click listener for the entire row
            categoryButton.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
