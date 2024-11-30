package edu.sjsu.android.jams;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        ImageView pomodoroImage = view.findViewById(R.id.pomodoro_image);
        ImageView bookImage = view.findViewById(R.id.book_image);
        ImageView computerImage = view.findViewById(R.id.computer_image);
        TextView logoutText = view.findViewById(R.id.logout_inHomepage);
        pomodoroImage.setOnClickListener(this::onClickPomodoro);
        bookImage.setOnClickListener(this::onClickBook);
        computerImage.setOnClickListener(this::onClickComputer);
        logoutText.setOnClickListener(this::logout);
        return view;
    }

    private void logout(View view) {
        Log.d("test", "clicked logout in homepage fragment");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_homepageFragment_to_openingFragment);
    }

    private void onClickComputer(View view) {
        Log.d("test", "clicked computer image in homepage fragment");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_homepageFragment_to_goalFragment);
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