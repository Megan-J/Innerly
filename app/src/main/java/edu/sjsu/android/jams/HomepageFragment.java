package edu.sjsu.android.jams;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class HomepageFragment extends Fragment {


    public HomepageFragment() {
        // Required empty public constructor
    }

    public static HomepageFragment newInstance(String param1, String param2) {
        HomepageFragment fragment = new HomepageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        ImageView pomodoroImage = view.findViewById(R.id.pomodoro_image);
        ImageView bookImage = view.findViewById(R.id.book_image);
        ImageView computerImage = view.findViewById(R.id.computer_image);
        pomodoroImage.setOnClickListener(this::onClickPomodoro);
        bookImage.setOnClickListener(this::onClickBook);
        computerImage.setOnClickListener(this::onClickComputer);
        return view;
    }

    private void onClickComputer(View view) {
        Log.d("test", "clicked computer image in homepage fragment");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_homepageFragment_to_goalItemFragment);
    }

    private void onClickBook(View view) {
        Log.d("test", "clicked book image in homepage fragment");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_homepageFragment_to_entryListFragment);
    }

    private void onClickPomodoro(View view) {
        Log.d("test", "clicked pomodoro image in homepage fragment");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_homepageFragment_to_pomodoroFragment);
    }
}