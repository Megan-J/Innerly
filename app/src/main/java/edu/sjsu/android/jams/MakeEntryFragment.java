package edu.sjsu.android.jams;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class MakeEntryFragment extends Fragment {

    private Button categoryButton;

    public MakeEntryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_make_entry, container, false);
        categoryButton = view.findViewById(R.id.change_prompt);
        ImageView backArrow = view.findViewById(R.id.back_arrow);
        categoryButton.setOnClickListener(this::onClickChangePrompt);
        backArrow.setOnClickListener(this::onClickBackArrow);

        // Check if a prompt was passed back from the PromptSpecificFragment
        if (getArguments() != null) {
            String selectedPrompt = getArguments().getString("selected_question");
            if (selectedPrompt != null) {
                updatePrompt(selectedPrompt);
            }
        }

        return view;
    }

    private void onClickChangePrompt(View view) {
        // Navigate to PromptAllFragment
        Log.d("MakeEntryFragment", "Clicked change prompt");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_makeEntryFragment_to_promptAllFragment);
    }

    private void onClickBackArrow(View view) {
        Log.d("MakeEntryFragment", "Clicked back arrow");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_makeEntryFragment_to_entryListFragment);
    }

    // You will update this method to update the prompt
    public void updatePrompt(String selectedPrompt) {
        categoryButton.setText(selectedPrompt);
    }
}