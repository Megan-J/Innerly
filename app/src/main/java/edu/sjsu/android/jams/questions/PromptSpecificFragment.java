package edu.sjsu.android.jams.questions;

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
        Bundle argument = getArguments();
        if (getArguments() != null) {
            String key = requireContext().getString(R.string.argument_key);
            questionCategory = getArguments().getParcelable(key);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentPromptSpecificBinding binding = FragmentPromptSpecificBinding.inflate(inflater);
        binding.category.setText(questionCategory.getCategory());

        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.questions);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        QuestionAdapter questionAdapter = new QuestionAdapter(questionCategory.getQuestions());
        recyclerView.setAdapter(questionAdapter);

        backArrow = binding.getRoot().findViewById(R.id.back_arrow_in_prompts_specific);
        backArrow.setOnClickListener(v -> backToHome(v));

        return binding.getRoot();
    }

    private void backToHome(View view) {
        Log.d("test", "clicked backButton in prompt all fragment");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_promptSpecificFragment_to_promptAllFragment);
    }
}