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
import java.util.List;

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
        List<String> category1List = new ArrayList<String>(List.of(getString(R.string.cat1q1), getString(R.string.cat1q2), getString(R.string.cat1q3), getString(R.string.cat1q4), getString(R.string.cat1q5)));
        List<String> category2List = new ArrayList<String>(List.of(getString(R.string.cat2q1), getString(R.string.cat2q2), getString(R.string.cat2q3), getString(R.string.cat2q4), getString(R.string.cat2q5)));
        List<String> category3List = new ArrayList<String>(List.of(getString(R.string.cat3q1), getString(R.string.cat3q2), getString(R.string.cat3q3), getString(R.string.cat3q4), getString(R.string.cat3q5)));
        List<String> category4List = new ArrayList<String>(List.of(getString(R.string.cat4q1), getString(R.string.cat4q2), getString(R.string.cat4q3), getString(R.string.cat4q4), getString(R.string.cat4q5)));
        List<String> category5List = new ArrayList<String>(List.of(getString(R.string.cat5q1), getString(R.string.cat5q2), getString(R.string.cat5q3), getString(R.string.cat5q4), getString(R.string.cat5q5)));
        List<String> category6List = new ArrayList<String>(List.of(getString(R.string.cat6q1), getString(R.string.cat6q2), getString(R.string.cat6q3), getString(R.string.cat6q4), getString(R.string.cat6q5)));
        List<String> category7List = new ArrayList<String>(List.of(getString(R.string.cat7q1), getString(R.string.cat7q2), getString(R.string.cat7q3), getString(R.string.cat7q4), getString(R.string.cat7q5)));
        List<String> category8List = new ArrayList<String>(List.of(getString(R.string.cat8q1), getString(R.string.cat8q2), getString(R.string.cat8q3), getString(R.string.cat8q4), getString(R.string.cat8q5)));
        // Adding the question categories and their questions
        questionCategoryList.add(new QuestionCategory(R.string.category1, category1List));
        questionCategoryList.add(new QuestionCategory(R.string.category2, category2List));
        questionCategoryList.add(new QuestionCategory(R.string.category3,category3List));
        questionCategoryList.add(new QuestionCategory(R.string.category4, category4List));
        questionCategoryList.add(new QuestionCategory(R.string.category5, category5List));
        questionCategoryList.add(new QuestionCategory(R.string.category6, category6List));
        questionCategoryList.add(new QuestionCategory(R.string.category7, category7List));
        questionCategoryList.add(new QuestionCategory(R.string.category8, category8List));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prompt_all, container, false);

        // Set up RecyclerView and its adapter after view inflation
        recyclerView = view.findViewById(R.id.question_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new QuestionCategoryAdapter(questionCategoryList, position -> {
            // Get the clicked category
            QuestionCategory clickedCategory = questionCategoryList.get(position);

            // Create a Bundle to pass the data to the next fragment
            Bundle bundle = new Bundle();
            bundle.putParcelable(getString(R.string.argument_key), clickedCategory);

            // Navigate to PromptSpecificFragment and pass the data
            NavController controller = Navigation.findNavController(view);
            controller.navigate(R.id.action_promptAllFragment_to_promptSpecificFragment, bundle);
        });
        recyclerView.setAdapter(adapter);

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