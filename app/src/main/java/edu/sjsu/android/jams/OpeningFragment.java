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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OpeningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OpeningFragment extends Fragment {

    public OpeningFragment() {
        // Required empty public constructor
    }

    public static OpeningFragment newInstance(String param1, String param2) {
        OpeningFragment fragment = new OpeningFragment();
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
        View view = inflater.inflate(R.layout.fragment_opening, container, false);
        Button enterButton = view.findViewById(R.id.enter_here_button);
        enterButton.setOnClickListener(this::onClick);
        return view;
    }

    private void onClick(View view) {
        Log.d("test", "clicked enter button in opening fragment");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_openingFragment_to_homepageFragment);
    }
}