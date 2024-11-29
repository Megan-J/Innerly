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
        Button loginButton = view.findViewById(R.id.login_button);
        Button signupButton = view.findViewById(R.id.signup_button);
        loginButton.setOnClickListener(this::goToLogin);
        signupButton.setOnClickListener(this::goToSignup);
        return view;
    }

    private void goToLogin(View view) {
        Log.d("test", "clicked login button in opening fragment");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_openingFragment_to_loginFragment);
    }

    private void goToSignup(View view) {
        Log.d("test", "clicked signup button in opening fragment");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_openingFragment_to_signupFragment);
    }

    private void onClick(View view) {
        Log.d("test", "clicked enter button in opening fragment");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_openingFragment_to_homepageFragment);
    }
}