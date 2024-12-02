package edu.sjsu.android.jams.questions;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import edu.sjsu.android.jams.R;
import edu.sjsu.android.jams.databinding.FragmentPromptSpecificBinding;

public class PromptSpecificFragment extends Fragment {

    private QuestionCategory questionCategory;
    private ImageView backArrow;

    public PromptSpecificFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionCategory = getArguments().getParcelable("selected_category");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentPromptSpecificBinding binding = FragmentPromptSpecificBinding.inflate(inflater);
        binding.category.setText(questionCategory.getCategory());

        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.questions);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        QuestionAdapter questionAdapter = new QuestionAdapter(questionCategory.getQuestions(), this::onQuestionSelected);
        recyclerView.setAdapter(questionAdapter);

        backArrow = binding.getRoot().findViewById(R.id.back_arrow_in_prompts_specific);
        backArrow.setOnClickListener(v -> navigateBack(v));

        return binding.getRoot();
    }

    private void onQuestionSelected(String selectedQuestion) {
        // Show confirmation dialog
        new AlertDialog.Builder(getContext())
                .setTitle("Change Prompt")
                .setMessage("Do you want to change the prompt to this question?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // If "Yes" is clicked, navigate back with the selected question
                    navigateBackWithQuestion(selectedQuestion);
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // If "No" is clicked, simply dismiss the dialog
                    dialog.dismiss();
                })
                .show();
    }

    private void navigateBackWithQuestion(String selectedQuestion) {
        // Navigate back to MakeEntryFragment with the selected question
        NavController controller = Navigation.findNavController(getView());
        Bundle bundle = new Bundle();
        bundle.putString("selected_question", selectedQuestion);
        controller.navigate(R.id.action_promptSpecificFragment_to_makeEntryFragment, bundle);
    }

    private void navigateBack(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_promptSpecificFragment_to_promptAllFragment);
    }
}