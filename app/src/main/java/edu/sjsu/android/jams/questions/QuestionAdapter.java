package edu.sjsu.android.jams.questions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.sjsu.android.jams.R;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private final List<String> questionList; // List of questions
    private final OnQuestionClickListener clickListener;

    public interface OnQuestionClickListener {
        void onQuestionClick(String question);
    }

    public QuestionAdapter(List<String> questionList, OnQuestionClickListener listener) {
        this.questionList = questionList;
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_prompt_question_content, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        // Bind the data to the ViewHolder
        String question = questionList.get(position);
        holder.questionText.setText(question);

        // Set up click listener for each question
        holder.questionText.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onQuestionClick(question); // Trigger the click event
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size(); // Number of items in the list
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        private final TextView questionText;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.entry_content_preview); // Make sure this ID matches the TextView in your layout
        }
    }
}
