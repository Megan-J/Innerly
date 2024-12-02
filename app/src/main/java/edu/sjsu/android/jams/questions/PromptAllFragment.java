package edu.sjsu.android.jams.questions;

import android.content.Context;
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

import java.util.ArrayList;

import edu.sjsu.android.jams.R;

/**
 *  A fragment listing the general prompt category list
 */

public class PromptAllFragment extends Fragment {

    private RecyclerView recyclerView;
    private QuestionCategoryAdapter adapter;
    private ArrayList<QuestionCategory> questionCategoryList;
    private ImageView backArrow;

    public PromptAllFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionCategoryList = new ArrayList<>();

        // Adding the question categories and their questions
        questionCategoryList.add(new QuestionCategory(R.string.category1));
        questionCategoryList.add(new QuestionCategory(R.string.category2));
        questionCategoryList.add(new QuestionCategory(R.string.category3));
        questionCategoryList.add(new QuestionCategory(R.string.category4));
        questionCategoryList.add(new QuestionCategory(R.string.category5));
        questionCategoryList.add(new QuestionCategory(R.string.category6));
        questionCategoryList.add(new QuestionCategory(R.string.category7));
        questionCategoryList.add(new QuestionCategory(R.string.category8));

        // Set up RecyclerView
        recyclerView = getView().findViewById(R.id.question_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new QuestionCategoryAdapter(questionCategoryList, position -> {
            // Handle item click
            // For example, show a toast with the category number
            Toast.makeText(getContext(), "Clicked on category " + (position + 1), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prompt_all, container, false);
        backArrow = view.findViewById(R.id.back_arrow_in_prompts);
        backArrow.setOnClickListener(this::backToHome);
        return view;
    }

    private void backToHome(View view) {
        Log.d("test", "clicked backButton in prompt all fragment");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_promptAllFragment_to_entryListFragment);
    }

}